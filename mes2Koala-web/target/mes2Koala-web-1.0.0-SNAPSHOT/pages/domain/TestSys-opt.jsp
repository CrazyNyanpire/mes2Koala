<%--
  Created by IntelliJ IDEA.
  User: sherrywan
  Date: 2016/5/29 0029
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <%@ page import="java.util.Date" %>
    <% String formId = "form_" + new Date().getTime();
        String gridId = "grid_" + new Date().getTime();
        String path = request.getContextPath() + request.getServletPath().substring(0, request.getServletPath().lastIndexOf("/") + 1);
    %>
</head>
<body>
<div class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">选择测试机台</h4>
            </div>
            <div class="modal-body">
                <div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
                    <!-- search form -->
                    <form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="col-lg-1">
                                <td class="col-lg-6">
                                    <div class="form-group ">
                                        <label class="control-label" style="width:100px;float:left;">platformNumber:&nbsp;</label>
                                        <div style="margin-left:15px;float:left;">
                                            <input name="platformNumber" class="form-control" type="text"
                                                   style="width:200px;" id="platformNumberID"/>
                                        </div>
                                    </div>
                                </td>
                                <td class="col-lg-3" style="vertical-align: bottom;">
                                    <button id="search" type="button"
                                            style="position:relative; margin-left:35px; top: -15px"
                                            class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <!-- grid -->
                    <div id="testSysGridId"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success" id="confirm">确定</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>