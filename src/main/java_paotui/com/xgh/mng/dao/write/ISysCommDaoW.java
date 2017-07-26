package com.xgh.mng.dao.write;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysCommDaoW
{
  public abstract int insert(Map<String, Object> paramMap);
  
  public abstract int update(Map<String, Object> paramMap);
  
  public abstract int delete(Map<String, Object> paramMap);
}
