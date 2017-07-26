package com.xgh.mng.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.dao.ISysMenuDao;
import com.xgh.mng.dao.ISysPrivilegeListDao;
import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysMenu;
import com.xgh.mng.entity.SysUser;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysMenuService")
public class SysMenuServiceImpl
  extends BaseService
  implements ISysMenuService
{
  @Autowired
  protected ISysMenuDao sysMenuDao;
  @Autowired
  protected ISysPrivilegeListDao sysPrivilegeListDao;
  
  public int add(SysMenu sysMenu)
  {
    return this.sysMenuDao.add(sysMenu);
  }
  
  public int delete(long id)
  {
    return this.sysMenuDao.deleteById(id);
  }
  
  public int deleteByIds(List<Long> ids)
  {
    return this.sysMenuDao.deleteByIds(ids);
  }
  
  public int update(SysMenu sysMenu)
  {
    return this.sysMenuDao.update(sysMenu);
  }
  
  public int save(HttpServletRequest request, SysMenu sysMenu)
  {
    int flg = -1;
    Map<String, Object> params = new HashMap();
    params.put("menuUrl", sysMenu.getMenuUrl());
    if (sysMenu.getId() > 0L) {
      params.put("id", Long.valueOf(sysMenu.getId()));
    }
    long count = 1L;
    if ((sysMenu != null) && (sysMenu.getParentId() != 0L)) {
      count = this.sysMenuDao.getSameMenuCount(params);
    } else {
      count = 0L;
    }
    if (count < 1L)
    {
      Date date = new Date();
      if (sysMenu.getId() < 1L)
      {
        SysIndustry sysIndustry = getCurrentIndustry(request);
        
        sysMenu.setInstId(sysIndustry.getId());
        sysMenu.setInstNid(sysIndustry.getNid());
        sysMenu.setStatus(1);
        sysMenu.setCreateDate(date);
        sysMenu.setUpdateDate(date);
        add(sysMenu);
        
        sysMenu.setNid(Long.parseLong(getOsNO() + sysMenu.getId()));
        flg = update(sysMenu);
      }
      else
      {
        SysMenu sysMenu2 = get(sysMenu.getId());
        sysMenu2.setParentId(sysMenu.getParentId());
        sysMenu2.setMenuName(sysMenu.getMenuName());
        sysMenu2.setMenuUrl(sysMenu.getMenuUrl());
        sysMenu2.setMenuIcon(sysMenu.getMenuIcon());
        sysMenu2.setIsGrid(sysMenu.getIsGrid());
        sysMenu2.setIsMain(sysMenu.getIsMain());
        sysMenu2.setIsQuery(sysMenu.getIsQuery());
        sysMenu2.setIsSys(sysMenu.getIsSys());
        sysMenu2.setIsValidate(sysMenu.getIsValidate());
        sysMenu2.setIsVisible(sysMenu.getIsVisible());
        sysMenu2.setOrd(sysMenu.getOrd());
        sysMenu2.setRemark(sysMenu.getRemark());
        sysMenu2.setUpdateDate(date);
        flg = update(sysMenu2);
      }
    }
    else
    {
      flg = -2;
    }
    return flg;
  }
  
  public SysMenu get(long id)
  {
    return this.sysMenuDao.get(id);
  }
  
  public List<SysMenu> getList(SysMenu sysMenu)
  {
    List<SysMenu> list = this.sysMenuDao.getList(sysMenu);
    


    return list;
  }
  
  public List<SysMenu> getListPage(Map<String, Object> map)
  {
    return this.sysMenuDao.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.sysMenuDao.getRows(map);
  }
  
  public List<Map<String, Object>> getUserMenuTreeData(long sysRoleId, SysUser sysUser)
  {
    List<Long> roleIds = this.sysPrivilegeListDao.getRoleIdsByUserId(sysUser.getId());
    
    List<Map<String, Object>> menuList = new ArrayList();
    if ((roleIds != null) && (!roleIds.isEmpty()))
    {
      Map<String, Object> params = new HashMap();
      params.put("uniIid", Long.valueOf(sysUser.getUnitId()));
      params.put("roleList", roleIds);
      params.put("sysRoleId", Long.valueOf(sysRoleId));
      menuList = this.sysMenuDao.getUserMenuTreeData(params);
    }
    if (menuList == null) {
      menuList = new ArrayList();
    }
    return menuList;
  }
  
  public List<Map<String, Object>> getSysMenuTreeData(SysUser sysUser)
  {
    List<Map<String, Object>> menuList = new ArrayList();
    
    Map<String, Object> map = new HashMap();
    if (sysUser.getUnitId() == 1L)
    {
      if ("SuperAdmin".equals(sysUser.getAccount()))
      {
        map.put("type", "inst");
      }
      else
      {
        List<Long> roleIds = this.sysPrivilegeListDao.getRoleIdsByUserId(sysUser.getId());
        map.put("type", "instnor");
        map.put("roleList", roleIds);
      }
    }
    else if (("admin".equals(sysUser.getAccount())) && (sysUser.getIsSys() == 1L))
    {
      map.put("type", "unit");
      map.put("sysRoleId", Long.valueOf(this.sysPrivilegeListDao.getSysRoleIdByUnitId(sysUser.getUnitId())));
    }
    return this.sysMenuDao.getSysMenuTreeData(map);
  }
  
  public Map<String, Object> getGridData(Map<String, Object> map)
  {
    Map<String, Object> gridData = new HashMap();
    
    List<SysMenu> dataList = this.sysMenuDao.getListPage(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Integer.valueOf(dataList.size()));
    return gridData;
  }
  
  public boolean isHasChildMenu(long id)
  {
    return this.sysMenuDao.getChildMenuRows(id) > 0L;
  }
  
  public List<SysMenu> getParentMenus(long nid)
  {
    return this.sysMenuDao.getParentMenus(nid);
  }
  
  public List<Map<String, Object>> getTreeData()
  {
    Map<String, Object> map = new HashMap();
    map.put("pid", Integer.valueOf(0));
    List<Map<String, Object>> treeMaps = this.sysMenuDao.getTreeData(map);
    if (treeMaps == null) {
      treeMaps = new ArrayList();
    }
    Map<String, Object> rootMap = new HashMap();
    rootMap.put("id", Integer.valueOf(0));
    rootMap.put("text", "菜单");
    treeMaps.add(rootMap);
    return treeMaps;
  }
  
  public List<Map<String, Object>> getButtonMenuTreeData()
  {
    Map<String, Object> map = new HashMap();
    List<Map<String, Object>> treeMaps = this.sysMenuDao.getTreeData(map);
    if (treeMaps == null) {
      treeMaps = new ArrayList();
    }
    return treeMaps;
  }
  
  public List<Map<String, Object>> getGridMenuTreeData()
  {
    return getMenuTreeData("ifGrid");
  }
  
  public List<Map<String, Object>> getQueryMenuTreeData()
  {
    return getMenuTreeData("ifQuery");
  }
  
  public List<Map<String, Object>> getValidationMenuTreeData()
  {
    return getMenuTreeData("ifValidate");
  }
  
  private List<Map<String, Object>> getMenuTreeData(String key)
  {
    Map<String, Object> map = new HashMap();
    map.put(key, Integer.valueOf(1));
    List<Map<String, Object>> treeMaps = this.sysMenuDao.getTreeData(map);
    if (treeMaps == null) {
      treeMaps = new ArrayList();
    }
    return treeMaps;
  }
  
  public SysMenu getSysMenuByRequest(HttpServletRequest request)
  {
    Map<String, Object> map = new HashMap();
    map.put("menuUrl", getRequestUrl(request));
    List<SysMenu> menuList = this.sysMenuDao.getMenuByUrl(map);
    return (menuList != null) && (!menuList.isEmpty()) ? (SysMenu)menuList.get(0) : null;
  }
  
  private String getRequestUrl(HttpServletRequest request)
  {
    String requestUrl = request.getRequestURI();
    

    String contextPath = request.getContextPath();
    if (null != contextPath) {
      requestUrl = requestUrl.substring(contextPath.length() + 1);
    }
    String urlParams = "";
    
    Enumeration<String> names = request.getParameterNames();
    while (names.hasMoreElements())
    {
      String name = (String)names.nextElement();
      String value = request.getParameter(name);
      if (urlParams.equals("")) {
        urlParams = name + "=" + value;
      } else {
        urlParams = urlParams + "&" + name + "=" + value;
      }
    }
    if (!urlParams.equals("")) {
      requestUrl = requestUrl + "?" + urlParams;
    }
    return requestUrl;
  }
}
