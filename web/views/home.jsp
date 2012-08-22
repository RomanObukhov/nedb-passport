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
                    <h2>Территориальная принадлежность</h2>
                </div>

                <div class="box round">
                    <c:choose>
                        <c:when test="${empty navItems}">
                            <spring:message code="label.no-elements"/>
                        </c:when>
                        <c:otherwise>
                            <ul class="list">
                                <c:forEach items="${navItems}" var="navItem">
                                    <li>
                                        <a href="${startPath}navigation?id=${navItem.id}">${navItem.getLocalizedName()}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div> <!--   content     -->
            <div id="footer">
                <jsp:include page="templates/footer.jsp"/>
            </div>
        </div> <!--   wspace     -->

    </body>
</html>