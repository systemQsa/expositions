<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<%--        <jsp:include page="/WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>--%>
</head>
<body>

<table class="table table-info table-hover">
    <h4 class="text-center">All Themes</h4>
    <thead>
    <tr>
        <th scope="col">${sessionScope.language['Theme']}</th>
        <th scope="col">
            <div class="accordion accordion-flush" id="addNewTheme">
                <button class="btn btn-success btn-sm" type="button" data-bs-toggle="collapse"
                        data-bs-target="#addTheme" aria-expanded="false" aria-controls="flush-collapseThree">
                    ${sessionScope.language['add_new']}
                </button>

                <div id="addTheme" class="accordion-collapse collapse" aria-labelledby="flush-headingThree"
                     data-bs-parent="#addNewTheme">
                    <div class="accordion-body">
                        <form method="post" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="action" value="addNewTheme">
                            <input type="hidden" name="page" value="1">
                            <input type="hidden" name="noOfPages" value="5">
                            <input type="hidden" name="sortBy" value="id">
                            <div class="input-group mb-3">
                                <input type="text" name="themeName" class="form-control" placeholder="${sessionScope.language['Theme']}"
                                       aria-label="new theme">
                                <button class="btn btn-info btn-sm" type="submit">${sessionScope.language['add']}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="theme" items="${sessionScope.themeList}">
        <tr>
            <td>${theme.name}</td>
            <td>
                <div class="accordion accordion-flush" id="accordionFlushExample">
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <div>
                            <button class="btn btn-primary btn-sm" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#flushTheme${theme.idTheme}" aria-expanded="false">
                                    ${sessionScope.language['update']}
                            </button>
                            <div id="flushTheme${theme.idTheme}" class="accordion-collapse collapse"
                                 aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
                                <div class="accordion-body">
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="action" value="updateTheme">
                                        <input type="hidden" name="idTheme" value="${theme.idTheme}">
                                        <input type="hidden" name="sortBy" value="id">
                                        <div class="input-group mb-3">
                                            <input type="text" name="themeNewName" class="form-control"
                                                   placeholder="${sessionScope.language['name']}" aria-label="new name">
                                            <button class="btn btn-info btn-sm" type="submit">${sessionScope.language['update']}</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div>
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="action" value="deleteTheme">
                                <input type="hidden" name="idTheme" value="${theme.idTheme}">
                                <input type="hidden" name="sortBy" value="id">
                                <button type="button" class="btn btn-danger btn-sm">${sessionScope.language['delete']}</button>
                            </form>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <li class="page-item">
            <c:if test="${sessionScope.currentPage !=1}">
                <a class="page-link"
                   href="?action=viewAllThemes&command=paginate&sortBy=id&page=${sessionScope.currentPage - 1}&noOfRecords=2">${sessionScope.language['prev']}</a>
            </c:if>
        </li>
        <li class="page-item">
            <a class="page-link"
               href="#">${sessionScope.language['page']} ${sessionScope.currentPage}</a>
        </li>
        <li class="page-item">
            <a class="page-link"
               href="?action=viewAllThemes&command=paginate&sortBy=id&page=${sessionScope.currentPage + 1}&noOfRecords=2">${sessionScope.language['next']}</a>
        </li>
    </ul>
</nav>
</body>
</html>
