<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <%@ include file="../../include/top_Jcrop.jsp" %>
    <%@ include file="../../include/top_lightbox.jsp" %>

    <style type="text/css">
        .zone_code_select {
            border: 1px solid #aecaf0;
            height: 25px;
            line-height: 25px;
        }

        .advert_img {
            max-width: 400px;
        }
    </style>

    <script type="text/javascript">
        var isSubmiting = false;
        //var form = null;

        $(function () {
            $("#toptoolbar").ligerToolBar({
                items: [{line: true}, {
                    text: '保存',
                    click: h2y_save,
                    icon: 'save'
                }, {line: true}, {text: '刷新', click: h2y_refresh, icon: 'refresh'}]
            });
            //form = $("form").ligerForm();

            $("#unitStatus").change(function () {
                if (this.value == "pass") {
                    $("#unitRoleId").attr("disabled", true);
                } else {
                    $("#unitRoleId").attr("disabled", false);
                }
            });

            $(${fileListDataJson}).each(function () {

                if ($("#h2y_file_div_" + this.dataType).attr("_flag") == "image") {
                    $("#h2y_file_div_" + this.dataType).append("<div><a class=\"light_box_a_" + this.dataType + "\" rel=\"lightbox\" href=\"common/image/view.htm?path=" + this.url + "\" title=\"" + this.fileName + "\"><img  class=\"advert_img\"  src=\"common/image/view.htm?path=" + this.url + "\"></a></div>");
                    $(".light_box_a_" + this.dataType).lightBox();
                } else {
                    $("#h2y_file_div_" + this.dataType).append("<div><a href=\"javascript:downloadFile('" + this.id + "');\" title=\"" + this.fileName + "\">" + this.fileName + "</a></div>");
                }
            });
        });


        function downloadFile(id) {

            var url = "<%=basePath%>common/file/down.htm?fileBean=sysUnitsService&id=" + id;
            location.href = url;
        }


        function h2y_save() {

            var formString = $('#editform').serialize();

            //if($("#unitRoleId option").length == 0){
            //		alert("请维护单位角色！");
            //	return;
            // }

            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;

            <%--注意该处url可能不符合你的要求，请注意修改--%>
            $.post("sys/units/save.htm", formString, function (data) {

                var jsonReturn = eval("(" + data + ")");
                if (jsonReturn.code == "1") {
                    alert(jsonReturn.msg);
                    if (top.f_getframe("sys_units_checklist_htm") != null) {
                        top.f_getframe("sys_units_checklist_htm").f_query();
                    } else if (top.f_getframe("sys_units_checkshoplist_htm") != null) {
                        top.f_getframe("sys_units_checkshoplist_htm").f_query();
                    } else if (top.f_getframe("sys_units_checkunitlist_htm") != null) {
                        top.f_getframe("sys_units_checkunitlist_htm").f_query();
                    } else if (top.f_getframe("sys_units_provincechecklist_htm") != null) {
                        top.f_getframe("sys_units_provincechecklist_htm").h2y_refresh();
                    }
                    top.f_delTab("sys_units_check_htm_op_check_id_${sysUnits.id}");
                } else {
                    alert(jsonReturn.msg);
                }
                isSubmiting = false;
            });
        }

        function h2y_refresh() {
            document.location.reload();
        }

    </script>

    <style type="text/css">
        body {
            font-size: 12px;
        }
    </style>


</head>

<body>

<div position="top">
    <table width="100%" class="my_toptoolbar_td">
        <tr>
            <td id="my_toptoolbar_td">
                <div class="l-toolbar">&nbsp;${mname}</div>
            </td>
            <td align="right" width="50%">
                <div id="toptoolbar"></div>
            </td>
        </tr>
    </table>
</div>


<form name="editform" method="post" action="" id="editform">
    <div>
        <input type="hidden" name="op" value="check"/>
        <input type="hidden" name="id" value="${sysUnits.id}"/>
        <input type="hidden" name="parentId" value="${sysUnits.parentId}"/>
        <input type="hidden" name="nid" value="${sysUnits.nid}"/>
        <input type="hidden" name="instId" value="${sysUnits.instId}"/>
        <input type="hidden" name="instNid" value="${sysUnits.instNid}"/>
        <input type="hidden" name="instCode" value="${sysUnits.instCode}"/>
        <input type="hidden" name="kindType" value="${kindType}"/>
    </div>
    <table class="h2y_table">

        <tr>
            <td class="h2y_table_label_td">名称:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.unitName}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">简称:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.shortName}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">域名:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.unitDomain}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">人数:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.userCount}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">地址:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.unitAddress}
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">电话区号:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.telAreaCode}
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">电话:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.tel}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">服务电话:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.telService}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">传真:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.fax}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">法人:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.legalPerson}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">法人手机:</td>
            <td class="h2y_table_edit_td2">
                ${sysUnits.legalPersonMobile}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">类型:</td>
            <td class="h2y_table_edit_td2">
                <!--regType 1 代理公司注册，2组织机构注册-->
                <c:if test="${kindType==1}">
                    <!--unitType 1 代码行业平台，2行业省级代理-->
                    <c:if test="${unitType == 1}">
                        <h2y:input name="unitType" id="unitType" type="radio" initoption="2,省级代理:3,市级代理"
                                   value="${sysUnits.unitType}"/>
                    </c:if>
                    <c:if test="${unitType == 2}">
                        <h2y:input name="unitType" id="unitType" type="radio" initoption="3,市级代理" value="3"/>
                    </c:if>
                </c:if>
                <c:if test="${kindType==2}">
                    <h2y:input name="unitType" id="unitType" type="radio" initoption="10,国家级组织:11,省级组织:12,市级组织"
                               value="${sysUnits.unitType}"/>
                </c:if>
            </td>
        </tr>


        <c:forEach var="c" items="${fileTypeList}">
            <tr>
                <td class="h2y_table_label_td">
                        ${c.name}:
                </td>
                <td class="h2y_table_edit_td2">
                    <div id="h2y_file_div_${c.type}" _flag="${c.flag}"></div>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <td class="h2y_table_label_td">审核:</td>
            <td class="h2y_table_edit_td2">
                <select name="unitStatus" id="unitStatus" ltype="select">
                    <option value="pass">通过</option>
                    <option value="unpass">不通过</option>
                </select>
            </td>
        </tr>

        <tr style="display: none">
            <td class="h2y_table_label_td">角色:</td>
            <td class="h2y_table_edit_td2">
                <!-- 平台公司可以审核全部 并选择角色 -->
                <select name="unitRoleId" id="unitRoleId">
                    <c:forEach var="c" items="${roleList}">
                        <option value="${c.id}" id="${c.id}">${c.roleName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
</form>

</body>
</html>