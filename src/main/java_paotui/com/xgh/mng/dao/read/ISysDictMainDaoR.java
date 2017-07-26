package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysDictMain;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysDictMainDaoR
{
  public abstract SysDictMain get(long paramLong);
  
  public abstract SysDictMain getByNid(long paramLong);
  
  public abstract List<SysDictMain> getList(SysDictMain paramSysDictMain);
  
  public abstract List<SysDictMain> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
  
  public abstract long getRowsByDictCode(Map<String, Object> paramMap);
  
  public abstract long getDictDetailRowsByMainId(long paramLong);
  
  public abstract List<Map<String, Object>> getDictMainTreeList(Map<String, Object> paramMap);
  
  public abstract List<SysDictMain> getAllList();
  
  public abstract SysDictMain getSysDictMainByCode(String paramString);
  
  public abstract List<Map<String, Object>> getSysDictMainFirstTreeData();
  
  public abstract long getSysDictMainChildRows(long paramLong);
}
