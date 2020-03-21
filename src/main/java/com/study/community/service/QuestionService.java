package com.study.community.service;

import com.study.community.dto.PaginationDTO;
import com.study.community.dto.QuestionDTO;
import com.study.community.exception.CustomizeErrorCode;
import com.study.community.exception.CustomizeException;
import com.study.community.mapper.QuestionMapper;
import com.study.community.mapper.UserMapper;
import com.study.community.model.Question;
import com.study.community.model.QuestionExample;
import com.study.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMappaer;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());

        if (page < 1) {
            page = 1;
        }
        paginationDTO.setPagination(totalCount, page, size);


        if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);


        //首先获得Question数据库
//        List<Question> questionList = questionMapper.list(offset, size);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));

        //最终的结果
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList){
            //通过question的id查询得到user数据库内容
            User user = userMappaer.selectByPrimaryKey(question.getCreator());
            //最后返回是带有user的QuestionDTO
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }


    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
//        Integer totalCount = questionMapper.conuntByUserId(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);

        if (page<1){
            page = 1;
        }
        paginationDTO.setPagination(totalCount, page, size);


        if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);

        //首先获得Question数据库
//        List<Question> questionList = questionMapper.listByUserId(userId,offset, size);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        //最终的结果
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList){
            //通过question的id查询得到user数据库内容
            User user = userMappaer.selectByPrimaryKey(question.getCreator());
            //最后返回是带有user的QuestionDTO
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTIOM_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMappaer.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            Question updataQuestion = new Question();
            updataQuestion.setGmtModified(System.currentTimeMillis());
            updataQuestion.setTitle(question.getTitle());
            updataQuestion.setDescription(question.getDescription());
            updataQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updataQuestion, example);
            if (update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTIOM_NOT_FOUND);
            }
        }
    }
}
