<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form>
    <input type="hidden" id="idID" name="id"/>
    <input type="hidden" id="versionID" name="version"/>
    <table style="font-size: 130%;">
        <tr>
            <td class="col-lg-3"><input type="checkbox" name="indexForm" id="indexFormID" checked disabled> 首页</td>
            <td class="col-lg-3"><input type="checkbox"  name="flowForm" id="flowFormID" checked disabled> FLOW</td>
        </tr>
        <tr>
            <td class="col-lg-3"><input type="checkbox" name="summaryForm"  id="summaryFormID" onclick="javascript:this.value=this.checked"> Summary</td>
            <td class="col-lg-3"><input type="checkbox" name="lossForm" id="lossFormID" onclick="javascript:this.value=this.checked"> 机台落料表</td>
        </tr>
        <tr>
            <td class="col-lg-3"><input type="checkbox" name="sizeForm" id="sizeFormID" onclick="javascript:this.value=this.checked"> 尺寸记录表</td>
            <td class="col-lg-3"><input type="checkbox" name="reelcodeForm" id="reelcodeFormID" onclick="javascript:this.value=this.checked"> ReelCode</td>
        </tr>
        <tr>
            <td class="col-lg-3"><input type="checkbox" name="checkSelfForm" id="checkSelfFormID" onclick="javascript:this.value=this.checked"> 自主检查表</td>
            <td class="col-lg-3"><input type="checkbox" name="checkBoxForm" id="checkBoxFormID" onclick="javascript:this.value=this.checked" > Checkbox</td>
        </tr>
</table>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>