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
<script src="<%=contextPath %>/lib/uploadPreview.js"></script>
<script type="text/javascript">
var grid;
var form;
var _dialog;
$(function (){
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	var contents = [{title:'请选择', value: ''}];
	PageLoader = {
	   //
	    initSearchPanel:function(){
			var startTimeVal = form.find('#createTimeID_start');
	        var startTime = startTimeVal.parent();
			var endTimeVal = form.find('#createTimeID_end');
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
			$.ajax({
				url: '${pageContext.request.contextPath}/SystemDictionary/getByType/CustomerStatus.koala',
				type: 'GET',
				dataType: 'json'
			}).done(function (msg) {
				for (var i = 0; i < msg.length; i++) {
					contents.push({
						title: msg[i]['label'],
						value: msg[i]['value']
					});
				}
				form.find('#status_SELECT').select({
					title: '请选择',
					contents: contents
				}).on('change',function(){
					form.find('#statusID_').val($(this).getValue());
				});
			});
	  },
	    initGridPanel: function(){
	         var self = this;
	         var width = 180;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
                            {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>导出到Excel</button>',action: 'exportExcel'}
	                    ],
	                url:"${pageContext.request.contextPath}/Customer/pageJson.koala",
	                columns: [
						{title: '客户编号', name: 'number', width: 100, sortable: true, sortName: 'number'},
						{title: '英文名称', name: 'englishName', width: 90, sortable: true, sortName: 'englishName'},
	                         { title: '中文名称', name: 'chineseName', width: width,sortable: true, sortName: 'chineseName'},
	                         { title: '客户状态', name: 'status', width: 80,sortable: true, sortName: 'status'},
	                         { title: '公司电话', name: 'phone', width: 120,sortable: true, sortName: 'phone'},
	                         { title: '客户地址1', name: 'address1', width: width,sortable: true, sortName: 'address1'},
	                         { title: '客户地址2', name: 'address2', width: width,sortable: true, sortName: 'address2'},
	                         { title: '添加时间', name: 'createTime', width: 180, sortable: true, sortName: 'createTimestamp'},
	                         /*
	                         { title: 'ReelCode固定码', name: 'reelFixCode', width: width},
	                         { title: 'Reel盘数量', name: 'reelQty', width: width},
	                        */
	                         { title: '操作', width: 120, render: function (rowdata, name, index)
	                            {
	                              var param = '"' + rowdata.id + '"';
	                              var h = "<a href='javascript:openCustomerDetailsPage(" + param + ")'>查看</a> ";
	                              return h;
	                            }
	                         }
	                ]
	         }).on({
	                   'add': function(){
	                       self.add($(this));
	                   },
	                   'modify': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
								debugger;
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行修改'
								});
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行修改'
								});
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
	                   },
	                   'exportExcel': function (event, data) {
                           var indexs = data.data;
                           var $this = $(this);
                           if (indexs.length == 0) {
                               $this.message({
                                   type: 'warning',
                                   content: '请选择要导出的记录'
                               });
                               return;
                           }
                           $this.confirm({
                               content: '确定要导出所选记录吗?',
                               callBack: function () {
                                   self.exportExcel(indexs, $this);
                               }
                           });
                       }
	         });
	    },
	    add: function(grid){
	        var self = this;
			var picInfoAdd;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	        	+'data-dismiss="modal" aria-hidden="true" id="close">&times;</button>'
	        	+'<h4 class="modal-title">新增客户信息</h4></div><div class="modal-body">'
	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
	        	+'<button type="button" class="btn btn-default" data-dismiss="modal" id="cancel">取消</button>'
	        	+'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
	        	+'</div></div>');
	        $.get('<%=path%>/Customer-add.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
				$.ajax({
					url: '${pageContext.request.contextPath}/SystemDictionary/getByType/CustomerSerialNumber.koala',
					type: 'GET',
					dataType: 'json'
				}).done(function (msg) {
					dialog.find('#codeDisplay').val(year % 100 + msg[0].value);
					dialog.find('#codeID').val(year % 100 + msg[0].value);
				});
				dialog.find('#logoUpload').change(function (e) {
					e.preventDefault();
					var fd = new FormData();
					fd.append('logo', $('#logoUpload')[0].files[0]);
					$.ajax({
						type: 'post',
						url: '${pageContext.request.contextPath}/FileUpload/uploadLogo.koala',
						data: fd,
						processData: false,
						contentType: false,
						cache: false,
						success: function (msg) {
							console.log(msg.data);
							picInfoAdd = msg.data;
						},
						error: function (msg) {
							console.log('error');
						}
					});
				});
				dialog.find('#save').on('click',{grid: grid}, function(e){
					if(!Validator.Validate(dialog.find('form')[0],3))return;
					/*	var fd = new FormData(dialog.find('form')[0]);//使用formData上传含表单的文件
					 $.ajax({
					 type: 'post',
					 url: '${pageContext.request.contextPath}/Customer/add.koala',
					 data: fd,
					 processData: false,
					 contentType: false,
					 cache: false,
					 success: function (result) {
					 dialog.modal('hide');
					 e.data.grid.data('koala.grid').refresh();
					 e.data.grid.message({
					 type: 'success',
					 content: '保存成功'
					 });
					 },
					 error: function (result) {
					 dialog.find('.modal-content').message({
					 type: 'error',
					 content: result.errorMessage
					 });
					 }
					 });*/
					//	var logoRouteNameArray = dialog.find('#logoUpload').val().split('\\');
					//	var logoRouteName = logoRouteNameArray[logoRouteNameArray.length-1];
					$.post('${pageContext.request.contextPath}/Customer/add.koala', dialog.find('form').serialize()+'&logo='+picInfoAdd).done(function(result){
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
								content: result.errorMessage
							});
						}
					});
				});
	        });
	    },
	    modify: function(id, grid){
	        var self = this;
			var picInfo;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改客户信息</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/Customer-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/Customer/get/' + id + '.koala').done(function(json){
	                       json = json.data;
	                        var elm;
					   for (var index in json) {
						   elm = dialog.find('#' + index + 'ID');
						   if (index == "number") {
							   var code = json['number'];
							   var codearray = code.split('-');
							   json['number'] = codearray[1] + '-' + codearray[2];
						   }
						   if (elm.hasClass('select')) {
	                                elm.setValue(json[index]);
	                            }else{
	                                elm.val(json[index]);
	                            }
					   }
					   dialog.find('#codeDisplay').val(dialog.find('#codeID').val());
					  // dialog.find('#logoUpload').val(json['logo']);//修改实现给file文件名赋值,暂时无法实现
					   dialog.find('#ImgPr').attr('src', json['logo']);
	                });
	                dialog.modal({
	                    keyboard:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
				dialog.find('#logoUpload').change(function (e) {
							e.preventDefault();
							var fd = new FormData();
							fd.append('logo', $('#logoUpload')[0].files[0]);
							$.ajax({
								type: 'post',
								url: '${pageContext.request.contextPath}/FileUpload/uploadLogo.koala',
								data: fd,
								processData: false,
								contentType: false,
								cache: false,
								success: function (msg) {
									console.log(msg.data);
									picInfo = msg.data;
								}
							})
				   })
				dialog.find('#save').on('click',{grid: grid}, function(e){
					if(!Validator.Validate(dialog.find('form')[0],3))return;
					$.post('${pageContext.request.contextPath}/Customer/update.koala?id='+id, dialog.find('form').serialize()+'&logo='+picInfo).done(function(result){
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
	                        contents: contents
	                    }).on('change', function(){
	                        form.find('#'+ idAttr + '_').val($(this).getValue());
	                    });
				   });
	            form.find('#logoUpload').uploadPreview();
	    },
	    remove: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids.join(',') }];
	    	$.post('${pageContext.request.contextPath}/Customer/delete.koala', data).done(function(result){
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
	    exportExcel: function (ids, grid) {
            var data = [{name: 'ids', value: ids.join(',')}];
            $.post('${pageContext.request.contextPath}/Customer/exportExcel.koala', data).done(function (result) {
                if (result.success) {
                    grid.data('koala.grid').refresh();
                    grid.message({
                        type: 'success',
                        content: '导出成功'
                    });
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">导出Excel</h4></div><div class="modal-body"><p id="downloadID"></p></div><div class="modal-footer"><button type="button" class="btn btn-success" data-dismiss="modal" id="save">确定</button></div></div></div></div>');
                    dialog.modal({
                        keyboard: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        }
                    }).find('#downloadID').html("导出成功！" + "<a style='margin-left:20px;'   href='"+ result.data +"'>点击下载</a>"); 
                } else {
                    grid.message({
                        type: 'error',
                        content: result.errorMessage
                    });
                }
            });
        }
	};
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
/*	grid.find('.grid-table-body').find('table').off('click').on('click', function(e) {
		debugger;
		e.stopPropagation();
		var checkedData=grid.getGrid().selectedRows();
		if(checkedData.length>1){

		}
	});*/
});

var openCustomerDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/Customer-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/Customer/get/' + id + '.koala').done(function(json){
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
					   dialog.find('#logoName').html(json['logo'].split("/")[json['logo'].split("/").length - 1]+"<a style='margin-left:20px;'   href='"+json['logo']+"'>下载</a>");
					   dialog.find('#ImgPr').attr('src', json['logo']);
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
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">中文名称:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="chineseName" class="form-control" type="text" style="width:180px;" id="chineseNameID"  />
        </div>
                      <label class="control-label" style="width:100px;float:left;">英文名称:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="englishName" class="form-control" type="text" style="width:180px;" id="englishNameID"  />
        </div>
            </div>
                  <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">添加时间:&nbsp;</label>
           <div style="margin-left:15px;float:left;">
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="createTimestamp" id="createTimeID_start" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="createTimestampEnd" id="createTimeID_end" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
       </div>
                      <label class="control-label" style="width:100px;float:left;">客户状态:&nbsp;</label>
    	  <div style="margin-left:15px;float:left;">
	      <div class="btn-group select" id="status_SELECT"></div>
	        <input type="hidden" id="statusID_" name="status" />
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
