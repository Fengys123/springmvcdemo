package com.dlut.controller;

import com.dlut.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestController
{
    @Autowired
    DemoService demoService;

    @RequestMapping(value = "/testRest",produces = "text/plain;charset=UTF-8")
    public @ResponseBody String testRest(Model model)
    {
        return demoService.saySomething();
    }
}
