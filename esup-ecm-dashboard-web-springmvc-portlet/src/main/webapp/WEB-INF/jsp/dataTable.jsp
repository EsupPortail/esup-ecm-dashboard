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
	                        	<td>
		                        	<c:choose>
				                        <c:when test="${doc.type == 'File' || doc.type == 'Picture'}">
				                            <a href="<portlet:resourceURL >
				                                    <portlet:param name="action" value="file" />
				                                    <portlet:param name="fid" value="${doc.id}" />
				                                </portlet:resourceURL>">${doc.title}</a>
				                        </c:when>
				                        <c:otherwise>
				                            <a href="<portlet:renderURL>
				                                    <portlet:param name="action" value="list" />
				                                    <portlet:param name="intranetPath" value="${doc.path}" />
				                                </portlet:renderURL>">${doc.title}</a>
				                        </c:otherwise>
				                    </c:choose>
	                        	</td>
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