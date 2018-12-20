<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<script type="text/javascript" src="/assets/js/jquery.js"></script>
<script type="text/javascript">
   deferred();

   /**
    * 此处的代码使用的jquery的Ajax请求,所以没有出现浏览器兼容器问题
    * 1.页面打开就向后台发送请求
    * 2.在控制台输出服务端推送的数据
    * 3.一次性请求完成后再向后台发送请求
    */
   function deferred()
   {
       $.get('defer',function(data){
           console.log(data);
           deferred();
       });
   }
</script>
</body>
</html>