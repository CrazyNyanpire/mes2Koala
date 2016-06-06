<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade selectDepartment">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">选择审批人</h4>
            </div>
            <div class="modal-body">
                  <form class="form-horizontal">
				    <div class="form-group" id="serachItems">
				        <label class="col-lg-2 control-label">工号:</label>
				        <div class="col-lg-3">
				            <input name="userAccount" style="display:inline; width:94%;" class="form-control"  type="text" />
				        </div>
				      <button type="button" class="btn btn-primary" id="searchFTLot">查询</button>
				    </div>
				    <div class="userGrantAuthority modal-body">
				            <div class="right-modal-body" style="font-size:150%;font-weight: bold;">选择人员</div>
				            <div id="staffGrid"></div>
				    </div>
				</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success" id="staffConfirm">确定</button>
            </div>
        </div>
    </div>
</div>