<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	
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
                    <label class="col-lg-3 control-label">optType:</label>
	                    <div class="col-lg-9">
                           <input name="optType" style="display:inline; width:94%;" class="form-control"  type="text"  id="optTypeID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">remark:</label>
	                    <div class="col-lg-9">
                           <input name="remark" style="display:inline; width:94%;" class="form-control"  type="text"  id="remarkID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">flownow:</label>
	                    <div class="col-lg-9">
                           <input name="flownow" style="display:inline; width:94%;" class="form-control"  type="text"  id="flownowID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">internalLot:</label>
	                    <div class="col-lg-9">
                           <input name="internalLot" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftLotID" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                                                            </script>
</body>
</html>