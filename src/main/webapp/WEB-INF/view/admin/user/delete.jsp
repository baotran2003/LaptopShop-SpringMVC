<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Create User Page</title>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container mt-5">
    <div class="row">
        <div class="col-12 mx-auto">
            <div class="d-flex justify-content-between">
                <h3>Delete the user with id = ${id}</h3>
            </div>

            <hr />
            <div class="alert alert-danger">
                Are you sure delete this user ?
            </div>
            <%--@elvariable id="newUser" type=""--%>
            <form:form method="post" action="/admin/user/delete" modelAttribute="newUser">
                <div class="mb-3" style="display: none">
                    <label class="form-label">Id</label>
                    <form:input value="${id}" type="text" class="form-control" path="id" />
                </div>
                <button class="btn btn-danger">Confirm</button>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>