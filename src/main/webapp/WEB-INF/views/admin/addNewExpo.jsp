<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${sessionScope.language['add_expo']}</title>
    <jsp:include page="/WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>
    <jsp:include page="/WEB-INF/views/general/informMsg.jsp"/>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid" style="margin-left: 35%">
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="action" value="addExpo">
                <!-- Expo name -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoName" class="form-control" name="expoName" placeholder="new expo name"/>
                    <label class="form-label" for="expoName">${sessionScope.language['expo_name']}</label>
                </div>

                <!-- Expo Date -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoDate" class="form-control" name="expoDate" placeholder="m/dd/yy"/>
                    <label class="form-label" for="expoDate">${sessionScope.language['expo_date']}</label>
                </div>

                <!-- Expo Time -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoTime" class="form-control" name="expoTime" placeholder="9:00 AM"/>
                    <label class="form-label" for="expoTime">${sessionScope.language['expo_time']}</label>
                </div>

                <!-- Expo Price -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoPrice" class="form-control" name="expoPrice" placeholder="100.00"/>
                    <label class="form-label" for="expoPrice">${sessionScope.language['expo_price']}</label>
                </div>

                <!-- Expo Sold -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoSold" class="form-control" name="expoSold" placeholder="0"/>
                    <label class="form-label" for="expoSold">${sessionScope.language['expo_sold']}</label>
                </div>



<%--               All  Halls--%>
                <div class="dropdown col-4">
                    <button class="btn btn-primary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton1"
                            data-bs-toggle="dropdown" aria-expanded="false">
                        ${sessionScope.language['choose_hall']}
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                        <c:forEach var="hall" items="${sessionScope.hallList}">
                            <li>
                                <input type="checkbox" name="halls" value="${hall.idHall}"> ${hall.name}
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="dropdown col-4">
                    <label class="form-label">${sessionScope.language['choose_hall']}</label>
                </div>

                <!-- Expo Theme name -->
                <div class="form-outline mb-4">
                    <select class="form-select" aria-label="Default select example" name="idTheme">
                        <c:forEach var="theme" items="${sessionScope.themeList}">
                            <option value="${theme.idTheme}">${theme.name}</option>
                        </c:forEach>
                    </select>
                    <label class="form-label">${sessionScope.language['choose_theme']}</label>
                </div>

                <!-- Expo Tickets -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoTickets" class="form-control" name="expoTickets" placeholder="100"/>
                    <label class="form-label" for="expoTickets">${sessionScope.language['amount_tickets']}</label>
                </div>

                <button type="submit" class="btn btn-primary btn-sm">${sessionScope.language['add']}</button>
            </form>
        </div>
    </nav>
</div>

</body>
</html>
