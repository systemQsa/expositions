<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<table class="table table-info table-hover">
    <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Surname</th>
            <th scope="col">Email</th>
            <th scope="col">Phone</th>
            <th scope="col">Role</th>
            <th scope="col">Status</th>
            <th scope="col">Block</th>
            <th scope="col">Unblock</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${sessionScope.usersList}">
        <tr>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.email}</td>
            <td>${user.phone}</td>
            <td>${user.userRole.role}</td>
            <td>${user.status}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="action" value="blockUnblockUser">
                    <input type="hidden" name="status" value="blocked">
                    <input type="hidden" name="userId" value="${user.idUser}">
                    <button type="submit" class="btn btn-danger btn-sm">block</button>
                </form>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="action" value="blockUnblockUser">
                    <input type="hidden" name="status" value="active">
                    <input type="hidden" name="userId" value="${user.idUser}">
                    <button type="submit" class="btn btn-warning btn-sm">unblock</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
