package com.xgh.mng.dao.read;

import com.xgh.mng.entity.FileData;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface IFileDataDaoR
{
  public abstract FileData get(long paramLong);
  
  public abstract FileData getByNid(long paramLong);
  
  public abstract List<FileData> getList(Map<String, Object> paramMap);
  
  public abstract List<FileData> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
}
