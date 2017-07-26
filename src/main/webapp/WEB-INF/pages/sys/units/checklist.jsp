<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <script type="text/javascript">
        var parentId = <%=unitId%>;

        var kindType = ${kindType};
        $(function () {
            $("#toptoolbar").ligerToolBar({items: [${toolbar}]});

            $("#layout1").ligerLayout({
                leftWidth: 190,
                height: "100%",
                topHeight: 23,
                allowTopResize: false
            });

            f_getList();
        });

        function f_getList() {
            var unitType = $("#unitType").val();
            var url_1 = "sys/units/getList.htm?op=check&kindType=" + kindType;

            $("#listgrid").ligerGrid({
                columns: [${gridComluns}],
                url: url_1,
                parms: [{name: "parentId", value: parentId}],
                showTitle: false,
                dataAction: "server",
                sortName: "ord",
                pageSize: 20,
                height: "100%",
                width: "100%",
                usePager: true,
                pageSizeOptions: [20, 30, 50, 100],
                onSelectRow: function (row, index, data) {
                    id = row.ID;
                },
                onDblClickRow: function (row, index, data) {
                    //alert("行双击事件");
                }
            });
        }

        function f_html(row) {

            return "";
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
            var src = "sys/units/check.htm?op=check&id=" + rows[0].id+"&kindType="+kindType;
            top.f_addTab("sys_units_check_htm_op_check_id_" + rows[0].id, "单位信息审核", src);
        }

        function h2y_refresh() {
            document.location.reload();
        }

        function f_query() {
            var manager = $("#listgrid").ligerGetGridManager();
            manager.loadData(true);
        }
    </script>

</head>
<body>
<form id="formedit" method="post">

    <div id="layout1" style="width: 100%">

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
            <div id="listgrid"></div>
        </div>
    </div>
</form>
</body>
</html>