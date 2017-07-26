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
            var realName = $("#realName").val();
            var checkState = $("#checkState").val();
            var url_1 = "paotui/deliveryManWithdrawals/getList.htm?realName="+realName+"&checkState="+checkState;
            $("#listgrid").ligerGrid({
                columns: [${gridComluns}],
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

        function h2y_check() {
            var manager = $("#listgrid").ligerGetGridManager();
            var rows = manager.getCheckedRows();
            if (rows == null || rows.length == 0) {
                alert('请选择行');
                return;
            } else if (rows.length > 1) {
                alert("请选择单行记录");
                return;
            }
            var src = "<%=basePath%>paotui/deliveryManWithdrawals/add.htm?op=modify&id=" + rows[0].id;
            top.f_addTab("paotui_deliveryManWithdrawals_add_htm_op_modify_id_" + rows[0].id, "跑客信息查看", src);
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
            跑客姓名:<input type="text " name="realName" id="realName"  class="h2y_input_just">
            审核状态:<select name="checkState" id="checkState" class="h2y_input_just">
                    <option id="" value=""></option>
                    <option id="1" value="1">审核通过</option>
                    <option id="2" value="2">未审核</option>
                    <option id="3" value="3">审核未通过</option>
                    </select>
        </div>
        <div id="listgrid"></div>
    </div>
</div>
</body>
</html>
