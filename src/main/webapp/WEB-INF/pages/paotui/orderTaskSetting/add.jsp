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
            $("#level").ligerSpinner({type: 'int', height: 25, width: 150, isNegative: false, value:${orderTaskSetting.level}});
            $("#staffOrderNum").ligerSpinner({type: 'int', height: 25, width: 150, isNegative: false, value:${orderTaskSetting.staffOrderNum}});
            $("#crowdsourcingOrderNum").ligerSpinner({type: 'int', height: 25, width: 150, isNegative: false, value:${orderTaskSetting.crowdsourcingOrderNum}});
            var openCityId = ${orderTaskSetting.openCityId};
            getAllOpenCitysForSelect("openCityId",openCityId);
        });

        function h2y_save() {
            var openCityId = $("#openCityId").val();
            var level = $("#level").val();
            var staffOrderNum = $("#staffOrderNum").val();
            var crowdsourcingOrderNum = $("#crowdsourcingOrderNum").val();
            if(openCityId==""){
                alert("开通城市不能为空!");
                return;
            }
            if (level==""||level<1){
                alert("等级不能为空!");
                return;
            }
            if (staffOrderNum==""||staffOrderNum<1){
                alert("公司员工抢单数不能为空!");
                return;
            }
            if (crowdsourcingOrderNum==""||crowdsourcingOrderNum<1){
                alert("众包抢单数不能为空!");
                return;
            }
            if (!Validator.form()) return;
            var queryString = $('#editform').serialize();
            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;
            $.post("paotui/orderTaskSetting/save.htm", queryString, function (data) {
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

        //获取开通城市
        function getAllOpenCitysForSelect(selectId,selectedValue) {
            $.ajax({
                url: "paotui/openCity/getAllOpenCitys.htm",
                type: "get",
                dataType: "json",
                traditional: true,
                success: function (json) {
                    var optionstring = "";
                    for (var i = 0; i < json.length; i++) {
                        optionstring += "<option value=\"" + json[i].id + "\" >" + json[i].cityName + "</option>";
                    }
                    $("#"+selectId+"").html("<option value=''>请选择</option> "+optionstring);
                    if(selectedValue){
                        $("#"+selectId+"").val(selectedValue);
                    }
                },
                error: function (msg) {
                    alert("出错了！");
                }
            });
        };

    </script>
</head>

<body>
<form name="editform" method="post" action="" id="editform">
    <div>
        <input type="hidden" name="id" value="${orderTaskSetting.id}"/>
        <input type="hidden" id="op"  name="op" value="${op}"/>
    </div>
    <table class="h2y_dialog_table" style="width: 560px;">
        <tr>
            <td class="h2y_table_label_td">开通城市:</td>
            <td class="h2y_dialog_table_edit_td" colspan="3">
                <select name="openCityId" id="openCityId" class="h2y_input_just">
                </select>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">等级：</td>
            <td class="h2y_table_edit_td" colspan="3">
                <input name="level" type="text" id="level" class="h2y_input_just"
                       value="${orderTaskSetting.level}"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">公司员工抢单数:</td>
            <td class="h2y_table_edit_td">
                <input name="staffOrderNum" type="text" id="staffOrderNum" class="h2y_input_just"
                       value="${orderTaskSetting.staffOrderNum}"/>
            </td>
            <td class="h2y_table_label_td">众包抢单数:</td>
            <td class="h2y_table_edit_td">
                <input name="crowdsourcingOrderNum" type="text" id="crowdsourcingOrderNum" class="h2y_input_just"
                       value="${orderTaskSetting.crowdsourcingOrderNum}"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>