package com.xgh.mng.dao.write;

import com.xgh.mng.entity.Zone;
import org.springframework.stereotype.Component;

@Component
public abstract interface IZoneDaoW
{
  public abstract int add(Zone paramZone);
  
  public abstract int update(Zone paramZone);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
