<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="row">
	<div class="col-lg-6">
	<fieldset disabled>
	<strong>基础资料查看:</strong>
		<div class="col-lg-12">
			<strong>To:</strong>
			<div class="form-group">
	            <div class="col-lg-2 col-lg-offset-1">
	               <input type="radio"   name="toDepartment" id="TDEID" >
	               <label class="control-label">TDE</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"   name="toDepartment" id="QAID" >
	               <label class="control-label">QA</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"   name="toDepartment" id="PPID" >
	               <label class="control-label">PP</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"  name="toDepartment" id="CSID" >
	               <label class="control-label">CS</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"   name="toDepartment" id="EEID" >
	               <label class="control-label">EE</label>
	            </div>
	        </div>
	    </div>
	    <div class="col-lg-12">
			<strong>异常站点:</strong>
			<div class="form-group" style="text-align:center;">
	            <div class="col-lg-3">
	               <label class="control-label" id="flowNowID"></label>
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
					<p><span>不良片数:</span></p>
					<p style="margin-top: 20%;"><span>测试机台:</span></p>
					<p style="margin-top: 15%;"><span>异常发现人:</span></p>
				</div>
				<div class="col-lg-7" style="padding:0;">
					<p><input style="width:100%;margin-right:10px"type="text" name="failQty" id="failQtyID"></p>
					<p><input style="width:100%;"type="text" name="testerSys" id="testerSysID"></p>
					<p><input style="width:100%;"type="text" name="workPerson"  id="workPersonID"></p>
				</div>
			</div>
		</div>
    </div>
    <div class="col-lg-12">
		<strong style="margin-bottom: 1%;display: block;">异常描述:</strong>
		<div class="col-lg-12">
            <table class="table table-condensed table-center QDNNOTE" style="margin-top:10px;">
                   <th style="width:100%;"><textarea rows="5" class="form-control" name="note" id="noteID"></textarea></th>
                   <th><img width="100" height="100" src="images/cp.png"/></th>
            </table>
            <table style="margin-top:1px;" >
            	<p><span>Risk Lot:</span><input style="margin-left:12px;" "type="text" name="riskLot" id="riskLotID"/><span style="margin-left: 5%;">PS:Lot用逗号隔开</span></p>
            </table>
        </div>
    </div>
    <div class="col-lg-12">
		<strong style="margin-bottom: 1%;display: block;">Low Yield:</strong>
		<div class="col-lg-12">
            <table class="table table-condensed table-center QDNINFO" style="margin-top:10px;">
                <tr>
                    <th>AVG</th><th>1#</th><th>2#</th><th>3#</th><th>4#</th><th>5#</th><th>6#</th>
                    <th>7#</th><th>8#</th><th>9#</th><th>10#</th><th>11#</th><th>12#</th>
                </tr>
            	<tr>
            	    <th><input style="width:25px" size="1" type="text" name="AVG" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="1#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="2#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="3#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="4#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="5#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="6#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="7#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="8#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="9#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="10#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="11#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="12#" value="0"></th>
            	</tr>
            	 <tr>
                    <th><input type="checkbox" name="AVG" id="AVG"></th>
                    <th><input type="checkbox" name="1"></th>
                    <th><input type="checkbox" name="2"></th>
                    <th><input type="checkbox" name="3"></th>
                    <th><input type="checkbox" name="4"></th>
                    <th><input type="checkbox" name="5"></th>
                    <th><input type="checkbox" name="6"></th>
                    <th><input type="checkbox" name="7"></th>
                    <th><input type="checkbox" name="8"></th>
                    <th><input type="checkbox" name="9"></th>
                    <th><input type="checkbox" name="10"></th>
                    <th><input type="checkbox" name="11"></th>
                    <th><input type="checkbox" name="12"></th>
                 </tr>
                <tr>
                    <th>13#</th><th>14#</th><th>15#</th><th>16#</th><th>17#</th><th>18#</th><th>19#</th>
                    <th>20#</th><th>21#</th><th>22#</th><th>23#</th><th>24#</th><th>25#</th>
                </tr>
            	<tr>
            	    <th><input style="width:25px" size="1" type="text" name="13#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="14#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="15#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="16#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="17#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="18#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="19#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="20#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="21#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="22#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="23#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="24#" value="0"></th>
                    <th><input style="width:25px" size="1" type="text" name="25#" value="0"></th>
            	</tr>
            	 <tr>
                    <th><input type="checkbox" name="13"></th>
                    <th><input type="checkbox" name="14"></th>
                    <th><input type="checkbox" name="15"></th>
                    <th><input type="checkbox" name="16"></th>
                    <th><input type="checkbox" name="17"></th>
                    <th><input type="checkbox" name="18"></th>
                    <th><input type="checkbox" name="19"></th>
                    <th><input type="checkbox" name="20"></th>
                    <th><input type="checkbox" name="21"></th>
                    <th><input type="checkbox" name="22"></th>
                    <th><input type="checkbox" name="23"></th>
                    <th><input type="checkbox" name="24"></th>
                    <th><input type="checkbox" name="25"></th>
                 </tr>
            </table>
          </div>
        </div>
	    <div class="col-lg-12" style="padding-left:6%;padding-right:6%;"> 
	    	<span style="float:left;"><span>QDN单号:</span><span id="qdnNoID"></span></span>
	    	<span style="float:right;"><span>开单人:</span><span id="createEmployNoID"></span></span>
	    </div>
	    <div class="col-lg-12" style="padding-left:6%;padding-right:6%;margin-top:15px;"> 
	    	<span style="float:left;"><span>开单日期:</span><span id="createTimestampID"></span></span>
	    </div>
	    </fieldset>
    </div>
    <form class="form-horizontal col-lg-6" name="upload"  enctype="multipart/form-data"  method="post" target="hiddenIframename" action="">
    <strong>进阶资料填写:</strong>
    <div class="col-lg-12">
    <fieldset id="hideDepartment">
    <input type="text"  class="hidden"  name="status" id="statusID">
    <input type="text"  class="hidden"  name="version" id="versionID">
		<div class="col-lg-12">
			<strong>分析部门描述异常原因:</strong>
			<div class="form-group" style="padding-left: 15px;">
	            <textarea rows="3" class="form-control" name="reason" placeholder="" id="reasonID"></textarea>
	        </div>
	    </div>
	    <div class="col-lg-12">
			<strong>内部处理意见:</strong>
			<div class="form-group">
	            <div class="col-lg-2 col-lg-offset-1" style="padding: 0;">
	               <input type="radio"   name="suggestion" >
	               <label class="control-label">继续生产</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"   name="suggestion" >
	               <label class="control-label">重工</label>
	            </div>
	            <div class="col-lg-2" style="padding:0;">
	               <input type="radio" name="suggestion" >
	               <label class="control-label">退回客户</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"  name="suggestion" >
	               <label class="control-label">报废</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"   name="suggestion" >
	               <label class="control-label">其他</label>
	            </div>
	        </div>
	        <strong>处理描述:</strong>
			<div class="form-group" style="padding-left: 15px;">
	            <textarea rows="3" class="form-control" name="internalDealNote" placeholder="" id="internalDealNoteID"></textarea>
	        </div>
	        <strong>处理方式:</strong>
			<div class="form-group">
	            <div class="col-lg-3 col-lg-offset-5" style="padding: 0;">
	               <input type="radio" index="QA"  name="internalDeal" >
	               <label class="control-label">QA判断解Hold</label>
	            </div>
	            <div class="col-lg-4">
	               <input type="radio" index="TDE"  name="internalDeal" >
	               <label class="control-label">仍需客户处理意见</label>
	            </div>
	        </div>
	    </div>
	    </fieldset>
	    <fieldset id="hideCustomer">
	    <div class="col-lg-12">
			<strong>客户处理意见:</strong>
			<div class="form-group">
	            <div class="col-lg-2 col-lg-offset-1" style="padding: 0;">
	               <input type="radio"   name="customerDeal" >
	               <label class="control-label">继续生产</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"   name="customerDeal" >
	               <label class="control-label">重工</label>
	            </div>
	            <div class="col-lg-2" style="padding: 0;">
	               <input type="radio"   name="customerDeal" >
	               <label class="control-label">退回客户</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"  name="customerDeal" >
	               <label class="control-label">报废</label>
	            </div>
	            <div class="col-lg-2">
	               <input type="radio"   name="customerDeal" >
	               <label class="control-label">其他</label>
	            </div>
	        </div>
	        <strong>处理描述:</strong>
			<div class="form-group" style="padding-left: 15px;">
	            <textarea rows="3" class="form-control" name="customerDealNote" placeholder="" id="customerDealNoteID"></textarea>
	        </div>
	        <strong>QA处理意见:</strong>
			<div class="form-group">
	            <div class="col-lg-2 col-lg-offset-8" style="padding: 0;">
	               <input type="radio"   name="qasuggestion">
	               <label class="control-label">解HOLD</label>
	            </div>
	            <div class="col-lg-2" style="padding: 0;">
	               <input type="radio"   name="qasuggestion">
	               <label class="control-label">继续Hold</label>
	            </div>
	        </div>
	        <div class="form-group">
	            <div class="col-lg-12" style="padding-left:6%;padding-right:6%;margin-top:15px;"> 
		    		<span style="float:right;"><span>确认人:</span><span id="lastModifyEmployNoID"></span></span>
			    </div>
			    <div class="col-lg-12" style="padding-left:6%;padding-right:6%;margin-top:15px;"> 
		    		<span style="float:right;"><span>确认日期:</span><span id="lastModifyTimestampID"></span></span>
			    </div>
	        </div>
	    </div>	    	    	
	    </fieldset>
    <div class="col-lg-12" style="padding-right:6%;"> 
    	<strong style="margin-bottom: 2%;display: block;">操作选项:</strong>
    	<span class="QDN_footer">
    	<span>接收人:</span>
    	<select name="toPerson" style="font-size: inherit;">
    		<option>张三</option>
    	</select>
    	<span>附件:</span>
    	<div style="display:inline" id="filelist"></div>
		<input name="file" type="file" style="display: inline;width: 200px;" id="tablefile" onchange="if($(this).val()!=''){ajaxsubmit('');}"/>	
		</span>
    	<span style="float:right;margin-top: 8px;"><span>填写人:</span><span id="QDN_writer"></span></span>
    </div>
</form>
</div>
<script type="text/javascript"> 
</script>
</body>
</html>