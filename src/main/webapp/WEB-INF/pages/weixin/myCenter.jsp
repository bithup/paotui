<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglibs.jsp" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>充值</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link href="<%=uiPath%>weixin/css/reset.css" rel="stylesheet" type="text/css"/>
    <link href="<%=uiPath%>weixin/css/wallet.css" rel="stylesheet" type="text/css"/>


    <script src="<%=uiPath%>weixin/js/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="<%=uiPath%>weixin/js/ort.js" type="text/javascript"></script>

</head>
<body>
<form name="editform" method="post" action="" id="editform">


    <!-- 头部开始 -->
    <div class="head ver">
        <a href="javascript:history.go(-1)" class="return"></a>
        充值
    </div>
    <!-- 内容开始 -->
    <div style="width:80%;line-height: 120px;">
        token： <input type="text" name="token" value="${token}"/>
        openid：<input type="text" id="openid"  name="openid" value="${openid}"/>
    </div>

    <!-- 下一步按钮 -->
    <div class="nextstep">
        <a  id="payButton" href="<%=basePath%>paotui/wechatPay/toSendPay.do?token=${token}">充值</a>
    </div>
    <script>

    </script>
</form>
</body>
</html>