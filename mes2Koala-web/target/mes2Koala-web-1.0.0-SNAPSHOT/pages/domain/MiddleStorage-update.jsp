<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="versionID" name="version" /> 
	
	           <div class="form-group">
                    <label class="col-lg-2 control-label">艾科批号:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="internalLotNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="internalLotNumberID" />
                     </div>
                     </div>
                     <label class="col-lg-2 control-label">品质:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                    	<div class="btn-group select" id="qualityID"></div>
                        <input type="hidden" name="quality" id="qualityID_" >
                     </div>
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-2 control-label">产品型号:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="shipmentProductNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="shipmentProductNumberID" />
                     </div>
                     </div>
                     <label class="col-lg-2 control-label">DateCode:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="dateCode" style="display:inline; width:94%;" class="form-control"  type="text"  id="dateCodeID" />
                     </div>
			    </div>
	</div>
		          <div class="form-group">
                    <label class="col-lg-2 control-label">版本型号:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="productVersion" style="display:inline; width:94%;" class="form-control"  type="text"  id="productVersionID" />
                     </div>
                    </div>
                     <label class="col-lg-2 control-label">Approved:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="approve" style="display:inline; width:94%;" class="form-control"  type="text"  id="approveID" />
                     </div>
			    </div>
	</div>
				<div class="form-group">
                    <label class="col-lg-2 control-label">客户PPO:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="customerPPO" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerPPOID" />
                     </div>
                    </div>
                     <label class="col-lg-2 control-label">出货形式:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="name" style="display:inline; width:94%;" class="form-control"  type="text"  id="nameID" />
                     </div>
			    </div>
	</div>
				<div class="form-group">
                    <label class="col-lg-2 control-label">实际数量:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="quantity" style="display:inline; width:94%;" class="form-control"  type="text"  id="quantityID" />
                     </div>
                    </div>
                     <label class="col-lg-2 control-label">Mark编号:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="name" style="display:inline; width:94%;" class="form-control"  type="text"  id="nameID" />
                     </div>
			    </div>
	</div>
				<div class="form-group">
                     <label class="col-lg-2 control-label">来料型号:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="customerProductNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerProductNumberID" />
                     </div> 
			    </div>
			    <label class="col-lg-2 control-label">是否满卷:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <div class="btn-group select" id="isFullID"></div>
                        <input type="hidden" name="isFull" id="isFullID_" >
                     </div>
			    </div>
	</div>
				<div class="form-group">
                    
                     <label class="col-lg-2 control-label">被合批信息:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="combinedLotNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="combinedLotNumberID" />
                     </div>
			    </div>
			    <label class="col-lg-2 control-label">合批信息:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="fromReelCode" style="display:inline; width:94%;" class="form-control"  type="text"  id="fromReelCodeID" />
                     </div>
                    </div>
	</div>
				<div class="form-group">
                <label class="col-lg-2 control-label">满卷数量:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="name" style="display:inline; width:94%;" class="form-control"  type="text"  id="nameID" />
                     </div>
			    </div>      
			    <label class="col-lg-2 control-label">状态:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="status" style="display:inline; width:94%;" class="form-control"  type="text"  id="statusID" />
                     </div>
                    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
    var contents = [ {title : '请选择',value : ''} ];	 
    contents.push( {title : 'Fail' ,value : 'fail'} );
    contents.push( {title : 'Pass' ,value : 'pass'} );
    contents.push( {title : 'Pass1' ,value : 'pass1'} );
    contents.push( {title : 'Pass2' ,value : 'pass2'} );
    selectItems['qualityID']=contents;
    var contents = [ {title : '请选择',value : ''} ];	 
    contents.push( {title : '是' ,value : '是'} );
    contents.push( {title : '否' ,value : '否'} );
    selectItems['isFullID']=contents;
</script>
</body>
</html>