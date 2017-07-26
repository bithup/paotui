package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysButton;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysButtonDaoW
{
  public abstract int add(SysButton paramSysButton);
  
  public abstract int update(SysButton paramSysButton);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int deleteByMenuId(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysButton> paramList);
}
