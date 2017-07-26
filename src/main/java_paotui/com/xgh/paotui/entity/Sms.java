package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 短信网关表
 * 
 **/
@SuppressWarnings("serial")
public class Sms implements Serializable {

	/****/
	private long id;

	/**短信用户名**/
	private String smsAccount;

	/**短信密码**/
	private String smsPassword;

	/**短信格式（描述）**/
	private String smsPattern;

	/**短信发送地址**/
	private String smsSendurl;

	/**状态:1启用;0,不启用**/
	private int status;

	/**每天发送短信验证码最大次数(默认为6次)**/
	private int codeCount;

	/**创建时间**/
	private Date createDate;

	/**修改时间**/
	private Date updateDate;

	/**扩展1**/
	private long data1;

	/**扩展2**/
	private long data2;

	/**扩展3**/
	private int data3;

	/**扩展4**/
	private int data4;

	/**扩展5**/
	private Double data5;

	/**扩展6**/
	private Double data6;

	/**扩展7**/
	private String data7;

	/**扩展9**/
	private String data8;

	/**扩展9**/
	private String data9;

	/**扩展10**/
	private String data10;


	public Sms() { super(); }

	public Sms(long id) {
		super();
		this.id=id;
	}

	public Sms(long id, String smsAccount, String smsPassword, String smsPattern, String smsSendurl, int status, int codeCount, Date createDate, Date updateDate, long data1, long data2, int data3, int data4, Double data5, Double data6, String data7, String data8, String data9, String data10){
		super();
		this.id = id;
		this.smsAccount = smsAccount;
		this.smsPassword = smsPassword;
		this.smsPattern = smsPattern;
		this.smsSendurl = smsSendurl;
		this.status = status;
		this.codeCount = codeCount;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
		this.data5 = data5;
		this.data6 = data6;
		this.data7 = data7;
		this.data8 = data8;
		this.data9 = data9;
		this.data10 = data10;

	}
	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setSmsAccount(String smsAccount){
		this.smsAccount = smsAccount;
	}

	public String getSmsAccount(){
		return this.smsAccount;
	}

	public void setSmsPassword(String smsPassword){
		this.smsPassword = smsPassword;
	}

	public String getSmsPassword(){
		return this.smsPassword;
	}

	public void setSmsPattern(String smsPattern){
		this.smsPattern = smsPattern;
	}

	public String getSmsPattern(){
		return this.smsPattern;
	}

	public void setSmsSendurl(String smsSendurl){
		this.smsSendurl = smsSendurl;
	}

	public String getSmsSendurl(){
		return this.smsSendurl;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setCodeCount(Integer codeCount){
		this.codeCount = codeCount;
	}

	public Integer getCodeCount(){
		return this.codeCount;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
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

	public void setData2(Long data2){
		this.data2 = data2;
	}

	public Long getData2(){
		return this.data2;
	}

	public void setData3(Integer data3){
		this.data3 = data3;
	}

	public Integer getData3(){
		return this.data3;
	}

	public void setData4(Integer data4){
		this.data4 = data4;
	}

	public Integer getData4(){
		return this.data4;
	}

	public void setData5(Double data5){
		this.data5 = data5;
	}

	public Double getData5(){
		return this.data5;
	}

	public void setData6(Double data6){
		this.data6 = data6;
	}

	public Double getData6(){
		return this.data6;
	}

	public void setData7(String data7){
		this.data7 = data7;
	}

	public String getData7(){
		return this.data7;
	}

	public void setData8(String data8){
		this.data8 = data8;
	}

	public String getData8(){
		return this.data8;
	}

	public void setData9(String data9){
		this.data9 = data9;
	}

	public String getData9(){
		return this.data9;
	}

	public void setData10(String data10){
		this.data10 = data10;
	}

	public String getData10(){
		return this.data10;
	}

}
