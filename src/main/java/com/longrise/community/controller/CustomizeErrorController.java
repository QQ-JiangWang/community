package com.longrise.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class CustomizeErrorController implements ErrorController {
    @Override
    public String getErrorPath() {

        return "error";
    }
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model){
        HttpStatus status = getStatus(request);
        if(status.is4xxClientError()){
            model.addAttribute("message","你的这个请求不存在，要不然换个姿势？");
        }else if(status.is5xxServerError()){
            model.addAttribute("message","服务器冒烟了,要不然你稍后试试？");
        }
        return new ModelAndView("error");
    }
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
