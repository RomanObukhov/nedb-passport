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
                    <h2><a href="${startPath}" style="color: #FFFFFF"><spring:message code="home.title"/></a></h2>
                </div>
                <div class="box round">
                    <a href="${startPath}passport?school=${schoolId}">${school.getLocalizedName()}</a>
                </div>

                <div class="panel">
                    <h2><spring:message code="label.creating"/></h2>
                </div>
                <div class="box round">
                    <form action="create-teacher" method="post">
                        <input type="hidden" name="school" value="${schoolId}"/>
                        <table id="enter">
                            <tr style="text-align: left; border: none;">
                                <td><spring:message code="label.iin"/></td>
                                <td><input type="text" name="teacher-iin"/></td>
                            </tr>
                            <tr style="text-align: left; border: none;">
                                <td><spring:message code="label.last-name"/></td>
                                <td><input type="text" name="teacher-f"/></td>
                            </tr>
                            <tr style="text-align: left; border: none;">
                                <td><spring:message code="label.first-name"/></td>
                                <td><input type="text" name="teacher-i"/></td>
                            </tr>
                            <tr style="text-align: left; border: none;">
                                <td><spring:message code="label.middle-name"/></td>
                                <td><input type="text" name="teacher-o"/></td>
                            </tr>
                            <tr style="text-align: left; border: none;">
                                <td colspan="2">
                                    <span class="float-right">
                                        <input type="submit" value="<spring:message code="label.create"/>"/>
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>

                <div class="elembox">
                    <table class="datatable">
                        <caption><spring:message code="label.stuff"/></caption>
                        <thead>
                            <tr>
                                <th><spring:message code="label.iin"/></th>
                                <th><spring:message code="label.last-name"/></th>
                                <th><spring:message code="label.first-name"/></th>
                                <th><spring:message code="label.middle-name"/></th>
                                <th><spring:message code="label.actions"/></th>
                            </tr>
                        </thead>
                        <c:forEach items="${teachers}" var="teacher">
                            <tr>
                                <td>
                                    <a href="${startPath}teacher?school=${schoolId}&teacher=${teacher.id}">
                                        ${teacher.iin}
                                    </a>
                                </td>
                                <td>
                                    <a href="${startPath}teacher?school=${schoolId}&teacher=${teacher.id}">
                                        ${teacher.f}
                                    </a>
                                </td>
                                <td>
                                    <a href="${startPath}teacher?school=${schoolId}&teacher=${teacher.id}">
                                        ${teacher.i}
                                    </a>
                                </td>
                                <td>
                                    <a href="${startPath}teacher?school=${schoolId}&teacher=${teacher.id}">
                                        ${teacher.o}
                                    </a>
                                </td>
                                <td>
                                    <a href="${startPath}delete-teacher?school=${schoolId}&teacher=${teacher.id}">
                                        <spring:message code="label.delete"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

            </div>
            <div id="footer">
                <jsp:include page="templates/footer.jsp"/>
            </div>
        </div>
    </body>
</html>