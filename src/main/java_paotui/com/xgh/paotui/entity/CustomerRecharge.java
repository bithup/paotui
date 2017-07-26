package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 客户充值表
 * 
 **/
@SuppressWarnings("serial")
public class CustomerRecharge implements Serializable {

	/**唯一id**/
	private long id;

	/**客户id**/
	private long customerId;

	/**充值金额**/
	private BigDecimal rechargeMoney;

	/**赠送金额**/
	private BigDecimal giveMoney;

	/**充值前余额**/
	private BigDecimal rechargeBeforeMoney;

	/**充值后余额**/
	private BigDecimal rechargeAfterMoney;

	/**充值来源（1：支付宝 2：微信 3：网银）**/
	private int rechargeFrom;

	/**第三方支付返回的支付订单号**/
	private String rechargeTransactionId;

	/**充值时间**/
	private Date rechargeDate;

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


	public CustomerRecharge() { super(); }

	public CustomerRecharge(long id) {
	 super();
	 this.id=id;
	}

	public CustomerRecharge(long id,long customerId,BigDecimal rechargeMoney,BigDecimal giveMoney,BigDecimal rechargeBeforeMoney,BigDecimal rechargeAfterMoney,int rechargeFrom,String rechargeTransactionId,Date rechargeDate,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.customerId = customerId;
		this.rechargeMoney = rechargeMoney;
		this.giveMoney = giveMoney;
		this.rechargeBeforeMoney = rechargeBeforeMoney;
		this.rechargeAfterMoney = rechargeAfterMoney;
		this.rechargeFrom = rechargeFrom;
		this.rechargeTransactionId = rechargeTransactionId;
		this.rechargeDate = rechargeDate;
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

	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}

	public Long getCustomerId(){
		return this.customerId;
	}

	public void setRechargeMoney(BigDecimal rechargeMoney){
		this.rechargeMoney = rechargeMoney;
	}

	public BigDecimal getRechargeMoney(){
		return this.rechargeMoney;
	}

	public void setGiveMoney(BigDecimal giveMoney){
		this.giveMoney = giveMoney;
	}

	public BigDecimal getGiveMoney(){
		return this.giveMoney;
	}

	public void setRechargeBeforeMoney(BigDecimal rechargeBeforeMoney){
		this.rechargeBeforeMoney = rechargeBeforeMoney;
	}

	public BigDecimal getRechargeBeforeMoney(){
		return this.rechargeBeforeMoney;
	}

	public void setRechargeAfterMoney(BigDecimal rechargeAfterMoney){
		this.rechargeAfterMoney = rechargeAfterMoney;
	}

	public BigDecimal getRechargeAfterMoney(){
		return this.rechargeAfterMoney;
	}

	public void setRechargeFrom(Integer rechargeFrom){
		this.rechargeFrom = rechargeFrom;
	}

	public Integer getRechargeFrom(){
		return this.rechargeFrom;
	}

	public void setRechargeTransactionId(String rechargeTransactionId){
		this.rechargeTransactionId = rechargeTransactionId;
	}

	public String getRechargeTransactionId(){
		return this.rechargeTransactionId;
	}

	public void setRechargeDate(Date rechargeDate){
		this.rechargeDate = rechargeDate;
	}

	public Date getRechargeDate(){
		return this.rechargeDate;
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
