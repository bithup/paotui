package com.xgh.mng.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysCommDao
{
  public abstract int insert(Map<String, Object> paramMap);
  
  public abstract int update(Map<String, Object> paramMap);
  
  public abstract int delete(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
}
