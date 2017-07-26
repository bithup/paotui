package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysUnitsDaoR;
import com.xgh.mng.dao.write.ISysUnitsDaoW;
import com.xgh.mng.entity.SysUnits;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUnitsDao")
public class SysUnitsDaoImpl
  implements ISysUnitsDao
{
  @Autowired
  protected ISysUnitsDaoR sysUnitsDaoR;
  @Autowired
  protected ISysUnitsDaoW sysUnitsDaoW;
  
  public int add(SysUnits sysUnits)
  {
    return this.sysUnitsDaoW.add(sysUnits);
  }
  
  public int update(SysUnits sysUnits)
  {
    return this.sysUnitsDaoW.update(sysUnits);
  }
  
  public int deleteById(long id)
  {
    return this.sysUnitsDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysUnitsDaoW.deleteByNid(nid);
  }
  
  public SysUnits get(long id)
  {
    return this.sysUnitsDaoR.get(id);
  }
  
  public SysUnits getByNid(long nid)
  {
    return this.sysUnitsDaoR.getByNid(nid);
  }
  
  public List<SysUnits> getList(SysUnits sysUnits)
  {
    return this.sysUnitsDaoR.getList(sysUnits);
  }
  
  public List<SysUnits> getListPage(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getRows(map);
  }
  
  public List<Map<String, Object>> getUnitTreeData(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getUnitTreeData(map);
  }
  
  public List<Map<String, Object>> getUnitTreeDataByProvinceId(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getUnitTreeDataByProvinceId(map);
  }
  
  public List<SysUnits> getUnitListMap(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getUnitListMap(map);
  }
  
  public long getUnitListRows(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getUnitListRows(map);
  }
  
  public List<SysUnits> getUnCheckListMap(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getUnCheckListMap(map);
  }
  
  public long getUnCheckListRows(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getUnitListRows(map);
  }
  
  public List<Map<String, Object>> getPassUnitList()
  {
    return this.sysUnitsDaoR.getPassUnitList();
  }
  
  public List<Map<String, Object>> getChildZoneListByPcode(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getChildZoneListByPcode(map);
  }
  
  public List<Map<String, Object>> getChildZoneListByPid(long pid)
  {
    return this.sysUnitsDaoR.getChildZoneListByPid(pid);
  }
  
  public String getZonePrefixByCode(String code)
  {
    return this.sysUnitsDaoR.getZonePrefixByCode(code);
  }
  
  public long getUnitRowsByZoneCode(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getUnitRowsByZoneCode(map);
  }
  
  public List<Map<String, Object>> getUnitRowsBySame(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getUnitRowsBySame(map);
  }
  
  public List<Map<String, Object>> getRegisteredZoneList(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getRegisteredZoneList(map);
  }
  
  public List<Map<String, Object>> getAllRegisteredZoneList(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getAllRegisteredZoneList(map);
  }
  
  public SysUnits getUnitByZoneCode(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getUnitByZoneCode(map);
  }
  
  public List<Map<String, Object>> getProvincialAgentZoneList(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getProvincialAgentZoneList(map);
  }
  
  public List<Map<String, Object>> getMunicipalAgentZoneList(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getMunicipalAgentZoneList(map);
  }
  
  public List<Map<String, Object>> getCountyAgentZoneList(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getCountyAgentZoneList(map);
  }
  
  public List<Map<String, Object>> getAreaList(Map<String, Object> map)
  {
    return this.sysUnitsDaoR.getAreaList(map);
  }
}
