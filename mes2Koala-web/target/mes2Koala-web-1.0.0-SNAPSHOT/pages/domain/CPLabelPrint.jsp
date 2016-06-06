<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<object class="hidden" id="activeX" classid="clsid:8738EBCB-92B1-4dcb-8E86-A4703EBD191E"> </object>
<form class="form-horizontal">
<input  type="text" class="hidden" id="labelNameID" />
	<div class="form-group">
		<div class="form-group">
	        <label class="col-lg-2 control-label">出货型号:</label>
			<div class="col-lg-4">
				<input name="lblProductType" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" readonly id="productNoID" />
			</div>
	        <label class="col-lg-2 control-label">PPO:</label>
			<div class="col-lg-4">
				<input name="lblppo" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" readonly  id="ppoID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-2 control-label">客户编号:</label>
			<div class="col-lg-4">
				<input name="customerNo" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" readonly  id="customerNoID" />
			</div>
	        <label class="col-lg-2 control-label">LOT NO:</label>
			<div class="col-lg-4">
				<input name="lblLot" style="display:inline; width:94%;" class="form-control"  type="text" dataType="Require" readonly  id="cpLotNoID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-2 control-label">客户批号:</label>
			<div class="col-lg-4">
				<input name="lblClientLot" style="display:inline; width:94%;" class="form-control sum" type="text" dataType="Require" readonly id="internalLotNumberID" />
			</div>
			<label class="col-lg-2 control-label">客户名称:</label>
			<div class="col-lg-4">
				<input name="lblClient" style="display:inline; width:94%;" class="form-control sum" type="text" dataType="Require" readonly id="warningTypeID" />
			</div>
		</div>
		<div class="form-group">
	        <label class="col-lg-2 control-label">Good Die:</label>
			<div class="col-lg-4">
				<input name="lblgoodDie" style="display:inline; width:94%;" class="form-control sum" type="text" readonly id="waferSizeID" />
			</div>
	        <label class="col-lg-2 control-label">GROSS DIE:</label>
			<div class="col-lg-4">
				<input name="lblgrossDie" style="display:inline; width:94%;" class="form-control sum" type="text" dataType="Require" readonly id="grossDieID" />
			</div>
		</div>
		<div class="form-group">
        <label class="col-lg-2 control-label">片号:</label>
        </div>
        <div class="col-lg-12">
        	<table class="table">
			<tr>
				<td><input type="checkbox" index="lblAll" class="checkItemAll"></td><td>All</td>
				<td><input type="checkbox" index="lbl1" class="checkItem01"></td><td>01</td>
				<td><input type="checkbox" index="lbl2" class="checkItem02"></td><td>02</td>
				<td><input type="checkbox" index="lbl3" class="checkItem03"></td><td>03</td>
				<td><input type="checkbox" index="lbl4" class="checkItem04"></td><td>04</td>
				<td><input type="checkbox" index="lbl5" class="checkItem05"></td><td>05</td>
				<td><input type="checkbox" index="lbl6" class="checkItem06"></td><td>06</td>
				<td><input type="checkbox" index="lbl7" class="checkItem07"></td><td>07</td>
				<td><input type="checkbox" index="lbl8" class="checkItem08"></td><td>08</td>
				<td><input type="checkbox" index="lbl9" class="checkItem09"></td><td>09</td>
				<td><input type="checkbox" index="lbl10" class="checkItem10"></td><td>10</td>
				<td><input type="checkbox" index="lbl11" class="checkItem11"></td><td>11</td>
				<td><input type="checkbox" index="lbl12" class="checkItem12"></td><td>12</td>
			</tr>
			<tr>
				<td><input type="checkbox" index="lbl13" class="checkItem13"></td><td>13</td>
				<td><input type="checkbox" index="lbl14" class="checkItem14"></td><td>14</td>
				<td><input type="checkbox" index="lbl15" class="checkItem15"></td><td>15</td>
				<td><input type="checkbox" index="lbl16" class="checkItem16"></td><td>16</td>
				<td><input type="checkbox" index="lbl17" class="checkItem17"></td><td>17</td>
				<td><input type="checkbox" index="lbl18" class="checkItem18"></td><td>18</td>
				<td><input type="checkbox" index="lbl19" class="checkItem19"></td><td>19</td>
				<td><input type="checkbox" index="lbl20" class="checkItem20"></td><td>20</td>
				<td><input type="checkbox" index="lbl21" class="checkItem21"></td><td>21</td>
				<td><input type="checkbox" index="lbl22" class="checkItem22"></td><td>22</td>
				<td><input type="checkbox" index="lbl23" class="checkItem23"></td><td>23</td>
				<td><input type="checkbox" index="lbl24" class="checkItem24"></td><td>24</td>
				<td><input type="checkbox" index="lbl25" class="checkItem25"></td><td>25</td>
			</tr>
          	</table>
        </div>
</form>
<script type="text/javascript">
    function onPrint(path,labeInfo)
	{
		//var parameter = "COUNTRY=China";//serializeForm("inputForm");
		var parameter = labeInfo;
		var v = document.getElementById("activeX").Print(path, 1, parameter,true,true);
    	if(v!="1")
    	{
    		alert(v);
    	}
    	else
    	{
    		document.getElementById("activeX").Quit();
    	}
	}
</script>
</body>
</html>