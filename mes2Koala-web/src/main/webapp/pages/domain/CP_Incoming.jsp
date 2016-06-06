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
    <div class="form-group" style="margin-top:20px;">
        <label class="col-lg-2 control-label">客户批号:</label>
        <div class="col-lg-3">
            <input name="customerLotNumber"  disabled style="display:inline; width:94%;" class="form-control"  type="text"  id="customerLotNumberID" />
        </div>
    </div>
    <div class="form-group" style="margin-top:20px;">
        <label class="col-lg-2 control-label">内部批号:</label>
        <div class="col-lg-3">
            <input name="internalLotNumber" disabled style="display:inline; width:94%;" class="form-control"  type="text"  id="internalLotNumberID" />
        </div>
    </div>
    <div class="form-group" style="margin-top:20px;">
        <label class="col-lg-2 control-label">封装厂批号:</label>
        <div class="col-lg-3">
            <input name="packageNumber" disabled style="display:inline; width:94%;" class="form-control"  type="text"  id="packageNumberID" />
        </div>
    </div>
    <div class="form-group" style="margin-top:20px;">
        <label class="col-lg-2 control-label">客户编号:</label>
        <div class="col-lg-3">
            <input name="customerNumber" disabled style="display:inline; width:94%;" class="form-control"  type="text"  id="customerNumberID" />
        </div>
    </div>
    <div class="form-group" style="margin-top:20px;">
        <label class="col-lg-2 control-label">产品编号:</label>
        <div class="col-lg-3">
            <input name="customerProductNumber" disabled style="display:inline; width:94%;" class="form-control"  type="text"  id="customerProductNumberID" />
        </div>
    </div>
    <div class="form-group" style="margin-top:20px;">
        <label class="col-lg-2 control-label">客户型号:</label>
        <div class="col-lg-3">
            <input name="customerProductNumber" disabled style="display:inline; width:94%;" class="form-control"  type="text"  id="customerProductNumberID" />
        </div>
    </div>
    <div class="form-group" style="margin-top:20px;">
        <label class="col-lg-2 control-label">出货型号:</label>
        <div class="col-lg-3">
            <input name="shipmentProductNumber" disabled style="display:inline; width:94%;" class="form-control"  type="text"  id="shipmentProductNumberID" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-lg-2 control-label">片号:</label>
        <div class="col-lg-8">
        	<table class="table">
            <tr><td>01</td>
				<td>02</td>
				<td>03</td>
				<td>04</td>
				<td>05</td>
				<td>06</td>
				<td>07</td>
				<td>08</td>
				<td>09</td>
				<td>10</td>
				<td>11</td>
				<td>12</td>
				<td>13</td>
				<td>14</td>
				<td>15</td>
				<td>16</td>
				<td>17</td>
				<td>18</td>
				<td>19</td>
				<td>20</td>
				<td>21</td>
				<td>22</td>
				<td>23</td>
				<td>24</td>
				<td>25</td>
			</tr>
			<tr>
				<td><input type="checkbox" class="checkItem01"></td>
				<td><input type="checkbox" class="checkItem02"></td>
				<td><input type="checkbox" class="checkItem03"></td>
				<td><input type="checkbox" class="checkItem04"></td>
				<td><input type="checkbox" class="checkItem05"></td>
				<td><input type="checkbox" class="checkItem06"></td>
				<td><input type="checkbox" class="checkItem07"></td>
				<td><input type="checkbox" class="checkItem08"></td>
				<td><input type="checkbox" class="checkItem09"></td>
				<td><input type="checkbox" class="checkItem10"></td>
				<td><input type="checkbox" class="checkItem11"></td>
				<td><input type="checkbox" class="checkItem12"></td>
				<td><input type="checkbox" class="checkItem13"></td>
				<td><input type="checkbox" class="checkItem14"></td>
				<td><input type="checkbox" class="checkItem15"></td>
				<td><input type="checkbox" class="checkItem16"></td>
				<td><input type="checkbox" class="checkItem17"></td>
				<td><input type="checkbox" class="checkItem18"></td>
				<td><input type="checkbox" class="checkItem19"></td>
				<td><input type="checkbox" class="checkItem20"></td>
				<td><input type="checkbox" class="checkItem21"></td>
				<td><input type="checkbox" class="checkItem22"></td>
				<td><input type="checkbox" class="checkItem23"></td>
				<td><input type="checkbox" class="checkItem24"></td>
				<td><input type="checkbox" class="checkItem25"></td>
			</tr>
          	</table>
        </div>
        <div class="col-lg-2"><button type="button" id="InvertButton" class="btn btn-default">反选</button></div>
        <div class="col-lg-10 col-lg-offset-1">
        <table class="table center">
        	<tr>
        		<td><span>1</span></td>
				<td><input type="text" name="internalWaferCode1"  class="form-control waferId01"></td>
				<td><span>2</span></td>
				<td><input type="text" name="internalWaferCode2"  class="form-control waferId02"></td>
				<td><span>3</span></td>
				<td><input type="text" name="internalWaferCode3"  class="form-control waferId03"></td>
				<td><span>4</span></td>
				<td><input type="text" name="internalWaferCode4"  class="form-control waferId04"></td>
				<td><span>5</span></td>
				<td><input type="text" name="internalWaferCode5"  class="form-control waferId05"></td>
			</tr>
			<tr>
				<td><span>6</span></td>
				<td><input type="text" name="internalWaferCode6"  class="form-control waferId06"></td>
				<td><span>7</span></td>
				<td><input type="text" name="internalWaferCode7"  class="form-control waferId07"></td>
				<td><span>8</span></td>
				<td><input type="text" name="internalWaferCode8"  class="form-control waferId08"></td>
				<td><span>9</span></td>
				<td><input type="text" name="internalWaferCode9"  class="form-control waferId09"></td>
				<td><span>10</span></td>
				<td><input type="text" name="internalWaferCode10"  class="form-control waferId10"></td>
			</tr>
			<tr>
				<td><span>11</span></td>
				<td><input type="text" name="internalWaferCode11"  class="form-control waferId11"></td>
				<td><span>12</span></td>
				<td><input type="text" name="internalWaferCode12"  class="form-control waferId12"></td>
				<td><span>13</span></td>
				<td><input type="text" name="internalWaferCode13"  class="form-control waferId13"></td>
				<td><span>14</span></td>
				<td><input type="text" name="internalWaferCode14"  class="form-control waferId14"></td>
				<td><span>15</span></td>
				<td><input type="text" name="internalWaferCode15"  class="form-control waferId15"></td>
			</tr>
			<tr>
				<td><span>16</span></td>
				<td><input type="text" name="internalWaferCode16"  class="form-control waferId16"></td>
				<td><span>17</span></td>
				<td><input type="text" name="internalWaferCode17"  class="form-control waferId17"></td>
				<td><span>18</span></td>
				<td><input type="text" name="internalWaferCode18"  class="form-control waferId18"></td>
				<td><span>19</span></td>
				<td><input type="text" name="internalWaferCode19"  class="form-control waferId19"></td>
				<td><span>20</span></td>
				<td><input type="text" name="internalWaferCode20"  class="form-control waferId20"></td>
			</tr>
			<tr>
				<td><span>21</span></td>
				<td><input type="text" name="internalWaferCode21"  class="form-control waferId21"></td>
				<td><span>22</span></td>
				<td><input type="text" name="internalWaferCode22"  class="form-control waferId22"></td>
				<td><span>23</span></td>
				<td><input type="text" name="internalWaferCode23"  class="form-control waferId23"></td>
				<td><span>24</span></td>
				<td><input type="text" name="internalWaferCode24"  class="form-control waferId24"></td>
				<td><span>25</span></td>
				<td><input type="text" name="internalWaferCode25"  class="form-control waferId25"></td>
			</tr>
        </table>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-2 control-label">数量:</label>
        <div class="col-lg-3">
            <input name="quantity" disabled style="display:inline; width:94%;" class="form-control"  type="text"  id="quantityID" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">MASK NAME:</label>
        <div class="col-lg-3">
            <input name="maskName" disabled style="display:inline; width:94%;" class="form-control"  type="text"  id="maskNameID" />
        </div>
    </div>
</form>
 <div class="form-inline" id="saveIncoming">
<button type="button" class="btn btn-default" style="margin-left: 300px;">进站</button>
<button type="button" class="btn btn-default hidden" style="margin-left: 300px;background-color:#fff;cursor: auto;border: none;"></button>
<button type="button" class="btn btn-default hidden" style="margin-left: 10px;">出站</button>
</div>
</div>
<script type="text/javascript">
    var selectItems = {};
    $("#InvertButton").bind("click",function(){
    	$("[class^='checkItem']").prop("checked", function(i,val) {
    		debugger;
    		if($(this).prop("checked"))
       		{
       			$(".waferId"+$(this).attr("class").replace(/[^0-9]/ig,"")).val("");
       		}
    		return !val;
    	});
    })
    $("[class^='checkItem']").bind("change",function(){
    	debugger;
    	if(!$(this).prop("checked"))
   		{
   			$(".waferId"+$(this).attr("class").replace(/[^0-9]/ig,"")).val("");
   		}
    })
</script>
</body>
</html>