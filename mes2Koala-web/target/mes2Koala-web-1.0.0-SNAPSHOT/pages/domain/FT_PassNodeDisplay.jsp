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
    <div class="form-group" style="margin-top: 50px">
        <label class="col-lg-2 control-label">测试结果:</label>
        <div class="col-lg-4" id="ftOQCDTO_resultID">
            <input type="radio" value="1" />Pass&nbsp;&nbsp;
            <input type="radio" value="0" />Fail
        </div>
    </div>
</form>
<div class="form-inline" id="saveOQC">
    <button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
    <button type="button" class="btn btn-default" style="margin-left: 10px;">保存</button>
    <button type="button" class="btn btn-default" style="margin-left: 10px;">出站</button>
</div>
</body>
</html>
