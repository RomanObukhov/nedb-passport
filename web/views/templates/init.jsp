<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    ServletContext servletContext = pageContext.getServletContext();
    servletContext.setAttribute("startPath", pageContext.getServletContext().getContextPath() + "/");
    servletContext.setAttribute("locale", LocaleContextHolder.getLocale());
%>