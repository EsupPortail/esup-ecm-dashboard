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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"/></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css" />

<script>
	function open_in_new_tab(id) {
		var url = "http://enor.univ-rennes1.fr:8080/nuxeo/nxdoc/default/" + id + "/view_documents";
		var win = window.open(url, '_blank');
		win.focus(); 
	}
</script>