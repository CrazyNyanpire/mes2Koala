<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <%@ page import="java.util.Date"%>
    <% String leftGridId = "leftGrid_" + new Date().getTime();
        String rightGridId = "rightGrid_" + new Date().getTime();
        String dialogId = "dialog_" + new Date().getTime();
        String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
    %>
    <style>
        .userGrantAuthority {
            display: block;
            text-align: center;
        }

        .left, .middle, .right{
            display:inline-block;
            vertical-align:middle;
        }

        .middle{
            text-align:center;
            position:relative;
            top:250px;
        }

    </style>
    <script type="text/javascript">
        var leftGrid;
        var rightGrid;
        var dialog;
        $(function () {
            var testTypeValue;
            leftGrid = $("#<%=leftGridId%>");
            rightGrid = $("#<%=rightGridId%>");
            dialog = $("#<%=dialogId%>");

            var productionScheduleStateContents = {};//添加状态字典表维护
            $.ajax({
                url: '${pageContext.request.contextPath}/SystemDictionary/getByType/productionScheduleState.koala',
                type: 'GET',
                dataType: 'json'
            }).done(function (msg) {
                for (var i = 0; i < msg.length; i++) {
                    productionScheduleStateContents[msg[i]['value']] = msg[i]['label'];
                }
            });

            var contentsTestType = [];//测试类型
            var flag = 1;
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

                dialog.find('#testTypeID').select({
                    title: '请选择',
                    contents: contentsTestType
                }).on('change', function () {
                    testTypeValue = $(this).getValue();
                    dialog.find('#testTypeID_').val($(this).getValue());
                    if(rightGrid.getGrid()){//如果右侧有表格则删除，便于加载新的grid项
                        rightGrid.getGrid().destory();
                    }
                    rightGrid.grid({//右侧表格，FT。koala 或CP.koala
                        identity: "id",
                        pageSize:1000,
                        url: "${pageContext.request.contextPath}/ProductionSchedule/pageJson/"+ testTypeValue +".koala",
                        columns: [
                            {title: '批次', name: 'lotNumber', width: 130},
                            {title: '数量', name: 'amount', width: 50},
                            {title: '预计需时(H)', name: 'plannedTimeTakes', width: 90},
                            {title: '测试状态', name: 'state', width: 70,
                                render: function (rowdata, name, index) {
                                    var Lab = rowdata['state'];
                                    return productionScheduleStateContents[Lab];
                                }},
/*                             {title: '批次当前状态', name: 'lotCurrentState', width: 100},
                            {title: '批次Hold状态', name: 'lotHoldState', width: 130} */
                        ],
                        isShowPages:false
                    })
                    <%--var contents = [{title: '请选择', value: ''}];//加载目标机台--%>
                    <%--$.ajax({--%>
                    <%--url: '${pageContext.request.contextPath}/TestSys/pageJson/'+ testTypeValue +'.koala',--%>
                    <%--type: 'GET',--%>
                    <%--dataType: 'json'--%>
                    <%--}).done(function (msg) {--%>
                    <%--for (var i = 0; i < msg.length; i++) {--%>
                    <%--var data={--%>
                    <%--'title': msg[i]['platformNumber'],--%>
                    <%--'value': msg[i]['id']--%>
                    <%--}--%>
                    <%--contents.push({--%>
                    <%--data: data--%>
                    <%--});--%>
                    <%--}--%>
//                        dialog.find('#targetTestSysNameID').select({
//                            title: '请选择',
//                            contents: contents
//                        }).on('change', function () {
//                            dialog.find('#targetTestSysNameID_').val($(this).getValue());
//                            var params ={};//搜索可用平台
//                            params['testSysId'] = dialog.find('#targetTestSysNameID_').val();
//                            rightGrid.getGrid().search(params);
//                        });
                    var params ={};//搜索类别
                    params['customerProductNumber'] = dialog.find('#customerProductNumberID').val();
                    params['testType'] = testTypeValue;
                    debugger;
                    leftGrid.getGrid().search(params);
                });
                if(flag){
                    dialog.find('#testTypeID').setValue('FT');
                }
            });
            //选择可用平台
            dialog.find('#targetTestSysNameID').on('click', function () {
//                debugger
                $.get('<%=path%>/TestSys-opt.jsp').done(function (html) {
                    var testSysdialog = $(html);
                    debugger

                    testSysdialog.modal({
                        keyboard: false,
                        backdrop: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        },
                        'shown.bs.modal': function () {
                            var testSysGrid = testSysdialog.find("#testSysGridId");
                            testSysGrid.grid({
                                identity: "id",
                                url: '${pageContext.request.contextPath}/TestSys/pageJson.koala',
                                columns: [
                                    {title: 'platformNumber', name: 'platformNumber', width: 180},
//                                            {title: 'testerNumber', name: 'testerNumber', width: 180},
//                                            {title: 'proberOrHandlerNumber', name: 'proberOrHandlerNumber', width: 180},
//                                            {title: 'state', name: 'state', width: 180},
                                ]
                            })
                            var params = {};
                            params['testType'] = testTypeValue;
                            testSysGrid.getGrid().search(params);
                        },
                    });

                    testSysdialog.find("#confirm").on('click', function () {
                        var testSysOptRows = testSysdialog.find('#testSysGridId').getGrid().selectedRows();
                        if (testSysOptRows.length > 1) {
                            testSysdialog.find('#testSysGridId').message({
                                type: 'warning',
                                content: '只能选择一个平台'
                            });
                            return;
                        }
                        var testSysOpt = testSysOptRows[0];
                        testSysdialog.modal('hide');
                        dialog.find('#targetTestSysNameID_')[0].value = testSysOpt.id;
                        dialog.find('#targetTestSysNameID').val(testSysOpt.platformNumber).trigger("change");
                        debugger

                    })
                    testSysdialog.find('#search').on('click', function () {
                        var params = {};
                        testSysdialog.find('input').each(function () {
                            var $this = $(this);
                            var name = $this.attr('name');
                            if (name) {
                                params[name] = $this.val();
                            }
                        });
                        testSysdialog.find("#testSysGridId").getGrid().search(params);
                    });
                })
//                        })
            });
            dialog.find('#targetTestSysNameID').on('change', function () {
                // dialog.find('#targetTestSysNameID_').val($(this).getValue());
                debugger;
                var params = {};//搜索可用平台
                params['testSysId'] = dialog.find('#targetTestSysNameID_').val();
                rightGrid.getGrid().search(params);
            });
            dialog.find('#toRight').on('click',function(){//右移
                if(dialog.find('#targetTestSysNameID_').val() == ""){//未选择可用平台则报错并返回
                    leftGrid.message({
                        type: 'warning',
                        content: '请选择可用平台'
                    });
                    return;
                }
                var leftIds = leftGrid.getGrid().selectedRowsIndex();//选中的id
                var data ='';
                if(leftIds.length == 0){
                    leftGrid.message({
                        type: 'warning',
                        content: '请选择要需要左移的数据项'
                    });
                    return;
                }
                for(var i=0;i<leftIds.length;i++){
                    data += "productionIds="+leftIds[i]+'&';
                }
                data += 'testSysId='+dialog.find('#targetTestSysNameID_').val();
                var url = contextPath+'/ProductionSchedule/arrangeProductionsInATestSys.koala';
                $.post(url, data).done(function(data) {
                    if(data.success){
                        leftGrid.message({
                            type: 'success',
                            content: '右移成功'
                        });
                        leftGrid.grid('refresh');
                        rightGrid.grid('refresh');
                    }
                });
            });

            dialog.find('#toLeft').on('click',function(){//左移
                var rightIds = rightGrid.getGrid().selectedRowsIndex();
                var data = '';
                if(rightIds.length == 0){
                    rightGrid.message({
                        type: 'warning',
                        content: '请选择要需要左移的数据项'
                    });
                    return;
                }
                for(var i=0;i<rightIds.length;i++){
                    data += "ids="+rightIds[i]+'&';
                }
                data = data.substring(0,data.length-1);
                $.post(contextPath + '/ProductionSchedule/revokeProductionSchedules.koala', data).done(function(data) {
                    if(data.success){
                        rightGrid.message({
                            type: 'success',
                            content: '左移成功！'
                        });
                        leftGrid.grid('refresh');
                        rightGrid.grid('refresh');
                    }
                });

            });
            dialog.find('#customerProductNumberID').on('keyup', function () {//搜索产品型号
                var params ={};
                params['customerProductNumber'] = dialog.find('#customerProductNumberID').val();
                params['testType'] = dialog.find('#testTypeID_').val();
                leftGrid.getGrid().search(params);
            });


            dialog.find('#toUp').on('click',function(){//上移

                var rightIds = rightGrid.getGrid().selectedRowsIndex();
                if(rightIds.length == 0){
                    rightGrid.message({
                        type: 'warning',
                        content: '请选择要需要上移的数据项'
                    });
                    return;
                }
                if(rightIds.length > 1 ){
                	rightGrid.message({
                		type: 'warning',
                		content: '只能选择一条记录'
                	});
                	return;
                }
                
                $.post(contextPath + '/ProductionSchedule/reorderUp.koala', {id:rightIds[0]}).done(function(data) {
                    if(data.success){
                        rightGrid.message({
                            type: 'success',
                            content: '上移成功！'
                        });
                        rightGrid.grid('refresh');
                    } else {
                    	rightGrid.message({
                    		type: 'warning',
                    		content: data.actionError
                    	})
                    }
                });

/*                 var upId = rightGrid.getGrid().selectedRowsIndex();
                var indexToUp = rightGrid.getGrid().getIndexByIdentityValue(upId);
                if(rightGrid.getGrid().up(indexToUp)){
                    rightGrid.message({
                        type: 'success',
                        content: '上移成功！'
                    });
                }
 */
 			});

            dialog.find('#toDown').on('click',function(){//下移
                var rightIds = rightGrid.getGrid().selectedRowsIndex();
                if(rightIds.length == 0){
                    rightGrid.message({
                        type: 'warning',
                        content: '请选择要需要下移的数据项'
                    });
                    return;
                }
                if(rightIds.length > 1 ){
                	rightGrid.message({
                		type: 'warning',
                		content: '只能选择一条记录'
                	});
                	return;
                }
                
                $.post(contextPath + '/ProductionSchedule/reorderDown.koala', {id:rightIds[0]}).done(function(data) {
                    if(data.success){
                        rightGrid.message({
                            type: 'success',
                            content: '下移成功！'
                        });
                        rightGrid.grid('refresh');
                    } else {
                    	rightGrid.message({
                    		type: 'warning',
                    		content: data.actionError
                    	})
                    }
                });

                /*var downId = rightGrid.getGrid().selectedRowsIndex();
                var indexToDown = rightGrid.getGrid().getIndexByIdentityValue(downId);
                if(rightGrid.getGrid().down(indexToDown)){
                    rightGrid.message({
                        type: 'success',
                        content: '下移成功！'
                    });
                }*/
            });

           leftGrid.grid({//左侧表格
                identity: "id",
                pageSize:1000,
                url: "${pageContext.request.contextPath}/ProductionSchedule/pageJsonProductionsToBeScheduled.koala",
                columns: [
                    {title: '批次', name: 'lotNumber', width:140},
                    {title: '所属站点', name: 'nodeName', width: 80},
                    {title: '数量', name: 'amount', width: 70}
                  ],
               isShowPages:false
            });

            //兼容IE8 IE9
            if(window.ActiveXObject){
                if(parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10){
                    dialog.trigger('shown.bs.modal');
                }
            }
        });

        //查看排产界面
        var gantt = function () {
            openTab('/pages/domain/ProductionSchedule-list.jsp', '测试任务', 'munuMark234');
        };
    </script>
</head>
<body>
<div class="userGrantAuthority container-fluid" id=<%=dialogId%>>
    <div class="left col-lg-4">
        <div class="left-modal-body" style="margin-left:-300px;">已下单批次信息：</div>
        <hr />
        <div class="form-group"  style="margin-left:15px;">
            <label class="col-lg-3 control-label">类别:</label>
            <div class="col-lg-6">
                <div class="btn-group select" id="testTypeID"></div>
                <input name="testType" type="hidden" id="testTypeID_"/>
            </div>
        </div>
        <br />
        <div style="margin:5px;" class="col-lg-10" >
            <label class="col-lg-4">产品型号:</label>
            <div class="col-lg-8">
                <input  class="form-control"  type= "text" name="customerProductNumber" id="customerProductNumberID" />
            </div>
        </div>
        <div style="height: 60px"></div>
        <div id=<%=leftGridId%>></div>
    </div>
    <div class="middle col-lg-1">
        <button class="btn btn-success glyphicon glyphicon-chevron-right" id="toRight">&nbsp;</button>
        <br/><br/><br/><br/>
        <button class="btn btn-danger glyphicon glyphicon-chevron-left" id="toLeft">&nbsp;</button>
        <br/><br/><br/><br/>
    </div>
    <div class="right col-lg-6">
        <div class="right-modal-body" style="margin-left:-500px;">机台信息：</div>
        <hr />
        <div style="margin: 5px;" class="col-lg-8">
            <label class="col-lg-3" style="margin-top:3px;">可用平台:</label>
            <div class="col-lg-9">
                <input type="button" class="btn btn-default" id="targetTestSysNameID" value="请选择"/>
                <input name="targetSysId" type="hidden" id="targetTestSysNameID_" />
            </div>
        </div>
        <br />
        <%--<div style="margin:5px;" class="col-lg-8">--%>
        <%--<label class="col-lg-3">配套设备:</label>--%>
        <%--<div class="col-lg-8">--%>
        <%--<input  class="form-control"  type= "text"  />--%>
        <%--</div>--%>
        <%--</div>--%>
        <div style="height: 60px"></div>
        <div id=<%=rightGridId%>></div>
    </div>
    <div class="middle col-lg-1">
        <button class="btn btn-success glyphicon glyphicon-chevron-up" id="toUp">&nbsp;</button>
        <br/><br/><br/><br/>
        <button class="btn btn-danger glyphicon glyphicon-chevron-down" id="toDown">&nbsp;</button>
        <br/><br/><br/><br/>
    </div>


    <div>
        <label class="col-lg-8" style="margin-top:3px;"></label>
        <div class="col-lg-3">
            <button class="btn btn-danger" onclick="gantt()"><span class="glyphicon glyphicon-plus"></span>排产界面</button>
        </div>
    </div>
</div>
</body>
</html>
