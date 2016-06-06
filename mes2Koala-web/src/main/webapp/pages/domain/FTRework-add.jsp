<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<div class="form-group">
			<label class="col-lg-3 control-label">审批结果</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="resultID"></div>
				<input name="approve" type="hidden" id="resultID_"/> 
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">备注</label>
			<div class="col-lg-9">
				<textarea class="form-control" id="remarkID" name=approveRemark></textarea>
			</div>
		</div>	
</form>
<script type="text/javascript">
var selectItems = {};
var contents = [ {title : '请选择',value : ''} ];
contents.push( {title : '同意',value : true} );
contents.push( {title : '不同意',value : false} );
selectItems['resultID']=contents;
</script>
</body>
</html>