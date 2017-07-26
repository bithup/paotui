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
            var openCityId = ${errandsFee.openCityId};
            getAllOpenCitysForSelect("openCityId",openCityId);
        });

        function h2y_save() {
            var openCityId = $("#openCityId").val();
            var nightStartTime = $("#nightStartTime").val();
            if(openCityId==""){
                alert("开通城市不能为空!");
                return;
            }
            if(nightStartTime<18){
                alert("夜间开始时间为18点以后，请设置18点以后的时间，为24小时制!");
                return;
            }
            if (!Validator.form()) return;
            var queryString = $('#editform').serialize();
            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;
            $.post("paotui/errandsFee/save.htm", queryString, function (data) {
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
        <input type="hidden" name="id" value="${errandsFee.id}"/>
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
            <td class="h2y_table_label_td">里程收费规则:</td>
            <td class="h2y_table_edit_td" colspan="3" style="line-height:30px">
                起步<input name="startMileage" type="text" id="startMileage" style="width:50px;height:19px"
                       value="${errandsFee.startMileage}"/>公里
                <input name="startFee" type="text" id="startFee" style="width:50px;height:19px"
                       value="${errandsFee.startFee}"/>元，
                <input name="exceedMileage" type="text" id="exceedMileage" style="width:50px;height:19px"
                       value="${errandsFee.exceedMileage}"/>公里加收
                <input name="exceedFee" type="text" id="exceedFee" style="width:50px;height:19px"
                       value="${errandsFee.exceedFee}"/>元。<br>
                夜间<input name="nightStartTime" type="text" id="nightStartTime" style="width:50px;height:19px"
                       value="${errandsFee.nightStartTime}"/>点到凌晨
                <input name="nightEndTime" type="text" id="nightEndTime" style="width:50px;height:19px"
                       value="${errandsFee.nightEndTime}"/>点，每单加收
                <input name="nightAddFee" type="text" id="nightAddFee" style="width:50px;height:19px"
                       value="${errandsFee.nightAddFee}"/>元。
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">排队收费规则:</td>
            <td class="h2y_table_edit_td" colspan="3" style="line-height:30px">
                起步<input name="queueStartTime" type="text" id="queueStartTime" style="width:50px;height:20px"
                         value="${errandsFee.queueStartTime}"/>分钟
                <input name="queueStartFee" type="text" id="queueStartFee" style="width:50px;height:20px"
                       value="${errandsFee.queueStartFee}"/>元。超出后每
                <input name="queueExceedTime" type="text" id="queueExceedTime" style="width:50px;height:20px"
                       value="${errandsFee.queueExceedTime}"/>分钟
                <input name="queueExceedFee" type="text" id="queueExceedFee" style="width:50px;height:20px"
                       value="${errandsFee.queueExceedFee}"/>元。
            </td>
        </tr>
    </table>
</form>
</body>
</html>