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
    <strong style="margin-bottom: 1%;display: block;">子批:</strong>
		<div class="col-lg-12">
            <table class="table table-condensed table-center ChildsINFO" style="margin-top:10px;" border="3px" cellspacing="1"  bordercolor="#00CCCC">
            </table>
        </div>
		<div class="col-lg-12" >
			<div class="col-lg-6" style="width: 320px">
			    <div class="col-lg-7" style="padding:0;">
                    <p><input name="mergeButton" id="mergeID" type="button" style="margin-bottom: 2%;float:right;margin-right: 5%" class="btn btn-primary" value="合批"></p>
				</div>
			</div>
		</div>
        <strong style="margin-bottom: 1%;display: block;">母批:</strong>
		<div class="col-lg-12" id="childrenLot">
		  <table class="table table-condensed table-center MotherLOTINFO" style="margin-top:10px;" border="3px" cellspacing="1" bordercolor="#00CCCC">
		    <tr border="1px" cellspacing="0"  bordercolor="#00CCCC">
                <th>批号</th><th>状态</th><th>数量</th>
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