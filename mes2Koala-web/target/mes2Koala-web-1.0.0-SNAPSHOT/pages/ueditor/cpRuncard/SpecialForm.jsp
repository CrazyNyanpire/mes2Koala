<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>

<%
    String contextPath = request.getContextPath();
%>
<head>
    <title>UEditor</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" charset="utf-8" src="<%=contextPath %>/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=contextPath %>/ueditor/ueditor.all.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=contextPath %>/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script src="<%=contextPath %>/ueditor/third-party/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>


    <style type="text/css">
        div {
            width: 100%;
        }
    </style>

    <script type="text/javascript">
        //实例化编辑器
        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
        var ue = UE.getEditor('editor');

        function insertHtml() {
            var str = '';
            debugger
            $.ajax({
                async: false,
                url: '<%=contextPath %>/cpRuncardController/getSpecialForm.koala?cpinfoId=${cpinfoId}&formType=${formType}',
                type: 'GET',
                dataType: 'json'
            }).done(function (msg) {
                debugger
                str = msg['data'];
                debugger;
                UE.getEditor('editor').execCommand('insertHtml', str);
            });
        }


        ue.addListener('ready', function (ue) {
            insertHtml();
            UE.getEditor('editor').setDisabled([]);
        });

    </script>

</head>
<body>
<div style="text-align: center;padding-left: 20%;padding-top: 5%">
    <div>
        <script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
    </div>
</div>
</body>
</html>