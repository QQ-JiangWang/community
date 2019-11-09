package com.longrise.community.Controller;

import com.longrise.community.dto.QuestionDTO;
import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.User;
import com.longrise.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public  String hello(HttpServletRequest request,
                         Model model){
      Cookie[] cookies = request.getCookies();
      if(cookies != null && cookies.length>0){
        for(Cookie cookie:cookies){
          if("token".equals(cookie.getName())){
            String token = cookie.getValue();
            User user = userMapper.findByToken(token);
            if(user != null){
              request.getSession().setAttribute("user",user);
            }
            break;
          }
        }
      }
      List<QuestionDTO> questionList = questionService.getQuestionList();
      model.addAttribute("pagination",questionList);
      return "index";
    }

}
