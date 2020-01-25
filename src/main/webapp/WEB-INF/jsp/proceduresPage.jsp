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
    <title><fmt:message key="procedureManagement.showProcedure"/></title>
</head>
<body>

<div><c:import url="adminControl.jsp"/></div>

<div class="d-flex justify-content-center">
    <h2><fmt:message key="procedureManagement.showProcedure"/></h2>
</div>
<br>

<%--Printing slot sign up result--%>
<c:if test="${not empty actionResult}">
    <div class="d-flex justify-content-center">
        <h4><fmt:message key="${actionResult}"/></h4>
    </div>
</c:if>

<c:if test="${not empty procedures}">
    <div class="bg-semi-transparent">
        <table class="table table-hover table-sm">

            <tr>
                <th class="text-center"><fmt:message key="procedureManagement.result.update"/></th>
                <th class="text-center"><fmt:message key="procedureManagement.result.name"/></th>
                <th class="text-center"><fmt:message key="procedureManagement.result.price"/></th>
                <th class="text-center"><fmt:message key="procedureManagement.result.description"/></th>
            </tr>

            <c:forEach var="user" items="${procedures}">
                <tr>
                        <td>
                            <form action="${contextPath}/admin/procedureManagement/updateProcedure" method="post">
                                <input type="text" name="procedureId" value="${user.id}" hidden>
                                <button class="btn brown-button"><fmt:message
                                        key="procedureManagement.result.update"/></button>
                            </form>
                        </td>
                    <td>
                        <c:if test="${language eq 'en'}">
                            ${user.nameEn}
                        </c:if>
                        <c:if test="${language eq 'ru'}">
                            ${user.nameRus}
                        </c:if>
                        <c:if test="${language eq 'ua'}">
                            ${user.nameUkr}
                        </c:if>
                    </td>
                    <td>
                            ${user.price}
                    </td>
                    <td>
                        <c:if test="${language eq 'en'}">
                            ${user.descriptionEn}
                        </c:if>
                        <c:if test="${language eq 'ru'}">
                            ${user.descriptionRus}
                        </c:if>
                        <c:if test="${language eq 'ua'}">
                            ${user.descriptionUkr}
                        </c:if>
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
                           href="${contextPath}/admin/procedureManagement/procedures?${queryStr}page=${currentPage - 1} "><fmt:message
                                key="pagination.previous"/> </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link text-dark"
                           href="${contextPath}/admin/procedureManagement/procedures?${queryStr}page=${currentPage - 1}"><fmt:message
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
                                                 href="${contextPath}/admin/procedureManagement/procedures?${queryStr}page=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>


                <%--For displaying Next link --%>
            <c:choose>
                <c:when test="${currentPage lt pagesQuantity}">
                    <li class="page-item">
                        <a class="page-link text-dark"
                           href="${contextPath}/admin/procedureManagement/procedures?${queryStr}page=${currentPage + 1}"><fmt:message
                                key="pagination.next"/> </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link text-dark"
                           href="${contextPath}/admin/procedureManagement/procedures?${queryStr}page=${currentPage + 1}"><fmt:message
                                key="pagination.next"/> </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</c:if>
</body>
</html>
