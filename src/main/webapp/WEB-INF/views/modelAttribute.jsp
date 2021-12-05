<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

<form:form action="modelAttribute/post" method="post" modelAttribute="userKey">
    <form:hidden path="id" />
    username: <form:input path="username" /><br />
    email: <form:input path="email" /><br />
    age: <form:input path="age" /><br />
    <input type="submit" value="Test Model Attribute" />
</form:form>

</body>
</html>
