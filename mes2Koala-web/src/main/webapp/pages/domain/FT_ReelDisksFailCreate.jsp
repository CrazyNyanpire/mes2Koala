<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        .td {
            width: 50px;
        }
    </style>
</head>
<body>
<form class="form-horizontal">
    <input type="text" hidden name="ftLotDTO.id" id="ftLotDTO_id">
    <div class="form-group">
        <label class="col-lg-3 control-label">Fail品箱数:</label>
        <div class="col-lg-6">
            <select name="failInfo">
                <option value='1'>1</option>
                <option value='2'>2</option>
                <option value='3'>3</option>
                <option value='4'>4</option>
                <option value='5'>5</option>
                <option value='6'>6</option>
                <option value='7'>7</option>
                <option value='8'>8</option>
                <option value='9'>9</option>
                <option value='10'>10</option>
                <option value='11'>11</option>
                <option value='12'>12</option>
                <option value='13'>13</option>
                <option value='14'>14</option>
                <option value='15'>15</option>
                <option value='16'>16</option>
                <option value='17'>17</option>
                <option value='18'>18</option>
                <option value='19'>19</option>
                <option value='20'>20</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-3 control-label" id="labelName">第1箱</label>
        <div class="col-lg-9">
            <table>
                <tr>
                    <th>other</th>
                    <th>loss</th>
                    <th hidden>bin</th>
                </tr>
                <tr>
                    <td><input type="text" class="td" name="ftResultDTO.loss"></td>
                    <td><input type="text" class="td" name="ftResultDTO.other"></td>
                    <td hidden><input type="text" name="ftResultDTO.bin" id="ftResultDTO_bin"></td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>