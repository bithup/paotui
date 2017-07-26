<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <script type="text/javascript">
        var id = 0;
        $(function () {
            $("#toptoolbar").ligerToolBar({items: [${toolbar}]});
            $("#layout1").ligerLayout({
                leftWidth: 230,
                height: "100%",
                topHeight: 23,
                allowTopResize: false
            });
            getList()
        });

        function getList(){
            var orderType = $("#orderType").val();
            var orderStatus = $("#orderStatus").val();
            var customerName = $("#customerName").val();
            var deliveryManName = $("#deliveryManName").val();
            var url_1 = "paotui/order/getList.htm?orderType="+orderType+"&orderStatus="+orderStatus+"&customerName="+customerName+"&deliveryManName="+deliveryManName;
            var gridComluns =[
                {display: "订单类型", name: "orderType", width: 60, align: "center",  isSort:true},
                {display: "订单编号", name: "orderNo", width: 120, align: "center",  isSort:true},
                {display: "下单人", name: "customerName", width: 60, align: "center",  isSort:true},
                {display: "物品所在地址", name: "addressA", width: 245, align: "left",  isSort:true},
                {display: "发货人电话", name: "mobilePhoneA", width: 115, align: "left",  isSort:true},
                {display: "送达地址", name: "addressB", width: 245, align: "left",  isSort:true},
                {display: "收货人电话", name: "mobilePhoneB", width: 115, align: "left",  isSort:true},
                {display: "下单时间", name: "createTime", width: 130, align: "left",  isSort:true},
                {display: "跑客", name: "deliveryManName", width: 70, align: "center",  isSort:true},
                {display: "状态", name: "orderStatus", width: 60, align: "center",  isSort:true}
            ];
            $("#listgrid").ligerGrid({
                columns: gridComluns,
                url: url_1,
                parms: [
                    {name: "pid", value: id}, {name: "op", value: "grid"}
                ],
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

        function h2y_search(){
            getList();
        }

        function h2y_view(){
            var manager = $("#listgrid").ligerGetGridManager();
            var rows = manager.getCheckedRows();
            if (rows == null || rows.length == 0) {
                alert('请选择行');
                return;
            } else if (rows.length > 1) {
                alert("请选择单行记录");
                return;
            }
            var buttons;
            if (rows[0].orderStatus=="已完成"||rows[0].orderStatus=="已取消"){
                buttons= [{
                        text: '关闭', onclick: function (item, dialog) {
                        dialog.close();
                    }
                    }];
            }else {
                buttons= [{
                    text: '取消订单', onclick: function (item, dialog) {
                        f_getframe("paotui_order_view").h2y_save();
                    }
                    },
                    {
                        text: '关闭', onclick: function (item, dialog) {
                        dialog.close();
                    }
                    }];
            }
            var src = "<%=basePath%>paotui/order/view.htm?op=modify&id="+rows[0].id;
            $.ligerDialog.open({
                name: "paotui_order_view",
                title: "查看订单",
                height: 500,
                url: src,
                width: 700,
                showMax: true,
                showToggle: true,
                showMin: true,
                isResize: true,
                modal: true,
                buttons:buttons
            });
        }

        function h2y_refresh(){
            window.location.reload();
        }

    </script>
</head>
<body>
<div id="layout1" style="width: 100%">
    <input type="hidden " id="status" name="status" value=""/>
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
    <div position="center" title="">
        <div id="conditiondiv" style="align:center">
            订单类型:<select name="orderType" id="orderType" style="width: 70px">
                        <option value=""></option>
                        <option value="1">帮送</option>
                        <option value="2">帮取</option>
                        <option value="3">帮买</option>
                        <option value="4">代排队</option>
                    </select>
            订单状态:<select name="orderStatus" id="orderStatus" style="width: 80px">
                        <option value=""></option>
                        <option value="1">等待支付</option>
                        <option value="2">等待接单</option>
                        <option value="3">进行中</option>
                        <option value="4">待收货</option>
                        <option value="5">已完成</option>
                        <option value="9">已取消</option>
                    </select>
            下单人姓名:<input type="text " name="customerName" id="customerName"  style="width: 100px">
            跑客姓名:<input type="text " name="deliveryManName" id="deliveryManName"  style="width: 100px">
        </div>
        <div id="listgrid"></div>
    </div>
</div>
</body>
</html>