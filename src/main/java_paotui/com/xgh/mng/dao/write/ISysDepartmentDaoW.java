package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysDepartment;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysDepartmentDaoW
{
  public abstract int add(SysDepartment paramSysDepartment);
  
  public abstract int update(SysDepartment paramSysDepartment);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
