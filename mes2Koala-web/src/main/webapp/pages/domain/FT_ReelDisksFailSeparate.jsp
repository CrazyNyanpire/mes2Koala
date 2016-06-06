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
    <div class="form-horizontal">
        <div class="form-group">
            <div class="col-lg-12">
                <table id="tableFailReelDisk" class="table">
                    <thead>
                    <tr>

                    </tr>
                    </thead>
                    <tbody>
                    <tr>

                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="form-horizontal">
        <div class="form-group">
            <div class="col-lg-8">
            </div>
            <div class="col-lg-2">
                <select>
                    <option value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>
            </div>
            <div class="col-lg-2">
                <button class="btn btn-primary" type="button" id="buttonSeparateFailReelDisk">分盘</button>
            </div>
        </div>
    </div>
    <div class="form-horizontal">
        <div class="form-group">
            <div class="col-lg-12">
                <table id="tableSeparatedFailReelDisks" class="table">
                    <thead>

                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="form-horizontal">
        <div class="form-group">
            <div class="col-lg-12 center" >
                <textarea class="form-control" rows="3" placeholder="备注"></textarea>
            </div>
        </div>
    </div>
</form>
</body>
</html>