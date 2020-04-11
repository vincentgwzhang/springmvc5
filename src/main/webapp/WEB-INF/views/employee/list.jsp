<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>Spring MVC 5 Demo</title>
    <base href="<%=basePath%>" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <spring:url value="/resources/js/employee.js" var="jqueryJS" />
    <script src="${jqueryJS}"></script>
</head>
<body>
<div class="container">
<a href="<%=basePath%>/employee">Back to Employee CRUD index page</a><br /><br /><br />

<c:if test="${empty requestScope.employees}">
    No data
</c:if>
<c:if test="${!empty requestScope.employees}">
    <table class="table table-striped table-bordered table-hover">
        <thead class="thead-dark">
            <tr>
                <td>ID</td>
                <td>LastName</td>
                <td>Email</td>
                <td>Gender</td>
                <td>Department</td>
                <td>Edit</td>
                <td>Delete</td>
            </tr>
        </thead>
        <c:forEach items="${requestScope.employees}" var="employee">
            <tr>
                <td>${employee.id}</td>
                <td>${employee.lastName}</td>
                <td>${employee.email}</td>
                <td>${employee.gender == 0 ? 'Female' : 'Male'}</td>
                <td>${employee.department.departmentName}</td>
                <td><a href="employee/${employee.id}">Edit</a></td>
                <td><a class="deleteEmployee" href="employee/${employee.id}?_method=DELETE">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
    <form:form action="" method="post" cssClass="submitForm">
        <input type="hidden" name="_method" value="DELETE" />
    </form:form>
    <br /><br />
    <a href="employee/create">Add new employee</a>
</div>
</body>
</html>
