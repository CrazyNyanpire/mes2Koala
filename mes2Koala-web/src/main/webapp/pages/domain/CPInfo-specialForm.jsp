<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    <input type="hidden" id="idID" name="id"/>
    <input type="hidden" id="versionID" name="version"/>

    <div class="form-group">
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="mcp_COVER1Sheet" id="mcp_COVER1SheetStatus"
                                                     onclick="javascript:this.value=this.checked"> <a
                href="${pageContext.request.contextPath}/cpRuncardController/getSpecialFormEditorPage.koala?cpinfoId=${cpinfoId}&formType=MCP_COVER1SHEET"
                target="_blank">首页</a></div>
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="cp1Sheet" id="cp1SheetStatus"
                                                     onclick="javascript:this.value=this.checked"> <a
                href="${pageContext.request.contextPath}/cpRuncardController/getSpecialFormEditorPage.koala?cpinfoId=${cpinfoId}&formType=CP1SHEET"
                target="_blank">针测记录表1</a></div>

    </div>
    <div class="form-group">
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="cp2Sheet" id="cp2SheetStatus"
                                                     onclick="javascript:this.value=this.checked"> <a
                href="${pageContext.request.contextPath}/cpRuncardController/getSpecialFormEditorPage.koala?cpinfoId=${cpinfoId}&formType=CP2SHEET"
                target="_blank">针测记录表2</a>
        </div>

        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="sheet1"
                                                     id="sheet1Status"
                                                     onclick="javascript:this.value=this.checked"> <a
                href="${pageContext.request.contextPath}/cpRuncardController/getSpecialFormEditorPage.koala?cpinfoId=${cpinfoId}&formType=SHEET1"
                target="_blank">CheckList1</a>
        </div>
    </div>

    <div class="form-group">
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="sheet2" id="sheet2Status"
                                                     onclick="javascript:this.value=this.checked"> <a
                href="${pageContext.request.contextPath}/cpRuncardController/getSpecialFormEditorPage.koala?cpinfoId=${cpinfoId}&formType=SHEET2"
                target="_blank">CheckList2</a>
        </div>

        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="map_SHIFT1" id="map_SHIFT1Status"
                                                     onclick="javascript:this.value=this.checked"> <a
                href="${pageContext.request.contextPath}/cpRuncardController/getSpecialFormEditorPage.koala?cpinfoId=${cpinfoId}&formType=MAP_SHIFT1SHEET"
                target="_blank">产品型号对照表</a>
        </div>
    </div>

</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>