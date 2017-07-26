<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <script src="<%=uiPath%>lib/jquery-ui/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
    <script type="text/javascript">
        var Validator = null;
        var isSubmiting = false;
        var op = "${op}";
        var form = null;
        $(function () {
            //验证信息
            ${validationRules}
            //验证属性设置
            $.metadata.setType("attr", "validate");
            Validator = deafultValidate($("#editform"));

        });

        function h2y_save() {

            var checkState = $('input:radio[name="checkState"]:checked').val();
            if(!checkState){
                alert("请选择审核");
                return;

            }

            if (!Validator.form()) return;
            var queryString = $('#editform').serialize();
            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;
            $.post("paotui/orderCancel/save.htm", queryString, function (data) {
                var jsonReturn = eval("(" + data + ")");
                if (jsonReturn.code == "1") {
                    alert(jsonReturn.msg);
                    parent.h2y_refresh();
                    frameElement.dialog.close();
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
        <input type="hidden" name="id" value="${orderCancel.id}"/>
        <input type="hidden" id="op"  name="op" value="${op}"/>
    </div>
    <table class="h2y_dialog_table" style="width: 560px;">

        <tr>
            <td class="h2y_table_label_td">订单类型:</td>
            <td class="h2y_table_edit_td">
                ${orderTypeName}
            </td>
            <td class="h2y_table_label_td">订单编号:</td>
            <td class="h2y_table_edit_td">
                ${orderCancel.orderNo}
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">退款内容：</td>
            <td class="h2y_table_edit_td" colspan="3">
                ${orderCancel.orderStatusRemark}
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">审核：</td>
            <td class="h2y_table_edit_td" colspan="3">
                <input type="radio" id="checkState_1"  name="checkState"    value="1"   class=""  title=""  style=""      />
                <label for="checkState_1">审核通过</label>&nbsp;&nbsp;
                <input type="radio" id="checkState_2"  name="checkState"    value="3"   class=""  title=""  style=""      />
                <label for="checkState_2">审核不通过</label>
            </td>
        </tr>
    </table>
</form>
</body>
</html>