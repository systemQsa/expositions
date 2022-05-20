<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>

</head>
<body>
<li class="nav-item dropdown">
    <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
        ${sessionScope.language['Lang']}
    </a>
    <%--                        Switch Language--%>
    <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
        <li><a class="dropdown-item" href="?lang=en&country=US">Eng</a></li>
        <li><a class="dropdown-item" href="?lang=uk&country=UA">Ukr</a></li>
    </ul>
</li>
</body>
</html>
