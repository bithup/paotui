package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysTypeDaoW
{
  public abstract int add(SysType paramSysType);
  
  public abstract int update(SysType paramSysType);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int addBatch(List<SysType> paramList);
}
