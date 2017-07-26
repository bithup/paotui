package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysDictDetail;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysDictDetailDaoR
{
  public abstract SysDictDetail get(long paramLong);
  
  public abstract SysDictDetail getByNid(long paramLong);
  
  public abstract List<SysDictDetail> getList(SysDictDetail paramSysDictDetail);
  
  public abstract List<SysDictDetail> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
  
  public abstract long getRowsByCode(Map<String, Object> paramMap);
  
  public abstract List<SysDictDetail> getAllList();
  
  public abstract List<SysDictDetail> getDetailListByMainCode(Map<String, Object> paramMap);
  
  public abstract SysDictDetail getDetailByMainCode(Map<String, Object> paramMap);
  
  public abstract List<SysDictDetail> getExtendsListMap(Map<String, Object> paramMap);
  
  public abstract long getExtendsListRows(Map<String, Object> paramMap);
}
