package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysDictMain;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysDictMainDaoW
{
  public abstract int add(SysDictMain paramSysDictMain);
  
  public abstract int update(SysDictMain paramSysDictMain);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
