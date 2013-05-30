<%@ include file="/WEB-INF/jsp/include.jsp"%>

<form:form  name="editForm" method="post" action="${editPreperencesUrl}" >

	<fieldset>
		<legend>Preferences Form</legend>
		<br/>
		<label>nuxeoHost</label>
		<input type="url" name="nuxeoHost" value="${nuxeoHost}" class="input-xxlarge" required/>
		<p>
		  <small>cf : http://doc.nuxeo.com/display/NXDOC/NXQL</small>
		</p>
		<br/>
		<label>NXQL</label>
		<textarea rows="5" name="NXQL" class="input-xxlarge" required>${NXQL}</textarea>
		<br/>
		<label>maxPageSize</label>
		<select id="maxPageSize" name="maxPageSize" class="span1">
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

</form:form>

<script>
    var val = '${maxPageSize}';
    $('#maxPageSize option:contains(' + val + ')').prop({selected: true});
</script>