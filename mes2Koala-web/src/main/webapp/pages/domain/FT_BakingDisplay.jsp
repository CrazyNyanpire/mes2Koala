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
<div>
    <form class="form-horizontal">
        <div class="form-group" style="margin-top:20px;">
            <label class="col-lg-3 control-label">烤箱编号:</label>
            <div class="col-lg-3">
                <select class="form-control">
                    <option>请选择</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">烤箱温度:</label>
            <div class="col-lg-4">
                <input  class="form-control"  type="text" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">进炉时间:</label>
            <div class="col-lg-9">
                    <input type="text" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">限制时间:</label>
            <div class="col-lg-4">
                <input  class="form-control"  type="text" />小时
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">出炉时间:</label>
            <div class="col-lg-9">
              <input type="text" class="form-control"  >
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">数量统计:</label>
            <div class="col-lg-9">
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
    </form>
    <div class="form-inline" id="saveBaking">
        <button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
        <button type="button" class="btn btn-default" style="margin-left: 10px;">保存</button>
        <button type="button" class="btn btn-default" style="margin-left: 10px;">出站</button>
    </div>
</div>
</body>
</html>