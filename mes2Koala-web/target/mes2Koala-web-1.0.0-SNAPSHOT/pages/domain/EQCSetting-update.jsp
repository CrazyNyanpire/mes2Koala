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
        <label class="col-lg-3 control-label">EQC数量:</label>
        <div class="col-lg-9">
            <input name="Qty" style="display:inline; width:94%;" class="form-control" type="text" id="QtyID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">来料数量下限:</label>
        <div class="col-lg-9">
            <input name="lowerLimit" style="display:inline; width:94%;" class="form-control" type="text"
                   id="lowerLimitID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">来料数量上限:</label>
        <div class="col-lg-9">
            <input name="upperLimit" style="display:inline; width:94%;" class="form-control" type="text"
                   id="upperLimitID"/>
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>