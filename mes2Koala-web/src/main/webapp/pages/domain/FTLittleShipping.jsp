<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<div class="form-group">
		<label class="col-lg-3 control-label">品质</label>
		<div class="col-lg-3">
			<div class="btn-group select" id="qualityID"></div>
			<input name="quality" type="hidden" id="qualityID_" dataType="Require"/> 
			<span class="required">*</span>
		</div>
		<label class="col-lg-2 control-label">数量</label>
		<div class="col-lg-4">
			<input name="qtyTotal" type="text" style="display:inline; width:85%;" class="form-control" readonly id="qtyTotalID"/> 
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">出货数量</label>
		<div class="col-lg-9">
			<input name="qty" type="text" style="display:inline; width:94%;" class="form-control" dataType="Require" id="qtyID"/> 
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">备注</label>
		<div class="col-lg-9">
			<textarea name="note" style="display:inline; width:94%;" class="form-control" id="noteID"></textarea> 
		</div>
	</div>
</form>
<script type="text/javascript">
    var selectItems = {};
    var contents = [{title:'请选择', value: ''}];
    contents.push({title : "Pass",value : "Pass"});
    contents.push({title : "Fail",value : "Fail"});
    contents.push({title : "Other",value : "Other"});
    contents.push({title : "LAT",value : "LAT"});
    selectItems['qualityID'] = contents;
</script>
</body>
</html>