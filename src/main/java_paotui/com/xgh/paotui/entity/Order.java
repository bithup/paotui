package com.xgh.paotui.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 订单表
 * 
 **/
@SuppressWarnings("serial")
public class Order implements Serializable {

	/****/
	private long id;

	/**订单类型：1、帮送；2、帮取；3、帮买；4：代排队**/
	private int orderType;

	/**订单编号**/
	private String orderNo;

	/**下单人id**/
	private long customerId;

	/**下单人名字**/
	private String customerName;

	/**下单人电话**/
	private String customerPhone;

	/**接单人id**/
	private long deliveryManId;

	/**接单人名称**/
	private String deliveryManName;

	/**接单人电话**/
	private String deliveryManPhone;

	/**接单人头像路径**/
	private String deliveryManHeadImage;

	/**抢单时间**/
	private Date deliveryOrderTime;

	/**下单工具：1、微信；2、android；3、ios**/
	private int shippingFrom;

	/**订单经度；帮买：发货地；帮取：取货地；帮买：买货地；代排队：排队地**/
	private Double longitude;

	/**订单纬度；帮买：发货地；帮取：取货地；帮买：买货地；代排队：排队地**/
	private Double latitude;

	/**帮送、帮取：备注留言；**/
	private String remark;

	/**下单城市id**/
	private long orderCity;

	/**下单城市名称**/
	private String orderCityName;

	/**订单预约类型：1、立即订单；2：预约订单**/
	private int bookingType;

	/**预约时间**/
	private Date bookingTime;

	/**原始跑腿费**/
	private BigDecimal originalFreight;

	/**实际跑腿费**/
	private BigDecimal actualFreight;

	/**是否使用优惠券：1、使用；0：未使用**/
	private int isUseCoupons;

	/**使用的优惠券id**/
	private long useCouponsId;

	/**优惠券优惠金额**/
	private BigDecimal couponsMoney;

	/**小费，打赏费**/
	private BigDecimal tips;

	/**小费第三方支付返回的支付订单号**/
	private String tipsTransactionId;

	/**下单时间**/
	private Date createTime;

	/**特殊要求（帮送、帮买、帮取使用）**/
	private String specialRequire;

	/**跑腿费支付方式，1：余额支付；2：微信支付；3、支付宝支付**/
	private int payType;

	/**跑腿费第三方支付返回的支付订单号**/
	private String payTransactionId;

	/**收货验证码（帮送、帮取、帮买）;验证码（代排队）**/
	private String smsCode;

	/**签收时间**/
	private Date signTime;

	/**订单中跑客当前操作步骤**/
	private int actionStep;

	/**订单取消原因**/
	private String orderCancelReason;

	/**订单状态；1：等待支付；2：等待接单；3、等待取货；4、进行中；5、待收货；6、已完成；9：已取消**/
	private int orderStatus;

	/**评价状态；1、已评价；2、未评价；3：订单未到评价状态**/
	private int evaluationStatus;

	/**状态（1：正常 2：已删除）**/
	private int status;

	/**扩展1**/
	private long data1;

	/**扩展2**/
	private int data2;

	/**扩展3**/
	private String data3;

	/**扩展4**/
	private String data4;

	/**扩展5**/
	private String data5;


	public Order() { super(); }

	public Order(long id) {
	 super();
	 this.id=id;
	}

	public Order(long id,int orderType,String orderNo,long customerId,String customerName,String customerPhone,long deliveryManId,String deliveryManName,String deliveryManPhone,String deliveryManHeadImage,Date deliveryOrderTime,int shippingFrom,Double longitude,Double latitude,String remark,long orderCity,String orderCityName,int bookingType,Date bookingTime,BigDecimal originalFreight,BigDecimal actualFreight,int isUseCoupons,long useCouponsId,BigDecimal couponsMoney,BigDecimal tips,String tipsTransactionId,Date createTime,String specialRequire,int payType,String payTransactionId,String smsCode,Date signTime,int actionStep,String orderCancelReason,int orderStatus,int evaluationStatus,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.orderType = orderType;
		this.orderNo = orderNo;
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPhone = customerPhone;
		this.deliveryManId = deliveryManId;
		this.deliveryManName = deliveryManName;
		this.deliveryManPhone = deliveryManPhone;
		this.deliveryManHeadImage = deliveryManHeadImage;
		this.deliveryOrderTime = deliveryOrderTime;
		this.shippingFrom = shippingFrom;
		this.longitude = longitude;
		this.latitude = latitude;
		this.remark = remark;
		this.orderCity = orderCity;
		this.orderCityName = orderCityName;
		this.bookingType = bookingType;
		this.bookingTime = bookingTime;
		this.originalFreight = originalFreight;
		this.actualFreight = actualFreight;
		this.isUseCoupons = isUseCoupons;
		this.useCouponsId = useCouponsId;
		this.couponsMoney = couponsMoney;
		this.tips = tips;
		this.tipsTransactionId = tipsTransactionId;
		this.createTime = createTime;
		this.specialRequire = specialRequire;
		this.payType = payType;
		this.payTransactionId = payTransactionId;
		this.smsCode = smsCode;
		this.signTime = signTime;
		this.actionStep = actionStep;
		this.orderCancelReason = orderCancelReason;
		this.orderStatus = orderStatus;
		this.evaluationStatus = evaluationStatus;
		this.status = status;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
		this.data5 = data5;

	}
	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setOrderType(Integer orderType){
		this.orderType = orderType;
	}

	public Integer getOrderType(){
		return this.orderType;
	}

	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	public String getOrderNo(){
		return this.orderNo;
	}

	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}

	public Long getCustomerId(){
		return this.customerId;
	}

	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}

	public String getCustomerName(){
		return this.customerName;
	}

	public void setCustomerPhone(String customerPhone){
		this.customerPhone = customerPhone;
	}

	public String getCustomerPhone(){
		return this.customerPhone;
	}

	public void setDeliveryManId(Long deliveryManId){
		this.deliveryManId = deliveryManId;
	}

	public Long getDeliveryManId(){
		return this.deliveryManId;
	}

	public void setDeliveryManName(String deliveryManName){
		this.deliveryManName = deliveryManName;
	}

	public String getDeliveryManName(){
		return this.deliveryManName;
	}

	public void setDeliveryManPhone(String deliveryManPhone){
		this.deliveryManPhone = deliveryManPhone;
	}

	public String getDeliveryManPhone(){
		return this.deliveryManPhone;
	}

	public void setDeliveryManHeadImage(String deliveryManHeadImage){
		this.deliveryManHeadImage = deliveryManHeadImage;
	}

	public String getDeliveryManHeadImage(){
		return this.deliveryManHeadImage;
	}

	public void setDeliveryOrderTime(java.util.Date deliveryOrderTime){
		this.deliveryOrderTime = deliveryOrderTime;
	}

	public java.util.Date getDeliveryOrderTime(){
		return this.deliveryOrderTime;
	}

	public void setShippingFrom(Integer shippingFrom){
		this.shippingFrom = shippingFrom;
	}

	public Integer getShippingFrom(){
		return this.shippingFrom;
	}

	public void setLongitude(Double longitude){
		this.longitude = longitude;
	}

	public Double getLongitude(){
		return this.longitude;
	}

	public void setLatitude(Double latitude){
		this.latitude = latitude;
	}

	public Double getLatitude(){
		return this.latitude;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setOrderCity(Long orderCity){
		this.orderCity = orderCity;
	}

	public Long getOrderCity(){
		return this.orderCity;
	}

	public void setOrderCityName(String orderCityName){
		this.orderCityName = orderCityName;
	}

	public String getOrderCityName(){
		return this.orderCityName;
	}

	public void setBookingType(Integer bookingType){
		this.bookingType = bookingType;
	}

	public Integer getBookingType(){
		return this.bookingType;
	}

	public void setBookingTime(Date bookingTime){
		this.bookingTime = bookingTime;
	}

	public Date getBookingTime(){
		return this.bookingTime;
	}

	public void setOriginalFreight(java.math.BigDecimal originalFreight){
		this.originalFreight = originalFreight;
	}

	public java.math.BigDecimal getOriginalFreight(){
		return this.originalFreight;
	}

	public void setActualFreight(java.math.BigDecimal actualFreight){
		this.actualFreight = actualFreight;
	}

	public java.math.BigDecimal getActualFreight(){
		return this.actualFreight;
	}

	public void setIsUseCoupons(Integer isUseCoupons){
		this.isUseCoupons = isUseCoupons;
	}

	public Integer getIsUseCoupons(){
		return this.isUseCoupons;
	}

	public void setUseCouponsId(Long useCouponsId){
		this.useCouponsId = useCouponsId;
	}

	public Long getUseCouponsId(){
		return this.useCouponsId;
	}

	public void setCouponsMoney(java.math.BigDecimal couponsMoney){
		this.couponsMoney = couponsMoney;
	}

	public java.math.BigDecimal getCouponsMoney(){
		return this.couponsMoney;
	}

	public void setTips(java.math.BigDecimal tips){
		this.tips = tips;
	}

	public java.math.BigDecimal getTips(){
		return this.tips;
	}

	public void setTipsTransactionId(String tipsTransactionId){
		this.tipsTransactionId = tipsTransactionId;
	}

	public String getTipsTransactionId(){
		return this.tipsTransactionId;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setSpecialRequire(String specialRequire){
		this.specialRequire = specialRequire;
	}

	public String getSpecialRequire(){
		return this.specialRequire;
	}

	public void setPayType(Integer payType){
		this.payType = payType;
	}

	public Integer getPayType(){
		return this.payType;
	}

	public void setPayTransactionId(String payTransactionId){
		this.payTransactionId = payTransactionId;
	}

	public String getPayTransactionId(){
		return this.payTransactionId;
	}

	public void setSmsCode(String smsCode){
		this.smsCode = smsCode;
	}

	public String getSmsCode(){
		return this.smsCode;
	}

	public void setSignTime(java.util.Date signTime){
		this.signTime = signTime;
	}

	public java.util.Date getSignTime(){
		return this.signTime;
	}

	public void setActionStep(Integer actionStep){
		this.actionStep = actionStep;
	}

	public Integer getActionStep(){
		return this.actionStep;
	}

	public void setOrderCancelReason(String orderCancelReason){
		this.orderCancelReason = orderCancelReason;
	}

	public String getOrderCancelReason(){
		return this.orderCancelReason;
	}

	public void setOrderStatus(Integer orderStatus){
		this.orderStatus = orderStatus;
	}

	public Integer getOrderStatus(){
		return this.orderStatus;
	}

	public void setEvaluationStatus(Integer evaluationStatus){
		this.evaluationStatus = evaluationStatus;
	}

	public Integer getEvaluationStatus(){
		return this.evaluationStatus;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setData1(Long data1){
		this.data1 = data1;
	}

	public Long getData1(){
		return this.data1;
	}

	public void setData2(Integer data2){
		this.data2 = data2;
	}

	public Integer getData2(){
		return this.data2;
	}

	public void setData3(String data3){
		this.data3 = data3;
	}

	public String getData3(){
		return this.data3;
	}

	public void setData4(String data4){
		this.data4 = data4;
	}

	public String getData4(){
		return this.data4;
	}

	public void setData5(String data5){
		this.data5 = data5;
	}

	public String getData5(){
		return this.data5;
	}

}
