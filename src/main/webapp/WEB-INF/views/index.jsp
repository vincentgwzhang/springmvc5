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
<ul>
    <li><a href="home/">HomeComponent</a></li>
    <li><a href="rest/">RestComponent</a></li>
    <li><a href="user/">UserController</a></li>
    <li><a href="model/">ModelController</a></li>
    <li><a href="sessionAttribute/">SessionAttributeController</a></li>
    <li><a href="modelAttribute/">ModelAttributeController</a></li>
    <li><a href="view/">view</a></li>
    <li><a href="redirect/testRedirect">RedirectController: redirect</a></li>
    <li><a href="redirect/testForward">RedirectController: forward</a></li>
    <li><a href="employee">EmployeeController</a></li>
    <li><a href="convert">ConverterDemoController</a></li>
</ul>

</body>
</html>
