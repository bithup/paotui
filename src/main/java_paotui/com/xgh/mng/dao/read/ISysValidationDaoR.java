package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysValidation;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysValidationDaoR
{
  public abstract SysValidation get(long paramLong);
  
  public abstract SysValidation getByNid(long paramLong);
  
  public abstract List<SysValidation> getList(Map<String, Object> paramMap);
  
  public abstract List<SysValidation> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<SysValidation> getListByJoinType(Map<String, Object> paramMap);
}
