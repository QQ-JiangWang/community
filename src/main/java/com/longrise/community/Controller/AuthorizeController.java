package com.longrise.community.Controller;

import com.longrise.community.dto.AccessTokenDTO;
import com.longrise.community.dto.GithubUser;
import com.longrise.community.mapper.UserMapper;
import com.longrise.community.model.User;
import com.longrise.community.provider.GihubProvider;
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
            user1.setGmtCreate(System.currentTimeMillis());
            user1.setGmtModified(user1.getGmtCreate());
            user1.setAvatarUrl(user.getAvatarUrl());
            userMapper.insert(user1);
            //登陆成功，写cookie
            response.addCookie(new Cookie("token",s));
            //request.getSession().setAttribute("user",user);

            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/";
        }
    }
}
