package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysUser;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysUserDaoR
{
  public abstract SysUser get(long paramLong);
  
  public abstract SysUser getByNid(long paramLong);
  
  public abstract List<SysUser> getList(SysUser paramSysUser);
  
  public abstract List<SysUser> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
  
  public abstract long getSameAcountRows(Map<String, Object> paramMap);
  
  public abstract long getDeptUserRowsByUserId(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getSelectDialogUserList(Map<String, Object> paramMap);
  
  public abstract List<SysUser> getLoginCheckUserList(Map<String, Object> paramMap);
  
  public abstract SysUser getUserByAccountAndUnitId(Map<String, Object> paramMap);
  
  public abstract SysUser getAdmin(long paramLong);
}
