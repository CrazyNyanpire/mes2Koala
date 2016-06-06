<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<div class="form-group">
		<div class="col-lg-3 col-lg-offset-2">
			<div  class="checkbox">
				<label>
					<input name="QDNNumber"  type="checkbox" id="QDNNumberID" disabled/>
					首页
				</label>
			</div>
		</div>
		<div class="col-lg-3 col-lg-offset-2">
			<div  class="checkbox">
				<label>
					<input name="QDNNumber"  type="checkbox" id="QDNNumberID" disabled/>
					FLOW
				</label>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="col-lg-3 col-lg-offset-2">
			<div  class="checkbox disabled">
				<label>
					<input name="QDNNumber"  type="checkbox" id="QDNNumberID" />
					Summary
				</label>
			</div>
		</div>
		<div class="col-lg-3 col-lg-offset-2">
			<div  class="checkbox disabled">
				<label>
					<input name="QDNNumber"  type="checkbox" id="QDNNumberID" />
					机台落料表
				</label>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="col-lg-3 col-lg-offset-2">
			<div  class="checkbox disabled">
				<label>
					<input name="QDNNumber"  type="checkbox" id="QDNNumberID" />
					尺寸记录表
				</label>
			</div>
		</div>
		<div class="col-lg-3 col-lg-offset-2">
			<div  class="checkbox disabled">
				<label>
					<input name="QDNNumber"  type="checkbox" id="QDNNumberID" />
					ReelCode
				</label>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="col-lg-3 col-lg-offset-2">
			<div  class="checkbox disabled">
				<label>
					<input name="QDNNumber"  type="checkbox" id="QDNNumberID" />
					自主检查表
				</label>
			</div>
		</div>
		<div class="col-lg-3 col-lg-offset-2">
			<div  class="checkbox disabled">
				<label>
					<input name="QDNNumber"  type="checkbox" id="QDNNumberID" />
					Checkbox
				</label>
			</div>
		</div>
	</div>
	<hr/>
	<div class="form-group">
		<div class="col-lg-10 col-lg-offset-2">
		<div style="margin-bottom:15px">
		    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" >
		   	 从起始站点开始列印
		</div>
		<div style="margin-bottom:15px">
		    <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
			从生产批目前作业站开始列印
		</div>
		<div style="margin-bottom:15px">
		    <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3">
			只列印目前作业站
		</div>
		</div>
	</div>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>