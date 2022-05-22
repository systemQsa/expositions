<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>${sessionScope.language['expositions']}</title>
    <jsp:include page="/WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">${sessionScope.language['expositions']}</a>
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
                        <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            ${sessionScope.language['more_options']}
                        </a>

                        <%--                        More actions --%>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <li>
                                <a class="dropdown-item"
                                   href="?action=viewAllExpos&command=paginate&sortBy=id&page=1&noOfRecords=2">${sessionScope.language['Expos']}</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>

            <!-- Collapsible wrapper -->
            <div class="collapse navbar-collapse" id="navbarButtonsExample">
                <!-- Search -->
                   <jsp:include page="/WEB-INF/views/general/search.jsp"/>
                <!-- Login / Register -->
                <div class="d-flex align-items-center">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="px-3 me-2">
                            <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">${sessionScope.language['Login']}</a>
                      </li>
                      <li class="me-3">
                          <a class="nav-link btn btn-primary btn-sm" role="button" href="${pageContext.request.contextPath}/register.jsp">${sessionScope.language['Register']}</a>
                       </li>
                   </ul>
                </div>
            </div>

        </div>
    </nav>
    <jsp:include page="/WEB-INF/views/general/informMsg.jsp"/>

    <c:if test="${not empty sessionScope.exposList}">
        <jsp:include page="/WEB-INF/views/admin/viewAllExpos.jsp"/>
    </c:if>

    <c:if test="${not empty sessionScope.searchedList}">
        <jsp:include page="/WEB-INF/views/general/seachedList.jsp"/>
    </c:if>

    <img src="imgs/logo.jpg" alt="anime boy">

<%--    Footer--%>
<jsp:include page="/WEB-INF/views/general/footer.jsp"/>
</div>
</body>
</html>