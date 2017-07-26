package com.xgh.mng.services;

import com.xgh.mng.entity.SysPrivilegeList;
import java.util.Map;

public abstract interface ISysPrivilegeListService
{
  public abstract int add(SysPrivilegeList paramSysPrivilegeList);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysPrivilegeList paramSysPrivilegeList);
  
  public abstract SysPrivilegeList get(long paramLong);
  
  public abstract SysPrivilegeList getSysPrivilegeListByMaster(Map<String, Object> paramMap);
}
