<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
</head>
<body>
<div class="dropdown col-4">
    <button class="btn btn-primary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton1"
            data-bs-toggle="dropdown" aria-expanded="false">
        ${sessionScope.language['sort']}
    </button>
    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
        <li><a class="dropdown-item" href="?action=viewAllExpos&command=paginate&sortBy=byPrice&page=1">${sessionScope.language['by_price']}</a>
        </li>
        <li><a class="dropdown-item" href="?action=viewAllExpos&command=paginate&sortBy=byDate&page=1">${sessionScope.language['by_date']}</a>
        </li>
        <li><a class="dropdown-item" href="?action=viewAllExpos&command=paginate&sortBy=byTheme&page=1">${sessionScope.language['by_theme']}</a>
        </li>
    </ul>
</div>
</body>
</html>
