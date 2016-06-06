<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
		
		<div class="form-group">
			<label class="col-lg-3 control-label">内部产品型号</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="internalProductID"></div>
				 
				<input name="internalProductId" type="hidden" id="internalProductID_"
					 /> 
			</div>
		</div>	
				<div class="form-group">
			<label class="col-lg-3 control-label">内箱标签</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="labelInsideID"></div>
				 
				<input name="labelInsideId" type="hidden" id="labelInsideID_"
					 /> 
			</div>
		</div>	
						<div class="form-group">
			<label class="col-lg-3 control-label">外箱标签</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="labelOutsideID"></div>
				 
				<input name="labelOutsideId" type="hidden" id="labelOutsideID_"
					 /> 
			</div>
		</div>	
						<div class="form-group">
			<label class="col-lg-3 control-label">Reel标签</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="labelReelID"></div>
				 
				<input name="labelReelId" type="hidden" id="labelReelID_"
					 /> 
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
			url : '${pageContext.request.contextPath}/InternalProduct/findAllInternalProduct.koala',
			type : 'POST',
			dataType : 'json',
		}).done(function(msg) {
			for (var i = 0; i < msg.length; i++) {
				contents.push({
					title : msg[i]['internalProductNumber'],
					value : msg[i]['id']
				});
			}
			selectItems['internalProductID'] = contents;
		});          
		
	    var contents = [ {
			title : '请选择',
			value : ''
		} ];
		$.ajax({
			async : false,
			url : '${pageContext.request.contextPath}/Label/findLabel.koala',
			type : 'POST',
			dataType : 'json',
		}).done(function(msg) {
			for (var i = 0; i < msg.length; i++) {
				contents.push({
					title : msg[i]['labelName'],
					value : msg[i]['id']
				});
			}
			selectItems['labelInsideID'] = contents;			
		});       
		
	    var contents = [ {
			title : '请选择',
			value : ''
		} ];
		$.ajax({
			async : false,
			url : '${pageContext.request.contextPath}/Label/findLabel.koala',
			type : 'POST',
			dataType : 'json',
		}).done(function(msg) {
			for (var i = 0; i < msg.length; i++) {
				contents.push({
					title : msg[i]['labelName'],
					value : msg[i]['id']
				});
			}
			selectItems['labelOutsideID'] = contents;			
		});       
		
	    var contents = [ {
			title : '请选择',
			value : ''
		} ];
		$.ajax({
			async : false,
			url : '${pageContext.request.contextPath}/Label/findLabel.koala',
			type : 'POST',
			dataType : 'json',
		}).done(function(msg) {
			for (var i = 0; i < msg.length; i++) {
				contents.push({
					title : msg[i]['labelName'],
					value : msg[i]['id']
				});
			}
			selectItems['labelReelID'] = contents;			
		});       
    </script>
</body>
</html>