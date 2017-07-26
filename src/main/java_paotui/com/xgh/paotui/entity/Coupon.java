package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 代金券表
 * 
 **/
@SuppressWarnings("serial")
public class Coupon implements Serializable {

	/**唯一id**/
	private long id;

	/**代金券使用客户id**/
	private long customerId;

	/**使用订单id**/
	private long orderId;

	/**代金券名称**/
	private String couponName;

	/**代金券金额(单位：元)**/
	private BigDecimal moneyAmount;

	/**抵扣金额(单位：元)**/
	private BigDecimal useAmount;

	/**有效期开始日期**/
	private Date startDate;

	/**有效期结束日期**/
	private Date endDate;

	/**代金券状态（1：未使用 2：已使用 3：已失效 ）**/
	private int useStatus;

	/**使用时间**/
	private Date updateDate;

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

	/**状态（1：正常 2：已删除）**/
	private int status;


	public Coupon() { super(); }

	public Coupon(long id) {
	 super();
	 this.id=id;
	}

	public Coupon(long id,long customerId,long orderId,String couponName,BigDecimal moneyAmount,BigDecimal useAmount,Date startDate,Date endDate,int useStatus,Date updateDate,long data1,int data2,String data3,String data4,String data5,int status){
		super();
		this.id = id;
		this.customerId = customerId;
		this.orderId = orderId;
		this.couponName = couponName;
		this.moneyAmount = moneyAmount;
		this.useAmount = useAmount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.useStatus = useStatus;
		this.updateDate = updateDate;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
		this.data5 = data5;
		this.status = status;

	}
	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}

	public Long getCustomerId(){
		return this.customerId;
	}

	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}

	public Long getOrderId(){
		return this.orderId;
	}

	public void setCouponName(String couponName){
		this.couponName = couponName;
	}

	public String getCouponName(){
		return this.couponName;
	}

	public void setMoneyAmount(BigDecimal moneyAmount){
		this.moneyAmount = moneyAmount;
	}

	public BigDecimal getMoneyAmount(){
		return this.moneyAmount;
	}

	public void setUseAmount(BigDecimal useAmount){
		this.useAmount = useAmount;
	}

	public java.math.BigDecimal getUseAmount(){
		return this.useAmount;
	}

	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}

	public Date getStartDate(){
		return this.startDate;
	}

	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}

	public Date getEndDate(){
		return this.endDate;
	}

	public void setUseStatus(Integer useStatus){
		this.useStatus = useStatus;
	}

	public Integer getUseStatus(){
		return this.useStatus;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
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

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

}
