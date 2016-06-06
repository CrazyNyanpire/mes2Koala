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
                    <label class="col-lg-3 control-label">createTimestamp:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="createTimestamp" id="createTimestampID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">lastModifyTimestamp:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="lastModifyTimestamp" id="lastModifyTimestampID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">createEmployNo:</label>
	                    <div class="col-lg-9">
                           <input name="createEmployNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="createEmployNoID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">lastModifyEmployNo:</label>
	                    <div class="col-lg-9">
                           <input name="lastModifyEmployNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="lastModifyEmployNoID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">logic:</label>
	                    <div class="col-lg-9">
                           <input name="logic" style="display:inline; width:94%;" class="form-control"  type="text"  id="logicID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">nodeName:</label>
	                    <div class="col-lg-9">
                           <input name="nodeName" style="display:inline; width:94%;" class="form-control"  type="text"  id="nodeNameID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">nodeNote:</label>
	                    <div class="col-lg-9">
                           <input name="nodeNote" style="display:inline; width:94%;" class="form-control"  type="text"  id="nodeNoteID" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                                            </script>
</body>
</html>