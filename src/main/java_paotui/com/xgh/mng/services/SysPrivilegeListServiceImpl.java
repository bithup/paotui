package com.xgh.mng.services;

import com.xgh.mng.dao.ISysPrivilegeListDao;
import com.xgh.mng.entity.SysPrivilegeList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysPrivilegeListService")
public class SysPrivilegeListServiceImpl
  implements ISysPrivilegeListService
{
  @Autowired
  protected ISysPrivilegeListDao sysPrivilegeListDao;
  
  public int add(SysPrivilegeList sysPrivilegeList)
  {
    return this.sysPrivilegeListDao.add(sysPrivilegeList);
  }
  
  public int delete(long id)
  {
    return this.sysPrivilegeListDao.deleteById(id);
  }
  
  public int update(SysPrivilegeList sysPrivilegeList)
  {
    return this.sysPrivilegeListDao.update(sysPrivilegeList);
  }
  
  public SysPrivilegeList get(long id)
  {
    return this.sysPrivilegeListDao.get(id);
  }
  
  public SysPrivilegeList getSysPrivilegeListByMaster(Map<String, Object> map)
  {
    return this.sysPrivilegeListDao.getSysPrivilegeListByMaster(map);
  }
}
