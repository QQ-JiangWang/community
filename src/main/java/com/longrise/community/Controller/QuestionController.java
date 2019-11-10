package com.longrise.community.Controller;

import com.longrise.community.dto.QuestionDTO;
import com.longrise.community.model.Question;
import com.longrise.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
  @Autowired
  private QuestionService questionService;
  @GetMapping("/question/{id}")
  public String question(@PathVariable(name="id") Integer id,
                         Model model){
    QuestionDTO question = questionService.getQuestionInfo(id);
    model.addAttribute("question",question);
    return "question";
  }
}
