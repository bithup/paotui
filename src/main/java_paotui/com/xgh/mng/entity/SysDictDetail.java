package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysDictDetail
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysDictDetail";
  private long id;
  private long nid;
  private long unitId;
  private long unitNid;
  private long dictMainId;
  private long dictMainNid;
  private String code;
  private String value;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysDictDetail() {}
  
  public SysDictDetail(long id)
  {
    this.id = id;
  }
  
  public SysDictDetail(long id, long nid, long unitId, long unitNid, long dictMainId, long dictMainNid, String code, String value, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.unitId = unitId;
    this.unitNid = unitNid;
    this.dictMainId = dictMainId;
    this.dictMainNid = dictMainNid;
    this.code = code;
    this.value = value;
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
  
  public long getDictMainId()
  {
    return this.dictMainId;
  }
  
  public void setDictMainId(long dictMainId)
  {
    this.dictMainId = dictMainId;
  }
  
  public long getDictMainNid()
  {
    return this.dictMainNid;
  }
  
  public void setDictMainNid(long dictMainNid)
  {
    this.dictMainNid = dictMainNid;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "unitId:" + this.unitId + "\t" + "unitNid:" + this.unitNid + "\t" + "dictMainId:" + this.dictMainId + "\t" + "dictMainNid:" + this.dictMainNid + "\t" + "code:" + this.code + "\t" + "value:" + this.value + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
