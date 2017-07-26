package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 跑客提现表
 * 
 **/
@SuppressWarnings("serial")
public class DeliveryManWithdrawals implements Serializable {

	/**唯一id**/
	private long id;

	/**跑客id**/
	private long deliverManId;

	/**提现前账户余额**/
	private BigDecimal withdrawalsBeforeMoney;

	/**提现金额**/
	private BigDecimal withdrawalsMoney;

	/**提现后账户余额**/
	private BigDecimal withdrawalsAfterMoney;

	/**提现时间**/
	private Date withdrawalsDate;

	/**备注**/
	private String remark;

	/**创建时间**/
	private Date createDate;

	/**提现审核人id**/
	private long checkUserId;

	/**提现审核时间**/
	private Date checkDate;

	/**提现审核状态（1：已审核 2：未审核 3：审核未通过 ）**/
	private int checkState;

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


	public DeliveryManWithdrawals() { super(); }

	public DeliveryManWithdrawals(long id) {
	 super();
	 this.id=id;
	}

	public DeliveryManWithdrawals(long id,long deliverManId,BigDecimal withdrawalsBeforeMoney,BigDecimal withdrawalsMoney,BigDecimal withdrawalsAfterMoney,Date withdrawalsDate,String remark,Date createDate,long checkUserId,Date checkDate,int checkState,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.deliverManId = deliverManId;
		this.withdrawalsBeforeMoney = withdrawalsBeforeMoney;
		this.withdrawalsMoney = withdrawalsMoney;
		this.withdrawalsAfterMoney = withdrawalsAfterMoney;
		this.withdrawalsDate = withdrawalsDate;
		this.remark = remark;
		this.createDate = createDate;
		this.checkUserId = checkUserId;
		this.checkDate = checkDate;
		this.checkState = checkState;
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

	public void setWithdrawalsBeforeMoney(BigDecimal withdrawalsBeforeMoney){
		this.withdrawalsBeforeMoney = withdrawalsBeforeMoney;
	}

	public BigDecimal getWithdrawalsBeforeMoney(){
		return this.withdrawalsBeforeMoney;
	}

	public void setWithdrawalsMoney(BigDecimal withdrawalsMoney){
		this.withdrawalsMoney = withdrawalsMoney;
	}

	public BigDecimal getWithdrawalsMoney(){
		return this.withdrawalsMoney;
	}

	public void setWithdrawalsAfterMoney(BigDecimal withdrawalsAfterMoney){
		this.withdrawalsAfterMoney = withdrawalsAfterMoney;
	}

	public BigDecimal getWithdrawalsAfterMoney(){
		return this.withdrawalsAfterMoney;
	}

	public void setWithdrawalsDate(Date withdrawalsDate){
		this.withdrawalsDate = withdrawalsDate;
	}

	public Date getWithdrawalsDate(){
		return this.withdrawalsDate;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCheckUserId(Long checkUserId){
		this.checkUserId = checkUserId;
	}

	public Long getCheckUserId(){
		return this.checkUserId;
	}

	public void setCheckDate(Date checkDate){
		this.checkDate = checkDate;
	}

	public Date getCheckDate(){
		return this.checkDate;
	}

	public void setCheckState(Integer checkState){
		this.checkState = checkState;
	}

	public Integer getCheckState(){
		return this.checkState;
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
