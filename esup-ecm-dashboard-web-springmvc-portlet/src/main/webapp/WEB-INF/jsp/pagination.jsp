<p class="text-center">
	<div class="pagination pagination-mini pagination-centered">
		<ul>
			<c:choose>
				<c:when test="${pageIndex > 0}">
				<li><a href="<portlet:renderURL>
                            <portlet:param name="pageIndex" value="${pageIndex-1}" />
                        </portlet:renderURL>">Prev</a></li>
				</c:when>
				<c:otherwise>
					<li  class="disabled"><a href="#">Prev</a></li>
				</c:otherwise>
			</c:choose>
			
			<c:forEach begin="0" end="${pageCount-1}" var="i">
				<c:choose>
				<c:when test="${pageIndex != i}">
					<li><a href="<portlet:renderURL>
							<c:if test="${not empty action}">
							<portlet:param name="action" value="list" />
							</c:if>
							<portlet:param name="intranetPath" value="${intranetPath}" />
                            <portlet:param name="pageIndex" value="${i}" />
                        </portlet:renderURL>">${i+1}</a></li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><a href="#">${i+1}</a></li>
				</c:otherwise>
				</c:choose>
			</c:forEach>
			
			<c:choose>
				<c:when test="${pageIndex+1 < pageCount}">
					<li><a href="<portlet:renderURL>
                            <portlet:param name="pageIndex" value="${pageIndex+1}" />
                        </portlet:renderURL>">Next</a></li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><a href="#">Next</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</p>