<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    <!-- 以下包含customerLotDTO内容，id命名规则为customerLotDTO + [memberName] + ID ref:customerLotFTLot-list.jsp
        经尝试，id名 中间加点号"."无法寻找到相应的控件 Howard-->
    <input type="hidden" id="idID" name="id" />
    <input type="hidden" id="versionID" name="version" />
    <input type="hidden" id="customerLotDTOidID" name="customerLotDTO.id" />
    <input type="hidden" id="customerLotDTOversionID" name="customerLotDTO.version" />
        <div class="form-group">
            <label class="col-lg-2 control-label">客户批号:</label>
            <div class="col-lg-4">
                <input name="lotNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="lotNumberID" disabled />
            </div>
            <label class="col-lg-2 control-label">原数量:</label>
		        <div class="col-lg-4"><input name="quantity" style="display:inline; width:94%;" class="form-control"  type="text"  id="quantityID"  disabled/>
	        </div>
        </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">分批数量:</label>
        <div class="col-lg-4"><input name="divideAmount" style="display:inline; width:94%;" class="form-control" type="number" min="1" id="divideAmountID" >
        </div>
    </div>
    <div id="group">
    </div>
</form>
</body>
</html>