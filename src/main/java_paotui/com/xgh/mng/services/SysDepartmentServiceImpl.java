package com.xgh.mng.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.dao.ISysDepartmentDao;
import com.xgh.mng.entity.SysDepartment;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sysDepartmentService")
public class SysDepartmentServiceImpl
  extends BaseService
  implements ISysDepartmentService
{
  private static Logger logger = Logger.getLogger(SysDepartmentServiceImpl.class);
  @Autowired
  protected ISysDepartmentDao sysDepartmentDao;
  
  public int add(SysDepartment sysDepartment)
  {
    return this.sysDepartmentDao.add(sysDepartment);
  }
  
  public int delete(long id)
  {
    return this.sysDepartmentDao.deleteById(id);
  }
  
  public int update(SysDepartment sysDepartment)
  {
    return this.sysDepartmentDao.update(sysDepartment);
  }
  
  public SysDepartment get(long id)
  {
    return this.sysDepartmentDao.get(id);
  }
  
  public List<SysDepartment> getList(SysDepartment sysDepartment)
  {
    List<SysDepartment> list = this.sysDepartmentDao.getList(sysDepartment);
    


    return list;
  }
  
  public List<SysDepartment> getListPage(Map<String, Object> map)
  {
    return this.sysDepartmentDao.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysDepartmentDao.getRows(map);
  }
  
  public boolean isHasChild(SysDepartment sysDepartment)
  {
    return this.sysDepartmentDao.getChildDepartmentRows(sysDepartment.getId()) > 0L;
  }
  
  public Map<String, Object> getGridData(HttpServletRequest request, long unitId, long parentId)
  {
    String page = request.getParameter("page");
    String pagesize = request.getParameter("pagesize");
    

    Map<String, Object> map = new HashMap();
    map.put("parentId", Long.valueOf(parentId));
    map.put("unitId", Long.valueOf(unitId));
    map.put("page", Integer.valueOf(Integer.parseInt(page)));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(pagesize)));
    
    Map<String, Object> gridData = new HashMap();
    
    List<SysDepartment> dataList = this.sysDepartmentDao.getListPage(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(this.sysDepartmentDao.getRows(map)));
    return gridData;
  }
  
  public List<Map<String, Object>> getChildTreeData(long unitId, long parentId)
  {
    Map<String, Object> map = new HashMap();
    map.put("unitId", Long.valueOf(unitId));
    map.put("parentId", Long.valueOf(parentId));
    
    List<Map<String, Object>> dataList = this.sysDepartmentDao.getChildTreeData(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    return dataList;
  }
  
  public int save(SysDepartment sysDepartment, String op)
  {
    if (op.equals("add"))
    {
      String prefix = "";
      if (sysDepartment.getParentId() != 0L)
      {
        SysDepartment parentSysDepartment = this.sysDepartmentDao.get(sysDepartment.getParentId());
        prefix = parentSysDepartment.getDeptCode();
      }
      else
      {
        prefix = sysDepartment.getUnitId() + "";
      }
      Date date = new Date();
      sysDepartment.setCreateDate(date);
      sysDepartment.setUpdateDate(date);
      sysDepartment.setStatus(1);
      this.sysDepartmentDao.add(sysDepartment);
      sysDepartment.setDeptCode(prefix + "_" + sysDepartment.getId());
      
      return this.sysDepartmentDao.update(sysDepartment);
    }
    SysDepartment sysDepartment2 = this.sysDepartmentDao.get(sysDepartment.getId());
    sysDepartment2.setDeptName(sysDepartment.getDeptName());
    sysDepartment2.setDeptShortName(sysDepartment.getDeptShortName());
    sysDepartment2.setDeptDesc(sysDepartment.getDeptDesc());
    sysDepartment2.setOrd(sysDepartment.getOrd());
    sysDepartment2.setDeptShortName(sysDepartment.getDeptShortName());
    sysDepartment2.setDeptType(sysDepartment.getDeptType());
    sysDepartment2.setUpdateDate(new Date());
    
    return this.sysDepartmentDao.update(sysDepartment2);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void deleteDepartment(SysDepartment sysDepartment)
  {
    SysDepartment sysDepartment2 = this.sysDepartmentDao.get(sysDepartment.getId());
    sysDepartment2.setStatus(0);
    this.sysDepartmentDao.update(sysDepartment2);
  }
  
  public String getDeptsByUserId(long userId)
  {
    String deptInfo = "";
    Map<String, Object> map = new HashMap();
    map.put("userId", Long.valueOf(userId));
    List<SysDepartment> sysDepartments = this.sysDepartmentDao.getDeptByUserId(map);
    if (sysDepartments != null) {
      for (SysDepartment department : sysDepartments) {
        if (deptInfo.equals("")) {
          deptInfo = department.getDeptName() + "";
        } else {
          deptInfo = deptInfo + "，" + department.getDeptName();
        }
      }
    }
    return deptInfo;
  }
  
  public long getUserRowsByDeptId(long deptId)
  {
    return this.sysDepartmentDao.getUserRowsByDeptId(deptId);
  }
}
