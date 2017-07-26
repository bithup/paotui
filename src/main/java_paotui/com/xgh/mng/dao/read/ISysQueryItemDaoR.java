package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysQueryItem;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysQueryItemDaoR
{
  public abstract SysQueryItem get(long paramLong);
  
  public abstract SysQueryItem getByNid(long paramLong);
  
  public abstract List<SysQueryItem> getList(Map<String, Object> paramMap);
  
  public abstract List<SysQueryItem> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<SysQueryItem> getListByJoinType(Map<String, Object> paramMap);
}
