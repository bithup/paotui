package com.xgh.paotui.services;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Tian on 2017/3/16.
 */
public interface IOrderPayService {


    /**
     * 余额支付订单跑腿费
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * ret:1:支付成功；2：余额不足；3：优惠券不能用；4：订单不存在；5：支付失败
     */
    public  int updateOfBalancePayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney);


    /**
     * 帮送和帮取支付宝支付订单跑腿费前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfSendAliPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney);

    /**
     * 帮送或帮取支付宝支付订单成功
     * @param orderNo 订单编号
     * @param trade_no 支付宝交易流水号
     *
     * @return -1:更新失败；1：更新成功
     */
    public int updateOfSendOrderAlipayPaySuccess(String orderNo,String trade_no);

    /**
     * 帮送和帮取微信支付订单跑腿费前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfSendWeixinPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney);

    /**
     * 帮送、帮取微信支付订单成功
     * @param capitalLogId  资金日志表id
     * @param trade_no 微信交易流水号
     * @param total_fee 订单总金额，单位为分
     * @param orderMap 订单信息返回值
     * @return 0:更新失败；1：更新成功;2:金额不正确；9:已通知
     */
    public int updateOfSendOrderWeixinPaySuccess(long capitalLogId,String trade_no,String total_fee,Map<String,Long> orderMap);


    /**
     * 帮买支付宝支付订单跑腿费（可能含商品费）前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfBuyAliPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney);

    /**
     * 帮买支付宝支付订单成功
     * @param orderNo 订单编号
     * @param trade_no 支付宝交易流水号
     * @param orderMap 订单信息返回值
     * @return -1:更新失败；1：更新成功
     */
    public int updateOfBuyOrderAlipayPaySuccess(String orderNo,String trade_no, Map<String,Long> orderMap);

    /**
     * 帮买微信支付订单跑腿费（可能含商品费）前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:0:更新订单状态失败；1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：支付金额不正确;
     */
    public  int updateOfBuyWeixinPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney);

    /**
     * 帮买微信支付订单成功
     * @param capitalLogId  资金日志表id
     * @param trade_no 微信交易流水号
     * @param total_fee 订单总金额，单位为分
     * @param orderMap 订单信息返回值
     * @return  0:更新失败；1：更新成功;2:金额不正确；9:已通知
     */
    public int updateOfBuyOrderWeixinPaySuccess(long capitalLogId,String trade_no,String total_fee,Map<String,Long> orderMap);

    /**
     * 帮买支付宝支付商品费成功
     * @param capitalLogId  资金日志id
     * @param trade_no 支付宝交易号
     * return:1:更新商品支付状态成功；0：更新商品支付状态失败
     */
    public  int updateOfBuyGoodsAliPayMoneySuccess(long capitalLogId,String trade_no);

    /**
     * 帮买微信支付商品费成功
     * @param capitalLogId  资金日志id
     * @param trade_no 微信交易号
     * @param total_fee 订单总金额，单位为分
     * return:1：成功；0：失败；9:已通知
     */
    public  int updateOfBuyGoodsWeixinPayMoneySuccess(long capitalLogId,String trade_no,String total_fee);


    /**
     * 代排队支付宝支付订单跑腿费前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfLineupAliPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney);

    /**
     * 代排队支付宝支付订单成功
     * @param orderNo 订单编号
     * @param trade_no 支付宝交易流水号
     *
     * @return -1:更新失败；1：更新成功
     */
    public int updateOfLineupOrderAlipayPaySuccess(String orderNo,String trade_no);

    /**
     * 代排队微信支付订单跑腿费前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfLineupWeixinPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney);

    /**
     * 代排队微信支付订单成功
     * @param trade_no 支付宝交易流水号
     * @param orderMap 订单信息返回值
     * @return -1:更新失败；1：更新成功
     */
    public  int updateOfLineupOrderWeixinPaySuccess(long capitalLogId,String trade_no,String total_fee,Map<String,Long> orderMap);

    /**
     * 代排队支付宝支付排队加时费成功
     * @param capitalLogId  资金日志id
     * @param trade_no 支付宝交易号
     * return:1:更新加时支付状态成功；0：更新加时支付状态失败
     */
    public  int updateOfLineupAddPayAliPayMoneySuccess(long capitalLogId,String trade_no);

    /**
     * 代排队微信支付加时费成功
     * @param capitalLogId  资金日志id
     * @param trade_no 微信交易号
     * return:1:更新加时费支付状态成功；0：更新加时费支付状态失败
     */
    public  int updateOfLineupAddPayWeixinPayMoneySuccess(long capitalLogId,String trade_no);


    /**
     * 通过支付宝付小费
     * @param capitalLogId
     * @param trade_no 支付宝交易号
     * @return 1：成功；2：失败
     */
    public int addTipsWithAliPay(long capitalLogId,String trade_no);


    /**
     * 通过微信付小费
     * @param capitalLogId
     * @param trade_no 微信交易号
     * @param total_fee 订单总金额，单位为分
     * @return 1：成功；2：失败；9:已通知
     */
    public int addTipsWithWeixinPay(long capitalLogId,String trade_no,String total_fee);


    /**
     *极光推送支付订单成功
     * @param customerId
     */
    public void getJpushPayOrderSuccess(long customerId);


    /**
     *取消订单业务处理,等待接单和进行中时，退款需要后台审核。
     * @param orderId
     * @param cancelReason
     * @return 1:取消成功;0：取消失败
     */
    public int updateOfCancelOrder(long orderId,String cancelReason);//帮送、帮取，帮买，代排队通用


    /**
     *帮买取消订单业务处理,等待接单和进行中时，退款需要后台审核。
     * @param orderId
     * @param cancelReason
     * @return 1:取消成功;0：取消失败
     */
    public int updateOfCancelBuyOrder(long orderId,String cancelReason);

    /**
     *帮买取消订单业务处理,等待接单和进行中时，退款需要后台审核。
     * @param orderId
     * @param cancelReason
     * @return 1:取消成功;0：取消失败
     */
    public int updateOfCancelLineupOrder(long orderId,String cancelReason);

}
