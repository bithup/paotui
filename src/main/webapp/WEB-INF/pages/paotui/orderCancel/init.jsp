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
            $("#toptoolbar").ligerToolBar({items: [{ line:true },{ text: '查询' , click: h2y_search , icon:'search' },{ line:true },{ text: '审核' , click: h2y_check , icon:'check' },{ line:true },{ text: '刷新' , click: h2y_refresh , icon:'refresh' }]});

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
            var url_1 = "paotui/orderCancel/getList.htm?orderType="+orderType;
            var gridComluns =[
                {display: "订单类型", name: "orderTypeName", width: 60, align: "left",  isSort:false},
                {display: "订单编号", name: "orderNo", width: 140, align: "left",  isSort:false},
                {display: "退款内容", name: "orderStatusRemark", width: 400, align: "left",  isSort:false},
                {display: "订单取消时间", name: "createTime", width: 100, align: "left",  isSort:false},
                {display: "审核人", name: "checkUserName", width: 70, align: "left",  isSort:false},
                {display: "审核时间", name: "checkTime", width: 80, align: "left",  isSort:false},
                {display: "审核情况", name: "checkStateName", width: 120, align: "left",  isSort:false},
                {display: "审核后退款情况", name: "jsonCancelInfo", width: 200, align: "left",  isSort:false}
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

        function h2y_check(){
            var manager = $("#listgrid").ligerGetGridManager();
            var rows = manager.getCheckedRows();
            if (rows == null || rows.length == 0) {
                alert('请选择行');
                return;
            } else if (rows.length > 1) {
                alert("请选择单行记录");
                return;
            }
            if(rows[0].checkState!=2){
                alert("不能重复审核");
                return;
            }
            var src = "<%=basePath%>paotui/orderCancel/add.htm?op=modify&id="+rows[0].id;
            $.ligerDialog.open({
                name: "paotui_orderCancel_modify",
                title: "审核取消订单退款",
                height: 340,
                url: src,
                width: 700,
                showMax: true,
                showToggle: true,
                showMin: true,
                isResize: true,
                modal: true,
                buttons: [
                    {   //点击确定触发事件
                        text: '确定', onclick: function (item, dialog) {
                        f_getframe("paotui_orderCancel_modify").h2y_save();
                    }
                    },
                    {
                        text: '取消', onclick: function (item, dialog) {
                        dialog.close();
                    }
                    }
                ]
            });
        }

        function h2y_refresh(){
            window.location.reload();
        }

        function h2y_search(){
            getList();
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
        订单类型:<select name="orderType" id="orderType" STYLE="height:25px ;width: 100px">
                <option id="" value=""></option>
                <option id="1" value="1">帮送</option>
                <option id="2" value="2">帮取</option>
                <option id="3" value="3">帮买</option>
                <option id="4" value="5">代排队</option>
                </select>
        <div id="listgrid"></div>
    </div>
</div>

</body>
</html>

