<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${sessionScope.language['change_email']}</title>
    <jsp:include page="/WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>
    <jsp:include page="/WEB-INF/views/general/informMsg.jsp"/>
</head>
<body>
<hr>
<div class="container">
    <div class="row">
        <div class="row" style="margin-left: 30%;">
            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="text-center">
                            <h3><i class="fa fa-lock fa-4x"></i></h3>
                            <h2 class="text-center">${sessionScope.language['change_email']}</h2>
                            <p>${sessionScope.language['you_can_change_your_email_here']}.</p>
                            <div class="panel-body">

                                <form class="form" method="post" action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden" name="action" value="changeEmail">
                                    <fieldset>
                                        <div class="form-group">
                                            <div class="input-group">
                                                <span class="input-group-addon"><i
                                                        class="glyphicon glyphicon-envelope color-blue"></i></span>
                                                <input id="emailInput" placeholder="${sessionScope.language['old_email']}" class="form-control"
                                                      name="oldEmail" type="text">
                                            </div>

                                            <div class="input-group">
                                                <span class="input-group-addon"><i
                                                        class="glyphicon glyphicon-envelope color-blue"></i></span>
                                                <input id="emailInput2" placeholder="${sessionScope.language['new_email']}" class="form-control"
                                                       name="newEmail" type="text">
                                            </div>
                                         <br/>
                                        </div>
                                        <div class="form-group">
                                            <input class="btn btn-primary btn-block" value="${sessionScope.language['change']}" type="submit">
                                        </div>
                                    </fieldset>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
