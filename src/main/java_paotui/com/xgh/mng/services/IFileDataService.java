package com.xgh.mng.services;

import com.xgh.mng.entity.FileData;
import com.xgh.util.ConstantUtil;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IFileDataService
{
  public abstract int add(FileData paramFileData);
  
  public abstract int delete(long paramLong);
  
  public abstract int updateByDataSource(Map<String, Object> paramMap);
  
  public abstract int update(FileData paramFileData);
  
  public abstract FileData get(long paramLong);
  
  public abstract List<FileData> getList(Map<String, Object> paramMap);
  
  public abstract List<FileData> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getFileDatas(Map<String, Object> paramMap);
  
  public abstract List<FileData> saveFiles(HttpServletRequest paramHttpServletRequest, String[] paramArrayOfString, long paramLong, ConstantUtil.FileUploadCode paramFileUploadCode, int paramInt);
}
