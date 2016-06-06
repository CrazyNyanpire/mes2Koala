<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	
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
                    </script>
</body>
</html>