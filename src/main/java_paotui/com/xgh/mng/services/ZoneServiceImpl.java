package com.xgh.mng.services;

import com.xgh.mng.dao.IZoneDao;
import com.xgh.mng.entity.Zone;
import com.xgh.util.PinyinUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("zoneService")
public class ZoneServiceImpl
  implements IZoneService
{
  @Autowired
  protected IZoneDao zoneDao;
  
  public int add(Zone zone)
  {
    return this.zoneDao.add(zone);
  }
  
  public int delete(long id)
  {
    return this.zoneDao.deleteById(id);
  }
  
  public int update(Zone zone)
  {
    return this.zoneDao.update(zone);
  }
  
  public Zone get(long id)
  {
    return this.zoneDao.get(id);
  }
  
  public List<Map<String, Object>> getTreeList(long pid, String zonecode, long unitId)
  {
    Map<String, Object> params = new HashMap();
    params.put("pid", Long.valueOf(pid));
    params.put("code", zonecode);
    params.put("unitId", Long.valueOf(unitId));
    
    List<Map<String, Object>> list = this.zoneDao.getTreeList(params);
    if (list == null) {
      list = new ArrayList();
    }
    return list;
  }
  
  public Map<String, Object> getGridData(HttpServletRequest request, long pid, String zonecode, long unitId)
  {
    Map<String, Object> params = new HashMap();
    params.put("pid", Long.valueOf(pid));
    params.put("code", zonecode);
    params.put("unitId", Long.valueOf(unitId));
    List<Zone> list = this.zoneDao.getGridList(params);
    if (list == null) {
      list = new ArrayList();
    }
    Map<String, Object> gridData = new HashMap();
    gridData.put("Rows", list);
    gridData.put("Total", Integer.valueOf(list.size()));
    return gridData;
  }
  
  public void save(String op, Zone zone)
  {
    if (op.equals("location"))
    {
      Zone zone2 = this.zoneDao.get(zone.getId());
      zone2.setLatitude(zone.getLatitude());
      zone2.setLongitude(zone.getLongitude());
      zone2.setLocation(zone.getLocation());
      this.zoneDao.update(zone2);
    }
    else if (op.equals("add"))
    {
      String maxCode = this.zoneDao.getMaxCodeByPid(zone.getPid());
      
      Zone pZone = this.zoneDao.get(Long.valueOf(zone.getPid()).longValue());
      String currenCode = "";
      if (maxCode == null) {
        currenCode = pZone.getCode() + "001";
      } else {
        currenCode = Long.valueOf(maxCode).longValue() + 1L + "";
      }
      pZone.setIsLast("0");
      this.zoneDao.update(pZone);
      zone.setSpellName(PinyinUtil.getPinYin(zone.getName()));
      zone.setFirSpellName(PinyinUtil.getPinYinHeadChar(zone.getName()));
      zone.setCode(currenCode);
      zone.setPcode(pZone.getCode());
      zone.setIsLast("1");
      zone.setLevel(Integer.parseInt(pZone.getLevel()) + 1 + "");
      this.zoneDao.add(zone);
      

      zone.setPreFix(pZone.getPreFix() + "_" + zone.getId());
      this.zoneDao.update(zone);
    }
    else
    {
      Zone zone2 = this.zoneDao.get(zone.getId());
      zone2.setName(zone.getName());
      zone.setSpellName(PinyinUtil.getPinYin(zone.getName()));
      zone.setFirSpellName(PinyinUtil.getPinYinHeadChar(zone.getName()));
      this.zoneDao.update(zone2);
    }
  }
  
  public String getCurrentName(String prefix)
  {
    String[] prefix_array = prefix.split("_");
    Map<String, Object> map = new HashMap();
    map.put("list", Arrays.asList(prefix_array));
    return this.zoneDao.getCurrentName(map);
  }
  
  public boolean isHasChild(String pid)
  {
    return this.zoneDao.getChildRowsByPid(pid) > 0L;
  }
  
  public Map<String, Object> getAll(HttpServletRequest request, long unid)
  {
    Map<String, Object> paraMap = new HashMap();
    Map<String, Object> resultMap = new HashMap();
    String keyWord = request.getParameter("keyword");
    if (StringUtils.isNotBlank(keyWord)) {
      keyWord = "%" + keyWord + "%";
    }
    String page = request.getParameter("page");
    String pagesize = request.getParameter("rows");
    paraMap.put("page", Integer.valueOf(Integer.parseInt(page)));
    paraMap.put("pagesize", Integer.valueOf(Integer.parseInt(pagesize)));
    paraMap.put("keyWord", keyWord);
    paraMap.put("unid", Long.valueOf(unid));
    List<Map<String, Object>> resultList = this.zoneDao.getAll(paraMap);
    if (resultList == null) {
      resultList = new ArrayList();
    }
    resultMap.put("rows", resultList);
    resultMap.put("total", this.zoneDao.getAllSize(paraMap));
    return resultMap;
  }
  
  public Zone getZoneByCode(String zoneCode)
  {
    return this.zoneDao.getZoneByCode(zoneCode);
  }
}
