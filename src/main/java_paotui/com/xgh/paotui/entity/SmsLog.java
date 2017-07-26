package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 短信日志表
 * 
 **/
@SuppressWarnings("serial")
public class SmsLog implements Serializable {

	/**唯一id**/
	private long id;

	/**接收短信的手机号码**/
	private String telephone;

	/**接收的短信内容**/
	private String content;

	/**发送时间**/
	private Date sendTime;

	/**发送状态（1：成功 2：失败 3：其他）**/
	private int status;

	/**扩展1**/
	private long data1;

	/**扩展2**/
	private String data2;

	/**扩展3**/
	private String data3;


	public SmsLog() { super(); }

	public SmsLog(long id) {
	 super();
	 this.id=id;
	}

	public SmsLog(long id,String telephone,String content,Date sendTime,int status,long data1,String data2,String data3){
		super();
		this.id = id;
		this.telephone = telephone;
		this.content = content;
		this.sendTime = sendTime;
		this.status = status;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;

	}
	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setTelephone(String telephone){
		this.telephone = telephone;
	}

	public String getTelephone(){
		return this.telephone;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setSendTime(java.util.Date sendTime){
		this.sendTime = sendTime;
	}

	public java.util.Date getSendTime(){
		return this.sendTime;
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

}
