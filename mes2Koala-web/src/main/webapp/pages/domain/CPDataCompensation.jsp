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
         debugger;
   	     if ("VI" != filename.substring(filename.lastIndexOf(".")+1) && 
   	    	 "vi" != filename.substring(filename.lastIndexOf(".")+1)  ) {
   	    	grid.message({
					type: 'error',
					content: '上传文件类型错误'
				})
		    return;
   	     }
   	     $("#productRequireID").val(filename);
 		 var fd = new FormData();
 		 fd.append('file', $('#inputfilebtnID')[0].files[0]);
 		 $.ajax({
 			type: 'post',
 			url: '${pageContext.request.contextPath}/CPLot/uploadCPDataCompensationFile.koala',
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
		<div class="col-lg-3" style="padding:0;text-align: right;">
			<p><span>LotNo:</span></p>
			<p><span>产品型号:</span></p>
			<p><span>客户名称:</span></p>
		</div>
		<div class="col-lg-3" style="padding-right:0">
			<p><span style="white-space: nowrap;"id="internalLotNumberID"></span></p>
			<p><span id="shipmentProductNumberID"></span></p>
			<p><span id="customerNumberID"></span></p>
		</div>
	</div>
    <div class="form-group">
        <label class="col-lg-3 control-label">上传文件:</label>
        <div class="col-lg-6">
            <input name="productRequire" style="display:inline; width:75%;" class="form-control" type="text"
                   id="productRequireID" disabled="true"/>
            <input type="file" name="inputfilebtn" id="inputfilebtnID" style="display:none" /> 
            <input name="productRequireBtn" class="btn btn-default" type="button" 
                   value="选择" id="productRequireBtnID" onclick="inputfile();"/>
        </div>
    </div>
</form>
<script type="text/javascript">
</script>
</body>
</html>