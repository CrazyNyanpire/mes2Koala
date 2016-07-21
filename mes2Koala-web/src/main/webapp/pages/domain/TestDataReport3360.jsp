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

        .left,.right{
            display:inline-block;
            vertical-align:middle;
        }

        .left{
            width:450px;
            text-align:left; 
            float:left;
        }

        .right{
            width:450px;
            float:left;
            white-space:nowrap;
        }
        .down{
            width:1000px;
            white-space:nowrap;
            float:left;
            text-align:left; 
        }
        
        .wafer{
            width:1000px;
            max-height:400px;
            white-space:nowrap;
            overflow:auto; 
            _height:expression(this.scrollHeight > 400 ? "400px" : auto);
        }
        .lotid{
            max-height:200px;
            white-space:nowrap;
            overflow:auto; 
            _height:expression(this.scrollHeight > 200 ? "200px" : auto);
        }

        tr.change:hover
       {
           background-color:yellow
       }
       
       .transfer
       {
           background-color:#00CCCC
       }
    </style>
    <script type="text/javascript">
        var dialog;
        var info;
        var waferInfo;
        var customer;
        var testType;
        var device;
        var lotID;
        $(function () {
        	waferInfo = "";
            dialog = $("#<%=dialogId%>");
            dialog.find('#clearID').on('click', function () {//搜索产品型号
            	$('.waferInfo').html('');
            });
            var customerInfo = [{title: '请选择',
                value: ''}];
            $.ajax({
                url: '${pageContext.request.contextPath}/Customer/findCustomer.koala',
                type: 'GET',
                dataType: 'json'
            }).done(function (msg) {
                for (var i = 0; i < msg.length; i++) {
                	customerInfo.push({
                        title: msg[i]['number'],
                        value: msg[i]['number']
                    });
                }
                dialog.find('#customerID').select({
                    title: '请选择',
                    contents: customerInfo
                }).on('change', function () {
                    dialog.find('#customerID_').val($(this).getValue());
                });
            });
            dialog.find('#selectID').on('click', function () {
           	 customer = $("#customerID_").val();
           	 testType = $("#testTypeSelectID").val();
             device = $("#DeviceID").val();
             if (customer == "" || customer == null) {
            	 dialog.message({
                     type: 'warning',
                     content: '请选择客户信息'
                 })
                 return false;
             }
             if (testType == "" || testType == null) {
            	 dialog.message({
                     type: 'warning',
                     content: '请选择测试类型'
                 })
                 return false;
             }
             if (device == "" || device == null) {
            	 dialog.message({
                     type: 'warning',
                     content: '请填写Device'
                 })
                 return false;
             }
           	 $("#lotInfoBody").html('');
                $.post('${pageContext.request.contextPath}/TestDataReport/get3360LotInfo.koala?customer='+ customer + '&testType=' + testType + '&device=' + device).done(function (data) {
                    debugger;
                    if (data.success) {
                    	 var html = "";
                         var th = "<table class='table table-bordered' style='background-color:#fff;text-align:center;'><tr style='background-color: #CFF;' bordercolor='#0FFCFF'><td>LotID</td><td>创建时间</td></tr>";
                         html += th;
                         data = data.data;
                         info = data;
                         for (var i = 0 ; i < data['directoryName'].length ; i++) {
                             html += "<tr class='change' ondblclick='dblclickResolveFile(" + parseInt(i) + ")'><td>" + data['directoryName'][i] + "</td><td>" + data['timeStamp'][i] + "</td></tr>";
                         }
                         html += "</table>";
                         $("#lotInfoBody").html(html);
                         $('tr').click(function(){
                             $(this).addClass("transfer").siblings().removeClass("transfer");
                         });
                    } else {
                    	if (data.success == false || data.success != undefined ) {
                        	dialog.message({
                                type: 'error',
                                content: data.errorMessage
                            });
                        } else {
                        	dialog.message({
                                type: 'error',
                                content: data.actionError
                            });
                        }
                    }
                });
           });
            
            dialog.find('#exportExcelID').on('click', function () {//导出Excel
            	debugger;
            	if (waferInfo == null || waferInfo == "") {
            		dialog.message({
                        type: 'warning',
                        content: '请先双击右边目录选择批次'
                    })
            		return false;
            	}
            	data = {info3360: JSON.stringify(waferInfo)};
            	$.post('${pageContext.request.contextPath}/TestDataReport/exportExcel3360.koala', data).done(function (result) {
            		if (result.success) {
            			dialog.message({
                            type: 'success',
                            content: '导出成功'
                        });
                        var dialog1 = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">导出Excel</h4></div><div class="modal-body"><p id="downloadID"></p></div><div class="modal-footer"><button type="button" class="btn btn-success" data-dismiss="modal" id="save">确定</button></div></div></div></div>');
                        dialog1.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        }).find('#downloadID').html("导出成功！" + "<a style='margin-left:20px;'   href='" + result.data + "'>点击下载</a>");
                    } else {
                    	dialog.message({
                            type: 'error',
                            content: result.errorMessage
                        });
                    }
            	});
           });
        });
        
        function dblclickResolveFile(num) {
        	lotID = info['directoryName'][num];
        	$.get('${pageContext.request.contextPath}/TestDataReport/resolve3360File.koala?customer='+ customer + '&testType=' + testType + '&device=' + device + '&lotID=' + info['directoryName'][num]).done(function (data) {
                debugger;
                if (data.success) {
                	json = data.data;
                    waferInfo = json;
                    if(json[0]['dbin'] !=null) {
                    	$('.waferInfo').html('');
                		$('.waferInfo').append("<tr style='background-color: #CFF;' bordercolor='#0FFCFF' class='title'>"
                                + "<th>Device</th>"
                                + "<th>Program Name</th>"
                                + "<th>Revision</th>"
                                + "<th>Tester Number</th>"
                                + "<th>Prober Card</th>"
                                + "<th>LotID</th>"
                                + "<th>WID</th>"
                                + "<th>TestStep</th>"
                                + "<th>Operator</th>"
                                + "<th>Temperature</th>"
                                + "<th>Start Date</th>"
                                + "<th>Start Time</th>"
                                + "<th>End Date</th>"
                                + "<th>End Time</th>"
                                + "<th>Total Test Time</th>"
                                + "<th>GrossDie</th>"
                                + "<th>GoodDie</th>"
                                + "<th>BadDie</th>"
                                + "<th>Yield</th>"
                                + "</tr>");
                		for ( var a = 1 ; a <= json[0]['mapSize'] ; a++) {
                			$('.waferInfo').find('.title').append("<th>DBIN" + a + "</th>");
                		}
                        for (var i = 0 ; i < json.length ; i++) {
                        	$('.waferInfo').append("<tr class='dataInfo" + parseInt(i) + "'>"
                                    + "<td>" + json[i]['device'] + "</td>"
                                    + "<td>" + json[i]['programName'] + "</td>"
                                    + "<td>" + json[i]['revision'] + "</td>"
                                    + "<td>" + json[i]['testerNumber'] + "</td>"
                                    + "<td>" + json[i]['proberCard'] + "</td>"
                                    + "<td>" + json[i]['lotID'] + "</td>"
                                    + "<td>" + json[i]['wID'] + "</td>"
                                    + "<td>" + json[i]['testStep'] + "</td>"
                                    + "<td>" + json[i]['operator'] + "</td>"
                                    + "<td>" + json[i]['temperature'] + "</td>"
                                    + "<td>" + json[i]['startDate'] + "</td>"
                                    + "<td>" + json[i]['startTime'] + "</td>"
                                    + "<td>" + json[i]['endDate'] + "</td>"
                                    + "<td>" + json[i]['endTime'] + "</td>"
                                    + "<td>" + json[i]['totalTestTime'] + "</td>"
                                    + "<td>" + json[i]['grossDie'] + "</td>"
                                    + "<td>" + json[i]['goodDie'] + "</td>"
                                    + "<td>" + json[i]['badDie'] + "</td>"
                                    + "<td>" + json[i]['yield'] + "</td>"
                                    + "</tr>");
                        	for ( var a = 1 ; a <= json[0]['mapSize'] ; a++) {
                        		if ( a < 10) {
                        			$('.waferInfo').find('.dataInfo' + parseInt(i) + '').append("<td style='background-color:yellow;>" + json[i]['dbin']['DBIN00' + parseInt(a) + ''] + "</td>");
                        		} else if ( a >= 10 && a < 100) {
                        			$('.waferInfo').find('.dataInfo' + parseInt(i) + '').append("<td style='background-color:yellow;>" + json[i]['dbin']['DBIN0' + parseInt(a) + ''] + "</td>");
                        		} else {
                        			$('.waferInfo').find('.dataInfo' + parseInt(i) + '').append("<td style='background-color:yellow;>" + json[i]['dbin']['DBIN' + parseInt(a) + ''] + "</td>");
                        		}
                    		}
                        }
                    } else {
                    	$('.waferInfo').html('');
                		$('.waferInfo').append("<tr style='background-color: #CFF;' bordercolor='#0FFCFF' class='title'>"
                                + "<th>Wafer Summary Data</th>"
                                + "<th>Handler/Prober Name</th>"
                                + "<th>Testing time</th>"
                                + "<th>Device Name</th>"
                                + "<th>Lot ID</th>"
                                + "<th>Wafer ID</th>"
                                + "<th>Probe Card No</th>"
                                + "<th>Yield</th>"
                                + "<th>Pass</th>"
                                + "<th>Fail</th>"
                                + "<th>SW_PASS_CLASS2(2)</th>"
                                + "</tr>");
                		for (var a=0;a < json[0]['map']['listTitle'].length ; a++) {  
                			var prop = json[0]['map']['listTitle'][a];
                    		$('.waferInfo').find('.title').append("<th>" + prop.substring(0,prop.lastIndexOf("-TestItem")) + "</th>");
                        }
                		for (var i = 0 ; i < json.length ; i++) {
                        	$('.waferInfo').append("<tr class='dataInfo" + parseInt(i) + "'>"
                                    + "<td>" + json[i]['waferSummaryData'] + "</td>"
                                    + "<td>" + json[i]['map']['Handler/Prober Name'] + "</td>"
                                    + "<td>" + json[i]['testingDate'] + "</td>"
                                    + "<td>" + json[i]['map']['Device Name'] + "</td>"
                                    + "<td>" + json[i]['map']['Lot ID'] + "</td>"
                                    + "<td>" + json[i]['map']['Wafer ID'] + "</td>"
                                    + "<td>" + json[i]['map']['Probe Card No'] + "</td>"
                                    + "<td>" + json[i]['yield'] + "</td>"
                                    + "<td>" + json[i]['pass'] + "</td>"
                                    + "<td>" + json[i]['fail'] + "</td>"
                                    + "<td>" + json[i]['swPassClass2'] + "</td>"
                                    + "</tr>");
                        	for (var b=0;b < json[i]['map']['listValue'].length ; b++) {  
                        		var prop2 = json[i]['map']['listValue'][b];
                        		$('.waferInfo').find('.dataInfo' + parseInt(i) + '').append("<td style='background-color:yellow;'>" + prop2 + "</td>");
                            }
                        }
                		$('tr').click(function(){
                            $(this).addClass("transfer").siblings().removeClass("transfer");
                        });
                    }
                } else {
                	if (data.success == false || data.success != undefined ) {
                    	dialog.message({
                            type: 'error',
                            content: data.errorMessage
                        });
                    } else {
                    	dialog.message({
                            type: 'error',
                            content: data.actionError
                        });
                    }
                }
            });
        }

        function openFolder() {
            try {
                var Message = "Please select the folder path."; //选择框提示信息
                var Shell = new ActiveXObject("Shell.Application");
                var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
                if (Folder != null) {
                    Folder = Folder.items(); // 返回 FolderItems 对象
                    Folder = Folder.item(); // 返回 Folderitem 对象
                    Folder = Folder.Path; // 返回路径
                    if (Folder.charAt(Folder.length - 1) != "\\") {
                        Folder = Folder + "\\";
                    }
                    data = {directory: Folder}
                    $.post('${pageContext.request.contextPath}/TestDataReport/resolve3360File.koala',data).done(function (json) {
                		debugger;
                		json = json.data;
                		$('.waferInfo').html('');
                		$('.waferInfo').append("<tr style='background-color: #CFF;' bordercolor='#0FFCFF' class='title'>"
                                + "<th>Device</th>"
                                + "<th>Program Name</th>"
                                + "<th>Revision</th>"
                                + "<th>Tester Number</th>"
                                + "<th>Prober Card</th>"
                                + "<th>LotID</th>"
                                + "<th>WID</th>"
                                + "<th>TestStep</th>"
                                + "<th>Operator</th>"
                                + "<th>Temperature</th>"
                                + "<th>Start Date</th>"
                                + "<th>Start Time</th>"
                                + "<th>End Date</th>"
                                + "<th>End Time</th>"
                                + "<th>Total Test Time</th>"
                                + "<th>GrossDie</th>"
                                + "<th>GoodDie</th>"
                                + "<th>BadDie</th>"
                                + "<th>Yield</th>"
                                + "</tr>");
                		for ( var a = 1 ; a <= json['infoLists3360'][0]['mapSize'] ; a++) {
                			$('.waferInfo').find('.title').append("<th>DBIN" + a + "</th>");
                		}
                        for (var i = 0 ; i < json['infoLists3360'].length ; i++) {
                        	$('.waferInfo').append("<tr class='dataInfo" + parseInt(i) + "'>"
                                    + "<td>" + json['infoLists3360'][i]['device'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['programName'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['revision'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['testerNumber'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['proberCard'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['lotID'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['wID'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['testStep'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['operator'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['temperature'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['startDate'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['startTime'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['endDate'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['endTime'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['totalTestTime'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['grossDie'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['goodDie'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['badDie'] + "</td>"
                                    + "<td>" + json['infoLists3360'][i]['yield'] + "</td>"
                                    + "</tr>");
                        	for ( var a = 1 ; a <= json['infoLists3360'][0]['mapSize'] ; a++) {
                        		if ( a < 10) {
                        			$('.waferInfo').find('.dataInfo' + parseInt(i) + '').append("<td>" + json['infoLists3360'][i]['dbin']['DBIN00' + parseInt(a) + ''] + "</td>");
                        		} else if ( a >= 10 && a < 100) {
                        			$('.waferInfo').find('.dataInfo' + parseInt(i) + '').append("<td>" + json['infoLists3360'][i]['dbin']['DBIN0' + parseInt(a) + ''] + "</td>");
                        		} else {
                        			$('.waferInfo').find('.dataInfo' + parseInt(i) + '').append("<td>" + json['infoLists3360'][i]['dbin']['DBIN' + parseInt(a) + ''] + "</td>");
                        		}
                    		}
                        }
                	});
                } 
            }
            catch (e) {
                alert(e.message);
            }
        }
    </script>
</head>
<body>
<div class="userGrantAuthority" id=<%=dialogId%>>
    <div class="left">
        <div class="left-modal-body">信息选择：</div>
        <hr />
        <div class="form-group"  style="margin-left:5px;">
            <label class="control-label" style="width:60px;float:left;margin-top:5px;">客户信息:</label>
            <div style="margin-left:15px;float:left;">
                <div class="btn-group select" id="customerID"></div>
                <input name="customer" type="hidden" id="customerID_"/>
            </div>
        </div>
        <div class="form-group"  style="margin-left:5px;">
            <label class="control-label" style="margin-left:25px;width:60px;float:left;margin-top:5px;">测试类型:</label>
            <select id="testTypeSelectID" style="margin-left:15px;margin-top:-1px;width:60px;display:inline" class='form-control'>
                   <option selected="selected" class="form-control">请选择</option>
                   <option value="FT">FT</option>
                   <option value="CP">CP</option>
            </select>
        </div>
        <div class="form-group"  style="margin-left:5px;">
            <label class="control-label" style="width:60px;float:left;margin-top:5px;">Device:</label>
            <div style="margin-left:15px;float:left;">
                <input  class="form-control"  type= "text" name="device" id="DeviceID" style="width:200px;" />
            </div>
        </div>
        <div style="margin-left:5px;">
            <div style="margin-left:10px;float:left;">
                <input  class="btn btn-default" type= "button" name="select" id="selectID" value = "搜索"/>
                <input  class="btn btn-default" type= "button" name="export" id="exportExcelID" value = "导出Excel"/>
            </div>
        </div>
    </div>
    <div class="right" style="margin-left:15px;">
        <div class="right-modal-body" style="margin-left:-400px;">Lot信息：</div>
        <hr />
        <div id="lotInfoBody" style="width:450px;overflow:auto" class="lotid">
            <table class='table table-bordered waferInfo' style='background-color:#fff;text-align:center;'>
                <tr style='background-color: #CFF;' bordercolor='#0FFCFF'><td>LotID</td><td>创建时间</td></tr>
            </table>
        </div>
    </div>
    <br />
    <br />
    <br />
    <div class="down" style="margin-top:5px;">
        <div class="right-modal-body">Wafer信息：</div>
        <hr />
        <div class="wafer" id="waferInfoBody" style="margin-top:5px;overflow:auto">
            <table class='table table-bordered waferInfo' style='background-color:#fff;text-align:center;'>
            </table>
        </div>
    </div>
</div>
</body>
</html>
