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
    <title><fmt:message key="masterCabinet.masterFeedback"/></title>
</head>
<body>

<div><c:import url="masterControl.jsp"/></div>

<div>

    <%--Printing slot sign up result--%>
    <c:if test="${not empty actionResult}">
        <div class="d-flex justify-content-center">
            <h4><fmt:message key="${actionResult}"/></h4>
        </div>
    </c:if>

    <div class="d-flex justify-content-center my-md-5">
        <h1><fmt:message key="masterCabinet.mastersFeedback"/></h1>
    </div>

    <c:choose>
        <c:when test="${not empty slots}">
            <div class="bg-semi-transparent mx-md-5 d-flex justify-content-center">
                <table class="table table-hover table-md">
                    <tr>
                        <th class="text-center"><fmt:message key="slotSearch.result.date"/></th>
                        <th class="text-center"><fmt:message key="slotSearch.result.procedure"/></th>
                        <th class="text-center"><fmt:message key="slotSearch.result.feedback"/></th>
                    </tr>


                    <c:forEach var="user" items="${slots}">
                        <tr>
                            <td>
                                    ${user.slot.date}
                            </td>
                            <td>
                                <c:if test="${language eq 'en'}">
                                    ${user.procedure.nameEn}
                                </c:if>
                                <c:if test="${language eq 'ru'}">
                                    ${user.procedure.nameRus}
                                </c:if>
                                <c:if test="${language eq 'ua'}">
                                    ${user.procedure.nameUkr}
                                </c:if>
                            </td>
                            <td>
                                ${user.feedback.text}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <br>
            <%------------------------- Pagination part -------------------------------%>

            <nav>
                <ul class="pagination justify-content-center">
                        <%--For displaying Previous link except for the 1st page --%>
                    <c:choose>
                        <c:when test="${currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link text-dark"
                                   href="${contextPath}/master/masterFeedback?${queryStr}page=${currentPage - 1} "><fmt:message
                                        key="pagination.previous"/> </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link text-dark"
                                   href="${contextPath}/master/masterFeedback?${queryStr}page=${currentPage - 1}"><fmt:message
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
                                                         href="${contextPath}/master/masterFeedback?${queryStr}page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>


                        <%--For displaying Next link --%>
                    <c:choose>
                        <c:when test="${currentPage lt pagesQuantity}">
                            <li class="page-item">
                                <a class="page-link text-dark"
                                   href="${contextPath}/master/masterFeedback?${queryStr}page=${currentPage + 1}"><fmt:message
                                        key="pagination.next"/> </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link text-dark"
                                   href="${contextPath}/master/masterFeedback?page=${currentPage + 1}"><fmt:message
                                        key="pagination.next"/> </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </c:when>
        <c:otherwise>
            <div class="d-flex justify-content-center">
                <h3><fmt:message key="noData"/></h3>
            </div>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>
