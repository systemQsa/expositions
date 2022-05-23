<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<%--inform user in case some problem occur during the processing the request--%>
<c:if test="${not empty requestScope.infoMsg}">
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
         <h5 class="text-center">${requestScope.infoMsg}</h5>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<%--inform user in case request had proceed successfully or also exception happend--%>
<c:if test="${not empty sessionScope.infMsg}">
    <div class="alert alert-success alert-dismissible fade-show text-center">
        <h5 class="text-center">${sessionScope.infMsg}</h5>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
                ${sessionScope.remove("infMsg")}
        </button>
    </div>
</c:if>
</body>
</html>
