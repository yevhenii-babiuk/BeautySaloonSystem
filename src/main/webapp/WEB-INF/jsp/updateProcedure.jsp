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
    <title><fmt:message key="procedureManagement.updateProcedure.title"/></title>
</head>
<body>

<div><c:import url="adminControl.jsp"/></div>


<c:choose>
    <c:when test="${errors.hasErrors}">
        <div class="d-flex justify-content-center invalid-form font-weight-bold">
            <h3><fmt:message key="procedureManagement.updateProcedure.errors"/></h3>
        </div>
        <%---------------------Form with errors----------------------%>

        <div class="d-flex justify-content-center h-50 pt-md-5">
            <form action="${contextPath}/admin/procedureManagement/updateProcedure.do" method="post">
                <div class="form-row">
                    <div class="col-md-4">
                        <label for="nameEn">
                            <h3><fmt:message key="procedureManagement.addProcedure.englishName"/></h3>
                        </label>
                    </div>
                    <div class="col-md-4">
                        <label for="nameUa">
                            <h3><fmt:message key="procedureManagement.addProcedure.ukrainianName"/></h3>
                        </label>
                    </div>
                    <div class="col-md-4">
                        <label for="nameRu">
                            <h3><fmt:message key="procedureManagement.addProcedure.russianName"/></h3>
                        </label>
                    </div>
                </div>
                <div class="form-row">
                    <c:choose>
                        <c:when test="${errors.errorsMap['englishName'] != null}">
                            <div class="col-md-4">
                                <input class="form-control is-invalid" type="text" name="nameEn" style="width: 100%" required>
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['englishName']}"/>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-md-4">
                                <input class="form-control is-valid" type="text" name="nameEn" style="width: 100%" required>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${errors.errorsMap['ukrainianName'] != null}">
                            <div class="col-md-4">
                                <input class="form-control is-invalid" type="text" name="nameUa" style="width: 100%" required>
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['ukrainianName']}"/>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-md-4">
                                <input class="form-control is-valid" type="text" name="nameUa" style="width: 100%" required>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${errors.errorsMap['russianName'] != null}">
                            <div class="col-md-4">
                                <input class="form-control is-invalid" type="text" name="nameRu" style="width: 100%" required>
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['russianName']}"/>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-md-4">
                                <input class="form-control is-valid" type="text" name="nameRu" style="width: 100%" required>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="form-row">
                    <div class="col-md-4">
                        <label for="descriptionEn">
                            <h3><fmt:message key="procedureManagement.addProcedure.englishDescription"/></h3>
                        </label>
                        <c:choose>
                            <c:when test="${errors.errorsMap['englishDescription'] != null}">
                                <textarea class="form-control is-invalid" rows="10" cols="50" name="descriptionEn"
                                          required></textarea>
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['englishDescription']}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <textarea class="form-control is-valid" rows="10" cols="50" name="descriptionEn"
                                          required></textarea>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-4">
                        <label for="descriptionUa">
                            <h3><fmt:message key="procedureManagement.addProcedure.ukrainianDescription"/></h3>
                        </label>
                        <c:choose>
                            <c:when test="${errors.errorsMap['ukrainianDescription'] != null}">
                                <textarea class="form-control is-invalid" rows="10" cols="50" name="descriptionUa"
                                          required></textarea>
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['ukrainianDescription']}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <textarea class="form-control is-valid" rows="10" cols="50" name="descriptionUa"
                                          required></textarea>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-4">
                        <label for="descriptionRu">
                            <h3><fmt:message key="procedureManagement.addProcedure.russianDescription"/></h3>
                        </label>
                        <c:choose>
                            <c:when test="${errors.errorsMap['russianDescription'] != null}">
                                <textarea class="form-control is-invalid" rows="10" cols="50" name="descriptionRu"
                                          required></textarea>
                                <div class="d-flex justify-content-center invalid-form font-weight-bold">
                                    <fmt:message key="${errors.errorsMap['russianDescription']}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <textarea class="form-control is-valid" rows="10" cols="50" name="descriptionRu"
                                          required></textarea>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="d-flex justify-content-center">
                    <label for="procedurePrice">
                        <h3><fmt:message key="procedureManagement.addProcedure.price"/></h3>
                    </label>
                </div>
                <c:choose>
                    <c:when test="${errors.errorsMap['price'] != null}">
                        <div class="d-flex justify-content-center">
                            <input class="is-invalid" type="text" name="procedurePrice" required>
                        </div>
                        <div class="d-flex justify-content-center invalid-form font-weight-bold">
                            <fmt:message key="${errors.errorsMap['price']}"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="d-flex justify-content-center">
                            <input class="is-valid" type="text" name="procedurePrice" required>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="d-flex justify-content-center">
                    <input type="text" value="${procedure.id}" name="procedureId" hidden>
                    <button class="btn btn-lg brown-button mt-3"><fmt:message
                            key="procedureManagement.updateProcedure.title"/></button>
                </div>
            </form>
        </div>
    </c:when>

    <c:otherwise>
        <div class="d-flex justify-content-center h-50 pt-md-5">
            <form action="${contextPath}/admin/procedureManagement/updateProcedure.do" method="post">
                <div class="form-row">
                    <div class="col-md-4">
                        <label for="nameEn">
                            <h3><fmt:message key="procedureManagement.addProcedure.englishName"/></h3>
                        </label>
                    </div>
                    <div class="col-md-4">
                        <label for="nameUa">
                            <h3><fmt:message key="procedureManagement.addProcedure.ukrainianName"/></h3>
                        </label>
                    </div>
                    <div class="col-md-4">
                        <label for="nameRu">
                            <h3><fmt:message key="procedureManagement.addProcedure.russianName"/></h3>
                        </label>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-4">
                        <input type="text" id="nameEn" name="nameEn" style="width: 100%" value="${procedure.nameEn}" required>
                    </div>
                    <div class="col-md-4">
                        <input type="text" id="nameUa" name="nameUa" style="width: 100%" value="${procedure.nameUkr}" required>
                    </div>
                    <div class="col-md-4">
                        <input type="text" id="nameRu" name="nameRu" style="width: 100%" value="${procedure.nameRus}" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-4">
                        <label for="descriptionEn">
                            <h3><fmt:message key="procedureManagement.addProcedure.englishDescription"/></h3>
                        </label>
                        <textarea id="descriptionEn" class="form-control" rows="10" cols="50"
                                  name="descriptionEn"
                                  value="${procedure.descriptionEn}"
                                  required>${procedure.descriptionEn}</textarea>
                    </div>
                    <div class="col-md-4">
                        <label for="descriptionUa">
                            <h3><fmt:message key="procedureManagement.addProcedure.ukrainianDescription"/></h3>
                        </label>
                        <textarea id="descriptionUa" class="form-control" rows="10" cols="50"
                                  name="descriptionUa"
                                  value="${procedure.descriptionUkr}"
                                  required>${procedure.descriptionUkr}</textarea>
                    </div>
                    <div class="col-md-4">
                        <label for="descriptionRu">
                            <h3><fmt:message key="procedureManagement.addProcedure.russianDescription"/></h3>
                        </label>
                        <textarea id="descriptionRu" class="form-control" rows="10" cols="50"
                                  name="descriptionRu"
                                  value="${procedure.descriptionRus}"
                                  required>${procedure.descriptionRus}</textarea>
                    </div>
                </div>
                <div class="d-flex justify-content-center">
                    <label for="procedurePrice">
                        <h3><fmt:message key="procedureManagement.addProcedure.price"/></h3>
                    </label>
                </div>
                <div class="d-flex justify-content-center">
                    <input type="text" name="procedurePrice" id="procedurePrice" value="${procedure.price}" required>
                </div>
                <div class="d-flex justify-content-center">
                    <input type="text" value="${procedure.id}" name="procedureId" hidden>
                    <button class="btn btn-lg brown-button mt-3"><fmt:message
                            key="procedureManagement.updateProcedure.title"/></button>
                </div>
            </form>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
