package com.xgh.mng.dao.write;

import com.xgh.mng.entity.OsNote;
import org.springframework.stereotype.Component;

@Component
public abstract interface IOsNoteDaoW
{
  public abstract int add(OsNote paramOsNote);
  
  public abstract int update(OsNote paramOsNote);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
