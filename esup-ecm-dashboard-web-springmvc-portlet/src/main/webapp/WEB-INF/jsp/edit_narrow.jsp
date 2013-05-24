<%@ include file="/WEB-INF/jsp/include.jsp"%>

<form:form class="form-horizontal" name="editForm" method="post" action="${editPreperencesUrl}" >

<div class="row">
  <div class="span2 offset0">nuxeoHost :</div>
  <div class="span2 offset1"><input type="url" name="nuxeoHost" value="${nuxeoHost}" placeholder="Text input"/></div>
</div>

<div class="row">
  <div class="span2 offset0">NXQL :</div>
  <div class="span2 offset1"><textarea rows="3" name="NXQL" >${NXQL}</textarea></div>
</div>

<div class="row">
  <div class="span2 offset0">maxPageSize :</div>
  <div class="span2 offset1">
    <select id="maxPageSize" name="maxPageSize">
          <option>10</option>
          <option>15</option>
          <option>20</option>
          <option>25</option>
          <option>30</option>
      </select></div>
</div>

<div class="control-group">
<div class="row">
  <div class="span2 offset0">columns :</div>
  <div class="span2 offset1"><input type="text" name="columns" value="${columns}" /></div>
</div>
</div>

<button type="submit" class="btn btn-small">Summit</button>
</form:form>

<script>
    var val = '${maxPageSize}';
    $('#maxPageSize option:contains(' + val + ')').prop({selected: true});
</script>