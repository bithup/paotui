package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysValidation
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysValidation";
  private long id;
  private long nid;
  private long joinId;
  private long joinNid;
  private String joinType;
  private String field;
  private String ruleType;
  private String ruleTypeValue;
  private String msg;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysValidation() {}
  
  public SysValidation(long id)
  {
    this.id = id;
  }
  
  public SysValidation(long id, long nid, long joinId, long joinNid, String joinType, String field, String ruleType, String ruleTypeValue, String msg, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.joinId = joinId;
    this.joinNid = joinNid;
    this.joinType = joinType;
    this.field = field;
    this.ruleType = ruleType;
    this.ruleTypeValue = ruleTypeValue;
    this.msg = msg;
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
  
  public long getJoinId()
  {
    return this.joinId;
  }
  
  public void setJoinId(long joinId)
  {
    this.joinId = joinId;
  }
  
  public long getJoinNid()
  {
    return this.joinNid;
  }
  
  public void setJoinNid(long joinNid)
  {
    this.joinNid = joinNid;
  }
  
  public String getJoinType()
  {
    return this.joinType;
  }
  
  public void setJoinType(String joinType)
  {
    this.joinType = joinType;
  }
  
  public String getField()
  {
    return this.field;
  }
  
  public void setField(String field)
  {
    this.field = field;
  }
  
  public String getRuleType()
  {
    return this.ruleType;
  }
  
  public void setRuleType(String ruleType)
  {
    this.ruleType = ruleType;
  }
  
  public String getRuleTypeValue()
  {
    return this.ruleTypeValue;
  }
  
  public void setRuleTypeValue(String ruleTypeValue)
  {
    this.ruleTypeValue = ruleTypeValue;
  }
  
  public String getMsg()
  {
    return this.msg;
  }
  
  public void setMsg(String msg)
  {
    this.msg = msg;
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "joinId:" + this.joinId + "\t" + "joinNid:" + this.joinNid + "\t" + "joinType:" + this.joinType + "\t" + "field:" + this.field + "\t" + "ruleType:" + this.ruleType + "\t" + "ruleTypeValue:" + this.ruleTypeValue + "\t" + "msg:" + this.msg + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
