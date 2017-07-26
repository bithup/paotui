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
            $("#startDate").datetimepicker({});
            $("#endDate").datetimepicker({});
            openCitySelect();
        });

        function h2y_save() {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            if (startDate>endDate){
             alert("开始时间不能迟于截止时间");
             return;
             }
            if (!Validator.form()) return;
            var queryString = $('#editform').serialize();
            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;
            $.post("paotui/coupon/save.htm", queryString, function (data) {
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

        /**
         * 获取开通城市
         * */
        function openCitySelect() {
            $.post("/paotui/openCity/getOpenCity.htm", function (data) {
                var jsonReturn = eval("(" + data + ")");
                var selectHtml = "";
                $(jsonReturn).each(function (){
                    selectHtml += "<option value=\"" + this.id + "\">" + this.cityName + "</option>";
                });
                $("#openCityId").append(selectHtml);
            });
        }
    </script>
</head>

<body>
<form name="editform" method="post" action="" id="editform">
    <div>
        <input type="hidden" name="id" value="${coupon.id}"/>
        <input type="hidden" id="op"  name="op" value="${op}"/>
    </div>
    <table class="h2y_dialog_table" style="width: 560px;">
        <tr>
            <td class="h2y_table_label_td">适用城市:</td>
            <td class="h2y_dialog_table_edit_td" colspan="3">
                <select name="openCityId" id="openCityId" class="h2y_input_just">
                    <option value=""></option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">优惠劵名称:</td>
            <td class="h2y_table_edit_td" colspan="3">
                <input name="couponName" type="text" id="couponName" class="h2y_dialog_input_long"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">优惠劵金额:</td>
            <td class="h2y_table_edit_td">
                <input name="moneyAmount" type="text" id="moneyAmount" class="h2y_input_just"/>
            </td>
            <td class="h2y_table_label_td">满抵金额:</td>
            <td class="h2y_table_edit_td">
                <input name="useAmount" type="text" id="useAmount" class="h2y_input_just"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">有效期开始时间:</td>
            <td class="h2y_table_edit_td">
                <input name="startDate" type="text" id="startDate" class="h2y_input_just" pattern="yyyy-MM-dd HH:mm"/>
            </td>
            <td class="h2y_table_label_td">有效期结束时间:</td>
            <td class="h2y_table_edit_td">
                <input name="endDate" type="text" id="endDate" class="h2y_input_just" pattern="yyyy-MM-dd HH:mm"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">指定用户账号：</td>
            <td class="h2y_table_edit_td" colspan="3">
                  <textarea name="loginNameList" id="loginNameList" style="width:300px;height:150px"></textarea>
            </td>
        </tr>
    </table>
</form>
</body>
</html>