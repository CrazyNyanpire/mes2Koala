<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-3 control-label">组合流程名称:</label>
        <div class="col-lg-6">
            <input name="name" style="display:inline; width:94%;" dataType="Require" class="form-control"  type="text" />
            <span class="required">*</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">流程节点:</label>
        <div class="col-lg-6">
            <table border="0" width="350">
                <tr>
                    <td width="30%" id="list1">
                    </td>
                    <td width="20%" align="center">
                        <button class='btn btn-primary btn-xs' type='button' id="add"><span class="glyphicon glyphicon-chevron-right"></span></button><br/><br/>
                        <button class='btn btn-danger btn-xs' type='button' id="delete"><span class="glyphicon glyphicon-chevron-left"></span></button>
                    </td>
                    <td width="30%" id="list2" name="listId">
                    </td>

                    <td width="20%" align="center" >
                        <button class='btn btn-primary btn-xs' type='button' id="up"><span class="glyphicon glyphicon-chevron-up"></span></button><br/><br/>
                        <button class='btn btn-primary btn-xs' type='button' id="down"><span class="glyphicon glyphicon-chevron-down"></span></button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>