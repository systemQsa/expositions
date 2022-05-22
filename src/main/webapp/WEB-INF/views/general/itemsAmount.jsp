<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
</head>
<body>
<div class="dropdown col-4">
    <button class="btn btn-primary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton2"
            data-bs-toggle="dropdown" aria-expanded="false">
        ${sessionScope.language['amount']}
    </button>
    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton2">
        <li><a class="dropdown-item"
               href="?action=viewAllExpos&command=paginate&sortBy=${sessionScope.sortBy}&page=1&noOfRecords=2">2</a>
        </li>
        <li><a class="dropdown-item"
               href="?action=viewAllExpos&command=paginate&sortBy=${sessionScope.sortBy}&page=1&noOfRecords=20">20</a>
        </li>
        <li><a class="dropdown-item"
               href="?action=viewAllExpos&command=paginate&sortBy=${sessionScope.sortBy}&page=1&noOfRecords=50">50</a>
        </li>
    </ul>
</div>
</body>
</html>
