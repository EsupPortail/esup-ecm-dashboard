<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="esup" uri="/WEB-INF/esup-ecm-dashboard-taglib.tld" %>

<portlet:defineObjects/>
<c:set var="n"><portlet:namespace/></c:set>

<portlet:actionURL var="editPreperencesUrl">
    <portlet:param name="action" value="edit" />
</portlet:actionURL>

<portlet:renderURL var="homeUrl" portletMode="VIEW"/>
<portlet:renderURL var="editPreferencesUrl" portletMode="EDIT"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"/></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css" />