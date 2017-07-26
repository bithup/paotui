package com.xgh.mng.services;

import com.xgh.mng.entity.SysDeptUser;

public abstract interface ISysDeptUserService
{
  public abstract int add(SysDeptUser paramSysDeptUser);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysDeptUser paramSysDeptUser);
  
  public abstract SysDeptUser get(long paramLong);
  
  public abstract int deleteByUserId(long paramLong);
  
  public abstract long getDeptRowsByUserId(long paramLong);
}
