<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title> ${sessionScope.language['admin']}</title>
    <jsp:include page="/WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"> ${sessionScope.language['expositions']}</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <jsp:include page="/WEB-INF/views/general/switchLang.jsp"/>

                    <li class="nav-item dropdown">
                        <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            ${sessionScope.language['more_options']}
                        </a>

                        <%--                        More actions --%>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <li>
                                <a class="dropdown-item" href="?action=viewAllThemes&command=paginate&sortBy=id&page=1&noOfRecords=2">${sessionScope.language['themes']}</a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="?action=viewAllHalls&command=paginate&sortBy=id&page=1&noOfRecords=2">${sessionScope.language['Halls']}</a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="?action=viewAllExpos&command=paginate&sortBy=id&page=1&noOfRecords=2">${sessionScope.language['Expos']}</a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="?action=viewAllUsers&command=paginate&sortBy=id&page=1&noOfRecords=5">${sessionScope.language['Users']}</a>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="?action=viewAllExpos&command=paginate&sortBy=statistic&page=1&noOfRecords=2"> ${sessionScope.language['Statistics']}</a>
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
                            <button type="submit" class="logout btn btn-link link-light"> ${sessionScope.language['Logout']}</button>
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

    <c:if test="${not empty sessionScope.usersList}">
        <jsp:include page="/WEB-INF/views/admin/allUsers.jsp"/>
    </c:if>
    <jsp:include page="/WEB-INF/views/general/footer.jsp"/>
    </div>
</div>
</body>
</html>
