package com.study.community.mapper;

import com.study.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}