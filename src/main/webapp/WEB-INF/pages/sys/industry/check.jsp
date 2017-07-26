<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
        var op = "${op}";
        var form = null;

        $(function () {

        });


        function h2y_save() {

            var queryString = $('#editform').serialize();

            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;

            $.post("sys/industry/check.htm", queryString, function (data) {

                var jsonReturn = eval("(" + data + ")");

                if (jsonReturn.code == "1") {
                    alert(jsonReturn.msg);
                    parent.h2y_refresh();
                } else {
                    alert(jsonReturn.msg);
                }

                isSubmiting = false;
            });
        }

    </script>
</head>

<body>
<form name="editform" method="post" action="" id="editform">
    <div>
        <input type="hidden" name="id" value="${sysIndustry.id}"/>
        <input type="hidden" name="op" value="${op}"/>
    </div>
    <table class="h2y_dialog_table" style="width:400px;">

        <tr>
            <td class="h2y_table_label_td">编码:</td>
            <td class="h2y_dialog_table_edit_td" style="width: 300px;">
                <input name="code" type="text" id="code" readonly="readonly" class="h2y_input_just"
                       value="${sysIndustry.code}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">名称:</td>
            <td class="h2y_dialog_table_edit_td" style="width: 300px;">
                <input name="name" type="text" id="name" readonly="readonly" class="h2y_input_just"
                       value="${sysIndustry.name}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">排序:</td>
            <td class="h2y_dialog_table_edit_td" style="width: 300px;">
                <input name="ord" type="text" id="ord" readonly="readonly" class="h2y_input_just"
                       value="${sysIndustry.ord}"/>
            </td>
        </tr>

        <tr id="tr_next">
            <td class="h2y_table_label_td">通过:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="isPass" id="isPass" type="radio" initoption="1,通过:0,不通过"
                           value="${sysIndustry.isPass}"/>
            </td>
        </tr>
    </table>
</form>

</body>
</html>