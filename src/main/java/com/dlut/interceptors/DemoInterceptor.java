package com.dlut.interceptors;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;


public class  DemoInterceptor extends HandlerInterceptorAdapter
{
    final static SimpleDateFormat format = new SimpleDateFormat("yyyy年-MM月-dd日-HH时mm分ss秒");

    /**
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制等处理；
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        System.out.println("[preHandle] startTime:" + format.format(new Date(startTime)));
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        System.out.println("[postHandle] startTime:" + format.format(new Date((Long)request.getAttribute("startTime"))));
        request.removeAttribute("startTime");
        System.out.println("[removing attribute] request");
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面），可以根据ex是否为null判断是否发生了异常，进行日志记录；
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        System.out.println("[afterCompletion] ");
    }

    /**
     * 这个方法会在Controller方法异步执行时开始执行
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        System.out.println("[afterConcurrentHandlingStarted] ");
    }
}
