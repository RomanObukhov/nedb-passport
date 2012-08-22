<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
buttonText: "<spring:message code="label.choose"/>", //текст на кнопке переключения (хз, что за кнопка)
closeText: "<spring:message code="label.close"/>", // текст на кнопке закрытия
currentText: "<spring:message code="label.today"/>", // текст ссылки на текущую дату
dayNames: [ // полные названия дней
    "<spring:message code="label.day-name-7"/>",
    "<spring:message code="label.day-name-1"/>",
    "<spring:message code="label.day-name-2"/>",
    "<spring:message code="label.day-name-3"/>",
    "<spring:message code="label.day-name-4"/>",
    "<spring:message code="label.day-name-5"/>",
    "<spring:message code="label.day-name-6"/>"
],
dayNamesMin: [ // Минимальные названия дней
    "<spring:message code="label.day-name-min-7"/>",
    "<spring:message code="label.day-name-min-1"/>",
    "<spring:message code="label.day-name-min-2"/>",
    "<spring:message code="label.day-name-min-3"/>",
    "<spring:message code="label.day-name-min-4"/>",
    "<spring:message code="label.day-name-min-5"/>",
    "<spring:message code="label.day-name-min-6"/>"
],
dayNamesShort: [ // Краткие названия дней
    "<spring:message code="label.day-name-short-7"/>",
    "<spring:message code="label.day-name-short-1"/>",
    "<spring:message code="label.day-name-short-2"/>",
    "<spring:message code="label.day-name-short-3"/>",
    "<spring:message code="label.day-name-short-4"/>",
    "<spring:message code="label.day-name-short-5"/>",
    "<spring:message code="label.day-name-short-6"/>"
],
monthNames: [ // Названия месяцев
    "<spring:message code="label.month-name-1"/>",
    "<spring:message code="label.month-name-2"/>",
    "<spring:message code="label.month-name-3"/>",
    "<spring:message code="label.month-name-4"/>",
    "<spring:message code="label.month-name-5"/>",
    "<spring:message code="label.month-name-6"/>",
    "<spring:message code="label.month-name-7"/>",
    "<spring:message code="label.month-name-8"/>",
    "<spring:message code="label.month-name-9"/>",
    "<spring:message code="label.month-name-10"/>",
    "<spring:message code="label.month-name-11"/>",
    "<spring:message code="label.month-name-12"/>"
],
monthNamesShort: [ // Краткие названия месяцев
    "<spring:message code="label.month-name-short-1"/>",
    "<spring:message code="label.month-name-short-2"/>",
    "<spring:message code="label.month-name-short-3"/>",
    "<spring:message code="label.month-name-short-4"/>",
    "<spring:message code="label.month-name-short-5"/>",
    "<spring:message code="label.month-name-short-6"/>",
    "<spring:message code="label.month-name-short-7"/>",
    "<spring:message code="label.month-name-short-8"/>",
    "<spring:message code="label.month-name-short-9"/>",
    "<spring:message code="label.month-name-short-10"/>",
    "<spring:message code="label.month-name-short-11"/>",
    "<spring:message code="label.month-name-short-12"/>"
],
nextText: "<spring:message code="label.next"/>",
prevText: "<spring:message code="label.prev"/>",
weekHeader: "<spring:message code="label.week-short"/>"