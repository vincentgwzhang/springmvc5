<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setDateHeader("Expires", -1);
%>
<html>
<head>
    <title>Spring MVC 5 Demo</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <spring:url value="/resources/js/input.js" var="jqueryJS" />
    <script src="${jqueryJS}"></script>
    <base href="<%=basePath%>"/>
</head>
<body>
<div class="container">
    <a href="<%=basePath%>employee">Back to Employee CRUD index page</a><br/><br/><br/>

    <form:form action="${employee.id == null? 'employee/save':'employee/update'}"
               method="post" modelAttribute="employee">
        <table class="table table-striped table-bordered">
            <c:if test="${employee.id == null}">
            <tr>
                <td>Last name</td>
                <td>
                    <form:input path="lastName" autocomplete="false"/>
                    <form:errors path="lastName"></form:errors>
                </td>
            </tr>
            </c:if>
            <tr>
                <td>Email</td>
                <td><form:input path="email" autocomplete="false"/>
                    <form:errors path="email"></form:errors></td>
            </tr>
            <tr>
                <td>Gender</td>
                <td><form:radiobuttons path="gender" items="${genders}" delimiter="&nbsp;&nbsp;" />
                    <form:errors path="gender"></form:errors></td>
            </tr>
            <tr>
                <td>Department</td>
                <td>
                    <form:select path="department.id"
                                 items="${departments}"
                                 itemLabel="departmentName"
                                 itemValue="id"/>
                </td>
            </tr>
            <tr>
                <td>Birth</td>
                <td>
                    <form:input path="birth" /><form:errors path="birth"></form:errors>
                </td>
            </tr>
            <tr>
                <td>Salary</td>
                <td>
                    <form:input path="salary" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Submit" class="btn btn-primary">
                    <c:if test="${employee.id != null}">
                        <form:hidden path="id" />
                    </c:if>
                </td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
