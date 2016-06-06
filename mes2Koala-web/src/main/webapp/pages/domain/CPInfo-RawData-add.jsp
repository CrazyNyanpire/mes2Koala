<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
</script>
<body>
<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-3 control-label">Product ID:</label>
        <div class="col-lg-6">
            <input name="productID" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="productIDID"/>
            <span class="required">*</span></div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Notch Side:</label>
        <div class="col-lg-6">
            <input name="notchSide" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="notchSideID"/>
            <span class="required">*</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Bindefinition File:</label>
        <div class="col-lg-6">
            <input name="bindefinitionFile" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="bindefinitionFileID"/>
            <span class="required">*</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Grid Xmax:</label>
        <div class="col-lg-6">
            <input name="gridXmax" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="gridXmaxID"/>
            <span class="required">*</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Fab Site:</label>
        <div class="col-lg-6">
            <input name="fabSite" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="fabSiteID"/>
            <span class="required">*</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">X Die Size:</label>
        <div class="col-lg-6">
            <input name="xDieSize" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="xdieSizeID"/>
            <span class="required">*</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Y Die Size:</label>
        <div class="col-lg-6">
            <input name="yDieSize" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="ydieSizeID"/>
            <span class="required">*</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Customer CodeID:</label>
        <div class="col-lg-6">
            <input name="customerCodeID" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="customerCodeIDID"/>
            <span class="required">*</span>
        </div>
    </div>
</form>
</body>
</html>