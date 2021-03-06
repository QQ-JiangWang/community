package com.longrise.community.controller;

import com.longrise.community.dto.CommentCreateDTO;
import com.longrise.community.dto.CommentDTO;
import com.longrise.community.dto.ResultDTO;
import com.longrise.community.enums.CommentTypeEnum;
import com.longrise.community.exception.CustomizeErrorCode;
import com.longrise.community.exception.CustomizeException;
import com.longrise.community.model.Comment;
import com.longrise.community.model.User;
import com.longrise.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 问题的回复和评论
     * @param commentCreateDTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object postComment(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_IS_NULL);
        }
        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());
        comment.setType(commentCreateDTO.getType());
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }

    /**
     * 获取回复的评论
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name="id") Long id){
        List<CommentDTO> commentDTOS = commentService.listById(id, CommentTypeEnum.COMMENT);

        return  ResultDTO.okOf(commentDTOS);
    }
}
