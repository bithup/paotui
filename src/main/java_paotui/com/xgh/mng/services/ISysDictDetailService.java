package com.xgh.mng.services;

import com.xgh.mng.entity.SysDictDetail;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysDictDetailService
{
  public abstract int add(SysDictDetail paramSysDictDetail);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysDictDetail paramSysDictDetail);
  
  public abstract SysDictDetail get(long paramLong);
  
  public abstract Map<String, Object> getGridData(HttpServletRequest paramHttpServletRequest, long paramLong);
  
  public abstract boolean isHasSameCode(SysDictDetail paramSysDictDetail, String paramString);
}
