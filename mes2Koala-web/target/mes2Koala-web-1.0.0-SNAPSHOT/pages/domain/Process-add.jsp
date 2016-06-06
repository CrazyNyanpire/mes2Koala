<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    		<div class="form-group">
				<label class="col-lg-3 control-label">产品型号</label>
				<div class="col-lg-9">
					<div class="btn-group select" id="processTemplateID"></div>
					 
					<input name="processTemplateDTO.id" type="hidden" id="processTemplateID_"
						 /> 
				</div>
			</div>	
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">name:</label>
	                    <div class="col-lg-9">
                           <input name="name" style="display:inline; width:94%;" class="form-control"  type="text"  id="nameID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">content:</label>
	                    <div class="col-lg-9">
                           <input name="content" style="display:inline; width:94%;" class="form-control"  type="text"  id="contentID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">testType:</label>
	                    <div class="col-lg-9">
                           <input name="testType" style="display:inline; width:94%;" class="form-control"  type="text"  id="testTypeID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">processState:</label>
	                    <div class="col-lg-9">
                           <input name="processState" style="display:inline; width:94%;" class="form-control"  type="text"  id="processStateID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">runCard:</label>
	                    <div class="col-lg-9">
                           <input name="runCard" style="display:inline; width:94%;" class="form-control"  type="text"  id="runCardID" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
    var data={page:0,pagesize:1000};
    var contents = [ {
		title : '请选择',
		value : ''
	} ];
	$.ajax({
		async : false,
		url : '${pageContext.request.contextPath}/ProcessTemplate/pageJson.koala',
		data: data,
		type : 'POST',
		dataType : 'json',
	}).done(function(msg) {
		for (var i = 0; i < msg.data.length; i++) {
			contents.push({
				title : msg.data[i]['name'],
				value : msg.data[i]['id']
			});
		}
		selectItems['processTemplateID'] = contents;
	});  
	
                                            </script>
</body>
</html>