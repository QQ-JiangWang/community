package com.longrise.community.controller;

import com.longrise.community.dto.NotificationDTO;
import com.longrise.community.model.User;
import com.longrise.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
  @Autowired
  private NotificationService notificationService;

  /**
   * 查看问题及回复和评论
   * @param id
   * @param request
   * @return
   */
  @GetMapping("/notification/{id}")
  public String question(@PathVariable(name="id") Long id,
                         HttpServletRequest request){
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
      return "redirect:/";
    }
    NotificationDTO notificationDTO = notificationService.read(id,user);
    if (notificationDTO != null){
      return "redirect:/question/"+notificationDTO.getOuterId();
    }
    return "redirect:/";
  }
}
