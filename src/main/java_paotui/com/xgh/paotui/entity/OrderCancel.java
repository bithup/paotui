package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 *
 *
 *
 **/
@SuppressWarnings("serial")
public class OrderCancel implements Serializable {

	/****/
	private long id;

	/**订单类型：1、帮送；2、帮取；3、帮买；4：代排队**/
	private int orderType;

	/**订单id**/
	private long orderId;

	/**订单编号**/
	private String orderNo;

	/**订单取消时，订单状态**/
	private int orderStatus;

	/**订单取消时，跑客的动作**/
	private int actionStep;

	/**订单状态描述**/
	private String orderStatusRemark;

	/**审核人id**/
	private long checkUserId;

	/**审核时间**/
	private Date checkTime;

	/**审核状态（1：审核通过 2：未审核 3：审核未通过 ）**/
	private int checkState;

	/**订单取消时间**/
	private Date createTime;

	/**是否超时取消订单，超时，将扣除部分跑腿费。1：未超时，2：超时**/
	private int isTimeout;

	/**状态（1：正常 2：已删除）**/
	private int status;

	/****/
	private String data1;

	/****/
	private String data2;

	/****/
	private String data3;

	/****/
	private String data4;

	/****/
	private String data5;


	public OrderCancel() { super(); }

	public OrderCancel(long id) {
		super();
		this.id=id;
	}

	public OrderCancel(long id,int orderType,long orderId,String orderNo,int orderStatus,int actionStep,String orderStatusRemark,long checkUserId,Date checkTime,int checkState,Date createTime,int isTimeout,int status,String data1,String data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.orderType = orderType;
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.orderStatus = orderStatus;
		this.actionStep = actionStep;
		this.orderStatusRemark = orderStatusRemark;
		this.checkUserId = checkUserId;
		this.checkTime = checkTime;
		this.checkState = checkState;
		this.createTime = createTime;
		this.isTimeout = isTimeout;
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

	public void setOrderType(Integer orderType){
		this.orderType = orderType;
	}

	public Integer getOrderType(){
		return this.orderType;
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

	public void setOrderStatus(Integer orderStatus){
		this.orderStatus = orderStatus;
	}

	public Integer getOrderStatus(){
		return this.orderStatus;
	}

	public void setActionStep(Integer actionStep){
		this.actionStep = actionStep;
	}

	public Integer getActionStep(){
		return this.actionStep;
	}

	public void setOrderStatusRemark(String orderStatusRemark){
		this.orderStatusRemark = orderStatusRemark;
	}

	public String getOrderStatusRemark(){
		return this.orderStatusRemark;
	}

	public void setCheckUserId(Long checkUserId){
		this.checkUserId = checkUserId;
	}

	public Long getCheckUserId(){
		return this.checkUserId;
	}

	public void setCheckTime(java.util.Date checkTime){
		this.checkTime = checkTime;
	}

	public java.util.Date getCheckTime(){
		return this.checkTime;
	}

	public void setCheckState(Integer checkState){
		this.checkState = checkState;
	}

	public Integer getCheckState(){
		return this.checkState;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setIsTimeout(Integer isTimeout){
		this.isTimeout = isTimeout;
	}

	public Integer getIsTimeout(){
		return this.isTimeout;
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
