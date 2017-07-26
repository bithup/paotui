package com.xgh.mng.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.dao.ISysIndustryDao;
import com.xgh.mng.entity.SysDepartment;
import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysPrivilegeList;
import com.xgh.mng.entity.SysRole;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.SysUser;
import com.xgh.security.MD5Util;
import com.xgh.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sysIndustryService")
public class SysIndustryServiceImpl
  extends BaseService
  implements ISysIndustryService
{
  @Autowired
  protected ISysIndustryDao sysIndustryDao;
  @Autowired
  protected ISysUnitsService sysUnitsService;
  @Autowired
  protected ISysDepartmentService sysDepartmentService;
  @Autowired
  protected ISysUserService sysUserService;
  @Autowired
  protected ISysRoleService sysRoleService;
  @Autowired
  protected ISysPrivilegeListService sysPrivilegeListService;
  
  public int add(SysIndustry sysIndustry)
  {
    return this.sysIndustryDao.add(sysIndustry);
  }
  
  public int delete(long id)
  {
    return this.sysIndustryDao.deleteById(id);
  }
  
  public int update(SysIndustry sysIndustry)
  {
    return this.sysIndustryDao.update(sysIndustry);
  }
  
  public SysIndustry get(long id)
  {
    return this.sysIndustryDao.get(id);
  }
  
  public List<SysIndustry> getList(SysIndustry sysIndustry)
  {
    return this.sysIndustryDao.getList(sysIndustry);
  }
  
  public Map<String, Object> getListPage(Map<String, Object> params)
  {
    Map<String, Object> map = new HashMap();
    map.put("configQuery", params.get("configQuery"));
    map.put("page", Integer.valueOf(Integer.parseInt(params.get("page") + "")));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(params.get("pagesize") + "")));
    
    Map<String, Object> gridData = new HashMap();
    
    List<SysIndustry> dataList = this.sysIndustryDao.getListPage(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(this.sysIndustryDao.getRows(map)));
    
    return gridData;
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysIndustryDao.getRows(map);
  }
  
  public List<SysIndustry> getSysIndustry()
  {
    List<SysIndustry> list = this.sysIndustryDao.getSysIndustry();
    
    SysIndustry sysIndustry = new SysIndustry();
    sysIndustry.setId(0L);
    sysIndustry.setName("系统");
    list.add(0, sysIndustry);
    
    return list;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public long save(SysIndustry sysIndustry, String op)
  {
    if ("add".equals(op))
    {
      Date date = new Date();
      sysIndustry.setCreateDate(date);
      sysIndustry.setUpdateDate(date);
      sysIndustry.setStatus(1);
      sysIndustry.setVersion(1);
      
      this.sysIndustryDao.add(sysIndustry);
      
      sysIndustry.setNid(Long.parseLong(getOsNO() + sysIndustry.getId()));
      
      return this.sysIndustryDao.update(sysIndustry);
    }
    if ("modify".equals(op))
    {
      SysIndustry sysIndustry2 = get(sysIndustry.getId());
      sysIndustry.setUpdateDate(new Date());
      sysIndustry.setCreateDate(sysIndustry2.getCreateDate());
      return this.sysIndustryDao.update(sysIndustry);
    }
    return 0L;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public int check(SysIndustry sysIndustry)
  {
    if (sysIndustry.getIsPass() == 1)
    {
      SysIndustry sysIndustry2 = get(sysIndustry.getId());
      
      sysIndustry2.setIsPass(sysIndustry.getIsPass());
      

      SysUnits sysUnits = getRegSysUnits(sysIndustry2);
      this.sysUnitsService.add(sysUnits);
      

      addDepartment(sysUnits, "行政部", "行政部");
      addDepartment(sysUnits, "运营部", "运营部");
      

      addSysUser(sysUnits);
      


      SysRole sysRole = addInstRole(sysIndustry2);
      
      sysRole.setUnitId(sysUnits.getId());
      
      addPrivilege(sysRole);
      

      addRoles(sysUnits);
      

      sysIndustry2.setUpdateDate(new Date());
      

      return update(sysIndustry2);
    }
    return 0;
  }
  
  public List<Map<String, Object>> getIndustryTree(Map<String, Object> map)
  {
    List<Map<String, Object>> treeList = this.sysIndustryDao.getIndustryTree(map);
    
    Map<String, Object> rootMap = new HashMap();
    rootMap.put("id", "0");
    rootMap.put("text", "行业");
    treeList.add(rootMap);
    
    return treeList;
  }
  
  private SysUnits getRegSysUnits(SysIndustry sysIndustry)
  {
    SysUnits sysUnits = new SysUnits();
    sysUnits.setParentId(0L);
    sysUnits.setInstId(sysIndustry.getId());
    sysUnits.setInstCode(sysIndustry.getCode());
    sysUnits.setUnitCode(sysIndustry.getCode());
    sysUnits.setUnitType(1);
    sysUnits.setUnitKind(1);
    sysUnits.setUnitDomain(sysIndustry.getCode());
    sysUnits.setUnitName(sysIndustry.getName() + "管理平台");
    sysUnits.setShortName(sysIndustry.getName());
    
    Date date = new Date();
    sysUnits.setRegDate(date);
    sysUnits.setStopDate(DateUtil.addYear(date, 1));
    sysUnits.setUserCount(1000L);
    sysUnits.setUnitStatus("pass");
    sysUnits.setVersion(1);
    sysUnits.setOrd(0L);
    sysUnits.setCreateDate(date);
    sysUnits.setUpdateDate(date);
    sysUnits.setStatus(1);
    
    return sysUnits;
  }
  
  private int addDepartment(SysUnits sysUnits, String deptName, String deptShortName)
  {
    SysDepartment sysDepartment = new SysDepartment();
    sysDepartment.setInstId(sysUnits.getInstId());
    sysDepartment.setInstCode(sysUnits.getInstCode());
    sysDepartment.setUnitId(sysUnits.getId());
    sysDepartment.setUnitCode(sysUnits.getUnitCode());
    
    sysDepartment.setDeptName(deptName);
    sysDepartment.setDeptShortName(deptShortName);
    
    sysDepartment.setDeptType(0L);
    sysDepartment.setOrd(0L);
    sysDepartment.setStatus(1);
    Date date = new Date();
    sysDepartment.setCreateDate(date);
    sysDepartment.setUpdateDate(date);
    
    return this.sysDepartmentService.save(sysDepartment, "add");
  }
  
  private int addSysUser(SysUnits sysUnits)
  {
    Date date = new Date();
    SysUser sysUser = new SysUser();
    sysUser.setInstId(sysUnits.getInstId());
    sysUser.setInstCode(sysUnits.getInstCode());
    sysUser.setUnitId(sysUnits.getId());
    sysUser.setUnitCode(sysUnits.getUnitCode());
    sysUser.setAccount("admin");
    sysUser.setPassword(MD5Util.getMD5("123456"));
    sysUser.setUserName("平台管理员");
    sysUser.setIsSys(1L);
    sysUser.setOrd(0L);
    sysUser.setCreateDate(date);
    sysUser.setUpdateDate(date);
    sysUser.setStatus(1);
    
    return this.sysUserService.add(sysUser);
  }
  
  private SysRole addInstRole(SysIndustry sysIndustry)
  {
    SysRole sysRole = new SysRole();
    sysRole.setInstId(sysIndustry.getId());
    
    sysRole.setUnitId(1L);
    sysRole.setRoleName(sysIndustry.getName());
    sysRole.setRoleDesc(sysIndustry.getName() + "角色");
    
    sysRole.setIsSys(1L);
    sysRole.setIsPrivilege(1L);
    sysRole.setLevel(1);
    Date date = new Date();
    sysRole.setCreateDate(date);
    sysRole.setUpdateDate(date);
    sysRole.setStatus(1);
    
    this.sysRoleService.save(sysRole, "add");
    
    return sysRole;
  }
  
  private int addPrivilege(SysRole sysRole)
  {
    SysPrivilegeList sysPrivilegeList = new SysPrivilegeList();
    sysPrivilegeList.setUnitId(sysRole.getUnitId());
    sysPrivilegeList.setPrivilegeMaster("unit");
    sysPrivilegeList.setPrivilegeMasterValue(sysRole.getUnitId());
    sysPrivilegeList.setPrivilegeAccess("role");
    sysPrivilegeList.setPrivilegeAccessValue(sysRole.getId());
    
    return this.sysPrivilegeListService.add(sysPrivilegeList);
  }
  
  private void addRoles(SysUnits sysUnits)
  {
    addRole(sysUnits, "省级代理", 2, 1, 2, 10);
    addRole(sysUnits, "市级代理", 2, 1, 3, 9);
    
    addRole(sysUnits, "国级机构", 3, 1, 10, 8);
    addRole(sysUnits, "省级机构", 3, 1, 11, 7);
    addRole(sysUnits, "市级机构", 3, 1, 12, 6);
    

    addRole(sysUnits, "管理员A", 2, 0, 1, 10);
    addRole(sysUnits, "市场部A", 2, 0, 2, 9);
    addRole(sysUnits, "运营部A", 2, 0, 3, 8);
    addRole(sysUnits, "客服部A", 2, 0, 4, 7);
    

    addRole(sysUnits, "管理员B", 3, 0, 1, 10);
    addRole(sysUnits, "市场部B", 3, 0, 2, 9);
    addRole(sysUnits, "运营部B", 3, 0, 3, 8);
    addRole(sysUnits, "客服部B", 3, 0, 4, 7);
    

    addRole(sysUnits, "管理员", 0, 0, 1, 10);
    addRole(sysUnits, "市场部", 0, 0, 2, 9);
    addRole(sysUnits, "运营部", 0, 0, 3, 8);
    addRole(sysUnits, "客服部", 0, 0, 4, 7);
  }
  
  private void addRole(SysUnits sysUnits, String roleName, int isSys, int isPrivilege, int level, int ord)
  {
    SysRole sysRole = new SysRole();
    sysRole.setInstId(sysUnits.getInstId());
    
    sysRole.setUnitId(sysUnits.getId());
    sysRole.setRoleName(roleName);
    sysRole.setRoleDesc(roleName + "角色");
    
    sysRole.setIsSys(isSys);
    sysRole.setIsPrivilege(isPrivilege);
    Date date = new Date();
    sysRole.setCreateDate(date);
    sysRole.setUpdateDate(date);
    sysRole.setLevel(level);
    sysRole.setStatus(1);
    sysRole.setOrd(ord);
    
    this.sysRoleService.save(sysRole, "add");
  }
}
