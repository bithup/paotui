package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysParamType
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysParamType";
  private long id;
  private long nid;
  private String typeCode;
  private String typeName;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysParamType() {}
  
  public SysParamType(long id)
  {
    this.id = id;
  }
  
  public SysParamType(long id, long nid, String typeCode, String typeName, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.typeCode = typeCode;
    this.typeName = typeName;
    this.ord = ord;
    this.createDate = createDate;
    this.updateDate = updateDate;
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
  
  public String getTypeCode()
  {
    return this.typeCode;
  }
  
  public void setTypeCode(String typeCode)
  {
    this.typeCode = typeCode;
  }
  
  public String getTypeName()
  {
    return this.typeName;
  }
  
  public void setTypeName(String typeName)
  {
    this.typeName = typeName;
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
  
  public String toString()
  {
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "typeCode:" + this.typeCode + "\t" + "typeName:" + this.typeName + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
