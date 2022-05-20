<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<c:if test="${not empty requestScope.infoMsg}">
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
         <h5 class="text-center">${requestScope.infoMsg}</h5>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
</body>
</html>
