package com.xgh.mng.services;

import com.xgh.mng.entity.SysDictDetail;
import com.xgh.mng.entity.SysDictMain;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysDictMainService
{
  public abstract int add(SysDictMain paramSysDictMain);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysDictMain paramSysDictMain);
  
  public abstract SysDictMain get(long paramLong);
  
  public abstract Map<String, Object> getGridData(HttpServletRequest paramHttpServletRequest);
  
  public abstract boolean isHasSameCode(SysDictMain paramSysDictMain, String paramString);
  
  public abstract void loadDictDataToCache();
  
  public abstract SysDictMain getSysDictMainByCode(String paramString);
  
  public abstract List<SysDictDetail> getDetailListByMainCode(long paramLong, String paramString);
  
  public abstract SysDictDetail getDetailByCode(long paramLong, String paramString1, String paramString2);
  
  public abstract long getDictDetailRowsByMainId(long paramLong);
  
  public abstract long getSysDictMainChildRows(long paramLong);
  
  public abstract List<Map<String, Object>> getSysDictMainFirstTreeData();
  
  public abstract List<Map<String, Object>> getDictMainTreeList(Map<String, Object> paramMap);
}
