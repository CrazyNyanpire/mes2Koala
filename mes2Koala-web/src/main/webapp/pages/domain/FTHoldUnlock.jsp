<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<!-- 	<div class="form-group">
        <label class="col-lg-3 control-label">QDN单号:</label>
		<div class="col-lg-9">
			<input name="QDNNumber" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="QDNNumberID" />
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
        <label class="col-lg-3 control-label">开Hold备注:</label>
		<div class="col-lg-9">
			<textarea name="HoldLockRemark" disabled style="display:inline; width:94%;" class="form-control" rows="3" type="text" dataType="Require" id="HoldLockRemarkID" ></textarea>
			<span class="required">*</span>
		</div>
	</div> -->
	<div class="form-group">
        <label class="col-lg-3 control-label">解Hold备注</label>
		<div class="col-lg-9">
			<textarea name="remark" style="display:inline; width:94%;" class="form-control" rows="3" type="text" dataType="Require" id="HoldUnLockRemarkID" ></textarea>
			<span class="required">*</span>
		</div>
	</div>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>