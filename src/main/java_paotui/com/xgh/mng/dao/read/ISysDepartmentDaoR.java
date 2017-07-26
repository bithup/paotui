package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysDepartment;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysDepartmentDaoR
{
  public abstract SysDepartment get(long paramLong);
  
  public abstract SysDepartment getByNid(long paramLong);
  
  public abstract List<SysDepartment> getList(SysDepartment paramSysDepartment);
  
  public abstract List<SysDepartment> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<SysDepartment> getDeptByUserId(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getChildTreeData(Map<String, Object> paramMap);
  
  public abstract long getChildDepartmentRows(long paramLong);
  
  public abstract long getUserRowsByDeptId(long paramLong);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getSelectDialogDepartmentList(Map<String, Object> paramMap);
  
  public abstract List<Long> getUserIdsByDeptCodes(Map<String, Object> paramMap);
}
