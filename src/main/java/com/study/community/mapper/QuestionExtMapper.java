package com.study.community.mapper;

import com.study.community.model.Question;


public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}