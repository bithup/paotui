package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysPrivilegeList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysPrivilegeListDaoW
{
  public abstract int add(SysPrivilegeList paramSysPrivilegeList);
  
  public abstract int update(SysPrivilegeList paramSysPrivilegeList);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int addBatch(List<SysPrivilegeList> paramList);
  
  public abstract int deleteByAccessList(Map<String, Object> paramMap);
  
  public abstract int deleteByMasterList(Map<String, Object> paramMap);
}
