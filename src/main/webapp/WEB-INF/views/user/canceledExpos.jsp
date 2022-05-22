<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
</head>
<body>
<c:set var="dateFormat" value="${sessionScope.dateFormat}"/>
<c:set var="timeFormat" value="${sessionScope.timeFormat}"/>
<h4>${sessionScope.language['contact_manger']}!
<table class="table table-info table-hover">
    <thead>
    <tr>
        <th scope="col">${sessionScope.language['Expos']}</th>
        <th scope="col">${sessionScope.language['Expo_date']}</th>
        <th scope="col">${sessionScope.language['Expo_time']}</th>
        <th scope="col">${sessionScope.language['Price']}</th>
        <th scope="col">${sessionScope.language['Halls']}</th>
        <th scope="col">${sessionScope.language['Theme']}</th>
        <th scope="col">${sessionScope.language['Status']}</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="dateFormat" value="${sessionScope.dateFormat}"/>
    <c:set var="timeFormat" value="${sessionScope.timeFormat}"/>
    <c:forEach var="expo" items="${sessionScope.canceledExpos}">
        <tr>
            <th>${expo.name}</th>
            <td>${expo.date.format(dateFormat)}</td>
            <td>${expo.time.format(timeFormat)}</td>
            <td>${expo.price}</td>
<%--            <td>${expo.hall.name}</td>--%>
            <td>
                <c:forEach var="hall" items="${expo.hallList}">
                    ${hall.name}
                </c:forEach>
            </td>
            <td>${expo.theme.name}</td>
            <td>
                <c:choose>
                    <c:when test="${expo.statusId == 1}">
                        active
                    </c:when>
                    <c:when test="${expo.statusId == 2}">
                        canceled
                    </c:when>
                </c:choose>

            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>
</body>
</html>
