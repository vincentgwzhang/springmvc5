<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>Spring MVC 5 Demo</title>
    <spring:url value="/resources/js/jquery-3.3.1.min.js" var="jqueryJS" />
    <script src="${jqueryJS}"></script>
    <base href="<%=basePath%>" />
</head>
<body>
<a href="<%=basePath%>">Back to home</a><br /><br /><br />

<form action="user/post" method="post">
    username: <input type="text" name="username" /><br />
    password: <input type="text" name="password" /><br />
    email: <input type="text" name="email" /><br />
    age: <input type="text" name="age" /><br />
    city: <input type="text" name="address.city" /><br />
    province: <input type="text" name="address.province" /><br />
    <input type="submit" value="Test Put Method" />
</form>

</body>
</html>
