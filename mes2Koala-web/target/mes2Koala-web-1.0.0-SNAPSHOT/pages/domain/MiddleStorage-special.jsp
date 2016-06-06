<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    		<div class="form-group">
			<label class="col-lg-3 control-label">特殊标示:</label>
			<div class="col-lg-9">
				<div class="btn-group select" id="specialSignID"></div>
				<input name="specialSign" type="hidden" id="specialSignID_"/> 
			</div>
		</div>	
		           <div class="form-group">
                    <label class="col-lg-3 control-label">详细信息:</label>
	                    <div class="col-lg-9">
                           <textarea name="specialSignRemark" style="display:inline; width:94%;" class="form-control"  type="text" id="specialSignRemarkID"></textarea>
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
    var contents = [ {title : '请选择',value : ''} ];
    contents.push({title :'取消',value : '取消'});
    contents.push({title :'报废',value : '报废'});
    contents.push({title :'制定出货',value : '制定出货'});
    contents.push({title :'降级',value : '降级'});
    selectItems['specialSignID'] = contents;
</script>
</body>
</html>