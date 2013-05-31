<%@ include file="/WEB-INF/jsp/include.jsp"%>

<form name="editForm" method="post" action="${editPreperencesUrl}" >

	<fieldset>
		<legend>Preferences Form</legend>
		<br/>
		<label>nuxeoHost</label>
		<input type="url" name="nuxeoHost" value="${nuxeoHost}" class="input-xxlarge" required/>
		<br/>
		<label>NXQL</label>
		<textarea rows="5" name="NXQL" class="input-xxlarge" required>${NXQL}</textarea>
		<br/>
		<label>maxPageSize</label>
		<select id="maxPageSize" name="maxPageSize" class="span1">
			<option>1</option>
			<option>2</option>
			<option>5</option>
          	<option>10</option>
          	<option>15</option>
          	<option>20</option>
          	<option>25</option>
          	<option>30</option>
      	</select>
		<br/>
		<label>columns</label>
		<input type="text" name="columns" value='${columns}' class="input-xxlarge"  required/>
		<br/>
		
	</fieldset>
	<center><div class="form-actions"><button type="submit" class="btn btn-small btn-success" >Summit</button></div>	</center>

</form>

<script>
    var val = '${maxPageSize}';
    $('#maxPageSize option:contains(' + val + ')').prop({selected: true});
</script>