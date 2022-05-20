<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
</head>
<body>

<table class="table table-info table-hover">
    <thead>
    <tr>
        <th scope="col">Name</th>
        <th scope="col">ExpoDate</th>
        <th scope="col">ExpoTime</th>
        <th scope="col">Price</th>
        <th scope="col">Sold</th>
        <th scope="col">Hall</th>
        <th scope="col">Theme</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="dateFormat" value="${sessionScope.dateFormat}"/>
    <c:set var="timeFormat" value="${sessionScope.timeFormat}"/>
    <c:forEach var="expo" items="${sessionScope.searchedList}">
        <tr>
            <td>
                <a href="?action=viewSelectedExpo&command=lookAtMe&expoId=${expo.idExpo}">
                        ${expo.name}
                </a>
            </td>
            <td>${expo.date.format(dateFormat)}</td>
            <td>${expo.time.format(timeFormat)}</td>
            <td>${expo.price}</td>
            <td>${expo.sold}</td>
            <td>
                <c:forEach var="hall" items="${expo.hallList}">
                    ${hall.name},
                </c:forEach>
            </td>
            <td>${expo.theme.name}</td>
        </tr>
    </c:forEach>
    </tbody>

</table>


</body>
</html>