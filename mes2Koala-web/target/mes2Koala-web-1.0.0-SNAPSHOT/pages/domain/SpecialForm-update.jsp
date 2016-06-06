<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<input type="hidden" id="idID" name="id" /> 
	<input type="hidden" id="versionID" name="version" /> 
	
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Checkbox:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="checkBoxFormID"></div>
	                       <input type="hidden" id="checkBoxFormID_" name="checkBoxForm" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">自主检查表:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="checkSelfFormID"></div>
	                       <input type="hidden" id="checkSelfFormID_" name="checkSelfForm" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">FLOW:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="flowFormID"></div>
	                       <input type="hidden" id="flowFormID_" name="flowForm" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">首页:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="indexFormID"></div>
	                       <input type="hidden" id="indexFormID_" name="indexForm" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">机台落料表:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="lossFormID"></div>
	                       <input type="hidden" id="lossFormID_" name="lossForm" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">ReelCode:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="reelcodeFormID"></div>
	                       <input type="hidden" id="reelcodeFormID_" name="reelcodeForm" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">尺寸记录表:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="sizeFormID"></div>
	                       <input type="hidden" id="sizeFormID_" name="sizeForm" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">Summary:</label>
	                    <div class="col-lg-9">
                           <div class="btn-group select" id="summaryFormID"></div>
	                       <input type="hidden" id="summaryFormID_" name="summaryForm" />
			    </div>
	</div>
	</form>
<script type="text/javascript">
    var selectItems = {};
            selectItems['checkBoxFormID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
                selectItems['checkSelfFormID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
                selectItems['flowFormID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
                selectItems['indexFormID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
                selectItems['lossFormID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
                selectItems['reelcodeFormID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
                selectItems['sizeFormID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
                selectItems['summaryFormID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: 'true'},
                   {title: '否', value: 'false'}
                ];
        </script>
</body>
</html>