package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 订单-帮送、帮取、帮买表
 * 
 **/
@SuppressWarnings("serial")
public class OrderGoods implements Serializable {

	/****/
	private long id;

	/**订单id**/
	private long orderId;

	/**定位地址-帮送：发货地；帮取：取货地；帮买：购买地**/
	private String locationAddressA;

	/**定位地址概述-帮送：发货地；帮取：取货地；帮买：购买地**/
	private String locationAddressNameA;

	/**详细地址，如楼层/门牌号-帮送：发货地；帮取：取货地；帮买：购买地**/
	private String detailAddressA;

	/**经度-帮送：发货地；帮取：取货地；帮买：购买地**/
	private Double longitudeA;

	/**纬度-帮送：发货地；帮取：取货地；帮买：购买地**/
	private Double latitudeA;

	/**手机号-帮送：发货人；帮取：取货地联系人**/
	private String mobilePhoneA;

	/**联系人姓名-帮送：发货人；帮取：取货地联系人**/
	private String linkmanNameA;

	/**定位地址-收货地**/
	private String locationAddressB;

	/**定位地址概述-收货地**/
	private String locationAddressNameB;

	/**详细地址，如楼层/门牌号-收货地**/
	private String detailAddressB;

	/**经度-收货地**/
	private Double longitudeB;

	/**纬度-收货地**/
	private Double latitudeB;

	/**收货人联系电话**/
	private String mobilePhoneB;

	/**收货人姓名（可不填）**/
	private String linkmanNameB;

	/**货物类型（帮送、帮取）**/
	private int goodsTypeId;

	/**货物类型名称（帮送、帮取）**/
	private String goodsTypeName;

	/**购买要求（帮买）**/
	private String buyRequire;

	/**帮买联系电话（帮买）**/
	private String buyLinkPhone;

	/**是否知道价格；1、知道价格；2、不知道价格（帮买）**/
	private int isKonwPrice;

	/**知道价格时，输入的商品价格（帮买）**/
	private BigDecimal buyPrice;

	/**是否就近购买(1：收货地附近就近购买；2：指定购买地）**/
	private int isNearBuy;

	/**购买中支付的商品费（帮买）**/
	private BigDecimal addPayMoney;

	/**购买中支付商品费的时间（帮买）**/
	private Date addPayTime;

	/**第三方支付返回的支付订单号（帮买）**/
	private String addPayTransactionId;

	/**订单总里程(单位：公里）**/
	private BigDecimal totalDistance;

	/**订单总时间（从拿到货到签收时间,单位：分钟）**/
	private int totalTime;

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


	public OrderGoods() { super(); }

	public OrderGoods(long id) {
	 super();
	 this.id=id;
	}

	public OrderGoods(long id,long orderId,String locationAddressA,String locationAddressNameA,String detailAddressA,Double longitudeA,Double latitudeA,String mobilePhoneA,String linkmanNameA,String locationAddressB,String locationAddressNameB,String detailAddressB,Double longitudeB,Double latitudeB,String mobilePhoneB,String linkmanNameB,int goodsTypeId,String goodsTypeName,String buyRequire,String buyLinkPhone,int isKonwPrice,BigDecimal buyPrice,int isNearBuy,BigDecimal addPayMoney,Date addPayTime,String addPayTransactionId,BigDecimal totalDistance,int totalTime,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.orderId = orderId;
		this.locationAddressA = locationAddressA;
		this.locationAddressNameA = locationAddressNameA;
		this.detailAddressA = detailAddressA;
		this.longitudeA = longitudeA;
		this.latitudeA = latitudeA;
		this.mobilePhoneA = mobilePhoneA;
		this.linkmanNameA = linkmanNameA;
		this.locationAddressB = locationAddressB;
		this.locationAddressNameB = locationAddressNameB;
		this.detailAddressB = detailAddressB;
		this.longitudeB = longitudeB;
		this.latitudeB = latitudeB;
		this.mobilePhoneB = mobilePhoneB;
		this.linkmanNameB = linkmanNameB;
		this.goodsTypeId = goodsTypeId;
		this.goodsTypeName = goodsTypeName;
		this.buyRequire = buyRequire;
		this.buyLinkPhone = buyLinkPhone;
		this.isKonwPrice = isKonwPrice;
		this.buyPrice = buyPrice;
		this.isNearBuy = isNearBuy;
		this.addPayMoney = addPayMoney;
		this.addPayTime = addPayTime;
		this.addPayTransactionId = addPayTransactionId;
		this.totalDistance = totalDistance;
		this.totalTime = totalTime;
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

	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}

	public Long getOrderId(){
		return this.orderId;
	}

	public void setLocationAddressA(String locationAddressA){
		this.locationAddressA = locationAddressA;
	}

	public String getLocationAddressA(){
		return this.locationAddressA;
	}

	public void setLocationAddressNameA(String locationAddressNameA){
		this.locationAddressNameA = locationAddressNameA;
	}

	public String getLocationAddressNameA(){
		return this.locationAddressNameA;
	}

	public void setDetailAddressA(String detailAddressA){
		this.detailAddressA = detailAddressA;
	}

	public String getDetailAddressA(){
		return this.detailAddressA;
	}

	public void setLongitudeA(Double longitudeA){
		this.longitudeA = longitudeA;
	}

	public Double getLongitudeA(){
		return this.longitudeA;
	}

	public void setLatitudeA(Double latitudeA){
		this.latitudeA = latitudeA;
	}

	public Double getLatitudeA(){
		return this.latitudeA;
	}

	public void setMobilePhoneA(String mobilePhoneA){
		this.mobilePhoneA = mobilePhoneA;
	}

	public String getMobilePhoneA(){
		return this.mobilePhoneA;
	}

	public void setLinkmanNameA(String linkmanNameA){
		this.linkmanNameA = linkmanNameA;
	}

	public String getLinkmanNameA(){
		return this.linkmanNameA;
	}

	public void setLocationAddressB(String locationAddressB){
		this.locationAddressB = locationAddressB;
	}

	public String getLocationAddressB(){
		return this.locationAddressB;
	}

	public void setLocationAddressNameB(String locationAddressNameB){
		this.locationAddressNameB = locationAddressNameB;
	}

	public String getLocationAddressNameB(){
		return this.locationAddressNameB;
	}

	public void setDetailAddressB(String detailAddressB){
		this.detailAddressB = detailAddressB;
	}

	public String getDetailAddressB(){
		return this.detailAddressB;
	}

	public void setLongitudeB(Double longitudeB){
		this.longitudeB = longitudeB;
	}

	public Double getLongitudeB(){
		return this.longitudeB;
	}

	public void setLatitudeB(Double latitudeB){
		this.latitudeB = latitudeB;
	}

	public Double getLatitudeB(){
		return this.latitudeB;
	}

	public void setMobilePhoneB(String mobilePhoneB){
		this.mobilePhoneB = mobilePhoneB;
	}

	public String getMobilePhoneB(){
		return this.mobilePhoneB;
	}

	public void setLinkmanNameB(String linkmanNameB){
		this.linkmanNameB = linkmanNameB;
	}

	public String getLinkmanNameB(){
		return this.linkmanNameB;
	}

	public void setGoodsTypeId(Integer goodsTypeId){
		this.goodsTypeId = goodsTypeId;
	}

	public Integer getGoodsTypeId(){
		return this.goodsTypeId;
	}

	public void setGoodsTypeName(String goodsTypeName){
		this.goodsTypeName = goodsTypeName;
	}

	public String getGoodsTypeName(){
		return this.goodsTypeName;
	}

	public void setBuyRequire(String buyRequire){
		this.buyRequire = buyRequire;
	}

	public String getBuyRequire(){
		return this.buyRequire;
	}

	public void setBuyLinkPhone(String buyLinkPhone){
		this.buyLinkPhone = buyLinkPhone;
	}

	public String getBuyLinkPhone(){
		return this.buyLinkPhone;
	}

	public void setIsKonwPrice(Integer isKonwPrice){
		this.isKonwPrice = isKonwPrice;
	}

	public Integer getIsKonwPrice(){
		return this.isKonwPrice;
	}

	public void setBuyPrice(java.math.BigDecimal buyPrice){
		this.buyPrice = buyPrice;
	}

	public java.math.BigDecimal getBuyPrice(){
		return this.buyPrice;
	}

	public void setIsNearBuy(Integer isNearBuy){
		this.isNearBuy = isNearBuy;
	}

	public Integer getIsNearBuy(){
		return this.isNearBuy;
	}

	public void setAddPayMoney(java.math.BigDecimal addPayMoney){
		this.addPayMoney = addPayMoney;
	}

	public java.math.BigDecimal getAddPayMoney(){
		return this.addPayMoney;
	}

	public void setAddPayTime(java.util.Date addPayTime){
		this.addPayTime = addPayTime;
	}

	public java.util.Date getAddPayTime(){
		return this.addPayTime;
	}

	public void setAddPayTransactionId(String addPayTransactionId){
		this.addPayTransactionId = addPayTransactionId;
	}

	public String getAddPayTransactionId(){
		return this.addPayTransactionId;
	}

	public void setTotalDistance(java.math.BigDecimal totalDistance){
		this.totalDistance = totalDistance;
	}

	public java.math.BigDecimal getTotalDistance(){
		return this.totalDistance;
	}

	public void setTotalTime(Integer totalTime){
		this.totalTime = totalTime;
	}

	public Integer getTotalTime(){
		return this.totalTime;
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
