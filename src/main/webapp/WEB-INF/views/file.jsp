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

    <spring:url value="/resources/js/file.js" var="fileJS" />
    <spring:url value="/resources/dropzone/basic.min.css" var="basicCSS" />
    <spring:url value="/resources/dropzone/dropzone.min.css" var="dropzoneCSS" />
    <spring:url value="/resources/dropzone/dropzone.min.js" var="dropzoneJS" />
    <link rel="stylesheet" href="${basicCSS}">
    <link rel="stylesheet" href="${dropzoneCSS}">
    <script src="${dropzoneJS}"></script>
    <script src="${fileJS}"></script>
    <base href="<%=basePath%>"/>
</head>
<body>
<div class="container">
    <a href="<%=basePath%>">Back to home</a><br /><br /><br />

    <h2>Example 1, traditional</h2>
    <form action="file/fileUpload" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label>Select File</label>
            <input class="form-control" type="file" name="file">
        </div>
        <div class="form-group">
            <button class="btn btn-primary" type="submit">Upload</button>
        </div>
    </form>
    <br />

    <!-- Bootstrap Progress bar -->
    <div class="progress">
        <div id="progressBar" class="progress-bar progress-bar-success" role="progressbar"
             aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>
    </div>
    <br />
    <!-- Alert -->
    <div id="alertMsg" style="color: red;font-size: 18px;"></div>

    <br />
    <h2>Example 2, use dropzone js</h2>
    <form action="file/fileUpload" class="dropzone">
        <div class="fallback">
            <input name="file" type="file" multiple />
        </div>
    </form>

    <h2>Download file</h2>
    <a href="file/fileDownload">File Download link</a>
</div>
</body>
</html>
