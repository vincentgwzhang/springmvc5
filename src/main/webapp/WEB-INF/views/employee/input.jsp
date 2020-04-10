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
    <title>Spring MVC XML Config Example</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <base href="<%=basePath%>"/>
</head>
<body>
<div class="container">
    <a href="<%=basePath%>/employee">Back to Employee CRUD index page</a><br/><br/><br/>
    <form:form action="employee/save" method="post" modelAttribute="employee">
        <table class="table table-striped table-bordered">
            <c:if test="${employee.id == null}">
            <tr>
                <td>Last name</td>
                <td>
                    <form:input path="lastName" autocomplete="false"/>
                </td>
            </tr>
            </c:if>
            <tr>
                <td>Email</td>
                <td><form:input path="email" autocomplete="false"/></td>
            </tr>
            <tr>
                <td>Gender</td>
                <td><form:radiobuttons path="gender" items="${genders}" delimiter="&nbsp;&nbsp;"/></td>
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
                <td colspan="2">
                    <input type="submit" value="Submit" class="btn btn-primary">
                    <c:if test="${employee.id != null}">
                        <form:hidden path="id" />
                        <input type="hidden" name="_method" value="PUT" />
                    </c:if>
                </td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
