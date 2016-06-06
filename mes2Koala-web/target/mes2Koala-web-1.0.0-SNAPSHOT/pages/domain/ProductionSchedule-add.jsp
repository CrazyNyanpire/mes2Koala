<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<ul class="nav nav-tabs" id="testTab">
		<li class="active"><a data-toggle="tab" href="#batchTest">分批测试</a></li>
		<li><a data-toggle="tab" href="#stopTest">暂停测试</a></li>
		<li><a data-toggle="tab" href="#stateChange">状态修改</a></li>
	</ul>

	<div class="tab-content" id="testTabContent" style="margin-top: 10px;">
		<div id="batchTest" class="tab-pane fade active in">
			<form class="form-horizontal">
				<div class="form-group">
					<label class="col-lg-2 control-label">原批次:</label>
					<div class="col-lg-4">
						<input  class="form-control"  type="text" id="lotNumber"/>
					</div>
					<label class="col-lg-2 control-label">原机台:</label>
					<div class="col-lg-4">
						<input  class="form-control"  type="text"  id="testSysName"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label">上限数量:</label>
					<div class="col-lg-4">
						<input  class="form-control"  type="text" id="amount"/>
					</div>
					<label class="col-lg-2 control-label">分批批号:</label>
					<div class="col-lg-4">
						<input class="form-control"  type="text"  name="newLotNumber"/>
					</div>
				</div>
				<hr />
				<div class="form-group">
					<label class="col-lg-2 control-label">百分比:</label>
					<div class="col-lg-4">
						<input  class="form-control col-lg-3"  type="text" name="percent" placeholder="0-100" id="percentID" style="width:80%;"/>
						<span class="control-label col-lg-1">%</span>
					</div>
					<label class="col-lg-2 control-label">目标机台:</label>
					<div class="col-lg-4">
						<div class="btn-group select" id="targetTestSysNameID"></div>
						<input name="targetTestSysId" type="hidden" id="targetTestSysNameID_"
						/>
					</div>
				</div>
				<br />
				<input type="button" value="提交" class="btn btn-primary" id="separateSubmit" style="float:right;">
			</form>
		</div>

		<div id="stopTest" class="tab-pane fade">
			<form class="form-horizontal">
				<div class="form-group">
					<label class="col-lg-2 control-label">批次:</label>
					<div class="col-lg-4">
						<input  class="form-control"  type= "text"  id="lotNumberID"/>
					</div>
					<label class="col-lg-2 control-label">机台：</label>
<!-- 					<div class="col-lg-4">
						<div class="btn-group select" id="testSysNameID"></div>
						<input name="testSysId" type="hidden" id="testSysNameID_"
						/>
					</div>
 -->	
 					<div class="col-lg-4">
						<input  class="form-control"  type="text"  id="testSysName"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label">数量:</label>
					<div class="col-lg-4">
						<input  class="form-control"  type="text"  id="amountID"/>
					</div>
					<label class="col-lg-2 control-label">已测数量:</label>
					<div class="col-lg-4">
						<input class="form-control"  type="text"   id="doneQtyID"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label">开始测试时间:</label>
					<div class="col-lg-4">
						<input  class="form-control"  type="text"  id="actualStartTimestampID" />
					</div>
					<label class="col-lg-2 control-label">暂停测试时间:</label>
					<div class="col-lg-4">
						<input class="form-control"  type="text" id="stopTimestampID" />
					</div>
				</div>
				<br />
				<input type="button" value="提交" id="stopSubmit" class="btn btn-primary" style="float:right;">
			</form>
		</div>


		<div id="stateChange" class="tab-pane fade">
			<form class="form-horizontal">
				<input type="hidden" id="idID" name="id" />
				<input type="hidden" id="versionID" name="version" />
				<div class="form-group">
					<label class="col-lg-2 control-label">状态:</label>
					<div class="col-lg-4">
						<div class="btn-group select" id="stateID"></div>
						<input name="state" type="hidden" id="stateID_"
						/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label">备注:</label>
					<textarea rows="10" cols="50" name="note" id="noteID"></textarea>
				</div>
				<br />
				<input type="button" value="提交" id="stateChangeSubmit" class="btn btn-primary" style="float:right;">
			</form>
		</div>
	</div>
	<script type="text/javascript">
		var selectItems = {};
		var contents = [{title: '请选择', value: ''}];//添加状态字典表维护
		$.ajax({
			url: '${pageContext.request.contextPath}/SystemDictionary/getByType/productionScheduleState.koala',
			type: 'GET',
			dataType: 'json'
		}).done(function (msg) {
			for (var i = 0; i < msg.length; i++) {
				contents.push({
					title: msg[i]['label'],
					value: msg[i]['value']
				});
			}
			selectItems['stateID'] = contents;
			/*dialog.find('#stateID').select({
				title: '请选择',
				contents: contents
			}).on('change', function () {
				dialog.find('#stateID_').val($(this).getValue());
			});*/
		});
	</script>
</body>
</html>