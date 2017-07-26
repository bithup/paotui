package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysPrivilegeList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysPrivilegeListDaoR
{
  public abstract SysPrivilegeList get(long paramLong);
  
  public abstract SysPrivilegeList getByNid(long paramLong);
  
  public abstract List<SysPrivilegeList> getList(SysPrivilegeList paramSysPrivilegeList);
  
  public abstract List<SysPrivilegeList> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Long> getRoleIdsByUserId(long paramLong);
  
  public abstract long getSysRoleIdByUnitId(long paramLong);
  
  public abstract SysPrivilegeList getSysPrivilegeListByMaster(Map<String, Object> paramMap);
}
