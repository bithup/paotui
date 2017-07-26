package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysDictMainDaoR;
import com.xgh.mng.dao.write.ISysDictMainDaoW;
import com.xgh.mng.entity.SysDictMain;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDictMainDao")
public class SysDictMainDaoImpl
  implements ISysDictMainDao
{
  @Autowired
  protected ISysDictMainDaoR sysDictMainDaoR;
  @Autowired
  protected ISysDictMainDaoW sysDictMainDaoW;
  
  public int add(SysDictMain sysDictMain)
  {
    return this.sysDictMainDaoW.add(sysDictMain);
  }
  
  public int update(SysDictMain sysDictMain)
  {
    return this.sysDictMainDaoW.update(sysDictMain);
  }
  
  public int deleteById(long id)
  {
    return this.sysDictMainDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysDictMainDaoW.deleteByNid(nid);
  }
  
  public SysDictMain get(long id)
  {
    return this.sysDictMainDaoR.get(id);
  }
  
  public SysDictMain getByNid(long nid)
  {
    return this.sysDictMainDaoR.getByNid(nid);
  }
  
  public List<SysDictMain> getList(SysDictMain sysDictMain)
  {
    return this.sysDictMainDaoR.getList(sysDictMain);
  }
  
  public List<SysDictMain> getListPage(Map<String, Object> map)
  {
    return this.sysDictMainDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysDictMainDaoR.getRows(map);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysDictMainDaoR.getListMap(map);
  }
  
  public long getListRows(Map<String, Object> map)
  {
    return this.sysDictMainDaoR.getListRows(map);
  }
  
  public long getRowsByDictCode(Map<String, Object> map)
  {
    return this.sysDictMainDaoR.getRowsByDictCode(map);
  }
  
  public long getDictDetailRowsByMainId(long mainId)
  {
    return this.sysDictMainDaoR.getDictDetailRowsByMainId(mainId);
  }
  
  public List<Map<String, Object>> getDictMainTreeList(Map<String, Object> map)
  {
    return this.sysDictMainDaoR.getDictMainTreeList(map);
  }
  
  public List<SysDictMain> getAllList()
  {
    return this.sysDictMainDaoR.getAllList();
  }
  
  public SysDictMain getSysDictMainByCode(String code)
  {
    return this.sysDictMainDaoR.getSysDictMainByCode(code);
  }
  
  public List<Map<String, Object>> getSysDictMainFirstTreeData()
  {
    return this.sysDictMainDaoR.getSysDictMainFirstTreeData();
  }
  
  public long getSysDictMainChildRows(long parentId)
  {
    return this.sysDictMainDaoR.getSysDictMainChildRows(parentId);
  }
}
