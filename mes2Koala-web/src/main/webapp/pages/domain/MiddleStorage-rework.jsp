<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	           <div class="form-group">
                    <label class="col-lg-2 control-label">客户PPO:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="customerPPO" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerPPOID" />
                     </div>
                     </div>
                     <label class="col-lg-2 control-label">物料类型:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                    	<div class="btn-group select" id="materialID"></div>
                        <input type="hidden" name="material" id="materialID_" dataType="Require">
                     </div>
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-2 control-label">内部PPO:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="customerPPO" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerPPOID" />
                     </div>
                     </div>
                     <label class="col-lg-2 control-label">Quantity:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="qty" style="display:inline; width:94%;" class="form-control"  type="text"  id="qtyID" />
                     </div>
			    </div>
	</div>
		          <div class="form-group">
                    <label class="col-lg-2 control-label">Customer lot NO:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="customerLotNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerLotNumberID" />
                     </div>
                    </div>
                     <label class="col-lg-2 control-label">Data Code:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="dateCode" style="display:inline; width:94%;" class="form-control"  type="text"  id="dateCodeID" />
                     </div>
			    </div>
	</div>
				<div class="form-group">
                    <label class="col-lg-2 control-label">封装批号:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="customerPPO" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerPPOID" />
                     </div>
                    </div>
                     <label class="col-lg-2 control-label">进料日期:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="incomingDate" style="display:inline; width:94%;" class="form-control"  type="text"  id="incomingDateID" />
                     </div>
			    </div>
	</div>
				<div class="form-group">
                    <label class="col-lg-2 control-label">Lot Number:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="internalLotNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="internalLotNumberID" />
                     </div>
                    </div>
                     <label class="col-lg-2 control-label">package type:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="packageType" style="display:inline; width:94%;" class="form-control"  type="text"  id="packageTypeID" />
                     </div>
			    </div>
	</div>
				<div class="form-group">
                     <label class="col-lg-2 control-label">Customer NO:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="customerNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerNumberID" />
                     </div> 
			    </div>
			    <label class="col-lg-2 control-label">wafer Lot:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="waferLot" style="display:inline; width:94%;" class="form-control"  type="text"  id="waferLotID" />
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
			    <label class="col-lg-2 control-label">保税类型:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="taxType" style="display:inline; width:94%;" class="form-control"  type="text"  id="taxTypeID" />
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
			    <label class="col-lg-2 control-label">出货型号:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="shipmentProductNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="shipmentProductNumberID" />
                     </div>
                    </div>
	</div>
				<div class="form-group">
                    
                     <label class="col-lg-2 control-label">重工类型:</label>
	                 <div class="col-lg-4">
	                 <div class="input-group" style="width:160px;float:left;" >
                        <div class="btn-group select" id="reworkTypeID"></div>
                        <input type="hidden" name="reworkType" id="reworkTypeID_"  dataType="Require">
                     </div>
                    </div>
                     <label class="col-lg-2 control-label">Wire Bond:</label>
	                 <div class="col-lg-4">
                    <div class="input-group" style="width:160px;float:left;" >
                        <input name="wireBond" style="display:inline; width:94%;" class="form-control"  type="text"  id="wireBondID" />
                     </div>
			    </div>
	</div>
				<div class="form-group">
                     <label class="col-lg-2 control-label">重工站点:</label>
	                 <div class="col-lg-4">
	                 <div class="input-group" style="width:160px;float:left;" >
                        <div class="btn-group select" id="stationID"></div>
                        <input type="hidden" name="station" id="stationID_"  dataType="Require">
                     </div>
                    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
    var contents = [ {title : '请选择',value : ''} ];	 
    contents.push( {title : '艾科重工' ,value : '艾科重工'} );
    contents.push( {title : '客户重工' ,value : '客户重工'} );
    selectItems['reworkTypeID']=contents;
    var contents = [ {title : '请选择',value : ''} ];	 
    contents.push( {title : '量产' ,value : '量产'} );
    contents.push( {title : '工程' ,value : '工程'} );
    contents.push( {title : 'RA' ,value : 'RA'} );
    contents.push( {title : 'RMA' ,value : 'RMA'} );
    contents.push( {title : 'QS' ,value : 'QS'} );
    contents.push( {title : '调运' ,value : '调运'} );
    contents.push( {title : 'M' ,value : 'M'} );
    selectItems['materialID']=contents;
</script>
</body>
</html>