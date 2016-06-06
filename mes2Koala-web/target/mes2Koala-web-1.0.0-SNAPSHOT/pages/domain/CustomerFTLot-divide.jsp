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
        <label class="col-lg-3 control-label">客户批号:</label>
        <div class="col-lg-6">
            <input name="customerLotNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   id="customerLotNumberID" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">原数量:</label>
        <div class="col-lg-6"><input name="incomingQuantity" style="display:inline; width:94%;" class="form-control"
                                     type="text"
                                     id="incomingQuantityID" disabled/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">分批数量:</label>
        <div class="col-lg-6"><input name="divideAmount" style="display:inline; width:94%;" class="form-control"
                                     type="number" min="1" id="divideAmountID">
        </div>
    </div>
    <div id="group">
    </div>
</form>
</body>
</html>