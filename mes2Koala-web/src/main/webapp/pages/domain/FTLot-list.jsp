<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <script type="text/javascript" src="<%=contextPath %>/js/common.js"></script>
    <LINK rel="stylesheet" type="text/css" href="<%=contextPath %>/js/easyui/themes/default/easyui.css"/>
    <script type="text/javascript" src="<%=contextPath %>/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <%@ page import="java.util.Date" %>
    <% String formId = "form_" + new Date().getTime();
        String gridId = "grid_" + new Date().getTime();
        String path = request.getContextPath() + request.getServletPath().substring(0, request.getServletPath().lastIndexOf("/") + 1);
    %>
    <script type="text/javascript">
        if (typeof String.prototype.startsWith != 'function') {
            String.prototype.startsWith = function (prefix) {
                return this.slice(0, prefix.length) === prefix;
            };
        }
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
                    var width = 130;
                    return grid.grid({
                        identity: "id",
                        gridheight:900,
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
                                content: '<button class="btn btn-primary" type="button">小样出货</button>',
                                action: 'little'
                            },
                            {
                                content: '<button class="btn btn-danger" id="updateftList" type="button">修改</button>',
                                action: 'updata'
                            },
                            {
                                content: '<button class="btn btn-danger" type="button">良率放行</button>',
                                action: 'endFailTest'
                            },
                            {
                                content: '<button class="btn btn-success" type="button">预Hold</button>',
                                action: 'featuHold'
                            },
                            {
                                content: '<button class="btn btn-success" type="button">Fail品标签打印</button>',
                                action: 'failPrint'
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
                                content: '<button class="btn btn-danger" type="button">删除</button>',
                                action: 'delete'
                            }
                        ],
                        url: "${pageContext.request.contextPath}/FTLot/ftPageJson.koala",
                        columns: [
                            {title: '状态', name: 'state', width: width},
                            {title: 'Lot Number', name: 'lotNumber', width: width},
                            {title: 'Quantity', name: 'quantity', width: width},
                            {title: '出货型号', name: 'shipmentProductNumber', width: width},
                            {
                                title: 'PID',
                                name: 'internalProductNumber',
                                width: width,
                            },
                            {title: '封装批号', name: 'packageNumber', width: width},
                            {title: '版本型号', name: 'productVersion', width: width},
                            {title: 'Customer Lot No', name: 'customerLotNumber', width: width},
                            {title: 'Customer No', name: 'customerNumber', width: width},
                            {title: '保税类型', name: 'taxType', width: width},
                            {title: '内PPO', name: 'internalPPO', width: width},
                            {title: '外PPO', name: 'customerPPO', width: width},
                            
                            {title: '来料型号', name: 'revision', width: width},
                            {title: 'Wafer Lot Number', name: 'waferLotNumber', width: width},
                            {title: '进料日期', name: 'incomingDate', width: width}
                        ]
                    }).on({
                        'add': function () {
                            self.add($(this));
                        },
                        'remove': function () {
                            self.remove($(this));
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
                        'little': function (event, data) {
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
                            self.little(indexs[0], $this);
                        },
                        'updata': function (event, data) {
                            /*           var indexs = data.data;
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
                             }*/
                            isModify = 1;
                            var $ftDetail = $('#ftDetail');
                            $ftDetail.find('input').attr('disabled', false);
                            $ftDetail.find('textarea').attr('disabled', false);
                            $ftDetail.find('select').attr('disabled', false);
                            $('#updateftList').removeClass('btn-danger').addClass('btn-default');
                            if (buttonName == 'Test') {
                                $ftDetail.find('#start').css('display', 'none');
                                $ftDetail.find('[name="save"]').removeClass('hidden');
                                $('#ftDetail').find('#end').addClass('hidden');
                                $('#ftDetail').find('[name="save"]').attr('disabled', false);
                            } else {

                                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").addClass('hidden');
                                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', false);
                            }
                            //self.hold(indexs[0], $this);
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
                        'failPrint': function (event, data) {
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
                            self.failPrint(indexs[0], $this);
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
                            //self.hold(indexs[0], $this);
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
                            self.merge();
                        },
                        'rework': function (event, data) {
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
                            self.rework(indexs[0], $this);
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
                        }
                    });
                },
                runcard: function (ftinfoId, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">RunCard</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">查看Runcard</button></div></div>'
                            + '</div></div>');

                    debugger
                    $.get('${pageContext.request.contextPath}/ueditor/getSpecialFormStatus.koala?ftinfoId=' + ftinfoId).done(function (data) {
                        data = data['data'];
                        var firstSheetStatus = data['firstSheetStatus'];
                        var recordSheetStatus = data['recordSheetStatus'];
                        var summarySheetStatus = data['summarySheetStatus'];
                        var reelcodeSheetStatus = data['reelcodeSheetStatus'];
                        var machineMaterialRecordSheetStatus = data['machineMaterialRecordSheetStatus'];
                        var checkSheetStatus = data['checkSheetStatus'];

                        $.get('<%=contextPath %>/ueditor/getSpecialFormPage.koala?&ftinfoId=' + ftinfoId).done(function (html) {
                            debugger
                            dialog.find('.modal-body').html(html);
                            self.initPage(dialog.find('form'));
                            dialog.find('.modal-body').find("form").append($('<hr/>'
                                    + '<div class="form-group">'
                                    + '<div class="col-lg-10 col-lg-offset-2">'
                                    + '<div style="margin-bottom:15px">'
                                    + '<input type="radio" name="optionsRadios" id="optionsRadios1" value="0" >从起始站点开始列印</div>'
                                    + '<div style="margin-bottom:15px">'
                                    + '<input type="radio" name="optionsRadios" id="optionsRadios2" value="1">从生产批目前作业站开始列印</div>'
                                    + '<div style="margin-bottom:15px">'
                                    + '<input type="radio" name="optionsRadios" id="optionsRadios3" value="2">只列印目前作业站</div></div></div>'));

                            dialog.find("#firstSheetStatus").attr("checked", firstSheetStatus);
                            dialog.find("#recordSheetStatus").attr("checked", recordSheetStatus);
                            dialog.find("#summarySheetStatus").attr("checked", summarySheetStatus);
                            dialog.find("#reelcodeSheetStatus").attr("checked", reelcodeSheetStatus);
                            dialog.find("#machineMaterialRecordSheetStatus").attr("checked", machineMaterialRecordSheetStatus);
                            dialog.find("#checkSheetStatus").attr("checked", checkSheetStatus);

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
                        var specialFormStr = dialog.find('form').find('input[type=checkbox]:checked').map(function () {
                            if (this.name == "flowForm") {
                                return;
                            }
                            else {
                                return this.name;
                            }
                        }).get().join(',');
                        var state = dialog.find('form').find('input[type=radio]:checked').val();
                        if (state == undefined) {
                            dialog.find('.modal-content').message({
                                type: 'warning',
                                content: '请选择列印状态'
                            })
                            return;
                        }

                        createmodalwindow("UEditor", 800, 500, '<%=contextPath %>/ueditor/getPageForRuncard.koala?ftLotId=' +
                                ftinfoId + '&specialFormStr=' + specialFormStr + '&state=' + state);
                    });
                },
                hold: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">开Hold</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/FTHoldQDN.jsp').done(function (html) {
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
                                    msg = msg['data'];
                                    $("select[name='toPerson']").empty();
                                    for (var i = 0; i < msg.length; i++) {
                                        $("select[name='toPerson']").append("<option value='" + msg[i]['id'] + "'>" + msg[i]['name'] + "<option>");
                                    }
                                });
                            }
                        });
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/FTLot/get/' + id + '.koala').done(function (json) {
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
                                    }
                                }

                            }
                            $.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function (json) {
                                dialog.find("#QDN_initiator").text(json['data']['userAccount'] + "|" + json['data']['name']);
                                dialog.find("[name='workPerson']").val(json['data']['userAccount'] + "|" + json['data']['name']);

                            });//获取申请人
                            $.get('${pageContext.request.contextPath}/FTProcess/findFTProcessByFTLotId/' + id + '.koala').done(function (json) {//根据SBL生成填写表格
                                json = json['data'];
                                var a = $.inArray(dialog.find("#currentStateID").text().replace(/[\u4E00-\u9FA5]/g, ''), json['content'].split("|"));
                                json = json['ftNodeDTOs'][a]['sblDTOs'];
                                var tableHead = "<tr><th>BIN</th>";
                                tableHead += json.filter(function (json) {
                                    return json != "";
                                }).map(function (json) {
                                    return "<th style='text-align:center;'>" + json.type + "</th>";
                                }).join("");
                                tableHead += "<th>Yield</th><th>Pass</th><th>Loss</th><th>Scrap</th><th>Other</th><th>Sum</th></tr>";
                                var tableBody = "<tr><td>Num</td>";
                                tableBody += json.filter(function (json) {
                                    return json != "";
                                }).map(function (json) {
                                    return "<td><input style='width:100%;' type='text'/></td>";
                                }).join("");

                                //
                                tableBody += "<td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td></tr>"
                                dialog.find(".QDNBin tbody").empty();
                                dialog.find(".QDNBin tbody").append(tableHead + tableBody);
                                dialog.find("input[name='qdnBinName']").val(json.filter(function (json) {
                                    return json != "";
                                }).map(function (json) {
                                    return json.type;
                                }).join(","));
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
                            var qdnBin = "";
                            for (var a = 0; a < $(".QDNBin input").length; a++) {
                                qdnBin += $(".QDNBin input")[a].value + ",";
                            }
                            qdnBin = qdnBin.substring(0, qdnBin.length - 1);
                            var postString = dialog.find('form').serialize() + "&qdnBin=" + qdnBin + "&toDepartment=" + $("input[name='To']:checked").next().text() + "&flowNow=" + $("[name='flowNow']").text();
                            //
                            $.post('${pageContext.request.contextPath}/FTLotNodeOption/hold.koala?id=' + id, postString).done(function (result) {
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
                    $.get('<%=path%>/FTHoldUnlock.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/FTLot/get/' + id + '.koala').done(function (json) {
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
                            $.post('${pageContext.request.contextPath}/FTLotNodeOption/unhold.koala?id=' + id, dialog.find('form').serialize()).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '解Hold成功'
                                    });
                                } else {
                                    //
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
                    $.get('<%=path%>/FTfutureHold.jsp').done(function (html) {
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
                                    msg = msg['data'];
                                    $("select[name='toPerson']").empty();
                                    for (var i = 0; i < msg.length; i++) {
                                        $("select[name='toPerson']").append("<option value='" + msg[i]['id'] + "'>" + msg[i]['name'] + "<option>");
                                    }
                                });
                            }
                        });
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/FTLot/get/' + id + '.koala').done(function (json) {
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
                                    }
                                }
                            }
                            $.get('${pageContext.request.contextPath}/FTProcess/findFTProcessByFTLotId/' + id + '.koala').done(function (json) {
                                //
                                json = json.data;
                                for (var a in json['ftNodeDTOs']) {
                                    if (json['ftNodeDTOs'][a]['ftState'] == 0) {
                                        for (var b = a; b < json['ftNodeDTOs'].length; b++) {
                                            dialog.find(".holdStation").append("<option value='" + json['ftNodeDTOs'][b]['name'] + "'>" + json['ftNodeDTOs'][b]['name'] + "</option>");
                                        }
                                        break;
                                    }
                                }
                            });//获取申请人
                            $.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function (json) {
                                dialog.find("#QDN_initiator").text(json['data']['userAccount'] + "|" + json['data']['name']);
                                dialog.find("[name='workPerson']").val(json['data']['userAccount'] + "|" + json['data']['name']);
                            });//获取申请人
                            $.get('${pageContext.request.contextPath}/FTLotNodeOption/getCurrentSblByInternalLot/' + id + '.koala').done(function (json) {//根据SBL生成填写表格
                                json = json['data'];
                                /* var a = $.inArray(dialog.find("#currentStateID").text().replace(/[\u4E00-\u9FA5]/g, ''), json['content'].split("|"));
                                 json = json['ftNodeDTOs'][a]['sblDTOs']; */
                                var tableHead = "<tr><th>BIN</th>";
                                tableHead += json.filter(function (json) {
                                    return json != "";
                                }).map(function (json) {
                                    return "<th style='text-align:center;'>" + json.type + "</th>";
                                }).join("");
                                tableHead += "<th>Yield</th><th>Pass</th><th>Loss</th><th>Scrap</th><th>Other</th><th>Sum</th></tr>";
                                var tableBody = "<tr><td>Num</td>";
                                tableBody += json.filter(function (json) {
                                    return json != "";
                                }).map(function (json) {
                                    return "<td><input style='width:100%;' type='text'/></td>";
                                }).join("");

                                //
                                tableBody += "<td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td><td><input style='width:100%;' type='text'/></td></tr>"
                                dialog.find(".QDNBin tbody").empty();
                                dialog.find(".QDNBin tbody").append(tableHead + tableBody);
                                dialog.find("input[name='qdnBinName']").val(json.filter(function (json) {
                                    return json != "";
                                }).map(function (json) {
                                    return json.type;
                                }).join(","));
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
                            var qdnBin = "";
                            for (var a = 0; a < $(".QDNBin input").length; a++) {
                                qdnBin += $(".QDNBin input")[a].value + ",";
                            }
                            qdnBin = qdnBin.substring(0, qdnBin.length - 1);
                            var postString = dialog.find('form').serialize() + "&qdnBin=" + qdnBin + "&toDepartment=" + $("input[name='To']:checked").next().text();
                            //
                            $.post('${pageContext.request.contextPath}/FTLotNodeOption/futureHold.koala?id=' + id, postString).done(function (result) {
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
                little: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">小样出货</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/FTLittleShipping.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        dialog.find('#qualityID .dropdown-menu').bind("click", function () {
                            var typearray = $("#qualityID input").val();
                            $.ajax({
                                        async: false,
                                        url: '${pageContext.request.contextPath}/SampleShipping/findQtyByQuality.koala',
                                        data: {"quality": typearray, "ftLotId": id},
                                        type: 'POST',
                                        dataType: 'json',
                                    })
                                    .done(function (msg) {
                                        $("#qtyTotalID").val(msg['data']);
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
                            $.post('${pageContext.request.contextPath}/FTLotNodeOption/sampleShipping.koala?id=' + id, dialog.find('form').serialize()).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '出货成功'
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
                failPrint: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Fail品标签打印</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">打印</button></div></div></div></div>');
                    $.get('<%=path%>/FTFailLabelPrint.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/FTLotNodeOption/getFTResultByLotId/' + id + '.koala?labelType=FAIL').done(function (json) {
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
                                if (json[index] == "-1" || !!json[index] == 0) {
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
                            //if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            if (!!$("#labelNameID").val() == 0) {
                                dialog.message({
                                    type: 'warning',
                                    content: '未找到对应标签请检查'
                                });
                                return false;
                            }

                            var path = "C:/WMS/PRINT/" + $("#labelNameID").val();
                            var parameter = dialog.find('form').serialize();
                            onPrint(path, parameter);
                        });
                    });
                },
                optdetal: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">操作详情</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
                    var html = "";
                    dialog.find('.modal-body').html(html);
                    self.initPage(dialog.find('form'));
                    $.get('${pageContext.request.contextPath}/FTLotOptionLog/pageJson.koala?internalLotId=' + id + '&page=0&pagesize=100').done(function (json) {
                        json = json.data;
                        //
                        var elm = "<table class='table'><tr>";
                        elm += "<th>节点名</th><th>开始时间</th><th>开始操作员</th><th>结束时间</th><th>结束操作员</th><th>备注</th>";
                        elm = elm + "</tr>";
                        $.each(json, function (a) {
                            elm = elm + "<tr>";
                            var remark = !!json[a]['remark'] == 0 ? "" : json[a]['remark'];
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
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:700px">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">分批</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">分批</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/FTSplit.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        $.get('${pageContext.request.contextPath}/FTLot/get/' + id + '.koala').done(function (json) {
                            json = json.data;
                            dialog.find('#quantityID').val(json['qty']);
                            dialog.find('#lotNumberID').val(json['internalLotNumber']);
                        });
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        }).find('.modal-body').html(html);
                        dialog.find('#divideAmountID').on('change', function () {
                            dialog.find('#group').html('');
                            var number = dialog.find('#divideAmountID').val();
                            for (var i = 0; i < parseInt(number); i++) {
                                var html = $('<div class = "form-group"><label class="col-lg-2 control-label">子批' + parseInt(i + 1) + ':</label><div class="col-lg-4"><input style="display:inline; width:94%;" name="lot' + parseInt(i + 1) + '" class="form-control" type="text" value=' + dialog.find('#lotNumberID').val() + "-" + parseInt(i + 1) + ' /></div><label class="col-lg-2 control-label">数量:</label><div class="col-lg-4"><input style="display:inline; width:94%;" name="quantity' + parseInt(i + 1) + '" class="form-control" type="number" /></div></div>');
                                dialog.find('#group').append(html);
                            }
                        })
                        dialog.find('#group').on('change', 'input', function () {
                            var totalInput = 0;
                            var count = 0
                            $.each(dialog.find('#group').find("input[type='number']"), function () {
                                if (this.value == '') {
                                    count++;
                                    return true
                                }
                                totalInput += parseInt(this.value);
                            });
                            if (parseInt(count) == 1) {
                                dialog.find('#group').find('input').each(function () {
                                    if (this.value == '') {
                                    	debugger;
                                        this.value = parseInt(dialog.find('#quantityID').val()) - totalInput;
                                        return false
                                    }
                                });
                            }
                        })
                        dialog.find('#save').on('click', {grid: grid}, function (e) {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            var divideAmount = [];
                            var flagSperate = 1;
                            var totalNumber = 0;
                            originNumber = dialog.find("#quantityID").val();
                            $.each(dialog.find('#group input[name^=quantity]'), function () {
                                divideAmount.push(parseInt(this.value));
                                totalNumber += parseInt(this.value);
                                if (parseInt(this.value) > parseInt(originNumber)) {
                                    flagSperate = 0;
                                    dialog.message({
                                        type: 'warning',
                                        content: '单批数量不能超过原数量！'
                                    })
                                    return false;
                                }
                            })
                            if ((divideAmount.length < 2)) {
                                flagSperate = 0;
                                dialog.message({
                                    type: 'warning',
                                    content: '分批数量至少为2！'
                                })
                                return false;
                            }
                            if (flagSperate == 0) return;
                            if (totalNumber != originNumber) {
                                dialog.message({
                                    type: 'warning',
                                    content: '分批总和不等于原批数量！'
                                })
                                return;
                            }
                            data = {id: id, splitQty: divideAmount.join(',')};
                            $.post('${pageContext.request.contextPath}/FTLotNodeOption/splitLot.koala', data).done(function (result) {
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
                },
                merge: function () {
                    var self = this;
                    var totalAmount = 0;
                    var ids = [];
                    var flag = 0;
                    var combinedLotNumber = '';
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:750px">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">合批</h4></div><div class="modal-body" style="height:600px">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/FTMerge.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        dialog.find('#grantAuthorityToUserButton').on('click', function () {
                            var amount = 0;
                            var diffLotNumber = 0;
                            var grantRolesToUserTableItems = dialog.find('#notGrantAuthoritiesToUserGrid').getGrid().selectedRows();
                            if (grantRolesToUserTableItems.length < 2) {
                                dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                    type: 'warning',
                                    content: '请选择至少两条要合并的批次'
                                });
                                return false;
                            }
                            $.each(grantRolesToUserTableItems, function () {
                                if (this.productVersion != grantRolesToUserTableItems[0].productVersion) {
                                    dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                        type: 'warning',
                                        content: '版本型号不符合'
                                    });
                                    flag = 1;
                                    return false;
                                }
                                totalAmount += parseInt(this.quantity);
                                if (parseInt(totalAmount) > 50000) {
                                    dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                        type: 'warning',
                                        content: '合批后的数量不能超过50K'
                                    });
                                    flag = 1;
                                    return false;
                                }
                                if (combinedLotNumber.indexOf(this.lotNumber) == -1) {
                                    combinedLotNumber += this.lotNumber + '/';
                                }
                                ids.push(this.id);
                            });
                            combinedLotNumber = combinedLotNumber.substring(0, combinedLotNumber.length - 1);
                            if (flag == 0) {
                                dialog.find('#td1FT').html(combinedLotNumber);
                                dialog.find('#td2FT').html(totalAmount);
                            } else {
                                dialog.find('#td1FT').html();
                                dialog.find('#td2FT').html();
                                flag = 0;
                                totalAmount = 0;
                                ids = [];
                                combinedLotNumber = '';
                            }
                        });
                        dialog.find('#searchFTLot').on('click', function () {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            var params = {};
                            dialog.find('#serachItems').find('input').each(function () {
                                var $this = $(this);
                                var name = $this.attr('name');
                                if (name) {
                                    params[name] = $this.val();
                                }
                            });
                            dialog.find('#notGrantAuthoritiesToUserGrid').getGrid().search(params);
                        });
                        dialog.modal({
                            keyboard: false,
                            backdrop: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            },
                            'shown.bs.modal': function () {
                                var notGrantRolecolumns = [{
                                    title: "艾科批号",
                                    name: "lotNumber",
                                    width: 120
                                }, {title: "来料数量", name: "quantity", width: 80}];
                                dialog.find('#notGrantAuthoritiesToUserGrid').grid({
                                    identity: 'id',
                                    columns: notGrantRolecolumns,
                                    isShowPages: false,
                                    url: "${pageContext.request.contextPath}/FTLot/ftPageJson.koala"
                                });
                                dialog.find("#notGrantAuthoritiesToUserGrid .grid-table-body").css("overflow-x", "hidden");
                            }
                        });
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        var data = {mergeIds: ids.join(',')};
                        //
                        $.post('${pageContext.request.contextPath}/FTLotNodeOption/mergeLot.koala', data).done(function (result) {
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
                rework: function (id, grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:1010px"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">重工申请</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
                    $.get('<%=path%>/FTReworkApply.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        //重工单赋值************************************************************************************************************
                        $.get('${pageContext.request.contextPath}/FTLot/get/' + id + '.koala').done(function (json) {
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
                            }
                            //
                        });
                        var mydate = new Date();
                        dialog.find("[name='reworkDate']").text(mydate.toLocaleDateString());
                        $.get('${pageContext.request.contextPath}/FTRework/createReworkNo/' + id + '.koala').done(function (json) {
                            dialog.find("td[name='reworkNo']").text(json['data']);
                        });//获取重工单编号；
                        $.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function (json) {
                            dialog.find("td[name='reworkDepartment']").text(json['data']['userAccount'] + "|" + json['data']['name']);
                            dialog.find("span[name='optpreson']").text(json['data']['userAccount'] + "|" + json['data']['name'])
                        });//获取申请人
                        //*********************************************************************************************************************
                        var authorizationMember = dialog.find('#authorizationMemberID');
                        authorizationMember.on('click', function () {
                            $.get(contextPath + '/pages/domain/StaffSelect.jsp').done(function (data) {
                                //
                                var staffDialog = $(data);
                                staffDialog.modal({
                                    backdrop: false,
                                    keyboard: false
                                }).on({
                                    'hidden.bs.modal': function () {
                                        $(this).remove();
                                    },
                                    'shown.bs.modal': function () {
                                        var notGrantRolecolumns = [{
                                            title: "工号",
                                            name: "userAccount",
                                            width: 120
                                        }, {title: "姓名", name: "name", width: 80}, {
                                            title: "部门",
                                            name: "employeeOrgName",
                                            width: 80
                                        }];
                                        staffDialog.find('#staffGrid').grid({
                                            identity: 'id',
                                            columns: notGrantRolecolumns,
                                            isShowPages: false,
                                            url: "${pageContext.request.contextPath}/auth/employeeUser/pagingQuery.koala"
                                        });
                                        staffDialog.find("#staffGrid .grid-table-body").css("overflow-x", "hidden");
                                        staffDialog.find("#staffGrid .grid-table-body").css("height", "181px");
                                    }
                                });
                                staffDialog.find('#searchFTLot').on('click', function () {
                                    if (!Validator.Validate(staffDialog.find('form')[0], 3))return;
                                    var params = {};
                                    staffDialog.find('#serachItems').find('input').each(function () {
                                        var $this = $(this);
                                        var name = $this.attr('name');
                                        if (name) {
                                            params[name] = $this.val();
                                        }
                                    });
                                    staffDialog.find('#staffGrid').getGrid().search(params);
                                });
                                staffDialog.find('#staffConfirm').on('click', function () {
                                    var grantRolesToUserTableItems = staffDialog.find('#staffGrid').getGrid().selectedRows();
                                    if (grantRolesToUserTableItems.length == 0) {
                                        staffDialog.find('#staffGrid').message({
                                            type: 'warning',
                                            content: '请选择审核人'
                                        });
                                        return;
                                    }
                                    //
                                    $(".applyPerson").append("<button type='button' staffId='" + grantRolesToUserTableItems[0]['id'] + "' style='margin-right:20px;' class='btn btn-primary'>" + grantRolesToUserTableItems[0]['name'] + "<span class='glyphicon glyphicon-remove' style='cursor: pointer;margin-left: 5px;' onclick='$(this).parent().remove();'></span></button>");
                                    staffDialog.modal('hide');
                                });
                            })
                        });
                        //选定审核人
                        //*********************************************************************************************************************
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        });
                        dialog.find('#save').on('click', {grid: grid}, function (e) {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            var postJson = {};
                            var basic = {};
                            for (var a = 0; a < dialog.find(".basic").length; a++) {
                                var names = dialog.find(".basic").eq(a).attr("name");
                                if (dialog.find(".basic").eq(a)[0].tagName == "TD") {
                                    basic[names] = dialog.find(".basic").eq(a).text();
                                }
                                else {
                                    basic[names] = dialog.find(".basic").eq(a).val();
                                }
                            }
                            postJson['basic'] = basic;
                            postJson['basic']["reworkReworkQty"] = dialog.find("input[name='reworkReworkQty']").val();
                            postJson['basic']["reworkTotalQty"] = dialog.find("span[name='reworkTotalQty']").text();
                            if (parseInt(postJson['basic']["reworkReworkQty"]) > parseInt(postJson['basic']["reworkRotalQty"])) {
                                dialog.find('.modal-content').message({
                                    type: 'warning',
                                    content: "重工数量需小于等于总数量请检查"
                                });
                                return;
                            }
                            postJson['reason'] = {};
                            postJson['reason']['reasons'] = dialog.find("input[name='Rework_reason']:checked").next().text();
                            postJson['reason']['explanation'] = dialog.find("textarea[name='explanation']").val();
                            if (postJson['reason']['reasons'] == "其他(Others)") {
                                postJson['reason']['other'] = dialog.find("input[name='Rework_reason']:checked").next().next().val()
                            }
                            var itemArray = [];
                            var itemTable = dialog.find(".rework_item").children("tr");
                            for (var a = 0; a < itemTable.length; a++) {
                                var itemJson = {};
                                itemJson['attentionItem'] = itemTable.eq(a).find("input").eq(0).val();
                                itemJson['reworkFlow'] = itemTable.eq(a).find("input").eq(1).val();
                                itemJson['operator'] = itemTable.eq(a).find("input").eq(2).val();
                                itemJson['accomplishDate'] = itemTable.eq(a).find("input").eq(3).val();
                                itemArray.push(itemJson);
                            }
                            postJson['item'] = itemArray;
                            postJson['summary'] = dialog.find("input[name='Rework_over']:checked").next().text();
                            applayPresonArray = [];
                            for (var a = 0; a < $(".applyPerson button").length; a++) {
                                var applayPreson = {};
                                applayPreson['id'] = $(".applyPerson button").eq(a).attr("staffid");
                                applayPreson['name'] = $(".applyPerson button").eq(a).text();
                                applayPresonArray.push(applayPreson)
                            }
                            postJson['approvePerson'] = applayPresonArray;
                            var a = {};
                            a['data'] = JSON.stringify(postJson);
                            $.post('${pageContext.request.contextPath}/FTRework/add.koala?id=' + id, a).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '申请成功'
                                    });
                                } else {
                                    //
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
                remove: function (ids, grid) {
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/CustomerFTLot/deleteOrder.koala', data).done(function (result) {
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
                endFailTest: function (id, grid) {
                    $.get('${pageContext.request.contextPath}/FTProcess/findFTProcessByFTLotId/' + id + '.koala').done(function (data) {
                        var pid = data.data.id;
                        var data = [{name: 'processId', value: pid}];
                        $.post('${pageContext.request.contextPath}/FTLotNodeOption/endFailTestNode.koala', data).done(function (result) {
                            if (result.success) {
                                grid.data('koala.grid').refresh();
                                grid.message({
                                    type: 'success',
                                    content: '良率放行成功'
                                });
                            } else {

                                grid.message({
                                    type: 'error',
                                    content: result.errorMessage
                                });
                            }
                        });
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
                $('#ftDetail').html('');
                e.stopPropagation();//这个e就是事件对象
                var checkedId = grid.getGrid().selectedRowsIndex();
                if (checkedId.length > 1) {
                    grid.message({
                        type: 'warning',
                        content: '只能选择一条记录进行关联'
                    });
                    return;
                }
                refreshButtonList(checkedId[0]);
//                debugger
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
        <%--var getData = function (checkedId) {--%>

        <%--if (checkedId == undefined)--%>
        <%--return;--%>
        <%--$.get('${pageContext.request.contextPath}/FTProcess/findFTProcessByFTLotId/' + checkedId + '.koala').done(function (data) {--%>
        <%--data = data.data;--%>
        <%--$.each(data.ftNodeDTOs, function (i) {--%>
        <%--var html = "";--%>
        <%--var tml = $('<li style="margin-left:100px;font-size:200%;">&darr;</li>');--%>
        <%--switch (this.ftState) {--%>
        <%--case 0:--%>
        <%--html = $('<li><button type="button" style="margin:1px 50px;width:120px;" class="btn btn-default" disabled>' + this.name + '</button></li>');--%>
        <%--break;--%>
        <%--case 1:--%>
        <%--case 2:--%>
        <%--html = $('<li><button type="button" style="margin:1px 50px;width:120px;" class="btn btn-success">' + this.name + '</button></li>');--%>
        <%--break;--%>
        <%--case 3:--%>
        <%--html = $('<li><button type="button" style="margin:1px 50px;width:120px;" class="btn btn-primary">' + this.name + '</button></li>');--%>
        <%--break;--%>
        <%--}--%>
        <%--$("#DynamicButton").append(html);--%>
        <%--if (i < data.ftNodeDTOs.length - 1) {--%>
        <%--$("#DynamicButton").append(tml);--%>
        <%--}--%>
        <%--})--%>

        <%--$("#DynamicButton").find('button').on('click', function () {--%>

        <%--$('#ftDetail').html('');--%>
        <%--var nodeName = this.innerHTML;--%>
        <%--onClickedDispatcher(checkedId, data, nodeName);--%>
        <%--});--%>
        <%--});--%>
        <%--};--%>

        // 当点击、进站、保存时刷新
        var refreshDetail = function (checkedId, nodeName) {
            if (arguments[2]) {//如果是test站点则传递三个参数，这时arguments[2]能取到值
                var testDetailToClick = arguments[2];
            }
            $.get('${pageContext.request.contextPath}/FTProcess/findFTProcessByFTLotId/' + checkedId + '.koala').done(function (data) {
//                
                if (!data.success) {
                    return;
                }
                data = data.data;
                onClickedDispatcher(checkedId, data, nodeName, testDetailToClick);
            });
        }

        // 当出站后需要刷新
        var refreshButtonList = function (checkedId) {
            $('#DynamicButton').html('');
            $.get('${pageContext.request.contextPath}/FTProcess/findFTProcessByFTLotId/' + checkedId + '.koala').done(function (data) {
//                            
                if (!data.success) {
                    return;
                }
                data = data.data;
                $.each(data.ftNodeDTOs, function (i) {
                    var html = "";
                    var tml = $('<li style="margin-left:48%;font-size:200%;">&darr;</li>');
                    switch (this.ftState) {
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
                    if (i < data.ftNodeDTOs.length - 1) {
                        $("#DynamicButton").append(tml);
                    }
                });
                $("#DynamicButton").find('button').on('click', function () {
                    $('#ftDetail').html('');

                    var nodeName = this.innerHTML;
                    refreshDetail(checkedId, nodeName);
                });
            });
        };

        // 当出站后需要刷新
        var refreshDetailAndButtonList = function (checkedId) {
            $("#DynamicButton").html('');
            refreshButtonList(checkedId);
            $.get('${pageContext.request.contextPath}/FTProcess/findFTProcessByFTLotId/' + checkedId + '.koala').done(function (data) {
//                
                if (!data.success) {
                    return;
                }
                data = data.data;
                $.each(data.ftNodeDTOs, function (i) {
                    switch (this.ftState) {
                        case 0:
                            break;
                        case 1:
                        case 2:
//                                debugger
                            onClickedDispatcher(checkedId, data, this.name);
                            break;
                        case 3:
                            break;
                    }

                });
            });
        };


        <%--var getDataafterClick = function (checkedId, nodeName) {--%>
        <%--$.get('${pageContext.request.contextPath}/FTProcess/findFTProcessByFTLotId/' + checkedId + '.koala').done(function (data) {--%>
        <%----%>
        <%--if (!data.success) {--%>
        <%--return;--%>
        <%--}--%>
        <%--data = data.data;--%>

        <%--$.each(data.ftNodeDTOs, function (i) {--%>
        <%--var html = "";--%>
        <%--var tml = $('<li style="margin-left:100px;font-size:200%;">&darr;</li>');--%>
        <%--switch (this.ftState) {--%>
        <%--case 0:--%>
        <%--html = $('<li><button type="button" style="margin:1px 50px;width:120px;" class="btn btn-default" disabled>' + this.name + '</button></li>');--%>
        <%--break;--%>
        <%--case 1:--%>
        <%--case 2:--%>
        <%--html = $('<li><button type="button" style="margin:1px 50px;width:120px;" class="btn btn-success">' + this.name + '</button></li>');--%>
        <%--nodeName = this.name;--%>
        <%--break;--%>
        <%--case 3:--%>
        <%--html = $('<li><button type="button" style="margin:1px 50px;width:120px;" class="btn btn-primary">' + this.name + '</button></li>');--%>
        <%--break;--%>
        <%--}--%>
        <%--$("#DynamicButton").append(html);--%>
        <%--if (i < data.ftNodeDTOs.length - 1) {--%>
        <%--$("#DynamicButton").append(tml);--%>
        <%--}--%>
        <%--});--%>
        <%--$("#DynamicButton").find('button').on('click', function () {--%>
        <%--$('#ftDetail').html('');--%>
        <%--$('#DynamicButton').html('');--%>
        <%--nodeName = this.innerHTML;--%>
        <%--getDataafterClick(checkedId, nodeName);--%>
        <%--});--%>
        <%--onClickedDispatcher(checkedId, data, nodeName);--%>
        <%--});--%>
        <%--};--%>


        var startNode = function (checkedId, FTProcessDTO, nodeName) {
            $.post('${pageContext.request.contextPath}/FTLotNodeOption/startFTNode/' + FTProcessDTO.id + '.koala').done(function (result) {
                if (result.success) {

                    refreshDetail(checkedId, nodeName);
                    grid.getGrid().refresh();
                    grid.message({
                        type: 'success',
                        content: '进站成功'
                    })
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
            $.post('${pageContext.request.contextPath}/FTLotNodeOption/saveFTNode.koala',
                            $.param(json) + '&' + $('#ftDetail').find('form').serialize())
                    .done(function (result) {
                        if (result.success) {

                            refreshDetail(checkedId, nodeName);
                            isModify = 0;
                            $('#updateftList').addClass('btn-danger').removeClass('btn-default');
                            grid.getGrid().refresh();
                            grid.message({
                                type: 'success',
                                content: '保存成功'
                            })
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.errorMessage
                            });
                        }
                    })
        }

        // 保存并出站
        var saveNodeThenEndNode = function (checkedId, json, nodeName) {
//            debugger
            $.post('${pageContext.request.contextPath}/FTLotNodeOption/saveFTNode.koala',
                            $.param(json) + '&' + $('#ftDetail').find('form').serialize())
                    .done(function (result) {
//                        debugger
                        if (!result.success) {
                            grid.message({
                                type: 'error',
                                content: result.errorMessage
                            });
                            return;
                        }
                        grid.message({
                            type: 'success',
                            content: '保存成功'
                        });
                        $.post('${pageContext.request.contextPath}/FTLotNodeOption/endFTNode.koala', $.param(json) + '&' + $('#ftDetail').find('form').serialize()).done(function (result) {
                            if (result.success) {

                                isModify = 0;
                                $('#updateftList').addClass('btn-danger').removeClass('btn-default');
                                grid.getGrid().refresh();
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
                    });

        }
        var registerOnFinalYieldChangedListener = function ($tableFinalYield) {

            function updateFail() {

                var failVal = 0;
                $tableFinalYield.find('.binFail').each(function () {
                    if (this.value.match('-') != null) {
                        return true;
                    }
                    var binFailValue = this.value.trim() === "" ? 0 : this.value.trim();//如果为空则值为0
                    failVal += parseInt(binFailValue);
                })
                $tableFinalYield.find('.fail').val(failVal);
            }

            function updateYield() {
                var sumVal = $tableFinalYield.find('.sum').val();
                if (sumVal == 0 || sumVal.trim() === "") {//判断sum为0,则yield为0;
                    $tableFinalYield.find('.yield').val(" %");
                    return;
                }
                var passVal = 0;

                $tableFinalYield.find('.binPass').each(function () {
                    if (this.value.match('-') != null) {
                        return true;
                    }
                    var binPassValue = this.value.trim() === "" ? 0 : this.value.trim();//如果为空则值为0
                    passVal += parseInt(binPassValue);
                })
                var yieldVal = parseFloat(passVal / sumVal).toFixed(5);
                $tableFinalYield.find('.yield').val((yieldVal * 100).toFixed(3) + '%');//百分号后保留3位小数
            }

            $tableFinalYield.find('.binFail').on('change', updateFail);
            $tableFinalYield.find('.binPass').on('change', updateYield);
            $tableFinalYield.find('.sum').on('change', updateYield);//sum值变化
            if ($tableFinalYield.find('.sum').val() == 0) {//判断sum为空,则yield为空;
                $tableFinalYield.find('.yield').val(" %");
            }
        }


        // Testing站点的FT, LAT, RT, EAT
        var generateComposedTestFTResultColumnHeaders = function (ftResult) {
            var columns = [];
            var hiddenColumns = ["bin", "id", "version"];
            var excludeColumns = ["lat"];
            for (var propertyName in ftResult) {
                if (hiddenColumns.indexOf(propertyName) != -1) {
                    var html1 = $('<th class="flag" hidden>' + propertyName + '</th>');
                    columns.push(html1);
                } else if (excludeColumns.indexOf(propertyName) != -1) {

                } else {
                    if(propertyName == "sum"){
                        columns.push($('<th class="flag" style="width:60px;">sum</th>'));
                        columns.push($('<th class="flag" style="width:60px;">bin1</th>'));
                    }else{
                        columns.push($('<th class="flag" style="width:60px;">' + propertyName + '</th>'));
                    }

                }
            }
            return columns;
        }

        // Testing站点的FT, LAT, RT, EAT
        var generateComposedTestFTResultColumns = function (ftResult, Bin1Quality, Bin1Value) {
            var columns = [];
            var excludeColumns = ["lat"];
            for (var propertyName in ftResult) {//加载除了bin外的所有项
                var propertyValue = ftResult[propertyName];
                if (propertyValue === '0') propertyValue = '';
                if (excludeColumns.indexOf(propertyName) != -1) {

                } else {
                    switch (propertyName) {
                        case 'bin':
                            var html2 = $('<td class="flag2" hidden><input type="text" class="flag"></td>');
                            columns.push(html2);
                            break;
                        case 'version':
                        case 'id':
                            var html2 = $('<td hidden><input type="text" class="flag" value=' + propertyValue + '></td>');
                            columns.push(html2);
                            break;
                        case 'yield':
                            if (propertyValue == '') {
                                var html2 = $('<td><input type="text" style="width:50px;" class="flag yield" value=" %" ></td>');
                            }
                            else {
                                var html2 = $('<td><input type="text" style="width:50px;" class="flag yield" value="' + parseFloat(propertyValue) * 100 + '%"></td>');
                            }
                            columns.push(html2);
                            break;
                        case 'fail':
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag fail" value="' + propertyValue + '"></td>');
                            columns.push(html2);
                            break;
                        case 'sum':
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag sum" value="' + propertyValue + '"></td>');
                            var bin1QualityClass = "bin" + Bin1Quality[0].toUpperCase()+ Bin1Quality.substr(1);
                            if (Bin1Value == 0) {
                                var $column = $('<td><input type="text" style="width:50px;" class="flag1 '+ bin1QualityClass +'" value="" ></td>');
                            }
                            else {
                                var $column = $('<td><input type="text" style="width:50px;" class="flag1 '+ bin1QualityClass +'" value="' + Bin1Value + '"></td>');
                            }
                            columns.push(html2);
                            columns.push($column);
                            break;
                        case 'pass':
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag pass" value="' + propertyValue + '"></td>');
                            columns.push(html2);
                            break;
                        default:
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag" value="' + propertyValue + '"></td>');
                            columns.push(html2);
                            break;
                    }
                }
            }
            return columns;
        }

        // Testing站点的FinalYield
        var generateComposedTestFinalYieldColumnHeaders = function (finalYield) {
            var columns = [];
            var hiddenColumns = ["bin", "id", "version"];
            var excludeColumns = [];
            for (var propertyName in finalYield) {
                var propertyValue = finalYield[propertyName];
                if (hiddenColumns.indexOf(propertyName) != -1) {
                    var html1 = $('<th hidden>' + propertyName + '</th>');
                    columns.push(html1);
                } else if (excludeColumns.indexOf(propertyName) != -1) {

                } else {
                    if(propertyName == "sum"){
                        columns.push($('<th class="flag" style="width:60px;">sum</th>'));
                        columns.push($('<th class="flag" style="width:60px;">bin1</th>'));
                    }else{
                        columns.push($('<th class="flag" style="width:60px;">' + propertyName + '</th>'));
                    }
                }
            }
            return columns;
        }

        // Testing站点的FinalYield
        var generateComposedTestFinalYieldColumns = function (finalYield, Bin1Quality, Bin1Value) {
            var columns = []; //
            var excludeColumns = [];
            for (var propertyName in finalYield) {
                var propertyValue = finalYield[propertyName];
                if (propertyValue === '0') propertyValue = '';
                if (excludeColumns.indexOf(propertyName) != -1) {

                } else {
                    switch (propertyName) {
                        case 'bin':
                            var html2 = $('<td class="flag2" hidden><input type="text" class="flag" name="ftTestDTO.finalYield.' + propertyName + '"></td>');
                            columns.push(html2);
                            break;
                        case 'version':
                        case 'id':
                            var html2 = $('<td hidden><input type="text" class="flag" value="' + propertyValue + '" name=ftTestDTO.finalYield.' + propertyName + '></td>');
                            columns.push(html2);
                            break;
                        case 'yield':
                            if (propertyValue == "&ensp;") {
                                var html2 = $('<td><input type="text" style="width:60px;" class="flag yield" value=" %" ></td>');
                            } else {
                                var html2 = $('<td><input type="text" style="width:60px;" class="flag yield" value="' + (parseFloat(propertyValue) * 100).toFixed(3) + '%" name=ftTestDTO.finalYield.' + propertyName + '></td>');
                            }
                            columns.push(html2);
                            break;
                        case 'fail':
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag fail" value="' + propertyValue + '" name=ftTestDTO.finalYield.' + propertyName + '></td>');
                            columns.push(html2);
                            break;
                        case 'sum':
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag sum" value="' + propertyValue + '" name=ftTestDTO.finalYield.' + propertyName + '></td>');
                            var bin1QualityClass = "bin" + Bin1Quality[0].toUpperCase()+ Bin1Quality.substr(1);
                            if (Bin1Value == 0) {
                                var $column = $('<td><input type="text" style="width:50px;" class="flag1 '+ bin1QualityClass +'" value="" ></td>');
                            }
                            else {
                                var $column = $('<td><input type="text" style="width:50px;" class="flag1 '+ bin1QualityClass +'" value="' + Bin1Value + '"></td>');
                            }
                            columns.push(html2);
                            columns.push($column);
                            break;
                        default:
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag" value="' + propertyValue + '" name=ftTestDTO.finalYield.' + propertyName + '></td>');
                            columns.push(html2);
                            break;
                    }
                }
            }
            return columns;
        }

        var generateFTResultColumnHeaders = function (ftStringName, ftResult) {
            var columns = [];
            var hiddenColumns = ["bin", "id", "version"];
            var excludeColumns;
            if (ftStringName != 'ftFinishDTO') {
                excludeColumns = ["lat"];
            }
            for (var propertyName in ftResult) {
                if (propertyName.startsWith('site')) {///finish站点
                    continue;
                }

                var propertyValue = ftResult[propertyName];
                if (hiddenColumns.indexOf(propertyName) != -1) {
                    var html1 = $('<th hidden>' + propertyName + '</th>');
                    columns.push(html1);
                } else if (excludeColumns && excludeColumns.indexOf(propertyName) != -1) {

                } else {
                    if (propertyName == 'resultSum' || propertyName == 'sum') {
                        columns.push($('<th  style="width: 60px;">sum</th>'));
                        columns.push($('<th  style="width: 60px;">bin1</th>'));
                       } else {
                        columns.push($('<th  style="width: 60px;">' + propertyName + '</th>'));
                    }
                }
                              // debugger
            }
            return columns;
        }


        var generateFTResultColumns = function (ftStringName, ftResult, Bin1Quality, Bin1Value) {
            var columns = [];
            var excludeColumns;
            if (ftStringName != 'ftFinishDTO') {
                excludeColumns = ["lat"];
            }
            for (var propertyName in ftResult) {//加载除了bin外的所有项
                var propertyValue = ftResult[propertyName];
                if (excludeColumns && excludeColumns.indexOf(propertyName) != -1) {

                } else {
                    switch (propertyName) {
                        case 'bin':
                            var html2 = $('<td class="flag2" hidden><input type="text" class="flag"  name="' + ftStringName + '.ftResultDTO.' + propertyName + '" ></td>');
                            columns.push(html2);
                            break;
                        case 'version':
                        case 'id':
                            var html2 = $('<td hidden><input type="text" class="flag" value="' + propertyValue + '" name="' + ftStringName + '.ftResultDTO.' + propertyName + '"></td>');
                            columns.push(html2);
                            break;
                        case 'yield':

                            if (propertyValue === '0' || propertyValue === '') {
                                var html2 = $('<td><input type="text" style="width:60px;" class="flag yield" value=" %" name="' + ftStringName + '.ftResultDTO.yield"></td>');
                            }
                            else {
                                var html2 = $('<td><input type="text" style="width:60px;" class="flag yield" value="' + parseFloat(propertyValue) * 100 + '%" name="' + ftStringName + '.ftResultDTO.yield"></td>');
                            }
                            columns.push(html2);
                            break;
                        case 'fail':
                            if (propertyValue === '0' || propertyValue === '') {
                                var html2 = $('<td><input type="text" style="width:50px;" class="flag fail" disabled name="' + ftStringName + '.ftResultDTO.' + propertyName + '"></td>');
                            } else {
                                var html2 = $('<td><input type="text" style="width:50px;" class="flag fail" disabled value="' + propertyValue + '" name="' + ftStringName + '.ftResultDTO.' + propertyName + '"></td>');
                            }
                            columns.push(html2);
                            break;
                        case 'sum':
                            if (propertyValue === '0' || propertyValue === '') {
                                var html2 = $('<td><input type="text" style="width:50px;" class="flag sum"  name="' + ftStringName + '.ftResultDTO.' + propertyName + '"></td>');
                            } else {
                                var html2 = $('<td><input type="text" style="width:50px;" class="flag sum" value="' + propertyValue + '" name="' + ftStringName + '.ftResultDTO.' + propertyName + '"></td>');
                            }
                            var bin1QualityClass = "bin" + Bin1Quality[0].toUpperCase()+ Bin1Quality.substr(1);
                            if (Bin1Value == 0) {
                                var $column = $('<td><input type="text" style="width:50px;" class="flag1 '+ bin1QualityClass +'" value="" ></td>');
                            }
                            else {
                                var $column = $('<td><input type="text" style="width:50px;" class="flag1 '+ bin1QualityClass +'" value="' + Bin1Value + '"></td>');
                            }
                            columns.push(html2);
                            columns.push($column);
                            break;
                        default:
                            if (propertyValue === '0' || propertyValue === '') {
                                var html2 = $('<td><input type="text" style="width:50px;" class="flag" name="' + ftStringName + '.ftResultDTO.' + propertyName + '"></td>');
                            } else {
                                var html2 = $('<td><input type="text" style="width:50px;" class="flag" value="' + propertyValue + '" name="' + ftStringName + '.ftResultDTO.' + propertyName + '"></td>');
                            }
                            columns.push(html2);
                            break;
                    }
                }
            }
            return columns;
        }
        var generateFTFinishStatictisColumns = function (ftStringName, ftResult, Bin1Quality, Bin1Value) {
            var columns = [];

            for (var propertyName in ftResult) {//加载除了bin外的所有项
                debugger;
                var propertyValue = ftResult[propertyName];
                switch (propertyName) {
                    case 'site1Name':
                    case 'site1Name':
                    case 'site1Quality':
                    case 'site2Name':
                    case 'site2Quality':
                    case 'site3Name':
                    case 'site3Quality':
                    case 'site4Name':
                    case 'site4Quality':
                    case 'site5Name':
                    case 'site5Quality':
                    case 'site6Name':
                    case 'site6Quality':
                    case 'site7Name':
                    case 'site7Quality':
                    case 'site8Name':
                    case 'site8Quality':
                    case 'site9Name':
                    case 'site9Quality':
                    case 'site10Name':
                    case 'site10Quality':
                    case 'site11Name':
                    case 'site11Quality':
                    case 'site12Name':
                    case 'site12Quality':
                    case 'site13Name':
                    case 'site13Quality':
                    case 'site14Name':
                    case 'site14Quality':
                    case 'site15Name':
                    case 'site15Quality':
                    case 'version':
                    case 'id':
                        var html2 = $('<td hidden><input type="text" class="flag" value="' + propertyValue + '" name="ftFinishDTO.ftStatisticsDTO.' + propertyName + '"></td>');
                        columns.push(html2);
                        break;
                    case 'yield':
                        if (propertyValue === '0' || propertyValue === '') {
                            var html2 = $('<td><input type="text" style="width:60px;" class="flag yield" value=" %" name="ftFinishDTO.ftStatisticsDTO.yield"></td>');
                        }
                        else {
                            var html2 = $('<td><input type="text" style="width:60px;" class="flag yield" value="' + (parseFloat(propertyValue) * 100).toFixed(3) + '%" name="ftFinishDTO.ftStatisticsDTO.yield"></td>');
                        }
                        columns.push(html2);
                        break;
                    case 'fail':
                        if (propertyValue === '0' || propertyValue === '') {
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag fail" readonly="readonly" name="ftFinishDTO.ftStatisticsDTO.fail"></td>');
                        } else {
                            var html2 = $('<td><input type="text" style="width:50px;"  name="ftFinishDTO.ftStatisticsDTO.fail" class="flag fail" readonly="readonly" value="' + propertyValue + '"></td>');
                        }
                        columns.push(html2);
                        break;
                    case 'resultSum':
                        if (propertyValue === '0' || propertyValue === '') {
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag sum" name="ftFinishDTO.ftStatisticsDTO.resultSum"></td>');
                        } else {
                            var html2 = $('<td><input type="text" style="width:50px;"  name="ftFinishDTO.ftStatisticsDTO.resultSum" class="flag sum" value="' + propertyValue + '"></td>');
                        }
                        var bin1QualityClass = "bin" + Bin1Quality[0].toUpperCase()+ Bin1Quality.substr(1);
                        if (Bin1Value == 0) {
                            var $column = $('<td><input type="text" style="width:50px;" class="flag1 '+ bin1QualityClass +'" value="" ></td>');
                        }
                        else {
                            var $column = $('<td><input type="text" style="width:50px;" class="flag1 '+ bin1QualityClass +'" value="' + Bin1Value + '"></td>');
                        }
                        columns.push(html2);
                        columns.push($column);
                        break;
                    case 'site1Num':
                    case 'site2Num':
                    case 'site3Num':
                    case 'site4Num':
                    case 'site5Num':
                    case 'site6Num':
                    case 'site7Num':
                    case 'site8Num':
                    case 'site9Num':
                    case 'site10Num':
                    case 'site11Num':
                    case 'site12Num':
                    case 'site13Num':
                    case 'site14Num':
                    case 'site15Num':
                        break;
                    default:
                        if (propertyValue === '0' || propertyValue === '') {
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag" value="" name="ftFinishDTO.ftStatisticsDTO.' + propertyName + '"></td>');
                        } else {
                            var html2 = $('<td><input type="text" style="width:50px;" class="flag" value="' + propertyValue + '" name= "ftFinishDTO.ftStatisticsDTO.' + propertyName + '"></td>');
                        }
                        columns.push(html2);
                        break;
                }
            }
            return columns;
        }

        var generateBinColumnHeaders = function (sblDTOs) {
            var seen = {};
            return sblDTOs
                    .sort(function (a, b) {
                        return a.type - b.type;
                    })
                    .filter(function (val, index) {//过滤掉重复的bin及Bin1
                        var binType = val.type;
                        if (binType == 1){
                            return false;
                        }
                        if (seen[binType] !== 1) {
                            seen[binType] = 1;
                            return true;
                        }
                        return false;
                    })
                    .map(function (val) {
                        return $('<th style="width:60px;">bin' + val.type + '</th>');
                    });
        }

        var generateBinColumns = function (bins, sblDTOs) {
            var seen = {};
            return sblDTOs
                    .sort(function (a, b) {
                        return a.type - b.type;
                    })
                    .filter(function (val, index) {
                        var binType = val.type;
                        if (binType == 1){
                            return false;
                        }
                        if (seen[binType] !== 1) {
                            seen[binType] = 1;
                            return true;
                        }
                        return false;
                    })
                    .map(function (val) {
                        var binIndex = parseInt(val.type) - 1;
                        if (bins[binIndex] == 0) {
                            var $column = $('<td><input type="text" style="width:50px;" class="flag1" value="" ></td>');
                        }
                        else {
                            var $column = $('<td><input type="text" style="width:50px;" class="flag1" value=' + bins[binIndex] + '></td>');
                        }

                        $column.find("input").addClass(val.quality == "pass" ? "binPass" : "binFail");
                        return $column;
                    });
        }

        var onComposedTestNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO, testDetailToClick) {

            var $ftDetail = $('#ftDetail');
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;//button已在上面定义，全局变量，在修改处要用
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;

            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $ftDetail.html(html);
                var elm;
                for (var index in ftNodeDTO) {
                    if (ftNodeDTO[index] != null) {
                        elm = $ftDetail.find('#' + index + 'ID');
                        elm.val(ftNodeDTO[index]);
                    }
                }
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {
                    for (var index in ftNodeDTO[ftStringName]) {
                        var input = ftNodeDTO[ftStringName];
                        if (input[index] != null) {
                            $ftDetail.find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                }

                // 组合测试流程结点
                var testNames = ftNodeDTO['ftTestDTO']['orderInfo'].split('|');//流程节点
                var hasLAT = testNames.indexOf("LAT") != -1;
                for (var i = 0; i < testNames.length; i++) {//以ft为例
                    var testName = testNames[i];
                    if (testName != 'RT') {
                        var tests = ftNodeDTO['ftTestDTO'][testName.toLowerCase() + 'List'];//ft节点对应的子包含有几个ft,ft1,ft2....

                        var $testTable = $('#' + testName.toLowerCase() + ' table');
                        var $connect = $ftDetail.find('#connect');

                        var html5 = $('<li><a data-toggle="tab" href="#' + testName.toLowerCase() + '">' + testName + '</a></li>');//加载Fttab
                        var head1 = $('<tr><th></th></tr>');

                        $connect.append(html5);
                        $testTable.append(head1);

                        // 加载除了bin外的所有项
//                    debugger
                        $testTable.find("tr:first").append(generateComposedTestFTResultColumnHeaders(tests[0]));
                        $testTable.find('tr:first').append(generateBinColumnHeaders(sblDTOs));
                        for (var j = 0; j < tests.length; j++) {//ft1
//                        debugger
                            $testTable.append($('<tr><td>' + testName + (parseInt(j) + 1) + '</td></tr>')); //加载表格名FT1
                            $testTable.find('tr:last').append(generateComposedTestFTResultColumns(tests[j],sblDTOs[0].quality,tests[j]['bin'][0]));
                            $testTable.find('tr:last').append(generateBinColumns(tests[j]['bin'], sblDTOs));
                            registerOnFinalYieldChangedListener($testTable.find('tr:last'));
                        }
                    } else {
                        var tests = ftNodeDTO['ftTestDTO']['rtList'];//ft节点对应的子包含有几个ft,ft1,ft2....

                        var $testTable = $('#ft table');
                        // 加载除了bin外的所有项
                        for (var j = 0; j < tests.length; j++) {//ft1
//                        debugger
                            $testTable.append($('<tr><td> RT' + (parseInt(j) + 1) + '</td></tr>')); //加载表格名FT1
                            $testTable.find('tr:last').append(generateComposedTestFTResultColumns(tests[j],sblDTOs[0].quality,tests[j]['bin'][0]));
                            $testTable.find('tr:last').append(generateBinColumns(tests[j]['bin'], sblDTOs));
                            registerOnFinalYieldChangedListener($testTable.find('tr:last'));
                        }
                    }

                }
                var html6 = $('<li><a data-toggle="tab" href="#finalYield">final Yield</a></li>');
                $ftDetail.find('#connect').append(html6);
                $ftDetail.find('#connect li:first a').click();
                $ftDetail.find('#connect li:first').addClass('active');

                var finalYield = ftNodeDTO['ftTestDTO']['finalYield'];
                var $tableFinalYield = $ftDetail.find('#finalYield table');


                $tableFinalYield.find('tr:first').append(generateComposedTestFinalYieldColumnHeaders(finalYield));
                $tableFinalYield.find('tr:last').append(generateComposedTestFinalYieldColumns(finalYield,sblDTOs[0].quality,finalYield['bin'][0]));

                $tableFinalYield.find('tr:first').append(generateBinColumnHeaders(sblDTOs));
                $tableFinalYield.find('tr:last').append(generateBinColumns(finalYield['bin'], sblDTOs));

                registerOnFinalYieldChangedListener($tableFinalYield);
                $ftDetail.find("a[href='#" + testDetailToClick + "']").click();//保存后模拟点击事件跳到当前的tab页
                var startButton = $('<button type="button" class="btn btn-default" id="start" style="margin-left: 300px;">进站</button>')
                $ftDetail.append(startButton);
                $ftDetail.find('#start').on('click', function () {
                    if (!Validator.Validate($ftDetail.find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });
                if (ftNodeDTO.ftState == 3) {
                    $ftDetail.find('#start').css('display', 'none');
                    $ftDetail.find('#end').removeClass('hidden');
                    $ftDetail.find('#end').attr('disabled', true);
                    $ftDetail.find('[name="save"]').removeClass('hidden');
                    $ftDetail.find('[name="save"]').attr('disabled', true);
                    $('#recordAdd').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 2) {
                    $ftDetail.find('#start').css('display', 'none');
                    $ftDetail.find('#end').removeClass('hidden');
                    $ftDetail.find('[name="save"]').removeClass('hidden');

                    $ftDetail.find('input').attr('disabled', false);
                    $ftDetail.find('.yield').attr('disabled', true);
                    $ftDetail.find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $ftDetail.find('input').attr('disabled', true);
                    $ftDetail.find('textarea').attr('disabled', true);
                    $ftDetail.find('select').attr('disabled', true);
                }
                var listValue = '';

                function dealDataToSave() {
                    $ftDetail.find('table .yield').each(function () {
                        var yieldPostVal = $(this).val() == ' %' ? '0' : $(this).val();
                        $(this).val(parseInt(yieldPostVal) / 100);
                    });
                    $.each($('#ftDetail').find('table .flag'), function () {
                        this.value = this.value == false ? '0' : this.value;
                    });
                    for (var i = 0; i < testNames.length; i++) {
                        var list1Value = [];
                        var list3Value = {};
                        var list = ftNodeDTO['ftTestDTO'][testNames[i].toLowerCase() + 'List'];
                        var list3Name = 'ftTestDTO.' + testNames[i].toLowerCase() + 'List';
                        if (testNames.indexOf('FT') > -1 && testNames[i] == 'RT') {//rt在ft的tab
                            $.each($('#ft').find('th+.flag'), function () {
                                list1Value.push(this.innerHTML);
                            });
                        } else {
                            $.each($('#' + testNames[i].toLowerCase()).find('th+.flag'), function () {
                                list1Value.push(this.innerHTML);
                            });
                        }
                        for (var tt = 0; tt < list.length; tt++) {
                            var n = 0;
                            var binValuetoSend = list[tt]['bin'];
                            var binValueGet = [];
                            var list2Value = [];

                            var list3Name = 'ftTestDTO.' + testNames[i].toLowerCase() + 'List';
                            if (testNames.indexOf('FT') > -1 && testNames[i] == 'RT') {
                                var len = ftNodeDTO['ftTestDTO']['ftList'].length;
                                var orderObj = $('#ft').find('tr')[parseInt(tt) + 1 + len];
                            } else {
                                var orderObj = $('#' + testNames[i].toLowerCase()).find('tr')[parseInt(tt) + 1];
                            }
                            $.each($(orderObj).find('.flag1'), function () {
                                var binValueDeal = this.value == false ? '0' : this.value;
                                binValueGet.push(binValueDeal);
                            });
                            for (var k = 0; k < binValuetoSend.length; k++) {
                                if (binValuetoSend[k] != '-1') {
                                    binValuetoSend[k] = binValueGet[n++];
                                }
                            }
                            $(orderObj).find('.flag2 input').val(binValuetoSend);

                            $.each($(orderObj).find('td .flag'), function () {
                                list2Value.push(this.value);
                            });
                            for (var m = 0; m < list1Value.length; m++) {
                                list3Value[list3Name + '[' + tt + ']' + '.' + list1Value[m]] = list2Value[m];
                            }
                        }
                        listValue += $.param(list3Value) + '&';
                    }
                    var m = 0;
                    var binfinalYieldSend = ftNodeDTO['ftTestDTO']['finalYield']['bin'];
                    var binfinalYieldGet = [];
                    $.each($('#finalYield table').find('td .flag1'), function () {
                        var binValueDeal = this.value == false ? '0' : this.value;
                        binfinalYieldGet.push(binValueDeal);
                    });
                    $.each($('#finalYield').find('table .flag'), function () {
                        this.value = this.value == false ? '0' : this.value;
                    });
                    for (var k = 0; k < binfinalYieldSend.length; k++) {
                        if (binfinalYieldSend[k] != '-1') {
                            binfinalYieldSend[k] = binfinalYieldGet[m++];
                        }
                    }
                    $('#finalYield').find('tr .flag2 input').val(binfinalYieldSend);
                }

                $ftDetail.find('[name="save"]').on('click', function () {
                    var testDetailStr = this.getAttribute("id");
                    var testDetailId = testDetailStr.substr(0, testDetailStr.length - 1);
                    dealDataToSave();
                    var json = {
                        'processId': FTProcessDTO.id,
                        'isModify': 1,
                        'ftTestDTO.finalYield.yield': $('#finalYield').find('table .yield').val(),
                        'ftTestDTO.finalYield.fail': $('#finalYield').find('table .fail').val()
                    }

                    $.post('${pageContext.request.contextPath}/FTLotNodeOption/saveFTNode.koala',
                                    listValue + $.param(json) + '&' + $ftDetail.find('form').serialize())
                            .done(function (result) {
                                if (result.success) {
                                    refreshDetail(checkedId, nodeName, testDetailId);
                                    isModify = 0;
                                    $('#updateftList').addClass('btn-danger').removeClass('btn-default');
                                    grid.getGrid().refresh();
                                    grid.message({
                                        type: 'success',
                                        content: '保存成功'
                                    })
                                } else {
                                    grid.message({
                                        type: 'error',
                                        content: result.errorMessage
                                    });
                                }
                            });
                });
                $ftDetail.find('#end').on('click', function () {
                    dealDataToSave();
                    /*                    $ftDetail.find('table .yield').each(function () {
                     //
                     var yieldPostVal = $(this).val() == ' %' ? '0': $(this).val();
                     $(this).val(parseInt(yieldPostVal) / 100);
                     });
                     var listValue = '';
                     for (var i = 0; i < testNames.length; i++) {
                     var list1Value = [];
                     var list3Value = {};
                     var list = ftNodeDTO['ftTestDTO'][testNames[i].toLowerCase() + 'List'];
                     var list3Name = 'ftTestDTO.' + testNames[i].toLowerCase() + 'List';
                     $.each($('#' + testNames[i].toLowerCase()).find('th+.flag'), function () {
                     list1Value.push(this.innerHTML);
                     });
                     for (var tt = 0; tt < list.length; tt++) {
                     var n = 0;
                     var binValuetoSend = list[tt]['bin'];
                     var binValueGet = [];
                     var list2Value = [];
                     var list3Name = 'ftTestDTO.' + testNames[i].toLowerCase() + 'List';
                     var orderObj = $('#' + testNames[i].toLowerCase()).find('tr')[parseInt(tt) + 1];
                     $.each($(orderObj).find('.flag1'), function () {
                     binValueGet.push(this.value);
                     })
                     for (var k = 0; k < binValuetoSend.length; k++) {
                     if (binValuetoSend[k] != '-1') {
                     binValuetoSend[k] = binValueGet[n++];
                     }
                     }
                     $(orderObj).find('.flag2 input').val(binValuetoSend);

                     $.each($(orderObj).find('td .flag'), function () {
                     list2Value.push(this.value);
                     });
                     for (var m = 0; m < list1Value.length; m++) {
                     list3Value[list3Name + '[' + tt + ']' + '.' + list1Value[m]] = list2Value[m];
                     }
                     }
                     listValue += $.param(list3Value) + '&';
                     var m = 0;
                     var binfinalYieldSend = ftNodeDTO['ftTestDTO']['finalYield']['bin'];
                     var binfinalYieldGet = [];
                     $.each($('#finalYield table').find('td .flag1'), function () {
                     binfinalYieldGet.push(this.value);
                     });
                     for (var k = 0; k < binfinalYieldSend.length; k++) {
                     if (binfinalYieldSend[k] != '-1') {
                     binfinalYieldSend[k] = binfinalYieldGet[m++];
                     }
                     }
                     $('#finalYield').find('tr .flag2 input').val(binfinalYieldSend);
                     }*/
                    var json = {
                        'processId': FTProcessDTO.id,
                        'isModify': 0,
                        'ftTestDTO.finalYield.yield': $('#finalYield').find('table .yield').val(),
                        'ftTestDTO.finalYield.fail': $('#finalYield').find('table .fail').val()
                    };
                    $.post('${pageContext.request.contextPath}/FTLotNodeOption/saveFTNode.koala',
                                    listValue + $.param(json) + '&' + $ftDetail.find('form').serialize())
                            .done(function (result) {
                                if (!result.success) {
                                    grid.message({
                                        type: 'error',
                                        content: result.errorMessage
                                    });
                                    return;
                                }
                                refreshDetail(checkedId, nodeName);
                                isModify = 0;
                                $('#updateftList').addClass('btn-danger').removeClass('btn-default');
                                grid.message({
                                    type: 'success',
                                    content: '保存成功'
                                });

                                $.post('${pageContext.request.contextPath}/FTLotNodeOption/endFTNode.koala', listValue + $.param(json) + '&' + $ftDetail.find('form').serialize()).done(function (result) {
                                    if (result.success) {
                                        refreshDetailAndButtonList(checkedId);
                                        grid.getGrid().refresh();
                                        grid.message({
                                            type: 'success',
                                            content: '出站成功'
                                        })
                                    } else {
                                        grid.message({
                                            type: 'error',
                                            content: '出站失败' + result.errorMessage
                                        });

                                    }
                                })
                            });
                })
            });
        };

        var onIQCNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $('#ftDetail').html(html);
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
                    $('#ftDetail').find('table:first tr:first').append(generateFTResultColumnHeaders(ftStringName, ftResult));

                    $('#ftDetail').find('table:first tr:last').append(generateFTResultColumns(ftStringName, ftResult, sblDTOs[0].quality, ftResult['bin'][0]));

                    $('#ftDetail').find('table:first tr:first').append(generateBinColumnHeaders(sblDTOs));

                    $('#ftDetail').find('table:first tr:last').append(generateBinColumns(ftResult['bin'], sblDTOs));

                    registerOnFinalYieldChangedListener($('#ftDetail').find('table:first'));
                }

                if (ftNodeDTO.ftState == 3) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);

                }
                if (ftNodeDTO.ftState == 2) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#ftDetail').find('input').attr('disabled', false);
                    $('#ftDetail').find('.yield').attr('disabled', true);
                    $('#ftDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $('#ftDetail').find('input').attr('disabled', true);
                    $('#ftDetail').find('textarea').attr('disabled', true);
                    $('#ftDetail').find('select').attr('disabled', true);
                }


                $('#ftDetail').find('#reelCodeUpload').change(function (e) {
                    e.preventDefault();
                    var fd = new FormData();
                    fd.append('excel', $('#reelCodeUpload')[0].files[0]);
                    //var data1 = [{name: 'multipartFile',value: fd},{name: 'ftProcessId', value: FTProcessDTO.id}];
                    fd.append('pid', FTProcessDTO.id);
                    $.ajax({
                        type: 'post',
                        url: '${pageContext.request.contextPath}/FTLotNodeOption/importReelCode.koala',
                        data: fd,
                        processData: false,
                        contentType: false,
                        cache: false,
                        success: function (msg) {
                            if (msg.success == true) {
                                $('#ftDetail').find('#ftIQCDTO_reelCodeID').val(msg.data);
                                grid.message({
                                    type: 'success',
                                    content: '上传reelCode成功'
                                });
                            } else {
                                grid.message({
                                    type: 'error',
                                    content: msg.errorMessage
                                });
                            }
                        },
                        error: function (data) {
                            console.log('error');
                            grid.message({
                                type: 'error',
                                content: '上传reelCode失败'
                            })
                        }
                    });
                });

                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                    //
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });
                function collectData() {
                    if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                        var n = 0;
                        var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                        var binValueGet = [];
                        $.each($('#ftDetail').find('table td .flag1'), function () {
                            var binValueDeal = this.value == false ? '0' : this.value;
                            binValueGet.push(binValueDeal);
                        });
                        for (var k = 0; k < binValuetoSend.length; k++) {
                            if (binValuetoSend[k] != '-1') {
                                binValuetoSend[k] = binValueGet[n++];
                            }
                        }

                        $('#ftDetail').find('table .flag2 input').val(binValuetoSend);
                        $.each($('#ftDetail').find('table .flag'), function () {
                            this.value = this.value == false ? '0' : this.value;
                        });
                        var yieldPostVal = $('#ftDetail').find('table .yield').val();
                        yieldPostVal = yieldPostVal === " %" ? '0' : yieldPostVal;
                        var failPostVal = $('#ftDetail').find('table .fail').val();
                        // failPostVal = failPostVal == false ? '0' : failPostVal;
                        //$('#ftDetail').find('table .yield').val(parseInt(yieldPostVal)/100);
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

                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNode(checkedId, json, nodeName);
                });
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    //	$('#ftDetail').find("button:nth-last-child(1),button:nth-last-child(2)").css('display','inline-block');
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNodeThenEndNode(checkedId, json, nodeName);
                });
            });
        };

        var onBakingNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $('#ftDetail').html(html);
                var contents = [{title: '请选择', value: ''}];//添加烤箱编号接口维护
                $.ajax({
                    url: '${pageContext.request.contextPath}/FTLotNodeOption/getOven.koala',
                    type: 'GET',
                    dataType: 'json',
                    async: false
                }).done(function (msg) {
                    for (var i = 0; i < msg.length; i++) {
                        contents.push({
                            title: msg[i],
                            value: msg[i]
                        });
                    }
                    $('#ftDetail').find('#ftBakingDTO_ovenNumberID').select({
                        title: '请选择',
                        contents: contents
                    }).on('change', function () {
                        $('#ftDetail').find('#ftBakingDTO_ovenNumberID_').val($(this).getValue());
                    });
                });
                var elm;
                debugger
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
                            elm = $('#ftDetail').find('#' + ftStringName + '_' + index + 'ID');
                            debugger
                            if (elm.hasClass('select')) {
                                // setValue()失效，没有找到原因。
                                elm.find("button:first-child").text(input[index]);
                            } else {
                                elm.val(input[index]);
                            }
                        }
                    }
                } else {

                }

                //
                if (ftNodeDTO.hasOwnProperty(ftStringName) && ftNodeDTO[ftStringName] && ftNodeDTO[ftStringName].ftResultDTO) {

                    var $tableFirst = $('#ftDetail').find('table:first');
                    var finalYield = ftNodeDTO[ftStringName]['ftResultDTO'];
                    $tableFirst.find('tr:first').append(generateFTResultColumnHeaders(ftStringName, finalYield));
                    $tableFirst.find('tr:last').append(generateFTResultColumns(ftStringName, finalYield, sblDTOs[0].quality, finalYield['bin'][0]));
                    $tableFirst.find('tr:first').append(generateBinColumnHeaders(sblDTOs));
                    $tableFirst.find('tr:last').append(generateBinColumns(finalYield['bin'], sblDTOs));
                    registerOnFinalYieldChangedListener($tableFirst);

                    // 进炉时间
                    if ($('#ftDetail').find('#ftBakingDTO_timeInID').val() == 'null') {
                        $('#ftDetail').find('#ftBakingDTO_timeInID').val('');
                    }

                    // 出炉时间
                    if ($('#ftDetail').find('#ftBakingDTO_timeOutID').val() == 'null') {
                        $('#ftDetail').find('#ftBakingDTO_timeOutID').val('');
                    }
                }
                if (ftNodeDTO.ftState == 3) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 2) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#ftDetail').find('input').attr('disabled', false);
                    $('#ftDetail').find('.yield').attr('disabled', true);
                    $('#ftDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('input').attr('disabled', true);
                    $('#ftDetail').find('textarea').attr('disabled', true);
                    $('#ftDetail').find('select').attr('disabled', true);
                }


                var setTimeOut;
                var timeIn;
                var $endButton = $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)");
                var $timeIn = $('#ftDetail').find('#ftBakingDTO_timeInID');
                var $bakingAlert = $('#ftDetail').find('#bakingAlert');
                var $timeLimit = $('#ftBakingDTO_timeLimitID');
                var $timeOut = $('#ftDetail').find('#ftBakingDTO_timeOutID');
                var $startButton = $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)");

                function checkBackingLimit(timeIn, timeLimit) {
                    debugger
                    var now = new Date();
                    var timeElapsed = now.getTime() - timeIn.getTime();//两次时间差
                    var timeRemain = timeLimit * 3600 * 1000 - timeElapsed;//时间差
                    if (timeRemain > 0) {
                        $endButton.attr('disabled', true);
                    }
                    setTimeOut = setTimeout(function () {
                        $endButton.attr('disabled', false);
                    }, timeRemain);
                }

                checkBackingLimit(new Date($timeIn.val()), $timeLimit.val());

                $('#gainTimein').on('click', function () {
                    var timeIn = new Date();
                    $timeIn.val(timeIn.format('isoDate') + ' ' + timeIn.format('isoTime'));
                    checkBackingLimit(timeIn, $timeLimit.val());
                });
                $timeLimit.on('change', function () {
                    checkBackingLimit(new Date($timeIn.val()), $(this).val());
                })
                $('#gainTimeout').on('click', function () {
                    clearTimeout(setTimeOut);
                    var timeOut = new Date();
                    $timeOut.val(timeOut.format('isoDate') + ' ' + timeOut.format('isoTime'));
                });
                debugger;
                $startButton.on('click', function () {
                    //
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });

                function collectData() {
                    if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                        var n = 0;
                        var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                        var binValueGet = [];
                        $.each($('#ftDetail').find('table td .flag1'), function () {
                            var binValueDeal = this.value == false ? '0' : this.value;
                            binValueGet.push(binValueDeal);
                        });
                        for (var k = 0; k < binValuetoSend.length; k++) {
                            if (binValuetoSend[k] != '-1') {
                                binValuetoSend[k] = binValueGet[n++];
                            }
                        }
                        $('#ftDetail').find('table .flag2 input').val(binValuetoSend);
                        $.each($('#ftDetail').find('table .flag'), function () {
                            this.value = this.value == false ? '0' : this.value;
                        });
                        var yieldPostVal = $('#ftDetail').find('table .yield').val();
                        yieldPostVal = yieldPostVal === " %" ? '0' : yieldPostVal;
                        var failPostVal = $('#ftDetail').find('table .fail').val();
                        /*       var yieldPostVal = $('#ftDetail').find('table .yield').val();
                         var failPostVal = $('#ftDetail').find('table .fail').val();
                         */
                        //$('#ftDetail').find('table .yield').val(parseInt(yieldPostVal)/100);
                        if (buttonName == 'IQC') {

                        } else if (buttonName == 'Baking') {
                            var json = {
                                'processId': FTProcessDTO.id,
                                'isModify': 0,
                                'ftBakingDTO.ftResultDTO.yield': parseInt(yieldPostVal) / 100,
                                'ftBakingDTO.ftResultDTO.fail': parseInt(failPostVal) / 100
                            };
                        } else {

                        }
                    } else {
                        var json = {'processId': FTProcessDTO.id, 'isModify': 0};
                    }
                    return json;
                }

                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNode(checkedId, json, nodeName);
                });
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNodeThenEndNode(checkedId, json, nodeName);
                });
            });
        };

        var onGuTestNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var list1 = [];
            var list2 = [];
            var list3 = [];
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $('#ftDetail').html(html);
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
                } else {

                }
                //
                // 组合测试流程结点
                if ((buttonName == 'GuTest') && (FTProcessDTO['ftNodeDTOs'][0]['ftIQCDTO'])) {
                    for (var index in FTProcessDTO['ftNodeDTOs'][0]['ftIQCDTO']['ftResultDTO']['bin']) {
                        var input = FTProcessDTO['ftNodeDTOs'][0]['ftIQCDTO']['ftResultDTO']['bin'];
                        if (input[index] != '-1') {
                            if ((ftNodeDTO.ftState == 1) || (ftNodeDTO.ftState == 2)) {
                                input[index] = '';
                            }
                            var binType = parseInt(index) + 1;
                            var html1 = $('<th style="width:60px;">bin' + binType + '</th>');
                            var html2 = $('<td><input type="text" style="width:50px;" value=' + input[index] + '></td>');
                            var html3 = $('<th style="width:60px;">bin' + binType + '</th>');
                            var html4 = $('<td><input type="text" style="width:50px;" value=' + input[index] + '></td>');
                            $('#ftDetail').find('#ftGuTestDTO_standardResult tr:first').append(html1);
                            $('#ftDetail').find('#ftGuTestDTO_standardResult tr:last').append(html2);
                            $('#ftDetail').find('#ftGuTestDTO_record tr:first').append(html3);
                            $('#ftDetail').find('#ftGuTestDTO_record tr:not(:first)').append(html4);
                        }
                    }
                    if (ftNodeDTO['ftGuTestDTO']['standardResult'] != null) {
                        var standardResult = ftNodeDTO['ftGuTestDTO']['standardResult'].split(',');
                        var record = ftNodeDTO['ftGuTestDTO']['record'].split(',');
                        if ((ftNodeDTO.ftState == 3) || (ftNodeDTO.ftState == 2)) {
                            var len = (record.length / standardResult.length) - 4;
                            if (len > 0) {
                                while (len--) {
                                    $('#ftGuTestDTO_record').append($('#ftGuTestDTO_record').find('tr:last').clone());
                                }
                            }
                        }
                        $('#ftGuTestDTO_standardResult').find('input').each(function (i) {
                            this.value = standardResult[i];
                        });
                        $('#ftGuTestDTO_record').find('input').each(function (i) {
                            this.value = record[i];
                        });
                        var nox = ftNodeDTO['ftGuTestDTO']['nox'].split(',');
                        $('#ftGuTestDTO_nox').find('input').each(function (i) {
                            this.value = nox[i];
                        });
                    }
                }
                if (ftNodeDTO.ftState == 3) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 2) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#ftDetail').find('input').attr('disabled', false);
                    $('#ftDetail').find('.yield').attr('disabled', true);
                    $('#ftDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $('#ftDetail').find('input').attr('disabled', true);
                    $('#ftDetail').find('textarea').attr('disabled', true);
                    $('#ftDetail').find('select').attr('disabled', true);
                }
                $('#ftDetail').find('#recordAdd').on('click', function () {
                    $('#ftGuTestDTO_record').append($('#ftGuTestDTO_record').find('tr:last').clone());
                });
                if (buttonName == 'Test') {

                } else {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                        //
                        if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                        startNode(checkedId, FTProcessDTO, nodeName);
                    });

                    function collectData() {
                        if (ftNodeDTO.name == 'GuTest') {

                            $('#ftGuTestDTO_standardResult').find('input').each(function () {
                                list1.push(this.value);
                            });
                            $('#ftGuTestDTO_record').find('input').each(function () {
                                list2.push(this.value);
                            });
                            $('#ftGuTestDTO_nox').find('input').each(function () {
                                list3.push(this.value);
                            });
                            var json = {
                                'processId': FTProcessDTO.id,
                                'isModify': 0,
                                'ftGuTestDTO.standardResult': list1.join(','),
                                'ftGuTestDTO.record': list2.join(','),
                                'ftGuTestDTO.nox': list3.join(',')
                            };
                        }
                        return json;
                    }

                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                        if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                        var json = collectData();
                        saveNode(checkedId, json, nodeName);
                    });
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                        if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                        var json = collectData();
                        saveNodeThenEndNode(checkedId, json, nodeName);
                    });
                }
            });
        };

        var onFinishNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {

            var $ftDetail = $('#ftDetail');
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            var passSBLs = sblDTOs.filter(function (bin) {
                return bin.quality == "pass";
            });
            var failSBLs = sblDTOs.filter(function (bin) {
                return bin.quality == "fail";
            });

            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $ftDetail.html(html);
                var elm;
                for (var index in ftNodeDTO) {
                    if (ftNodeDTO[index] != null) {
                        elm = $ftDetail.find('#' + index + 'ID');
                        elm.val(ftNodeDTO[index]);
                    }
                }
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {
                    for (var index in ftNodeDTO[ftStringName]) {
                        var input = ftNodeDTO[ftStringName];
                        if (input[index] != null) {
                            $ftDetail.find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                }

                // 组合测试流程结点
                if (ftNodeDTO.hasOwnProperty(ftStringName) && ftNodeDTO[ftStringName] && ftNodeDTO[ftStringName].ftResultDTO) {

                    var $tableFinalYield = $ftDetail.find("#tableFinalYield");
                    var $tableSecond = $ftDetail.find("#tableStatistics");
                    var $tableReelDisk = $ftDetail.find("#tableReelDisk");
                    var $tableReelDiskStatic = $ftDetail.find("#tableReelDiskStatic");
                    var $tableIntegratedReelDisk = $ftDetail.find("#tableIntegratedReelDisk");

                    var finalYield = ftNodeDTO[ftStringName]['ftResultDTO'];
                    debugger;
                    $tableFinalYield.find('tr:first').append(generateFTResultColumnHeaders(ftStringName, finalYield));

                    $tableFinalYield.find('tr:last').append(generateFTResultColumns(ftStringName, finalYield, sblDTOs[0].quality, finalYield['bin'][0]));

                    $tableFinalYield.find('tr:first').append(generateBinColumnHeaders(sblDTOs));
                    $tableFinalYield.find('tr:last').append(generateBinColumns(finalYield['bin'], sblDTOs));
                    registerOnFinalYieldChangedListener($tableFinalYield);

                    // 加载ReelDisk
                    reelDisk($tableReelDisk, $tableReelDiskStatic, $tableIntegratedReelDisk, FTProcessDTO, checkedId);

                    // 数量统计
                    //     $.get('${pageContext.request.contextPath}/FTLotNodeOption/quantityStatistics/' + FTProcessDTO.id + '.koala').done(function (statistics) {

                    var ftResult = ftNodeDTO[ftStringName]['ftStatisticsDTO'];
                    $tableSecond.find('tr:first').append(generateFTResultColumnHeaders(ftStringName, ftResult));
                    $tableSecond.find('tr:last').append(generateFTFinishStatictisColumns(ftStringName, ftResult, sblDTOs[0].quality, finalYield['bin'][0]));
                    $tableSecond.find('.yield').attr('disabled', true);
                    //创建两个大小为15的数组
                    var site = 15;
                    var siteArray = new Array(site);
                    siteArray = [{'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''},
                        {'siteTitle': 'bin', 'siteValue': 0, 'siteQuality': ''}
                    ]
                    //数量统计的标题开头均为bin
                    for (var i = 0; i < site; i++)
                        siteArray[i].siteTitle = 'bin';
                    //初始化数量统计数组
                    for (var i = 0; i < site; i++)
                        siteArray[i].siteValue = 0;
                    //循环填充数组
                    for (var index in ftResult) {
                        switch (index) {
                            case 'site1Name':
                                siteArray[0].siteTitle += ftResult[index];
                                break;
                            case 'site1Num':
                                siteArray[0].siteValue = ftResult[index];
                                break;
                            case 'site1Quality':
                                siteArray[0].siteQuality = ftResult[index];
                                break;
                            case 'site2Name':
                                siteArray[1].siteTitle += ftResult[index];
                                break;
                            case 'site2Num':
                                siteArray[1].siteValue = ftResult[index];
                                break;
                            case 'site2Quality':
                                siteArray[1].siteQuality = ftResult[index];
                                break;
                            case 'site3Name':
                                siteArray[2].siteTitle += ftResult[index];
                                break;
                            case 'site3Num':
                                siteArray[2].siteValue = ftResult[index];
                                break;
                            case 'site3Quality':
                                siteArray[2].siteQuality = ftResult[index];
                                break;
                            case 'site4Name':
                                siteArray[3].siteTitle += ftResult[index];
                                break;
                            case 'site4Num':
                                siteArray[3].siteValue = ftResult[index];
                                break;
                            case 'site4Quality':
                                siteArray[3].siteQuality = ftResult[index];
                                break;
                            case 'site5Name':
                                siteArray[4].siteTitle += ftResult[index];
                                break;
                            case 'site5Num':
                                siteArray[4].siteValue = ftResult[index];
                                break;
                            case 'site5Quality':
                                siteArray[4].siteQuality = ftResult[index];
                                break;
                            case 'site6Name':
                                siteArray[5].siteTitle += ftResult[index];
                                break;
                            case 'site6Num':
                                siteArray[5].siteValue = ftResult[index];
                                break;
                            case 'site6Quality':
                                siteArray[5].siteQuality = ftResult[index];
                                break;
                            case 'site7Name':
                                siteArray[6].siteTitle += ftResult[index];
                                break;
                            case 'site7Num':
                                siteArray[6].siteValue = ftResult[index];
                                break;
                            case 'site7Quality':
                                siteArray[6].siteQuality = ftResult[index];
                                break;
                            case 'site8Name':
                                siteArray[7].siteTitle += ftResult[index];
                                break;
                            case 'site8Num':
                                siteArray[7].siteValue = ftResult[index];
                                break;
                            case 'site8Quality':
                                siteArray[7].siteQuality = ftResult[index];
                                break;
                            case 'site9Name':
                                siteArray[8].siteTitle += ftResult[index];
                                break;
                            case 'site9Num':
                                siteArray[8].siteValue = ftResult[index];
                                break;
                            case 'site9Quality':
                                siteArray[8].siteQuality = ftResult[index];
                                break;
                            case 'site10Name':
                                siteArray[9].siteTitle += ftResult[index];
                                break;
                            case 'site10Num':
                                siteArray[9].siteValue = ftResult[index];
                                break;
                            case 'site10Quality':
                                siteArray[9].siteQuality = ftResult[index];
                                break;
                            case 'site11Name':
                                siteArray[10].siteTitle += ftResult[index];
                                break;
                            case 'site11Num':
                                siteArray[10].siteValue = ftResult[index];
                                break;
                            case 'site11Quality':
                                siteArray[10].siteQuality = ftResult[index];
                                break;
                            case 'site12Name':
                                siteArray[11].siteTitle += ftResult[index];
                                break;
                            case 'site12Num':
                                siteArray[11].siteValue = ftResult[index];
                                break;
                            case 'site12Quality':
                                siteArray[11].siteQuality = ftResult[index];
                                break;
                            case 'site13Name':
                                siteArray[12].siteTitle += ftResult[index];
                                break;
                            case 'site13Num':
                                siteArray[12].siteValue = ftResult[index];
                                break;
                            case 'site13Quality':
                                siteArray[12].siteQuality = ftResult[index];
                                break;
                            case 'site14Name':
                                siteArray[13].siteTitle += ftResult[index];
                                break;
                            case 'site14Num':
                                siteArray[13].siteValue = ftResult[index];
                                break;
                            case 'site14Quality':
                                siteArray[13].siteQuality = ftResult[index];
                                break;
                            case 'site15Name':
                                siteArray[14].siteTitle += ftResult[index];
                                break;
                            case 'site15Num':
                                siteArray[14].siteValue = ftResult[index];
                                break;
                            case 'site15Quality':
                                siteArray[14].siteQuality = ftResult[index];
                                break;
                        }
                    }
                    /*          for (var index = 0; index < ftResult.length; index++) {
                     var input = bins[index];
                     if (input['value'] != '-1') {
                     var siteIndex = parseInt(input['site']) - 1;
                     siteTitle[siteIndex] += ' ' + input['type'];
                     siteValue[siteIndex] += parseInt(input['value']);
                     }
                     }*/
                    //将数据转化为html并嵌入
                    for (var i = 0; i < site; i++) {
                        var siteName = 'site' + parseInt(i + 1) + 'Num';
                        if (siteArray[i].siteQuality.startsWith('PASS')) {
                            var html1 = $('<th style="width:90px;">' + siteArray[i].siteTitle + '</th>');
                            if (siteArray[i].siteValue == 0) {
                                var html2 = $('<td><input class="binPass" type="text" style="width:80px;" value="" name="ftFinishDTO.ftStatisticsDTO.' + siteName + '"></td>');
                            }
                            else {
                                var html2 = $('<td><input class="binPass" type="text" style="width:80px;" value="' + siteArray[i].siteValue + '" name="ftFinishDTO.ftStatisticsDTO.' + siteName + '"></td>');
                            }
                        }
                        else if (siteArray[i].siteQuality == '') {
                            var html1 = $('<th style="width:90px;" hidden>' + siteArray[i].siteTitle + '</th>');
                            var html2 = $('<td hidden><input type="text" style="width:80px;" value="' + siteArray[i].siteValue + '" name="ftFinishDTO.ftStatisticsDTO.' + siteName + '"></td>');
                        } else {
                            var html1 = $('<th style="width:90px;">' + siteArray[i].siteTitle + '</th>');
                            if (siteArray[i].siteValue == 0) {
                                var html2 = $('<td><input class="binFail" type="text" style="width:80px;" value="" name="ftFinishDTO.ftStatisticsDTO.' + siteName + '"></td>');
                            }
                            else {
                                var html2 = $('<td><input class="binFail" type="text" style="width:80px;" value="' + siteArray[i].siteValue + '" name="ftFinishDTO.ftStatisticsDTO.' + siteName + '"></td>');
                            }
                        }
                        $tableSecond.find('tr:first').append(html1);
                        $tableSecond.find('tr:last').append(html2);
                    }
                    //                        debugger
                    registerOnFinalYieldChangedListener($tableSecond);
                    //      });
                    // 添加pass品下拉框
                    $ftDetail.find('select:last').append(siteArray.filter(function (sbl) {
                        return sbl.siteQuality.startsWith("PASS");
                    }).map(function (sbl) {
                        return $("<option class='passSBLOption' value='" + JSON.stringify(sbl) + "'> [pass]bin" + sbl.siteTitle.substr(3) + '</option>')
                    }));
                    // 添加fail品下拉
                    var siteFailArray = siteArray.filter(function (sbl) {
                        return !(sbl.siteQuality.startsWith('PASS') || (sbl.siteQuality === ''))
                    });
                    $ftDetail.find('select:last').append($("<option class='failSBLOption' value='" + JSON.stringify(siteFailArray) + "' >[fail]bin" + siteFailArray.map(function (sbl) {
                                return sbl.siteTitle.substr(3);
                            }).join("|") + '</option>'));
                    // 获取ReelDisk的配置
                    $.get('${pageContext.request.contextPath}/ReelDisk/findReelDiskSettingByFTLotId/' + checkedId + '.koala').done(function (reelDiskSetting) {
                                if (!reelDiskSetting.success) {
                                    return;
                                }
                                reelDiskSetting = reelDiskSetting.data;
                                var fixCode = reelDiskSetting.fixCode;
                                var reelQty = reelDiskSetting.reelQty;
//debugger
                                $ftDetail.find('span:first').html(fixCode);
                                if ((reelQty != '3000') && (reelQty != '1680')) {
                                    var option = $('<option>' + reelQty + '</option>');
                                    $ftDetail.find('select:first').append(option);
                                }
                                $ftDetail.find('select:first').val(reelQty);

                                /*    // 添加pass品下拉框
                                 $ftDetail.find('select:last').append(passSBLs.map(function (sbl) {
                                 return $("<option class='passSBLOption' value='" + JSON.stringify(sbl) + "'> [pass]bin" + sbl.type + '</option>')
                                 }));*/
                                // 添加fail品下拉
                                /*        $ftDetail.find('select:last').append($("<option class='failSBLOption' value='" + JSON.stringify(failSBLs) + "' >[fail]bin" + siteArray.filter(function (sbl) {
                                 return sbl.siteQuality === "fail"
                                 }).map(function (sbl) {
                                 return sbl.siteTitle.substr(3);
                                 }).join("|") + '</option>')); */
                            }
                    );


                    // 拆盘
                    $ftDetail.find('#separate').on('click', function () {
                        var $checkObj = $tableReelDiskStatic.find('tr td input[type=checkbox]:checked')
                        if ($checkObj.length == 0) {
                            grid.message({
                                type: 'warning',
                                content: '请选择一条记录进行拆批'
                            });
                            return;
                        }
                        if ($checkObj.length > 1) {
                            grid.message({
                                type: 'warning',
                                content: '只能选择一条记录进行拆批'
                            });
                            return;
                        }
                        var reelDiskId = $checkObj.val();

                        $.get('${pageContext.request.contextPath}/ReelDisk/get/' + reelDiskId + '.koala').done(function (reelDiskDTO) {
                            reelDiskDTO = reelDiskDTO.data;
                            var quality = reelDiskDTO.quality;
                            // 拆分pass
                            if (quality == "pass") {
                                var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                                        + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                                        + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                                        + '<h4 class="modal-title">拆批</h4></div><div class="modal-body">'
                                        + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                                        + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                                        + '<button type="button" class="btn btn-success" id="confirm">确定</button></div></div>'
                                        + '</div></div>');
                                $.get('<%=path%>/FT_ReelCodeSeparate.jsp').done(function (html) {
                                    dialog.modal({
                                        keyboard: false
                                    }).on({
                                        'hidden.bs.modal': function () {
                                            $(this).remove();
                                        }
                                    }).find('.modal-body').html(html);
                                    dialog.find('#reelcode').val(reelDiskDTO.reelCode);
                                    dialog.find('#quality').val(reelDiskDTO.quality);
                                    dialog.find('#amount').val(reelDiskDTO.quantity);
                                    dialog.find('#confirm').on('click', function () {
                                        var separateData = dialog.find('#separateQty').val();
                                        if ((separateData < 0) || (separateData > reelDiskDTO.quantity)) {
                                            dialog.message({
                                                type: warning,
                                                content: '分拆数量不能小于0或大于总数量'
                                            });
                                            return;
                                        }
                                        var dataSep = {
                                            'reelId': reelDiskDTO.id,
                                            'separateQty': dialog.find('#separateQty').val()
                                        }
                                        $.post('${pageContext.request.contextPath}/ReelDisk/separateReelDisk.koala', dataSep).done(function (results) {
                                            if (results.success) {
                                                dialog.modal('hide');
                                                reelDisk($tableReelDisk, $tableReelDiskStatic, $tableIntegratedReelDisk, FTProcessDTO, checkedId);
                                                dialog.message({
                                                    type: 'success',
                                                    content: '分拆成功'
                                                })
                                            } else {
                                                dialog.message({
                                                    type: 'error',
                                                    content: results.actionError
                                                })
                                            }
                                        })
                                    })
                                })
                                // 拆分fail
                            } else if (quality == "fail") {
                                var failSBLDTOs = JSON.parse($("#ftDetail").find("select:last option:last").val());
                                var binTypes = failSBLDTOs.map(function (failSBLDTO) {
                                    return failSBLDTO.siteTitle.substr(3);
                                });
                                $.get('${pageContext.request.contextPath}/ReelDisk/get/' + reelDiskId + '.koala').done(function (failReelDisk) {
                                    if (!failReelDisk.success) {
                                        return;
                                    }
                                    var failReelDisk = failReelDisk.data;
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
                                                                    if (binValue[binIndex] === "-2") {
                                                                        dialog.find('#tableFailReelDisk tbody tr:eq(0)').append($('<td hidden><input name="bin' + (binIndex + 1) + '" type="text" class="binValue" style="width:50px;" value="' + binValue[binIndex] + '"></td>'));
                                                                    }
                                                                    else if (binValue[binIndex] != "-1") {
                                                                        //dialog.find('#tableFailReelDisk thead tr:eq(0)').append($('<th class="bin">bin' + (binIndex + 1) + '</th>'));
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
                                        dialog.find('#tableFailReelDisk thead tr:eq(0)').append(binTypes.map(function (binType) {
                                                    /* 	if(binType.trim().indexOf(' ') > -1){
                                                     var binTypeArray = binType.trim().split(" ");
                                                     var tdHtml = '<td><input name="bin' + binTypeArray[0] + '" type="text" class="binValue" style="width:50px;" value="0"></td>';
                                                     for(var i=1;i<binTypeArray.length;i++){
                                                     tdHtml += '<input hidden name="bin' + binTypeArray[i] + '" type="text" class="binValue" style="width:50px;" value="0">';
                                                     }
                                                     return $(tdHtml);
                                                     }else{ */
                                            return $('<th style="width:120px">bin' + binType + '</th>');
                                                    //	}

                                        }))

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

                                                //

                                                dialog.find('#separateSave').on('click', function () {
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
                                                    ////

                                                    var params = "reelDiskId=" + failReelDiskId + "&" + "separateBins=" + JSON.stringify(separateBins1);
                                                    $.post('${pageContext.request.contextPath}/ReelDisk/separateFailReelDisk.koala', params).done(function (results) {
                                                        if (results.success) {
                                                            dialog.modal('hide');
                                                            reelDisk($tableReelDisk, $tableReelDiskStatic, $tableIntegratedReelDisk, FTProcessDTO, checkedId);
                                                            dialog.message({
                                                                type: 'success',
                                                                content: '分拆成功'
                                                            })
                                                        } else {
                                                            dialog.message({
                                                                type: 'error',
                                                                content: results.actionError
                                                            })
                                                        }
                                                    });
                                                });
                                            }
                                    );
                                });
                            }
                        });
                    });
                    var itemValue;
                    var flag = 0;
                    $('#ftDetail').find('#saveReel').on('click', function () {
                        /*		var td = [];
                         $.each($('#ftDetail').find('table:last tr td input[type=text]'),function(){
                         td.push($(this).prop('name')+':'+this.value);
                         })*/
                        if (flag == 0) {//标志位，点全后点保存
                            return;
                        }
                        $.post('${pageContext.request.contextPath}/ReelDisk/saveReelDisk.koala', {'json': JSON.stringify(itemValue)}).done(function (results) {
                            if (results.success) {
                                itemValue = "";
                                flag = 0;
                                reelDisk($tableReelDisk, $tableReelDiskStatic, $tableIntegratedReelDisk, FTProcessDTO, checkedId);
                                grid.message({
                                    type: "success",
                                    content: "保存成功"
                                })
                            } else {
                                grid.message({
                                    type: "error",
                                    content: results.errorMessage
                                })
                            }
                        })
                    })
                    // 全
                    $ftDetail.find('#create').on('click', function () {
                        flag = 1;
                        // pass品
                        // 获取被选中的下拉框条目的类型是pass还是fail
                        var qualityClass = $ftDetail.find("select:last option:selected").attr("class");
                        if (qualityClass == "passSBLOption") {
                            var passSBL = JSON.parse($ftDetail.find('select:last').val());
                            var passReelDisk = {
                                "ftLotId": checkedId,
                                "reelNumber": $ftDetail.find('select:first').val(),
                                "binType": passSBL.siteTitle.substr(3)
                            };
                            $.post('${pageContext.request.contextPath}/ReelDisk/previewReelDisk.koala', passReelDisk).done(function (item) {
                                //
                                console.log(item);
                                itemValue = item;
                                $tableReelDisk.html('');
                                if ($tableReelDiskStatic.find('tr').length === 0) {
                                    var html = $('<tr><th></th></tr>');
                                    $tableReelDisk.append(html);
                                    for (var index in item[0]) {
                                        var specialRows = ["reelCode", "quality", "combinedLotNumber", "quantity", "partNumber", "packagingTimeStr", "dateCode", "fromReelCode"];
                                        if (specialRows.indexOf(index) != -1) {
                                            var html1 = $('<th>' + index + '</th>');
                                        } else if (index == 'packagingTimeStr') {
                                            var html1 = $('<th>packagingTime</th>');
                                        } else {
                                            var html1 = $('<th hidden>' + index + '</th>');
                                        }
                                        $tableReelDisk.find('tr:first').append(html1);
                                    }
                                }
                                for (var i = 0; i < item.length; i++) {
                                    var html2 = $('<tr><td><input style="width:30px;" type="checkbox"  logic="' + item[i].logic + '" value="' + item[i].id + '" /></td></tr>');
                                    $tableReelDisk.append(html2);
                                    for (var index in item[i]) {
                                        if (["reelCode", "quality", "combinedLotNumber", "quantity", "partNumber", "packagingTimeStr", "dateCode", "fromReelCode"].indexOf(index) != -1) {
                                            if ((item[i][index] == null) || (item[i][index] == 'null')) {
                                                var html3 = $('<td><input type="text" style="width:150px;" name="' + index + '" value=""></td>');
                                            } else {
                                                var html3 = $('<td><input type="text" style="width:150px;" name="' + index + '" value="' + item[i][index] + '" ></td>');
                                            }
                                        } else {
                                            if ((index.search('DTO') > 0) && (item[i][index] != null)) {
                                                var html3 = $('<td hidden><input type="text" name="' + index + '.id" value=' + item[i][index].id + '></td>');
                                            } else {
                                                var html3 = $('<td hidden><input type="text" name="' + index + '" value=' + item[i][index] + '></td>');
                                            }
                                        }
                                        $tableReelDisk.find('tr:last').append(html3);
                                    }
                                }

                            })
                        } else if (qualityClass == "failSBLOption") {




                            // var failReelDiskId = failReelDisk.id;
                            var dialog = $('<div class="modal fade"><div class="modal-dialog" >'
                                    + '<div class="modal-content" style="width:800px"><div class="modal-header"><button type="button" class="close" '
                                    + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                                    + '<h4 class="modal-title">Fail品全</h4></div><div class="modal-body">'
                                    + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                                    + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                                    + '<button type="button" class="btn btn-success" id="separateSave">保存</button></div></div>'
                                    + '</div></div>');
                            $.get('<%=path%>/FT_ReelDisksFailCreate1.jsp').done(function (html) {
                                        dialog.modal({
                                            keyboard: false
                                        }).on({
                                            'hidden.bs.modal': function () {
                                                $(this).remove();
                                            }
                                        }).find('.modal-body').html(html);

                                //debugger;
                                var failSBLDTOs = JSON.parse($("#ftDetail").find("select:last option:selected").val());
                                var binTypes = failSBLDTOs.map(function (failSBLDTO) {
                                    return failSBLDTO.siteTitle.substr(3);
                                });

                                var includeProperties = {
                                    "quality": "fail",
                                    "other": "0",
                                    "loss": "0"
                                };
                                var $table = dialog.find('#tableFailReelDisk');
                                // 更新界面
                                for (var propertyName in includeProperties) {
                                    var propertyValue = includeProperties[propertyName] != null ? includeProperties[propertyName] : "";
                                    if (["reelCode", "quality"].indexOf(propertyName) != -1) {
                                        $table.find('thead tr:eq(0)').append($('<th style="width:120px">' + propertyName + '</th>'));
                                        $table.find('tbody tr:eq(0)').append($('<td style="width:120px">' + propertyValue + '</td>'));
                                    } else if (["loss", "other"].indexOf(propertyName) != -1) {
                                        $table.find('thead tr:eq(0)').append($('<th>' + propertyName + '</th>'));
                                        $table.find('tbody tr:eq(0)').append($('<td><input type="text" name="' + propertyName + '" style="width:50px;" value="' + propertyValue + '"></td>'));
                                    }
                                }
                                $table.find('thead tr:eq(0)').append(binTypes.map(function (binType) {
                                    return $('<th class="bin">bin' + binType + '</th>')
                                }));
                                $table.find('tbody tr:eq(0)').append(binTypes.map(function (binType) {
                                    if (binType.trim().indexOf(' ')) {
                                        var binTypeArray = binType.trim().split(" ");
                                        var tdHtml = '<td><input name="bin' + binTypeArray[0] + '" type="text" class="binValue" style="width:50px;" value="0"></td>';
                                        for (var i = 1; i < binTypeArray.length; i++) {
                                            tdHtml += '<input hidden name="bin' + binTypeArray[i] + '" type="text" class="binValue" style="width:50px;" value="-2">';
                                        }
                                        return $(tdHtml);
                                    } else {
                                        return $('<td><input name="bin' + binType + '" type="text" class="binValue" style="width:50px;" value="0"></td>');
                                    }

                                }));

                                // 动态选择下拉
                                dialog.find("#selectNumber").on("change", function () {
                                    var separateCount = dialog.find("#selectNumber").find("option:selected").val();
                                    var tr1 = $table.find('tbody tr:eq(0)').clone();
                                    $table.find("tbody tr").remove();
                                    for (var i = 0; i < separateCount; ++i) {
                                        $table.find("tbody").append(tr1.clone());
                                    }
                                });

                                //
                                dialog.find('#separateSave').on('click', function () {
                                    var $tableSeparatedFailReelDisksBody = $table.find("tbody");
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
                                    ////

                                    var params = "ftLotId=" + checkedId + "&" + "failBins=" + JSON.stringify(separateBins1);
                                    $.post('${pageContext.request.contextPath}/ReelDisk/createFailReelDisks.koala', params).done(function (results) {
                                        if (results.success) {
                                            dialog.modal('hide');
                                            reelDisk($tableReelDisk, $tableReelDiskStatic, $tableIntegratedReelDisk, FTProcessDTO, checkedId);
                                            dialog.message({
                                                type: 'success',
                                                content: 'Fail全成功'
                                            })
                                                } else {
                                            dialog.message({
                                                type: 'error',
                                                content: results.errorMessage
                                            })
                                                }
                                    });
                                });
                                    }
                            );


                            /*var dialog = $('<div class="modal fade"><div class="modal-dialog" >'
                             + '<div class="modal-content" style="width:800px"><div class="modal-header"><button type="button" class="close" '
                             + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                             + '<h4 class="modal-title">Fail品</h4></div><div class="modal-body">'
                             + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                             + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                             + '<button type="button" class="btn btn-success" id="failSave">保存</button></div></div>'
                             + '</div></div>');


                             $.get('
                            <%=path%>/FT_ReelDisksFailCreate.jsp').done(function (html) {
                             dialog.modal({
                             keyboard: false
                             }).on({
                             'hidden.bs.modal': function () {
                             $(this).remove();
                             }
                             }).find('.modal-body').html(html);
                             dialog.find('select').on('change', function () {
                             dialog.find('#labelName').html('第' + dialog.find('select').val() + '箱:')
                             });

                             var count = dialog.find("table:first tr:last").size();
                             dialog.find(".btn").on("click", function () {
                             dialog.find("table:first tr:last").append($('<tr><td><label class="td" id="labelName">第1箱</label></td></tr>'));
                             });

                             // 创建bin列
                             failSBLs.forEach(function (failSBL) {
                             var html1 = $('<th class="binFlag">bin' + failSBL.type.toLowerCase() + '</th>');
                             var html2 = $('<td><input type="text" class="binValue" style="width:50px;"></td>');
                             dialog.find('table tr:first').append(html1);
                             dialog.find('table tr:last').append(html2);
                             });


                             dialog.find('#failSave').on('click', function () {
                             dialog.find('#ftLotDTO_id').val(checkedId);
                             var bin = [];
                             var binType = [];
                             var binValue = [];
                             dialog.find('table input').each(function () {//判断fail品生成界面是否有未填写的，如果有，自动填入0，否则后台会报错
                             if (this.value == '') {
                             this.value = 0;
                             }
                             })
                             $.each(dialog.find('table .binFlag'), function () {
                             var number = this.innerHTML;
                             binType.push(number.replace(/[^0-9]/ig, ''));
                             });
                             $.each(dialog.find('table .binValue'), function () {
                             binValue.push(this.value);
                             });

                             var bin = [];
                             for (var i = 0; i < 20; ++i) {
                             bin[i] = "-1";
                             }
                             for (var i = 0; i < binType.length; ++i) {
                             bin[parseInt(binType[i])] = binValue[i];
                             }

                             dialog.find('#ftResultDTO_bin').val(bin);
                             $.post('
                            ${pageContext.request.contextPath}/ReelDisk/createFailReelDisk.koala ', dialog.find('form').serialize()).done(function (output) {
                             reelDisk($tableReelDisk, $tableIntegratedReelDisk, FTProcessDTO, checkedId);
                             if (output.success) {
                             dialog.modal('hide');
                             } else {

                             }
                             })
                             })
                             }
                             )*/
                        }
                    });
                    // 合盘
                    $ftDetail.find('#integrate').on('click', function () {

                        var $checkObj = $tableReelDiskStatic.find('tr td input[type=checkbox]:checked');
                        var logic = $checkObj.attr('logic');
                        var isFull = $checkObj.attr('isFull');
                        if (logic == 1) {
                            grid.message({
                                type: 'warning',
                                content: '该盘已合批'
                            })
                            return;
                        }
                        if (isFull == '是') {
                            grid.message({
                                type: 'warning',
                                content: '该盘已满卷'
                            })
                            return;
                        }

                        if ($checkObj.length == 0) {
                            grid.message({
                                type: 'warning',
                                content: '请选择一条记录进行合批'
                            })
                            return;
                        }
                        if ($checkObj.length > 1) {
                            grid.message({
                                type: 'warning',
                                content: '只能选择一条记录进行合批'
                            })
                            return;
                        }
                        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                                + '<div class="modal-content" style="width:750px;"><div class="modal-header"><button type="button" class="close" '
                                + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                                + '<h4 class="modal-title">合批</h4></div><div class="modal-body">'
                                + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                                + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                                + '<button type="button" class="btn btn-success" id="reelItg">合批</button></div></div>'
                                + '</div></div>');
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
                                    dialog.find('#reelTable').getGrid().search({'logic': 0, 'isFull': '否'});
                                }
                            }).find('.modal-body').html(html);
                            var reelId = $checkObj.val();
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

                            dialog.find('#reelItg').on('click', function () {
                                var LotId = dialog.find('#lotNumberTable').getGrid().selectedRowsIndex();
                                var toReelId = dialog.find('#reelTable').getGrid().selectedRowsIndex();
                                if ((toReelId.length > 1) || (LotId.length > 1) || ((LotId.length == 1) && (toReelId.length == 1))) {
                                    grid.message({
                                        type: 'warning',
                                        content: '只能选择一条记录进行合批'
                                    });
                                    return;
                                } else if ((LotId.length == 0) && (toReelId.length == 0)) {
                                    grid.message({
                                        type: 'warning',
                                        content: '请选择一条记录进行合批'
                                    });
                                    return;
                                } else if ((LotId.length == 1) && (toReelId.length == 0)) {
                                    var dataItg = {'reelId': reelId, 'LotId': LotId[0]}
                                    $.post('${pageContext.request.contextPath}/ReelDisk/gotoLot.koala', dataItg).done(function (options) {
                                        if (options.success) {
                                            dialog.modal('hide');
                                            reelDisk($tableReelDisk, $tableIntegratedReelDisk, FTProcessDTO, checkedId);
                                            dialog.message({
                                                type: 'success',
                                                content: '合批成功'
                                            })
                                        } else {
                                            dialog.message({
                                                type: 'error',
                                                content: options.errorMessage
                                            })
                                        }
                                    })
                                } else if ((LotId.length == 0) && (toReelId.length == 1)) {
                                    var dataItg = {'reelId': reelId, 'toReelId': toReelId[0]}
                                    $.post('${pageContext.request.contextPath}/ReelDisk/integrateReelDisk.koala', dataItg).done(function (options) {
                                        if (options.success) {
                                            dialog.modal('hide');
                                            reelDisk($tableReelDisk, $tableIntegratedReelDisk, FTProcessDTO, checkedId);
                                            dialog.message({
                                                type: 'success',
                                                content: '合批成功'
                                            })
                                        } else {
                                            dialog.message({
                                                type: 'error',
                                                content: options.errorMessage
                                            })
                                        }
                                    })
                                }
                            })
                        })
                    });

                    $ftDetail.find('#sample').on('click', function () {
                        var checkedRow = $tableReelDiskStatic.find('tr td input[type=checkbox]:checked');
                        var reelDiskIds = checkedRow.map(function () {
                            return $(this).val();
                        }) // no join
                        var arr = [];
                        for (var i = 0; i < reelDiskIds.length; ++i) {
                            arr[i] = reelDiskIds[i];
                        }
                        reelDiskIds = arr.join(",");
                        var params = "reelDiskIds=" + reelDiskIds;
                        $.post('${pageContext.request.contextPath}/ReelDisk/sampleReelDisks.koala', params).done(function (result) {
                            if (result.success) {
                                grid.message({
                                    type: 'success',
                                    content: '小样成功'
                                })
                            } else {
                                grid.message({
                                    type: 'error',
                                    content: result.errorMessage
                                })
                            }
                        })
                    });
                }
                if (ftNodeDTO.ftState == 3) {
                    $ftDetail.find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $ftDetail.find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $ftDetail.attr('disabled', true);
                    $ftDetail.find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $ftDetail.find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $ftDetail.find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 2) {
                    $ftDetail.find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $ftDetail.find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $ftDetail.find('input').attr('disabled', false);
                    $ftDetail.find('.yield').attr('disabled', true);
                    $ftDetail.find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $ftDetail.find('input').attr('disabled', true);
                    $ftDetail.find('textarea').attr('disabled', true);
                    $ftDetail.find('select').attr('disabled', true);
                }

                function collectData() {
                    if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                        var n = 0;
                        var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                        var binValueGet = [];
                        //
                        $.each($('#ftDetail').find('table td .flag1'), function () {
                            var binValueDeal = this.value == false ? '0' : this.value;
                            binValueGet.push(binValueDeal);
                        });
                        for (var k = 0; k < binValuetoSend.length; k++) {
                            if (binValuetoSend[k] != '-1') {
                                binValuetoSend[k] = binValueGet[n++];
                            }
                        }
                        $('#ftDetail').find('table .flag2 input').val(binValuetoSend);
                        var yieldFinialVal = $tableFinalYield.find('.yield').val().trim();
                        yieldFinialVal = yieldFinialVal == '%' ? 0 : (parseFloat(yieldFinialVal) / 100).toFixed(5);
                        var failFinialVal = $tableFinalYield.find('.fail').val().trim();
                        failFinialVal = failFinialVal == '' ? 0 : parseFloat(failFinialVal);
                        var yieldStatisticslVal = $tableSecond.find('.yield').val().trim();
                        yieldStatisticslVal = yieldStatisticslVal == '%' ? 0 : (parseFloat(yieldStatisticslVal) / 100).toFixed(5);
                        var failStatisticslVal = $tableSecond.find('.fail').val().trim();
                        failStatisticslVal = failStatisticslVal == '' ? 0 : parseFloat(failStatisticslVal);
                        var json = {
                            'processId': FTProcessDTO.id,
                            'isModify': 1,
                            'ftFinishDTO.ftResultDTO.yield': yieldFinialVal,
                            'ftFinishDTO.ftResultDTO.fail': failFinialVal,
                            'ftFinishDTO.ftStatisticsDTO.yield': yieldStatisticslVal,
                            'ftFinishDTO.ftStatisticsDTO.fail': failStatisticslVal
                        };
                    } else {
                        var json = {'processId': FTProcessDTO.id, 'isModify': 1};
                    }
                    return json;
                }

                //TODO
                $('#ftDetail').find('#printID').on('click', function () {
                    var self = this;
                    var reelDiskNum = $("#tableReelDisk").find("tbody").find("input[type=checkbox]:checked").map(function () {
                        return $(this).val()
                    })
                    if (reelDiskNum.length > 1) {
                        grid.message({
                            type: 'warning',
                            content: '只能选择一条记录进行打印'
                        });
                        return;
                    } else if (reelDiskNum.length == 0) {
                        grid.message({
                            type: 'warning',
                            content: '请选择一条记录进行打印'
                        });
                        return;
                    }

                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content">' +
                            '<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;' +
                            '</button><h4 class="modal-title">ReelCode标签打印</h4></div><div class="modal-body">' +
                            '<p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>' +
                            '<button type="button" class="btn btn-success" id="save">打印</button></div></div></div></div>');

                    $.get('<%=path%>/FTReelCodePrint.jsp').done(function (html) {
//                        debugger
                        dialog.find('.modal-body').html(html);
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        });
                        //$.get('${pageContext.request.contextPath}/FTLotNodeOption/getFTResultByLotId/' + id + '.koala?labelType=FAIL').done(function (json) {

                    });

                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        if ($("labelNameID").val() == "" || $("labelNameID").val() == undefined) {
                            dialog.message({
                                type: 'warning',
                                content: '未找到对应标签请检查'
                            });
                            return false;
                        }
                        var path = "C:/WMS/PRINT/tests.btw";//+$("labelNameID").val()
                        onPrint(path);
                    });
                });
                //TODO
                $('#ftDetail').find('#printsingleID').on('click', function () {
                    var self = this;
                    var reelDiskNum = $("#tableReelDisk").find("tbody").find("input[type=checkbox]:checked").map(function () {
                        return $(this).val()
                    })
                    debugger
                    if (reelDiskNum.length > 1) {
                        grid.message({
                            type: 'warning',
                            content: '只能选择一条记录进行打印'
                        });
                        return;
                    } else if (reelDiskNum.length == 0) {
                        grid.message({
                            type: 'warning',
                            content: '请选择一条记录进行打印'
                        });
                        return;
                    }

                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content">' +
                            '<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;' +
                            '</button><h4 class="modal-title">ReelCode标签打印</h4></div><div class="modal-body">' +
                            '<p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button>' +
                            '<button type="button" class="btn btn-success" id="save">打印</button></div></div></div></div>');

                    $.get('<%=path%>/FTReelCodePrint.jsp').done(function (html) {
//                        debugger
                        dialog.find('.modal-body').html(html);
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        });
                        //$.get('${pageContext.request.contextPath}/FTLotNodeOption/getFTResultByLotId/' + id + '.koala?labelType=FAIL').done(function (json) {

                    });

                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        if ($("labelNameID").val() == "" || $("labelNameID").val() == undefined) {
                            dialog.message({
                                type: 'warning',
                                content: '未找到对应标签请检查'
                            });
                            return false;
                        }
                        var path = "C:/WMS/PRINT/tests.btw";//+$("labelNameID").val()
                        onPrint(path);
                    });

                });

                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNode(checkedId, json, nodeName);
                });
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNodeThenEndNode(checkedId, json, nodeName);
                });
            })
        }

        var onFVINodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $('#ftDetail').html(html);
                var elm;
                for (var index in ftNodeDTO) {
                    if (ftNodeDTO[index] != null) {
                        elm = $('#ftDetail').find('#' + index + 'ID');
                        elm.val(ftNodeDTO[index]);
                    }
                }
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {

                } else {
                    for (var index in ftNodeDTO['ftPassNodeDTO']) {
                        var input = ftNodeDTO['ftPassNodeDTO'];
                        if (input[index] != null) {
                            $('#ftDetail').find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                }
                //
                // 组合测试流程结点
                if (ftNodeDTO.hasOwnProperty(ftStringName) && ftNodeDTO[ftStringName] && ftNodeDTO[ftStringName].ftResultDTO) {
                    var ftResult = ftNodeDTO[ftStringName]['ftResultDTO'];
                    var $tableFirst = $('#ftDetail').find('table:first');
                    $tableFirst.find('tr:first').append(generateFTResultColumnHeaders(ftStringName, ftResult));
                    $tableFirst.find('tr:last').append(generateFTResultColumns(ftStringName, ftResult));
                    $tableFirst.find('tr:first').append(generateBinColumnHeaders(sblDTOs));
                    $tableFirst.find('tr:last').append(generateBinColumns(ftResult['bin'], sblDTOs));
                    registerOnFinalYieldChangedListener($tableFirst);

                } else if (ftNodeDTO.ftPassNodeDTO) {
                    parseInt(ftNodeDTO['ftPassNodeDTO']['result']) ? $('#ftDetail').find('#' + ftStringName + '_resultID input:first').attr('checked', true) : $('#ftDetail').find('#' + ftStringName + '_resultID input:last').attr('checked', true);

                }
                if (ftNodeDTO.ftState == 3) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 2) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#ftDetail').find('input').attr('disabled', false);
                    $('#ftDetail').find('.yield').attr('disabled', true);
                    $('#ftDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $('#ftDetail').find('input').attr('disabled', true);
                    $('#ftDetail').find('textarea').attr('disabled', true);
                    $('#ftDetail').find('select').attr('disabled', true);
                }
                if (buttonName == 'Test') {

                } else {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                        //
                        if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                        startNode(checkedId, FTProcessDTO, nodeName);
                    });

                    function collectData() {
                        if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                            var n = 0;
                            var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                            var binValueGet = [];
                            $.each($('#ftDetail').find('table td .flag1'), function () {
                                binValueGet.push(this.value);
                            });
                            for (var k = 0; k < binValuetoSend.length; k++) {
                                if (binValuetoSend[k] != '-1') {
                                    binValuetoSend[k] = binValueGet[n++];
                                }
                            }
                            $('#ftDetail').find('table .flag2 input').val(binValuetoSend);
                            /*  var yieldPostVal = $('#ftDetail').find('table .yield').val().trim();
                             var failPostVal = $('#ftDetail').find('table .fail').val().trim();*/
                            //$('#ftDetail').find('table .yield').val(parseInt(yieldPostVal)/100);
                            if (buttonName == 'IQC') {

                            } else if (buttonName == 'Baking') {

                            } else {
                                var json = {
                                    'processId': FTProcessDTO.id,
                                    'isModify': 0,
                                    /*  'ftFinishDTO.ftResultDTO.yield': (parseFloat(yieldPostVal) / 100),
                                     'ftFinishDTO.ftResultDTO.fail': parseFloat(failPostVal) / 100*/
                                };
                            }
                        } else {
                            var json = {'processId': FTProcessDTO.id, 'isModify': 0};
                        }
                        return json;
                    }

                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                        if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                        var json = collectData();
                        var noteData = {'note': $('#ftDetail').find('noteID').val(), 'processId': FTProcessDTO.id};//备注
                        $.post('${pageContext.request.contextPath}/FTProcess/updateFTNote.koala', noteData).done(function (result) {
                            if (result.success) {
                                grid.message({
                                    type: 'success',
                                    content: '备注保存成功'
                                })
                            } else {
                                grid.message({
                                    type: 'error',
                                    content: result.actionError
                                });
                            }
                        })
                        saveNode(checkedId, json, nodeName);
                    });
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                        if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                        var json = collectData();
                        var noteData = {'note': $('#ftDetail').find('noteID').val(), 'processId': FTProcessDTO.id};//备注
                        $.post('${pageContext.request.contextPath}/FTProcess/updateFTNote.koala', noteData).done(function (result) {
                            if (result.success) {
                                grid.message({
                                    type: 'success',
                                    content: '备注保存成功'
                                })
                            } else {
                                grid.message({
                                    type: 'error',
                                    content: result.actionError
                                });
                            }
                        })
                        saveNodeThenEndNode(checkedId, json, nodeName);
                    });
                }
            });
        };

        var onFQCNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $('#ftDetail').html(html);
                var elm;
                for (var index in ftNodeDTO) {
                    if (ftNodeDTO[index] != null) {
                        elm = $('#ftDetail').find('#' + index + 'ID');
                        elm.val(ftNodeDTO[index]);
                    }
                }
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {

                } else {
                    for (var index in ftNodeDTO['ftPassNodeDTO']) {
                        var input = ftNodeDTO['ftPassNodeDTO'];
                        if (input[index] != null) {
                            $('#ftDetail').find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                }

                parseInt(ftNodeDTO['ftPassNodeDTO']['result']) ? $('#ftDetail').find('#' + ftStringName + '_resultID input:first').attr('checked', true) : $('#ftDetail').find('#' + ftStringName + '_resultID input:last').attr('checked', true);

                if (ftNodeDTO.ftState == 3) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);

                }
                if (ftNodeDTO.ftState == 2) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#ftDetail').find('input').attr('disabled', false);
                    $('#ftDetail').find('.yield').attr('disabled', true);
                    $('#ftDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $('#ftDetail').find('input').attr('disabled', true);
                    $('#ftDetail').find('textarea').attr('disabled', true);
                    $('#ftDetail').find('select').attr('disabled', true);
                }

                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                    //
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });

                function collectData() {
                    if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                        var n = 0;
                        var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                        var binValueGet = [];
                        $.each($('#ftDetail').find('table td .flag1'), function () {
                            binValueGet.push(this.value);
                        });
                        for (var k = 0; k < binValuetoSend.length; k++) {
                            if (binValuetoSend[k] != '-1') {
                                binValuetoSend[k] = binValueGet[n++];
                            }
                        }
                        $('#ftDetail').find('table .flag2 input').val(binValuetoSend);
                        var yieldPostVal = $('#ftDetail').find('table .yield').val();
                        var failPostVal = $('#ftDetail').find('table .fail').val();
                        //$('#ftDetail').find('table .yield').val(parseInt(yieldPostVal)/100);
                        if (buttonName == 'IQC') {

                        } else if (buttonName == 'Baking') {

                        } else {
                            var json = {
                                'processId': FTProcessDTO.id,
                                'isModify': 0,
                                /*      'ftFinishDTO.ftResultDTO.yield': parseInt(yieldPostVal) / 100,
                                 'ftFinishDTO.ftResultDTO.fail': parseInt(failPostVal) / 100*/
                            };
                        }
                    } else {
                        var json = {'processId': FTProcessDTO.id, 'isModify': 0};
                    }
                    return json;
                }

                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNode(checkedId, json, nodeName);
                });
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNodeThenEndNode(checkedId, json, nodeName);
                });
            });
        }

        var onPackingNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $('#ftDetail').html(html);
                var elm;
                for (var index in ftNodeDTO) {
                    if (ftNodeDTO[index] != null) {
                        elm = $('#ftDetail').find('#' + index + 'ID');
                        elm.val(ftNodeDTO[index]);
                    }
                }
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {

                } else {
                    for (var index in ftNodeDTO['ftPassNodeDTO']) {
                        var input = ftNodeDTO['ftPassNodeDTO'];
                        if (input[index] != null) {
                            $('#ftDetail').find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                }
                //标签打印事件绑定
                $("#packingNodePrintId").bind("click", function (e) {
                    var labelName = "";
                    $.get('${pageContext.request.contextPath}/ReelDisk/get/' + $("#tableReelDiskStatic input[type='checkbox']:checked").eq(0).val() + '.koala?').done(function (data) {
                        data = data['data'];
                        if (data['quality'] == "fail") {
                            $.ajax({
                                        url: '${pageContext.request.contextPath}/FTLotNodeOption/getFTResultByLotId/' + checkedId + '.koala?labelType=FAIL',
                                        type: 'GET',
                                        async: false
                                    })
                                    .done(function (data) {
                                        labelName = data['data']['labelName']
                                    })
                        }
                        else {
                            $.ajax({
                                        url: '${pageContext.request.contextPath}/FTLotNodeOption/getFTResultByLotId/' + checkedId + '.koala?labelType=PASS',
                                        type: 'GET',
                                        async: false
                                    })
                                    .done(function (data) {
                                        labelName = data['data']['labelName']
                                    })
                        }
                        labelName = "pass2_1412_inter.btw";
                        if (!!labelName == 0) {
                            grid.message({
                                type: 'warning',
                                content: '未找到对应标签请检查'
                            });
                            return false;
                        }
                        if (data['quality'] == "fail") {
                            labelName = "fail_1412_inter.btw";
                            var parameter = "lotNo=" + data['ftLotDTO']['internalLotNumber']
                                    + "&reelCode=" + data['reelCode']
                                    + "&packagingTime=" + data['packagingTime']
                                    + "&dateCode=" + data['dateCode']
                                    + "&partNumber=" + data['ftLotDTO']['shipmentProductNumber']
                                    + "&total=" + data['quantity']
                                    + "&customerLotNomber=" + data['ftLotDTO']['customerLotDTO']['customerLotNumber']
                            var failbininfo = [];
                            var failBin = {};
                            $.ajax({
                                        url: '${pageContext.request.contextPath}/ReelDisk/getAllReel/' + $("#tableReelDiskStatic input[type='checkbox']:checked").eq(0).val() + '.koala',
                                        type: 'GET',
                                        async: false
                                    })
                                    .done(function (data) {
                                        data = data[0];
                                        failBin = data['ftStatisticsDTO'];
                                        for (var i = 1; i < 16; i++) {
                                            if (!!data['ftStatisticsDTO']['site' + i + "Name"] == 0)
                                                continue;
                                            else {
                                                if (data['ftStatisticsDTO']['site' + i + "Quality"].startsWith("PASS"))
                                                    continue;
                                                else {
                                                    var json = {};
                                                    json['siteName'] = data['ftStatisticsDTO']['site' + i + "Name"];
                                                    json['value'] = data['ftStatisticsDTO']['site' + i + "Num"];
                                                    json['station'] = data['ftStatisticsDTO']['site' + i + "Quality"].substring(0, data['ftStatisticsDTO']['site' + i + "Quality"].length - 1);
                                                    json['site'] = data['ftStatisticsDTO']['site' + i + "Quality"].charAt(data['ftStatisticsDTO']['site' + i + "Quality"].length - 1);
                                                    failbininfo.push(json);
                                                }
                                            }
                                        }
                                    });
                            parameter += "&loss=" + failBin['loss'] + "&other=" + failBin['other']
                            //var sum=0;
                            $.each(failbininfo, function (a) {
                                //sum+=+this.value;
                                parameter += "&failBin" + a + "=" + this.value;
                            })
                            //parameter+="&total="+sum;
                            debugger;
                        }
                        else {
                            var parameter = "lotNo=" + data['ftLotDTO']['internalLotNumber']
                                    + "&reelCode=" + data['reelCode']
                                    + "&packagingTime=" + data['packagingTime']
                                    + "&dateCode=" + data['dateCode']
                                    + "&partNumber=" + data['ftLotDTO']['shipmentProductNumber']
                                    + "&quantity=" + data['quantity']
                                    + "&customerLotNomber=" + data['ftLotDTO']['customerLotDTO']['customerLotNumber']
                        }
                        var path = "C:/WMS/PRINT/" + labelName;
                        debugger;
                        var v = document.getElementById("activeX").Print(path, 1, parameter, true, true);
                        if (v != "1") {
                            alert(v);
                        }
                        else {
                            document.getElementById("activeX").Quit();
                        }
                    })
                });
                $("#packingNodePrintAllId").bind("click", function (e) {
                    var labelName = "";
                    var result = eval('(' + localStorage.getItem('reelcodeData') + ')');
                    $.each(result, function (index) {
                        var data = this;
                        if (labelName == "") {
                            if (data['quantity'] == "fail") {
                                $.ajax({
                                            url: '${pageContext.request.contextPath}/FTLotNodeOption/getFTResultByLotId/' + checkedId + '.koala?labelType=FAIL',
                                            type: 'GET',
                                            async: false
                                        })
                                        .done(function (data) {
                                            labelName = data['data']['labelName']
                                        })
                            }
                            else {
                                $.ajax({
                                            url: '${pageContext.request.contextPath}/FTLotNodeOption/getFTResultByLotId/' + checkedId + '.koala?labelType=PASS',
                                            type: 'GET',
                                            async: false
                                        })
                                        .done(function (data) {
                                            labelName = data['data']['labelName']
                                        })
                            }
                        }
                        labelName = "pass2_1412_inter.btw";
                        if (!!labelName == 0) {
                            grid.message({
                                type: 'warning',
                                content: '未找到对应标签请检查'
                            });
                            return false;
                        }
                        if (data['quality'] == "fail") {
                            labelName = "fail_1412_inter.btw";
                            var parameter = "lotNo=" + data['ftLotDTO']['internalLotNumber']
                                    + "&reelCode=" + data['reelCode']
                                    + "&packagingTime=" + data['packagingTime']
                                    + "&dateCode=" + data['dateCode']
                                    + "&partNumber=" + data['ftLotDTO']['shipmentProductNumber']
                                    + "&total=" + data['quantity']
                                    + "&customerLotNomber=" + data['ftLotDTO']['customerLotDTO']['customerLotNumber']
                            var failbininfo = [];
                            var failBin = {};
                            $.ajax({
                                        url: '${pageContext.request.contextPath}/ReelDisk/getAllReel/' + $("#tableReelDiskStatic input[type='checkbox']:checked").eq(0).val() + '.koala',
                                        type: 'GET',
                                        async: false
                                    })
                                    .done(function (data) {
                                        data = data[0];
                                        failBin = data['ftStatisticsDTO'];
                                        for (var i = 1; i < 16; i++) {
                                            if (!!data['ftStatisticsDTO']['site' + i + "Name"] == 0)
                                                continue;
                                            else {
                                                if (data['ftStatisticsDTO']['site' + i + "Quality"].startsWith("PASS"))
                                                    continue;
                                                else {
                                                    var json = {};
                                                    json['siteName'] = data['ftStatisticsDTO']['site' + i + "Name"];
                                                    json['value'] = data['ftStatisticsDTO']['site' + i + "Num"];
                                                    json['station'] = data['ftStatisticsDTO']['site' + i + "Quality"].substring(0, data['ftStatisticsDTO']['site' + i + "Quality"].length - 1);
                                                    json['site'] = data['ftStatisticsDTO']['site' + i + "Quality"].charAt(data['ftStatisticsDTO']['site' + i + "Quality"].length - 1);
                                                    failbininfo.push(json);
                                                }
                                            }
                                        }
                                    });
                            parameter += "&loss=" + failBin['loss'] + "&other=" + failBin['other']
                            //var sum=0;
                            $.each(failbininfo, function (a) {
                                //sum+=+this.value;
                                parameter += "&failBin" + a + "=" + this.value;
                            })
                            //parameter+="&total="+sum;
                        }
                        else {
                            var parameter = "lotNo=" + data['ftLotDTO']['internalLotNumber']
                                    + "&reelCode=" + data['reelCode']
                                    + "&packagingTime=" + data['packagingTime']
                                    + "&dateCode=" + data['dateCode']
                                    + "&partNumber=" + data['ftLotDTO']['shipmentProductNumber']
                                    + "&quantity=" + data['quantity']
                                    + "&customerLotNomber=" + data['ftLotDTO']['customerLotDTO']['customerLotNumber']
                        }
                        var path = "C:/WMS/PRINT/" + labelName;
                        var v = document.getElementById("activeX").Print(path, 1, parameter, false, false);
                        if (v != "1") {
                            alert(v);
                        }
                        else {
                            document.getElementById("activeX").Quit();
                        }
                    })
                });
                //
                // 组合测试流程结点
                if (buttonName == 'Packing') {
                    reelPacking(FTProcessDTO, checkedId);

                    $('#ftDetail').find('#separatePacking').on('click', function () {
                        var $checkbox = $('#ftDetail').find('table:first tr td input[type=checkbox]:checked')
                        if ($checkbox.length == 0) {
                            grid.message({
                                type: 'warning',
                                content: '请选择一条记录进行拆批'
                            })
                            return;
                        }
                        if ($checkbox.length > 1) {
                            grid.message({
                                type: 'warning',
                                content: '只能选择一条记录进行拆批'
                            })
                            return;
                        }
                        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                                + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                                + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                                + '<h4 class="modal-title">拆批</h4></div><div class="modal-body">'
                                + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                                + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                                + '<button type="button" class="btn btn-success" id="confirm">确定</button></div></div>'
                                + '</div></div>');
                        $.get('<%=path%>/FT_ReelCodeSeparate.jsp').done(function (html) {
                            dialog.modal({
                                keyboard: false
                            }).on({
                                'hidden.bs.modal': function () {
                                    $(this).remove();
                                }
                            }).find('.modal-body').html(html);
                            var reelId = $checkbox.val();

                            $.get('${pageContext.request.contextPath}/ReelDisk/get/' + reelId + '.koala').done(function (reelDisk) {
                                if (reelDisk.success) {
                                    reelDisk = reelDisk.data;
                                    dialog.find('#reelcode').val(reelDisk.reelCode);
                                    dialog.find('#quality').val(reelDisk.quality);
                                    dialog.find('#amount').val(reelDisk.quantity);
                                    dialog.find('#confirm').on('click', function () {
                                        var separateData = dialog.find('#separateQty').val();
                                        if ((separateData < 0) || (separateData > reelDisk.quantity)) {
                                            dialog.message({
                                                type: warning,
                                                content: '分拆数量不能小于0或大于总数量'
                                            })
                                            return;
                                        }
                                        var dataSep = {
                                            'reelId': reelId,
                                            'separateQty': dialog.find('#separateQty').val()
                                        }
                                        $.post('${pageContext.request.contextPath}/ReelDisk/separateReelDisk.koala', dataSep).done(function (results) {
                                            if (results.success) {
                                                dialog.modal('hide');
                                                reelPacking(FTProcessDTO, checkedId);
                                                dialog.find('.modal-content').message({
                                                    type: 'success',
                                                    content: '分拆成功'
                                                })
                                            } else {
                                                dialog.find('.modal-content').message({
                                                    type: 'error',
                                                    content: results.actionError
                                                })
                                            }
                                        })
                                    })
                                }
                            });
                        })
                    })
                    $('#ftDetail').find('#itgPacking').on('click', function () {
                        var $checkObj = $('#ftDetail').find('table:first tr td input[type=checkbox]:checked');
                        if ($checkObj.length == 0) {
                            grid.message({
                                type: 'warning',
                                content: '请选择一条记录进行合批'
                            })
                            return;
                        }
                        if ($checkObj.length > 1) {
                            grid.message({
                                type: 'warning',
                                content: '只能选择一条记录进行合批'
                            })
                            return;
                        }
                        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                                + '<div class="modal-content" style="width:750px;"><div class="modal-header"><button type="button" class="close" '
                                + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                                + '<h4 class="modal-title">合批</h4></div><div class="modal-body">'
                                + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                                + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                                + '<button type="button" class="btn btn-success" id="reelItg">合批</button></div></div>'
                                + '</div></div>');
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

                            dialog.find('#reelItg').on('click', function () {
                                var LotId = dialog.find('#lotNumberTable').getGrid().selectedRowsIndex();
                                var toReelId = dialog.find('#reelTable').getGrid().selectedRowsIndex();
                                if ((toReelId.length > 1) || (LotId.length > 1) || ((LotId.length == 1) && (toReelId.length == 1))) {
                                    grid.message({
                                        type: 'warning',
                                        content: '只能选择一条记录进行合批'
                                    });
                                    return;
                                } else if ((LotId.length == 0) && (toReelId.length == 0)) {
                                    grid.message({
                                        type: 'warning',
                                        content: '请选择一条记录进行合批'
                                    });
                                    return;
                                } else if ((LotId.length == 1) && (toReelId.length == 0)) {
                                    var dataItg = {'reelId': reelId, 'LotId': LotId[0]}
                                    $.post('${pageContext.request.contextPath}/ReelDisk/gotoLot.koala', dataItg).done(function (options) {
                                        if (options.success) {
                                            dialog.modal('hide');
                                            reelPacking(FTProcessDTO, checkedId);
                                            dialog.find('.modal-content').message({
                                                type: 'success',
                                                content: '合批成功'
                                            })
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
                                            dialog.modal('hide');
                                            reelPacking(FTProcessDTO, checkedId);
                                            dialog.find('.modal-content').message({
                                                type: 'success',
                                                content: '合批成功'
                                            })
                                        } else {
                                            dialog.find('.modal-content').message({
                                                type: 'error',
                                                content: options.actionError
                                            })
                                        }
                                    })
                                }
                            })
                        })
                    })
                }
                if (ftNodeDTO.ftState == 3) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 2) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#ftDetail').find('input').attr('disabled', false);
                    $('#ftDetail').find('.yield').attr('disabled', true);
                    $('#ftDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $('#ftDetail').find('input').attr('disabled', true);
                    $('#ftDetail').find('textarea').attr('disabled', true);
                    $('#ftDetail').find('select').attr('disabled', true);
                }
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                    //
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });

                function collectData() {
                    if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                        var n = 0;
                        var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                        var binValueGet = [];
                        $.each($('#ftDetail').find('table td .flag1'), function () {
                            binValueGet.push(this.value);
                        });
                        for (var k = 0; k < binValuetoSend.length; k++) {
                            if (binValuetoSend[k] != '-1') {
                                binValuetoSend[k] = binValueGet[n++];
                            }
                        }
                        $('#ftDetail').find('table .flag2 input').val(binValuetoSend);
                        var yieldPostVal = $('#ftDetail').find('table .yield').val();
                        var failPostVal = $('#ftDetail').find('table .fail').val();
                        //$('#ftDetail').find('table .yield').val(parseInt(yieldPostVal)/100);
                        if (buttonName == 'IQC') {

                        } else if (buttonName == 'Baking') {

                        } else {
                            var json = {
                                'processId': FTProcessDTO.id,
                                'isModify': 0,
                                /* 'ftFinishDTO.ftResultDTO.yield': parseInt(yieldPostVal) / 100,
                                 'ftFinishDTO.ftResultDTO.fail': parseInt(failPostVal) / 100*/
                            };
                        }
                    } else {
                        var json = {'processId': FTProcessDTO.id, 'isModify': 0};
                    }
                    return json;
                }

                //if(ftNodeDTO.state != 1) return;
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    //	$('#ftDetail').find("button:nth-last-child(1),button:nth-last-child(2)").css('display','inline-block');
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    var noteData = {'note': $('#ftDetail').find('noteID').val(), 'processId': FTProcessDTO.id};//备注
                    $.post('${pageContext.request.contextPath}/FTProcess/updateFTNote.koala', noteData).done(function (result) {
                        if (result.success) {
                            grid.message({
                                type: 'success',
                                content: '备注保存成功'
                            })
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.actionError
                            });
                        }
                    })
                    saveNode(checkedId, json, nodeName);
                });
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    //	$('#ftDetail').find("button:nth-last-child(1),button:nth-last-child(2)").css('display','inline-block');
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    var noteData = {'note': $('#ftDetail').find('noteID').val(), 'processId': FTProcessDTO.id};//备注
                    $.post('${pageContext.request.contextPath}/FTProcess/updateFTNote.koala', noteData).done(function (result) {
                        if (result.success) {
                            grid.message({
                                type: 'success',
                                content: '备注保存成功'
                            })
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.actionError
                            });
                        }
                    })
                    saveNodeThenEndNode(checkedId, json, nodeName);
                });
            });
        };

        var onOQCNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $('#ftDetail').html(html);
                var elm;
                for (var index in ftNodeDTO) {
                    if (ftNodeDTO[index] != null) {
                        elm = $('#ftDetail').find('#' + index + 'ID');
                        elm.val(ftNodeDTO[index]);
                    }
                }
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {

                } else {
                    for (var index in ftNodeDTO['ftPassNodeDTO']) {
                        var input = ftNodeDTO['ftPassNodeDTO'];
                        if (input[index] != null) {
                            $('#ftDetail').find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                }
                parseInt(ftNodeDTO['ftPassNodeDTO']['result']) ? $('#ftDetail').find('#' + ftStringName + '_resultID input:first').attr('checked', true) : $('#ftDetail').find('#' + ftStringName + '_resultID input:last').attr('checked', true);
                if (ftNodeDTO.ftState == 3) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);

                }
                if (ftNodeDTO.ftState == 2) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#ftDetail').find('input').attr('disabled', false);
                    $('#ftDetail').find('.yield').attr('disabled', true);
                    $('#ftDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $('#ftDetail').find('input').attr('disabled', true);
                    $('#ftDetail').find('textarea').attr('disabled', true);
                    $('#ftDetail').find('select').attr('disabled', true);
                }


                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                    //
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });

                function collectData() {
                    if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                        var n = 0;
                        var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                        var binValueGet = [];
                        $.each($('#ftDetail').find('table td .flag1'), function () {
                            binValueGet.push(this.value);
                        });
                        for (var k = 0; k < binValuetoSend.length; k++) {
                            if (binValuetoSend[k] != '-1') {
                                binValuetoSend[k] = binValueGet[n++];
                            }
                        }
                        $('#ftDetail').find('table .flag2 input').val(binValuetoSend);
                        var yieldPostVal = $('#ftDetail').find('table .yield').val();
                        var failPostVal = $('#ftDetail').find('table .fail').val();
                        //$('#ftDetail').find('table .yield').val(parseInt(yieldPostVal)/100);
                        if (buttonName == 'IQC') {
                            var json = {
                                'processId': FTProcessDTO.id,
                                'isModify': 0,
                                /*   'ftIQCDTO.ftResultDTO.yield': parseInt(yieldPostVal) / 100,
                                 'ftIQCDTO.ftResultDTO.fail': parseInt(failPostVal)*/
                            };
                        } else if (buttonName == 'Baking') {
                            var json = {
                                'processId': FTProcessDTO.id,
                                'isModify': 0,
                                /*  'ftBakingDTO.ftResultDTO.yield': parseInt(yieldPostVal) / 100,
                                 'ftBakingDTO.ftResultDTO.fail': parseInt(failPostVal)*/
                            };
                        } else {
                            var json = {
                                'processId': FTProcessDTO.id,
                                'isModify': 0,
                                /*   'ftFinishDTO.ftResultDTO.yield': parseInt(yieldPostVal) / 100,
                                 'ftFinishDTO.ftResultDTO.fail': parseInt(failPostVal)*/
                            };
                        }
                    } else {
                        var json = {'processId': FTProcessDTO.id, 'isModify': 0};
                    }
                    return json;
                }

                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNode(checkedId, json, nodeName);
                });
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNodeThenEndNode(checkedId, json, nodeName);
                });
            });
        };

        var onFQCNodeClicked = function (checkedId, FTProcessDTO, ftNodeDTO) {
            var nodeName = ftNodeDTO.name;
            buttonName = ftNodeDTO.name;
            var sblDTOs = ftNodeDTO.sblDTOs;
            buttonName = buttonName.startsWith("Test-") ? "Test" : buttonName;
            $.get('<%=path%>/FT_' + buttonName + '.jsp').done(function (html) {
                $('#ftDetail').html(html);
                var elm;
                for (var index in ftNodeDTO) {
                    if (ftNodeDTO[index] != null) {
                        elm = $('#ftDetail').find('#' + index + 'ID');
                        elm.val(ftNodeDTO[index]);
                    }
                }
                var ftStringName = 'ft' + buttonName + 'DTO';
                if (ftNodeDTO.hasOwnProperty(ftStringName)) {

                } else {
                    for (var index in ftNodeDTO['ftPassNodeDTO']) {
                        var input = ftNodeDTO['ftPassNodeDTO'];
                        if (input[index] != null) {
                            $('#ftDetail').find('#' + ftStringName + '_' + index + 'ID').val(input[index]);
                        }
                    }
                }
                parseInt(ftNodeDTO['ftPassNodeDTO']['result']) ? $('#ftDetail').find('#' + ftStringName + '_resultID input:first').attr('checked', true) : $('#ftDetail').find('#' + ftStringName + '_resultID input:last').attr('checked', true);
                if (ftNodeDTO.ftState == 3) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").removeClass('hidden');
                    $('#recordAdd').attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").removeClass('hidden');
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(1)").attr('disabled', true);
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").attr('disabled', true);

                }
                if (ftNodeDTO.ftState == 2) {
                    $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").css('display', 'none');
                    $('#ftDetail').find('#save' + buttonName + '').find("button~.hidden").removeClass('hidden');
                    $('#ftDetail').find('input').attr('disabled', false);
                    $('#ftDetail').find('.yield').attr('disabled', true);
                    $('#ftDetail').find('.fail').attr('disabled', true);
                }
                if (ftNodeDTO.ftState == 1 || (ftNodeDTO.ftState == 3 )) {
                    $('#ftDetail').find('input').attr('disabled', true);
                    $('#ftDetail').find('textarea').attr('disabled', true);
                    $('#ftDetail').find('select').attr('disabled', true);
                }

                function collectData() {
                    if ((ftNodeDTO.hasOwnProperty(ftStringName)) && (ftNodeDTO[ftStringName].hasOwnProperty('ftResultDTO'))) {
                        var n = 0;
                        var binValuetoSend = ftNodeDTO[ftStringName]['ftResultDTO']['bin'];
                        var binValueGet = [];
                        $.each($('#ftDetail').find('table td .flag1'), function () {
                            binValueGet.push(this.value);
                        });
                        for (var k = 0; k < binValuetoSend.length; k++) {
                            if (binValuetoSend[k] != '-1') {
                                binValuetoSend[k] = binValueGet[n++];
                            }
                        }
                        $('#ftDetail').find('table .flag2 input').val(binValuetoSend);
                        var yieldPostVal = $('#ftDetail').find('table .yield').val();
                        var failPostVal = $('#ftDetail').find('table .fail').val();
                        var json = {
                            'processId': FTProcessDTO.id,
                            'isModify': 1,
                            /*          'ftFinishDTO.ftResultDTO.yield': parseInt(yieldPostVal) / 100,
                             'ftFinishDTO.ftResultDTO.fail': parseInt(failPostVal) / 100*/
                        };
                    } else {
                        var json = {'processId': FTProcessDTO.id, 'isModify': 1};
                    }
                    return json;
                }

                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(3)").on('click', function () {
                    //
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    startNode(checkedId, FTProcessDTO, nodeName);
                });
                //if(ftNodeDTO.state != 1) return;
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-child(2)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNode(checkedId, json, nodeName);
                });
                $('#ftDetail').find('#save' + buttonName + '').find("button:nth-last-of-type(1)").on('click', function () {
                    if (!Validator.Validate($('#ftDetail').find('form')[0], 3))return;
                    var json = collectData();
                    saveNodeThenEndNode(checkedId, json, nodeName);
                });
            });
        };
        //here
        var onClickedDispatcher = function (checkedId, FTProcessDTO, nodeName) {
            var callbackMap = {
                "IQC": onIQCNodeClicked,
                "Baking": onBakingNodeClicked,
                "GuTest": onGuTestNodeClicked,
                "Test": onComposedTestNodeClicked,
                "Finish": onFinishNodeClicked,
                "FVI": onFVINodeClicked,
                "Packing": onPackingNodeClicked,
                "OQC": onOQCNodeClicked,
                "FQC": onFQCNodeClicked
            };
            var ftNodeDTO = FTProcessDTO.ftNodeDTOs.filter(function (value) {
                return value.name == nodeName
            })[0];
            var callback = callbackMap[ftNodeDTO.name.startsWith("Test-") ? "Test" : ftNodeDTO.name];

            if (arguments[3]) {
                var testDetailToClick = arguments[3];
                callback(checkedId, FTProcessDTO, ftNodeDTO, testDetailToClick);
            } else {
                callback(checkedId, FTProcessDTO, ftNodeDTO);
            }
        };


        var reelDisk = function ($tableReelDisk, $tableReelDiskStatic, $tableIntegratedReelDisk, FTProcessDTO, checkedId) {
            $tableReelDiskStatic.html('');
            $tableReelDisk.html('');
            $tableIntegratedReelDisk.html('');
            $.get('${pageContext.request.contextPath}/ReelDisk/findReelDiskByFTLotId/' + checkedId + '.koala').done(function (reelDisks) {
                localStorage.setItem("reelcodeData", JSON.stringify(reelDisks));
                var reelDiskHead = function (reelDisk) {
                    var headRow = $('<tr></tr>');
                    headRow.append('<th></th>');
                    for (var prop in reelDisk) {
                        var props = ['quality', 'reelCode', 'combinedLotNumber', 'quantity', 'partNumber', 'packagingTimeStr', 'dateCode', 'fromReelCode']
                        if (props.indexOf(prop) != -1) {
                            headRow.append($('<th>' + prop + '</th>'));
                        } else if (prop == 'packagingTimeStr') {
                            headRow.append($('<th>packagingTime</th>'));
                        } else {
                            headRow.append($('<th hidden>' + prop + '</th>'));
                        }
                    }
                    return headRow;
                }
                var reelDiskRow = function (reelDisk) {

                    var row = $('<tr></tr>');
                    row.append($('<td><input style="width:30px;" type="checkbox" logic="' + reelDisk.logic + '" isFull ="' + reelDisk.isFull + '"value="' + reelDisk.id + '"></td>'));
                    for (var prop in reelDisk) {
                        var props = ['quality', 'reelCode', 'combinedLotNumber', 'quantity', 'partNumber', 'packagingTimeStr', 'dateCode', 'fromReelCode']
                        if (props.indexOf(prop) != -1) {
                            if (reelDisk[prop] == null) {
                                var html3 = $('<td><input type="text" style="width:150px;"  value=" "></td>');
                            } else {
                                var html3 = $('<td><input type="text" style="width:150px;"  value="' + reelDisk[prop] + '"></td>');
                            }
                        } else {
                            if ((prop.search('DTO') > 0) && (reelDisk[prop] != null)) {
                                var html3 = $('<td hidden><input type="text" value=' + reelDisk[prop].id + '></td>');
                            } else {
                                var html3 = $('<td hidden><input type="text" value=' + reelDisk[prop] + '></td>');
                            }
                        }
                        row.append(html3);
                    }
                    return row;
                }
                var integrateReelDiskRow = function (reelDisk) {
                    var row = $('<tr></tr>');
                    row.append('<td><input style="width:30px;" type="checkbox" value="' + reelDisk.id + '"></td>');
                    row.append(Object
                            .keys(reelDisk)
                            .map(function (prop) {
                                var propValue = reelDisk[prop];
                                var props = ['quality', 'reelCode', 'combinedLotNumber', 'quantity', 'partNumber', 'packagingTimeStr', 'dateCode', 'fromReelCode']
                                if (props.indexOf(prop) != -1) {
                                    if (propValue == null) {
                                        return $('<td><input type="text" style="width:150px;" s value=" "></td>');
                                    } else {
                                        return $('<td><input type="text" style="width:150px;"  value="' + propValue + '"></td>');
                                    }
                                } else {
                                    if ((prop.search('DTO') > 0) && (propValue != null)) {
                                        return $('<td hidden><input type="text" value=' + propValue.id + '></td>');
                                    } else {
                                        return $('<td hidden><input type="text"  value=' + propValue + '></td>');
                                    }
                                }
                            }));
                    return row;
                }
                if (reelDisks.length <= 0 || reelDisks[0] == null) {
                    $.get('${pageContext.request.contextPath}/ReelDisk/findReelDiskByIntegratedLotId/' + checkedId + '.koala')
                            .done(function (integrateReelDisk) {
                                if (integrateReelDisk.success) {
                                    $tableIntegratedReelDisk.append(integrateReelDiskRow(integrateReelDisk.data));
                                }
                            });
                    return;
                }


                $tableReelDiskStatic.append(reelDiskHead(reelDisks[0]));
                reelDisks.forEach(function (reelDisk) {
                    $tableReelDiskStatic.append(reelDiskRow(reelDisk));
                })

                var parentIntegrationIds = reelDisks
                        .filter(function (reelDisk) {
                            return reelDisk.parentIntegrationIds;
                        })
                        .map(function (reelDisk) {
                            reelDisk.parentIntegrationIds;
                        });

                $tableIntegratedReelDisk.append(reelDiskHead(reelDisks[0]));

                $.get('${pageContext.request.contextPath}/ReelDisk/findReelDiskByIntegratedLotId/' + checkedId + '.koala')
                        .done(function (integrateReelDisk) {
                            if (integrateReelDisk.success) {
                                $tableIntegratedReelDisk.append(integrateReelDiskRow(integrateReelDisk.data));
                            }
                        });

                if (parentIntegrationIds.length > 0) {
                    parentIntegrationIds = parentIntegrationIds.join(",");
                    $.get('${pageContext.request.contextPath}/ReelDisk/getAll/' + parentIntegrationIds + '.koala')
                            .done(function (integrateReelDisks) {
                                $tableIntegratedReelDisk.append(
                                        integrateReelDisks.map(function (integrateReelDisk) {
                                            return integrateReelDiskRow(integrateReelDisk);
                                        }));
                            });

                }
            });
        }

        var reelPacking = function (FTProcessDTO, checkedId) {
            var $tableIntegratedReelDisk = $('#ftDetail').find('table:last');
            var $tableReelDiskStatic = $('#ftDetail').find('table:first');
            $tableReelDiskStatic.html('');
            $tableIntegratedReelDisk.html('');
            reelDisk($tableReelDiskStatic, $tableReelDiskStatic, $tableIntegratedReelDisk, FTProcessDTO, checkedId);
        }
    </script>
</head>
<body>
<div style="width:100%;margin-right:auto; margin-left:auto; padding-top: 15px;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-2 sidebar"><%--此div块是左侧动态按钮--%>
                <div style="width:100%;height:914px;border:1px solid grey;">
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
                                        <input name="state" class="form-control" type="text" style="width:80px;" id="stateID"/>
                                    </div>
<!--                                     <label class="control-label" style="width:50px;float:left;">站点:&nbsp;</label>
                                    <div style="margin-left:5px;float:left;">
                                        <input name="state" class="form-control" type="text" style="width:80px;"
                                               id="createEmployNoID"/>
                                    </div> -->
                                    <label class="control-label" style="width:50px;float:left;">Lot:&nbsp;</label>
                                    <div style="margin-left:5px;float:left;">
                                        <input name="lotNumber" class="form-control" type="text" style="width:80px;" id="lotNumberID"/>
                                    </div>
                                    <label class="control-label" style="width:50px;float:left;">时段:&nbsp;</label>
                                    <div style="margin-left:5px;float:left;">
                                        <div class="input-group date form_datetime" style="width:100px;float:left;">
                                            <input type="text" class="form-control" style="width:100px;"
                                                   name="createTimestamp" id="createTimestampID_start">
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                        </div>
                                        <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
                                        <div class="input-group date form_datetime" style="width:100px;float:left;">
                                            <input type="text" class="form-control" style="width:100px;"
                                                   name="createTimestampEnd" id="createTimestampID_end">
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td style="vertical-align: bottom;">
                                <button id="search" type="button" style="position:relative; margin-left:25px; margin-left:50px; top: -15px" class="btn btn-primary">
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
                <div style="height:400px; border:1px solid grey;overflow:auto;margin-bottom: 12px;" id="ftDetail"></div>
                <%--此div块是按钮对应内容--%>
            </div>
        </div>
    </div>
</div>
</body>
</html>
