package com.dlut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestErrorController
{
    @RequestMapping(value = "testError")
    public String TestError() throws Exception
    {
        throw new Exception("TestErrorMsg");
    }
}
