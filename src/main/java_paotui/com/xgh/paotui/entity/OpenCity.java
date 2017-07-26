package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 *
 * 开通城市表
 *
 **/
@SuppressWarnings("serial")
public class OpenCity implements Serializable {

	/**唯一id**/
	private long id;

	/**城市名称**/
	private String cityName;

	/**经营类别（1：自营； 2：加盟商）**/
	private int managementType;

	/**负责人姓名**/
	private String responsibleName;

	/**负责人电话**/
	private String responsiblePhone;

	/**办公地址**/
	private String officeAddress;

	/**订单前缀**/
	private String orderPrefix;

	/**订单流水号所在的日期**/
	private Date orderSerialDate;

	/**当天订单流水号**/
	private int orderSerialNo;

	/**创建时间**/
	private Date createDate;

	/**修改时间**/
	private Date updateDate;

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


	public OpenCity() { super(); }

	public OpenCity(long id) {
		super();
		this.id=id;
	}

	public OpenCity(long id,String cityName,int managementType,String responsibleName,String responsiblePhone,String officeAddress,String orderPrefix,Date orderSerialDate,int orderSerialNo,Date createDate,Date updateDate,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.cityName = cityName;
		this.managementType = managementType;
		this.responsibleName = responsibleName;
		this.responsiblePhone = responsiblePhone;
		this.officeAddress = officeAddress;
		this.orderPrefix = orderPrefix;
		this.orderSerialDate = orderSerialDate;
		this.orderSerialNo = orderSerialNo;
		this.createDate = createDate;
		this.updateDate = updateDate;
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

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return this.cityName;
	}

	public void setManagementType(Integer managementType){
		this.managementType = managementType;
	}

	public Integer getManagementType(){
		return this.managementType;
	}

	public void setResponsibleName(String responsibleName){
		this.responsibleName = responsibleName;
	}

	public String getResponsibleName(){
		return this.responsibleName;
	}

	public void setResponsiblePhone(String responsiblePhone){
		this.responsiblePhone = responsiblePhone;
	}

	public String getResponsiblePhone(){
		return this.responsiblePhone;
	}

	public void setOfficeAddress(String officeAddress){
		this.officeAddress = officeAddress;
	}

	public String getOfficeAddress(){
		return this.officeAddress;
	}

	public void setOrderPrefix(String orderPrefix){
		this.orderPrefix = orderPrefix;
	}

	public String getOrderPrefix(){
		return this.orderPrefix;
	}

	public void setOrderSerialDate(java.util.Date orderSerialDate){
		this.orderSerialDate = orderSerialDate;
	}

	public java.util.Date getOrderSerialDate(){
		return this.orderSerialDate;
	}

	public void setOrderSerialNo(Integer orderSerialNo){
		this.orderSerialNo = orderSerialNo;
	}

	public Integer getOrderSerialNo(){
		return this.orderSerialNo;
	}

	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}

	public java.util.Date getUpdateDate(){
		return this.updateDate;
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
