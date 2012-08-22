<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="home.title"/></title>

        <jsp:include page="templates/init.jsp"/>
        <jsp:include page="templates/styles.jsp"/>

        <script type="text/javascript" src="/static/jquery.js"></script>
        <script type="text/javascript" src="/static/jquery-ui/js/jquery-ui.js"></script>
        <script type="text/javascript" src="/static/jquery.numberinput.js"></script>
        <script type="text/javascript" src="/static/cit-tree.js"></script>

        <script type="text/javascript">
            jQuery(function(){
                jQuery('#passport').addClass('ahover').css("color", "#ed1e24");
            });            
        </script>

        <script type="text/javascript">
            (function($){
                $(document).ready(function(){
                    jQuery('#passport').addClass('ahover').css("color", "#ed1e24");
                    
                    $("input[id*=\"field-number\"]").numberInput();
                    $("input[id*=\"field-table\"]").numberInput();
                    $("input[id*=\"field-date\"]").datepicker({
                        autoSize: true, // подгоняет размер поля ввода под формат даты
                        changeMonth: true, // возможность выбрать месяц
                        changeYear: true, // возможность выбрать год
                        constrainInput: true, // не дает вводить символы, которые не подходят по формату
                        dateFormat: "dd.mm.yy", // формат даты
                        firstDay: 1, // первый день - понедельник (воскресенье - 0)
                        navigationAsDateFormat: true, // навигация согласно формату даты (надо глянуть, что за чудо-навигация)
                        showButtonPanel: true, // показ кнопков
                        showWeek: true, // показ колонки недели
                        yearRange: "1900:2030",
                        
            <jsp:include page="templates/jquery-datepicker-localization.jsp"/>
                        });
                    });
                })(jQuery);
        </script>
    </head>
    <body>
        <div id="wspace">
            <div class="header">
                <jsp:include page="templates/language.jsp"/>
                <h1><a href="${startPath}"></a></h1>
            </div>
            <div class="menu"><jsp:include page="templates/navigation.jsp"/></div>
            <div id="content">

                <div class="panel">
                    <h2>
                        <a href="${startPath}" style="color: #FFFFFF">
                            <spring:message code="home.title"/>
                        </a>
                        <c:if test="${not empty navBreadcrumbs}">&nbsp;&raquo;&nbsp;
                            <c:forEach items="${navBreadcrumbs}" var="navBreadcrumb">
                                <a style="color: #FFFFFF" href="${startPath}navigation?id=${navBreadcrumb.id}">${navBreadcrumb.getLocalizedName()}</a>
                            </c:forEach>
                        </c:if>
                    </h2>
                </div>
                        
                <div class="box round">
                    <table id="enter"><tr style="text-align: left; border: none;">
                            <td><span>${school.getLocalizedName()}</span></td>
                            <c:choose>
                                <c:when test="${(not empty school.passConfirmed) && (school.passConfirmed == 1)}">
                                    <td>(<spring:message code="label.confirmed"/>)</td>
                                </c:when>
                                <c:otherwise>
                                    <td>(<spring:message code="label.not-confirmed"/>)</td>
                                    <td>
                                        <form action="${startPath}confirm-passport" method="post">
                                            <input type="hidden" name="school" value="${school.id}"/>
                                            <c:if test="${not empty passport}">
                                                <input type="hidden" name="passport" value="${passport.id}"/>
                                            </c:if>
                                            <input type="submit" value="<spring:message code="label.confirm"/>"/>
                                        </form>
                                    </td>    
                                </c:otherwise>
                            </c:choose>
                        </tr></table>
                </div>

                <a id="caption" href="${startPath}passport?school=${school.id}"><spring:message code="home.title"/></a>
                <c:if test="${not empty breadcrumbs}">
                    <c:forEach items="${breadcrumbs}" var="breadcrumb">&nbsp;&raquo;&nbsp;
                        <a id="caption" href="${startPath}passport?school=${school.id}&passport=${breadcrumb.id}">${breadcrumb.getLocalizedName()}</a>
                    </c:forEach>
                </c:if>

                <c:if test="${empty pasportId}">
                    <div class="box round">
                        <form action="${startPath}save-school-data" method="post">
                            <input type="hidden" name="school" value="${schoolId}"/>
                            <table id="enter">
                                <tr>
                                    <td><strong><spring:message code="label.school-name"/></strong></td>
                                    <td><input type="text" name="rname" value="${school.rname}" size="100px"/></td>
                                </tr>
                                <!--                            <tr>
                                                                <td><spring:message code="label.kname"/></td>
                                                                <td><input type="text" name="kname" value="${school.kname}"/></td>
                                                            </tr>
                                                            <tr>
                                                                <td><spring:message code="label.ename"/></td>
                                                                <td><input type="text" name="ename" value="${school.ename}"/></td>
                                                            </tr>-->
                                <tr>
                                    <td><strong><spring:message code="label.organization-type"/></strong></td>
                                    <td>
                                        <select name="orgType">
                                            <c:forEach items="${orgTypes}" var="orgType">
                                                <option value="${orgType.id}"
                                                        <c:if test="${(not empty school.orgTypeId) && (school.orgTypeId eq orgType.id)}">
                                                            selected
                                                        </c:if>
                                                        >${orgType.getLocalizedName()}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <span class="float-right">
                                            <input type="submit" value="<spring:message code="label.save"/>"/>
                                        </span>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>    
                </c:if>

                <ul class="list">
                    <c:if test="${not empty passports}">
                        <c:forEach items="${passports}" var="passport">
                            <li>
                                <a href="${startPath}passport?school=${school.id}&passport=${passport.id}">${passport.getLocalizedName()}</a>
                            </li>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty pasportId}">
                        <li>
                            <a href="${startPath}teacher-list?school=${schoolId}">
                                <spring:message code="label.stuff"/>
                            </a>
                        </li>
                        <li>
                            <a href="${startPath}student-list?school=${schoolId}">
                                <spring:message code="label.students"/>
                            </a>
                        </li>
                    </c:if>
                </ul>

                <c:if test="${not empty flcErrors}">
                    <div class="solid-border ui-state-error" style="margin: 10px; padding: 10px; color: #ed1e24;">
                        <strong><spring:message code="label.flc-errors"/></strong>
                        <c:forEach items="${flcErrors}" var="flcError">
                            <p>
                                ${flcError.getLocalizedName()}
                            </p>
                        </c:forEach>
                    </div>
                </c:if>

                <c:if test="${not empty passportElements}">
                    <div class="box round">
                        <form action="${startPath}save-passport" method="post" enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="school" value="${schoolId}"/>
                            <input type="hidden" name="passport" value="${pasportId}"/>
                            <input type="submit" value="<spring:message code="label.save"/>"/>
                            <br/>
                            <table id="enter">
                                <c:forEach items="${passportElements}" var="passportElement">
                                    <tr>
                                        ${passportElement.toFormField()}
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="margin-top: 10px;">
                                <input type="submit" value="<spring:message code="label.save"/>"/>
                            </div>
                        </form>
                    </div>
                </c:if>


            </div> <!--   content     -->
            <div id="footer">
                <jsp:include page="templates/footer.jsp"/>
            </div>
        </div> <!--   wspace     -->
    </body>
</html>
