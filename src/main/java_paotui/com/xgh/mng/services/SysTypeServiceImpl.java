package com.xgh.mng.services;

import com.xgh.mng.dao.ISysTypeDao;
import com.xgh.mng.entity.SysType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysTypeService")
public class SysTypeServiceImpl
  implements ISysTypeService
{
  @Autowired
  protected ISysTypeDao sysTypeDao;
  
  public int add(SysType sysType)
  {
    return this.sysTypeDao.add(sysType);
  }
  
  public int delete(long id)
  {
    return this.sysTypeDao.deleteById(id);
  }
  
  public int deleteByIds(List<Long> ids)
  {
    return this.sysTypeDao.deleteByIds(ids);
  }
  
  public int update(SysType sysType)
  {
    return this.sysTypeDao.update(sysType);
  }
  
  public SysType get(long id)
  {
    return this.sysTypeDao.get(id);
  }
  
  public List<SysType> getList(Map<String, Object> map)
  {
    List<SysType> list = this.sysTypeDao.getList(map);
    


    return list;
  }
  
  public List<SysType> getListPage(Map<String, Object> map)
  {
    return this.sysTypeDao.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysTypeDao.getRows(map);
  }
  
  public List<Map<String, Object>> getChildTreeData(String type, Long pid)
  {
    Map<String, Object> map = new HashMap();
    map.put("type", type);
    map.put("parentId", pid);
    List<Map<String, Object>> list = this.sysTypeDao.getTreeData(map);
    
    return list != null ? list : new ArrayList();
  }
  
  public List<Map<String, Object>> getTreeData(String type)
  {
    return getChildTreeData(type, null);
  }
  
  public Map<String, Object> getGridData(String type, long pid)
  {
    Map<String, Object> gridData = new HashMap();
    
    Map<String, Object> map = new HashMap();
    map.put("type", type);
    map.put("parentId", Long.valueOf(pid));
    List<SysType> dataList = this.sysTypeDao.getList(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Integer.valueOf(dataList.size()));
    return gridData;
  }
  
  public void save(String type, long pid) {}
  
  public boolean isHasSameCode(String op, SysType sysType)
  {
    Map<String, Object> map = new HashMap();
    map.put("code", sysType.getCode());
    map.put("type", sysType.getType());
    if (op.equals("modify")) {
      map.put("id", Long.valueOf(sysType.getId()));
    }
    return this.sysTypeDao.getSameCodeRows(map) > 0L;
  }
  
  public boolean isHasChild(SysType sysType)
  {
    return this.sysTypeDao.getChildTypeRows(sysType.getId()) > 0L;
  }
}
