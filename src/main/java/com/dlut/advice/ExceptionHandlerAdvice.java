package com.dlut.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerAdvice
{
    /**
     * 用于全局处理控制器里异常
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception, WebRequest request)
    {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage",exception.getMessage());
        System.out.println("exception.getMessage():" + exception.getMessage());
        System.out.println("name:" + request.getParameter("name"));
        System.out.println("id:" + request.getParameter("id"));
        return modelAndView;
    }


    /**
     * 将其添加到全局,所有@requestmapping的方法可获得此键值对
     * 有个问题是添加到了哪儿
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model)
    {
        model.addAttribute("msg","额外信息");
    }

    /**
     * 此处需要查看WebDataBinder的API文档
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder)
    {
        //忽略request参数的id
        webDataBinder.setDisallowedFields("id");
    }
}
