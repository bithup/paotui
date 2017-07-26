package com.xgh.mng.services;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysDialogService
{
  public abstract List<Map<String, Object>> getList(HttpServletRequest paramHttpServletRequest, long paramLong, String paramString);
  
  public abstract Map<String, List<String>> getMixSelectedList(String[] paramArrayOfString);
  
  public abstract List<Long> getMixDistinctPepleIds(String[] paramArrayOfString, long paramLong);
}
