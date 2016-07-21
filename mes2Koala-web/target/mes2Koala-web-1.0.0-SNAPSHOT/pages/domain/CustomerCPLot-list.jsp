<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <%@ page import="java.util.Date" %>
    <script type="text/javascript" src="<%=contextPath %>/js/common.js"></script>
    <LINK rel="stylesheet" type="text/css"
          href="<%=contextPath %>/js/easyui/themes/default/easyui.css"/>
    <script type="text/javascript"
            src="<%=contextPath %>/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <% String formId = "form_" + new Date().getTime();
        String gridId = "grid_" + new Date().getTime();
        String path = request.getContextPath() + request.getServletPath().substring(0, request.getServletPath().lastIndexOf("/") + 1);
    %>
    <script>
        //加载runcard预览打印界面
        var showRuncardInfo = function (id, siteName) {
            var cpinfoId = id;
            createmodalwindow("UEditor", 800, 500, '<%=contextPath %>/cpRuncardController/getUEditorPage.koala?cpinfoId=' +
                    cpinfoId + '&currentSite=' + siteName + '&totalSites=' + siteName);

        };
    </script>
    <script type="text/javascript">
        var grid;
        var form;
        var _dialog;
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
                    var startTimeVal = form.find('#timeID_start');
                    var startTime = startTimeVal.parent();
                    var endTimeVal = form.find('#timeID_end');
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
                    var contents = {};//加载字典表中的物料类型，因为是在grid中加载，因此需在加载grid前
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/materialType.koala',
                        type: 'GET',
                        dataType: 'json'
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            /*contents.push({
                             title: msg[i]['label'],
                             value: msg[i]['value']
                             });*/
                            contents[msg[i]['value']] = msg[i]['label'];
                        }
                    });
                    var self = this;
                    var width = 130;
                    return grid.grid({
                        identity: "id",
                        buttons: [
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>下单</button>',
                                action: 'createCPLot'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>批量下单</button>',
                                action: 'orderInBatches'
                            },
                            {
                                content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-plus"><span>排产界面</button>',
                                action: 'gantt'
                            },
/*                             {
                                content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>WMSTest</button>',
                                action: 'WMSTest'
                            }, */
                        ],
                        url: "${pageContext.request.contextPath}/CustomerCPLot/pageJson.koala",
                        columns: [
                            {
                                title: '状态', name: 'state', width: 80, render: function (rowdata, name, index) {
                                var state = -1;
                                state = rowdata['state'];

                                var param = '';
                                switch (state) {
                                    case 0:
                                        param = '未下单';
                                        break;
                                    case 1:
                                        param = '已下单';
                                        break;
                                    default:
                                        param = '未知状态';
                                }
                                	return param;
                            	}
                            },{
                                title: '客户编号',
                                name: 'customerNumber',
                                width: width,
                                render: function (rowdata, name, index) {
                                    var param = '';
                                    param += rowdata['customerNumber'];
                                    param += '';
                                    return param;
                                }
                            },
                            {title: '来料型号', name: 'customerProductNumber', width: width},
                            {
                                title: '出货型号',
                                name: 'shipmentProductNumber',
                                width: width,
                                sortable: true, sortName: 'customerProductNumber'
                            },{
                                title: '艾科内部产品型号',
                                name: 'internalProductNumber',
                                width: width
                            },{
                                title: '客户批号',
                                name: 'customerLotNumber',
                                width: width,
                                render: function (rowdata, name, index) {
                                    var param = '';
                                    param += rowdata['customerLotNumber'];
                                    param += '';
                                    return param;
                                }
                            },{title: '数量', name: 'incomingQuantity', width: 70},
                            {
                            	title: '艾科批号',
                            	name: 'internalLotNumber',
                            	width: width,
                                render: function (rowdata, name, index) {
                                    var param = '';
                                    if(rowdata['internalLotNumber']!=null)
                                    param += rowdata['internalLotNumber'];
                                    param += '';
                                    return param;
                                }
                            },
                            {
                                title: '客户PPO',
                                name: 'customerPPO',
                                width: width,
                                render: function (rowdata, name, index) {
                                    var param = '';
                                    param += rowdata['customerPPO'];
                                    param += '';
                                    return param;
                                }
                            },
                            {
                                title: '封装批号',
                                name: 'packingLot',
                                width: width
//                                sortable: true,
//                                sortName: 'packageNumber'
                            },
                            {
                                title: '晶圆批号',
                                name: 'waferLot',
                                width: width
                            },
                            {title: '版本型号', name: 'productVersion', width: width},
                            {title: '来料型号', name: 'customerProductNumber', width: width},
                            {
                                title: '来料形式',
                                name: 'incomingStyle',
                                width: 70,
                                render: function (rowdata, name, index) {
                                    if (rowdata[name] == '01') {
                                        return '晶舟盒';
                                    } else {
                                        return '圆盒';
                                    }
                                }
                            },
                            {title: '规格', name: 'size', width: 70},
                            {title: 'MASK_NAME', name: 'maskName', width: width},
                            {title: '来料日期', name: 'incomingDate', width: width},
                            {title: '下单人员', name: 'orderUser', width: width},
                            {title: '下单时间', name: 'orderDate', width: width,sortable: true, sortName: 'orderDate'},
                            {title: '物料类型', name: 'materialType', width: width},
                            {title: '来料类型', name: '', width: width},
                        ]
                    }).on({
                        'orderInBatches': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择要批量下单的批次'
                                });
                                return;
                            }
                            var orderInBatches = function () {
                                self.orderInBatches(indexs, $this);
                            };
                            $this.confirm({
                                content: '确定要批量下单所选批次吗?',
                                callBack: orderInBatches
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
                        'createCPLot': function (event, data) {

                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择需要下单的批次'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '请使用批量下单'
                                })
                                return;
                            }
                            if (data.item[0].state == 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '该批次已下单，请重新选择'
                                })
                                return;
                            }
                            if ((data.item[0].processTemplateName == '') || (data.item[0].processTemplateContent == '')) {
                                $this.message({
                                    type: "warning",
                                    content: "该产品未绑定process,不可下单"
                                })
                                return
                            }
                            self.createCPLot(indexs[0], $this);
                        },
                        'WMSTest': function () {
                            /*
                             var data = [{
                             name: "json",
                             value: '[{"ID":"f0054161-e66a-44d0-a68e-f9ccd59e3039","TASK_NUM":"WIN20151217001","CUS_PPO":"100048816","CUS_LOT":"D82965.04","CUS_CODE":"1409-SMI-SH","IN_PARTNUM":"6355C-40","QUANTITY":25,"SIZE":"02","MASK_NAME":"NA","BONDED_TYPE":"03","BONDED_NUM":"NA","MATERIAL_TYPE":"01","STORAGE_TYPE":"01","PACKING_LOT":"D82965.04","WAFER_LOT":"D82965.04","TASK_STATUS":"01","STORAGE_ID":"6101a715-b610-404f-95fe-2c60fd76768e","IN_TYPE":"来料","TASK_IS_END":"01","REWORK_TYPE":"01","CUS_MATERIAL_TYPE":"08","OUT_PARTNUM":"6355C-40","ingWafers":[{"ID":"new_","WAFER_CODE":"D82965-01","WAFER_INDEX":"01","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-02","WAFER_INDEX":"02","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-03","WAFER_INDEX":"03","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-04","WAFER_INDEX":"04","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-05","WAFER_INDEX":"05","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-06","WAFER_INDEX":"06","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-07","WAFER_INDEX":"07","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-08","WAFER_INDEX":"08","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-09","WAFER_INDEX":"09","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-10","WAFER_INDEX":"10","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-11","WAFER_INDEX":"11","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-12","WAFER_INDEX":"12","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-13","WAFER_INDEX":"13","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-14","WAFER_INDEX":"14","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-15","WAFER_INDEX":"15","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-16","WAFER_INDEX":"16","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-17","WAFER_INDEX":"17","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-18","WAFER_INDEX":"18","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-19","WAFER_INDEX":"19","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-20","WAFER_INDEX":"20","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-21","WAFER_INDEX":"21","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-22","WAFER_INDEX":"22","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-23","WAFER_INDEX":"23","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-24","WAFER_INDEX":"24","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-25","WAFER_INDEX":"25","IS_TRUE":0}],"STATUS":"01","FLAG":1,"flag":1,"id":"f0054161-e66a-44d0-a68e-f9ccd59e3039","quantity":25,"bonded_NUM":"NA","cus_CODE":"1409-SMI-SH","cus_LOT":"D82965.04","cus_PPO":"100048816","in_PARTNUM":"6355C-40","mask_NAME":"NA","size":"02","task_NUM":"WIN20151217001","wafer_LOT":"D82965.04","storage_ID":"6101a715-b610-404f-95fe-2c60fd76768e","in_TYPE":"来料","bonded_TYPE":"03","material_TYPE":"01","storage_TYPE":"01","packing_LOT":"D82965.04","rework_TYPE":"01","cus_MATERIAL_TYPE":"08","out_PARTNUM":"6355C-40","task_IS_END":"01","task_STATUS":"01"}]'
                             }];
                             */
                            var data = [{
                                name: "json",
                                value: '[{"ID":"f0054161-e66a-44d0-a68e-f9ccd59e3039","TASK_NUM":"WIN20151217001","CUS_PPO":"100048816","CUS_LOT":"D82965.04","CUS_CODE":"1304-JCT-WX","IN_PARTNUM":"GXBB_C","QUANTITY":25,"SIZE":"02","MASK_NAME":"NA","BONDED_TYPE":"03","BONDED_NUM":"NA","MATERIAL_TYPE":"01","STORAGE_TYPE":"01","PACKING_LOT":"D82965.04","WAFER_LOT":"D82965.04","TASK_STATUS":"01","STORAGE_ID":"6101a715-b610-404f-95fe-2c60fd76768e","IN_TYPE":"来料","TASK_IS_END":"01","REWORK_TYPE":"01","CUS_MATERIAL_TYPE":"08","OUT_PARTNUM":"6355C-40","ingWafers":[{"ID":"new_","WAFER_CODE":"D82965-01","WAFER_INDEX":"01","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-02","WAFER_INDEX":"02","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-03","WAFER_INDEX":"03","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-04","WAFER_INDEX":"04","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-05","WAFER_INDEX":"05","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-06","WAFER_INDEX":"06","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-07","WAFER_INDEX":"07","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-08","WAFER_INDEX":"08","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-09","WAFER_INDEX":"09","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-10","WAFER_INDEX":"10","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-11","WAFER_INDEX":"11","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-12","WAFER_INDEX":"12","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-13","WAFER_INDEX":"13","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-14","WAFER_INDEX":"14","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-15","WAFER_INDEX":"15","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-16","WAFER_INDEX":"16","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-17","WAFER_INDEX":"17","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-18","WAFER_INDEX":"18","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-19","WAFER_INDEX":"19","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-20","WAFER_INDEX":"20","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-21","WAFER_INDEX":"21","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-22","WAFER_INDEX":"22","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-23","WAFER_INDEX":"23","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-24","WAFER_INDEX":"24","IS_TRUE":0},{"ID":"new_","WAFER_CODE":"D82965-25","WAFER_INDEX":"25","IS_TRUE":0}],"STATUS":"01","FLAG":1,"flag":1,"id":"f0054161-e66a-44d0-a68e-f9ccd59e3039","quantity":25,"bonded_NUM":"NA","cus_CODE":"1409-SMI-SH","cus_LOT":"D82965.04","cus_PPO":"100048816","in_PARTNUM":"6355C-40","mask_NAME":"NA","size":"02","task_NUM":"WIN20151217001","wafer_LOT":"D82965.04","storage_ID":"6101a715-b610-404f-95fe-2c60fd76768e","in_TYPE":"来料","bonded_TYPE":"03","material_TYPE":"01","storage_TYPE":"01","packing_LOT":"D82965.04","rework_TYPE":"01","cus_MATERIAL_TYPE":"08","out_PARTNUM":"6355C-40","task_IS_END":"01","task_STATUS":"01"}]'
                            }];

                            $.post('${pageContext.request.contextPath}/WMSService/CustomerCPLot/addAll.koala', data).done(function (result) {
                                grid.data('koala.grid').refresh();
                                grid.message({
                                    type: 'success',
                                    content: '成功'
                                });
                            });
                        },
                        'gantt': function (event, data) {

                            /*	 var indexs = data.data;
                             var $this = $(this);
                             if(indexs.length == 0){
                             $this.message({
                             type: 'warning',
                             content: '请选择一条记录进行查看'
                             });
                             return;
                             }
                             if(indexs.length > 1){
                             $this.message({
                             type: 'warning',
                             content: '只能选择一条记录进行查看'
                             })
                             return;
                             }
                             self.modify(indexs[0], $this);*/
                            openTab('/pages/domain/ProductionSchedule-list.jsp', '测试任务', 'munuMark234')
                            //window.history.back(-1);
                        }
                    });
                },
                separateCPLot: function (id, grid) {
                    var self = this;
                    var originNumber = 0;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:400px">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">分批</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">分批</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/CustomerCPLot-divide.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        $.get('${pageContext.request.contextPath}/CustomerCPLot/get/' + id + '.koala').done(function (json) {
                            json = json.data;
                            originNumber = json['incomingQuantity'];
                            dialog.find('#incomingQuantityID').val(json['incomingQuantity']);
                            dialog.find('#customerLotNumberID').val(json['customerLotNumber']);
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
                                var html = $('<div class = "form-group"><label class="col-lg-3 control-label">子批' + parseInt(i + 1) + ':</label><div class="col-lg-6"><input style="display:inline; width:94%;" class="form-control" type="text" /></div></div>');
                                dialog.find('#group').append(html);
                            }
                        })
                        dialog.find('#group').on('change', 'input', function () {
                            var totalInput = 0;
                            var count = 0
                            $.each(dialog.find('#group').find('input'), function () {
                                if (this.value == '') {
                                    count++;
                                    return true
                                }
                                totalInput += parseInt(this.value);
                            });
                            if (parseInt(count) == 1) {
                                dialog.find('#group').find('input').each(function () {
                                    if (this.value == '') {
                                        this.value = parseInt(dialog.find('#incomingQuantityID').val()) - totalInput;
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
                            $.each(dialog.find('#group input'), function () {
                                divideAmount.push(parseInt(this.value));
                                totalNumber += parseInt(this.value);
                                if ((divideAmount < 0) || divideAmount > originNumber) {

                                    flagSperate = 0;
                                    grid.message({
                                        type: 'warning',
                                        content: '分批数量为负数或单批数量不能超过原数量！'
                                    })
                                    return false;
                                }
                            })
                            if (flagSperate == 0) return;
                            if (totalNumber != originNumber) {
                                grid.message({
                                    type: 'warning',
                                    content: '分批总和不等于原批数量！'
                                })
                                return;
                            }
                            data = {parentId: id, separationQties: divideAmount.join(',')};
                            $.post('${pageContext.request.contextPath}/CustomerCPLot/lotSeparate.koala', data).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '分批成功'
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
                integrateCPLot: function (grid) {
                    var self = this;
                    var flag = 0;
                    var totalAmount = 0;
                    var ids = [];
                    var combinedLotNumber = '';
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:750px">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">合批</h4></div><div class="modal-body" style="height:600px">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/CustomerCPLot-integrate.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);

                        var contents = {
                            'VC1613': 0,
                            'VC1614': 0,
                            'VC1616': 0,
                            'VC1618': 0,
                            'VC1623': 0,
                            'VC1673': 0,
                            'VC3278': 0,
                            'VC3584': 0,
                            'VC5268Q': 0,
                            'VC5276': 0,
                            'VC5276Q': 0,
                            'VC5278': 0,
                            'VC5278-21': 0,
                            'VC5278Q': 0,
                            'VC5282': 0,
                            'VC5318': 0,
                            'VC5341': 0,
                            'VC5341-21': 0,
                            'VC5342': 0,
                            'VC5342-21': 0,
                            'VC5343': 0,
                            'VC5345': 0,
                            'VC5348': 0,
                            'VC5439': 0,
                            'VC7582': 0,
                            'VC7584': 0,
                            'VC7584-21': 0,
                            'VC7584-51': 0,
                            'VC7590': 0,
                            'VC7590-21': 0,
                            'VC7590-31': 0,
                            'VC7590-51': 0,
                            'VC7592': 0,
                            'VC7592-11': 0,
                            'VC7593': 0,
                            'VC7593-21': 0,
                            'VC7594': 0,
                            'VC7594-31': 0,
                            'VC7810': 0,
                            'VC7902': 0,
                            'VC7903': 0,
                            'VC7905': 0,
                            'VC7909': 0
                        };
                        dialog.find('#grantAuthorityToUserButton').on('click', function () {
                            var amount = 0;
                            var diffLotNumber = 0;
                            var grantRolesToUserTableItems = dialog.find('#notGrantAuthoritiesToUserGrid').getGrid().selectedRows();
                            if (grantRolesToUserTableItems.length == 0) {
                                dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                    type: 'warning',
                                    content: '请选择要需要合并的行'
                                });
                                return;
                            }
                            $.each(grantRolesToUserTableItems, function () {
                                if (!contents.hasOwnProperty(this.customerProductNumber)) {
                                    dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                        type: 'warning',
                                        content: '产品型号不符合'
                                    });
                                    flag = 1;
                                    return true;
                                }

                                contents[this.customerProductNumber] = 1;

                                if (this.incomingQuantity < 10000) {
                                    dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                        type: 'warning',
                                        content: '来料数量不能小于10K'
                                    });
                                    flag = 1;
                                    return true;
                                } else {
                                    totalAmount += this.incomingQuantity;
                                    if (totalAmount > 50000) {
                                        dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                            type: 'warning',
                                            content: '合批后的数量不能超过50K'
                                        });
                                        //totalAmount -= this.incomingQuantity;
                                        flag = 1;
                                        return false;
                                    }
                                }

                                if (combinedLotNumber.indexOf(this.customerLotNumber) == -1) {
                                    if (++diffLotNumber > 2) {
                                        dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                            type: 'warning',
                                            content: '不同客批不能超过两个'
                                        });
                                        flag = 1;
                                        return true;
                                    } else {
                                        combinedLotNumber += this.customerLotNumber + '/';
                                    }
                                }
                                ids.push(this.id);
                            });
                            combinedLotNumber = combinedLotNumber.substring(0, combinedLotNumber.length - 1);
                            if (grantRolesToUserTableItems.length > 2) {

                                for (var i in contents) {
                                    amount += contents[i];
                                }
                            }

                            if (amount >= 2) {
                                dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                    type: 'warning',
                                    content: '产品型号不一致'
                                });
                                flag = 1;
                            }
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
                        dialog.find('#searchCPLot').on('click', function () {
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
                                    title: "客户批号",
                                    name: "customerLotNumber",
                                    width: 100
                                }, {
                                    title: "来料数量",
                                    name: "incomingQuantity",
                                    width: 80
                                }];

                                dialog.find('#notGrantAuthoritiesToUserGrid').grid({
                                    identity: 'id',
                                    columns: notGrantRolecolumns,
                                    isShowPages: false,
                                    url: "${pageContext.request.contextPath}/CustomerCPLot/pageJson.koala"
                                });
                            }
                        });
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        var data = {ids: ids.join(',')};

                        $.post('${pageContext.request.contextPath}/CustomerCPLot/lotCombineManually.koala', data).done(function (result) {
                            if (result.success) {
                                dialog.modal('hide');
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                    type: 'success',
                                    content: '合批成功'
                                });
                            } else {
                                dialog.find('.modal-content').message({
                                    type: 'error',
                                    content: result.errorMessage
                                });
                                flag = 0;
                                totalAmount = 0;
                                ids = [];
                            }
                        });
                    });
                },
                /*	    add: function(grid){
                 var self = this;
                 var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:700px">'
                 +'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                 +'data-dismiss="modal" aria-hidden="true">&times;</button>'
                 +'<h4 class="modal-title">FT下单</h4></div><div class="modal-body">'
                 +'<p>One fine body&hellip;</p></div><div class="modal-footer">'
                 +'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                 +'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                 +'</div></div>');
                 $.get('<%=path%>/CustomerCPLot-add.jsp').done(function(html){
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
                 $.post('${pageContext.request.contextPath}/CustomerCPLot/add.koala', dialog.find('form').serialize()).done(function(result){
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
                 },
                 modify: function(id, grid){
                 var self = this;
                 var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:700px"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                 $.get('<%=path%>/CustomerCPLot-update.jsp').done(function(html){
                 dialog.find('.modal-body').html(html);
                 self.initPage(dialog.find('form'));
                 $.get( '${pageContext.request.contextPath}/CustomerCPLot/get/' + id + 'customerLotDTO.koala').done(function(json){
                 json = json.data;
                 var elm;
                 for(var index in json){
                 //此处寻找json包内的customerCPLotDTO包，并按照命名规则解析填入对应的控件中
                 if ( typeof(json[index]) == "object" ){
                 for(var DTOindex in json[index] ){
                 elm = dialog.find('#' + index + DTOindex + 'ID' );
                 elm.val(json[index][DTOindex]);
                 }
                 } else {
                 elm = dialog.find('#'+ index + 'ID');
                 if(elm.hasClass('select')){
                 elm.setValue(json[index]);
                 }else{
                 elm.val(json[index]);
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
                 $.post('${pageContext.request.contextPath}/CustomerCPLot/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
                 },*/
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
                    $.post('${pageContext.request.contextPath}/CustomerCPLot/delete.koala', data).done(function (result) {
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
                createCPLot: function (id, grid) {
                    debugger
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">CP下单</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">下单</button></div></div></div></div>');
                    $.get('<%=path%>/CPLot-create.jsp').done(function (html) {

                        // wafer复选框和文本框
                        dialog.find('.modal-body').html(html);

                        var $waferIndexheaderPlaceholder = dialog.find('#waferIndexheaderID');
                        var $waferIndexPlaceholder = dialog.find('#waferIndexID');
                        var $waferCodePlaceholder = dialog.find('#waferCodeID');

                        for (var i = 0; i < 5; ++i) {
                            var $row = $("<tr></tr>");
                            for (var j = 0; j < 5; ++j) {
                                var num = (i * 5 + j + 1);
                                num = num < '10' ? '0' + num : num;
                                var $waferIndex = $("<td style='width:32px'><input type='checkbox'  /></td>");
                                $waferIndexheaderPlaceholder.append("<td style='width:32px'>" + num + "</td>");
                                $waferIndexPlaceholder.append($waferIndex);
                                var $waferCode = $("<td >" + num + "<input type='text' class='form-control' style = 'width:100px'  /></td>");
                                $row.append($waferCode);
                            }
                            $waferCodePlaceholder.append($row);
                        }

                        self.initPage(dialog.find('form'));

                        //填充cp下单模态框的控件
                        $.get('${pageContext.request.contextPath}/CustomerCPLot/getCPVo/' + id + '.koala').done(function (json) {
//                            debugger;
                            json = json.data;
                            var elm;
                            for (var index in json) {

                                //此处寻找json包内的customerCPLotDTO包，并按照命名规则解析填入对应的控件中
                                if (typeof(json[index]) == "object") {
                                    for (var DTOindex in json[index]) {
                                        elm = dialog.find('#' + index + DTOindex + 'ID');
                                        elm.val(json[index][DTOindex]);
                                    }
                                } else {
                                    elm = dialog.find('#' + index + 'ID');
                                    if (elm.hasClass('select')) {
                                        elm.setValue(json[index]);
                                    } else {
                                        elm.val(json[index]);
                                    }
                                }
                            }
                            
                            //var shipmentProductNumber = dialog.find('#customerProductNumberID').val();
//                            var customerCPLot = {"customerLotDTO": json};
//                            for (var index in customerCPLot) {
//
//                                //此处寻找json包内的customerFTLotDTO包，并按照命名规则解析填入对应的控件中
//                                if (typeof(customerCPLot[index]) == "object") {
//                                    for (var DTOindex in customerCPLot[index]) {
//                                        elm = dialog.find('#' + index + DTOindex + 'ID');
//                                        elm.val(customerCPLot[index][DTOindex]);
//                                    }
//                                } else {
//                                    elm = dialog.find('#' + index + 'ID');
//                                    if (elm.hasClass('select')) {
//                                        elm.setValue(customerCPLot[index]);
//                                    } else {
//                                        elm.val(customerCPLot[index]);
//                                    }
//                                }
//                            }
//                            if (json.incomingStyle == '01') {
//                                dialog.find('#incomingStyleID').attr('checked', true);
//                            } else {
//                                dialog.find('#incomingStyleID_').attr('checked', true);
//                            }
							if(!!json['shipmentProductNumber']==0)
							{
								dialog.find('#shipmentProductNumberID').val(json['customerProductNumber']);
							}
							else{
								dialog.find('#shipmentProductNumberID').val(json['shipmentProductNumber']);
							}
							
                            

                        });
                        //查看Runcard
                        dialog.find("#showRuncard").on("click", function () {
                            showRuncardInfo(id, 'BeforeOrdered');
                        })

                        //填充cp下单模态框中 wafer部分
                        $.get('${pageContext.request.contextPath}/CustomerCPLot/getCPCustomerWafer.koala?customerCPLotId=' + id).done(function (json) {
                            json = json.data;

                            $(json).each(function () {
                                if (this.waferIndex != null) {
                                    var waferI = (this.waferIndex) - 1;
                                    dialog.find('#waferIndexID').find('td').eq(waferI).find('input').attr('checked', "checked");
                                    dialog.find('#waferIndexID').find('td').eq(waferI).find('input').attr('disabled', "disabled");
                                    dialog.find('#waferCodeID').find('td').eq(waferI).find('input').attr('value', this.waferCode);
                                    dialog.find('#waferCodeID').find('td').eq(waferI).find('input').attr('disabled', "disabled");
                                }
                            })
                        });
                        //填充lotnumber
                        $.get('${pageContext.request.contextPath}/CustomerCPLot/getExpectedLotNumber.koala?customerCPLotId=' + id).done(function (json) {
//                            debugger
                            if (json.success) {
                                json = json.data;
                                dialog.find('#InternalLotNumberID').val(json);
                            } else {
                                dialog.find('.modal-content').message({
                                    type: 'error',
                                    content: result.errorMessage
                                });
                            }
                        });
                        
                        $.get('${pageContext.request.contextPath}/CustomerCPLot/findPIDByCustomerCPLotId/'+id+'.koala').done(function (json) {
                          if (json.success) {
                              	json = json.data;
                              	var contents = [ {title : '请选择',value : ''} ];
                              	$.each(json,function(a){
                              		contents.push({title : json[a]['internalProductNumber'],value : json[a]['id']});
                              	})
	                  	      	dialog.find('#internalProductNumberID').select({
	                                  title: '请选择',
	                                  contents: contents
                             	}).on('change',function(){
                             		dialog.find('#internalProductNumberID_').val($(this).getValue());
                             	});
                          } else {
                              dialog.find('.modal-content').message({
                                  type: 'error',
                                  content: result.errorMessage
                              });
                          }
                      	});

                        //填充RCnumber
                        $.get('${pageContext.request.contextPath}/CustomerCPLot/getExpectedRCNumber.koala?customerCPLotId=' + id).done(function (json) {
                            if (json.success) {
                                json = json.data;
                                dialog.find('#rcNumberID').val(json);
                            } else {
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
                            debugger
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            $.post('${pageContext.request.contextPath}/CustomerCPLot/order.koala', dialog.find('form').serialize()).done(function (result) {

                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '下单成功'
                                    });
                                    showRuncardInfo(result.data, 'AfterOrdered');
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
                separationRestore: function (ids, grid) {
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/CustomerCPLot/separationRestore.koala', data).done(function (result) {
                        if (result.success) {
                            grid.data('koala.grid').refresh();
                            grid.message({
                                type: 'success',
                                content: '还原成功'
                            });
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.errorMessage
                            });
                        }
                    });
                },
                combinationRestore: function (id, grid) {
                    var data = [{name: 'id', value: id}]
                    $.post('${pageContext.request.contextPath}/CustomerCPLot/combinationRestore.koala', data).done(function (result) {
                        if (result.success) {
                            grid.data('koala.grid').refresh();
                            grid.message({
                                type: 'success',
                                content: '合批还原成功'
                            });
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.errorMessage
                            });
                        }
                    });
                },
				orderInBatches: function (ids, grid) {
					var self = this;
                    var data = [{name: 'ids', value: ids.join(',')}];
                    var dialog = $('<div class="modal fade"><div class="modal-dialog">'
            	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
            	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
            	        	+'<h4 class="modal-title">批量下单</h4></div><div class="modal-body"><form class="form-horizontal">'
            	        	+'<div class="form-group"> <label class="col-lg-3 control-label">PID:</label>'
            	            +'<div class="col-lg-9"><div class="btn-group select" id="internalProductNumberID"></div>'
            	            +'<input type="hidden" id="internalProductNumberID_" name="cpInfoId" dataType="Require"/><span class="required">*</span>'
            	            +'</div><form></div></div><div class="modal-footer">'
            	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
            	        	+'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
            	        	+'</div></div>');
            	            dialog.modal({
            	                keyboard:false
            	            }).on({
            	                'hidden.bs.modal': function(){
            	                    $(this).remove();
            	                }
            	            })
            	            $.get('${pageContext.request.contextPath}/CustomerCPLot/findPIDByCustomerCPLotId/'+ids[0]+'.koala').done(function (json) {
	                          if (json.success) {
	                              	json = json.data;
	                              	var contents = [ {title : '请选择',value : ''} ];
	                              	$.each(json,function(a){
	                              		contents.push({title : json[a]['internalProductNumber'],value : json[a]['id']});
	                              	})
		                  	      	dialog.find('#internalProductNumberID').select({
		                                  title: '请选择',
		                                  contents: contents
	                             	}).on('change',function(){
	                             		dialog.find('#internalProductNumberID_').val($(this).getValue());
	                             	});
	                          } else {
	                              dialog.find('.modal-content').message({
	                                  type: 'error',
	                                  content: result.errorMessage
	                              });
	                          }
	                      	});
            	            //self.initPage(dialog.find('form'));
            	        dialog.find('#save').on('click',{grid: grid}, function(e){
            	              if(!Validator.Validate(dialog.find('form')[0],3))return;
            	              data.push({name:'cpInfoId',value:$("#internalProductNumberID_").val()});
            	              $.post('${pageContext.request.contextPath}/CustomerCPLot/orderInBatches.koala', data).done(function (result) {

                                  if (result.success) {
                                	  dialog.modal('hide');
                                      grid.data('koala.grid').refresh();
                                      grid.message({
                                          type: 'success',
                                          content: '批量下单成功' + result.data
                                      });

                                      var ftLotIds = result.data.ids;
                                      //注释行为调取显示Runcard的方法，待开发
                                      for (var n = 0; n < ftLotIds.length; n++) {
                                          showRuncardInfo(ftLotIds[n], 'AfterOrdered');
                                      }
                                  } else {
                                      grid.message({
                                          type: 'error',
                                          content: result.errorMessage
                                      });
                                  }
                              });
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
        });

        var openDetailsPage = function (id) {
            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
            $.get('<%=path%>/CustomerCPLot-view.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);
                $.get('${pageContext.request.contextPath}/CustomerCPLot/get/' + id + '.koala').done(function (json) {
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
        				<label class="control-label" style="width:100px;float:left;">客户编号:&nbsp;</label>
        				<div style="margin-left:15px;float:left;">
        					<input name="customerNumber" class="form-control" type="text" style="width:180px;" id="customerNumberID"  />
        				</div>
        				<label class="control-label" style="width:100px;float:left;">客户批号:&nbsp;</label>
        				<div style="margin-left:15px;float:left;">
        					<input name="customerLotNumber" class="form-control" type="text" style="width:180px;" id="customerLotNumberID"  />
        				</div>
        				<label class="control-label" style="width:100px;float:left;">封装批号:&nbsp;</label>
        				<div style="margin-left:15px;float:left;">
        					<input name="packageNumber" class="form-control" type="text" style="width:180px;" id="packageNumberID"  />
        				</div>
        				<label class="control-label" style="width:100px;float:left;">产品型号:&nbsp;</label>
        				<div style="margin-left:15px;float:left;">
        					<input name="customerProductNumber" class="form-control" type="text" style="width:180px;" id="customerProductNumberID"  />
        				</div>
<!--         				<label class="control-label" style="width:100px;float:left;">数量:&nbsp;</label>
        				<div style="margin-left:15px;float:left;">
        					<input name="incomingQuantity" class="form-control" type="text" style="width:180px;" id="incomingQuantityID"  />
        				</div> -->
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
