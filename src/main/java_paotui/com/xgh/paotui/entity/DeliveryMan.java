package com.xgh.paotui.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 跑客信息表
 * 
 **/
@SuppressWarnings("serial")
public class DeliveryMan implements Serializable {

	/****/
	private long id;

	/**登录名**/
	private String loginName;

	/**密码**/
	private String password;

	/**真实名字**/
	private String realName;

	/**头像**/
	private String headImage;

	/**姓氏**/
	private String surname;

	/**出生日期**/
	private Date birthday;

	/**性别**/
	private String sex;

	/**手机号**/
	private String mobilePhone;

	/**账户余额**/
	private BigDecimal balance;

	/**个人推广二维码**/
	private String qrCode;

	/**跑客星级**/
	private int starLevel;

	/**跑客类别，1：众包；2：公司员工**/
	private int belongType;

	/**所属城市,跑客只能接所属城市的订单**/
	private long belongCityId;

	/**接单总数**/
	private int getOrderCount;

	/**好评率**/
	private Double praiseRate;

	/**认证状态，1、已认证；2、未认证；3、已注销；4、已冻结**/
	private int authStatus;

	/**用户的微信openid**/
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


	public DeliveryMan() { super(); }

	public DeliveryMan(long id) {
	 super();
	 this.id=id;
	}

	public DeliveryMan(long id,String loginName,String password,String realName,String headImage,String surname,Date birthday,String sex,String mobilePhone,BigDecimal balance,String qrCode,int starLevel,int belongType,long belongCityId,int getOrderCount,Double praiseRate,int authStatus,String token,int status,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.loginName = loginName;
		this.password = password;
		this.realName = realName;
		this.headImage = headImage;
		this.surname = surname;
		this.birthday = birthday;
		this.sex = sex;
		this.mobilePhone = mobilePhone;
		this.balance = balance;
		this.qrCode = qrCode;
		this.starLevel = starLevel;
		this.belongType = belongType;
		this.belongCityId = belongCityId;
		this.getOrderCount = getOrderCount;
		this.praiseRate = praiseRate;
		this.authStatus = authStatus;
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

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getRealName(){
		return this.realName;
	}

	public void setHeadImage(String headImage){
		this.headImage = headImage;
	}

	public String getHeadImage(){
		return this.headImage;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}

	public String getSurname(){
		return this.surname;
	}

	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}

	public java.util.Date getBirthday(){
		return this.birthday;
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

	public void setBalance(java.math.BigDecimal balance){
		this.balance = balance;
	}

	public java.math.BigDecimal getBalance(){
		return this.balance;
	}

	public void setQrCode(String qrCode){
		this.qrCode = qrCode;
	}

	public String getQrCode(){
		return this.qrCode;
	}

	public void setStarLevel(Integer starLevel){
		this.starLevel = starLevel;
	}

	public Integer getStarLevel(){
		return this.starLevel;
	}

	public void setBelongType(Integer belongType){
		this.belongType = belongType;
	}

	public Integer getBelongType(){
		return this.belongType;
	}

	public void setBelongCityId(Long belongCityId){
		this.belongCityId = belongCityId;
	}

	public Long getBelongCityId(){
		return this.belongCityId;
	}

	public void setGetOrderCount(Integer getOrderCount){
		this.getOrderCount = getOrderCount;
	}

	public Integer getGetOrderCount(){
		return this.getOrderCount;
	}

	public void setPraiseRate(Double praiseRate){
		this.praiseRate = praiseRate;
	}

	public Double getPraiseRate(){
		return this.praiseRate;
	}

	public void setAuthStatus(Integer authStatus){
		this.authStatus = authStatus;
	}

	public Integer getAuthStatus(){
		return this.authStatus;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return this.token;
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
