<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form class="form-horizontal">
		<div class="form-group">
			<label class="col-lg-3 control-label">测试类型:</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="testTypeID"></div>
				<input name="testType" type="hidden" id="testTypeID_" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">封装类型:</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="packageTypeID"></div>
				<input name="packageType" type="hidden" id="packageTypeID_" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">保税类型:</label>

			<div class="col-lg-9">
				<div class="btn-group select" id="taxTypeID"></div>
				<input name="taxType" type="hidden" id="taxTypeID_" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">标签类型:</label>

			<div class="col-lg-9">
				<div class="btn-group select" id="labelTypeID"></div>
				<input name="labelType" type="hidden" id="labelTypeID_" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-3 control-label">标签名称:</label>
			<div class="col-lg-6">
				<input name="labelUpload" class="form-control" type="file" id="labelUpload" dataType="Require" />
				<span class="required">*</span>
				<input style='display:none;' type='text' name='labelFullName' id="labelFullNameID"/>
			</div>
		</div>
	</form>
	<script type="text/javascript">

	</script>
</body>
</html>