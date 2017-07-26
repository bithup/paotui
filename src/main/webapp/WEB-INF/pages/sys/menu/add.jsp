<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>


    <script type="text/javascript">
        var Validator = null;
        var isSubmiting = false;
        var op = "${op}"
        var form = null;

        $(function () {
            //$.metadata.setType("attr", "validate");
            //form = $("form").ligerForm();

            if (op == "modify") {
                $("#tr_next").hide();
            } else {
                $("input:radio[name='ifVisible']:eq(1)").attr("checked", true);
                $("input:radio[name='ifGrid']:eq(1)").attr("checked", true);
                $("input:radio[name='configQuery']:eq(1)").attr("checked", true);
                $("input:radio[name='ifValidate']:eq(1)").attr("checked", true);
                $("input:radio[name='ifSys']:eq(1)").attr("checked", true);
            }

            $("#ord").ligerSpinner({type: 'int', height: 25,value:${sysMenu.ord}});

        });


        function h2y_save() {

            var queryString = $('#editform').serialize();

            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;

            $.post("sys/menu/save.htm", queryString, function (data) {

                var jsonReturn = eval("(" + data + ")");
                if (op == "modify") {
                    if (jsonReturn.code == "1") {
                        alert(jsonReturn.msg);
                        parent.f_query();
                        frameElement.dialog.close();
                    } else {
                        alert(jsonReturn.msg);
                    }
                } else {
                    if (jsonReturn.code == "1") {
                        alert(jsonReturn.msg);

                        var ifnext = $("input:radio[name=next]:checked").val();

                        if (ifnext == 1) {
                            $("#menuName").val("");
                            $("#menuUrl").val("");
                            $("#menuIcon").val("");
                            parent.f_query();
                        } else {
                            parent.f_query();
                            frameElement.dialog.close();
                        }

                    } else {
                        alert(jsonReturn.msg);
                    }
                }
                isSubmiting = false;
            });
        }

    </script>
</head>

<body>
<form name="editform" method="post" action="" id="editform">
    <div>
        <input type="hidden" name="id" value="${sysMenu.id}"/>
        <input type="hidden" name="nid" value="${sysMenu.nid}"/>
        <input type="hidden" name="instId" value="${sysMenu.instId}"/>
        <input type="hidden" name="instNid" value="${sysMenu.instNid}"/>
        <input type="hidden" name="op" value="${op}"/>
        <input type="hidden" name="status" value="${sysMenu.status}"/>
    </div>
    <table class="h2y_dialog_table">

        <c:choose>
            <c:when test="${op=='add'}">
                <input type="hidden" name="parentId" value="${sysMenu.parentId}"/>
            </c:when>
            <c:otherwise>
                <tr>
                    <td class="h2y_table_label_td">父级菜单:</td>
                    <td class="h2y_dialog_table_edit_td">
                        <select class="h2y_select_just" name="parentId" id="parentId" ltype="select">
                            <option value="0"
                                    <c:if test="${0==sysMenu.parentId}">selected</c:if> title="顶级菜单">顶级菜单
                            </option>
                            <c:forEach var="c" items="${prentMenuList}">
                                <option value="${c.id}"
                                        <c:if test="${c.id==sysMenu.parentId}">selected</c:if>
                                        title="${c.menuName }"> ${c.menuName }</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>

        <tr>
            <td class="h2y_table_label_td">名称:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="menuName" type="text" class="h2y_input_just" id="menuName" value="${sysMenu.menuName}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">URL:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="menuUrl" type="text" class="h2y_dialog_input_long" id="menuUrl"
                       value="${sysMenu.menuUrl}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">图标:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="menuIcon" type="text" class="h2y_input_just" id="menuIcon"
                       value="${sysMenu.menuIcon}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">显示:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="isVisible" id="isVisible" type="radio" initoption="0,否:1,是"
                           value="${sysMenu.isVisible}"/>
            </td>
        </tr>


        <tr>
            <td class="h2y_table_label_td">列维护:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="isGrid" id="isGrid" type="radio" initoption="0,否:1,是" value="${sysMenu.isGrid}"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">查询:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="isQuery" id="isQuery" type="radio" initoption="0,否:1,是" value="${sysMenu.isQuery}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">验证:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="isValidate" id="isValidate" type="radio" initoption="0,否:1,是"
                           value="${sysMenu.isValidate}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">系统菜单:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="isSys" id="isSys" type="radio" initoption="0,否:1,是" value="${sysMenu.isSys}"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">排序:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="ord" type="text" id="ord" value="${sysMenu.ord}"/>
            </td>
        </tr>

        <tr id="tr_next">
            <td class="h2y_table_label_td">下一步:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="next" id="next" type="radio" initoption="1,继续添加:0,返回列表" value="1"/>
            </td>
        </tr>

    </table>
</form>

</body>
</html>