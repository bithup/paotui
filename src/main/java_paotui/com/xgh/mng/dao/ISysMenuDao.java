package com.xgh.mng.dao;

import com.xgh.mng.entity.SysMenu;
import java.util.List;
import java.util.Map;

public abstract interface ISysMenuDao
{
  public abstract int add(SysMenu paramSysMenu);
  
  public abstract int update(SysMenu paramSysMenu);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysMenu get(long paramLong);
  
  public abstract SysMenu getByNid(long paramLong);
  
  public abstract List<SysMenu> getList(SysMenu paramSysMenu);
  
  public abstract List<SysMenu> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract long getSameMenuCount(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getTreeData(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getUserMenuTreeData(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getSysMenuTreeData(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getChildMenuRows(long paramLong);
  
  public abstract List<SysMenu> getParentMenus(long paramLong);
  
  public abstract List<SysMenu> getMenuByUrl(Map<String, Object> paramMap);
  
  public abstract List<Long> getMenuIdByParentIds(Map<String, Object> paramMap);
}
