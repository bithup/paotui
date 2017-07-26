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

        var ruleTypeList = [{ruleType: 'required', text: '必填字段'},
            {ruleType: 'remote', text: 'Ajax验证'},
            {ruleType: 'email', text: '电子邮件'},
            {ruleType: 'url', text: '网址'},
            {ruleType: 'date', text: '合法日期'},
            {ruleType: 'dateISO', text: '合法日期ISO'},
            {ruleType: 'number', text: '数字'},
            {ruleType: 'digits', text: '整数'},
            {ruleType: 'creditcard', text: '信用卡号'},
            {ruleType: 'equalTo', text: '相同'},
            {ruleType: 'accept', text: '指定后缀'},
            {ruleType: 'maxlength', text: '最大长度'},
            {ruleType: 'minlength', text: '最小长度'},
            {ruleType: 'rangelength', text: '长度范围'},
            {ruleType: 'range', text: '值范围'},
            {ruleType: 'max', text: '最大值'},
            {ruleType: 'min', text: '最小值'},
            {ruleType: 'alnum', text: '字母数字'},
            {ruleType: 'alnumex', text: '字母数字下划线'},
            {ruleType: 'cellphone', text: '手机号'},
            {ruleType: 'telephone', text: '电话号码'},
            {ruleType: 'zipcode', text: '邮政编码'},
            {ruleType: 'chcharacter', text: '汉字'},
            {ruleType: 'qq', text: 'QQ'},
            {ruleType: 'idcard', text: '身份证'}
        ];

        var isWorkList = [
            {isWork: '1', text: '启用'},
            {isWork: '0', text: '不启用'}];


        var dataTypeList = [
            {dataType: 'string', text: '字符串'},
            {dataType: 'int', text: '数字'},
            {dataType: 'date', text: '日期'}];

        var inputNameList = [
            {inputName: 'string', text: '字符串'},
            {inputName: 'int', text: '数字'},
            {inputName: 'date', text: '日期'}];

        var datasourceTypeList = [{datasourceType: 'input', text: '输入'},
            {datasourceType: 'json', text: 'json数组'},
            {datasourceType: 'sql', text: 'sql语句'},
            {datasourceType: 'dict', text: '字典'}];

        var queryTypeList = [{queryType: 'input', text: '输入'},
            {queryType: 'select', text: '选择'},
            {queryType: 'radio', text: '单选'},
            {queryType: 'checkbox', text: '复选'}];


        var operatorList = [{operator: '=', text: '等于'},
            {operator: '>', text: '大于'},
            {operator: '>=', text: '大于等于'},
            {operator: '<', text: '小于'},
            {operator: '<=', text: '小于等于'},
            {operator: 'like', text: 'like'},
            {operator: 'in', text: 'in'}];

        var rowsList = [{row: '1', text: '1'},
            {row: '2', text: '2'},
            {row: '3', text: '3'},
            {row: '4', text: '4'},
            {row: '5', text: '5'},
            {row: '6', text: '6'},
            {row: '7', text: '7'},
            {row: '8', text: '8'},
            {row: '9', text: '9'},
            {row: '10', text: '10'}];

        var rowspanList = [{rowspan: '1', text: '1'},
            {rowspan: '2', text: '2'},
            {rowspan: '3', text: '3'},
            {rowspan: '4', text: '4'},
            {rowspan: '5', text: '5'},
            {rowspan: '6', text: '6'},
            {rowspan: '7', text: '7'},
            {rowspan: '8', text: '8'},
            {rowspan: '9', text: '9'},
            {rowspan: '10', text: '10'}];

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
                //url: "sys/queryitem/getList.htm?op=tree",
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

            var url_1 = "sys/queryitem/getList.htm?op=grid";

            $("#listgrid").ligerGrid({
                columns: [
                    {
                        display: '字段名称',
                        name: 'fieldName',
                        width: 100,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'text'}
                    },
                    {
                        display: 'Form名称',
                        name: 'formName',
                        width: 100,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'text'}
                    },
                    {
                        display: '显示名称',
                        name: 'name',
                        width: 100,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'text'}
                    },
                    {
                        display: '宽度',
                        name: 'width',
                        width: 60,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'int'}
                    },
                    {
                        display: '数据类型', name: 'dataType', width: 80, align: 'left', isSort: false, type: 'string',
                        editor: {type: 'select', data: dataTypeList, valueField: 'dataType'},
                        render: function (rowdata, index, value) {
                            return getDataText(dataTypeList, "dataType", value);
                        }
                    },
                    {
                        display: '编辑类型', name: 'inputName', width: 80, align: 'left', isSort: false, type: 'string',
                        editor: {type: 'select', data: inputNameList, valueField: 'inputName'},
                        render: function (rowdata, index, value) {
                            return getDataText(inputNameList, "inputName", value);
                        }
                    },
                    {
                        display: '默认值',
                        name: 'VALUE',
                        width: 80,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'text'}
                    },
                    {
                        display: '数据源类型',
                        name: 'datasourceType',
                        width: 80,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'select', data: datasourceTypeList, valueField: 'datasourceType'},
                        render: function (rowdata, index, value) {
                            return getDataText(datasourceTypeList, "datasourceType", value);
                        }
                    },
                    {
                        display: '数据源值',
                        name: 'datasourceValue',
                        width: 80,
                        align: 'left',
                        isSort: false,
                        type: 'string',
                        editor: {type: 'text'}
                    },
                    {
                        display: '表单类型', name: 'queryType', width: 80, align: 'left', isSort: false, type: 'string',
                        editor: {type: 'select', data: queryTypeList, valueField: 'queryType'},
                        render: function (rowdata, index, value) {
                            return getDataText(queryTypeList, "queryType", value);
                        }
                    },
                    {
                        display: '操作符', name: 'operator', width: 80, align: 'left', isSort: false, type: 'string',
                        editor: {type: 'select', data: operatorList, valueField: 'operator'},
                        render: function (rowdata, index, value) {
                            return getDataText(operatorList, "operator", value);
                        }
                    },
                    {display: '行号', name: 'row', width: 50, align: 'left', isSort: false, editor: {type: 'spinner'}},
                    {
                        display: '行占列数',
                        name: 'rowspan',
                        width: 50,
                        align: 'left',
                        isSort: false,
                        editor: {type: 'spinner'}
                    },
                    {
                        display: '排序',
                        name: 'ord',
                        width: 60,
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
                sortName: "ID",
                pageSize: 20,
                height: "100%",
                width: "100%",
                usePager: false,
                enabledEdit: true,
                clickToEdit: true,
                isScroll: false,
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
                "ord": "1", "fieldName": "", "formName": "", "name": "", "width": "", "value": "",
                "dataType": "string", "inputName": "string", "datasourceType": "input", "datasourceValue": "",
                "queryType": "input", "operator": "=", "row": "1", "rowspan": "1"
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

            $.post("sys/queryitem/save.htm", {
                queryItemData: JSON.stringify(data),
                joinId: joinId,
                joinType: joinType
            }, function (data) {

                var jsonReturn = eval("(" + data + ")");
                if (jsonReturn.code == "1") {
                    alert(jsonReturn.msg);
                    f_query()
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
        <%--<div id="conditiondiv" style="align:center;padding-top:5px;padding-bottom:5px;">${exparams.conditionHtml} </div>--%>
        <div id="listgrid"></div>
    </div>
</div>
</body>
</html>