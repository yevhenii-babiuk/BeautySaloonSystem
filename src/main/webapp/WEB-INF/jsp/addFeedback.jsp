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
    <title><fmt:message key="feedbackManagement.addFeedback"/></title>
</head>
<body>

<div><c:import url="userControl.jsp"/></div>


<div class="d-flex justify-content-center">
    <form action="${contextPath}/user/addFeedback.do" method="post">
        <div class="form-row">
            <div class="col-md-6">
            <label for="slotFeedback">
                <h3><fmt:message key="feedbackManagement.addFeedback"/></h3>
            </label>
            </div>
            <textarea id="slotFeedback"  class="form-control" rows="10" cols="45" name="feedbackText" required></textarea>
            <input type="text" name="slotId" value="${slotId}" hidden>
        </div>
        <div class="d-flex justify-content-center">
            <button class="btn btn-lg brown-button mt-3"><fmt:message
                    key="feedbackManagement.addFeedback"/></button>
        </div>
    </form>
</div>

</body>
</html>
