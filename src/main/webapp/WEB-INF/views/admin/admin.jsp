<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Admin</title>
    <jsp:include page="/WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Expos</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Home</a>
                    </li>

                    <jsp:include page="/WEB-INF/views/general/switchLang.jsp"/>

                    <li class="nav-item dropdown">
                        <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            More actions
                        </a>

                        <%--                        More actions --%>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <li>
                                <a class="dropdown-item" href="?action=viewAllThemes&command=paginate&sortBy=id&page=1&noOfRecords=2">Themes</a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="?action=viewAllHalls&command=paginate&sortBy=id&page=1&noOfRecords=2">Halls</a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="?action=viewAllExpos&command=paginate&sortBy=id&page=1&noOfRecords=2">Expos</a>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="?action=viewAllExpos&command=paginate&sortBy=statistic&page=1&noOfRecords=2">Statistics</a>
                    </li>
                </ul>
            </div>

            <!-- Search -->
            <jsp:include page="/WEB-INF/views/general/search.jsp"/>

    <%--            Logout--%>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="action" value="logout">
                            <button type="submit" class="logout btn btn-link link-light">Logout</button>
                        </form>
                    </li>
                </ul>
        </div>
    </nav>
    <jsp:include page="/WEB-INF/views/general/informMsg.jsp"/>


    <c:if test="${not empty sessionScope.themeList}">
        <jsp:include page="/WEB-INF/views/admin/viewAllThemes.jsp"/>
    </c:if>

    <c:if test="${not empty sessionScope.hallList}">
        <jsp:include page="/WEB-INF/views/admin/viewAllHalls.jsp"/>
    </c:if>

    <c:if test="${not empty sessionScope.exposList}">
        <jsp:include page="/WEB-INF/views/admin/viewAllExpos.jsp"/>
    </c:if>

    <c:if test="${not empty sessionScope.searchedList}">
        <jsp:include page="/WEB-INF/views/general/seachedList.jsp"/>
    </c:if>
    </div>

<%--   All data about user--%>

<%--    <c:if test="${not empty sessionScope.userData}">--%>
<%--        <p>${sessionScope.userData.name}</p>--%>
<%--        <p>${sessionScope.userData.surname}</p>--%>
<%--        <p>${sessionScope.userData.email}</p>--%>
<%--        <p>${sessionScope.userData.balance}</p>--%>
<%--        <p>${sessionScope.userData.phone}</p>--%>
<%--        <p>${sessionScope.userData.idUser}</p>--%>
<%--        <p>${sessionScope.userData.userRole.role}</p>--%>
<%--    </c:if>--%>

</div>
</body>
</html>
