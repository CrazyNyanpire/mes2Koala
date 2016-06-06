<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <%@ page import="java.util.Date"%>
    <% String formId = "form_" + new Date().getTime();
        String previewId = "preview_" + new Date().getTime();
        String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
    %>
    <script type="text/javascript">
        var form;
        var preview;

        $(function(){
            form = $("#<%=formId%>");
            preview = $("#<%=previewId%>");
            var contents = [{title:'请选择', value: ''}];
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
            form.find('#testTypeID').select({
                title: '请选择',
                contents: contents
            }).on('change',function(){
                form.find('#testTypeID_').val($(this).getValue());
            });
            form.find('#productOption').on('click',function(){//生产批号选择
                var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                        +'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                        +'data-dismiss="modal" aria-hidden="true">&times;</button>'
                        +'<h4 class="modal-title">选择批</h4></div><div class="modal-body">'
                        +'<div style="margin-bottom: 10px;"><label>生产批号:&nbsp;</label><input name="internalLotNumber" type="text" id="internalLotOption"/></div><div id="lotNumber"></div></div><div class="modal-footer">'
                        +'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                        +'<button type="button" class="btn btn-success" id="lotNumberSave">提交</button></div></div>'
                        +'</div></div>');
                    dialog.modal({
                        keyboard: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        },
                        'shown.bs.modal' :function(){
                            var lotNumbercolumns = [{
                                title : "生产批号",
                                name : "internalLotNumber",
                                width : 100
                            }];
                            dialog.find('#lotNumber').grid({
                                identity: 'id',
                                columns: lotNumbercolumns,
                                isShowPages:false,
                                url: "${pageContext.request.contextPath}/FTLot/pageJson.koala"
                            });
                        }
                    });
                dialog.find('#internalLotOption').on('keyup',function(){
                    var params={}
                    params.internalLotNumber = dialog.find('#internalLotOption').val();
                    dialog.find('#lotNumber').getGrid().search(params);
                })
                dialog.find('#lotNumberSave').on('click',function(){
                    var dataRows = dialog.find('#lotNumber').getGrid().selectedRows();
                    if(dataRows.length == 0){
                        dialog.find('#lotNumber').message({
                            type:'warning',
                            content:'请选择一个批次'
                        });
                        return;
                    }
                    debugger
                    var internalLotNumbers = dataRows.map(function (dataRows) {
                        return dataRows.internalLotNumber;
                    }).join(",");
                    var internalLotIDs = dataRows.map(function (dataRows) {
                        return dataRows.id;
                    }).join(",");

                    $.post('${pageContext.request.contextPath}/WorkOrder/getExpectedWorkOrderNumber/' + internalLotIDs + '.koala').done(function (msg) {
                        if (msg.success) {
                            dialog.find('#lotNumber').message({
                                type: 'success',
                                content: '提交成功'
                            });
                            form.find('#internalLotNumberID').val(internalLotNumbers);
                            form.find('#internalLotIDID').val(internalLotIDs);
                            form.find('#workNumberID').val(msg.data);
                            dialog.modal('hide');
                        } else {
                            dialog.find('#lotNumber').message({
                                type: 'error',
                                content: msg.errorMessage
                            });
                        }
					});

                })
            })
            form.find('#bomOption').on('click',function(){
                var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                        +'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                        +'data-dismiss="modal" aria-hidden="true">&times;</button>'
                        +'<h4 class="modal-title">选择物料</h4></div><div class="modal-body">'
                        +'<div style="margin-bottom: 10px;"><label>测试类型:&nbsp;</label><div style="display:inline"><div class="btn-group select" id="bomTestTypeID"></div><input type="hidden" id="bomTestTypeID_" name="internalProductDTO.testType" /> </div><div style="display:inline;margin-left: 20px;"><label>产品类型:&nbsp;</label><input name="internalProductDTO.customerProductNumber" type="text" id="internalProductOption"/></div></div><div id="bomNumber"></div></div><div class="modal-footer">'
                        +'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                        +'<button type="button" class="btn btn-success" id="bomNumberSave">提交</button></div></div>'
                        +'</div></div>');
                dialog.modal({
                    keyboard: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    },
                    'shown.bs.modal' :function(){
                        dialog.find('#bomTestTypeID').select({
                            title: '请选择',
                            contents: contents
                        }).on('change',function(){
                            var params={}
                            var name = dialog.find('#bomTestTypeID_').attr('name');
                            params[name] = $(this).getValue();
                            dialog.find('#bomNumber').getGrid().search(params);
                        });
                        var columns = [{
                            title : "料号",
                            name : "number",
                            width : 100
                            },
                            {
                                title : "Description",
                                name : "description",
                                width : 100
                            }];
                        dialog.find('#bomNumber').grid({
                            identity: 'id',
                            columns: columns,
                            isShowPages:false,
                            url: "${pageContext.request.contextPath}/BomTemplate/pageJson.koala"
                        });
                    }
                });
                dialog.find('#internalProductOption').on('keyup',function(){
                    var params={}
                    var name = dialog.find('#internalProductOption').attr('name');
                    params[name] = dialog.find('#internalProductOption').val();
                 //   params.modelNumber = dialog.find('#internalProductOption').val();
                    dialog.find('#bomNumber').getGrid().search(params);
                });
                dialog.find('#bomNumberSave').on('click',function(event, data){
                    var dataRowsIndex = dialog.find('#bomNumber').getGrid().selectedRowsIndex();
                    if(dataRowsIndex.length == 0){
                        dialog.find('#bomNumber').message({
                            type:'warning',
                            content:'请选择一个批次'
                        });
                        return;
                    }
                    form.find('#bomNumberID').val(dataRowsIndex + '');
                    dialog.modal('hide');
                })
            })
			form.find('#workorderPreview').on('click',function(){
				var data= {workOrderNumber: form.find('#workNumberID').val(),
                    lotIds: form.find('#internalLotIDID').val(),
							bomIds: form.find('#bomNumberID').val() };
                debugger;
                if (data.workOrderNumber == '' || data.lotIds == '' || data.bomIds == '') {
					$('#workOrderOpID').message({
						type: 'error',
						content: '请添加必要信息:生产批号、BOM清单、工单编号'
					});
					return;
				}
				$.ajax({
					async: false,
					url: '${pageContext.request.contextPath}/WorkOrder/getWorkOrderVo.koala',
					type: 'GET',
					dataType: 'json',
					data: data
				}).done(function (msg) {
					//TODO 填充table显示
				    if(msg.success){
                        var data = msg.data;
                        var productTable =  ('<table class="table table-bordered table-condensed"><caption style="font-size:30px;font-weight: bolder;margin:6px;">镇江艾科半导体有限公司</caption><caption style="font-size:24px;font-weight: bold">生产工单</caption><tr><td>客户代码：</td>' +
                                '<td><input type="text" value='+ data.customerNumber +'></td><td>客户交期:</td><td><input type="text" ></td>' +
                                '<td>生产数量(pcs)</td><td><input type="text" value='+ data.amount +'></td><td>开始日期:</td><td>' +
                                '<input type="text" value='+ data.startTime +'></td></tr><tr><td>工单编号:</td><td>' +
                                '<input type="text" value='+ data.workOrderNumber +'></td><td>产品型号:</td><td><input type="text" value='+ data.customerProductNumber +'></td>' +
                                '<td>生产批号:</td><td><textarea readonly="readonly" cols="22" rows="'+ (data.internalLotNumber.split('\n').length-1) +'">'+ data.internalLotNumber +'</textarea></td><td>结束日期:</td><td><input type="text" ></td></tr></table>');
                        var materialTable = ('<table class="table table-bordered table-condensed"><caption style="font-size:30px;font-weight: bolder;">需求物料清单</caption>' +
                                '<tr style="width:50px;"><td>序号</td><td>料号</td><td style="width:350px;">品名/规格</td><td style="width:50px;">最小用量</td><td style="font-weight: bold;">理论需求</td><td style="font-weight: bold">实际领用</td><td>最小单位</td></tr>');
            
                        var docfrg = [];
                        docfrg.push(productTable);
                        docfrg.push(materialTable);
                        var dataInput = data.bomTemplateVos;
                        for(var i= 0,len=data.bomTemplateVos.length;i<len;i++){
                            var materialTr = ('<tr><td><input type="text" style="width:50px;" value='+ dataInput[i].bomId +'></td><td><input type="text" value='+ dataInput[i].number +'></td>' +
                            '<td><input type="text" style="width:350px;" value="'+ (dataInput[i].description) +'"></td><td><input type="text" value='+ dataInput[i].minRequire +'></td>' +
                            '<td><input type="text"  style="font-weight: bold;" value='+ dataInput[i].theoryQuantity +'></td><td><input  style="font-weight: bold;" type="text" value='+ dataInput[i].quantity +'></td>' +
                            '<td><input style="width:50px;" type="text" value='+ dataInput[i].um +'></td></tr>');
                            docfrg.push(materialTr);
                        }
                        docfrg.push('<tr><td colspan="8">备注：载（盖）带预算15%buffer，包材预算10%buffer，内标签20%buffer</td></tr><tr><td>生管:</td><td>物控:</td><td>计划:</td><td>核准:</td></tr>' +
                                '<tr><td style="font-size:24px;font-weight: bold;text-align: center;" colspan="8">制造工艺流程</td></tr><tr>' +
                                '<td colspan="8"  style="text-align: center;">IQC-->Baking-->FT+EQC-->FVI-->PACKING-->WAREHOUSE-->OQC -->Ship</td></tr>' +
                                '<tr><td>结单日期:</td><td><input type="text" ></td><td>入库人员:</td><td><input type="text"></td><td>移交人员:</td><td colspan="2"><input type="text" ></td>');
                        docfrg.push('</table>');
                        preview.find('div').html(docfrg.join(''));
                        $.each(preview.find('div').find('input'),function(){
                            $(this).attr('disabled', true);
                        })
                    }
				});
			})
			form.find('#saveWorkorder').on('click',function(){
				var data= {workOrderNumber: form.find('#workNumberID').val(),
                    lotIds: form.find('#internalLotIDID').val(),
							bomIds: form.find('#bomNumberID').val() };
                if (data.workOrderNumber == '' || data.lotIds == '' || data.bomIds == '') {
					$('#workOrderOpID').message({
						type: 'error',
						content: '请添加必要信息:生产批号、BOM清单、工单编号'
					});
					return;
				}
				$.ajax({
					async: false,
					url: '${pageContext.request.contextPath}/WorkOrder/create.koala',
					type: 'GET',
					dataType: 'json',
					data: data
				}).done(function (msg) {
					if ( msg.success ) {
						$('#workOrderOpID').message({
							type: 'success',
							content: '导出成功'
						});
						var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">导出工单</h4></div><div class="modal-body"><p id="downloadID"></p></div><div class="modal-footer"><button type="button" class="btn btn-success" data-dismiss="modal" id="save">确定</button></div></div></div></div>');
						dialog.modal({
							keyboard: false
						}).on({
							'hidden.bs.modal': function () {
								$(this).remove();
							}
						}).find('#downloadID').html("导出成功！" + "<a style='margin-left:20px;'   href='"+ msg.data +"'>点击下载</a>"); 
					} else {
						$('#workOrderOpID').message({
							type: 'error',
							content: '导出失败 ' + msg.errorMessage
						});
					}
				});
			})
        })
    </script>
</head>
<body>
<div style="width:100%;height:800px;margin-right:auto; margin-left:auto; padding: 15px;background-color: #ABABAB;padding-bottom: 100px;">
    <!-- search form -->
    <form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
        <div style="background-color: white;padding:10px;border:1px solid black;margin-top: 10px;">
            <div class="form-group">
                <label class="control-label" style="width:100px;float:left;">测试类型:&nbsp;</label>
                <div style="margin-left:15px;float:left;">
                    <div class="btn-group select" id="testTypeID"></div>
                    <input type="hidden" id="testTypeID_" name="internalProductDTO.testType" />
                </div>
                <label class="control-label" style="width:100px;float:left;">生产批号:&nbsp;</label>
                <div style="margin-left:15px;float:left;">
                    <input name="internalLotNumber" class="form-control" type="text" style="width:400px;"
                           id="internalLotNumberID"/>
                    <input name="internalLotID" type="hidden" id="internalLotIDID" />
                </div>
                <button type="button" class="btn btn-primary" style="margin-left:20px;" id="productOption">选择</button>
            </div>
            <div class="form-group"  style="margin-left:195px;" >
                    <label class="control-label" style="width:100px;float:left;">BOM清单:&nbsp;</label>
                    <div style="margin-left:15px;float:left;">
                        <input name="bomNumber" class="form-control" type="text" style="width:400px;" id="bomNumberID"/>
                    </div>
                    <button type="button" class="btn btn-primary" style="margin-left:20px;" id="bomOption">选择</button>
                </div>
        </div>
        <div style="background-color: white;padding:10px;bordor:1px solid black;margin-top:20px;" id="workOrderOpID">
            <div class="form-group">
                <label class="control-label" style="width:100px;float:left;">工单编号:&nbsp;</label>
                <div style="margin-left:15px;float:left;">
                    <input name="workNumber" class="form-control" type="text" style="width:180px;" id="workNumberID"  />
                </div>
                <button type="button" class="btn btn-primary" style="margin-left:20px;" id="workorderPreview">预览工单</button>
                <button id="saveWorkorder" type="button" style="position:relative; margin-left:435px;" class="btn btn-primary">另存为</button>
                <button id="printWorkorder" type="button" style="position:relative; margin-left:35px;" class="btn btn-primary">打印</button>
        </div>
        </div>
    </form>
    <!-- grid -->
    <div id=<%=previewId%>  style="margin-top:20px;">
       <label>工单预览:</label>
       <div style="heigth:500px;width:100%;background-color: white;border:1px solid black;" class="table-responsive">
         <%--  <img style="border:1px solid transparent; width:100%; height:100%;margin-top:10px;" src="<%=path%>/demoPic.png"/>--%>
       </div>
    </div>
</div>
</body>
</html>
