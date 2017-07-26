package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysParam;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysParamDaoR
{
  public abstract SysParam get(long paramLong);
  
  public abstract SysParam getByNid(long paramLong);
  
  public abstract List<SysParam> getList(SysParam paramSysParam);
  
  public abstract List<SysParam> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
  
  public abstract long getRowsByTypeIdAndCode(Map<String, Object> paramMap);
  
  public abstract SysParam getByTypeIdAndCode(Map<String, Object> paramMap);
  
  public abstract List<SysParam> getListByTypeId(Map<String, Object> paramMap);
}
