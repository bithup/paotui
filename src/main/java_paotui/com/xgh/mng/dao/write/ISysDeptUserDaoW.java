package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysDeptUser;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysDeptUserDaoW
{
  public abstract int add(SysDeptUser paramSysDeptUser);
  
  public abstract int update(SysDeptUser paramSysDeptUser);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByUserId(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int addBatch(List<SysDeptUser> paramList);
  
  public abstract int deleteByDeptIdAndUserIds(Map<String, Object> paramMap);
}
