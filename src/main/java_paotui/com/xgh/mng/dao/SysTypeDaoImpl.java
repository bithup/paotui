package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysTypeDaoR;
import com.xgh.mng.dao.write.ISysTypeDaoW;
import com.xgh.mng.entity.SysType;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysTypeDao")
public class SysTypeDaoImpl
  implements ISysTypeDao
{
  @Autowired
  protected ISysTypeDaoR sysTypeDaoR;
  @Autowired
  protected ISysTypeDaoW sysTypeDaoW;
  
  public int add(SysType sysType)
  {
    return this.sysTypeDaoW.add(sysType);
  }
  
  public int update(SysType sysType)
  {
    return this.sysTypeDaoW.update(sysType);
  }
  
  public int deleteById(long id)
  {
    return this.sysTypeDaoW.deleteById(id);
  }
  
  public int deleteByIds(List<Long> ids)
  {
    return this.sysTypeDaoW.deleteByIds(ids);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysTypeDaoW.deleteByNid(nid);
  }
  
  public SysType get(long id)
  {
    return this.sysTypeDaoR.get(id);
  }
  
  public SysType getByNid(long nid)
  {
    return this.sysTypeDaoR.getByNid(nid);
  }
  
  public List<SysType> getList(Map<String, Object> map)
  {
    return this.sysTypeDaoR.getList(map);
  }
  
  public List<SysType> getListPage(Map<String, Object> map)
  {
    return this.sysTypeDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysTypeDaoR.getRows(map);
  }
  
  public int addBatch(List<SysType> list)
  {
    return this.sysTypeDaoW.addBatch(list);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysTypeDaoR.getListMap(map);
  }
  
  public List<Map<String, Object>> getTreeData(Map<String, Object> map)
  {
    return this.sysTypeDaoR.getTreeData(map);
  }
  
  public long getChildTypeRows(long pid)
  {
    return this.sysTypeDaoR.getChildTypeRows(pid);
  }
  
  public long getSameCodeRows(Map<String, Object> map)
  {
    return this.sysTypeDaoR.getSameCodeRows(map);
  }
  
  public SysType getSysTypeByCode(Map<String, Object> map)
  {
    return this.sysTypeDaoR.getSysTypeByCode(map);
  }
}
