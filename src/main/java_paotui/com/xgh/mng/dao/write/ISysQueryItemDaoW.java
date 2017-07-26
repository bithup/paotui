package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysQueryItem;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysQueryItemDaoW
{
  public abstract int add(SysQueryItem paramSysQueryItem);
  
  public abstract int update(SysQueryItem paramSysQueryItem);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int addBatch(List<SysQueryItem> paramList);
  
  public abstract int deleteByJoinType(Map<String, Object> paramMap);
}
