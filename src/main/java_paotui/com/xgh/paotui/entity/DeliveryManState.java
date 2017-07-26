package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 跑客接单状态表
 * 
 **/
@SuppressWarnings("serial")
public class DeliveryManState implements Serializable {

	/****/
	private long id;

	/**跑客id**/
	private long deliverManId;

	/**所属城市id**/
	private long belongCityId;

	/**当前经度**/
	private Double nowLongitude;

	/**当前纬度**/
	private Double nowLatitude;

	/**当前位置**/
	private String nowAddress;

	/**工作状态，1：上班，2：下班**/
	private int workStatus;

	/**是否允许接新订单。1：可以接单；2：有任务不能接单**/
	private int gainNewOrder;

	/**状态更新时间**/
	private Date stateUpdateTime;

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


	public DeliveryManState() { super(); }

	public DeliveryManState(long id) {
	 super();
	 this.id=id;
	}

	public DeliveryManState(long id,long deliverManId,long belongCityId,Double nowLongitude,Double nowLatitude,String nowAddress,int workStatus,int gainNewOrder,Date stateUpdateTime,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.deliverManId = deliverManId;
		this.belongCityId = belongCityId;
		this.nowLongitude = nowLongitude;
		this.nowLatitude = nowLatitude;
		this.nowAddress = nowAddress;
		this.workStatus = workStatus;
		this.gainNewOrder = gainNewOrder;
		this.stateUpdateTime = stateUpdateTime;
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

	public void setDeliverManId(Long deliverManId){
		this.deliverManId = deliverManId;
	}

	public Long getDeliverManId(){
		return this.deliverManId;
	}

	public void setBelongCityId(Long belongCityId){
		this.belongCityId = belongCityId;
	}

	public Long getBelongCityId(){
		return this.belongCityId;
	}

	public void setNowLongitude(Double nowLongitude){
		this.nowLongitude = nowLongitude;
	}

	public Double getNowLongitude(){
		return this.nowLongitude;
	}

	public void setNowLatitude(Double nowLatitude){
		this.nowLatitude = nowLatitude;
	}

	public Double getNowLatitude(){
		return this.nowLatitude;
	}

	public void setNowAddress(String nowAddress){
		this.nowAddress = nowAddress;
	}

	public String getNowAddress(){
		return this.nowAddress;
	}

	public void setWorkStatus(Integer workStatus){
		this.workStatus = workStatus;
	}

	public Integer getWorkStatus(){
		return this.workStatus;
	}

	public void setGainNewOrder(Integer gainNewOrder){
		this.gainNewOrder = gainNewOrder;
	}

	public Integer getGainNewOrder(){
		return this.gainNewOrder;
	}

	public void setStateUpdateTime(java.util.Date stateUpdateTime){
		this.stateUpdateTime = stateUpdateTime;
	}

	public java.util.Date getStateUpdateTime(){
		return this.stateUpdateTime;
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
