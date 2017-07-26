package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysDeptUserDaoR;
import com.xgh.mng.dao.write.ISysDeptUserDaoW;
import com.xgh.mng.entity.SysDeptUser;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDeptUserDao")
public class SysDeptUserDaoImpl
  implements ISysDeptUserDao
{
  @Autowired
  protected ISysDeptUserDaoR sysDeptUserDaoR;
  @Autowired
  protected ISysDeptUserDaoW sysDeptUserDaoW;
  
  public int add(SysDeptUser sysDeptUser)
  {
    return this.sysDeptUserDaoW.add(sysDeptUser);
  }
  
  public int update(SysDeptUser sysDeptUser)
  {
    return this.sysDeptUserDaoW.update(sysDeptUser);
  }
  
  public int deleteById(long id)
  {
    return this.sysDeptUserDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysDeptUserDaoW.deleteByNid(nid);
  }
  
  public SysDeptUser get(long id)
  {
    return this.sysDeptUserDaoR.get(id);
  }
  
  public SysDeptUser getByNid(long nid)
  {
    return this.sysDeptUserDaoR.getByNid(nid);
  }
  
  public List<SysDeptUser> getList(SysDeptUser sysDeptUser)
  {
    return this.sysDeptUserDaoR.getList(sysDeptUser);
  }
  
  public List<SysDeptUser> getListPage(Map<String, Object> map)
  {
    return this.sysDeptUserDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysDeptUserDaoR.getRows(map);
  }
  
  public int deleteByUserId(long userId)
  {
    return this.sysDeptUserDaoW.deleteByUserId(userId);
  }
  
  public int addBatch(List<SysDeptUser> list)
  {
    return this.sysDeptUserDaoW.addBatch(list);
  }
  
  public int deleteByDeptIdAndUserIds(Map<String, Object> map)
  {
    return this.sysDeptUserDaoW.deleteByDeptIdAndUserIds(map);
  }
  
  public long getDeptRowsByUserId(long userId)
  {
    return this.sysDeptUserDaoR.getDeptRowsByUserId(userId);
  }
  
  public List<SysDeptUser> getListByUserId(Long userId)
  {
    return this.sysDeptUserDaoR.getListByUserId(userId);
  }
}
