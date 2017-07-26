package com.xgh.mng.dao.write;

import com.xgh.mng.entity.FileData;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface IFileDataDaoW
{
  public abstract int add(FileData paramFileData);
  
  public abstract int update(FileData paramFileData);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int updateByDataSource(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<FileData> paramList);
}
