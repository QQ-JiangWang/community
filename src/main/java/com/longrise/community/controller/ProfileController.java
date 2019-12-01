package com.longrise.community.controller;

import com.longrise.community.dto.PaginationDTO;
import com.longrise.community.model.User;
import com.longrise.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

  @Autowired
  private QuestionService questionService;

  /**
   * 个人中心列表点击
   * @param request
   * @param model
   * @param action
   * @param page
   * @param size
   * @return
   */
  @GetMapping("/profile/{action}")
  public  String profile(HttpServletRequest request,
                       Model model,
                       //获取请求路由
                       @PathVariable(name="action") String action,
                       @RequestParam(name="page",defaultValue = "1") Integer page,
                       @RequestParam(name="size",defaultValue = "2") Integer size){
    User user = (User)request.getSession().getAttribute("user");
    if(user == null){
      return "redirect:/";
    }
    if("questions".equals(action)){
      model.addAttribute("section","questions");
      model.addAttribute("sectionName","我的提题");
      PaginationDTO paginationDTO = questionService.getQuestionListByUserId(user.getId(),page,size);
      model.addAttribute("pagination",paginationDTO);
    }else if("replies".equals(action)){
      model.addAttribute("section","replies");
      model.addAttribute("sectionName","最新回复");
      PaginationDTO paginationDTO = questionService.getQuestionListByUserId(user.getId(),page,size);
      model.addAttribute("pagination",paginationDTO);
    }

    return "profile";
  }
}
