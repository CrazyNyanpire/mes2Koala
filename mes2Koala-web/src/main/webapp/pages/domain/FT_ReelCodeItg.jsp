<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    <div class="form-group" style="margin-left:-50px;">
        <label class="col-lg-2 control-label">批号:</label>
        <div class="col-lg-4">
            <input style="display:inline; width:60%;" class="form-control"  type="text"  id="internalLotNumber" />
          <%--  <button type="button" class="btn btn-primary" id="lotSearch">批号查询</button>--%>
        </div>
        <label class="col-lg-2 control-label">Reel:</label>
        <div class="col-lg-4">
            <input  style="display:inline; width:60%;" class="form-control"  type="text"  id="reelcode" />
           <%-- <button type="button" class="btn btn-primary" id="reelSearch">Reel查询</button>--%>
        </div>
    </div>
<div style="display:block;">
    <div id="lotNumberTable" style="display:inline-block;width:250px;margin-left:80px">
    </div>
    <div id="reelTable" style="display:inline-block;width:250px;margin-left:100px">
    </div>
</div>
</form>
</body>
</html>