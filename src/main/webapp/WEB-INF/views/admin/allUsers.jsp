<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<table class="table table-info table-hover">
    <h4 class="text-center">${sessionScope.language['all_users']}</h4>
    <thead>
        <tr>
            <th scope="col">${sessionScope.language['name']}</th>
            <th scope="col">${sessionScope.language['surname']}</th>
            <th scope="col">${sessionScope.language['email']}</th>
            <th scope="col">${sessionScope.language['phone']}</th>
            <th scope="col">${sessionScope.language['role']}</th>
            <th scope="col">${sessionScope.language['status']}</th>
            <th scope="col">${sessionScope.language['block']}</th>
            <th scope="col">${sessionScope.language['unblock']}</th>
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
                    <button type="submit" class="btn btn-danger btn-sm">${sessionScope.language['block']}</button>
                </form>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="action" value="blockUnblockUser">
                    <input type="hidden" name="status" value="active">
                    <input type="hidden" name="userId" value="${user.idUser}">
                    <button type="submit" class="btn btn-warning btn-sm">${sessionScope.language['unblock']}</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
