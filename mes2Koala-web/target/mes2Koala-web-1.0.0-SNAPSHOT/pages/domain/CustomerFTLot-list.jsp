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
        var showRuncardInfo = function (id) {
            var ftinfoId = id;
            $.get('${pageContext.request.contextPath}/CustomerFTLot/get/' + id + '.koala').done(function (data) {
//debugger
                data = data.data.processTemplateContent;
                var totalSites = "";
                var str = data.split("|");
                for (var i = 0; i < str.length; i++) {
                    totalSites = totalSites + str[i] + ','
                }

                $.get('${pageContext.request.contextPath}/ueditor/isRuncardFinished2.koala?customerLotId=' + id + '&totalSites=' + totalSites
                ).done(function (data) {
//                    debugger
                    success = data['success'];
                    if (success == false) {

                        grid.message({
                            type: 'error',
                            content: 'Runcard没有填写完成'
                        })
                        return;
                    }

                    createmodalwindow("UEditor", 800, 500, '<%=contextPath %>/ueditor/getUEditorPage.koala?ftinfoId=' +
                            ftinfoId + '&currentSite=BeforeReplaced' + '&totalSites=BeforeReplaced');

                })
            })

        };

        //加载预览打印界面
        var showRuncardInfoAfterReplaced = function (ftLotId ) {
         //   $.get('${pageContext.request.contextPath}/getProcessTemplateByFtLotId/' + id + '.koala').done(function (data) {
		/* 		var data = data.data;
                var totalSites = "";
                var str = data.split("|");
                for (var i = 0; i < str.length; i++) {
                    totalSites = totalSites + str[i] + ','
                } */

 /*                $.get('${pageContext.request.contextPath}/ueditor/isRuncardFinished2.koala?customerLotId=' + id + '&totalSites=' + totalSites
                ).done(function (data) {
                    success = data['success'];
                    if (success == false) {

                        grid.message({
                            type: 'error',
                            content: 'Runcard没有填写完成'
                        })
                        return;
                    } */

                    createmodalwindow("UEditor", 800, 500, '<%=contextPath %>/ueditor/getUEditorPage.koala?ftinfoId=' +
                            ftLotId + '&currentSite=AfterReplaced' + '&totalSites=AfterReplaced');

             //   })
       //     })

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
                    var width = 150;
                    return grid.grid({
                        identity: "id",
                        buttons: [
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>下单</button>',
                                action: 'createFTLot'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>分批</button>',
                                action: 'separateFTLot'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>合批</button>',
                                action: 'combineFTLot'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>批量下单</button>',
                                action: 'orderInBatches'
                            },
                            {
                                content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>分批还原</button>',
                                action: 'separationRestore'
                            },
                            {
                                content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>合批还原</button>',
                                action: 'combinationRestore'
                            },
                            /*	    {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
                             {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},*/
                            //        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'},
                            {
                                content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-plus"><span>排产界面</button>',
                                action: 'gantt'
                            },
/*                             {
                                content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>WMSTest</button>',
                                action: 'WMSTest'
                            } */
                        ],
                        url: "${pageContext.request.contextPath}/CustomerFTLot/pageJson.koala",
                        columns: [
                            {
                                title: '状态', name: 'state', width: 90, sortable: true, sortName: 'state',render: function (rowdata, name, index) {
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
                            },
                            {
                            	title: '艾科批号',
                            	name: 'lotNumber',
                            	width: width,
                                render: function (rowdata, name, index) {
                                    var param = '';
                                    if(rowdata['lotNumber']!=null)
                                    param += rowdata['lotNumber'];
                                    param += '';
                                    return param;
                                }
                            },
                            {
                                title: '出货型号',
                                name: 'shipmentProductionNumber',
                                width: width,
                                sortable: true, sortName: 'shipmentProductNumber'
                            },
                            {
                                title: '艾科内部产品型号',
                                name: 'internalProductNumber',
                                width: width,
                                sortable: true, sortName: 'internalProductNumber'
                            },
                            {
                                title: '客户批号',
                                name: 'customerLotNumber',
                                width: width,
                                sortable: true, sortName: 'customerLotNumber',
                                render: function (rowdata, name, index) {
                                    var param = '';
                                    param += rowdata['customerLotNumber'];
                                    param += '';
                                    return param;
                                }
                            },
                            {
                                title: '客户编号',
                                name: 'customerNumber',
                                width: width,
                                sortable: true, sortName: 'customerNumber',
                                render: function (rowdata, name, index) {
                                    var param = '';
                                    param += rowdata['customerNumber'];
                                    param += '';
                                    return param;
                                }
                            },
                            {
                                title: '客户PPO',
                                name: 'customerPPO',
                                width: width,
                                sortable: true, sortName: 'customerPPO',
                                render: function (rowdata, name, index) {
                                    var param = '';
                                    param += rowdata['customerPPO'];
                                    param += '';
                                    return param;
                                }
                            },
                            {title: '封装批号', name: 'packageNumber', width: width,sortable: true, sortName: 'packageNumber'},
                            {title: '版本型号', name: 'productVersion', width: width,sortable: true, sortName: 'productVersion'},
                            {title: '来料型号', name: 'customerProductNumber', width: width,sortable: true, sortName: 'customerProductNumber'},
                            {
                                title: '数量',
                                name: 'incomingQuantity',
                                width: width,
                                sortable: true,
                                sortName: 'incomingQuantity'
                            },
                            {
                                title: '到料形式',
                                name: 'incomingStyle',
                                width: width,
                                sortable: true, sortName: 'incomingStyle',
                                render: function (rowdata, name, index) {
                                    if (rowdata[name] == '01') {
                                        return '静电散装';
                                    } else {
                                        return 'Tray盘箱装';
                                    }
                                }
                            },
                            {title: 'Date Code', name: 'dateCode', width: width,sortable: true, sortName: 'dateCode'},
                            {title: '来料日期', name: 'incomingDate', width: width,sortable: true, sortName: 'incomingDate'},
                            {
                                title: '物料类型',
                                name: 'materialType',
                                width: width,
                                sortable: true, sortName: 'materialType',
                                render: function (rowdata, name, index) {
                                    return contents[rowdata[name]];
                                }
                            },
                            {title: '保税类型', name: 'taxType', width: width,sortable: true, sortName: 'taxType'},
                            {title: '下单人员', name: 'orderDate', width: width},
                            {title: '下单时间', name: 'orderUser', width: width},
                            {title: 'WIre Bond', name: 'wireBond', width: width,sortable: true, sortName: 'wireBond'},
                            {title: 'Wafer Lot', name: 'waferLot', width: width,sortable: true, sortName: 'waferLot'},
                            {title: 'MFG PN', name: 'MFGPN', width: width,sortable: true, sortName: 'MFGPN'},
                            {title: '晶圆厂商', name: 'waferManufacturer', width: width,sortable: true, sortName: 'waferManufacturer'},
                            /* 							{ title: '操作', width: 120, render: function (rowdata, name, index)
                             {
                             var param = '"' + rowdata.id + '"';
                             var h = "<a href='javascript:openDetailsPage(" + param + ")'>查看</a> ";
                             return h;
                             }
                             } */
                        ]
                    }).on({
                        'separateFTLot': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行分批'
                                });
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行分批'
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
                            self.separateFTLot(indexs[0], $this);
                        },
                        'combineFTLot': function () {
                            self.combineFTLot($(this));
                        },
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
                        /*   'add': function(){
                         self.add($(this));
                         },
                         'modify': function(event, data){
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
                         self.modify(indexs[0], $this);
                         },*/
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
                        'createFTLot': function (event, data) {


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
                            self.createFTLot(indexs[0], $this);
                        },
                        'separationRestore': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择要还原的批次'
                                });
                                return;
                            }
                            $.each(data.item, function (n, value) {

                            })
                            for (var x in data.item) {
                                if (data.item[x].state == 1) {
                                    $this.message({
                                        type: 'warning',
                                        content: '该批次已下单，请重新选择'
                                    })
                                    return;
                                }
                            }
                            var restore = function () {
                                self.separationRestore(indexs, $this);
                            };
                            $this.confirm({
                                content: '确定要还原所选批次吗?',
                                callBack: restore
                            });
                        },
                        'combinationRestore': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择需要合批还原的批次'
                                })
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一个批次进行合批还原'
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
                            self.combinationRestore(indexs[0], $this);
                        },
                        'WMSTest': function () {
                            /*var data = [{
                                name: "json",
                                value: '[{"ID": "59030e02-7ea7-4613-987b-b35c8661aaef","TASK_NUM": "WIN20160411002","CUS_PPO": "PPO002418","CUS_LOT": "AH789P20101","CUS_CODE": "1201-VCP-BJ","IN_PARTNUM": "VC5341-21","QUANTITY": 34092,"BONDED_TYPE": "01","BONDED_NUM": "ACE00009","LINE_TYPE": "02","VERSION": "VC5341-21_A3_03","DATECODE": "1613","INCOMING_STYLE": "01","PACKING_LOT": "AH789P20101-D04","GROSS_WEIGHT": "80","MFG_PN": "AH789P20101-D04","WAFER_CLIENT": "TSG","WAFER_VERSION": "BV","WIREBOND_TYPE": "01","RUNCARD_QUANTITY": 0,"STORAGE_TYPE": "01","MATERIAL_TYPE": "01","SHIPPING_STYLE": "01","FLAG": 1,"TASK_STATUS": "01","STORAGE_ID": "59030e02-7ea7-4613-987b-b35c8661aaef","IN_TYPE": "来料","TASK_IS_END": "01","REWORK_TYPE": "01","OUT_PARTNUM": "VC5341-21","STATUS": "01","SHIPPING_QUANTITY": 0,"shipping_QUANTITY": 0,"flag": 1,"task_STATUS": "01","id": "59030e02-7ea7-4613-987b-b35c8661aaef","bonded_TYPE": "01","material_TYPE": "01","storage_TYPE": "01","packing_LOT": "AH789P20101-D04","rework_TYPE": "01","out_PARTNUM": "VC5341-21","task_IS_END": "01","incoming_STYLE": "01","gross_WEIGHT": "80","wafer_CLIENT": "TSG","wafer_VERSION": "BV","wirebond_TYPE": "01","runcard_QUANTITY": 0,"shipping_STYLE": "01","quantity": 34092,"bonded_NUM": "ACE00009","cus_CODE": "1201-VCP-BJ","cus_LOT": "AH789P20101","cus_PPO": "PPO002418","in_PARTNUM": "VC5345","task_NUM": "WIN20160411002","storage_ID": "59030e02-7ea7-4613-987b-b35c8661aaef","in_TYPE": "来料","datecode": "1613","version": "VC5341-21_A3_03","line_TYPE": "02","mfg_PN": "AH789P20101-D04"}]'
                            }];
                        	*/
                            var data = [{
                                name: "json",
                                value: '[{"ID": "59030e02-7ea7-4613-987b-b35c8661aaef","TASK_NUM": "WIN20160411002","CUS_PPO": "PPO002473","CUS_LOT": "AH789P21415","CUS_CODE": "1201-VCP-BJ","IN_PARTNUM": "VC5345","QUANTITY": 34092,"BONDED_TYPE": "01","BONDED_NUM": "ACE00009","LINE_TYPE": "02","VERSION": "VC5345_A3_03","DATECODE": "1613","INCOMING_STYLE": "01","PACKING_LOT": "VCR616M045-D03","GROSS_WEIGHT": "80","MFG_PN": "AH789P20101-D04","WAFER_CLIENT": "TSG","WAFER_VERSION": "BV","WIREBOND_TYPE": "01","RUNCARD_QUANTITY": 0,"STORAGE_TYPE": "01","MATERIAL_TYPE": "01","SHIPPING_STYLE": "01","FLAG": 1,"TASK_STATUS": "01","STORAGE_ID": "59030e02-7ea7-4613-987b-b35c8661aaef","IN_TYPE": "来料","TASK_IS_END": "01","REWORK_TYPE": "01","OUT_PARTNUM": "VC5345","STATUS": "01","SHIPPING_QUANTITY": 0,"shipping_QUANTITY": 0,"flag": 1,"task_STATUS": "01","id": "59030e02-7ea7-4613-987b-b35c8661aaef","bonded_TYPE": "01","material_TYPE": "01","storage_TYPE": "01","packing_LOT": "AH789P20101-D04","rework_TYPE": "01","out_PARTNUM": "VC5341-21","task_IS_END": "01","incoming_STYLE": "01","gross_WEIGHT": "80","wafer_CLIENT": "TSG","wafer_VERSION": "BV","wirebond_TYPE": "01","runcard_QUANTITY": 0,"shipping_STYLE": "01","quantity": 34092,"bonded_NUM": "ACE00009","cus_CODE": "1201-VCP-BJ","cus_LOT": "AH789P20101","cus_PPO": "PPO002418","in_PARTNUM": "VC5345","task_NUM": "WIN20160411002","storage_ID": "59030e02-7ea7-4613-987b-b35c8661aaef","in_TYPE": "来料","datecode": "1613","version": "VC5341-21_A3_03","line_TYPE": "02","mfg_PN": "AH789P20101-D04"}]'
                        }];

                            //var data = [{name: "json", value: '[    {"ID": "6d1bf779-74c2-4f2d-bea5-94b28896abe9","TASK_NUM": "WIN20160118005","CUS_PPO": "RA","CUS_LOT": "UHST96","CUS_CODE": "1201-VCP-BJ","IN_PARTNUM": "VC7903","QUANTITY": 76,"BONDED_TYPE": "03","LINE_TYPE": "02","VERSION": "VC7903A3E1.20.1","DATECODE": "1546","INCOMING_STYLE": "01","PACKING_LOT": "VCR546M0D4-D01","RUNCARD_QUANTITY": 0,"NOTE": "NA","STORAGE_TYPE": "01","MATERIAL_TYPE": "03","SHIPPING_STYLE": "01","FLAG": 1,"STORAGE_ID": "e42d1ece-f5e1-4a2a-88ee-a815eadd4f1e","IN_TYPE": "来料","TASK_IS_END": "01","REWORK_TYPE": "01","OUT_PARTNUM": "VC7903","STATUS": "01","SHIPPING_QUANTITY": 0,"shipping_QUANTITY": 0,"flag": 1,"id": "6d1bf779-74c2-4f2d-bea5-94b28896abe9","quantity": 76,"note": "NA","cus_CODE": "1201-VCP-BJ","cus_LOT": "UHST96","cus_PPO": "RA","in_PARTNUM": "VC7903","task_NUM": "WIN20160118005","storage_ID": "e42d1ece-f5e1-4a2a-88ee-a815eadd4f1e","in_TYPE": "来料","datecode": "1546","version": "VC7903A3E1.20.1","line_TYPE": "02","bonded_TYPE": "03","material_TYPE": "03","storage_TYPE": "01","packing_LOT": "VCR546M0D4-D01","rework_TYPE": "01","out_PARTNUM": "VC7903","task_IS_END": "01","incoming_STYLE": "01","runcard_QUANTITY": 0,"shipping_STYLE": "01"    },    {"ID": "1b90bf57-ef28-47cd-9459-6b0804bd7769","TASK_NUM": "WIN20160118005","CUS_PPO": "RA","CUS_LOT": "TCT500","CUS_CODE": "1201-VCP-BJ","IN_PARTNUM": "VC7903","QUANTITY": 76,"BONDED_TYPE": "03","LINE_TYPE": "02","VERSION": "VC7903A3E1.20.1","DATECODE": "1546","INCOMING_STYLE": "01","PACKING_LOT": "VCR546M0E0-D01","RUNCARD_QUANTITY": 0,"NOTE": "NA","STORAGE_TYPE": "01","MATERIAL_TYPE": "03","SHIPPING_STYLE": "01","FLAG": 1,"STORAGE_ID": "64b91bce-34d6-41e2-ab43-9eda0a7d34ea","IN_TYPE": "来料","TASK_IS_END": "01","REWORK_TYPE": "01","OUT_PARTNUM": "VC7903","STATUS": "01","SHIPPING_QUANTITY": 0,"shipping_QUANTITY": 0,"flag": 1,"id": "1b90bf57-ef28-47cd-9459-6b0804bd7769","quantity": 76,"note": "NA","cus_CODE": "1201-VCP-BJ","cus_LOT": "TCT500","cus_PPO": "RA","in_PARTNUM": "VC7903","task_NUM": "WIN20160118005","storage_ID": "64b91bce-34d6-41e2-ab43-9eda0a7d34ea","in_TYPE": "来料","datecode": "1546","version": "VC7903A3E1.20.1","line_TYPE": "02","bonded_TYPE": "03","material_TYPE": "03","storage_TYPE": "01","packing_LOT": "VCR546M0E0-D01","rework_TYPE": "01","out_PARTNUM": "VC7903","task_IS_END": "01","incoming_STYLE": "01","runcard_QUANTITY": 0,"shipping_STYLE": "01"    },    {"ID": "da79663f-d672-4a75-b8d7-cd20bbbc8f2f","TASK_NUM": "WIN20160118005","CUS_PPO": "RA","CUS_LOT": "UHST96","CUS_CODE": "1201-VCP-BJ","IN_PARTNUM": "VC1616","QUANTITY": 75,"BONDED_TYPE": "03","LINE_TYPE": "02","VERSION": "VC1616A3E1.1","DATECODE": "1545","INCOMING_STYLE": "01","PACKING_LOT": "VCR545N043-D01","RUNCARD_QUANTITY": 0,"NOTE": "NA","STORAGE_TYPE": "01","MATERIAL_TYPE": "03","SHIPPING_STYLE": "01","FLAG": 1,"STORAGE_ID": "23831093-f695-45e7-baaa-a5d6a3e9fd37","IN_TYPE": "来料","TASK_IS_END": "01","REWORK_TYPE": "01","OUT_PARTNUM": "VC1616","STATUS": "01","SHIPPING_QUANTITY": 0,"shipping_QUANTITY": 0,"flag": 1,"id": "da79663f-d672-4a75-b8d7-cd20bbbc8f2f","quantity": 75,"note": "NA","cus_CODE": "1201-VCP-BJ","cus_LOT": "UHST96","cus_PPO": "RA","in_PARTNUM": "VC1616","task_NUM": "WIN20160118005","storage_ID": "23831093-f695-45e7-baaa-a5d6a3e9fd37","in_TYPE": "来料","datecode": "1545","version": "VC1616A3E1.1","line_TYPE": "02","bonded_TYPE": "03","material_TYPE": "03","storage_TYPE": "01","packing_LOT": "VCR545N043-D01","rework_TYPE": "01","out_PARTNUM": "VC1616","task_IS_END": "01","incoming_STYLE": "01","runcard_QUANTITY": 0,"shipping_STYLE": "01"    },    {"ID": "20e95306-7d01-47eb-a95b-1b0063ca7917","TASK_NUM": "WIN20160118005","CUS_PPO": "RA","CUS_LOT": "TCT500","CUS_CODE": "1201-VCP-BJ","IN_PARTNUM": "VC1616","QUANTITY": 75,"BONDED_TYPE": "03","LINE_TYPE": "02","VERSION": "VC1616A3E1.1","DATECODE": "1545","INCOMING_STYLE": "01","PACKING_LOT": "VCR545N043-D01","RUNCARD_QUANTITY": 0,"NOTE": "NA","STORAGE_TYPE": "01","MATERIAL_TYPE": "03","SHIPPING_STYLE": "01","FLAG": 1,"STORAGE_ID": "7e24f5eb-52db-4bb6-ab03-9f96d3f2f1fa","IN_TYPE": "来料","TASK_IS_END": "01","REWORK_TYPE": "01","OUT_PARTNUM": "VC1616","STATUS": "01","SHIPPING_QUANTITY": 0,"shipping_QUANTITY": 0,"flag": 1,"id": "20e95306-7d01-47eb-a95b-1b0063ca7917","quantity": 75,"note": "NA","cus_CODE": "1201-VCP-BJ","cus_LOT": "TCT500","cus_PPO": "RA","in_PARTNUM": "VC1616","task_NUM": "WIN20160118005","storage_ID": "7e24f5eb-52db-4bb6-ab03-9f96d3f2f1fa","in_TYPE": "来料","datecode": "1545","version": "VC1616A3E1.1","line_TYPE": "02","bonded_TYPE": "03","material_TYPE": "03","storage_TYPE": "01","packing_LOT": "VCR545N043-D01","rework_TYPE": "01","out_PARTNUM": "VC1616","task_IS_END": "01","incoming_STYLE": "01","runcard_QUANTITY": 0,"shipping_STYLE": "01"    },    {"ID": "e5ab388a-3244-490f-ad47-32c15a8fe323","TASK_NUM": "WIN20160118005","CUS_PPO": "RA","CUS_LOT": "UHST96","CUS_CODE": "1201-VCP-BJ","IN_PARTNUM": "VC1618","QUANTITY": 75,"BONDED_TYPE": "03","LINE_TYPE": "02","VERSION": "VC1618A3E7.1","DATECODE": "1544","INCOMING_STYLE": "01","PACKING_LOT": "VCR544N083-D01","RUNCARD_QUANTITY": 0,"NOTE": "NA","STORAGE_TYPE": "01","MATERIAL_TYPE": "03","SHIPPING_STYLE": "01","FLAG": 1,"STORAGE_ID": "27832bc3-0f5e-446d-9806-6ac1f5ab17e0","IN_TYPE": "来料","TASK_IS_END": "01","REWORK_TYPE": "01","OUT_PARTNUM": "VC1618","STATUS": "01","SHIPPING_QUANTITY": 0,"shipping_QUANTITY": 0,"flag": 1,"id": "e5ab388a-3244-490f-ad47-32c15a8fe323","quantity": 75,"note": "NA","cus_CODE": "1201-VCP-BJ","cus_LOT": "UHST96","cus_PPO": "RA","in_PARTNUM": "VC1618","task_NUM": "WIN20160118005","storage_ID": "27832bc3-0f5e-446d-9806-6ac1f5ab17e0","in_TYPE": "来料","datecode": "1544","version": "VC1618A3E7.1","line_TYPE": "02","bonded_TYPE": "03","material_TYPE": "03","storage_TYPE": "01","packing_LOT": "VCR544N083-D01","rework_TYPE": "01","out_PARTNUM": "VC1618","task_IS_END": "01","incoming_STYLE": "01","runcard_QUANTITY": 0,"shipping_STYLE": "01"    },    {"ID": "fa4e24e6-a2ba-4f86-8176-59125aadb33a","TASK_NUM": "WIN20160118005","CUS_PPO": "RA","CUS_LOT": "TCT500","CUS_CODE": "1201-VCP-BJ","IN_PARTNUM": "VC1618","QUANTITY": 75,"BONDED_TYPE": "03","LINE_TYPE": "02","VERSION": "VC1618A3E7.1","DATECODE": "1544","INCOMING_STYLE": "01","PACKING_LOT": "VCR544N083-D01","RUNCARD_QUANTITY": 0,"NOTE": "NA","STORAGE_TYPE": "01","MATERIAL_TYPE": "03","SHIPPING_STYLE": "01","FLAG": 1,"STORAGE_ID": "ebcdb009-8c66-434b-9901-63bacc3e7b9e","IN_TYPE": "来料","TASK_IS_END": "01","REWORK_TYPE": "01","OUT_PARTNUM": "VC1618","STATUS": "01","SHIPPING_QUANTITY": 0,"shipping_QUANTITY": 0,"flag": 1,"id": "fa4e24e6-a2ba-4f86-8176-59125aadb33a","quantity": 75,"note": "NA","cus_CODE": "1201-VCP-BJ","cus_LOT": "TCT500","cus_PPO": "RA","in_PARTNUM": "VC1618","task_NUM": "WIN20160118005","storage_ID": "ebcdb009-8c66-434b-9901-63bacc3e7b9e","in_TYPE": "来料","datecode": "1544","version": "VC1618A3E7.1","line_TYPE": "02","bonded_TYPE": "03","material_TYPE": "03","storage_TYPE": "01","packing_LOT": "VCR544N083-D01","rework_TYPE": "01","out_PARTNUM": "VC1618","task_IS_END": "01","incoming_STYLE": "01","runcard_QUANTITY": 0,"shipping_STYLE": "01"    }]'}];
                            $.post('${pageContext.request.contextPath}/WMSService/addCustomerFTLots.koala', data).done(function (result) {
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
                            //window.open('${pageContext.request.contextPath}/pages/gantt/index.html');
                            openTab('/pages/domain/ProductionSchedule-list.jsp','测试任务','munuMark234')
                            //window.history.back(-1);
                        }
                    });
                },
                separateFTLot: function (id, grid) {
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
                    $.get('<%=path%>/CustomerFTLot-divide.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        $.get('${pageContext.request.contextPath}/CustomerFTLot/get/' + id + '.koala').done(function (json) {
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
                            $.post('${pageContext.request.contextPath}/CustomerFTLot/lotSeparate.koala', data).done(function (result) {
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
                combineFTLot: function (grid) {
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
                    $.get('<%=path%>/CustomerFTLot-combine.jsp').done(function (html) {
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
                           /*     if (this.incomingQuantity < 10000) {
                                    dialog.find('#notGrantAuthoritiesToUserGrid').message({
                                        type: 'warning',
                                        content: '来料数量不能小于10K'
                                    });
                                    flag = 1;
                                    return true;
                                } else {*/
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
                                //}
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
                                    url: "${pageContext.request.contextPath}/CustomerFTLot/pageJson.koala"
                                });
                            }
                        });
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        var data = {ids: ids.join(',')};
                        $.post('${pageContext.request.contextPath}/CustomerFTLot/lotCombineManually.koala', data).done(function (result) {
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
                 $.get('<%=path%>/CustomerFTLot-add.jsp').done(function(html){
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
                 $.post('${pageContext.request.contextPath}/CustomerFTLot/add.koala', dialog.find('form').serialize()).done(function(result){
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
                 $.get('<%=path%>/CustomerFTLot-update.jsp').done(function(html){
                 dialog.find('.modal-body').html(html);
                 self.initPage(dialog.find('form'));
                 $.get( '${pageContext.request.contextPath}/CustomerFTLot/get/' + id + '.koala').done(function(json){
                 json = json.data;
                 var elm;
                 for(var index in json){
                 //此处寻找json包内的customerFTLotDTO包，并按照命名规则解析填入对应的控件中
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
                 $.post('${pageContext.request.contextPath}/CustomerFTLot/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
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
                    $.post('${pageContext.request.contextPath}/CustomerFTLot/delete.koala', data).done(function (result) {
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
                createFTLot: function (id, grid) {

                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" style="width:900px;"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">FT下单</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">下单</button></div></div></div></div>');
                    $.get('<%=path%>/FTLot-create.jsp').done(function (html) {
//                        debugger;
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        $.get('${pageContext.request.contextPath}/CustomerFTLot/get/' + id + '.koala').done(function (json) {
//                            debugger;
                            json = json.data;
                            var elm;
                            for (var property in json) {
//                                debugger
                                //此处寻找json包内的customerFTLotDTO包，并按照命名规则解析填入对应的控件中
                                if (typeof(json[property]) == "object") {
                                    for (var DTOindex in json[property]) {
                                        elm = dialog.find('#' + property + DTOindex + 'ID');
                                        elm.val(json[property][DTOindex]);
                                    }
                                } else {
                                    elm = dialog.find('#' + property + 'ID');
                                    if (elm.hasClass('select')) {
                                        elm.setValue(json[property]);
                                    } else {
                                        elm.val(json[property]);
                                    }
                                }
                            }
//                            debugger;
                            var customerFTLot = {"customerLotDTO": json};
                            for (var property in customerFTLot) {
//                                debugger
                                //此处寻找json包内的customerFTLotDTO包，并按照命名规则解析填入对应的控件中
                                if (typeof(customerFTLot[property]) == "object") {
                                    for (var DTOindex in customerFTLot[property]) {
                                        elm = dialog.find('#' + property + DTOindex + 'ID');
                                        elm.val(customerFTLot[property][DTOindex]);
                                    }
                                } else {
                                    elm = dialog.find('#' + property + 'ID');
                                    if (elm.hasClass('select')) {
                                        elm.setValue(customerFTLot[property]);
                                    } else {
                                        elm.val(customerFTLot[property]);
                                    }
                                }
                            }
//                            debugger;
                            if (json.incomingStyle == '01') {
                                dialog.find('#incomingStyleID').attr('checked', true);
                            } else {
                                dialog.find('#incomingStyleID_').attr('checked', true);
                            }
//                            debugger
                            dialog.find('#shipmentProductNumberID').val(json['customerProductNumber']);
                        });
                        $.get('${pageContext.request.contextPath}/CustomerFTLot/getExpectedLotNumber/' + id + '.koala').done(function (json) {
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
                        $.get('${pageContext.request.contextPath}/CustomerFTLot/getExpectedRCNumber/' + id + '.koala').done(function (json) {
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
                        
                        $.get('${pageContext.request.contextPath}/CustomerFTLot/findPIDByCustomerFTLotId/'+id+'.koala').done(function (json) {
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
                        //查看Runcard
                        dialog.find("#showRuncard").on("click", function () {
                            showRuncardInfo(id);
                        })

                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        });
                        dialog.find('#save').on('click', {grid: grid}, function (e) {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            // 下单

                            $.post('${pageContext.request.contextPath}/CustomerFTLot/order.koala', dialog.find('form').serialize()).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '下单成功'
                                    });
                                    showRuncardInfoAfterReplaced(result.data );
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
                    $.post('${pageContext.request.contextPath}/CustomerFTLot/separationRestore.koala', data).done(function (result) {
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
                    $.post('${pageContext.request.contextPath}/CustomerFTLot/combinationRestore.koala', data).done(function (result) {
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
            	            +'<input type="hidden" id="internalProductNumberID_" name="ftInfoId" dataType="Require"/><span class="required">*</span>'
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
            	            $.get('${pageContext.request.contextPath}/CustomerFTLot/findPIDByCustomerFTLotId/'+ids[0]+'.koala').done(function (json) {
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
            	              data.push({name:'ftInfoId',value:$("#internalProductNumberID_").val()});
            	              $.post('${pageContext.request.contextPath}/CustomerFTLot/orderInBatches.koala', data).done(function (result) {

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
            $.get('<%=path%>/CustomerFTLot-view.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);
                $.get('${pageContext.request.contextPath}/CustomerFTLot/get/' + id + '.koala').done(function (json) {
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
        <input type="hidden" name="page" value="0">
        <input type="hidden" name="pagesize" value="10">
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
        				<label class="control-label" style="width:100px;float:left;">数量:&nbsp;</label>
        				<div style="margin-left:15px;float:left;">
        					<input name="incomingQuantity" class="form-control" type="text" style="width:180px;" id="incomingQuantityID"  />
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
