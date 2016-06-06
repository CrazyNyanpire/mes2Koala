<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div>
<form class="form-horizontal" contenteditable="false">
    <div class="hidden">
    <input type="text" id="idID" name="id"/>
    <input type="text" id="nameID" name="name"/>
    <input type="text" id="orderID" name="turn"/>
    <input type="text" id="versionID" name="version"/>
    <input type="text" id="ftStateID" name="state"/>
    <input type="text" id="ftIQCDTO_idID" name="ftIQCDTO.id"/>
    <input type="text" id="ftIQCDTO_versionID" name="ftIQCDTO.version"/>
    </div>
    <fieldset disabled>
    <table class="table table-bordered table-hover">
    	<thead>
    		<tr>
    			<th></th>
    			<th></th>
    			<th>内部WaferID</th>
    			<th>操作/<a id="allopt">全部</a></th>
    			<th>MAP图</th>
    			<th>详情</th>
    			<th>客户WaferID</th>
    			<th>PASS</th>
    			<th>FAIL</th>
    			<th>内部补偿</th>
    			<th>外部补偿</th>
    		</tr>
    	</thead>
    	<tbody>
    		
    	</tbody>
    </table>
    </fieldset>
</form>
<div class="form-inline" id="saveIQC" style="margin-bottom:15px">
<button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
<button type="button" class="btn btn-default hidden" style="margin-left: 300px;background-color:#fff;cursor: auto;border: none;"></button>
<button type="button" class="btn btn-default hidden" style="margin-left: 10px;">出站</button>
</div>
</div>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>