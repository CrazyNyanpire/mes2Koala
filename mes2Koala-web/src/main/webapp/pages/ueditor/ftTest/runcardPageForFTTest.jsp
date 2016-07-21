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
        td{
        	border:1px solid #333;
        }
        table{
        	margin:0 auto;
        	width:850px;
        	border-collapse: collapse;
        	page-break-after: auto;
        	
        }
    </style>
</head>
<body>
<div>
    <script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
</div>
<div id="btns" style="">
    <div>
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

        function insertHtml() {
            var str = '';

            $.ajax({
                url: '<%=contextPath %>/ueditor/getRuncardInfoByState.koala?ftLotId=${ftLotId}&specialFormStr=${specialFormStr}&state=${state}',
                type: 'GET',
                success: function (data) {
                    if (data.data == undefined){
                        alert('脏数据错误，请清理数据重新运行！');
                        return;
                    }
					var dialog = $("<div>");
					dialog.html(data.data);

					dialog.find(".barcodeArea").each(function(){
						var bValue = this.getAttribute("value");
						bValue.replace(/(^\s*)|(\s*$)/g, "");
						$(this).barcode(bValue, "code128",{barWidth:2, barHeight:40,output:'svg'});
					});

					str = dialog[0].innerHTML;  
					UE.getEditor('editor').execCommand('insertHtml', str);
					//$("body").html(str);
				},

                error: function (error) {
                    console.log('error');
                }
            });
        }
        
        function print() {
           UE.getEditor('editor').execCommand('print');
        }
        ue.addListener('ready', function () {
            insertHtml();
            $("#btns").hide();
        });
    </script>
</div>
</body>
</html>