<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-2 control-label">客户PPO:</label>
        <div class="col-lg-2">
            <input name="customerPPO" style="display:inline; width:94%;" class="form-control" type="text"
                   id="customerPPOID"/>
        </div>
        <label class="col-lg-2 control-label">来料型号:</label>
        <div class="col-lg-2">
            <input name="revision" style="display:inline; width:94%;" class="form-control" type="text"
                   id="customerProductNumberID"/>
        </div>
        <label class="col-lg-2 control-label">保税类型:</label>
        <div class="col-lg-2">
            <input name="taxType" style="display:inline; width:94%;" class="form-control" type="text" id="taxTypeID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">Customer Lot No.:</label>
        <div class="col-lg-2">
            <input name="customerLotNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   id="customerLotNumberID"/>
        </div>
        <label class="col-lg-2 control-label">数量:</label>
        <div class="col-lg-2">
            <input name="incomingQuantity" style="display:inline; width:94%;" class="form-control" type="text"
                   id="incomingQuantityID"/>
        </div>
        <label class="col-lg-2 control-label">Wire Bond:</label>
        <div class="col-lg-2">
            <input name="wireBond" style="display:inline; width:94%;" class="form-control" type="text" id="wireBondID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">封装批号:</label>
        <div class="col-lg-2">
            <input name="packageNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   id="packageNumberID"/>
        </div>
        <label class="col-lg-2 control-label">到料形式:</label>
        <div class="col-lg-4">
            <input type="radio" name="incomingStyle" id="incomingStyleID"/>静电散装
            <input type="radio" name="incomingStyle" id="incomingStyleID_"/>Tray盘箱装
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">Wafer Lot:</label>
        <div class="col-lg-2">
            <input name="waferLot" style="display:inline; width:94%;" class="form-control" type="text" id="waferLotID"/>
        </div>
        <label class="col-lg-2 control-label">Customer No.:</label>
        <div class="col-lg-2">
            <input name="customerNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   id="customerNumberID"/>
        </div>
        <label class="col-lg-2 control-label">Date Code:</label>
        <div class="col-lg-2">
            <input name="dateCode" style="display:inline; width:94%;" class="form-control" type="text" id="dateCodeID"/>
        </div>

    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">MFG PN:</label>
        <div class="col-lg-2">
            <input name="MFGPN" style="display:inline; width:94%;" class="form-control" type="text" id="MFGPNID"/>
        </div>
        <label class="col-lg-2 control-label">进料日期:</label>
        <div class="col-lg-3">
            <div class="input-group date form_datetime" style="width:160px;float:left;">
                <input type="text" class="form-control" style="width:160px;" name="time" id="timeID">
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">版本型号:</label>
        <div class="col-lg-2">
            <input name="productVersion" style="display:inline; width:94%;" class="form-control" type="text"
                   id="productVersionID"/>
        </div>
        <label class="col-lg-2 control-label">晶圆厂商:</label>
        <div class="col-lg-2">
            <input name="waferManufacturer" style="display:inline; width:94%;" class="form-control" type="text"
                   id="waferManufacturerID"/>
        </div>
        <label class="col-lg-2 control-label">物料类型:</label>
        <div class="col-lg-2">
            <input name="materialType" style="display:inline; width:94%;" class="form-control" type="text"
                   id="materialTypeID"/>
        </div>
    </div>
    <label>需填内容:</label>
    <hr/>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>