package com.xgh.mng.services;

import com.xgh.mng.entity.SysRole;
import com.xgh.mng.entity.SysUser;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

public abstract interface ISysRoleService
{
  public abstract int add(SysRole paramSysRole);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysRole paramSysRole);
  
  public abstract SysRole get(long paramLong);
  
  public abstract int save(SysRole paramSysRole, String paramString);
  
  public abstract Map<String, Object> getGridData(HttpServletRequest paramHttpServletRequest, SysUser paramSysUser, long paramLong);
  
  public abstract boolean isHasSameCode(SysRole paramSysRole, String paramString);
  
  public abstract List<Map<String, Object>> getRoleTreeData(String paramString, HttpServletRequest paramHttpServletRequest);
  
  @Transactional(rollbackFor={Exception.class})
  public abstract void savePrivilege(HttpServletRequest paramHttpServletRequest, long paramLong1, long paramLong2);
  
  @Transactional(rollbackFor={Exception.class})
  public abstract void saveRoleUser(HttpServletRequest paramHttpServletRequest, long paramLong1, long paramLong2);
  
  public abstract void removeRoleUser(HttpServletRequest paramHttpServletRequest);
  
  public abstract SysRole getSysRole(Map<String, Object> paramMap);
  
  public abstract List<SysRole> getAgentOrgRoleList(Map<String, Object> paramMap);
  
  public abstract SysRole getAgentOrgRole(Map<String, Object> paramMap);
}
