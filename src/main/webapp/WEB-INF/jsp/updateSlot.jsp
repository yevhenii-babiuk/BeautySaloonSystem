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
    <title><fmt:message key="adminCabinet.slotManagement.updateSlot.title"/></title>
</head>
<body>

<div><c:import url="adminControl.jsp"/></div>

<div class="d-flex justify-content-center">
    <h2><fmt:message key="adminCabinet.slotManagement.updateSlot.title"/></h2>
</div>
<br>
<c:choose>
    <c:when test="${errors.hasErrors}">
        <div class="d-flex justify-content-center invalid-form font-weight-bold">
            <h3><fmt:message key="slot.update.errors"/></h3>
        </div>
        <c:if test="${errors.errorsMap['slotId'] != null}">
            <div class="d-flex justify-content-center invalid-form font-weight-bold">
                <h3><fmt:message key="${errors.errorsMap['slotId']}"/></h3>
            </div>
        </c:if>
        <%---------------------Form with errors----------------------%>
        <div class="d-flex justify-content-center mt-5">
            <form class="col-5" action="${contextPath}/admin/slotManagement/updateSlot.do">
                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="masterSelect" class="col-12 col-form-label">
                            <fmt:message key="editSlot.master"/>
                        </label>
                    </div>
                    <div class="col">
                        <c:choose>
                            <c:when test="${errors.errorsMap['master'] != null}">
                                <select class="custom-select is-invalid" name="masterId" required>
                                    <option disabled selected value><fmt:message key="editSlot.chooseMaster"/></option>
                                    <c:forEach var="master" items="${masters}">
                                        <option value="${master.id}">${master.lastName} ${master.firstName}</option>
                                    </c:forEach>
                                </select>
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['master']}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <select class="custom-select is-valid" name="masterId" required>
                                    <option disabled selected value><fmt:message key="editSlot.chooseMaster"/></option>
                                    <c:forEach var="master" items="${masters}">
                                        <option value="${master.id}">${master.lastName} ${master.firstName}</option>
                                    </c:forEach>
                                </select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="procedureSelect" class="col-12 col-form-label">
                            <fmt:message key="editSlot.procedure"/>:
                        </label>
                    </div>
                    <div class="col">
                        <c:choose>
                            <c:when test="${errors.errorsMap['procedure'] != null}">
                                <select class="custom-select is-invalid" name="procedureId">
                                    <option disabled selected value><fmt:message
                                            key="editSlot.chooseProcedure"/></option>
                                    <c:forEach var="slot" items="${procedures}">
                                        <c:if test="${language eq 'en'}">
                                            <option value="${slot.id}">${slot.nameEn}</option>
                                        </c:if>
                                        <c:if test="${language eq 'ru'}">
                                            <option value="${slot.id}">${slot.nameRus}</option>
                                        </c:if>
                                        <c:if test="${language eq 'ua'}">
                                            <option value="${slot.id}">${slot.nameUkr}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['master']}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <select class="custom-select is-valid" name="procedureId">
                                    <option disabled selected value><fmt:message
                                            key="editSlot.chooseProcedure"/></option>
                                    <c:forEach var="slot" items="${procedures}">
                                        <c:if test="${language eq 'en'}">
                                            <option value="${slot.id}">${slot.nameEn}</option>
                                        </c:if>
                                        <c:if test="${language eq 'ru'}">
                                            <option value="${slot.id}">${slot.nameRus}</option>
                                        </c:if>
                                        <c:if test="${language eq 'ua'}">
                                            <option value="${slot.id}">${slot.nameUkr}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="date" class="col-12 col-form-label">
                            <fmt:message key="editSlot.date"/>:
                        </label>
                    </div>
                    <div class="col">
                        <c:choose>
                            <c:when test="${errors.errorsMap['date'] != null}">
                                <input class="custom-select is-invalid" type="date"
                                       placeholder="<fmt:message key="editSlot.date"/>"
                                       name="date">
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['date']}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <input class="custom-select is-invalid" type="date"
                                       placeholder="<fmt:message key="editSlot.date"/>"
                                       name="date">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="startTime" class="col-12 col-form-label">
                            <fmt:message key="editSlot.startTime"/>:
                        </label>
                    </div>
                    <div class="col">
                        <c:choose>
                            <c:when test="${errors.errorsMap['startTime'] != null}">
                                <input class="custom-select is-invalid" type="time" name="startTime">
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['startTime']}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <input class="custom-select is-valid" type="time" name="startTime">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="startTime" class="col-12 col-form-label">
                            <fmt:message key="editSlot.endTime"/>:
                        </label>
                    </div>
                    <div class="col">
                        <c:choose>
                            <c:when test="${errors.errorsMap['endTime'] != null}">
                                <input class="custom-select is-invalid" type="time" name="endTime">
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['endTime']}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <input class="custom-select is-valid" type="time" name="endTime" >
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="d-flex justify-content-center" style="margin:3%">
                    <input type="text" name="slotId" value="${slot.slot.id}" hidden>
                    <button class="btn btn-lg brown-button"><fmt:message key="editSlot.updateSlot"/></button>
                </div>
            </form>
        </div>
    </c:when>

    <c:otherwise>
        <%---------------------Form without errors----------------------%>
        <div class="d-flex justify-content-center">
            <form class="col-5" action="${contextPath}/admin/slotManagement/updateSlot.do">

                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="masterSelect" class="col-12 col-form-label">
                            <fmt:message key="editSlot.master"/>
                        </label>
                    </div>
                    <div class="col">
                        <select class="custom-select" id="masterSelect" name="masterId">
                            <option selected value="${slot.slot.master}">${slot.master.lastName} ${slot.master.firstName}</option>
                            <c:forEach var="master" items="${masters}">
                                <option value="${master.id}">${master.lastName} ${master.firstName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="procedureSelect" class="col-12 col-form-label">
                            <fmt:message key="editSlot.procedure"/>:
                        </label>
                    </div>
                    <div class="col">
                        <select class="custom-select" id="procedureSelect" name="procedureId">
                            <c:if test="${language eq 'en'}">
                                <option selected value="${slot.slot.procedure}">${slot.procedure.nameEn}</option>
                            </c:if>
                            <c:if test="${language eq 'ru'}">
                                <option selected value="${slot.slot.procedure}">${slot.procedure.nameRus}</option>
                            </c:if>
                            <c:if test="${language eq 'ua'}">
                                <option selected value="${slot.slot.procedure}">${slot.procedure.nameUkr}</option>
                            </c:if>
                            <c:forEach var="slot" items="${procedures}">
                                <c:if test="${language eq 'en'}">
                                    <option value="${slot.id}">${slot.nameEn}</option>
                                </c:if>
                                <c:if test="${language eq 'ru'}">
                                    <option value="${slot.id}">${slot.nameRus}</option>
                                </c:if>
                                <c:if test="${language eq 'ua'}">
                                    <option value="${slot.id}">${slot.nameUkr}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="date" class="col-12 col-form-label">
                            <fmt:message key="editSlot.date"/>:
                        </label>
                    </div>
                    <div class="col">
                        <input class="custom-select" type="date" placeholder="<fmt:message key="editSlot.date"/>"
                               name="date" id="date" value="${slot.slot.date}">
                    </div>
                </div>

                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="startTime" class="col-12 col-form-label">
                            <fmt:message key="editSlot.startTime"/>:
                        </label>
                    </div>
                    <div class="col">
                        <input class="custom-select" type="time" name="startTime" id="startTime" value="${slot.slot.startTime}">
                    </div>
                </div>

                <div class="row" style="margin:3%">
                    <div class="col">
                        <label for="endTime" class="col-12 col-form-label">
                            <fmt:message key="editSlot.endTime"/>:
                        </label>
                    </div>
                    <div class="col">
                        <input class="custom-select" type="time" name="endTime" id="endTime" value="${slot.slot.endTime}">
                    </div>
                </div>

                <div class="d-flex justify-content-center" style="margin:3%">
                    <input type="text" name="slotId" value="${slot.slot.id}" hidden>
                    <button class="btn btn-lg brown-button"><fmt:message key="editSlot.updateSlot"/></button>
                </div>
            </form>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>