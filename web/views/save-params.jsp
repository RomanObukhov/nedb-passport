<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><spring:message code="home.title"/></title>
        
        <link href="/static/jquery-ui/css/flick/jquery-ui.css" rel="stylesheet"/>
        
        <jsp:include page="templates/init.jsp"/>
        <jsp:include page="templates/styles.jsp"/>
    </head>
    <body class="default-component">
        <table class="ui-widget ui-widget-content ui-state-default ui-corner-all" align="center">
            <tr>
                <td class="default-component" align="center">
                    <jsp:include page="templates/navigation.jsp"/> &nbsp;&nbsp;|&nbsp;&nbsp; <jsp:include page="templates/language.jsp"/>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="${back}">Back</a>
                    <hr/>
                    <c:choose>
                        <c:when test="${not empty strings}">
                            <c:forEach items="${strings}" var="string">
                                ${string}<br/>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            no strings
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="default-component" align="center">
                    <jsp:include page="templates/footer.jsp"/>
                </td>
            </tr>
        </table>
    </body>
</html>