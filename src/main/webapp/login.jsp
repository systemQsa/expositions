<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${sessionScope.language['Login']}</title>
    <jsp:include page="/WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>
</head>
<body>
<div class="container">
    <div class="col-md-6 col-lg-4 offset-lg-4 offset-md-3 mt-5">
        <jsp:include page="/WEB-INF/views/general/informMsg.jsp"/>
        <!-- Pills navs -->
        <ul class="nav nav-pills nav-justified mb-3" id="ex1" role="tablist">

            <li class="nav-item" role="presentation">
                <a class="nav-link active" id="tab-login" data-mdb-toggle="pill" href="${pageContext.request.contextPath}/login.jsp" role="tab"
                   aria-controls="pills-login" aria-selected="true">${sessionScope.language['Login']}</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="tab-register" data-mdb-toggle="pill" href="${pageContext.request.contextPath}/register.jsp" role="tab"
                   aria-controls="pills-register" aria-selected="false">${sessionScope.language['Register']}</a>
            </li>
        </ul>
        <!-- Pills navs -->

        <!-- Pills content -->
        <div class="tab-content">
            <div class="tab-pane fade show active" id="pills-login" role="tabpanel" aria-labelledby="tab-login">
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="action" value="login">

                    <!-- Email input -->
                    <div class="form-outline mb-4">
                        <input type="text" id="loginName" name="email" class="form-control" />
                        <label class="form-label" for="loginName">${sessionScope.language['your_email']}</label>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-4">
                        <input type="password" id="loginPassword" name="password" class="form-control" />
                        <label class="form-label" for="loginPassword">${sessionScope.language['Password']}</label>
                    </div>

                    <!-- 2 column grid layout -->
                    <div class="row mb-4">

                        <div class="col-md-6 d-flex justify-content-center">
                            <!-- Simple link -->
                            <a href="${pageContext.request.contextPath}/changeEmail.jsp">${sessionScope.language['change_email']}</a>
                        </div>
                        <div class="col-md-6 d-flex justify-content-center">
                            <!-- Simple link -->
                            <a href="${pageContext.request.contextPath}/changePass.jsp">${sessionScope.language['forgot_pass']}?</a>
                        </div>
                    </div>

                    <!-- Submit button -->
                    <button type="submit" class="btn btn-primary btn-block mb-4">${sessionScope.language['sign_in']}</button>

                    <!-- Register buttons -->
                    <div class="text-center">
                        <p>${sessionScope.language['not_a_member']}? <a href="${pageContext.request.contextPath}/register.jsp">${sessionScope.language['Register']}</a></p>
                    </div>
                </form>
            </div>
            <div class="tab-pane fade" id="pills-register" role="tabpanel" aria-labelledby="tab-register">
                <form>

                    <!-- Name input -->
                    <div class="form-outline mb-4">
                        <input type="text" id="registerName" class="form-control" />
                        <label class="form-label" for="registerName">${sessionScope.language['your_name']}</label>
                    </div>

                    <!-- Username input -->
                    <div class="form-outline mb-4">
                        <input type="text" id="registerUsername" class="form-control" />
                        <label class="form-label" for="registerUsername">${sessionScope.language['your_surname']}</label>
                    </div>

                    <!-- Email input -->
                    <div class="form-outline mb-4">
                        <input type="email" id="registerEmail" class="form-control" />
                        <label class="form-label" for="registerEmail">${sessionScope.language['your_email']}</label>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-4">
                        <input type="password" id="registerPassword" class="form-control" />
                        <label class="form-label" for="registerPassword">${sessionScope.language['Password']}</label>
                    </div>

                    <!-- Submit button -->
                    <button type="submit" class="btn btn-primary btn-block mb-3">${sessionScope.language['sign_in']}</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
