<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        .userGrantAuthority {
            display: block;
            text-align: center;
        }

        .left, .middle, .right{
            display:inline-block;
            font-size:14px;
            vertical-align:middle;
        }

        .left{
            width:300px;
        }

        .middle{
            width:80px;
            text-align:center;
        }

        .right{
            width:300px;
            margin-left:-30px;
            margin-top:-60px;
        }

    </style>
</head>
<body>
<form class="form-horizontal">
    <div class="form-group" id="serachItems">
        <label class="col-lg-2 control-label">艾科批号:</label>
        <div class="col-lg-3">
            <input name="lotNumber" style="display:inline; width:94%;" class="form-control"  type="text" />
        </div>
        <label class="col-lg-2 control-label" style="margin-left:-30px;">版本型号:</label>
        <div class="col-lg-3">
            <input name="productVersion" style="display:inline; width:94%;" class="form-control"  type="text"  />
        </div>
      <button type="button" class="btn btn-primary" id="searchFTLot">查询</button>
    </div>
    <div class="userGrantAuthority modal-body">
        <div class="left">
            <div class="right-modal-body" style="font-size:150%;font-weight: bold;">选择批次</div>
            <div id="notGrantAuthoritiesToUserGrid"></div>
        </div>
        <div class="middle">
            <button type="button" class="btn btn-success glyphicon glyphicon-chevron-right" id="grantAuthorityToUserButton">&nbsp;合并</button>
            <br/><br/><br/><br/>
            <div id="grantAuthorityToUserMessage"></div>
        </div>
        <div class="right">
            <div class="left-modal-body" style="font-size:150%;font-weight: bold;" >合并对象</div>
            <div id="grantAuthoritiesToUserGrid" style="margin-left:50px">
                <table class="table table-responsive table-bordered table-hover table-striped">
                    <tr>
                        <th>艾科批号</th>
                        <th>来料数量</th>
                    </tr>
                    <tr>
                        <td style="height:35px;" id="td1FT"></td>
                        <td style="height:35px;" id="td2FT"></td>
                    </tr>
                    </table>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
</script>
</body>
</html>