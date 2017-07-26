package com.xgh.mng.entity;

import java.io.Serializable;

public class SysDeptUser
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysDeptUser";
  private long id;
  private long nid;
  private long deptId;
  private long deptNid;
  private String deptCode;
  private long userId;
  private long userNid;
  private long ord;
  private long userLevel;
  private int status;
  private String remark;
  
  public SysDeptUser() {}
  
  public SysDeptUser(long id)
  {
    this.id = id;
  }
  
  public SysDeptUser(long id, long nid, long deptId, long deptNid, String deptCode, long userId, long userNid, long ord, long userLevel, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.deptId = deptId;
    this.deptNid = deptNid;
    this.deptCode = deptCode;
    this.userId = userId;
    this.userNid = userNid;
    this.ord = ord;
    this.userLevel = userLevel;
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
  
  public long getDeptId()
  {
    return this.deptId;
  }
  
  public void setDeptId(long deptId)
  {
    this.deptId = deptId;
  }
  
  public long getDeptNid()
  {
    return this.deptNid;
  }
  
  public void setDeptNid(long deptNid)
  {
    this.deptNid = deptNid;
  }
  
  public String getDeptCode()
  {
    return this.deptCode;
  }
  
  public void setDeptCode(String deptCode)
  {
    this.deptCode = deptCode;
  }
  
  public long getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(long userId)
  {
    this.userId = userId;
  }
  
  public long getUserNid()
  {
    return this.userNid;
  }
  
  public void setUserNid(long userNid)
  {
    this.userNid = userNid;
  }
  
  public long getOrd()
  {
    return this.ord;
  }
  
  public void setOrd(long ord)
  {
    this.ord = ord;
  }
  
  public long getUserLevel()
  {
    return this.userLevel;
  }
  
  public void setUserLevel(long userLevel)
  {
    this.userLevel = userLevel;
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "deptId:" + this.deptId + "\t" + "deptNid:" + this.deptNid + "\t" + "deptCode:" + this.deptCode + "\t" + "userId:" + this.userId + "\t" + "userNid:" + this.userNid + "\t" + "ord:" + this.ord + "\t" + "userLevel:" + this.userLevel + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
