package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysUserDaoR;
import com.xgh.mng.dao.write.ISysUserDaoW;
import com.xgh.mng.entity.SysUser;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserDao")
public class SysUserDaoImpl
  implements ISysUserDao
{
  @Autowired
  protected ISysUserDaoR sysUserDaoR;
  @Autowired
  protected ISysUserDaoW sysUserDaoW;
  
  public int add(SysUser sysUser)
  {
    return this.sysUserDaoW.add(sysUser);
  }
  
  public int update(SysUser sysUser)
  {
    return this.sysUserDaoW.update(sysUser);
  }
  
  public int deleteById(long id)
  {
    return this.sysUserDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysUserDaoW.deleteByNid(nid);
  }
  
  public SysUser get(long id)
  {
    return this.sysUserDaoR.get(id);
  }
  
  public SysUser getByNid(long nid)
  {
    return this.sysUserDaoR.getByNid(nid);
  }
  
  public List<SysUser> getList(SysUser sysUser)
  {
    return this.sysUserDaoR.getList(sysUser);
  }
  
  public List<SysUser> getListPage(Map<String, Object> map)
  {
    return this.sysUserDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysUserDaoR.getRows(map);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysUserDaoR.getListMap(map);
  }
  
  public long getListRows(Map<String, Object> map)
  {
    return this.sysUserDaoR.getListRows(map);
  }
  
  public long getSameAcountRows(Map<String, Object> map)
  {
    return this.sysUserDaoR.getSameAcountRows(map);
  }
  
  public long getDeptUserRowsByUserId(Map<String, Object> map)
  {
    return this.sysUserDaoR.getDeptUserRowsByUserId(map);
  }
  
  public List<Map<String, Object>> getSelectDialogUserList(Map<String, Object> map)
  {
    return this.sysUserDaoR.getSelectDialogUserList(map);
  }
  
  public List<SysUser> getLoginCheckUserList(Map<String, Object> map)
  {
    return this.sysUserDaoR.getLoginCheckUserList(map);
  }
  
  public SysUser getUserByAccountAndUnitId(Map<String, Object> map)
  {
    return this.sysUserDaoR.getUserByAccountAndUnitId(map);
  }
  
  public SysUser getAdmin(long unitId)
  {
    return this.sysUserDaoR.getAdmin(unitId);
  }
}
