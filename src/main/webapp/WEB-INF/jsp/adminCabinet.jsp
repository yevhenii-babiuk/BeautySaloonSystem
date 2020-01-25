<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${empty sessionScope.language}">
    <c:set var="language" value="${applicationScope.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="textContent" />

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title><fmt:message key="adminCabinet.title"/></title>
</head>
<body>

<div><c:import url="header.jsp"/></div>

<div class="d-flex justify-content-center h-50 pt-md-5">
    <a class="btn btn-lg brown-button mx-md-5 my-auto" href="${contextPath}/admin/feedbackSearch">
        <fmt:message key="adminCabinet.allFeedback"/>
    </a>
    <a class="btn btn-lg brown-button mx-md-5 my-auto" href="${contextPath}/admin/slotManagement">
        <fmt:message key="adminCabinet.slotManagement.title"/>
    </a>
    <a class="btn btn-lg brown-button mx-md-5 my-auto" href="${contextPath}/admin/userManagement">
        <fmt:message key="adminCabinet.userManagement.title"/>
    </a>
    <a class="btn btn-lg brown-button mx-md-5 my-auto" href="${contextPath}/admin/procedureManagement">
        <fmt:message key="adminCabinet.procedureManagement.title"/>
    </a>
</div>
</body>
</html>
