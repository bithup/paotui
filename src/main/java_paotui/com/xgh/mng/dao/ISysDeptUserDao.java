package com.xgh.mng.dao;

import com.xgh.mng.entity.SysDeptUser;
import java.util.List;
import java.util.Map;

public abstract interface ISysDeptUserDao
{
  public abstract int add(SysDeptUser paramSysDeptUser);
  
  public abstract int update(SysDeptUser paramSysDeptUser);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysDeptUser get(long paramLong);
  
  public abstract SysDeptUser getByNid(long paramLong);
  
  public abstract List<SysDeptUser> getList(SysDeptUser paramSysDeptUser);
  
  public abstract List<SysDeptUser> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract int deleteByUserId(long paramLong);
  
  public abstract int addBatch(List<SysDeptUser> paramList);
  
  public abstract int deleteByDeptIdAndUserIds(Map<String, Object> paramMap);
  
  public abstract long getDeptRowsByUserId(long paramLong);
  
  public abstract List<SysDeptUser> getListByUserId(Long paramLong);
}
