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
            //queryString为form表单提交的值
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
        <div tabid="home" title="${orderMap.orderType}订单详情" lselected="true"  class="orderDetail" >
            <div>订单类型：${orderMap.orderType}</div>
            <div>订单编号：${orderMap.orderNo}</div>
            <div>发货人：${orderMap.linkmanNameA}</div>
            <div>发货人电话：${orderMap.mobilePhoneA}</div>
            <div style="width:90%;">取货地址：${orderMap.addressA}</div>
            <div>收货人：${orderMap.linkmanNameB}</div>
            <div>收货人电话：${orderMap.mobilePhoneB}</div>
            <div style="width:90%;">送达地址：${orderMap.addressB}</div>
            <div>物品类型：${orderMap.goodsTypeName}</div>
            <div>特殊要求：${orderMap.specialRequire}</div>
            <div>订单发货类型：${orderMap.bookingType}</div>
            <div>发货时间：${orderMap.bookingTime}</div>
            <div style="width:90%;">备注留言：${orderMap.remark}</div>
            <div style="width:90%;">费用：${orderMap.fee}</div>
            <div>抢单时间：${orderMap.deliveryOrderTime}</div>
            <div>跑客姓名：${orderMap.deliveryManName}</div>
            <div>跑客电话：${orderMap.deliveryManPhone}</div>
            <div>验证码：${orderMap.smsCode}</div>
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
        <div title="查看地图" style="height: 400px;overflow: hidden;margin:0;">
            <iframe src="paotui/order/viewMap.htm?id=${orderMap.id}" ></iframe>
        </div>
    </div>
</form>
</body>
</html>