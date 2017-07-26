package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 跑腿费表
 * 
 **/
@SuppressWarnings("serial")
public class ErrandsFee implements Serializable {

	/**唯一id**/
	private long id;

	/**开通城市id**/
	private long openCityId;

	/**起步公里数**/
	private int startMileage;

	/**起步费**/
	private BigDecimal startFee;

	/**超出里程数**/
	private int exceedMileage;

	/**超出费用**/
	private BigDecimal exceedFee;

	/**夜间加收费用的开始时刻**/
	private String nightStartTime;

	/**夜间加收费用的截止时刻**/
	private String nightEndTime;

	/**夜间每单加收费用**/
	private BigDecimal nightAddFee;

	/**排队起步时间**/
	private int queueStartTime;

	/**排队起步时间内费用**/
	private BigDecimal queueStartFee;

	/**排队超出起步时间外的时间**/
	private int queueExceedTime;

	/**排队超出起步时间外得费用(每多少时间多少钱）**/
	private BigDecimal queueExceedFee;

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


	public ErrandsFee() { super(); }

	public ErrandsFee(long id) {
	 super();
	 this.id=id;
	}

	public ErrandsFee(long id,long openCityId,int startMileage,BigDecimal startFee,int exceedMileage,BigDecimal exceedFee,String nightStartTime,String nightEndTime,BigDecimal nightAddFee,int queueStartTime,BigDecimal queueStartFee,int queueExceedTime,BigDecimal queueExceedFee,Date createDate,Date updateDate,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.openCityId = openCityId;
		this.startMileage = startMileage;
		this.startFee = startFee;
		this.exceedMileage = exceedMileage;
		this.exceedFee = exceedFee;
		this.nightStartTime = nightStartTime;
		this.nightEndTime = nightEndTime;
		this.nightAddFee = nightAddFee;
		this.queueStartTime = queueStartTime;
		this.queueStartFee = queueStartFee;
		this.queueExceedTime = queueExceedTime;
		this.queueExceedFee = queueExceedFee;
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

	public void setStartMileage(Integer startMileage){
		this.startMileage = startMileage;
	}

	public Integer getStartMileage(){
		return this.startMileage;
	}

	public void setStartFee(BigDecimal startFee){
		this.startFee = startFee;
	}

	public BigDecimal getStartFee(){
		return this.startFee;
	}

	public void setExceedMileage(Integer exceedMileage){
		this.exceedMileage = exceedMileage;
	}

	public Integer getExceedMileage(){
		return this.exceedMileage;
	}

	public void setExceedFee(BigDecimal exceedFee){
		this.exceedFee = exceedFee;
	}

	public BigDecimal getExceedFee(){
		return this.exceedFee;
	}

	public void setNightStartTime(String nightStartTime){
		this.nightStartTime = nightStartTime;
	}

	public String getNightStartTime(){
		return this.nightStartTime;
	}

	public void setNightEndTime(String nightEndTime){
		this.nightEndTime = nightEndTime;
	}

	public String getNightEndTime(){
		return this.nightEndTime;
	}

	public void setNightAddFee(BigDecimal nightAddFee){
		this.nightAddFee = nightAddFee;
	}

	public BigDecimal getNightAddFee(){
		return this.nightAddFee;
	}

	public void setQueueStartTime(Integer queueStartTime){
		this.queueStartTime = queueStartTime;
	}

	public Integer getQueueStartTime(){
		return this.queueStartTime;
	}

	public void setQueueStartFee(BigDecimal queueStartFee){
		this.queueStartFee = queueStartFee;
	}

	public BigDecimal getQueueStartFee(){
		return this.queueStartFee;
	}

	public void setQueueExceedTime(Integer queueExceedTime){
		this.queueExceedTime = queueExceedTime;
	}

	public Integer getQueueExceedTime(){
		return this.queueExceedTime;
	}

	public void setQueueExceedFee(BigDecimal queueExceedFee){
		this.queueExceedFee = queueExceedFee;
	}

	public BigDecimal getQueueExceedFee(){
		return this.queueExceedFee;
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
