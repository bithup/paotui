package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysIndustryDaoR;
import com.xgh.mng.dao.write.ISysIndustryDaoW;
import com.xgh.mng.entity.SysIndustry;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysIndustryDao")
public class SysIndustryDaoImpl
  implements ISysIndustryDao
{
  @Autowired
  protected ISysIndustryDaoR sysIndustryDaoR;
  @Autowired
  protected ISysIndustryDaoW sysIndustryDaoW;
  
  public int add(SysIndustry sysIndustry)
  {
    return this.sysIndustryDaoW.add(sysIndustry);
  }
  
  public int update(SysIndustry sysIndustry)
  {
    return this.sysIndustryDaoW.update(sysIndustry);
  }
  
  public int deleteById(long id)
  {
    return this.sysIndustryDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysIndustryDaoW.deleteByNid(nid);
  }
  
  public SysIndustry get(long id)
  {
    return this.sysIndustryDaoR.get(id);
  }
  
  public SysIndustry getByNid(long nid)
  {
    return this.sysIndustryDaoR.getByNid(nid);
  }
  
  public List<SysIndustry> getList(SysIndustry sysIndustry)
  {
    return this.sysIndustryDaoR.getList(sysIndustry);
  }
  
  public List<SysIndustry> getListPage(Map<String, Object> map)
  {
    return this.sysIndustryDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysIndustryDaoR.getRows(map);
  }
  
  public List<SysIndustry> getSysIndustry()
  {
    return this.sysIndustryDaoR.getSysIndustry();
  }
  
  public List<Map<String, Object>> getIndustryTree(Map<String, Object> map)
  {
    return this.sysIndustryDaoR.getIndustryTree(map);
  }
}
