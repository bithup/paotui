package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysLog;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysLogDaoR
{
  public abstract SysLog get(long paramLong);
  
  public abstract SysLog getByNid(long paramLong);
  
  public abstract List<SysLog> getList(SysLog paramSysLog);
  
  public abstract List<SysLog> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
}
