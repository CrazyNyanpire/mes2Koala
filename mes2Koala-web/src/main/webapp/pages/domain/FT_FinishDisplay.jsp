<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<form class="form-horizontal finalYield" style="margin-top: 20px;">
    <div class="form-group" style="padding-left: 30px;">
        <label class="">Final Yield:</label><br>
        <table class="table table-bordered table-condensed" id="tableFinalYield">
            <tr>
                <th>sum</th>
                <th>other</th>
                <th>loss</th>
                <th>yield</th>
                <th>backUp</th>
                <th>markF</th>
                <th>fail</th>
            </tr>
            <tr>
                <td><input type="text"/></td>
                <td><input type="text"/></td>
                <td><input type="text"/></td>
                <td><input type="text"/></td>
                <td><input type="text" /></td>
                <td><input type="text"/></td>
                <td><input type="text" /></td>
            </tr>
        </table>
    </div>
</form>

<div class="form-horizontal">
    <div class="form-group" style="padding-left: 30px;">
        <label class="">数量统计：</label><br>
        <table class="table table-bordered table-condensed statistics" id="tableStatistics">
            <tr>
                <th>sum</th>
                <th>other</th>
                <th>loss</th>
                <th>yield</th>
                <th>backUp</th>
                <th>markF</th>
                <th>fail</th>
            </tr>
            <tr>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
            </tr>
        </table>
    </div>
</div>

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
        <button class="btn btn-default" type="button" id="separate">分批</button>
        <button class="btn btn-default" type="button" id="integrate">合批</button>
        <button class="btn btn-default" type="button" id="sample">小样</button>
    </div>
    <div class="form-group" style="padding-left: 30px;">
        <table class="table table-bordered table-condensed" id="tableReelDisk" disabled="true">
            <tr>
                <th>reelCode</th>
                <th>packagingTime</th>
                <th>quantity</th>
                <th>dateCode</th>
                <th>partNumber</th>
                <th>fromReelCode</th>
                <th>combinedLotNumber</th>
                <th>quality</th>
            </tr>
            <tr>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
            </tr>
        </table>
    </div>
    <div class="form-group" style="padding-left: 30px;">
        <label class="">合批信息:</label><br>
        <table class="table table-bordered table-condensed" id="tableIntegratedReelDisk">
            <tr>
                <th>reelCode</th>
                <th>packagingTime</th>
                <th>quantity</th>
                <th>dateCode</th>
                <th>partNumber</th>
                <th>fromReelCode</th>
                <th>combinedLotNumber</th>
                <th>quality</th>
            </tr>
            <tr>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
                <td><input type="text" /></td>
            </tr>
        </table>
    </div>
    <div style="padding-left:30px;"><a>打印</a>|<a>打印单个</a></div>
    <div class="form-inline" id="saveFinish" style="padding-left:300px;">
        <button type="button" class="btn btn-default">进站</button>
        <button type="button" class="btn btn-default">保存</button>
        <button type="button" class="btn btn-default">出站</button>
    </div>
</div>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>