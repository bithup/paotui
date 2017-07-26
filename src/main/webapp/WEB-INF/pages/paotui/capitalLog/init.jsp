<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <script src="<%=uiPath%>lib/jquery-ui/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
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
            $("#createDate1").datetimepicker({});
            $("#createDate2").datetimepicker({});
            getList()
        });

        function getList(){
            var loginName = $("#loginName").val();
            var type = $("#type").val();
            var createDate1 = $("#createDate1").val();
            var createDate2 = $("#createDate2").val();
            var url_1 = "paotui/capitalLog/getList.htm?loginName="+loginName+"&type="+type+"&createDate1="+createDate1+"&createDate2="+createDate2;
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
            账号名:<input type="text " name="loginName" id="loginName"  class="h2y_input_just">
            类别:<select name="type" id="type" class="h2y_input_just">
                    <option id="" value=""></option>
                    <option id="1" value="1">帮取</option>
                    <option id="2" value="2">帮送</option>
                    <option id="3" value="3">帮买</option>
                    <option id="4" value="4">代排队</option>
                    <option id="5" value="5">提现</option>
                    <option id="6" value="5">管理员修改</option>
                 </select>
            变动日期:<input name="createDate1" type="text" id="createDate1" class="h2y_input_just" pattern="yyyy-MM-dd HH:mm:ss"/>
                    ~~<input name="createDate2" type="text" id="createDate2" class="h2y_input_just" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
        <div id="listgrid"></div>
    </div>
</div>
</body>
</html>
