<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <%@ include file="../../include/top_lightbox.jsp" %>
    <style type="text/css">
        .orderDetail{height: 400px;}
        .orderDetail div{margin-left: 10px; width:45%;height:30px;line-height:30px;float:left;}
    </style>
    <script type="text/javascript">
        var Validator = null;
        var isSubmiting = false;
        var op = "${op}";
        var form = null;
        $(function () {
            //验证属性设置
            $.metadata.setType("attr", "validate");
            Validator = deafultValidate($("#editform"));
            $(".img_lightbox").lightBox();
        });

        function h2y_save() {
            if (!Validator.form()) return;
            var queryString = $('#editform').serialize();
            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;
            //data服务器返回数据
            $.post("paotui/order/save.htm", queryString, function (data) {
                var jsonReturn = eval("(" + data + ")");
                if (op == "modify") {
                    if (jsonReturn.code == "1") {
                        alert(jsonReturn.msg);
                        parent.h2y_refresh();
                    } else {
                        alert(jsonReturn.msg);
                    }
                } else {
                    if (jsonReturn.code == "1") {
                        alert(jsonReturn.msg);
                        parent.h2y_refresh();
                        frameElement.dialog.close();
                    } else {
                        alert(jsonReturn.msg);
                    }
                }
                isSubmiting = false;
            });
        }
    </script>
</head>

<body>
<form name="editform" method="post" action="" id="editform">
    <input type="hidden" name="id" id="id" value="${order.id}"/>
    <div id="navtab1" style="width: 683px; overflow: hidden; border: 1px solid #D3D3d3;" class="liger-tab">
        <div tabid="home" title="代排队订单详情" lselected="true"  class="orderDetail" >
            <div>订单类型：${orderMap.orderType}</div>
            <div>订单编号：${orderMap.orderNo}</div>
            <div>下单人：${orderMap.customerName}</div>
            <div>下单人电话：${orderMap.customerPhone}</div>
            <div>联系人电话：${orderMap.lineupPhone}</div>
            <div>排队任务：${orderMap.lineupRequire}</div>
            <div style="width:90%;">排队地点：${orderMap.address}</div>
            <div>初始排队时长(分钟)：${orderMap.lineupDuration}</div>
            <div>排队时间：${orderMap.bookingTime}</div>
            <div>排队加时时长(分钟)：${orderMap.lineupAddPayDuration}</div>
            <div>排队加时费用：${orderMap.lineupAddPayMoney}</div>
            <div>排队开始时间：${orderMap.lineupBeginTime}</div>
            <div>排队结束时间：${orderMap.lineupEndTime}</div>
            <div>排队实际时长(分钟)：${orderMap.lineupRealDuration}</div>
            <div>排队实际费用：${orderMap.lineupRealMoney}</div>
            <div style="width:90%;">总费用：${orderMap.lineupFee}</div>
            <div>下单时间：${orderMap.createTime}</div>
            <div>抢单时间：${orderMap.deliveryOrderTime}</div>
            <div>跑客姓名：${orderMap.deliveryManName}</div>
            <div>跑客电话：${orderMap.deliveryManPhone}</div>
            <div style="width:90%;">验证码：${orderMap.smsCode}</div>
        </div>
        <div title="状态轨迹"  class="orderDetail" >
            <c:forEach var="data" items="${orderActionMaps}" varStatus="status">
                <div style="width:90%;">${data.actionName}：<fmt:formatDate value="${data.actionTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
                <c:if test="${not empty data.actionImageRealUrl}">
                    <div style="width:90%;height:100px">
                        <a  class="img_lightbox" rel="lightbox" href="${data.actionImageRealUrl}">
                            <img src="${data.actionImageRealUrl}" height="100" width="100" />
                        </a>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <div title="查看地图"  style="height: 400px;overflow: hidden;margin:0;">
            <iframe src="paotui/order/viewMap.htm?id=${orderMap.id}" ></iframe>
        </div>
    </div>
</form>
</body>
</html>