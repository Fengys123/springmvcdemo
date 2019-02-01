<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/event-stream">
</head>
<body>
   <div id="msgFromPush"></div>
   <script type="text/javascript" src="/assets/js/jquery.js"></script>
   <script>
       //Eventsource是新式浏览器的sse客户端
       if(!!window.EventSource)
       {
           console.log('浏览器支持sse');
           var source = new EventSource('anno/push1');
           s = '';

           //当接受到消息时产生
           source.addEventListener('message',function(e){
               console.log('进入message监听');
               s += e.data + "<br/>";
               $("#msgFromPush").html(s);
           });

           //当出现错误时
           source.addEventListener('error',function(e){
               console.log('进入error监听');
               if(e.readyState == EventSource.CLOSED)
               {
                   console.log("连接关闭");
               }
               else
               {
                   console.log(e.readyState);
               }
           },false);

           //当成功建立连接时产生
           source.addEventListener('open',function(e){
               console.log("连接打开");
           },false);
       }
       else
       {
           console.log("您的浏览器不支持SSE");
       }
   </script>
</body>
</html>