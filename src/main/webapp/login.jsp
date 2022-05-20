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
                        <label class="form-label" for="loginName">Email or username</label>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-4">
                        <input type="password" id="loginPassword" name="password" class="form-control" />
                        <label class="form-label" for="loginPassword">Password</label>
                    </div>

                    <!-- 2 column grid layout -->
                    <div class="row mb-4">

                        <div class="col-md-6 d-flex justify-content-center">
                            <!-- Simple link -->
                            <a href="${pageContext.request.contextPath}/changeEmail.jsp">Change email</a>
                        </div>
                        <div class="col-md-6 d-flex justify-content-center">
                            <!-- Simple link -->
                            <a href="${pageContext.request.contextPath}/changePass.jsp">Forgot password?</a>
                        </div>
                    </div>

                    <!-- Submit button -->
                    <button type="submit" class="btn btn-primary btn-block mb-4">Sign in</button>

                    <!-- Register buttons -->
                    <div class="text-center">
                        <p>Not a member? <a href="${pageContext.request.contextPath}/register.jsp">Register</a></p>
                    </div>
                </form>
            </div>
            <div class="tab-pane fade" id="pills-register" role="tabpanel" aria-labelledby="tab-register">
                <form>

                    <!-- Name input -->
                    <div class="form-outline mb-4">
                        <input type="text" id="registerName" class="form-control" />
                        <label class="form-label" for="registerName">Name</label>
                    </div>

                    <!-- Username input -->
                    <div class="form-outline mb-4">
                        <input type="text" id="registerUsername" class="form-control" />
                        <label class="form-label" for="registerUsername">Username</label>
                    </div>

                    <!-- Email input -->
                    <div class="form-outline mb-4">
                        <input type="email" id="registerEmail" class="form-control" />
                        <label class="form-label" for="registerEmail">Email</label>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-4">
                        <input type="password" id="registerPassword" class="form-control" />
                        <label class="form-label" for="registerPassword">Password</label>
                    </div>

                    <!-- Submit button -->
                    <button type="submit" class="btn btn-primary btn-block mb-3">Sign in</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
