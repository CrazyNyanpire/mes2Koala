<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        .td{
            width:50px;
            margin-right:10px;
        }
    </style>
</head>
<body>
<form class="form-horizontal" style="margin-top:20px;">
    <div class="form-group">
        <label class="col-lg-3 control-label">Customer Name:</label>
        <div class="col-lg-4">
            <input  class="form-control"  type="text" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Golden Units Type:</label>
        <div class="col-lg-4">
            <input class="form-control"  type="text"  />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Golden Units No:</label>
        <div class="col-lg-4">
            <input  class="form-control"  type="text" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">使用次数:</label>
        <div class="col-lg-4">
            <input class="form-control"  type="text"   />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">使用产品型号:</label>
        <div class="col-lg-4">
            <input class="form-control"  type="text"  />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">责任PE:</label>
        <div class="col-lg-4">
            <input class="form-control"  type="text"  />
        </div>
    </div>
</form>
<div class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-3 control-label">Standard Result:</label>
        <div class="col-lg-4">
            <table class="table table-inverse">
                <tr>
                    <th>sum</th>
                    <th>other</th>
                    <th>loss</th>
                    <th>yield</th>
                    <%--<th>inTape</th>--%>
                    <th>backUp</th>
                    <th>markF</th>
                    <th>pass</th>
                    <th>fail</th>
                </tr>
                <tr>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <%--<td><input type="text" class="td" /></td>--%>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                </tr>
            </table>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Record:</label>
        <div class="col-lg-9">
            <table class="table table-inverse">
                <tr>
                    <th>sum</th>
                    <th>other</th>
                    <th>loss</th>
                    <th>yield</th>
                    <%--<th>inTape</th>--%>
                    <th>backUp</th>
                    <th>markF</th>
                    <th>pass</th>
                    <th>fail</th>
                </tr>
                <tr>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <%--<td><input type="text" class="td" /></td>--%>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                </tr>
                <tr>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <%--<td><input type="text" class="td" /></td>--%>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                </tr>
                <tr>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <%--<td><input type="text" class="td" /></td>--%>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                </tr>
                <tr>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <%--<td><input type="text" class="td" /></td>--%>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                    <td><input type="text" class="td" /></td>
                </tr>
            </table>
            <button type="button" id="recordAdd">+</button>
        </div>
    </div>
    <hr />
    <div class="form-group">
        <table class="col-lg-9 col-lg-offset-3 table table-inverse" >
            <tr>
                <th></th>
                <th>NoX</th>
                <th>标准值</th>
                <th>Test</th>
                <th></th>
                <th>NoX</th>
                <th>标准值</th>
                <th>Test</th>
            </tr>
            <tr>
                <td>#1</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td>#6</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
            </tr>
            <tr>
                <td>#2</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td>#7</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
            </tr>
            <tr>
                <td>#3</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td>#8</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
            </tr>
            <tr>
                <td>#4</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td>#9</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
            </tr>
            <tr>
                <td>#5</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td>#10</td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
                <td><input type="text" class="td" /></td>
            </tr>
        </table>
    </div>
</div>
<div class="form-inline" id="saveGuTest">
    <button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
    <button type="button" class="btn btn-default" style="margin-left: 10px;">保存</button>
    <button type="button" class="btn btn-default" style="margin-left: 10px;">出站</button>
</div>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>