<%@ include file="/WEB-INF/jsp/mobile_header.jsp"%>
<table class="table table-striped table-hover table-condensed">
    <tr>
    	<th></th>
    	<c:forEach var="column" items="${columns}">
        	<th><spring:message code="dc.${column}" /></th>
        </c:forEach>
    </tr>
    <tbody>
        <c:if test="${not empty docs}">
        	
	        <c:forEach items="${docs}" var="doc" >
	            <tr>
	            	<td width="30"><img src="<%=request.getContextPath()%>/img/${esup:getImgFileName(doc.properties)}" ></td> 
	            	<c:forEach items="${columns}" var="column" >
	            	
	            		<c:choose>
	                        <c:when test="${column == 'title'}">
	                        	<td><a href="${nuxeoHost}/nxdoc/default/${doc.id}/view_documents" rel="external">${doc.title}</a></td>
	                        </c:when>
	                        <c:otherwise>
	                        	<td>${esup:getDublinCoreProperty(doc.properties, column)}</td>
	                        </c:otherwise>
                        </c:choose>
			        	
			        </c:forEach>
	            </tr>
	        </c:forEach>
        </c:if>
    </tbody>
    <tr>
    	<td colspan="5"><%@ include file="/WEB-INF/jsp/pagination.jsp"%></td>
	</tr>
</table>
<%@ include file="/WEB-INF/jsp/mobile_footer.jsp"%>