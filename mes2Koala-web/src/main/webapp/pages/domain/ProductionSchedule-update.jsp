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
        <label class="col-lg-4 control-label">预计开始时间:</label>

        <div class="col-lg-6">
            <div class="input-group date form_datetime" style="width:160px;float:left;">
                <input type="text" class="form-control" style="width:160px;" name="planedStartTimestamp"
                       id="planedStartTimestampID">
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-4 control-label">机台：</label>
        <div class="col-lg-6">
            <div class="btn-group select" id="testSysIdID"></div>
            <input name="testSysId" type="hidden" id="testSysIdID_"
            />
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>