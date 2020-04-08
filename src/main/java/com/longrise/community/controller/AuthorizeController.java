package com.longrise.community.controller;

import com.longrise.community.dto.AccessTokenDTO;
import com.longrise.community.dto.GithubUser;
import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.User;
import com.longrise.community.provider.GihubProvider;
import com.longrise.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GihubProvider gihubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param code
     * @param state
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gihubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = gihubProvider.getUser(accessToken);
        if (user != null){
            User user1 = new User();
            String s = UUID.randomUUID().toString();
            user1.setToken(s);
            user1.setName(user.getName());
            user1.setAccountId(String.valueOf(user.getId()));
            user1.setAvatarUrl(user.getAvatarUrl());
            userService.createOrUpdate(user1);
            //登陆成功，写cookie
            response.addCookie(new Cookie("token",s));
            //request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/";
        }
    }

    /**
     * 用户退出
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
