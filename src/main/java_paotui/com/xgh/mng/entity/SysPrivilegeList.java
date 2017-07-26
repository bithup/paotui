package com.xgh.mng.entity;

import java.io.Serializable;

public class SysPrivilegeList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysPrivilegeList";
  private long id;
  private long nid;
  private long unitId;
  private long unitNid;
  private String privilegeMaster;
  private long privilegeMasterValue;
  private String privilegeAccess;
  private long privilegeAccessValue;
  
  public SysPrivilegeList() {}
  
  public SysPrivilegeList(long id)
  {
    this.id = id;
  }
  
  public SysPrivilegeList(long id, long nid, long unitId, long unitNid, String privilegeMaster, long privilegeMasterValue, String privilegeAccess, long privilegeAccessValue)
  {
    this.id = id;
    this.nid = nid;
    this.unitId = unitId;
    this.unitNid = unitNid;
    this.privilegeMaster = privilegeMaster;
    this.privilegeMasterValue = privilegeMasterValue;
    this.privilegeAccess = privilegeAccess;
    this.privilegeAccessValue = privilegeAccessValue;
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
  
  public String getPrivilegeMaster()
  {
    return this.privilegeMaster;
  }
  
  public void setPrivilegeMaster(String privilegeMaster)
  {
    this.privilegeMaster = privilegeMaster;
  }
  
  public long getPrivilegeMasterValue()
  {
    return this.privilegeMasterValue;
  }
  
  public void setPrivilegeMasterValue(long privilegeMasterValue)
  {
    this.privilegeMasterValue = privilegeMasterValue;
  }
  
  public String getPrivilegeAccess()
  {
    return this.privilegeAccess;
  }
  
  public void setPrivilegeAccess(String privilegeAccess)
  {
    this.privilegeAccess = privilegeAccess;
  }
  
  public long getPrivilegeAccessValue()
  {
    return this.privilegeAccessValue;
  }
  
  public void setPrivilegeAccessValue(long privilegeAccessValue)
  {
    this.privilegeAccessValue = privilegeAccessValue;
  }
  
  public String toString()
  {
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "unitId:" + this.unitId + "\t" + "unitNid:" + this.unitNid + "\t" + "privilegeMaster:" + this.privilegeMaster + "\t" + "privilegeMasterValue:" + this.privilegeMasterValue + "\t" + "privilegeAccess:" + this.privilegeAccess + "\t" + "privilegeAccessValue:" + this.privilegeAccessValue;
  }
}
