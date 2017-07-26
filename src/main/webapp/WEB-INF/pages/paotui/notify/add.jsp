<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <%@ include file="../../include/top_kindeditor.jsp" %>
    <script src="<%=uiPath%>lib/jquery-ui/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
    <script type="text/javascript">
        var Validator = null;
        var isSubmiting = false;
        var op = "${op}";
        var form = null;
        var introduceEditor = null;
        $(function () {
            KindEditor.ready(function (K) {
                introduceEditor = K.create("#content", {
                    pasteType :1,
                    uploadJson: '<%=basePath%>kindeditor/uploadJson.htm',
                    afterBlur: function () {
                        this.sync();
                    }
                });
            });
            //验证信息
            ${validationRules}
            //验证属性设置
            $.metadata.setType("attr", "validate");
            Validator = deafultValidate($("#editform"));
            $("#notifyDate").datetimepicker({});
            //修改时获取跑客类型
            var notifyRole='${notify.notifyRole}';
            if(notifyRole){
                $("input[name=notifyRole][value="+notifyRole+"]").attr("checked",true);
            }
            var openCityId = ${notify.openCityId};
            getAllOpenCitysForSelect("openCityId",openCityId);
        });

        function h2y_save() {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            if (!Validator.form()) return;
            var queryString = $('#editform').serialize();
            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;
            $.post("paotui/notify/save.htm", queryString, function (data) {
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
        <input type="hidden" name="id" value="${notify.id}"/>
        <input type="hidden" id="op"  name="op" value="${op}"/>
    </div>
    <table class="h2y_dialog_table" style="width: 560px;">
        <tr>
            <td class="h2y_table_label_td">通知城市:</td>
            <td class="h2y_dialog_table_edit_td" colspan="3">
                <select name="openCityId" id="openCityId" class="h2y_input_just">
                    <option value=""></option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">通知标题:</td>
            <td class="h2y_table_edit_td" colspan="3">
                <input name="title" type="text" id="title" class="h2y_dialog_input_long" value="${notify.title}"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">通知对象:</td>
            <td class="h2y_table_edit_td">
                <h2y:input name="notifyRole" id="notifyRole" type="radio" initoption="${notifyRoleRadioInit}" value="${notify.notifyRole}"/>
            </td>
            <td class="h2y_table_label_td">通知时间:</td>
            <td class="h2y_table_edit_td">
                <input name="notifyDate" type="text" id="notifyDate" class="h2y_input_just"
                       value="<fmt:formatDate value="${notify.notifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" pattern="yyyy-MM-dd HH:mm"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">内容：</td>
            <td class="h2y_table_edit_td" colspan="3">
                <textarea name="content" id="content" style="height:300px">${notify.content}</textarea>
            </td>
        </tr>
    </table>
</form>
</body>
</html>