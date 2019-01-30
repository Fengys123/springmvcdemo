package com.dlut.controller;

import com.dlut.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
public class AysncController
{
    @Autowired
    PushService pushService;

    @RequestMapping("/defer")
    @ResponseBody
    public DeferredResult<String> deferredCall()
    {
        return pushService.getAsyncUpdate();
    }

    /**
     * 下面写的异步方法是什么呀,不对
     * 先注释掉
     * @return
     */
    /*@RequestMapping("/async")
    @ResponseBody
    public String async()
    {
        for(int i=0 ; i<10 ; i++)
        {
            printi(i);
            printi1(i);
        }
        return "async";
    }

    @Async
    public void printi(int i)
    {
        System.out.println("[print i] = " + i);
    }

    @Async
    public void printi1(int i)
    {
        System.out.println("[print i+1] = " + (i+1));
    }*/
}
