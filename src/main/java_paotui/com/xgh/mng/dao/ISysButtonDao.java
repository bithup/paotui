package com.xgh.mng.dao;

import com.xgh.mng.entity.SysButton;
import java.util.List;
import java.util.Map;

public abstract interface ISysButtonDao
{
  public abstract int add(SysButton paramSysButton);
  
  public abstract int update(SysButton paramSysButton);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysButton get(long paramLong);
  
  public abstract SysButton getByNid(long paramLong);
  
  public abstract List<SysButton> getList(SysButton paramSysButton);
  
  public abstract List<SysButton> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract int deleteByMenuId(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysButton> paramList);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<SysButton> getUserButtons(Map<String, Object> paramMap);
  
  public abstract List<SysButton> getButtonByMenuId(long paramLong);
  
  public abstract List<Long> getButtonIdsByMenuIds(Map<String, Object> paramMap);
}
