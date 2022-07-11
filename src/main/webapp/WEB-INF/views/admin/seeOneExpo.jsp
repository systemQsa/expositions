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
                        <li class="list-group item">${sessionScope.language['status']}:
                            <c:choose>
                                <c:when test="${requestScope.oneExpoData.statusId == 2}">
                                    ${sessionScope.language['canceled']}
                                </c:when>
                                <c:when test="${requestScope.oneExpoData.statusId == 1}">
                                    ${sessionScope.language['active']}
                                </c:when>
                            </c:choose>
                        </li>
                        <li>
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="action" value="changeExpoStatus">
                                <input type="hidden" name="expoId" value="${requestScope.oneExpoData.idExpo}">
                                <input type="hidden" name="status" value="canceled">
                                <button class="btn btn-warning btn-sm" type="submit"> ${sessionScope.language['cancel']}</button>
                            </form>
                        </li>
                        <li>
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="action" value="changeExpoStatus">
                                <input type="hidden" name="expoId" value="${requestScope.oneExpoData.idExpo}">
                                <input type="hidden" name="status" value="active">
                                <button class="btn btn-info btn-sm" type="submit">${sessionScope.language['activate']}</button>
                            </form>
                        </li>
                    </ul>
                    <div class="card-body">

                        <div class="accordion accordion-flush" role="link" id="updateTheExpo">
                            <button class="btn btn-success btn-sm" role="link" data-bs-toggle="collapse"
                                    data-bs-target="#updateExpo" aria-expanded="false" aria-controls="flush-collapseThree">
                                ${sessionScope.language['update_expo']}
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
                                                <label class="form-label" for="expoName">${sessionScope.language['name']}</label>
                                            </div>

                                            <!-- Expo Date -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoDate" class="form-control" name="expoDate" value="${requestScope.oneExpoData.date.format(dateFormat)}"/>
                                                <label class="form-label" for="expoDate">${sessionScope.language['Expo_date']}</label>
                                            </div>

                                            <!-- Expo Time -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoTime" class="form-control" name="expoTime" value="${requestScope.oneExpoData.time.format(timeFormat)}"/>
                                                <label class="form-label" for="expoTime">${sessionScope.language['Expo_time']}</label>
                                            </div>

                                            <!-- Expo Price -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoPrice" class="form-control" name="expoPrice" value="${requestScope.oneExpoData.price}"/>
                                                <label class="form-label" for="expoPrice">${sessionScope.language['Price']}</label>
                                            </div>

                                            <!-- Expo Sold -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoSold" class="form-control" name="expoSold" value="${requestScope.oneExpoData.sold}"/>
                                                <label class="form-label" for="expoSold">${sessionScope.language['Sold']}</label>
                                            </div>

                                            <!-- Expo Hall Name -->
                                            <div class="form-outline mb-4">

                                                <div class="dropdown col-4">
                                                    <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton1"
                                                            data-bs-toggle="dropdown" aria-expanded="false">
                                                        ${sessionScope.language['choose_hall']}
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
                                                <label class="form-label" for="expoHallName">${sessionScope.language['hall_name']}</label>
                                            </div>

                                            <!-- Expo Theme name -->
                                            <div class="form-outline mb-4">
                                                <select class="form-select" aria-label="Default select example" name="idTheme">
                                                    <c:forEach var="theme" items="${sessionScope.themeList}">
                                                        <option value="${theme.idTheme}">${theme.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <label class="form-label">${sessionScope.language['theme_name']}</label>
                                            </div>

                                            <!-- Expo Tickets -->
                                            <div class="form-outline mb-4">
                                                <input type="text" id="expoTickets" class="form-control"  name="expoTickets" value="${requestScope.oneExpoData.tickets}"/>
                                                <label class="form-label" for="expoTickets">${sessionScope.language['Tickets']}</label>
                                            </div>
                                            <div>
                                                <c:if test="${role.equals('admin')}">
                                                    <button class="btn btn-info btn-sm" type="submit">${sessionScope.language['update']}</button>
                                                </c:if>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


        </div>
    </nav>
</div>

</body>
</html>
