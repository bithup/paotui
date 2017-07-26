package com.xgh.paotui.services;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.xgh.mng.basic.BaseService;
import com.xgh.mng.dao.ISysUserDao;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.alipay.config.AlipayConfig;
import com.xgh.paotui.alipayApp.config.AlipayConfigApp;
import com.xgh.paotui.dao.*;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.weixin.pay.WeiChartUtil;
import com.xgh.util.DictUtils;
import com.xgh.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service("orderCancelService")
public class OrderCancelServiceImpl extends BaseService implements IOrderCancelService {

    @Autowired
    protected IOrderCancelDao orderCancelDao;

    @Autowired
    protected IOrderDao orderDao;

    @Autowired
    protected ISysUserDao sysUserDao;

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
    protected IOrderGoodsDao orderGoodsDao;

    public int add(OrderCancel orderCancel) {
        return orderCancelDao.add(orderCancel);
    }

    public int save(OrderCancel orderCancel) {
        return orderCancelDao.add(orderCancel);
    }

    public OrderCancel get(Long id) {
        return orderCancelDao.get(id);
    }

    public Map<String, Object> getGridList(Map<String, Object> map) {
        Map<String, Object> gridMap = new HashMap<String, Object>();
        List<Map<String, Object>> dataList = orderCancelDao.getListMapPage(map);
        if (dataList == null) {
            dataList = new ArrayList<Map<String, Object>>();
        }
        for (Map<String, Object> dataList_ : dataList) {
            if (dataList_.get("orderType") != null) {
                String orderType = String.valueOf(dataList_.get("orderType"));
                dataList_.put("orderTypeName", DictUtils.getDictLabel(orderType, "paotui_order_type", ""));
            }
            if (dataList_.get("checkState") != null) {
                String checkState = String.valueOf(dataList_.get("checkState"));
                dataList_.put("checkStateName", DictUtils.getDictLabel(checkState, "paotui_check_state", ""));
            }
            Long checkUserId = (Long) dataList_.get("checkUserId");
            if (checkUserId != null && checkUserId != 0) {
                SysUser sysUser = sysUserDao.get(checkUserId);
                dataList_.put("checkUserName", sysUser.getUserName());
            }
            String data1 = (String) dataList_.get("data1");
            String msg="";
            if(StringUtils.isNotEmpty(data1)){
                JSONObject jsonCancelInfo=JSONObject.parseObject(data1);
                if("success".equals(jsonCancelInfo.get("refundFreight"))){
                    msg+="跑腿费退款成功；";
                }
                else if("fail".equals(jsonCancelInfo.get("refundFreight"))){
                    msg+="跑腿费退款失败；";
                }
                if("success".equals(jsonCancelInfo.get("refundTips"))){
                    msg+="小费退款成功；";
                }
                else if("fail".equals(jsonCancelInfo.get("refundTips"))){
                    msg+="小费退款失败；";
                }
                if("success".equals(jsonCancelInfo.get("refundGoods"))){
                    msg+="商品费退款成功；";
                }
                else if("fail".equals(jsonCancelInfo.get("refundGoods"))){
                    msg+="商品费退款失败；";
                }
            }
            dataList_.put("jsonCancelInfo",msg);
        }
        gridMap.put("Rows", dataList);
        gridMap.put("Total", orderCancelDao.getRows(map));
        return gridMap;
    }

    /**
     * 分页查询
     *
     * @param map
     * @return
     */
    public Map<String, Object> getPageList(Map<String, Object> map) {

        Map<String, Object> gridMap = new HashMap<String, Object>();

        List<Map<String, Object>> dataList = orderCancelDao.getListMapPage(map);
        gridMap.put("Rows", dataList);
        gridMap.put("Total", orderCancelDao.getRows(map));
        return gridMap;
    }

    /**
     * 后台审核订单取消的退款业务
     *
     * @param orderCancelId
     * @param checkState    审核状态
     * @param currentUserId 审核人id
     * @return  后台退款信息
     */
    public String updateOrderCancelCheck(long orderCancelId, int checkState, long currentUserId) {
        JSONObject cancelFailInfo = new JSONObject();
        OrderCancel orderCancel = orderCancelDao.get(orderCancelId);
        //审核通过,退款
        if(checkState==1){
            Order order = orderDao.get(orderCancel.getOrderId());
            JSONObject oldCancelFailInfo=new JSONObject();
            if(StringUtil.isNotEmpty(orderCancel.getData1())){
                oldCancelFailInfo=JSONObject.parseObject(orderCancel.getData1());
            }
            //取消订单，需要退回跑腿费、小费和商品费
            //如果后续不需要人工退款，去掉orderCancel这个表操作，直接调用这个方法
            int orderType = order.getOrderType();
            if (orderType==1||orderType==2){
                cancelFailInfo=this.refundMoneyOfCancelSendOrder(order,orderCancel.getIsTimeout(),String.valueOf(currentUserId),oldCancelFailInfo);
            }else if (orderType==3){
                cancelFailInfo=this.refundMoneyOfCancelBuyOrder(order,orderCancel.getIsTimeout(),String.valueOf(currentUserId),oldCancelFailInfo);
            }else if (orderType==4){
                cancelFailInfo=this.refundMoneyOfCancelLineupOrder(order,orderCancel.getIsTimeout(),String.valueOf(currentUserId),oldCancelFailInfo);
            }
            orderCancel.setData1(cancelFailInfo.toJSONString());//订单取消成功与失败信息
            orderCancel.setCheckState(checkState);
            orderCancel.setCheckTime(new Date());
            orderCancel.setCheckUserId(currentUserId);
            orderCancelDao.update(orderCancel);
        }
        //审核不通过，不退款
        else{
            orderCancel.setCheckState(checkState);
            orderCancel.setCheckTime(new Date());
            orderCancel.setCheckUserId(currentUserId);
            orderCancelDao.update(orderCancel);
        }
        return cancelFailInfo.toJSONString();
    }

    /**
     * 帮送取消订单，需要退回跑腿费、小费
     * @param order
     * @param isTimeout 2:超时，扣除5元跑腿费；1：不超时，直接退回
     * @return 返回退款失败信息
     */
    private JSONObject refundMoneyOfCancelSendOrder(Order order, int isTimeout, String currentUserId, JSONObject oldCancelFailInfo) {
        JSONObject cancelFailInfo=new JSONObject();
        //计算跑腿费退款金额
        BigDecimal refund_amount = order.getActualFreight();
        //超时，跑腿费扣5元
        if (isTimeout == 2) {
            refund_amount = refund_amount.subtract(new BigDecimal("5"));
        }
        Customer customer = customerDao.get(order.getCustomerId());
        DeliveryMan deliveryMan = deliveryManDao.get(order.getDeliveryManId());
        //更新退款跑腿费日志（成功）
        CapitalLog capitalLog = new CapitalLog();
        capitalLog.setAccountType(1);
        capitalLog.setAccountId(customer.getId());
        capitalLog.setLoginName(customer.getLoginName());
        capitalLog.setRealName(customer.getRealName());
        capitalLog.setType(order.getOrderType());
        capitalLog.setOrderId(order.getId());
        capitalLog.setOrderNo(order.getOrderNo());
        capitalLog.setTradeNo(order.getPayTransactionId());
        capitalLog.setCreateDate(new Date());
        capitalLog.setStatus(1);
        //更新退款跑腿费日志（失败）
        CapitalLog capitalLog_ = new CapitalLog();
        capitalLog_.setAccountType(1);
        capitalLog_.setAccountId(customer.getId());
        capitalLog_.setLoginName(customer.getLoginName());
        capitalLog_.setRealName(customer.getRealName());
        capitalLog_.setType(order.getOrderType());
        capitalLog_.setOrderId(order.getId());
        capitalLog_.setOrderNo(order.getOrderNo());
        capitalLog_.setTradeNo(order.getPayTransactionId());
        capitalLog_.setCreateDate(new Date());
        capitalLog_.setStatus(1);
        //更新跑客资金日志
        CapitalLog capitalLogDeliveryMan = new CapitalLog();
        capitalLogDeliveryMan.setAccountType(2);
        capitalLogDeliveryMan.setAccountId(deliveryMan.getId());
        capitalLogDeliveryMan.setLoginName(deliveryMan.getLoginName());
        capitalLogDeliveryMan.setRealName(deliveryMan.getRealName());
        capitalLogDeliveryMan.setType(order.getOrderType());
        capitalLogDeliveryMan.setOrderId(order.getId());
        capitalLogDeliveryMan.setOrderNo(order.getOrderNo());
        capitalLogDeliveryMan.setTradeNo(order.getPayTransactionId());
        capitalLogDeliveryMan.setCapitalChangeReason("客户超时取消订单，增加5元跑腿费");
        capitalLogDeliveryMan.setChangeMoney(new BigDecimal(5));
        capitalLogDeliveryMan.setCreateDate(new Date());
        capitalLogDeliveryMan.setStatus(1);
        /*
         *根据不同的跑腿费来源，执行不同的退款方式
        */
        String refundFreight=(String)oldCancelFailInfo.get("refundFreight");
        if(!"success".equals(refundFreight) ){
            //余额支付退跑腿费
            if (order.getPayType() == 1) {
                customer.setBalance(customer.getBalance().add(refund_amount));
                int flag=customerDao.update(customer);
                if(flag>0){
                    capitalLog.setTradeFrom(1);
                    capitalLog.setCapitalChangeReason("余额退款跑腿费成功");
                    capitalLog.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog);
                    if (isTimeout == 2) {
                        //跑客增加5元跑腿费
                        deliveryMan.setBalance(deliveryMan.getBalance().add(new BigDecimal(5)));
                        deliveryManDao.update(deliveryMan);
                        capitalLogDeliveryMan.setTradeFrom(1);
                        capitalLogDao.add(capitalLogDeliveryMan);
                    }
                    cancelFailInfo.put("refundFreight","success");
                }else{
                    capitalLog_.setTradeFrom(1);
                    capitalLog_.setCapitalChangeReason("余额退款跑腿费失败");
                    capitalLog_.setChangeMoney(order.getActualFreight());
                    capitalLogDao.add(capitalLog_);
                    cancelFailInfo.put("refundFreight","fail");
                }
            }
            //微信支付退跑腿费
            else if (order.getPayType() == 2) {
                String refundResponse = this.weixinRefundRequest(order.getOrderNo(), order.getPayTransactionId(),
                        WeiChartUtil.changeToFen(order.getActualFreight().toString()), WeiChartUtil.changeToFen(refund_amount.toString()),currentUserId);
                if ("退款成功".equals(refundResponse)) {
                    //更新资金日志表
                    capitalLog.setTradeFrom(2);
                    capitalLog.setCapitalChangeReason("微信退款跑腿费成功");
                    capitalLog.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog);
                    if (isTimeout == 2) {
                        //跑客增加5元跑腿费
                        deliveryMan.setBalance(deliveryMan.getBalance().add(new BigDecimal(5)));
                        deliveryManDao.update(deliveryMan);
                        capitalLogDeliveryMan.setTradeFrom(2);
                        capitalLogDao.add(capitalLogDeliveryMan);
                    }
                    cancelFailInfo.put("refundFreight","success");
                } else {
                    capitalLog_.setTradeFrom(2);
                    capitalLog_.setCapitalChangeReason("微信退款跑腿费失败");
                    capitalLog_.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog_);
                    cancelFailInfo.put("refundFreight","fail");
                }
            }
            //支付宝支付退跑腿费
            else if (order.getPayType() == 3) {
                String refundResponse = this.alipayRefundRequest(order.getOrderNo(), order.getPayTransactionId(), refund_amount.doubleValue());
                if ("退款成功".equals(refundResponse)) {
                    capitalLog.setTradeFrom(3);
                    capitalLog.setCapitalChangeReason("支付宝退款跑腿费成功");
                    capitalLog.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog);
                    if (isTimeout == 2) {
                        //跑客增加5元跑腿费
                        deliveryMan.setBalance(deliveryMan.getBalance().add(new BigDecimal(5)));
                        deliveryManDao.update(deliveryMan);
                        capitalLogDeliveryMan.setTradeFrom(3);
                        capitalLogDao.add(capitalLogDeliveryMan);
                    }
                    cancelFailInfo.put("refundFreight","success");
                } else {
                    capitalLog_.setTradeFrom(3);
                    capitalLog_.setCapitalChangeReason("支付宝退款跑腿费失败");
                    capitalLog_.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog_);
                    cancelFailInfo.put("refundFreight","fail");
                }
            }
        }
        /**
         * 有小费的情况下，小费原路退回
         */
        String refundTips=(String)oldCancelFailInfo.get("refundTips");
        if(!"success".equals(refundTips)  ){
            if (order.getTips() != null && order.getTips().compareTo(new BigDecimal("0")) > 0) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tradeNo", order.getTipsTransactionId());
                //根据小费支付日志获取小费支付来源
                List<CapitalLog> capitalLogs = capitalLogDao.getList(map);
                if (capitalLogs!=null&&capitalLogs.size() > 0) {
                    CapitalLog capitalLogTips = capitalLogs.get(0);
                    if (capitalLogTips.getTradeFrom() == 2) {
                        String refundResponse = this.weixinRefundRequest(order.getOrderNo(), order.getTipsTransactionId(),
                                WeiChartUtil.changeToFen(order.getTips().toString()), WeiChartUtil.changeToFen(order.getTips().toString()),currentUserId);
                        if ("退款成功".equals(refundResponse)) {
                            capitalLog.setTradeFrom(2);
                            capitalLog.setCapitalChangeReason("微信退款小费成功");
                            capitalLog.setChangeMoney(order.getTips());
                            cancelFailInfo.put("refundTips","success");
                        }else{
                            capitalLog_.setTradeFrom(2);
                            capitalLog_.setCapitalChangeReason("微信退款小费失败");
                            capitalLog_.setChangeMoney(order.getTips());
                            capitalLogDao.add(capitalLog_);
                            cancelFailInfo.put("refundTips","fail");
                        }
                    } else if (capitalLogTips.getTradeFrom() == 3) {
                        String ret = this.alipayRefundRequest("", order.getTipsTransactionId(), order.getTips().doubleValue());
                        if ("退款成功".equals(ret)) {
                            capitalLog.setTradeFrom(3);
                            capitalLog.setCapitalChangeReason("支付宝退款小费成功");
                            capitalLog.setChangeMoney(order.getTips());
                            capitalLogDao.add(capitalLog);
                            cancelFailInfo.put("refundTips","success");
                        }else{
                            capitalLog_.setTradeFrom(3);
                            capitalLog_.setCapitalChangeReason("支付宝退款小费失败");
                            capitalLog_.setChangeMoney(order.getTips());
                            capitalLogDao.add(capitalLog_);
                            cancelFailInfo.put("refundTips","fail");
                        }
                    }
                } else {
                    cancelFailInfo.put("refundTips","fail");
                }
            }
        }
        return cancelFailInfo;
    }

    /**
     * 帮买取消订单，需要退回跑腿费、小费和商品费
     * @param order
     * @param isTimeout 2:超时，扣除5元跑腿费；1：不超时，直接退回
     * @return 返回退款失败信息
     */
    private JSONObject refundMoneyOfCancelBuyOrder(Order order, int isTimeout, String currentUserId, JSONObject oldCancelFailInfo) {
        //退款失败信息
        JSONObject cancelFailInfo=new JSONObject();
        //计算跑腿费退款金额
        BigDecimal refund_amount = order.getActualFreight();
        //超时，跑腿费扣5元
        if (isTimeout == 2) {
            refund_amount = refund_amount.subtract(new BigDecimal("5"));
        }
        Customer customer = customerDao.get(order.getCustomerId());
        DeliveryMan deliveryMan = deliveryManDao.get(order.getDeliveryManId());
        //更新退款跑腿费日志（成功）
        CapitalLog capitalLog = new CapitalLog();
        capitalLog.setAccountType(1);
        capitalLog.setAccountId(customer.getId());
        capitalLog.setLoginName(customer.getLoginName());
        capitalLog.setRealName(customer.getRealName());
        capitalLog.setType(order.getOrderType());
        capitalLog.setOrderId(order.getId());
        capitalLog.setOrderNo(order.getOrderNo());
        capitalLog.setTradeNo(order.getPayTransactionId());
        capitalLog.setCreateDate(new Date());
        capitalLog.setStatus(1);
        //更新退款跑腿费日志（失败）
        CapitalLog capitalLog_ = new CapitalLog();
        capitalLog_.setAccountType(1);
        capitalLog_.setAccountId(customer.getId());
        capitalLog_.setLoginName(customer.getLoginName());
        capitalLog_.setRealName(customer.getRealName());
        capitalLog_.setType(order.getOrderType());
        capitalLog_.setOrderId(order.getId());
        capitalLog_.setOrderNo(order.getOrderNo());
        capitalLog_.setTradeNo(order.getPayTransactionId());
        capitalLog_.setCreateDate(new Date());
        capitalLog_.setStatus(1);
        //更新跑客资金日志
        CapitalLog capitalLogDeliveryMan = new CapitalLog();
        capitalLogDeliveryMan.setAccountType(2);
        capitalLogDeliveryMan.setAccountId(deliveryMan.getId());
        capitalLogDeliveryMan.setLoginName(deliveryMan.getLoginName());
        capitalLogDeliveryMan.setRealName(deliveryMan.getRealName());
        capitalLogDeliveryMan.setType(order.getOrderType());
        capitalLogDeliveryMan.setOrderId(order.getId());
        capitalLogDeliveryMan.setOrderNo(order.getOrderNo());
        capitalLogDeliveryMan.setTradeNo(order.getPayTransactionId());
        capitalLogDeliveryMan.setCapitalChangeReason("客户超时取消订单，增加5元跑腿费");
        capitalLogDeliveryMan.setChangeMoney(new BigDecimal(5));
        capitalLogDeliveryMan.setCreateDate(new Date());
        capitalLogDeliveryMan.setStatus(1);
        /*
         *根据不同的跑腿费来源，执行不同的退款方式
        */
        String refundFreight=(String)oldCancelFailInfo.get("refundFreight");
        if(!"success".equals(refundFreight) ){
            //余额支付退跑腿费
            if (order.getPayType() == 1) {
                customer.setBalance(customer.getBalance().add(refund_amount));
                int flag=customerDao.update(customer);
                if(flag>0){
                    capitalLog.setTradeFrom(1);
                    capitalLog.setCapitalChangeReason("余额退款跑腿费成功");
                    capitalLog.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog);
                    if (isTimeout == 2) {
                        //跑客增加5元跑腿费
                        deliveryMan.setBalance(deliveryMan.getBalance().add(new BigDecimal(5)));
                        deliveryManDao.update(deliveryMan);
                        capitalLogDeliveryMan.setTradeFrom(1);
                        capitalLogDao.add(capitalLogDeliveryMan);
                    }
                    cancelFailInfo.put("refundFreight","success");
                }else{
                    capitalLog_.setTradeFrom(1);
                    capitalLog_.setCapitalChangeReason("余额退款跑腿费失败");
                    capitalLog_.setChangeMoney(order.getActualFreight());
                    capitalLogDao.add(capitalLog_);
                    cancelFailInfo.put("refundFreight","fail");
                }
            }
            //微信支付退跑腿费
            else if (order.getPayType() == 2) {
                String refundResponse = this.weixinRefundRequest(order.getOrderNo(), order.getPayTransactionId(),
                        WeiChartUtil.changeToFen(order.getActualFreight().toString()), WeiChartUtil.changeToFen(refund_amount.toString()),currentUserId);
                if ("退款成功".equals(refundResponse)) {
                    //更新资金日志表
                    capitalLog.setTradeFrom(2);
                    capitalLog.setCapitalChangeReason("微信退款跑腿费成功");
                    capitalLog.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog);
                    if (isTimeout == 2) {
                        //跑客增加5元跑腿费
                        deliveryMan.setBalance(deliveryMan.getBalance().add(new BigDecimal(5)));
                        deliveryManDao.update(deliveryMan);
                        capitalLogDeliveryMan.setTradeFrom(2);
                        capitalLogDao.add(capitalLogDeliveryMan);
                    }
                    cancelFailInfo.put("refundFreight","success");
                } else {
                    capitalLog_.setTradeFrom(2);
                    capitalLog_.setCapitalChangeReason("微信退款跑腿费失败");
                    capitalLog_.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog_);
                    cancelFailInfo.put("refundFreight","fail");
                }
            }
            //支付宝支付退跑腿费
            else if (order.getPayType() == 3) {
                String refundResponse = this.alipayRefundRequest(order.getOrderNo(), order.getPayTransactionId(), refund_amount.doubleValue());
                if ("退款成功".equals(refundResponse)) {
                    capitalLog.setTradeFrom(3);
                    capitalLog.setCapitalChangeReason("支付宝退款跑腿费成功");
                    capitalLog.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog);
                    if (isTimeout == 2) {
                        //跑客增加5元跑腿费
                        deliveryMan.setBalance(deliveryMan.getBalance().add(new BigDecimal(5)));
                        deliveryManDao.update(deliveryMan);
                        capitalLogDeliveryMan.setTradeFrom(3);
                        capitalLogDao.add(capitalLogDeliveryMan);
                    }
                    cancelFailInfo.put("refundFreight","success");
                } else {
                    capitalLog_.setTradeFrom(3);
                    capitalLog_.setCapitalChangeReason("支付宝退款跑腿费失败");
                    capitalLog_.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog_);
                    cancelFailInfo.put("refundFreight","fail");
                }
            }
        }
        /**
         * 有小费的情况下，小费原路退回
         */
        String refundTips=(String)oldCancelFailInfo.get("refundTips");
        if(!"success".equals(refundTips)  ){
            if (order.getTips() != null && order.getTips().compareTo(new BigDecimal("0")) > 0) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tradeNo", order.getTipsTransactionId());
                //根据小费支付日志获取小费支付来源
                List<CapitalLog> capitalLogs = capitalLogDao.getList(map);
                if (capitalLogs!=null&&capitalLogs.size() > 0) {
                    CapitalLog capitalLogTips = capitalLogs.get(0);
                    if (capitalLogTips.getTradeFrom() == 2) {
                        String refundResponse = this.weixinRefundRequest(order.getOrderNo(), order.getTipsTransactionId(),
                                WeiChartUtil.changeToFen(order.getTips().toString()), WeiChartUtil.changeToFen(order.getTips().toString()),currentUserId);
                        if ("退款成功".equals(refundResponse)) {
                            capitalLog.setTradeFrom(2);
                            capitalLog.setCapitalChangeReason("微信退款小费成功");
                            capitalLog.setChangeMoney(order.getTips());
                            cancelFailInfo.put("refundTips","success");
                        }else{
                            capitalLog_.setTradeFrom(2);
                            capitalLog_.setCapitalChangeReason("微信退款小费失败");
                            capitalLog_.setChangeMoney(order.getTips());
                            capitalLogDao.add(capitalLog_);
                            cancelFailInfo.put("refundTips","fail");
                        }
                    } else if (capitalLogTips.getTradeFrom() == 3) {
                        String ret = this.alipayRefundRequest("", order.getTipsTransactionId(), order.getTips().doubleValue());
                        if ("退款成功".equals(ret)) {
                            capitalLog.setTradeFrom(3);
                            capitalLog.setCapitalChangeReason("支付宝退款小费成功");
                            capitalLog.setChangeMoney(order.getTips());
                            capitalLogDao.add(capitalLog);
                            cancelFailInfo.put("refundTips","success");
                        }else{
                            capitalLog_.setTradeFrom(3);
                            capitalLog_.setCapitalChangeReason("支付宝退款小费失败");
                            capitalLog_.setChangeMoney(order.getTips());
                            capitalLogDao.add(capitalLog_);
                            cancelFailInfo.put("refundTips","fail");
                        }
                    }
                } else {
                    cancelFailInfo.put("refundTips","fail");
                }
            }
        }
        /**
         *有商品费的情况下，商品费原路退回
         */
        String refundGoods=(String)oldCancelFailInfo.get("refundGoods");
        if(!"success".equals(refundGoods)  ){
            OrderGoods orderGoods = orderGoodsDao.getOrderGoods(order.getId());
            if (orderGoods.getAddPayTransactionId() != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tradeNo", orderGoods.getAddPayTransactionId());
                //根据商品费支付日志获取商品支付来源
                List<CapitalLog> capitalLogs = capitalLogDao.getList(map);
                if (capitalLogs!=null&&capitalLogs.size() > 0) {
                    CapitalLog capitalLogGoods = capitalLogs.get(0);
                    if (capitalLogGoods.getTradeFrom() == 2) {
                        String refundResponse = this.weixinRefundRequest(order.getOrderNo(), orderGoods.getAddPayTransactionId(),
                                WeiChartUtil.changeToFen(orderGoods.getAddPayMoney().toString()), WeiChartUtil.changeToFen(orderGoods.getAddPayMoney().toString()),currentUserId);
                        if ("退款成功".equals(refundResponse)) {
                            capitalLog.setTradeFrom(2);
                            capitalLog.setCapitalChangeReason("微信退款商品费成功");
                            capitalLog.setChangeMoney(orderGoods.getAddPayMoney());
                            capitalLogDao.add(capitalLog);
                            cancelFailInfo.put("refundGoods","success");
                        }else{
                            capitalLog_.setTradeFrom(2);
                            capitalLog_.setCapitalChangeReason("微信退款商品费失败");
                            capitalLog_.setChangeMoney(orderGoods.getAddPayMoney());
                            capitalLogDao.add(capitalLog_);
                            cancelFailInfo.put("refundGoods","fail");
                        }
                    } else if (capitalLogGoods.getTradeFrom() == 3) {
                        String ret = this.alipayRefundRequest("",  orderGoods.getAddPayTransactionId(), orderGoods.getAddPayMoney().doubleValue());
                        if ("退款成功".equals(ret)) {
                            capitalLog.setTradeFrom(3);
                            capitalLog.setCapitalChangeReason("支付宝退款商品费成功");
                            capitalLog.setChangeMoney(orderGoods.getAddPayMoney());
                            capitalLogDao.add(capitalLog);
                            cancelFailInfo.put("refundGoods","success");
                        }else{
                            capitalLog_.setTradeFrom(3);
                            capitalLog_.setCapitalChangeReason("支付宝退款商品费失败");
                            capitalLog_.setChangeMoney(orderGoods.getAddPayMoney());
                            capitalLogDao.add(capitalLog_);
                            cancelFailInfo.put("refundGoods","fail");
                        }
                    }
                } else {
                    cancelFailInfo.put("refundGoods","fail");
                }
            }
        }
        return cancelFailInfo;
    }

    /**
     * 代排队取消订单，需要退回跑腿费、小费
     * @param order
     * @param isTimeout 2:超时，扣除5元跑腿费；1：不超时，直接退回
     * @return 返回退款失败信息
     */
    private JSONObject refundMoneyOfCancelLineupOrder(Order order, int isTimeout, String currentUserId, JSONObject oldCancelFailInfo) {
        //退款失败信息
        JSONObject cancelFailInfo=new JSONObject();
        //计算跑腿费退款金额
        BigDecimal refund_amount = order.getActualFreight();
        //超时，跑腿费扣5元
        if (isTimeout == 2) {
            refund_amount = refund_amount.subtract(new BigDecimal("5"));
        }
        Customer customer = customerDao.get(order.getCustomerId());
        DeliveryMan deliveryMan = deliveryManDao.get(order.getDeliveryManId());
        //更新退款跑腿费日志（成功）
        CapitalLog capitalLog = new CapitalLog();
        capitalLog.setAccountType(1);
        capitalLog.setAccountId(customer.getId());
        capitalLog.setLoginName(customer.getLoginName());
        capitalLog.setRealName(customer.getRealName());
        capitalLog.setType(order.getOrderType());
        capitalLog.setOrderId(order.getId());
        capitalLog.setOrderNo(order.getOrderNo());
        capitalLog.setTradeNo(order.getPayTransactionId());
        capitalLog.setCreateDate(new Date());
        capitalLog.setStatus(1);
        //更新退款跑腿费日志（失败）
        CapitalLog capitalLog_ = new CapitalLog();
        capitalLog_.setAccountType(1);
        capitalLog_.setAccountId(customer.getId());
        capitalLog_.setLoginName(customer.getLoginName());
        capitalLog_.setRealName(customer.getRealName());
        capitalLog_.setType(order.getOrderType());
        capitalLog_.setOrderId(order.getId());
        capitalLog_.setOrderNo(order.getOrderNo());
        capitalLog_.setTradeNo(order.getPayTransactionId());
        capitalLog_.setCreateDate(new Date());
        capitalLog_.setStatus(1);
        //更新跑客资金日志
        CapitalLog capitalLogDeliveryMan = new CapitalLog();
        capitalLogDeliveryMan.setAccountType(2);
        capitalLogDeliveryMan.setAccountId(deliveryMan.getId());
        capitalLogDeliveryMan.setLoginName(deliveryMan.getLoginName());
        capitalLogDeliveryMan.setRealName(deliveryMan.getRealName());
        capitalLogDeliveryMan.setType(order.getOrderType());
        capitalLogDeliveryMan.setOrderId(order.getId());
        capitalLogDeliveryMan.setOrderNo(order.getOrderNo());
        capitalLogDeliveryMan.setTradeNo(order.getPayTransactionId());
        capitalLogDeliveryMan.setCapitalChangeReason("客户超时取消订单，增加5元跑腿费");
        capitalLogDeliveryMan.setChangeMoney(new BigDecimal(5));
        capitalLogDeliveryMan.setCreateDate(new Date());
        capitalLogDeliveryMan.setStatus(1);
        /*
         *根据不同的跑腿费来源，执行不同的退款方式
        */
        String refundFreight=(String)oldCancelFailInfo.get("refundFreight");
        if(!"success".equals(refundFreight) ){
            //余额支付退跑腿费
            if (order.getPayType() == 1) {
                customer.setBalance(customer.getBalance().add(refund_amount));
                int flag=customerDao.update(customer);
                if(flag>0){
                    capitalLog.setTradeFrom(1);
                    capitalLog.setCapitalChangeReason("余额退款跑腿费成功");
                    capitalLog.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog);
                    if (isTimeout == 2) {
                        //跑客增加5元跑腿费
                        deliveryMan.setBalance(deliveryMan.getBalance().add(new BigDecimal(5)));
                        deliveryManDao.update(deliveryMan);
                        capitalLogDeliveryMan.setTradeFrom(1);
                        capitalLogDao.add(capitalLogDeliveryMan);
                    }
                    cancelFailInfo.put("refundFreight","success");
                }else{
                    capitalLog_.setTradeFrom(1);
                    capitalLog_.setCapitalChangeReason("余额退款跑腿费失败");
                    capitalLog_.setChangeMoney(order.getActualFreight());
                    capitalLogDao.add(capitalLog_);
                    cancelFailInfo.put("refundFreight","fail");
                }
            }
            //微信支付退跑腿费
            else if (order.getPayType() == 2) {
                String refundResponse = this.weixinRefundRequest(order.getOrderNo(), order.getPayTransactionId(),
                        WeiChartUtil.changeToFen(order.getActualFreight().toString()), WeiChartUtil.changeToFen(refund_amount.toString()),currentUserId);
                if ("退款成功".equals(refundResponse)) {
                    //更新资金日志表
                    capitalLog.setTradeFrom(2);
                    capitalLog.setCapitalChangeReason("微信退款跑腿费成功");
                    capitalLog.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog);
                    if (isTimeout == 2) {
                        //跑客增加5元跑腿费
                        deliveryMan.setBalance(deliveryMan.getBalance().add(new BigDecimal(5)));
                        deliveryManDao.update(deliveryMan);
                        capitalLogDeliveryMan.setTradeFrom(2);
                        capitalLogDao.add(capitalLogDeliveryMan);
                    }
                    cancelFailInfo.put("refundFreight","success");
                } else {
                    capitalLog_.setTradeFrom(2);
                    capitalLog_.setCapitalChangeReason("微信退款跑腿费失败");
                    capitalLog_.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog_);
                    cancelFailInfo.put("refundFreight","fail");
                }
            }
            //支付宝支付退跑腿费
            else if (order.getPayType() == 3) {
                String refundResponse = this.alipayRefundRequest(order.getOrderNo(), order.getPayTransactionId(), refund_amount.doubleValue());
                if ("退款成功".equals(refundResponse)) {
                    capitalLog.setTradeFrom(3);
                    capitalLog.setCapitalChangeReason("支付宝退款跑腿费成功");
                    capitalLog.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog);
                    if (isTimeout == 2) {
                        //跑客增加5元跑腿费
                        deliveryMan.setBalance(deliveryMan.getBalance().add(new BigDecimal(5)));
                        deliveryManDao.update(deliveryMan);
                        capitalLogDeliveryMan.setTradeFrom(3);
                        capitalLogDao.add(capitalLogDeliveryMan);
                    }
                    cancelFailInfo.put("refundFreight","success");
                } else {
                    capitalLog_.setTradeFrom(3);
                    capitalLog_.setCapitalChangeReason("支付宝退款跑腿费失败");
                    capitalLog_.setChangeMoney(refund_amount);
                    capitalLogDao.add(capitalLog_);
                    cancelFailInfo.put("refundFreight","fail");
                }
            }
        }
        /**
         * 有小费的情况下，小费原路退回
         */
        String refundTips=(String)oldCancelFailInfo.get("refundTips");
        if(!"success".equals(refundTips)  ){
            if (order.getTips() != null && order.getTips().compareTo(new BigDecimal("0")) > 0) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tradeNo", order.getTipsTransactionId());
                //根据小费支付日志获取小费支付来源
                List<CapitalLog> capitalLogs = capitalLogDao.getList(map);
                if (capitalLogs!=null&&capitalLogs.size() > 0) {
                    CapitalLog capitalLogTips = capitalLogs.get(0);
                    if (capitalLogTips.getTradeFrom() == 2) {
                        String refundResponse = this.weixinRefundRequest(order.getOrderNo(), order.getTipsTransactionId(),
                                WeiChartUtil.changeToFen(order.getTips().toString()), WeiChartUtil.changeToFen(order.getTips().toString()),currentUserId);
                        if ("退款成功".equals(refundResponse)) {
                            capitalLog.setTradeFrom(2);
                            capitalLog.setCapitalChangeReason("微信退款小费成功");
                            capitalLog.setChangeMoney(order.getTips());
                            cancelFailInfo.put("refundTips","success");
                        }else{
                            capitalLog_.setTradeFrom(2);
                            capitalLog_.setCapitalChangeReason("微信退款小费失败");
                            capitalLog_.setChangeMoney(order.getTips());
                            capitalLogDao.add(capitalLog_);
                            cancelFailInfo.put("refundTips","fail");
                        }
                    } else if (capitalLogTips.getTradeFrom() == 3) {
                        String ret = this.alipayRefundRequest("", order.getTipsTransactionId(), order.getTips().doubleValue());
                        if ("退款成功".equals(ret)) {
                            capitalLog.setTradeFrom(3);
                            capitalLog.setCapitalChangeReason("支付宝退款小费成功");
                            capitalLog.setChangeMoney(order.getTips());
                            capitalLogDao.add(capitalLog);
                            cancelFailInfo.put("refundTips","success");
                        }else{
                            capitalLog_.setTradeFrom(3);
                            capitalLog_.setCapitalChangeReason("支付宝退款小费失败");
                            capitalLog_.setChangeMoney(order.getTips());
                            capitalLogDao.add(capitalLog_);
                            cancelFailInfo.put("refundTips","fail");
                        }
                    }
                } else {
                    cancelFailInfo.put("refundTips","fail");
                }
            }
        }
        return cancelFailInfo;
    }

    /**
     * @param out_trade_no  订单支付时传入的商户订单号,不能和 trade_no同时为空。
     * @param trade_no      支付宝交易号，和商户订单号不能同时为空
     * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     * @return String
     * @方法名称:alipayRefundRequest
     * @内容摘要: ＜支付宝退款请求＞
     */
    public String alipayRefundRequest(String out_trade_no, String trade_no, double refund_amount) {
        // 发送请求
        String strResponse = null;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient
                    (AlipayConfig.alipayurl, AlipayConfigApp.app_id,
                            AlipayConfigApp.private_key, AlipayConfig.input_charset, AlipayConfig.input_charset, AlipayConfigApp.alipay_public_key);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            JSONObject refundDate = new JSONObject();
            refundDate.put("out_trade_no", out_trade_no);
            refundDate.put("refund_amount", refund_amount);
            refundDate.put("trade_no", trade_no);
            request.setBizContent(refundDate.toJSONString());

//            AlipayRefundInfo alidata= new AlipayRefundInfo();
//            alidata.setOut_trade_no(out_trade_no);
//            alidata.setRefund_amount(refund_amount);
//            alidata.setTrade_no(trade_no);
            //request.setBizContent(JsonUtils.convertToString(alidata));
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            strResponse = response.getCode();
            if (response.isSuccess()) {
                strResponse = "退款成功";
            } else {
                strResponse = response.getSubMsg();
            }
            Logger.getLogger(getClass()).info("支付宝退款返回的字符串：" + response.getSubMsg());
        } catch (Exception e) {
            Logger.getLogger(getClass()).error("alipayRefundRequest", e);
        }
        return strResponse;

    }

    /**
     * @param out_trade_no  商户订单号
     * @param trade_no      退款单号
     * @param totralFee     总金额（分）
     * @param refund_amount 退款金额（分）
     * @param opUserId      操作员ID
     * @return String
     * @方法名称:weixinRefundRequest
     * @内容摘要: ＜微信退款请求＞
     */
    public String weixinRefundRequest(String out_trade_no, String trade_no, String totralFee, String refund_amount, String opUserId) {
        // 发送请求
        String strResponse = null;
        try {
            Map<String, String> retMap = WeiChartUtil.refundWei(out_trade_no, trade_no, totralFee, refund_amount, opUserId);
            strResponse = retMap.toString();
            String return_code = retMap.get("return_code");
            if ("success".equalsIgnoreCase(return_code)) {
                strResponse = "退款成功";
            } else {
                strResponse = "退款失败";
            }
            Logger.getLogger(getClass()).info("微信退款返回的字符串：" + strResponse);
        } catch (Exception e) {
            Logger.getLogger(getClass()).error("alipayRefundRequest", e);
        }
        return strResponse;

    }
}