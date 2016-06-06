<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<object class="hidden" id="activeX" classid="clsid:8738EBCB-92B1-4dcb-8E86-A4703EBD191E"> </object>
<form class="form-horizontal row">
<input name="labelName" type="text" class="hidden" id="labelNameID" />
	<div class="col-lg-12">
		<div class="form-group">
	        <label class="col-lg-2 control-label">出货型号:</label>
			<div class="col-lg-4">
				<input name="productNo" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="productNoID" />
			</div>
			<label class="col-lg-2 control-label">PPO:</label>
			<div class="col-lg-4">
				<input name="incomingPPO" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="ppoID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-2 control-label">CUS NO:</label>
			<div class="col-lg-10">
				<input name="customerNo" style="display:inline; width:98%;" class="form-control"  type="text" dataType="Require" id="customerNoID" />
			</div>
		</div>
	</div>
	<div class="col-lg-6">
		<div class="form-group">
	        <label class="col-lg-4 control-label">LOT NO</label>
			<div class="col-lg-8">
				<input name="incomingLotNumber" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="ftLotNoID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">Mark Fail</label>
			<div class="col-lg-8">
				<input name="markFail" style="display:inline; width:94%;" class="form-control sum" value="0"  type="text" dataType="Require" id="markFailID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN2</label>
			<div class="col-lg-8">
				<input name="bin2" style="display:inline; width:94%;" class="form-control sum" value="0"  type="text" dataType="Require" id="bin2ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN3</label>
			<div class="col-lg-8">
				<input name="bin3" style="display:inline; width:94%;" class="form-control sum" value="0"  type="text" dataType="Require" id="bin3ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN4</label>
			<div class="col-lg-8">
				<input name="bin4" style="display:inline; width:94%;" class="form-control sum" value="0"  type="text" dataType="Require" id="bin4ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN5</label>
			<div class="col-lg-8">
				<input name="bin5" style="display:inline; width:94%;" class="form-control sum" value="0"  type="text" dataType="Require" id="bin5ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN7</label>
			<div class="col-lg-8">
				<input name="bin7" style="display:inline; width:94%;" class="form-control sum" value="0" type="text" dataType="Require" id="bin7ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN8</label>
			<div class="col-lg-8">
				<input name="bin8" style="display:inline; width:94%;" class="form-control sum" value="0" type="text" dataType="Require" id="bin8ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">Other</label>
			<div class="col-lg-8">
				<input name="other" style="display:inline; width:94%;" class="form-control sum" value="0" type="text" dataType="Require" id="otherID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">LAT FAIL</label>
			<div class="col-lg-8">
				<input name="latFail" style="display:inline; width:94%;" class="form-control sum" value="0" type="text" dataType="Require" id="latFailID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">EQC FAIL</label>
			<div class="col-lg-8">
				<input name="eqcFail" style="display:inline; width:94%;" class="form-control sum" value="0" type="text" dataType="Require" id="eqcFailID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">总计</label>
			<div class="col-lg-8">
				<input name="total" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="failID" />
			</div>
		</div>
	</div>
	<div class="col-lg-6 taxType">
		<div class="form-group">
	        <label class="col-lg-4 control-label">称重(g)</label>
	        <div class="col-lg-8">
				<input style="display:inline;visibility: hidden; width:94%;" class="form-control" value="0" type="text" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN1</label>
			<div class="col-lg-8">
				<input name="bondedBin1" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedBin1ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN2</label>
			<div class="col-lg-8">
				<input name="bondedBin2" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedBin2ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN3</label>
			<div class="col-lg-8">
				<input name="bondedBin3" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedBin3ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN4</label>
			<div class="col-lg-8">
				<input name="bondedBin4" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedBin4ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN5</label>
			<div class="col-lg-8">
				<input name="bondedBin5" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedBin5ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN7</label>
			<div class="col-lg-8">
				<input name="bondedBin7" style="display:inline; width:94%;" class="form-control" value="0"  type="text" dataType="Require" id="bondedBin7ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">BIN8</label>
			<div class="col-lg-8">
				<input name="bondedBin8" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedBin8ID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">Other</label>
			<div class="col-lg-8">
				<input name="bondedOther" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedOtherID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">LAT FAIL</label>
			<div class="col-lg-8">
				<input name="bondedLatFail" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedLatFailID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">EQC FAIL</label>
			<div class="col-lg-8">
				<input name="bondedEqcFail" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedEqcFailID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-4 control-label">总计</label>
			<div class="col-lg-8">
				<input name="bondedTotal" style="display:inline; width:94%;" class="form-control" value="0" type="text" dataType="Require" id="bondedFailID" />
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
    var selectItems = {};
    function onPrint(path,parameter)
	{
		var v = document.getElementById("activeX").Print(path, 1, parameter);
    	if(v!="1")
    	{
    		alert(v);
    	}
    	else
    	{
    		document.getElementById("activeX").Quit();
    	}
	}	
</script>
</body>
</html>