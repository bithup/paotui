package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysGridColumnsDaoR;
import com.xgh.mng.dao.write.ISysGridColumnsDaoW;
import com.xgh.mng.entity.SysGridColumns;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysGridColumnsDao")
public class SysGridColumnsDaoImpl
  implements ISysGridColumnsDao
{
  @Autowired
  protected ISysGridColumnsDaoR sysGridColumnsDaoR;
  @Autowired
  protected ISysGridColumnsDaoW sysGridColumnsDaoW;
  
  public int add(SysGridColumns sysGridColumns)
  {
    return this.sysGridColumnsDaoW.add(sysGridColumns);
  }
  
  public int update(SysGridColumns sysGridColumns)
  {
    return this.sysGridColumnsDaoW.update(sysGridColumns);
  }
  
  public int deleteById(long id)
  {
    return this.sysGridColumnsDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysGridColumnsDaoW.deleteByNid(nid);
  }
  
  public int deleteByIds(List<Long> ids)
  {
    return this.sysGridColumnsDaoW.deleteByIds(ids);
  }
  
  public SysGridColumns get(long id)
  {
    return this.sysGridColumnsDaoR.get(id);
  }
  
  public SysGridColumns getByNid(long nid)
  {
    return this.sysGridColumnsDaoR.getByNid(nid);
  }
  
  public List<SysGridColumns> getList(Map<String, Object> map)
  {
    return this.sysGridColumnsDaoR.getList(map);
  }
  
  public List<SysGridColumns> getListPage(Map<String, Object> map)
  {
    return this.sysGridColumnsDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysGridColumnsDaoR.getRows(map);
  }
  
  public int deleteByJoinType(Map<String, Object> map)
  {
    return this.sysGridColumnsDaoW.deleteByJoinType(map);
  }
  
  public int addBatch(List<SysGridColumns> list)
  {
    return this.sysGridColumnsDaoW.addBatch(list);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysGridColumnsDaoR.getListMap(map);
  }
  
  public List<SysGridColumns> getListByJoinType(Map<String, Object> map)
  {
    return this.sysGridColumnsDaoR.getListByJoinType(map);
  }
}
