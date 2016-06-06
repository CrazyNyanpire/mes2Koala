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
<form class="form-horizontal">
    <div class="form-horizontal">
        <div class="col-lg-11 form-group">
            <label class="">Reel Code:</label><br>
            <table class="table table-bordered table-condensed">
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
    </div>
    <div class="form-horizontal">
        <div class="col-lg-11 form-group">
            <button class="btn btn-default" type="button" id="packingNodePrintId">打印</button>
            <input class="" type="text" disabled>
            <button class="btn btn-default" type="button" id="separatePacking">分批</button>
            <button class="btn btn-default" type="button" id="itgPacking">合批</button>
            <button class="btn btn-default" type="button" hidden>撤回</button>
        </div>
    </div>

    <div class="form-horizontal">
        <div class="col-lg-11 form-group">
            <label class="">合批信息:</label><br>
            <table class="table table-bordered table-condensed">
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
    </div>
    </div>
</form>
<div class="form-inline" id="savePacking">
    <button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
    <button type="button" class="btn btn-default hidden" style="margin-left: 10px;">保存</button>
    <button type="button" class="btn btn-default hidden" style="margin-left: 10px;">出站</button>
</div>

</body>
</html>
