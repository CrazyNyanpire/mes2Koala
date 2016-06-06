<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade selectDepartment">
    <div class="modal-dialog" style="width:1000px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">LAT打印</h4>
            </div>
            <div class="modal-body">
                  <form class="form-horizontal">
				    <div id="serachItems">
				    <div class="form-group">
				    	<label class="col-lg-2 control-label" style="width: 11%;">产品PPO:</label>
				        <div class="col-lg-3">
				            <input name="customerPPO" style="display:inline; width:94%;" class="form-control"  type="text" />
				        </div>
				        <label class="control-label" style="width:100px;float:left;">艾科批号:</label>
				        <div style="margin-left:15px;float:left;">
				            <input name="userAccount" style="display:inline; width:94%;" class="form-control"  type="text" />
				        </div>
				        <button type="button" class="btn btn-primary" id="searchFTLot" style="float: right;margin-right: 40px;">查询</button>
				    </div>
				    <div class="form-group">
				        <label class="col-lg-2 control-label" style="width: 11%;">产品型号:</label>
				        <div class="col-lg-3">
				            <input name="shipmentProductNumber" style="display:inline; width:94%;" class="form-control"  type="text" />
				        </div>
				      	<label class="control-label" style="width:100px;float:left;">入库时间:&nbsp;</label>
				        <div style="margin-left:15px;float:left;">
				            <div class="input-group date form_datetime" style="width:160px;float:left;" >
				                <input type="text" class="form-control" style="width:160px;" name="packagingTime" id="packagingTimeID_start" >
				                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
				            </div>
				            <div style="float:left; width:10px; margin-left:auto; margin-right:auto;">&nbsp;-&nbsp;</div>
				            <div class="input-group date form_datetime" style="width:160px;float:left;" >
				                <input type="text" class="form-control" style="width:160px;" name="packagingTimeEnd" id="packagingTimeID_end" >
				                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
				            </div>  
				       </div>
				    </div>
				    <div class="userGrantAuthority modal-body">
				            <div class="right-modal-body" style="font-size:150%;font-weight: bold;">LAT打印</div>
				            <div id="latGrid"></div>
				    </div>
				</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success" id="latConfirm">打印</button>
            </div>
        </div>
    </div>
</div>