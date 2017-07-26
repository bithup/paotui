package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysParamDaoR;
import com.xgh.mng.dao.write.ISysParamDaoW;
import com.xgh.mng.entity.SysParam;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysParamDao")
public class SysParamDaoImpl
  implements ISysParamDao
{
  @Autowired
  protected ISysParamDaoR sysParamDaoR;
  @Autowired
  protected ISysParamDaoW sysParamDaoW;
  
  public int add(SysParam sysParam)
  {
    return this.sysParamDaoW.add(sysParam);
  }
  
  public int update(SysParam sysParam)
  {
    return this.sysParamDaoW.update(sysParam);
  }
  
  public int deleteById(long id)
  {
    return this.sysParamDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysParamDaoW.deleteByNid(nid);
  }
  
  public SysParam get(long id)
  {
    return this.sysParamDaoR.get(id);
  }
  
  public SysParam getByNid(long nid)
  {
    return this.sysParamDaoR.getByNid(nid);
  }
  
  public List<SysParam> getList(SysParam sysParam)
  {
    return this.sysParamDaoR.getList(sysParam);
  }
  
  public List<SysParam> getListPage(Map<String, Object> map)
  {
    return this.sysParamDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysParamDaoR.getRows(map);
  }
  
  public int addBatch(List<SysParam> list)
  {
    return this.sysParamDaoW.addBatch(list);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysParamDaoR.getListMap(map);
  }
  
  public long getListRows(Map<String, Object> map)
  {
    return this.sysParamDaoR.getListRows(map);
  }
  
  public long getRowsByTypeIdAndCode(Map<String, Object> map)
  {
    return this.sysParamDaoR.getRowsByTypeIdAndCode(map);
  }
  
  public SysParam getByTypeIdAndCode(Map<String, Object> map)
  {
    return this.sysParamDaoR.getByTypeIdAndCode(map);
  }
  
  public List<SysParam> getListByTypeId(Map<String, Object> map)
  {
    return this.sysParamDaoR.getListByTypeId(map);
  }
}
