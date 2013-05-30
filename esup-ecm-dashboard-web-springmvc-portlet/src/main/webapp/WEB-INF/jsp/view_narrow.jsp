<%@ include file="/WEB-INF/jsp/include.jsp"%>
<p class="lead">
<a href="${homeUrl}" class="btn btn-small disabled"><spring:message code="menu.home" /></a>
</p>

<table class="table table-hover">
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
	            	<td><img src="<%=request.getContextPath()%>/img/${doc.docMap['icon']}" ></td>
	            	<c:forEach items="${columns}" var="column" >
	            	
	            		<c:choose>
	                        <c:when test="${column == 'title'}">
	                        	<td>
		                        	<c:choose>
				                        <c:when test="${doc.docMap['type'] == 'File' || doc.docMap['type'] == 'Picture'}">
				                            <a href="<portlet:resourceURL >
				                                    <portlet:param name="action" value="file" />
				                                    <portlet:param name="fid" value="${doc.docMap['id']}" />
				                                </portlet:resourceURL>">${doc.docMap['title']}</a>
				                        </c:when>
				                        <c:otherwise>
				                            <a href="<portlet:renderURL>
				                                    <portlet:param name="action" value="list" />
				                                    <portlet:param name="intranetPath" value="${doc.docMap['path']}" />
				                                </portlet:renderURL>">${doc.docMap['title']}</a>
				                        </c:otherwise>
				                    </c:choose>
	                        	</td>
	                        </c:when>
	                        <c:otherwise>
	                        	<td>${doc.docMap[column]}</td>
	                        </c:otherwise>
                        </c:choose>
			        	
			        </c:forEach>
	            </tr>
	        </c:forEach>
        </c:if>
    </tbody>
</table>