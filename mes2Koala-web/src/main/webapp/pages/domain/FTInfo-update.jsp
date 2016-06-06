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
        <div class="col-lg-6">
            <input name="customerProductNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="customerProductNumberID"/>
            <span class="required">*</span></div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">客户产品版本:</label>
        <div class="col-lg-6">
            <input name="customerProductRevision" style="display:inline; width:94%;" class="form-control" type="text"
                   id="customerProductRevisionID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">PID:</label>
        <div class="col-lg-6">
            <input name="internalProductNumber" style="display:inline; width:94%;" readonly class="form-control" type="text"
                  dataType="Require"  id="internalProductNumberID"/>
                  <span class="required">*</span>
        </div>
    </div>
    <!--
    <div class="form-group">
        <label class="col-lg-3 control-label">内部产品型号:</label>
        <div class="col-lg-6">
            <input name="internalProductNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   id="internalProductNumberID"/>
        </div>
    </div>
    -->

    <%--暂时去掉require属性  by lcn--%>
    <%--<div class="form-group">--%>
    <%--<label class="col-lg-3 control-label">产品主要负责人:</label>--%>
    <%--<div class="col-lg-9">--%>
    <%--<div class="btn-group select" id="keyEmployeeDTOID"></div>--%>
    <%--<input type="hidden" id="keyEmployeeDTOID_" name="keyEmployeeDTO.id" />--%>
    <%--<span class="required">*</span></div>--%>
    <%--</div>--%>

    <%--<div class="form-group">--%>
    <%--<label class="col-lg-3 control-label">产品协助负责人:</label>--%>
    <%--<div class="col-lg-9">--%>
    <%--<div class="btn-group select" id="assistEmployeeDTOID"></div>--%>
    <%--<input type="hidden" id="assistEmployeeDTOID_" name="assistEmployeeDTO.id" />--%>
    <%--<span class="required">*</span></div>--%>
    <%--</div>--%>

    <div id="ManagerDTOID">
        <div class="form-group">

            <label class="col-lg-3 control-label">质量主要负责人:</label>
            <div class="col-lg-3">
                <button type="button" id="keyQuantityManagerDTOID" class="btn btn-default mes2">请选择</button>
                <input name="keyQuantityManagerDTO.id" id="keyQuantityManagerDTOID_" dataType="Require" type="hidden">
                <span class="required">*</span>
            </div>
            <label class="col-lg-3 control-label">质量协助负责人:</label>
            <div class="col-lg-3">
                <button type="button" id="assistQuantityManagerDTOID" class="btn btn-default mes2">请选择</button>
                <input type="hidden" name="assistQuantityManagerDTO.id" id="assistQuantityManagerDTOID_" dataType="Require">
                <span class="required">*</span>
            </div>
        </div>

        <div class="form-group">

            <label class="col-lg-3 control-label">产品主要负责人:</label>
            <div class="col-lg-3">
                <button type="button" id="keyProductionManagerDTOID" class="btn btn-default mes2">请选择</button>
                <input type="hidden" name="keyProductionManagerDTO.id" id="keyProductionManagerDTOID_" dataType="Require">
                <span class="required">*</span>
            </div>
            <label class="col-lg-3 control-label">产品协助负责人:</label>
            <div class="col-lg-3">
                <button type="button" id="assistProductionManagerDTOID" class="btn btn-default mes2">请选择</button>
                <input type="hidden" name="assistProductionManagerDTO.id" id="assistProductionManagerDTOID_" dataType="Require">
                <span class="required">*</span>
            </div>
        </div>


        <div class="form-group">

            <label class="col-lg-3 control-label">TDE主要负责人:</label>
            <div class="col-lg-3">
                <button type="button" id="keyTDEManagerDTOID" class="btn btn-default mes2">请选择</button>
                <input type="hidden" name="keyTDEManagerDTO.id" id="keyTDEManagerDTOID_" dataType="Require">
                <span class="required">*</span>
            </div>
            <label class="col-lg-3 control-label">TDE协助负责人:</label>
            <div class="col-lg-3">
                <button type="button" id="assistTDEManagerDTOID" class="btn btn-default mes2">请选择</button>
                <input type="hidden" name="assistTDEManagerDTO.id" id="assistTDEManagerDTOID_" dataType="Require">
                <span class="required">*</span>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 control-label">所属直接客户:</label>
        <div class="col-lg-9">
            <div class="btn-group select" id="customerDirectDTOID"></div>
            <input type="hidden" id="customerDirectDTOID_" name="customerDirectDTO.id" dataType="Require"/>
            <span class="required">*</span></div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">所属间接客户:</label>
        <div class="col-lg-9">
            <div class="btn-group select" id="customerIndirectDTOID"></div>
            <input type="hidden" id="customerIndirectDTOID_" name="customerIndirectDTO.id" /></div>
    </div>
    <!-- 		           <div class="form-group">
                        <label class="col-lg-3 control-label">内部产品型号:</label>
                            <div class="col-lg-6">
                                 <input name="internalProductNumber" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" id="internalProductNumberID" />
            <span class="required">*</span>	    </div>
        </div> -->
    <div class="form-group">
        <label class="col-lg-3 control-label">出货产品型号:</label>
        <div class="col-lg-6">
            <input name="shipmentProductNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   id="shipmentProductNumberID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">封装形式:</label>
        <div class="col-lg-9">
            <div class="btn-group select" id="packageTypeID"></div>
            <input type="hidden" id="packageTypeID_" name="packageType" dataType="Require"/>
            <span class="required">*</span></div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">包装形式:</label>
        <div class="col-lg-9">
            <div class="btn-group select" id="packingTypeID"></div>
            <input type="hidden" id="packingTypeID_" name="packingType"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">产品尺寸:</label>
        <div class="col-lg-6">
            <input name="size" style="display:inline; width:94%;" class="form-control" type="text" id="sizeID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">封装厂:</label>
        <div class="col-lg-6">
            <input name="packingFactory" style="display:inline; width:94%;" class="form-control" type="text"
                   id="packingFactoryID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">晶圆厂:</label>
        <div class="col-lg-6">
            <input name="waferFactory" style="display:inline; width:94%;" class="form-control" type="text"
                   id="waferFactoryID"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 control-label">测试方案1:</label>
        <label class="col-lg-2 control-label">TestSys:</label>
        <div class="col-lg-3" style="margin-left:-20px;margin-right:-20px">
            <input name="TestSys1" style="display:inline;" class="form-control" type="text" id="testSys1ID"/>
        </div>
        <label class="col-lg-2 control-label" style="margin-left:-20px;">H/P:</label>
        <div class="col-lg-3" style="margin-left:-20px;">
            <input name="H_P_1" style="display:inline; width:94%;" class="form-control" type="text" id="h_P_1ID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">测试方案2:</label>
        <label class="col-lg-2 control-label">TestSys:</label>
        <div class="col-lg-3" style="margin-left:-20px;margin-right:-20px">
            <input name="TestSys2" style="display:inline; width:94%;" class="form-control" type="text" id="testSys2ID"/>
        </div>
        <label class="col-lg-2 control-label" style="margin-left:-20px;">H/P:</label>
        <div class="col-lg-3" style="margin-left:-20px;">
            <input name="H_P_2" style="display:inline; width:94%;" class="form-control" type="text" id="h_P_2ID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">测试方案3:</label>
        <label class="col-lg-2 control-label">TestSys:</label>
        <div class="col-lg-3" style="margin-left:-20px;margin-right:-20px">
            <input name="TestSys3" style="display:inline; width:94%;" class="form-control" type="text" id="testSys3ID"/>
        </div>
        <label class="col-lg-2 control-label" style="margin-left:-20px;">H/P:</label>
        <div class="col-lg-3" style="margin-left:-20px;">
            <input name="H_P_3" style="display:inline; width:94%;" class="form-control" type="text" id="h_P_3ID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">ReelCode固定码:</label>

        <div class="col-lg-6">
            <input name="reelFixCode" style="display:inline; width:94%;" class="form-control" type="text"
                   id="reelFixCodeID"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 control-label">Reel盘数量:</label>

        <div class="col-lg-6">
            <input name="reelQty" style="display:inline; width:94%;" class="form-control" type="text" id="reelQtyID"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 control-label">产品说明:</label>
        <div class="col-lg-9">
            <textarea class="form-control" style="display:inline; width:94%;" rows="3" id="noteID"
                      name="note"></textarea>
        </div>
    </div>
</form>
<script type="text/javascript">

</script>
</body>
</html>