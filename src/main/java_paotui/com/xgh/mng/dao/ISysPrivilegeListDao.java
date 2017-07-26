package com.xgh.mng.dao;

import com.xgh.mng.entity.SysPrivilegeList;
import java.util.List;
import java.util.Map;

public abstract interface ISysPrivilegeListDao
{
  public abstract int add(SysPrivilegeList paramSysPrivilegeList);
  
  public abstract int update(SysPrivilegeList paramSysPrivilegeList);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysPrivilegeList get(long paramLong);
  
  public abstract SysPrivilegeList getByNid(long paramLong);
  
  public abstract List<SysPrivilegeList> getList(SysPrivilegeList paramSysPrivilegeList);
  
  public abstract List<SysPrivilegeList> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysPrivilegeList> paramList);
  
  public abstract int deleteByAccessList(Map<String, Object> paramMap);
  
  public abstract int deleteByMasterList(Map<String, Object> paramMap);
  
  public abstract List<Long> getRoleIdsByUserId(long paramLong);
  
  public abstract long getSysRoleIdByUnitId(long paramLong);
  
  public abstract SysPrivilegeList getSysPrivilegeListByMaster(Map<String, Object> paramMap);
}
