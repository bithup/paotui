package com.xgh.mng.dao;

import com.xgh.mng.dao.read.ISysDepartmentDaoR;
import com.xgh.mng.dao.write.ISysDepartmentDaoW;
import com.xgh.mng.entity.SysDepartment;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDepartmentDao")
public class SysDepartmentDaoImpl
  implements ISysDepartmentDao
{
  @Autowired
  protected ISysDepartmentDaoR sysDepartmentDaoR;
  @Autowired
  protected ISysDepartmentDaoW sysDepartmentDaoW;
  
  public int add(SysDepartment sysDepartment)
  {
    return this.sysDepartmentDaoW.add(sysDepartment);
  }
  
  public int update(SysDepartment sysDepartment)
  {
    return this.sysDepartmentDaoW.update(sysDepartment);
  }
  
  public int deleteById(long id)
  {
    return this.sysDepartmentDaoW.deleteById(id);
  }
  
  public int deleteByNid(long nid)
  {
    return this.sysDepartmentDaoW.deleteByNid(nid);
  }
  
  public SysDepartment get(long id)
  {
    return this.sysDepartmentDaoR.get(id);
  }
  
  public SysDepartment getByNid(long nid)
  {
    return this.sysDepartmentDaoR.getByNid(nid);
  }
  
  public List<SysDepartment> getList(SysDepartment sysDepartment)
  {
    return this.sysDepartmentDaoR.getList(sysDepartment);
  }
  
  public List<SysDepartment> getListPage(Map<String, Object> map)
  {
    return this.sysDepartmentDaoR.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysDepartmentDaoR.getRows(map);
  }
  
  public List<SysDepartment> getDeptByUserId(Map<String, Object> map)
  {
    return this.sysDepartmentDaoR.getDeptByUserId(map);
  }
  
  public List<Map<String, Object>> getChildTreeData(Map<String, Object> map)
  {
    return this.sysDepartmentDaoR.getChildTreeData(map);
  }
  
  public long getChildDepartmentRows(long parentId)
  {
    return this.sysDepartmentDaoR.getChildDepartmentRows(parentId);
  }
  
  public long getUserRowsByDeptId(long deptId)
  {
    return this.sysDepartmentDaoR.getUserRowsByDeptId(deptId);
  }
  
  public List<Map<String, Object>> getListMap(Map<String, Object> map)
  {
    return this.sysDepartmentDaoR.getListMap(map);
  }
  
  public long getListRows(Map<String, Object> map)
  {
    return this.sysDepartmentDaoR.getListRows(map);
  }
  
  public List<Map<String, Object>> getSelectDialogDepartmentList(Map<String, Object> map)
  {
    return this.sysDepartmentDaoR.getSelectDialogDepartmentList(map);
  }
  
  public List<Long> getUserIdsByDeptCodes(Map<String, Object> map)
  {
    return this.sysDepartmentDaoR.getUserIdsByDeptCodes(map);
  }
}
