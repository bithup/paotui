package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 跑客抢单数设置表
 * 
 **/
@SuppressWarnings("serial")
public class OrderTaskSetting implements Serializable {

	/**唯一id**/
	private long id;

	/**开通城市id**/
	private long openCityId;

	/**等级**/
	private int level;

	/**公司员工抢单数**/
	private int staffOrderNum;

	/**众包抢单数**/
	private int crowdsourcingOrderNum;

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


	public OrderTaskSetting() { super(); }

	public OrderTaskSetting(long id) {
	 super();
	 this.id=id;
	}

	public OrderTaskSetting(long id,long openCityId,int level,int staffOrderNum,int crowdsourcingOrderNum,Date createDate,Date updateDate,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.openCityId = openCityId;
		this.level = level;
		this.staffOrderNum = staffOrderNum;
		this.crowdsourcingOrderNum = crowdsourcingOrderNum;
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

	public void setOpenCityId(Long openCityId){
		this.openCityId = openCityId;
	}

	public Long getOpenCityId(){
		return this.openCityId;
	}

	public void setLevel(Integer level){
		this.level = level;
	}

	public Integer getLevel(){
		return this.level;
	}

	public void setStaffOrderNum(Integer staffOrderNum){
		this.staffOrderNum = staffOrderNum;
	}

	public Integer getStaffOrderNum(){
		return this.staffOrderNum;
	}

	public void setCrowdsourcingOrderNum(Integer crowdsourcingOrderNum){
		this.crowdsourcingOrderNum = crowdsourcingOrderNum;
	}

	public Integer getCrowdsourcingOrderNum(){
		return this.crowdsourcingOrderNum;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
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
