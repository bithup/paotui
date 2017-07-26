package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 订单评价表
 * 
 **/
@SuppressWarnings("serial")
public class Evaluation implements Serializable {

	/**唯一id**/
	private long id;

	/**订单id**/
	private long orderId;

	/**客户id**/
	private long customerId;

	/**跑客id**/
	private long deliveryManId;

	/**评价内容**/
	private String content;

	/**评价等级(1：好评  2：中评  3：差评)**/
	private String level;

	/**评论时间**/
	private Date createDate;

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


	public Evaluation() { super(); }

	public Evaluation(long id) {
	 super();
	 this.id=id;
	}

	public Evaluation(long id,long orderId,long customerId,long deliveryManId,String content,String level,Date createDate,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.orderId = orderId;
		this.customerId = customerId;
		this.deliveryManId = deliveryManId;
		this.content = content;
		this.level = level;
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

	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}

	public Long getOrderId(){
		return this.orderId;
	}

	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}

	public Long getCustomerId(){
		return this.customerId;
	}

	public void setDeliveryManId(Long deliveryManId){
		this.deliveryManId = deliveryManId;
	}

	public Long getDeliveryManId(){
		return this.deliveryManId;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setLevel(String level){
		this.level = level;
	}

	public String getLevel(){
		return this.level;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
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
