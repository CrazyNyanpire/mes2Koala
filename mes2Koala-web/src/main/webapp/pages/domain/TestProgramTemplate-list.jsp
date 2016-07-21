<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <%@include file="/commons/taglibs.jsp" %>
    <%@ page import="java.util.Date" %>
    <% String formId = "form_" + new Date().getTime();
        String gridId = "grid_" + new Date().getTime();
        String path = request.getContextPath() + request.getServletPath().substring(0, request.getServletPath().lastIndexOf("/") + 1);
    %>
    <script src="<%=contextPath %>/lib/koala-tree.js"></script>
    <script type="text/javascript">
        var grid;
        var form;
        var _dialog;
        var departmentId = [];
        var departmentName = '';
        var count = 0;
        var authorizationMember = null;
        var personHtml = '';
        var personId = [];
        var memberTree = {};
        $(function () {
            grid = $("#<%=gridId%>");
            form = $("#<%=formId%>");
            var contentsTestType = [];
            PageLoader = {
                initSearchPanel: function () {
                    contentsTestType = [{title: '请选择', value: ''}];//测试类型
                    $.ajax({
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/TestType.koala',
                        type: 'GET',
                        dataType: 'json'
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contentsTestType.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                        form.find('#testTypeSearchID').select({
                            title: '请选择',
                            contents: contentsTestType
                        }).on('change', function () {
                            form.find('#testTypeSearchID_').val($(this).getValue());
                            if (form.find('#testTypeSearchID_').val() == 'CP') {
                                form.find('#packageTypeID').setValue('');
                                form.find('#packageTypeID').find('button').attr('disabled', true);
                            } else {
                                form.find('#packageTypeID').find('button').attr('disabled', false);

                            }
                        });
                    });
                    var contentsPackageType = [{title: '请选择', value: ''}];//封装类型
                    $.ajax({
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/PackageType.koala',
                        type: 'GET',
                        dataType: 'json'
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contentsPackageType.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                        form.find('#packageTypeID').select({
                            title: '请选择',
                            contents: contentsPackageType
                        }).on('change', function () {
                            form.find('#packageTypeID_').val($(this).getValue());
                        });
                    });
                },
                initGridPanel: function () {
                    var self = this;
                    var width = 130;
                    return grid.grid({
                        identity: "id",
                        buttons: [
                            {
                                content: '<ks:hasSecurityResource identifier="testProgramTemplateAdd"><button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button></ks:hasSecurityResource>',
                                action: 'add'
                            },
                            {
                                content: '<ks:hasSecurityResource identifier="testProgramTemplateUpdate"><button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button></ks:hasSecurityResource>',
                                action: 'modify'
                            },
                            {
                                content: '<ks:hasSecurityResource identifier="testProgramTemplateDelete"><button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button></ks:hasSecurityResource>',
                                action: 'delete'
                            },
                            {
                                content: '<ks:hasSecurityResource identifier="testProgramTemplateRealityUPH"><button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-star"><span>维护实际UPH</button></ks:hasSecurityResource>',
                                action: 'maintainUPHReal'
                            },
                            {
                                content: '<ks:hasSecurityResource identifier="testProgramTemplateAuthorize"><button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-star"><span>授权</button></ks:hasSecurityResource>',
                                action: 'authorize'
                            }
                        ],
                        url: "${pageContext.request.contextPath}/TestProgramTemplate/pageJson.koala",
                        columns: [
                            {
                                title: 'Customer_NO',
                                name: 'customerNumber',
                                width: width
                            },
                            {
                                title: '客户产品型号',
                                name: 'customerProductNumber',
                                width: width
                            },
                            {title: 'version', name: 'productVersion', width: width},
                            {title: '测试程序', name: 'name', width: width},
                            {title: '程序版本', name: 'revision', width: 90},
                            {title: '测试机台', name: 'testSys', width: width},
                            {
                                title: '授权人',
                                name: 'authorizationEmployeeNames',
                                width: 90
                            },
                            {title: '是否授权', name: 'allowState', width: 90},
                            {
                                title: '授权时间',
                                name: 'authorizationDatetime',
                                width: width
                            },
                            {
                                title: 'IS_YELLOW',
                                name: 'isYellow',
                                width: 120,
                                render: function (rowdata, name, index) {
                                    return rowdata.yellow ? "<span style='color:#47a447' class='glyphicon glyphicon-ok'></span>" : "<span style='color:#d2322d' class='glyphicon glyphicon-remove'></span>";
                                }
                            },
                            {title: '理论UPH', name: 'uphtheory', width: width},
                            {title: '实际UPH', name: 'uphreality', width: width},
                            {title: '站点', name: 'site', width: width},
                            {title: '备注', name: 'note', width: width},
                            {
                                title: '操作', width: 120, render: function (rowdata, name, index) {
                                var param = '"' + rowdata.id + '"';
                                var h = "<a href='javascript:openTestProgramTemplateDetailsPage(" + param + ")'>查看</a> ";
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
                        'maintainUPHReal': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行维护'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行维护'
                                })
                                return;
                            }
                            self.maintainUPHReal(indexs[0], $this);
                        },
                        authorize: function (event, data) {
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
                        }
                    });
                },
                add: function (grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">新增测试程序</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/TestProgramTemplate-add.jsp').done(function (html) {
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        }).find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        dialog.find('#internalProductDTOID').on('change', function () {
                            //根据客户产品型号得到站点信息
                            var internalProductId = $(this).getValue();
                            var selectItems = {};
                            var contents = [{title: '请选择', value: ''}];
                            if (internalProductId == "")return;
                            if ($("#testTypeID").getValue() == "FT") {
                                $.ajax({
                                    url: '${pageContext.request.contextPath}/FTInfo/get/' + internalProductId + '.koala',
                                    type: 'POST',
                                    dataType: 'json'
                                }).done(function (msg) {
                                    dialog.find("#productVersionID").val(msg['data']['customerProductRevision']);
                                });
                                $.ajax({
                                    url: '${pageContext.request.contextPath}/FTProcess/findComposedTestNodeNamesByProductId/' + internalProductId + '.koala',
                                    type: 'POST',
                                    dataType: 'json'
                                }).done(function (msg) {
                                    for (var i = 0, len = msg.length; i < len; i++) {
                                        contents.push({title: msg[i], value: msg[i]});
                                    }
                                    selectItems['siteID'] = contents;
                                    dialog.find('#siteID').each(function () {
                                        var select = $(this);
                                        select.select({
                                            title: '请选择',
                                            contents: selectItems['siteID']
                                        }).on('change', function () {
                                            dialog.find('#siteID_').val($(this).getValue());
                                        })
                                    })
                                });
                            } else if ($("#testTypeID").getValue() == "CP") {
                                $.ajax({
                                    url: '${pageContext.request.contextPath}/CPInfo/get/' + internalProductId + '.koala',
                                    type: 'POST',
                                    dataType: 'json'
                                }).done(function (msg) {
                                    dialog.find("#productVersionID").val(msg['data']['customerProductRevision']);
                                });
                                $.ajax({
                                    url: '${pageContext.request.contextPath}/CPInfo/findProcessTemplateByInternalProductId/' + internalProductId + '.koala',
                                    type: 'POST',
                                    dataType: 'json'
                                }).done(function (msg) {
                                    msg = msg['data']['content'].split("|").filter(function (a) {
                                        return a.substring(0, 2) == "CP";
                                    });
                                    for (var i = 0, len = msg.length; i < len; i++) {
                                        contents.push({
                                            title: msg[i],
                                            value: msg[i]
                                        });
                                    }
                                    selectItems['siteID'] = contents;
                                    dialog.find('#siteID').each(function () {
                                        var select = $(this);
                                        select.select({
                                            title: '请选择',
                                            contents: selectItems['siteID']
                                        }).on('change', function () {
                                            dialog.find('#siteID_').val($(this).getValue());
                                        })
                                    })
                                });
                            }
                        });
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        console.log(dialog.find('form').serialize());
                        debugger
                        $.post('${pageContext.request.contextPath}/TestProgramTemplate/add.koala', dialog.find('form').serialize()).done(function (result) {
                            if (result.success) {
                                departmentId = [];
                                departmentName = '';
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
                },
                //TODO维护实际UPH
                maintainUPHReal: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content">' +
                            '<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;' +
                            '</button><h4 class="modal-title">维护实际UPH</h4></div><div class="modal-body">' +
                            '</div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>' +
                            '<button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    dialog.find('.modal-body').append('<div class="form-horizontal"><div class="form-group"> <label class="col-lg-3 control-label">实际UPH:</label> <div class="col-lg-6"> <input name="UPHReality" style="display:inline; width:94%;" class="form-control" dataType="Require" type="text"  id="uphrealityID" /> <span class="required">*</span> </div> </div></div>');
                    self.initPage(dialog.find('form'));
                    $.get('${pageContext.request.contextPath}/TestProgramTemplate/getUPHReal/' + id + '.koala').done(function (json) {
                        dialog.find("#uphrealityID").val(json);
                    });
                    dialog.modal({
                        keyboard: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        }
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
//                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        var uph = parseFloat(dialog.find("#uphrealityID").val());

//                        debugger;
                        $.post('${pageContext.request.contextPath}/TestProgramTemplate/setUPHReal.koala?id=' + id + '&uph=' + uph).done(function (result) {
//                            debugger
                            if (result.success) {
                                departmentId = [];
                                departmentName = '';
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
                modify: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改测试程序</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/TestProgramTemplate-update.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/TestProgramTemplate/get/' + id + '.koala').done(function (json) {
                            personHtml = '';
                            personId = [];
                            json = json.data;
                            var countChange = 0;//nternalProductDTO,changez执行次数标记，第一次只执行赋值，后面执行关联
                            var selectItems = {};
                            var contents = [{title: '请选择', value: ''}];
                            if (json['internalProductDTO']['testType'] == "CP") {
                                $.ajax({
                                    async: false,
                                    url: '${pageContext.request.contextPath}/InternalProduct/findAllInternalProduct/CP.koala',
                                    type: 'POST',
                                    dataType: 'json'
                                }).done(function (msg) {
                                    for (var i = 0; i < msg.length; i++) {
                                        contents.push({
                                            title: msg[i]['customerProductNumber'],
                                            value: msg[i]['id']
                                        });
                                    }
                                    selectItems['internalProductDTOID'] = contents;
                                    dialog.find('#internalProductDTOID').each(function () {
                                        var select = $(this);
                                        var idAttr = select.attr('id');
                                        select.select({
                                            title: '请选择',
                                            contents: selectItems['internalProductDTOID']
                                        }).on('change', function () {
                                            form.find('#internalProductDTOID_').val($(this).getValue());
                                        })
                                    });
                                });
                            } else if (json['internalProductDTO']['testType'] == "FT") {
                                $.ajax({
                                    async: false,
                                    url: '${pageContext.request.contextPath}/InternalProduct/findAllInternalProduct/FT.koala',
                                    type: 'POST',
                                    dataType: 'json'
                                }).done(function (msg) {
                                    for (var i = 0; i < msg.length; i++) {
                                        contents.push({
                                            title: msg[i]['customerProductNumber'],
                                            value: msg[i]['id']
                                        });
                                    }
                                    selectItems['internalProductDTOID'] = contents;
                                    dialog.find('#internalProductDTOID').each(function () {
                                        var select = $(this);
                                        var idAttr = select.attr('id');
                                        select.select({
                                            title: '请选择',
                                            contents: selectItems['internalProductDTOID']
                                        }).on('change', function () {
                                            form.find('#internalProductDTOID_').val($(this).getValue());
                                        })
                                    });
                                });
                            }
                            var contents = [{title: '请选择', value: ''}];
                            if (json['internalProductDTO']['testType'] == "FT") {
                                $.ajax({
                                    async: false,
                                    url: '${pageContext.request.contextPath}/FTProcess/findComposedTestNodeNamesByProductId/' + json['internalProductDTO']['id'] + '.koala',
                                    type: 'POST',
                                    dataType: 'json'
                                }).done(function (msg) {
                                    var contents = [{title: '请选择', value: ''}];
                                    for (var i = 0, len = msg.length; i < len; i++) {
                                        contents.push({
                                            title: msg[i],
                                            value: msg[i]
                                        });
                                    }
                                    selectItems['siteID'] = contents;
                                    dialog.find('#siteID').each(function () {
                                        var select = $(this);
                                        select.select({
                                            title: '请选择',
                                            contents: selectItems['siteID']
                                        }).on('change', function () {
                                            dialog.find('#siteID_').val($(this).getValue());
                                        })
                                    })
                                    //internalProductDTO与site关联
                                });
                            } else if (json['internalProductDTO']['testType'] == "CP") {
                                $.ajax({
                                    async: false,
                                    url: '${pageContext.request.contextPath}/CPInfo/findProcessTemplateByInternalProductId/' + json['internalProductDTO']['id'] + '.koala',
                                    type: 'POST',
                                    dataType: 'json'
                                }).done(function (msg) {
                                    msg = msg['data']['content'].split("|").filter(function (a) {
                                        return a.substring(0, 2) == "CP"
                                    });
                                    for (var i = 0, len = msg.length; i < len; i++) {
                                        contents.push({
                                            title: msg[i],
                                            value: msg[i]
                                        });
                                    }
                                    selectItems['siteID'] = contents;
                                    dialog.find('#siteID').each(function () {
                                        var select = $(this);
                                        select.select({
                                            title: '请选择',
                                            contents: selectItems['siteID']
                                        }).on('change', function () {
                                            dialog.find('#siteID_').val($(this).getValue());
                                        })
                                    })
                                });
                            }
                            var elm;
                            for (var index in json) {
                                elm = dialog.find('#' + index + 'ID');
                                if (elm.hasClass('select')) {
                                    if ((index == 'internalProductDTO') && (json[index] != null)) {
                                        dialog.find("#testTypeID").setValue(json[index]['testType']);
                                        elm.setValue(json[index]['id']);
                                        dialog.find("#siteID").setValue(json[index]['site']);
                                    } else {
                                        elm.setValue(json[index]);
                                    }
                                } else {
                                    elm.val(json[index]);
                                }
                            }
                            $.each(json['acetecAuthorizationDTOs'], function () {
                                if (this['employeeDTO'] != null) {
                                    personHtml += this.employeeDTO.name + ',';
                                    personId.push(this.employeeDTO.id);
                                }
                            })
                            if (personHtml == '') {
                                dialog.find('#acetecAuthorizationIdsID').html('选择授权人');
                            } else {
                                personHtml = personHtml.substring(0, personHtml.length - 1);
                                //  dialog.find('#acetecAuthorizationIdsID').find('[data-toggle="item"]').html(personHtml);
                                dialog.find('#acetecAuthorizationIdsID').html(personHtml);
                                dialog.find('#acetecAuthorizationID').val(personId);
                            }
                            dialog.find('#internalProductDTOID').on('change', function () {
                                countChange++;
                                if (countChange === 1)//第一次发生change时不执行下面函数
                                    return;
                                var selectItems = {};
                                var contents = [{
                                    title: '请选择',
                                    value: ''
                                }];
                                //根据客户产品型号得到站点信息
                                var internalProductId = $(this).getValue();
                                if (internalProductId == "")return;
                                if ($("#testTypeID").getValue() == "FT") {
                                    $.ajax({
                                        url: '${pageContext.request.contextPath}/FTProcess/findComposedTestNodeNamesByProductId/' + internalProductId + '.koala',
                                        type: 'POST',
                                        dataType: 'json'
                                    }).done(function (msg) {
                                        for (var i = 0, len = msg.length; i < len; i++) {
                                            contents.push({
                                                title: msg[i],
                                                value: msg[i]
                                            });
                                        }
                                        selectItems['siteID'] = contents;
                                        $('#siteID').each(function () {
                                            var select = $(this);
                                            select.select({
                                                title: '请选择',
                                                contents: selectItems['siteID']
                                            }).on('change', function () {
                                                dialog.find('#siteID_').val($(this).getValue());
                                            })
                                        })
                                    });
                                }
                                else if ($("#testTypeID").getValue() == "CP") {
                                    $.ajax({
                                        url: '${pageContext.request.contextPath}/CPInfo/findProcessTemplateByInternalProductId/' + internalProductId + '.koala',
                                        type: 'POST',
                                        dataType: 'json'
                                    }).done(function (msg) {
                                        msg = msg['data']['content'].split("|").filter(function (a) {
                                            return a.substring(0, 2) == "CP"
                                        });
                                        for (var i = 0, len = msg.length; i < len; i++) {
                                            contents.push({
                                                title: msg[i],
                                                value: msg[i]
                                            });
                                        }
                                        selectItems['siteID'] = contents;
                                        $('#siteID').each(function () {
                                            var select = $(this);
                                            select.select({
                                                title: '请选择',
                                                contents: selectItems['siteID']
                                            }).on('change', function () {
                                                dialog.find('#siteID_').val($(this).getValue());
                                            })
                                        })
                                    });
                                }
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
                            $.post('${pageContext.request.contextPath}/TestProgramTemplate/update.koala?id=' + id, dialog.find('form').serialize()).done(function (result) {
                                if (result.success) {
                                    departmentId = [];
                                    departmentName = '';
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
                authorize: function (id, grid) {
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">授权</h4></div><div class="modal-body"></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');

                    $.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function (json) {
                        employeeName = json['data']['name'];
                        userId = json['data']['id'];
                        //模拟签核人员
                        $.get('${pageContext.request.contextPath}/TestProgramTemplate/checkTheAuthorizer.koala?testProgramTemplateId=' + id +
                                '&userId=' + userId).done(function (json) {
                            var success = json['success'];
                            if (!success) {
                                grid.message({
                                    type: 'error',
                                    content: '您没有签核权限'
                                })
                                return;
                            }

                            $.get('${pageContext.request.contextPath}/TestProgramTemplate/get/' + id + '.koala').done(function (json) {
                                $.each(json['data']['acetecAuthorizationDTOs'], function () {
                                    var body = $("<div class='form-group'><label class='col-lg-3 control-label'  class='style:display:inline'>" + this.employeeDTO.name + ':</label>'
                                            + "<input type='hidden' name='id' value=" + this.id + " /><input type='hidden' name='employeeId' value=" + this.employeeDTO.id + " /><select style='margin-left:30px;' name='opinion' id='opinionID'><option value=''>请选择</option><option value='同意'>同意</option><option value='不同意'>不同意</option></select><div style='margin-top:10px;margin-left:40px'><label class='col-lg-3 control-label'  class='style:display:inline；'>授权备注：</label><textarea name='note' id='noteID' cols='70' rows='5'></textarea></div></div>");
                                    self = this;
                                    var elm;
                                    for (var index in self) {
                                        elm = body.find('#' + index + 'ID');
                                        if (elm.hasClass('select')) {
                                            elm.setValue(self[index]);
                                        } else {
                                            elm.val(self[index]);
                                        }
                                    }
                                    dialog.find('.modal-body').append(body);
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
                                //if(!Validator.Validate(dialog.find('form')[0],3))return;
                                var authorInfo = dialog.find('.form-group');
                                var len = authorInfo.length;
                                $.each(authorInfo, function (i) {//发送数据给后台，每一条发送一次
                                    var data = {
                                        'id': $(this).find('input')[0].value,
                                        'employeeId': $(this).find('input')[1].value,
                                        'note': $(this).find('#noteID').val(),
                                        'opinion': $(this).find('#opinionID').val()
                                    };
                                    //$.post('${pageContext.request.contextPath}/TestProgramTemplate/authorize/' + id + '.koala', data).done(function (result) {
                                    $.post('${pageContext.request.contextPath}/AcetecAuthorization/update.koala', data).done(function (result) {
                                        console.log(result);
                                        if (result.error) {
                                            dialog.find('.modal-content').message({
                                                type: 'error',
                                                content: result.actionError
                                            });

                                        }
                                        if (i == (len - 1)) {
                                            if (result.success) {
                                                dialog.modal('hide');
                                                e.data.grid.data('koala.grid').refresh();
                                                e.data.grid.message({
                                                    type: 'success',
                                                    content: '保存成功'
                                                });
                                            }
                                        }
                                    });
                                })
                                e.data.grid.data('koala.grid').refresh();
                            });
                        })
                    });
                },
                initPage: function (form, id) {
                    var selectItems = {};
                    selectItems['isYellowID'] = [
                        {title: '请选择', value: ''},
                        {title: '是', value: 'true'},
                        {title: '否', value: 'false'}
                    ];
                    selectItems['testTypeID'] = contentsTestType;
                    var contentsTesterList = [{
                        title: '请选择',
                        value: ''
                    }];
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/TestProgramTemplate/getTesterList.koala',
                        type: 'POST',
                        dataType: 'json'
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contentsTesterList.push({
                                title: msg[i],
                                value: msg[i]
                            });
                        }
                        selectItems['testSysID'] = contentsTesterList;
                        /*                         form.find('#testSysID').select({
                         title: '请选择',
                         contents: contentsTesterList
                         }).on('change', function () {
                         form.find('#testSysID_').val($(this).getValue());
                         }); */
                    });
                    form.find('#testTypeID').on('change', function () {
                        var contents = [{
                            title: '请选择',
                            value: ''
                        }];
                        if ($(this).getValue() == "CP") {
                            $.ajax({
                                async: false,
                                url: '${pageContext.request.contextPath}/InternalProduct/findAllInternalProduct/CP.koala',
                                type: 'POST',
                                dataType: 'json'
                            }).done(function (msg) {
                            	form.find("#internalProduct_list").empty();
                            	var option="";
                                for (var i = 0; i < msg.length; i++) {
                                	option+="<option index='"+msg[i]['id']+"' value='"+msg[i]['customerDirectDTO']['code']+" | "+msg[i]['customerProductNumber']+" | "+msg[i]['internalProductNumber']+"'>";
                                    contents.push({
                                        title: msg[i]['customerDirectDTO']['code']+" | "+msg[i]['customerProductNumber']+" | "+msg[i]['internalProductNumber'],
                                        value: msg[i]['id']
                                    });
                                }
                                form.find("#internalProduct_list").append(option);
                                selectItems['internalProductDTOID'] = contents;
                                $('#internalProductDTOID').each(function () {
                                    var select = $(this);
                                    var idAttr = select.attr('id');
                                    select.select({
                                        title: '请选择',
                                        contents: selectItems[idAttr]
                                    }).on('change', function () {
                                        form.find('#' + idAttr + '_').val($(this).getValue());
                                    })
                                });
                            });
                        } else if ($(this).getValue() == "FT") {
                            $.ajax({
                                async: false,
                                url: '${pageContext.request.contextPath}/InternalProduct/findAllInternalProduct/FT.koala',
                                type: 'POST',
                                dataType: 'json'
                            }).done(function (msg) {
                            	form.find("#internalProduct_list").empty();
                            	var option="";
                                for (var i = 0; i < msg.length; i++) {
                                	option+="<option index='"+msg[i]['id']+"' value='"+msg[i]['customerDirectDTO']['code']+" | "+msg[i]['customerProductNumber']+" | "+msg[i]['internalProductNumber']+"'>";
                                	contents.push({
                                        title: msg[i]['customerDirectDTO']['code']+" | "+msg[i]['customerProductNumber']+" | "+msg[i]['internalProductNumber'],
                                        value: msg[i]['id']
                                    });
                                }
                                form.find("#internalProduct_list").append(option);
                                selectItems['internalProductDTOID'] = contents;
                                $('#internalProductDTOID').each(function () {
                                    var select = $(this);
                                    var idAttr = select.attr('id');
                                    select.select({
                                        title: '请选择',
                                        contents: selectItems[idAttr]
                                    }).on('change', function () {
                                        form.find('#' + idAttr + '_').val($(this).getValue());
                                    })
                                });
                            });
                        }
                    });
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
                        })
                    });
                    testSysButton = form.find('#testSysID_');
                    testSysButton.on("click", function () {
                        $.get('<%=path%>/TestSys-opt.jsp').done(function (html) {
                            var dialog = $(html);

                            dialog.modal({
                                keyboard: false,
                                backdrop: false
                            }).on({
                                'hidden.bs.modal': function () {
                                    $(this).remove();
                                },
                                'shown.bs.modal': function () {
                                    dialog.find("#testSysGridId").grid({
                                        identity: "id",
                                        url: "${pageContext.request.contextPath}/TestSys/pageJson.koala",
                                        columns: [
                                            {title: 'platformNumber', name: 'platformNumber', width: 180},
//                                            {title: 'testerNumber', name: 'testerNumber', width: 180},
//                                            {title: 'proberOrHandlerNumber', name: 'proberOrHandlerNumber', width: 180},
//                                            {title: 'state', name: 'state', width: 180},
                                        ]
                                    })
                                },
                            });

                            dialog.find("#confirm").on('click', function () {
                                debugger
                                var testSysOptRows = dialog.find('#testSysGridId').getGrid().selectedRows();
                                var testSysOpt = form.find('#testSysID').val();
                                if (testSysOpt != "") {
                                    testSysOpt += ',' + '\n';
                                }
                                testSysOpt += testSysOptRows.map(function (testSysOptRows) {
                                    return testSysOptRows.platformNumber;
                                }).join(',' + '\n');
                                dialog.modal('hide');
                                form.find('#testSysID').val(testSysOpt);
                            })
                            dialog.find('#search').on('click', function () {
                                if (!Validator.Validate(document.getElementById("<%=formId%>"), 3))return;
                                var params = {};
                                dialog.find('input').each(function () {
                                    var $this = $(this);
                                    var name = $this.attr('name');
                                    if (name) {
                                        params[name] = $this.val();
                                    }
                                });
                                dialog.find("#testSysGridId").getGrid().search(params);
                            });
                        })
                    })
                    authorizationMember = form.find('#acetecAuthorizationIdsID');
                    authorizationMember.on('click', function () {
                        $.get(contextPath + '/pages/domain/TestProgramTemplate-authorize.jsp').done(function (data) {
                            departmentId = [];
                            departmentName = '';
                            var memberTreeDialog = $(data);
                            memberTreeDialog.find('.modal-body').css({height: '325px'});
                            memberTree = memberTreeDialog.find('.tree');
                            loadmemberTree();
                            memberTreeDialog.find('#confirm').on('click', function () {
                                if ((departmentName == '')) {
                                    departmentId = personId;
                                    departmentName = personHtml;
                                }
                                memberTreeDialog.modal('hide');
                                form.find('#acetecAuthorizationID').val(departmentId);
//                                authorizationMember.val(departmentName);
                                authorizationMember.html(departmentName);
                                authorizationMember.trigger('keydown');
                            }).end().modal({
                                backdrop: false,
                                keyboard: false
                            }).on({
                                'hidden.bs.modal': function () {
                                    $(this).remove();
                                }
                            });
                        });
                    });
                },
                remove: function (ids, grid) {
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/TestProgramTemplate/delete.koala', data).done(function (result) {
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
                }
            }
            PageLoader.initSearchPanel();
            PageLoader.initGridPanel();
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
        })
        ;

        /**
         * 加载部门树
         */
        function loadmemberTree() {
            memberTree.parent().loader({
                opacity: 0
            });
            $.get(contextPath + '/organization/organizationEmployeeTree.koala').done(function (data) {
                memberTree.parent().loader('hide');
                var zNodes = new Array();
                $.each(data, function () {
                    var memberId = data['orgTree'].id;
                    var zNode = {};
                    if (this.organizationType == 'company') {//后期需要修改
                        zNode.type = 'parent';
                    } else {
                        zNode.icon = 'glyphicon glyphicon-list-alt'
                    }
                    this.title = this.name;
                    zNode.menu = this;
                    if (this.children && this.children.length > 0) {
                        zNode.children = getChildrenData(new Array(), this.children);
                    } else if (this.employeeVos && this.employeeVos.length > 0) {
                        zNode.children = getChildrenData(new Array(), this.employeeVos);
                    }
                    zNodes.push(zNode);
                });
                var dataSourceTree = {
                    data: zNodes,
                    delay: 400
                };
                memberTree.tree({
                    dataSource: dataSourceTree,
                    loadingHTML: '<div class="static-loader">Loading...</div>',
                    multiSelect: false,
                    cacheItems: true
                }).on({
                    'selectChildren': function (event, data) {
                        if (departmentId == '') {
                            departmentId.push(data.id);
                            departmentName = data.name;
                        } else {
                            alert('只能选择一位授权人！')
                        }
                    }
                });
            });
        }

        function getChildrenData(nodes, items) {
            $.each(items, function () {
                var zNode = {};
                if (this.organizationType == 'company') {
                    zNode.type = 'parent';
                } else {
                    zNode.icon = 'glyphicon glyphicon-list-alt'
                }
                this.title = this.name;
                zNode.menu = this;
                if (this.children && this.children.length > 0) {
                    zNode.children = getChildrenData([], this.children);
                } else if (this.employeeVos && this.employeeVos.length > 0) {
                    zNode.children = getChildrenData(new Array(), this.employeeVos);
                }

                /*else {//不需要这块。。。
                 $.ajax({
                 type: 'get',
                 url: contextPath + '/employee/getEmployee-by-org.koala?organizationId=' + this.id,
                 dataType: 'json',
                 success: function (result) {
                 zNode.children = []
                 $.each(result, function () {
                 var childNode = {};
                 this.title = this.name;
                 childNode.menu = this;
                 childNode.icon = 'glyphicon glyphicon-list-alt';
                 childNode.children = [];
                 zNode.children.push(childNode);
                 });
                 }
                 });
                 }*/
                nodes.push(zNode);
            });
            return nodes;
        }

        var openTestProgramTemplateDetailsPage = function (id) {
            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
            $.get('<%=path%>/TestProgramTemplate-view.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);
                $.get('${pageContext.request.contextPath}/TestProgramTemplate/get/' + id + '.koala').done(function (json) {
                    json = json.data;
                    var elm;
                    for (var index in json) {
                        if (json[index] + "" == "false") {
                            dialog.find('#' + index + 'ID').html("<span style='color:#d2322d' class='glyphicon glyphicon-remove'></span>");
                        } else if (json[index] + "" == "true") {
                            dialog.find('#' + index + 'ID').html("<span style='color:#47a447' class='glyphicon glyphicon-ok'></span>");
                        } else if (typeof json[index] == "object") {
                            if ("acetecAuthorizationDTOs" == index && json[index][0] && null != json[index][0]['employeeDTO']) {
                                dialog.find("#acetecAuthorizationID").html(json[index][0]['employeeDTO']['name']);
                                dialog.find("#lastModifyTimeID").html(json[index][0]['lastModifyTime'] + "");
                            }
                            if ("internalProductDTO" == index && null != json[index]["customerDirectDTO"]) {
                                dialog.find("#productNameID").html(json[index]["customerProductNumber"]);
                                dialog.find("#customerNumberID").html(json[index]["customerDirectDTO"]["number"]);
                            }
                            /*                                 for ( var i in json[index]) {
                             dialog.find('#'+ index+ "_"+ i+ 'ID').html(json[index][i]+ "");
                             }  */
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
                        <label class="control-label" style="width:110px;float:left;">所属客户(编号):&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <input type="text" id="numberID" name="internalProductDTO.customerDirectDTO.number"
                                   class="form-control" style="width:150px;"/>
                        </div>
                        <label class="control-label" style="width:100px;float:left;">产品型号:&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <input type="text" id="customerProductNumberID"
                                   name="internalProductDTO.customerProductNumber" class="form-control"
                                   style="width:150px;"/>
                        </div>
                        <label class="control-label" style="width:100px;float:left;">测试类型:&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <div class="btn-group select" id="testTypeSearchID"></div>
                            <input type="hidden" id="testTypeSearchID_" name="internalProductDTO.testType"/>
                        </div>
                        <label class="control-label" style="width:100px;float:left;">封装形式:&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <div class="btn-group select" id="packageTypeID"></div>
                            <input type="hidden" id="packageTypeID_" name="internalProductDTO.packageType"/>
                        </div>
                    </div>
                </td>
                <td>
                    <button id="search" type="button" style="position:relative;margin-left:150px;margin-bottom:20px;"
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
