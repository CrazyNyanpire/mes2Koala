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
        var optionIds = [];
        var contents = [{title: '请选择', value: ''}];
        $(function () {
            grid = $("#<%=gridId%>");
            form = $("#<%=formId%>");
            PageLoader = { //不加var ,此时就是全局变量，不推荐这样用，但是在此处没有影响
                initSearchPanel: function () {
                    $.ajax({
                    	async: false,
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/HandlerType.koala',
                        type: 'GET',
                        dataType: 'json',
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contents.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                    });
                    $('#handlerTypeID').select({
                        title: '请选择',
                        contents: contents
                    }).on('change', function () {
                        $('#handlerTypeID_').val($(this).getValue());
                    });
                },
                initGridPanel: function () {
                    var self = this;
                    var width = 100;
                    return grid.grid({
                        identity: "id",
                        buttons: [
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>',
                                action: 'add'
                            },
                            {
                                content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>',
                                action: 'modify'
                            },
                            {
                                content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>',
                                action: 'delete'
                            },
                            /*
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-eye-open"><span>查看R/C</button>',
                                action: 'checkRuncard'
                            },
                            */
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-leaf"><span>流程组装</button>',
                                action: 'assembly'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-star"><span>流程签核</button>',
                                action: 'authorize'
                            },
                            /*
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-tag"><span>特殊表单</button>',
                                action: 'specialForm'
                            }
                            */
                        ],
                        url: "${pageContext.request.contextPath}/ProcessTemplate/pageJson.koala",
                        columns: [
                            {title: '机械手类型', name: 'handlerType', width: width,sortable: true, sortName: 'handlerType'},
                            {title: '测试类型', name: 'testType', width: width,sortable: true, sortName: 'testType'},
                            {title: 'Process名称', name: 'name', width: width,sortable: true, sortName: 'name'},
                            {
                                title: '流程明细', name: 'content',sortable: true, sortName: 'content', width: 450, render: function (rowdata, name, index) {
                                var h = [];
                                var str = rowdata.content;
                                if (rowdata['testingTemplateDTO']) {
                                    str = str.replace('Test', rowdata['testingTemplateDTO']['content']);
                                }
                                ;
                                var content = str.split('|');
                                for (var i = 0; i < content.length; i++) {
                                    h[i] = "<a href='javascript:processAssembly(" + index + ',' + i + ")'>" + content[i] + "</a>";
                                }
                                return h;
                            }
                            },
                            {title: '创建人', name: 'createEmployNo', width: width,sortable: true, sortName: 'createEmployNo'},
                            {title: '签核状态', name: 'allowState', width: width,sortable: true, sortName: 'allowState'},
                            {title: 'Runcard', name: 'runcard', width: width,sortable: true, sortName: 'runcard'},
                            {
                                title: '操作', width: 120, render: function (rowdata, name, index) {
                                var param = rowdata.id;
                                var h = "<a href='javascript:openProcessTemplateDetailsPage(" + param + ")'>查看</a> ";
                                return h;
                            }
                            }
                        ]
                    }).on({
                        'add': function () {
                            self.add($(this));
                        },
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
                        'assembly': function () {
                            self.assembly($(this));
                        },
                        'authorize': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行授权'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行授权'
                                })
                                return;
                            }
                            self.authorize(indexs[0], $this);
                        },
                        'checkRuncard': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行Runcard生成'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行Runcard生成'
                                })
                                return;
                            }
                            self.checkRuncard(indexs[0], $this);
                        },
                        'specialForm': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行关联特殊表单'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行关联特殊表单'
                                })
                                return;
                            }
                            self.specialForm(data.item[0]['specialFormDTO'].id, $(this));
                        }
                    });
                },
                add: function (grid) {
                    var flag = 1;
                    var oString = [];
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                            + '<div class="modal-content" id="modalContent"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">新增process</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/ProcessTemplate-add.jsp').done(function (html) {
                        dialog.modal({
                            keyboard: false
                        }).on({
        	                'hidden.bs.modal': function(){
        	                    $(this).remove();
        	                }
        	            }).find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        dialog.find('#modalOpen').click(function () {//打开
                         	dialogs=$('<div class="modal fade" id="myModal" data-backdrop="false" aria-labelledby="myModalLabel">'
                    		+'<div class="modal-dialog"><div class="modal-content"><div class="modal-header">'
                    		+'<button type="button" class="close" id="modalClose" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                    		+'<h4 class="modal-title" id="myModalLabel">新增机械手类型</h4></div><div class="modal-body"><div class="form-horizontal">'
                    		+'<div class="form-group"><label class="col-lg-3 control-label">机械手名称值:</label>'
                    		+'<div class="col-lg-9"><input name="value" style="display:inline; width:94%;" class="form-control"  type="text"  id="valueID" datatype="Require" />'
                    		+'<span class="required">*</span></div></div>'
                    		+'<div class="form-group"><label class="col-lg-3 control-label">机械手名称标签:</label>'
                    		+'<div class="col-lg-9"><input name="label" style="display:inline; width:94%;" class="form-control"  type="text"  id="labelID" datatype="Require" />'
                    		+'<span class="required">*</span></div></div>'
                    		+'<div class="form-group"><label class="col-lg-3 control-label">类型:</label>'
                    		+'<div class="col-lg-9"><input name="type" style="display:inline; width:94%;" class="form-control"  type="text" value="HandlerType" id="typeID" disabled/>'
                    		+'</div></div>'
                    		+'<div class="form-group"><label class="col-lg-3 control-label">描述:</label>'
                    		+'<div class="col-lg-9"><textarea class="form-control" style="display:inline; width:94%;" rows="3" id="descriptionID" name="description"  disabled>机械手类型</textarea>'
                    		+'</div></div></div></div>'
                    		+'<div class="modal-footer">'
                    		+'<button type="button" class="btn btn-default" id="modalCancel" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="modalSave">保存</button>'
                    		+'</div></div></div></div>');
                         	dialogs.modal({
                                keyboard: false
                            }).on({
            	                'hidden.bs.modal': function(){
            	                    $(this).remove();
            	                }
            	            })
                         	dialogs.find('#modalSave').on('click', function () {//新增机械手类型
                                for (var i = 0; i < contents.length; i++) {//判断新增的是否已有
                                    if (dialogs.find('#valueID').val() == contents[i].value) {
                                        flag = 0;
                                    }
                                }
                                if (flag) {// 确定是新增的机械手类型
                                    var data = {//取需要保存的数据
                                        "value": dialogs.find('#valueID').val(),
                                        'label': dialogs.find('#labelID').val(),
                                         type: 'HandlerType',
                                        'description': '机械手类型'
                                    }
                                    $.post('${pageContext.request.contextPath}/SystemDictionary/add.koala', data).done(function (result) {
                                        if (result.success) {
                                            flag = 1;
                                            contents.push({//保存完添加到机械手类型的字典表维护中和当前显示的下拉框中
                                                title: dialogs.find('#labelID').val(),
                                                value: dialogs.find('#valueID').val()
                                            });
                                            dialog.find('#handlerTypeID').select({
                                                title: dialog.find('#labelID').val(),
                                                contents: contents
                                            });
                                            dialogs.modal('hide');// 关闭模态框
                                            dialog.find('#handlerTypeID').setValue(dialogs.find('#valueID').val());
                                            dialog.find('#handlerTypeID_').val(dialogs.find('#valueID').val());
                                            dialog.find('form').message({
                                                type: 'success',
                                                content: '保存成功'
                                            });
                                        } else {
                                            dialogs.find('#myModal').find('.modal-content').message({
                                                type: 'error',
                                                content: result.actionError
                                            });
                                        }
                                    });
                                } else {
                                    flag = 1;//添加的机械手类型实际已存在
                                    dialogs.find('#valueID').val('');//清空已填的无效机械手类型
                                    dialogs.find('#labelID').val('');
                                    dialogs.modal('hide');
                                    dialogs.find('.modal-content').message({
                                        type: 'error',
                                        content: '机械手类型已存在'
                                    });
                                }
                            });
                        }); 
                        
                        dialog.find("#testTypeID").on("change",function(){
                        	if($(this).getValue()=="CP")
                       		{
                       			$("#list1").empty();
                       			$("#list2").empty();
                       			var list1 = $('#list1');
                       			$.get('${pageContext.request.contextPath}/SystemDictionary/getByType/CPProcessNode.koala').done(function (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        var oOption = $("<option value=" + data[i].label + ">" + data[i].value + "</option>");
                                        list1.append(oOption);
                                    }
                                });
                       		}
                        	else{
                        		$("#list1").empty();
                        		$("#list2").empty();
                        		 var testFlag = {};
                                 var list1 = $('#list1');
                                 var data = {'page': 0, 'pagesize': 100};
                                 $.post('${pageContext.request.contextPath}/TestingTemplate/pageJson.koala', data).done(function (msg) {
                                     for (var i = 0; i < msg.data.length; i++) {
                                         var oOption = $("<option title=" + msg.data[i].content + " id=" + msg.data[i].id + " value='Test-" + msg.data[i].name + "'>" + msg.data[i].name + "</option>");
                                         list1.append(oOption);
                                         testFlag[msg.data[i].name] = 1;
                                     }
                                 });
                                 $.get('${pageContext.request.contextPath}/SystemDictionary/getByType/ProcessNode.koala').done(function (data) {
                                     for (var i = 0; i < data.length; i++) {
                                         var oOption = $("<option value=" + data[i].label + ">" + data[i].value + "</option>");
                                         list1.append(oOption);
                                     }
                                 });
                        	}
                        });
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        var optionArray = [];
                        for (var i = 0; i < optionIds.length; i++) {
                            optionArray.push(optionIds[i]['id']);//取所有test节点的id拼装成数组传给后台
                        }
                        dialog.find('#testingID').val(optionArray);
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        $.each(dialog.find('form').find('#list2').find('option'), function () {//右侧数据数组
                            oString.push(this.value);
                        });
                        $.post('${pageContext.request.contextPath}/ProcessTemplate/add.koala', dialog.find('form').serialize() + '&content=' + oString.join('|')).done(function (result) {
                            if (result.success) {
                                optionIds = [];
                                dialog.modal('hide');
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                    type: 'success',
                                    content: '保存成功'
                                });
                            } else {
                                dialog.find('#modalContent').message({
                                    type: 'error',
                                    content: result.errorMessage
                                });
                                oString = '';
                            }
                        });
                    });

                },
                modify: function (id, grid) {
                    var oString = [];
                    var self = this;
                    var j = 0;
                    var flag = 1;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content" id="modalContent"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改Process</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/ProcessTemplate-update.jsp').done(function (html) {
                    	dialog.modal({
                            keyboard: false
                        }).on({
        	                'hidden.bs.modal': function(){
        	                    $(this).remove();
        	                }
        	            }).find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/ProcessTemplate/get/' + id + '.koala').done(function (json) {
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
                            var content = json['content'].split('|');
                            for (var i = 0; i < content.length; i++) {
                                if (content[i].search('Test-') < 0) {
                                    var oOption = $("<option value=" + content[i] + ">" + content[i] + "</option>");
                                } else {
                                    var oOption = $("<option value=" + content[i] + ">" + json['testingTemplateDTOs'][j++]['name'] + "</option>");

                                }
                                dialog.find('form').find('#list2').append(oOption);
                            }
                            if (json['testingTemplateDTOs'] != null) {
                                $.each(json['testingTemplateDTOs'], function () {
                                    optionIds.push({id: this.id});
                                })
                            }
                            if(json["testType"]=="CP"){
                            	dialog.find("#list1").empty();
                       			var list1 = dialog.find('#list1');
                       			$.get('${pageContext.request.contextPath}/SystemDictionary/getByType/CPProcessNode.koala').done(function (data) {
                       				$("#list1").empty();
                                    for (var i = 0; i < data.length; i++) {
                                        var oOption = $("<option value=" + data[i].label + ">" + data[i].value + "</option>");
                                        list1.append(oOption);
                                    }
                                });
                            }
                        });                      
                        dialog.find('#modalOpen').click(function () {//打开
                         	dialogs=$('<div class="modal fade" id="myModal" data-backdrop="false" aria-labelledby="myModalLabel">'
                    		+'<div class="modal-dialog"><div class="modal-content"><div class="modal-header">'
                    		+'<button type="button" class="close" id="modalClose" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                    		+'<h4 class="modal-title" id="myModalLabel">新增机械手类型</h4></div><div class="modal-body"><div class="form-horizontal">'
                    		+'<div class="form-group"><label class="col-lg-3 control-label">机械手名称值:</label>'
                    		+'<div class="col-lg-9"><input name="value" style="display:inline; width:94%;" class="form-control"  type="text"  id="valueID" datatype="Require" />'
                    		+'<span class="required">*</span></div></div>'
                    		+'<div class="form-group"><label class="col-lg-3 control-label">机械手名称标签:</label>'
                    		+'<div class="col-lg-9"><input name="label" style="display:inline; width:94%;" class="form-control"  type="text"  id="labelID" datatype="Require" />'
                    		+'<span class="required">*</span></div></div>'
                    		+'<div class="form-group"><label class="col-lg-3 control-label">类型:</label>'
                    		+'<div class="col-lg-9"><input name="type" style="display:inline; width:94%;" class="form-control"  type="text" value="HandlerType" id="typeID" disabled/>'
                    		+'</div></div>'
                    		+'<div class="form-group"><label class="col-lg-3 control-label">描述:</label>'
                    		+'<div class="col-lg-9"><textarea class="form-control" style="display:inline; width:94%;" rows="3" id="descriptionID" name="description"  disabled>机械手类型</textarea>'
                    		+'</div></div></div></div>'
                    		+'<div class="modal-footer">'
                    		+'<button type="button" class="btn btn-default" id="modalCancel" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="modalSave">保存</button>'
                    		+'</div></div></div></div>');
                         	dialogs.modal({
                                keyboard: false
                            }).on({
            	                'hidden.bs.modal': function(){
            	                    $(this).remove();
            	                }
            	            })
                         	dialogs.find('#modalSave').on('click', function () {//新增机械手类型
                                for (var i = 0; i < contents.length; i++) {//判断新增的是否已有
                                    if (dialogs.find('#valueID').val() == contents[i].value) {
                                        flag = 0;
                                    }
                                }
                                if (flag) {// 确定是新增的机械手类型
                                    var data = {//取需要保存的数据
                                        "value": dialogs.find('#valueID').val(),
                                        'label': dialogs.find('#labelID').val(),
                                         type: 'HandlerType',
                                        'description': '机械手类型'
                                    }
                                    $.post('${pageContext.request.contextPath}/SystemDictionary/add.koala', data).done(function (result) {
                                        if (result.success) {
                                            flag = 1;
                                            contents.push({//保存完添加到机械手类型的字典表维护中和当前显示的下拉框中
                                                title: dialogs.find('#labelID').val(),
                                                value: dialogs.find('#valueID').val()
                                            });
                                            dialog.find('#handlerTypeID').select({
                                                title: dialog.find('#labelID').val(),
                                                contents: contents
                                            });
                                            dialogs.modal('hide');// 关闭模态框
                                            dialog.find('#handlerTypeID').setValue(dialogs.find('#valueID').val());
                                            dialog.find('#handlerTypeID_').val(dialogs.find('#valueID').val());
                                            dialog.find('form').message({
                                                type: 'success',
                                                content: '保存成功'
                                            });
                                        } else {
                                            dialogs.find('#myModal').find('.modal-content').message({
                                                type: 'error',
                                                content: result.actionError
                                            });
                                        }
                                    });
                                } else {
                                    flag = 1;//添加的机械手类型实际已存在
                                    dialogs.find('#valueID').val('');//清空已填的无效机械手类型
                                    dialogs.find('#labelID').val('');
                                    dialogs.modal('hide');
                                    dialogs.find('.modal-content').message({
                                        type: 'error',
                                        content: '机械手类型已存在'
                                    });
                                }
                            });
                        }); 
                        dialog.find("#testTypeID").on("change",function(){
                        	if($(this).getValue()=="CP")
                       		{
                       			$("#list1").empty();
                       			$("#list2").empty();
                       			var list1 = $('#list1');
                       			$.get('${pageContext.request.contextPath}/SystemDictionary/getByType/CPProcessNode.koala').done(function (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        var oOption = $("<option value=" + data[i].label + ">" + data[i].value + "</option>");
                                        list1.append(oOption);
                                    }
                                });
                       		}
                        	else{
                        		$("#list1").empty();
                        		$("#list2").empty();
                        		 var testFlag = {};
                                 var list1 = $('#list1');
                                 var data = {'page': 0, 'pagesize': 100};
                                 $.post('${pageContext.request.contextPath}/TestingTemplate/pageJson.koala', data).done(function (msg) {
                                     for (var i = 0; i < msg.data.length; i++) {
                                         var oOption = $("<option title=" + msg.data[i].content + " id=" + msg.data[i].id + " value='Test-" + msg.data[i].name + "'>" + msg.data[i].name + "</option>");
                                         list1.append(oOption);
                                         testFlag[msg.data[i].name] = 1;
                                     }
                                 });
                                 $.get('${pageContext.request.contextPath}/SystemDictionary/getByType/ProcessNode.koala').done(function (data) {
                                     for (var i = 0; i < data.length; i++) {
                                         var oOption = $("<option value=" + data[i].label + ">" + data[i].value + "</option>");
                                         list1.append(oOption);
                                     }
                                 });
                        	}
                        });
                        dialog.find('#save').on('click', {grid: grid}, function (e) {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            var optionArray = [];
                            for (var i = 0; i < optionIds.length; i++) {
                                optionArray.push(optionIds[i]['id']);
                            }
                            dialog.find('#testingID').val(optionArray);
                            $.each(dialog.find('form').find('#list2').find('option'), function () {
                                oString.push(this.value);
                            });
                            $.post('${pageContext.request.contextPath}/ProcessTemplate/update.koala?id=' + id, dialog.find('form').serialize() + '&content=' + oString.join('|')).done(function (result) {
                                if (result.success) {
                                    optionIds = [];
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '保存成功'
                                    });
                                } else {
                                    dialog.find('#modalContent').message({
                                        type: 'error',
                                        content: result.errorMessage
                                    });
                                    oString = '';
                                }
                            })
                        });
                    });
                },
                authorize: function (id) {
                    $.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function (json) {
                        var employeeName = json['data']['name'];
                        var userId = json['data']['id'];
                        $.get('${pageContext.request.contextPath}/ProcessTemplate/getSignInfo.koala?processId=' + id + "&userId=" + userId).done(function (json) {
                            var data = json["data"];
                            if (!data["validate"]) {
                                grid.message({
                                    type: 'error',
                                    content: '您没有签核权限'
                                })
                                return;
                            }

                            var processTemplateSignInfo = {};
                            processTemplateSignInfo.opinion = json['data']['opinion'];
                            processTemplateSignInfo.note = json['data']['note'];


                            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">签核</h4></div>' +
                                    '<div class="modal-body"></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');

                            var body = $("<div class='form-group'><label class='col-lg-2 control-label'  class='style:display:inline'>" + employeeName + ':</label>' +
                                    "<div class='form-group'>" +
                                    "<input id='employeeID' type='hidden' name='id' value=" + userId + " />" +
                                    "<select style='margin-left:30px;' name='opinion' id='opinionID'><option value=''>请选择</option><option  value ='同意' >同意</option><option value='不同意'>不同意</option></select><div style='margin-top:10px;margin-left:40px'><label class='col-lg-3 control-label'  class='style:display:inline；'>签核备注：</label><textarea name='note' id='noteID' cols='70' rows='5'></textarea></div></div>");

                            body.find("#noteID").val(processTemplateSignInfo.note);
                            body.find("#opinionID");
                            if (processTemplateSignInfo.opinion === "同意") {
                                body.find("#opinionID").find("option[value='同意']").attr("selected", true);
                            }
                            if (processTemplateSignInfo.opinion === "不同意") {
                                body.find("#opinionID").find("option[value='不同意']").attr("selected", true);
                            }

                            dialog.find('.modal-body').append(body);

                            dialog.modal({
                                keyboard: false
                            }).on({
                                'hidden.bs.modal': function () {
                                    $(this).remove();
                                }
                            });

                            dialog.find('#save').on('click', {grid: grid}, function (e) {
                                debugger;
                                var authorInfo = dialog.find('.form-group');
                                var data = {
                                    'processTemplateId': id,
                                    'userId': $(authorInfo).find('#employeeID').val(),
                                    'opinion': $(authorInfo).find('#opinionID').val(),
                                    'note': $(authorInfo).find('#noteID').val(),
                                };
                                $.ajax({
                                    type: 'POST',
                                    url: '<%=contextPath %>/ProcessTemplate/signProcessTemplate.koala',
                                    contentType: "application/json; charset=utf-8",
                                    data: JSON.stringify(data),
                                    dataType: 'json',
                                    success: function (data) {
                                        debugger
                                        success = data['success'];
                                        if (success) {
                                            $.get('${pageContext.request.contextPath}/ProcessTemplate/updateAllowState/' + id + '.koala').done(function () {
                                                e.data.grid.data('koala.grid').refresh()
                                            });
                                            dialog.modal('hide');
                                            e.data.grid.data('koala.grid').refresh();
                                            e.data.grid.message({
                                                type: 'success',
                                                content: '保存成功'
                                            });
                                        } else {
                                            e.data.grid.data('koala.grid').refresh();
                                            e.data.grid.message({
                                                type: 'error',
                                                content: '签核失败'
                                            });
                                        }
                                    },

                                    error: function (data) {
                                        e.data.grid.data('koala.grid').refresh();
                                        e.data.grid.message({
                                            type: 'error',
                                            content: '签核失败'
                                        });
                                    }
                                })
                            });

                        })
                    });

                },
                initPage: function (form) {
                    var selectItems = {};//测试类型
                    var contents = [{title: '请选择', value: ''}];
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/TestType.koala',
                        type: 'GET',
                        dataType: 'json',
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contents.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                    });
                    selectItems['testTypeID'] = contents;//机械手类型
                    var contents = [{title: '请选择', value: ''}];
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/HandlerType.koala',
                        type: 'GET',
                        dataType: 'json',
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contents.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                    });
                    selectItems['handlerTypeID'] = contents;
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
                    var testFlag = {};
                    var count = {};
                    var list1 = form.find('#list1');
                    var list2 = form.find('#list2');
                    var data = {'page': 0, 'pagesize': 20};
                    $.post('${pageContext.request.contextPath}/TestingTemplate/pageJson.koala', data).done(function (msg) {//test节点
                        for (var i = 0; i < msg.data.length; i++) {
                            var oOption = $("<option title=" + msg.data[i].content + " id=" + msg.data[i].id + " value='Test-" + msg.data[i].name + "'>" + msg.data[i].name + "</option>");
                            list1.append(oOption);
                            testFlag[msg.data[i].name] = 1;
                        }
                    });
                    $.get('${pageContext.request.contextPath}/SystemDictionary/getByType/ProcessNode.koala').done(function (data) {//普通节点
                        for (var i = 0; i < data.length; i++) {
                            count[data[i].value] = 0;//普通节点设置count对象，方便计数
                            var oOption = $("<option value=" + data[i].label + ">" + data[i].value + "</option>");
                            list1.append(oOption);
                        }
                    });
                    $.get('${pageContext.request.contextPath}/SystemDictionary/getByType/CPProcessNode.koala').done(function (data) {
                        for (var i = 0; i < data.length; i++) {
                        	count[data[i].value] = 0;
                        	var oOption = $("<option value=" + data[i].label + ">" + data[i].value + "</option>");
                            list1.append(oOption);
                        }
                    });
                    form.find('#add').on('click', function () {//右移
                        var index = 0;
                        var arrSelValue = [];
                        var arrSelText = [];
                        $.each(list1.find('option'), function () {//遍历左侧框的数据列
                            self = this;
                            if (self.selected) {//如果有选中
                                var optionFlag = self.hasAttribute("title");//判断是否有title属性，有的话表示是test节点
                                if (!optionFlag && (list2.find('option').length)) {//如果是普通节点
                                    for (var i = 0; i < list2.find('option').length; i++) {//遍历右侧框数据列，主要是为了修改操作
                                        if (list2.find('option')[i].value.match(self.value)) {//和当前新加入的值一致
                                            count[self.value] = list2.find('option')[i].value.replace(/[^0-9]/ig, '');//取出当前的序号，放入count中
                                            if (count[self.value] == '') {//如果是空，表示右侧只有该流程节点的一次
                                                count[self.value] = count[self.value] + 1;//count中显示1
                                            }
                                            count[self.value] = parseInt(count[self.value]);
                                        }
                                    }
                                } else if (optionFlag && (list2.find('option').length)) {//如果加入节点是test节点，且右侧有数据，则判断是否test站点已经加过，这里test站点可能有多种
                                    for (var i = 0; i < list2.find('option').length; i++) {
                                        if (list2.find('option')[i].value == self.value) {
                                            testFlag[self.text] = 0;//有的话标志位置0
                                        }
                                    }
                                }
                                if (optionFlag && testFlag[self.text]) {//如果选中有test,且标志位为1，表示右侧没有加入该节点
                                    optionIds.push({title: self.text, id: self.getAttribute("id")});
                                    arrSelValue[index] = self.value;//这里test站点显示的值和传输的值是不一样的所以要设置value，和text
                                    arrSelText[index++] = self.text;//加入该节点
                                    testFlag[self.text] = 0;//此时标志位置0
                                }
                                for (var j in count) {//普通节点加入，依据count节点一次递增
                                    if (self.value == j) {
                                        if (count[j] == 0) {
                                            arrSelValue[index] = self.value;
                                            arrSelText[index++] = self.text;
                                            count[j]++;
                                        } else if (count[j] == 1) {
                                            list2.find('option').each(function () {
                                                var that = this;
                                                if (that.value == j) {
                                                    that.value = self.value + '1';
                                                    that.text = self.text + '1';
                                                    arrSelValue[index] = self.value + '2';
                                                    arrSelText[index++] = self.text + '2';
                                                    count[j]++;
                                                }
                                            })
                                        } else {
                                            arrSelValue[index] = self.value + (++count[j]);
                                            arrSelText[index++] = self.text + count[j];
                                        }
                                    }
                                }
                            }
                        });
                        for (var i = 0; i < index; i++) {//一次性将左侧的加入右侧，这样的目的是一次可以将多组数据加到右侧
                            var oOption = $("<option value=" + arrSelValue[i] + ">" + arrSelText[i] + "</option>");
                            list2.append(oOption);
                        }
                    });
                    form.find('#delete').on('click', function () {//左移/删除，关键是判断是删除的是test 节点还是普通节点，此处通过option标签的text 是否等于value
                        $.each(list2.find('option'), function (i) {//遍历
                            self = this;
                            if (self.selected) {//选中
                                this.remove(i);//删除
                                if (self.value != self.text) {//如果value不等于text,表示删除的test
                                    optionIds.splice(optionIds.indexOf({title: self.text, id: /[^0-9]/ig}), 1);//删除已加进optionIds中的数据
                                    testFlag[self.text] = 1;//将test设为可右移
                                }
                                for (var j in count) {
                                    var removeItem = self.value.match(j);
                                    if(removeItem==null)
                                    continue;
                                    if (removeItem['input'].replace(/\d+/g,"") == j) {
                                        count[j]--;
                                        var num = 0;
                                        var lastIndex = 0;
                                        for (var k = 0; k < list2.find('option').length; k++) {
                                        	if(list2.find('option')[k].value.match(j)==null)
                                        	continue;
                                            if (list2.find('option')[k].value.match(j)['input'].replace(/\d+/g,"") == j) {
                                                num++;
                                                $(list2.find('option')[k]).html(j + num);
                                                list2.find('option')[k].value = j + num;
                                                lastIndex = k;
                                            }
                                        }
                                        if (num == 1) {
                                            $(list2.find('option')[lastIndex]).html(j);
                                            list2.find('option')[lastIndex].value = j;
                                        }
                                        if (num == 0) {
                                            count[j] = 0;
                                        }
                                    }
                                }
                            }
                        });
                    });
                    form.find('#up').on('click', function () {
                        $.each(list2.find('option'), function (i) {
                            self = this;
                            if (self.selected) {
                                if (i == 0) {
                                    alert('oh~撞到天花板了哦！');
                                    return false;
                                }
                                var upValue = self.value;
                                var upText = self.text;
                                self.value = list2.find('option')[i - 1].value;
                                self.text = list2.find('option')[i - 1].text;
                                list2.find('option')[i - 1].value = upValue;
                                list2.find('option')[i - 1].text = upText;
                                self.selected = false;
                                list2.find('option')[i - 1].selected = true;
                                return false;
                            }
                        });
                    });
                    form.find('#down').on('click', function () {
                        $.each(list2.find('option'), function (i) {
                            self = this;
                            if (self.selected) {
                                if (i == (list2.find('option').length - 1)) {
                                    alert('oh~撞到地板了哦！');
                                    return false;
                                }
                                var downValue = self.value;
                                var downText = self.text;
                                self.value = list2.find('option')[i + 1].value;
                                self.text = list2.find('option')[i + 1].text;
                                list2.find('option')[i + 1].value = downValue;
                                list2.find('option')[i + 1].text = downText;
                                self.selected = false;
                                list2.find('option')[i + 1].selected = true;
                                return false;
                            }
                        });
                    });
                },
                remove: function (ids, grid) {
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/ProcessTemplate/delete.koala', data).done(function (result) {
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
                checkRuncard: function (id, grid) {
                    $.get('${pageContext.request.contextPath}/ProcessTemplate/checkRuncard/' + id + '.koala').done(function (result) {
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
                    //window.location = '${pageContext.request.contextPath}/ProcessTemplate/checkRuncard.koala';
                },
                assembly: function (grid) {
                    var self = this;
                    var aString = '';
                    var count = {};
                    var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">组合节点</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/ProcessTemplate-assembly.jsp').done(function (html) {
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        }).find('.modal-body').html(html);
                        var list1 = $('<select style="WIDTH:100%"  multiple name="list1" size="5"></select>');
                        var list2 = $('<select style="WIDTH:100%" multiple name="list2" size="5"></select>');
                        $.get('${pageContext.request.contextPath}/SystemDictionary/getByType/TestingNode.koala').done(function (data) {
                            for (var i = 0; i < data.length; i++) {
                                count[data[i].value] = 0;
                                var oOption = $("<option value=" + data[i].label + ">" + data[i].value + "</option>");
                                list1.append(oOption);
                            }
                        });
                        dialog.find('#list1').html(list1);
                        dialog.find('#list2').html(list2);

                        dialog.find('#add').on('click', function () {
                            var index = 0;
                            var arrSelValue = [];
                            var arrSelText = [];
                            $.each(list1.find('option'), function () {
                                self = this;
                                if (self.selected) {
                                    for (var j in count) {
                                        if (self.value == j) {
                                            if (count[j] == 0) {
                                                arrSelValue[index] = self.value;
                                                arrSelText[index++] = self.text;
                                                count[j]++;
                                            } else if (count[j] == 1) {
                                                list2.find('option').each(function () {
                                                    var that = this;
                                                    if (that.value == j) {
                                                        that.value = self.value + '1';
                                                        that.text = self.text + '1';
                                                        arrSelValue[index] = self.value + '2';
                                                        arrSelText[index++] = self.text + '2';
                                                        count[j]++;
                                                    }
                                                })
                                            } else {
                                                arrSelValue[index] = self.value + (++count[j]);
                                                arrSelText[index++] = self.text + count[j];
                                            }
                                        }
                                    }
                                }
                            });
                            for (var i = 0; i < index; i++) {
                                var oOption = $("<option value=" + arrSelValue[i] + ">" + arrSelText[i] + "</option>");
                                list2.append(oOption);
                            }
                        });
                        dialog.find('#delete').on('click', function () {
                            $.each(list2.find('option'), function (i) {
                                self = this;
                                if (self.selected) {
                                    this.remove(i);
                                    for (var j in count) {
                                        var removeItem = self.value.match(j);
                                        if (removeItem == j) {
                                            count[j]--;
                                            var num = 0;
                                            var lastIndex = 0;
                                            for (var k = 0; k < list2.find('option').length; k++) {
                                                if (list2.find('option')[k].value.match(j) == j) {
                                                    num++;
                                                    $(list2.find('option')[k]).html(j + num);
                                                    list2.find('option')[k].value = j + num;
                                                    lastIndex = k;
                                                }
                                                ;
                                            }
                                            ;
                                            if (num == 1) {
                                                $(list2.find('option')[lastIndex]).html(j);
                                                list2.find('option')[lastIndex].value = j;
                                            }
                                            ;
                                        }
                                    }
                                }
                            });
                        });
                        dialog.find('#up').on('click', function () {
                            $.each(list2.find('option'), function (i) {
                                self = this;
                                if (self.selected) {
                                    if (i == 0) {
                                        alert('oh~撞到天花板了哦！');
                                        return false;
                                    }
                                    var upValue = self.value;
                                    var upText = self.text;
                                    self.value = list2.find('option')[i - 1].value;
                                    self.text = list2.find('option')[i - 1].text;
                                    list2.find('option')[i - 1].value = upValue;
                                    list2.find('option')[i - 1].text = upText;
                                    self.selected = false;
                                    list2.find('option')[i - 1].selected = true;
                                    return false;
                                }
                            });
                        });
                        dialog.find('#down').on('click', function () {
                            $.each(list2.find('option'), function (i) {
                                self = this;
                                if (self.selected) {
                                    if (i == (list2.find('option').length - 1)) {
                                        alert('oh~撞到地板了哦！');
                                        return false;
                                    }
                                    var downValue = self.value;
                                    var downText = self.text;
                                    self.value = list2.find('option')[i + 1].value;
                                    self.text = list2.find('option')[i + 1].text;
                                    list2.find('option')[i + 1].value = downValue;
                                    list2.find('option')[i + 1].text = downText;
                                    self.selected = false;
                                    list2.find('option')[i + 1].selected = true;
                                    return false;
                                }
                            });
                        });
                        dialog.find('#save').on('click', {grid: grid}, function (e) {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            $.each(dialog.find('#list2').find('option'), function () {
                                aString += this.value + '|';
                            });
                            aString = aString.substring(0, aString.length - 1);
                            var data = dialog.find('form').serialize() + '&content=' + aString;
                            $.post('${pageContext.request.contextPath}/TestingTemplate/add.koala', data).done(function (result) {
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
                    });
                },

                specialForm: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">特殊表单关联</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/ProcessTemplate-specialForm.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        $.get('${pageContext.request.contextPath}/SpecialForm/get/' + id + '.koala').done(function (json) {
                            json = json.data;
                            var elm;
                            for (var index in json) {
                                elm = dialog.find('#' + index + 'ID');
                                if (elm.hasClass('select')) {
                                    elm.setValue(json[index]);
                                } else {
                                    elm.val(json[index]);
                                }
                                if (json[index] == true) {
                                    elm.prop('checked', true);
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
                        dialog.find('#save').on('click', {grid: grid}, function (e) {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            $.post('${pageContext.request.contextPath}/SpecialForm/update.koala?id=' + id, dialog.find('form').serialize()).done(function (result) {
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
                }
            }
            PageLoader.initSearchPanel();
            PageLoader.initGridPanel();
            $('#search').on('click', function () {
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

        var openProcessTemplateDetailsPage = function (id) {
            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
            $.get('<%=path%>/ProcessTemplate-view.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);
                $.get('${pageContext.request.contextPath}/ProcessTemplate/get/' + id + '.koala').done(function (json) {
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
                        if ((index == 'content') && (json.testingTemplateDTO != null)) {
                            var str = json[index];
                            str = str.replace('Test', json['testingTemplateDTO']['content'])
                            dialog.find('#contentID').html(str + "");
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
        var processAssembly = function (index, i) {
            var content;
            var data = {page: 0, pagesize: 20};
            $.post("${pageContext.request.contextPath}/ProcessTemplate/pageJson.koala", data).done(function (msg) {
                //这个地方用get就可以了吧？ Howard
                //看来由于无法从grid中获取id值，只能重新获取 Howard
                var data = msg['data'][index];
                var strData = data.content;
                strData = strData.split('|');
                content = strData[i];
                //获取Runcard注意事项具体内容
                $.ajax({
                    async: false,
                    url: "${pageContext.request.contextPath}/ProcessTemplate/findRuncardNoteByProcessTemplate/" + data.id + ".koala",
                    type: 'GET',
                    dataType: 'json'
                }).done(function (result) {
                    if (result.success)
                        runcardNote = result['data'];
                    else
                        alert("获取RuncardNote失败！");
                });

                var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px">'
                        + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                        + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                        + '<h4 class="modal-title">' + content + '</h4></div><div class="modal-body">'
                        + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                        + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                        + '<button type="button" class="btn btn-success" id= "save">保存</button></div></div>'
                        + '</div></div>');

                $.get('<%=path%>/ProcessTemplate-buttonFuc.jsp').done(function (html) {
                    dialog.modal({
                        keyboard: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        }
                    }).find('.modal-body').html(html);
                    //流程细则展示
                    var contentArray = ['FQC','FVI','OQC'];//为FQC，fvi,oqc
                    if(content.startsWith('Test-')){
                        var testContent = data.testingTemplateDTOs
                                .map(function(testDTO){
                                    if(testDTO.name == content.substr(5,content.length)) {
                                        return testDTO.content;
                                    }
                                })
                                .filter(function(i){
                                    if(i != undefined){
                                        return i;
                                    }
                                })[0];
                          var p1 = new Promise(function(resolve,reject){
                                    dialog.find('iframe').attr('src', "<%=path%>/FT_TestDisplay.jsp");
                                    dialog.find('iframe')[0].onload =function(){
                                        resolve(dialog.find('iframe')[0]);
                                    }

                           });
                            p1.then(function(objectLi){
                                var oLi = '';
                            var testContentArray = testContent.split('|');
                            testContentArray.forEach(function(index){
                            oLi +='<li><a data-toggle="tab" href="#' + index.toLowerCase() + '">' + index + '</a></li>';
                           });
                           objectLi.contentDocument.getElementById('connect').innerHTML = oLi;
                    });
              /*          dialog.find('iframe')[0].src ="<%=path%>/FT_TestDisplay.jsp";
                        dialog.find('iframe')[0].onload = function(){
                            alert("Local iframe is now loaded.");
                        }
*/
                     //   if((testContent.indexOf('FT') >= 0)&&(testContent.indexOf('RT') >= 0)){

                     //   }else{

                     //   }
                    }else if(contentArray.indexOf(content) != -1){
                        dialog.find('iframe').attr('src', "<%=path%>/FT_PassNodeDisplay.jsp");
                    }else{
                    	if(content.replace(/\d+/g,"")=="CP")return;
                        dialog.find('iframe').attr('src', "<%=path%>/FT_"+ content +"Display.jsp");
                    }
                    //显示对应的注意事项内容。content是否可以保持一致需要考虑
                    if (runcardNote[content] != null) {
                        var note = runcardNote[content]['nodeNote']
                        var txt = "";
                        for (var x in note) {
                            txt += note[x] + '\n';
                        }
                        var textarea = dialog.find('#nodeNote');
                        textarea.attr('disabled', false);
                        textarea.html(txt);
                    }

                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        var nodeNoteArray = new Array();
                        nodeNoteArray = dialog.find('#nodeNote').val().split('\n');
                        if (nodeNoteArray[nodeNoteArray.length - 1] == "") {
                            nodeNoteArray.pop();
                        }
                        data = runcardNote[content];
                        data['nodeNote'] = nodeNoteArray;
                        data = [{name: 'json', value: JSON.stringify(data)}];
                        $.post('${pageContext.request.contextPath}/RuncardNote/updateJson.koala', data).done(function (result) {
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
                        <label class="control-label" style="width:110px;float:left;">Process名称:&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <input name="name" class="form-control" type="text" id="nameID"/>
                        </div>
                        <label class="control-label" style="width:100px;float:left;">机械手类型:&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <div class="btn-group select" id="handlerTypeID"></div>
                            <input type="hidden" id="handlerTypeID_" name="handlerType"/>
                        </div>
                    </div>
                </td>
                <td style="vertical-align: bottom;">
                    <button id="search" type="button" style="position:relative; margin-left:135px; top: -15px"
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
