<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<form class="form-horizontal">
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
						<select style="WIDTH:100%"  multiple  size="11" id="list1"></select>
				    </td>
					<td width="20%" align="center">
					<button class='btn btn-primary' title='左移' type='button' id="add"><span class="glyphicon glyphicon-chevron-right"></span></button><br/><br/><br/>
					<button class='btn btn-danger' title='右移' type='button' id="delete"><span class="glyphicon glyphicon-chevron-left"></span></button>
					</td>
					<td width="30%">
						<select style="WIDTH:100%" multiple size="11"  id="list2"></select>
					</td>

					<td width="20%" align="center" >
						<button class='btn btn-primary' title='上移' type='button' id="up"><span class="glyphicon glyphicon-chevron-up"></span></button><br/><br/><br/>
						<button class='btn btn-primary' title='下移' type='button' id="down"><span class="glyphicon glyphicon-chevron-down"></span></button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	</form>
<script type="text/javascript">
</script>
</body>
</html>