<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring MVC XML Config Example</title>
    <spring:url value="/resources/js/jquery-3.3.1.min.js" var="jqueryJS" />
    <script src="${jqueryJS}"></script>
</head>
<body>
<spring:message code="demokey" />
${message}

</body>
</html>
