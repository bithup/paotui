package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysType;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysTypeDaoR
{
  public abstract SysType get(long paramLong);
  
  public abstract SysType getByNid(long paramLong);
  
  public abstract List<SysType> getList(Map<String, Object> paramMap);
  
  public abstract List<SysType> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getTreeData(Map<String, Object> paramMap);
  
  public abstract long getChildTypeRows(long paramLong);
  
  public abstract long getSameCodeRows(Map<String, Object> paramMap);
  
  public abstract SysType getSysTypeByCode(Map<String, Object> paramMap);
}
