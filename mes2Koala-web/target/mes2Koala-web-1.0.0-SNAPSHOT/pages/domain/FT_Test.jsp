<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        .td{
            width:60px;
        }
    </style>
</head>
<body>
<form class="form-horizontal">
    <div class="hidden">
        <input type="text" id="idID" name="id"/>
        <input type="text" id="nameID" name="name"/>
        <input type="text" id="orderID" name="turn"/>
        <input type="text" id="versionID" name="version"/>
        <input type="text" id="ftStateID" name="state"/>
        <input type="text" id="ftTestDTO_idID" name="ftTestDTO.id"/>
        <input type="text" id="ftTestDTO_versionID" name="ftTestDTO.version"/>
        <input type="text" id="ftTestDTO_eqcStateID" name="ftTestDTO.eqcState"/>
        <input type="text" id="ftTestDTO_ftStateID" name="ftTestDTO.state"/>
        <input type="text" id="ftTestDTO_latStateID" name="ftTestDTO.latState"/>
        <input type="text" id="ftTestDTO_rtStateID" name="ftTestDTO.rtState"/>
        <input type="text" id="ftTestDTO_orderInfoID" name="ftTestDTO.orderInfo"/>
    </div>
    <ul class="nav nav-tabs" id='connect'></ul>
    <div class="tab-content">
        <div id="ft" class="tab-pane fade active in">
            <table style="margin-left:30px;margin-top:10px;">
            </table>
            <div class="form-group" style="margin-left:-230px;margin-top:30px;">
                <label class="col-lg-3 control-label">备注:</label>
                <br />
                <div class="col-lg-9" style="margin-left:-28px;">
                    <textarea name="ftTestDTO.ftNote" style="display:inline; width:65%;" rows="5" class="form-control"  type="text"  id="ftTestDTO_ftNoteID"></textarea>
                </div>
            </div>
            <button type="button" class="btn btn-default hidden" id="ft_" name="save" style="margin-left: 300px;">保存</button>
        </div>
        <div id="rt" class="tab-pane fade">
            <table style="margin-left:30px;margin-top:10px;">
            </table>
            <div class="form-group" style="margin-left:-230px;margin-top:30px;">
                <label class="col-lg-3 control-label">备注:</label>
                <br />
                <div class="col-lg-9" style="margin-left:-28px;">
                    <textarea name="ftTestDTO.rtNote" style="display:inline; width:65%;" rows="5" class="form-control"  type="text"  id="ftTestDTO_rtNoteID"></textarea>
                </div>
            </div>
            <button type="button" class="btn btn-default hidden" id="rt_" name="save" style="margin-left: 300px;">保存</button>
        </div>
        <div id="eqc" class="tab-pane fade">
            <table style="margin-left:30px;margin-top:10px;">
            </table>
            <div class="form-group" style="margin-left:-230px;margin-top:30px;">
                <label class="col-lg-3 control-label">备注:</label>
                <br />
                <div class="col-lg-9" style="margin-left:-28px;">
                    <textarea name="ftTestDTO.eqcNote" style="display:inline; width:65%;" rows="5" class="form-control"  type="text"  id="ftTestDTO_eqcNoteID"></textarea>
                </div>
            </div>
            <button type="button" class="btn btn-default hidden" id="eqc_" name="save" style="margin-left: 300px;">保存</button>
        </div>
        <div id="lat" class="tab-pane fade">
            <table style="margin-left:30px;margin-top:10px;">
            </table>
            <div class="form-group" style="margin-left:-230px;margin-top:30px;">
                <label class="col-lg-3 control-label">备注:</label>
                <br />
                <div class="col-lg-9" style="margin-left:-28px;">
                    <textarea name="ftTestDTO.latNote" style="display:inline; width:65%;" rows="5" class="form-control"  type="text"  id="ftTestDTO_latNoteID"></textarea>
                </div>
                   </div>
            <button type="button" class="btn btn-default hidden" id="lat_" name="save" style="margin-left: 300px;">保存</button>
        </div>
        <div id="finalYield" class="tab-pane fade">
            <table style="margin-left:30px;margin-top:10px;">
                <tr>
                    <th></th>
                </tr>
                <tr>
                    <td class="td">数量统计:</td>
                </tr>
            </table>
            <div class="form-inline" style="margin-top:20px;">
                <button type="button" class="btn btn-default hidden" name="save" id="finalYield_" style="margin-left: 300px;">保存</button>
                <button type="button" class="btn btn-default hidden" id="end" style="margin-left: 10px;">出站</button>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>