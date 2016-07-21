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
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-barcode.min.js"></script>


    <style type="text/css">
        div {
            width: 100%;
        }
    </style>
</head>
<body>
<div>
    <script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
</div>
<div id="btns" style="">
    <div>
        <button onclick="save()">保存内容</button>
        <button onclick="parent.closemodalwindow()">关闭</button>
    </div>
</div>

<div id="printId">
    <button onclick="print()">打印</button>
</div>

<div>
    <script type="text/javascript">
        //实例化编辑器
        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
        var ue = UE.getEditor('editor');
        function save() {
            $.ajax({
                async: false,
                url: '<%=contextPath %>/ueditor/isRuncardInfoSigned.koala?ftinfoId=${ftinfoId}',
                type: 'GET',
                dataType: 'json'
            }).done(function (msg) {
                success = msg['success'];
                if (success == true) {
                    alert("runcard已经签核，不能修改！");
                } else {
                    //获取文本中的数据并向服务器端发送
                    var content = UE.getEditor('editor').getContent();
                    var json = {
                        'data': content,
                        'ftinfoId':${ftinfoId},
                        'currentSite': "${currentSite}",
                        'totalSites': "${totalSites}"
                    }
                    $.ajax({
                        type: 'POST',
                        url: '<%=contextPath %>/ueditor/saveRuncard.koala',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify(json),
                        dataType: 'json',
                        success: function (data) {
                            alert("保存成功");
                            parent.closemodalwindow();
                        },

                        error: function (data) {
                            alert("保存失败！" + data);
                        }
                    });
                }
            });

        }
        function print() {
            var readOnly = '${currentSite}';

            if (readOnly === 'ALL') {
               var url = '<%=contextPath %>/ueditor/isRuncardInfoSigned.koala?ftinfoId=${ftinfoId}';
            } else if (readOnly === "BeforeReplaced" || readOnly === "AfterReplaced"){
                var url = '<%=contextPath %>/ueditor/isRuncardInfoSigned2.koala?ftinfoId=${ftinfoId}';
            }
            //打印权限控制
            $.ajax({
                async: false,
                url: url,
                type: 'GET',
                dataType: 'json'
            }).done(function (msg) {
                debugger
                success = msg['success'];
                if (success == true) {
                    UE.getEditor('editor').execCommand('print');

                } else {
                    alert("签核未完成！");
                    return;
                }
            });
        }
        function insertHtml() {
            var str = '';
            $.ajax({
                async: false,
                url: '<%=contextPath %>/ueditor/getRuncardInfo.koala?ftinfoId=${ftinfoId}&currentSite=${currentSite}',
                type: 'GET',
                dataType: 'json'
            }).done(function (msg) {

				var dialog = $("<div>");
				dialog.html(msg.data);

				dialog.find(".barcodeArea").each(function(){
					var bValue = this.getAttribute("value");
					$(this).barcode(bValue, "code128",{barWidth:2, barHeight:40,output:'svg'});
				});

                //str = msg['data'];
				str = dialog[0].innerHTML; 
				debugger;
                UE.getEditor('editor').execCommand('insertHtml', str);
                UE.getEditor('editor').iframe.contentDocument.title="RunCard";
             });
        }
        ue.addListener('ready', function () {
            insertHtml();
            var readOnly = '${currentSite}';
            if (readOnly === 'ALL') {
                UE.getEditor('editor').setDisabled([]);
                $("#btns").hide();
            } else {
                $("#printId").hide();
            }
            if (readOnly === "BeforeReplaced" || readOnly === "AfterReplaced"){
                UE.getEditor('editor').setDisabled([]);
                $("#btns").hide();
                $("#printId").show();
            }
        });
    </script>
</div>
</body>
</html>