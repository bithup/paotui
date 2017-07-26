package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysDeptUser;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysDeptUserDaoR
{
  public abstract SysDeptUser get(long paramLong);
  
  public abstract SysDeptUser getByNid(long paramLong);
  
  public abstract List<SysDeptUser> getList(SysDeptUser paramSysDeptUser);
  
  public abstract List<SysDeptUser> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract long getDeptRowsByUserId(long paramLong);
  
  public abstract List<SysDeptUser> getListByUserId(Long paramLong);
}
