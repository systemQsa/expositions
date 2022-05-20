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
<%--        ${requestScope.oneExpoData.hall.name}--%>
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
                        <li class="list-group-item">Hall
                         <c:forEach var="hall" items="${requestScope.oneExpoData.hallList}">
                             ${hall.name},
                         </c:forEach>
                        </li>
                        <li class="list-group-item">Theme ${requestScope.oneExpoData.theme.name}</li>
                        <li class="list-group-item">Tickets ${requestScope.oneExpoData.tickets}</li>
                        <li class="list-group item">Status:
                            <c:choose>
                                <c:when test="${requestScope.oneExpoData.statusId == 2}">
                                    canceled
                                </c:when>
                                <c:when test="${requestScope.oneExpoData.statusId == 1}">
                                    active
                                </c:when>
                            </c:choose>
                        </li>
                        <li>
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="action" value="cancelExpo">
                                <input type="hidden" name="expoId" value="${requestScope.oneExpoData.idExpo}">
                                <input type="hidden" name="statusId" value="2">
                                <button class="btn btn-warning btn-sm" type="submit">Cancel</button>
                            </form>
                        </li>
                    </ul>
                    <div class="card-body">



                        <div class="accordion accordion-flush" role="link" id="updateTheExpo">
                            <button class="btn btn-success btn-sm" role="link" data-bs-toggle="collapse"
                                    data-bs-target="#updateExpo" aria-expanded="false" aria-controls="flush-collapseThree">
                                Update Expo
                            </button>
                            <div id="updateExpo" class="accordion-collapse collapse" aria-labelledby="flush-headingThree"
                                 data-bs-parent="#updateValues">
                                <div class="accordion-body">
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="action" value="updateExpo">
                                        <input type="hidden" name="expoId" value="${requestScope.oneExpoData.idExpo}">
                                        <input type="hidden" name="sortBy" value="id">
                                        <input type="hidden" name="page" value="1">
                                        <input type="hidden"name="noOfRecords" value="2">
                                        <div class="input-group mb-3">
                                            <!-- Expo name -->
                                            <div class="form-outline mb-4">

                                                <input type="text" id="expoName" class="form-control" name="expoName" value="${requestScope.oneExpoData.name}" />
                                                <label class="form-label" for="expoName">Name</label>
                                            </div>

                                            <!-- Expo Date -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoDate" class="form-control" name="expoDate" value="${requestScope.oneExpoData.date.format(dateFormat)}"/>
                                                <label class="form-label" for="expoDate">Date</label>
                                            </div>

                                            <!-- Expo Time -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoTime" class="form-control" name="expoTime" value="${requestScope.oneExpoData.time.format(timeFormat)}"/>
                                                <label class="form-label" for="expoTime">Time</label>
                                            </div>

                                            <!-- Expo Price -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoPrice" class="form-control" name="expoPrice" value="${requestScope.oneExpoData.price}"/>
                                                <label class="form-label" for="expoPrice">Price</label>
                                            </div>

                                            <!-- Expo Sold -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoSold" class="form-control" name="expoSold" value="${requestScope.oneExpoData.sold}"/>
                                                <label class="form-label" for="expoSold">Sold</label>
                                            </div>

                                            <!-- Expo Hall Name -->
                                            <div class="form-outline mb-4">

                                                <div class="dropdown col-4">
                                                    <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton1"
                                                            data-bs-toggle="dropdown" aria-expanded="false">
                                                        choose hall
                                                    </button>
                                                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                                        <c:forEach var="hall" items="${sessionScope.hallList}">
                                                            <li>
                                                                <input type="checkbox" name="expoHallId" value="${hall.idHall}"> ${hall.name}
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                                <c:forEach var="hall" items="${requestScope.oneExpoData.hallList}">
                                                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                                            <input type="hidden" name="expoHallId" value="${hall.idHall}">
                                                            <input type="text" id="expoHallName" class="form-control" name="expoHallName" value="${hall.name}"/>
                                                         <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                                    </div>
                                                </c:forEach>
                                                <label class="form-label" for="expoHallName">Hall name</label>
                                            </div>

                                            <!-- Expo Theme name -->
<%--                                            <div class="form-outline mb-4">--%>
<%--                                                <input type="hidden" name="idTheme" value="${requestScope.oneExpoData.theme.idTheme}">--%>
<%--                                                <input type="text" id="expoThemeName" class="form-control" name="expoThemeName" value="${requestScope.oneExpoData.theme.name}"/>--%>
<%--                                                <label class="form-label" for="expoThemeName">Theme name</label>--%>
<%--                                            </div>--%>

                                            <!-- Expo Theme name -->
                                            <div class="form-outline mb-4">
                                                <select class="form-select" aria-label="Default select example" name="idTheme">
                                                    <c:forEach var="theme" items="${sessionScope.themeList}">
                                                        <option value="${theme.idTheme}">${theme.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <label class="form-label">Theme name</label>
                                            </div>

                                            <!-- Expo Tickets -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoTickets" class="form-control"  name="expoTickets" value="${requestScope.oneExpoData.tickets}"/>
                                                <label class="form-label" for="expoTickets">Tickets</label>
                                            </div>
                                            <c:if test="${role.equals('admin')}">
                                                <button class="btn btn-info btn-sm" type="submit">Update</button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
<%--                        <c:if test="${role.equals('admin')}">--%>
<%--                            <a href="#" class="card-link">Cancel</a>--%>
<%--                            <a href="#" class="card-link">Delete</a>--%>
<%--                        </c:if>--%>
                    </div>
                </div>


        </div>
    </nav>
</div>

</body>
</html>
