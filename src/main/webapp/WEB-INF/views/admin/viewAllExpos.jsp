<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
</head>
<body>
<c:set var="role" value="${sessionScope.userData.userRole.role}"/>
<div class="row justify-content-center">
    <jsp:include page="/WEB-INF/views/general/sorting.jsp"/>
    <jsp:include page="/WEB-INF/views/general/itemsAmount.jsp"/>
    <c:if test="${role.equals('admin')}">
        <div class="col-4">
            <form method="get" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="action" value="prepareToAddExpo">
                <input type="hidden" name="noOfRecords" value="10">
                <button type="submit" class="btn btn-success btn-sm">add new</button>
            </form>
        </div>
    </c:if>
</div>
<table class="table table-info table-hover">
    <thead>
    <tr>
        <th scope="col">Expos</th>
        <th scope="col">ExpoDate</th>
        <th scope="col">ExpoTime</th>
        <th scope="col">Price</th>
        <th scope="col">Sold</th>
        <th scope="col">Hall</th>
        <th scope="col">Theme</th>
        <th scope="col">Tickets</th>
        <th scope="col">Status</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="dateFormat" value="${sessionScope.dateFormat}"/>
    <c:set var="timeFormat" value="${sessionScope.timeFormat}"/>
    <c:forEach var="expo" items="${sessionScope.exposList}">
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
                <%--            <td>${expo.tickets}</td>--%>
            <td>
                <c:choose>
                    <c:when test="${expo.tickets == 0}">
                        <p>sold out</p>
                    </c:when>
                    <c:otherwise>
                        ${expo.tickets}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${expo.statusId == 2}">
                        canceled
                    </c:when>
                    <c:when test="${expo.statusId == 1}">
                        active
                    </c:when>
                </c:choose>
            </td>

            <c:if test="${role.equals('user')}">
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="action" value="buyExpo">
                        <input type="hidden" name="expoId" value="${expo.idExpo}">
                        <button type="submit" class="btn btn-success btn-sm">Buy</button>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>

</table>

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <li class="page-item">
            <c:if test="${sessionScope.currentPage !=1}">
                <a class="page-link"
                   href="?action=viewAllExpos&command=paginate&sortBy=${sessionScope.sortBy}&page=${sessionScope.currentPage - 1}&noOfRecords=${sessionScope.noOfRecords}">prev</a>
            </c:if>
        </li>
        <li class="page-item">
            <a class="page-link"
               href="#">page ${sessionScope.currentPage}</a>
        </li>
        <li class="page-item">
            <a class="page-link"
               href="?action=viewAllExpos&command=paginate&sortBy=${sessionScope.sortBy}&page=${sessionScope.currentPage + 1}&noOfRecords=${sessionScope.noOfRecords}">next</a>
        </li>
    </ul>
</nav>
</body>
</html>
