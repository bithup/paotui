package com.xgh.mng.dao;

import com.xgh.mng.dao.read.IZoneDaoR;
import com.xgh.mng.dao.write.IZoneDaoW;
import com.xgh.mng.entity.Zone;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("zoneDao")
public class ZoneDaoImpl
  implements IZoneDao
{
  @Autowired
  protected IZoneDaoR zoneDaoR;
  @Autowired
  protected IZoneDaoW zoneDaoW;
  
  public int add(Zone zone)
  {
    return this.zoneDaoW.add(zone);
  }
  
  public int update(Zone zone)
  {
    return this.zoneDaoW.update(zone);
  }
  
  public int deleteById(long id)
  {
    return this.zoneDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.zoneDaoW.deleteByNid(nid);
  }
  
  public Zone get(long id)
  {
    return this.zoneDaoR.get(id);
  }
  
  public Zone getByNid(long nid)
  {
    return this.zoneDaoR.getByNid(nid);
  }
  
  public List<Zone> getList(Zone zone)
  {
    return this.zoneDaoR.getList(zone);
  }
  
  public List<Zone> getListPage(Map<String, Object> map)
  {
    return this.zoneDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.zoneDaoR.getRows(map);
  }
  
  public Zone getZoneByCode(String code)
  {
    return this.zoneDaoR.getZoneByCode(code);
  }
  
  public List<Map<String, Object>> getTreeList(Map<String, Object> map)
  {
    return this.zoneDaoR.getTreeList(map);
  }
  
  public List<Zone> getGridList(Map<String, Object> map)
  {
    return this.zoneDaoR.getGridList(map);
  }
  
  public String getCurrentName(Map<String, Object> map)
  {
    return this.zoneDaoR.getCurrentName(map);
  }
  
  public String getMaxCodeByPid(String pid)
  {
    return this.zoneDaoR.getMaxCodeByPid(pid);
  }
  
  public long getChildRowsByPid(String pid)
  {
    return this.zoneDaoR.getChildRowsByPid(pid);
  }
  
  public Zone getZoneCodeByName(String zoneName)
  {
    return this.zoneDaoR.getZoneCodeByName(zoneName);
  }
  
  public List<Map<String, Object>> getAll(Map<String, Object> map)
  {
    return this.zoneDaoR.getAll(map);
  }
  
  public List<Map<String, Object>> getAllSize(Map<String, Object> map)
  {
    return this.zoneDaoR.getAllSize(map);
  }
}
