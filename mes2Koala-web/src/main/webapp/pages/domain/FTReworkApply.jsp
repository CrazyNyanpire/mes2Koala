<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<title>FT重工需求单(FT Rework Requisition)</title>	
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal row">
<style>
td {
    	vertical-align: middle!important;
	}
.binSummary td{
	padding:12px!important;
}
.footer:not(:nth-of-type(1)){
	margin-left:10%;
}
@media print{
	td {
    	vertical-align: middle!important;
	}
	.col-lg-3,.other{
		display:inline-block;
	}
	.col-lg-offset-2 {
	    margin-left: 8%;
	}
	.printRight{
		float:right;
	}
	.footer:not(:nth-of-type(1)){
		margin-left:7%;
		font-size:12px;
	}
}
</style>
	<div class="col-lg-12">
	<strong>基础信息(Basic Information):</strong>
		<table class="table table-condensed table-bordered basicInfomation" style="margin-top: 10px;font-size:12px;">
		<tr>
			<td style="font-weight:bold">日期</td><td class="basic" style="vertical-align: middle;" name="reworkDate"></td>
			<td style="font-weight:bold">重工编号</td><td class="basic" style="vertical-align: middle;" name="reworkNo"></td>
			<td style="font-weight:bold">申请部门</td><td class="basic" style="vertical-align: middle;" name="reworkDepartment"></td>
		</tr>
		<tr>
			<td style="font-weight:bold">客户<br/>(Customer)</td><td class="basic" style="vertical-align: middle;" id="customerNumberID" name="reworkCustomer"></td>
			<td style="font-weight:bold">产品型号<br/>(Device Name)</td><td class="basic" style="vertical-align: middle;" id="shipmentProductNumberID" name="reworkProduct"></td>
			<td style="font-weight:bold">产品批号<br/>(Lot No.)</td><td class="basic" style="vertical-align: middle;" id="internalLotNumberID" name="reworkLot"></td>
		</tr>
		<tr>
			<td style="font-weight:bold">数量(Qty)</td><td><input type="text" name="reworkReworkQty" style="width:75px" dataType="Require"/>/<span name="reworkTotalQty" id="qtyID" class="totalQty"></span></td>
			<td style="font-weight:bold">机台<br/>(Machine No.)</td><td><input class="basic" type="text" name="reworkEquipment" style="width:100%;" value="NX16-004"/></td>
			<td style="font-weight:bold">流程卡编号<br/>(RunCard No.)</td><td><input class="basic" type="text" name="reworkRCNo" style="width:100%;" value="RC20150819001"/></td>
		</tr>
		</table>
    </div>
    <div class="col-lg-12" style="margin-top: 10px;">
		<strong>重工原因(Rework Reson):</strong>
		<div class="form-group">
            <div class="col-lg-3 col-lg-offset-2">
               <input type="radio"  name="Rework_reason" >
               <label class="control-label">客户要求(Customer Request)</label>
            </div>
            <div class="col-lg-3">
               <input type="radio"  name="Rework_reason" >
               <label class="control-label">LAT Fail</label>
            </div>
            <div class="col-lg-3">
               <input type="radio"  name="Rework_reason" >
               <label class="control-label">低良率(Low Yield)</label>
            </div>
        </div>
        <div class="form-group">
        	<div class="col-lg-3 col-lg-offset-2">
               <input type="radio"  name="Rework_reason">
               <label class="control-label">操作错误(Miss Operation)</label>
            </div>
            <div class="col-lg-3">
               <input type="radio"  name="Rework_reason">
               <label class="control-label">机台异常</label>
            </div>
            <div class="col-lg-4 other">
               <input type="radio"  name="Rework_reason">
               <label class="control-label">其他(Others)</label>
               <input type="text"  name="Rework_other">
            </div>
        </div>
    </div>
    <div class="col-lg-12" style="margin-top: 10px;">
		<strong style="margin-bottom: 2%;display: block;">重工说明(Rework Explanation):</strong>
		<div class="col-lg-12">
			<textarea rows="5" class="form-control" name="explanation" dataType="Require"></textarea>
		</div>
    </div>
    <div class="col-lg-12" style="margin-top: 10px;">
		<strong style="display: block;">重工事项(Rework Item):</strong>
		<div class="col-lg-12" >
            <table class="table table-condensed table-bordered" style="margin-top:10px;font-size:12px;">
            	<thead>
            	<tr><th style="text-align:center;">注意事项<br/>(Attention Item)</th><th style="text-align:center;">重工站别<br/>(Rework Flow)</th><th style="text-align:center;">操作人员<br/>(Operator)</th><th style="text-align:center;">完成日期<br/>(Accomplish Date)</th></tr>
            	</thead>
            	<tbody class="rework_item">
            		<tr><td><input type="text" class="form-control"/></td><td><input type="text" class="form-control"/></td><td><input type="text" class="form-control"/></td><td><input type="date" class="form-control"/></td></tr>
            		<tr><td><input type="text" class="form-control"/></td><td><input type="text" class="form-control"/></td><td><input type="text" class="form-control"/></td><td><input type="date" class="form-control"/></td></tr>
            	</tbody>
            </table>
            <div><span style="cursor: pointer;float: right;margin-right: 15px;margin-bottom: 15px;"onclick="rework_itemAdd()" class="glyphicon glyphicon-plus"></span></div>
        </div>
        <div class="col-lg-12">
            <div class="col-lg-4">
               <input type="radio"  name="Rework_over">
               <label class="control-label">重工后续Go(Go After Rework)</label>
            </div>
            <div class="col-lg-4">
               <input type="radio"  name="Rework_over">
               <label class="control-label">重工完Hold(Hold after rework)</label>
            </div>
            <div class="col-lg-4  printRight">
               <label class="control-label" >需求人员(Demand Eng):</label>
               <span class="control-label" style="white-space: nowrap;" name="optpreson" >方杰</span>
            </div>
        </div>
    </div>
    <div class="col-lg-12" style="margin-top: 10px;">
    	<strong style="display: block;">执行单位填写:</strong>
		<strong style="display: block;">重工测试结果(Rework F/T Bin Summary):</strong>
		<div class="col-lg-12" >
            <table class="table table-condensed table-bordered binSummary" style="margin-top:10px;font-size:12px;">
            	<thead>
            	<tr><th rowspan="2" style="text-align:center;vertical-align: middle;">批号<br/>(Lot No.)</th><th rowspan="2" style="text-align:center;vertical-align: middle;">站别</th><th colspan="2" style="text-align:center;">重工前<br/>(Before Rework)</th><th colspan="2" style="text-align:center;">重工后<br/>(After Rework)</th><th style="vertical-align: middle;text-align:center;" colspan="2" rowspan="2" style="text-align:center;">备注<br/>(Comment)</th></tr>
            	<tr><th style="text-align:center;" colspan="1">PASS</th><th style="text-align:center;" colspan="1">Yield</th><th style="text-align:center;" colspan="1">PASS</th><th style="text-align:center;" colspan="1">Yield</th></tr>
            	</thead>
            	<tbody>
            		<tr><td rowspan="5"></td></tr>
            		<tr><td></td><td></td><td></td><td></td><td></td><td colspan="2"></td></tr>
            		<tr><td></td><td></td><td></td><td></td><td></td><td colspan="2"></td></tr>
            		<tr><td></td><td></td><td></td><td></td><td></td><td colspan="2"></td></tr>
            		<tr><td></td><td></td><td></td><td></td><td></td><td colspan="2"></td></tr>
            	</tbody>
            </table>
        </div>
    </div>
    <div class="col-lg-12" style="margin-top: 10px;"> 
    	<strong style="margin-bottom: 2%;display: block;">审核人员:</strong>
    	<input type="hidden" name="acetecAuthorizationIds" id="acetecAuthorizationID">
        <button type="button"  id="authorizationMemberID" class="btn btn-default">选择审核人</button>
    	<div class="applyPerson" style="margin-top: 10px;padding-left: 10px;"></div>
    </div>
    <div class="col-lg-12" style="margin-top: 20px;text-align:center;">
    	<span class="footer">WMG-G006-01</span><span class="footer">责任/保存部门：PD/MFG</span><span class="footer">保存年限：3年</span>
    </div>
</form>
<script type="text/javascript">
function rework_itemAdd()
{
	$(".rework_item").append("<tr><td><input type='text' class='form-control'/></td><td><input type='text' class='form-control'/></td><td><input type='text' class='form-control'/></td><td><input type='date' class='form-control'/></td></tr>");
}
</script>
</body>
</html>