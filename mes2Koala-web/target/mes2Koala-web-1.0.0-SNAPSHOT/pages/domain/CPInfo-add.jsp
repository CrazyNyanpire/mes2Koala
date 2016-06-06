<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
// 文件上传
function inputfile() {
    $("#inputfilebtnID").click();
    $("#inputfilebtnID").on("change", function () {
  	     var path = $("#inputfilebtnID").val();
   	     var pos1 = path.lastIndexOf('/');
   	     var pos2 = path.lastIndexOf('\\');
   	     var pos  = Math.max(pos1, pos2)
   	     if( pos<0 ){
   	    	filename = path;
   	     }else{
   	    	filename = path.substring(pos+1);
   	     }
   	     $("#productRequireID").val(filename);
 		 var fd = new FormData();
 		 fd.append('file', $('#inputfilebtnID')[0].files[0]);
 		 $.ajax({
 			type: 'post',
 			url: '${pageContext.request.contextPath}/CPInfo/uploadProdcutRequireFile.koala',
 			data: fd,
 			processData: false,
 			contentType: false,
 			cache: false,
 			success: function (msg) {
 				console.log(msg.data);
 				grid.message({
 					type: 'success',
 					content: '上传文件成功'
 				});
 			},
 			error: function (data) {
 				console.log('error');
 				grid.message({
 					type: 'error',
 					content: '上传文件失败'
 				})
 			}
 		});
    }); 
}

</script>
<body>
<form class="form-horizontal">

    <div class="form-group">
        <label class="col-lg-3 control-label">客户产品型号:</label>
        <div class="col-lg-6">
            <input name="customerProductNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="customerProductNumberID"/>
            <span class="required">*</span></div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">客户产品版本:</label>
        <div class="col-lg-6">
            <input name="customerProductRevision" style="display:inline; width:94%;" class="form-control" type="text"
                   id="customerProductRevisionID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">PID:</label>
        <div class="col-lg-6">
            <input name="internalProductNumber" style="display:inline; width:94%;" readonly class="form-control" type="text"
                  dataType="Require"  id="internalProductNumberID"/>
                  <span class="required">*</span>
        </div>
    </div>
    
    <div id="ManagerDTOID">
    <div class="form-group">
        <label class="col-lg-3 control-label">质量主要负责人:</label>
        <div class="col-lg-3">
            <input type="hidden" id="keyQuantityManagerDTOID_" name="keyQuantityManagerDTO.id"/>
            <button type="button" id="keyQuantityManagerDTOID" class="btn btn-default">请选择</button>
        </div>

        <label class="col-lg-3 control-label">质量协助负责人:</label>
        <div class="col-lg-3">
            <input type="hidden" id="assistQuantityManagerDTOID_" name="assistQuantityManagerDTO.id"/>
            <button type="button" id="assistQuantityManagerDTOID" class="btn btn-default">请选择</button>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 control-label">产品主要负责人:</label>
        <div class="col-lg-3">
            <input type="hidden" id="keyProductionManagerDTOID_" name="keyProductionManagerDTO.id"/>
            <button type="button" id="keyProductionManagerDTOID" class="btn btn-default">请选择</button>
        </div>

        <label class="col-lg-3 control-label">产品协助负责人:</label>
        <div class="col-lg-3">
            <input type="hidden" id="assistProductionManagerDTOID_" name="assistProductionManagerDTO.id"/>
            <button type="button" id="assistProductionManagerDTOID" class="btn btn-default">请选择</button>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 control-label">TDE主要负责人:</label>
        <div class="col-lg-3">
            <input type="hidden" id="keyTDEManagerDTOID_" name="keyTDEManagerDTO.id"/>
            <button type="button" id="keyTDEManagerDTOID" class="btn btn-default">请选择</button>
        </div>

        <label class="col-lg-3 control-label">TDE协助负责人:</label>
        <div class="col-lg-3">
            <input type="hidden" id="assistTDEManagerDTOID_" name="assistTDEManagerDTO.id"/>
            <button type="button" id="assistTDEManagerDTOID" class="btn btn-default">请选择</button>
        </div>
    </div>
    </div>
    
    <div class="form-group">
        <label class="col-lg-3 control-label">所属直接客户:</label>
        <div class="col-lg-9">
            <div class="btn-group select" id="customerDirectDTOID"></div>
            <input type="hidden" id="customerDirectDTOID_" name="customerDirectDTO.id" dataType="Require"/>
            <span class="required">*</span></div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">所属间接客户:</label>
        <div class="col-lg-9">
            <div class="btn-group select" id="customerIndirectDTOID"></div>
            <input type="hidden" id="customerIndirectDTOID_" name="customerIndirectDTO.id"/>
            </div>
    </div>
    
    <div class="form-group">
        <label class="col-lg-3 control-label">出货产品型号:</label>
        <div class="col-lg-6">
            <input name="shipmentProductNumber" style="display:inline; width:94%;" class="form-control" type="text"
                   id="shipmentProductNumberID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">晶圆尺寸:</label>
        <div class="col-lg-9">
            <div class="btn-group select" id="waferSizeID"></div>
            <input type="hidden" id="waferSizeID_" name="waferSize"/>
            <input name="waferSizeAddBtn" class="btn btn-default" type="button" 
                   value="+" id="modalOpen"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">GROSS DIE:</label>
        <div class="col-lg-6">
            <input name="grossDie" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="grossDieID"/>
            <span class="required">*</span>
        </div>
    </div>
    <!-- <div class="form-group">
        <label class="col-lg-3 control-label">UPH:</label>
        <div class="col-lg-6">
            <input name="uph" style="display:inline; width:94%;" class="form-control" type="text"
                   dataType="Require" id="uphID"/>
            <span class="required">*</span>
        </div>
    </div> -->

    <div class="form-group">
        <label class="col-lg-3 control-label">报警指标分类:</label>
        <div class="col-lg-6">
            <div class="btn-group select" id="warningTypeID"></div>
            <input type="hidden" id="warningTypeID_" name="warningType"/>
            <span class="required">*</span>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 control-label">测试时间/片:</label>
        <div class="col-lg-6">
            <input name="testTime" style="display:inline; width:94%;" class="form-control" type="text"
                   id="testTimeID"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">产品制程要求:</label>
        <div class="col-lg-6">
            <input name="productRequire" style="display:inline; width:75%;" class="form-control" type="text"
                   id="productRequireID" disabled="true"/>
            <input type="file" name="inputfilebtn" id="inputfilebtnID" style="display:none" /> 
            <input name="productRequireBtn" class="btn btn-default" type="button" 
                   value="选择" id="productRequireBtnID" onclick="inputfile();"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label">每片接触次数:</label>
        <div class="col-lg-6">
            <input name="touchQty" style="display:inline; width:94%;" class="form-control" type="text"
                   id="touchQtyID"/>
        </div>
    </div>
</form>
<!-- Modal -->
<div class="modal fade" id="myModal" data-backdrop="false" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id='modalClose' aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">晶圆尺寸</h4>
			</div>
			<div class="modal-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="col-lg-3 control-label">值:</label>
						<div class="col-lg-9">
							<input name="value" style="display:inline; width:94%;" class="form-control"  type="text"  id="valueID" datatype="Require" />
						    <span class="required">*</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">标签:</label>
						<div class="col-lg-9">
							<input name="label" style="display:inline; width:94%;" class="form-control"  type="text"  id="labelID" datatype="Require" />
							<span class="required">*</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">类型:</label>
						<div class="col-lg-9">
							<input name="type" style="display:inline; width:94%;" class="form-control"  type="text" value="HandlerType" id="typeID" disabled/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">描述:</label>
						<div class="col-lg-9">
							<textarea class="form-control" style="display:inline; width:94%;" rows="3" id="descriptionID" name="description"  disabled>晶圆尺寸</textarea>
						</div>
					</div>
					</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="modalCancel">取消</button>
				<button type="button" class="btn btn-success" id="modalSave">保存</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

</script>
</body>
</html>