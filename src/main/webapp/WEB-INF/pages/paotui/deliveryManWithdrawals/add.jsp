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
        var checkState="${deliveryManWithdrawals.checkState}";
        if(checkState=="2"){
            $("#toptoolbar").ligerToolBar({items: [
                {line: true},
                {text: '保存', click: h2y_save, icon: 'save'},
                {line: true},
                {text: '刷新', click: h2y_refresh, icon: 'refresh'}
            ]});
        }
        else{
            $("#toptoolbar").ligerToolBar({items: [
                {line: true},
                {text: '刷新', click: h2y_refresh, icon: 'refresh'}
            ]});
        }

        if (op == "modify") {
            $("#tr_next").hide();
        }
        var checkState = ${deliveryManWithdrawals.checkState};
        if(checkState==1){
            $("#isCheck").css("display","none");
            $("#isRemark").css("display","none");
        }else if (checkState==3){
            $("#isCheck").css("display","none");
            document.getElementById('remark').disabled = true;
        }else {
            $("#isCheck").css("display","table-row");
        }
        getList()
    });

    function getList(){
        var deliveryManId = ${deliveryManWithdrawals.deliverManId};
        var url_1 = "paotui/capitalLog/getList.htm?deliveryManId="+deliveryManId+"&accountType="+2;
        $("#listgrid").ligerGrid({
            columns: [{display: "类别", name: "type", width: 100,  type: "string", isSort:true},{display: "订单号", name: "orderNo", width: 130,  type: "string", isSort:true},{display: "资金变动事由", name: "capitalChangeReason", width: 120, type: "string", isSort:true},{display: "变动金额", name: "changeMoney", width: 120, type: "string", isSort:true},{display: "资金变动日期", name: "createDate", width: 150,type: "string", isSort:true},{display: "备注", name: "remark", width: 200,  type: "string", isSort:true}],
            url: url_1,
            parms: [
                {name: "pid", value: id}, {name: "op", value: "grid"}
            ],
            title:"个人资金变动记录",
            showTitle: false,
            dataAction: "server",
            sortName: "ord",
            pageSize: 20,
            height: "100%",
            width: "100%",
            usePager: true,
            pageSizeOptions: [20, 30, 50, 100],
            onSelectRow: function (row, index, data) {
                //id = row.id;
            },
            onDblClickRow: function (row, index, data) {
                //alert("行双击事件");
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
        $.post("paotui/deliveryManWithdrawals/save.htm", queryString, function (data){
            var jsonReturn = eval("(" + data + ")");
            if (op == "modify") {
                if (jsonReturn.code == "1") {
                    alert(jsonReturn.msg);
                    var src = "<%=basePath%>paotui_deliveryManWithdrawals_init_htm";
                    top.f_addTab("paotui_deliveryManWithdrawals_init_htm", "跑客详情", src);
                    top.f_getframe("paotui_deliveryManWithdrawals_init_htm").h2y_refresh();
                    top.f_delTab("paotui_deliveryManWithdrawals_add_htm_op_modify_id_${deliveryManWithdrawals.id}");
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
    <input name="id" type="hidden" id="id" value="${deliveryManWithdrawals.id}"/>
    <input name="op" type="hidden" id="op" value="${op}"/>
    <table class="h2y_table">
        <tr>
            <td class="h2y_table_label_td">跑客姓名:</td>
            <td class="h2y_table_edit_td">
                ${deliveryMan.realName}
            </td>
            <td class="h2y_table_label_td">跑客类型:</td>
            <td class="h2y_table_edit_td">
                <c:if test="${deliveryMan.belongType=='1'}">众包</c:if>
                <c:if test="${deliveryMan.belongType=='2'}">公司员工</c:if>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">跑客手机号:</td>
            <td class="h2y_table_edit_td">
                ${deliveryMan.mobilePhone}
            </td>
            <td class="h2y_table_label_td">跑客所在城市:</td>
            <td class="h2y_table_edit_td">
                ${openCity.cityName}
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">提现申请时间:</td>
            <td class="h2y_table_edit_td">
                <fmt:formatDate value="${deliveryManWithdrawals.withdrawalsDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td class="h2y_table_label_td">提现状态:</td>
            <td class="h2y_table_edit_td">
                <c:if test="${deliveryManWithdrawals.checkState=='1'}">已审核</c:if>
                <c:if test="${deliveryManWithdrawals.checkState=='2'}">未审核</c:if>
                <c:if test="${deliveryManWithdrawals.checkState=='3'}">审核未通过</c:if>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">提现前账户余额:</td>
            <td class="h2y_table_edit_td">
                ${deliveryManWithdrawals.withdrawalsBeforeMoney}
            </td>
            <td class="h2y_table_label_td">提现金额:</td>
            <td class="h2y_table_edit_td">
                ${deliveryManWithdrawals.withdrawalsMoney}
            </td>
        </tr>
        <tr id="isCheck">
            <td class="h2y_table_label_td">审核:</td>
            <td class="h2y_table_edit_td2" colspan="3">
                <select name="checkState" id="checkState" class="h2y_input_just">
                    <c:choose>
                    <c:when test="${deliveryManWithdrawals.checkState==null}">
                    <option id="1" value="1">审核通过</option>

                    <option id="3" value="3">审核未通过</option>
                    </c:when>
                    <c:otherwise>
                    <option id="1" value="1"
                            <c:if test="${deliveryManWithdrawals.checkState=='1'}">selected</c:if>>审核通过
                    </option>

                    <option id="3" value="3"
                            <c:if test="${deliveryManWithdrawals.checkState=='3'}">selected</c:if>>审核未通过
                    </option>
                    </c:otherwise>
                    </c:choose>
                </select>
            </td>
        </tr>
        <tr id="isRemark">
            <td class="h2y_table_label_td">备注:</td>
            <td class="h2y_table_edit_td2" colspan="3">
                <input name="remark" type="text" id="remark" class="h2y_input_long" value="${deliveryManWithdrawals.remark}"/>
            </td>
        </tr>
    </table>
    <div id="listgrid"></div>
</form>
</body>
</html>