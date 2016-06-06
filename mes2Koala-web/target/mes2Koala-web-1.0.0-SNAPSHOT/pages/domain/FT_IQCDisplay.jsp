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
<div>
    <form class="form-horizontal" contenteditable="false">
        <div class="form-group" style="margin-top:20px;">
            <label class="col-lg-3 control-label">Mark:</label>
            <div class="col-lg-4">
                <input  class="form-control"  type="text"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">数量统计:</label>
            <div class="col-lg-4">
                <table class="table table-inverse">
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
                        <td><input type="text" class="td" /></td>
                        <td><input type="text" class="td" /></td>
                        <td><input type="text" class="td" /></td>
                        <td><input type="text" class="td" /></td>
                        <td><input type="text" class="td" /></td>
                        <td><input type="text" class="td" /></td>
                        <td><input type="text" class="td" /></td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">净重(g):</label>
            <div class="col-lg-4">
                <input class="form-control"  type="text"  />
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">毛重(g):</label>
            <div class="col-lg-4">
                <input class="form-control"  type="text" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">reelCode导入:</label>
            <div class="col-lg-4">
                <input name="ftIQCDTO.reelCode" class="form-control"  type="file"  id="ftIQCDTO_reelCodeID" />
            </div>
        </div>
    </form>
    <div class="form-inline" id="saveIQC">
        <button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
        <button type="button" class="btn btn-default" style="margin-left: 10px;">保存</button>
        <button type="button" class="btn btn-default" style="margin-left: 10px;">出站</button>
    </div>
</div>
<script type="text/javascript">
    var selectItems = {};

</script>
</body>
</html>