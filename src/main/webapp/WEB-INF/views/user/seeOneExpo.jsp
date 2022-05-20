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
<c:choose>
    <c:when test="${not empty requestScope.oneExpoData}">
        ${requestScope.oneExpoData.name}
        ${requestScope.oneExpoData.date.format(dateFormat)}
        ${requestScope.oneExpoData.time.format(timeFormat)}
        ${requestScope.oneExpoData.price}
        ${requestScope.oneExpoData.sold}
        ${requestScope.oneExpoData.hall.name}
        ${requestScope.oneExpoData.theme.name}
        ${requestScope.oneExpoData.tickets}
    </c:when>
    <c:otherwise>
        <p>No Information was found!</p>
    </c:otherwise>
</c:choose>


<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <div class="card text-white bg-success" style="width: 22rem;margin-left: 30%;">
                <img class="picture" src="https://ichef.bbci.co.uk/news/999/cpsprodpb/6D5A/production/_119449972_10.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${requestScope.oneExpoData.name}</h5>
                    <p class="card-text"> Date ${requestScope.oneExpoData.date.format(dateFormat)}</p>
                    <p class="card-text"> Time ${requestScope.oneExpoData.time.format(timeFormat)}</p>
                    <p class="card-text"> Price ${requestScope.oneExpoData.price}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">Sold ${requestScope.oneExpoData.sold}</li>
                    <li class="list-group-item">Hall ${requestScope.oneExpoData.hall.name}</li>
                    <li class="list-group-item">Theme ${requestScope.oneExpoData.theme.name}</li>
                    <li class="list-group-item">Tickets ${requestScope.oneExpoData.tickets}</li>
                </ul>
            </div>


        </div>
    </nav>
</div>

</body>
</html>
