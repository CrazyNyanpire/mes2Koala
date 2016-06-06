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
                    <label class="col-lg-3 control-label">interval:</label>
	                    <div class="col-lg-9">
                           <input name="interval" style="display:inline; width:94%;" class="form-control"  type="text"  id="intervalID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">maxNum:</label>
	                    <div class="col-lg-9">
                           <input name="maxNum" style="display:inline; width:94%;" class="form-control"  type="text"  id="maxNumID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">serialNum:</label>
	                    <div class="col-lg-9">
                           <input name="serialNum" style="display:inline; width:94%;" class="form-control"  type="text"  id="serialNumID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">startStr:</label>
	                    <div class="col-lg-9">
                           <input name="startStr" style="display:inline; width:94%;" class="form-control"  type="text"  id="startStrID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">endStr:</label>
	                    <div class="col-lg-9">
                           <input name="endStr" style="display:inline; width:94%;" class="form-control"  type="text"  id="endStrID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">type:</label>
	                    <div class="col-lg-9">
                           <input name="type" style="display:inline; width:94%;" class="form-control"  type="text"  id="typeID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">isZero:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="isZeroID"></div>
	                       <input type="hidden" id="isZeroID_" name="isZero" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">isDayClean:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="isDayCleanID"></div>
	                       <input type="hidden" id="isDayCleanID_" name="isDayClean" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                                                                                    selectItems['isZeroID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
                selectItems['isDayCleanID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
        </script>
</body>
</html>