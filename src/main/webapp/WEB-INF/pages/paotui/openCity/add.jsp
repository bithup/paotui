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
        var op = "${op}";
        var form = null;
        $(function () {
            //验证信息
            ${validationRules}
            //验证属性设置
            $.metadata.setType("attr", "validate");
            Validator = deafultValidate($("#editform"));

            var op=$("#op").val();
            //操作是新增,设置经营类别默认值
            if(op=="add"){
                $("input[name=managementType][value='1']").attr("checked",true);
            }

        });

        function h2y_save() {

            var cityName = $("#cityName").val();
            if(cityName==""){
                alert("城市名不能为空");
                return;
            }


            var responsibleName = $("#responsibleName").val();
            if(responsibleName==""){
                alert("负责人不能为空");
                return;
            }

            var orderPrefix = $("#orderPrefix").val();
            if(orderPrefix==""){
                alert("订单前缀不能为空");
                return;
            }


            if (!Validator.form()) return;
            //queryString为form表单提交的值
            var queryString = $('#editform').serialize();
            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;
            //data服务器返回数据
            $.post("paotui/openCity/save.htm", queryString, function (data) {
                var jsonReturn = eval("(" + data + ")");
                if (op == "modify") {
                    if (jsonReturn.code == "1") {
                        alert(jsonReturn.msg);
                        parent.h2y_refresh();
                    } else {
                        alert(jsonReturn.msg);
                    }
                } else {
                    if (jsonReturn.code == "1") {
                        alert(jsonReturn.msg);
                        parent.h2y_refresh();
                        frameElement.dialog.close();
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
        <input type="hidden" name="id" value="${openCity.id}"/>
        <input type="hidden" id="op"  name="op" value="${op}"/>
    </div>
    <table class="h2y_dialog_table" style="width: 450px;">
        <tr>
            <td class="h2y_table_label_td">城市名:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="cityName" type="text" id="cityName" class="h2y_input_just" value="${openCity.cityName}"/>
            </td>
        </tr>
        <tr id="tr_next">
            <td class="h2y_table_label_td">经营类别:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="managementType" id="managementType" type="radio" initoption="${managementTypeRadioInit}" value="${openCity.managementType}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">负责人:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="responsibleName" type="text" id="responsibleName" class="h2y_input_just" value="${openCity.responsibleName}"/>
            </td>
        </tr>


        <tr>
            <td class="h2y_table_label_td">负责人电话:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="responsiblePhone" type="text" id="responsiblePhone" class="h2y_input_just" value="${openCity.responsiblePhone}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">办公地址:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="officeAddress" type="text" id="officeAddress" class="h2y_input_just" value="${openCity.officeAddress}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">订单前缀:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="orderPrefix" type="text" id="orderPrefix" class="h2y_input_just" value="${openCity.orderPrefix}"/>
            </td>
        </tr>

    </table>
</form>
</body>
</html>