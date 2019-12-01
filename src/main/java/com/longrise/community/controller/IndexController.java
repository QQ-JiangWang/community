package com.longrise.community.controller;

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

    /**
     * 获取首页问题列表
     * @param model
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/")
    public  String hello(
                         Model model,
                         @RequestParam(name="page",defaultValue = "1") Integer page,
                         @RequestParam(name="size",defaultValue = "4") Integer size){

      PaginationDTO paginationDTO = questionService.getQuestionList(page,size);

      model.addAttribute("pagination",paginationDTO);
      return "index";
    }

}
