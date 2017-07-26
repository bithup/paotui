package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysDictMain
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysDictMain";
  private long id;
  private long nid;
  private long parentId;
  private long parentNid;
  private String dictPrefix;
  private String dictCode;
  private String dictName;
  private String dictType;
  private String dictValue;
  private long isSys;
  private long isUserConf;
  private int isMate;
  private int isExtends;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysDictMain() {}
  
  public SysDictMain(long id)
  {
    this.id = id;
  }
  
  public SysDictMain(long id, long nid, long parentId, long parentNid, String dictPrefix, String dictCode, String dictName, String dictType, String dictValue, long isSys, long isUserConf, int isMate, int isExtends, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.parentId = parentId;
    this.parentNid = parentNid;
    this.dictPrefix = dictPrefix;
    this.dictCode = dictCode;
    this.dictName = dictName;
    this.dictType = dictType;
    this.dictValue = dictValue;
    this.isSys = isSys;
    this.isUserConf = isUserConf;
    this.isMate = isMate;
    this.isExtends = isExtends;
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
  
  public long getParentId()
  {
    return this.parentId;
  }
  
  public void setParentId(long parentId)
  {
    this.parentId = parentId;
  }
  
  public long getParentNid()
  {
    return this.parentNid;
  }
  
  public void setParentNid(long parentNid)
  {
    this.parentNid = parentNid;
  }
  
  public String getDictPrefix()
  {
    return this.dictPrefix;
  }
  
  public void setDictPrefix(String dictPrefix)
  {
    this.dictPrefix = dictPrefix;
  }
  
  public String getDictCode()
  {
    return this.dictCode;
  }
  
  public void setDictCode(String dictCode)
  {
    this.dictCode = dictCode;
  }
  
  public String getDictName()
  {
    return this.dictName;
  }
  
  public void setDictName(String dictName)
  {
    this.dictName = dictName;
  }
  
  public String getDictType()
  {
    return this.dictType;
  }
  
  public void setDictType(String dictType)
  {
    this.dictType = dictType;
  }
  
  public String getDictValue()
  {
    return this.dictValue;
  }
  
  public void setDictValue(String dictValue)
  {
    this.dictValue = dictValue;
  }
  
  public long getIsSys()
  {
    return this.isSys;
  }
  
  public void setIsSys(long isSys)
  {
    this.isSys = isSys;
  }
  
  public long getIsUserConf()
  {
    return this.isUserConf;
  }
  
  public void setIsUserConf(long isUserConf)
  {
    this.isUserConf = isUserConf;
  }
  
  public int getIsMate()
  {
    return this.isMate;
  }
  
  public void setIsMate(int isMate)
  {
    this.isMate = isMate;
  }
  
  public int getIsExtends()
  {
    return this.isExtends;
  }
  
  public void setIsExtends(int isExtends)
  {
    this.isExtends = isExtends;
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "parentId:" + this.parentId + "\t" + "parentNid:" + this.parentNid + "\t" + "dictPrefix:" + this.dictPrefix + "\t" + "dictCode:" + this.dictCode + "\t" + "dictName:" + this.dictName + "\t" + "dictType:" + this.dictType + "\t" + "dictValue:" + this.dictValue + "\t" + "isSys:" + this.isSys + "\t" + "isUserConf:" + this.isUserConf + "\t" + "isMate:" + this.isMate + "\t" + "isExtends:" + this.isExtends + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
