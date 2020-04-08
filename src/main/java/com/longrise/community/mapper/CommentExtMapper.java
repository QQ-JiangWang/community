package com.longrise.community.mapper;

import com.longrise.community.model.Comment;

/**
 * @author Wangjiang
 * @create 2019-12-02 21:21
 */
public interface CommentExtMapper {
  int incCommentCount(Comment comment);
}
