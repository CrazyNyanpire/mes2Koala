<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal row">
    <div class="col-lg-12">
		<strong style="margin-bottom: 2%;display: block;">基础信息:</strong>
		<div class="col-lg-12">
			<div class="col-lg-6" style="padding-left: 0;">
				<div class="col-lg-4" style="padding:0;text-align: right;">
					<p><span>客户批号:</span></p>
					<p><span>客户编号:</span></p>
					<p><span>内部批号:</span></p>
					<p><span>产品型号:</span></p>
				</div>
				<div class="col-lg-8" style="padding-right:0">
					<p><span style="white-space: nowrap;"id="customerLotNumberID"></span></p>
					<p><span id="customerNumberID"></span></p>
					<p><span id="internalLotNumberID"></span></p>
					<p><span id="shipmentProductNumberID"></span></p>
				</div>
			</div>
		</div>
    </div>
	<div class="col-lg-12">
	    <strong style="margin-bottom: 2%;display: block;">Wafer信息:</strong>
		<div class="col-lg-9" style="height: 215px; width: 530px;overflow:auto">
            <table class="table table-condensed table-center QDNINFO" style="margin-top:10px;" border="3px" cellspacing="1"  bordercolor="#00CCCC">
                <tr id="img" style='background-color: #CFF;' border='1px' cellspacing='0'  bordercolor='#00CCCC'>
                    <th></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="1#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="2#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="3#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="4#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="5#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="6#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="7#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="8#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="9#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="10#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="11#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="12#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="13#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="14#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="15#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="16#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="17#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="18#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="19#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="20#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="21#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="22#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="23#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="24#"></div></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;" name="25#"></div></th>
                </tr>
                <tr style='background-color: #CFF;' border='1px' cellspacing='0'  bordercolor='#00CCCC'>
                    <th></th><th>1#</th><th>2#</th><th>3#</th><th>4#</th><th>5#</th><th>6#</th>
                    <th>7#</th><th>8#</th><th>9#</th><th>10#</th><th>11#</th><th>12#</th>
                    <th>13#</th><th>14#</th><th>15#</th><th>16#</th><th>17#</th><th>18#</th><th>19#</th>
                    <th>20#</th><th>21#</th><th>22#</th><th>23#</th><th>24#</th><th>25#</th>
                </tr>
                <tr id="onoffhold" style='background-color: #CFF;' border='1px' cellspacing='0'  bordercolor='#00CCCC'>
                    <th>开解Hold</th><th>开</th><th>开</th><th>开</th><th>开</th><th>开</th><th>开</th>
                    <th>开</th><th>开</th><th>开</th><th>开</th><th>开</th><th>开</th>
                    <th>开</th><th>开</th><th>开</th><th>开</th><th>开</th><th>开</th><th>开</th>
                    <th>开</th><th>开</th><th>开</th><th>开</th><th>开</th><th>开</th>
                </tr>
            	<tr id="select" style='background-color: #CFF;' border='1px' cellspacing='0'  bordercolor='#00CCCC'>
            	    <th>选择</th>
                    <th><input type="checkbox" name="select1"></th>
                    <th><input type="checkbox" name="select2"></th>
                    <th><input type="checkbox" name="select3"></th>
                    <th><input type="checkbox" name="select4"></th>
                    <th><input type="checkbox" name="select5"></th>
                    <th><input type="checkbox" name="select6"></th>
                    <th><input type="checkbox" name="select7"></th>
                    <th><input type="checkbox" name="select8"></th>
                    <th><input type="checkbox" name="select9"></th>
                    <th><input type="checkbox" name="select10"></th>
                    <th><input type="checkbox" name="select11"></th>
                    <th><input type="checkbox" name="select12"></th>
                    <th><input type="checkbox" name="select13"></th>
                    <th><input type="checkbox" name="select14"></th>
                    <th><input type="checkbox" name="select15"></th>
                    <th><input type="checkbox" name="select16"></th>
                    <th><input type="checkbox" name="select17"></th>
                    <th><input type="checkbox" name="select18"></th>
                    <th><input type="checkbox" name="select19"></th>
                    <th><input type="checkbox" name="select20"></th>
                    <th><input type="checkbox" name="select21"></th>
                    <th><input type="checkbox" name="select22"></th>
                    <th><input type="checkbox" name="select23"></th>
                    <th><input type="checkbox" name="select24"></th>
                    <th><input type="checkbox" name="select25"></th>
                 </tr>
                 <tr id="rework" style='background-color: #CFF;' border='1px' cellspacing='0'  bordercolor='#00CCCC'>
            	    <th>Rework</th>
                    <th><input type="checkbox" name="rework1"></th>
                    <th><input type="checkbox" name="rework2"></th>
                    <th><input type="checkbox" name="rework3"></th>
                    <th><input type="checkbox" name="rework4"></th>
                    <th><input type="checkbox" name="rework5"></th>
                    <th><input type="checkbox" name="rework6"></th>
                    <th><input type="checkbox" name="rework7"></th>
                    <th><input type="checkbox" name="rework8"></th>
                    <th><input type="checkbox" name="rework9"></th>
                    <th><input type="checkbox" name="rework10"></th>
                    <th><input type="checkbox" name="rework11"></th>
                    <th><input type="checkbox" name="rework12"></th>
                    <th><input type="checkbox" name="rework13"></th>
                    <th><input type="checkbox" name="rework14"></th>
                    <th><input type="checkbox" name="rework15"></th>
                    <th><input type="checkbox" name="rework16"></th>
                    <th><input type="checkbox" name="rework17"></th>
                    <th><input type="checkbox" name="rework18"></th>
                    <th><input type="checkbox" name="rework19"></th>
                    <th><input type="checkbox" name="rework20"></th>
                    <th><input type="checkbox" name="rework21"></th>
                    <th><input type="checkbox" name="rework22"></th>
                    <th><input type="checkbox" name="rework23"></th>
                    <th><input type="checkbox" name="rework24"></th>
                    <th><input type="checkbox" name="rework25"></th>
                 </tr>
            </table>
        </div>
        <div class="col-lg-9">
			<input name="toggleSelectBtn" class="btn btn-primary" type="button" 
                   value="反选" id="toggleSelectBtnBtnID"/>
		</div>
    </div>
    <div class="col-lg-12">
        <label class="col-lg-3 control-label" style="text-align: left;">解Hold备注</label>
		<div class="col-lg-12">
			<textarea name="remark" style="display:inline; width:94%;" class="form-control" rows="3" type="text" id="HoldUnLockRemarkID" ></textarea>
		</div>
	</div>
</form>
<script type="text/javascript">
</script>
</body>
</html>