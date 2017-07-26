package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 排队订单表
 * 
 **/
@SuppressWarnings("serial")
public class OrderLineup implements Serializable {

	/****/
	private long id;

	/**订单id**/
	private long orderId;

	/**排队地点定位地址**/
	private String locationAddress;

	/**排队地点定位地址概述**/
	private String locationAddressName;

	/**排队地点详细地址，如楼层/门牌号**/
	private String detailAddress;

	/**经度**/
	private Double longitude;

	/**纬度**/
	private Double latitude;

	/**排队要求**/
	private String lineupRequire;

	/**排队时长，单位：分钟**/
	private int lineupDuration;

	/**联系电话**/
	private String lineupPhone;

	/**排队加时支付的金额**/
	private BigDecimal addPayMoney;

	/**排队加时时长，单位:分钟**/
	private int addPayDuration;

	/**排队加时支付时间**/
	private Date addPayTime;

	/**第三方支付返回的支付订单号**/
	private String addPayTransactionId;

	/**排队开始时间**/
	private Date lineupBeginTime;

	/**排队结束时间**/
	private Date lineupEndTime;

	/**排队实际时长**/
	private int lineupRealDuration;

	/**排队实际费用**/
	private BigDecimal lineupRealMoney;

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


	public OrderLineup() { super(); }

	public OrderLineup(long id) {
	 super();
	 this.id=id;
	}

	public OrderLineup(long id,long orderId,String locationAddress,String locationAddressName,String detailAddress,Double longitude,Double latitude,String lineupRequire,int lineupDuration,String lineupPhone,BigDecimal addPayMoney,int addPayDuration,Date addPayTime,String addPayTransactionId,Date lineupBeginTime,Date lineupEndTime,int lineupRealDuration,BigDecimal lineupRealMoney,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.orderId = orderId;
		this.locationAddress = locationAddress;
		this.locationAddressName = locationAddressName;
		this.detailAddress = detailAddress;
		this.longitude = longitude;
		this.latitude = latitude;
		this.lineupRequire = lineupRequire;
		this.lineupDuration = lineupDuration;
		this.lineupPhone = lineupPhone;
		this.addPayMoney = addPayMoney;
		this.addPayDuration = addPayDuration;
		this.addPayTime = addPayTime;
		this.addPayTransactionId = addPayTransactionId;
		this.lineupBeginTime = lineupBeginTime;
		this.lineupEndTime = lineupEndTime;
		this.lineupRealDuration = lineupRealDuration;
		this.lineupRealMoney = lineupRealMoney;
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

	public void setLocationAddress(String locationAddress){
		this.locationAddress = locationAddress;
	}

	public String getLocationAddress(){
		return this.locationAddress;
	}

	public void setLocationAddressName(String locationAddressName){
		this.locationAddressName = locationAddressName;
	}

	public String getLocationAddressName(){
		return this.locationAddressName;
	}

	public void setDetailAddress(String detailAddress){
		this.detailAddress = detailAddress;
	}

	public String getDetailAddress(){
		return this.detailAddress;
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

	public void setLineupRequire(String lineupRequire){
		this.lineupRequire = lineupRequire;
	}

	public String getLineupRequire(){
		return this.lineupRequire;
	}

	public void setLineupDuration(Integer lineupDuration){
		this.lineupDuration = lineupDuration;
	}

	public Integer getLineupDuration(){
		return this.lineupDuration;
	}

	public void setLineupPhone(String lineupPhone){
		this.lineupPhone = lineupPhone;
	}

	public String getLineupPhone(){
		return this.lineupPhone;
	}

	public void setAddPayMoney(BigDecimal addPayMoney){
		this.addPayMoney = addPayMoney;
	}

	public BigDecimal getAddPayMoney(){
		return this.addPayMoney;
	}

	public void setAddPayDuration(Integer addPayDuration){
		this.addPayDuration = addPayDuration;
	}

	public Integer getAddPayDuration(){
		return this.addPayDuration;
	}

	public void setAddPayTime(Date addPayTime){
		this.addPayTime = addPayTime;
	}

	public Date getAddPayTime(){
		return this.addPayTime;
	}

	public void setAddPayTransactionId(String addPayTransactionId){
		this.addPayTransactionId = addPayTransactionId;
	}

	public String getAddPayTransactionId(){
		return this.addPayTransactionId;
	}

	public void setLineupBeginTime(Date lineupBeginTime){
		this.lineupBeginTime = lineupBeginTime;
	}

	public Date getLineupBeginTime(){
		return this.lineupBeginTime;
	}

	public void setLineupEndTime(Date lineupEndTime){
		this.lineupEndTime = lineupEndTime;
	}

	public Date getLineupEndTime(){
		return this.lineupEndTime;
	}

	public void setLineupRealDuration(Integer lineupRealDuration){
		this.lineupRealDuration = lineupRealDuration;
	}

	public Integer getLineupRealDuration(){
		return this.lineupRealDuration;
	}

	public void setLineupRealMoney(BigDecimal lineupRealMoney){
		this.lineupRealMoney = lineupRealMoney;
	}

	public BigDecimal getLineupRealMoney(){
		return this.lineupRealMoney;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
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
