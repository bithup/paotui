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

            //获取开通城市
            var openCityId='${feeAllocation.openCityId}';
            getAllOpenCitysForSelect("openCityId",openCityId);

            var allocationType='${feeAllocation.allocationType}';
            if(allocationType){
                $("input[name=allocationType][value="+allocationType+"]").attr("checked",true);
            }


            if(allocationType==1){
                $("#trProportion").css("display","table-row");
                $("#trOrderFee").css("display","none");
            }
            else{
                $("#trProportion").css("display","none");
                $("#trOrderFee").css("display","table-row");
            }


            $("input:radio[name=allocationType]").change(function () {
                var allocationType = $(this).val();
                if(allocationType==1){
                    $("#trProportion").css("display","table-row");
                    $("#trOrderFee").css("display","none");
                }
                else{
                    $("#trProportion").css("display","none");
                    $("#trOrderFee").css("display","table-row");
                }

            });

            var op=$("#op").val();
            //操作是新增,设置佣金方式默认值
            if(op=="add"){
                $("input[name=allocationType][value='1']").click();
            }
        });

        function h2y_save() {

            var openCityId = $("#openCityId").val();
            if(openCityId==""){
                alert("开通城市不能为空");
                return;
            }

            //佣金方式
            var allocationType = $("input[name='allocationType']:checked").val();

            if(allocationType==1){
                var runProportion = $("#runProportion").val();
                if(runProportion==""){
                    alert("公司跑客跑腿费不能为空");
                    return;
                }

                var crowdsourcingProportion = $("#crowdsourcingProportion").val();
                if(crowdsourcingProportion==""){
                    alert("众包跑客跑腿费不能为空");
                    return;
                }

            }
            else{
                var runOrderFee = $("#runOrderFee").val();
                if(runOrderFee==""){
                    alert("公司跑客跑腿费不能为空");
                    return;
                }

                var crowdsourcingOrderFee = $("#crowdsourcingOrderFee").val();
                if(crowdsourcingOrderFee==""){
                    alert("众包跑客跑腿费不能为空");
                    return;
                }
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
            $.post("paotui/feeAllocation/save.htm", queryString, function (data) {
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
</head>

<body>
<form name="editform" method="post" action="" id="editform">
    <div>
        <input type="hidden" name="id" value="${feeAllocation.id}"/>
        <input type="hidden" id="op"  name="op" value="${op}"/>
    </div>
    <table class="h2y_dialog_table" style="width: 560px;">
        <tr>
            <td class="h2y_table_label_td" style="width: 99px;">开通城市:</td>
            <td     class="h2y_dialog_table_edit_td"  colspan="3"  style="width: 460px;">
                <select name="openCityId" id="openCityId" class="h2y_input_just">
                </select>

            </td>
        </tr>
        <tr>
        <td class="h2y_table_label_td" style="width: 99px;">佣金方式:</td>
        <td     class="h2y_dialog_table_edit_td"  colspan="3"  style="width: 460px;">
            <h2y:input name="allocationType" id="allocationType" type="radio" initoption="${allocationTypeRadioInit}" value="${feeAllocation.allocationType}"/>
        </td>
    </tr>

        <!--
        </table>

        <table class="h2y_dialog_table" style="width: 560px;">-->
        <tr id="trProportion" >
            <td class="h2y_table_label_td" style="width: 99px;">公司跑客:</td>
            <td class="h2y_dialog_table_edit_td" >
                每单<input name="runProportion" type="text" id="runProportion" class="h2y_input_short" value="${feeAllocation.runProportion}"/>%跑腿费
            </td>

            <td class="h2y_table_label_td" style="width: 99px;">众包跑客:</td>
            <td class="h2y_dialog_table_edit_td">
                每单 <input name="crowdsourcingProportion" type="text" id="crowdsourcingProportion" class="h2y_input_short" value="${feeAllocation.crowdsourcingProportion}"/>%跑腿费
            </td>

        </tr>


        <tr   id="trOrderFee">
            <td class="h2y_table_label_td" style="width: 99px;">公司跑客:</td>
            <td class="h2y_dialog_table_edit_td" >
                每单<input name="runOrderFee" type="text" id="runOrderFee" class="h2y_input_short" value="${feeAllocation.runOrderFee}"/>元跑腿费
            </td>

            <td class="h2y_table_label_td" style="width: 99px;">众包跑客:</td>
            <td class="h2y_dialog_table_edit_td">
                每单<input name="crowdsourcingOrderFee" type="text" id="crowdsourcingOrderFee" class="h2y_input_short" value="${feeAllocation.crowdsourcingOrderFee}"/>元跑腿费
            </td>

        </tr>
        </div>

    </table>
</form>
</body>
</html>