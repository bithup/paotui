package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysGridColumns;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysGridColumnsDaoW
{
  public abstract int add(SysGridColumns paramSysGridColumns);
  
  public abstract int update(SysGridColumns paramSysGridColumns);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int deleteByJoinType(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysGridColumns> paramList);
}
