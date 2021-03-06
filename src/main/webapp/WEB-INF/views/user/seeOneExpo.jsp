<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <jsp:include page="/WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>
    <jsp:include page="/WEB-INF/views/general/informMsg.jsp"/>
</head>
<body>
<c:set var="role" value="${sessionScope.userData.userRole.role}"/>
<c:set var="dateFormat" value="${sessionScope.dateFormat}"/>
<c:set var="timeFormat" value="${sessionScope.timeFormat}"/>

<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <div class="card text-white bg-success" style="width: 25rem;margin-left: 30%;">
                <img class="picture" src="https://ichef.bbci.co.uk/news/999/cpsprodpb/6D5A/production/_119449972_10.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${requestScope.oneExpoData.name}</h5>
                    <p class="card-text"> ${sessionScope.language['Expo_date']} ${requestScope.oneExpoData.date.format(dateFormat)}</p>
                    <p class="card-text"> ${sessionScope.language['Expo_time']} ${requestScope.oneExpoData.time.format(timeFormat)}</p>
                    <p class="card-text"> ${sessionScope.language['Price']} ${requestScope.oneExpoData.price}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">${sessionScope.language['Sold']} ${requestScope.oneExpoData.sold}</li>
                    <li class="list-group-item">${sessionScope.language['Theme']} ${requestScope.oneExpoData.theme.name}</li>
                    <li class="list-group-item">${sessionScope.language['Tickets']} ${requestScope.oneExpoData.tickets}</li>
                    <li class="list-group item">__Status:
                        <c:choose>
                            <c:when test="${requestScope.oneExpoData.statusId == 2}">
                                ${sessionScope.language['canceled']}
                            </c:when>
                            <c:when test="${requestScope.oneExpoData.statusId == 1}">
                                ${sessionScope.language['active']}
                            </c:when>
                        </c:choose>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>
</body>
</html>
