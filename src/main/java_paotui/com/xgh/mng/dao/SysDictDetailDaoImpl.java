package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysDictDetailDaoR;
import com.xgh.mng.dao.write.ISysDictDetailDaoW;
import com.xgh.mng.entity.SysDictDetail;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDictDetailDao")
public class SysDictDetailDaoImpl
  implements ISysDictDetailDao
{
  @Autowired
  protected ISysDictDetailDaoR sysDictDetailDaoR;
  @Autowired
  protected ISysDictDetailDaoW sysDictDetailDaoW;
  
  public int add(SysDictDetail sysDictDetail)
  {
    return this.sysDictDetailDaoW.add(sysDictDetail);
  }
  
  public int update(SysDictDetail sysDictDetail)
  {
    return this.sysDictDetailDaoW.update(sysDictDetail);
  }
  
  public int deleteById(long id)
  {
    return this.sysDictDetailDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysDictDetailDaoW.deleteByNid(nid);
  }
  
  public SysDictDetail get(long id)
  {
    return this.sysDictDetailDaoR.get(id);
  }
  
  public SysDictDetail getByNid(long nid)
  {
    return this.sysDictDetailDaoR.getByNid(nid);
  }
  
  public List<SysDictDetail> getList(SysDictDetail sysDictDetail)
  {
    return this.sysDictDetailDaoR.getList(sysDictDetail);
  }
  
  public List<SysDictDetail> getListPage(Map<String, Object> map)
  {
    return this.sysDictDetailDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysDictDetailDaoR.getRows(map);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysDictDetailDaoR.getListMap(map);
  }
  
  public long getListRows(Map<String, Object> map)
  {
    return this.sysDictDetailDaoR.getListRows(map);
  }
  
  public long getRowsByCode(Map<String, Object> map)
  {
    return this.sysDictDetailDaoR.getRowsByCode(map);
  }
  
  public List<SysDictDetail> getAllList()
  {
    return this.sysDictDetailDaoR.getAllList();
  }
  
  public List<SysDictDetail> getDetailListByMainCode(Map<String, Object> map)
  {
    return this.sysDictDetailDaoR.getDetailListByMainCode(map);
  }
  
  public SysDictDetail getDetailByMainCode(Map<String, Object> map)
  {
    return this.sysDictDetailDaoR.getDetailByMainCode(map);
  }
  
  public List<SysDictDetail> getExtendsListMap(Map<String, Object> map)
  {
    return this.sysDictDetailDaoR.getExtendsListMap(map);
  }
  
  public long getExtendsListRows(Map<String, Object> map)
  {
    return this.sysDictDetailDaoR.getExtendsListRows(map);
  }
}
