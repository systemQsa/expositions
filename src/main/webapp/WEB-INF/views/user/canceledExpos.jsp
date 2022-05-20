<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>

</head>
<body>
<c:set var="dateFormat" value="${sessionScope.dateFormat}"/>
<c:set var="timeFormat" value="${sessionScope.timeFormat}"/>
<h4>Please contact the manager to get your money back<!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Document</title>
    </head>
    <body>
    
    </body>
    </html></h4>
<table class="table table-info table-hover">
    <thead>
    <tr>
        <th scope="col">Name</th>
        <th scope="col">ExpoDate</th>
        <th scope="col">ExpoTime</th>
        <th scope="col">Price</th>
        <th scope="col">Hall</th>
        <th scope="col">Theme</th>
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
                <%--            <td>${expo.tickets}</td>--%>
        </tr>
    </c:forEach>
    </tbody>

</table>
</body>
</html>
