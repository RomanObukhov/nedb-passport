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
                    <h2><a href="${startPath}" style="color: #FFFFFF"><spring:message code="home.title"/></a></h2>
                </div>
                <div class="box round">
                    <c:if test="${not empty school}">
                        <a href="${startPath}passport?school=${school.id}">${school.getLocalizedName()} -</a> 
                        <a href="${startPath}teacher-list?school=${school.id}"><spring:message code="label.stuff"/></a>
                    </c:if>
                </div>

                <div class="panel">
                    <h2><spring:message code="label.editing"/></h2>
                </div>
                <div class="box round">
                    <form action="${startPath}save-teacher" method="post" enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="school" value="${schoolId}"/>
                        <input type="hidden" name="teacher" value="${teacherId}"/>
                        <input type="submit" value="<spring:message code="label.save"/>"/>
                        <br/>
                        <table id="enter">
                            <tr>
                                <td><spring:message code="label.iin"/></td>
                                <td><input type="text" disabled value="${teacher.iin}"/></td>
                            </tr>
                            <tr>
                                <td><spring:message code="label.last-name"/></td>
                                <td><input type="text" name="teacher-f" value="${teacher.f}"/></td>
                            </tr>
                            <tr>
                                <td><spring:message code="label.first-name"/></td>
                                <td><input type="text" name="teacher-i" value="${teacher.i}"/></td>
                            </tr>
                            <tr>
                                <td><spring:message code="label.middle-name"/></td>
                                <td><input type="text" name="teacher-o" value="${teacher.o}"/></td>
                            </tr>
                            <c:if test="${not empty passportElements}">
                                <c:forEach items="${passportElements}" var="passportElement">
                                    <tr>
                                        ${passportElement.toFormField()}
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </table>
                        <input type="submit" value="<spring:message code="label.save"/>"/>
                    </form>
                </div>

            </div> <!--   content     -->
            <div id="footer">
                <jsp:include page="templates/footer.jsp"/>
            </div>
        </div> <!--   wspace     -->       
    </body>    
    <!--<body class="default-component">
        <table class="ui-widget ui-widget-content ui-state-default ui-corner-all" align="center">

            <tr>
                <td>


                </td>
            </tr>

        </table>
    </body>-->
</html>