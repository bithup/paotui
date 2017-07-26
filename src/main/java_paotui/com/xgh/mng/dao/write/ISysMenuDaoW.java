package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysMenu;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysMenuDaoW
{
  public abstract int add(SysMenu paramSysMenu);
  
  public abstract int update(SysMenu paramSysMenu);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int deleteByNid(long paramLong);
}
