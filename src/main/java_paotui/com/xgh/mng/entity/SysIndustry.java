package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysIndustry
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysIndustry";
  private long id;
  private long nid;
  private String code;
  private String name;
  private int version;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private Date stopDate;
  private int isPass;
  private int status;
  private String remark;
  
  public SysIndustry() {}
  
  public SysIndustry(long id)
  {
    this.id = id;
  }
  
  public SysIndustry(long id, long nid, String code, String name, int version, long ord, Date createDate, Date updateDate, Date stopDate, int isPass, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.code = code;
    this.name = name;
    this.version = version;
    this.ord = ord;
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.stopDate = stopDate;
    this.isPass = isPass;
    this.status = status;
    this.remark = remark;
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
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int getVersion()
  {
    return this.version;
  }
  
  public void setVersion(int version)
  {
    this.version = version;
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
  
  public Date getStopDate()
  {
    return this.stopDate;
  }
  
  public void setStopDate(Date stopDate)
  {
    this.stopDate = stopDate;
  }
  
  public int getIsPass()
  {
    return this.isPass;
  }
  
  public void setIsPass(int isPass)
  {
    this.isPass = isPass;
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
  
  public String toString()
  {
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "code:" + this.code + "\t" + "name:" + this.name + "\t" + "version:" + this.version + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "stopDate:" + this.stopDate + "\t" + "isPass:" + this.isPass + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
