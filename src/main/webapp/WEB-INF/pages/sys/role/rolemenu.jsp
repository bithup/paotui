<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <script type="text/javascript">
        var deptId = 0;
        var roleId = 0;
        var type = "${type}";
        var isSubmiting = false;
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
                onSelect: f_onSelect,
                idFieldName: "id",
                parentIDFieldName: "pid",
                textFieldName: "text"
            });

            f_getList();
        });

        function f_getList() {

            var url_1 = "sys/role/getList.htm?listType=rolemenu&type=" + type;

            $("#listgrid").ligerGrid({
                columns: [${gridComluns}],
                url: url_1,
                parms: [{name: "roleId", value: roleId}],
                showTitle: false,
                dataAction: "server",
                sortName: "ORD",
                pageSize: 1,
                height: "100%",
                width: "100%",
                checkbox: true,
                usePager: true,
                isChecked: function (row, index, data) {
                    return row.ishas == 0 ? false : true;
                },
                pageSizeOptions: [1, 5],
                tree: {columnName: "name"},
                onSelectRow: function (row, index, data) {
                    id = row.id;
                },
                onDblClickRow: function (row, index, data) {
                    //alert("行双击事件");
                }
            });
        }


        function f_onSelect(node) {
            if (node == null || node.data == null || node.data.id == null) return;
            roleId = node.data.id + "";
            roleId = roleId.replace(new RegExp("inst_|agents_|sys_|nor_", "gm"), "");
            f_query();
        }

        function h2y_refresh() {
            document.location.reload();
        }

        function f_query() {
            var manager = $("#listgrid").ligerGetGridManager();
            manager.setOptions({
                parms: [{name: "roleId", value: roleId}]
            });
            manager.loadData(true);
        }

        function h2y_save() {

            if (roleId == 0) {
                alert("请选中对应的角色！");
                return;
            }

            var manager = $("#listgrid").ligerGetGridManager();
            //var allData = manager.getData();

            var checkedRows = manager.getCheckedRows();
            var checkedData = "";
            var topMenuIds = "";

            /**
             * 获取选中数据
             */
            $(checkedRows).each(function () {
                if (checkedData == "") {
                    checkedData += "{\"id\":" + this.id + ",\"type\":\"" + this.type + "\"}";
                } else {
                    checkedData += ",{\"id\":" + this.id + ",\"type\":\"" + this.type + "\"}";
                }
            });

            /**
             * 获取topMenuIds
             */
            $(manager.getData()).each(function () {
                if (this.pid == "0" && this.type == "menu") {
                    if (topMenuIds == "") {
                        topMenuIds += this.id;
                    } else {
                        topMenuIds += "," + this.id;
                    }
                }
            });

            checkedData = "[" + checkedData + "]";

            var src = "sys/role/savePrivilege.htm";

            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;

            $.post(src, {roleId: roleId, topMenuIds: topMenuIds, checkedData: checkedData}, function (data) {
                var jsonReturn = eval("(" + data + ")");
                if (jsonReturn.code == "1") {
                    alert(jsonReturn.msg);
                } else {
                    alert(jsonReturn.msg);
                }
                isSubmiting = false;
            });
        }

    </script>


</head>
<body>
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
        <div id="listgrid"></div>
    </div>
</div>
</body>
</html>