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
        <script type="text/javascript">
            jQuery(function(){
                jQuery('#passport').addClass('ahover').css("color", "#ed1e24");
            });            
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
                        <c:if test="${not empty breadcrumbs}">
                            <c:forEach items="${breadcrumbs}" var="breadcrumb">&nbsp;&raquo;&nbsp;
                                <a style="color: #FFFFFF" href="${startPath}navigation?id=${breadcrumb.id}">${breadcrumb.getLocalizedName()}</a>
                            </c:forEach>
                        </c:if>
                    </h2>
                </div>

                <c:if test="${not empty navItems}">
                    <div class="box round">
                        <span style="padding-left: 5px">
                            <ul class="list">
                                <c:forEach items="${navItems}" var="navItem">
                                    <li>
                                        <a href="${startPath}navigation?id=${navItem.id}">${navItem.getLocalizedName()}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </span>
                    </div>
                </c:if>

                <c:if test="${not empty schools}">
                    <div class="elembox">
                        <table class="datatable">
                            <caption>Реестр</caption>
                            <thead>
                                <tr>
                                    <th><spring:message code="label.school-name"/></th>
                                    <th><spring:message code="label.passport-status"/></th>
                                    <th><spring:message code="label.data-status"/></th>
                                    <th><spring:message code="label.settlement"/></th>
                                </tr>
                            </thead>
                            <c:forEach items="${schools}" var="school">
                                <tbody>
                                    <tr>
                                        <td>
                                            <a href="${startPath}passport?school=${school.id}">${school.getLocalizedName()}</a>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${(not empty school.passConfirmed) && (school.passConfirmed == 1)}">
                                                    <spring:message code="label.confirmed"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <spring:message code="label.not-confirmed"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${(not empty school.passApproved) && (school.passApproved == 1)}">
                                                    <spring:message code="label.approved"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${(not empty school.passConfirmed) && (school.passConfirmed == 1)}">
                                                            <a href="${startPath}approve-passport?nav=${navId}&school=${school.id}">
                                                                <spring:message code="label.not-approved"/>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <spring:message code="label.not-approved"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${startPath}navigation?id=${school.classItemTree.id}">${school.classItemTree.getLocalizedName()}</a>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>

            </div> <!--   content     -->        
            <div id="footer">
                <jsp:include page="templates/footer.jsp"/>
            </div>
        </div> <!--   wspace     -->
    </body>
</html>