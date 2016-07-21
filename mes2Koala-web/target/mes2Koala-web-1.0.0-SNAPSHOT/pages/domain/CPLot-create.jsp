<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal" >
    <!-- 该页面为FT下单页面，利用id属性获得需要的数据，利用name属性传输到后台创建InternalLot以及 FTLot
        Howard 2016.01.05
         -->
    <input type="hidden" id="idID" name="customerCPLotDTO.id"/>

    <div class="form-group">
        <label class="col-lg-2 control-label text-left">客户PPO:</label>
        <div class="col-lg-3">
            <input style="display:inline; width:150px;" class="form-control" type="text"
                   id="customerPPOID"/>
        </div>
        <%--<label class="col-lg-3 control-label">内部型号:</label>--%>
        <%--<div class="col-lg-4">--%>
            <%--<input style="display:inline; width:150px;" class="form-control" type="text"--%>
                   <%--id="内部型号"/>--%>
        <%--</div>--%>
            <label class="col-lg-3 control-label">Customer Lot No.:</label>
            <div class="col-lg-4">
                <input style="display:inline; width:150px;" class="form-control" type="text"
                       id="customerLotNumberID"/>
            </div>
        </div>
    <div class="form-group">

        <label class="col-lg-2 control-label">物料类型:</label>
        <div class="col-lg-3">
            <input style="display:inline; width:150px;" class="form-control" type="text"
                   id="materialTypeID"/>
        </div>
        <label class="col-lg-3 control-label">封装厂批号:</label>
        <div class="col-lg-4">
            <input style="display:inline; width:150px;" class="form-control" type="text"
                   id="packingLotID"/>
        </div>
    </div>
    <div class="form-group">

        <label class="col-lg-2 control-label">Mask Name:</label>
        <div class="col-lg-3">
            <input style="display:inline; width:150px;" class="form-control" type="text"
                   id="maskNameID"/>
        </div>
        <label class="col-lg-3 control-label">晶圆批号:</label>
        <div class="col-lg-4">
            <input style="display:inline; width:150px;" class="form-control" type="text"
                   id="waferLotID"/>
        </div>
    </div>
    <div class="form-group">

        <label class="col-lg-2 control-label">Customer No:</label>
        <div class="col-lg-3">
            <input style="display:inline; width:150px;" class="form-control" type="text"
                   id="customerNumberID"/>
        </div>
        <label class="col-lg-3 control-label">数量:</label>
        <div class="col-lg-4">
            <input style="display:inline; width:150px;" class="form-control" type="text"
                   id="incomingQuantityID"/>
        </div>
    </div>

    <!-- 以下为复选框与文本框 -->
    <br/>
    <label>wafer:</label>
    <hr/>
    <div class="form-group">
        <div class="col-lg-12">
            <table class="text-right">
                <tr id="waferIndexheaderID"></tr>
                <tr id="waferIndexID"></tr>
            </table>
        </div>
        <div class="col-lg-12">
            <table class="table table-condensed" id="waferCodeID"></table>
        </div>
    </div>

    <!-- 以下为InternalLotDTO内字段 -->
    <br/>
    <label>需填内容:</label>
    <hr/>
    <div class="form-group">
        <label class="col-lg-2 control-label">Process名称:</label>
        <div class="col-lg-2">
            <input style="display:inline;width:130px;" class="form-control" type="text" id="processNameID"/>
        </div>
        <label class="col-lg-2 control-label">Lot Number:</label>
        <div class="col-lg-2">
            <input name="internalLotNumber" style="display:inline; width:200px;" class="form-control" type="text"
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
            <input style="display:inline;width:710px;" class="form-control" type="text" id="processContentID"/>
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
            <input type="hidden" id="internalProductNumberID_" name="cpInfoId" dataType="Require"/>
             <span class="required">*</span>
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