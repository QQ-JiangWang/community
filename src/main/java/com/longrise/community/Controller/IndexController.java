package com.longrise.community.Controller;

import com.longrise.community.dto.PaginationDTO;
import com.longrise.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public  String hello(
                         Model model,
                         @RequestParam(name="page",defaultValue = "1") Integer page,
                         @RequestParam(name="size",defaultValue = "2") Integer size){

      PaginationDTO paginationDTO = questionService.getQuestionList(page,size);

      model.addAttribute("pagination",paginationDTO);
      return "index";
    }

}
