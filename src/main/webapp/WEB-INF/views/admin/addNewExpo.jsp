<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Add new Expo</title>
    <jsp:include page="/WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>
    <jsp:include page="/WEB-INF/views/general/informMsg.jsp"/>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="action" value="addExpo">
                <!-- Expo name -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoName" class="form-control" name="expoName"/>
                    <label class="form-label" for="expoName">Expo name</label>
                </div>

                <!-- Expo Date -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoDate" class="form-control" name="expoDate"/>
                    <label class="form-label" for="expoDate">Expo Date</label>
                </div>

                <!-- Expo Time -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoTime" class="form-control" name="expoTime"/>
                    <label class="form-label" for="expoTime">Expo time</label>
                </div>

                <!-- Expo Price -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoPrice" class="form-control" name="expoPrice"/>
                    <label class="form-label" for="expoPrice">Expo price</label>
                </div>

                <!-- Expo Sold -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoSold" class="form-control" name="expoSold"/>
                    <label class="form-label" for="expoSold">ExpoSold</label>
                </div>



<%--               All  Halls--%>
                <div class="dropdown col-4">
                    <button class="btn btn-primary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton1"
                            data-bs-toggle="dropdown" aria-expanded="false">
                        choose hall
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
                    <label class="form-label">Choose hall(s)</label>
                </div>

                <!-- Expo Theme name -->
                <div class="form-outline mb-4">
                    <select class="form-select" aria-label="Default select example" name="idTheme">
                        <c:forEach var="theme" items="${sessionScope.themeList}">
                            <option value="${theme.idTheme}">${theme.name}</option>
                        </c:forEach>
                    </select>
                    <label class="form-label">Choose theme</label>
                </div>

                <!-- Expo Tickets -->
                <div class="form-outline mb-4">
                    <input type="text" id="expoTickets" class="form-control" name="expoTickets"/>
                    <label class="form-label" for="expoTickets">Amount of tickets</label>
                </div>

                <button type="submit" class="btn btn-primary btn-sm">add</button>
            </form>
        </div>
    </nav>
</div>

</body>
</html>