package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysRoleDaoR;
import com.xgh.mng.dao.write.ISysRoleDaoW;
import com.xgh.mng.entity.SysRole;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysRoleDao")
public class SysRoleDaoImpl
  implements ISysRoleDao
{
  @Autowired
  protected ISysRoleDaoR sysRoleDaoR;
  @Autowired
  protected ISysRoleDaoW sysRoleDaoW;
  
  public int add(SysRole sysRole)
  {
    return this.sysRoleDaoW.add(sysRole);
  }
  
  public int update(SysRole sysRole)
  {
    return this.sysRoleDaoW.update(sysRole);
  }
  
  public int deleteById(long id)
  {
    return this.sysRoleDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysRoleDaoW.deleteByNid(nid);
  }
  
  public SysRole get(long id)
  {
    return this.sysRoleDaoR.get(id);
  }
  
  public SysRole getByNid(long nid)
  {
    return this.sysRoleDaoR.getByNid(nid);
  }
  
  public List<SysRole> getList(SysRole sysRole)
  {
    return this.sysRoleDaoR.getList(sysRole);
  }
  
  public List<SysRole> getListPage(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getRows(map);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getListMap(map);
  }
  
  public long getListRows(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getListRows(map);
  }
  
  public long getSameCodeRows(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getSameCodeRows(map);
  }
  
  public List<Map<String, Object>> getMenuRoleTreeList(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getMenuRoleTreeList(map);
  }
  
  public List<Map<String, Object>> getUserRoleTreeList(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getUserRoleTreeList(map);
  }
  
  public List<Map<String, Object>> getRoleGridMenuList(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getRoleGridMenuList(map);
  }
  
  public long getRoleGridMenuRows(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getRoleGridMenuRows(map);
  }
  
  public List<Map<String, Object>> getRoleButtonMenuList(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getRoleButtonMenuList(map);
  }
  
  public List<Map<String, Object>> getRoleUserList(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getRoleUserList(map);
  }
  
  public long getRoleUserRows(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getRoleUserRows(map);
  }
  
  public List<SysRole> getAgentOrgRoleList(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getAgentOrgRoleList(map);
  }
  
  public SysRole getAgentOrgRole(Map<String, Object> map)
  {
    return this.sysRoleDaoR.getAgentOrgRole(map);
  }
}
