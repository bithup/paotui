package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysValidation;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysValidationDaoW
{
  public abstract int add(SysValidation paramSysValidation);
  
  public abstract int update(SysValidation paramSysValidation);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int deleteByJoinType(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysValidation> paramList);
}
