<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--<span class="float-left">
    <a href="${startPath}"><spring:message code="home.title"/></a>
</span>-->
<span class="float-left">
    <ul id="menu">
        <li><a id="home" href="${startPath}" title="Главная"><img src="/static/styles/images/home.png" alt="Home" class="home" /></a></li>       
        <li><a id="passport" href="${startPath}"><spring:message code="home.title"/></a></li>
        <li><a id="analyse" href="/OLAPLAYOUT">Аналитика</a></li>
        <li><a id="reports" href="/Reports">Регламентированные отчеты</a></li>
    </ul>
</span>