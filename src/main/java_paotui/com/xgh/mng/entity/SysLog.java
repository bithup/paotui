package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysLog
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysLog";
  private long id;
  private long nid;
  private long userId;
  private String userName;
  private String account;
  private String ip;
  private String moduleName;
  private Date operateDate;
  private String operateType;
  private String operateResult;
  private String operateOs;
  private String operateBrowser;
  private long unitId;
  private String businessId;
  private String tableName;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  private String data1;
  private String data2;
  
  public SysLog() {}
  
  public SysLog(long id)
  {
    this.id = id;
  }
  
  public SysLog(long id, long nid, long userId, String userName, String account, String ip, String moduleName, Date operateDate, String operateType, String operateResult, String operateOs, String operateBrowser, long unitId, String businessId, String tableName, long ord, Date createDate, Date updateDate, int status, String remark, String data1, String data2)
  {
    this.id = id;
    this.nid = nid;
    this.userId = userId;
    this.userName = userName;
    this.account = account;
    this.ip = ip;
    this.moduleName = moduleName;
    this.operateDate = operateDate;
    this.operateType = operateType;
    this.operateResult = operateResult;
    this.operateOs = operateOs;
    this.operateBrowser = operateBrowser;
    this.unitId = unitId;
    this.businessId = businessId;
    this.tableName = tableName;
    this.ord = ord;
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.status = status;
    this.remark = remark;
    this.data1 = data1;
    this.data2 = data2;
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
  
  public long getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(long userId)
  {
    this.userId = userId;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public String getAccount()
  {
    return this.account;
  }
  
  public void setAccount(String account)
  {
    this.account = account;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public String getModuleName()
  {
    return this.moduleName;
  }
  
  public void setModuleName(String moduleName)
  {
    this.moduleName = moduleName;
  }
  
  public Date getOperateDate()
  {
    return this.operateDate;
  }
  
  public void setOperateDate(Date operateDate)
  {
    this.operateDate = operateDate;
  }
  
  public String getOperateType()
  {
    return this.operateType;
  }
  
  public void setOperateType(String operateType)
  {
    this.operateType = operateType;
  }
  
  public String getOperateResult()
  {
    return this.operateResult;
  }
  
  public void setOperateResult(String operateResult)
  {
    this.operateResult = operateResult;
  }
  
  public String getOperateOs()
  {
    return this.operateOs;
  }
  
  public void setOperateOs(String operateOs)
  {
    this.operateOs = operateOs;
  }
  
  public String getOperateBrowser()
  {
    return this.operateBrowser;
  }
  
  public void setOperateBrowser(String operateBrowser)
  {
    this.operateBrowser = operateBrowser;
  }
  
  public long getUnitId()
  {
    return this.unitId;
  }
  
  public void setUnitId(long unitId)
  {
    this.unitId = unitId;
  }
  
  public String getBusinessId()
  {
    return this.businessId;
  }
  
  public void setBusinessId(String businessId)
  {
    this.businessId = businessId;
  }
  
  public String getTableName()
  {
    return this.tableName;
  }
  
  public void setTableName(String tableName)
  {
    this.tableName = tableName;
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
  
  public String getData1()
  {
    return this.data1;
  }
  
  public void setData1(String data1)
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
  
  public String toString()
  {
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "userId:" + this.userId + "\t" + "userName:" + this.userName + "\t" + "account:" + this.account + "\t" + "ip:" + this.ip + "\t" + "moduleName:" + this.moduleName + "\t" + "operateDate:" + this.operateDate + "\t" + "operateType:" + this.operateType + "\t" + "operateResult:" + this.operateResult + "\t" + "operateOs:" + this.operateOs + "\t" + "operateBrowser:" + this.operateBrowser + "\t" + "unitId:" + this.unitId + "\t" + "businessId:" + this.businessId + "\t" + "tableName:" + this.tableName + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark + "\t" + "data1:" + this.data1 + "\t" + "data2:" + this.data2;
  }
}
