package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysGridColumns;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysGridColumnsDaoR
{
  public abstract SysGridColumns get(long paramLong);
  
  public abstract SysGridColumns getByNid(long paramLong);
  
  public abstract List<SysGridColumns> getList(Map<String, Object> paramMap);
  
  public abstract List<SysGridColumns> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<SysGridColumns> getListByJoinType(Map<String, Object> paramMap);
}
