package com.xgh.mng.dao.read;

import com.xgh.mng.entity.OsNote;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface IOsNoteDaoR
{
  public abstract OsNote get(long paramLong);
  
  public abstract OsNote getByNid(long paramLong);
  
  public abstract List<OsNote> getList(OsNote paramOsNote);
  
  public abstract List<OsNote> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
}
