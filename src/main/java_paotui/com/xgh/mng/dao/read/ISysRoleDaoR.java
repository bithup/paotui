package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysRole;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysRoleDaoR
{
  public abstract SysRole get(long paramLong);
  
  public abstract SysRole getByNid(long paramLong);
  
  public abstract List<SysRole> getList(SysRole paramSysRole);
  
  public abstract List<SysRole> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
  
  public abstract long getSameCodeRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getMenuRoleTreeList(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getUserRoleTreeList(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getRoleGridMenuList(Map<String, Object> paramMap);
  
  public abstract long getRoleGridMenuRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getRoleButtonMenuList(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getRoleUserList(Map<String, Object> paramMap);
  
  public abstract long getRoleUserRows(Map<String, Object> paramMap);
  
  public abstract List<SysRole> getAgentOrgRoleList(Map<String, Object> paramMap);
  
  public abstract SysRole getAgentOrgRole(Map<String, Object> paramMap);
}
