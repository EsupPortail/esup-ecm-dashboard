<%@ include file="/WEB-INF/jsp/include.jsp"%>
<form name="editForm" method="post" action="${editPreperencesUrl}" >

	<table style="width: 100%">
	  <tr>
	    <th colspan="2"><legend>Preferences Form</legend></th>
	  </tr>
	  <tr>
	    <td><label>nuxeoHost</label></td>
	    <td><input type="url" name="nuxeoHost" value="${nuxeoHost}" class="input-xxlarge" required/></td>
	  </tr>
	  <tr>
	    <td><label>NXQL</label></td>
	    <td><textarea rows="5" name="NXQL" class="input-xxlarge" required>${NXQL}</textarea>
        <p class="text-info"><small>ex:) SELECT * FROM Document WHERE ecm:path STARTSWITH '/default-domain/workspaces/' ORDER BY dc:modified DESC</small></p>
	    
	    </td>
	  </tr>
	  <tr>
	    <td><label>Max Page Size</label></td>
	    <td><select id="maxPageSize" name="maxPageSize" class="span1">
			<option>5</option>
          	<option>10</option>
          	<option>15</option>
          	<option>20</option>
          	<option>25</option>
          	<option>30</option>
      	</select></td>
	  </tr>
	  <tr>
	    <td><label>columns</label></td>
	    <td><input type="text" name="columns" value='${columns}' class="input-xxlarge" style="width: 533px" required/>
	    	<p class="text-info"><small>ex:)["dc:title", "dc:modified", "dc:creator", "dc:description"]</small></p>
		</td>
	  </tr>
	  <tr>
	    <td colspan="2"><center><button type="submit" class="btn btn-small btn-success" >Submit</button></center></td>
	  </tr>
	</table>
</form>

<script>
    var val = '${maxPageSize}';
    $("#maxPageSize").val(val);
</script>