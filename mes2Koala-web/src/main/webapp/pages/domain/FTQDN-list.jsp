<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script type="text/javascript">
function requestParamToJson(paramArray)
{
	var jsonObj={};
	$(paramArray).each(function()
		{
			jsonObj[this.name]=this.value;
		});
	console.log(jsonObj);
	return jsonObj;
}
function ajaxsubmit()
{
	var form = $("form[name=upload]");
	var options  = {    
        url: "/FileUpload/upload.koala",    
        type:'post',
        dataType : 'xml',
        beforeSubmit:function(){
        	var filename=$("#tablefile").val();
        	var filearray=filename.split('.');
        	if(filename.length>80)
    		{
        		alert("文件名过长");
        		return false;
    		}
        	if(filearray[1]!="jpg"&&filearray[1]!="png"&&filearray[1]!="xls"&&filearray[1]!="docx"&&filearray[1]!="doc"&&filearray[1]!="xlsx"&&filearray[1]!="txt")
    		{
        		debugger;
        		alert("文件格式不正确，请上传图片或文档格式的文件");
        		return false;
    		}
        	if(filename==""){
        		alert("上传文件不能为空");
        		return false;
        	}
        	
        },
        success:function(data)    
        {
        	debugger;
        	data=eval('(' + data['body']['innerText'] + ')');
        	if(data['data']==""||data['data']==null)
       		{
        		$(".modal-body").message({
                    type: 'error',
                    content: data['errorMessage']
                });
       		}
        	else{
        		$(".modal-body").message({
                    type: 'success',
                    content: "上传成功"
                });
        	$("#filelist").empty();
        	$("#filelist").append("<p>"+data['data'].split("/")[data['data'].split("/").length-1]+"<a style='margin-left:20px;' download  href='"+data['data']+"'>下载</a><input name='attachment' style='display:none;' value='"+data['data']+"'></p>");
        	}
        },
        error : function(result) {
			var data = eval("("+result.responseText+")");
			var message = data['actionError'];
            $(".modal-dialog").message({
                type: 'error',
                content: message
             });
		}
    };    
    form.ajaxSubmit(options);
}
var grid;
var form;
var _dialog;
$(function (){
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	   //
	    initSearchPanel:function(){
	        	            	                	            	                	            	                	                     var startTimeVal = form.find('#createTimestampID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#createTimestampID_end');
	                     var endTime = endTimeVal.parent();
	                     startTime.datetimepicker({
	                                        language: 'zh-CN',
	                                        format: "yyyy-mm-dd",
	                                        autoclose: true,
	                                        todayBtn: true,
	                                        minView: 2,
	                                        pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(){
	                                 endTime.datetimepicker('setStartDate', startTimeVal.val());
	                           });//加载日期选择器
	                     var yesterday = new Date();
	                     yesterday.setDate(yesterday.getDate() - 1);
	                     endTime.datetimepicker({
	                             language: 'zh-CN',
	                             format: "yyyy-mm-dd",
	                             autoclose: true,
	                             todayBtn: true,
	                             minView: 2,
	                             pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(ev){
	                                startTime.datetimepicker('setEndDate', endTimeVal.val());
	                           }).datetimepicker('setDate', new Date()).trigger('changeDate');//加载日期选择器
	                     startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
	                	            	                	            	                	            	                	                     var startTimeVal = form.find('#lastModifyTimestampID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#lastModifyTimestampID_end');
	                     var endTime = endTimeVal.parent();
	                     startTime.datetimepicker({
	                                        language: 'zh-CN',
	                                        format: "yyyy-mm-dd",
	                                        autoclose: true,
	                                        todayBtn: true,
	                                        minView: 2,
	                                        pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(){
	                                 endTime.datetimepicker('setStartDate', startTimeVal.val());
	                           });//加载日期选择器
	                     var yesterday = new Date();
	                     yesterday.setDate(yesterday.getDate() - 1);
	                     endTime.datetimepicker({
	                             language: 'zh-CN',
	                             format: "yyyy-mm-dd",
	                             autoclose: true,
	                             todayBtn: true,
	                             minView: 2,
	                             pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(ev){
	                                startTime.datetimepicker('setEndDate', endTimeVal.val());
	                           }).datetimepicker('setDate', new Date()).trigger('changeDate');//加载日期选择器
	                     startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
	                	            	                	            	                	            	                	                     var startTimeVal = form.find('#dateID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#dateID_end');
	                     var endTime = endTimeVal.parent();
	                     startTime.datetimepicker({
	                                        language: 'zh-CN',
	                                        format: "yyyy-mm-dd",
	                                        autoclose: true,
	                                        todayBtn: true,
	                                        minView: 2,
	                                        pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(){
	                                 endTime.datetimepicker('setStartDate', startTimeVal.val());
	                           });//加载日期选择器
	                     var yesterday = new Date();
	                     yesterday.setDate(yesterday.getDate() - 1);
	                     endTime.datetimepicker({
	                             language: 'zh-CN',
	                             format: "yyyy-mm-dd",
	                             autoclose: true,
	                             todayBtn: true,
	                             minView: 2,
	                             pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(ev){
	                                startTime.datetimepicker('setEndDate', endTimeVal.val());
	                           }).datetimepicker('setDate', new Date()).trigger('changeDate');//加载日期选择器
	                     startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
	                	            	                	            	                	            	                	                     var startTimeVal = form.find('#dealDateID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#dealDateID_end');
	                     var endTime = endTimeVal.parent();
	                     startTime.datetimepicker({
	                                        language: 'zh-CN',
	                                        format: "yyyy-mm-dd",
	                                        autoclose: true,
	                                        todayBtn: true,
	                                        minView: 2,
	                                        pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(){
	                                 endTime.datetimepicker('setStartDate', startTimeVal.val());
	                           });//加载日期选择器
	                     var yesterday = new Date();
	                     yesterday.setDate(yesterday.getDate() - 1);
	                     endTime.datetimepicker({
	                             language: 'zh-CN',
	                             format: "yyyy-mm-dd",
	                             autoclose: true,
	                             todayBtn: true,
	                             minView: 2,
	                             pickerPosition: 'bottom-left'
	                     }).on('changeDate', function(ev){
	                                startTime.datetimepicker('setEndDate', endTimeVal.val());
	                           }).datetimepicker('setDate', new Date()).trigger('changeDate');//加载日期选择器
	                     startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	                	            	        	     },
	    initGridPanel: function(){
	         var self = this;
	         var width = 110;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>查看详情</button>', action: 'modify'},
/* 	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'} */
	                    ],
	                url:"${pageContext.request.contextPath}/FTQDN/pageJson.koala",
	                columns: [
      	                         { title: 'LotNo', name: 'lotNo', width: width},
      	                         { title: '当前站点', name: 'flowNow', width: width},
      	                         { title: '客户名称', name: 'customerName', width: 200},
      	                         { title: '流程', name: 'flow', width: 220},
      	                         { title: 'QDN单号', name: 'qdnNo', width: width},
      	                         { title: '异常单类型', name: 'type', width: width},
      	                         { title: '异常单提交人', name: 'createEmployNo', width: width},
      	                         { title: '异常单提交日期', name: 'createTimestamp', width: width},
      	                         { title: '异常单状态', name: 'status', width: width ,render: function (rowdata, name, index)
	                                 {
      	                        	 	if(rowdata[name]==0)
  	                        	 		{
  	                        	 			return "待处理";
  	                        	 		}
      	                        	 	else if(rowdata[name]==1)
   	                        	 		{
   	                        	 			return "处理中";
   	                        	 		}
      	                        	 	else{
      	                        	 		return "已处理";
      	                        	 	}
                                 	 }
      	                         }
/* 	                         	 { title: '操作', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openDetailsPage(" + param + ")'>查看</a> ";
	                                     return h;
	                                 }
	                             } */
	                ]
	         }).on({
	                   'add': function(){
	                       self.add($(this));
	                   },
	                   'modify': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行修改'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行修改'
	                            })
	                            return;
	                        }
	                       self.modify(indexs[0], $this);
	                    },
	                   'delete': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                   type: 'warning',
	                                   content: '请选择要删除的记录'
	                            });
	                            return;
	                        }
	                        var remove = function(){
	                            self.remove(indexs, $this);
	                        };
	                        $this.confirm({
	                            content: '确定要删除所选记录吗?',
	                            callBack: remove
	                        });
	                   }
	         });
	    },
	    add: function(grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"style="width: 1172px;">'
	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
	        	+'<h4 class="modal-title">新增</h4></div><div class="modal-body">'
	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
	        	+'<button type="button" class="btn btn-success" id="save">发送</button></div></div>'
	        	+'</div></div>');
	        $.get('<%=path%>/FTQDNunlock.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
	              if(!Validator.Validate(dialog.find('form')[0],3))return;
	              $.post('${pageContext.request.contextPath}/FTQDN/add.koala', dialog.find('form').serialize()).done(function(result){
	                   if(result.success ){
	                        dialog.modal('hide');
	                        e.data.grid.data('koala.grid').refresh();
	                        e.data.grid.message({
	                            type: 'success',
	                            content: '保存成功'
	                         });
	                    }else{
	                        dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: result.actionError
	                        });
	                     }
	              });
	        });
	    },
	    modify: function(id, grid){
	        var self = this;
	        var QDNstatus="";
	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width: 1172px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button><button type="button" class="btn btn-success" id="save">发送</button></div></div></div></div>');
	        $.get('<%=path%>/FTQDNunlock.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
				dialog.find("input[name='cpProcess']").bind("click", function () {
		               	if($(this).attr("checked")=="checked")
		              		{
			               		$.ajax({
	                                async: false,
	                                url: '${pageContext.request.contextPath}/HoldMail/holdUsers.koala',
	                                data: {"dept": $(this).attr("index")},
	                                type: 'POST',
	                                dataType: 'json',
	                            }).done(function (msg) {
	                            	msg=msg['data'];
	                                $("select[name='toPerson']").empty();
	                                for (var i = 0; i < msg.length; i++) {
	                                    $("select[name='toPerson']").append("<option value='" + msg[i]['id'] + "'>" + msg[i]['name'] + "<option>");
	                                }
	                            });
		              		}
		               });
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function(json){
                       dialog.find("#QDN_writer").text(json['data']['userAccount']+"|"+json['data']['name']);
				   });
	               $.get( '${pageContext.request.contextPath}/FTQDN/get/' + id + '.koala').done(function(json){
	                        json = json.data;
	                        var elm;
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(elm.hasClass('select')){
	                                elm.setValue(json[index]);
	                            }else{
	                                elm.val(json[index]);
	                            }
	                            if(elm.tagName="SPAN"){
	                            	elm.text(json[index]);
	                            }
	                            var radio=dialog.find("input[name='"+index+"'][type='radio']");
	                            for(var a=0;a<radio.length;a++)
	                            	{
	                            		if(radio.eq(a).next().text()==json[index])
                            			{
	                            			radio.eq(a).attr("checked","checked");
                            			}
	                            	}
	                        }
	                        dialog.find("#productTypeID").text(json['ftLotDTO']['shipmentProductNumber']);
	                      	//动态生成SBL表格
	                      	if(json['qdnBinName']!=null)
                      		{
	                      		//var sbls=json['qdnBinName'];
	                      		
	                      		binName = json['qdnBinName'].split(",");
                                var tableHead = "<tr><th>BIN</th>";
                                tableHead += binName.filter(function (json) {
                                    return binName != "";
                                }).map(function (binName) {
                                    return "<th style='text-align:center;'>" + binName + "</th>";
                                }).join("");
                                tableHead += "<th>Yield</th><th>Pass</th><th>Loss</th><th>Scrap</th><th>Other</th><th>Sum</th></tr>";
                                var tableBody = "<tr><td>Num</td>";
                                tableBody += binName.filter(function (binName) {
                                    return binName != "";
                                }).map(function (binName) {
                                    return "<td><input style='width:100%;' type='text'/></td>";
                                }).join("");
                                tableBody += "<td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td></tr>"
                                dialog.find(".QDNBin tbody").empty();
                                dialog.find(".QDNBin tbody").append(tableHead + tableBody);
	                      		
/* 	    	               		var tableHead="<tr><th>BIN</th>";
	                   			for(var a in sbls.split(","))
	             				{
	                   				tableHead+="<th style='text-align:center;'>"+sbls.split(",")[a]+"</th>";
	             				}
	                   			tableHead+="<th>Yield</th><th>Pass</th><th>Loss</th><th>Scrap</th><th>Other</th><th>Sum</th></tr>";
	                   			var tableBody="<tr><td>Num</td>"
	                   			for(var a in sbls.split(","))
	             				{
	                   				tableBody+="<td><input style='width:100%;' type='text'/></td>";
	             				}
	                   			tableBody+="<td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td></tr>"
	                   			dialog.find(".QDNBin tbody").empty();
	                   			dialog.find(".QDNBin tbody").append(tableHead+tableBody); */
                      		}
	                        var qdnBin=json["qdnBin"].split(",");
	                        for(var a=0;a<dialog.find(".QDNBin input").length;a++){
	                        	dialog.find(".QDNBin input").eq(a).val(qdnBin[a]);
		                    }
	                     	
	                        debugger;
	                        dialog.find("#To"+json["toDepartment"]+"ID").attr("checked","checked");
	                        if(json["status"]==0||json["status"]==undefined)
	                        	{
	                        		dialog.find("#hideCustomer").attr("disabled","disabled");
	                        	}
	                        else if(json["status"]==1){
	                        	dialog.find("#hideDepartment").attr("disabled","disabled");
	                        }
	                        else{
	                        	dialog.find("#hideCustomer").attr("disabled","disabled");
	                        	dialog.find("#hideDepartment").attr("disabled","disabled");
	                        	dialog.find(".QDN_footer").remove();
	                        	dialog.find("#save").remove();
	                        }
	                        QDNstatus=json["status"];
	                        
	                });
	                dialog.modal({
	                    keyboard:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    var formjson=dialog.find('form').serializeArray();
	                    formjson=requestParamToJson(formjson);
	                    var postJson={};
	                    var postJsonStr="";
	                    if(QDNstatus=="0"||QDNstatus==undefined)
                    	{
	                    	postJson['suggestion']=$("input[name='suggestion']:checked").next().text();
	                    	postJson['reason']=formjson['reason'];
	                    	postJson['internalDealNote']=formjson['internalDealNote'];
	                    	postJson['internalDeal']=$("input[name='internalDeal']:checked").next().text();
	                    	postJsonStr="suggestion="+postJson['suggestion']+"&reason="+postJson['reason']+"&internalDealNote="+postJson['internalDealNote']+"&internalDeal="+postJson['internalDeal'];
                    	}
	                    else if(QDNstatus=="1")
                    	{
	                    	postJson['customerDeal']=$("input[name='customerDeal']:checked").next().text();
	                    	postJson['customerDealNote']=formjson['customerDealNote'];
	                    	postJson['QASuggestion']=$("input[name='qaSuggestion']:checked").next().text();
	                    	postJsonStr="customerDeal="+postJson['customerDeal']+"&customerDealNote="+postJson['customerDealNote']+"&QASuggestion="+postJson['QASuggestion'];
	                    }
	                    debugger;
	                    postJsonStr+="&status="+formjson['status']+"&toPerson="+formjson['toPerson']+"&attachment="+formjson['attachment']+"&version="+formjson['version'];
	                    $.post('${pageContext.request.contextPath}/FTQDN/dispose.koala?id='+id, postJsonStr).done(function(result){
	                        if(result.success){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '保存成功'
	                            });
	                        }else{
	                            dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: result.actionError
	                            });
	                        }
	                    });
	                });
	        });
	    },
	    initPage: function(form){
	              form.find('.form_datetime').datetimepicker({
	                   language: 'zh-CN',
	                   format: "yyyy-mm-dd",
	                   autoclose: true,
	                   todayBtn: true,
	                   minView: 2,
	                   pickerPosition: 'bottom-left'
	               }).datetimepicker('setDate', new Date());//加载日期选择器
	               form.find('.select').each(function(){
	                    var select = $(this);
	                    var idAttr = select.attr('id');
	                    select.select({
	                        title: '请选择',
	                        contents: selectItems[idAttr]
	                    }).on('change', function(){
	                        form.find('#'+ idAttr + '_').val($(this).getValue());
	                    });
	               });
	    },
	    remove: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids.join(',') }];
	    	$.post('${pageContext.request.contextPath}/FTQDN/delete.koala', data).done(function(result){
	                        if(result.success){
	                            grid.data('koala.grid').refresh();
	                            grid.message({
	                                type: 'success',
	                                content: '删除成功'
	                            });
	                        }else{
	                            grid.message({
	                                type: 'error',
	                                content: result.result
	                            });
	                        }
	    	});
	    }
	}
	PageLoader.initSearchPanel();
	PageLoader.initGridPanel();
	form.find('#search').on('click', function(){
            if(!Validator.Validate(document.getElementById("<%=formId%>"),3))return;
            var params = {};
            form.find('input').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                if(name){
                    params[name] = $this.val();
                }
            });
            grid.getGrid().search(params);
        });
});

var openDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/FTQDN-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/FTQDN/get/' + id + '.koala').done(function(json){
                       json = json.data;
                        var elm;
                        for(var index in json){
                        if(json[index]+"" == "false"){
                        		dialog.find('#'+ index + 'ID').html("<span style='color:#d2322d' class='glyphicon glyphicon-remove'></span>");
                        	}else if(json[index]+"" == "true"){
                        		dialog.find('#'+ index + 'ID').html("<span style='color:#47a447' class='glyphicon glyphicon-ok'></span>");
                        	}else{
                          		 dialog.find('#'+ index + 'ID').html(json[index]+"");
                        	}
                        }
               });
                dialog.modal({
                    keyboard:false
                }).on({
                    'hidden.bs.modal': function(){
                        $(this).remove();
                    }
                });
        });
}
</script>
</head>
<body>
<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <div class="form-group">
          <label class="control-label" style="width:65PX;float:left;">类型:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="type" class="form-control" type="text" style="width:180px;" id="typeID"  />
        	</div>
        	<label class="control-label" style="width:65PX;float:left;">QDNNo:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="qdnNo" class="form-control" type="text" style="width:180px;" id="qdnNoID"  />
        	</div>
        	<label class="control-label" style="width:65PX;float:left;">LotNo:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="lotNo" class="form-control" type="text" style="width:180px;" id="LotNoID"  />
        	</div>
        	<label class="control-label" style="width:65PX;float:left;">产品型号:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="ftLotDTO.shipmentProductNumber" class="form-control" type="text" style="width:180px;" id="shipmentProductNumberID"  />
        	</div>
       </div>
	</td>
  <td style="vertical-align: bottom;"><button id="search" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</button></td>
  </tr>
</table>	
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
</div>
</body>
</html>
