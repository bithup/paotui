package com.xgh.mng.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.dao.ISysButtonDao;
import com.xgh.mng.dao.ISysMenuDao;
import com.xgh.mng.dao.ISysPrivilegeListDao;
import com.xgh.mng.dao.ISysRoleDao;
import com.xgh.mng.entity.SysPrivilegeList;
import com.xgh.mng.entity.SysRole;
import com.xgh.mng.entity.SysUser;
import com.xgh.util.JSONUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sysRoleService")
public class SysRoleServiceImpl
  extends BaseService
  implements ISysRoleService
{
  private Logger logger = Logger.getLogger(SysRoleServiceImpl.class);
  @Autowired
  protected ISysRoleDao sysRoleDao;
  @Autowired
  protected ISysPrivilegeListDao sysPrivilegeListDao;
  @Autowired
  protected ISysMenuDao sysMenuDao;
  @Autowired
  protected ISysButtonDao sysButtonDao;
  @Autowired
  protected ISysDialogService sysDialogService;
  @Autowired
  protected ISysLogService sysLogService;
  
  public int add(SysRole sysRole)
  {
    return this.sysRoleDao.add(sysRole);
  }
  
  public int delete(long id)
  {
    return this.sysRoleDao.deleteById(id);
  }
  
  public int update(SysRole sysRole)
  {
    return this.sysRoleDao.update(sysRole);
  }
  
  public SysRole get(long id)
  {
    return this.sysRoleDao.get(id);
  }
  
  public int save(SysRole sysRole, String op)
  {
    String roleCode_prefix = "nor_";
    if ((sysRole.getIsSys() == 1L) && (sysRole.getIsPrivilege() == 1L)) {
      roleCode_prefix = "inst_";
    } else if ((sysRole.getIsSys() == 2L) && (sysRole.getIsPrivilege() == 1L)) {
      roleCode_prefix = "agent_";
    } else if ((sysRole.getIsSys() == 2L) && (sysRole.getIsPrivilege() == 0L)) {
      roleCode_prefix = "agents_";
    } else if ((sysRole.getIsSys() == 3L) && (sysRole.getIsPrivilege() == 1L)) {
      roleCode_prefix = "org_";
    } else if ((sysRole.getIsSys() == 3L) && (sysRole.getIsPrivilege() == 0L)) {
      roleCode_prefix = "orgs_";
    }
    if (op.equals("add"))
    {
      Date date = new Date();
      sysRole.setCreateDate(date);
      sysRole.setUpdateDate(date);
      
      add(sysRole);
      sysRole.setRoleCode(roleCode_prefix + sysRole.getId());
      
      return update(sysRole);
    }
    SysRole sysRole2 = get(sysRole.getId());
    sysRole2.setRoleName(sysRole.getRoleName());
    sysRole2.setRoleDesc(sysRole.getRoleDesc());
    sysRole2.setRoleCode(roleCode_prefix + sysRole2.getId());
    sysRole2.setIsPrivilege(sysRole.getIsPrivilege());
    sysRole2.setOrd(sysRole.getOrd());
    sysRole2.setUpdateDate(new Date());
    return update(sysRole2);
  }
  
  public Map<String, Object> getGridData(HttpServletRequest request, SysUser sysUser, long sysRoleId)
  {
    Map<String, Object> gridData = new HashMap();
    
    long userId = sysUser.getId();
    long unitId = sysUser.getUnitId();
    
    String page = request.getParameter("page");
    String pageSize = request.getParameter("pagesize");
    String sortName = request.getParameter("sortname");
    String sortOrder = request.getParameter("sortorder");
    String listType = request.getParameter("listType");
    
    Map<String, Object> map = new HashMap();
    map.put("page", Integer.valueOf(Integer.parseInt(page)));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(pageSize)));
    map.put("unitId", Long.valueOf(unitId));
    map.put("userId", Long.valueOf(userId));
    








    map.put("type", request.getParameter("type"));
    
    List<Map<String, Object>> dataList = new ArrayList();
    long totalRows = 0L;
    if ("grid".equals(listType))
    {
      if ((sortName != null) && (!sortName.equals(""))) {
        sortName = sortName.toLowerCase();
      }
      map.put("sortname", sortName);
      map.put("sortorder", sortOrder);
      map.put("userId", Long.valueOf(userId));
      
      dataList = this.sysRoleDao.getListMap(map);
      totalRows = this.sysRoleDao.getListRows(map);
    }
    else if ("rolemenu".equals(listType))
    {
      long roleId = Long.valueOf(request.getParameter("roleId")).longValue();
      
      map.put("pId", Long.valueOf(0L));
      map.put("roleId", Long.valueOf(roleId));
      map.put("sysRoleId", Long.valueOf(sysRoleId));
      dataList = this.sysRoleDao.getRoleGridMenuList(map);
      totalRows = this.sysRoleDao.getRoleGridMenuRows(map);
      if (dataList != null) {
        for (Map<String, Object> map2 : dataList)
        {
          map.put("pId", map2.get("id"));
          map.put("page", null);
          map.put("pagesize", null);
          List<Map<String, Object>> secondList = this.sysRoleDao.getRoleGridMenuList(map);
          if ((secondList != null) && (!secondList.isEmpty()))
          {
            map2.put("children", secondList);
            for (Map<String, Object> map3 : secondList)
            {
              map.put("menuId", map3.get("id"));
              map3.put("children", this.sysRoleDao.getRoleButtonMenuList(map));
            }
          }
        }
      }
    }
    else if ("roleuser".equals(listType))
    {
      long roleId = Long.valueOf(request.getParameter("roleId")).longValue();
      if ((sortName != null) && (!sortName.equals(""))) {
        if (sortName.equals("ACCOUNT")) {
          sortName = "su.account";
        } else {
          sortName = "";
        }
      }
      map.put("userName", request.getParameter("userName"));
      map.put("roleId", Long.valueOf(roleId));
      map.put("sortname", sortName);
      map.put("sortorder", sortOrder);
      dataList = this.sysRoleDao.getRoleUserList(map);
      totalRows = this.sysRoleDao.getRoleUserRows(map);
    }
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(totalRows));
    return gridData;
  }
  
  public boolean isHasSameCode(SysRole sysRole, String op)
  {
    Map<String, Object> map = new HashMap();
    map.put("unitId", Long.valueOf(sysRole.getUnitId()));
    map.put("roleCode", sysRole.getRoleCode());
    if (op.equals("modify")) {
      map.put("id", Long.valueOf(sysRole.getId()));
    }
    return this.sysRoleDao.getSameCodeRows(map) > 0L;
  }
  
  public List<Map<String, Object>> getRoleTreeData(String listType, HttpServletRequest request)
  {
    Map<String, Object> map = new HashMap();
    map.put("instId", Long.valueOf(getCurrentInstId(request)));
    map.put("unitId", Long.valueOf(getCurrentUnitId(request)));
    map.put("userId", Long.valueOf(getCurrentUserId(request)));
    
    String type = request.getParameter("type");
    






    map.put("type", type);
    List<Map<String, Object>> roleList = new ArrayList();
    if ("rolemenu".equals(listType))
    {
      roleList = this.sysRoleDao.getMenuRoleTreeList(map);
      if (roleList == null) {
        roleList = new ArrayList();
      }
      if ("inst".equals(type))
      {
        Map<String, Object> ispMap = new HashMap();
        ispMap.put("id", "inst_0");
        ispMap.put("text", "行业角色");
        roleList.add(ispMap);
      }
      else if ("agent".equals(type))
      {
        Map<String, Object> agentMap = new HashMap();
        agentMap.put("id", "agent_0");
        agentMap.put("text", "代理角色");
        roleList.add(agentMap);
        
        Map<String, Object> sysMap = new HashMap();
        sysMap.put("id", "agents_0");
        sysMap.put("text", "代理系统角色");
        roleList.add(sysMap);
      }
      else if ("agents".equals(type))
      {
        Map<String, Object> sysMap = new HashMap();
        sysMap.put("id", "agents_0");
        sysMap.put("text", "代理系统角色");
        roleList.add(sysMap);
      }
      else if ("org".equals(type))
      {
        Map<String, Object> agentMap = new HashMap();
        agentMap.put("id", "org_0");
        agentMap.put("text", "机构角色");
        roleList.add(agentMap);
        
        Map<String, Object> sysMap = new HashMap();
        sysMap.put("id", "orgs_0");
        sysMap.put("text", "机构系统角色");
        roleList.add(sysMap);
      }
      else if ("orgs".equals(type))
      {
        Map<String, Object> sysMap = new HashMap();
        sysMap.put("id", "orgs_0");
        sysMap.put("text", "机构系统角色");
        roleList.add(sysMap);
      }
      else
      {
        Map<String, Object> norMap = new HashMap();
        norMap.put("id", "nor_0");
        norMap.put("text", "角色");
        roleList.add(norMap);
      }
    }
    else if ("roleuser".equals(listType))
    {
      roleList = this.sysRoleDao.getUserRoleTreeList(map);
      if (roleList == null) {
        roleList = new ArrayList();
      }
      if ("agents".equals(type))
      {
        Map<String, Object> sysMap = new HashMap();
        sysMap.put("id", "agents_0");
        sysMap.put("text", "代理系统角色");
        roleList.add(sysMap);
      }
      else if ("orgs".equals(type))
      {
        Map<String, Object> sysMap = new HashMap();
        sysMap.put("id", "orgs_0");
        sysMap.put("text", "机构系统角色");
        roleList.add(sysMap);
      }
      else
      {
        Map<String, Object> norMap = new HashMap();
        norMap.put("id", "nor_0");
        norMap.put("text", "角色");
        roleList.add(norMap);
      }
    }
    return roleList;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void savePrivilege(HttpServletRequest request, long unitId, long roleId)
  {
    String topMenuIds = request.getParameter("topMenuIds");
    

    clearPrivilegeByTopMenuIds(roleId, topMenuIds);
    
    String checkedData = request.getParameter("checkedData");
    List<Map<String, Object>> dataList = JSONUtil.jsonToListMap(checkedData);
    
    List<SysPrivilegeList> privilegeLists = new ArrayList();
    for (Map<String, Object> dataMap : dataList)
    {
      SysPrivilegeList sysPrivilegeList = new SysPrivilegeList();
      sysPrivilegeList.setUnitId(unitId);
      sysPrivilegeList.setPrivilegeAccess(dataMap.get("type") + "");
      sysPrivilegeList.setPrivilegeAccessValue(Long.valueOf(dataMap.get("id") + "").longValue());
      sysPrivilegeList.setPrivilegeMaster("role");
      sysPrivilegeList.setPrivilegeMasterValue(roleId);
      privilegeLists.add(sysPrivilegeList);
    }
    if (!privilegeLists.isEmpty()) {
      this.sysPrivilegeListDao.addBatch(privilegeLists);
    }
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void saveRoleUser(HttpServletRequest request, long unitId, long roleId)
  {
    String userData = request.getParameter("userData");
    
    String[] userData_array = userData.split(",");
    List<Long> userIds = this.sysDialogService.getMixDistinctPepleIds(userData_array, unitId);
    if ((userIds == null) || (userIds.isEmpty())) {
      return;
    }
    Map<String, Object> map = new HashMap();
    map.put("master", "user");
    map.put("masterValueList", userIds);
    map.put("accessValue", Long.valueOf(roleId));
    map.put("access", "role");
    this.sysPrivilegeListDao.deleteByMasterList(map);
    
    List<SysPrivilegeList> privilegeLists = new ArrayList();
    for (Iterator i$ = userIds.iterator(); i$.hasNext();)
    {
      long userId = ((Long)i$.next()).longValue();
      SysPrivilegeList sysPrivilegeList = new SysPrivilegeList();
      sysPrivilegeList.setUnitId(unitId);
      sysPrivilegeList.setPrivilegeMaster("user");
      sysPrivilegeList.setPrivilegeMasterValue(userId);
      sysPrivilegeList.setPrivilegeAccess("role");
      sysPrivilegeList.setPrivilegeAccessValue(roleId);
      privilegeLists.add(sysPrivilegeList);
      if (privilegeLists.size() > 1000)
      {
        this.sysPrivilegeListDao.addBatch(privilegeLists);
        privilegeLists.clear();
      }
    }
    if (!privilegeLists.isEmpty()) {
      this.sysPrivilegeListDao.addBatch(privilegeLists);
    }
  }
  
  public void removeRoleUser(HttpServletRequest request)
  {
    String privilegeIds = request.getParameter("privilegeIds");
    String[] privilegeId_array = privilegeIds.split(",");
    
    List<Long> privilegeIdList = new ArrayList();
    for (String privilegeIdstr : privilegeId_array) {
      privilegeIdList.add(Long.valueOf(privilegeIdstr));
    }
    if (!privilegeIdList.isEmpty()) {
      this.sysPrivilegeListDao.deleteByIds(privilegeIdList);
    }
  }
  
  public SysRole getSysRole(Map<String, Object> map)
  {
    SysPrivilegeList sysPrivilegeList = this.sysPrivilegeListDao.getSysPrivilegeListByMaster(map);
    if (sysPrivilegeList != null) {
      return get(sysPrivilegeList.getPrivilegeAccessValue());
    }
    return null;
  }
  
  public List<SysRole> getAgentOrgRoleList(Map<String, Object> map)
  {
    return this.sysRoleDao.getAgentOrgRoleList(map);
  }
  
  public SysRole getAgentOrgRole(Map<String, Object> map)
  {
    return this.sysRoleDao.getAgentOrgRole(map);
  }
  
  private void clearPrivilegeByTopMenuIds(long roleId, String topMenuIds)
  {
    if ((null != topMenuIds) && (!"".equals(topMenuIds)))
    {
      Map<String, Object> menuParams = new HashMap();
      menuParams.put("parentIds", topMenuIds);
      List<Long> childMenuIds = this.sysMenuDao.getMenuIdByParentIds(menuParams);
      if ((childMenuIds == null) || (childMenuIds.isEmpty())) {
        childMenuIds = new ArrayList();
      }
      String[] menuId_array = topMenuIds.split(",");
      for (String menuIdStr : menuId_array) {
        childMenuIds.add(Long.valueOf(menuIdStr));
      }
      Map<String, Object> buttonMap = new HashMap();
      buttonMap.put("list", childMenuIds);
      List<Long> buttonIds = this.sysButtonDao.getButtonIdsByMenuIds(buttonMap);
      
      deletePrivilege("role", roleId, "button", buttonIds);
      

      deletePrivilege("role", roleId, "menu", childMenuIds);
    }
  }
  
  private void deletePrivilege(String master, long masterValue, String access, List<Long> accessValueList)
  {
    if ((accessValueList == null) || (accessValueList.isEmpty())) {
      return;
    }
    Map<String, Object> map = new HashMap();
    map.put("master", master);
    map.put("masterValue", Long.valueOf(masterValue));
    map.put("access", access);
    map.put("accessValueList", accessValueList);
    int row = this.sysPrivilegeListDao.deleteByAccessList(map);
    this.logger.info("delete row = " + row);
  }
}
