package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 平台佣金分配表
 * 
 **/
@SuppressWarnings("serial")
public class PlatformCommission implements Serializable {

	/**唯一id**/
	private long id;

	/**开通城市id**/
	private long openCityId;

	/**佣金分配方式（0：按比例 1：按单量）**/
	private int allocationType;

	/**按比例：跑客分配比例**/
	private String runProportion;

	/**按比例：众包分配比例**/
	private String crowdsourcingProportion;

	/**按单量：跑客每单费用**/
	private BigDecimal runOrderFee;

	/**按单量：众包每单费用**/
	private BigDecimal crowdsourcingOrderFee;

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


	public PlatformCommission() { super(); }

	public PlatformCommission(long id) {
	 super();
	 this.id=id;
	}

	public PlatformCommission(long id,long openCityId,int allocationType,String runProportion,String crowdsourcingProportion,BigDecimal runOrderFee,BigDecimal crowdsourcingOrderFee,Date createDate,Date updateDate,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.openCityId = openCityId;
		this.allocationType = allocationType;
		this.runProportion = runProportion;
		this.crowdsourcingProportion = crowdsourcingProportion;
		this.runOrderFee = runOrderFee;
		this.crowdsourcingOrderFee = crowdsourcingOrderFee;
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

	public void setAllocationType(Integer allocationType){
		this.allocationType = allocationType;
	}

	public Integer getAllocationType(){
		return this.allocationType;
	}

	public void setRunProportion(String runProportion){
		this.runProportion = runProportion;
	}

	public String getRunProportion(){
		return this.runProportion;
	}

	public void setCrowdsourcingProportion(String crowdsourcingProportion){
		this.crowdsourcingProportion = crowdsourcingProportion;
	}

	public String getCrowdsourcingProportion(){
		return this.crowdsourcingProportion;
	}

	public void setRunOrderFee(BigDecimal runOrderFee){
		this.runOrderFee = runOrderFee;
	}

	public BigDecimal getRunOrderFee(){
		return this.runOrderFee;
	}

	public void setCrowdsourcingOrderFee(BigDecimal crowdsourcingOrderFee){
		this.crowdsourcingOrderFee = crowdsourcingOrderFee;
	}

	public BigDecimal getCrowdsourcingOrderFee(){
		return this.crowdsourcingOrderFee;
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
