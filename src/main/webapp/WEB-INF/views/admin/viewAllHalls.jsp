<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
</head>
<body>
<table class="table table-info table-hover">
    <thead>
        <tr>
            <th scope="col">Halls</th>
            <th scope="col">
                <div class="accordion accordion-flush" id="addNewHall">
                    <button class="btn btn-success btn-sm" type="button" data-bs-toggle="collapse"
                            data-bs-target="#addTheme" aria-expanded="false" aria-controls="flush-collapseThree">
                        Add new
                    </button>

                    <div id="addTheme" class="accordion-collapse collapse" aria-labelledby="flush-headingThree"
                         data-bs-parent="#addNewHall">
                        <div class="accordion-body">
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="action" value="addNewHall">
                                <input type="hidden" name="sortBy" value="id">
                                <div class="input-group mb-3">
                                    <input type="text" name="hallName" class="form-control" placeholder="new hall"
                                           aria-label="new hall">
                                    <button class="btn btn-info btn-sm" type="submit">Add</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </th>
        </tr>
    </thead>

    <tbody>
    <c:forEach var="hall" items="${sessionScope.hallList}">
        <tr>
            <td>${hall.name}</td>
            <td>
                <div class="accordion accordion-flush" id="accordionFlushExample">
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <div>
                            <button class="btn btn-primary btn-sm" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#flushTheme${hall.idHall}" aria-expanded="false">
                                Update
                            </button>
                            <div id="flushTheme${hall.idHall}" class="accordion-collapse collapse"
                                 aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
                                <div class="accordion-body">
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="action" value="updateHall">
                                        <input type="hidden" name="idHall" value="${hall.idHall}">
                                        <input type="hidden" name="sortBy" value="id">
                                        <div class="input-group mb-3">
                                            <input type="text" name="hallNewName" class="form-control"
                                                   placeholder="new name" aria-label="new name">
                                            <button class="btn btn-info btn-sm" type="submit">update</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div>
                            <form method="post" action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="action" value="deleteHall">
                                <input type="hidden" name="idHall" value="${hall.idHall}">
                                <input type="hidden" name="sortBy" value="id">
                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
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
                   href="?action=viewAllHalls&command=paginate&sortBy=id&page=${sessionScope.currentPage - 1}&noOfRecords=2">prev</a>
            </c:if>
        </li>
        <li class="page-item">
            <a class="page-link"
               href="#">page ${sessionScope.currentPage}</a>
        </li>
        <li class="page-item">
            <a class="page-link"
               href="?action=viewAllHalls&command=paginate&sortBy=id&page=${sessionScope.currentPage + 1}&noOfRecords=2">next</a>
        </li>
    </ul>
</nav>
</body>
</html>
