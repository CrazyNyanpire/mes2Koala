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
    <div class="hidden">
        <input type="text" id="idID" name="id"/>
        <input type="text" id="nameID" name="name"/>
        <input type="text" id="orderID" name="turn"/>
        <input type="text" id="versionID" name="version"/>
        <input type="text" id="ftStateID" name="state"/>
        <input type="text" id="ftGuTestDTO_idID" name="ftGuTestDTO.id"/>
        <input type="text" id="ftGuTestDTO_versionID" name="ftGuTestDTO.version"/>
        <%--      <input type="text" id="ftGuTestDTO_standardResultID" name="ftGuTestDTO.standardResult"/>
              <input type="text" id="ftGuTestDTO_recordID" name="ftGuTestDTO.record"/>
              <input type="text" id="ftGuTestDTO_noxID" name="ftGuTestDTO.nox"/>--%>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">Customer Name:</label>
        <div class="col-lg-4">
            <input name="ftGuTestDTO.customerName" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftGuTestDTO_customerNameID" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">Golden Units Type:</label>
        <div class="col-lg-4">
            <input name="ftGuTestDTO.goldenUnitsType" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftGuTestDTO_goldenUnitsTypeID" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">Golden Units No:</label>
        <div class="col-lg-4">
            <input name="ftGuTestDTO.goldenUnitsNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftGuTestDTO_goldenUnitsNoID" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">使用次数:</label>
        <div class="col-lg-4">
            <input name="ftGuTestDTO.useTimes" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftGuTestDTO_useTimesID" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">使用产品型号:</label>
        <div class="col-lg-4">
            <input name="ftGuTestDTO.productNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftGuTestDTO_productNumberID" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">责任PE:</label>
        <div class="col-lg-4">
            <input name="ftGuTestDTO.pe" style="display:inline; width:94%;" class="form-control"  type="text"  id="ftGuTestDTO_peID" />
        </div>
    </div>
</form>
<div class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-2 control-label">Standard Result:</label>
        <div class="col-lg-4">
            <table id="ftGuTestDTO_standardResult" name="ftGuTestDTO.standardResult">
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
        <label class="col-lg-2 control-label">Record:</label>
        <div class="col-lg-9">
            <table id="ftGuTestDTO_record" name="ftGuTestDTO.record">
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
        <table class="col-lg-9 col-lg-offset-2" id="ftGuTestDTO_nox" name="ftGuTestDTO.nox">
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
    <button type="button" class="btn btn-default hidden" style="margin-left: 300px;">保存</button>
    <button type="button" class="btn btn-default hidden" style="margin-left: 10px;">出站</button>
</div>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>