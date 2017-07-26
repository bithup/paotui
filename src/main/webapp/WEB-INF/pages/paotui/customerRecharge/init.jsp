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
            $("#rechargeDate1").datetimepicker({});
            $("#rechargeDate2").datetimepicker({});
            getList()
        });

        function getList(){
            var realName = $("#realName").val();
            var mobilePhone = $("#mobilePhone").val();
            var rechargeDate1 = $("#rechargeDate1").val();
            var rechargeDate2 = $("#rechargeDate2").val();
            var url_1 = "paotui/customerRecharge/getList.htm?realName="+realName+"&mobilePhone="+mobilePhone+"&rechargeDate1="+rechargeDate1+"&rechargeDate2="+rechargeDate2;
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
            客户姓名:<input type="text " name="realName" id="realName"  class="h2y_input_just">
            手机号:<input type="text " name="mobilePhone" id="mobilePhone" class="h2y_input_just">
            充值日期:<input name="rechargeDate1" type="text" id="rechargeDate1" class="h2y_input_just" pattern="yyyy-MM-dd HH:mm:ss"/>
                    ~~<input name="rechargeDate2" type="text" id="rechargeDate2" class="h2y_input_just" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
        <div id="listgrid"></div>
    </div>
</div>
</body>
</html>
