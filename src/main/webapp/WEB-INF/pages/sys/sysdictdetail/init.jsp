<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <script type="text/javascript">
        var dictMainId = 0;
        var dictMainParentId = 0;
        var unitId = "${unitId}";
        var isextends = "";
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
                nodeWidth: 120,
                isExpand: false,
                onSelect: f_onSelect,
                idFieldName: "id",
                parentIDFieldName: "pid",
                textFieldName: "text"
            });

            f_getList();
        });

        function f_getList() {

            var url_1 = "sys/sysdictdetail/getList.htm?op=grid";

            $("#listgrid").ligerGrid({
                columns: [${gridComluns}],
                url: url_1,
                parms: [
                    {name: "dictMainId", value: dictMainId}
                ],
                showTitle: false,
                dataAction: "server",
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

        function f_onSelect(node) {

            if (node == null || node.data == null || node.data.id == null) return;
            dictMainId = node.data.id;
            dictMainParentId = node.data.pid;
            isextends = node.data.isextends;
            f_query();
        }

        function h2y_add() {

            if (dictMainId == 0) {
                alert("请选择字典类型！");
                return;
            }

            //if (dictMainParentId == 0) {
            //    alert("字典第一级不可添加字典列表！");
            //    return;
            //}

            var src = "<%=basePath%>sys/sysdictdetail/add.htm?op=add&dictMainId=" + dictMainId;

            $.ligerDialog.open({
                name: "sys_sysdictdetail_add",
                title: "添加详细",
                height: 340,
                url: src,
                width: 700,
                showMax: true,
                showToggle: true,
                showMin: true,
                isResize: true,
                modal: true,
                buttons: [
                    {
                        text: '确定', onclick: function (item, dialog) {
                        f_getframe("sys_sysdictdetail_add").h2y_save();
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

            // alert(isextends+"-"+rows[0].UNIT_ID);

            if (isextends == "0" && rows[0].unitId != unitId) {
                alert("平台数据不可以修改！");
                return;
            }

            var src = "sys/sysdictdetail/add.htm?op=modify&id=" + rows[0].id;

            $.ligerDialog.open({
                name: "sys_sysdictdetail_modify",
                title: "修改详细",
                height: 340,
                url: src,
                width: 700,
                showMax: true,
                showToggle: true,
                showMin: true,
                isResize: true,
                modal: true,
                buttons: [
                    {
                        text: '确定', onclick: function (item, dialog) {
                        f_getframe("sys_sysdictdetail_modify").h2y_save();
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

            if (rows[0].UNIT_ID != unitId) {
                alert("平台数据不可以删除！");
                return;
            }

            if (!confirm("删除后不可恢复，确定删除此行吗？")) return;
            var src = "sys/sysdictdetail/delete.htm?op=delete&id=" + rows[0].id;

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
                    {name: "dictMainId", value: dictMainId}
                ]
            });
            manager.loadData(true);
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