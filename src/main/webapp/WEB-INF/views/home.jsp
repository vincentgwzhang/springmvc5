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

<ul>
    <li><a href="home/default">default</a></li>
    <li><a href="home/link">link</a></li>
    <li><a href="home/parametertest?username=vincent&age=37">parametertest</a></li>
    <li><a href="home/requestMapping/!@$&*(()_+/regx">Regex Mapping</a></li>
    <li><a href="home/path/vincent/age/37">Test path variable</a></li>
    <li><a href="home/headertest">Header test</a></li>
    <li><a href="home/cookieValue">Cookie test</a></li>
</ul>

</body>
</html>
