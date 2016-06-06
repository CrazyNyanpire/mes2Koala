<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <%@ page import="java.util.Date" %>
    <% String formId = "form_" + new Date().getTime();
        String gridId = "grid_" + new Date().getTime();
        String path = request.getContextPath() + request.getServletPath().substring(0, request.getServletPath().lastIndexOf("/") + 1);
    %>
    <script type="text/javascript">
        var grid;
        var form;
        var isModify = 0;
        var _dialog;
        var buttonName;
        $(function () {
            grid = $("#<%=gridId%>");
            form = $("#<%=formId%>");
            PageLoader = {
                //
                initSearchPanel: function () {
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
                    }).on('changeDate', function () {
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
                    }).on('changeDate', function (ev) {
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
                    }).on('changeDate', function () {
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
                    }).on('changeDate', function (ev) {
                        startTime.datetimepicker('setEndDate', endTimeVal.val());
                    }).datetimepicker('setDate', new Date()).trigger('changeDate');//加载日期选择器
                    startTime.datetimepicker('setDate', yesterday).trigger('changeDate');
                },
                initGridPanel: function () {
                    var self = this;
                    var width = 120;
                    return grid.grid({
                        identity: "id",
                        gridheight:1000,
                        buttons: [
                            {
                                content: '<button class="btn btn-danger" type="button">开HOLD</button>',
                                action: 'hold'
                            },
                            {
                                content: '<button class="btn btn-success" type="button">解Hold</button>',
                                action: 'unhold'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button">流程卡</button>',
                                action: 'runcard'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button">导出Excel</button>',
                                action: 'exportExcel'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button">跳站</button>',
                                action: 'jumpNode'
                            },
                            {
                                content: '<button class="btn btn-danger" id="updateftList" type="button">修改</button>',
                                action: 'updata'
                            },
                            {
                                content: '<button class="btn btn-danger" id="updateftList" type="button">良率放行</button>',
                                action: 'endFailTest'
                            },
                            {
                                content: '<button class="btn btn-success" type="button">预Hold</button>',
                                action: 'featuHold'
                            },
                            {
                                content: '<button class="btn btn-success" type="button">标签打印</button>',
                                action: 'labelPrint'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button">操作日志</button>',
                                action: 'optdetal'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button">设备嫁动</button>',
                                action: 'ems'
                            },
                            {
                                content: '<button class="btn btn-success" type="button">分批</button>',
                                action: 'split'
                            },
                            {
                                content: '<button class="btn btn-success" type="button">合批</button>',
                                action: 'merge'
                            },
                            {
                                content: '<button class="btn btn-success" type="button">数据补偿</button>',
                                action: 'dataCompensation'
                            },
                            {
                                content: '<button class="btn btn-success" type="button">删除</button>',
                                action: 'delete'
                            }
                        ],
                        url: "${pageContext.request.contextPath}/CPLot/pageJson.koala",
                        columns: [
                            {title: '状态', name: 'currentState', width: width},
                            {title: '客户批号', name: 'customerLotNumber', width: width,
                                render: function (rowdata, rowindex, value) {
                                    return rowdata['customerCPLotDTO'][rowindex];
                                }
                            },
                            {title: '内部批号', name: 'internalLotNumber', width: width},
                            {
                                title: 'PID',
                                name: 'internalProductNumber',
                                width: width,
                            },
                            {title: '数量', name: 'diskContent', width: width},
                            {title: '产品编号', name: 'customerNumber', width: width},
                            {title: '客户型号', name: 'shipmentProductNumber', width: width},
                            {title: '出货型号', name: 'shipmentProductNumber', width: width},
                            {title: '封装厂批号', name: 'packageNumber', width: width},
                            {title: '客户编号', name: 'customerNumber', width: width,
                                render: function (rowdata, rowindex, value){
                                    return rowdata['customerCPLotDTO'][rowindex];
                                }
                            },
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
                        'dataCompensation': function (event, data) {
                        	var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                })
                                return;
                            }
                            self.dataCompensation(indexs[0], $this);
                        },
                        'hold': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                })
                                return;
                            }
                            self.hold(indexs[0], $this);
                        },
                        'unhold': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                })
                                return;
                            }
                            if (data.item[0].state != "HOLD") {
                                $this.message({
                                    type: 'warning',
                                    content: '该批次状态不是Hold无法解Hold'
                                })
                                return;
                            }
                            self.unhold(indexs[0], $this);
                        },
                        'runcard': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                })
                                return;
                            }
                            self.runcard(indexs[0], $this);
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
                        },
                        'jumpNode': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                })
                                return;
                            }
                            self.jumpNode(indexs[0], $this);
                        },
                        'updata': function (event, data) {
                            isModify = 1;
                            var $ftDetail = $('#CPDetail');
                            $ftDetail.find('input').attr('disabled', false);
                            $ftDetail.find('textarea').attr('disabled', false);
                            $ftDetail.find('select').attr('disabled', false);
                            $('#updateftList').removeClass('btn-success').addClass('btn-default');
                            if (buttonName == 'Test') {
                                $ftDetail.find('#start').css('display', 'none');
                                $ftDetail.find('[name="save"]').removeClass('hidden');
                                $('#CPDetail').find('#end').addClass('hidden');
                                $('#CPDetail').find('[name="save"]').attr('disabled', false);
                            } else {
                                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").addClass('hidden');
                                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', false);
                            }
                        },
                        'endFailTest': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行良率放行'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行良率放行'
                                })
                                return;
                            }
                            self.endFailTest(indexs[0], $this);
                        },
                        'featuHold': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                })
                                return;
                            }
                            self.featuHold(indexs[0], $this);
                        },
                        'labelPrint': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                })
                                return;
                            }
                            self.labelPrint(indexs[0], $this);
                        },
                        'optdetal': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                })
                                return;
                            }
                            self.optdetal(indexs[0], $this);
                        },
                        'ems': function (event, data) {
                            window.open("http://192.168.1.59:8080");
                        },
                        'split': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                })
                                return;
                            }
                            self.split(indexs[0], $this);
                        },
                        'merge': function (event, data) {
                        	var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行合批'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行合批'
                                })
                                return;
                            }
                            self.merge(indexs[0], $this);
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
                runcard: function (id,grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">RunCard</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/FTRunCard.jsp').done(function (html) {
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        }).find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        $.post('${pageContext.request.contextPath}/FTLot/add.koala', dialog.find('form').serialize()).done(function (result) {
                            if (result.success) {
                                dialog.modal('hide');
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                    type: 'success',
                                    content: '保存成功'
                                });
                            } else {
                                dialog.find('.modal-content').message({
                                    type: 'error',
                                    content: result.errorMessage
                                });
                            }
                        });
                    });
                },
                hold: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">开Hold</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/CPHoldQDN.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        dialog.find("input[name='To']").bind("click", function () {
                            if ($(this).attr("checked") == "checked") {
                            	$.ajax({
                                    async: false,
                                    url: '${pageContext.request.contextPath}/HoldMail/holdUsers.koala',
                                    data: {"dept": $(this).next().text()},
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
                        $.get('${pageContext.request.contextPath}/CPLot/get/' + id + '.koala').done(function (json) {
                            json = json.data;
                            debugger;
                            var elm;
                            for (var index in json) {
                                elm = dialog.find('#' + index + 'ID');
                                if (elm.hasClass('select')) {
                                    elm.setValue(json[index]);
                                } else {
                                    elm.val(json[index]);
                                    elm.text(json[index]);
                                }
                                if (typeof(json[index]) == "object") {
                                    for (var index1 in json[index]) {
                                        elm = dialog.find('#' + index1 + 'ID');
                                        if (elm.hasClass('select')) {
                                            elm.setValue(json[index][index1]);
                                        } else {
                                            elm.val(json[index][index1]);
                                            elm.text(json[index][index1]);
                                        }
                                        if (typeof(json[index][index1]) == "object") {
                                            for (var index2 in json[index][index1]) {
                                                elm = dialog.find('#' + index2 + 'ID');
                                                if (elm.hasClass('select')) {
                                                    elm.setValue(json[index][index1][index2]);
                                                } else {
                                                    elm.val(json[index][index1][index2]);
                                                    elm.text(json[index][index1][index2]);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            $.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function (json) {
                                dialog.find("#QDN_initiator").text(json['data']['userAccount'] + "|" + json['data']['name']);
                                dialog.find("[name='workPerson']").val(json['data']['userAccount'] + "|" + json['data']['name']);
                            });//获取申请人
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
                            var qdnInfo = "";
                            var qdnChk = "";
                            dialog.find("tr").each(function() {
                    		  $(this).find("th").each(function() {
                    		  	   if ($(this).children().length > 0) {
                    				  if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "text") {
                    					  qdnInfo += $(this).children().val() + ",";
                    				  } else if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox") {
                    					  if ($(this).children().is(':checked')) {
                                  			qdnChk += "1" + ",";
                                  		  } else {
                                  			qdnChk += "0" + ",";
                                  		  }
                    				  }
                    			   }
                    		   })
                            })
                            qdnInfo = qdnInfo.substring(0, qdnInfo.length - 1);
                            qdnChk = qdnChk.substring(0, qdnChk.length - 1);
                            
                            var postString = dialog.find('form').serialize() + "&qdnChk=" + qdnChk + "&qdnInfo=" + qdnInfo + "&toDepartment=" + $("input[name='To']:checked").next().text() + "&flowNow=" + $("[name='flowNow']").text();
                            debugger;
                            $.post('${pageContext.request.contextPath}/CPLot/hold.koala?id=' + id, postString).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: 'Hold成功'
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
                },
                unhold: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">解Hold</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/CPHoldUnlock.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/CPLot/get/' + id + '.koala').done(function (json) {
                            json = json.data;
                            var elm;
                            for (var index in json) {
                                elm = dialog.find('#' + index + 'ID');
                                if (elm.hasClass('select')) {
                                    elm.setValue(json[index]);
                                } else {
                                    elm.val(json[index]);
                                    elm.text(json[index]);
                                }
                                if (typeof(json[index]) == "object") {
                                    for (var index1 in json[index]) {
                                        elm = dialog.find('#' + index1 + 'ID');
                                        if (elm.hasClass('select')) {
                                            elm.setValue(json[index][index1]);
                                        } else {
                                            elm.val(json[index][index1]);
                                            elm.text(json[index][index1]);
                                        }
                                        if (typeof(json[index][index1]) == "object") {
                                            for (var index2 in json[index][index1]) {
                                                elm = dialog.find('#' + index2 + 'ID');
                                                if (elm.hasClass('select')) {
                                                    elm.setValue(json[index][index1][index2]);
                                                } else {
                                                    elm.val(json[index][index1][index2]);
                                                    elm.text(json[index][index1][index2]);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        });
                        $.get('${pageContext.request.contextPath}/CPQDN/getQDNChk/' + id + '.koala').done(function (json) {
                        	if (json != null){
                        	var qdnChk = json.split(',');
                        	dialog.find("tr").each(function() {
                        	   if ($(this).attr("id") == "select"){
                        		   $(this).find("th").each(function(i) {
                        			 if ($(this).children().length > 0) {
                        			   if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox"){
                                       	  if ( qdnChk[i] == "1"){
                                       		 $(this).children()[0].disabled = false;
                                       	  }else {
                                             $(this).children()[0].disabled = true;
                                          }
                                       } 
                        			 }
                        		   });
                        	   } 
                        	   if ($(this).attr("id") == "rework"){
                        		   $(this).find("th").each(function(j) {
                        			 if ($(this).children().length > 0) {
                        			   if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox"){
                                        	  if ( qdnChk[j] == "1"){
                                        		 $(this).children()[0].disabled = false;
                                        	  }else {
                                                 $(this).children()[0].disabled = true;
                                              }
                                        }
                        			  }
                        		   });
                        	   }
                        	   if ($(this).attr("id") == "img"){
                        		   $(this).find("th").each(function(b) {
                        			 if ($(this).children().length > 0) {
                        			   if ($(this).children()[0].tagName == "DIV"){
                                        	  if ( qdnChk[b] == "1"){
                                        		 $(this).children().css("background-color","red");
                                        	  }
                                        }
                        			  }
                        		   });
                        	   }
                        	   if ($(this).attr("id") == "onoffhold"){
                        		   $(this).find("th").each(function(a) {
                        			   if ($(this).text() != "开解Hold"){
                                        	  if ( qdnChk[a] == "1"){
                                        		 $(this).text("解");
                                        	  }
                                        }
                        		   });
                        	   }
                           });
                          }
                        });
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        });
                        dialog.find("#toggleSelectBtnBtnID").bind("click",function(){
        			    	$("[type^='checkbox']").prop("checked", function(i,val) {
        			    		debugger;
        			    		if ( !$(this).is(':disabled')) {
        			    			if($(this).prop("checked"))
            			       		{
        			    				$(this).val("");
            			       		}
            			    		return !val;
        			    		}
        			    	});
        			    })
                        dialog.find('#save').on('click', {grid: grid}, function (e) {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            var selectInfo = "";
                            var reworkInfo = "";
                            debugger;
                            dialog.find("tr").each(function() {
                               if ($(this).attr("id") == "select"){
                    		      $(this).find("th").each(function() {
                    		       if ($(this).children().length > 0) {
                    				 if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox") {
                    					  if ($(this).children().is(':checked')) {
                    			            selectInfo += "1" + ",";
                                  		  } else {
                                  			selectInfo += "0" + ",";
                                  		  }
                    				  }
                    		        }
                    		     })
                               }
                               if ($(this).attr("id") == "rework"){
                     		      $(this).find("th").each(function() {
                     		    	 if ($(this).children().length > 0) {
                     				    if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox") {
                     					   if ($(this).children().is(':checked')) {
                                             reworkInfo += "1" + ",";
                                   		   } else {
                                   			 reworkInfo += "0" + ",";
                                   		   }
                     				    }
                     		    	 }
                     		      })
                               }
                            })
                            selectInfo = selectInfo.substring(0, selectInfo.length - 1);
                            reworkInfo = reworkInfo.substring(0, reworkInfo.length - 1);
                            var postString = dialog.find('form').serialize() + "&selectInfo=" + selectInfo + "&reworkInfo=" + reworkInfo; 
                            $.post('${pageContext.request.contextPath}/CPLot/unhold.koala?id=' + id, postString).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '解Hold成功'
                                    });
                                } else {
                                    //debugger;
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
                },
                featuHold: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">开Hold</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/CPfutureHold.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        dialog.find("input[name='To']").bind("click", function () {
                            if ($(this).attr("checked") == "checked") {
                            	$.ajax({
                                    async: false,
                                    url: '${pageContext.request.contextPath}/HoldMail/holdUsers.koala',
                                    data: {"dept": $(this).next().text()},
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
                        $.get('${pageContext.request.contextPath}/CPLot/get/' + id + '.koala').done(function (json) {
                            json = json.data;
                            var elm;
                            for (var index in json) {
                                elm = dialog.find('#' + index + 'ID');
                                if (elm.hasClass('select')) {
                                    elm.setValue(json[index]);
                                } else {
                                    elm.val(json[index]);
                                    elm.text(json[index]);
                                }
                                if (typeof(json[index]) == "object") {
                                    for (var index1 in json[index]) {
                                        elm = dialog.find('#' + index1 + 'ID');
                                        if (elm.hasClass('select')) {
                                            elm.setValue(json[index][index1]);
                                        } else {
                                            elm.val(json[index][index1]);
                                            elm.text(json[index][index1]);
                                        }
                                        if (typeof(json[index][index1]) == "object") {
                                            for (var index2 in json[index][index1]) {
                                                elm = dialog.find('#' + index2 + 'ID');
                                                if (elm.hasClass('select')) {
                                                    elm.setValue(json[index][index1][index2]);
                                                } else {
                                                    elm.val(json[index][index1][index2]);
                                                    elm.text(json[index][index1][index2]);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            $.get('${pageContext.request.contextPath}/CPProcess/findCPProcessByCPLotId/' + id + '.koala').done(function (json) {
                                debugger;
                                json = json.data;
                                for (var a in json['cpNodeDTOs']) {
                                    if (json['cpNodeDTOs'][a]['state'] == "UNREACHED") {
                                        for (var b = a; b < json['cpNodeDTOs'].length; b++) {
                                            dialog.find(".holdStation").append("<option value='" + json['cpNodeDTOs'][b]['name'] + "'>" + json['cpNodeDTOs'][b]['name'] + "</option>");
                                        }
                                        break;
                                    }
                                }
                            });//获取申请人
                            $.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function (json) {
                                dialog.find("#QDN_initiator").text(json['data']['userAccount'] + "|" + json['data']['name']);
                                dialog.find("[name='workPerson']").val(json['data']['userAccount'] + "|" + json['data']['name']);
                            });
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
                            var qdnInfo = "";
                            var qdnChk = "";
                            dialog.find("tr").each(function() {
                    		  $(this).find("th").each(function() {
                    		  	   if ($(this).children().length > 0) {
                    				  if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "text") {
                    					  qdnInfo += $(this).children().val() + ",";
                    				  } else if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "checkbox") {
                    					  if ($(this).children().is(':checked')) {
                                  			qdnChk += "1" + ",";
                                  		  } else {
                                  			qdnChk += "0" + ",";
                                  		  }
                    				  }
                    			   }
                    		   })
                            })
                            qdnInfo = qdnInfo.substring(0, qdnInfo.length - 1);
                            qdnChk = qdnChk.substring(0, qdnChk.length - 1);
                            var postString = dialog.find('form').serialize() + "&qdnChk=" + qdnChk + "&qdnInfo=" + qdnInfo + "&toDepartment=" + $("input[name='To']:checked").next().text();
                            debugger;
                            $.post('${pageContext.request.contextPath}/CPLot/futureHold.koala?id=' + id, postString).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '预Hold成功'
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
                },
                endFailTest: function (id, grid) {
                    $.get('${pageContext.request.contextPath}/CPProcess/findCPProcessByCPLotId/' + id + '.koala').done(function (data) {
                        var pid = data.data.id;
                        var data = [{name: 'processId', value: pid}];
                        $.post('${pageContext.request.contextPath}/CPLot/endFailTestNode.koala', data).done(function (result) {
                            if (result.success) {
                                grid.data('koala.grid').refresh();
                                grid.message({
                                    type: 'success',
                                    content: '良率放行成功'
                                });
                            } else {
                                debugger;
                                grid.message({
                                    type: 'error',
                                    content: result.errorMessage
                                });
                            }
                        });
                    });
                },
                jumpNode: function (id, grid) {
                    var self = this;
                    var CPProcessId;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">跳站</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/CPJumpNode.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/CPProcess/findCPProcessByCPLotId/' + id + '.koala').done(function (json) {
                            debugger;
                            json = json.data;
                            CPProcessId = json['id'];
                            var contents = [{title:'请选择', value: ''}];
                            for (var a in json['cpNodeDTOs']) {
                                contents.push({
                                	title : json['cpNodeDTOs'][a]['name'],
                                	value : json['cpNodeDTOs'][a]['name']
                                });
                            }
                            dialog.find('#targetNodeID').select({
                                title: '请选择',
                                contents: contents
                            });
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
                            debugger;
                            $.post('${pageContext.request.contextPath}/CPLot/jumpCPNode.koala?processId=' + CPProcessId, dialog.find('form').serialize()).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '跳站成功'
                                    });
                                } else {
                                    if (result.success != undefined || !result.success) {
                                        dialog.find('.modal-content').message({
                                            type: 'error',
                                            content: result.errorMessage
                                        });
                                    }
                                    else {
                                        dialog.find('.modal-content').message({
                                            type: 'error',
                                            content: result.errorMessage
                                        });
                                    }
                                }
                            });
                        });
                    });
                },
                labelPrint: function (id, grid) {
                    var self = this;
                    var labelName = "";
                    var labelInfo = "";
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:800px"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">标签打印</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">打印</button></div></div></div></div>');
                    $.get('<%=path%>/CPLabelPrint.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/CPLot/getLabelInfo/' + id + '.koala').done(function (json) {
                            debugger;
                        	json = json.data;
                            labelName = json['labelName'];
                            var elm;
                            for (var index in json) {	
                                elm = dialog.find('#' + index + 'ID');
                                if (elm.hasClass('select')) {
                                    elm.setValue(json[index]);
                                } else {
                                    elm.val(json[index]);
                                }
                            }
                            if(json['cPWaferDTOs'].length==25)
                           	{
                            	$(".checkItemAll").attr("checked","checked");
                           	}
                            else{
                            	$.each(json['cPWaferDTOs'],function(a){
    		                		$(".checkItem"+this.customerWaferIndex).attr("checked","checked");
    		                	})
                            }
                            
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
                            labelName=$("#labelNameID").val();
                            labelName="CPShipping.btw";
                            
                            if ( labelName == "" || labelName == undefined) {
                                dialog.message({
                                    type: 'warning',
                                    content: '未找到对应标签请检查'
                                });
                                return false;
                            }
                            var path = "C:/WMS/PRINT/" + labelName;
                            labelInfo=dialog.find('form').serialize();
                            var waferinfo="";
                            $("[class^='checkItem']").each(function(a){
                            	if($(this).attr("checked")=="checked"){
                            		waferinfo+="&"+$(this).attr("index")+"=■";
                            	}
                            	else{
                            		waferinfo+="&"+$(this).attr("index")+"=□";
                            	}
                            })
                            labelInfo+=waferinfo;
                            labelInfo=decodeURI(labelInfo)
                            onPrint(path,labelInfo);
                        });
                    });
                },
                optdetal: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">操作详情</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
                    var html = "";
                    dialog.find('.modal-body').html(html);
                    self.initPage(dialog.find('form'));
                    $.get('${pageContext.request.contextPath}/CPLotOptionLog/pageJson.koala?lotId=' + id + '&page=0&pagesize=100').done(function (json) {
                    	json = json.data;
                        var elm = "<table class='table'><tr>";
                        elm += "<th>节点名</th><th>开始时间</th><th>开始操作员</th><th>结束时间</th><th>结束操作员</th><th>备注</th>";
                        elm = elm + "</tr>";
                        $.each(json,function(a){
                            elm = elm + "<tr>";
                            var remark = !!json[a]['remark']==0?"":json[a]['remark'];
                            elm += "<td>" + json[a]['optType'] + "</td><td>" + json[a]['createTimestamp'] + "</td><td>" + json[a]['createEmployNo'] + "</td><td>" + json[a]['lastModifyTimestamp'] + "</td><td>" + json[a]['lastModifyEmployNo'] + "</td><td>" + remark + "</td>";
                            elm = elm + "</tr>";
                        });
                        elm = elm + "</table>";
                        dialog.find(".modal-body").append(elm);
                    });
                    dialog.modal({
                        keyboard: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        }
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
                remove: function (ids, grid) {
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/CustomerCPLot/deleteOrder.koala', data).done(function (result) {
                        if (result.success) {
                            grid.data('koala.grid').refresh();
                            grid.message({
                                type: 'success',
                                content: '删除成功'
                            });
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.errorMessage
                            });
                        }
                    });
                },
                initPage: function (form) {
                    form.find('.form_datetime').datetimepicker({
                        language: 'zh-CN',
                        format: "yyyy-mm-dd",
                        autoclose: true,
                        todayBtn: true,
                        minView: 2,
                        pickerPosition: 'bottom-left'
                    }).datetimepicker('setDate', new Date());//加载日期选择器
                    form.find('.select').each(function () {
                        var select = $(this);
                        var idAttr = select.attr('id');
                        select.select({
                            title: '请选择',
                            contents: selectItems[idAttr]
                        }).on('change', function () {
                            form.find('#' + idAttr + '_').val($(this).getValue());
                        });
                    });
                },
                dataCompensation: function (id, grid) {
                	var self = this;
                	var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">数据补偿</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/CPDataCompensation.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.post('${pageContext.request.contextPath}/CPLot/dataCompensationChk/' + id + '.koala').done(function (result) {
                        	debugger;
                        	if (result.success) {
                        		json = result.data;
                            	dialog.find('#internalLotNumberID').text(json['internalLotNumber']);
                                dialog.find('#shipmentProductNumberID').text(json['shipmentProductNumber']);
                                dialog.find('#customerNumberID').text(json['customerCPLotDTO']['customerNumber']);
                            } else {
                            	dialog.find('#productRequireBtnID').attr("disabled",true);
                            	dialog.find('#save').attr("disabled",true);
                            	dialog.find('.modal-content').message({
                                    type: 'error',
                                    content: result.errorMessage
                                });
                            }
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
                            $.post('${pageContext.request.contextPath}/CPLot/dataCompensation.koala?fileName=' + dialog.find("#productRequireID").val()).done(function (result) {
	                            if (result.success) {
	                                dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '数据补偿成功'
                                    });
                                } else {
                                    if (result.success != undefined || !result.success) {
                                        dialog.find('.modal-content').message({
                                            type: 'error',
                                            content: result.errorMessage
                                        });
                                    }
                                    else {
                                        dialog.find('.modal-content').message({
                                            type: 'error',
                                            content: result.errorMessage
                                        });
                                    }
                                }
                            });
                        });
                    });
                },
                exportExcel: function (ids, grid) {
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/CPLot/exportExcel.koala', data).done(function (result) {
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
                            }).find('#downloadID').html("导出成功！" + "<a style='margin-left:20px;'   href='" + result.data + "'>点击下载</a>");
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
            var height=$(".mainContent")[0].offsetHeight-7
        	$(".sidebar").children().css("height",height);
            form.find('#search').on('click', function () {
                if (!Validator.Validate(document.getElementById("<%=formId%>"), 3))return;
                var params = {};
                form.find('input').each(function () {
                    var $this = $(this);
                    var name = $this.attr('name');
                    if (name) {
                        params[name] = $this.val();
                    }
                });
                grid.getGrid().search(params);
            });
            /* grid对应行生成动态按钮在页面的左侧显示，以及按钮单击的具体内容在页面的右下方显示*/
            grid.find('.grid-table-body').find('table').on('click', function (e) { //点击页面grid的某一行，这里问题是选择器只能选择到table,并且点击复选框时无反应
                $("#DynamicButton").html(''); //每次点击都要将动态生成按钮内容清空，否则，多次点击会出现累加
                e.stopPropagation();//这个e就是事件对象
                var checkedId = grid.getGrid().selectedRowsIndex();
                if (checkedId.length > 1) {
                    grid.message({
                        type: 'warning',
                        content: '只能选择一条记录进行关联'
                    });
                    return;
                }
                getData(checkedId[0]);
            });
        });

        var openDetailsPage = function (id) {
            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
            $.get('<%=path%>/FTLot-view.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);
                $.get('${pageContext.request.contextPath}/FTLot/get/' + id + '.koala').done(function (json) {
                    json = json.data;
                    var elm;
                    for (var index in json) {
                        if (json[index] + "" == "false") {
                            dialog.find('#' + index + 'ID').html("<span style='color:#d2322d' class='glyphicon glyphicon-remove'></span>");
                        } else if (json[index] + "" == "true") {
                            dialog.find('#' + index + 'ID').html("<span style='color:#47a447' class='glyphicon glyphicon-ok'></span>");
                        } else {
                            dialog.find('#' + index + 'ID').html(json[index] + "");
                        }
                    }
                });
                dialog.modal({
                    keyboard: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    }
                });
            });
        }
        var getData = function (checkedId) {
            if (checkedId == undefined)
                return;
            $.get('${pageContext.request.contextPath}/CPProcess/findCPProcessByCPLotId/' + checkedId + '.koala').done(function (data) {
                data = data.data; 
                //var data ={'cpNodeDTOs':[{'state':1,"name":'IQC'},{'state':0,"name":'CP1'},{'state':0,"name":'CP2'}],'id':60};
                //debugger;
                $.each(data.cpNodeDTOs, function (i) {
                    var html = "";
                    var tml = $('<li style="margin-left:48%;font-size:200%;">&darr;</li>');
                    switch (this.cpState) {
                        case 0:
                            html = $('<li><button type="button" style="margin:1% 25%;width:50%;" class="btn btn-default" disabled>' + this.name + '</button></li>');
                            break;
                        case 1:
                        case 2:
                            html = $('<li><button type="button" style="margin:1% 25%;width:50%;" class="btn btn-success">' + this.name + '</button></li>');
                            break;
                        case 3:
                            html = $('<li><button type="button" style="margin:1% 25%;width:50%;" class="btn btn-primary">' + this.name + '</button></li>');
                            break;
                    }
                    $("#DynamicButton").append(html);
                    if (i < data.cpNodeDTOs.length - 1) {
                        $("#DynamicButton").append(tml);
                    }
                })
                $("#DynamicButton").find('button').on('click', function () {
                    $('#CPDetail').html('');
                    var nodeName = this.innerHTML;
                    onClickedDispatcher(checkedId, data, nodeName);
                });
             }); 
        }
        // 当点击、进站、保存时刷新
        var refreshDetail = function (checkedId, nodeName) {
            $.get('${pageContext.request.contextPath}/CPProcess/findCPProcessByCPLotId/' + checkedId + '.koala').done(function (data) {
                if (!data.success) {
                    return;
                }
                data = data.data;
                onClickedDispatcher(checkedId, data, nodeName);
            });
        }
        // 当出站后需要刷新
        var refreshButtonList = function (checkedId) {
            $('#DynamicButton').html('');
            $.get('${pageContext.request.contextPath}/CPProcess/findCPProcessByCPLotId/' + checkedId + '.koala').done(function (data) {
                if (!data.success) {
                    return;
                }
                data = data.data;
                $.each(data.cpNodeDTOs, function (i) {
                    var html = "";
                    var tml = $('<li style="margin-left:48%;font-size:200%;">&darr;</li>');
                    switch (this.cpState) {
                        case 0:
                            html = $('<li><button type="button" style="margin:1% 25%;width:50%;" class="btn btn-default" disabled>' + this.name + '</button></li>');
                            break;
                        case 1:
                        case 2:
                            html = $('<li><button type="button" style="margin:1% 25%;width:50%;" class="btn btn-success">' + this.name + '</button></li>');
                            break;
                        case 3:
                            html = $('<li><button type="button" style="margin:1% 25%;width:50%;" class="btn btn-primary">' + this.name + '</button></li>');
                            break;
                    }
                    $("#DynamicButton").append(html);
                    if (i < data.cpNodeDTOs.length - 1) {
                        $("#DynamicButton").append(tml);
                    }
                });
                $("#DynamicButton").find('button').on('click', function () {
                    $('#CPDetail').html('');

                    var nodeName = this.innerHTML;
                    refreshDetail(checkedId, nodeName);
                });
            });
        };
     	// 当出站后需要刷新
        var refreshDetailAndButtonList = function (checkedId) {
            $("#DynamicButton").html('');
            refreshButtonList(checkedId);
            $.get('${pageContext.request.contextPath}/CPProcess/findCPProcessByCPLotId/' + checkedId + '.koala').done(function (data) {
                if (!data.success) {
                    return;
                }
                data = data.data;
                $.each(data.cpNodeDTOs, function (i) {
                    switch (this.cpState) {
                        case 0:
                            break;
                        case 1:
                        case 2:
                            onClickedDispatcher(checkedId, data, this.name);
                            break;
                        case 3:
                            break;
                    }

                });
            });
        };
        
        var getDataafterClick = function (checkedId, nodeName) {
            $.get('${pageContext.request.contextPath}/CPProcess/findCPProcessByCPLotId/' + checkedId + '.koala').done(function (data) {
                if (!data.success) {
                    return;
                }
                data = data.data;
                $.each(data.cpNodeDTOs, function (i) {
                    var html = "";
                    var tml = $('<li style="margin-left:48%;font-size:200%;">&darr;</li>');
                    switch (this.cpState) {
                        case 0:
                            html = $('<li><button type="button" style="margin:1% 25%;width:50%;" class="btn btn-default" disabled>' + this.name + '</button></li>');
                            break;
                        case 1:
                        case 2:
                            html = $('<li><button type="button" style="margin:1% 25%;width:50%;" class="btn btn-success">' + this.name + '</button></li>');
                            onClickedDispatcher(checkedId, data, this.name);
                            break;
                        case 3:
                            html = $('<li><button type="button" style="margin:1% 25%;width:50%;" class="btn btn-primary">' + this.name + '</button></li>');
                            break;
                    }
                    $("#DynamicButton").append(html);
                    if (i < data.cpNodeDTOs.length - 1) {
                        $("#DynamicButton").append(tml);
                    }
                });
                $("#DynamicButton").find('button').on('click', function () {
                    $('#CPDetail').html('');
                    $('#DynamicButton').html('');
                    nodeName = this.innerHTML;
                    getDataafterClick(checkedId, nodeName);
                });
                onClickedDispatcher(checkedId, data, nodeName);
            });
        };


        var startNode = function (checkedId, FTProcessDTO, nodeName) {
             $.post('${pageContext.request.contextPath}/CPLot/startCPNode/' + FTProcessDTO.id + '.koala').done(function (result) {
                if (result.success) { 
                    $("#DynamicButton").html('');
                    //getDataafterClick(checkedId, nodeName);
                    refreshDetailAndButtonList(checkedId);
                    //grid.getGrid().refresh();
                    grid.message({
                        type: 'success',
                        content: '进站成功'
                    })
                    //CPtablebind($("#CPDetail table"));
                 } else {
                	 grid.message({
                        type: 'error',
                        content: '进站失败' + result.errorMessage
                    })
                }
            }) 
        }
        // 保存
        var saveNode = function (checkedId, json, nodeName) {
            debugger;
            $.post('${pageContext.request.contextPath}/CPLot/saveCPNode.koala', $.param(json) + '&' + $('#CPDetail').find('form').serialize())
                    .done(function (result) {
                        if (result.success) {
                            $("#DynamicButton").html('');
                            getDataafterClick(checkedId, nodeName);
                            isModify = 0;
                            $('#updateftList').addClass('btn-success').removeClass('btn-default');
                            grid.getGrid().refresh();
                            grid.message({
                                type: 'success',
                                content: '保存成功'
                            })
                        } else {
                            grid.gridmessage({
                                type: 'error',
                                content: result.errorMessage
                            });
                        }
                    })
        }

        var saveNodeThenEndNodeIncoming = function (checkedId, json, nodeName) {
        	debugger;
            var data = {'data': JSON.stringify(json)};
            $.post('${pageContext.request.contextPath}/CPLot/endCPNodeIncoming/' + json.processId + '.koala',data).done(function (result) {
                if (result.success) {
                    $("#DynamicButton").html('');
                    isModify = 0;
                    $('#updateftList').addClass('btn-success').removeClass('btn-default');
                    grid.getGrid().refresh();
                    //getDataafterClick(checkedId, nodeName);
                    refreshDetailAndButtonList(checkedId);
                    grid.message({
                        type: 'success',
                        content: '出站成功'
                    })
                } else {
                	grid.message({
                        type: 'error',
                        content: "出站失败" + result.errorMessage
                    });
                }
            });
       	}

        // 保存并出站
        var saveNodeThenEndNode = function (checkedId, json, nodeName) {
             $.post('${pageContext.request.contextPath}/CPLot/endCPNode/' + json.processId + '.koala').done(function (result) {
                 if (result.success) {
                     $("#DynamicButton").html('');
                     isModify = 0;
                     $('#updateftList').addClass('btn-success').removeClass('btn-default');
                     grid.getGrid().refresh();
                     //getDataafterClick(checkedId, nodeName);
                     refreshDetailAndButtonList(checkedId);
                     grid.message({
                         type: 'success',
                         content: '出站成功'
                     })
                 } else {
                	 grid.message({
                         type: 'error',
                         content: "出站失败" + result.errorMessage
                     });
                 }
             });
        };

		
        var CPtablebind=function(table){
        	debugger;
        	table.find(".optbutton").unbind();
    		table.find(".optbutton").bind("click",function(e){
    			var thisbutton=$(this);
    			var id=$(this).parent().parent().find("span").text();
    			var opt = function () {
    				$.post('${pageContext.request.contextPath}/CPWafer/changeStatusPassed.koala?ids=' + id).done(function (result) {
    						if(result.success)
    						{
	    						thisbutton.text("已通过"+thisbutton.text().replace(/[\u4E00-\u9FA5]/g,""));
	    						thisbutton.css("color","#888");
	    						thisbutton.unbind();
    						}
    				})
                };
                thisbutton.confirm({
                    content: '确定要进行操作?',
                    callBack: opt
                });
    		})
    		table.find("#allopt").unbind();
    		table.find("#allopt").bind("click",function(e){
    			var ids=$.map(table.find("span"),function(a){ return a.textContent}).join(",");
    			var opt = function () {
                 				$.post('${pageContext.request.contextPath}/CPWafer/changeStatusPassed.koala?ids='+ids).done(function (result) {
    						if(result.success)
    						{ 
	    						$.each(table.find(".optbutton"),function(a){
	    							$(this).text("已通过"+$(this).text().replace(/[\u4E00-\u9FA5]/g,""));
	    							$(this).css("color","#888");
	    							$(this).unbind();
	    						});
                 			}
    				}); 
                };
                $(this).confirm({
                    content: '确定要进行操作?',
                    callBack: opt
                });
    		})
    	};
        
        var CPtableqcbind=function(table,data){
        	table.find("#allopt").text("抽检");
        	table.find("#allopt").unbind();
    		table.find("#allopt").bind("click",function(e){
    			var ids=$.map(table.find("span"),function(a){ return a.textContent}).join(",");
                var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                        + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                        + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                        + '<h4 class="modal-title">QC抽检</h4></div><div class="modal-body">'
                        + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                        + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                        + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                        + '</div></div>');
                $.get('<%=path%>/CP_IQCWafer.jsp').done(function (html) {
                	dialog.modal({
                        keyboard: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        }
                    }).find('.modal-body').html(html);
                	$.each(data,function(a){
                		dialog.find(".checkItem"+this.customerWaferIndex).attr("checked","checked");
                		dialog.find(".checkItem"+this.customerWaferIndex).attr("waferId",this.id);
                		//dialog.find(".waferId"+this.customerWaferIndex).val(this.internalWaferCode);
                	})
                	dialog.find("[class^='checkItem']").prop("checked", function(i,val) {
                    		if(dialog.find(this).prop("checked"))
                       		{
                       			//$(".waferId"+$(this).attr("class").replace(/[^0-9]/ig,"")).val("");
                       			return !val;
                       		}
                    		else{
                    			dialog.find(this).attr("disabled","disabled");
                    		}
                    		
                    });
                	dialog.find("#InvertButton").bind("click",function(){
                    	$("[class^='checkItem']").prop("checked", function(i,val) {
                    		if($(this).prop("checked"))
                       		{
                       			$(".waferId"+$(this).attr("class").replace(/[^0-9]/ig,"")).val("");
                       		}
                    		if($(this).attr("disabled")!="disabled")
                   			{
                   				return !val;
                   			}
                    	});
                    })
                });
                dialog.find('#save').on('click', function (e) {
                    var ids=$.makeArray($("[class^='checkItem']:checked").map(function(a){return $(this).attr("waferId")})).join(",");
                    if (!Validator.Validate(dialog.find('form')[0], 3))return;
                    $.get('${pageContext.request.contextPath}/CPWafer/saveCheck.koala?ids=' + ids).done(function (result) {
                        if (result.success) {
                            dialog.modal('hide');
                            $(grid).message({
                                type: 'success',
                                content: '保存成功'
                            });
                            renderRawData();
                        } else {
                            dialog.find('.modal-content').message({
                                type: 'error',
                                content: result.actionError
                            });
                        }
                    });
                });
    		});
    	}
    	
        var onIQCNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            var buttonName = ftNodeDTO.name;
            buttonName="IQC";
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/CP_' + buttonName + '.jsp').done(function (html) {
                $('#CPDetail').html(html);
              	//IQC界面根据Wafer片生成对应操作表格
                $.get('${pageContext.request.contextPath}/CPWafer/getCPWaferInfo.koala?cpLotId='+checkedId+'&nodeId='+ftNodeDTO.id).done(function (data) {
                	data=data['data'];
                	$("#CPDetail table tbody").empty();
                	//data=[{'id':'1','aceWaferid':'S02481-01','opt':'IQC'},{'id':'4','aceWaferid':'S02481-01','opt':'IQC'},{'id':'2','aceWaferid':'S02481-01','opt':'IQC'},{'id':'3','aceWaferid':'S02481-01','opt':'IQC'}]
                	$.each(data,function(a){
                		var optitem="";
                		if(data[a].state=="0")
               			{
                			optitem='<a class="optbutton">'+ftNodeDTO.name+'</a>';
               			}
                		else{
                			optitem='<a style="color:#888">已通过'+ftNodeDTO.name+'</a>';
                		}
                		var color="#999";
                		if(data[a].isCheck=="1")
               			{
                			color="#f6ad49";
               			}
                		$("#CPDetail table tbody").append('<tr><td><div style="background-color:'+color+';height: 15px;width: 15px;border-radius: 50%;"></div></td>'
                        		+'<td><span style="display:none;">'+data[a].id+'</span>'+parseInt(a+1)+'</td><td>'+data[a].internalWaferCode+'</td><td>'+optitem+'</td><td><a href="'+data[a].map+'">查看</a></td>'
                        		+'<td><a>详情</a></td><td>'+data[a].internalWaferCode+'</td><td>'+data[a].pass+'</td><td>'+data[a].fail+'</td><td>'+data[a].internalOffset+'</td><td>'+data[a].customerOffset+'</td></tr>');
                	});
                	if (ftNodeDTO.cpState == 2) {
                        CPtableqcbind($("#CPDetail table"),data);
                    }
                }); 
                var elm;
                for (var index in ftNodeDTO) {
                    if (ftNodeDTO[index] != null) {
                        elm = $('#ftDetail').find('#' + index + 'ID');
                        elm.val(ftNodeDTO[index]);
                    }
                }
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {
                    for (var index in ftNodeDTO[ftStringName]) {
                        var input = ftNodeDTO[ftStringName];
                        if (input[index] != null) {
                            $('#ftDetail').find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                }
                // 组合测试流程结点
                if (ftNodeDTO.hasOwnProperty(ftStringName) && ftNodeDTO[ftStringName] && ftNodeDTO[ftStringName].ftResultDTO) {
                    var ftResult = ftNodeDTO[ftStringName]['ftResultDTO'];
                    $('#CPDetail').find('table:first tr:first').append(generateFTResultColumnHeaders(ftStringName, ftResult));
                    $('#CPDetail').find('table:first tr:last').append(generateFTResultColumns(ftStringName, ftResult));
                    $('#CPDetail').find('table:first tr:first').append(generateBinColumnHeaders(sblDTOs));
                    $('#CPDetail').find('table:first tr:last').append(generateBinColumns(ftResult['bin'], sblDTOs));
                    registerOnFinalYieldChangedListener($('#CPDetail').find('table:first'));
                }

                if (ftNodeDTO.cpState == 3) {
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);

                }
                if (ftNodeDTO.cpState == 2) {
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#CPDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#CPDetail').find('input').attr('disabled', false);
                    $('#CPDetail').find('.yield').attr('disabled', true);
                    $('#CPDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.cpState == 1 || (ftNodeDTO.cpState == 3 )) {
                    $('#CPDetail').find('input').attr('disabled', true);
                    $('#CPDetail').find('textarea').attr('disabled', true);
                    $('#CPDetail').find('select').attr('disabled', true);
                }

                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                    //debugger;
                    if (!Validator.Validate($('#CPDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });
                function collectData() {
                    if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                        var n = 0;
                        var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                        var binValueGet = [];
                        $.each($('#CPDetail').find('table td .flag1'), function () {
                            binValueGet.push(this.value);
                        });
                        for (var k = 0; k < binValuetoSend.length; k++) {
                            if (binValuetoSend[k] != '-1') {
                                binValuetoSend[k] = binValueGet[n++];
                            }
                        }
                        $('#CPDetail').find('table .flag2 input').val(binValuetoSend);
                        var yieldPostVal = $('#CPDetail').find('table .yield').val();
                        var failPostVal = $('#CPDetail').find('table .fail').val();
                        //$('#CPDetail').find('table .yield').val(parseInt(yieldPostVal)/100);
                        if (buttonName == 'IQC') {
                            var json = {
                                'processId': FTProcessDTO.id,
                                'isModify': 0,
                                'ftIQCDTO.ftResultDTO.yield': parseInt(yieldPostVal) / 100,
                                'ftIQCDTO.ftResultDTO.fail': parseInt(failPostVal) / 100
                            };
                        } else if (buttonName == 'Baking') {

                        } else {

                        }
                    } else {
                        var json = {'processId': FTProcessDTO.id, 'isModify': 0};
                    }
                    return json;
                }
                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    if (!Validator.Validate($('#CPDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNode(checkedId, json, nodeName);
                });
                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    //	$('#CPDetail').find("button:nth-last-child(1),button:nth-last-child(2)").css('display','inline-block');
                    if (!Validator.Validate($('#CPDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNodeThenEndNode(checkedId, json, nodeName);
                });
            });
        };
        
        var onCPNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            var buttonName = ftNodeDTO.name;
            buttonName="IQC";
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/CP_' + buttonName + '.jsp').done(function (html) {
                $('#CPDetail').html(html);
              	//IQC界面根据Wafer片生成对应操作表格
              	debugger;
                $.get('${pageContext.request.contextPath}/CPWafer/getCPWaferInfo.koala?cpLotId='+checkedId+'&nodeId='+ftNodeDTO.id).done(function (data) {
                	debugger;
                	data=data['data'];
                	$("#CPDetail table tbody").empty();
                	//data=[{'id':'1','aceWaferid':'S02481-01','opt':'IQC'},{'id':'4','aceWaferid':'S02481-01','opt':'IQC'},{'id':'2','aceWaferid':'S02481-01','opt':'IQC'},{'id':'3','aceWaferid':'S02481-01','opt':'IQC'}]
                	$.each(data,function(a){
                		var optitem="";
                		if(data[a].state=="0")
               			{
                			optitem='<a class="optbutton">'+ftNodeDTO.name+'</a>';
               			}
                		else{
                			optitem='<a style="color:#888">已通过'+ftNodeDTO.name+'</a>';
                		}
                		$("#CPDetail table tbody").append('<tr><td><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></div></td>'
                        		+'<td><span style="display:none;">'+data[a].id+'</span>'+parseInt(a+1)+'</td><td>'+data[a].internalWaferCode+'</td><td>'+optitem+'</td><td><a href="'+data[a].map+'">查看</a></td>'
                        		+'<td><a>详情</a></td><td>'+data[a].internalWaferCode+'</td><td>'+data[a].pass+'</td><td>'+data[a].fail+'</td><td>'+data[a].internalOffset+'</td><td>'+data[a].customerOffset+'</td></tr>');
                	});
                	if (ftNodeDTO.cpState == 2) {
                        CPtablebind($("#CPDetail table"));
                    }
                }); 
                var elm;
                for (var index in ftNodeDTO) {
                    if (ftNodeDTO[index] != null) {
                        elm = $('#ftDetail').find('#' + index + 'ID');
                        elm.val(ftNodeDTO[index]);
                    }
                }
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {
                    for (var index in ftNodeDTO[ftStringName]) {
                        var input = ftNodeDTO[ftStringName];
                        if (input[index] != null) {
                            $('#ftDetail').find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                }
                // 组合测试流程结点
                if (ftNodeDTO.hasOwnProperty(ftStringName) && ftNodeDTO[ftStringName] && ftNodeDTO[ftStringName].ftResultDTO) {
                    var ftResult = ftNodeDTO[ftStringName]['ftResultDTO'];
                    $('#CPDetail').find('table:first tr:first').append(generateFTResultColumnHeaders(ftStringName, ftResult));
                    $('#CPDetail').find('table:first tr:last').append(generateFTResultColumns(ftStringName, ftResult));
                    $('#CPDetail').find('table:first tr:first').append(generateBinColumnHeaders(sblDTOs));
                    $('#CPDetail').find('table:first tr:last').append(generateBinColumns(ftResult['bin'], sblDTOs));
                    registerOnFinalYieldChangedListener($('#CPDetail').find('table:first'));
                }

                if (ftNodeDTO.cpState == 3) {
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);

                }
                if (ftNodeDTO.cpState == 2) {
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#CPDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#CPDetail').find('input').attr('disabled', false);
                    $('#CPDetail').find('.yield').attr('disabled', true);
                    $('#CPDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.cpState == 1 || (ftNodeDTO.cpState == 3 )) {
                    $('#CPDetail').find('input').attr('disabled', true);
                    $('#CPDetail').find('textarea').attr('disabled', true);
                    $('#CPDetail').find('select').attr('disabled', true);
                }


                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                    //debugger;
                    if (!Validator.Validate($('#CPDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });
                function collectData() {
                    if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                        var n = 0;
                        var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                        var binValueGet = [];
                        $.each($('#CPDetail').find('table td .flag1'), function () {
                            binValueGet.push(this.value);
                        });
                        for (var k = 0; k < binValuetoSend.length; k++) {
                            if (binValuetoSend[k] != '-1') {
                                binValuetoSend[k] = binValueGet[n++];
                            }
                        }
                        $('#CPDetail').find('table .flag2 input').val(binValuetoSend);
                        var yieldPostVal = $('#CPDetail').find('table .yield').val();
                        var failPostVal = $('#CPDetail').find('table .fail').val();
                        //$('#CPDetail').find('table .yield').val(parseInt(yieldPostVal)/100);
                        if (buttonName == 'IQC') {
                            var json = {
                                'processId': FTProcessDTO.id,
                                'isModify': 0,
                                'ftIQCDTO.ftResultDTO.yield': parseInt(yieldPostVal) / 100,
                                'ftIQCDTO.ftResultDTO.fail': parseInt(failPostVal) / 100
                            };
                        } else if (buttonName == 'Baking') {

                        } else {

                        }
                    } else {
                        var json = {'processId': FTProcessDTO.id, 'isModify': 0};
                    }
                    return json;
                }

                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    if (!Validator.Validate($('#CPDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNode(checkedId, json, nodeName);
                });
                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    //	$('#CPDetail').find("button:nth-last-child(1),button:nth-last-child(2)").css('display','inline-block');
                    if (!Validator.Validate($('#CPDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNodeThenEndNode(checkedId, json, nodeName);
                });
            });
        };

        var onIncomingNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            var buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/CP_Incoming.jsp').done(function (html) {
                $('#CPDetail').html(html);
                var elm;
                $.get('${pageContext.request.contextPath}/CPLot/get/'+checkedId+'.koala').done(function(data){
                	debugger;
                	data=data.data;
                	for (var index in data) {
                        if (data[index] != null) {
                            elm = $('#CPDetail').find('#' + index + 'ID');
                            elm.val(data[index]);
                        }
                    }
                	for (var index in data['customerCPLotDTO']) {
                        if (data['customerCPLotDTO'][index] != null) {
                            elm = $('#CPDetail').find('#' + index + 'ID');
                            elm.val(data['customerCPLotDTO'][index]);
                        }
                    }
                	for (var index in data['customerCPLotDTO']['internalProductDTO']) {
                        if (data['customerCPLotDTO']['internalProductDTO'][index] != null) {
                            elm = $('#CPDetail').find('#' + index + 'ID');
                            elm.val(data['customerCPLotDTO']['internalProductDTO'][index]);
                        }
                    }
                	$.each(data['cPWaferDTOs'],function(a){
                		$(".checkItem"+this.customerWaferIndex).attr("checked","checked");
                		$(".waferId"+this.customerWaferIndex).val(this.internalWaferCode);
                	})
                	debugger;
                	$("#quantityID").val(data['cPWaferDTOs'].length);
                });
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {
                    for (var index in ftNodeDTO[ftStringName]) {
                        var input = ftNodeDTO[ftStringName];
                        if (input[index] != null) {
                            $('#CPDetail').find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                } else {

                }
                //debugger;
                if (ftNodeDTO.hasOwnProperty(ftStringName) && ftNodeDTO[ftStringName] && ftNodeDTO[ftStringName].ftResultDTO) {

                    var $tableFirst = $('#CPDetail').find('table:first');
                    var finalYield = ftNodeDTO[ftStringName]['ftResultDTO'];
                    $tableFirst.find('tr:first').append(generateFTResultColumnHeaders(ftStringName, finalYield));
                    $tableFirst.find('tr:last').append(generateFTResultColumns(ftStringName, finalYield));
                    $tableFirst.find('tr:first').append(generateBinColumnHeaders(sblDTOs));
                    $tableFirst.find('tr:last').append(generateBinColumns(finalYield['bin'], sblDTOs));
                    registerOnFinalYieldChangedListener($tableFirst);

                    // 进炉时间
                    if ($('#CPDetail').find('#ftBakingDTO_timeInID').val() == 'null') {
                        $('#CPDetail').find('#ftBakingDTO_timeInID').val('');
                    }

                    // 出炉时间
                    if ($('#CPDetail').find('#ftBakingDTO_timeOutID').val() == 'null') {
                        $('#CPDetail').find('#ftBakingDTO_timeOutID').val('');
                    }
                }
                if (ftNodeDTO.cpState == 3) {
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);
                }
                if (ftNodeDTO.cpState == 2) {
                    $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#CPDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                }
                if (ftNodeDTO.cpState == 1 || (ftNodeDTO.cpState == 3 )) {
                    $('#CPDetail').find('input').attr('disabled', true);
                    $('#CPDetail').find('textarea').attr('disabled', true);
                    $('#CPDetail').find('select').attr('disabled', true);
                }

                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                    //debugger;
                    if (!Validator.Validate($('#CPDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });

                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    if (!Validator.Validate($('#CPDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNode(checkedId, json, nodeName);
                });
                $('#CPDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    if (!Validator.Validate($('#CPDetail').find('form')[0], 3))return;
                    var json = {}; 
                    json['processId']=FTProcessDTO.id;
                    var waferCode=[];
                    $("input[name^='internalWaferCode']").each(function(i){
                    	var a={};
                    	a['index']=this.getAttribute("name").replace(/[^0-9]/ig,"");
                    	a['waferCode']=this.value;
                    	waferCode.push(a)
                    });
                    json['waferCode']=waferCode;
                    saveNodeThenEndNodeIncoming(checkedId, json, nodeName);
                });
            });
        };

        var onClickedDispatcher = function (checkedId, CPProcessDTO, nodeName) {
            debugger;
            var callbackMap = {
            	"Incoming": onIncomingNodeClicked,
                "IQC": onIQCNodeClicked,
                "INK": onCPNodeClicked,
                "Bake": onCPNodeClicked,
                "CP": onCPNodeClicked,
                "Packing": onCPNodeClicked,
                "OQC": onCPNodeClicked,
                "FQC": onIQCNodeClicked
            };
            var cpNodeDTO = CPProcessDTO.cpNodeDTOs.filter(function (value) {
                return value.name == nodeName
            })[0];

            var callback = callbackMap[cpNodeDTO.name.startsWith("CP") ? "CP" : cpNodeDTO.name.startsWith("INK")? "INK" : cpNodeDTO.name];
            callback(checkedId, CPProcessDTO, cpNodeDTO);
        };

    </script>
</head>
<body>
<div style="width:100%;margin-right:auto; margin-left:auto; padding-top: 15px;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-2 sidebar"><%--此div块是左侧动态按钮--%>
                <div style="width:100%;height:914px;border:1px solid grey;overflow-y:auto;">
                    <ul id="DynamicButton" style="margin-top:10px;"></ul>
                </div>
            </div>
            <!-- search form -->
            <div class="col-lg-10 mainContent"><%--此div块是搜索框--%>
                <form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <div class="form-group">
                                    <label class="control-label" style="width:80px;float:left;">封装类型:&nbsp;</label>
                                    <div style="margin-left:5px;float:left;">
                                        <input name="packageType" class="form-control" type="text" style="width:80px;" id="typeID"/>
                                    </div>
                                    <label class="control-label" style="width:50px;float:left;">状态:&nbsp;</label>
                                    <div style="margin-left:5px;float:left;">
                                        <input name="currentState" class="form-control" type="text" style="width:80px;" id="currentStateID"/>
                                    </div>
<!--                                     <label class="control-label" style="width:50px;float:left;">站点:&nbsp;</label>
                                    <div style="margin-left:5px;float:left;">
                                        <input name="state" class="form-control" type="text" style="width:80px;" id="createEmployNoID"/>
                                    </div> -->
                                    <label class="control-label" style="width:50px;float:left;">Lot:&nbsp;</label>
                                    <div style="margin-left:5px;float:left;">
                                        <input name="internalLotNumber" class="form-control" type="text" style="width:80px;" id="internalLotNumberID"/>
                                    </div>
                                    <label class="control-label" style="width:50px;float:left;">时段:&nbsp;</label>
                                    <div style="margin-left:5px;float:left;">
                                        <div class="input-group date form_datetime" style="width:100px;float:left;">
                                            <input type="text" class="form-control" style="width:100px;" name="createTimestamp" id="createTimestampID_start">
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                        </div>
                                        <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
                                        <div class="input-group date form_datetime" style="width:100px;float:left;">
                                            <input type="text" class="form-control" style="width:100px;" name="createTimestampEnd" id="createTimestampID_end">
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td style="vertical-align: bottom;">
                                <button id="search" type="button" style="position:relative; margin-left:50px; top: -15px" class="btn btn-primary">
                                    <span class="glyphicon glyphicon-search"></span>&nbsp;查询
                                </button>
                            </td>
                        </tr>
                    </table>
                </form>
                <!-- grid -->
                <div id=<%=gridId%>></div>
                <%--此div块是表格--%>
                <%--background-color: lightgrey;--%>
                <div style="height:400px; border:1px solid grey;margin-bottom: 12px; overflow:auto;"id="CPDetail"></div>
                <%--此div块是按钮对应内容--%>
            </div>
        </div>
    </div>
</div>
</body>
</html>
