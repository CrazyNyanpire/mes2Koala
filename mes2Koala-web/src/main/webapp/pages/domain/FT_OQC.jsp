<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/11 0011
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form class="form-horizontal">
    <div class="hidden">
        <input type="text" id="idID" name="id"/>
        <input type="text" id="nameID" name="name"/>
        <input type="text" id="orderID" name="turn"/>
        <input type="text" id="versionID" name="version"/>
        <input type="text" id="ftStateID" name="state"/>
        <input type="text" id="ftOQCDTO_idID" name="ftPassNodeDTO.id"/>
        <input type="text" id="ftOQCDTO_versionID" name="ftPassNodeDTO.version"/>
    </div>
    <div class="form-group" style="margin-top: 50px">
        <label class="col-lg-2 control-label">测试结果:</label>
        <div class="col-lg-4" id="ftOQCDTO_resultID">
            <input type="radio" name="ftPassNodeDTO.result" value="1" />Pass&nbsp;&nbsp;
            <input type="radio" name="ftPassNodeDTO.result" value="0" />Fail
        </div>
    </div>
</form>
<div class="form-inline" id="saveOQC">
    <button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
    <button type="button" class="btn btn-default hidden" style="margin-left: 300px;">保存</button>
    <button type="button" class="btn btn-default hidden" style="margin-left: 10px;">出站</button>
</div>
</body>
</html>
