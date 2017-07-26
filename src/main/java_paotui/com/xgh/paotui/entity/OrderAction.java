package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 跑客操作步骤表
 * 
 **/
@SuppressWarnings("serial")
public class OrderAction implements Serializable {

	/****/
	private long id;

	/**订单id**/
	private long orderId;

	/**订单动作名称**/
	private String actionName;

	/**订单动作时间**/
	private Date actionTime;

	/**动作类型，1、完成动作；2、进行中动作**/
	private int actionType;

	/**部分环节，拍的照片。多个照片，用“||”隔开**/
	private String actionImage;

	/**显示标志，完成动作后，上一个进行中动作要设置为不显示，1：显示；2、不显示**/
	private int showFlag;

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


	public OrderAction() { super(); }

	public OrderAction(long id) {
	 super();
	 this.id=id;
	}

	public OrderAction(long id,long orderId,String actionName,Date actionTime,int actionType,String actionImage,int showFlag,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.orderId = orderId;
		this.actionName = actionName;
		this.actionTime = actionTime;
		this.actionType = actionType;
		this.actionImage = actionImage;
		this.showFlag = showFlag;
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

	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}

	public Long getOrderId(){
		return this.orderId;
	}

	public void setActionName(String actionName){
		this.actionName = actionName;
	}

	public String getActionName(){
		return this.actionName;
	}

	public void setActionTime(java.util.Date actionTime){
		this.actionTime = actionTime;
	}

	public java.util.Date getActionTime(){
		return this.actionTime;
	}

	public void setActionType(Integer actionType){
		this.actionType = actionType;
	}

	public Integer getActionType(){
		return this.actionType;
	}

	public void setActionImage(String actionImage){
		this.actionImage = actionImage;
	}

	public String getActionImage(){
		return this.actionImage;
	}

	public void setShowFlag(Integer showFlag){
		this.showFlag = showFlag;
	}

	public Integer getShowFlag(){
		return this.showFlag;
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
