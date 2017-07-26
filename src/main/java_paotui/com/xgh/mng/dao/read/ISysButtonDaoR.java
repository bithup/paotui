package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysButton;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysButtonDaoR
{
  public abstract SysButton get(long paramLong);
  
  public abstract SysButton getByNid(long paramLong);
  
  public abstract List<SysButton> getList(SysButton paramSysButton);
  
  public abstract List<SysButton> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<SysButton> getUserButtons(Map<String, Object> paramMap);
  
  public abstract List<SysButton> getButtonByMenuId(long paramLong);
  
  public abstract List<Long> getButtonIdsByMenuIds(Map<String, Object> paramMap);
}
