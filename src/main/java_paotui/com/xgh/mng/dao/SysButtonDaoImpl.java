package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysButtonDaoR;
import com.xgh.mng.dao.write.ISysButtonDaoW;
import com.xgh.mng.entity.SysButton;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysButtonDao")
public class SysButtonDaoImpl
  implements ISysButtonDao
{
  @Autowired
  protected ISysButtonDaoR sysButtonDaoR;
  @Autowired
  protected ISysButtonDaoW sysButtonDaoW;
  
  public int add(SysButton sysButton)
  {
    return this.sysButtonDaoW.add(sysButton);
  }
  
  public int update(SysButton sysButton)
  {
    return this.sysButtonDaoW.update(sysButton);
  }
  
  public int deleteById(long id)
  {
    return this.sysButtonDaoW.deleteById(id);
  }
  
  public int deleteByIds(List<Long> ids)
  {
    return this.sysButtonDaoW.deleteByIds(ids);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysButtonDaoW.deleteByNid(nid);
  }
  
  public SysButton get(long id)
  {
    return this.sysButtonDaoR.get(id);
  }
  
  public SysButton getByNid(long nid)
  {
    return this.sysButtonDaoR.getByNid(nid);
  }
  
  public List<SysButton> getList(SysButton sysButton)
  {
    return this.sysButtonDaoR.getList(sysButton);
  }
  
  public List<SysButton> getListPage(Map<String, Object> map)
  {
    return this.sysButtonDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysButtonDaoR.getRows(map);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysButtonDaoR.getListMap(map);
  }
  
  public int deleteByMenuId(Map<String, Object> map)
  {
    return this.sysButtonDaoW.deleteByMenuId(map);
  }
  
  public int addBatch(List<SysButton> list)
  {
    return this.sysButtonDaoW.addBatch(list);
  }
  
  public List<SysButton> getUserButtons(Map<String, Object> map)
  {
    return this.sysButtonDaoR.getUserButtons(map);
  }
  
  public List<SysButton> getButtonByMenuId(long menuId)
  {
    return this.sysButtonDaoR.getButtonByMenuId(menuId);
  }
  
  public List<Long> getButtonIdsByMenuIds(Map<String, Object> map)
  {
    return this.sysButtonDaoR.getButtonIdsByMenuIds(map);
  }
}
