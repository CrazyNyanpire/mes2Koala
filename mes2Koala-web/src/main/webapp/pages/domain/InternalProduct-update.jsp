<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    <input type="hidden" id="idID" name="id"/>
    <input type="hidden" id="versionID" name="version"/>

    <div class="form-group">
        <label class="col-lg-3 control-label">客户产品型号:</label>

        <div class="col-lg-9">
            <input name="revision" style="display:inline; width:94%;" class="form-control" type="text"
                   id="customerProductNumberID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">客户产品版本:</label>

        <div class="col-lg-9">
            <input name="customerProductRevision" style="display:inline; width:94%;" class="form-control" type="text"
                   id="customerProductRevisionID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">内部产品型号:</label>

        <div class="col-lg-9">
            <input name="internalProductNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   id="internalProductNumberID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">内部产品版本:</label>

        <div class="col-lg-9">
            <input name="internalProductRevision" style="display:inline; width:94%;" class="form-control" type="text"
                   id="internalProductRevisionID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">封装形式:</label>

        <div class="col-lg-9">
            <input name="packageType" style="display:inline; width:94%;" class="form-control" type="text"
                   id="packageTypeID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">出货产品型号:</label>

        <div class="col-lg-9">
            <input name="shipmentProductNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   id="shipmentProductNumberID"/>
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>