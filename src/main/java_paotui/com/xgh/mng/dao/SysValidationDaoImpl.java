package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysValidationDaoR;
import com.xgh.mng.dao.write.ISysValidationDaoW;
import com.xgh.mng.entity.SysValidation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysValidationDao")
public class SysValidationDaoImpl
  implements ISysValidationDao
{
  @Autowired
  protected ISysValidationDaoR sysValidationDaoR;
  @Autowired
  protected ISysValidationDaoW sysValidationDaoW;
  
  public int add(SysValidation sysValidation)
  {
    return this.sysValidationDaoW.add(sysValidation);
  }
  
  public int update(SysValidation sysValidation)
  {
    return this.sysValidationDaoW.update(sysValidation);
  }
  
  public int deleteById(long id)
  {
    return this.sysValidationDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysValidationDaoW.deleteByNid(nid);
  }
  
  public SysValidation get(long id)
  {
    return this.sysValidationDaoR.get(id);
  }
  
  public SysValidation getByNid(long nid)
  {
    return this.sysValidationDaoR.getByNid(nid);
  }
  
  public List<SysValidation> getList(Map<String, Object> map)
  {
    return this.sysValidationDaoR.getList(map);
  }
  
  public List<SysValidation> getListPage(Map<String, Object> map)
  {
    return this.sysValidationDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysValidationDaoR.getRows(map);
  }
  
  public int deleteByJoinType(Map<String, Object> map)
  {
    return this.sysValidationDaoW.deleteByJoinType(map);
  }
  
  public int addBatch(List<SysValidation> list)
  {
    return this.sysValidationDaoW.addBatch(list);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysValidationDaoR.getListMap(map);
  }
  
  public List<SysValidation> getListByJoinType(Map<String, Object> map)
  {
    return this.sysValidationDaoR.getListByJoinType(map);
  }
}
