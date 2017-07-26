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
            if (op == "modify") {
                $("#tr_next").hide();
            } else {
                $("input:radio[name='next']:first").attr("checked", true);

            }

            //验证属性设置
            $.metadata.setType("attr", "validate");
            //验证信息
            ${validationRules}
            //设置默认验证样式
            Validator = deafultValidate($("#editform"));

            //form = $("form").ligerForm();

            $("#ord").ligerSpinner({type: 'int', height: 25, width: 194});

            //获取开通城市
            var openCityId='${sysUser.openCityId}';
            getAllOpenCitysForSelect("openCityId",openCityId);
        });


        function h2y_save() {

            var openCityId = $("#openCityId").val();
            if(openCityId==""){
                alert("开通城市不能为空");
                return;
            }


            if (!Validator.form()) return;

            var queryString = $('#editform').serialize();

            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;

            <%--注意该处url可能不符合你的要求，请注意修改--%>
            $.post("sys/user/save.htm", queryString, function (data) {

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
                            $("#sysUser_userName").val("");
                            $("#sysUser_account").val("");
                            $("#sysUser_mobile").val("");
                            $("#sysUser_email").val("");
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


        //获取开通城市
        function getAllOpenCitysForSelect(selectId,selectedValue) {
            $.ajax({
                url: "paotui/openCity/getAllOpenCitys.htm",    //
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

    <style type="text/css">
        body {
            font-size: 12px;
        }


    </style>


</head>

<body>
<form name="editform" method="post" action="" id="editform">
    <div>
        <input type="hidden" name="sysUser.id" value="${sysUser.id}"/>
        <input type="hidden" name="op" value="${op}"/>
        <input type="hidden" name="sysDeptUser.id" value="${sysDeptUser.id}"/>
        <input type="hidden" name="sysDeptUser.deptId" value="${sysDeptUser.deptId}"/>
    </div>

    <table class="h2y_dialog_table">

        <tr>
            <td class="h2y_table_label_td">所属开通城市:</td>
            <td class="h2y_dialog_table_edit_td">
                <select name="sysUser.openCityId" id="openCityId" class="h2y_input_just">
                </select>
            </td>
        </tr>


        <tr>
            <td class="h2y_table_label_td">用户名:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="sysUser.userName" type="text" id="userName" class="h2y_input_just"
                       value="${sysUser.userName}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">账号:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="sysUser.account" type="text" id="account" class="h2y_input_just"
                       value="${sysUser.account}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">电话:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="sysUser.mobile" type="text" id="mobile" class="h2y_input_just"
                       value="${sysUser.mobile}"/></td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">邮箱:</td>
            <td class="h2y_dialog_table_edit_td">
                <input name="sysUser.email" type="text" id="sysUser_email" class="h2y_input_just"
                       value="${sysUser.email}"/></td>
        </tr>

        <tr style="display:none">
            <td class="h2y_table_label_td">级别:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="userLevel" id="userLevel" type="radio" initoption="1,负责人:0,普通用户"
                           value="${sysDeptUser.userLevel}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">排序:</td>
            <td class="h2y_dialog_table_edit_td">

                <input name="ord" type="text" id="ord" value="${sysDeptUser.ord}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">是否锁定:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="status" id="status" type="radio" initoption="1,是:0,否" value="${sysUser.status}"/>
            </td>
        </tr>


        <tr id="tr_next">
            <td class="h2y_table_label_td">下一步:</td>
            <td class="h2y_dialog_table_edit_td">
                <h2y:input name="next" id="next" type="radio" initoption="1,继续添加:0,返回列表" value="1"/>
                <div id="ifNext_radios"></div>
            </td>
        </tr>
    </table>
</form>

</body>
</html>