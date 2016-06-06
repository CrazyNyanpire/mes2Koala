<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id"/>
	<input type="hidden" id="versionID" name="version"/>
	<input type="hidden" id="createEmployNoID" name="createEmployNo"/>
	<div class="form-group">
		<label class="col-lg-3 control-label">Process名称:</label>
		<div class="col-lg-6">
			<input name="name" style="display:inline; width:94%;" datatype="Require" class="form-control"  type="text"  id="nameID" />
		    <span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">机械手类型:</label>

		<div class="col-lg-6">
			<div class="btn-group select" id="handlerTypeID"></div>
			<input type="hidden" id="handlerTypeID_" name="handlerType" dataType="Require"/>
			<span class="required">*</span>
			<button type="button" title="增加机械手类型" id="modalOpen"><span class="glyphicon glyphicon-plus"></span></button>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">测试类型:</label>

		<div class="col-lg-6">
			<div class="btn-group select" id="testTypeID"></div>
			<input type="hidden" id="testTypeID_" name="testType" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	<input name="testIds" type="hidden" id="testingID"/>
	<div class="form-group">
		<label class="col-lg-3 control-label">流程明细:</label>
		<div class="col-lg-9">
			<table border="0" width="350">
				<tr>
					<td width="30%">
						<select style="WIDTH:100%"  multiple size="11" id="list1"></select>
					</td>
					<td width="20%" align="center">
						<button class='btn btn-primary' type='button' id="add"><span class="glyphicon glyphicon-chevron-right"></span></button><br/><br/><br/>
						<button class='btn btn-danger' type='button' id="delete"><span class="glyphicon glyphicon-chevron-left"></span></button>
					</td>
					<td width="30%">
						<select style="WIDTH:100%" multiple size="11"  id="list2"></select>
					</td>

					<td width="20%" align="center" >
						<button class='btn btn-primary' type='button' id="up"><span class="glyphicon glyphicon-chevron-up"></span></button><br/><br/><br/>
						<button class='btn btn-primary' type='button' id="down"><span class="glyphicon glyphicon-chevron-down"></span></button>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
<!-- Modal -->
<div class="modal fade" id="myModal" data-backdrop="false" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id='modalClose' aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">新增机械手类型</h4>
			</div>
			<div class="modal-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="col-lg-3 control-label">值:</label>
						<div class="col-lg-9">
							<input name="value" style="display:inline; width:94%;" class="form-control"  type="text"  id="valueID" datatype="Require"  />
							<span class="required">*</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">标签:</label>
						<div class="col-lg-9">
							<input name="label" style="display:inline; width:94%;" class="form-control"  type="text"  id="labelID" datatype="Require" />
							<span class="required">*</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">类型:</label>
						<div class="col-lg-9">
							<input name="type" style="display:inline; width:94%;" class="form-control"  type="text" value="HandlerType" id="typeID" disabled/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">描述:</label>
						<div class="col-lg-9">
							<textarea class="form-control" style="display:inline; width:94%;" rows="3" id="descriptionID" name="description"  disabled>机械手类型</textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="modalCancel">取消</button>
				<button type="button" class="btn btn-success" id="modalSave">保存</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
</script>
</body>
</html>