package com.xgh.mng.dao;

import com.xgh.mng.entity.OsNote;
import java.util.List;
import java.util.Map;

public abstract interface IOsNoteDao
{
  public abstract int add(OsNote paramOsNote);
  
  public abstract int update(OsNote paramOsNote);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract OsNote get(long paramLong);
  
  public abstract OsNote getByNid(long paramLong);
  
  public abstract List<OsNote> getList(OsNote paramOsNote);
  
  public abstract List<OsNote> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
}
