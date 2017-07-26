package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysParamTypeDaoR;
import com.xgh.mng.dao.write.ISysParamTypeDaoW;
import com.xgh.mng.entity.SysParamType;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysParamTypeDao")
public class SysParamTypeDaoImpl
  implements ISysParamTypeDao
{
  @Autowired
  protected ISysParamTypeDaoR sysParamTypeDaoR;
  @Autowired
  protected ISysParamTypeDaoW sysParamTypeDaoW;
  
  public int add(SysParamType sysParamType)
  {
    return this.sysParamTypeDaoW.add(sysParamType);
  }
  
  public int update(SysParamType sysParamType)
  {
    return this.sysParamTypeDaoW.update(sysParamType);
  }
  
  public int deleteById(long id)
  {
    return this.sysParamTypeDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysParamTypeDaoW.deleteByNid(nid);
  }
  
  public SysParamType get(long id)
  {
    return this.sysParamTypeDaoR.get(id);
  }
  
  public SysParamType getByNid(long nid)
  {
    return this.sysParamTypeDaoR.getByNid(nid);
  }
  
  public List<SysParamType> getList(SysParamType sysParamType)
  {
    return this.sysParamTypeDaoR.getList(sysParamType);
  }
  
  public List<SysParamType> getListPage(Map<String, Object> map)
  {
    return this.sysParamTypeDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysParamTypeDaoR.getRows(map);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysParamTypeDaoR.getListMap(map);
  }
  
  public long getListRows(Map<String, Object> map)
  {
    return this.sysParamTypeDaoR.getListRows(map);
  }
  
  public long getRowsByCode(Map<String, Object> map)
  {
    return this.sysParamTypeDaoR.getRowsByCode(map);
  }
  
  public List<Map<String, Object>> getTreeList()
  {
    return this.sysParamTypeDaoR.getTreeList();
  }
  
  public SysParamType getParamTypeByCode(String code)
  {
    return this.sysParamTypeDaoR.getParamTypeByCode(code);
  }
}
