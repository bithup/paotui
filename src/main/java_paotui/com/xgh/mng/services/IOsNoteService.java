package com.xgh.mng.services;

import com.xgh.mng.entity.OsNote;
import java.util.List;
import java.util.Map;

public abstract interface IOsNoteService
{
  public abstract int add(OsNote paramOsNote);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(OsNote paramOsNote);
  
  public abstract OsNote get(long paramLong);
  
  public abstract List<OsNote> getList(OsNote paramOsNote);
  
  public abstract List<OsNote> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
}
