package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysUser
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysUser";
  private long id;
  private long nid;
  private long instId;
  private long instNid;
  private String instCode;
  private long unitId;
  private long unitNid;
  private String unitCode;
  private String account;
  private String password;
  private String userName;
  private String mobile;
  private String email;
  private String userCord;
  private long isSys;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  private long data1;
  private String data2;
  private String data3;
  /**所属城市id**/
  private long openCityId;

  /**所属城市名称**/
  private String openCityName;
  
  public SysUser() {}
  
  public SysUser(long id)
  {
    this.id = id;
  }
  
  public SysUser(long id, long nid, long instId, long instNid, String instCode, long unitId, long unitNid, String unitCode, String account, String password, String userName, String mobile, String email, String userCord, long isSys, long ord, Date createDate, Date updateDate, int status, String remark, long data1, String data2, String data3,long openCityId,String openCityName)
  {
    this.id = id;
    this.nid = nid;
    this.instId = instId;
    this.instNid = instNid;
    this.instCode = instCode;
    this.unitId = unitId;
    this.unitNid = unitNid;
    this.unitCode = unitCode;
    this.account = account;
    this.password = password;
    this.userName = userName;
    this.mobile = mobile;
    this.email = email;
    this.userCord = userCord;
    this.isSys = isSys;
    this.ord = ord;
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.status = status;
    this.remark = remark;
    this.data1 = data1;
    this.data2 = data2;
    this.data3 = data3;
    this.openCityId = openCityId;
    this.openCityName = openCityName;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long id)
  {
    this.id = id;
  }
  
  public long getNid()
  {
    return this.nid;
  }
  
  public void setNid(long nid)
  {
    this.nid = nid;
  }
  
  public long getInstId()
  {
    return this.instId;
  }
  
  public void setInstId(long instId)
  {
    this.instId = instId;
  }
  
  public long getInstNid()
  {
    return this.instNid;
  }
  
  public void setInstNid(long instNid)
  {
    this.instNid = instNid;
  }
  
  public String getInstCode()
  {
    return this.instCode;
  }
  
  public void setInstCode(String instCode)
  {
    this.instCode = instCode;
  }
  
  public long getUnitId()
  {
    return this.unitId;
  }
  
  public void setUnitId(long unitId)
  {
    this.unitId = unitId;
  }
  
  public long getUnitNid()
  {
    return this.unitNid;
  }
  
  public void setUnitNid(long unitNid)
  {
    this.unitNid = unitNid;
  }
  
  public String getUnitCode()
  {
    return this.unitCode;
  }
  
  public void setUnitCode(String unitCode)
  {
    this.unitCode = unitCode;
  }
  
  public String getAccount()
  {
    return this.account;
  }
  
  public void setAccount(String account)
  {
    this.account = account;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getUserCord()
  {
    return this.userCord;
  }
  
  public void setUserCord(String userCord)
  {
    this.userCord = userCord;
  }
  
  public long getIsSys()
  {
    return this.isSys;
  }
  
  public void setIsSys(long isSys)
  {
    this.isSys = isSys;
  }
  
  public long getOrd()
  {
    return this.ord;
  }
  
  public void setOrd(long ord)
  {
    this.ord = ord;
  }
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }
  
  public Date getUpdateDate()
  {
    return this.updateDate;
  }
  
  public void setUpdateDate(Date updateDate)
  {
    this.updateDate = updateDate;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public long getData1()
  {
    return this.data1;
  }
  
  public void setData1(long data1)
  {
    this.data1 = data1;
  }
  
  public String getData2()
  {
    return this.data2;
  }
  
  public void setData2(String data2)
  {
    this.data2 = data2;
  }
  
  public String getData3()
  {
    return this.data3;
  }
  
  public void setData3(String data3)
  {
    this.data3 = data3;
  }

  public void setOpenCityId(Long openCityId){
    this.openCityId = openCityId;
  }

  public Long getOpenCityId(){
    return this.openCityId;
  }

  public void setOpenCityName(String openCityName){
    this.openCityName = openCityName;
  }

  public String getOpenCityName(){
    return this.openCityName;
  }

  
  public String toString()
  {
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "instId:" + this.instId + "\t" + "instNid:" + this.instNid + "\t" + "instCode:" + this.instCode + "\t" + "unitId:" + this.unitId + "\t" + "unitNid:" + this.unitNid + "\t" + "unitCode:" + this.unitCode + "\t" + "account:" + this.account + "\t" + "password:" + this.password + "\t" + "userName:" + this.userName + "\t" + "mobile:" + this.mobile + "\t" + "email:" + this.email + "\t" + "userCord:" + this.userCord + "\t" + "isSys:" + this.isSys + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark + "\t" + "data1:" + this.data1 + "\t" + "data2:" + this.data2 + "\t" + "data3:" + this.data3+ "\t" + "openCityId:" + this.openCityId + "\t" + "openCityName:" + this.openCityName;
  }
}
