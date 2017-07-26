<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <%@ include file="../../include/top_Jcrop.jsp" %>
    <%@ include file="../../include/top_lightbox.jsp" %>
    <%@ include file="../../include/top_kindeditor.jsp" %>
    <script src="<%=uiPath%>lib/jquery-ui/jquery-ui.js" type="text/javascript"></script>
    <script src="<%=uiPath%>lib/jquery-ui/jquery-ui-slide.min.js" type="text/javascript"></script>
    <script src="<%=uiPath%>lib/jquery-ui/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
    <script type="text/javascript">
        var isSubmiting = false;
        var op = "${op}";
        var fileId = 0;
        $(function () {
            $("#toptoolbar").ligerToolBar({items: [
                {line: true},
                {text: '保存', click: h2y_save, icon: 'save'},
                {line: true},
                {text: '刷新', click: h2y_refresh, icon: 'refresh'}
            ]});
            $("#birthday").datetimepicker({});
            if (op == "modify") {
                $("#tr_next").hide();
                //修改时图片显示
                $(${fileDataListJson}).each(function () {
                    var json_str = "{\"id\":\"" + this.id + "\"}";
                    if (this.dataType == 1) {
                        $("#idcardPic_div").append("<input id=\"file_input_" + fileId + "\" type=\"hidden\" name=\"picData\"  value='" + json_str + "'/>" +
                            "<a id=\"file_lightbox_" + fileId + "\" class=\"idcardPicImg_lightbox\" rel=\"lightbox\" href=\"common/image/view.htm?path=" + this.url + "\" title=\"" + this.fileName + "\">" +
                            "<img  id=\"file_img_" + fileId + "\" class=\"idcardPicImg\"  height=\"150px\" width=\"150px\" src=\"common/image/view.htm?path=" + this.url + "\"></a>" +"\t");
                    }
                    fileId++;
                });
                $(".idcardPicImg_lightbox").lightBox();

                modifyOpenCitySelect(${deliveryMan.belongCityId});
            }else {
                openCitySelect();
            }
            $("#idCardPositivePicUploadBut").click(function () {
                openImageUploadDialog();
            });
            $("#idcardPicUploadBut").click(function () {
                openFileUploadDialog({
                    fileTypeExts: "*.jpg;*.png;*.jpeg;*.gif",
                    multi: true
                });
            });

            //修改时获取跑客类型
            var belongType='${deliveryMan.belongType}';
            if(belongType){
                $("input[name=belongType][value="+belongType+"]").attr("checked",true);
            }
        });

        /**
         * 城市分类
         * */
        function openCitySelect() {
            $.post("/paotui/openCity/getOpenCity.htm", function (data) {
                var jsonReturn = eval("(" + data + ")");
                var selectHtml = "<option value=\"\"></option>";
                $(jsonReturn).each(function (){
                    selectHtml += "<option value=\"" + this.id + "\">" + this.cityName + "</option>";
                });
                if (selectHtml != "") {
                    $("#belongCityId").append(selectHtml + "");
                }
            });
        }
        /**
         * 修改时获取城市分类
         * @param belongCityId
         */
        function modifyOpenCitySelect(belongCityId){
            $.post("/paotui/openCity/getOpenCity.htm", function (data) {
                var jsonReturn = eval("(" + data + ")");
                var selectHtml = "<option value=\"\"></option>";
                $(jsonReturn).each(function (){
                    if(this.id == belongCityId){
                        selectHtml += "<option value=\"" + this.id + "\" selected=\"selected\">" + this.cityName + "</option>";
                    }else{
                        selectHtml += "<option value=\"" + this.id + "\">" + this.cityName + "</option>";
                    }
                });
                if (selectHtml != "") {
                    $("#belongCityId").append(selectHtml + "");
                }
            });
        }

        function h2y_save(){
            var queryString = $('#editform').serialize();
            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;
            $.post("paotui/deliveryMan/save.htm", queryString, function (data){
                var jsonReturn = eval("(" + data + ")");
                if (op == "modify") {
                    if (jsonReturn.code == "1") {
                        alert(jsonReturn.msg);
                        var src = "<%=basePath%>paotui_deliveryMan_init_htm";
                        top.f_addTab("paotui_deliveryMan_init_htm", "跑客详情", src);
                        top.f_getframe("paotui_deliveryMan_init_htm").h2y_refresh();
                        top.f_delTab("paotui_deliveryMan_add_htm_op_modify_id_${deliveryMan.id}");
                    } else {
                        alert(jsonReturn.msg);
                    }
                }
                isSubmiting = false;
            });
        }

        function h2y_refresh(){
            window.location.reload();
        }
    </script>
</head>
<body>
    <div position="top">
        <table width="100%" class="my_toptoolbar_td">
            <tr>
                <td id="my_toptoolbar_td">
                    <div class="l-toolbar">&nbsp;${mname}</div>
                </td>
                <td align="right" width="50%">
                    <div id="toptoolbar"></div>
                </td>
            </tr>
        </table>
    </div>
    <form name="editform" method="post" action="" id="editform">
        <input type="hidden" name="id" id="id" value="${deliveryMan.id}"/>
        <input name="op" type="hidden" id="op" value="${op}"/>
        <table class="h2y_table">
            <tr>
                <td class="h2y_table_label_td">登录名：</td>
                <td class="h2y_table_edit_td" >
                    ${deliveryMan.loginName}
                </td>
                <td class="h2y_table_label_td">真实姓名：</td>
                <td class="h2y_table_edit_td" >
                    ${deliveryMan.realName}
                </td>
            </tr>
            <tr>
                <td class="h2y_table_label_td">性别：</td>
                <td class="h2y_table_edit_td" >
                    ${deliveryMan.sex}
                </td>
                <td class="h2y_table_label_td">出生日期：</td>
                <td class="h2y_table_edit_td" >
                    <fmt:formatDate value="${deliveryMan.birthday}" pattern="yyyy-MM-dd"/>
                </td>
            </tr>
            <tr>
                <td class="h2y_table_label_td">手机号：</td>
                <td class="h2y_table_edit_td" >
                    ${deliveryMan.mobilePhone}
                </td>
                <td class="h2y_table_label_td">身份证号：</td>
                <td class="h2y_table_edit_td" >
                    ${deliveryManAuth.idCardNo}
                </td>
            </tr>
            <tr>
                <td class="h2y_table_label_td">跑客类型：</td>
                <td class="h2y_table_edit_td" >
                    <h2y:input name="belongType" id="belongType" type="radio" initoption="${belongTpeRadioInit}"
                               value="${deliveryMan.belongType}"/>
                </td>
                <td class="h2y_table_label_td">跑客星级：</td>
                <td class="h2y_table_edit_td" >
                    <select name="starLevel" id="starLevel" class="h2y_input_just">
                    <c:choose>
                        <c:when test="${deliveryMan.starLevel==null}">
                        <option id="1" value="1">1星</option>
                        <option id="2" value="2">2星</option>
                        <option id="3" value="3">3星</option>
                        <option id="4" value="4">4星</option>
                        <option id="5" value="5">5星</option>
                        </c:when>
                        <c:otherwise>
                        <option id="1" value="1"
                                <c:if test="${deliveryMan.starLevel=='1'}">selected</c:if>>1星
                        </option>
                        <option id="2" value="2"
                                <c:if test="${deliveryMan.starLevel=='2'}">selected</c:if>>2星
                        </option>
                        <option id="3" value="3"
                                <c:if test="${deliveryMan.starLevel=='3'}">selected</c:if>>3星
                        </option>
                        <option id="4" value="4"
                                <c:if test="${deliveryMan.starLevel=='4'}">selected</c:if>>4星
                        </option>
                        <option id="5" value="5"
                                <c:if test="${deliveryMan.starLevel=='5'}">selected</c:if>>5星
                        </option>
                        </c:otherwise>
                    </c:choose>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="h2y_table_label_td">所属城市：</td>
                <td class="h2y_table_edit_td" >
                    <select name="belongCityId" id="belongCityId">
                    </select>
                </td>
                <td class="h2y_table_label_td">认证状态：</td>
                <td class="h2y_table_edit_td" >
                    <select name="authStatus" id="authStatus" class="h2y_input_just">
                    <c:choose>
                        <c:when test="${deliveryMan.authStatus==null}">
                        <option id="1" value="1">已认证</option>
                        <option id="2" value="2">未认证</option>
                        <option id="3" value="3">已注销</option>
                        <option id="4" value="4">已冻结</option>
                        </c:when>
                        <c:otherwise>
                        <option id="1" value="1"
                                <c:if test="${deliveryMan.authStatus=='1'}">selected</c:if>>已认证
                        </option>
                        <option id="2" value="2"
                                <c:if test="${deliveryMan.authStatus=='2'}">selected</c:if>>未认证
                        </option>
                        <option id="3" value="3"
                                <c:if test="${deliveryMan.authStatus=='3'}">selected</c:if>>已注销
                        </option>
                        <option id="4" value="4"
                                <c:if test="${deliveryMan.authStatus=='4'}">selected</c:if>>已冻结
                        </option>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="h2y_table_label_td">微信OpendID：</td>
                <td class="h2y_table_edit_td" >
                    ${deliveryManAuth.weixinOpenId}
                </td>
                <td class="h2y_table_label_td">微信认证状态：</td>
                <td class="h2y_table_edit_td" >
                    <c:if test="${deliveryManAuth.weixinAuthStatus=='0'}">未认证</c:if>
                    <c:if test="${deliveryManAuth.weixinAuthStatus=='1'}">已认证</c:if>
                    <c:if test="${deliveryManAuth.weixinAuthStatus=='2'}">认证失败</c:if>
                    <c:if test="${deliveryManAuth.weixinAuthStatus=='3'}">审核中</c:if>
                </td>
            </tr>
            <tr>
                <td class="h2y_table_label_td">好评率：</td>
                <td class="h2y_table_edit_td" >
                    ${deliveryMan.praiseRate}%
                </td>
                <td class="h2y_table_label_td">余额：</td>
                <td class="h2y_table_edit_td" >
                    ${deliveryMan.balance}
                </td>
            </tr>
<%--            <tr>
                <td class="h2y_table_label_td">个人推广二维码：</td>
                <td class="h2y_table_edit_td2" colspan="3">
                    <img height=200px width=200px src=${qrCodeUrl}>
                </td>
            </tr>--%>
            <tr>
                <td class="h2y_table_label_td">证件照(身份证正面、背面、手持)：</td>
                <td class="h2y_table_edit_td2" colspan="3">
                    <div id="idcardPic_div"></div>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
