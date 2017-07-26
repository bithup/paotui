package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysCommDaoR;
import com.xgh.mng.dao.write.ISysCommDaoW;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysCommDao")
public class SysCommDaoImpl
  implements ISysCommDao
{
  @Autowired
  protected ISysCommDaoR sysCommDaoR;
  @Autowired
  protected ISysCommDaoW sysCommDaoW;
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysCommDaoR.getListMap(map);
  }
  
  public int insert(Map<String, Object> map)
  {
    return this.sysCommDaoW.insert(map);
  }
  
  public int update(Map<String, Object> map)
  {
    return this.sysCommDaoW.update(map);
  }
  
  public int delete(Map<String, Object> map)
  {
    return this.sysCommDaoW.delete(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysCommDaoR.getRows(map);
  }
}
