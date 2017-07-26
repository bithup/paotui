package com.xgh.paotui.entity;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 通知表
 * 
 **/
@SuppressWarnings("serial")
public class Notify implements Serializable {

	/**唯一id**/
	private long id;

	/**通知城市id**/
	private long openCityId;

	/**城市名称**/
	private String cityName;

	/**标题**/
	private String title;

	/**内容**/
	private String content;

	/**通知对象(1：客户 2：跑客 3：所有）**/
	private int notifyRole;

	/**通知对象id**/
	private long roleId;

	/**通知状态（1：未通知 2：已通知 ）**/
	private int notifyStatus;

	/**通知时间**/
	private Date notifyDate;

	/**状态（1：正常 2：已删除）**/
	private int status;

	/**创建时间**/
	private Date createDate;

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


	public Notify() { super(); }

	public Notify(long id) {
	 super();
	 this.id=id;
	}

	public Notify(long id,long openCityId,String cityName,String title,String content,int notifyRole,long roleId,int notifyStatus,Date notifyDate,int status,Date createDate,long data1,int data2,String data3,String data4,String data5){
		super();
		this.id = id;
		this.openCityId = openCityId;
		this.cityName = cityName;
		this.title = title;
		this.content = content;
		this.notifyRole = notifyRole;
		this.roleId = roleId;
		this.notifyStatus = notifyStatus;
		this.notifyDate = notifyDate;
		this.status = status;
		this.createDate = createDate;
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

	public void setOpenCityId(Long openCityId){
		this.openCityId = openCityId;
	}

	public Long getOpenCityId(){
		return this.openCityId;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return this.cityName;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setNotifyRole(Integer notifyRole){
		this.notifyRole = notifyRole;
	}

	public Integer getNotifyRole(){
		return this.notifyRole;
	}

	public void setRoleId(Long roleId){
		this.roleId = roleId;
	}

	public Long getRoleId(){
		return this.roleId;
	}

	public void setNotifyStatus(Integer notifyStatus){
		this.notifyStatus = notifyStatus;
	}

	public Integer getNotifyStatus(){
		return this.notifyStatus;
	}

	public void setNotifyDate(java.util.Date notifyDate){
		this.notifyDate = notifyDate;
	}

	public Date getNotifyDate(){
		return this.notifyDate;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
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
