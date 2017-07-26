package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysRole
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysRole";
  private long id;
  private long nid;
  private long instId;
  private long unitId;
  private String roleCode;
  private String roleName;
  private String roleDesc;
  private long isSys;
  private long isPrivilege;
  private int level;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysRole() {}
  
  public SysRole(long id)
  {
    this.id = id;
  }
  
  public SysRole(long id, long nid, long instId, long unitId, String roleCode, String roleName, String roleDesc, long isSys, long isPrivilege, int level, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.instId = instId;
    this.unitId = unitId;
    this.roleCode = roleCode;
    this.roleName = roleName;
    this.roleDesc = roleDesc;
    this.isSys = isSys;
    this.isPrivilege = isPrivilege;
    this.level = level;
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
  
  public long getInstId()
  {
    return this.instId;
  }
  
  public void setInstId(long instId)
  {
    this.instId = instId;
  }
  
  public long getUnitId()
  {
    return this.unitId;
  }
  
  public void setUnitId(long unitId)
  {
    this.unitId = unitId;
  }
  
  public String getRoleCode()
  {
    return this.roleCode;
  }
  
  public void setRoleCode(String roleCode)
  {
    this.roleCode = roleCode;
  }
  
  public String getRoleName()
  {
    return this.roleName;
  }
  
  public void setRoleName(String roleName)
  {
    this.roleName = roleName;
  }
  
  public String getRoleDesc()
  {
    return this.roleDesc;
  }
  
  public void setRoleDesc(String roleDesc)
  {
    this.roleDesc = roleDesc;
  }
  
  public long getIsSys()
  {
    return this.isSys;
  }
  
  public void setIsSys(long isSys)
  {
    this.isSys = isSys;
  }
  
  public long getIsPrivilege()
  {
    return this.isPrivilege;
  }
  
  public void setIsPrivilege(long isPrivilege)
  {
    this.isPrivilege = isPrivilege;
  }
  
  public int getLevel()
  {
    return this.level;
  }
  
  public void setLevel(int level)
  {
    this.level = level;
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "instId:" + this.instId + "\t" + "unitId:" + this.unitId + "\t" + "roleCode:" + this.roleCode + "\t" + "roleName:" + this.roleName + "\t" + "roleDesc:" + this.roleDesc + "\t" + "isSys:" + this.isSys + "\t" + "isPrivilege:" + this.isPrivilege + "\t" + "level:" + this.level + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
