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
    <title><fmt:message key="adminCabinet.allFeedback.title"/></title>
</head>
<body>

<div><c:import url="adminControl.jsp"/></div>

<div class="d-flex justify-content-center">
    <h2><fmt:message key="adminCabinet.allFeedback.searchFeedback"/></h2>
</div>
<br>
<div class="d-flex justify-content-center">
    <form class="col-5" action="${contextPath}/admin/feedbackSearch.do">

        <div class="row" style="margin:3%">
            <div class="col">
                <label for="masterSelect" class="col-12 col-form-label">
                    <fmt:message key="slotSearch.master"/>
                </label>
            </div>
            <div class="col">
                <select class="custom-select" id="masterSelect" name="masterId">
                    <option disabled selected value><fmt:message key="slotSearch.chooseMaster"/></option>
                    <c:forEach var="master" items="${masters}">
                        <option value="${master.id}">${master.lastName} ${master.firstName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>


        <div class="row" style="margin:3%">
            <div class="col">
                <label for="procedureSelect" class="col-12 col-form-label">
                    <fmt:message key="slotSearch.procedure"/>:
                </label>
            </div>
            <div class="col">
                <select class="custom-select" id="procedureSelect" name="procedureId">
                    <option disabled selected value><fmt:message key="slotSearch.chooseProcedure"/></option>
                    <c:forEach var="procedure" items="${procedures}">
                        <c:if test="${language eq 'en'}">
                            <option value="${procedure.id}">${procedure.nameEn}</option>
                        </c:if>
                        <c:if test="${language eq 'ru'}">
                            <option value="${procedure.id}">${procedure.nameRus}</option>
                        </c:if>
                        <c:if test="${language eq 'ua'}">
                            <option value="${procedure.id}">${procedure.nameUkr}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="row" style="margin:3%">
            <div class="col">
                <label for="minDate" class="col-12 col-form-label">
                    <fmt:message key="slotSearch.minDate"/>:
                </label>
            </div>
            <div class="col">
                <input class="custom-select" type="date" placeholder="<fmt:message key="slotSearch.minDate"/>"
                       name="minDate" id="minDate">
            </div>
        </div>

        <div class="row" style="margin:3%">
            <div class="col">
                <label for="maxDate" class="col-12 col-form-label">
                    <fmt:message key="slotSearch.maxDate"/>:
                </label>
            </div>
            <div class="col">
                <input class="custom-select" type="date" placeholder="<fmt:message key="slotSearch.maxDate"/>"
                       name="maxDate" id="maxDate">
            </div>
        </div>

        <div class="row" style="margin:3%">
            <div class="col">
                <label for="minTime" class="col-12 col-form-label">
                    <fmt:message key="slotSearch.minTime"/>:
                </label>
            </div>
            <div class="col">
                <input class="custom-select" type="time" name="minTime" id="minTime">
            </div>
        </div>

        <div class="row" style="margin:3%">
            <div class="col">
                <label for="maxTime" class="col-12 col-form-label">
                    <fmt:message key="slotSearch.maxTime"/>:
                </label>
            </div>
            <div class="col">
                <input class="custom-select" type="time" name="maxTime" id="maxTime">
            </div>
        </div>

        <div class="d-flex justify-content-center" style="margin:3%">
            <button class="btn btn-lg brown-button"><fmt:message key="slotSearch.findSlot"/></button>
        </div>
    </form>
</div>

<c:if test="${not empty slots}">
    <div class="d-flex justify-content-center">
        <h3><fmt:message key="slotSearch.searchResult"/>:</h3>
    </div>
    <div class="bg-semi-transparent">
        <table class="table table-hover table-sm">

            <tr>
                <th class="text-center"><fmt:message key="slotSearch.result.master"/></th>
                <th class="text-center"><fmt:message key="slotSearch.result.date"/></th>
                <th class="text-center"><fmt:message key="slotSearch.result.time"/></th>
                <th class="text-center"><fmt:message key="slotSearch.result.user"/></th>
                <th class="text-center"><fmt:message key="slotSearch.result.procedure"/></th>
                <th class="text-center"><fmt:message key="slotSearch.result.feedback"/></th>
            </tr>

            <c:forEach var="slotDTO" items="${slots}">
                <tr>
                    <td>
                            ${slotDTO.master.firstName} ${slotDTO.master.lastName}
                    </td>
                    <td>
                            ${slotDTO.slot.date}
                    </td>
                    <td>
                            ${slotDTO.slot.startTime} - ${slotDTO.slot.endTime}
                    </td>
                    <td>
                            ${slotDTO.client.firstName} ${slotDTO.client.lastName}
                    </td>
                    <td>
                        <c:if test="${language eq 'en'}">
                            ${slotDTO.procedure.nameEn}
                        </c:if>
                        <c:if test="${language eq 'ru'}">
                            ${slotDTO.procedure.nameRus}
                        </c:if>
                        <c:if test="${language eq 'ua'}">
                            ${slotDTO.procedure.nameUkr}
                        </c:if>
                    </td>
                    <td>
                            ${slotDTO.feedback.text}
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
                           href="${contextPath}/slotSearch.do?page=${currentPage - 1} "><fmt:message
                                key="pagination.previous"/> </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link text-dark"
                           href="${contextPath}/slotSearch.do?page=${currentPage - 1}"><fmt:message
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
                                                 href="${contextPath}/slotSearch.do?page=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>


                <%--For displaying Next link --%>
            <c:choose>
                <c:when test="${currentPage lt pagesQuantity}">
                    <li class="page-item">
                        <a class="page-link text-dark"
                           href="${contextPath}/slotSearch.do?page=${currentPage + 1}"><fmt:message
                                key="pagination.next"/> </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link text-dark"
                           href="${contextPath}/slotSearch.do?page=${currentPage + 1}"><fmt:message
                                key="pagination.next"/> </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</c:if>
</body>
</html>
