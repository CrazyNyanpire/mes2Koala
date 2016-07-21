<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    <!-- 该页面为FT下单页面，利用id属性获得需要的数据，利用name属性传输到后台创建InternalLot以及 FTLot
        Howard 2016.01.05
         -->
    <input type="hidden" id="customerLotDTOidID" name="customerLotDTO.id"/>

    <div class="form-group">
        <label class="col-lg-2 control-label">客户PPO:</label>
        <div class="col-lg-2">
            <input style="display:inline; width:120px;" class="form-control" type="text"
                   id="customerLotDTOcustomerPPOID"/>
        </div>
        <label class="col-lg-2 control-label">来料型号:</label>
        <div class="col-lg-2">
            <input style="display:inline;width:120px;" class="form-control" type="text" id="customerProductNumberID"/>
        </div>
        <label class="col-lg-2 control-label">保税类型:</label>
        <div class="col-lg-2">
            <input style="display:inline; width:120px;" class="form-control" type="text" id="taxTypeID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">Customer Lot No.:</label>
        <div class="col-lg-2">
            <input style="display:inline; width:120px;" class="form-control" type="text"
                   id="customerLotDTOcustomerLotNumberID"/>
        </div>
        <label class="col-lg-2 control-label">数量:</label>
        <div class="col-lg-2">
            <input name="Qty" style="display:inline; width:120px;" class="form-control" type="text"
                   id="incomingQuantityID"/>
        </div>

        <label class="col-lg-2 control-label">Wire Bond:</label>
        <div class="col-lg-2">
            <input style="display:inline; width:120px;" class="form-control" type="text" id="wireBondID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">封装批号:</label>
        <div class="col-lg-2">
            <input style="display:inline;  width:120px;" class="form-control" type="text" id="packageNumberID"/>
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
            <input style="display:inline;  width:120px;" class="form-control" type="text" id="waferLotID"/>
        </div>
        <label class="col-lg-2 control-label">Customer No.:</label>
        <div class="col-lg-2">
            <input style="display:inline; width:120px;" class="form-control" type="text"
                   id="customerLotDTOcustomerNumberID"/>
        </div>
        <label class="col-lg-2 control-label">Date Code:</label>
        <div class="col-lg-2">
            <input style="display:inline; width:120px;" class="form-control" type="text" id="dateCodeID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">MFG PN:</label>
        <div class="col-lg-2">
            <!-- id设置为小写mfgpn，因为前端接收到的json包的键值对即为 "mfgpn"->"" 原因不明 -->
            <input style="display:inline; width:120px;" class="form-control" type="text" id="mfgpnID"/>
        </div>
        <label class="col-lg-2 control-label">进料日期:</label>
        <div class="col-lg-2">
            <div class="input-group date form_datetime" style="width:160px;float:left;">
                <input type="text" class="form-control" style="width:160px;" id="timeID">
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">版本型号:</label>
        <div class="col-lg-2">
            <input style="display:inline;width:120px;" class="form-control" type="text" id="productVersionID"/>
        </div>
        <label class="col-lg-2 control-label">晶圆厂商:</label>
        <div class="col-lg-2">
            <input style="display:inline; width:120px;" class="form-control" type="text" id="waferManufacturerID"/>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">物料类型:</label>
            <div class="col-lg-2">
                <div class="btn-group select" id="materialTypeID"></div>
                <input type="hidden" id="materialTypeID_" name="materialType"/>
            </div>
        </div>
    </div>


    <!-- 以下为InternalLotDTO内字段 -->
    <label>需填内容:</label>
    <hr/>
    <div class="form-group">
        <label class="col-lg-2 control-label">Process名称:</label>
        <div class="col-lg-2">
            <input style="display:inline;width:130px;" class="form-control" type="text" id="processTemplateNameID"/>
        </div>
        <label class="col-lg-2 control-label">Lot Number:</label>
        <div class="col-lg-2">
            <input name="InternalLotNumber" style="display:inline; width:200px;" class="form-control" type="text"
                   id="InternalLotNumberID"/>
        </div>
        <label class="col-lg-2 control-label">出货型号:</label>
        <div class="col-lg-2">
            <input name="shipmentProductNumber" style="display:inline;width:120px;" class="form-control" type="text"
                   id="shipmentProductNumberID"/>
        </div>

    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">Process内容:</label>
        <div class="col-lg-2">
            <input style="display:inline;width:710px;" class="form-control" type="text" id="processTemplateContentID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">Run Card编号:</label>
        <div class="col-lg-2">
            <input name="rcNumber" style="display:inline;width:130px;" class="form-control" type="text" id="rcNumberID"/>
        </div>
        <button type="button" id="showRuncard" class="btn btn-default">查看RunCard</button>
        <label class="col-lg-2 control-label">PID:</label>
        <div class="col-lg-3">
            <div class="btn-group select" id="internalProductNumberID"></div>
            <input type="hidden" id="internalProductNumberID_" name="cpInfoId"/>
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
    var contents = [{title: '请选择', value: ''}];
    $.ajax({
        async: false,
        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/materialType.koala',
        type: 'GET',
        dataType: 'json',
    }).done(function (msg) {
        for (var i = 0; i < msg.length; i++) {
            contents.push({
                title: msg[i]['label'],
                value: msg[i]['value']
            });
        }
    });
    selectItems['materialTypeID'] = contents;
</script>
</body>
</html>