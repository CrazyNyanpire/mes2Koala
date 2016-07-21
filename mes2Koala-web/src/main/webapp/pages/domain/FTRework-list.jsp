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
<style>
td {
    vertical-align: middle!important;
}
</style>
<script type="text/javascript">
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
	        var contents = [ {title : '请选择',value : ''} ];	 
	        contents.push( {title : 'FT' ,value : 'FT'} );
	        contents.push( {title : 'CP' ,value : 'CP'} );
	        form.find('#categoryID').select({
                title: '请选择',
                contents: contents
           	}).on('change',function(){
               form.find('#categoryID_').val($(this).getValue());
           	});
	        var contents = [ {title : '请选择',value : ''} ];	 
	        form.find('#reworkCustomerID').select({
                title: '请选择',
                contents: contents
           	}).on('change',function(){
               form.find('#reworkCustomerID_').val($(this).getValue());
           	});
	        var contents = [ {title : '请选择',value : ''} ];	 
	        form.find('#reworkProductID').select({
                title: '请选择',
                contents: contents
           	}).on('change',function(){
               form.find('#reworkProductID_').val($(this).getValue());
           	});
	        var contents = [ {title : '请选择',value : ''} ];	 
	        form.find('#reworkFlowID').select({
                title: '请选择',
                contents: contents
           	}).on('change',function(){
               form.find('#reworkFlowID_').val($(this).getValue());
           	});
	        var contents = [ {title : '请选择',value : ''} ];	 
	        form.find('#reworkEquipmentID').select({
                title: '请选择',
                contents: contents
           	}).on('change',function(){
               form.find('#reworkEquipmentID_').val($(this).getValue());
           	});
	        var contents = [ {title : '请选择',value : ''} ];	
	        contents.push( {title : '完成' ,value : '完成'} );
	        contents.push( {title : '未完成' ,value : '未完成'} );
	        form.find('#statusID').select({
                title: '请选择',
                contents: contents
           	}).on('change',function(){
               form.find('#statusID_').val($(this).getValue());
           	});
		},
	    initGridPanel: function(){
	         var self = this;
	         var width = 120;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>审批</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-plus"><span>导出Excel</button>', action: 'exportExcel'},
/* 	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'} */
	                    ],
	                url:"${pageContext.request.contextPath}/Rework/pageJson.koala",
	                columns: [
								 { title: '重工单类型', name: 'category', width: width},
								 { title: '重工编号', name: 'reworkNo', width: width ,render: function (rowdata, name, index)
		                             {
	                                     var param = '"' + rowdata.id + '"';
	                                     var category= '"' + rowdata.category + '"';
	                                     var h = "<a href='javascript:openDetailsPage(" + param + ","+category+")'>"+rowdata[name]+"</a> ";
	                                     return h;
                                 	 }
								 },
								 { title: '客户编号', name: 'reworkCustomer', width: width},
								 { title: '产品型号', name: 'product', width: width},
								 { title: '机台编号', name: 'reworkEquipment', width: width},
       	                         { title: '提交日期', name: 'reworkDate', width: width},
       	                         { title: '艾科批号', name: 'lotNo', width: width},
       	                         { title: '完成状态', name: 'approve', width: width ,render: function (rowdata, name, index)
		                             {
	                                    var a = eval("(" + rowdata.approvePerson + ")");
	                                    return a.map(function(i){return i.name+":"+(i.status=='true'?"<span style='color:#47a447'>通过</span>":"<span style='color:#d9333f'>未通过</span>")}).join(",");;
                             	 	 }
							 	 },
	                ]
	         }).on({
	                   'add': function(event, data){
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
	                       self.add(indexs[0], $this);
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
	                   'exportExcel': function(event, data){
	                	   var indexs = data.data;
	                       var $this = $(this);
	                	   self.exportExcel(indexs[0], $this);
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
	    add: function(id,grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
	        	+'<h4 class="modal-title">审核</h4></div><div class="modal-body">'
	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
	        	+'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
	        	+'</div></div>');
	        $.get('<%=path%>/FTRework-add.jsp').done(function(html){
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
	              $.post("${pageContext.request.contextPath}/Rework/approve.koala?id="+id, dialog.find('form').serialize()).done(function(result){
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
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/FTRework-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/FTRework/get/' + id + '.koala').done(function(json){
	                       json = json.data;
	                        var elm;
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(elm.hasClass('select')){
	                                elm.setValue(json[index]);
	                            }else{
	                                elm.val(json[index]);
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
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    $.post('${pageContext.request.contextPath}/FTRework/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
	    	$.post('${pageContext.request.contextPath}/FTRework/delete.koala', data).done(function(result){
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
	    },
	    exportExcel: function(ids, grid){
	    	if(!Validator.Validate(document.getElementById("<%=formId%>"),3))return;
            var params = {};
            form.find('input').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                if(name){
                    params[name] = $this.val();
                }
            });
	    	$.post('${pageContext.request.contextPath}/Rework/exportExcel.koala', params).done(function(result){
	                        if(result.success){
	                        	window.open(result['data']);
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

var openDetailsPage = function(id,type){
        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1010px"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button><button type="button" class="btn print btn-success">打印</button></div></div></div></div>');
        	$.get('<%=path%>/'+type+'ReworkApply.jsp').done(function(html){
                dialog.find('.modal-body').html(html);
                dialog.find(".print").bind("click",function(){$('.modal-body').jqprint()});
                $.get( '${pageContext.request.contextPath}/'+type+'Rework/get/' + id + '.koala').done(function(json){
                        json = json.data;
                         var elm;
                         for(var index in json){
                         	dialog.find("[name='"+index+"']").val(json[index]);
                         	if(dialog.find("[name='"+index+"']").attr("type")=="text"){
                         		dialog.find("[name='"+index+"']").after("<span>"+json[index]+"</span>");
                         		dialog.find("[name='"+index+"']").remove();
                         	}
                         	dialog.find("[name='"+index+"']").text(json[index]);
                         	dialog.find("[name='reworkLot']").text(json['lotNo']);
                         	dialog.find("[name='explanation']").text(json['reasonExplanation']);
                         	dialog.find("[name='optpreson']").text(json['createEmployNo']);
                         	
                         }
                         dialog.find("#shipmentProductNumberID").text(json['reworkLot']['shipmentProductNumber']);
                         dialog.find(".rework_item").empty();
                         json['reworkItems'].map(function(a){
                         	return $.each(a,function(b){if (a[b] == null)a[b] = "";});
                         });
                         $.each(json['reworkItems'],function(index)
                         {
                         	dialog.find(".rework_item").append("<tr><td>"+json['reworkItems'][index]['attentionItem']+"</td>"
                         			+"<td>"+json['reworkItems'][index]['reworkFlow']+"</td>"
                         			+"<td>"+json['reworkItems'][index]['operator']+"</td>"
                         			+"<td>"+json['reworkItems'][index]['accomplishDate']+"</td></tr>");	
                         });
                         dialog.find(".glyphicon-plus").remove();
                         var radio=dialog.find("input[name='Rework_reason'][type='radio']");
                         for(var a=0;a<radio.length;a++)
                         	{
                         		if(radio.eq(a).next().text()==json['reasonReasons'])
                     			{
                         			radio.eq(a).attr("checked","checked");
                     			}
                         	}
                         var radio=dialog.find("input[name='Rework_gist'][type='radio']");
                         for(var a=0;a<radio.length;a++)
                         	{
                         		if(radio.eq(a).next().text()==json['gist'])
                     			{
                         			radio.eq(a).attr("checked","checked");
                     			}
                         	}
                         var radio=dialog.find("input[name='Rework_over'][type='radio']");
                         for(var a=0;a<radio.length;a++)
                         	{
                         		if(radio.eq(a).next().text()==json['summary'])
                     			{
                         			radio.eq(a).attr("checked","checked");
                     			}
                         	}
                         dialog.find("[name='Rework_other']").val(json['reasonOther']);
                         dialog.find("[name='Rework_other']").after("<span>("+json['reasonOther']+")<span>");
                         dialog.find("[name='Rework_other']").remove();
                         dialog.find("#authorizationMemberID").remove();
                         json['approvePerson']=eval('(' + json['approvePerson'] + ')');
                         debugger;
                         for(var a=0;a<json['approvePerson'].length;a++)
 						{
                         	dialog.find(".applyPerson").append("<button type='button' style='margin-right:20px;' class='btn btn-primary'>"+json['approvePerson'][a]['name']+"</button>");
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
          	<label class="control-label" style="width:100px;float:left;">重工单类型:&nbsp;</label>
	            <div style="margin-left:15px;float:left;">
	            <div class="btn-group select" id="categoryID"></div>
	            <input name="category" type="hidden" id="categoryID_"  />
	        </div>
	        <label class="control-label" style="width:100px;float:left;">客户编号:&nbsp;</label>
 	            <div style="margin-left:15px;float:left;">
<!--	            <div class="btn-group select" id="reworkCustomerID"></div>
	            <input name="reworkCustomer" type="hidden" id="reworkCustomerID_"  /> -->
	            <input name="reworkCustomer" type="text" class="form-control" style="width:180px;" id="reworkCustomerID_"  />
	        </div>
	        <label class="control-label" style="width:100px;float:left;">产品型号:&nbsp;</label>
 	            <div style="margin-left:15px;float:left;">
<!--	            <div class="btn-group select" id="reworkProductID"></div>
	            <input name="reworkProduct" type="hidden" id="reworkProductID_"  /> -->
	            <input name="reworkProduct" type="text" class="form-control" style="width:180px;" id="reworkProductID_"  />
	        </div>
            <label class="control-label" style="width:100px;float:left;">艾科批号:&nbsp;</label>
            	<div style="margin-left:15px;float:left;">
            	<input name="lotNo" class="form-control" type="text" style="width:180px;" id="lotNoID"  />
        	</div>
         </div>
         <div class="form-group">
          	<label class="control-label" style="width:100px;float:left;">站点:&nbsp;</label>
	            <div style="margin-left:15px;float:left;">
	            <div class="btn-group select" id="reworkFlowID"></div>
	            <input name="reworkFlow" type="hidden" id="reworkFlowID_"  />
	        </div>
            <label class="control-label" style="width:100px;float:left;">测试机台:&nbsp;</label>
	            <div style="margin-left:15px;float:left;">
	            <div class="btn-group select" id="reworkEquipmentID"></div>
	            <input name="reworkEquipment" type="hidden" id="reworkEquipmentID_"  />
	        </div>
          	<label class="control-label" style="width:100px;float:left;">重工单编号:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="reworkNo" class="form-control" type="text" style="width:180px;" id="reworkNoID"  />
        	</div>
            <label class="control-label" style="width:100px;float:left;">完成状态:&nbsp;</label>
	            <div style="margin-left:15px;float:left;">
	            <div class="btn-group select" id="statusID"></div>
	            <input name="status" type="hidden" id="statusID_" />
	        </div>
        </div>
        <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">提交日期:&nbsp;</label>
           <div style="margin-left:15px;float:left;">
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="createTimestamp" id="createTimestampID_start" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="createTimestampEnd" id="createTimestampID_end" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
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
