<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        .td {
            width: 60px;
        }
    </style>
</head>
<form class="form-horizontal finalYield" style="margin-top: 20px;">
    <div class="hidden">
        <input type="text" id="idID" name="id"/>
        <input type="text" id="nameID" name="name"/>
        <input type="text" id="orderID" name="turn"/>
        <input type="text" id="versionID" name="version"/>
        <input type="text" id="ftStateID" name="state"/>
        <input type="text" id="ftFinishDTO_idID" name="ftFinishDTO.id"/>
        <input type="text" id="ftFinishDTO_versionID" name="ftFinishDTO.version"/>
    </div>
    <div class="form-group" style="padding-left: 30px;">
        <label class="">Final Yield:</label><br>
        <table class="table table-bordered table-condensed" id="tableFinalYield">
            <tr></tr>
            <tr></tr>
        </table>
    </div>


<div class="form-horizontal">
    <div class="form-group" style="padding-left: 30px;">
        <label class="">数量统计：</label><br>
        <table class="table table-bordered table-condensed statistics" id="tableStatistics">
            <tr></tr>
            <tr></tr>
        </table>
    </div>
</div>
</form>
<div class="form-horizontal">
    <div class="form-group" style="padding-left: 30px;">
        <label class="control-label">ReelCode:</label><br>
        <label>固定码</label>
        <span class="td" style="background-color:white;padding:2px;"></span>
        <label>编码日期格式</label>
        <span class="td" style="background-color:white;padding:2px;">yyMMdd</span>
        <label>默认数量</label>
        <select>
            <option>1680</option>
            <option>3000</option>
        </select>
        <select>
        </select>
        <button class="btn btn-default" type="button" id="create">全</button>
        <button class="btn btn-default" type="button" id="saveReel">保存</button>
        <button class="btn btn-default" type="button" id="separate">拆盘</button>
        <button class="btn btn-default" type="button" id="integrate">合批</button>
        <button class="btn btn-default" type="button" id="sample">小样</button>
    </div>
    <div class="form-group" style="padding-left: 30px;">
        <table class="table table-bordered table-condensed" id="tableReelDiskStatic" disabled="true"> <%--固定区--%>
        </table>
        <table class="table table-bordered table-condensed" id="tableReelDisk" disabled="true"><%--临时区--%>
        </table>
    </div>
    <div class="form-group" style="padding-left: 30px;">
        <label class="">合批信息:</label><br>
        <table class="table table-bordered table-condensed" id="tableIntegratedReelDisk">
        </table>
    </div>
    <div style="padding-left:30px;"><a id="printsingleID" href="#">打印</a>|<a id="printID" href="#">打印全部</a></div>
    <div class="form-group" style="padding-left: 30px;">
        <label class="">备注：</label><br>
        <textarea id="noteID" cols="100" rows="5"></textarea>
    </div>
    <div class="form-inline" id="saveFinish" style="padding-left:300px;">
        <button type="button" class="btn btn-default">进站</button>
        <button type="button" class="btn btn-default hidden">保存</button>
        <button type="button" class="btn btn-default hidden">出站</button>
    </div>
</div>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>