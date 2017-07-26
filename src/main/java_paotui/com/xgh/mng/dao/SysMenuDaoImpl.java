package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysMenuDaoR;
import com.xgh.mng.dao.write.ISysMenuDaoW;
import com.xgh.mng.entity.SysMenu;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysMenuDao")
public class SysMenuDaoImpl
  implements ISysMenuDao
{
  @Autowired
  protected ISysMenuDaoR sysMenuDaoR;
  @Autowired
  protected ISysMenuDaoW sysMenuDaoW;
  
  public int add(SysMenu sysMenu)
  {
    return this.sysMenuDaoW.add(sysMenu);
  }
  
  public int update(SysMenu sysMenu)
  {
    return this.sysMenuDaoW.update(sysMenu);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysMenuDaoW.deleteByNid(nid);
  }
  
  public int deleteById(long id)
  {
    return this.sysMenuDaoW.deleteById(id);
  }
  
  public int deleteByIds(List<Long> ids)
  {
    return this.sysMenuDaoW.deleteByIds(ids);
  }
  
  public SysMenu get(long id)
  {
    return this.sysMenuDaoR.get(id);
  }
  
  public SysMenu getByNid(long nid)
  {
    return this.sysMenuDaoR.getByNid(nid);
  }
  
  public List<SysMenu> getList(SysMenu sysMenu)
  {
    return this.sysMenuDaoR.getList(sysMenu);
  }
  
  public List<SysMenu> getListPage(Map<String, Object> map)
  {
    return this.sysMenuDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysMenuDaoR.getRows(map);
  }
  
  public long getSameMenuCount(Map<String, Object> map)
  {
    return this.sysMenuDaoR.getSameMenuCount(map);
  }
  
  public List<Map<String, Object>> getTreeData(Map<String, Object> map)
  {
    return this.sysMenuDaoR.getTreeData(map);
  }
  
  public List<Map<String, Object>> getUserMenuTreeData(Map<String, Object> map)
  {
    return this.sysMenuDaoR.getUserMenuTreeData(map);
  }
  
  public List<Map<String, Object>> getSysMenuTreeData(Map<String, Object> map)
  {
    return this.sysMenuDaoR.getSysMenuTreeData(map);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysMenuDaoR.getListMap(map);
  }
  
  public long getChildMenuRows(long nid)
  {
    return this.sysMenuDaoR.getChildMenuRows(nid);
  }
  
  public List<SysMenu> getParentMenus(long nid)
  {
    return this.sysMenuDaoR.getParentMenus(nid);
  }
  
  public List<SysMenu> getMenuByUrl(Map<String, Object> map)
  {
    return this.sysMenuDaoR.getMenuByUrl(map);
  }
  
  public List<Long> getMenuIdByParentIds(Map<String, Object> map)
  {
    return this.sysMenuDaoR.getMenuIdByParentIds(map);
  }
}
