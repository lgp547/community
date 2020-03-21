package com.study.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount,Integer page, Integer size){



        if (totalCount % size == 0){
            totalPage = totalCount / size;
            if (totalCount == 0){
                totalPage = 1;
            }
        } else {
            totalPage = totalCount /size + 1;
        }

        if (page<1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }


        this.page = page;

        pages.add(page);
        for (int i = 1; i<=3; i++){
            if (page - i > 0){
                pages.add(0,page-i);
            }
            if (page + i <= totalPage){
                pages.add(page + i);
            }
        }



        //判断当前页数
        if (page == 1 ) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        if (page.equals(totalPage)){
            showNext = false;
        } else{
            showNext = true;
        }

        //判断页面是否包含第1页
        if (pages.contains(1) ){
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        if (pages.contains(totalPage)){
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
