package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysQueryItemDaoR;
import com.xgh.mng.dao.write.ISysQueryItemDaoW;
import com.xgh.mng.entity.SysQueryItem;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysQueryItemDao")
public class SysQueryItemDaoImpl
  implements ISysQueryItemDao
{
  @Autowired
  protected ISysQueryItemDaoR sysQueryItemDaoR;
  @Autowired
  protected ISysQueryItemDaoW sysQueryItemDaoW;
  
  public int add(SysQueryItem sysQueryItem)
  {
    return this.sysQueryItemDaoW.add(sysQueryItem);
  }
  
  public int update(SysQueryItem sysQueryItem)
  {
    return this.sysQueryItemDaoW.update(sysQueryItem);
  }
  
  public int deleteById(long id)
  {
    return this.sysQueryItemDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysQueryItemDaoW.deleteByNid(nid);
  }
  
  public SysQueryItem get(long id)
  {
    return this.sysQueryItemDaoR.get(id);
  }
  
  public SysQueryItem getByNid(long nid)
  {
    return this.sysQueryItemDaoR.getByNid(nid);
  }
  
  public List<SysQueryItem> getList(Map<String, Object> map)
  {
    return this.sysQueryItemDaoR.getList(map);
  }
  
  public List<SysQueryItem> getListPage(Map<String, Object> map)
  {
    return this.sysQueryItemDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysQueryItemDaoR.getRows(map);
  }
  
  public int addBatch(List<SysQueryItem> list)
  {
    return this.sysQueryItemDaoW.addBatch(list);
  }
  
  public int deleteByJoinType(Map<String, Object> map)
  {
    return this.sysQueryItemDaoW.deleteByJoinType(map);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysQueryItemDaoR.getListMap(map);
  }
  
  public List<SysQueryItem> getListByJoinType(Map<String, Object> map)
  {
    return this.sysQueryItemDaoR.getListByJoinType(map);
  }
}
