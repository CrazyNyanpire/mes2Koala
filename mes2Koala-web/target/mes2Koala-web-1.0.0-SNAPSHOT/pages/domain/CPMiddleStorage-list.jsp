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
				        contents.push( {title : '在库' ,value : 'Fail'} );
				        contents.push( {title : '出库' ,value : 'Pass'} );
				        contents.push( {title : '丢料' ,value : 'Pass1'} );
				        contents.push( {title : '借料' ,value : 'Pass2'} );
				        contents.push( {title : '重工' ,value : 'Pass2'} );
				        form.find('#statusID').select({
			                title: '请选择',
			                contents: contents
			           	}).on('change',function(){
			               form.find('#statusID_').val($(this).getValue());
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
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-success" type="button">修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-primary" type="button">重工建批</button>', action: 'rework'},
	                        {content: '<button class="btn btn-danger" type="button">重工申请</button>', action: 'reworkApply'},
	                        {content: '<button class="btn btn-danger" type="button">分批</button>', action: 'split'},
	                        {content: '<button class="btn btn-primary" type="button">合批</button>', action: 'merge'},
/* 	                        {content: '<button class="btn btn-primary" type="button">退库记录</button>', action: 'record'}, */
	                    ],
	                url:"${pageContext.request.contextPath}/CPTransferStorage/pageJson.koala",
	                columns: [
	                            {title: '状态', name: 'currentState', width: width},
	                            {title: '客户批号', name: 'customerLotNumber', width: width,
	                                render: function (rowdata, rowindex, value) {
	                                    return rowdata['customerCPLotDTO'][rowindex];
	                                }
	                            },
	                            {title: '内部批号', name: 'internalLotNumber', width: width},
	                            {title: '封装厂批号', name: 'packageNumber', width: width},
	                            {title: '客户编号', name: 'customerNumber', width: width,
	                                render: function (rowdata, rowindex, value){
	                                    return rowdata['customerCPLotDTO'][rowindex];
	                                }
	                            },
	                            {title: '产品编号', name: 'customerNumber', width: width},
	                            {title: '客户型号', name: 'internalLotNumber', width: width},
	                            {title: '出货型号', name: 'shipmentProductNumber', width: width},
	                            {title: '数量', name: 'diskContent', width: width},
	                            {title: '片号', name: 'packageNumber', width: width},
	                            {title: '测试设备', name: 'tester', width: width},
	                            {title: 'MASK Name', name: 'maskName', width: width,
	                                render: function (rowdata, rowindex, value){
	                                    return rowdata['customerCPLotDTO'][rowindex];
	                                }
	                            },
	                            {title: '来料时间', name: 'createTimestamp', width: width,
	                                render: function (rowdata, rowindex, value){
	                                    return rowdata['customerCPLotDTO']['createTimestamp'];
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
	                                content: '此批已Hold请确认'
	                            })
	                            return;
                        	}
	                       self.split(indexs[0], $this,data['item'][0]);
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
	                    'record': function(event, data){
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
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
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
	            		var v = document.getElementById("activeX").Print(path, 1, parameter);
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
	    failPrint: function(id, grid,lotid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Fail品标签打印</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">打印</button></div></div></div></div>');
	        $.get('<%=path%>/FTFailLabelPrint.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               debugger;
                   $.get( '${pageContext.request.contextPath}/FTLotNodeOption/getFTResultByLotId/' + lotid + '.koala?labelType=FAIL').done(function(json){
	                       json = json.data;
	                      	if(json['taxType']=="非保税")
                       		{
	                      		dialog.find(".taxType").remove();
                       		}
	                        var elm;
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(elm.hasClass('select')){
	                                elm.setValue(json[index]);
	                            }else{
	                                elm.val(json[index]);
	                            }
	                        }
	                        json = json.binInfo;
	                       	for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(json[index]=="-1"){
	                                continue;
	                            }else{
	                                elm.val(json[index]);
	                            }
	                     	}
	                       	var sum=0;
	                       	dialog.find(".sum").each(function(){sum+=parseInt($(this).val());})
	                       	dialog.find("#failID").val(sum);
	                       	dialog.find(".sum").bind('change',function(){
	                       		var sum=0;
		                       	dialog.find(".sum").each(function(){sum+=parseInt($(this).val());});
		                       	dialog.find("#failID").val(sum);
	                       	})
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
	                    if($("labelNameID").val()==""||$("labelNameID").val()==undefined)
                    	{
	                    	dialog.message({
	                            type: 'warning',
	                            content: '未找到对应标签请检查'
                            });
                    		return false;
                    	}
	                    var path="C:/WMS/PRINT/tests.btw";//+$("labelNameID").val()
	                    onPrint(path);
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
	    split: function (id, grid) {
            var self = this;
            var originNumber = 0;
            var cpLotDTO = [];
            var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1000px">'
                    + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                    + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                    + '<h4 class="modal-title">分批</h4></div><div class="modal-body">'
                    + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                    + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                    + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                    + '</div></div>');
            $.get('<%=path%>/CPSplit.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);
                $.get('${pageContext.request.contextPath}/CPLot/get/' + id + '.koala').done(function (json) {
                    json = json.data;
                    cpLotDTO = json;
                    dialog.find('#quantityID').val(json['qty']);
                    //dialog.find('#lotNumberID').val(json['internalLotNumber']);
                    dialog.find('#internalLotNumber').text(json['internalLotNumber']);
                    dialog.find('#currentState').text(json['currentState']);
                    dialog.find('#shipmentProductNumber').text(json['shipmentProductNumber']);
                    dialog.find('#qty').text(json['cPWaferDTOs'].length);
                  	for (var i = 0;i < json['cPWaferDTOs'].length;i++) {
                         var waferCode = json['cPWaferDTOs'][i]["internalWaferCode"].substring(json['cPWaferDTOs'][i]["internalWaferCode"].lastIndexOf("-")+1);
                         dialog.find('#motherlot' + '-' + waferCode).attr("checked",true);
                    }
                });
                dialog.modal({
                    keyboard: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    }
                }).find('.modal-body').html(html);
                dialog.find('#divideAmountID').on('change', function () {
                    dialog.find('#childrenLotNumberSpan').html('');
                    dialog.find('#childrenLotNumberText').html('');
                    dialog.find('#childrenShipmentNumberSpan').html('');
                    dialog.find('#childrenShipmentNumberText').html('');
                    var number = dialog.find('#divideAmountID').val();
                    //debugger;
                    for (var i = 0; i < parseInt(number); i++) {
                    	if ( i == 0 ) {
                    		var htmlSpan = $('<p style="margin-top: 8%;"><span>子批' + parseInt(i + 1) + ':</span></p>');  
                    	} else {
                    		var htmlSpan = $('<p style="margin-top: 37%;"><span>子批' + parseInt(i + 1) + ':</span></p>');  
                    	}
                    	var htmlText = $('<p><input style="width:100%;"type="text" id="lotNumber' + parseInt(i + 1) + '" class="form-control"  value=' + dialog.find('#internalLotNumber').text() + "-" + parseInt(i + 1) + '></p>');  
                    	dialog.find('#childrenLotNumberSpan').append(htmlSpan);
                        dialog.find('#childrenLotNumberText').append(htmlText);
                    }
                    for (var i = 0; i < parseInt(number); i++) {
                    	if ( i == 0 ) {
                    		var htmlSpan = $('<p style="margin-top: 8%;"><span>出货' + parseInt(i + 1) + ':</span></p>');  
                    	} else {
                    		var htmlSpan = $('<p style="margin-top: 37%;"><span>出货' + parseInt(i + 1) + ':</span></p>');  
                    	}  
                    	var htmlText = $('<p><input style="width:100%;"type="text" id="shipmentNumber' + parseInt(i + 1) + '" class="form-control" value=' + dialog.find('#shipmentProductNumber').text() + '></p>');  
                    	dialog.find('#childrenShipmentNumberSpan').append(htmlSpan);
                        dialog.find('#childrenShipmentNumberText').append(htmlText);
                    }
                })
                dialog.find('#splitID').on('click', function () {
                    dialog.find('.CHILDRENLOTINFO').html('');
                    var number = dialog.find('#divideAmountID').val();
                    var html = "<tr border='1px' cellspacing='0'  bordercolor=''#00CCCC'><th>批号</th><th>状态</th><th>出货型号</th><th>数量</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th><th>13</th><th>14</th><th>15</th><th>16</th><th>17</th><th>18</th><th>19</th><th>20</th><th>21</th><th>22</th><th>23</th><th>24</th><th>25</th></tr>";;
                    dialog.find('.CHILDRENLOTINFO').append(html);
                    for (var i = 0; i < parseInt(number); i++) {
                    	var html ;
                    	if (i == 0) {
                    		html = "<tr style='background-color: #CFF;' border='1px' cellspacing='0'  bordercolor='#00CCCC' id='lot" + parseInt(i + 1) + "'><th id='internalLotNumber" + parseInt(i + 1) + "'>"+ dialog.find('#lotNumber' + parseInt(i + 1) + '').val() +"</th><th id='currentState" + parseInt(i + 1) + "'>"+ dialog.find('#currentState').text() +"</th><th id='shipmentProductNumber" + parseInt(i + 1) + "'>"+ dialog.find('#shipmentNumber' + parseInt(i + 1) + '').val() +"</th><th id='qty" + parseInt(i + 1) + "'></th>";
                    	}else{
                    		html = "<tr style='background-color: #CFF;' border='1px' cellspacing='0'  bordercolor='#00CCCC' id='lot" + parseInt(i + 1) + "'><th id='internalLotNumber" + parseInt(i + 1) + "'>"+ dialog.find('#lotNumber' + parseInt(i + 1) + '').val() +"</th><th id='currentState" + parseInt(i + 1) + "'>"+ dialog.find('#currentState').text() +"</th><th id='shipmentProductNumber" + parseInt(i + 1) + "'>"+ dialog.find('#shipmentNumber' + parseInt(i + 1) + '').val() +"</th><th id='qty" + parseInt(i + 1) + "'>0</th>";
                    	}
                    	
                    dialog.find('.CHILDRENLOTINFO').append(html
                    		                              +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-01'></th>"
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-02'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-03'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-04'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-05'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-06'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-07'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-08'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-09'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-10'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-11'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-12'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-13'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-14'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-15'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-16'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-17'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-18'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-19'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-20'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-21'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-22'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-23'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-24'></th>" 
                        		                          +"<th><input type='checkbox' id='lot" + parseInt(i + 1) + "-25'></th></tr>");
                    }
                    dialog.find('#qty1').text(dialog.find('#qty').text());
                    dialog.find('.CHILDRENLOTINFO').find("tr").each(function() {
                    	if ($(this).attr("id") == "lot1") {   
                    		var i =0;
                    	    $(this).find("th").each(function() {
            			    if ($(this).children().length > 0) {
          				        if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox") {
          					      	    $(this).children()[0].checked = false;
          					    	    $(this).children()[0].disabled = true;
          				        }
          		    	      }
                    	    });
                        }else {
                        	var j =0;
                        	$(this).find("th").each(function() {
                			    if ($(this).children().length > 0) {
              				        if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox") {
              					      	    $(this).children()[0].checked = false;
              					    	    $(this).children()[0].disabled = true;
              				        }
              		    	      }
                        	 });
                        }
                    	debugger;
        			    for (var i = 0;i < cpLotDTO['cPWaferDTOs'].length;i++) {
                            var waferCode = cpLotDTO['cPWaferDTOs'][i]["internalWaferCode"].substring(cpLotDTO['cPWaferDTOs'][i]["internalWaferCode"].lastIndexOf("-")+1);
                            dialog.find('#lot1' + '-' + waferCode).attr("checked",true);
                            dialog.find('#lot1' + '-' + waferCode).attr("disabled",false);
                            for (var j =2;j <= number;j++){
            			    	dialog.find('#lot' + j + '-' + waferCode).attr("checked",false);
                                dialog.find('#lot' + j + '-' + waferCode).attr("disabled",false);
            			    } 
                        }
                   });
                    dialog.find("input[type='checkbox']").on("click", function () {
                    	var checkboxID = $(this).attr("id");
                    	var lotnum = checkboxID.substring(0,checkboxID.indexOf("-"));
                    	var num = lotnum.substring(3);
                    	var checkboxnum = checkboxID.substring(checkboxID.indexOf("-")+1);
                    	dialog.find('.CHILDRENLOTINFO').find("tr").each(function() {
                        	if ($(this).attr("id") == lotnum) {   
                        		var i =0;
                        	    $(this).find("th").each(function() {
                			    if ($(this).children().length > 0) {
              				        if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox") {
              				      	    if ( $(this).children().is(':checked')) {
              					    	    i++;
              					        } 
              				        }
              		    	      }
                        	    });
                        	    dialog.find('.CHILDRENLOTINFO').find('#qty' + num + '').text(i);
                            }else if ($(this).attr("id")!=null && $(this).attr("id") != ""){
                            	var j =0;
                            	var otherlotnum = $(this).attr("id").substring(0,checkboxID.indexOf("-"));
                            	var othernum = otherlotnum.substring(3);
                            	dialog.find('.CHILDRENLOTINFO').find('#lot' + othernum + '-' + checkboxnum)[0].checked = false;
                            	$(this).find("th").each(function() {
                    			    if ($(this).children().length > 0) {
                  				        if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox") {
                  				      	    if ( $(this).children().is(':checked')) {
                  					    	    j++;
                  					        } 
                  				        }
                  		    	      }
                            	  });
                            	dialog.find('.CHILDRENLOTINFO').find('#qty' + othernum + '').text(j);
                            }
                       });
                    });
                })
                dialog.find('#save').on('click', {grid: grid}, function (e) {
                    if (!Validator.Validate(dialog.find('form')[0], 3))return;
                    var childsInfo = [];
                    var childsum = 0;
                    var childQtySum = 0;
                    dialog.find('.CHILDRENLOTINFO').find("tr").each(function() {
                    	childsum++;
                    });
                    if ((childsum-1) < 2) {
                        flagSperate = 0;
                        dialog.message({
                            type: 'warning',
                            content: '分批数量至少为2！'
                        })
                        return false;
                    }
                    for (var i = 1; i < childsum ; i++ ) {
                    	childQtySum = childQtySum + parseInt(dialog.find('.CHILDRENLOTINFO').find('#qty' + i + '').text());
                    }
                    if (childQtySum != parseInt(dialog.find('#qty').text())) {
                        flagSperate = 0;
                        dialog.message({
                            type: 'warning',
                            content: '子批数量之和与母批不等！'
                        })
                        return false;
                    }
                    
                    if (childQtySum == 0) {
                        flagSperate = 0;
                        dialog.message({
                            type: 'warning',
                            content: '子批数量之和为0！'
                        })
                        return false;
                    }
                    
                	$.get('${pageContext.request.contextPath}/CPLot/get/' + id + '.koala').done(function (json) {
                		json = json.data;
                        dialog.find('.CHILDRENLOTINFO').find("tr").each(function() {
                        	if ($(this).attr("id") != null && $(this).attr("id") != "") {
                        		var chliddata = {};
                        		var chlidwaferdata = [];
                            	$(this).find("th").each(function() {
                    			    if ($(this).children().length > 0) {
                  				        if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox") {
                  				      	    if ( $(this).children().is(':checked')) {
                  				      	    	var checkboxnum = $(this).children().attr("id").substring($(this).children().attr("id").indexOf("-")+1);
                  				      	        for (var j = 0;j < json['cPWaferDTOs'].length;j++) {
                                            	  var waferCode = json['cPWaferDTOs'][j]["internalWaferCode"].substring(json['cPWaferDTOs'][j]["internalWaferCode"].lastIndexOf("-")+1);
                                            	  if( parseInt(waferCode) == checkboxnum) {
                                            		  chlidwaferdata.push(json['cPWaferDTOs'][j]);
                        				      	      chliddata["cPWaferDTOs"] = chlidwaferdata;
                                            	  }
                                                }
                  					        }
                  				        } 
                  		    	     }else {
                  		    	    	 if ($(this).attr("id").startsWith("internalLotNumber")){
                  		    	    		chliddata["internalLotNumber"]=$(this).text();
                  		    	    	 }else if ($(this).attr("id").startsWith("currentState")){
                  		    	    		chliddata["currentState"]=$(this).text();
                  		    	    	 }else if ($(this).attr("id").startsWith("shipmentProductNumber")){
                  		    	    		chliddata["shipmentProductNumber"]=$(this).text();
                  		    	    	 }
               				         }
                            	 });
                            	childsInfo.push(chliddata);
                        	}
                        });
                        data = {id: id, childsInfo: JSON.stringify(childsInfo)};
                        $.post('${pageContext.request.contextPath}/CPLot/splitLot.koala', data).done(function (result) {
                            if (result.success) {
                                dialog.modal('hide');
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                    type: 'success',
                                    content: '分批成功'
                                });
                            } else {
                                if (result.success != undefined || result.success == false) {
                                    dialog.find('.modal-content').message({
                                        type: 'error',
                                        content: result.errorMessage
                                    });
                                }
                                else {
                                    dialog.find('.modal-content').message({
                                        type: 'error',
                                        content: result.actionError
                                    });
                                }
                            }
                        });
                	});
                });
            });
        },
        merge: function (id, grid) {
            var self = this;
            var hposition = 0;
            var ids = "";
            var flag = 0;
            var cpLotDTOs = [];
            var combinedLotNumber = '';
            var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1000px">'
                    + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                    + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                    + '<h4 class="modal-title">合批</h4></div><div class="modal-body" style="height:400px">'
                    + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                    + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                    + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                    + '</div></div>');
            $.get('<%=path%>/CPMerge.jsp').done(function (html) {
            	dialog.find('.modal-body').html(html);
            	$.get('${pageContext.request.contextPath}/CPLot/getChildsLot/' + id + '.koala').done(function (json) {
            		cpLotDTOs = json;
            		dialog.find('.ChildsINFO').html('');
                    var html = "<tr border='1px' cellspacing='0'  bordercolor=''#00CCCC'><th></th><th>批号</th><th>状态</th><th>数量</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th><th>13</th><th>14</th><th>15</th><th>16</th><th>17</th><th>18</th><th>19</th><th>20</th><th>21</th><th>22</th><th>23</th><th>24</th><th>25</th></tr>";;
                    dialog.find('.ChildsINFO').append(html);
                    if (json.length == 0) {
                    	 dialog.message({
                             type: 'warning',
                             content: '该批次为初始母批，不能合批'
                         })
                         return false;
                    }
                    for (var i = 0; i < json.length; i++) {
                    	var html  = "<tr style='background-color: #CFF;' border='1px' cellspacing='0'  bordercolor='#00CCCC' id='lot" + parseInt(i + 1) + "'><th><input type='checkbox' id='mergelot" + parseInt(i + 1) + "'></th><th id='mergeinternalLotNumber" + parseInt(i + 1) + "'></th><th id='mergecurrentState" + parseInt(i + 1) + "'></th><th id='mergeqty" + parseInt(i + 1) + "'></th>";
                        dialog.find('.ChildsINFO').append(html
                    		                              +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-01' disabled = true></th>"
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-02' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-03' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-04' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-05' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-06' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-07' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-08' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-09' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-10' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-11' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-12' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-13' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-14' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-15' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-16' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-17' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-18' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-19' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-20' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-21' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-22' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-23' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-24' disabled = true></th>" 
                        		                          +"<th><input type='checkbox' id='mergechildslot" + parseInt(i + 1) + "-25' disabled = true></th></tr>");
                        dialog.find('#mergeinternalLotNumber' + parseInt(i + 1) +'').text(json[i]['internalLotNumber']);
                        dialog.find('#mergecurrentState' + parseInt(i + 1) +'').text(json[i]['currentState']);
                        dialog.find('#mergeqty' + parseInt(i + 1) +'').text(json[i]['cPWaferDTOs'].length);
                        for (var j = 0;j < json[i]['cPWaferDTOs'].length;j++) {
                        	var waferCode = json[i]['cPWaferDTOs'][j]["internalWaferCode"].substring(json[i]['cPWaferDTOs'][j]["internalWaferCode"].lastIndexOf("-")+1);
                        	dialog.find('#mergechildslot' + parseInt(i + 1) + '-' + waferCode).attr("checked",true);
                        }
                        if (json[i]['internalLotNumber'].indexOf("-H") != -1) {
                        	hposition =json[i]['internalLotNumber'];
                        	hposition = parseInt(hposition.substring(hposition.lastIndexOf("-H")+2));
                        }
                    }
            	});
                dialog.find('#mergeID').on('click', function () {
                    if (!Validator.Validate(dialog.find('form')[0], 3))return;
                    var i = 0;
                    var childsSum = 0;
                    var nodeState="";
                    var idsCount = "";
                    var checklotnum;
                    var motherqty = 0;
                    dialog.find('.ChildsINFO').find("tr").each(function() {
                    	childsSum ++;
                    	$(this).find("input[type='checkbox']").each(function() {
                    		if($(this).attr("id").startsWith("mergelot") && $(this).is(":checked")){
                    			i++;
                    			checklotnum = $(this).attr("id").substring($(this).attr("id").indexOf("mergelot")+8);
                            	if (dialog.find('#mergeqty'+ checklotnum).text() != ""){
                            		motherqty = motherqty + parseInt(dialog.find('#mergeqty'+ checklotnum).text());
                            	}
                    		}
                    	});
                    	if(nodeState ==""){
                    		nodeState = dialog.find('#mergecurrentState'+ checklotnum).text();
                    	}else{
                    		if (nodeState != dialog.find('#mergecurrentState'+ checklotnum).text()){
                    			dialog.message({
                                    type: 'warning',
                                    content: '请选择相同站点状态的子批合批！'
                                })
                                return false;
                    		}
                    	}
                    	
                    });
                    if (i < 2) {
                        dialog.message({
                            type: 'warning',
                            content: '子批数量至少为2！'
                        })
                        return false;
                    }
                    dialog.find('.MotherLOTINFO').html('');
                    var html = "<tr border='1px' cellspacing='0'  bordercolor=''#00CCCC'><th>批号</th><th>状态</th><th>数量</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th><th>13</th><th>14</th><th>15</th><th>16</th><th>17</th><th>18</th><th>19</th><th>20</th><th>21</th><th>22</th><th>23</th><th>24</th><th>25</th></tr>";;
                    dialog.find('.MotherLOTINFO').append(html);
                    var html  = "<tr style='background-color: #CFF;' border='1px' cellspacing='0'  bordercolor='#00CCCC' id='lot'><th id='motherinternalLotNumber'></th><th id='mothercurrentState'></th><th id='motherqty'></th>";
                        dialog.find('.MotherLOTINFO').append(html
                    		                              +"<th><input type='checkbox' id='mergemotherlot01'></th>"
                        		                          +"<th><input type='checkbox' id='mergemotherlot02'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot03'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot04'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot05'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot06'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot07'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot08'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot09'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot10'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot11'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot12'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot13'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot14'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot15'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot16'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot17'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot18'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot19'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot20'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot21'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot22'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot23'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot24'></th>" 
                        		                          +"<th><input type='checkbox' id='mergemotherlot25'></th></tr>");
                        dialog.find('#mothercurrentState').text(nodeState);
                        dialog.find('#motherqty').text(motherqty);
                        dialog.find('.ChildsINFO').find("tr").each(function() {
                        	$(this).find("input[type='checkbox']").each(function() {
                        		if($(this).attr("id").startsWith("mergelot") && $(this).is(":checked")){
                        			checklotnum = $(this).attr("id").substring($(this).attr("id").indexOf("mergelot")+8);
                        			for (var a = 0;a < cpLotDTOs[checklotnum-1]['cPWaferDTOs'].length;a++) {
                                    	var waferCode = cpLotDTOs[checklotnum-1]['cPWaferDTOs'][a]["internalWaferCode"].substring(cpLotDTOs[checklotnum-1]['cPWaferDTOs'][a]["internalWaferCode"].lastIndexOf("-")+1);
                                    	dialog.find('#mergemotherlot' + waferCode).attr("checked",true);
                                    	dialog.find('#mergechildslot' + waferCode).attr("disabled",true);
                                    }
                        			debugger;
                        			if (idsCount == ""){
                        				idsCount = cpLotDTOs[checklotnum-1]['id'] + ",";
                        			}else {
                        				idsCount = idsCount + cpLotDTOs[checklotnum-1]['id'] + ",";
                        			}
                        			if ((childsSum-1) == i && dialog.find('#motherinternalLotNumber').text() =="") {
                                        dialog.find('#motherinternalLotNumber').text(cpLotDTOs[checklotnum-1]['parentIntegrationIds']);
                                    } else if((childsSum-1) != i && dialog.find('#motherinternalLotNumber').text() =="" && hposition ==0){
                                       	dialog.find('#motherinternalLotNumber').text(cpLotDTOs[checklotnum-1]['parentIntegrationIds'] + '-H1');
                                    } else if((childsSum-1) != i && dialog.find('#motherinternalLotNumber').text() =="" && hposition !=0){
                                    	dialog.find('#motherinternalLotNumber').text(cpLotDTOs[checklotnum-1]['parentIntegrationIds'] + '-H' + (hposition+1));
                                    }
                        		}
                        	});
                        });
                        ids = idsCount;
                });
                dialog.modal({
                    keyboard: false,
                    backdrop: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    }
                });
            });
            dialog.find('#save').on('click', {grid: grid}, function (e) {
                if (!Validator.Validate(dialog.find('form')[0], 3))return;
                $.post('${pageContext.request.contextPath}/CPLot/mergeLot.koala', 
                   {"ids": ids.substring(0,ids.lastIndexOf(",")),
        		    "motherName":dialog.find('#motherinternalLotNumber').text()}).done(function (result) {
                    if (result.success) {
                        dialog.modal('hide');
                        e.data.grid.data('koala.grid').refresh();
                        e.data.grid.message({
                            type: 'success',
                            content: '合批成功'
                        });
                    } else {
                        if (result.success != undefined || result.success == false) {
                            dialog.find('.modal-content').message({
                                type: 'error',
                                content: result.errorMessage
                            });
                        }
                        else {
                            dialog.find('.modal-content').message({
                                type: 'error',
                                content: result.actionError
                            });
                        }
                    }
                });
            });
        },
		reworkApply: function(id, grid,lotid){
			var self = this;
			var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1010px"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">重工申请</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
			$.get('<%=path%>/CPReworkApply.jsp').done(function(html){
				dialog.find('.modal-body').html(html);
				self.initPage(dialog.find('form'));
				//重工单赋值************************************************************************************************************
				$.get('${pageContext.request.contextPath}/CPLot/get/' + id + '.koala').done(function (json) {
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
					dialog.find("#customerNumberID").text(json['customerCPLotDTO']['customerNumber']);
					dialog.find("#qtyID").text(json['cPWaferDTOs'].length);
				});
				var mydate=new Date();
				dialog.find("[name='reworkDate']").text(mydate.toLocaleDateString());
				$.get( '${pageContext.request.contextPath}/CPRework/createReworkNo/'+id+'.koala').done(function(json){
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
					postJson['reason']['gist']=dialog.find("input[name='Rework_gist']:checked").next().text();
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
					$.post('${pageContext.request.contextPath}/CPRework/add.koala?id='+id, a).done(function(result){
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
			var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:950px"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">重工下单</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
			$.get('<%=path%>/CPMiddleStorage-rework.jsp').done(function(html){
				dialog.find('.modal-body').html(html);
				self.initPage(dialog.find('form'));
				//重工单赋值************************************************************************************************************
				$.get( '${pageContext.request.contextPath}/CPLot/get/' + id + '.koala').done(function(json){
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
					$.each(json['cPWaferDTOs'],function(a){
						dialog.find(".checkItem"+this.customerWaferIndex).attr("checked","checked");
						dialog.find(".waferId"+this.customerWaferIndex).val(this.internalWaferCode);
                	})
					debugger;
				});
				$.ajax({
				    async: false,
				    url: '${pageContext.request.contextPath}/CPProcess/findCPProcessByCPLotId/'+id+'.koala',
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
				dialog.find("#InvertButton").bind("click",function(){
			    	$("[class^='checkItem']").prop("checked", function(i,val) {
			    		debugger;
			    		if($(this).prop("checked"))
			       		{
			       			$(".waferId"+$(this).attr("class").replace(/[^0-9]/ig,"")).val("");
			       		}
			    		return !val;
			    	});
			    })
			    dialog.find("[class^='checkItem']").bind("change",function(){
			    	debugger;
			    	if(!$(this).prop("checked"))
			   		{
			   			$(".waferId"+$(this).attr("class").replace(/[^0-9]/ig,"")).val("");
			   		}
			    })
				dialog.find('#save').on('click',{grid: grid}, function(e){
					if(!Validator.Validate(dialog.find('form')[0],3))return;
					var json={};
					var waferCode=[];
                    $("input[name^='internalWaferCode']").each(function(i){
                    	var a={};
                    	a['index']=this.getAttribute("name").replace(/[^0-9]/ig,"");
                    	a['waferCode']=this.value;
                    	waferCode.push(a)
                    });
                    json['waferCode']=waferCode;
                    json['base']=dialog.find('form').serializeArray().map(function(a){var b={};b[a.name]=a.value;return b});
                    debugger;
                    var data = {'data': JSON.stringify(json)};
					$.post('${pageContext.request.contextPath}/CPTransferStorage/reworkLot.koala?id='+id, data).done(function(result){
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
	    shipping: function(ids, grid){
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
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
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
                    <label class="control-label" style="width:100px;float:left;">内部批号:&nbsp;</label>
		            <div style="margin-left:15px;float:left;">
		            <input name="reelCode" class="form-control" type="text" style="width:180px;" id="internalLotNumberID"  />
		        </div>
<!-- 		        <label class="control-label" style="width:100px;float:left;">品质:&nbsp;</label>
	            <div style="margin-left:15px;float:left;">
		            <div class="btn-group select" id="qualityID"></div>
		            <input name="quality" type="hidden" id="qualityID_"/>
		        </div> -->
		        <label class="control-label" style="width:100px;float:left;">状态:&nbsp;</label>
	            <div style="margin-left:15px;float:left;">
		            <div class="btn-group select" id="statusID"></div>
		            <input name="status" type="hidden" id="statusID_"/>
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
