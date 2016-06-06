<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/11 0011
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<object class="hidden" id="activeX" classid="clsid:8738EBCB-92B1-4dcb-8E86-A4703EBD191E"> </object>
<form class="form-horizontal">
    <div class="hidden">
        <input type="text" id="idID" name="id"/>
        <input type="text" id="nameID" name="name"/>
        <input type="text" id="orderID" name="turn"/>
        <input type="text" id="versionID" name="version"/>
        <input type="text" id="ftStateID" name="state"/>
        <input type="text" id="ftPackingDTO_idID" name="ftPassNodeDTO.id"/>
        <input type="text" id="ftPackingDTO_versionID" name="ftPassNodeDTO.version"/>
    </div>
    <div class="form-horizontal">
        <div class="col-lg-11 form-group">
            <label class="">Reel Code:</label><br>
            <table class="table table-bordered table-condensed" id="tableReelDiskStatic">
            </table>
        </div>
    </div>
    <div class="form-horizontal">
        <div class="col-lg-11 form-group">
            <button class="btn btn-default" type="button" id="packingNodePrintId">打印</button>
            <button class="btn btn-default" type="button" id="packingNodePrintAllId">打印全部</button>
            <button class="btn btn-default" type="button" id="separatePacking">分批</button>
            <button class="btn btn-default" type="button" id="itgPacking">合批</button>
            <!-- <button class="btn btn-default" type="button" hidden>撤回</button> -->
        </div>
    </div>

    <div class="form-horizontal">
        <div class="col-lg-11 form-group">
            <label class="">合批信息:</label><br>
            <table class="table table-bordered table-condensed">
            </table>
        </div>
    </div>
    </div>
    <div class="form-group" style="padding-left: 30px;">
        <label class="">备注：</label><br>
        <textarea name="note" id="noteID" cols="100" rows="5"></textarea>
    </div>
</form>
<object class="hidden" id="activeX_Packing" classid="clsid:8738EBCB-92B1-4dcb-8E86-A4703EBD191E"> </object>
<div class="form-inline" id="savePacking">
    <button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
    <button type="button" class="btn btn-default hidden" style="margin-left: 300px;">保存</button>
    <button type="button" class="btn btn-default hidden" style="margin-left: 10px;">出站</button>
</div>
<script type="text/javascript">
    var selectItems = {};//打印Packing
</script>
</body>
</html>
