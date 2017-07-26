package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysParam;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysParamDaoW
{
  public abstract int add(SysParam paramSysParam);
  
  public abstract int update(SysParam paramSysParam);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int addBatch(List<SysParam> paramList);
}
