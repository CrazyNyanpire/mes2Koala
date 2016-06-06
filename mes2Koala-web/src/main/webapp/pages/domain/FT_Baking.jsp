<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div>
<form class="form-horizontal">
    <div class="hidden">
        <input type="text" id="idID" name="id"/>
        <input type="text" id="nameID" name="name"/>
        <input type="text" id="orderID" name="turn"/>
        <input type="text" id="versionID" name="version"/>
        <input type="text" id="ftStateID" name="state"/>
        <input type="text" id="ftBakingDTO_idID" name="ftBakingDTO.id"/>
        <input type="text" id="ftBakingDTO_versionID" name="ftBakingDTO.version"/>
    </div>
    <div class="form-group" style="margin-top:20px;">
        <label class="col-lg-2 control-label">烤箱编号:</label>
        <div class="col-lg-4">
          <div class="btn-group select" id="ftBakingDTO_ovenNumberID"></div>
          <input name="ftBakingDTO.ovenNumber" type="hidden" id="ftBakingDTO_ovenNumberID_"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">烤箱温度:</label>
        <div class="col-lg-4">
            <input name="ftBakingDTO.ovenTemperature" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftBakingDTO_ovenTemperatureID" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">进炉时间:</label>
        <div class="col-lg-9">
            <div class="input-group date form_datetime" style="width:200px;float:left;" >
                <input type="text" class="form-control" style="width:200px;" name="ftBakingDTO.timeIn"
                       id="ftBakingDTO_timeInID">
                <span class="input-group-addon" id="gainTimein"><span class="glyphicon glyphicon-th"></span></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">限制时间:</label>
        <div class="col-lg-4">
            <input name="ftBakingDTO.timeLimit" style="display:inline; width:200px;" class="form-control"  type="text"  id="ftBakingDTO_timeLimitID" />小时
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">出炉时间:</label>
        <div class="col-lg-9">
            <div class="input-group date form_datetime" style="width:200px;float:left;" >
                <input type="text" class="form-control" readonly style="width:200px;" name="ftBakingDTO.timeOut"
                       id="ftBakingDTO_timeOutID">
                <span class="input-group-addon" id="gainTimeout"><span class="glyphicon glyphicon-th"></span></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">数量统计:</label>
        <div class="col-lg-9">
            <table>
                <tr></tr>
                <tr></tr>
            </table>
        </div>
    </div>
</form>
    <div class="form-inline" id="saveBaking">
        <button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
        <button type="button" class="btn btn-default hidden" style="margin-left: 300px;">保存</button>
        <button type="button" class="btn btn-default hidden" style="margin-left: 10px;">出站</button>
    </div>
</div>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>