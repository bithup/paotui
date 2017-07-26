<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglibs.jsp" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>充值方式</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link href="<%=uiPath%>weixin/css/reset.css" rel="stylesheet" type="text/css"/>
    <link href="<%=uiPath%>weixin/css/wallet.css" rel="stylesheet" type="text/css"/>


    <script src="<%=uiPath%>weixin/js/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="<%=uiPath%>weixin/js/ort.js" type="text/javascript"></script>

</head>
<body>
<form name="editform" method="post" action="" id="editform">
    <div>
        <input type="hidden" name="token" value="${token}"/>
        <input type="hidden" name="trade_type" value="JSAPI"/>
        <input type="hidden" id="appVersion"  name="appVersion" value="1"/>
    </div>

<!-- 头部开始 -->
<div class="head ver">
    <a href="javascript:history.go(-1)" class="return"></a>
    充值
</div>
<!-- 内容开始 -->
<div class="recharge">
    <h6>金额</h6>
    <input type="text" name="money" value="" placeholder="请输入充值金额">
</div>

<!-- 内容开始 -->
<div class="recharge_way">
    <ul>
        <li><span class="active">微信</span></li>
    </ul>
</div>
<!-- 下一步按钮 -->
<div class="nextstep">
    <a  id="payButton" href="###">下一步</a>
</div>
<script>
    $(function(){
        $("#payButton").click(function(){
            //充值
            var data = $('#editform').serialize();
            url="/paotui/front/v1/common/addBalanceWithWeixinPay.do";

            //帮买支付跑腿费
            //var data = "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTU2NzU0MzIyNTMsInBheWxvYWQiOiJ7XCJpZFwiOjEwMDAwNSxcImFwcFR5cGVcIjpcImN1c3RvbWVyXCJ9In0.MYYUauJCN9HwcKadSK7hPcShlkPjJBT_LqWUV1LZ350&orderId=1000064&isUseCoupons=0&payMoney=0.01&trade_type=JSAPI";
            //url="/paotui/front/v1/buy/buyWeixinPayMoney.do";


            //支付小费
            //var data = "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTU2NzU0MzIyNTMsInBheWxvYWQiOiJ7XCJpZFwiOjEwMDAwNSxcImFwcFR5cGVcIjpcImN1c3RvbWVyXCJ9In0.MYYUauJCN9HwcKadSK7hPcShlkPjJBT_LqWUV1LZ350&money=0.01&orderId=1000064&trade_type=JSAPI";
            //url="/paotui/front/v1/common/addTipsWithWeixinPay.do";

            //帮买支付商品费
            //var data = "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTU2NzU0MzIyNTMsInBheWxvYWQiOiJ7XCJpZFwiOjEwMDAwNSxcImFwcFR5cGVcIjpcImN1c3RvbWVyXCJ9In0.MYYUauJCN9HwcKadSK7hPcShlkPjJBT_LqWUV1LZ350&orderId=1000064&goodsMoney=0.01&trade_type=JSAPI";
            //url="/paotui/front/v1/buy/buyGoodsWeixinPayMoney.do";


            //帮送支付跑腿费
           // var data = "&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTU2NzU0MzIyNTMsInBheWxvYWQiOiJ7XCJpZFwiOjEwMDAwNSxcImFwcFR5cGVcIjpcImN1c3RvbWVyXCJ9In0.MYYUauJCN9HwcKadSK7hPcShlkPjJBT_LqWUV1LZ350&orderId=1000066&isUseCoupons=0&payMoney=0.01&trade_type=JSAPI";
           // url="/paotui/front/v1/send/sendWeixinMoney.do";


            $.ajax({
                //url: "/paotui/front/v1/common/addBalanceWithWeixinPay.do",
                url: url,
                type: "post",
                data: data,
                dataType: "json",
                traditional: true,
                success: function (json) {


//                    if (typeof WeixinJSBridge == "undefined"){
//                        if( document.addEventListener ){
//                            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
//                        }else if (document.attachEvent){
//                            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
//                            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
//                        }
//                    }else{
//                        onBridgeReady();
//                    }

                    if(json.code=="1"){
                        var resultData=json.resultData;

                        WeixinJSBridge.invoke(
                                'getBrandWCPayRequest', {
                                    "appId":resultData.appId,     //公众号名称，由商户传入
                                    "timeStamp":resultData.timeStamp,         //时间戳，自1970年以来的秒数
                                    "nonceStr":resultData.nonceStr, //随机串
                                    "package":resultData.package,
                                    "signType":"MD5",         //微信签名方式：
                                    "paySign":resultData.sign //微信签名
                                },
                                function(res){
                                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                                        alert("支付成功");
                                    }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                                }
                        );
                    }


                },
                error: function (msg) {
                    alert("出错了！");
                }
            });
        });


        $(".recharge_way ul li").click(function(){
            $(this).find("span").addClass("active");
            $(this).siblings().find("span").removeClass("active");

        });
    });



</script>
    </form>
</body>
</html>