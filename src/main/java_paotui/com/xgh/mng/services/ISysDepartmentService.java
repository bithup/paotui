package com.xgh.mng.services;

import com.xgh.mng.entity.SysDepartment;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

public abstract interface ISysDepartmentService
{
  public abstract int add(SysDepartment paramSysDepartment);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysDepartment paramSysDepartment);
  
  public abstract SysDepartment get(long paramLong);
  
  public abstract List<SysDepartment> getList(SysDepartment paramSysDepartment);
  
  public abstract List<SysDepartment> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract boolean isHasChild(SysDepartment paramSysDepartment);
  
  public abstract Map<String, Object> getGridData(HttpServletRequest paramHttpServletRequest, long paramLong1, long paramLong2);
  
  public abstract List<Map<String, Object>> getChildTreeData(long paramLong1, long paramLong2);
  
  public abstract int save(SysDepartment paramSysDepartment, String paramString);
  
  @Transactional(rollbackFor={Exception.class})
  public abstract void deleteDepartment(SysDepartment paramSysDepartment);
  
  public abstract String getDeptsByUserId(long paramLong);
  
  public abstract long getUserRowsByDeptId(long paramLong);
}
