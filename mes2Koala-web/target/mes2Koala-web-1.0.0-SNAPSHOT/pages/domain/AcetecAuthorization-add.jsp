<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
		<div class="form-group">
			<label class="col-lg-3 control-label">授权人:</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="employeeID"></div>
				<input name="employeeId" type="hidden" id="employeeID_"
					 /> 
			</div>
		</div>
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">意见:</label>
	                    <div class="col-lg-9">
                           <input name="opinion" style="display:inline; width:94%;" class="form-control"  type="text"  id="opinionID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">备注:</label>
	                    <div class="col-lg-9">
                           <input name="note" style="display:inline; width:94%;" class="form-control"  type="text"  id="noteID" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
    
		var contents = [ {
			title : '请选择',
			value : ''
		} ];
		$.ajax({
			async : false,
			url : '${pageContext.request.contextPath}/employee/getEmployee.koala',
			type : 'POST',
			dataType : 'json',
		}).done(function(msg) {
			for (var i = 0; i < msg.length; i++) {
				contents.push({
					title : msg[i]['name']+" | "+msg[i]['organizationName'],
					value : msg[i]['id']
				});
			}
			selectItems['employeeID'] = contents;
			console.log(contents);
		});
                    </script>
</body>
</html>