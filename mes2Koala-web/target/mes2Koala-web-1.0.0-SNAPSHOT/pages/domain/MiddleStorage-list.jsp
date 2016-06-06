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
var grid;
var form;
var _dialog;
$(function (){
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	   //
	    initSearchPanel:function(){
				    	var contents = [ {title : '请选择',value : ''} ];	 
				        contents.push( {title : 'Fail' ,value : 'Fail'} );
				        contents.push( {title : 'Pass' ,value : 'Pass'} );
				        contents.push( {title : 'Pass1' ,value : 'Pass1'} );
				        contents.push( {title : 'Pass2' ,value : 'Pass2'} );
				        form.find('#qualityID').select({
			                title: '请选择',
			                contents: contents
			           	}).on('change',function(){
			               form.find('#qualityID_').val($(this).getValue());
			           	});
				        var contents = [ {title : '请选择',value : ''} ];	 
				        contents.push( {title : '在库' ,value : '在库'} );
				        contents.push( {title : '出库未签核' ,value : '出库未签核'} );
				        contents.push( {title : '出库已签核' ,value : '出库已签核'} );
				        contents.push( {title : '丢料' ,value : '丢料 '} );
				        contents.push( {title : '借料' ,value : '借料'} );
				        contents.push( {title : '重工' ,value : '重工'} );
				        form.find('#statusID').select({
			                title: '请选择',
			                contents: contents
			           	}).on('change',function(){
			               form.find('#statusID_').val($(this).getValue());
			           	});
				        var contents = [ {title : '请选择',value : ''} ];	 
				        contents.push( {title : '是' ,value : true} );
				        contents.push( {title : '否' ,value : false} );
				        form.find('#isCombinedID').select({
			                title: '请选择',
			                contents: contents
			           	}).on('change',function(){
			               form.find('#isCombinedID_').val($(this).getValue());
			           	});
	        	         var startTimeVal = form.find('#packagingTimeID_start');
	                     var startTime = startTimeVal.parent();
	                     var endTimeVal = form.find('#packagingTimeID_end');
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
	         var width = 120;
	         return grid.grid({
	        	 	searchCondition:{"status":"在库"},
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-success" type="button">修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button">拆盘</button>', action: 'split'},
	                        {content: '<button class="btn btn-primary" type="button">Lat打包</button>', action: 'latPackage'},
	                        {content: '<button class="btn btn-primary" type="button">Fail品标签打印</button>', action: 'failPrint'},
	                        {content: '<button class="btn btn-danger" type="button">开Hold</button>', action: 'hold'},
	                        {content: '<button class="btn btn-primary" type="button">解Hold</button>', action: 'unhold'},
	                        {content: '<button class="btn btn-primary" type="button">重工下单</button>', action: 'rework'},
	                        {content: '<button class="btn btn-danger" type="button">重工申请</button>', action: 'reworkApply'},
	                        {content: '<button class="btn btn-primary" type="button"><span>合批</button>', action: 'merge'},
	                        {content: '<button class="btn btn-primary" type="button">出货</button>', action: 'shipping'},
	                        {content: '<button class="btn btn-primary" type="button">丢料</button>', action: 'loss'},
	                        {content: '<button class="btn btn-primary" type="button">借料</button>', action: 'borrow'},
	                        {content: '<button class="btn btn-primary" type="button">特殊标示</button>', action: 'special'},
	                        {content: '<button class="btn btn-primary" type="button">合批还原</button>', action: 'mergeRestore'},
	                        {content: '<button class="btn btn-primary" type="button">工程打印</button>', action: 'enginePrint'},
	                        {content: '<button class="btn btn-primary" type="button">Lat打印</button>', action: 'latPrint'}
	                    ],
	                url:"${pageContext.request.contextPath}/ReelDiskTransferStorage/pageJson.koala",
	                columns: [
 									 { title: 'Approve', name: 'approve', width: 70},
									 { title: '状态', name: 'status', width: 70},
									 { title: 'ReelCode', name: 'reelCode', width: width},
           	                      	 { title: 'dateCode', name: 'dateCode', width: 90},
           	                   		 { title: '实际数量', name: 'quantity', width: 90},
           	                   		 { title: '版本型号', name: 'customerProductNumber', width: width},
           	                      	 { title: '来料型号', name: 'customerProductNumber', width: width},
        	                         { title: '出货型号', name: 'shipmentProductNumber', width: width},
        	                         { title: '特殊标示', name: 'specialSign', width: 90, render: function (rowdata, name, index)
	                                 {
										 var a=rowdata['specialSign']+":"+rowdata['specialSignRemark'];
										 if(!!rowdata['specialSign']==0)a="";
	                                     return a;
	                                 }},
        	                         { title: '产品PPO', name: 'customerPPO', width: width},
        	                         { title: '艾科批号', name: '', width: 140, render: function (rowdata, name, index)
	                                 {
	                                     return rowdata['ftLotDTO']['internalLotNumber'];
	                                 }},
           	                         { title: '客户批号', name: 'customerLotNumber', width: width},
           	                         
           	                 		 { title: '是否合批', name: '', width: 70, render: function (rowdata, name, index)
	                                 {
	                                     if(!!rowdata['fromReelCode']==0&&!!rowdata['combinedLotNumber']==0)
	                                    	 return "否";
	                                     else
	                                    	 return "是";
	                                 }},
	                                 { title: '来料时间', name: '', width: width, render: function (rowdata, name, index)
		                                 {
											 var a=rowdata['ftLotDTO']['customerLotDTO']['incomingDate']
		                                     return a;
		                                 }},
	           	                         { title: '入库时间', name: 'packagingTime', width: width},
           	              			 { title: '合批', name: 'combinedLotNumber', width: 140},
           	                         { title: '品质', name: 'quality', width: width},
           	                      	 { title: '包装', name: '', width: width},
           	                   		 { title: 'Mark编号', name: '', width: width},
           	                		 { title: '被合批Lot', name: 'fromReelCode', width: 125},
           	             			 { title: '备注', name: 'remark', width: width},
           	                   		 { title: '是否Hold', name: 'holdSign', width: 70},
           	                         { title: '是否满卷', name: 'isFull', width: 70},
       	                             { title: '详情', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openDetailsPage(" + param + ")'>查看</a> ";
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
	                        if(data['item'][0]['holdSign']=="是")
                        	{
	                        	$this.message({
	                                type: 'warning',
	                                content: '此reelcode已Hold请确认'
	                            })
	                            return;
                        	}
	                       self.modify(indexs[0], $this);
	                    },
	                    'split': function(event, data){
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
	                        if(data['item'][0]['holdSign']=="是")
                        	{
	                        	$this.message({
	                                type: 'warning',
	                                content: '此reelcode已Hold请确认'
	                            })
	                            return;
                        	}
	                       self.split(indexs[0], $this,data['item'][0]);
	                    },
	                    'latPackage': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                       self.latPackage(indexs[0], $this);
	                    },
	                    'failPrint': function(event, data){
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
							debugger;
							self.failPrint(indexs[0], $this, data.item[0]['internalLotId']);
	                    },
	                    'hold': function(event, data){
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
	                       self.hold(indexs[0], $this);
	                    },
	                    'unhold': function(event, data){
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
	                       self.unhold(indexs[0], $this);
	                    },
	                    'rework': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行修改'
	                            })
	                            return;
	                        }
	                        var customerLotNumber0=data['item'][0]['customerLotNumber0'];
	                        for(var a in data['item'])
                        	{
	                        	if(customerLotNumber0!=data['item'][a]['customerLotNumber0'])
                        		{
	                        		$this.message({
		                                type: 'warning',
		                                content: '请确认所选reelcode为同一批次'
		                            })
                        		}
                        	}
/* 	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行修改'
	                            })
	                            return;
	                        } */
	                        if(data['item'][0]['holdSign']=="是")
                        	{
	                        	$this.message({
	                                type: 'warning',
	                                content: '此reelcode已Hold请确认'
	                            })
	                            return;
                        	}
	                       self.rework(indexs, $this,data.item[0]['internalLotId']);
	                    },
	                    'reworkApply': function(event, data){
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
	                       self.reworkApply(indexs[0], $this,data.item[0]['internalLotId']);
	                    },
	                    'merge': function(event, data){
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
	                       self.merge(indexs[0], $this);
	                    },
	                    'shipping': function(event, data){
	                    	var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                   type: 'warning',
	                                   content: '请选择一条记录进行修改'
	                            });
	                            return;
	                        }
	                        var shipping = function(){
	                            self.shipping(indexs, $this);
	                        };
	                        var sum=0;
	                        var flag=false;
	                        $.each(data['item'],function(a){
	                        	sum+=parseInt(data['item'][a]['quantity']);
	                        	if(data['item'][a]['holdSign']=="是")
	                        	{
		                        	$this.message({
		                                type: 'warning',
		                                content: '此reelcode已Hold请确认'
		                            })
		                            flag=true;
	                        	}
	                        });
	                        if(flag)return;
	                        $this.confirm({
	                        	
	                            content: '确定要出货'+sum+'颗?',
	                            callBack: shipping
	                        });
	                    },
	                    'loss': function(event, data){
	                    	var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                   type: 'warning',
	                                   content: '请选择一条记录进行修改'
	                            });
	                            return;
	                        }
	                        var loss = function(){
	                            self.loss(indexs, $this);
	                        };
	                        var sum=0;
	                        var flag=false;
	                        $.each(data['item'],function(a){
	                        	sum+=parseInt(data['item'][a]['quantity']);
	                        	if(data['item'][a]['holdSign']=="是")
	                        	{
		                        	$this.message({
		                                type: 'warning',
		                                content: '此reelcode已Hold请确认'
		                            })
		                            flag=true;
	                        	}
	                        });
	                        if(flag)return;
	                        $this.confirm({
	                        	
	                            content: '确定要标记丢料'+sum+'颗?',
	                            callBack: loss
	                        });
	                    },
	                    'borrow': function(event, data){
	                    	var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                   type: 'warning',
	                                   content: '请选择一条记录进行修改'
	                            });
	                            return;
	                        }
	                        var borrow = function(){
	                            self.borrow(indexs, $this);
	                        };
	                        var sum=0;
	                        var flag=false;
	                        $.each(data['item'],function(a){
	                        	sum+=parseInt(data['item'][a]['quantity']);
	                        	if(data['item'][a]['holdSign']=="是")
	                        	{
		                        	$this.message({
		                                type: 'warning',
		                                content: '此reelcode已Hold请确认'
		                            })
		                            flag=true;
	                        	}
	                        });
	                        if(flag)return;
	                        $this.confirm({
	                            content: '确定要标记借料'+sum+'颗?',
	                            callBack: borrow
	                        });
	                    },
	                    'special': function(event, data){
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
	                       self.special(indexs[0], $this);
	                    },
	                    'mergeRestore': function(event, data){
	                    	var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
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
	                            })
	                            return;
	                        }
	                        var mergeRestore = function(){
	                            self.mergeRestore(indexs[0], $this);
	                        };
	                        $this.confirm({
	                            content: '确定要合批还原?',
	                            callBack: mergeRestore
	                        });
	                    },
	                    'enginePrint': function(event, data){
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
	                       self.enginePrint(indexs[0], $this);
	                    },
	                    'latPrint': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                       self.latPrint(indexs[0], $this);
	                    },
	                    'export': function(event, data){
	                    	var indexs = data.data;
							var $this = $(this);
							if(indexs.length == 0){
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
	        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
	        	+'<h4 class="modal-title">新增</h4></div><div class="modal-body">'
	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
	        	+'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
	        	+'</div></div>');
	        $.get('<%=path%>/ReelDisk-add.jsp').done(function(html){
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
	              $.post('${pageContext.request.contextPath}/ReelDisk/add.koala', dialog.find('form').serialize()).done(function(result){
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
	    latPrint:function(id, grid){
	               $.get(contextPath + '/pages/domain/MiddleStorage-latPrint.jsp').done(function(data){
						 var staffDialog = $(data);
						 staffDialog.modal({
								backdrop: false,
								keyboard: false
							}).on({
								'hidden.bs.modal': function(){
									$(this).remove();
								},
								'shown.bs.modal' : function(){
									var width=110;
									var notGrantRolecolumns = [
													{ title: 'Approve', name: 'approve', width: width},
													{ title: '状态', name: 'status', width: width},
													{ title: '来料时间', name: '', width: width, render: function (rowdata, name, index)
													{
														 var a=new Date(rowdata['ftLotDTO']['customerLotDTO']['createTimestamp'])
													    return a.toLocaleDateString();
													}},
													   { title: '入库时间', name: 'packagingTime', width: width},
														 { title: '来料型号', name: 'customerProductNumber', width: width},
													{ title: '出货型号', name: 'shipmentProductNumber', width: width},
													{ title: '特殊标示', name: 'specialSign', width: width},
													{ title: '产品PPO', name: 'customerPPO', width: width},
													{ title: '艾科批号', name: '', width: width, render: function (rowdata, name, index)
													{
													    return rowdata['ftLotDTO']['internalLotNumber'];
													}},
													   { title: '客户批号', name: 'customerLotNumber', width: width},
													   { title: 'ReelCode', name: 'reelCode', width: width},
														 { title: 'dateCode', name: 'dateCode', width: width},
															 { title: '实际数量', name: 'quantity', width: width},
															 { title: '版本型号', name: 'customerProductNumber', width: width},
														 { title: '是否合批', name: '', width: width, render: function (rowdata, name, index)
													{
													    if(rowdata['combinedLotNumber']==null)
													   	 return "否";
													    else
													   	 return "是";
													}},
														 { title: '合批', name: '', width: width},
													   { title: '品质', name: 'quality', width: width},
														 { title: '包装', name: '', width: width},
															 { title: 'Mark编号', name: '', width: width},
														 { title: '被合批Lot', name: 'combinedLotNumber', width: width},
														 { title: '备注', name: 'remark', width: width},
															 { title: '是否Hold', name: 'holdSign', width: width},
													   { title: '是否满卷', name: 'isFull', width: width},
								                ];
									staffDialog.find('#latGrid').grid({
										identity: 'id',
										columns: notGrantRolecolumns,
										isShowPages:false,
										url: "${pageContext.request.contextPath}/ReelDiskTransferStorage/pageJson.koala?quality=LAT&latSign=1&page=0&pagesize=100"
									});
									staffDialog.find("#latGrid .grid-table-body").css("height","281px");
								}
							});
						 staffDialog.find('#searchFTLot').on('click',function(){
								if(!Validator.Validate(staffDialog.find('form')[0],3))return;
								var params = {};
								staffDialog.find('#serachItems').find('input').each(function(){
									var $this = $(this);
									var name = $this.attr('name');
									if(name){
										params[name] = $this.val();
									}
								});
								staffDialog.find('#latGrid').getGrid().search(params);
							});
					    staffDialog.find('#latConfirm').on('click',{grid: grid},function(e){
				    	 	var grantRolesToUserTableItems = staffDialog.find('#latGrid').getGrid().selectedRows();
							if(grantRolesToUserTableItems.length == 0){
								staffDialog.find('#latGrid').message({
									type: 'warning',
									content: '请选择需打印的reelcode'
								});
								return;
							}
							var ids=[];
							for(var a in grantRolesToUserTableItems){
								ids.push(grantRolesToUserTableItems[a].id);
								}
							ids=ids.join(",");
							$.get('${pageContext.request.contextPath}/ReelDiskTransferStorage/latPrint.koala?id='+ids+'&latPackageNo='+grantRolesToUserTableItems[0].latPackageNo).done(function(result){
		                        if(result.success){
		                        	staffDialog.modal('hide');
		                            e.data.grid.data('koala.grid').refresh();
		                            e.data.grid.message({
		                            type: 'success',
		                            content: '保存成功'
		                            });
		                        }else{
		                        	if(result.success!=undefined||result.success==false)
	                        		{
		                        		staffDialog.find('.modal-content').message({
		    	                            type: 'error',
		    	                            content: result.errorMessage
		                        		});
	                        		}
		                        	else{
		                        		staffDialog.find('.modal-content').message({
		    	                            type: 'error',
		    	                            content: result.actionError
		                        		});
		                        	}
		                        }
		                    });
							staffDialog.modal('hide');
						});
					})
	    },
	    latPackage: function(id, grid){
	    	$.get(contextPath + '/pages/domain/MiddleStorage-latPackage.jsp').done(function(data){
				 var staffDialog = $(data);
				 staffDialog.modal({
						backdrop: false,
						keyboard: false
					}).on({
						'hidden.bs.modal': function(){
							$(this).remove();
						},
						'shown.bs.modal' : function(){
							var width=110;
							var notGrantRolecolumns = [
											{ title: 'Approve', name: 'approve', width: width},
											{ title: '状态', name: 'status', width: width},
											{ title: '来料时间', name: '', width: width, render: function (rowdata, name, index){
												 var a=new Date(rowdata['ftLotDTO']['customerLotDTO']['createTimestamp'])
											     return a.toLocaleDateString();
											}},
											{ title: '入库时间', name: 'packagingTime', width: width},
											{ title: '来料型号', name: 'customerProductNumber', width: width},
											{ title: '出货型号', name: 'shipmentProductNumber', width: width},
											{ title: '特殊标示', name: 'specialSign', width: width},
											{ title: '产品PPO', name: 'customerPPO', width: width},
											{ title: '艾科批号', name: '', width: width, render: function (rowdata, name, index)
											{
											    return rowdata['ftLotDTO']['internalLotNumber'];
											}},
											{ title: '客户批号', name: 'customerLotNumber', width: width},
											{ title: 'ReelCode', name: 'reelCode', width: width},
											{ title: 'dateCode', name: 'dateCode', width: width},
											{ title: '实际数量', name: 'quantity', width: width},
											{ title: '版本型号', name: 'customerProductNumber', width: width},
											{ title: '是否合批', name: '', width: width, render: function (rowdata, name, index)
												{
											    if(rowdata['combinedLotNumber']==null)
											   	 return "否";
											    else
											   	 return "是";
											}},
											{ title: '合批', name: '', width: width},
											{ title: '品质', name: 'quality', width: width},
											{ title: '包装', name: '', width: width},
											{ title: 'Mark编号', name: '', width: width},
											{ title: '被合批Lot', name: 'combinedLotNumber', width: width},
											{ title: '备注', name: 'remark', width: width},
											{ title: '是否Hold', name: 'holdSign', width: width},
											{ title: '是否满卷', name: 'isFull', width: width},
						                ];
							staffDialog.find('#latGrid').grid({
								identity: 'id',
								columns: notGrantRolecolumns,
								isShowPages:false,
								url: "${pageContext.request.contextPath}/ReelDiskTransferStorage/pageJson.koala?quality=LAT&latSign=0&page=0&pagesize=100"
							});
							staffDialog.find("#latGrid .grid-table-body").css("height","281px");
						}
					});
				 staffDialog.find('#searchFTLot').on('click',function(){
						if(!Validator.Validate(staffDialog.find('form')[0],3))return;
						var params = {};
						staffDialog.find('#serachItems').find('input').each(function(){
							var $this = $(this);
							var name = $this.attr('name');
							if(name){
								params[name] = $this.val();
							}
						});
						staffDialog.find('#latGrid').getGrid().search(params);
					});
			    staffDialog.find('#latConfirm').on('click',{grid: grid},function(e){
		    	 	var grantRolesToUserTableItems = staffDialog.find('#latGrid').getGrid().selectedRows();
					if(grantRolesToUserTableItems.length == 0){
						staffDialog.find('#latGrid').message({
							type: 'warning',
							content: '请选择需打包的reelcode'
						});
						return;
					}
					debugger;
					var ids=[];
					for(var a in grantRolesToUserTableItems){
						ids.push(grantRolesToUserTableItems[a].id);
						}
					ids=ids.join(",");
					$.post('${pageContext.request.contextPath}/ReelDiskTransferStorage/latPackage.koala?ids='+ids).done(function(result){
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
					staffDialog.modal('hide');
				});
			})
	    },
	    enginePrint: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header">'
					        +'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
					        +'<h4 class="modal-title">工程样品出货标签</h4></div><div class="modal-body">'
							+'<form class="form-horizontal"><object class="hidden" id="activeX" classid="clsid:8738EBCB-92B1-4dcb-8E86-A4703EBD191E"></object><div class="form-group"><label class="col-lg-3 control-label">Part No:</label>'
							+'<div class="col-lg-9"><input name="lblPartNo" readonly type="text" style="display:inline; width:94%;" class="form-control" id="partNumberID"/></div></div>'
				            +'<div class="form-group"><label class="col-lg-3 control-label">LOT:</label>'
				            +'<div class="col-lg-9"><input name="lblLot" readonly style="display:inline; width:94%;" class="form-control"  type="text"  id="lotID" /></div></div>'
					        +'<div class="form-group"><label class="col-lg-3 control-label">产品类别:</label>'
				            +'<div class="col-lg-9"><input name="lblQuality" readonly style="display:inline; width:94%;" class="form-control"  type="text"  id="qualityID"/></div></div>'
				            +'<div class="form-group"><label class="col-lg-3 control-label">数量:</label>'
				            +'<div class="col-lg-9"><input name="lblQuantity" readonly style="display:inline; width:94%;" class="form-control"  type="text"  id="quantityID"/></div></div>'
				            +'<div class="form-group" style="display:none;"><label class="col-lg-3 control-label">标识码:</label>'
				            +'<div class="col-lg-9"><input name="number" readonly style="display:inline; width:94%;" class="form-control"  type="text"  id="numberID"/></div></div>'
							+'</form></div><div class="modal-footer">'
					        +'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
					        +'<button type="button" class="btn btn-success" id="save">打印</button></div></div></div></div>');
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/ReelDisk/get/' + id + '.koala').done(function(json){
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
	                        dialog.find('#numberID').val(json['ftLotDTO']['wmsTestId']);
	                        dialog.find('#lotID').val(json['ftLotDTO']['customerLotDTO']['customerLotNumber']);
	                });
	                dialog.modal({
	                    keyboard:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    /* if($("labelNameID").val()==""||$("labelNameID").val()==undefined)
                    	{
	                    	dialog.message({
	                            type: 'warning',
	                            content: '未找到对应标签请检查'
                            });
                    		return false;
                    	} */
	                    var path="C:/WMS/PRINT/工程样品出货标签.btw";//+$("pathID").val()
	                    var parameter = dialog.find('form').serialize();//serializeForm("inputForm");
	            		var v = document.getElementById("activeX").Print(path, 1, parameter,true,true);
	                	if(v!="1")
	                	{
	                		alert(v);
	                	}
	                	else
	                	{
	                		document.getElementById("activeX").Quit();
	                	}
	                });
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:750px"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/MiddleStorage-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/ReelDiskTransferStorage/get/' + id + '.koala').done(function(json){
	                       json = json.data;
	                       debugger;
	                        var elm;
	                        for(var index in json){
	    						elm = dialog.find('#'+ index + 'ID');
	    						if(elm.hasClass('select')){
	    							elm.setValue(json[index]);
	    						}else{
	    							elm.val(json[index]);
	    							elm.text(json[index]);
	    						}
	    						if(typeof(json[index])=="object")
	    						{
	    							for(var index1 in json[index]){
	    								if(index1=="version")continue;
	    								elm = dialog.find('#'+ index1 + 'ID');
	    								if(elm.hasClass('select')){
	    									elm.setValue(json[index][index1]);
	    								}else{
	    									elm.val(json[index][index1]);
	    									elm.text(json[index][index1]);
	    								}
	    								if(typeof(json[index][index1])=="object")
	    								{
	    									for(var index2 in json[index][index1]){
	    										if(index2=="version")continue;
	    										elm = dialog.find('#'+ index2 + 'ID');
	    										if(elm.hasClass('select')){
	    											elm.setValue(json[index][index1][index2]);
	    										}else{
	    											elm.val(json[index][index1][index2]);
	    											elm.text(json[index][index1][index2]);
	    										}
	    									}
	    								}
	    							}
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
	                    $.post('${pageContext.request.contextPath}/ReelDisk/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
	    hold: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">开Hold</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/MiddleStorage-hold.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/FTLot/get/' + id + '.koala').done(function(json){
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
	                    $.post('${pageContext.request.contextPath}/ReelDiskTransferStorage/hold.koala?id='+id, dialog.find('form').serialize()).done(function(result){
	                        if(result.success){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '开Hold成功'
	                            });
	                        }else{
	                        	debugger;
	                        	if(result.success!=undefined||result.success==false)
                        		{
	                        		dialog.find('.modal-content').message({
	    	                            type: 'error',
	    	                            content: result.errorMessage
	                        		});
                        		}
	                        	else{
	                        		dialog.find('.modal-content').message({
	    	                            type: 'error',
	    	                            content: result.actionError
	                        		});
	                        	}
	                        }
	                    });
	                });
	        });
	    },
	    unhold: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">解Hold</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/MiddleStorage-unhold.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/FTLot/get/' + id + '.koala').done(function(json){
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
	                    $.post('${pageContext.request.contextPath}/ReelDiskTransferStorage/unhold.koala?id='+id, dialog.find('form').serialize()).done(function(result){
	                        if(result.success){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '解Hold成功'
	                            });
	                        }else{
	                        	debugger;
	                        	if(result.success!=undefined||result.success==false)
                        		{
	                        		dialog.find('.modal-content').message({
	    	                            type: 'error',
	    	                            content: result.errorMessage
	                        		});
                        		}
	                        	else{
	                        		dialog.find('.modal-content').message({
	    	                            type: 'error',
	    	                            content: result.actionError
	                        		});
	                        	}
	                        }
	                    });
	                });
	        });
	    },
	    failPrint: function (id, gri,lotid) {
            var self = this;
            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Fail品标签打印</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">打印</button></div></div></div></div>');
            $.get('<%=path%>/FTFailLabelPrint.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);                                                                                                                                                                                                                                                                                                                                                                                          
                self.initPage(dialog.find('form'));
                $.get('${pageContext.request.contextPath}/FTLotNodeOption/getFTResultByLotId/' + lotid + '.koala?labelType=FAIL').done(function (json) {
                    json = json.data;
                    if (json['taxType'] == "非保税") {
                        dialog.find(".taxType").remove();
                    }
                    var elm;
                    for (var index in json) {
                        elm = dialog.find('#' + index + 'ID');
                        if (elm.hasClass('select')) {
                            elm.setValue(json[index]);
                        } else {
                            elm.val(json[index]);
                        }
                    }
                    json = json.binInfo;
                    for (var index in json) {
                        elm = dialog.find('#' + index + 'ID');
                        if (json[index] == "-1"||!!json[index]==0) {
                            continue;
                        } else {
                            elm.val(json[index]);
                        }
                    }
                    var sum = 0;
                    dialog.find(".sum").each(function () {
                        sum += parseInt($(this).val());
                    })
                    dialog.find("#failID").val(sum);
                    dialog.find(".sum").bind('change', function () {
                        var sum = 0;
                        dialog.find(".sum").each(function () {
                            sum += parseInt($(this).val());
                        });
                        dialog.find("#failID").val(sum);
                    })
                });
                dialog.modal({
                    keyboard: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    }
                });
                dialog.find('#save').on('click', {grid: grid}, function (e) {
                    if (!Validator.Validate(dialog.find('form')[0], 3))return;
                    if (!!$("#labelNameID").val() == 0) {
                        dialog.message({
                            type: 'warning',
                            content: '未找到对应标签请检查'
                        });
                        return false;
                    }

                    var path = "C:/WMS/PRINT/"+$("#labelNameID").val();
                    var parameter=dialog.find('form').serialize();
                    onPrint(path,parameter);
                });
            });
        },
	    special: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">特殊标示</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/MiddleStorage-special.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	                dialog.modal({
	                    keyboard:false 
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                $.post('${pageContext.request.contextPath}/ReelDiskTransferStorage/specialSign.koala?id='+id, dialog.find('form').serialize()).done(function(results){
	                    if(results.success){
							dialog.modal('hide');
							e.data.grid.data('koala.grid').refresh();
                            e.data.grid.message({
                            	type: 'success',
                            	content: '标示成功'
                            });
						}else{
							dialog.find('.modal-content').message({
								type:'error',
								content:results.actionError
							})
						}
	                });
	        })
	        });
	    },
	    split: function(id, grid,rowdata){
	    	var self = this;
			var quality = rowdata.quality;
            // 拆分pass
            if (quality == "pass") {
            	var dialog = $('<div class="modal fade"><div class="modal-dialog">'
    					+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
    					+'data-dismiss="modal" aria-hidden="true">&times;</button>'
    					+'<h4 class="modal-title">拆批</h4></div><div class="modal-body">'
    					+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
    					+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
    					+'<button type="button" class="btn btn-success" id="confirm">确定</button></div></div>'
    					+'</div></div>');
    			$.get('<%=path%>/FT_ReelCodeSeparate.jsp').done(function(html){
    				dialog.find('.modal-body').html(html);
                    self.initPage(dialog.find('form'));
    				dialog.modal({
                        keyboard:false 
                    }).on({
                        'hidden.bs.modal': function(){
                            $(this).remove();
                        },'shown.bs.modal' :function(){
                        	debugger;
                        	dialog.find('#reelcode').val(rowdata.reelCode);
            				dialog.find('#quality').val(rowdata.quality);
            				dialog.find('#amount').val(rowdata.quantity);
                        }
                    });
                })
                dialog.find('#confirm').on('click',{grid: grid}, function (e) {
                        var separateData = dialog.find('#separateQty').val();
                        if ((separateData < 0) || (separateData > rowdata.quantity)) {
                            dialog.message({
                                type: warning,
                                content: '分拆数量不能小于0或大于总数量'
                            });
                            return;
                        }
                        var dataSep = {'reelId': id, 'separateQty': dialog.find('#separateQty').val()}
                        $.post('${pageContext.request.contextPath}/ReelDisk/separateReelDisk.koala', dataSep).done(function (results) {
                            if (results.success) {
                                dialog.modal('hide');
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                	type: 'success',
                                	content: '分拆成功'
                                });
                            } else {
                                dialog.find('.modal-content').message({
                                    type: 'error',
                                    content: results.actionError
                                })
                            }
                        })
                    })
                // 拆分fail
            } else if (quality == "fail") {
                $.get('${pageContext.request.contextPath}/ReelDisk/get/' + id + '.koala').done(function (failReelDisk) {
                    if (!failReelDisk.success) {
                        return;
                    }
                    failReelDisk = failReelDisk.data;
                    var failReelDiskId = failReelDisk.id;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" >'
                            + '<div class="modal-content" style="width:800px"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">Fail品</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="separateSave">保存</button></div></div>'
                            + '</div></div>');
					$.get('<%=path%>/FT_ReelDisksFailSeparate.jsp').done(function (html) {
                                dialog.modal({
                                    keyboard: false
                                }).on({
                                    'hidden.bs.modal': function () {
                                        $(this).remove();
                                    }
                                }).find('.modal-body').html(html);


                                // 更新界面
                                for (var propertyName in failReelDisk) {
                                    if (["reelCode", "quality"].indexOf(propertyName) != -1) {
                                        var propertyValue = failReelDisk[propertyName] != null ? failReelDisk[propertyName] : "";
                                        dialog.find('#tableFailReelDisk thead tr:eq(0)').append($('<th style="width:120px">' + propertyName + '</th>'));
                                        dialog.find('#tableFailReelDisk tbody tr:eq(0)').append($('<td style="width:120px">' + propertyValue + '</td>'));
                                    } else if (["ftResultDTO"].indexOf(propertyName) != -1) {
                                        var ftResultDTO = failReelDisk[propertyName];
                                        for (var ftResultDTOPropertyName in ftResultDTO) {
                                            var ftResultDTOPropertyValue = ftResultDTO[ftResultDTOPropertyName];
                                            // 创建bin列
                                            if (ftResultDTOPropertyName.startsWith("bin")) {
                                                var binValue = ftResultDTOPropertyValue;
                                                for (var binIndex = 0; binIndex < 20; ++binIndex) {
                                                    if (binValue[binIndex] != "-1") {
                                                        dialog.find('#tableFailReelDisk thead tr:eq(0)').append($('<th class="bin">bin' + (binIndex + 1) + '</th>'));
                                                        dialog.find('#tableFailReelDisk tbody tr:eq(0)').append($('<td><input name="bin' + (binIndex + 1) + '" type="text" class="binValue" style="width:50px;" value="' + binValue[binIndex] + '"></td>'));
                                                    }
                                                }
                                                // 创建其他属性列
                                            } else if (["loss", "other"].indexOf(ftResultDTOPropertyName) != -1) {
                                                dialog.find('#tableFailReelDisk thead tr:eq(0)').append($('<th>' + ftResultDTOPropertyName + '</th>'));
                                                dialog.find('#tableFailReelDisk tbody tr:eq(0)').append($('<td><input type="text" name="' + ftResultDTOPropertyName + '" style="width:50px;" value="' + ftResultDTOPropertyValue + '"></td>'));
                                            }
                                        }
                                    }
                                }

                                // 点击分盘按钮，动态生成对应数目的行
                                dialog.find("#buttonSeparateFailReelDisk").on("click", function () {
                                    var separateCount = dialog.find("option:selected").val();
                                    dialog.find("#tableSeparatedFailReelDisks tr").remove();
                                    var tr0 = dialog.find('#tableFailReelDisk thead tr:eq(0)').clone();
                                    dialog.find("#tableSeparatedFailReelDisks thead").append(tr0);
                                    for (var i = 0; i < separateCount; ++i) {
                                        var tr1 = dialog.find('#tableFailReelDisk tbody tr:eq(0)').clone();
                                        dialog.find("#tableSeparatedFailReelDisks tbody").append(tr1);
                                    }
                                });
                        //debugger;
                        dialog.find('#separateSave').on('click', {grid: grid} ,function (e) {
                            var $tableSeparatedFailReelDisksBody = dialog.find("#tableSeparatedFailReelDisks").find("tbody");
                            var losses = $tableSeparatedFailReelDisksBody.find("[name=loss]");
                            var others = $tableSeparatedFailReelDisksBody.find("[name=other]");
                            var bins = [];
                            for (var binIndex = 0; binIndex < 20; ++binIndex) {
                                bins[binIndex] = $tableSeparatedFailReelDisksBody.find("[name=bin" + (binIndex + 1) + "]");
                            }

                            var numOfRows = $tableSeparatedFailReelDisksBody.find("tr").length;
                            var separateBins1 = [];
                            for (var index = 0; index < numOfRows; ++index) {
                                var separateBin = {
                                    "loss": losses[index].value,
                                    "other": others[index].value
                                };
                                var bin = [];
                                for (var binIndex = 0; binIndex < 20; ++binIndex) {
                                    var temp0 = bins[binIndex][index];
                                    if (temp0 == undefined) {
                                        bin[binIndex] = "-1";
                                    } else {
                                        var value = temp0.value;
                                        if (value == undefined || value == "")
                                            value = "0";
                                        bin[binIndex] = value;
                                    }
                                }
                                separateBin["bin"] = bin;
                                separateBins1.push(separateBin);
                            }
                            ////debugger;

                            var params = "reelDiskId=" + failReelDiskId + "&" + "separateBins=" + JSON.stringify(separateBins1);
                            $.post('${pageContext.request.contextPath}/ReelDisk/separateFailReelDisk.koala', params).done(function (results) {
                                if (results.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                    	type: 'success',
                                    	content: '分拆成功'
                                    });
                                } else {
                                    dialog.find('.modal-content').message({
                                        type: 'error',
                                        content: results.actionError
                                    })
                                }
                            });
                        });
                    });
                });
            }
	    },
		merge:function(){
			var self = this;
			var totalAmount=0;
			var ids = [];
			var flag=0;
			var combinedLotNumber = '';
			var dialog = $('<div class="modal fade"><div class="modal-dialog">'
					+'<div class="modal-content" style="width:750px;"><div class="modal-header"><button type="button" class="close" '
					+'data-dismiss="modal" aria-hidden="true">&times;</button>'
					+'<h4 class="modal-title">合批</h4></div><div class="modal-body">'
					+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
					+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
					+'<button type="button" class="btn btn-success" id="reelItg">合批</button></div></div>'
					+'</div></div>');
			$.get('<%=path%>/FT_ReelCodeItg.jsp').done(function (html) {
                dialog.modal({
                    keyboard: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    },
                    'shown.bs.modal': function () {
                        var lotNumbercolumns = [{
                            title: "批号",
                            name: "internalLotNumber",
                            width: 100
                        }];
                        dialog.find('#lotNumberTable').grid({
                            identity: 'id',
                            columns: lotNumbercolumns,
                            isShowPages: false,
                            url: "${pageContext.request.contextPath}/FTLot/pageJson.koala"
                        });
                        var reelcolumns = [{
                            title: "Reel",
                            name: "reelCode",
                            width: 100
                        }];
                        dialog.find('#reelTable').grid({
                            identity: 'id',
                            columns: reelcolumns,
                            isShowPages: false,
                            url: "${pageContext.request.contextPath}/ReelDisk/pageJson.koala"
                        });
                        dialog.find('#reelTable').getGrid().search({'logic': 0});
                    }
                }).find('.modal-body').html(html);
                var reelId = $checkObj.val();
                /*   var rowData;
                 $.each(result, function () {
                 if (reelId == this.id) {
                 rowData = this;
                 return false;
                 }
                 })*/
                dialog.find('#reelcode').on('keyup', function () {
                    var params = {};
                    params['reelCode'] = dialog.find('#reelcode').val();
                    //params['logic'] = 0;
                    dialog.find('#reelTable').getGrid().search(params);
                })
                dialog.find('#internalLotNumber').on('keyup', function () {
                    var params = {};
                    params['internalLotNumber'] = dialog.find('#internalLotNumber').val();
                    dialog.find('#lotNumberTable').getGrid().search(params);
                })

                dialog.find('#reelItg').on('click',{grid: grid}, function (e) {
                    var LotId = dialog.find('#lotNumberTable').getGrid().selectedRowsIndex();
                    var toReelId = dialog.find('#reelTable').getGrid().selectedRowsIndex();
                    if ((toReelId.length > 1) || (LotId.length > 1) || ((LotId.length == 1) && (toReelId.length == 1))) {
                        $('body').message({
                            type: 'warning',
                            content: '只能选择一条记录进行合批'
                        });
                        return;
                    } else if ((LotId.length == 0) && (toReelId.length == 0)) {
                        $('body').message({
                            type: 'warning',
                            content: '请选择一条记录进行合批'
                        });
                        return;
                    } else if ((LotId.length == 1) && (toReelId.length == 0)) {
                        var dataItg = {'reelId': reelId, 'LotId': LotId[0]}
                        $.post('${pageContext.request.contextPath}/ReelDisk/gotoLot.koala', dataItg).done(function (options) {
                            if (options.success) {
                                dialog.modal('hide');
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                	type: 'success',
                                	content: '合批成功'
                                });
                            } else {
                                dialog.find('.modal-content').message({
                                    type: 'error',
                                    content: options.actionError
                                })
                            }
                        })
                    } else if ((LotId.length == 0) && (toReelId.length == 1)) {
                        var dataItg = {'reelId': reelId, 'toReelId': toReelId[0]}
                        $.post('${pageContext.request.contextPath}/ReelDisk/integrateReelDisk.koala', dataItg).done(function (options) {
                            if (options.success) {
                                //debugger;
                                dialog.modal('hide');
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                	type: 'success',
                                	content: '合批成功'
                                });
                            } else {
                                dialog.find('.modal-content').message({
                                    type: 'error',
                                    content: options.actionError
                                })
                            }
                        })
                    }
                })
            });
		},
		reworkApply: function(id, grid,lotid){
			var self = this;
			var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1010px"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">重工申请</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
			$.get('<%=path%>/FTReworkApply.jsp').done(function(html){
				dialog.find('.modal-body').html(html);
				self.initPage(dialog.find('form'));
				//重工单赋值************************************************************************************************************
				$.get('${pageContext.request.contextPath}/FTLot/get/' + lotid + '.koala').done(function (json) {
					json = json.data;
					var elm;
					for(var index in json){
						elm = dialog.find('#'+ index + 'ID');
						if(elm.hasClass('select')){
							elm.setValue(json[index]);
						}else{
							elm.val(json[index]);
							elm.text(json[index]);
						}
					}
					dialog.find("#customerNumberID").text(json['customerLotDTO']['customerNumber']);
				});
				var mydate=new Date();
				dialog.find("[name='reworkDate']").text(mydate.toLocaleDateString());
				$.get( '${pageContext.request.contextPath}/FTRework/createReworkNo/'+lotid+'.koala').done(function(json){
					dialog.find("td[name='reworkNo']").text(json['data']);
				});//获取重工单编号；
				$.get( '${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function(json){
					dialog.find("td[name='reworkDepartment']").text(json['data']['userAccount']+"|"+json['data']['name']);
					dialog.find("span[name='optpreson']").text(json['data']['userAccount']+"|"+json['data']['name'])
				});//获取申请人
				//*********************************************************************************************************************
				var authorizationMember=dialog.find('#authorizationMemberID');
				authorizationMember.on('click', function(){
					$.get(contextPath + '/pages/domain/StaffSelect.jsp').done(function(data){
						debugger;
						 var staffDialog = $(data);
						 staffDialog.modal({
								backdrop: false,
								keyboard: false
							}).on({
								'hidden.bs.modal': function(){
									$(this).remove();
								},
								'shown.bs.modal' : function(){
									var notGrantRolecolumns = [{title : "工号",name : "userAccount",width : 120}, {title : "姓名",name : "name",width : 80},{title : "部门",name : "employeeOrgName",width : 80}];
									staffDialog.find('#staffGrid').grid({
										identity: 'id',
										columns: notGrantRolecolumns,
										isShowPages:false,
										url: "${pageContext.request.contextPath}/auth/employeeUser/pagingQuery.koala"
									});
									staffDialog.find("#staffGrid .grid-table-body").css("overflow-x","hidden");
									staffDialog.find("#staffGrid .grid-table-body").css("height","181px");
								}
							});
						 staffDialog.find('#searchFTLot').on('click',function(){
								if(!Validator.Validate(staffDialog.find('form')[0],3))return;
								var params = {};
								staffDialog.find('#serachItems').find('input').each(function(){
									var $this = $(this);
									var name = $this.attr('name');
									if(name){
										params[name] = $this.val();
									}
								});
								staffDialog.find('#staffGrid').getGrid().search(params);
							});
					    staffDialog.find('#staffConfirm').on('click',function(){
				    	 	var grantRolesToUserTableItems = staffDialog.find('#staffGrid').getGrid().selectedRows();
							if(grantRolesToUserTableItems.length == 0){
								staffDialog.find('#staffGrid').message({
									type: 'warning',
									content: '请选择审核人'
								});
								return;
							}
							debugger;
							$(".applyPerson").append("<button type='button' staffId='"+grantRolesToUserTableItems[0]['id']+"' style='margin-right:20px;' class='btn btn-primary'>"+grantRolesToUserTableItems[0]['name']+"<span class='glyphicon glyphicon-remove' style='cursor: pointer;margin-left: 5px;' onclick='$(this).parent().remove();'></span></button>");
							staffDialog.modal('hide');
						});
					})
				});
				//选定审核人
				//*********************************************************************************************************************
				dialog.modal({
					keyboard:false
				}).on({
					'hidden.bs.modal': function(){
						$(this).remove();
					}
				});
				dialog.find('#save').on('click',{grid: grid}, function(e){
					if(!Validator.Validate(dialog.find('form')[0],3))return;
					var postJson={};
					var basic={};
					for(var a=0;a<dialog.find(".basic").length;a++)
					{
						var names=dialog.find(".basic").eq(a).attr("name");
						if(dialog.find(".basic").eq(a)[0].tagName=="TD")
						{
							basic[names]=dialog.find(".basic").eq(a).text();
						}
						else{
							basic[names]=dialog.find(".basic").eq(a).val();
						}
					}
					postJson['basic']=basic;
					postJson['basic']["reworkReworkQty"]=dialog.find("input[name='reworkReworkQty']").val();
					postJson['basic']["reworkTotalQty"]=dialog.find("span[name='reworkTotalQty']").text();
					if(parseInt(postJson['basic']["reworkReworkQty"])>parseInt(postJson['basic']["reworkRotalQty"]))
					{
						dialog.find('.modal-content').message({
							type: 'warning',
							content: "重工数量需小于等于总数量请检查"
						});
						return;
					}
					postJson['reason']={};
					postJson['reason']['reasons']=dialog.find("input[name='Rework_reason']:checked").next().text();
					postJson['reason']['explanation']=dialog.find("textarea[name='explanation']").val();
					if(postJson['reason']['reasons']=="其他(Others)")
					{
						postJson['reason']['other']=dialog.find("input[name='Rework_reason']:checked").next().next().val()
					}
					var itemArray=[];
					var itemTable=dialog.find(".rework_item").children("tr");
					for(var a=0;a<itemTable.length;a++)
					{
						var itemJson={};
						itemJson['attentionItem']=itemTable.eq(a).find("input").eq(0).val();
						itemJson['reworkFlow']=itemTable.eq(a).find("input").eq(1).val();
						itemJson['operator']=itemTable.eq(a).find("input").eq(2).val();
						itemJson['accomplishDate']=itemTable.eq(a).find("input").eq(3).val();
						itemArray.push(itemJson);
					}
					postJson['item']=itemArray;
					postJson['summary']=dialog.find("input[name='Rework_over']:checked").next().text();
					applayPresonArray=[];
					for(var a=0;a<$(".applyPerson button").length;a++)
					{
						var applayPreson={};
						applayPreson['id']=$(".applyPerson button").eq(a).attr("staffid");
						applayPreson['name']=$(".applyPerson button").eq(a).text();
						applayPresonArray.push(applayPreson)
					}
					postJson['approvePerson']=applayPresonArray;
					var a={};
					a['data']=JSON.stringify(postJson);
					$.post('${pageContext.request.contextPath}/FTRework/add.koala?id='+lotid, a).done(function(result){
						if(result.success){
							dialog.modal('hide');
							e.data.grid.data('koala.grid').refresh();
							e.data.grid.message({
								type: 'success',
								content: '申请成功'
							});
						}else{
							debugger;                                                                                                                                                                                                       
							if(result.success!=undefined||result.success==false)
							{
								dialog.find('.modal-content').message({
									type: 'error',
									content: result.errorMessage
								});
							}
							else{
								dialog.find('.modal-content').message({
									type: 'error',
									content: result.actionError
								});
							}
						}
					});
				});
			});
		},
		rework: function(id, grid,lotid){
			var self = this;
			var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:820px"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">重工下单</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
			$.get('<%=path%>/MiddleStorage-rework.jsp').done(function(html){
				dialog.find('.modal-body').html(html);
				self.initPage(dialog.find('form'));
				//重工单赋值************************************************************************************************************
				$.get( '${pageContext.request.contextPath}/ReelDiskTransferStorage/get/' + id[0] + '.koala').done(function(json){
					json = json.data;
					var elm;
					for(var index in json){
						if(index=="qty")continue;
						elm = dialog.find('#'+ index + 'ID');
						if(elm.hasClass('select')){
							elm.setValue(json[index]);
						}else{
							elm.val(json[index]);
							elm.text(json[index]);
						}
						if(typeof(json[index])=="object")
						{
							for(var index1 in json[index]){
								elm = dialog.find('#'+ index1 + 'ID');
								if(elm.hasClass('select')){
									elm.setValue(json[index][index1]);
								}else{
									elm.val(json[index][index1]);
									elm.text(json[index][index1]);
								}
								if(typeof(json[index][index1])=="object")
								{
									for(var index2 in json[index][index1]){
										elm = dialog.find('#'+ index2 + 'ID');
										if(elm.hasClass('select')){
											elm.setValue(json[index][index1][index2]);
										}else{
											elm.val(json[index][index1][index2]);
											elm.text(json[index][index1][index2]);
										}
									}
								}
							}
						}
					}
					dialog.find("#materialID").setValue(json['ftLotDTO']['customerLotDTO']['materialType']);
					debugger;
				});
				$.ajax({
				    async: false,
				    url: '${pageContext.request.contextPath}/FTProcess/findFTProcessByFTLotId/'+lotid+'.koala',
				    type: 'POST',
				    dataType: 'json',
				}).done(function (msg) {
				    $("select[name='toPreson']").empty();
				    var contents = [ {title : '请选择',value : ''} ];
				    var station=msg['data']['content'].split("|");
				    var length=station.length;
				    for(var i =0;i<length;i++)
				    	{
				    		contents.push({title :station[i] ,value : i} );
				    	}
				    dialog.find('#stationID').select({
		                title: '请选择',
		                contents: contents
		           	}).on('change',function(){
		               form.find('#stationID_').val($(this).getValue());
		           	});
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
					$.post('${pageContext.request.contextPath}/ReelDiskTransferStorage/reworkLot.koala?ids='+id.join(","), dialog.find('form').serialize()).done(function(result){
						if(result.success){
							dialog.modal('hide');
							e.data.grid.data('koala.grid').refresh();
							e.data.grid.message({
								type: 'success',
								content: '下单成功'
							});
						}else{
							debugger;                                                                                                                                                                                                       
							if(result.success!=undefined||result.success==false)
							{
								dialog.find('.modal-content').message({
									type: 'error',
									content: result.errorMessage
								});
							}
							else{
								dialog.find('.modal-content').message({
									type: 'error',
									content: result.actionError
								});
							}
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
	    	$.post('${pageContext.request.contextPath}/ReelDisk/delete.koala', data).done(function(result){
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
	    borrow: function(ids, grid){
	    	var data = [{ name:"ids" , value: ids.join(',') },{ name: "status", value: "借料" }];
	    	$.post('${pageContext.request.contextPath}/ReelDiskTransferStorage/changeStatus.koala', data).done(function(result){
	                        if(result.success){
	                            grid.data('koala.grid').refresh();
	                            grid.message({
	                                type: 'success',
	                                content: '标记成功'
	                            });
	                        }else{
	                            grid.message({
	                                type: 'error',
	                                content: result.result
	                            });
	                        }
	    	});
	    },
	    loss: function(ids, grid){
	    	var data = [{ name:"ids" , value: ids.join(',') },{ name: "status", value: "丢料" }];
	    	$.post('${pageContext.request.contextPath}/ReelDiskTransferStorage/changeStatus.koala', data).done(function(result){
	                        if(result.success){
	                            grid.data('koala.grid').refresh();
	                            grid.message({
	                                type: 'success',
	                                content: '标记成功'
	                            });
	                        }else{
	                            grid.message({
	                                type: 'error',
	                                content: result.result
	                            });
	                        }
	    	});
	    },
	    mergeRestore: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids }];
	    	$.post('${pageContext.request.contextPath}/ReelDisk/delete.koala', data).done(function(result){
	                        if(result.success){
	                            grid.data('koala.grid').refresh();
	                            grid.message({
	                                type: 'success',
	                                content: '还原成功'
	                            });
	                        }else{
	                            grid.message({
	                                type: 'error',
	                                content: result.result
	                            });
	                        }
	    	});
	    },
	    shipping: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids }];
	    	$.post('${pageContext.request.contextPath}/ReelDiskTransferStorage/shipping.koala', data).done(function(result){
	    		debugger;
	                        if(result.success){
	                            grid.data('koala.grid').refresh();
	                            grid.message({
	                                type: 'success',
	                                content: result.data
	                            });
	                        }else{
	                            grid.message({
	                                type: 'error',
	                                content: result.errorMessage
	                            });
	                        }
	    	});
	    },
		exportExcel: function (ids, grid) {
			var data = [{name: 'ids', value: ids.join(',')}];
			$.post('${pageContext.request.contextPath}/FTLot/exportExcel.koala', data).done(function (result) {
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
        $.get('<%=path%>/MiddleStorage-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/ReelDisk/get/' + id + '.koala').done(function(json){
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
                        	if(typeof(json[index])=="object")
                        	{
                        		for(var index1 in json[index]){
                                	if(json[index][index1]+"" == "false"){
                                		dialog.find('#'+ index1 + 'ID').html("<span style='color:#d2322d' class='glyphicon glyphicon-remove'></span>");
                                	}else if(json[index][index1]+"" == "true"){
                                		dialog.find('#'+ index1 + 'ID').html("<span style='color:#47a447' class='glyphicon glyphicon-ok'></span>");
                                	}else{
                                  		dialog.find('#'+ index1 + 'ID').html(json[index][index1]+"");
                                	}
                        		}
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
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">客户批号:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="customerLotNumber" class="form-control" type="text" style="width:180px;" id="customerLotNumberID"  />
        </div>
         <label class="control-label" style="width:100px;float:left;">来料时间:&nbsp;</label>
           <div style="margin-left:15px;float:left;">
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="packagingTime" id="incomingTimeID_start" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
            <div class="input-group date form_datetime" style="width:160px;float:left;" >
                <input type="text" class="form-control" style="width:160px;" name="packagingTimeEnd" id="incomingTimeID_end" >
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
       </div>
            </div>
                <div class="form-group">
                    <label class="control-label" style="width:100px;float:left;">艾科批号:&nbsp;</label>
		            <div style="margin-left:15px;float:left;">
		            <input name="internalLotNumber" class="form-control" type="text" style="width:180px;" id="internalLotNumberID"  />
		        </div>
		        <label class="control-label" style="width:100px;float:left;">品质:&nbsp;</label>
	            <div style="margin-left:15px;float:left;">
		            <div class="btn-group select" id="qualityID"></div>
		            <input name="quality" type="hidden" id="qualityID_"/>
		        </div>
		        <label class="control-label" style="width:100px;float:left;">状态:&nbsp;</label>
	            <div style="margin-left:15px;float:left;">
		            <div class="btn-group select" id="statusID"></div>
		            <input name="status" type="hidden" id="statusID_"/>
		        </div>
		        <label class="control-label" style="width:100px;float:left;">合批:&nbsp;</label>
	            <div style="margin-left:15px;float:left;">
		            <div class="btn-group select" id="isCombinedID"></div>
		            <input name="isCombined" type="hidden" id="isCombinedID_"/>
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
