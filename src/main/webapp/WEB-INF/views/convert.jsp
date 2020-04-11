<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>Spring MVC 5 Demo</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <base href="<%=basePath%>"/>
</head>
<body>
<div class="container">
    <a href="<%=basePath%>">Back to home</a><br/><br/><br/>

        <form action="convert" method="post">
            <div class="form-group">
                <label for="employee">Employee:(lastname - email - gender - department.id)</label>
                <input type="text" class="form-control" id="employee" name="employee" autocomplete="false" value="Zhang - vincentzhang@outlook.es - 1 - 105" />
            </div>
            <div class="form-group">
                <input type="submit" value="Submit" class="btn btn-info">
            </div>
        </form>
    <br /><br /><br />
    <c:if test="${requestScope.employee != null}">
        Receive object, employee = ${requestScope.employee}
    </c:if>
</div>
</body>
</html>
