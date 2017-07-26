package com.xgh.mng.services;

import com.xgh.mng.dao.ISysDeptUserDao;
import com.xgh.mng.entity.SysDeptUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDeptUserService")
public class SysDeptUserServiceImpl
  implements ISysDeptUserService
{
  @Autowired
  protected ISysDeptUserDao sysDeptUserDao;
  
  public int add(SysDeptUser sysDeptUser)
  {
    return this.sysDeptUserDao.add(sysDeptUser);
  }
  
  public int delete(long id)
  {
    return this.sysDeptUserDao.deleteById(id);
  }
  
  public int update(SysDeptUser sysDeptUser)
  {
    return this.sysDeptUserDao.update(sysDeptUser);
  }
  
  public SysDeptUser get(long id)
  {
    return this.sysDeptUserDao.get(id);
  }
  
  public int deleteByUserId(long userId)
  {
    return this.sysDeptUserDao.deleteByUserId(userId);
  }
  
  public long getDeptRowsByUserId(long userId)
  {
    return this.sysDeptUserDao.getDeptRowsByUserId(userId);
  }
}
