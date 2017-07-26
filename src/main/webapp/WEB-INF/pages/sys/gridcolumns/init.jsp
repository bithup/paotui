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
        var joinId = 0;
        var parentJoinId = 0;

        var dataTypeList = [{dataType: 'string', text: '字符串'},
            {dataType: 'int', text: '整数'},
            {dataType: 'float', text: '浮点'},
            {dataType: 'date', text: '时间'}];

        var alignList = [{align: 'left', text: '左对齐'},
            {align: 'right', text: '右对齐'},
            {align: 'center', text: '居中'}];

        var isWidthList = [{isWidth: '1', text: '宽度'}, {isWidth: '0', text: '百分比'}];

        var isSortList = [{isSort: '1', text: '支持'}, {isSort: '0', text: '不支持'}];

        var isVisibleList = [{isVisible: '1', text: '显示'}, {isVisible: '0', text: '不显示'}];


        var joinType = "${joinType}";
        $(function () {

            $("#toptoolbar").ligerToolBar({
                items: [
                    {line: true},
                    {text: '添加', click: h2y_add, icon: 'add'},
                    {line: true},
                    {text: '删除', click: h2y_delete, icon: 'delete'},
                    {line: true},
                    {text: '保存', click: h2y_save, icon: 'save'},
                    {line: true},
                    {text: '刷新', click: h2y_refresh, icon: 'refresh'}
                ]
            });


            $("#layout1").ligerLayout({
                leftWidth: 190,
                height: "100%",
                topHeight: 23,
                allowTopResize: false
            });


            $("#tree1").ligerTree({
                //远程加载，有时会加载不出来
                //url: "sys/gridcolumns/getList.htm?op=tree",
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

            var url_1 = "sys/gridcolumns/getList.htm?op=grid";

            $("#listgrid").ligerGrid({
                columns: [
                    {
                        display: '列标题',
                        name: 'title',
                        width: 100,
                        maxWidth: 150,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'text'}
                    },
                    {
                        display: '列名',
                        name: 'name',
                        width: 100,
                        maxWidth: 200,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'text'}
                    },
                    {
                        display: '宽度',
                        name: 'width',
                        width: 80,
                        maxWidth: 150,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'int'}
                    },
                    {
                        display: '单位',
                        name: 'isWidth',
                        width: 80,
                        maxWidth: 150,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'select', data: isWidthList, valueField: 'isWidth', value: 1},
                        render: function (rowdata, index, value) {
                            return getDataText(isWidthList, "isWidth", value);
                        }
                    },
                    {
                        display: '对齐方式',
                        name: 'align',
                        width: 80,
                        maxWidth: 150,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'select', data: alignList, valueField: 'align'},
                        render: function (rowdata, index, value) {
                            return getDataText(alignList, "align", value);
                        }
                    },
                    {
                        display: '数据类型',
                        name: 'dataType',
                        width: 80,
                        maxWidth: 150,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {
                            type: 'select',
                            data: dataTypeList,
                            valueField: 'dataType'
                        },
                        render: function (rowdata, index, value) {

                            return getDataText(dataTypeList, "dataType", value);
                        }
                    },
                    {
                        display: '支持排序',
                        name: 'isSort',
                        width: 80,
                        maxWidth: 150,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'select', data: isSortList, valueField: 'isSort'},
                        render: function (rowdata, index, value) {
                            return getDataText(isSortList, "isSort", value);
                        }
                    },
                    {
                        display: '渲染器',
                        name: 'render',
                        width: 240,
                        maxWidth: 300,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'text'}
                    },
                    {
                        display: '是否显示',
                        name: 'isVisible',
                        width: 100,
                        maxWidth: 150,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'select', data: isVisibleList, valueField: 'isVisible'},
                        render: function (rowdata, index, value) {
                            return getDataText(isVisibleList, "isVisible", value);
                        }
                    },
                    {
                        display: '序号',
                        name: 'ord',
                        width: 100,
                        maxWidth: 200,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'spinner'}
                    }
                ],
                url: url_1,
                parms: [
                    {name: "joinId", value: joinId}, {name: "joinType", value: joinType}
                ],
                showTitle: false,
                dataAction: "server",
                sortName: "id",
                pageSize: 20,
                height: "100%",
                width: "100%",
                usePager: false,
                enabledEdit: true,
                clickToEdit: true,
                isScroll: true,
                pageSizeOptions: [20, 30, 50, 100],
                onSelectRow: function (row, index, data) {
                    id = row.ID;
                },
                onDblClickRow: function (row, index, data) {
                    //alert("行双击事件");
                }
            });
        }

        function getDataText(dataRows, key, value) {

            var text = "";
            $(dataRows).each(function () {

                if (this[key] == value) {
                    text = this.text;
                    return;
                }
            })
            return text;
        }

        function f_html(row) {

            return "";
        }

        function f_onSelect(node) {
            joinId = node.data.id;
            parentJoinId = node.data.pid;
            f_query();
        }

        function h2y_add() {


            if (joinId == null || joinId == 0) {
                alert("请选择左侧二级树节点！");
                return;
            }

            if (parentJoinId == 0) {
                alert("顶级不可添加列维护！");
                return;
            }

            var manager = $("#listgrid").ligerGetGridManager();
            manager.addRow({
                "render": "",
                "isSort": 1,
                "name": "",
                "width": "",
                "isWidth": 1,
                "isVisible": 1,
                "align": "left",
                "ord": 0,
                "dateType": "string",
                "title": ""
            });
        }


        function h2y_modify() {

            var manager = $("#listgrid").ligerGetGridManager();
            var row = manager.getSelectedRow();
            if (!row) {
                alert('请选择行');
                return;
            }
            manager.beginEdit(row);
        }

        function h2y_save() {

            var manager = $("#listgrid").ligerGetGridManager();
            manager.endEdit();
            var data = manager.getData();

            $.post("sys/gridcolumns/save.htm", {
                gridColumnsData: JSON.stringify(data),
                joinId: joinId,
                joinType: joinType
            }, function (data) {

                var jsonReturn = eval("(" + data + ")");
                if (jsonReturn.code == "1") {
                    alert(jsonReturn.msg);
                    f_query();
                } else {
                    alert(jsonReturn.msg);
                }
            });
        }

        function h2y_delete() {

            var manager = $("#listgrid").ligerGetGridManager();
            manager.deleteSelectedRow();
        }

        function h2y_refresh() {
            document.location.reload();
        }

        function f_query() {
            var manager = $("#listgrid").ligerGetGridManager();
            manager.setOptions({
                parms: [
                    {name: "joinId", value: joinId}, {name: "joinType", value: joinType}
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
        <%--
     <div id="conditiondiv" style="align:center;padding-top:5px;padding-bottom:5px;">${exparams.conditionHtml} </div>
     --%>
        <div id="listgrid"></div>
    </div>
</div>
</body>
</html>