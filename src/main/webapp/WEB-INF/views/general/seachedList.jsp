<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
</head>
<body>

<table class="table table-info table-hover">
    <h4 class="text-center">Searched List</h4>
    <thead>
    <tr>
        <th scope="col">${sessionScope.language['Expos']}</th>
        <th scope="col">${sessionScope.language['Expo_date']}</th>
        <th scope="col">${sessionScope.language['Expo_time']}</th>
        <th scope="col">${sessionScope.language['Price']}</th>
        <th scope="col">${sessionScope.language['Sold']}</th>
        <th scope="col">${sessionScope.language['Halls']}</th>
        <th scope="col">${sessionScope.language['Theme']}</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="dateFormat" value="${sessionScope.dateFormat}"/>
    <c:set var="timeFormat" value="${sessionScope.timeFormat}"/>
    <c:forEach var="expo" items="${sessionScope.searchedList}">
        <tr>
            <td>
            ${expo.name}
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
            <c:if test="${role.equals('user')}">
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="action" value="buyExpo">
                        <input type="hidden" name="expoId" value="${expo.idExpo}">
                        <button type="submit" class="btn btn-success btn-sm">${sessionScope.language['buy']}</button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
