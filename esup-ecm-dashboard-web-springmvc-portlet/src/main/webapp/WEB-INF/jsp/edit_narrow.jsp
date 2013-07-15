<%@ include file="/WEB-INF/jsp/include.jsp"%>
<form name="editForm" id="editForm1" method="post" action="${editPreperencesUrl}" >

    <table style="width: 100%">
      <tr>
        <th colspan="2"><legend>Preferences Form</legend></th>
      </tr>
      <c:if test="${not nuxeoHost_readOnly}">
      <tr>
        <td><label>nuxeoHost</label></td>
        <td><input type="url" name="nuxeoHost" value="${nuxeoHost}" class="input-xxlarge" required /></td>
      </tr>
      </c:if>
      <c:if test="${not NXQL_readOnly}">
      <tr>
        <td><label>NXQL</label></td>
        <td><textarea rows="5" name="NXQL" class="input-xxlarge" required >${NXQL}</textarea>
        <p class="text-info"><small>ex:) SELECT * FROM Document WHERE ecm:path STARTSWITH 
                '/default-domain/workspaces/' ORDER BY dc:modified DESC</small></p></td>
      </tr>
      </c:if>
      <c:if test="${not maxPageSize_readOnly}">
      <tr>
        <td><label>Max Page Size</label></td>
        <td><select id="maxPageSize" name="maxPageSize" class="span1" required>
            <option>5</option>
            <option>10</option>
            <option>15</option>
            <option>20</option>
            <option>25</option>
            <option>30</option>
        </select></td>
      </tr>
      </c:if>
      <c:if test="${not columns_readOnly}">
      <tr>
        <td><label>columns</label>
        	<br/><button class="btn btn-mini btn-primary" type="button" onclick="javascript:add()"><spring:message code="button.add" /></button>
        </td>
        <td>
        <span id="preferences">
        <c:forEach items="${columns}" var="column" varStatus="row">
        	<input type="text" name="columns" class="input-xxlarge" value='${column}' id="prefernceInput${row.index}"/>
        	<button class="btn btn-mini btn-primary" type="button" 
        		onclick="javascript:removePref('${row.index}')" id="prefernceButton${row.index}">
        		<spring:message code="button.remove" /></button><br/>
        </c:forEach>
        </span>
        <p class="text-info"><small>ex:) dc:title, dc:modified, dc:creator, dc:description</small></p>
        </td>
      </tr>
      <tr>
        <td colspan="2"><center><button type="submit" class="btn btn-small btn-success" >Submit</button></center></td>
      </tr>
      </c:if>
    </table>
</form>

<script>
    var val = '${maxPageSize}';
    $("#maxPageSize").val(val);
    
    var index = ${fn:length(columns)};
    
    function add() {
    	
    	var preferences = document.getElementById("preferences");
    	var removeMsg = '<spring:message code="button.remove" />';
    	 
        //Create an input type dynamically.
        var inputE = document.createElement("input");     
        //Assign different attributes to the element.
        inputE.setAttribute("id", "prefernceInput"+index);
        inputE.setAttribute("type", "text");
        inputE.setAttribute("name", "columns");
        inputE.setAttribute("class", "input-xxlarge");
        
        var buttonE = document.createElement("button");
        buttonE.setAttribute("id", "prefernceButton"+index);
        buttonE.setAttribute("class", "btn btn-mini btn-primary");
        buttonE.setAttribute("type", "button");
        buttonE.setAttribute("onclick", "javascript:removePref('"+index+"')");
        buttonE.appendChild(document.createTextNode(removeMsg));
     
        //Append the element in page (in span).
        preferences.appendChild(inputE);
        preferences.appendChild(buttonE);
        
        index++;
    }
    
    function removePref(i){
    	var preferences = document.getElementById("preferences");
    	var prefernceInput = document.getElementById("prefernceInput"+i);
    	var prefernceButton = document.getElementById("prefernceButton"+i);
    	
    	preferences.removeChild(prefernceInput);
    	preferences.removeChild(prefernceButton);
    }
    
</script>