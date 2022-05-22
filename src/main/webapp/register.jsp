<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${sessionScope.language['Register']}</title>
    <meta charset="UTF-8" content="text/html"/>
    <jsp:include page="WEB-INF/views/general/bootstrap/include_bootstap.jsp"/>
</head>
<body>
<div class="container">
    <%--<div class="col-md-6 col-lg-4 offset-lg-4 offset-md-3 mt-5">--%>
        <jsp:include page="/WEB-INF/views/general/informMsg.jsp"/>
    <section class="vh-100">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-lg-12 col-xl-11">
                    <div class="card text-black">
                        <div class="card-body p-md-5">
                            <div class="row justify-content-center">
                                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                                    <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">${sessionScope.language['sign_in']}</p>

                                    <form class="mx-1 mx-md-4" method="post" accept-charset="UTF-8"
                                          action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="action" value="register">
                                        <%--                                        Input Name--%>
                                        <div class="d-flex flex-row align-items-center mb-1">
                                            <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                            <div class="form-outline flex-fill mb-0">
                                                <input type="text" id="name" name="name" class="form-control"/>
                                                <label class="form-label" for="name">${sessionScope.language['your_name']}</label>
                                            </div>
                                        </div>
                                        <%--                                        Input surname--%>
                                        <div class="d-flex flex-row align-items-center mb-1">
                                            <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                            <div class="form-outline flex-fill mb-0">
                                                <input type="text" id="surname" name="surname" class="form-control"/>
                                                <label class="form-label" for="surname">${sessionScope.language['your_surname']}</label>
                                            </div>
                                        </div>
                                        <%--                                        Input Email--%>
                                        <div class="d-flex flex-row align-items-center mb-1">
                                            <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                            <div class="form-outline flex-fill mb-0">
                                                <input type="text" id="email" name="email" class="form-control"/>
                                                <label class="form-label" for="email">${sessionScope.language['your_email']}</label>
                                            </div>
                                        </div>
                                        <%--                                        Input password--%>
                                        <div class="d-flex flex-row align-items-center mb-1">
                                            <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                                            <div class="form-outline flex-fill mb-0">
                                                <input type="password" id="password" name="password"
                                                       class="form-control"/>
                                                <label class="form-label" for="password">${sessionScope.language['Password']}</label>
                                            </div>
                                        </div>
                                        <%--                                        Input Phone--%>
                                        <div class="d-flex flex-row align-items-center mb-1">
                                            <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                                            <div class="form-outline flex-fill mb-0">
                                                <input type="text" id="phone" name="phone" class="form-control"/>
                                                <label class="form-label" for="phone">${sessionScope.language['phone_number']}</label>
                                            </div>
                                        </div>

                                        <%--                                        Register button--%>
                                        <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                            <button type="submit"
                                                    class="btn btn-primary btn-lg">${sessionScope.language['Register']}</button>
                                        </div>
                                    </form>

                                </div>
                                <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                                         class="img-fluid" alt="Sample image">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <%--</div>--%>
</div>
</body>
</html>
