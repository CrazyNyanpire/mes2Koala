<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div>
<form class="form-horizontal" contenteditable="false">
    <div class="hidden">
    <input type="text" id="idID" name="id"/>
    <input type="text" id="nameID" name="name"/>
    <input type="text" id="orderID" name="turn"/>
    <input type="text" id="versionID" name="version"/>
    <input type="text" id="ftStateID" name="state"/>
    <input type="text" id="ftIQCDTO_idID" name="ftIQCDTO.id"/>
    <input type="text" id="ftIQCDTO_versionID" name="ftIQCDTO.version"/>
    </div>
    <div class="form-group" style="margin-top:20px;">
        <label class="col-lg-2 control-label">Mark:</label>
        <div class="col-lg-4">
            <input name="ftIQCDTO.mark" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftIQCDTO_markID" />
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

    <div class="form-group">
        <label class="col-lg-2 control-label">净重(g):</label>
        <div class="col-lg-4">
            <input name="ftIQCDTO.grossWeight" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftIQCDTO_grossWeightID" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">毛重(g):</label>
        <div class="col-lg-4">
            <input name="ftIQCDTO.netWeight" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftIQCDTO_netWeightID" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">reelCode导入:</label>
        <div class="col-lg-4">
            <!--  <input name="ftIQCDTO.reelCode" style="display:inline; width:94%;" class="form-control"  type="file"  id="ftIQCDTO_reelCodeID" />
        	-->
        	<input name="reelCodeUpload" class="form-control" type="file" id="reelCodeUpload" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">已导入:</label>
        <div class="col-lg-4">
            <!--  <input name="ftIQCDTO.reelCode" style="display:inline; width:94%;" class="form-control"  type="file"  id="ftIQCDTO_reelCodeID" />
            -->
            <input readonly="true" class="form-control" name="ftIQCDTO.reelCode" type='text' name='reelCodelFullName' id="ftIQCDTO_reelCodeID"/>
        </div>
    </div>
</form>
 <div class="form-inline" id="saveIQC">
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