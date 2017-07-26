package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysDictDetail;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysDictDetailDaoW
{
  public abstract int add(SysDictDetail paramSysDictDetail);
  
  public abstract int update(SysDictDetail paramSysDictDetail);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
