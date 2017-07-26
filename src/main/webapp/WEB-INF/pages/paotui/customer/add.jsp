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
<style type="text/css">
</style>
<script type="text/javascript">
    var isSubmiting = false;
    var op = "${op}";
    var form = null;
    $(function () {
        if (op == "modify") {
            $("#tr_next").hide();
            $(${fileDataListJson}).each(function () {
                var json_str = "{\"id\":\"" + this.id + "\"}";
                $("#headPicUrl_div").append("<input type=\"hidden\" name=\"logoData\"  value='" + json_str + "'/>" +
                "<a class=\"headPicUrl_lightbox\" rel=\"lightbox\" href=\"common/image/view.htm?path=" + this.url + "\" title=\"" + this.fileName + "\">" +
                "<img class=\"headPicUrlImg\" height=\"150px\" width=\"150px\" src=\"common/image/view.htm?path=" + this.url + "\"></a>");
            });
            $(".headPicUrl_lightbox").lightBox();
        }
    });
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
    <input name="id" type="hidden" id="id" value="${customer.id}"/>
    <input name="op" type="hidden" id="op" value="${op}"/>
    <table class="h2y_table">
        <tr>
            <td class="h2y_table_label_td">登录名：</td>
            <td class="h2y_table_edit_td">
                ${customer.loginName}
            </td>
            <td class="h2y_table_label_td">昵称：</td>
            <td class="h2y_table_edit_td">
                ${customer.nickName}
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">真实姓名:</td>
            <td class="h2y_table_edit_td">
                ${customer.realName}
            </td>
            <td class="h2y_table_label_td">性别:</td>
            <td class="h2y_table_edit_td">
                ${customer.sex}
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">身份证号:</td>
            <td class="h2y_table_edit_td">
                ${customer.idCardNo}
            </td>
            <td class="h2y_table_label_td">手机号:</td>
            <td class="h2y_table_edit_td">
                ${customer.mobilePhone}
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">微信名:</td>
            <td class="h2y_table_edit_td">
                ${customer.weixinLoginName}
            </td>
            <td class="h2y_table_label_td">所在城市:</td>
            <td class="h2y_table_edit_td">
                ${customer.belongCity}
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">账户余额:</td>
            <td class="h2y_table_edit_td">
                ${customer.balance}
            </td>
            <td class="h2y_table_label_td">注册时间:</td>
            <td class="h2y_table_edit_td">
                <fmt:formatDate value="${customer.registerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">最新发货地址:</td>
            <td class="h2y_table_edit_td2" colspan="3">
                ${customer.newestDeliverAddress}
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">头像：</td>
            <td class="h2y_table_edit_td2" colspan="3">
                <div id="headPicUrl_div"></div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>