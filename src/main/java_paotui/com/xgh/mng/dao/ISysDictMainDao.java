package com.xgh.mng.dao;

import com.xgh.mng.entity.SysDictMain;
import java.util.List;
import java.util.Map;

public abstract interface ISysDictMainDao
{
  public abstract int add(SysDictMain paramSysDictMain);
  
  public abstract int update(SysDictMain paramSysDictMain);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysDictMain get(long paramLong);
  
  public abstract SysDictMain getByNid(long paramLong);
  
  public abstract List<SysDictMain> getList(SysDictMain paramSysDictMain);
  
  public abstract List<SysDictMain> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
  
  public abstract long getRowsByDictCode(Map<String, Object> paramMap);
  
  public abstract long getDictDetailRowsByMainId(long paramLong);
  
  public abstract List<Map<String, Object>> getDictMainTreeList(Map<String, Object> paramMap);
  
  public abstract List<SysDictMain> getAllList();
  
  public abstract SysDictMain getSysDictMainByCode(String paramString);
  
  public abstract List<Map<String, Object>> getSysDictMainFirstTreeData();
  
  public abstract long getSysDictMainChildRows(long paramLong);
}
