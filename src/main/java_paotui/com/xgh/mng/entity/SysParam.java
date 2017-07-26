package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysParam
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysParam";
  private long id;
  private long nid;
  private long unitId;
  private long unitNid;
  private long typeId;
  private long typeNid;
  private String paramsCode;
  private String paramsValue;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysParam() {}
  
  public SysParam(long id)
  {
    this.id = id;
  }
  
  public SysParam(long id, long nid, long unitId, long unitNid, long typeId, long typeNid, String paramsCode, String paramsValue, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.unitId = unitId;
    this.unitNid = unitNid;
    this.typeId = typeId;
    this.typeNid = typeNid;
    this.paramsCode = paramsCode;
    this.paramsValue = paramsValue;
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
  
  public long getTypeId()
  {
    return this.typeId;
  }
  
  public void setTypeId(long typeId)
  {
    this.typeId = typeId;
  }
  
  public long getTypeNid()
  {
    return this.typeNid;
  }
  
  public void setTypeNid(long typeNid)
  {
    this.typeNid = typeNid;
  }
  
  public String getParamsCode()
  {
    return this.paramsCode;
  }
  
  public void setParamsCode(String paramsCode)
  {
    this.paramsCode = paramsCode;
  }
  
  public String getParamsValue()
  {
    return this.paramsValue;
  }
  
  public void setParamsValue(String paramsValue)
  {
    this.paramsValue = paramsValue;
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "unitId:" + this.unitId + "\t" + "unitNid:" + this.unitNid + "\t" + "typeId:" + this.typeId + "\t" + "typeNid:" + this.typeNid + "\t" + "paramsCode:" + this.paramsCode + "\t" + "paramsValue:" + this.paramsValue + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
