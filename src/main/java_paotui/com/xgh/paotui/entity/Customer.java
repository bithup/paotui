package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 用户信息表
 * 
 **/
@SuppressWarnings("serial")
public class Customer implements Serializable {

	/****/
	private long id;

	/**账户登录名，默认是手机号**/
	private String loginName;

	/**密码**/
	private String password;

	/**昵称**/
	private String nickName;

	/**头像url**/
	private String headPicUrl;

	/**真实名字**/
	private String realName;

	/**身份证号码**/
	private String idCardNo;

	/**性别**/
	private String sex;

	/**手机号**/
	private String mobilePhone;

	/**账户余额**/
	private BigDecimal balance;

	/**微信名**/
	private String weixinLoginName;

	/**注册时间**/
	private Date registerTime;

	/**城市，可从微信中获取**/
	private String belongCity;

	/**最新一次发货地址**/
	private String newestDeliverAddress;

	/**微信身份认证openid**/
	private String token;

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


	public Customer() { super(); }

	public Customer(long id) {
	 super();
	 this.id=id;
	}

	public Customer(long id,String loginName,String password,String nickName,String headPicUrl,String realName,String idCardNo,String sex,String mobilePhone,BigDecimal balance,String weixinLoginName,Date registerTime,String belongCity,String newestDeliverAddress,String token,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.loginName = loginName;
		this.password = password;
		this.nickName = nickName;
		this.headPicUrl = headPicUrl;
		this.realName = realName;
		this.idCardNo = idCardNo;
		this.sex = sex;
		this.mobilePhone = mobilePhone;
		this.balance = balance;
		this.weixinLoginName = weixinLoginName;
		this.registerTime = registerTime;
		this.belongCity = belongCity;
		this.newestDeliverAddress = newestDeliverAddress;
		this.token = token;
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

	public void setLoginName(String loginName){
		this.loginName = loginName;
	}

	public String getLoginName(){
		return this.loginName;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getNickName(){
		return this.nickName;
	}

	public void setHeadPicUrl(String headPicUrl){
		this.headPicUrl = headPicUrl;
	}

	public String getHeadPicUrl(){
		return this.headPicUrl;
	}

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getRealName(){
		return this.realName;
	}

	public void setIdCardNo(String idCardNo){
		this.idCardNo = idCardNo;
	}

	public String getIdCardNo(){
		return this.idCardNo;
	}

	public void setSex(String sex){
		this.sex = sex;
	}

	public String getSex(){
		return this.sex;
	}

	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhone(){
		return this.mobilePhone;
	}

	public void setBalance(BigDecimal balance){
		this.balance = balance;
	}

	public BigDecimal getBalance(){
		return this.balance;
	}

	public void setWeixinLoginName(String weixinLoginName){
		this.weixinLoginName = weixinLoginName;
	}

	public String getWeixinLoginName(){
		return this.weixinLoginName;
	}

	public void setRegisterTime(Date registerTime){
		this.registerTime = registerTime;
	}

	public Date getRegisterTime(){
		return this.registerTime;
	}

	public void setBelongCity(String belongCity){
		this.belongCity = belongCity;
	}

	public String getBelongCity(){
		return this.belongCity;
	}

	public void setNewestDeliverAddress(String newestDeliverAddress){
		this.newestDeliverAddress = newestDeliverAddress;
	}

	public String getNewestDeliverAddress(){
		return this.newestDeliverAddress;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return this.token;
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
