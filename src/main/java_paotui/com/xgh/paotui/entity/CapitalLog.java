package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 资金日志表
 * 
 **/
@SuppressWarnings("serial")
public class CapitalLog implements Serializable {

	/**唯一id**/
	private long id;

	/**账户类别（1：客户 2：跑客 ）**/
	private int accountType;

	/**账户id**/
	private long accountId;

	/**登录名**/
	private String loginName;

	/**真实姓名**/
	private String realName;

	/**类别(1、帮送；2、帮取；3、帮买；4：代排队； 5:提现 6:管理员修改)**/
	private int type;

	/**订单id**/
	private long orderId;

	/**订单号**/
	private String orderNo;

	/**交易来源，1：余额；2：微信；3、支付宝**/
	private int tradeFrom;

	/**交易号**/
	private String tradeNo;

	/**提现id**/
	private long withdrawalsId;

	/**资金变动事由**/
	private String capitalChangeReason;

	/**变动金额**/
	private BigDecimal changeMoney;

	/**备注**/
	private String remark;

	/**创建时间**/
	private Date createDate;

	/**状态（1：正常 2：已删除）**/
	private int status;

	/**扩展1**/
	private long data1;

	/**微信或支付宝回调通知使用，1：已通知；0：未通知**/
	private int data2;

	/**扩展3**/
	private String data3;

	/**扩展4**/
	private String data4;

	/**扩展5**/
	private String data5;


	public CapitalLog() { super(); }

	public CapitalLog(long id) {
	 super();
	 this.id=id;
	}

	public CapitalLog(long id,int accountType,long accountId,String loginName,String realName,int type,long orderId,String orderNo,int tradeFrom,String tradeNo,long withdrawalsId,String capitalChangeReason,BigDecimal changeMoney,String remark,Date createDate,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.accountType = accountType;
		this.accountId = accountId;
		this.loginName = loginName;
		this.realName = realName;
		this.type = type;
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.tradeFrom = tradeFrom;
		this.tradeNo = tradeNo;
		this.withdrawalsId = withdrawalsId;
		this.capitalChangeReason = capitalChangeReason;
		this.changeMoney = changeMoney;
		this.remark = remark;
		this.createDate = createDate;
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

	public void setAccountType(Integer accountType){
		this.accountType = accountType;
	}

	public Integer getAccountType(){
		return this.accountType;
	}

	public void setAccountId(Long accountId){
		this.accountId = accountId;
	}

	public Long getAccountId(){
		return this.accountId;
	}

	public void setLoginName(String loginName){
		this.loginName = loginName;
	}

	public String getLoginName(){
		return this.loginName;
	}

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getRealName(){
		return this.realName;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}

	public Long getOrderId(){
		return this.orderId;
	}

	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}

	public String getOrderNo(){
		return this.orderNo;
	}

	public void setTradeFrom(Integer tradeFrom){
		this.tradeFrom = tradeFrom;
	}

	public Integer getTradeFrom(){
		return this.tradeFrom;
	}

	public void setTradeNo(String tradeNo){
		this.tradeNo = tradeNo;
	}

	public String getTradeNo(){
		return this.tradeNo;
	}

	public void setWithdrawalsId(Long withdrawalsId){
		this.withdrawalsId = withdrawalsId;
	}

	public Long getWithdrawalsId(){
		return this.withdrawalsId;
	}

	public void setCapitalChangeReason(String capitalChangeReason){
		this.capitalChangeReason = capitalChangeReason;
	}

	public String getCapitalChangeReason(){
		return this.capitalChangeReason;
	}

	public void setChangeMoney(java.math.BigDecimal changeMoney){
		this.changeMoney = changeMoney;
	}

	public java.math.BigDecimal getChangeMoney(){
		return this.changeMoney;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public java.util.Date getCreateDate(){
		return this.createDate;
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
