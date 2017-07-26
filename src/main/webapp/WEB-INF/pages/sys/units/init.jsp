<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <script type="text/javascript">

        var parentId = ${parentId};
        //公司类型
        var kindType = "${kindType}";

        var unitType = 1;
        $(function () {
            $("#toptoolbar").ligerToolBar({items: [${toolbar}]});

            $("#layout1").ligerLayout({
                leftWidth: 190,
                height: "100%",
                topHeight: 23,
                allowTopResize: false
            });

            $("#tree1").ligerTree({
                data:${treedata},
                checkbox: false,
                isExpand: false,
                nodeWidth: 120,
                delay: 2,
                onSelect: f_onSelect,
                idFieldName: "id",
                parentIDFieldName: "pid",
                textFieldName: "text"
            });

            f_getList();
        });

        function f_getList() {

            var url = "sys/units/getList.htm?op=init&kindType="+kindType;

            $("#listgrid").ligerGrid({
                columns: [${gridComluns}],
                url: url,
                parms: [
                    {name: "parentId", value: parentId}
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
                    id = row.id;
                },
                onDblClickRow: function (row, index, data) {
                    //alert("行双击事件");
                }
            });
        }

        function f_html(row) {
            return "";
        }

        function f_onSelect(node) {

            if (node == null || node.data == null || node.data.id == null)
                return;
            parentId = node.data.id;
            unitType = node.data.unitType;
            f_query();
        }

        function h2y_add() {

            var manager = $("#tree1").ligerGetTreeManager();
            var treeNote = manager.getSelected();
            if(unitType>2){
                alert("市级代理不能进行添加子级代理!");
                return;
            }
            if (treeNote == null || treeNote.length == 0) {
                alert("请选择父级单位！");
                return;
            }
            var src = "<%=basePath%>sys/units/add.htm?op=add&parentId=" + parentId+"&kindType="+kindType;
            top.f_addTab("sys_units_add_htm_op_add_parentId_" + parentId, "单位注册", src);
        }


        function h2y_modify() {

            var manager = $("#listgrid").ligerGetGridManager();
            var rows = manager.getCheckedRows();
            if (rows == null || rows.length == 0) {
                alert('请选择行');
                return;
            } else if (rows.length > 1) {
                alert("请选择单行记录");
                return;
            }
            var src = "sys/units/add.htm?op=modify&id=" + rows[0].id+"&kindType="+kindType;
            top.f_addTab("sys_units_add_htm_op_modify_id_" + rows[0].id, "单位信息修改", src);
        }

        function h2y_delete() {

            var manager = $("#listgrid").ligerGetGridManager();

            var rows = manager.getCheckedRows();
            if (rows == null || rows.length == 0) {
                alert('请选择行');
                return;
            } else if (rows.length > 1) {
                alert("请选择单行记录");
                return;
            }

            if (!confirm("删除后不可恢复，确定删除此行吗？")) return;

            var src = "sys/units/delete.htm?op=delete&id=" + rows[0].id;

            <%--注意该处url可能不符合你的要求，请注意修改--%>
            $.post(src, function (data) {
                var jsonReturn = eval("(" + data + ")");
                if (jsonReturn.code == "1") {
                    alert(jsonReturn.msg);
                    f_query();
                } else {
                    alert(jsonReturn.msg);
                }
            });
        }

        function h2y_refresh() {
            document.location.reload();
        }

        function f_query() {
            var manager = $("#listgrid").ligerGetGridManager();
            manager.setOptions({
                parms: [
                    {name: "parentId", value: parentId}
                ]
            });
            manager.loadData(true);
        }

        function h2y_search() {
            var sqlWhere = h2y_getSqlCondition();
            var manager = $("#listgrid").ligerGetGridManager();
            manager.changePage("first");
            manager.setOptions({
                parms: [{name: "configQuery", value: sqlWhere}, {name: "parentId", value: parentId}]
            });
            manager.loadData(true);
        }
    </script>

</head>
<body>
<form id="editForm" method="post">
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

        <div position="left" style="height: 94%; overflow: auto;">
            <ul id="tree1"></ul>
        </div>

        <div position="center" title="">
            <input type="hidden" name="unitType" id="unitType" value="${unitType}"/>
            <%--<div id="conditiondiv" style="align:center;padding-top:5px;padding-bottom:5px;">${conditionHtml} </div>--%>
            <div id="listgrid"></div>
        </div>
    </div>
</form>
</body>
</html>