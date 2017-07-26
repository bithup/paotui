package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysUnits;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysUnitsDaoR
{
  public abstract SysUnits get(long paramLong);
  
  public abstract SysUnits getByNid(long paramLong);
  
  public abstract List<SysUnits> getList(SysUnits paramSysUnits);
  
  public abstract List<SysUnits> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getUnitTreeData(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getUnitTreeDataByProvinceId(Map<String, Object> paramMap);
  
  public abstract List<SysUnits> getUnitListMap(Map<String, Object> paramMap);
  
  public abstract long getUnitListRows(Map<String, Object> paramMap);
  
  public abstract List<SysUnits> getUnCheckListMap(Map<String, Object> paramMap);
  
  public abstract long getUnCheckListRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getPassUnitList();
  
  public abstract List<Map<String, Object>> getChildZoneListByPcode(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getChildZoneListByPid(long paramLong);
  
  public abstract String getZonePrefixByCode(String paramString);
  
  public abstract long getUnitRowsByZoneCode(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getUnitRowsBySame(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getRegisteredZoneList(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getAllRegisteredZoneList(Map<String, Object> paramMap);
  
  public abstract SysUnits getUnitByZoneCode(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getProvincialAgentZoneList(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getMunicipalAgentZoneList(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getCountyAgentZoneList(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getAreaList(Map<String, Object> paramMap);
}
