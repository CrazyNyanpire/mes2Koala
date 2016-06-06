<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<!--     <div class="form-group">
        <label class="col-lg-3 control-label">Level:</label>
        <div class="col-lg-3">
            <input name="level" style="display:inline; width:94%;" class="form-control" type="text" id="levelID"/>
        </div>
    </div> -->
    <div class="form-group">
        <label class="col-lg-3 control-label">序号:</label>
        <div class="col-lg-3">
            <input name="bomId" style="display:inline; width:94%;" class="form-control" type="text" id="bomIdID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">产品型号:</label>
        <div class="col-lg-6">
            <div class="btn-group select" id="modelNumberID"></div>
            <input name="modelNumber" type="hidden" id="modelNumberID_" dataType="Require"/>
            <span class="required">*</span>
        </div>
      </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">料号:</label>
        <div class="col-lg-3">
            <input name="number" style="display:inline; width:94%;" class="form-control" type="text" id="numberID"/>
        </div>
        <label class="col-lg-3 control-label">Um:</label>
        <div class="col-lg-3">
            <input name="um" style="display:inline; width:94%;" class="form-control" type="text" id="umID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Revision:</label>
        <div class="col-lg-3">
            <input name="revision" style="display:inline; width:94%;" class="form-control" type="text" id="revisionID"/>
        </div>
        <label class="col-lg-3 control-label">BOM.Qty:</label>
        <div class="col-lg-3">
            <input name="quantity" style="display:inline; width:94%;" class="form-control" type="text" id="quantityID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Description:</label>
        <div class="col-lg-3">
            <input name="description" style="display:inline; width:94%;" class="form-control" type="text"
                   id="descriptionID"/>
        </div>
        <label class="col-lg-3 control-label">BOM.Qty/理论:</label>
        <div class="col-lg-3">
            <input name="theoryQuantity" style="display:inline; width:94%;" class="form-control" type="text"
                   id="theoryQuantityID"/>
        </div>
<!--         <label class="col-lg-3 control-label">BOM.Item Description:</label>
        <div class="col-lg-3">
            <input name="itemDescription" style="display:inline; width:94%;" class="form-control" type="text" id="itemDescriptionID"/>
        </div> -->
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Customer Code:</label>
        <div class="col-lg-4">
            <input name="customerCode" style="display:inline; width:66%;" class="form-control" type="text"
                   id="customerCodeID" dataType="Require"/><span class="required">*</span>
        </div>
        <label class="col-lg-2 control-label">供应商:</label>
        <div class="col-lg-3">
            <input name="manufacturerName" style="display:inline; width:94%;" class="form-control" type="text"
                   id="manufacturerNameID"/>
        </div>
    </div>

</form>
<script type="text/javascript">
    var selectItems = {};

   selectItems["modelNumberID"] = [];
    $.ajax({
        async: false,
        url: '${pageContext.request.contextPath}/InternalProduct/findAllInternalProduct.koala',
        type: 'POST',
        dataType: 'json'
    }).done(function (msg) {
        contents = [];
        for (var i = 0; i < msg.length; i++) {
            contents.push({
                title: msg[i]['customerProductNumber'],
                value: msg[i]['customerProductNumber']
            });
        }
        selectItems['modelNumberID'] = contents;
    });
</script>
</body>
</html>