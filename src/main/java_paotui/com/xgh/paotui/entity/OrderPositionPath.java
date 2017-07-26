package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 订单轨迹表
 * 
 **/
@SuppressWarnings("serial")
public class OrderPositionPath implements Serializable {

	/**唯一id**/
	private long id;

	/**订单id**/
	private long orderId;

	/**经度**/
	private Double longitude;

	/**纬度**/
	private Double latitude;

	/**记录时间**/
	private Date createDate;

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


	public OrderPositionPath() { super(); }

	public OrderPositionPath(long id) {
	 super();
	 this.id=id;
	}

	public OrderPositionPath(long id,long orderId,Double longitude,Double latitude,Date createDate,int status,long data1,int data2,String data3,String data4){
		super();
		this.id = id;
		this.orderId = orderId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.createDate = createDate;
		this.status = status;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;

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

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
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

}
