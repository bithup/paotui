package com.xgh.mng.dao;

import com.xgh.mng.entity.FileData;
import java.util.List;
import java.util.Map;

public abstract interface IFileDataDao
{
  public abstract int add(FileData paramFileData);
  
  public abstract int update(FileData paramFileData);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int updateByDataSource(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<FileData> paramList);
  
  public abstract FileData get(long paramLong);
  
  public abstract List<FileData> getList(Map<String, Object> paramMap);
  
  public abstract List<FileData> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
}
