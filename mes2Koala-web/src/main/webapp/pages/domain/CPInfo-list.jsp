<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">
        a {
            text-decoration: none;
            color: #000;
        }

        .clear {
            clear: both;
            margin-top: 20px;
            height: 50px;
            line-height: 10px;
        }

        #processtop {
            padding: 15px;
            width: 800px;
            height: 70px;
            line-height: 43px;
            margin-bottom: 10px;
            font-size: 15px;
            font-family: Georgia, "Times New Roman", Times, serif;
        }

        #processBody_CPInfo {
            margin-top: 8px;
            padding: 10px;
            height: 220px;
            overflow: auto;
        }

        #ulfoot {
            margin-top: 10px;
            list-style-type: none;
            display: block inline;
            text-align: center;
            margin-left: -40px;
        }

        .fudong2 {
            border: #CDBECF 1px solid;
            float: left;
            background-color: #fff;
            width: 95px;
            height: 30px;
            line-height: 30px;
            color: #000;
            text-align: center;
        }
    </style>

    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <%@ page import="java.util.Date" %>
    <script type="text/javascript" src="<%=contextPath %>/js/common.js"></script>
    <LINK rel="stylesheet" type="text/css"
          href="<%=contextPath %>/js/easyui/themes/default/easyui.css"/>
    <script type="text/javascript"
            src="<%=contextPath %>/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <%
        String formId = "form_" + new Date().getTime();
        String gridId = "grid_" + new Date().getTime();
        String path = request.getContextPath()
                + request.getServletPath().substring(0,
                request.getServletPath().lastIndexOf("/") + 1);
    %>
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/koala-tree.css"/>
    <script src="<%=contextPath %>/lib/koala-tree.js"></script>
    <script type="text/javascript">
        var grid;
        var form;
        var _dialog;
        var count = 1;
        var count1 = 1;
        var i = 0;
        var j = 0;
        grid = $("#<%=gridId%>");
        form = $("#<%=formId%>");
        var departmentId = [];
        var departmentName = '';
        var personHtml = '';
        var personId = [];
        var memberTree = {};
        /**
         * 加载部门树
         */
        function loadmemberTree(data) {
            memberTree.parent().loader({
                opacity: 0
            });
            memberTree.parent().loader('hide');
            var zNodes = new Array();
            $.each(data, function () {
                var memberId = data['orgTree'].id;
                var zNode = {};
                if (this.organizationType == 'company') {//后期需要修改
                    zNode.type = 'parent';
                } else {
                    zNode.icon = 'glyphicon glyphicon-list-alt'
                }
                this.title = this.name;
                zNode.menu = this;
                if (this.children && this.children.length > 0) {
                    zNode.children = getChildrenData(new Array(), this.children);
                } else if (this.employeeVos && this.employeeVos.length > 0) {
                    zNode.children = getChildrenData(new Array(), this.employeeVos);
                }
                zNodes.push(zNode);
            });
            var dataSourceTree = {
                data: zNodes,
                delay: 400
            };
            memberTree.tree({
                dataSource: dataSourceTree,
                loadingHTML: '<div class="static-loader">Loading...</div>',
                multiSelect: false,
                cacheItems: true
            }).on({
                'selectChildren': function (event, data) {
                    if (departmentId == '') {
                        departmentId.push(data.id);
                        departmentName = data.name;
                    } else {
                        alert('只能选择一位授权人！')
                    }
                }
            });
        }

        function getChildrenData(nodes, items) {
            $.each(items, function () {
                var zNode = {};
                if (this.organizationType == 'company') {
                    zNode.type = 'parent';
                } else {
                    zNode.icon = 'glyphicon glyphicon-list-alt'
                }
                this.title = this.name;
                zNode.menu = this;
                if (this.children && this.children.length > 0) {
                    zNode.children = getChildrenData([], this.children);
                } else if (this.employeeVos && this.employeeVos.length > 0) {
                    zNode.children = getChildrenData(new Array(), this.employeeVos);
                }

                /*else {//不需要这块。。。
                 $.ajax({
                 type: 'get',
                 url: contextPath + '/employee/getEmployee-by-org.koala?organizationId=' + this.id,
                 dataType: 'json',
                 success: function (result) {
                 zNode.children = []
                 $.each(result, function () {
                 var childNode = {};
                 this.title = this.name;
                 childNode.menu = this;
                 childNode.icon = 'glyphicon glyphicon-list-alt';
                 childNode.children = [];
                 zNode.children.push(childNode);
                 });
                 }
                 });
                 }*/
                nodes.push(zNode);
            });
            return nodes;
        }

        function addRawData() {

            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            $.get('${pageContext.request.contextPath}/RawData/getRawData/' + id + '.koala').done(function (json) {
                json = json.data;
                if (json != null) {
                    $(grid).message({
                        type: 'warning',
                        content: '只能添加一条RawData信息'
                    });
                    return;
                }
                var self = this;
                var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                        + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                        + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                        + '<h4 class="modal-title">新增Raw Data</h4></div><div class="modal-body">'
                        + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                        + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                        + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                        + '</div></div>');
                $.get('<%=path%>/CPInfo-RawData-add.jsp').done(function (html) {
                    dialog.modal({
                        keyboard: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        }
                    }).find('.modal-body').html(html);
                });

                dialog.find('#save').on('click', function (e) {
                    var rawData = {};
                    rawData['productID'] = dialog.find("#productIDID").val();
                    rawData['notchSide'] = dialog.find("#notchSideID").val();
                    rawData['bindefinitionFile'] = dialog.find("#bindefinitionFileID").val();
                    rawData['gridXmax'] = dialog.find("#gridXmaxID").val();
                    rawData['fabSite'] = dialog.find("#fabSiteID").val();
                    rawData['xdieSize'] = dialog.find("#xdieSizeID").val();
                    rawData['ydieSize'] = dialog.find("#ydieSizeID").val();
                    rawData['customerCodeID'] = dialog.find("#customerCodeIDID").val();
                    rawDataParam = rawData['productID'] + "," + rawData['notchSide'] + "," + rawData['bindefinitionFile'] + "," + rawData['gridXmax']
                            + "," + rawData['fabSite'] + "," + rawData['xdieSize'] + "," + rawData['ydieSize'] + "," + rawData['customerCodeID'];
                    if (!Validator.Validate(dialog.find('form')[0], 3))return;
                    $.post('${pageContext.request.contextPath}/RawData/addRawData/' + id + '.koala',
                            {rawData: rawDataParam}).done(function (result) {
                        debugger;
                        if (result.success) {
                            dialog.modal('hide');
                            $(grid).message({
                                type: 'success',
                                content: '保存成功'
                            });
                            renderRawData();
                        } else {
                            dialog.find('.modal-content').message({
                                type: 'error',
                                content: result.actionError
                            });
                        }
                    });
                });
            });
        }

        function updateRawData() {
            var id = getCheckedIds()[0];
            var self = this;
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            $.get('${pageContext.request.contextPath}/RawData/getRawData/' + id + '.koala').done(function (json) {
                json = json.data;
                if (json == null) {
                    $(grid).message({
                        type: 'warning',
                        content: '请先添加一条RawData信息'
                    });
                    return;
                }
                var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改Raw Data</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                $.get('<%=path%>/CPInfo-RawData-update.jsp').done(function (html) {
                    dialog.find('.modal-body').html(html);
                    var elm;
                    for (var index in json) {
                        elm = dialog.find('#' + index + 'ID');
                        elm.val(json[index]);
                    }
                    dialog.modal({
                        keyboard: false
                    }).on({
                        'hidden.bs.modal': function () {
                            $(this).remove();
                        }
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        debugger;
                        $.post('${pageContext.request.contextPath}/RawData/updateRawData/' + id + '.koala', dialog.find('form').serialize()).done(function (result) {
                            if (result.success) {
                                dialog.modal('hide');
                                $(grid).message({
                                    type: 'success',
                                    content: '保存成功'
                                });
                                renderRawData();
                            } else {
                                dialog.find('.modal-content').message({
                                    type: 'error',
                                    content: result.actionError
                                });
                            }
                        });
                    });
                });
            });
        }

        //RawData清除
        function deleteRawData() {
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            debugger;
            $.get('${pageContext.request.contextPath}/RawData/getRawData/' + id + '.koala').done(function (json) {
                json = json.data;
                if (json == null) {
                    $(grid).message({
                        type: 'warning',
                        content: 'RawData信息不存在'
                    });
                    return;
                }
                $(this).confirm({
                    content: '确定要删除所选记录吗?',
                    callBack: function () {
                        $.post('${pageContext.request.contextPath}/RawData/deleteRawData/' + id + '.koala').done(function (result) {
                            if (result.success) {
                                renderRawData();
                                $(grid).message({
                                    type: 'success',
                                    content: '清除成功'
                                });
                            } else {
                                $(grid).message({
                                    type: 'error',
                                    content: '清除失败'
                                });
                            }
                        });
                    }
                });
            });
        }

        // 获取选中
        function getCheckedIds() {
            // grid.getGrid().selectedRowsIndex()
            var rows = grid.getGrid().selectedRows()
            var length = rows.length;
            var ids = [];
            for (var i = 0; i < length; ++i) {
                ids.push(rows[i]["id"]);
            }
            return ids;
        }

        // 选择测试
        function renderTestProgram() {
            $('#testProgram_CPInfo').html('');
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            $.get("${pageContext.request.contextPath}/CPInfo/getTestProgramTemplateByProduct/" + id + ".koala")
                    .done(function (data) {
                        var html = "";
                        var th = "<table class='table table-bordered'  style='background-color:#fff;margin-top:10px'><tr height='30px'><td width='20px'></td><td>Customer_No</td><td>version</td><td>测试程序</td><td>程序版本</td><td>可用机台</td><td>标注</td><td>授权时间</td><td>理论UPH</td><td>实际UPH</td><td>IS_YELLOW</td><td>授权人</td><td>站点</td></tr>";
                        html += th;
                        $.each(data, function (index) {
                            var b = index;
                            ++b;
                            html += "<tr height='30px'><td>" + b + "</td><td>" +
                                    data[index]["customerDirectNumber"] + "</td><td>" +
                                    data[index]["productVersion"] + "</td><td>" +
                                    data[index]["name"] + "</td><td>" +
                                    data[index]["revision"] + "</td><td>" +
                                    data[index]["testSys"] + "</td><td>" +
                                    data[index]["note"] + "</td><td>" +
                                    data[index]["acetecAuthorizationDTOs"][0]["lastModifyTime"] + "</td><td>" +
                                    data[index]["uphtheory"] + "</td><td>" +
                                    data[index]["uphreality"] + "</td><td>" +
                                    data[index]["isYellow"] + "</td><td >" +
                                    data[index]["acetecAuthorizationDTOs"][0]["employeeDTO"]["name"] + "</td><td>" +
                                    data[index]["site"] + "</td></tr>";
                        });
                        html += "</table>";
                        $("#testProgram_CPInfo").html(html);
                    });
        }

        function deleteRow1(r) {
            var tr = r.parentNode.parentNode;
            tr.parentNode.removeChild(tr);
        }

        // SBL显示页面
        function renderSBL() {
            $('#sblTBody_CPInfo').html('');
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            else {
                $.get('${pageContext.request.contextPath}/CPInfo/findProcessTemplateByInternalProductId/' + id + '.koala').done(function (json) {
                    debugger;
                    json = json.data;
                    if (json == null || json == undefined) {
                        $(grid).message({
                            type: 'warning',
                            content: '请先绑定Process'
                        });
                        return;
                    }

                    var nodeOptions = json['content'].split("|").filter(function (value) {
                        return value.startsWith("CP")
                    }).map(function (value) {
                        return "<option value=" + value + ">" + value + "</option>"
                    }).join("");
                    $.get("${pageContext.request.contextPath}/CPInfo/getCPSBLTemplatesByProduct/" + id + ".koala").done(function (data) {
                        $('#sblTBody_CPInfo').empty();
                        debugger;
                        $.each(data, function (a) {
                            if (data == null || data[a] == null) {
                                $('#sblTBody_CPInfo').html();
                            } else {
                                debugger;
                                $('#sblTBody_CPInfo').append("<tr><td >"
                                        + "<input type='text' name='rule" + a + "' class='form-control'/>"
                                        + "</td>"
                                        + "<td><input type='text' name='upperLimit" + a + "'  class='form-control'/></td>"
                                        + "<td><input type='text' name='lowerLimit" + a + "'  class='form-control'/></td>"
                                        + "<td><input type='text' name='site" + a + "'  class='form-control'/></td>"
                                        + "<td><select id='type" + a + "' class='form-control'><option name='SoftBin' value='SB'  class='form-control'>SoftBin</option>"
                                        + "<option name='HardBin' value='HB'  class='form-control'>HardBin</option></select></td>"
                                        + "<td><select id='testRange" + a + "' class='form-control'><option name='by lot' value='by lot'  class='form-control'>by lot</option>"
                                        + "<option name='by wafer' value='by wafer'  class='form-control'>by wafer</option></select></td>"
                                        + "<td><select id='controlType" + a + "' class='form-control'><option name='die' value='die'  class='form-control'>die</option>"
                                        + "<option name='rate' value='rate'  class='form-control'>rate</option></select></td>"
                                        + "<td><select id='quality" + a + "' class='form-control'><option name='pass' value='PASS'  class='form-control'>PASS</option>"
                                        + "<option name='fail' value='FAIL'  class='form-control'>FAIL</option></select></td>"
                                        + "<td><select id='node" + a + "' class='form-control'>" + nodeOptions + "</select></td>"
                                        + "<td style='height:32px;'><input type='button'  style='display: block;margin-left:5px; ' id='subLine' class='btn btn-primary' value='-' onclick='deleteRow1(this)'></td>"
                                        + "</tr>");
                                $("input[name= " + 'rule' + a + "]").val(data[a]['rule']);
                                $("input[name= " + 'site' + a + "]").val(data[a]['site']);
                                $("input[name= " + 'lowerLimit' + a + "]").val(data[a]['lowerLimit']);
                                $("input[name= " + 'upperLimit' + a + "]").val(data[a]['upperLimit']);
                                $('#type' + a + '').val(data[a]['type']);
                                $("#testRange" + a).val(data[a]["testRange"]);
                                $("#controlType" + a).val(data[a]["controlType"]);
                                $("#quality" + a).val(data[a]["quality"]);
                                $("#node" + a).val(data[a]["node"]);
                                count = data.length + 1;
                            }
                        });
                    });
                });
            }
        }

        //SBL保存
        function saveSBL() {
            for (var i = 0; i < document.sbls.elements.length - 1; i++) {
                if (document.sbls.elements[i].value == "") {
                    $("body").message({
                        type: 'warning',
                        content: '请填写完整'
                    });
                    document.sbls.elements[i].focus();
                    return false;
                }
                $("#sblForm_CPInfo").find("input[type='text']").each(function () {
                    if ($(this).val() < 0 || $(this).val() > 101) {
                        {
                            $("body").message({
                                type: 'warning',
                                content: '请核对数据'
                            });
                            $(this).focus();
                            return false;
                        }
                    }
                })

            }
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $("body").message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            } else {
                var sblArray = [];
                $("#sblTBody_CPInfo").find("tr").each(function () {
                    var sblData = {};
                    debugger;
                    $(this).find("td").each(function () {
                        if ($(this).children().length > 0) {
                            if ($(this).children()[0].tagName == "INPUT" && $(this).children().attr("type") == "text") {
                                sblData[$(this).children().attr("name").replace(/\d+/g, "")] = $(this).children().val();
                            } else if ($(this).children()[0].tagName == "SELECT") {
                                sblData[$(this).children().attr("id").replace(/\d+/g, "")] = $(this).children().find("option:selected").val();
                            }
                        }
                    })
                    sblArray.push(sblData);
                })
                $.post('${pageContext.request.contextPath}/CPInfo/bindSBLTemplates.koala', {
                    "internalProductId": id,
                    "sblTemplates": JSON.stringify(sblArray)
                }).done(function (result) {
                    if (!result['success']) {
                        $(grid).message({
                            type: 'error',
                            content: '绑定失败'
                        });
                        return;
                    }
                    $(grid).message({
                        type: 'success',
                        content: '绑定成功'
                    });
                    renderSBL();
                })
            }
        }

        //Sbl清除
        function clearSBL() {

            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            $.ajax({
                url: "${pageContext.request.contextPath}/CPInfo/clearCPSBLTemplates/" + id + ".koala",
                dataType: "json",
                success: function (data) {

                    $(grid).message({
                        type: 'success',
                        content: '清除成功'
                    });
                    $('#sblTBody_CPInfo').empty();
                    count = 1;
                    j = 0;
                    // setTimeout('clearSBL()',1000);
                }
            });
        }


        function renderProcess() {
            debugger;
            $('#processBody_CPInfo').html();
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            $.get('${pageContext.request.contextPath}/CPInfo/findProcessTemplateByInternalProductId/' + id + '.koala').done(function (data) {
                debugger
                data = data.data;
                // for(var a in data){
                if (data == null) {
                    $('#processBody_CPInfo').html();
                } else {

                    var str = data['content'].split("|");
                    debugger;
                    var totalType = "";
                    for (var i = 0; i < str.length; i++) {
                        totalType = totalType + str[i] + ','
                    }

                    $.get('${pageContext.request.contextPath}/cpRuncardController/getRuncardFinishedStatus.koala?cpinfoId=' +
                            id + '&totalSites=' + totalType).done(function (json) {

                        var result = json["data"];

                        var str = data['content'].split("|");
                        debugger;
                        var totalType = "";
                        for (var i = 0; i < str.length; i++) {
                            totalType = totalType + str[i] + ','
                        }

                        if (data['testingTemplateDTO'] == null) {
                            var html = "";
                            var html1 = "";
                            var th = "<table class='table table-bordered'  style='background-color:#fff;text-align:center;'>";
                            html += th;
                            html += "<tr height='30px'>";

                            for (var i = 0; i < str.length; i++) {
                                if (str[i] === 'Incoming' ||str[i] === 'INK' || str[i] === 'INKBake' ) {
                                    html += '<td style="color:#888888">' + str[i] + "</td>";
                                    continue;
                                }
                                if (result[str[i]] === true){
                                    html += "<td><a  style='color:#000000' href='javascript:showUEditor(" + id + ',' + '"' + str[i] + '"' + ',' +
                                            '"' + totalType + '"' +
                                            ")'>" + str[i] + "</a></td>";
                                }else {
                                    html += "<td><a style='color:#FF3333' href='javascript:showUEditor(" + id + ',' + '"' + str[i] + '"' + ',' +
                                            '"' + totalType + '"' +
                                            ")'>" + str[i] + "</a></td>";
                                }
                            }
                            html += "</tr >";
                            html += "</table>";
                            $("#processBody_CPInfo").html(html);
                        } else {
                            var html = "";
                            var html1 = "";
                            var th = "<table class='table table-bordered'  style='background-color:#fff;text-align:center;'>";
                            html += th;
                            html += "<tr height='30px'>";
                            var str = data['content'].split("|");
                            var str1 = data['testingTemplateDTO']['content'].split("|");
                            debugger;
                            $.each(str, function (i) {
                                if (str[i] == "test") {
                                    for (var b in str1) {
                                        html += "<td><a href='#' style='color:#f00;' >" + str1[b] + "</a></td>";
                                    }
                                }
                                else
                                    html += "<td><a href='#'>" + str[i] + "</a></td>";
                            });
                            html += "</tr >";
                            html += "</table>";
                            $("#processBody_CPInfo").html(html);
                        }
                    });


                }
            });
        }

        var openProcessTemplateTab = function () {
            var pp = $("#pp").html();
            var thiz = $(this),
                    mark = thiz.attr('mark');
            mark = openTab('/pages/domain/ProcessTemplate-list.jsp', "process管理", mark);
            if (mark) {
                thiz.attr("mark", mark);
            }
        };
        $.get('${pageContext.request.contextPath}/SystemDictionary/getByType/HandlerType.koala').done(function (data) {
            $("#processHandlerTypeSelect").empty();
            ;
            $("#processHandlerTypeSelect").append("<option>请选择</option>")
            $.each(data, function (a) {
                $("#processHandlerTypeSelect").append("<option value='" + data[a]['value'] + "'>" + data[a]['value'] + "</option>");
            });
        });
        // 选中机械手时更新对应的process的select
        $("#processHandlerTypeSelect").on("change", function () {
            var handlerType = $("#processHandlerTypeSelect").val();
            $.get('${pageContext.request.contextPath}/ProcessTemplate/findProcessByHandler/' + handlerType + '.koala')
                    .done(function (data) {
                        var processSelect_CPInfo = $("#processSelect_CPInfo");
                        processSelect_CPInfo.empty();
                        processSelect_CPInfo.append("<option value='' selected='selected'>请选择</option>");
                        debugger;
                        for (var a in data) {
                            processSelect_CPInfo.append("<option value='" + data[a]['id'] + "'>" + data[a]['name'] + "</option>");
                        }
                    });

        });

        // 绑定ProcessTemplate
        $("#bindProcess").on("click", function () {
            debugger
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            var processSelect_CPInfo = $("#processSelect_CPInfo");
            var requestParams = {
                "id": id,
                "processTemplateId": processSelect_CPInfo.find("option:selected").val()
            };
            $.post('${pageContext.request.contextPath}/CPInfo/bindProcess.koala', requestParams)
                    .done(function (json) {
                        debugger;
                        if (json['success'] == true) {
                            $(grid).message({
                                type: 'success',
                                content: '绑定成功'
                            });
                            renderProcess();
                        }
                    });
        });

        // 取消绑定ProcessTemplate
        $("#clearProcess").on("click", function () {
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            $.get('${pageContext.request.contextPath}/CPInfo/clearProcess/' + id + '.koala').done(function (data) {
                $(grid).message({
                    type: 'success',
                    content: '清除成功'
                });
                $('#processBody_CPInfo').empty();
            })
        });
        function runcarddialog(cpinfoId, employeeId, employeeName, runcardSignInfo) {

            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">签核</h4></div>' +
                    '<div class="modal-body"></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');

            var body = $("<div class='form-group'><label class='col-lg-2 control-label'  class='style:display:inline'>" + employeeName + ':</label>' +
                    "<div class='form-group'><label class='col-lg-4 control-label'  class='style:display:inline'>" + runcardSignInfo.departmentName + ':</label>' +
                    "<input id='employeeID' type='hidden' name='id' value=" + employeeId + " />" +
                    "<select style='margin-left:30px;' name='opinion' id='opinionID'><option value=''>请选择</option><option  value ='同意' >同意</option><option value='不同意'>不同意</option></select><div style='margin-top:10px;margin-left:40px'><label class='col-lg-3 control-label'  class='style:display:inline；'>签核备注：</label><textarea name='note' id='noteID' cols='70' rows='5'></textarea></div></div>");

//            debugger
            body.find("#noteID").val(runcardSignInfo.note);
            body.find("#opinionID");
            if (runcardSignInfo.opinion === "同意") {
                body.find("#opinionID").find("option[value='同意']").attr("selected", true);
            }
            if (runcardSignInfo.opinion === "不同意") {
                body.find("#opinionID").find("option[value='不同意']").attr("selected", true);
            }

            dialog.find('.modal-body').append(body);

            dialog.modal({
                keyboard: false
            }).on({
                'hidden.bs.modal': function () {
                    $(this).remove();
                }
            });

            dialog.find('#save').on('click', {grid: grid}, function (e) {
//                debugger;
                var authorInfo = dialog.find('.form-group');
                var data = {
                    'cpinfoId': cpinfoId,
                    'userId': $(authorInfo).find('#employeeID').val(),
                    'opinion': $(authorInfo).find('#opinionID').val(),
                    'note': $(authorInfo).find('#noteID').val(),
                };
                $.ajax({
                    type: 'POST',
                    url: '<%=contextPath %>/cpRuncardController/signRuncardInfo.koala',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(data),
                    dataType: 'json',
                    success: function (data) {
//                        debugger
                        success = data['success'];
                        if (success) {
                            dialog.modal('hide');
                            e.data.grid.data('koala.grid').refresh();
                            e.data.grid.message({
                                type: 'success',
                                content: '保存成功'
                            });
                        } else {
                            e.data.grid.data('koala.grid').refresh();
                            e.data.grid.message({
                                type: 'error',
                                content: '签核失败'
                            });
                        }
                    },

                    error: function (data) {
                        e.data.grid.data('koala.grid').refresh();
                        e.data.grid.message({
                            type: 'error',
                            content: '签核失败'
                        });
                    }
                })
            });
        }
        // 签核
        $("#confirmProcess").on("click", function () {

            var cpinfoId = getCheckedIds();

            if (cpinfoId.length != 1) {
                if (cpinfoId.length == 0) {
                    $(grid).message({
                        type: 'warning',
                        content: '请选择一条产品记录'
                    });
                } else {
                    $(grid).message({
                        type: 'warning',
                        content: '请只选择一条产品记录'
                    });
                }

                return;
            }
            cpinfoId = cpinfoId[0];

            var employeeId;
            var employeeName;
            var runcardSignInfo = {};
            $.get('${pageContext.request.contextPath}/CPInfo/findProcessTemplateByInternalProductId/' + cpinfoId + '.koala').done(function (data) {
//                debugger
                data = data.data.content;
                var totalSites = "";
                var str = data.split("|");
                for (var i = 0; i < str.length; i++) {
                    totalSites = totalSites + str[i] + ','
                }

                $.get('${pageContext.request.contextPath}/cpRuncardController/isRuncardFinished.koala?cpinfoId=' + cpinfoId + '&totalSites=' + totalSites
                ).done(function (data) {
//                    debugger
                    success = data['success'];
                    if (success == false) {

                        grid.message({
                            type: 'error',
                            content: 'Runcard没有填写完成'
                        })
                        return;
                    }
                    $.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function (json) {
//                        debugger
                        employeeName = json['data']['name'];
                        employeeId = json['data']['id'];
                        //模拟签核人员
                        $.get('${pageContext.request.contextPath}/cpRuncardController/getDepartmentByEmployeeId.koala?cpinfoId=' + cpinfoId +
                                '&employeeid=' + employeeId).done(function (json) {
//                            debugger
                            var validate = json['data']['validate'];
                            if (!validate) {
                                grid.message({
                                    type: 'error',
                                    content: '您没有签核权限'
                                })
                                return;
                            }

                            runcardSignInfo.departmentName = json['data']['departmentName'];
                            runcardSignInfo.opinion = json['data']['opinion'];
                            runcardSignInfo.note = json['data']['note'];

                            $.get('${pageContext.request.contextPath}/cpRuncardController/isRuncardInfoSigned.koala?cpinfoId=' + cpinfoId).done(function (data) {
//                                debugger
                                success = data['success'];
                                if (success == true) {
//                            alert("已经签核");
                                    grid.message({
                                        type: 'success',
                                        content: '已经签核'
                                    })
                                    return;
                                }
                                runcarddialog(cpinfoId, employeeId, employeeName, runcardSignInfo);
                            });
                        })
                    });
                });
            });
        });

        // 特殊表单
        $("#superform").on("click", function () {
            var cpinfoId = grid.getGrid().selectedRowsIndex();
            if (cpinfoId.length != 1) {
                if (cpinfoId.length == 0) {
                    $(grid).message({
                        type: 'warning',
                        content: '请选择一条产品记录'
                    });
                } else {
                    $(grid).message({
                        type: 'warning',
                        content: '请只选择一条产品记录'
                    });
                }
                return;
            }

            cpinfoId = cpinfoId[0];


            $.get('${pageContext.request.contextPath}/cpRuncardController/getSpecialFormStatus.koala?cpinfoId=' + cpinfoId).done(function (data) {
                data = data['data'];
                var mcp_COVER1SheetStatus = data['mcp_COVER1SheetStatus'];
                var cp1SheetStatus = data['cp1SheetStatus'];
                var cp2SheetStatus = data['cp2SheetStatus'];

                var sheet1Status = data['sheet1Status'];
                var sheet2Status = data['sheet2Status'];

                var map_SHIFT1Status = data['map_SHIFT1Status'];

                var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">特殊表单关联</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                $.get('<%=contextPath %>/cpRuncardController/getSpecialFormPage.koala?&cpinfoId=' + cpinfoId).done(function (html) {
                    debugger
                    dialog.find('.modal-body').html(html);
                    dialog.find("#mcp_COVER1SheetStatus").attr("checked", mcp_COVER1SheetStatus);
                    dialog.find("#cp1SheetStatus").attr("checked", cp1SheetStatus);
                    dialog.find("#cp2SheetStatus").attr("checked", cp2SheetStatus);
                    dialog.find("#sheet1Status").attr("checked", sheet1Status);
                    dialog.find("#sheet2Status").attr("checked", sheet2Status);
                    dialog.find("#map_SHIFT1Status").attr("checked", map_SHIFT1Status);
                });

                dialog.modal({
                    keyboard: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    }
                });

                dialog.find('#save').on('click', {grid: grid}, function (e) {
                    var mcp_COVER1SheetStatus = dialog.find("#mcp_COVER1SheetStatus").prop("checked");
                    var cp1SheetStatus = dialog.find("#cp1SheetStatus").prop("checked");
                    var cp2SheetStatus = dialog.find("#cp2SheetStatus").prop("checked");
                    var sheet1Status = dialog.find("#sheet1Status").prop("checked");
                    var sheet2Status = dialog.find("#sheet2Status").prop("checked");
                    var map_SHIFT1Status = dialog.find("#map_SHIFT1Status").prop("checked");


                    var json = {
                        "cpinfoId": cpinfoId,
                        "MCP_Cover1SheetStatus": mcp_COVER1SheetStatus,
                        "CP1SheetStatus": cp1SheetStatus,
                        "CP2SheetStatus": cp2SheetStatus,
                        "Sheet1Status": sheet1Status,
                        "Sheet2Status": sheet2Status,
                        "Map_Shift1SheetStatus": map_SHIFT1Status
                    }

                    $.ajax({
                        type: 'POST',
                        url: '<%=contextPath %>/cpRuncardController/saveSpecialFormStaus.koala',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify(json),
                        dataType: 'json',
                        success: function (data) {
                            debugger
                            success = data['success'];
                            if (success) {
                                dialog.modal('hide');
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                    type: 'success',
                                    content: '保存成功'
                                });
                            } else {
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                    type: 'error',
                                    content: '保存失败'
                                });
                            }
                        },

                        error: function (data) {
                            e.data.grid.data('koala.grid').refresh();
                            e.data.grid.message({
                                type: 'error',
                                content: '保存失败'
                            });
                        }
                    })
                });

            })

        });

        // 渲染Labels
        function renderLabel() {
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                $('#labelBody_CPInfo').empty();
                return;
            }
            $.get('${pageContext.request.contextPath}/FTInfo/findLabelsByInternalProductId/' + id + '.koala').done(
                    function (json) {
                        var html = "";
                        var th = "<table style='border:#CDBECF 1px solid;' border='1' width='800px'><tr height='30px'><td width='20px'></td><td>标签命名</td><td>标签类型</td></tr>";
                        html += th;
                        $.each(json, function (a) {
                            var b = a;
                            ++b;
                            html += "<tr height='30px'><td>" + b
                                    + " </td><td>"
                                    + json[a]['labelName']
                                    + "</td><td>"
                                    + json[a]["labelType"]
                                    + "</td></tr>";
                        });
                        html += "</table>";
                        $("#labelBody_CPInfo").html(html);
                    });
        }

        // 绑定标签模板
        function saveLabel() {
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            var requestParams = {};
            requestParams["id"] = id;
            requestParams["labelId"] = $("#labelTemplateNamesSelect").find("option:selected").val();
            $.post("${pageContext.request.contextPath}/CPInfo/bindLabel.koala",
                    requestParams).done(function (json) {
                debugger;
                if (json["success"]) {
                    $(grid).message({
                        type: "success",
                        content: "绑定成功"
                    });
                    renderLabel();
                }
            });

        }

        // 清空标签
        function clearLabel() {
            var id = getCheckedIds()[0];
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            // debugger
            $.post("${pageContext.request.contextPath}/CPInfo/clearLabels/" + id + ".koala")
                    .done(function (json) {
                        debugger
                        $(grid).message({
                            type: 'success',
                            content: '清除成功'
                        });
                        $("#labelBody_CPInfo").empty();
                        var th = "<table style='border:#CDBECF 1px solid;' border='1' width='800px'><tr height='30px'><td width='20px'></td><td>标签命名</td><td>标签类型</td></tr></table>";
                        $("#labelBody_CPInfo").html(th);
                    });
        }

        // 标签类型选择
        function getLabelTypes(value) {
            var data = [{name: "page", value: 0}, {name: "pagesize", value: 1000}, {name: "testType", value: value}];
            $.post('${pageContext.request.contextPath}/Label/pageJson.koala', data).done(
                    function (json) {
                        json = json.data;
                        var $selectLabelName = $("#labelTemplateNamesSelect");
                        $selectLabelName.empty();
                        $selectLabelName.append(
                                "<option value='' selected='selected'>请选择</option>");
                        for (var a in json) {
                            $selectLabelName.append(
                                    "<option value='" + json[a]['id'] + "'>"
                                    + json[a]['labelName']
                                    + "</option>");
                        }
                    });
        }

        function appendEmptySBLTemplateRow() {
            var ids = grid.getGrid().selectedRowsIndex();
            if (ids.length != 1) {
                $(grid).message({
                    type: 'warning',
                    content: '请只一条产品记录'
                });
                return;
            }
            var id = parseInt(ids[0]);
            $.get('${pageContext.request.contextPath}/CPInfo/getProcessTemplateContentByProduct/' + id + '.koala').done(function (data) {
                data = data.data;
                if (data == null || data == undefined) {
                    $(grid).message({
                        type: 'warning',
                        content: '请先绑定Process'
                    });
                    return;
                }
                var nodeOptions = data.split("|").filter(function (value) {
                    return value.startsWith("Test-");
                }).map(function (value) {
                    return "<option value=" + value + ">" + value + "</option>"
                }).join("");

                var siteOptions = [1, 2, 3, 4, 5].map(function (value) {
                    return "<option value=" + value + ">" + value + "</option>";
                }).join("");

                var binType = [SB, HB].map(function (value) {
                    return "<option value=" + value + ">" + value + "</option>";
                }).join("");

                var index = $("#sblTemplateBody").find("tr:last").index() + 1;
                $('#sblTemplateBody').append("<tr><td>"
                        + "<div><span>Bin</span><input type='text' name='sblcount" + index + "' style='width: 85%;display:inline;' class='form-control'/></div>"
                        + "</td>"
                        + "<td><input type='text' name='bottomLimit" + index + "'  class='form-control'/></td>"
                        + "<td><input type='text' name='topLimit" + index + "'  class='form-control'/></td>"
                        + "<td><select id='selectslbSite" + index + "' class='form-control'>" + siteOptions + "</select></td>"
                        + "<td><select id='selectslbpingzhi" + index + "' class='form-control'><option name='fail' value='fail'  class='form-control'>fail</option>"
                        + "<option name='pass' value='pass'  class='form-control'>pass</option></select></td>"
                        + "<td><select id='selectbinType" + index + "' class='form-control'>" + binType + "</select></td>"
                        + "<td><select id='selectSBLNode" + index + "' class='form-control'>" + nodeOptions + "</select></td>"
                        + "<td style='height:32px;'><input type='button'  style='display: block;margin-left:5px; ' id='subLine' class='btn btn-primary' value='-' onclick='deleteRow1(this)'></td>"
                        + "</tr>");
            });
        }

        function renderRawData() {
            $("#RawDataBody").html('');
            var ids = grid.getGrid().selectedRowsIndex();
            if (ids.length != 1) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            var id = parseInt(ids[0]);
            $.get('${pageContext.request.contextPath}/RawData/getRawData/' + id + '.koala').done(function (data) {
                var html = "";
                var th = "<table class='table table-bordered'  style='background-color:#fff;margin-top:10px;text-align:center;'><tr height='30px'><td>Product ID</td><td>Notch Side</td><td>Bindefinition File</td><td>Grid Xmax</td><td>Fab Site</td><td>X Die Size</td><td>Y Die Size</td><td>Customer CodeID</td></tr>";
                html += th;
                data = data.data;
                if (data != null) {
                    html += "<tr height='30px'><td>" + data["productID"] + "</td><td>" + data["notchSide"] + "</td><td>" + data["bindefinitionFile"] + "</td><td>" + data["gridXmax"] + "</td><td>" + data["fabSite"] + "</td><td>" + data["xdieSize"] + "</td><td>" + data["ydieSize"] + "</td><td>" + data["customerCodeID"] + "</td></tr>";
                }
                html += "</table>";
                $("#RawDataBody").html(html);
            });
        }


        $(function () {
            $('#addLine').on('click', function () {
                var id = getCheckedIds()[0];
                if (id == undefined) {
                    $(grid).message({
                        type: 'warning',
                        content: '请选择一条产品记录'
                    });
                    return;
                }
                $.get('${pageContext.request.contextPath}/CPInfo/findProcessTemplateByInternalProductId/' + id + '.koala').done(function (json) {
                    debugger;
                    json = json.data;
                    if (json == null || json == undefined) {
                        $(grid).message({
                            type: 'warning',
                            content: '请先绑定Process'
                        });
                        return;
                    }
                    var nodeOptions = json['content'].split("|").filter(function (value) {
                        return value.startsWith("CP");
                    }).map(function (value) {
                        return "<option value=" + value + ">" + value + "</option>"
                    }).join("");
                    $('#sblTBody_CPInfo').append("<tr>"
                            + "<td><input type='text' name='rule" + j + "'  class='form-control'/></td>"
                            + "<td><input type='text' name='upperLimit" + j + "'  class='form-control'/></td>"
                            + "<td><input type='text' name='lowerLimit" + j + "'  class='form-control'/></td>"
                            + "<td><input type='text' name='site" + j + "'  class='form-control'/></td>"
                            + "<td><select id='type" + j + "' class='form-control'><option name='SoftBin' value='SB'  class='form-control'>SoftBin</option>"
                            + "<option name='HardBin' value='HB'  class='form-control'>HardBin</option></select></td>"
                            + "<td><select id='testRange" + j + "' class='form-control'><option name='by lot' value='by lot'  class='form-control'>by lot</option>"
                            + "<option name='by wafer' value='by wafer'  class='form-control'>by wafer</option></select></td>"
                            + "<td><select id='controlType" + j + "' class='form-control'><option name='die' value='die'  class='form-control'>die</option>"
                            + "<option name='rate' value='rate'  class='form-control'>rate</option></select></td>"
                            + "<td><select id='quality" + j + "' class='form-control'><option name='pass' value='PASS'  class='form-control'>PASS</option>"
                            + "<option name='fail' value='FAIL'  class='form-control'>FAIL</option></select></td>"
                            + "<td><select id='node" + j + "' class='form-control'>" + nodeOptions + "</select></td>"
                            + "<td style='height:32px;'><input type='button'  style='display: block;margin-left:5px; ' id='subLine' class='btn btn-primary' value='-' onclick='deleteRow1(this)'></td>"
                            + "</tr>");
                    count++;
                    j++;
                });
            });

            $('#clear').on('click', function () {
                $('form').find();
            });
            PageLoader = {
                initSearchPanel: function () {
                    var contents = [{
                        title: '请选择',
                        value: ''
                    }];
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/Customer/findCustomer.koala',
                        type: 'POST',
                        dataType: 'json'
                    }).done(function (msg) {
                        $.each(msg, function () {
                            contents.push({
                                title: this.chineseName,
                                value: this.number
                            });
                        })
                    });

                    form.find('#numberID').select({
                        title: '请选择',
                        contents: contents
                    }).on('change', function () {
                        form.find('#numberID_').val($(this).getValue());
                    });
                },
                initGridPanel: function () {
                    var self = this;
                    var width = 120;
                    return grid.grid({
                        identity: "id",
                        buttons: [
                            {
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>',
                                action: 'add'
                            },
                            {
                                content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>',
                                action: 'modify'
                            },
                            {
                                content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>',
                                action: 'delete'
                            },
                            {
                                content: '<button class="btn btn-primary" type="button" style="float:right;"><span class="glyphicon glyphicon-export"><span>导出Excel</button>',
                                action: 'exportExcel'
                            }
                        ],
                        url: "${pageContext.request.contextPath}/CPInfo/pageJson.koala",
                        columns: [
                            {title: '客户产品型号', name: 'customerProductNumber', width: width},
                            {title: '客户产品版本', name: 'customerProductRevision', width: width},
                            {
                                title: '所属直接客户',
                                name: 'customerDirectName',
                                width: 180
                            },
                            {
                                title: 'PID',
                                name: 'internalProductNumber',
                                width: width,
                            },
                            {
                                title: '所属间接客户',
                                name: 'customerIndirectName',
                                width: 180
                            },
                            {title: '晶圆尺寸', name: 'waferSize', width: width},
                            {title: 'GROSS DIE', name: 'grossDie', width: width},
                            {title: '最低PASS报警', name: 'warningQty', width: width},
                            {title: '报警指标分类', name: 'warningType', width: width},
                            {title: '测试时间/片', name: 'testTime', width: width},
                            {title: '产品制程要求', name: 'productRequire', width: width},
                            {title: '每片接触次数', name: 'touchQty', width: width},
                            {
                                title: '质量部主要负责人',
                                name: 'keyQuantityManagerName',
                                width: width
                            },
                            {
                                title: '质量部协助负责人',
                                name: 'assistQuantityManagerName',
                                width: width
                            },
                            {
                                title: '产品部主要负责人',
                                name: 'keyProductionManagerName',
                                width: width
                            },
                            {
                                title: '产品部协助负责人',
                                name: 'assistProductionManagerName',
                                width: width
                            },
                            {
                                title: 'TDE主要负责人',
                                name: 'keyTDEManagerName',
                                width: width
                            },
                            {
                                title: 'TDE协助负责人',
                                name: 'assistTDEManagerName',
                                width: width
                            }
                        ]
                    }).on({
                        'exportExcel': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择要导出的记录'
                                });
                                return;
                            }
                            $this.confirm({
                                content: '确定要导出所选记录吗?',
                                callBack: function () {
                                    self.exportExcel(indexs, $this);
                                }
                            });
                        },
                        'add': function (event, data) {
                            self.add($(this));
                        },
                        'modify': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择一条记录进行修改'
                                });
                                return;
                            }
                            if (indexs.length > 1) {
                                $this.message({
                                    type: 'warning',
                                    content: '只能选择一条记录进行修改'
                                });
                                return;
                            }
                            self.modify(indexs[0], $this);
                        },
                        'delete': function (event, data) {
                            var indexs = data.data;
                            var $this = $(this);
                            if (indexs.length == 0) {
                                $this.message({
                                    type: 'warning',
                                    content: '请选择要删除的记录'
                                });
                                return;
                            }
                            var remove = function () {
                                self.remove(indexs, $this);
                            };
                            $this.confirm({
                                content: '确定要删除所选记录吗?',
                                callBack: remove
                            });
                        }
                    });
                },
                add: function (grid) {
                    var flag = 1;
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">新增CP产品信息管理</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<input name="addmore" type="checkbox" id="addmoreID" style="vertical-align:middle;margin-right:10px">'
                            + '<label class="add-control-label" style="margin-right:10px">连续添加</label>'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/CPInfo-add.jsp').done(function (html) {
                        dialog.modal({
                            keyboard: false
                        }).find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        dialog.find('#customerProductNumberID').on('blur', function () {
                       	 if(!!$(this).val()!=0)
                      		 {
	                       		 $.get('${pageContext.request.contextPath}/InternalProduct/createPID.koala?customerProductNumber='+$(this).val()).done(function (result) {
	                                 	dialog.find('#internalProductNumberID').val(result['data']);
	                                 });
                      		 }
                        })
                        dialog.find('#modalClose').click(function () {
                            dialog.find('#myModal').modal('hide');
                        });
                        dialog.find('#modalCancel').click(function () {
                            dialog.find('#myModal').modal('hide');
                        });
                        dialog.find('#modalOpen').click(function () {
                            dialog.find('#myModal').modal('show');
                        });
                        dialog.find('#modalSave').on('click', function () {
                            var waferSizeContents = [{title: '请选择', value: ''}];
                            $.ajax({
                                async: false,
                                url: '${pageContext.request.contextPath}/SystemDictionary/getByType/WaferSize.koala',
                                type: 'GET',
                                dataType: 'json',
                            }).done(function (msg) {
                                for (var i = 0; i < msg.length; i++) {
                                    waferSizeContents.push({
                                        title: msg[i]['label'],
                                        value: msg[i]['value']
                                    });
                                }
                            });
                            for (var i = 0; i < waferSizeContents.length; i++) {
                                if (dialog.find('#valueID').val() == waferSizeContents[i].value) {
                                    flag = 0;
                                }
                            }
                            if (flag) {
                                var data = {
                                    "value": dialog.find('#valueID').val(),
                                    'label': dialog.find('#labelID').val(),
                                    type: 'WaferSize',
                                    'description': '晶圆尺寸'
                                }
                                $.post('${pageContext.request.contextPath}/SystemDictionary/add.koala', data).done(function (result) {
                                    if (result.success) {
                                        flag = 1;
                                        waferSizeContents.push({
                                            title: dialog.find('#labelID').val(),
                                            value: dialog.find('#valueID').val()
                                        });
                                        dialog.find('#waferSizeID').select({
                                            title: dialog.find('#labelID').val(),
                                            contents: waferSizeContents
                                        });
                                        dialog.find('#myModal').modal('hide');
                                        dialog.find('#waferSizeID').setValue(dialog.find('#valueID').val());
                                        dialog.find('#waferSizeID_').val(dialog.find('#valueID').val());
                                        dialog.find('form').message({
                                            type: 'success',
                                            content: '保存成功'
                                        });
                                    } else {
                                        dialog.find('#myModal').find('.modal-content').message({
                                            type: 'error',
                                            content: result.actionError
                                        });
                                    }
                                });
                            } else {
                                flag = 1;
                                dialog.find('#valueID').val('');
                                dialog.find('#labelID').val('');
                                dialog.find('#myModal').modal('hide');
                                dialog.find('#myModal').find('.modal-content').message({
                                    type: 'error',
                                    content: '晶圆尺寸已存在'
                                });
                            }
                        });
                        dialog.find('#customerProductNumberID').on('change', function () {
                            var customerValue = dialog.find('#customerProductNumberID').val();
                            dialog.find('#shipmentProductNumberID').val(customerValue);
                        })
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        $.post('${pageContext.request.contextPath}/CPInfo/add.koala', dialog.find('form').serialize()).done(function (result) {
                            if (result.success) {
                                dialog.modal('hide');
                                e.data.grid.data('koala.grid').refresh();
                                e.data.grid.message({
                                    type: 'success',
                                    content: '保存成功'
                                });
                            } else {
                                dialog.find('.modal-content').message({
                                    type: 'error',
                                    content: result.actionError
                                });
                            }
                        });
                    });
                },
                modify: function (id, grid) {
                    var flag = 1;
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改CP产品信息管理</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/CPInfo-update.jsp').done(function (html) {
                        dialog.modal({
                            keyboard: false
                        }).find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));
                        dialog.find('#modalClose').click(function () {
                            dialog.find('#myModal').modal('hide');
                        });
                        dialog.find('#modalCancel').click(function () {
                            dialog.find('#myModal').modal('hide');
                        });
                        dialog.find('#modalOpen').click(function () {
                            dialog.find('#myModal').modal('show');
                        });
                        dialog.find('#modalSave').on('click', function () {
                            var waferSizeContents = [{title: '请选择', value: ''}];
                            $.ajax({
                                async: false,
                                url: '${pageContext.request.contextPath}/SystemDictionary/getByType/WaferSize.koala',
                                type: 'GET',
                                dataType: 'json',
                            }).done(function (msg) {
                                for (var i = 0; i < msg.length; i++) {
                                    waferSizeContents.push({
                                        title: msg[i]['label'],
                                        value: msg[i]['value']
                                    });
                                }
                            });
                            for (var i = 0; i < waferSizeContents.length; i++) {
                                if (dialog.find('#valueID').val() == waferSizeContents[i].value) {
                                    flag = 0;
                                }
                            }
                            if (flag) {
                                var data = {
                                    "value": dialog.find('#valueID').val(),
                                    'label': dialog.find('#labelID').val(),
                                    type: 'WaferSize',
                                    'description': '晶圆尺寸'
                                }
                                $.post('${pageContext.request.contextPath}/SystemDictionary/add.koala', data).done(function (result) {
                                    if (result.success) {
                                        flag = 1;
                                        waferSizeContents.push({
                                            title: dialog.find('#labelID').val(),
                                            value: dialog.find('#valueID').val()
                                        });
                                        dialog.find('#waferSizeID').select({
                                            title: dialog.find('#labelID').val(),
                                            contents: waferSizeContents
                                        });
                                        dialog.find('#myModal').modal('hide');
                                        dialog.find('#waferSizeID').setValue(dialog.find('#valueID').val());
                                        dialog.find('#waferSizeID_').val(dialog.find('#valueID').val());
                                        dialog.find('form').message({
                                            type: 'success',
                                            content: '保存成功'
                                        });
                                    } else {
                                        dialog.find('#myModal').find('.modal-content').message({
                                            type: 'error',
                                            content: result.actionError
                                        });
                                    }
                                });
                            } else {
                                flag = 1;
                                dialog.find('#valueID').val('');
                                dialog.find('#labelID').val('');
                                dialog.find('#myModal').modal('hide');
                                dialog.find('#myModal').find('.modal-content').message({
                                    type: 'error',
                                    content: '晶圆尺寸已存在'
                                });
                            }
                        });
                        $.get('${pageContext.request.contextPath}/CPInfo/get/' + id + '.koala').done(function (json) {
                            json = json.data;
                            var elm;
                            for (var index in json) {
                                elm = dialog.find('#' + index + 'ID');
                                debugger;
                                if (elm.hasClass('select')) {
                                    if (index == '') {
                                        elm.setValue(json[index]['id']);
                                    } else if (index == 'customerDirectDTO') {
                                        elm.setValue(json[index]['id']);
                                    } else if (index == 'customerIndirectDTO') {
                                        elm.setValue(json[index]['id']);
                                    } else {
                                        elm.setValue(json[index]);
                                    }
                                } else {
                                    elm.val(json[index]);
                                    if (index == 'assistProductionManagerDTO') {
                                        elm.html(json[index]['name']);
                                        dialog.find('#' + index + 'ID_').val(json[index]['id']);
                                    } else if (index == 'keyQuantityManagerDTO') {
                                        elm.html(json[index]['name']);
                                        dialog.find('#' + index + 'ID_').val(json[index]['id']);
                                    } else if (index == 'assistQuantityManagerDTO') {
                                        elm.html(json[index]['name']);
                                        dialog.find('#' + index + 'ID_').val(json[index]['id']);
                                    } else if (index == 'keyTDEManagerDTO') {
                                        elm.html(json[index]['name']);
                                        dialog.find('#' + index + 'ID_').val(json[index]['id']);
                                    } else if (index == 'assistTDEManagerDTO') {
                                        elm.html(json[index]['name']);
                                        dialog.find('#' + index + 'ID_').val(json[index]['id']);
                                    } else if (index == 'keyProductionManagerDTO') {
                                        elm.html(json[index]['name']);
                                        dialog.find('#' + index + 'ID_').val(json[index]['id']);
                                    }
                                }
                            }
                        });
                        dialog.find('#customerProductNumberID').on('change', function () {
                            var customerValue = dialog.find('#customerProductNumberID').val();
                            dialog.find('#shipmentProductNumberID').val(customerValue);
                        })

                        dialog.find('#save').on('click', {grid: grid}, function (e) {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            $.post('${pageContext.request.contextPath}/CPInfo/update.koala?id=' + id, dialog.find('form').serialize()).done(function (result) {
                                if (result.success) {
                                    dialog.modal('hide');
                                    e.data.grid.data('koala.grid').refresh();
                                    e.data.grid.message({
                                        type: 'success',
                                        content: '保存成功'
                                    });
                                } else {
                                    dialog.find('.modal-content').message({
                                        type: 'error',
                                        content: result.actionError
                                    });
                                }
                            });
                        });
                    });
                },
                initPage: function (form) {
                    var selectItems = {};

                    // 报警指标分类WarningType，下拉框内容
                    var warningTypeContents = [{title: '请选择', value: ''}];
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/WarningType.koala',
                        type: 'GET',
                        dataType: 'json',
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            warningTypeContents.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                    });
                    selectItems['warningTypeID'] = warningTypeContents;

                    // 晶圆尺寸WaferSize，下拉框内容
                    var waferSizeContents = [{title: '请选择', value: ''}];
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/WaferSize.koala',
                        type: 'GET',
                        dataType: 'json',
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            waferSizeContents.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                    });
                    selectItems['waferSizeID'] = waferSizeContents;

                    // 报警指标分类，下拉框内容
                    var warningTypeContents = [{title: '请选择', value: ''}];
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/warningType.koala',
                        type: 'GET',
                        dataType: 'json',
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            warningTypeContents.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                    });
                    selectItems['warningTypeID'] = warningTypeContents;


                    // 填充客户列表
                    var contents = [{
                        title: '请选择',
                        value: ''
                    }];
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/Customer/findCustomer.koala',
                        type: 'POST',
                        dataType: 'json'
                    }).done(function (msg) {
                        $.each(msg, function () {
                            contents.push({
                                title: this.chineseName,
                                value: this.id
                            });
                            selectItems['customerDirectDTOID'] = contents;
                            selectItems['customerIndirectDTOID'] = contents;
                        })
                    });

                    <%--var contents = [{
                        title: '请选择',
                        value: ''
                    }];
                    $.ajax({
                        async: false,
                        url: '${pageContext.request.contextPath}/employee/getEmployeesIdAndName.koala',
                        type: 'GET',
                        dataType: 'json',
                    }).done(function (msg) {
                        debugger
                        for (var i = 0; i < msg.length; i++) {
                            contents.push({
                                title: msg[i]['name'],
                                value: msg[i]['id']
                            });
                        }
                    });
                    selectItems['keyProductionManagerDTOID'] = contents;
                    selectItems['assistProductionManagerDTOID'] = contents;
                    selectItems['keyQuantityManagerDTOID'] = contents;
                    selectItems['assistQuantityManagerDTOID'] = contents;
                    selectItems['keyTDEManagerDTOID'] = contents;
                    selectItems['assistTDEManagerDTOID'] = contents;--%>

                    form.find('.form_datetime').datetimepicker({
                        language: 'zh-CN',
                        format: "yyyy-mm-dd",
                        autoclose: true,
                        todayBtn: true,
                        minView: 2,
                        pickerPosition: 'bottom-left'
                    }).datetimepicker('setDate', new Date());//加载日期选择器
                    form.find('.select').each(function () {
                        var select = $(this);
                        var idAttr = select.attr('id');
                        select.select({
                            title: '请选择',
                            contents: selectItems[idAttr]
                        }).on('change', function () {
                            form.find('#' + idAttr + '_').val($(this).getValue());
                        });
                    });
                    $.get(contextPath + '/organization/organizationEmployeeTree.koala').done(function (treeData) {//和后台交互一次数据复用
                        form.find("#ManagerDTOID").find("button").on('click', function () {
                            authorizationMember = $(this);
                            var idAttrButton = $(this).attr('id');
                            $.get(contextPath + '/pages/domain/TestProgramTemplate-authorize.jsp').done(function (data) {
                                debugger;
                                departmentId = [];
                                departmentName = '';
                                var memberTreeDialog = $(data);
                                memberTreeDialog.find('.modal-body').css({height: '325px'});
                                memberTree = memberTreeDialog.find('.tree');
                                loadmemberTree(treeData);
                                memberTreeDialog.find('#confirm').on('click', function () {
                                    debugger;
                                    if ((departmentName == '')) {
                                        departmentId = personId;
                                        departmentName = personHtml;
                                    }
                                    memberTreeDialog.modal('hide');
                                    //form.find('#' + authorizationMember.id + '_').val(departmentId);
                                    form.find('#' + idAttrButton + '_').val(departmentId);
                                    authorizationMember.html(departmentName);
                                    debugger;
                                    authorizationMember.trigger('keydown');
                                }).end().modal({
                                    backdrop: false,
                                    keyboard: false
                                }).on({
                                    'hidden.bs.modal': function () {
                                        $(this).remove();
                                    }
                                });
                            });
                        });
                    })
                },
                remove: function (ids, grid) {
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/CPInfo/delete.koala', data).done(function (result) {
                        if (result.success) {
                            grid.data('koala.grid').refresh();
                            grid.message({
                                type: 'success',
                                content: '删除成功'
                            });
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.result
                            });
                        }
                    });
                },
                exportExcel: function (ids, grid) {
                    debugger;
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/CPInfo/exportExcel.koala', data).done(function (result) {
                        debugger;
                        if (result.success) {
                            grid.data('koala.grid').refresh();
                            grid.message({
                                type: 'success',
                                content: '导出成功'
                            });
                            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">导出Excel</h4></div><div class="modal-body"><p id="downloadID"></p></div><div class="modal-footer"><button type="button" class="btn btn-success" data-dismiss="modal" id="save">确定</button></div></div></div></div>');
                            dialog.modal({
                                keyboard: false
                            }).on({
                                'hidden.bs.modal': function () {
                                    $(this).remove();
                                }
                            }).find('#downloadID').html("导出成功！" + "<a style='margin-left:20px;'   href='" + result.data + "'>点击下载</a>");
                        } else {
                            grid.message({
                                type: 'error',
                                content: result.errorMessage
                            });
                        }
                    });
                }
            };
            PageLoader.initSearchPanel();
            PageLoader.initGridPanel();
            form.find('#search').on('click', function () {
                debugger;
                if (!Validator.Validate(document.getElementById("<%=formId%>"), 3))return;
                var params = {};
                form.find('input').each(function () {
                    debugger;
                    var $this = $(this);
                    var name = $this.attr('name');
                    if (name) {
                        params[name] = $this.val();
                    }
                });
                grid.getGrid().search(params);
            });
            grid.find('.grid-table-body').find('table').off('click').on('click', function (e) {
                //debugger;
                e.stopPropagation();
                $('#connect').find('.active').click();
            });
        });

        var openDetailsPageOfCPInfo = function (id) {
            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
            $.get('<%=path%>/CPInfo-view.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);
                $.get('${pageContext.request.contextPath}/CPInfo/get/' + id + '.koala').done(function (json) {
                    json = json.data;
                    var elm;
                    for (var index in json) {
                        if (json[index] + "" == "false") {
                            dialog.find('#' + index + 'ID').html("<span style='color:#d2322d' class='glyphicon glyphicon-remove'></span>");
                        } else if (json[index] + "" == "true") {
                            dialog.find('#' + index + 'ID').html("<span style='color:#47a447' class='glyphicon glyphicon-ok'></span>");
                        } else {
                            dialog.find('#' + index + 'ID').html(json[index] + "");
                        }
                    }
                });
                dialog.modal({
                    keyboard: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    }
                });
            });
        }

        var showUEditor = function (cpinfoId, currentSite, totalSites) {
            createmodalwindow("UEditor", 800, 500, '<%=contextPath %>/cpRuncardController/getUEditorPage.koala?cpinfoId=' +
                    cpinfoId + '&currentSite=' + currentSite + "&totalSites=" + totalSites,renderProcess);
        };

        var showRuncardInfo = function () {

            var cpinfoId = grid.getGrid().selectedRowsIndex();
            if (cpinfoId.length != 1) {
                if (cpinfoId.length == 0) {
                    $(grid).message({
                        type: 'warning',
                        content: '请选择一条产品记录'
                    });
                } else {
                    $(grid).message({
                        type: 'warning',
                        content: '请只选择一条产品记录'
                    });
                }

                return;
            }
            cpinfoId = cpinfoId[0];
            $.get('${pageContext.request.contextPath}/CPInfo/findProcessTemplateByInternalProductId/' + cpinfoId + '.koala').done(function (data) {
                data = data.data;
                var totalSites = "";
                var str = data['content'].split("|");
                for (var i = 0; i < str.length; i++) {
                    totalSites = totalSites + str[i] + ','
                }

                $.get('${pageContext.request.contextPath}/cpRuncardController/isRuncardFinished.koala?cpinfoId=' + cpinfoId + '&totalSites=' + totalSites
                ).done(function (data) {
                    success = data['success'];
                    if (success == false) {

                        grid.message({
                            type: 'error',
                            content: 'Runcard没有填写完成'
                        })
                        return;
                    }

                    createmodalwindow("UEditor", 800, 500, '<%=contextPath %>/cpRuncardController/getUEditorPage.koala?cpinfoId=' +
                            cpinfoId + '&currentSite=ALL' + '&totalSites=ALL');
                })
            })

        };


    </script>
</head>
<body>
<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
    <!-- search form -->
    <form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <div class="form-group">
                        <label class="control-label" style="width:110px;float:left;">所属客户(编号):&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <div class="btn-group select" id="numberID"></div>
                            <input type="hidden" id="numberID_" name="customerDirectDTO.number" class="form-control"/>
                        </div>
                    </div>
                </td>
                <td>
                    <button id="search" type="button" style="position:relative;margin-left:100px;margin-bottom:20px;"
                            class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;查询
                    </button>
                </td>
            </tr>
        </table>
    </form>
    <!-- grid -->
    <div id=<%=gridId%>></div>
    <div style="height: 400px; width: auto;over-flow:auto">
        <ul class="nav nav-tabs" id='connect'>
            <li class="active" onclick='renderTestProgram()'><a data-toggle='tab' href="#testProgram_CPInfo">测试程序</a>
            </li>
            <li onclick='renderSBL()'><a data-toggle='tab' href="#SBL">SBL</a></li>
            <li onclick="renderProcess()"><a data-toggle='tab' href="#Process">Process</a></li>
            <li onclick="showRuncardInfo()"><a data-toggle='tab'>查看RunCard</a></li>
            <li onclick="renderLabel()"><a data-toggle='tab' href="#Label">标签模板</a></li>
            <li onclick="renderRawData()"><a data-toggle='tab' href="#RawData">Raw Data</a></li>
        </ul>
        <div class="tab-content" style="height: 350px;overflow: auto;">
            <div id="testProgram_CPInfo" class="tab-pane fade active in"
                 style="padding: 10px; text-align: center; width: auto;">
                <div style="background-color: #fff; width: 1250px; margin-top: 10px; margin-left: 20px;" id="pt"
                     class="table-responsive"></div>
            </div>
            <div id="SBL" class="tab-pane fade" style="padding: 10px;">
                <form name="sbls" id='sblForm_CPInfo' style="margin-top: 10px;">
                    <div id="sbl_CPInfo">
                        <table class="table table-bordered" style="width:85%;">
                            <thead>
                            <tr>
                                <td style="width: 5%;">规则</td>
                                <td>良率下限</td>
                                <td>良率上限</td>
                                <td>Bin别/合并Bin别</td>
                                <td>H/S</td>
                                <td>使用范围</td>
                                <td>卡控形式</td>
                                <td>Pass/Fail</td>
                                <td>站点</td>
                                <td></td>
                            </tr>
                            </thead>
                            <tbody id="sblTBody_CPInfo">
                            </tbody>
                        </table>
                        <input type="button" style="display: block; margin-bottom: 20px; margin-top: 10px;" id="addLine"
                               class="btn btn-primary" value="+"/>
                        <input type="button" class="btn btn-primary" value="清空" id="clear" onclick="clearSBL()"/>
                        <input type="button" style="float:right;" class="btn btn-primary" value="保存"
                               onclick="saveSBL()"/>
                    </div>
                </form>
            </div>
            <div id="Process" class="tab-pane fade" style="padding: 10px;">
                <form style="margin-top: 10px;">
                    <div>
                        <label for="processHandlerTypeSelect">机械手类型：</label>
                        <select id="processHandlerTypeSelect"
                                style="width: 140px;display:inline"
                                class='form-control'>
                            <option selected="selected" class="form-control">请选择</option>
                            <option value="TurnTower">TurnTower</option>
                            <option value="P&P">P&P</option>
                            <option value="Gravity">Gravity</option>
                            <option value="Prober">Prober</option>
                            <option value="Turn">Turn</option>
                        </select>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <label for="processSelect_CPInfo">选择ProcessMap：</label>
                        <select id="processSelect_CPInfo"
                                style="width: 140px;display:inline"
                                class='form-control'>
                        </select> &nbsp;&nbsp;&nbsp;&nbsp;
                        <input id="bindProcess" type="button" value="确认" class="btn btn-primary"/>
                        <input id="clearProcess" type="button" class="btn btn-primary" value="清空"
                               style="margin-left: 20px;"/>
                        <input id="superform" type="button" class="btn btn-primary" value="特殊表单"
                               style="margin-left: 20px;"/>
                        <input id="confirmProcess" type="button" class="btn btn-danger" value="Runcard签核"
                               style="margin-left: 20px;"/>
                    </div>
                    <div id="processBody_CPInfo"></div>
                    <div class="clear">
                        <button type="button" class="btn btn-primary" onclick="openProcessTemplateTab()">新增Process
                        </button>
                        <button type="button" class="btn btn-primary" onclick="openProcessTemplateTab()">修改Process
                        </button>
                        <button type="button" class="btn btn-primary" onclick="showRuncardInfo()">查看RunCard</button>
                        <%-- <ul id="ulfoot">
                            <li class="fudong2" data-target="${pageContext.request.contextPath}/pages/domain/ProcessTemplate-list.jsp" data-role="openTab" data-title="新增Process" data-mark="menuMark195">
                        <a href="##menuMark195" id="pp" onclick="showprocess()"><span>新增Process</span></a>	</li>
                            <li class="fudong2" data-target="${pageContext.request.contextPath}/pages/domain/ProcessTemplate-list.jsp" data-role="openTab" data-title="修改Process" data-mark="menuMark195"><a href="##menuMark195" id="pp" onclick="showprocess()" title="修改process"><span>修改Process</span></a>	</li>
                            <li class="fudong2"><a href="#"><span>查看RunCard</span></a></li>
                        </ul> --%>
                    </div>
                </form>
            </div>
            <div id="Label" class="tab-pane fade">
                <form name="productLabel" style="margin-top: 20px;">
                    <div>
                        <span>标签类型：</span>
                        <select onchange="getLabelTypes(this.value)"
                                style="width: 140px;display:inline" class='form-control'
                                id="labelTemplateTypesSelect">
                            <option selected="selected">请选择</option>
                            <option value="FT">FT</option>
                            <option value="CP">CP</option>
                        </select> <span> 标签名：</span>
                        <select style="width: 140px;display:inline" class='form-control'
                                id="labelTemplateNamesSelect">
                            <option selected="selected">请选择</option>
                        </select>
                        <input type="button" value="确认" class="btn btn-primary" onclick="saveLabel()"/>
                        <input type="button" value="清空" class="btn btn-primary" onclick="clearLabel()"/>
                    </div>
                    <div style="background-color: #fff; width: 800px; margin-top: 10px; margin-left: 2px; text-align: center"
                         id="labelBody_CPInfo"></div>
                </form>
            </div>
            <div id="RawData" class="tab-pane fade">
                <form name="RowDataform" style="margin-top: 20px;">

                    <div id="buttonArea" class="tab-pane fade active in">
                        <table class="table table-bordered" style="width:90%">
                            <tr>
                                <input type="button" value="新增" style="margin-left: 15px;" class="btn btn-primary"
                                       onclick="addRawData()"/>
                                <input type="button" value="删除" style="margin-left: 15px;" class="btn btn-primary"
                                       onclick="deleteRawData()"/>
                                <input type="button" value="修改" style="margin-left: 15px;" class="btn btn-primary"
                                       onclick="updateRawData()"/>
                            </tr>
                        </table>
                    </div>
                    <div id="RawDataBody" class="tab-pane fade  active in">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

