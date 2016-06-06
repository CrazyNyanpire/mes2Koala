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
                    <label class="col-lg-3 control-label">值:</label>
	                    <div class="col-lg-9">
                           <input name="value" style="display:inline; width:94%;" class="form-control"  type="text"  id="valueID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">标签:</label>
	                    <div class="col-lg-9">
                           <input name="label" style="display:inline; width:94%;" class="form-control"  type="text"  id="labelID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">类型:</label>
	                    <div class="col-lg-9">
                           <input name="type" style="display:inline; width:94%;" class="form-control"  type="text"  id="typeID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">描述:</label>
	                <div class="col-lg-9">
                    <textarea class="form-control" style="display:inline; width:94%;" rows="3" id="descriptionID" name="description"  ></textarea>
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                    </script>
</body>
</html>