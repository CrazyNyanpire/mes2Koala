<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal row">
	<input type="hidden"  name="qdnBinName">
	<div class="col-lg-12">
		<strong>To:</strong>
		<div class="form-group">
            <div class="col-lg-2 col-lg-offset-1">
               <input type="radio"   name="To" >
               <label class="control-label">TDE</label>
            </div>
            <div class="col-lg-2">
               <input type="radio"   name="To" >
               <label class="control-label">QA</label>
            </div>
            <div class="col-lg-2">
               <input type="radio"   name="To" >
               <label class="control-label">PP</label>
            </div>
            <div class="col-lg-2">
               <input type="radio"  name="To">
               <label class="control-label">CS</label>
            </div>
            <div class="col-lg-2">
               <input type="radio"   name="To">
               <label class="control-label">EE</label>
            </div>
        </div>
    </div>
    <div class="col-lg-12">
		<strong>Hold站点:</strong>
		<div class="form-group" style="text-align:center;">
            <div class="col-lg-3">
               <select style="font-size:inherit;" name="flowNow" class="holdStation">
               </select>
            </div>
        </div>
    </div>
    <div class="col-lg-12">
		<strong style="margin-bottom: 2%;display: block;">基础信息:</strong>
		<div class="col-lg-12">
			<div class="col-lg-6" style="padding-left: 0;">
				<div class="col-lg-4" style="padding:0;text-align: right;">
					<p><span>LotNo:</span></p>
					<p><span>产品型号:</span></p>
					<p><span>客户名称:</span></p>
				</div>
				<div class="col-lg-8" style="padding-right:0">
					<p><span style="white-space: nowrap;"id="internalLotNumberID"></span></p>
					<p><span id="shipmentProductNumberID"></span></p>
					<p><span id="customerNumberID"></span></p>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="col-lg-5" style="padding:0;text-align: right;padding-right: 1%;">
					<p style="margin-top: 2%;"><span>不良数/抽样数:</span></p>
					<p style="margin-top: 16%;"><span>不良数/总数量:</span></p>
					<p style="margin-top: 17%;"><span>测试机台:</span></p>
					<p style="margin-top: 15%;"><span>作业人员:</span></p>
				</div>
				<div class="col-lg-7" style="padding:0;">
					<p><input style="width:40%;margin-right:10px"type="text" name="failSample">/<input style="width:40%;margin-left:10px"type="text" name="sampleQty"></p>
					<p><input style="width:40%;margin-right:10px"type="text" name="failTotal">/<input style="width:40%;margin-left:10px"type="text" id="qtyID" name="totalQty"></p>
					<p><input style="width:100%;"type="text" name="testerSys"></p>
					<p><input style="width:100%;"type="text" name="workPerson"></p>
				</div>
			</div>
		</div>
    </div>
    <div class="col-lg-12">
		<strong style="margin-bottom: 2%;display: block;">异常描述:</strong>
		<div class="col-lg-12">
            <textarea rows="5" class="form-control" name=note></textarea>
            <table class="table table-condensed table-center QDNBin" style="margin-top:30px;">
            	<tr><th>BIN</th><th>Yield</th><th>Pass</th><th>Loss</th><th>Scrap</th><th>Other</th><th>Sum</th></tr>
            	<tr><td>Num</td><td><input style="width:100%;"type="text" /></td><td><input style="width:100%;"type="text" /></td><td><input style="width:100%;"type="text" /></td><td><input style="width:100%;"type="text" /></td><td><input style="width:100%;"type="text" /></td><td><input style="width:100%;"type="text" /></td></tr>
            </table>
        </div>
    </div>
    <div class="col-lg-12" style="padding-left:6%;padding-right:6%;"> 
    	<span>接收人:</span>
    	<select name="toPerson" style="font-size: inherit;">
    	</select>
    	<span style="float:right;"><span>开单人:</span><span id="QDN_initiator"></span></span>
    </div>
</form>
<script type="text/javascript">
/* dialog.find("input[name='To']").bind("click",function(){
   	if($(this).attr("checked")=="checked")
  		{
   		debugger;
   		$.ajax({
   			async : false,
   			url : '${pageContext.request.contextPath}/.koala',
   			type : 'POST',
   			dataType : 'json',
   		}).done(function(msg) {
   			$("select[name='QDN_receiver']").empty();
   			for (var i = 0; i < msg.length; i++) {
   				$("select[name='QDN_receiver']").append("<option value='"+msg[i]['number']+"'>"+msg[i]['name']+"<option>");
   			}
   		}); 
  		}
   		
   }); */
</script>
</body>
</html>