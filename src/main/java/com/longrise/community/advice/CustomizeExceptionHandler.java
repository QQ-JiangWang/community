package com.longrise.community.advice;

import com.longrise.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 监听所有运行时异常
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handler(Throwable e, Model model){
        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else{
            model.addAttribute("message","服务器冒烟了，要不等下在试试?");
        }
        return new ModelAndView("error");
    }
}
