<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
    <script type="text/javascript" src="<%=contextPath %>/js/common.js"></script>
    <LINK rel="stylesheet" type="text/css"
          href="<%=contextPath %>/js/easyui/themes/default/easyui.css"/>
    <script type="text/javascript"
            src="<%=contextPath %>/js/easyui/jquery.easyui.min.1.2.2.js"></script>
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

        #processTemplateBody {
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


    <%@ page import="java.util.Date" %>
    <%
        String formId = "form_" + new Date().getTime();
        String gridId = "grid_" + new Date().getTime();
        String path = request.getContextPath()
                + request.getServletPath().substring(0,
                request.getServletPath().lastIndexOf("/") + 1);
    %>

    <script>
        <%--totalType runcard的所有站点信息 以,分隔--%>
        function callback(id) {
            alert(id)
        }

        var showUEditor = function (ftinfoId, currentSite, totalSites) {
            createmodalwindow("UEditor", 800, 500, '<%=contextPath %>/ueditor/getUEditorPage.koala?ftinfoId=' +
                    ftinfoId + '&currentSite=' + currentSite + "&totalSites=" + totalSites, showProcessTemplate);
        };

        var showRuncardInfo = function () {

            var ftinfoId = grid.getGrid().selectedRowsIndex();
            if (ftinfoId.length != 1) {
                if (ftinfoId.length == 0) {
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
            ftinfoId = ftinfoId[0];
            $.get('${pageContext.request.contextPath}/FTInfo/getProcessTemplateContentByProduct/' + ftinfoId + '.koala').done(function (data) {
                data = data.data;
                var totalSites = "";
                var str = data.split("|");
                for (var i = 0; i < str.length; i++) {
                    totalSites = totalSites + str[i] + ','
                }

                $.get('${pageContext.request.contextPath}/ueditor/isRuncardFinished.koala?ftinfoId=' + ftinfoId + '&totalSites=' + totalSites
                ).done(function (data) {
                    success = data['success'];
                    if (success == false) {

                        grid.message({
                            type: 'error',
                            content: 'Runcard没有填写完成'
                        })
                        return;
                    }

                    createmodalwindow("UEditor", 800, 500, '<%=contextPath %>/ueditor/getUEditorPage.koala?ftinfoId=' +
                            ftinfoId + '&currentSite=ALL' + '&totalSites=ALL');

                })
            })

        };
    </script>
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


        function deleteRow1(r) {
            var tr = r.parentNode.parentNode;
            tr.parentNode.removeChild(tr);
        }

        // 跳转到ProcessTemplate的Tab中
        var jumpProcessTemplateTab = function () {
            var pp = $("#pp").html();
            var thiz = $(this);
            var mark = thiz.attr('mark');
            mark = openTab('/pages/domain/ProcessTemplate-list.jsp', "process管理", mark);
            if (mark) {
                thiz.attr("mark", mark);
            }
        };

        // 显示TestProgram表
        function showTestPrograms(id) {
            $("#programTestingBody").html('');
            var ids = grid.getGrid().selectedRowsIndex();
            if (ids.length != 1) {
                $(grid).message({
                    type: 'warning',
                    content: '请只一条产品记录'
                });
                return;
            }
            var id = parseInt(ids[0]);
            $.get('${pageContext.request.contextPath}/FTInfo/getTestProgramByProduct/' + id + '.koala').done(function (data) {
                var html = "";
                var th = "<table class='table table-bordered'  style='background-color:#fff;margin-top:10px'><tr height='30px'><td width='20px'></td><td>Customer_No</td><td>version</td><td>测试程序</td><td>程序版本</td><td>可用机台</td><td>标注</td><td>授权时间</td><td>理论UPH</td><td>实际UPH</td><td>IS_YELLOW</td><td>授权人</td><td>站点</td></tr>";
                html += th;
                if (data.length > 0) {
                    $.each(data, function (index) {
                        var number = index + 1
                        html += "<tr height='30px'><td>" + number + "</td><td>" +
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
                }
                html += "</table>";
                $("#programTestingBody").html(html);
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
            $.get('${pageContext.request.contextPath}/FTInfo/getProcessTemplateContentByProduct/' + id + '.koala').done(function (data) {
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

                var siteOptions = [1, 2, 3, 4, 5, 6].map(function (value) {
                    return "<option value=" + value + ">" + value + "</option>";
                }).join("");

                var index = $("#sblTemplateBody").find("tr:last").index() + 1;
                $('#sblTemplateBody').append("<tr><td>"
                        + "<div><span>Bin</span><input type='text' name='sblcount" + index + "' style='width: 85%;display:inline;' class='form-control'/></div>"
                        + "</td>"
                        + "<td><input type='text' name='bottomLimit" + index + "'  class='form-control'/></td>"
                        + "<td><input type='text' name='topLimit" + index + "'  class='form-control'/></td>"
                        + "<td><select id='selectslbpingzhi" + index + "' class='form-control'><option name='fail' value='fail'  class='form-control'>fail</option>"
                        + "<option name='pass' value='pass'  class='form-control'>pass</option></select></td>"
                        + "<td><select id='selectslbSite" + index + "' class='form-control'>" + siteOptions + "</select></td>"
                        + "<td><select id='selectSBLNode" + index + "' class='form-control'>" + nodeOptions + "</select></td>"
                        + "<td style='height:32px;'><input type='button'  style='display: block;margin-left:5px; ' id='subLine' class='btn btn-primary' value='-' onclick='deleteRow1(this)'></td>"
                        + "</tr>");
            });
        }

        // 显示SBLSetting页面
        function showSBLTemplates() {
            $("#sblTemplateBody").html('');
            var ids = grid.getGrid().selectedRowsIndex();
            if (ids.length != 1) {
                $(grid).message({
                    type: 'warning',
                    content: '请只一条产品记录'
                });
                return;
            }
            var id = parseInt(ids[0]);
            $.get('${pageContext.request.contextPath}/FTInfo/getProcessTemplateContentByProduct/' + id + '.koala').done(function (data) {
                        var content = data.data;
                        if (content == null || content == undefined) {
                            return;
                        }
                        var nodeOptions = content.split("|").filter(function (value) {
                            return value.startsWith("Test-");
                        }).map(function (value) {
                            return "<option value=" + value + ">" + value + "</option>"
                        }).join("");

                        var siteOptions = [1, 2, 3, 4, 5, 6].map(function (value) {
                            return "<option value=" + value + ">" + value + "</option>";
                        }).join("");

                        $.get('${pageContext.request.contextPath}/FTInfo/getSBLTemplatesByProduct/' + id + '.koala').done(function (sblTemplateDTOs) {
                            $('#sblTemplateBody').empty();
                            sblTemplateDTOs.forEach(function (sblTemplateDTO, index) {
                                if (sblTemplateDTO == null) {
                                    return;
                                }
                                $('#sblTemplateBody').append("<tr><td>"
                                        + "<div><span>Bin</span><input type='text' name='sblcount" + index + "' style='width: 85%;display:inline;' class='form-control'/></div>"
                                        + "</td>"
                                        + "<td><input type='text' name='bottomLimit" + index + "'  class='form-control'/></td>"
                                        + "<td><input type='text' name='topLimit" + index + "'  class='form-control'/></td>"
                                        + "<td><select id='selectslbpingzhi" + index + "' class='form-control'><option name='fail' value='fail'  class='form-control'>fail</option>"
                                        + "<option name='pass' value='pass'  class='form-control'>pass</option></select></td>"
                                        + "<td><select id='selectslbSite" + index + "' class='form-control'>" + siteOptions + "</select></td>"
                                        + "<td><select id='selectSBLNode" + index + "' class='form-control'>" + nodeOptions + "</select></td>"
                                        + "<td style='height:32px;'><input type='button'  style='display: block;margin-left:5px; ' id='subLine' class='btn btn-primary' value='-' onclick='deleteRow1(this)'></td>"
                                        + "</tr>");
                                $("input[name= " + 'sblcount' + index + "]").val(sblTemplateDTO['binType'].substring(3));
                                $("input[name= " + 'bottomLimit' + index + "]").val(sblTemplateDTO['lowerLimit']);
                                $("input[name= " + 'topLimit' + index + "]").val(sblTemplateDTO['upperLimit']);
                                $("#selectslbSite" + index).val(sblTemplateDTO["site"]);
                                $('#selectslbpingzhi' + index + '').val(sblTemplateDTO['binQuality']);
                                $("#selectSBLNode" + index).val(sblTemplateDTO["nodeName"]);
                                count = sblTemplateDTOs.length + 1;
                            });
                        });
                    }
            );
        }

        // 清除SBLSettings
        function clearSBLTemplates() {
            var id = $(".grid-body .checked").eq(0).attr("data-value");
            if (id == undefined || isNaN(id)) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            $.ajax({
                url: "${pageContext.request.contextPath}/FTInfo/clearSBLTemplates/" + id + ".koala",
                dataType: "json",
                success: function (data) {
                    $(grid).message({
                        type: 'success',
                        content: '清除成功'
                    });
                    $('#sblTemplateBody').empty();
                    count = 1;
                    j = 0;
                    // setTimeout('sblclear11()',1000);
                }
            });
        }

        // SBL保存
        function bindSBLTemplates() {
            // return true;
            var id = grid.getGrid().selectedRowsIndex()[0];
            if (id == undefined || isNaN(id)) {
                $("body").message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return false;
            }
            for (var i = 0; i < document.sbl.elements.length - 1; i++) {
                if (document.sbl.elements[i].value == "") {
                    $("body").message({
                        type: 'warning',
                        content: '请填写完整'
                    });
                    document.sbl.elements[i].focus();
                    return false;
                }
            }
            var sblflag = false;
            $("#sblform").find("input[type='text']").each(function () {
                if ($(this).val() < 0 || $(this).val() > 101) {
                    $("body").message({
                        type: 'warning',
                        content: '请核对数据'
                    });
                    $(this).focus();
                    sblflag = true;
                }
                if (isNaN($(this).val())) {
                    $("body").message({
                        type: 'warning',
                        content: '请核对数据'
                    });
                    $(this).focus();
                    sblflag = true;
                }
            });
            if (sblflag)return;
            // debugger;
            var sblTemplates = [];
            //$("#sblSettingBody").children("tr").length
            for (var index = 0; index < count - 1; index++) {
                var sblTemplate = {};
                //$("#sblSettingBody").children("tr").eq(0).find("input[name^='sblcount']");
                sblTemplate['site'] = $("#selectslbSite" + index + " option:selected").val();
                sblTemplate['upperLimit'] = $("input[name= " + 'topLimit' + index + "]").val();
                sblTemplate['lowerLimit'] = $("input[name= " + 'bottomLimit' + index + "]").val();
                sblTemplate['binType'] = "Bin" + $("input[name= " + 'sblcount' + index + "]").val();
                sblTemplate['binQuality'] = $("#selectslbpingzhi" + index + " option:selected").val();
                sblTemplate["nodeName"] = $("#selectSBLNode" + index + " option:selected").val();

                if (sblTemplate['upperLimit'] == undefined && sblTemplate['lowerLimit'] == undefined) {
                    break;
                }
                sblTemplates.push(sblTemplate);
            }
            $.post("${pageContext.request.contextPath}/FTInfo/bindSBLTemplates.koala",
                    {
                        "internalProductId": id,
                        "sblTemplates": JSON.stringify(sblTemplates)
                    })
                    .done(function (result) {
                        if (!result['success']) {
                            $(grid).message({
                                type: 'success',
                                content: '绑定失败'
                            });
                            return;
                        }
                        $(grid).message({
                            type: 'success',
                            content: '绑定成功'
                        });
                        showSBLTemplates();
                    });
        }

        function showEQCSettings() {
            $("#eqcSettingBody").html('');
            var ids = grid.getGrid().selectedRowsIndex();
            if (ids.length != 1) {
                $(grid).message({
                    type: 'warning',
                    content: '请只一条产品记录'
                });
                return;
            }
            var id = parseInt(ids[0]);

            $.get('${pageContext.request.contextPath}/FTInfo/getEQCSettingsByProduct/' + id + '.koala').done(function (data) {
                $.get('${pageContext.request.contextPath}/FTInfo/getProcessTemplateContentByProduct/' + id + '.koala').done(function (msg) {
                    msg = msg.data;
                    if (msg == null || msg == undefined) {
                        $(grid).message({
                            type: 'warning',
                            content: '请先绑定Process'
                        });
                        return;
                    }
                    var nodeOptions = msg.split("|").filter(function (value) {
                        return value.startsWith("Test-");
                    }).map(function (value) {
                        return "<option value=" + value + ">" + value + "</option>"
                    }).join("");
                    $('#eqcSettingBody').empty();
                    //	var data= eval('(' + data + ')');
                    $.each(data, function (a) {
                        if (data == "") {

                            $('#eqcSettingBody').html();
                        } else {
                            $('#eqcSettingBody').append("<tr>"
                                    + "<td><input type='text' name='eqcqty" + a + "' class='form-control'/> </td>"
                                    + "<td><input type='text' name='eqcbottomLimit" + a + "' class='form-control'  /></td>"
                                    + "<td><input type='text' name='eqctopLimit" + a + "'  class='form-control'/></td>"
                                    + "<td><select name='eqcstatus" + a + "' class='form-control'>" + nodeOptions + "</select></td>"
                                    + "<td style='height:32px;'><input type='button'  style='display: block;margin-left:5px; ' id='subLine' class='btn btn-primary' value='-' onclick='deleteRow1(this)'></td>"
                                    + "</tr>");
                            debugger;
                            $("input[name= '" + "eqcqty" + a + "']").val(data[a]['qty']);
                            $("input[name= '" + "eqcbottomLimit" + a + "']").val(data[a]['lowerLimit']);
                            $("input[name= '" + "eqctopLimit" + a + "']").val(data[a]['upperLimit']);
                            $("select[name= '" + "eqcstatus" + a + "']").val(data[a]['nodeName']);
                        }
                    });
                });
            })


        }
        function myCheckeqc() {
            for (var i = 0; i < document.eqc.elements.length; i++) {
                if (document.eqc.elements[i].value == "") {
                    $("body").message({
                        type: 'warning',
                        content: '请填写完整'
                    });
                    document.eqc.elements[i].focus();
                    return false;
                }
            }
            var id = grid.getGrid().selectedRowsIndex()[0];
            if (id == undefined || isNaN(id)) {
                $("body").message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            } else {
                var eqcflag = false;
                $("#eqcform").find("input[type='text']").each(function () {
                    if (isNaN($(this).val())) {
                        $("body").message({
                            type: 'warning',
                            content: '请核对数据'
                        });
                        $(this).focus();
                        eqcflag = true;
                    }
                });
                if (eqcflag)return;
                //var c = document.eqc.elements.length / 3;
                // debugger;
                var eqcSettings = [];
                for (var a = 0; a < $("#eqcSettingBody tr").length; a++) {
                    var eqcSetting = {};//$("input[name= " + 'eqcbottomLimit' + "]").val(0);
                    eqcSetting['upperLimit'] = $("#eqcSettingBody tr").eq(a).find("input[name^= 'eqctopLimit']").val();
                    eqcSetting['lowerLimit'] = $("#eqcSettingBody tr").eq(a).find("input[name^= 'eqcbottomLimit']").val();
                    eqcSetting['nodeName'] = $("#eqcSettingBody tr").eq(a).find("select[name^= 'eqcstatus']").val();
                    var internalProductDTO = {};
                    internalProductDTO['id'] = id;
                    eqcSetting['internalProductDTO'] = internalProductDTO;
                    eqcSetting['qty'] = $("#eqcSettingBody tr").eq(a).find("input[name^='eqcqty']").val();
                    eqcSettings[a] = eqcSetting;
                }
                debugger;
                /*                 for (var index in eqcSettings) {
                 if (eqcSettings[index]['upperLimit'] == undefined && eqcSettings[index]['lowerLimit'] == undefined) {
                 eqcSettings.splice(index, 1)
                 }
                 } */
                //var a=JSON.stringify(json);
                $.post('${pageContext.request.contextPath}/EQCSetting/add.koala',
                        {"eqcSettings": JSON.stringify(eqcSettings)}).done(function (json) {
                    // debugger;
                    if (json['success'] == true) {
                        $("body").message({
                            type: 'success',
                            content: '绑定成功'
                        });
                        showEQCSettings();
                    }
                    //}

                })
            }
        }

        //eqc清空
        function eqcclear1() {
            // debugger;
            var id = $(".grid-body .checked").eq(0).attr("data-value");
            if (id == undefined) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            else {
                // debugger;
                $.ajax({
                    url: "${pageContext.request.contextPath}/FTInfo/clearEQCSettings/" + id + ".koala",
                    dataType: "json",
                    success: function (data) {
                        debugger;

                        $(grid).message({
                            type: 'success',
                            content: '清除成功'
                        });
                        $('#eqcSettingBody').empty();
                        // window.location.reload();
                        i = 0;
                    }
                });
            }
        }

        function showProcessTemplate() {
            $("#processTemplateBody").html('');
            var ids = grid.getGrid().selectedRowsIndex();
            if (ids.length != 1) {
                $(grid).message({
                    type: 'warning',
                    content: '请只一条产品记录'
                });
                return;
            }
            var id = parseInt(ids[0]);
            $.get('${pageContext.request.contextPath}/SystemDictionary/getByType/HandlerType.koala').done(function (data) {
                $("#jitai").empty();
                $("#jitai").append("<option>请选择</option>")
                $.each(data, function (a) {
                    $("#jitai").append("<option value='" + data[a]['value'] + "'>" + data[a]['value'] + "</option>");
                });
            });
            $.get('${pageContext.request.contextPath}/FTInfo/findProcessTemplateByInternalProductId/' + id + '.koala').done(function (data) {
                data = data.data;
                // for(var a in data){
                if (data == null) {
                    $('#processTemplateBody').html();
                } else {
                    var str = data['content'].split("|");
                    var totalType = "";
                    for (var i = 0; i < str.length; i++) {
                        totalType = totalType + str[i] + ','
                    }

                    $.get('${pageContext.request.contextPath}/ueditor/getRuncardFinishedStatus.koala?ftinfoId='+
                            id + '&totalSites='+ totalType).done(function (json) {
                        var result = json["data"];
                        debugger
                        if (data['testingTemplateDTO'] == null) {
                            var html = "";
                            html += "<table class='table table-bordered'  style='background-color:#fff;text-align:center;'>";
                            html += "<tr height='30px'>";
                            var str = data['content'].split("|");
                            var totalType = "";
                            for (var i = 0; i < str.length; i++) {
                                totalType = totalType + str[i] + ','
                            }
                            var count = 1;
                            for (var i = 0; i < str.length; i++) {
                                //一个个Test-可以点击
                                if (str[i].match("Test-.+")) {
                                    if (count === 1) {
                                        if (result["siteTest"] === true) {
                                            html += "<td><a  style='color:#000000' href='javascript:showUEditor(" + id + ',' + '"' + str[i] + '"' + ',' +
                                                    '"' + totalType + '"' +
                                                    ")'>" + str[i] + "</a></td>";
                                            count++;
                                        }else {
                                            html += "<td><a style='color:#FF3333' href='javascript:showUEditor(" + id + ',' + '"' + str[i] + '"' + ',' +
                                                    '"' + totalType + '"' +
                                                    ")'>" + str[i] + "</a></td>";
                                            count++;
                                        }

                                        continue;
                                    } else {
                                        html += '<td style="color:#888888">' + str[i] + "</td>";
                                        continue;
                                    }
                                }

                                if (str[i] === 'Finish') {
                                    html += '<td style="color:#888888">' + str[i] + "</td>";
                                } else if (str[i] === 'GuTest') {
                                    html += '<td style="color:#888888">' + str[i] + "</td>";
                                } else {
                                    if (result[str[i]] === true) {
                                        html += "<td ><a style='color:#000000' href='javascript:showUEditor(" + id + ',' + '"' + str[i] + '"' + ',' +
                                                '"' + totalType + '"' +
                                                ")'>" + str[i] + "</a></td>";
                                    }else {
                                        html += "<td ><a style='color:#FF3333' href='javascript:showUEditor(" + id + ',' + '"' + str[i] + '"' + ',' +
                                                '"' + totalType + '"' +
                                                ")'>" + str[i] + "</a></td>";
                                    }

                                }
                            }
                            html += "</tr >";
                            html += "</table>";
                            $("#processTemplateBody").html(html);
                        } else {
                            var html = "";
                            html += "<table class='table table-bordered'  style='background-color:#fff;text-align:center;'>";
                            html += "<tr height='30px'>";
                            var str = data['content'].split("|");
                            var str1 = data['testingTemplateDTO']['content'].split("|");
                            // debugger;
                            for (var indexi in str) {
                                // debugger;
                                if (str[indexi] == "test") {
                                    for (var indexj in str1) {
                                        html += "<td><a href='#' style='color:#f00;' >" + str1[indexj] + "</a></td>"
                                    }
                                }
                                else {
                                    html += "<td><a href='#'>" + str[indexi] + "</a></td>";
                                }
                            }
                            html += "</tr >";
                            html += "</table>";
                            $("#processTemplateBody").html(html);
                        }
                    })


                }
            });

        }

        function findProcessByHandler(handlerType) {
            $.get('${pageContext.request.contextPath}/ProcessTemplate/findProcessByHandler/' + handlerType + '.koala').done(
                    function (data) {
                        var $processmap = $("#processmap");
                        $processmap.empty();
                        $processmap.append("<option value='' selected='selected'>请选择</option>");
                        // debugger;
                        $.each(data, function (a) {
                            $processmap.append("<option value='" + data[a]['id'] + "'>" + data[a]['name'] + "</option>");
                        })
                    });
        }

        function bindProcess() {
//            debugger;
            $("#processTemplateBody").html('');
            var ids = grid.getGrid().selectedRowsIndex();
            if (ids.length != 1) {
                $(grid).message({
                    type: 'warning',
                    content: '请只一条产品记录'
                });
                return;
            }
            var id = parseInt(ids[0]);


            $.get('${pageContext.request.contextPath}/ueditor/isRuncardInfoSigned.koala?ftinfoId=' + id).done(function (data) {

                success = data['success'];
                if (success == true) {
                    grid.message({
                        type: 'success',
                        content: 'Runcard已经签核，不可更改测试流程'
                    })
                    return;
                }

                $.post('${pageContext.request.contextPath}/FTInfo/bindProcess.koala', "ftInfoId=" + id + "&processId=" + $("#processmap option:selected").val()).done(function (json) {
                    // debugger;
                    if (json['success'] == true) {
                        $(grid).message({
                            type: 'success',
                            content: '绑定成功'
                        });
                        showProcessTemplate();
                    }
                });
            });


        }
        function dialog(ftinfoId, employeeId, employeeName, runcardSignInfo) {

            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">签核</h4></div>' +
                    '<div class="modal-body"></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');

            var body = $("<div class='form-group'><label class='col-lg-2 control-label'  class='style:display:inline'>" + employeeName + ':</label>' +
                    "<div class='form-group'><label class='col-lg-4 control-label'  class='style:display:inline'>" + runcardSignInfo.departmentName + ':</label>' +
                    "<input id='employeeID' type='hidden' name='id' value=" + employeeId + " />" +
                    "<select style='margin-left:30px;' name='opinion' id='opinionID'><option value=''>请选择</option><option  value ='同意' >同意</option><option value='不同意'>不同意</option></select><div style='margin-top:10px;margin-left:40px'><label class='col-lg-3 control-label'  class='style:display:inline；'>签核备注：</label><textarea name='note' id='noteID' cols='70' rows='5'></textarea></div></div>");

            debugger
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
                debugger;
                var authorInfo = dialog.find('.form-group');
                var data = {
                    'ftinfoId': ftinfoId,
                    'userId': $(authorInfo).find('#employeeID').val(),
                    'opinion': $(authorInfo).find('#opinionID').val(),
                    'note': $(authorInfo).find('#noteID').val(),
                };
                $.ajax({
                    type: 'POST',
                    url: '<%=contextPath %>/ueditor/signRuncardInfo.koala',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(data),
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
        function RuncardCheck() {
//            debugger;


            var ftinfoId = grid.getGrid().selectedRowsIndex();
            if (ftinfoId.length != 1) {
                if (ftinfoId.length == 0) {
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
            ftinfoId = ftinfoId[0];

            var employeeId;
            var employeeName;
            var runcardSignInfo = {};
            $.get('${pageContext.request.contextPath}/FTInfo/getProcessTemplateContentByProduct/' + ftinfoId + '.koala').done(function (data) {
                data = data.data;
                var totalSites = "";
                var str = data.split("|");
                for (var i = 0; i < str.length; i++) {
                    totalSites = totalSites + str[i] + ','
                }

                $.get('${pageContext.request.contextPath}/ueditor/isRuncardFinished.koala?ftinfoId=' + ftinfoId + '&totalSites=' + totalSites
                ).done(function (data) {
                    success = data['success'];
                    if (success == false) {

                        grid.message({
                            type: 'error',
                            content: 'Runcard没有填写完成'
                        })
                        return;
                    }
                    $.get('${pageContext.request.contextPath}/auth/currentUser/getUserDetail.koala').done(function (json) {
                        employeeName = json['data']['name'];
                        employeeId = json['data']['id'];
                        //模拟签核人员
                        $.get('${pageContext.request.contextPath}/ueditor/getDepartmentByEmployeeId.koala?ftinfoId=' + ftinfoId +
                                '&employeeid=' + employeeId).done(function (json) {
                            debugger
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

                            dialog(ftinfoId, employeeId, employeeName, runcardSignInfo);
                            /*
                             $.get('
                            ${pageContext.request.contextPath}/ueditor/isRuncardInfoSigned.koala?ftinfoId=' + ftinfoId).done(function (data) {
                             success = data['success'];
                             if (success == true) {
                             //                            alert("已经签核");
                             grid.message({
                             type: 'success',
                             content: '已经签核'
                             })
                             return;
                             }
                             });
                             */
                        })
                    });
                });


            });
//            debugger

        }

        function specialForm() {
            var ftinfoId = grid.getGrid().selectedRowsIndex();
            if (ftinfoId.length != 1) {
                if (ftinfoId.length == 0) {
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
            ftinfoId = ftinfoId[0];
            debugger
            $.get('${pageContext.request.contextPath}/ueditor/getSpecialFormStatus.koala?ftinfoId=' + ftinfoId).done(function (data) {
                data = data['data'];
                var firstSheetStatus = data['firstSheetStatus'];
                var recordSheetStatus = data['recordSheetStatus'];
                var summarySheetStatus = data['summarySheetStatus'];
                var reelcodeSheetStatus = data['reelcodeSheetStatus'];
                var machineMaterialRecordSheetStatus = data['machineMaterialRecordSheetStatus'];
                var checkSheetStatus = data['checkSheetStatus'];

                var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">特殊表单关联</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                $.get('<%=contextPath %>/ueditor/getSpecialFormPage.koala?&ftinfoId=' + ftinfoId).done(function (html) {
                    debugger
                    dialog.find('.modal-body').html(html);
                    dialog.find("#firstSheetStatus").attr("checked", firstSheetStatus);
                    dialog.find("#recordSheetStatus").attr("checked", recordSheetStatus);
                    dialog.find("#summarySheetStatus").attr("checked", summarySheetStatus);
                    dialog.find("#reelcodeSheetStatus").attr("checked", reelcodeSheetStatus);
                    dialog.find("#machineMaterialRecordSheetStatus").attr("checked", machineMaterialRecordSheetStatus);
                    dialog.find("#checkSheetStatus").attr("checked", checkSheetStatus);
                });

                dialog.modal({
                    keyboard: false
                }).on({
                    'hidden.bs.modal': function () {
                        $(this).remove();
                    }
                });
                dialog.find('#save').on('click', {grid: grid}, function (e) {
                    var reelcodeSheetStatus = dialog.find("#reelcodeSheetStatus").prop("checked");
                    var firstSheetStatus = dialog.find("#firstSheetStatus").prop("checked");
                    var summarySheetStatus = dialog.find("#summarySheetStatus").prop("checked");
                    var recordSheetStatus = dialog.find("#recordSheetStatus").prop("checked");

                    var machineMaterialRecordSheetStatus = dialog.find("#machineMaterialRecordSheetStatus").prop("checked");
                    var checkSheetStatus = dialog.find("#checkSheetStatus").prop("checked");


                    var json = {
                        "ftinfoId": ftinfoId,
                        "reelcodeSheetStatus": reelcodeSheetStatus,
                        "summarySheetStatus": summarySheetStatus,
                        "recordSheetStatus": recordSheetStatus,
                        "machineMaterialRecordSheetStatus": machineMaterialRecordSheetStatus,
                        "checkSheetStatus": checkSheetStatus
                    }

                    $.ajax({
                        type: 'POST',
                        url: '<%=contextPath %>/ueditor/saveSpecialFormStaus.koala',
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


        }

        function clearProcess(ftInfoId) {
            id = grid.getGrid().selectedRowsIndex()[0];
            $.get('${pageContext.request.contextPath}/FTInfo/clearProcess/' + id + '.koala').done(function (data) {

                $(grid).message({
                    type: 'success',
                    content: '清除成功'
                });
                $('#processTemplateBody').empty();
            })

        }

        function showLabel() {
            $("#lb").html('');
            var ids = grid.getGrid().selectedRowsIndex();
            if (ids.length != 1) {
                $(grid).message({
                    type: 'warning',
                    content: '请选择一条产品记录'
                });
                return;
            }
            var id = parseInt(ids[0]);
            $.get('${pageContext.request.contextPath}/FTInfo/findLabelsByInternalProductId/' + id + '.koala').done(
                    function (data) {
                        var html = "";
                        if (data.length < 1) {
                            $("#showLabelsTable").html(html);
                            return;
                        }
                        var th = "<table style='border:#CDBECF 1px solid;' border='1' width='800px'><tr height='30px'><td width='20px'></td><td>标签命名</td><td>标签类型</td></tr>";
                        html += th;
                        $.each(data, function (a) {
                            var b = a;
                            ++b;
                            html += "<tr height='30px'><td>" + b
                                    + " </td><td>"
                                    + data[a]['labelName']
                                    + "</td><td>"
                                    + data[a]["labelType"]
                                    + "</td></tr>";
                        });
                        html += "</table>";
                        $("#showLabelsTable").html(html);
                    });

        }
        function clearLabe(id) {
            if (id == undefined) {
                id = grid.getGrid().selectedRowsIndex()[0];
            }
            $.ajax({
                url: '${pageContext.request.contextPath}/FTInfo/clearLabel/' + id + '.koala',
                dataType: "json",
                success: function (data) {
                    $(grid).message({
                        type: 'success',
                        content: '清除成功'
                    });
                    $("#showLabelsTable").empty();
                    /*                     var th = "<table style='border:#CDBECF 1px solid;' border='1' width='800px'><tr height='30px'><td width='20px'></td><td>标签命名</td><td>标签类型</td></tr></table>";
                     $("#showLabelsTable").html(th); */
                }
            });
        }
        function showLabels(value) {
            var data = [{name: "page", value: 0}, {name: "pagesize", value: 1000}, {name: "testType", value: value}];
            $.post('${pageContext.request.contextPath}/Label/pageJson.koala', data).done(
                    function (json) {
                        json = json.data;
                        var $selectLabelName = $("#selectLableName");
                        $selectLabelName.empty();
                        $selectLabelName.append(
                                "<option value='' selected='selected'>请选择</option>");
                        $.each(json, function (a) {
                            $selectLabelName.append(
                                    "<option value='" + json[a]['id'] + "'>"
                                    + json[a]['labelName']
                                    + "</option>");
                        });
                    });
        }
        function bindLael(id) {
            $("#lb").html('');
            var ids = grid.getGrid().selectedRowsIndex();
            if (ids.length != 1) {
                $(grid).message({
                    type: 'warning',
                    content: '请只一条产品记录'
                });
                return;
            }
            var id = parseInt(ids[0]);

            var params = "ftInfoId=" + id + "&labelId=" + $("#selectLableName option:selected").val();
            $.post('${pageContext.request.contextPath}/FTInfo/bindLabel.koala', params).done(function (json) {
                // debugger;
                if (json['success'] == true) {
                    $(grid).message({
                        type: 'success',
                        content: '绑定成功'
                    });
                    showLabel();
                }
            });
        }

        var testProgramTemplate;
        var sblTemplateDTOs;
        var eqcSettings;
        var processTemplate;
        var labels;

        // 请求对应的数据
        function getNecessaryData(callback) {

            var id = parseInt(checkedId);

            // 请求测试程序
            $.get('${pageContext.request.contextPath}/FTInfo/getTestProgramByProduct/' + id + '.koala').done(function (data) {
                testProgramTemplate = data;
            });

            // 请求SBL模板
            $.get('${pageContext.request.contextPath}/FTInfo/getSBLTemplatesByProduct/' + id + '.koala').done(function (data) {
                sblTemplateDTOs = data;
            });

            // 请求EQC模板
            $.get('${pageContext.request.contextPath}/FTInfo/getEQCSettingsByProduct/' + id + '.koala').done(function (data) {
                eqcSettings = data;
            });

            // 请求Process
            $.get('${pageContext.request.contextPath}/FTInfo/findProcessTemplateByInternalProductId/' + id + '.koala').done(function (data) {
                processTemplate = data;
            });

            // 请求标签模板
            $.get('${pageContext.request.contextPath}/Label/pageJson.koala?page=0&pagesize=1000').done(function (data) {
                labels = data;
            });
        }

        $(function () {

            $('#eqcaddLine').on('click', function () {
                $.get('${pageContext.request.contextPath}/FTInfo/getProcessTemplateContentByProduct/' + grid.getGrid().selectedRowsIndex() + '.koala').done(function (data) {
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
                    $('#eqcSettingBody').append(
                            "<tr>" + "<td><input type='text' name='eqcqty" + i + "'  class='form-control'/></td>" +
                            "<td><input type='text' name='eqcbottomLimit" + i + "' class='form-control'/></td>" + "<td><input type='text' name='eqctopLimit" + i + "' class='form-control' /></td>"
                            + "<td><select id='EQCstatus" + i + "' name='eqcstatus" + i + "' class='form-control'>" + nodeOptions + "</select></td>"
                            + "<td style='height:32px;'><input type='button'  style='display: block;margin-left:5px; ' id='subLine' class='btn btn-primary' value='-' onclick='deleteRow1(this)'></td></tr>");
                    i++;
                });
            });

            $('#eqcclear').on('click', function () {
                $('form').find()
            });

            //添加新记录
            $('#addSBLTemplateRow').on('click', function () {
                // debugger;
                appendEmptySBLTemplateRow();
                count++;
                j++;
            });

            $('#clear').on('click', function () {
                $('form').find();
            });

            PageLoader = {
                initSearchPanel: function () {
                    var contents = [{title: '请选择', value: ''}];//封装字典表维护
                    $.ajax({
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/PackageType.koala',
                        type: 'GET',
                        dataType: 'json'
                    }).then(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contents.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                        form.find('#packageTypeID').select({
                            title: '请选择',
                            contents: contents
                        }).on('change', function () {
                            form.find('#packageTypeID_').val($(this).getValue());
                        });
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
                                content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>导出到Excel</button>',
                                action: 'exportExcel'
                            }
                        ],
                        url: "${pageContext.request.contextPath}/FTInfo/pageJson.koala",
                        columns: [
                            {
                                title: '客户产品型号',
                                name: 'customerProductNumber',
                                width: width,
                                sortable: true,
                                sortName: 'customerProductNumber'
                            },
                            {
                                title: '客户产品版本',
                                name: 'customerProductRevision',
                                width: width,
                                sortable: true,
                                sortName: 'customerProductRevision'
                            },
                            {
                                title: 'PID',
                                name: 'internalProductNumber',
                                width: width,
                                sortable: true,
                                sortName: 'internalProductNumberRevision'
                            },
                            {
                                title: '所属直接客户',
                                name: 'customerDirectName',
                                width: 150,
                                sortable: true, sortName: 'customerDirectName'
                            },
                            {
                                title: '所属间接客户',
                                name: 'customerIndirectName',
                                width: 150,
                                sortable: true, sortName: 'customerIndirectName'
                            },
                            //{title: '内部产品型号', name: 'internalProductNumber', width: width},
                            //{title: '内部产品版本', name: 'internalProductRevision', width: width},
                            {title: '包装形式', name: 'packingType', width: width, sortable: true, sortName: 'packingType'},
                            {
                                title: '出货产品型号',
                                name: 'shipmentProductNumber',
                                width: width,
                                sortable: true,
                                sortName: 'shipmentProductNumber'
                            },
                            {title: '产品说明', name: 'note', width: width, sortable: true, sortName: 'note'},
                            {
                                title: '包装厂',
                                name: 'packingFactory',
                                width: width,
                                sortable: true,
                                sortName: 'packingFactory'
                            },
                            {title: '封装形式', name: 'packageType', width: width, sortable: true, sortName: 'packageType'},
                            {title: '产品尺寸', name: 'size', width: width, sortable: true, sortName: 'size'},
                            {
                                title: '晶圆厂',
                                name: 'waferFactory',
                                width: width,
                                sortable: true,
                                sortName: 'waferFactory'
                            },
                            {
                                title: 'ReelCode固定码',
                                name: 'reelFixCode',
                                width: width,
                                sortable: true,
                                sortName: 'reelFixCode'
                            },
                            {title: 'Reel盘数量', name: 'reelQty', width: width, sortable: true, sortName: 'reelQty'},
                            {
                                title: '质量部主要负责人',
                                name: 'keyQuantityManagerName',
                                width: width,
                                sortable: true, sortName: 'keyQuantityManagerName'
                            },

                            {
                                title: '质量部协助负责人',
                                name: 'assistQuantityManagerName',
                                width: width,
                                sortable: true, sortName: 'assistQuantityManagerName'
                            },
                            {
                                title: '产品部主要负责人',
                                name: 'keyProductionManagerName',
                                width: width,
                                sortable: true, sortName: 'keyProductionManagerName'
                            },

                            {
                                title: '产品部协助负责人',
                                name: 'assistProductionManagerName',
                                width: width,
                                sortable: true, sortName: 'assistProductionManagerName'
                            },

                            {
                                title: 'TDE主要负责人',
                                name: 'keyTDEManagerName',
                                width: width,
                                sortable: true, sortName: 'keyTDEManagerName'
                            },

                            {
                                title: 'TDE协助负责人',
                                name: 'assistTDEManagerName',
                                width: width,
                                sortable: true, sortName: 'assistTDEManagerName'
                            }


                        ]
                    }).on({
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
                        },
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
                        }
                    });
                },
                add: function (grid) {
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog">'
                            + '<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
                            + 'data-dismiss="modal" aria-hidden="true">&times;</button>'
                            + '<h4 class="modal-title">新增FT产品信息管理</h4></div><div class="modal-body">'
                            + '<p>One fine body&hellip;</p></div><div class="modal-footer">'
                            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                            + '<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
                            + '</div></div>');
                    $.get('<%=path%>/FTInfo-add.jsp').done(function (html) {
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
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
                        dialog.find('#customerProductNumberID').on('change', function () {
                            var customerValue = dialog.find('#customerProductNumberID').val();
                            dialog.find('#shipmentProductNumberID').val(customerValue);
                        })
                    });
                    dialog.find('#save').on('click', {grid: grid}, function (e) {
                        if (!Validator.Validate(dialog.find('form')[0], 3))return;
                        $.post('${pageContext.request.contextPath}/FTInfo/add.koala', dialog.find('form').serialize()).done(function (result) {
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
                    var self = this;
                    var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改FT产品信息管理</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
                    $.get('<%=path%>/FTInfo-update.jsp').done(function (html) {
                        dialog.find('.modal-body').html(html);
                        self.initPage(dialog.find('form'));

                        $.get('${pageContext.request.contextPath}/FTInfo/get/' + id + '.koala').done(function (json) {
                            json = json.data;
                            var elm;
                            for (var index in json) {
                                elm = dialog.find('#' + index + 'ID');
                                if (elm.hasClass('select')) {
                                    if (index.endsWith('DTO')) {
                                        elm.setValue(json[index]['id']);
                                    } else {
                                        elm.setValue(json[index]);
                                    }
                                } else if (index.endsWith('ManagerDTO')) {
                                    elm.html(json[index]['name']);
                                    dialog.find('#' + index + 'ID_').val(json[index]['id']);
                                } else {
                                    elm.val(json[index]);
                                }
                            }
                        });
                        dialog.find('#customerProductNumberID').on('change', function () {
                            var customerValue = dialog.find('#customerProductNumberID').val();
                            dialog.find('#shipmentProductNumberID').val(customerValue);
                        })
                        dialog.modal({
                            keyboard: false
                        }).on({
                            'hidden.bs.modal': function () {
                                $(this).remove();
                            }
                        });
                        dialog.find('#save').on('click', {grid: grid}, function (e) {
                            if (!Validator.Validate(dialog.find('form')[0], 3))return;
                            $.post('${pageContext.request.contextPath}/FTInfo/update.koala?id=' + id, dialog.find('form').serialize()).done(function (result) {
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
                    /*           var contents = [{title: '请选择', value: ''}];//产品尺寸字典表维护
                     $.ajax({
                     async: false,
                     url: '
                    ${pageContext.request.contextPath}/SystemDictionary/getByType/ProductSizeType.koala',
                     type: 'GET',
                     dataType: 'json',
                     }).done(function (msg) {
                     for (var i = 0; i < msg.length; i++) {
                     contents.push({
                     title: msg[i]['label'],
                     value: msg[i]['value']
                     });
                     }
                     });
                     selectItems['sizeID'] = contents;*/
                    var contentsPackageType = [{title: '请选择', value: ''}];//封装形式字典表维护
                    var p1 = $.ajax({
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/PackageType.koala',
                        type: 'GET',
                        dataType: 'json'
                    }).then(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contentsPackageType.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                        selectItems['packageTypeID'] = contentsPackageType;
                        return selectItems;
                    });
                    var contentsPackingType = [{title: '请选择', value: ''}];//包装形式字典表维护
                    var p2 = $.ajax({
                        url: '${pageContext.request.contextPath}/SystemDictionary/getByType/PackingType.koala',
                        type: 'GET',
                        dataType: 'json'
                    }).then(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contentsPackingType.push({
                                title: msg[i]['label'],
                                value: msg[i]['value']
                            });
                        }
                        selectItems['packingTypeID'] = contentsPackingType;
                        return selectItems;
                    });
                    var contentsCustomer = [{//所属直接客户查询
                        title: '请选择',
                        value: ''
                    }];
                    var p3 = $.ajax({
                        url: '${pageContext.request.contextPath}/Customer/findCustomer.koala',
                        type: 'POST',
                        dataType: 'json'
                    }).done(function (msg) {
                        for (var i = 0; i < msg.length; i++) {
                            contentsCustomer.push({
                                title: msg[i]['chineseName'],
                                value: msg[i]['id']
                            })
                        }

                        selectItems['customerDirectDTOID'] = contentsCustomer;
                        selectItems['customerIndirectDTOID'] = contentsCustomer;
                        return selectItems;
                    });

                    Promise.all([p1, p2, p3]).then(function (selectItems) {
                        form.find('.select').each(function () {
                            var select = $(this);
                            var idAttr = select.attr('id');
                            select.select({
                                title: '请选择',
                                contents: selectItems[0][idAttr]
                            }).on('change', function () {
                                form.find('#' + idAttr + '_').val($(this).getValue());
                            });
                        })
                    });
                    <%--selectItems['keyProductionManagerDTOID'] = contents;--%>
                    <%--selectItems['assistProductionManagerDTOID'] = contents;--%>
                    <%--selectItems['keyQuantityManagerDTOID'] = contents;--%>
                    <%--selectItems['assistQuantityManagerDTOID'] = contents;--%>
                    <%--selectItems['keyTDEManagerDTOID'] = contents;--%>
                    <%--selectItems['assistTDEManagerDTOID'] = contents;--%>


                    <%--var contents = [{title: '请选择', value: ''}];--%>
                    <%--$.ajax({--%>
                    <%--async: false,--%>
                    <%--url: '${pageContext.request.contextPath}/SystemDictionary/getByType/PackingType.koala',--%>
                    <%--type: 'GET',--%>
                    <%--dataType: 'json'--%>


                    <%--}).done(function (msg) {--%>
                    <%--for (var i = 0; i < msg.length; i++) {--%>
                    <%--contents.push({--%>
                    <%--title: msg[i]['label'],--%>
                    <%--value: msg[i]['value']--%>
                    <%--});--%>
                    <%--}--%>
                    <%--});--%>

                    form.find('.form_datetime').datetimepicker({
                        language: 'zh-CN',
                        format: "yyyy-mm-dd",
                        autoclose: true,
                        todayBtn: true,
                        minView: 2,
                        pickerPosition: 'bottom-left'
                    }).datetimepicker('setDate', new Date());//加载日期选择器

                    $.get(contextPath + '/organization/organizationEmployeeTree.koala').done(function (treeData) {//和后台交互一次数据复用
                        form.find("#ManagerDTOID").find("button").on('click', function () {
                            authorizationMember = $(this);
                            $.get(contextPath + '/pages/domain/TestProgramTemplate-authorize.jsp').done(function (data) {
                                departmentId = [];
                                departmentName = '';
                                var memberTreeDialog = $(data);
                                memberTreeDialog.find('.modal-body').css({height: '325px'});
                                memberTree = memberTreeDialog.find('.tree');
                                loadmemberTree(treeData);
                                memberTreeDialog.find('#confirm').on('click', function () {
                                    if ((departmentName == '')) {
                                        departmentId = personId;
                                        departmentName = personHtml;
                                    }
                                    memberTreeDialog.modal('hide');
                                    form.find('#' + authorizationMember[0].getAttribute('id') + '_').val(departmentId[0]);
                                    if (!departmentId[0]) {
                                        authorizationMember.html('请选择');//如果没选中，则显示“请选择”
                                    } else {
                                        authorizationMember.html(departmentName);
                                    }
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
                    $.post('${pageContext.request.contextPath}/FTInfo/delete.koala', data).done(function (result) {
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
                    var data = [{name: 'ids', value: ids.join(',')}];
                    $.post('${pageContext.request.contextPath}/FTInfo/exportExcel.koala', data).done(function (result) {
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
            PageLoader.initGridPanel().on(//事件绑定
                    'click', '.grid-table-body table tr [data-role="indexCheckbox"]',
                    function (e) {
                        debugger;
                        e.stopPropagation();
                        $('#connect').find('.active').click();
                    });

            form.find('#search').on('click', function () {
                if (!Validator.Validate(document.getElementById("<%=formId%>"), 3))return;
                var params = {};
                form.find('input').each(function () {
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
            /*     grid.find('.grid-table-body').find('table').off('click').on('click', function (e) {
             // debugger;
             e.stopPropagation();
             $('#connect').find('.active').click();


             // showTestPrograms();
             // showSBLTemplates();
             // showEQCSettings();
             //  showProcessTemplate();
             // showLabels();
             });*/
        });

        var openDetailsPageOfFTInfo = function (id) {
            var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
            $.get('<%=path%>/FTInfo-view.jsp').done(function (html) {
                dialog.find('.modal-body').html(html);
                $.get('${pageContext.request.contextPath}/FTInfo/get/' + id + '.koala').done(function (json) {
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
    </script>
</head>
<body>
<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
    <!-- search form -->
    <!-- search form -->
    <form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <div class="form-group">
                        <label class="control-label" style="width:110px;float:left;">所属客户(编号):&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <input type="text" id="numberID" name="customerDirectDTO.number" class="form-control"
                                   style="width:150px;"/>
                        </div>
                        <label class="control-label" style="width:100px;float:left;">封装形式:&nbsp;</label>
                        <div style="margin-left:15px;float:left;">
                            <div class="btn-group select" id="packageTypeID"></div>
                            <input type="hidden" id="packageTypeID_" name="packageType"/>
                        </div>
                    </div>
                </td>
                <td>
                    <button id="search" type="button" style="position:relative;margin-left:150px;margin-bottom:20px;"
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
            <li class="active" onclick='showTestPrograms()'><a data-toggle='tab' href="#programTestingBody">测试程序</a>
            </li>
            <li onclick='showSBLTemplates()'><a data-toggle='tab' href="#SBL">SBL</a></li>
            <li onclick='showEQCSettings()'><a data-toggle='tab' href="#EQC">EQC设定</a></li>
            <li onclick="showProcessTemplate()"><a data-toggle='tab' href="#Process">Process</a></li>
            <li onclick="showLabel()"><a data-toggle='tab' href="#Tab">标签模板</a></li>
        </ul>
        <div class="tab-content" style="height: 350px;overflow: auto;">
            <div id="programTestingBody" class="tab-pane fade active in"
                 style="padding: 10px; text-align: center; width: auto;">
                <div style="background-color: #fff; width: 1250px; margin-top: 10px; margin-left: 20px;" id="pt"
                     class="table-responsive"></div>
            </div>
            <div id="SBL" class="tab-pane fade" style="padding: 10px;">
                <form name="sbl" id='sblform' style="margin-top: 10px;">
                    <div>
                        <!--                     	<span>站点:</span>
                                                <select class="stationSBL" style="padding:0;margin:2px">
                                                </select> -->
                        <table class="table table-bordered" style="width:85%">
                            <thead>
                            <tr>
                                <th style="width: 20%;">Bin别</th>
                                <th>良率下限（%）</th>
                                <th>良率上限（%）</th>
                                <th>品质</th>
                                <th style="width: 10%;">Site</th>
                                <th>测试站点</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="sblTemplateBody">
                            </tbody>
                        </table>
                        <input type="button" style="display: block; margin-bottom: 20px; margin-top: 10px;"
                               id="addSBLTemplateRow"
                               class="btn btn-primary" value="+"/>
                        <input type="button" class="btn btn-primary" value="清空" id="clear"
                               onclick="clearSBLTemplates()"/>
                        <input type="button" style="float:right;" class="btn btn-primary" value="保存"
                               onclick="bindSBLTemplates()"/>
                    </div>
                </form>
            </div>
            <div id="EQC" class="tab-pane fade" style="padding: 10px;">
                <form style="margin-top: 10px;" name="eqc" id='eqcform'>
                    <div>
                        <!--                     	<span>站点:</span>
                                                <select class="stationEQC" style="padding:0;margin:2px">
                                                </select> -->
                        <table class="table table-bordered" style="width:65%">
                            <thead>
                            <tr>
                                <th>EQC数量</th>
                                <th>来料数量下限</th>
                                <th>来料数量上限</th>
                                <th>测试站点</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody id="eqcSettingBody">
                            </tbody>
                        </table>
                        <input type="button" style="display: block; margin-bottom: 2px; margin-top: 10px;"
                               id="eqcaddLine" class="btn btn-primary" value="+"/>
                        <p></p>
                        <input type="button" class="btn btn-primary" value="清空  " id="eqcclear" onclick="eqcclear1()">
                        <input type="button" style="float:right" class="btn btn-primary" value="保存  "
                               onclick="myCheckeqc()"/>
                    </div>
                </form>
            </div>
            <div id="Process" class="tab-pane fade" style="padding: 10px;">
                <form style="margin-top: 10px;">
                    <div>
                        <span>机械手类型：</span>
                        <select onchange="findProcessByHandler(this.value)" style="width: 140px;display:inline"
                                class='form-control' id='jitai'>
                            <option selected="selected" class="form-control">请选择</option>
                            <option value="TurnTower">TurnTower</option>
                            <option value="P&P">P&P</option>
                            <option value="Gravity">Gravity</option>
                            <option value="Prober">Prober</option>
                            <option value="Turn">Turn</option>
                        </select> &nbsp;&nbsp;&nbsp;&nbsp; 选择ProcessMap：&nbsp;&nbsp;
                        <select style="width: 140px;display:inline" class='form-control' id='processmap'>
                        </select> &nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="button" value="确认  " class="btn btn-primary" onclick="bindProcess()"/>
                        <input type="button" class="btn btn-primary" value="清空  " style="margin-left: 20px; "
                               onclick="clearProcess()"/>
                        <input type="button" class="btn btn-primary" value="特殊表单  " style="margin-left: 20px; "
                               onclick="specialForm()"/>
                        <input type="button" value="Runcard签核  " class="btn btn-danger" style="margin-left: 20px"
                               onclick="RuncardCheck()"/>
                    </div>
                    <div id="processTemplateBody"></div>
                    <div class="clear">
                        <button type="button" class="btn btn-primary" onclick="jumpProcessTemplateTab()">新增Process
                        </button>
                        <button type="button" class="btn btn-primary" onclick="jumpProcessTemplateTab()">修改Process
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
            <div id="Tab" class="tab-pane fade">
                <form name="productLabel" style="margin-top: 20px;">
                    <div>
                        <span>测试类型：</span> <select onchange="showLabels(this.value)"
                                                   style="width: 140px;display:inline" class='form-control'
                                                   id='labeltype'>
                        <option selected="selected">请选择</option>
                        <option value="FT">FT</option>
                        <option value="CP">CP</option>
                    </select> <span> 标签名：</span> <select style="width: 140px;display:inline" class='form-control'
                                                         id="selectLableName">
                        <option selected="selected">请选择</option>
                    </select>
                        <input type="button" value="确认  " class="btn btn-primary" onclick="bindLael()"/>
                        <input type="button" class="btn btn-primary" value="清空  " onclick="clearLabe()"/>
                    </div>
                    <div style="background-color: #fff; width: 800px; margin-top: 10px; margin-left: 2px; text-align: center"
                         id="showLabelsTable"></div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
