<%@ include file="/WEB-INF/jsp/include.jsp"%>
<table class="table table-striped table-hover table-condensed">
    <tr>
    	<th></th>
    	<c:forEach var="column" items="${columns}">
        	<th><spring:message code="${fn:replace(column, ':', '.')}" text="${column}"/></th>
        </c:forEach>
    </tr>
    <tbody>
        <c:if test="${not empty docs}">
	        <c:forEach items="${docs}" var="doc" >
	            <tr onclick="javascript:open_in_new_tab('${doc.id}')" style="cursor:pointer;">
	            	<td width="30"><img src="<%=request.getContextPath()%>/img/${esup:getImgFileName(doc.properties)}" ></td> 
	            	<c:forEach items="${columns}" var="column" >
	            		<td>${esup:getColumnValue(doc.properties, column)}</td>
			        </c:forEach>
	            </tr>
	        </c:forEach>
        </c:if>
    </tbody>
    <tr>
    	<td colspan="5"><%@ include file="/WEB-INF/jsp/pagination.jsp"%></td>
	</tr>
</table>
<script>
	function open_in_new_tab(id) {
		var url = "${nuxeoHost}/nxdoc/default/" + id + "/view_documents";
		var win = window.open(url, '_blank');
		win.focus(); 
	}
</script>