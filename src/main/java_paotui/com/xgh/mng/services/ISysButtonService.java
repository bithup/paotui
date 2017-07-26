package com.xgh.mng.services;

import com.xgh.mng.entity.SysButton;
import com.xgh.mng.entity.SysUser;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysButtonService
{
  public abstract int add(SysButton paramSysButton);
  
  public abstract int delete(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int update(SysButton paramSysButton);
  
  public abstract SysButton get(long paramLong);
  
  public abstract List<SysButton> getList(SysButton paramSysButton);
  
  public abstract List<SysButton> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getGridData(long paramLong);
  
  public abstract void saveButton(long paramLong, List<Map<String, Object>> paramList);
  
  public abstract String getMenuButtons(HttpServletRequest paramHttpServletRequest, long paramLong, SysUser paramSysUser);
}
