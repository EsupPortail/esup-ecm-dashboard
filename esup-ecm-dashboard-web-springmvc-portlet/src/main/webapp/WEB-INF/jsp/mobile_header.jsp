<%@ include file="/WEB-INF/jsp/include.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.mobile-1.3.1.min.css" />
<c:if test="${not isuPortal}">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"/></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.mobile-1.3.1.min.js"/></script>
	<div data-role="page">
</c:if>
<div data-role="content"><!-- content/ -->

	<div class="ui-body-d ui-body" ><!-- container/ -->
    