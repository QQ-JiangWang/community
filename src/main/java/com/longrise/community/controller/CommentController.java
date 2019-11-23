package com.longrise.community.controller;

import com.longrise.community.dto.CommentDTO;
import com.longrise.community.mapper.CommentMapper;
import com.longrise.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object postComment(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setParentId(commentDTO.getParentId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setCommentator(1L);
        comment.setType(commentDTO.getType());
        commentMapper.insert(comment);
        return null;
    }
}
