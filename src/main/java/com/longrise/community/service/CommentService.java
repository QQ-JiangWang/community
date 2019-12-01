package com.longrise.community.service;

import com.longrise.community.dto.CommentDTO;
import com.longrise.community.enums.CommentTypeEnum;
import com.longrise.community.exception.CustomizeErrorCode;
import com.longrise.community.exception.CustomizeException;
import com.longrise.community.mapper.CommentMapper;
import com.longrise.community.mapper.QuestionExtMapper;
import com.longrise.community.mapper.QuestionMapper;
import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
  @Autowired
  private QuestionMapper questionMapper;
  @Autowired
  private CommentMapper commentMapper;
  @Autowired
  private QuestionExtMapper questionExtMapper;
  @Autowired
  private UserMapper userMapper;

  /**
   * 增加回复或者评论
   *
   * @param comment
   */
  @Transactional
  public void insert(Comment comment) {
    if (comment.getParentId() == null) {
      throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
    }
    Integer type = comment.getType();
    if (type == null || !CommentTypeEnum.isExist(type)) {
      throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
    }
    if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
      //增加回复数
      Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
      if (question == null) {
        throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
      }
      commentMapper.insert(comment);
      question.setCommentCount(1);
      questionExtMapper.incCommentCount(question);
    } else {
      //增加评论数
      Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
      if (dbComment == null) {
        throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
      }
      commentMapper.insert(comment);
    }
  }

  /**
   * 获取问题的回复列表数据
   *
   * @param id（问题id）
   * @return
   */
  public List<CommentDTO> listById(Long id) {
    CommentExample commentExample = new CommentExample();
    commentExample.createCriteria()
        .andParentIdEqualTo(id)
        .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
    commentExample.setOrderByClause("gmt_create desc");
    List<Comment> comments = commentMapper.selectByExample(commentExample);
    if(comments.size() == 0){
      return new ArrayList<>();
    }
    //获取去重的所有评论人
    //java8新语法,set相当于去重的遍历
    Set<Long> set = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
    List<Long> userIds = new ArrayList<>();
    userIds.addAll(set);
    //获取所有评论人信息
    UserExample userExample = new UserExample();
    userExample.createCriteria()
        .andIdIn(userIds);
    List<User> users = userMapper.selectByExample(userExample);
    Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

    //将评论人和comment合并为commentDTO
    List<CommentDTO> commentDTOList = comments.stream().map(comment -> {
      CommentDTO commentDTO = new CommentDTO();
      //将遍历的comment对象合并到commentDTO
      BeanUtils.copyProperties(comment,commentDTO);
      commentDTO.setUser(userMap.get(comment.getCommentator()));
      return commentDTO;
    }).collect(Collectors.toList());

    return commentDTOList;
  }
}
