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
                    <label class="col-lg-3 control-label">reworkRCNo:</label>
	                    <div class="col-lg-9">
                           <input name="reworkRCNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="reworkRCNoID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reworkCustomer:</label>
	                    <div class="col-lg-9">
                           <input name="reworkCustomer" style="display:inline; width:94%;" class="form-control"  type="text"  id="reworkCustomerID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reworkDate:</label>
	                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="reworkDate" id="reworkDateID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reworkDepartment:</label>
	                    <div class="col-lg-9">
                           <input name="reworkDepartment" style="display:inline; width:94%;" class="form-control"  type="text"  id="reworkDepartmentID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reworkEquipment:</label>
	                    <div class="col-lg-9">
                           <input name="reworkEquipment" style="display:inline; width:94%;" class="form-control"  type="text"  id="reworkEquipmentID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reworkLot:</label>
	                    <div class="col-lg-9">
                           <input name="reworkLot" style="display:inline; width:94%;" class="form-control"  type="text"  id="reworkLotID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reworkNo:</label>
	                    <div class="col-lg-9">
                           <input name="reworkNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="reworkNoID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reworkReworkQty:</label>
	                    <div class="col-lg-9">
                           <input name="reworkReworkQty" style="display:inline; width:94%;" class="form-control"  type="text"  id="reworkReworkQtyID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reworkTotalQty:</label>
	                    <div class="col-lg-9">
                           <input name="reworkTotalQty" style="display:inline; width:94%;" class="form-control"  type="text"  id="reworkTotalQtyID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reasonExplanation:</label>
	                    <div class="col-lg-9">
                           <input name="reasonExplanation" style="display:inline; width:94%;" class="form-control"  type="text"  id="reasonExplanationID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reasonOther:</label>
	                    <div class="col-lg-9">
                           <input name="reasonOther" style="display:inline; width:94%;" class="form-control"  type="text"  id="reasonOtherID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">reasonReasons:</label>
	                    <div class="col-lg-9">
                           <input name="reasonReasons" style="display:inline; width:94%;" class="form-control"  type="text"  id="reasonReasonsID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">summary:</label>
	                    <div class="col-lg-9">
                           <input name="summary" style="display:inline; width:94%;" class="form-control"  type="text"  id="summaryID" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
                                                                                                                                                    </script>
</body>
</html>