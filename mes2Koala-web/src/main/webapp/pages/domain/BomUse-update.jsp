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
        <label class="col-lg-3 control-label">selected:</label>

        <div class="col-lg-9">
            <div class="btn-group select" id="selectedID"></div>
            <input type="hidden" id="selectedID_" name="selected"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">Bom:</label>
        <div class="col-lg-9">
            <div class="btn-group select" id="bomID"></div>
            <input name="bomTemplateDTO.id" type="hidden" id="bomID_"
                   dataType="Require"/> <span class="required">*</span>
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
    selectItems['selectedID'] = [
        {title: '请选择', value: ''},
        {title: '是', value: 'true'},
        {title: '否', value: 'false'}
    ];
    selectItems["bomID"] = [];
    $.ajax({
        async: false,
        url: '${pageContext.request.contextPath}/BomTemplate/findAllBomTemplate.koala',
        type: 'POST',
        dataType: 'json'
    }).done(function (msg) {
        contents = []
        for (var i = 0; i < msg.length; i++) {
            contents.push({
                title: msg[i]['name'],
                value: msg[i]['id']
            });
        }
        selectItems['bomID'] = contents;
    });
</script>
</body>
</html>