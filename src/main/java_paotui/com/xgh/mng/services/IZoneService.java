package com.xgh.mng.services;

import com.xgh.mng.entity.Zone;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IZoneService
{
  public abstract int add(Zone paramZone);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(Zone paramZone);
  
  public abstract Zone get(long paramLong);
  
  public abstract List<Map<String, Object>> getTreeList(long paramLong1, String paramString, long paramLong2);
  
  public abstract Map<String, Object> getGridData(HttpServletRequest paramHttpServletRequest, long paramLong1, String paramString, long paramLong2);
  
  public abstract void save(String paramString, Zone paramZone);
  
  public abstract String getCurrentName(String paramString);
  
  public abstract boolean isHasChild(String paramString);
  
  public abstract Map<String, Object> getAll(HttpServletRequest paramHttpServletRequest, long paramLong);
  
  public abstract Zone getZoneByCode(String paramString);
}
