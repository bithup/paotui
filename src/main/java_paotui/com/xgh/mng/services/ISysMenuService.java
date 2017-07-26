package com.xgh.mng.services;

import com.xgh.mng.entity.SysMenu;
import com.xgh.mng.entity.SysUser;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysMenuService
{
  public abstract int add(SysMenu paramSysMenu);
  
  public abstract int delete(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int update(SysMenu paramSysMenu);
  
  public abstract int save(HttpServletRequest paramHttpServletRequest, SysMenu paramSysMenu);
  
  public abstract SysMenu get(long paramLong);
  
  public abstract List<SysMenu> getList(SysMenu paramSysMenu);
  
  public abstract List<SysMenu> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getUserMenuTreeData(long paramLong, SysUser paramSysUser);
  
  public abstract List<Map<String, Object>> getSysMenuTreeData(SysUser paramSysUser);
  
  public abstract Map<String, Object> getGridData(Map<String, Object> paramMap);
  
  public abstract boolean isHasChildMenu(long paramLong);
  
  public abstract List<SysMenu> getParentMenus(long paramLong);
  
  public abstract List<Map<String, Object>> getTreeData();
  
  public abstract List<Map<String, Object>> getButtonMenuTreeData();
  
  public abstract List<Map<String, Object>> getGridMenuTreeData();
  
  public abstract List<Map<String, Object>> getQueryMenuTreeData();
  
  public abstract List<Map<String, Object>> getValidationMenuTreeData();
  
  public abstract SysMenu getSysMenuByRequest(HttpServletRequest paramHttpServletRequest);
}
