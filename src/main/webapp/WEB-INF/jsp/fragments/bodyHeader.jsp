<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container">
        <a href="meals" class="navbar-brand"><img src="resources/images/icon-meal.png"><spring:message
                code="app.title"/></a>
        <sec:authorize access="isAuthenticated()">
            <form:form class="form-inline my-2" action="logout" method="post">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn btn-info mr-1" href="users"><spring:message code="user.title"/></a>
                </sec:authorize>
                <a class="btn btn-info mr-1" href="profile">${userTo.name} <spring:message code="app.profile"/></a>
                <button class="btn btn-primary" type="submit">
                    <span class="fa fa-sign-out"></span>
                </button>
            </form:form>
        </sec:authorize>

        <sec:authorize access="isAnonymous()">

            <li class="nav-item">
                <form:form class="form-inline my-2" id="login_form" action="spring_security_check"
                           method="post">
                    <input class="form-control mr-1" type="text" placeholder="Email" name="username">
                    <input class="form-control mr-1" type="password" placeholder="Password" name="password">
                    <button class="btn btn-success" type="submit">
                        <span class="fa fa-sign-in"></span>
                    </button>
                </form:form>
            </li>
            <li class="nav-item dropdown show">
                <select id="locales" class="custom-select" >
                        <option  disabled selected value>${pageContext.response.locale}</option>
                        <option value="en" >English </option>
                        <option value="ru" >Русский</option>
                </select>
            </li>


        </sec:authorize>


    </div>

    <script type="text/javascript">
        $(document).ready(function() {
            $("#locales").change(function () {
                var selectedOption = $('#locales').val();
                if (selectedOption != ''){
                    window.location.href =  window.location.protocol + window.location.pathname + '?lang='+selectedOption;
                }
            });
        });
    </script>

</nav>