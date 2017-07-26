package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysPrivilegeListDaoR;
import com.xgh.mng.dao.write.ISysPrivilegeListDaoW;
import com.xgh.mng.entity.SysPrivilegeList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysPrivilegeListDao")
public class SysPrivilegeListDaoImpl
  implements ISysPrivilegeListDao
{
  @Autowired
  protected ISysPrivilegeListDaoR sysPrivilegeListDaoR;
  @Autowired
  protected ISysPrivilegeListDaoW sysPrivilegeListDaoW;
  
  public int add(SysPrivilegeList sysPrivilegeList)
  {
    return this.sysPrivilegeListDaoW.add(sysPrivilegeList);
  }
  
  public int update(SysPrivilegeList sysPrivilegeList)
  {
    return this.sysPrivilegeListDaoW.update(sysPrivilegeList);
  }
  
  public int deleteById(long id)
  {
    return this.sysPrivilegeListDaoW.deleteById(id);
  }
  
  public int deleteByIds(List<Long> Ids)
  {
    return this.sysPrivilegeListDaoW.deleteByIds(Ids);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysPrivilegeListDaoW.deleteByNid(nid);
  }
  
  public SysPrivilegeList get(long id)
  {
    return this.sysPrivilegeListDaoR.get(id);
  }
  
  public SysPrivilegeList getByNid(long nid)
  {
    return this.sysPrivilegeListDaoR.getByNid(nid);
  }
  
  public List<SysPrivilegeList> getList(SysPrivilegeList sysPrivilegeList)
  {
    return this.sysPrivilegeListDaoR.getList(sysPrivilegeList);
  }
  
  public List<SysPrivilegeList> getListPage(Map<String, Object> map)
  {
    return this.sysPrivilegeListDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysPrivilegeListDaoR.getRows(map);
  }
  
  public int addBatch(List<SysPrivilegeList> list)
  {
    return this.sysPrivilegeListDaoW.addBatch(list);
  }
  
  public int deleteByAccessList(Map<String, Object> map)
  {
    return this.sysPrivilegeListDaoW.deleteByAccessList(map);
  }
  
  public int deleteByMasterList(Map<String, Object> map)
  {
    return this.sysPrivilegeListDaoW.deleteByMasterList(map);
  }
  
  public List<Long> getRoleIdsByUserId(long userId)
  {
    return this.sysPrivilegeListDaoR.getRoleIdsByUserId(userId);
  }
  
  public long getSysRoleIdByUnitId(long unitId)
  {
    return this.sysPrivilegeListDaoR.getSysRoleIdByUnitId(unitId);
  }
  
  public SysPrivilegeList getSysPrivilegeListByMaster(Map<String, Object> map)
  {
    return this.sysPrivilegeListDaoR.getSysPrivilegeListByMaster(map);
  }
}
