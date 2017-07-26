package com.xgh.mng.dao.read;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysCommDaoR
{
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
}
