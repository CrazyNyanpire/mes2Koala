<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
    <div class="form-group">
        <input type="hidden" id="idID" name="id"/>
        <input type="hidden" id="versionID" name="version"/>
        <div class="form-group">
            <label class="col-lg-3 control-label">测试类型</label>
            <div class="col-lg-9">
                <div class="btn-group select" id="testTypeID"></div>
                <input name="testType" type="hidden" id="testTypeID_"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">客户产品型号:</label>
            <div class="col-lg-9">
                <div class="btn-group select" id="internalProductDTOID"></div>
                <input name="internalProductDTO.id" type="hidden" id="internalProductDTOID_"
                />
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">版本型号:</label>
            <div class="col-lg-6">
                <input name="productVersion" style="display:inline; width:94%;" readonly class="form-control"
                       type="text" id="productVersionID"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">测试程序:</label>
            <div class="col-lg-6">
                <input name="name" style="display:inline; width:94%;" class="form-control" type="text" id="nameID"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">程序版本:</label>
            <div class="col-lg-6">
                <input name="revision" style="display:inline; width:94%;" class="form-control" type="text"
                       id="revisionID"/>
            </div>
        </div>
        <!--   <div class="form-group">
              <label class="col-lg-3 control-label">测试机台:</label>
              <div class="col-lg-6">
                  <input name="testSys" style="display:inline; width:94%;" class="form-control"  type="text"  id="testSysID" />
              </div>
          </div> -->
        <div class="form-group">
            <label class="col-lg-3 control-label">测试机台:</label>
            <div class="col-lg-6">
                <textarea class="form-control" id="testSysID" name="testSys" style="width:94%"></textarea>
            </div>
            <div class="col-lg-3">
                <input type="button" class="btn btn-default" id="testSysID_" value="请选择"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">备注:</label>
            <div class="col-lg-6">
                <input name="note" style="display:inline; width:94%;" class="form-control" type="text" id="noteID"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">授权人:</label>
            <div class="col-lg-6">
                <button type="button" id="acetecAuthorizationIdsID" class="btn btn-default mes2">选择授权人</button>
                <input type="text" style="display:none" name="acetecAuthorizationIds" id="acetecAuthorizationID"
                       dataType="Require">
                <span class="required">*</span>
                <%--       <div class="btn-group select" id="authorizationMemberID">
                        <button data-toggle="item" class="btn btn-default" type="button">
                            选择授权人
                        </button>
                        <button data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button">
                            <span class="caret"></span>
                        </button>
                    </div>--%>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">理论UPH:</label>
            <div class="col-lg-6">
                <input name="UPHTheory" style="display:inline; width:94%;" class="form-control" dataType="Require"
                       type="text" id="uphtheoryID"/>
                <span class="required">*</span>
            </div>
        </div>
        <div class="form-group" style="display:none">
            <label class="col-lg-3 control-label">实际UPH:</label>
            <div class="col-lg-6">
                <input name="UPHReality" style="display:inline; width:94%;" class="form-control" dataType="Require"
                       type="text" id="uphrealityID"/>
                <span class="required">*</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">IS_YELLOW:</label>
            <div class="col-lg-6">
                <div class="btn-group select" id="isYellowID"></div>
                <input type="hidden" id="isYellowID_" name="isYellow" dataType="Require"/>
                <span class="required">*</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 control-label">站点：</label>
            <div class="col-lg-9">
                <div class="btn-group select" id="siteID"></div>
                <input name="site" type="hidden" id="siteID_"/>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    var selectItems = {};
    var contents = [{title: '请选择', value: ''}];
    contents.push({title: 'FT', value: 'FT'});
    contents.push({title: 'CP', value: 'CP'});
    selectItems['testTypeID'] = contents;
</script>
</body>
</html>