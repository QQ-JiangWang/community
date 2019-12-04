package com.longrise.community.controller;

import com.longrise.community.dto.CommentDTO;
import com.longrise.community.dto.QuestionDTO;
import com.longrise.community.enums.CommentTypeEnum;
import com.longrise.community.service.CommentService;
import com.longrise.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
  @Autowired
  private QuestionService questionService;
  @Autowired
  private CommentService commentService;
  /**
   * 查看问题及回复和评论
   * @param id
   * @param model
   * @return
   */
  @GetMapping("/question/{id}")
  public String question(@PathVariable(name="id") Long id,
                         Model model){
    QuestionDTO question = questionService.getQuestionInfo(id);
    questionService.incView(id);
    model.addAttribute("question",question);
    List<CommentDTO> comments = commentService.listById(id,CommentTypeEnum.QUESTION);
    List<QuestionDTO> relatedQuestions = questionService.selectRelated(question);
    model.addAttribute("comments",comments);
    model.addAttribute("relatedQuestions",relatedQuestions);
    return "question";
  }
}
