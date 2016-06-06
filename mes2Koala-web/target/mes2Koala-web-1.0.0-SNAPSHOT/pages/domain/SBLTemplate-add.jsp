<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-3 control-label">产品型号</label>
        <div class="col-lg-9">
            <div class="btn-group select" id="internalProductID"></div>

            <input name="internalProductDTO.id" type="hidden" id="internalProductID_"
            />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Bin别:</label>
        <div class="col-lg-9">
            <input name="BinType" style="display:inline; width:94%;" class="form-control" type="text" id="BinTypeID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">下限:</label>
        <div class="col-lg-9">
            <input name="lowerLimit" style="display:inline; width:94%;" class="form-control" type="text"
                   id="lowerLimitID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">上限:</label>
        <div class="col-lg-9">
            <input name="upperLimit" style="display:inline; width:94%;" class="form-control" type="text"
                   id="upperLimitID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">品质:</label>
        <div class="col-lg-9">
            <input name="BinQuality" style="display:inline; width:94%;" class="form-control" type="text"
                   id="BinQualityID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Site:</label>
        <div class="col-lg-9">
            <input name="site" style="display:inline; width:94%;" class="form-control" type="text" id="siteID"/>
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
    var contents = [{
        title: '请选择',
        value: ''
    }];
    $.ajax({
        async: false,
        url: '${pageContext.request.contextPath}/InternalProduct/findAllInternalProduct.koala',
        type: 'POST',
        dataType: 'json',
    }).done(function (msg) {
        for (var i = 0; i < msg.length; i++) {
            contents.push({
                title: msg[i]['internalProductNumber'],
                value: msg[i]['id']
            });
        }
        selectItems['internalProductID'] = contents;
    });
</script>
</body>
</html>