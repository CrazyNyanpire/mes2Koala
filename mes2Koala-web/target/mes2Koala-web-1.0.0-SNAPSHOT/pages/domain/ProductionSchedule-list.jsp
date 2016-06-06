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
        var _dialog;
        var content = {};
        $(function () {
            grid = $("#<%=gridId%>");
            form = $("#<%=formId%>");
            PageLoader = {
                //
                initSearchPanel: function () {
                    var contents = [{title: '请选择', value: ''}];//添加状态字典表维护
                    $.ajax({
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/productionScheduleState.koala',
                        type: 'GET',
                        dataType: 'json'
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            content[msg[i]['value']] = msg[i]['label'];
                            contents.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                        form.find('#stateID').select({
                            title: '请选择',
                            contents: contents
                        }).on('change', function () {
                            form.find('#stateID_').val($(this).getValue());
                        });
                    });
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
                    var width = 180;
                    return grid.grid({
                        identity: "id",
                        buttons: [
                            //  {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
                            {
                                content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>排产</button>',
                                action: 'modify'
                            },
                            {
                                content: '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-edit"><span>置顶</button>',
                                action: 'moveToTop'
                            },
                            {
                                content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>',
                                action: 'delete'
                            },
                            {
                                content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-chevron-up"><span>上移</button>',
                                action: 'reorderUp'
                            },
                            {
                                content: '<button class="btn btn-warning" type="button"><span class="glyphicon glyphicon-chevron-down"><span>下移</button>',
                                action: 'reorderDown'
                            }
                        ],
                        url: "${pageContext.request.contextPath}/ProductionSchedule/pageJson.koala",
                        columns: [
                            {title: '机台', name: 'testSysName', width: width},
                            {title: '艾科批号', name: 'lotNumber', width: width},
                            {
                                title: 'PID',
                                name: 'internalProductNumber',
                                width: width,
                            },
                            {title: '客户型号', name: 'customerProductNumber', width: 130},
                            {title: '客户批号', name: 'customerLotNumber', width: width},
                            {title: '数量(pcs)', name: 'amount', width: 100},
                            {title: '所属节点', name: 'nodeName', width: 100},
                            {title: '备注', name: 'note', width: width},
                            {title: '机台状态', name: 'subState', width: 100},
                            {title: 'PPO', name: 'pPO', width: width},
                            {title: '封装批号', name: 'packageNumber', width: width},
                            {title: '数量(pcs)', name: 'amount', width: 100},
                            {title: '测试数量(pcs)', name: 'doneQty', width: 100},
                            {title: '预计开始时间', name: 'planedStartTimestamp', width: width},
                            {title: '预计结束时间', name: 'planedEndTimestamp', width: width},
                            {title: '实际开始时间', name: 'actualStartTimestamp', width: width},
                            {title: '实际结束时间', name: 'actualEndTimestamp', width: width},
                            {title: '预计需时', name: 'plannedTimeTakes', width: 100},
                            {title: '实际需时', name: 'actualTimeTakes', width: 100},
                            {
                                title: '测试状态', name: 'state', width: width, render: function (rowdata, name, index) {
                                return content[rowdata['state']];
                                }
                            },
                            {title: '批次当前状态', name: 'lotCurrentState', width: width},
                            {title: '批次Hold状态', name: 'lotHoldState', width: width}



                            /*                         	                             { title: '操作', width: 120, render: function (rowdata, name, index)
                             {
                             var param = '"' + rowdata.id + '"';
                             var h = "<a href='javascript:openDetailsPage(" + param + ")'>查看</a> ";
                             return h;
                             }
                             }*/
                        ]
                    }).on({
                        /*'add': function(){
                         self.add($(this));
                         },*/
                        'modify': function (event, data) {
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
                            self.modify(indexs[0], $this);
                        },
                        'moveToTop': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择要置顶的任务'
                                });
                                return;
                            }
                            var moveToTop = function () {
                                self.moveToTop(indexs, $this);
                            };
                            $this.confirm({
                                content: '确定要置顶所选记录吗?',
                                callBack: moveToTop
                            });
                        },
                        'delete': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择要删除的记录'
                                });
                                return;
                            }
                            var remove = function () {
                                self.remove(indexs, $this);
                            };
                            $this.confirm({
                                content: '确定要删除所选记录吗?',
                                callBack: remove
                            });
                        },
                        'reorderUp': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择需要上移的数据'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行上移'
                                })
                                return;
                            }
                            self.reorderUp(indexs[0], $this);
                        },
                        'reorderDown': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择需要下移的数据'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行下移'
                                })
                                return;
                            }
                            self.reorderDown(indexs[0], $this);
                        }
                    });
                },
                /*  add: function(grid){
                 var self = this;
                 var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                 +'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                 +'data-dismiss="modal" aria-hidden="true">&times;</button>'
                 +'<h4 class="modal-title">新增</h4></div><div class="modal-body">'
                 +'<p>One fine body&hellip;</p></div><div class="modal-footer">'
                 +'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                 +'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                 +'</div></div>');
                 $.get('<%=path%>/ProductionSchedule-add.jsp').done(function(html){
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
                 $.post('${pageContext.request.contextPath}/ProductionSchedule/add.koala', dialog.find('form').serialize()).done(function(result){
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
                 },*/
                modify: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/ProductionSchedule-update.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/ProductionSchedule/get/' + id + '.koala').done(function (json) {
                            json = json.data;
                            var elm;
                            for (var index in json) {
                                elm = dialog.find('#' + index + 'ID');
                                if (elm.hasClass('select')) {
                                    elm.setValue(json[index]);
                                } else {
                                    elm.val(json[index]);
                                }
                            }
                            var contents = [{title: '请选择', value: ''}];//添加机台选择下拉框
                            $.ajax({
                                url: '${pageContext.request.contextPath}/TestSys/getTestSyses/' + json.testType + '.koala',
                                type: 'GET',
                                dataType: 'json'
                            }).done(function (msg) {
                                for (var i = 0; i < msg.length; i++) {
                                    contents.push({
                                        title: msg[i]['platformNumber'],
                                        value: msg[i]['id']
                                    });
                                }
                                dialog.find('#testSysIdID').select({
                                    title: '请选择',
                                    contents: contents
                                }).on('change', function () {
                                    dialog.find('#testSysIdID_').val($(this).getValue());
                                });
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
                            $.post('${pageContext.request.contextPath}/ProductionSchedule/basicScheduling.koala', dialog.find('form').serialize()).done(function (result) {
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
                                        content: result.actionError
                                    });
                                }
                            });
                        });
                    });
                },
                initPage: function (form) {
                    form.find('.form_datetime').datetimepicker({
                        language: 'zh-CN',
                        format: "yyyy-mm-dd hh:ii",
                        autoclose: true,
                        todayBtn: true,
                        minView: 0,
                        pickerPosition: 'bottom-left'
                    }).datetimepicker('setDate', new Date());//加载日期选择器

                    /* 	var selectItems = {};
                     var contents = [{title: '请选择', value: ''}];//加载目标机台
                     $.ajax({
                     async: false,
                     url: '
                    ${pageContext.request.contextPath}/TestSys/getTestSyses.koala',
                     type: 'GET',
                     dataType: 'json'
                     }).done(function (msg) {
                     for (var i = 0; i < msg.length; i++) {
                     contents.push({
                     title: msg[i]['platformNumber'],
                     value: msg[i]['id']
                     });
                     }
                     });
                     selectItems['testSysIdID'] = contents;*/

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
                    /*         form.find('.select').each(function(){
                     var select = $(this);
                     var idAttr = select.attr('id');
                     select.select({
                     title: '请选择',
                     contents: selectItems[idAttr]
                     }).on('change', function(){
                     form.find('#'+ idAttr + '_').val($(this).getValue());
                     });
                     });*/
                },
                moveToTop: function (ids, grid) {
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/ProductionSchedule/moveToTop.koala', data).done(function (result) {
                        if (result.success) {
                            grid.data('koala.grid').refresh();
                            grid.message({
                                type: 'success',
                                content: '置顶成功'
                            });
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.actionError
                            });
                        }
                    });
                },
                remove: function (ids, grid) {
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/ProductionSchedule/delete.koala', data).done(function (result) {
                        if (result.success) {
                            grid.data('koala.grid').refresh();
                            grid.message({
                                type: 'success',
                                content: '删除成功'
                            });
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.result
                            });
                        }
                    });
                },
                reorderUp: function (id, grid) {
                    $.post('${pageContext.request.contextPath}/ProductionSchedule/reorderUp.koala', {'id': id}).done(function (result) {
                        if (result.success) {
                            grid.data('koala.grid').refresh();
                            grid.message({
                                type: 'success',
                                content: '上移成功'
                            });
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.actionError
                            });
                        }
                    });
                },
                reorderDown: function (id, grid) {
                    $.post('${pageContext.request.contextPath}/ProductionSchedule/reorderDown.koala', {'id': id}).done(function (result) {
                        if (result.success) {
                            grid.data('koala.grid').refresh();
                            grid.message({
                                type: 'success',
                                content: '下移成功'
                            });
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.actionError
                            });
                        }
                    });
                }
            }
            PageLoader.initSearchPanel();
            PageLoader.initGridPanel();
            window.setInterval(function () {
                grid.data('koala.grid').refresh();
            }, 900000);//定期刷新，15分钟=900000；
            //双击弹出模态框
            grid.find('.grid-table-body').find('table').on('dblclick', function (e) {
                e.stopPropagation();
                var selectedId = grid.getGrid().selectedRowsIndex();//获取某一行id
                if (selectedId.length > 1) {
                    grid.message({
                        type: 'warning',
                        content: '只能选择一条记录进行修改'
                    })
                    return;
                }
                var selectedRows = grid.getGrid().selectedRows()[0];//获取某一行数据
                debugger;
                var self = this;
                var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"  style="width:800px;"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"></div></div></div></div>');
                $.get('<%=path%>/ProductionSchedule-add.jsp').done(function (html) {
                    dialog.find('.modal-body').html(html);
                    var contents = [{title: '请选择', value: ''}];//添加状态字典表维护
                    $.ajax({
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/productionScheduleState.koala',
                        type: 'GET',
                        dataType: 'json'
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contents.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                        var p1 = new Promise(function (resolve, reject) {
                            dialog.find('#stateID').select({
                                title: '请选择',
                                contents: contents
                            });
                            resolve(dialog.find('#stateID'));
                        })
                        p1.then(function (stateObject) {
                            stateObject.setValue(selectedRows['state']);
                            stateObject.on('change', function () {
                                dialog.find('#stateID_').val($(this).getValue());
                            });
                        })
                    });
                    var elm;
                    for (var index in selectedRows) {
                        elm = dialog.find('#' + index + 'ID');
                        elm.val(selectedRows[index]);
                    }
                    for (var index in selectedRows) {
                        elm = dialog.find('#' + index);
                        elm.val(selectedRows[index]);
                    }
                    var time = new Date();//暂停时间
                    var timeShow = time.format('isoDate') + ' ' + time.format('isoTime');
                    dialog.find('#stopTimestampID').val(timeShow.substr(0, timeShow.length - 3));
                    dialog.modal({
                        keyboard: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        },
                        'shown.bs.modal': function () {
                            var testSyses = [{title: '请选择', value: ''}];//添加机台选择下拉框
                            $.ajax({
                                url: '${pageContext.request.contextPath}/TestSys/getTestSyses/' + selectedRows.testType + '.koala',
                                type: 'GET',
                                dataType: 'json'
                            }).done(function (msg) {
                                for (var i = 0; i < msg.length; i++) {
                                    testSyses.push({
                                        title: msg[i]['platformNumber'],
                                        value: msg[i]['id']
                                    });
                                }
                                dialog.find('#targetTestSysNameID').select({
                                    title: '请选择',
                                    contents: testSyses
                                }).on('change', function () {
                                    dialog.find('#targetTestSysNameID_').val($(this).getValue());
                                });
                            });
                        }
                    });
                    dialog.find('#separateSubmit').on('click', {grid: grid}, function (e) {//分批测试
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        $.post('${pageContext.request.contextPath}/ProductionSchedule/separate.koala', 'id=' + selectedId + '&' + dialog.find('form:first').serialize()).done(function (result) {
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
                                    content: result.actionError
                                });
                            }
                        });
                    });
                    dialog.find('#stopSubmit').on('click', {grid: grid}, function (e) {//暂停测试
                        if (!Validator.Validate(dialog.find('form')[1], 3))return;
                        var data = {'id': selectedId, 'version': dialog.find('versionID').val(), 'state': 4};
                        $.post('${pageContext.request.contextPath}/ProductionSchedule/updateState/' + selectedId + '.koala', data).done(function (result) {
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
                                    content: result.actionError
                                });
                            }
                        });
                    });
                    dialog.find('#stateChangeSubmit').on('click', {grid: grid}, function (e) {//状态修改
                        if (!Validator.Validate(dialog.find('form')[2], 3))return;
                        $.post('${pageContext.request.contextPath}/ProductionSchedule/updateState/' + selectedId + '.koala', dialog.find('form:last').serialize()).done(function (result) {
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
                                    content: result.actionError
                                });
                            }
                        });
                    });
                });
            });
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
        });


        var openDetailsPage = function (id) {
            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
            $.get('<%=path%>/ProductionSchedule-view.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);
                $.get('${pageContext.request.contextPath}/ProductionSchedule/get/' + id + '.koala').done(function (json) {
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
                        <label class="control-label" style="width:100px;float:left;">机台:&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <input name="testSysName" class="form-control" type="text" style="width:180px;"
                                   id="siteID"/>
                        </div>
                        <label class="control-label" style="width:100px;float:left;">艾科批号:&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <input name="lotNumber" class="form-control" type="text" style="width:180px;"
                                   id="lotNumberID"/>
                        </div>
                        <label class="control-label" style="width:100px;float:left;">状态:&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <div class="btn-group select" id="stateID"></div>
                            <input name="state" type="hidden" id="stateID_"/>
                        </div>
                    </div>
                </td>
                <td style="vertical-align: bottom;">
                    <button id="search" type="button" style="position:relative; margin-left:35px; top: -15px"
                            class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询
                    </button>
                </td>
            </tr>
        </table>
    </form>
    <!-- grid -->
    <div id=<%=gridId%>></div>
</div>
</body>
</html>
