package com.xgh.paotui.services;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.alipay.config.AlipayConfig;
import com.xgh.paotui.alipayApp.config.AlipayConfigApp;
import com.xgh.paotui.dao.*;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.weixin.pay.WeiChartUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tian on 2017/3/16.
 */

@Service("orderPayService")
public class OrderPayServiceImpl extends BaseService implements IOrderPayService {
    private static Logger logger = Logger.getLogger(OrderPayServiceImpl.class);
    @Autowired
    protected IOrderDao orderDao;



    @Autowired
    protected IOrderGoodsDao orderGoodsDao;

    @Autowired
    protected IOpenCityDao openCityDao;

    @Autowired
    protected ICapitalLogDao capitalLogDao;

    @Autowired
    protected ICouponDao couponDao;

    @Autowired
    protected ICustomerDao customerDao;

    @Autowired
    protected IDeliveryManDao deliveryManDao;

    @Autowired
    protected IDeliveryManStateDao deliveryManStateDao;

    @Autowired
    protected IOrderLineupDao orderLineupDao;


    @Autowired
    protected IOrderCancelDao orderCancelDao;




    /**
     * 余额支付订单跑腿费
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * ret:1:支付成功；2：余额不足；3：优惠券不能用；4：订单不存在；5：支付失败
     */
    public  int updateOfBalancePayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney){
        int ret=1;
        Customer customer= customerDao.get(customerId);

        //账户余额
        BigDecimal balance = customer.getBalance();

        Order order = orderDao.get(orderId);
        if(order==null) {
            ret=4;
            return ret;
        }

        //计算实际跑腿费
        BigDecimal actualFreight=order.getOriginalFreight();

        Coupon coupon=null;
        if(isUseCoupons==1){
            //使用优惠券
            coupon = couponDao.get(couponId);
            //优惠券可以使用
            if(coupon.getUseStatus()==1  && coupon.getCustomerId()==customerId){
                actualFreight=order.getOriginalFreight().subtract(coupon.getMoneyAmount());

            }
            else{
                //优惠券已使用，支付失败
                ret=3;
                return  ret;

            }

            order.setIsUseCoupons(1);
            order.setUseCouponsId(couponId);
            order.setCouponsMoney(coupon.getMoneyAmount());
        }
        else{
            order.setIsUseCoupons(0);
        }


        if(payMoney.compareTo(actualFreight)!= 0){
            //前台计算还需支付金额不正确
            ret=5;
            return  ret;
        }
        if(balance.compareTo(actualFreight)<0){
            //余额不足
            ret=2;
            return  ret;
        }


        order.setOrderStatus(2);//等待接单状态
        order.setPayType(1);//余额支付
        //实际支付的跑腿费
        order.setActualFreight(actualFreight);
        orderDao.update(order);

        //更新账户余额
        customer.setBalance(balance.subtract(actualFreight));
        customerDao.update(customer);

        //更新优惠券表
        if(isUseCoupons==1){
            //设置为已使用
            coupon.setUseStatus(2);
            //使用金额默认优惠券金额
            coupon.setUseAmount(coupon.getMoneyAmount());

            coupon.setOrderId(orderId);
            coupon.setUpdateDate( new Date());
            couponDao.update(coupon);

        }

        //更新资金日志表
        CapitalLog capitalLog = new CapitalLog();
        capitalLog.setOrderId(orderId);
        capitalLog.setAccountType(1);
        capitalLog.setLoginName(customer.getLoginName());
        capitalLog.setRealName(customer.getRealName());
        capitalLog.setType(order.getOrderType());
        capitalLog.setOrderNo(order.getOrderNo());
        //余额
        capitalLog.setTradeFrom(1);
        capitalLog.setCapitalChangeReason("余额支付跑腿费");

        capitalLog.setChangeMoney(actualFreight);
        capitalLog.setCreateDate(new Date());
        capitalLog.setStatus(1);
        capitalLogDao.add(capitalLog);

        return ret;
    }



    /**
     * 帮送和帮取支付宝支付订单跑腿费前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfSendAliPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney){
        int ret=1;
        Customer customer= customerDao.get(customerId);


        Order order = orderDao.get(orderId);
        if(order==null) {
            ret=4;
            return ret;
        }



        //不是等待支付状态时，返回失败状态
        if(order.getOrderStatus()!=1) {
            ret=5;
            return ret;
        }

        BigDecimal actualFreight=order.getOriginalFreight();

        Coupon coupon=null;
        if(isUseCoupons==1){
            //使用优惠券
            coupon = couponDao.get(couponId);

            if(coupon.getCustomerId()==customerId){
                if(coupon.getUseStatus()==1 || coupon.getOrderId()==orderId){
                    actualFreight=order.getOriginalFreight().subtract(coupon.getMoneyAmount());
                }
                else{
                    //优惠券不能用，更新订单支付状态
                    ret=3;
                    return  ret;
                }

            }
            else{
                //优惠券不能用，更新订单支付状态
                ret=3;
                return  ret;
            }

            order.setIsUseCoupons(1);
            order.setUseCouponsId(couponId);
            order.setCouponsMoney(coupon.getUseAmount());
        }
        else{
            order.setIsUseCoupons(0);
        }


        if(payMoney.compareTo(actualFreight)!= 0){
            //前台计算还需支付金额不正确
            ret=5;
            return  ret;
        }



        order.setOrderStatus(1);//等待支付状态
        //order.setPayType(3);//支付宝支付
        //实际支付的跑腿费
        order.setActualFreight(actualFreight);
        orderDao.update(order);

        //更新优惠券表
        if(isUseCoupons==1){

            //使用金额默认优惠券金额
            coupon.setUseAmount(coupon.getMoneyAmount());

            coupon.setOrderId(orderId);
            coupon.setUpdateDate( new Date());
            couponDao.update(coupon);

        }


        return ret;
    }



    /**
     * 帮送或帮取支付宝支付订单成功
     * @param orderNo 订单编号
     * @param trade_no 支付宝交易流水号
     *
     * @return -1:更新失败；1：更新成功
     */
    public int updateOfSendOrderAlipayPaySuccess(String orderNo,String trade_no){
        int ret=-1;
        Map<String, Object> map= new HashMap<String, Object>();
        map.put("orderNo",orderNo);

        List<Order> orders=orderDao.getList(map);
        if(orders.size()>0){
            Order order=orders.get(0);
            order.setPayTransactionId(trade_no);
            //支付宝支付
            order.setPayType(3);
            order.setOrderStatus(2);//等待接单
            ret=orderDao.update(order);


            //资金日志流表
            CapitalLog capitalLog=new CapitalLog();
            capitalLog.setAccountType(1);
            capitalLog.setAccountId(order.getCustomerId());
            capitalLog.setChangeMoney(order.getActualFreight().negate());
            capitalLog.setRemark("跑腿费");
            capitalLog.setLoginName(order.getCustomerPhone());
            capitalLog.setRealName(order.getCustomerName());
            //订单类别
            capitalLog.setType(order.getOrderType());
            capitalLog.setOrderId(order.getId());
            capitalLog.setOrderNo(order.getOrderNo());
            capitalLog.setTradeFrom(3);
            capitalLog.setTradeNo(trade_no);
            capitalLog.setCreateDate(new Date());
            capitalLog.setStatus(1);
            capitalLogDao.add(capitalLog);

            //更新优惠券表
            if(order.getIsUseCoupons()==1){
                Coupon coupon = couponDao.get(order.getUseCouponsId());
                coupon.setUseStatus(2);//已使用
                coupon.setUpdateDate( new Date());
                couponDao.update(coupon);
            }
        }
        return ret;
    }

    /**
     * 帮送或帮取微信支付订单跑腿费前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfSendWeixinPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney){
        int ret=1;
        Customer customer= customerDao.get(customerId);


        Order order = orderDao.get(orderId);
        if(order==null) {
            ret=4;
            return ret;
        }

        BigDecimal actualFreight=order.getOriginalFreight();

        Coupon coupon=null;
        if(isUseCoupons==1){
            //使用优惠券
            coupon = couponDao.get(couponId);
            //优惠券可以使用
            if(coupon.getUseStatus()==1  && coupon.getCustomerId()==customerId){
                actualFreight=order.getOriginalFreight().subtract(coupon.getMoneyAmount());

            }
            else{
                //优惠券已使用，更新订单支付状态
                ret=3;
                return  ret;


            }

            order.setIsUseCoupons(1);
            order.setUseCouponsId(couponId);
            order.setCouponsMoney(coupon.getUseAmount());
        }
        else{
            order.setIsUseCoupons(0);
        }


        if(payMoney.compareTo(actualFreight)!= 0){
            //前台计算还需支付金额不正确
            ret=5;
            return  ret;
        }



        order.setOrderStatus(1);//等待支付状态
        //order.setPayType(3);//支付宝支付
        //实际支付的跑腿费
        order.setActualFreight(actualFreight);
        orderDao.update(order);

        //更新优惠券表
        if(isUseCoupons==1){
            //设置为已使用
            coupon.setUseStatus(2);
            //使用金额默认优惠券金额
            coupon.setUseAmount(coupon.getMoneyAmount());

            coupon.setOrderId(orderId);
            coupon.setUpdateDate( new Date());
            couponDao.update(coupon);

        }


        return ret;
    }



    /**
     * 帮送、帮取微信支付订单成功
     * @param capitalLogId  资金日志表id
     * @param trade_no 微信交易流水号
     * @param total_fee 订单总金额，单位为分
     * @param orderMap 订单信息返回值
     * @return 0:更新失败；1：更新成功;2:金额不正确；9:已通知
     */
    public int updateOfSendOrderWeixinPaySuccess(long capitalLogId,String trade_no,String total_fee,Map<String,Long> orderMap){
        int ret=0;

        CapitalLog capitalLog = capitalLogDao.get(capitalLogId);

        //校验微信支付成功通知是否多次发送给商户系统
        if(capitalLog.getData2()==1){
            return  9;
        }

        //校验返回的订单金额是否与商户侧的订单金额一致
        if(!total_fee.equals(WeiChartUtil.changeToFen(capitalLog.getChangeMoney().toString()))){
            return  2;
        }


        Order order=orderDao.get(capitalLog.getOrderId());


        if(order!=null){

            order.setPayTransactionId(trade_no);
            //微信支付
            order.setPayType(2);
            order.setOrderStatus(2);//等待接单
            ret=orderDao.update(order);



            /*更新资金日志表 */
            capitalLog.setTradeNo(trade_no);
            capitalLog.setData2(1);
            capitalLog.setCapitalChangeReason("提交到微信支付跑腿费成功");
            capitalLogDao.update(capitalLog);

            //更新优惠券表
            if(order.getIsUseCoupons()==1){
                Coupon coupon = couponDao.get(order.getUseCouponsId());
                coupon.setUseStatus(2);//已使用
                coupon.setUpdateDate( new Date());
                couponDao.update(coupon);
            }
        }
        return ret;
    }

    /**
     * 帮买支付宝支付订单跑腿费（可能含商品费）前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfBuyAliPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney){
        int ret=1;
        Customer customer= customerDao.get(customerId);


        Order order = orderDao.get(orderId);
        if(order==null) {
            ret=4;
            return ret;
        }

        //不是等待支付状态时，返回失败状态
        if(order.getOrderStatus()!=1) {
            ret=5;
            return ret;
        }

        OrderGoods orderGoods = orderGoodsDao.getOrderGoods(orderId);
        if(orderGoods==null) {
            ret=4;
            return ret;
        }

        BigDecimal actualFreight=order.getOriginalFreight();

        Coupon coupon=null;
        if(isUseCoupons==1){
            //使用优惠券
            coupon = couponDao.get(couponId);

            if(coupon.getCustomerId()==customerId){
                if(coupon.getUseStatus()==1 || coupon.getOrderId()==orderId){
                    actualFreight=order.getOriginalFreight().subtract(coupon.getMoneyAmount());
                }
                else{
                    //优惠券不能用，更新订单支付状态
                    ret=3;
                    return  ret;
                }

            }
            else{
                //优惠券不能用，更新订单支付状态
                ret=3;
                return  ret;
            }


            order.setIsUseCoupons(1);
            order.setUseCouponsId(couponId);
            order.setCouponsMoney(coupon.getUseAmount());
        }
        else{
            order.setIsUseCoupons(0);
        }


        int isKonwPrice=orderGoods.getIsKonwPrice();

        BigDecimal actualMoney=actualFreight;
        //知道价格
        if(isKonwPrice==1){
            actualMoney=actualMoney.add(orderGoods.getBuyPrice());
        }
        if(payMoney.compareTo(actualMoney)!= 0){
            //前台计算还需支付金额不正确
            ret=5;
            return  ret;
        }



        order.setOrderStatus(1);//等待支付状态
        //order.setPayType(3);//支付宝支付
        //实际支付的跑腿费
        order.setActualFreight(actualFreight);
        orderDao.update(order);

        //更新优惠券表
        if(isUseCoupons==1){
            //设置为已使用
            //coupon.setUseStatus(2);
            //使用金额默认优惠券金额
            coupon.setUseAmount(coupon.getMoneyAmount());

            coupon.setOrderId(orderId);
            coupon.setUpdateDate( new Date());

            couponDao.update(coupon);

        }


        return ret;
    }


    /**
     * 帮买支付宝支付订单成功
     * @param orderNo 订单编号
     * @param trade_no 支付宝交易流水号
     * @param orderMap 订单信息返回值
     * @return -1:更新失败；1：更新成功
     */
    public int updateOfBuyOrderAlipayPaySuccess(String orderNo,String trade_no, Map<String,Long> orderMap){
        int ret=-1;
        Map<String, Object> map= new HashMap<String, Object>();
        map.put("orderNo",orderNo);

        List<Order> orders=orderDao.getList(map);
        if(orders.size()>0){
            Order order=orders.get(0);
            order.setPayTransactionId(trade_no);
            //支付宝支付
            order.setPayType(3);
            order.setOrderStatus(2);//等待接单
            ret=orderDao.update(order);


            OrderGoods orderGoods = orderGoodsDao.getOrderGoods(order.getId());
            if(orderGoods==null) {
                ret=4;
                return ret;
            }



            //资金日志流表
            CapitalLog capitalLog=new CapitalLog();
            capitalLog.setAccountType(1);
            capitalLog.setAccountId(order.getCustomerId());

            if(orderGoods.getIsKonwPrice()==1){
                //更新商品表
                orderGoods.setAddPayMoney(orderGoods.getBuyPrice());
                orderGoods.setAddPayTransactionId(trade_no);
                orderGoods.setAddPayTime(new Date());
                orderGoodsDao.update(orderGoods);


                BigDecimal payMoney=order.getActualFreight().add(orderGoods.getBuyPrice());
                capitalLog.setChangeMoney(payMoney.negate());
                capitalLog.setRemark("跑腿费和商品费");
            }
            else{
                capitalLog.setChangeMoney(order.getActualFreight().negate());
                capitalLog.setRemark("跑腿费");
            }



            capitalLog.setLoginName(order.getCustomerPhone());
            capitalLog.setRealName(order.getCustomerName());
            //订单类别
            capitalLog.setType(order.getOrderType());
            capitalLog.setOrderId(order.getId());
            capitalLog.setOrderNo(order.getOrderNo());
            capitalLog.setTradeFrom(3);
            capitalLog.setTradeNo(trade_no);
            capitalLog.setCreateDate(new Date());
            capitalLog.setStatus(1);
            capitalLogDao.add(capitalLog);

            //更新优惠券表
            if(order.getIsUseCoupons()==1){
                Coupon coupon = couponDao.get(order.getUseCouponsId());
                coupon.setUseStatus(2);
                coupon.setUpdateDate( new Date());
                couponDao.update(coupon);
            }

        }
        return ret;
    }

    /**
     * 帮买微信支付订单跑腿费（可能含商品费），更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:0:更新订单状态失败；1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：支付金额不正确;
     */
    public  int updateOfBuyWeixinPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney){
        int ret=1;
        Customer customer= customerDao.get(customerId);


        Order order = orderDao.get(orderId);
        if(order==null) {
            ret=4;
            return ret;
        }

        //不是等待支付状态时，返回失败状态
        if(order.getOrderStatus()!=1) {
            ret=0;
            return ret;
        }

        OrderGoods orderGoods = orderGoodsDao.getOrderGoods(orderId);
        if(orderGoods==null) {
            ret=4;
            return ret;
        }

        BigDecimal actualFreight=order.getOriginalFreight();

        Coupon coupon=null;
        if(isUseCoupons==1){
            //使用优惠券
            coupon = couponDao.get(couponId);
            //优惠券可以使用
            if(coupon.getUseStatus()==1  && coupon.getCustomerId()==customerId){
                actualFreight=order.getOriginalFreight().subtract(coupon.getMoneyAmount());

            }
            else{
                //优惠券已使用，更新订单支付状态
                ret=3;
                return  ret;


            }

            order.setIsUseCoupons(1);
            order.setUseCouponsId(couponId);
            order.setCouponsMoney(coupon.getUseAmount());
        }
        else{
            order.setIsUseCoupons(0);
        }


        int isKonwPrice=orderGoods.getIsKonwPrice();

        BigDecimal actualMoney=actualFreight;
        //知道价格
        if(isKonwPrice==1){
            actualMoney=actualMoney.add(orderGoods.getBuyPrice());

        }
        if(payMoney.compareTo(actualMoney)!= 0){
            //前台计算还需支付金额不正确
            ret=5;
            return  ret;
        }




        order.setOrderStatus(1);//等待支付状态
        //实际支付的跑腿费
        order.setActualFreight(actualFreight);
        ret=orderDao.update(order);

        //更新优惠券表
        if(isUseCoupons==1){

            //使用金额默认优惠券金额
            coupon.setUseAmount(coupon.getMoneyAmount());

            coupon.setOrderId(orderId);
            coupon.setUpdateDate( new Date());
            couponDao.update(coupon);

        }


        return ret;
    }



    /**
     * 帮买微信支付订单成功
     * @param capitalLogId  资金日志表id
     * @param trade_no 微信交易流水号
     * @param total_fee 订单总金额，单位为分
     * @param orderMap 订单信息返回值
     * @return 0:更新失败；1：更新成功;2:金额不正确；9:已通知
     */
    public int updateOfBuyOrderWeixinPaySuccess(long capitalLogId,String trade_no,String total_fee,Map<String,Long> orderMap){
        int ret=0;

        CapitalLog capitalLog = capitalLogDao.get(capitalLogId);

        //校验微信支付成功通知是否多次发送给商户系统
        if(capitalLog.getData2()==1){
            return  9;
        }

        //校验返回的订单金额是否与商户侧的订单金额一致
        if(!total_fee.equals(WeiChartUtil.changeToFen(capitalLog.getChangeMoney().toString()))){
            return  2;
        }


        Order order=orderDao.get(capitalLog.getOrderId());
        if(order !=null){

            orderMap.put("customerId",order.getCustomerId());
            order.setPayTransactionId(trade_no);
            //微信支付
            order.setPayType(2);
            order.setOrderStatus(2);//等待接单
            ret=orderDao.update(order);
            OrderGoods orderGoods = orderGoodsDao.getOrderGoods(order.getId());
            if(orderGoods==null) {
                ret=4;
                return ret;
            }

            if(orderGoods.getIsKonwPrice()==1){
                //更新商品表
                orderGoods.setAddPayMoney(orderGoods.getBuyPrice());
                orderGoods.setAddPayTransactionId(trade_no);
                orderGoods.setAddPayTime(new Date());
                orderGoodsDao.update(orderGoods);

            }

            /*更新资金日志表 */
            capitalLog.setTradeNo(trade_no);
            capitalLog.setData2(1);
            capitalLog.setCapitalChangeReason("提交到微信支付"+capitalLog.getRemark()+"成功");
            capitalLogDao.update(capitalLog);

            //更新优惠券表
            if(order.getIsUseCoupons()==1){
                Coupon coupon = couponDao.get(order.getUseCouponsId());
                coupon.setUseStatus(2);//已使用
                coupon.setUpdateDate( new Date());
                couponDao.update(coupon);
            }
        }
        return ret;
    }

    /**
     * 带排队支付宝支付订单跑腿费前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfLineupAliPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney){
        int ret=1;
        Order order = orderDao.get(orderId);
        if(order!=null&&order.getId()>0) {
            //实际跑腿费
            BigDecimal actualFreight=order.getOriginalFreight();
            //使用优惠劵
            if(isUseCoupons==1){
                Coupon coupon = couponDao.get(couponId);
                if(coupon.getCustomerId()==customerId&&coupon.getUseStatus()==1&&coupon.getOrderId()==orderId){
                    actualFreight=order.getOriginalFreight().subtract(coupon.getMoneyAmount());
                    if(payMoney.compareTo(actualFreight)!= 0){
                        //前台计算需支付金额不正确
                        ret=4;
                        return ret;
                    }else {
                        //使用金额默认优惠券金额
                        coupon.setUseAmount(coupon.getMoneyAmount());
                        coupon.setOrderId(orderId);
                        coupon.setUpdateDate( new Date());
                        couponDao.update(coupon);
                        order.setIsUseCoupons(1);
                        order.setUseCouponsId(couponId);
                        order.setCouponsMoney(coupon.getUseAmount());
                    }
                }else{
                    //优惠券不能使用
                    ret=3;
                    return ret;
                }
            }else{
                order.setIsUseCoupons(0);
            }
            order.setOrderStatus(1);
            order.setActualFreight(actualFreight);
            orderDao.update(order);
        }else {
            //订单不存在
            ret=2;
        }
        return ret;
    }

    /**
     * 代排队支付宝支付订单成功
     * @param orderNo 订单编号
     * @param trade_no 支付宝交易流水号
     * @return -1:更新失败；1：更新成功
     */
    public int updateOfLineupOrderAlipayPaySuccess(String orderNo,String trade_no){
        int ret=-1;
        Map<String, Object> map= new HashMap<String, Object>();
        map.put("orderNo",orderNo);
        List<Order> orders=orderDao.getList(map);
        if(orders!=null&&orders.size()>0){
            Order order=orders.get(0);
            order.setPayTransactionId(trade_no);
            order.setPayType(3);
            order.setOrderStatus(2);
            ret=orderDao.update(order);
            //资金日志流表
            CapitalLog capitalLog=new CapitalLog();
            capitalLog.setAccountType(1);
            Customer customer = customerDao.get(order.getCustomerId());
            capitalLog.setAccountId(customer.getId());
            capitalLog.setLoginName(customer.getLoginName());
            capitalLog.setRealName(customer.getRealName());
            capitalLog.setType(order.getOrderType());
            capitalLog.setOrderId(order.getId());
            capitalLog.setOrderNo(order.getOrderNo());
            capitalLog.setTradeFrom(3);
            capitalLog.setTradeNo(trade_no);
            capitalLog.setCapitalChangeReason("跑腿费支付");
            capitalLog.setChangeMoney(order.getActualFreight().negate());
            capitalLog.setCreateDate(new Date());
            capitalLog.setStatus(1);
            capitalLogDao.add(capitalLog);
            //更新优惠券表
            if(order.getIsUseCoupons()==1){
                Coupon coupon = couponDao.get(order.getUseCouponsId());
                coupon.setUseStatus(2);
                coupon.setUpdateDate( new Date());
                couponDao.update(coupon);
            }
        }
        return ret;
    }

    /**
     * 带排队微信支付订单跑腿费前，更新订单支付状态
     * @param customerId  客户id
     * @param orderId 订单id
     * @param  isUseCoupons  是否使用优惠券1、使用；0：未使用
     * @param couponId  优惠券id
     * @param payMoney  前台返回的还需支付金额
     * return:1:更新订单支付状态成功；3：优惠券不能用；4：订单不存在；5：更新订单支付状态失败
     */
    public  int updateOfLineupWeixinPayMoney(long customerId,long orderId,int isUseCoupons,Long couponId,BigDecimal payMoney){
        int ret=1;
        Order order = orderDao.get(orderId);
        if(order!=null&&order.getId()>0) {
            //实际跑腿费
            BigDecimal actualFreight=order.getOriginalFreight();
            //使用优惠劵
            if(isUseCoupons==1){
                Coupon coupon = couponDao.get(couponId);
                if(coupon.getCustomerId()==customerId&&coupon.getUseStatus()==1&&coupon.getOrderId()==orderId){
                    actualFreight=order.getOriginalFreight().subtract(coupon.getMoneyAmount());
                    if(payMoney.compareTo(actualFreight)!= 0){
                        //前台计算需支付金额不正确
                        ret=4;
                        return ret;
                    }else {
                        //使用金额默认优惠券金额
                        coupon.setUseAmount(coupon.getMoneyAmount());
                        coupon.setOrderId(orderId);
                        coupon.setUpdateDate( new Date());
                        couponDao.update(coupon);
                        order.setIsUseCoupons(1);
                        order.setUseCouponsId(couponId);
                        order.setCouponsMoney(coupon.getUseAmount());
                    }
                }else{
                    //优惠券不能使用
                    ret=3;
                    return ret;
                }
            }else{
                order.setIsUseCoupons(0);
            }
            order.setOrderStatus(1);
            order.setActualFreight(actualFreight);
            orderDao.update(order);
        }else {
            //订单不存在
            ret=2;
        }
        return ret;
    }

    /**
     * 代排队微信支付订单成功
     * @param trade_no 微信交易流水号
     * @param orderMap 订单信息返回值
     * @return -1:更新失败；1：更新成功
     */
    public int updateOfLineupOrderWeixinPaySuccess(long capitalLogId,String trade_no,String total_fee,Map<String,Long> orderMap){
        int ret=0;
        CapitalLog capitalLog = capitalLogDao.get(capitalLogId);
        //校验微信支付成功通知是否多次发送给商户系统
        if(capitalLog.getData2()==1){
            return  9;
        }

        //校验返回的订单金额是否与商户侧的订单金额一致
        if(!total_fee.equals(WeiChartUtil.changeToFen(capitalLog.getChangeMoney().toString()))){
            return  2;
        }

        Order order=orderDao.get(capitalLog.getOrderId());
        if(order!=null&&order.getId()>0){
            orderMap.put("customerId",order.getCustomerId());
            order.setPayTransactionId(trade_no);
            order.setPayType(2);
            order.setOrderStatus(2);
            ret=orderDao.update(order);
            //资金日志流表
            capitalLog.setTradeNo(trade_no);
            capitalLog.setData2(1);
            capitalLogDao.update(capitalLog);
            //更新优惠券表
            if(order.getIsUseCoupons()==1){
                Coupon coupon = couponDao.get(order.getUseCouponsId());
                coupon.setUseStatus(2);
                coupon.setUpdateDate( new Date());
                couponDao.update(coupon);
            }
        }
        return ret;
    }


    /**
     *取消订单业务处理,等待接单和进行中时，退款需要后台审核。
     * @param orderId
     * @param cancelReason
     * @return 1:取消成功;0：取消失败
     */
    public int updateOfCancelOrder(long orderId,String cancelReason){
        int flag;
        Order order = orderDao.get(orderId);
        order.setOrderCancelReason(cancelReason);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cancelTime = simpleDateFormat.format(new Date());
        order.setData3(cancelTime);
        //订单取消，提交后台审核
        OrderCancel orderCancel= new OrderCancel();
        orderCancel.setOrderType(order.getOrderType());
        orderCancel.setOrderId(orderId);
        orderCancel.setOrderNo(order.getOrderNo());
        orderCancel.setOrderStatus(order.getOrderStatus());
        orderCancel.setActionStep(order.getActionStep());
        orderCancel.setCheckState(2);
        orderCancel.setCreateTime(new Date());
        orderCancel.setStatus(1);
        //等待接单时
        if(order.getOrderStatus()==2){
            String orderStatusRemark=this.getRemarkOfCancelOrder(order,false);
            orderCancel.setOrderStatusRemark(orderStatusRemark);
            orderCancel.setIsTimeout(1);
            orderCancelDao.add(orderCancel);
        }else if(order.getOrderStatus()==3){
            //跑客未到达排队地
            if(order.getActionStep()< 2){
                String orderStatusRemark=this.getRemarkOfCancelOrder(order,false);
                orderCancel.setOrderStatusRemark(orderStatusRemark);
                orderCancel.setIsTimeout(1);
            }
            //跑客到达排队地,未开始排队
            else if (order.getActionStep()< 4){
                String orderStatusRemark=this.getRemarkOfCancelOrder(order,true);
                orderCancel.setOrderStatusRemark(orderStatusRemark);
                orderCancel.setIsTimeout(2);
            }
            //跑客已开始排队，不可取消订单
            else{
                return -1;
            }
            orderCancelDao.add(orderCancel);
            //跑客跑客可以继续接单
            DeliveryManState deliveryManState= deliveryManStateDao.getByDeliverManId(order.getDeliveryManId());
            deliveryManState.setGainNewOrder(1);
            deliveryManStateDao.update(deliveryManState);
        }else {
            return -1;
        }
        order.setOrderStatus(9);
        flag = orderDao.update(order);
        //把优惠劵的状态变回以前的状态
        if(order.getUseCouponsId()!=null && order.getUseCouponsId()>0){
            this.updateCouponCanUse(order.getUseCouponsId());
        }
        return flag;
    }

    /**
     *帮买取消订单业务处理,等待接单和进行中时，退款需要后台审核。
     * @param orderId
     * @param cancelReason
     * @return 1:取消成功;0：取消失败
     */
    public int updateOfCancelBuyOrder(long orderId,String cancelReason){
        int ret=0;
        Order order = orderDao.get(orderId);
        order.setOrderCancelReason(cancelReason);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cancelTime = simpleDateFormat.format(new Date());
        order.setData3(cancelTime);
        //订单取消，提交后台审核
        OrderCancel orderCancel= new OrderCancel();
        orderCancel.setOrderId(orderId);
        orderCancel.setOrderNo(order.getOrderNo());
        orderCancel.setCreateTime(new Date());
        orderCancel.setCheckState(2);//未审核
        orderCancel.setStatus(1);
        orderCancel.setOrderType(order.getOrderType());
        orderCancel.setOrderStatus(order.getOrderStatus());
        orderCancel.setActionStep(order.getActionStep());
        if(order.getOrderStatus()==1){
            //更新优惠券,重新设置优惠券可用
            if(order.getUseCouponsId() !=null && order.getUseCouponsId()>0){
                this.updateCouponCanUse(order.getUseCouponsId());
            }
            ret= orderDao.update(order);
            return ret;
        }else if(order.getOrderStatus()==2){
            //等待接单状态
            String OrderStatusRemark=this.getRemarkOfCancelOrder(order,false);
            orderCancel.setIsTimeout(1);
            orderCancel.setOrderStatusRemark(OrderStatusRemark);
            orderCancelDao.add(orderCancel);
            //更新优惠券,重新设置优惠券可用
            if(order.getUseCouponsId() !=null && order.getUseCouponsId()>0){
                this.updateCouponCanUse(order.getUseCouponsId());
            }
            ret= orderDao.update(order);
            return ret;
        }else if(order.getOrderStatus()==3){
            //进行中时
            if(order.getActionStep()< 2){
                //如果跑客没有到达，可以直接取消订单
                //quitMoneyOfCancelBuyOrder(order,false);
                String OrderStatusRemark=this.getRemarkOfCancelOrder(order,false);
                orderCancel.setIsTimeout(1);
                orderCancel.setOrderStatusRemark(OrderStatusRemark);
                orderCancelDao.add(orderCancel);
            }else{
                //超时取消订单扣除5元跑腿费
                //quitMoneyOfCancelBuyOrder(order,true);
                String OrderStatusRemark=this.getRemarkOfCancelOrder(order,true);
                orderCancel.setIsTimeout(2);//超时
                orderCancel.setOrderStatusRemark(OrderStatusRemark);
                orderCancelDao.add(orderCancel);
            }
            //跑客可以继续接单
            DeliveryManState deliveryManState= deliveryManStateDao.getByDeliverManId(order.getDeliveryManId());
            deliveryManState.setGainNewOrder(1);
            deliveryManStateDao.update(deliveryManState);
            //更新优惠券,重新设置优惠券可用
            if(order.getUseCouponsId() !=null && order.getUseCouponsId()>0){
                this.updateCouponCanUse(order.getUseCouponsId());
            }
            order.setOrderStatus(9);
            ret= orderDao.update(order);
            return ret;
        } else {
            return ret;
        }
    }

    /**
     *代排队取消订单业务处理,等待接单和进行中时，退款需要后台审核。
     * @param orderId
     * @param cancelReason
     * @return 1:取消成功;0：取消失败
     */
    public int updateOfCancelLineupOrder(long orderId,String cancelReason){
        int flag = 0;
        Order order = orderDao.get(orderId);
        order.setOrderCancelReason(cancelReason);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cancelTime = simpleDateFormat.format(new Date());
        order.setData3(cancelTime);
        //订单取消，提交后台审核
        OrderCancel orderCancel= new OrderCancel();
        orderCancel.setOrderType(order.getOrderType());
        orderCancel.setOrderId(orderId);
        orderCancel.setOrderNo(order.getOrderNo());
        orderCancel.setOrderStatus(order.getOrderStatus());
        orderCancel.setActionStep(order.getActionStep());
        orderCancel.setCheckState(2);
        orderCancel.setCreateTime(new Date());
        orderCancel.setStatus(1);
        //等待接单时
        if(order.getOrderStatus()==2){
            String orderStatusRemark=this.getRemarkOfCancelOrder(order,false);
            orderCancel.setOrderStatusRemark(orderStatusRemark);
            orderCancel.setIsTimeout(1);
            orderCancelDao.add(orderCancel);
        }else if(order.getOrderStatus()==3){
            //跑客未到达排队地
            if(order.getActionStep()< 2){
                String orderStatusRemark=this.getRemarkOfCancelOrder(order,false);
                orderCancel.setOrderStatusRemark(orderStatusRemark);
                orderCancel.setIsTimeout(1);
            }
            //跑客到达排队地,未开始排队
            else if (order.getActionStep()< 4){
                String orderStatusRemark=this.getRemarkOfCancelOrder(order,true);
                orderCancel.setOrderStatusRemark(orderStatusRemark);
                orderCancel.setIsTimeout(2);
            }
            //跑客已开始排队，不可取消订单
            else{
                return flag;
            }
            orderCancelDao.add(orderCancel);
            //跑客跑客可以继续接单
            DeliveryManState deliveryManState= deliveryManStateDao.getByDeliverManId(order.getDeliveryManId());
            deliveryManState.setGainNewOrder(1);
            deliveryManStateDao.update(deliveryManState);
        }
        order.setOrderStatus(9);
        flag = orderDao.update(order);
        //把优惠劵的状态变回以前的状态
        if(order.getUseCouponsId()!=null && order.getUseCouponsId()>0){
            this.updateCouponCanUse(order.getUseCouponsId());
        }
        return flag;
    }

    /**
     * 重新设置优惠券可用
     * @param couponsId
     */
    private void updateCouponCanUse(long couponsId){
        Coupon coupon = couponDao.get(couponsId);
        coupon.setOrderId((long)0);
        coupon.setUseStatus(1);
        coupon.setUpdateDate(null);
        coupon.setUseAmount(null);
        couponDao.update(coupon);
    }

    /**
     * 取消订单，返回审核时，备注退款详情
     * @param order
     * @param isTimeoutOfCancelOrder  true:超时，扣除5元跑腿费；false：不超时，直接退回
     */
    private String getRemarkOfCancelOrder(Order order, boolean isTimeoutOfCancelOrder){
        //待接单时，取消订单，需要退回跑腿费和小费
        String orderStatusRemark="客户取消订单，";
        //要退回的跑腿费
        BigDecimal refund_amount=order.getActualFreight();
        if(isTimeoutOfCancelOrder){
            //超时，跑腿费扣5元
            refund_amount=refund_amount.subtract(new BigDecimal("5"));
        }
        orderStatusRemark+="跑腿费需退款：";
        orderStatusRemark+=String.valueOf(refund_amount);
        orderStatusRemark+="元";
        //要退回的小费
        if(order.getTips()!=null && order.getTips().compareTo(new BigDecimal("0"))>0){
            BigDecimal tips=order.getTips();
            orderStatusRemark+="；小费需退款：";
            orderStatusRemark+=String.valueOf(tips);
            orderStatusRemark+="元";
        }
        //帮买时要退回的商品费
        if (order.getOrderType()==3){
            OrderGoods orderGoods =orderGoodsDao.getOrderGoods(order.getId());
            if(orderGoods.getAddPayTransactionId() != null){
                BigDecimal addPayMoney=orderGoods.getAddPayMoney();
                orderStatusRemark+="；商品费需退款：";
                orderStatusRemark+=String.valueOf(addPayMoney);
                orderStatusRemark+="元";
            }
        }
        if(isTimeoutOfCancelOrder){
            //超时，跑腿费扣5元
            orderStatusRemark+="；超时取消订单，跑客收入5元";
        }
        return orderStatusRemark;
    }

    /**
     *
     * @方法名称:alipayRefundRequest
     * @内容摘要: ＜支付宝退款请求＞
     * @param out_trade_no 订单支付时传入的商户订单号,不能和 trade_no同时为空。
     * @param trade_no 支付宝交易号，和商户订单号不能同时为空
     * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     * @return
     * String
     */
    public String alipayRefundRequest(String out_trade_no,String trade_no,double refund_amount){

        // 发送请求
        String strResponse = null;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient
                    (AlipayConfig.alipayurl,AlipayConfigApp.app_id,
                            AlipayConfigApp.private_key,AlipayConfig.input_charset,AlipayConfig.input_charset,AlipayConfigApp.alipay_public_key);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            JSONObject refundDate= new JSONObject();
            refundDate.put("out_trade_no",out_trade_no);
            refundDate.put("refund_amount",refund_amount);
            refundDate.put("trade_no",trade_no);
            request.setBizContent(refundDate.toJSONString());

//            AlipayRefundInfo alidata= new AlipayRefundInfo();
//            alidata.setOut_trade_no(out_trade_no);
//            alidata.setRefund_amount(refund_amount);
//            alidata.setTrade_no(trade_no);
            //request.setBizContent(JsonUtils.convertToString(alidata));
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            strResponse=response.getCode();
            if (response.isSuccess()) {
                strResponse="退款成功";
            }else {
                strResponse=response.getSubMsg();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass()).error("alipayRefundRequest", e);
        }
        return strResponse;

    }


    /**
     *
     * @方法名称:weixinRefundRequest
     * @内容摘要: ＜微信退款请求＞
     * @param out_trade_no  商户订单号
     * @param trade_no  退款单号
     * @param totralFee 总金额（分）
     * @param refund_amount 退款金额（分）
     * @param opUserId 操作员ID
     * @return
     * String
     */
    public String weixinRefundRequest(String out_trade_no,String trade_no,double totralFee,double refund_amount,String opUserId){

        // 发送请求
        String strResponse = null;
        try {
            Map<String, String> retMap= WeiChartUtil.refundWei(out_trade_no,trade_no,String.valueOf(totralFee),String.valueOf(refund_amount),opUserId);
            strResponse=retMap.toString();
            String return_code= retMap.get("return_code");
            if("success".equalsIgnoreCase(return_code)){
                strResponse="退款成功";
            }
            else{
                strResponse="退款失败";
            }
            System.out.println("微信退款返回的字符串："+strResponse);
        } catch (Exception e) {
            Logger.getLogger(getClass()).error("alipayRefundRequest", e);
        }
        strResponse="退款失败";
        return strResponse;

    }


    /**
     * 通过支付宝付小费
     * @param capitalLogId
     * @param trade_no 支付宝交易号
     * @return 1：成功；2：失败
     */
    public int addTipsWithAliPay(long capitalLogId,String trade_no){
        try{
            CapitalLog capitalLog = capitalLogDao.get(capitalLogId);
            Order order = orderDao.get(capitalLog.getOrderId());
            order.setTips(capitalLog.getChangeMoney());
            order.setTipsTransactionId(trade_no);
            orderDao.update(order);
            capitalLog.setType(order.getOrderType());
            capitalLog.setTradeNo(trade_no);
            capitalLog.setCapitalChangeReason("提交到支付宝付小费成功！");
            capitalLog.setCapitalChangeReason("支付宝付小费成功");


            capitalLogDao.update(capitalLog);
        }catch(Exception e){
            e.printStackTrace();
            return  2;
        }
        return  1;
    }


    /**
     * 通过微信付小费
     * @param capitalLogId
     * @param trade_no 微信交易号
     * @param total_fee 订单总金额，单位为分
     * @return 1：成功；2：失败；9:已通知
     */
    public int addTipsWithWeixinPay(long capitalLogId,String trade_no,String total_fee){
        try{
            CapitalLog capitalLog = capitalLogDao.get(capitalLogId);

            //校验微信支付成功通知是否多次发送给商户系统
            if(capitalLog.getData2()==1){
                return  9;
            }

            //校验返回的订单金额是否与商户侧的订单金额一致
            if(!total_fee.equals(WeiChartUtil.changeToFen(capitalLog.getChangeMoney().toString()))){
                return  2;
            }

            Order order = orderDao.get(capitalLog.getOrderId());
            order.setTips(capitalLog.getChangeMoney());
            order.setTipsTransactionId(trade_no);
            orderDao.update(order);



            capitalLog.setType(order.getOrderType());
            capitalLog.setTradeNo(trade_no);
            capitalLog.setData2(1);
            capitalLog.setOrderNo(order.getOrderNo());
            capitalLog.setCapitalChangeReason("微信付小费成功");


            capitalLogDao.update(capitalLog);
        }catch(Exception e){
            e.printStackTrace();
            return  2;
        }
        return  1;
    }


    /**
     * 帮买支付宝支付商品费成功
     * @param capitalLogId  资金日志id
     * @param trade_no 支付宝交易号
     * return:1:更新商品支付状态成功；0：更新商品支付状态失败
     */
    public  int updateOfBuyGoodsAliPayMoneySuccess(long capitalLogId,String trade_no){
        int ret=1;
        CapitalLog  capitalLog = capitalLogDao.get(capitalLogId);
        capitalLog.setTradeNo(trade_no);
        capitalLogDao.update(capitalLog);
        OrderGoods orderGoods = orderGoodsDao.getOrderGoods(capitalLog.getOrderId());
        if(orderGoods==null) {
            ret=0;
            return ret;
        }
        orderGoods.setAddPayTransactionId(trade_no);
        orderGoods.setAddPayMoney(capitalLog.getChangeMoney());
        orderGoods.setAddPayTime(new Date());
        ret=orderGoodsDao.update(orderGoods);
        return ret;
    }



    /**
     * 帮买微信支付商品费成功
     * @param capitalLogId  资金日志id
     * @param trade_no 微信交易号
     * @param total_fee 订单总金额，单位为分
     * return:1：成功；0：失败；9:已通知
     */
    public  int updateOfBuyGoodsWeixinPayMoneySuccess(long capitalLogId,String trade_no,String total_fee){
        int ret=1;
        CapitalLog  capitalLog = capitalLogDao.get(capitalLogId);

        //校验微信支付成功通知是否多次发送给商户系统
        if(capitalLog.getData2()==1){
            return  9;
        }

        //校验返回的订单金额是否与商户侧的订单金额一致
        if(!total_fee.equals(WeiChartUtil.changeToFen(capitalLog.getChangeMoney().toString()))){
            return  0;
        }
        capitalLog.setTradeNo(trade_no);
        capitalLog.setData2(1);
        capitalLogDao.update(capitalLog);
        OrderGoods orderGoods = orderGoodsDao.getOrderGoods(capitalLog.getOrderId());
        if(orderGoods==null) {
            ret=0;
            return ret;
        }
        orderGoods.setAddPayTransactionId(trade_no);
        orderGoods.setAddPayMoney(capitalLog.getChangeMoney());
        orderGoods.setAddPayTime(new Date());
        ret=orderGoodsDao.update(orderGoods);
        return ret;
    }


    /**
     * 代排队支付宝支付排队加时费成功
     * @param capitalLogId  资金日志id
     * @param trade_no 支付宝交易号
     * return:1:更新加时支付状态成功；0：更新加时支付状态失败
     */
    public int updateOfLineupAddPayAliPayMoneySuccess(long capitalLogId,String trade_no){
        int ret=1;
        CapitalLog  capitalLog = capitalLogDao.get(capitalLogId);
        capitalLog.setTradeNo(trade_no);
        capitalLogDao.update(capitalLog);
        OrderLineup orderLineup= orderLineupDao.getLineup(capitalLog.getOrderId());
        if(orderLineup!=null&&orderLineup.getId()>0) {
            orderLineup.setAddPayTransactionId(trade_no);
            orderLineup.setAddPayMoney(capitalLog.getChangeMoney());
            orderLineup.setAddPayTime(new Date());
            orderLineup.setLineupRealMoney(orderLineup.getLineupRealMoney().add(capitalLog.getChangeMoney()));
            ret=orderLineupDao.update(orderLineup);
        }else {
            ret=0;
        }
        return ret;
    }

    /**
     * 代排队微信支付排队加时费成功
     * @param capitalLogId  资金日志id
     * @param trade_no 支付宝交易号
     * return:1:更新加时支付状态成功；0：更新加时支付状态失败
     */
    public int updateOfLineupAddPayWeixinPayMoneySuccess(long capitalLogId,String trade_no){
        int ret=1;
        CapitalLog  capitalLog = capitalLogDao.get(capitalLogId);
        capitalLog.setTradeNo(trade_no);
        capitalLogDao.update(capitalLog);
        OrderLineup orderLineup= orderLineupDao.getLineup(capitalLog.getOrderId());
        if(orderLineup!=null&&orderLineup.getId()>0) {
            orderLineup.setAddPayTransactionId(trade_no);
            orderLineup.setAddPayMoney(capitalLog.getChangeMoney());
            orderLineup.setAddPayTime(new Date());
            orderLineup.setLineupRealMoney(orderLineup.getLineupRealMoney().add(capitalLog.getChangeMoney()));
            ret=orderLineupDao.update(orderLineup);
        }else {
            ret=0;
        }
        return ret;
    }



    /**
     *极光推送支付订单成功
     * @param customerId
     */
    public void getJpushPayOrderSuccess(long customerId){
        //            //推送抢单接口
//            JPushClient jpushClient = new JPushClient(PaotuiUtil.JPUSH_MASTER_SECRET, PaotuiUtil.JPUSH_APP_KEY, null, ClientConfig.getInstance());
//
//            // For push, all you need do is to build PushPayload object.
//            PushPayload payload = PushPayload.newBuilder()
//                    .setPlatform(Platform.all())
//                    .setAudience(Audience.alias("c"+String.valueOf(customerId)))
//                    .setNotification(Notification.alert("支付成功"))
//                    .build();
//
//            try {
//                PushResult result = jpushClient.sendPush(payload);
//                logger.info("Got result - " + result);
//
//            } catch (APIConnectionException e) {
//                logger.error("Connection error, should retry later", e);
//
//            } catch (APIRequestException e) {
//                logger.info("HTTP Status: " + e.getStatus());
//                logger.info("Error Code: " + e.getErrorCode());
//                logger.info("Error Message: " + e.getErrorMessage());
//            }
    }
}
