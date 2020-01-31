<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${empty sessionScope.language}">
    <c:set var="language" value="${applicationScope.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="textContent"/>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title><fmt:message key="userManagement.findUser.title"/></title>
</head>
<body>

<div><c:import url="adminControl.jsp"/></div>

<c:if test="${not empty actionResult}">
    <div class="d-flex justify-content-center">
        <h4><fmt:message key="${actionResult}"/></h4>
    </div>
</c:if>

<div class="d-flex justify-content-center">
    <h2><fmt:message key="userManagement.findUser.title"/></h2>
</div>
<br>
<div class="d-flex justify-content-center">
    <form class="col-5" action="${contextPath}/admin/userManagement/searchUser.do">

        <div class="row" style="margin:3%">
            <div class="col">
                <label for="searchString" class="col-12 col-form-label">
                    <fmt:message key="userManagement.findUser.nameOrSurname"/>
                </label>
            </div>
            <div class="col">
                <input class="custom-select" id="searchString" name="searchString" type="text" placeholder="<fmt:message key="userManagement.findUser.userNameOrSurname"/>">
            </div>
        </div>

        <div class="row" style="margin:3%">
            <div class="col">
                <label for="email" class="col-12 col-form-label">
                    <fmt:message key="userManagement.findUser.email"/>
                </label>
            </div>
            <div class="col">
                <input class="custom-select" id="email" name="email" type="text" placeholder="<fmt:message key="userManagement.findUser.email"/>">
            </div>
        </div>

        <div class="row" style="margin:3%">
            <div class="col">
                <label for="phone" class="col-12 col-form-label">
                    <fmt:message key="userManagement.findUser.phone"/>
                </label>
            </div>
            <div class="col">
                <input class="custom-select" id="phone" name="phone" type="text" placeholder="<fmt:message key="userManagement.findUser.phone"/>">
            </div>
        </div>

        <div class="row" style="margin:3%">
            <div class="col">
                <label for="roleSelect" class="col-12 col-form-label">
                    <fmt:message key="userManagement.findUser.role"/>:
                </label>
            </div>
            <div class="col">
                <select class="custom-select" id="roleSelect" name="role">
                    <option disabled selected><fmt:message key="userManagement.findUser.chooseRole"/></option>
                    <option value="ADMINISTRATOR"><fmt:message key="userManagement.findUser.role.admin"/></option>
                    <option value="MASTER"><fmt:message key="userManagement.findUser.role.master"/></option>
                    <option value="USER"><fmt:message key="userManagement.findUser.role.user"/></option>
                </select>
            </div>
        </div>

        <div class="d-flex justify-content-center" style="margin:3%">
            <button class="btn btn-lg brown-button"><fmt:message key="userManagement.findUser.title"/></button>
        </div>
    </form>
</div>

<c:if test="${not empty users}">
    <div class="d-flex justify-content-center">
        <h3><fmt:message key="userManagement.findUser.result"/></h3>
    </div>
    <div class="bg-semi-transparent">
        <table class="table table-hover table-sm">

            <tr>
                <th class="text-center"><fmt:message key="userManagement.findUser.result.userId"/></th>
                <th class="text-center"><fmt:message key="userManagement.findUser.result.role"/></th>
                <th class="text-center"><fmt:message key="userManagement.findUser.result.nameAndSurname"/></th>
                <th class="text-center"><fmt:message key="userManagement.findUser.result.email"/></th>
                <th class="text-center"><fmt:message key="userManagement.findUser.result.phone"/></th>
            </tr>

            <c:forEach var="user" items="${users}">
                <tr>
                    <td>
                        ${user.id}
                    </td>
                    <td>
                        <c:if test="${user.role eq 'ADMINISTRATOR'}">
                            <fmt:message key="userManagement.findUser.role.admin"/>
                        </c:if>
                        <c:if test="${user.role eq 'MASTER'}">
                            <fmt:message key="userManagement.findUser.role.master"/>
                        </c:if>
                        <c:if test="${user.role eq 'USER'}">
                            <fmt:message key="userManagement.findUser.role.user"/>
                        </c:if>
                    </td>
                    <td>
                            ${user.firstName} ${user.lastName}
                    </td>
                    <td>
                            ${user.email}
                    </td>
                    <td>
                            ${user.phone}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
    <%----------------------- Pagination part -----------------------------%>

    <nav>
        <ul class="pagination justify-content-center">
                <%--For displaying Previous link except for the 1st page --%>
            <c:choose>
                <c:when test="${currentPage != 1}">
                    <li class="page-item">
                        <a class="page-link text-dark"
                           href="${contextPath}/admin/userManagement/searchUser.do?${queryStr}page=${currentPage - 1} "><fmt:message
                                key="pagination.previous"/> </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link text-dark"
                           href="${contextPath}/admin/userManagement/searchUser.do?${queryStr}page=${currentPage - 1}"><fmt:message
                                key="pagination.previous"/> </a>
                    </li>
                </c:otherwise>
            </c:choose>

                <%--For displaying Page numbers.
                The when condition does not display a link for the current page--%>

            <c:forEach begin="1" end="${pagesQuantity}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item">
                            <div class="page-link text-dark">${i}</div>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link text-dark"
                                                 href="${contextPath}/admin/userManagement/searchUser.do?${queryStr}page=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>


                <%--For displaying Next link --%>
            <c:choose>
                <c:when test="${currentPage lt pagesQuantity}">
                    <li class="page-item">
                        <a class="page-link text-dark"
                           href="${contextPath}/admin/userManagement/searchUser.do?${queryStr}page=${currentPage + 1}"><fmt:message
                                key="pagination.next"/> </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link text-dark"
                           href="${contextPath}/admin/userManagement/searchUser.do?${queryStr}page=${currentPage + 1}"><fmt:message
                                key="pagination.next"/> </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</c:if>
<c:if test="${not empty slots}">
    <div class="d-flex justify-content-center">
        <h3><fmt:message key="noData"/></h3>
    </div>
</c:if>
</body>
</html>
