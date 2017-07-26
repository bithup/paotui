package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 跑客认证表
 * 
 **/
@SuppressWarnings("serial")
public class DeliveryManAuth implements Serializable {

	/****/
	private long id;

	/**跑客id**/
	private long deliveryManId;

	/**身份证号码**/
	private String idCardNo;

	/**身份证照1(正面、背面、手持)**/
	private String idCardPicture1;

	/**身份证照2(正面、背面、手持)**/
	private String idCardPicture2;

	/**身份证照3(正面、背面、手持)**/
	private String idCardPicture3;

	/**微信OpendID**/
	private String weixinOpenId;

	/**微信认证时间**/
	private Date weixinAuthTime;

	/**微信认证状态，0：未认证，1：已认证，2：认证失败 3 ：审核中**/
	private int weixinAuthStatus;

	/**认证提交时间**/
	private Date authSubmitTime;

	/**认证审核时间**/
	private Date authCheckTime;

	/**认证审核人**/
	private long authUserId;

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


	public DeliveryManAuth() { super(); }

	public DeliveryManAuth(long id) {
	 super();
	 this.id=id;
	}

	public DeliveryManAuth(long id,long deliveryManId,String idCardNo,String idCardPicture1,String idCardPicture2,String idCardPicture3,String weixinOpenId,Date weixinAuthTime,int weixinAuthStatus,Date authSubmitTime,Date authCheckTime,long authUserId,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.deliveryManId = deliveryManId;
		this.idCardNo = idCardNo;
		this.idCardPicture1 = idCardPicture1;
		this.idCardPicture2 = idCardPicture2;
		this.idCardPicture3 = idCardPicture3;
		this.weixinOpenId = weixinOpenId;
		this.weixinAuthTime = weixinAuthTime;
		this.weixinAuthStatus = weixinAuthStatus;
		this.authSubmitTime = authSubmitTime;
		this.authCheckTime = authCheckTime;
		this.authUserId = authUserId;
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

	public void setDeliveryManId(Long deliveryManId){
		this.deliveryManId = deliveryManId;
	}

	public Long getDeliveryManId(){
		return this.deliveryManId;
	}

	public void setIdCardNo(String idCardNo){
		this.idCardNo = idCardNo;
	}

	public String getIdCardNo(){
		return this.idCardNo;
	}

	public void setIdCardPicture1(String idCardPicture1){
		this.idCardPicture1 = idCardPicture1;
	}

	public String getIdCardPicture1(){
		return this.idCardPicture1;
	}

	public void setIdCardPicture2(String idCardPicture2){
		this.idCardPicture2 = idCardPicture2;
	}

	public String getIdCardPicture2(){
		return this.idCardPicture2;
	}

	public void setIdCardPicture3(String idCardPicture3){
		this.idCardPicture3 = idCardPicture3;
	}

	public String getIdCardPicture3(){
		return this.idCardPicture3;
	}

	public void setWeixinOpenId(String weixinOpenId){
		this.weixinOpenId = weixinOpenId;
	}

	public String getWeixinOpenId(){
		return this.weixinOpenId;
	}

	public void setWeixinAuthTime(Date weixinAuthTime){
		this.weixinAuthTime = weixinAuthTime;
	}

	public Date getWeixinAuthTime(){
		return this.weixinAuthTime;
	}

	public void setWeixinAuthStatus(Integer weixinAuthStatus){
		this.weixinAuthStatus = weixinAuthStatus;
	}

	public Integer getWeixinAuthStatus(){
		return this.weixinAuthStatus;
	}

	public void setAuthSubmitTime(Date authSubmitTime){
		this.authSubmitTime = authSubmitTime;
	}

	public Date getAuthSubmitTime(){
		return this.authSubmitTime;
	}

	public void setAuthCheckTime(Date authCheckTime){
		this.authCheckTime = authCheckTime;
	}

	public Date getAuthCheckTime(){
		return this.authCheckTime;
	}

	public void setAuthUserId(Long authUserId){
		this.authUserId = authUserId;
	}

	public Long getAuthUserId(){
		return this.authUserId;
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
