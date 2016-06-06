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
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="firstSheet" id="firstSheetStatus"
                                                     checked="checked"
        > <a
                href="${pageContext.request.contextPath}/ueditor/getSpecialFormEditorPage.koala?ftinfoId=${ftinfoId}&formType=firstSheet"
                target="_blank">首页</a>
        </div>
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="flowForm" id="flowFormID" checked="checked"
                                                     disabled> FLOW
        </div>
    </div>
    <div class="form-group">
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="summarySheet" id="summarySheetStatus"
                                                     onclick="javascript:this.value=this.checked"> <a
                href="${pageContext.request.contextPath}/ueditor/getSpecialFormEditorPage.koala?ftinfoId=${ftinfoId}&formType=summarySheet"
                target="_blank">Summary</a></div>
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="reelcodeSheet" id="reelcodeSheetStatus"
                                                     onclick="javascript:this.value=this.checked"> <a
                href="${pageContext.request.contextPath}/ueditor/getSpecialFormEditorPage.koala?ftinfoId=${ftinfoId}&formType=reelcodeSheet"
                target="_blank">ReelCode</a></div>

    </div>
    <div class="form-group">
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="recordSheet" id="recordSheetStatus"
                                                     onclick="javascript:this.value=this.checked"> <a
                    href="${pageContext.request.contextPath}/ueditor/getSpecialFormEditorPage.koala?ftinfoId=${ftinfoId}&formType=recordSheet"
                    target="_blank">记录表</a>
        </div>

        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="machineMaterialRecordSheet"
                                                     id="machineMaterialRecordSheetStatus"
                                                     onclick="javascript:this.value=this.checked"> <a
                    href="${pageContext.request.contextPath}/ueditor/getSpecialFormEditorPage.koala?ftinfoId=${ftinfoId}&formType=machineMaterialRecordSheet"
                    target="_blank">机台落料记录表</a>
        </div>
    </div>

    <div class="form-group">
        <div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="checkSheet" id="checkSheetStatus"
                                                     onclick="javascript:this.value=this.checked"> <a
                    href="${pageContext.request.contextPath}/ueditor/getSpecialFormEditorPage.koala?ftinfoId=${ftinfoId}&formType=checkSheet"
                    target="_blank">自主检查表</a>
        </div>

        <%--<div class="col-lg-3 col-lg-offset-2"><input type="checkbox" name="recordForm" id="checkbox"--%>
                                                     <%--onclick="javascript:this.value=this.checked"> <a--%>
                    <%--href="${pageContext.request.contextPath}/ueditor/getSpecialFormEditorPage.koala?ftinfoId=${ftinfoId}&formType=checkSheet"--%>
                    <%--target="_blank">checkbox</a>--%>
        <%--</div>--%>
    </div>

</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>