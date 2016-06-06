<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal row">
    <!-- 以下包含customerLotDTO内容，id命名规则为customerLotDTO + [memberName] + ID ref:customerLotFTLot-list.jsp
        经尝试，id名 中间加点号"."无法寻找到相应的控件 Howard-->
    <input type="hidden" id="idID" name="id" />
    <input type="hidden" id="versionID" name="version" />
    <strong style="margin-bottom: 1%;display: block;">母批:</strong>
		<div class="col-lg-12">
            <table class="table table-condensed table-center WAFERINFO" style="margin-top:10px;" border="3px" cellspacing="1"  bordercolor="#00CCCC">
                <tr  border="1px" cellspacing="0"  bordercolor="#00CCCC">
                    <th>批号</th><th>状态</th><th>出货型号</th><th>数量</th>
                    <th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th>
                    <th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th>
                    <th>13</th><th>14</th><th>15</th><th>16</th><th>17</th><th>18</th>
                    <th>19</th><th>20</th><th>21</th><th>22</th><th>23</th><th>24</th><th>25</th>
                </tr>
            	<tr id="checkboxID1"   border="1px" cellspacing="0"  bordercolor="#00CCCC" style="background-color: #CFF;">
            	    <th id="internalLotNumber"></th>
            	    <th id="currentState"></th>
            	    <th id="shipmentProductNumber"></th>
            	    <th id="qty"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-01"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-02"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-03"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-04"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-05"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-06"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-07"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-08"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-09"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-10"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-11"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-12"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-13"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-14"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-15"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-16"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-17"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-18"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-19"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-20"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-21"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-22"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-23"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-24"></th>
                    <th><input type="checkbox" disabled = true id="motherlot-25"></th>
            	</tr>
            	<tr border="1px" cellspacing="0"  bordercolor="#00CCCC" style="background-color: #CFF;">
            	    <th></th>
            	    <th></th>
            	    <th></th>
            	    <th></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
                    <th><div style="background-color: #999;height: 15px;width: 15px;border-radius: 50%;"></th>
            	</tr>
            </table>
        </div>
        <strong style="margin-bottom: 2%;display: block;">子批批号:</strong>
		<div class="col-lg-12" >
		    <div class="col-lg-6" style="width: 280px;overflow:auto">
			    <div class="col-lg-4" style="padding-left: 0;text-align: right;" id="childrenLotNumberSpan">
			    </div>
			    <div class="col-lg-8" style="padding:0;text-align: left;" id="childrenLotNumberText">
			    </div>
			</div>
			<div class="col-lg-6" style="width: 280px;overflow:auto">
			    <div class="col-lg-4" style="padding-left: 0;text-align: right;" id="childrenShipmentNumberSpan">
			    </div>
			    <div class="col-lg-8" style="padding:0;text-align: left;" id="childrenShipmentNumberText">
			    </div>
			</div>
			<div class="col-lg-6" style="width: 320px">
				<div class="col-lg-5" style="padding:0;text-align: right;padding-right: 1%;">
					<p style="margin-top: 5%;"><span>分批数量:</span></p>
			    </div>
			    <div class="col-lg-7" style="padding:0;">
                    <p><input name="divideAmount" style="display:inline; width:94%;" class="form-control" type="number" min="2" id="divideAmountID" value="2"></p>
                    <p><input name="splitButton" id="splitID" type="button" style="margin-bottom: 2%;float:right;margin-right: 5%" class="btn btn-primary" value="分批"></p>
				</div>
			</div>
		</div>
        <strong style="margin-bottom: 1%;display: block;">子批:</strong>
		<div class="col-lg-12" id="childrenLot">
		  <table class="table table-condensed table-center CHILDRENLOTINFO" style="margin-top:10px;" border="3px" cellspacing="1" bordercolor="#00CCCC">
		    <tr border="1px" cellspacing="0"  bordercolor="#00CCCC">
                <th>批号</th><th>状态</th><th>出货型号</th><th>数量</th>
                <th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th>
                <th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th>
                <th>13</th><th>14</th><th>15</th><th>16</th><th>17</th><th>18</th>
                <th>19</th><th>20</th><th>21</th><th>22</th><th>23</th><th>24</th><th>25</th>
            </tr>
          </table> 
        </div>
</form>
</body>
</html>