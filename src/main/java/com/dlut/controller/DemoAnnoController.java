package com.dlut.controller;

import com.dlut.domain.DemoObj;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

//@Controller
@RestController
@RequestMapping(value = "/anno")
public class DemoAnnoController
{
    /**
     *  produces可定制response的媒体类型和字符集
     */
    @RequestMapping(produces = "text/plain;charset=UTF-8")
    public String index(HttpServletRequest request)
    {
        return "url:" + request.getRequestURL() + "can access";
    }


    /**
     * 演示接受参数 接受路径参数
     */
    @RequestMapping(value = "/pathvar/{str}",produces = "text/plain;charset=UTF-8")
    public String demoPathVar(@PathVariable(value = "str") String str1,@PathVariable(value = "str") String str2)
    {
        return "receved str1 = " + str1 + ",str2 = " + str2;
    }


    /**
     * 获取url参数
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/requestParam" ,produces = "text/plain;charset=UTF-8" )
    public String passRequestParam(Long id,HttpServletRequest request)
    {
        return "url:" + request.getRequestURI() + " can access,id: " + id;
    }


    /**
     * 将url参数转化为model
     * @param obj
     * @param request
     * @return
     */
    @RequestMapping(value="/obj" , produces = "application/json;charset=UTF-8")
    public String passObj(DemoObj obj,HttpServletRequest request)
    {
        return "url:" + request.getRequestURI() + " can access,obj id: " + obj.getId() + ",name: " + obj.getName();
    }


    /**
     * 返回一个对象会被自动转化为json格式
     * produces指明了返回对象的类型
     */
    @RequestMapping(value = "/getjson" , produces = "application/json;charset=UTF-8")
    public DemoObj getjson(DemoObj obj)
    {
        return new DemoObj(obj.getId() + 1,obj.getName() + "yy");
    }


    /**
     * 返回一个对象时,被转为xml格式
     * produces指明了返回对象的类型
     */
    @RequestMapping(value = "/getxml" , produces = {"application/xml;charset=UTF-8"})
    public DemoObj getxml(DemoObj obj)
    {
        //return new DemoObj(obj.getId() + 1,obj.getName() + "yy");
        return obj;
    }

    /**
     * 如果没有@RequestParam,他会根据属性名称进行匹配
     */
    @RequestMapping(value = "/getxmlwithnoobj" , produces = {"application/xml;charset=UTF-8"})
    public DemoObj getxml(String id,String name)
    {
        System.out.println("id:" + Long.valueOf(id));
        System.out.println("name:" + name);
        return new DemoObj(Long.valueOf(id),name);
    }

    /**
     * 如果没有@RequestParam,他会根据属性名称进行匹配
     */
    @RequestMapping(value = "/getxmlwithnoobj1" , produces = {"application/xml;charset=UTF-8"})
    public DemoObj getxml1(@RequestParam(value = "ids") String id, @RequestParam String name)
    {
        System.out.println("id:" + Long.valueOf(id));
        System.out.println("name:" + name);
        return new DemoObj(Long.valueOf(id),name);
    }


    /**
     * 测试系统处理全局错误信息
     * @param msg
     * @param obj
     * @return
     */
    @RequestMapping("/advice")
    public String getSomething(@ModelAttribute("msg") String msg,DemoObj obj)
    {
        System.out.println("msg: " + msg);
        System.out.println("obj.getName(): " + obj.getName());
        System.out.println("obj.getID(): " + obj.getId());
        throw new IllegalArgumentException("非常抱歉,参数有误/" + "来自@ModelAttribute:" + msg);
    }

    /**
     * 本访问路径
     * 会把url中的参数写到对象中,但前提是key与属性值要想对应
     * @param obj
     */
    @RequestMapping("/advice1")
    public void getSomething1(DemoObj obj)
    {
        System.out.println("obj.getName(): " + obj.getName());
        System.out.println("obj.getID(): " + obj.getId());
    }


    @RequestMapping(value = "/upload" , method = RequestMethod.POST)
    public String upload(MultipartFile file)
    {
        try
        {
            System.out.println("[file upload] name: " + file.getOriginalFilename());
            FileUtils.writeByteArrayToFile(new File("e:/upload/" + file.getOriginalFilename()),file.getBytes());
            return "ok";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "wrong";
        }
    }


    @RequestMapping(value = "/convert",produces = {"application/x-wisely"})
    public DemoObj convert(@RequestBody DemoObj demoObj)
    {
        return demoObj;
    }


    /**
     * SSE(server-sent Event)就是浏览器向服务器发送一个http请求,保持常连接,服务器不断地单向的向浏览器推送信息
     * 长轮询:客户端向服务器发送Ajax请求，服务器接到请求后保持连接，直到有新消息才返回响应信息并关闭连接，客户端处理完响应信息后再向服务器发送新的请求
     * 长连接：保持长时间的连接，服务器发送数据后，连接不关闭，下次有新数据时仍然用此连接发送
     * SSE缺点:如果客户端较多的话,就要保持很多长连接
     * @param response
     */
    @RequestMapping(value="/push")
    public void push(HttpServletResponse response)
    {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        //while(true)
        {
            Random r = new Random();
            try
            {
                System.out.println("开始推送");
                PrintWriter writer = response.getWriter();
                writer.write("data: 中文测试 \n\n");
                //这里需要\n\n，必须要，不然前台接收不到值,键必须为data
                writer.flush();
                if(writer.checkError())
                {
                    System.out.println("客户端断开连接!!!");
                    return;
                }
                Thread.sleep(3000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value="/push1",produces="text/event-stream")
    public String push1(HttpServletResponse res){

        res.setHeader("Access-Control-Allow-Origin","*");

        /**
         * 为什么默认是三秒,然后这里休息2秒,总共就是5秒
         */
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String nowDate=sdf.format(date);

        return "data: time is:"+nowDate+"retry:5000"+"\n\n";

    }


}
