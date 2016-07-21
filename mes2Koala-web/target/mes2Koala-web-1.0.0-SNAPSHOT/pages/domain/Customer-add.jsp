<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal" id="form">

	<div class="form-group">
		<label class="control-label col-lg-3">客户编号:</label>
                    <div class="col-lg-3" style="display:inline;">
						<input name="number" style="display:inline; width:94%;" class="form-control" type="text"
							   id="numberID" placeholder="xxx-xx" 
							   dataType="Regex" validateExpr="/^[A-Z]{3}-[A-Z]{2}$/" errorMsg="格式不正确。请注意大小写。eg:ABC-DE"/>
					</div>
		<label class="col-lg-3 control-label" style="margin-left:-50px;">客户编码:</label>

		<div class="col-lg-3" style="display:inline;">
			<input name="code" style="display:inline; width:94%;" class="form-control" type="hidden"
				   id="codeID"/>
			<input name="codeDisplay" style="display:inline; width:94%;" disabled="disabled"
				   class="form-control" type="text" id="codeDisplay"/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">客户状态:</label>

		<div class="col-lg-6">
			<div class="btn-group select" id="statusID"></div>
			<input type="hidden" id="statusID_" name="status"/>
			    </div>
	</div>

		           <div class="form-group">
                    <label class="col-lg-3 control-label">中文名称:</label>

					   <div class="col-lg-6">
						   <input name="chineseName" style="display:inline; width:94%;" class="form-control" type="text"
								  dataType="Require" id="chineseNameID"/>
						   <span class="required">*</span>
					   </div>
	              </div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">英文名称:</label>

		<div class="col-lg-6">
			<input name="englishName" style="display:inline; width:94%;" class="form-control" type="text"
				   dataType="Require" id="englishNameID"/>
			<span class="required">*</span>
		</div>
	</div>

	<div class="form-group">
                    <label class="col-lg-3 control-label">公司电话:</label>

		<div class="col-lg-6">
                           <input name="phone" style="display:inline; width:94%;" class="form-control"  type="text"  dataType="TelePhone" require="false" id="phoneID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">客户地址1:</label>

					   <div class="col-lg-6">
                           <input name="address1" style="display:inline; width:94%;" class="form-control"  type="text"  id="address1ID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">客户地址2:</label>

					   <div class="col-lg-6">
                           <input name="address2" style="display:inline; width:94%;" class="form-control"  type="text"  id="address2ID" />
			    </div>
	</div>
		           <div class="form-group">
                    <label class="col-lg-3 control-label">客户&nbsp;logo:</label>
	                    <div class="col-lg-6">
                         <input name="logoUpload"  class="form-control"  type="file"  id="logoUpload" />                        
                         <div><img id="ImgPr" alt="客户logo" style="border:1px solid transparent; width:100%; height:100%;margin-top:10px;"/></div>
						</div>
			       </div>

</form>
<script type="text/javascript">
	var date = new Date();
	var year = date.getFullYear();
 </script>
</body>
</html>