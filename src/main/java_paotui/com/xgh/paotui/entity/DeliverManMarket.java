package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 跑客推广表
 * 
 **/
@SuppressWarnings("serial")
public class DeliverManMarket implements Serializable {

	/****/
	private long  id;

	/**跑客id**/
	private long deliveryManId;

	/**客户id**/
	private long customerId;

	/**推广时间**/
	private Date marketTime;

	private String openId;

	/**状态（1：正常 2：已删除）**/
	private int status;

	/****/
	private String data1;

	/****/
	private String data2;


	public DeliverManMarket() { super(); }

	public DeliverManMarket(long id) {
	 super();
	 this.id=id;
	}

	public DeliverManMarket(long  id,long deliveryManId,long customerId,Date marketTime,String openId,int status,String data1,String data2){
		super();
		this. id =  id;
		this.deliveryManId = deliveryManId;
		this.customerId = customerId;
		this.marketTime = marketTime;
		this.openId = openId;
		this.status = status;
		this.data1 = data1;
		this.data2 = data2;

	}
	public void setId(Long  id){
		this. id =  id;
	}

	public Long getId(){
		return this. id;
	}

	public void setDeliveryManId(Long deliveryManId){
		this.deliveryManId = deliveryManId;
	}

	public Long getDeliveryManId(){
		return this.deliveryManId;
	}

	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}

	public Long getCustomerId(){
		return this.customerId;
	}

	public void setMarketTime(java.util.Date marketTime){
		this.marketTime = marketTime;
	}

	public java.util.Date getMarketTime(){
		return this.marketTime;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setData1(String data1){
		this.data1 = data1;
	}

	public String getData1(){
		return this.data1;
	}

	public void setData2(String data2){
		this.data2 = data2;
	}

	public String getData2(){
		return this.data2;
	}

}
