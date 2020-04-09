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
    <title>Spring MVC XML Config Example</title>
    <spring:url value="/resources/js/jquery-3.3.1.min.js" var="jqueryJS" />
    <script src="${jqueryJS}"></script>
    <base href="<%=basePath%>" />
</head>
<body>
<a href="<%=basePath%>">Back to home</a><br /><br /><br />

<ul>
    <li><a href="sessionAttribute/case1">case1</a></li>
</ul>

Request Scope: ${requestScope.user}<br />
Session Scope: ${sessionScope.user}<br />
Session School: ${sessionScope.school}<br />

</body>
</html>
