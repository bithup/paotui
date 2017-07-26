package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysLogDaoR;
import com.xgh.mng.dao.write.ISysLogDaoW;
import com.xgh.mng.entity.SysLog;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysLogDao")
public class SysLogDaoImpl
  implements ISysLogDao
{
  @Autowired
  protected ISysLogDaoR sysLogDaoR;
  @Autowired
  protected ISysLogDaoW sysLogDaoW;
  
  public int add(SysLog sysLog)
  {
    return this.sysLogDaoW.add(sysLog);
  }
  
  public int update(SysLog sysLog)
  {
    return this.sysLogDaoW.update(sysLog);
  }
  
  public int deleteById(long id)
  {
    return this.sysLogDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysLogDaoW.deleteByNid(nid);
  }
  
  public SysLog get(long id)
  {
    return this.sysLogDaoR.get(id);
  }
  
  public SysLog getByNid(long nid)
  {
    return this.sysLogDaoR.getByNid(nid);
  }
  
  public List<SysLog> getList(SysLog sysLog)
  {
    return this.sysLogDaoR.getList(sysLog);
  }
  
  public List<SysLog> getListPage(Map<String, Object> map)
  {
    return this.sysLogDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysLogDaoR.getRows(map);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysLogDaoR.getListMap(map);
  }
  
  public long getListRows(Map<String, Object> map)
  {
    return this.sysLogDaoR.getListRows(map);
  }
}
