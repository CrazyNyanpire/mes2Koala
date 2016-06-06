<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <%@ page import="java.util.Date"%>
    <% String leftGridId = "leftGrid_" + new Date().getTime();
        String dialogId = "dialog_" + new Date().getTime();
        String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
    %>
    <style>
        .userGrantAuthority {
            display: block;
            text-align: center;
        }

        .left, .middle, .right{
            display:inline-block;
            vertical-align:middle;
        }

        .left{
            width:450px;
            text-align:left; 
            float:left;
        }

        .middle{
            width:85px;
            text-align:center;
            position:relative;
            top:100px;
        }

        .right{
            width:650px;
            white-space:nowrap;
        }

        tr.change:hover
       {
           background-color:#00CCCC
       }
    </style>
    <script type="text/javascript">
        var dialog;
        var info;
        var waferInfo;
        var paramDirectory;
        var paramDirectoryName;
        $(function () {
            dialog = $("#<%=dialogId%>");
            var contentsTskDirectory = [{title: '请选择',
                                         value: ''}];
            $.ajax({
                url: '${pageContext.request.contextPath}/SystemDictionary/getByType/TskDirectory.koala',
                type: 'GET',
                dataType: 'json'
            }).done(function (msg) {
                for (var i = 0; i < msg.length; i++) {
                	contentsTskDirectory.push({
                        title: msg[i]['label'],
                        value: msg[i]['value']
                    });
                }

                dialog.find('#tskDirectoryID').select({
                    title: '请选择',
                    contents: contentsTskDirectory
                }).on('change', function () {
                    dialog.find('#tskDirectoryID_').val($(this).getValue());
                });
            });
            
            dialog.find('#selectID').on('click', function () {//搜索产品型号
            	 if ($("#tskDirectoryID_").val() == "" ) {
            		 dialog.message({
                         type: 'warning',
                         content: '请选择主目录'
                     })
                     return false;
            	 }
            	 paramDirectory = $("#tskDirectoryID_").val();
                 paramDirectoryName = $("#directoryNameID").val();
                 debugger;
            	 $("#tskFileNameBody").html('');
                 $.get('${pageContext.request.contextPath}/CPLot/getTskFileNames.koala?upDown='+ paramDirectory + '&directory=' + paramDirectoryName).done(function (data) {
                     debugger;
                	 var html = "";
                     var th = "<table class='table table-bordered' style='background-color:#fff;margin-top:10px;text-align:center;'><tr height='30px'><td>目录名称</td></tr>";
                     html += th;
                     data = data.data;
                     info = data;
                     for (var i = 0 ; i < data['directoryName'].length ; i++) {
                         html += "<tr height='30px' class='change' ondblclick='dblclickResolveFile(" + parseInt(i) + ")'><td>" + data['directoryName'][i] + "</td></tr>";
                     }
                     html += "</table>";
                     $("#tskFileNameBody").html(html);
                 });
            });
            
            //兼容IE8 IE9
            if(window.ActiveXObject){
                if(parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10){
                    dialog.trigger('shown.bs.modal');
                }
            }
        });
        
        function dblclickResolveFile(num) {
        	paramDirectoryName = info['directoryName'][num];
        	$.get('${pageContext.request.contextPath}/CPLot/resolveFile.koala?upDown='+ paramDirectory + '&directoryName=' + info['directoryName'][num]).done(function (data) {
        		debugger;
                data = data.data;
                waferInfo = data;
                $('.waferInfo').find('.change').html('');
                for (var i = 0 ; i < data.length ; i++) {
                	$('.waferInfo').append("<tr class='change' ondblclick='dblclickMapCreate(" + parseInt(i) + ")'>"
                            + "<td>" + data[i]['device_Name'] + "</td>"
                            + "<td>" + data[i]['index_X'] + "</td>"
                            + "<td>" + data[i]['index_Y'] + "</td>"
                            + "<td>" + data[i]['operator_Name'] + "</td>"
                            + "<td>" + data[i]['wafer_ID'] + "</td>"
                            + "<td>" + data[i]['lot_No'] + "</td>"
                            + "<td>" + data[i]['start_Time'] + "</td>"
                            + "<td>" + data[i]['end_Time'] + "</td>"
                            + "<td>" + data[i]['load_Time'] + "</td>"
                            + "<td>" + data[i]['unLoad_Time'] + "</td>"
                            + "<td>" + data[i]['total_Dice'] + "</td>"
                            + "<td>" + data[i]['total_Pass_Dice'] + "</td>"
                            + "<td>" + data[i]['total_Fail_Dice'] + "</td>"
                            + "<td>" + data[i]['total_Yield'] + "</td>"
                            + "<td>" + data[i]['passbin2'] + "</td>"
                            + "<td>" + data[i]['passbin3'] + "</td>"
                            + "<td>" + data[i]['passbin4'] + "</td>"
                            + "<td>" + data[i]['acetec_Vi_Fail'] + "</td>"
                            + "<td>" + data[i]['client_Vi_Fail'] + "</td>"
                            + "<td>" + data[i]['good_Die'] + "</td>"
                            + "<td>" + data[i]['client_Final_Good_Die'] + "</td>"
                            + "<td>" + data[i]['pass_Dice_Result'] + "</td>"
                            + "<td>" + data[i]['client_Vi_Fail_Result'] + "</td>"
                            + "<td>" + data[i]['acetec_Vi_Fail_Result'] + "</td>"
                            + "<td>" + data[i]['acetec_Final_Good_Die_Result'] + "</td>"
                            + "<td>" + data[i]['client_Acetec_Vi_Fail'] + "</td>"
                            + "<td>" + data[i]['client_Client_Vi_Fail'] + "</td>"
                            + "<td>" + data[i]['client_Pass_Dice'] + "</td>"
                            + "</tr>");
                }
            });
        }
        
        function dblclickMapCreate11(num) {
        	$.get('${pageContext.request.contextPath}/CPLot/mapCreate.koala?upDown='+ paramDirectory + '&directoryName=' + paramDirectoryName+ '&fileName=' + waferInfo[num]['fileName']).done(function (data) {
        		debugger;
        		data = data.data;
        		var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1000px;height:1000px">'
                        + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                        + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                        + '<h4 class="modal-title">分批</h4></div><div class="modal-body">'
                        + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                        + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                        + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                        + '</div></div>');
        		$.get('/html/map.html').done(function (html) {
        		  dialog.find('.modal-body').html(html);
        		  dialog.find('.table').html(data);
        		  initPage(dialog.find('form'));
        		  dialog.modal({
                    keyboard: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    }
                });
             });
        	});
        }
        
        function dblclickMapCreate(num) {
        	$.get('${pageContext.request.contextPath}/CPLot/mapCreate.koala?upDown='+ paramDirectory + '&directoryName=' + paramDirectoryName+ '&fileName=' + waferInfo[num]['fileName']).done(function (data) {
        		debugger;
        		data = data.data;
        		OpenWindow = window.open('/html/map.html');
        		OpenWindow.document.write(data);
        		OpenWindow.document.close();
        		//window.open ('map.html', 'newwindow', 'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
        	});
        }
        
        function initPage(form) {
            form.find('.form_datetime').datetimepicker({
                language: 'zh-CN',
                format: "yyyy-mm-dd",
                autoclose: true,
                todayBtn: true,
                minView: 2,
                pickerPosition: 'bottom-left'
            }).datetimepicker('setDate', new Date());//加载日期选择器
        }
    </script>
</head>
<body>
<div class="userGrantAuthority" id=<%=dialogId%>>
    <div class="left">
        <div class="left-modal-body">TSK文件目录信息：</div>
        <hr />
        <div class="form-group"  style="margin-left:5px;">
            <label class="control-label" style="width:60px;float:left;margin-top:5px;">主目录:</label>
            <div style="margin-left:15px;float:left;">
                <div class="btn-group select" id="tskDirectoryID"></div>
                <input name="tskDirectory" type="hidden" id="tskDirectoryID_"/>
            </div>
        </div>
        <br />
        <br />
        <div class="form-group"  style="margin-left:5px;">
            <label class="control-label" style="width:60px;float:left;margin-top:5px;">目录名称:</label>
            <div style="margin-left:15px;float:left;">
                <input  class="form-control"  type= "text" name="directoryName" id="directoryNameID" style="width:220px;" />
                <input  class="btn btn-default" type= "button" name="select" id="selectID" value = "搜索"/>
            </div>
        </div>
        <div class="col-lg-10" id="tskFileNameBody" style="margin-top:5px;height: 450px; overflow:auto">
            <table class='table table-bordered' style='background-color:#fff;margin-top:10px;text-align:center;'>
                <tr height='30px'><td>目录名称</td></tr>
            </table>
        </div>
    </div>
    <div class="right">
        <div class="right-modal-body" style="margin-left:-500px;">Wafer信息：</div>
        <hr />
        <br />
        <div class="col-lg-10" id="waferInfoBody" style="width:650px;margin-top:5px;overflow:auto">
            <table class='table table-bordered waferInfo' style='background-color:#fff;margin-top:10px;text-align:center;'>
                <tr height='30px' style='background-color: #CFF;' bordercolor='#0FFCFF'>
                    <th>Device Name</th>
                    <th>Index X</th>
                    <th>Index Y</th>
                    <th>Operator Name</th>
                    <th>Wafer ID</th>
                    <th>Lot No</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Load Time</th>
                    <th>Unload Time</th>
                    <th>Total Dice</th>
                    <th>Total Pass Dice</th>
                    <th>Total Fail Dice</th>
                    <th>Total Yield</th>
                    <th>pass bin2</th>
                    <th>pass bin3</th>
                    <th>pass bin4</th>
                    <th>内部补偿</th>
                    <th>外部补偿</th>
                    <th>Good Die</th>
                    <th>Pass Dice结果</th>
                    <th>内部补偿结果</th>
                    <th>外部补偿结果</th>
                    <th>最终Pass Dice结果</th>
                    <th>客户Pass Dice</th>
                    <th>客户内部补偿</th>
                    <th>客户外部补偿</th>
                    <th>客户最终Pass Dice</th>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>
