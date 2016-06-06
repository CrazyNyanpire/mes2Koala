<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<div class="form-group">
		<label class="col-lg-3 control-label">目标站点:</label>
		<div class="col-lg-3">
			<div class="btn-group select" id="targetNodeID"></div>
			<input name="targetNode" type="hidden" id="targetNodeID_" dataType="Require"/> 
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">备注:</label>
		<div class="col-lg-9">
			<textarea name="note" rows="3" style="display:inline; width:94%;" class="form-control" id="noteID"></textarea> 
		</div>
	</div>
</form>
<script type="text/javascript">
var selectItems = {};
</script>
</body>
</html>