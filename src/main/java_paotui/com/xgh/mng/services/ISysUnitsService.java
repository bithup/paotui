package com.xgh.mng.services;

import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.SysUser;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysUnitsService
{
  public abstract int add(SysUnits paramSysUnits);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysUnits paramSysUnits);
  
  public abstract SysUnits get(long paramLong);
  
  public abstract Map<String, Object> getGridData(HttpServletRequest paramHttpServletRequest);
  
  public abstract List<Map<String, Object>> getUnitTreeData(long paramLong);
  
  public abstract List<Map<String, Object>> getUnitTreeData(HttpServletRequest paramHttpServletRequest);
  
  public abstract List<Map<String, Object>> getUnitTreeDataByProvinceId(long paramLong);
  
  public abstract Map<String, Object> save(HttpServletRequest paramHttpServletRequest, SysUnits paramSysUnits, String paramString);
  
  public abstract void checkUnit(HttpServletRequest paramHttpServletRequest, SysUnits paramSysUnits, SysUser paramSysUser);
  
  public abstract List<Map<String, Object>> getChildZoneList(HttpServletRequest paramHttpServletRequest, String paramString);
  
  public abstract List<Map<String, Object>> getFirstZoneList();
  
  public abstract List<Map<String, Object>> getChildZoneListByPid(long paramLong);
  
  public abstract String getZonePrefixByCode(String paramString);
  
  public abstract boolean isHasSameZoneCode(String paramString, SysUnits paramSysUnits);
  
  public abstract boolean isHasSame(String paramString, SysUnits paramSysUnits, Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getUnitFileTypeList();
  
  public abstract List<Map<String, Object>> getZoneListByPCode(String paramString);
  
  public abstract SysUnits getUnitByZoneCode(String paramString);
  
  public abstract List<Map<String, Object>> getZoneListOnChange(HttpServletRequest paramHttpServletRequest);
  
  public abstract List<Map<String, Object>> getAreaList(Map<String, Object> paramMap);
  
  public abstract long updateUnitUser(SysUnits paramSysUnits);
}
