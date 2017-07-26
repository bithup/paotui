package com.xgh.mng.dao;

import com.xgh.mng.entity.Zone;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface IZoneDao
{
  public abstract int add(Zone paramZone);
  
  public abstract int update(Zone paramZone);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract Zone get(long paramLong);
  
  public abstract Zone getByNid(long paramLong);
  
  public abstract Zone getZoneByCode(String paramString);
  
  public abstract List<Map<String, Object>> getTreeList(Map<String, Object> paramMap);
  
  public abstract List<Zone> getGridList(Map<String, Object> paramMap);
  
  public abstract String getCurrentName(Map<String, Object> paramMap);
  
  public abstract String getMaxCodeByPid(String paramString);
  
  public abstract long getChildRowsByPid(String paramString);
  
  public abstract Zone getZoneCodeByName(String paramString);
  
  public abstract List<Map<String, Object>> getAll(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getAllSize(Map<String, Object> paramMap);
}
