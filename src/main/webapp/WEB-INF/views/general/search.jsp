<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
</head>
<body>
<ul class="navbar-nav me-auto mb-0 mb-lg-0">
    <li class="nav-item">
        <form class="navbar-form navbar-left">
            <div class="grid-container">
                <div class="grid-child purple">
                    <%--                                            select--%>
                    <div style="width: 5%;" class="f-flex flex-row">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="searchByDate">
                            <label class="form-check-label text-white" for="inlineRadio1">searchByDate</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="searchByTheme">
                            <label class="form-check-label text-white" for="inlineRadio2">searchByTheme</label>
                        </div>
                    </div>
                </div>

                <div class="grid-child green">
                    <div class="d-flex flex-row bd-highlight mb-3">
                        <input type="hidden" name="action" value="searchExpo">
                        <input type="hidden" name="command" value="search">
                        <input type="hidden" name="page" value="1">
                        <input type="hidden" name="noOfPages" value="5">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="search" name="searchedItem">
                            <button class="btn btn-info btn-sm" type="submit"><i class="glyphicon glyphicon-search"></i>ok</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </li>
</ul>
</body>
</html>
