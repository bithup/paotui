package com.xgh.mng.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.dao.ISysPrivilegeListDao;
import com.xgh.mng.dao.ISysUnitsDao;
import com.xgh.mng.entity.FileData;
import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysPrivilegeList;
import com.xgh.mng.entity.SysRole;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.SysUser;
import com.xgh.security.MD5Util;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.FileUploadCode;
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

@Service("sysUnitsService")
public class SysUnitsServiceImpl
  extends BaseService
  implements ISysUnitsService
{
  private static final Logger logger = Logger.getLogger(SysUnitsServiceImpl.class);
  @Autowired
  protected ISysUnitsDao sysUnitsDao;
  @Autowired
  protected ISysUserService sysUserService;
  @Autowired
  protected ISysPrivilegeListDao sysPrivilegeListDao;
  @Autowired
  protected IFileDataService fileDataService;
  @Autowired
  protected ISysRoleService sysRoleService;
  
  public int add(SysUnits sysUnits)
  {
    return this.sysUnitsDao.add(sysUnits);
  }
  
  public int delete(long id)
  {
    return this.sysUnitsDao.deleteById(id);
  }
  
  public int update(SysUnits sysUnits)
  {
    return this.sysUnitsDao.update(sysUnits);
  }
  
  public SysUnits get(long id)
  {
    return this.sysUnitsDao.get(id);
  }
  
  public Map<String, Object> getGridData(HttpServletRequest request)
  {
    long parentId = Long.parseLong(request.getParameter("parentId"));
    String page = request.getParameter("page");
    String pagesize = request.getParameter("pagesize");
    String op = request.getParameter("op");
    String configQuery = request.getParameter("configQuery");
    String kindType = request.getParameter("kindType");
    
    Map<String, Object> map = new HashMap();
    map.put("instId", Long.valueOf(getCurrentInstId(request)));
    map.put("unitId", Long.valueOf(getCurrentUnitId(request)));
    map.put("page", Integer.valueOf(Integer.parseInt(page)));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(pagesize)));
    map.put("parentId", Long.valueOf(parentId));
    map.put("configQuery", configQuery);
    map.put("kindType", kindType);
    
    Map<String, Object> gridData = new HashMap();
    
    List<SysUnits> dataList = new ArrayList();
    long totalRows = 0L;
    if (("init".equals(op)) || ("initorg".equals(op)))
    {
      dataList = this.sysUnitsDao.getUnitListMap(map);
      totalRows = this.sysUnitsDao.getUnitListRows(map);
    }
    else if (("check".equals(op)) || ("checkorg".equals(op)))
    {
      map.put("parentUnitId", Long.valueOf(parentId));
      
      dataList = this.sysUnitsDao.getUnCheckListMap(map);
      totalRows = this.sysUnitsDao.getUnCheckListRows(map);
    }
    if ((null == dataList) || (dataList.isEmpty())) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(totalRows));
    return gridData;
  }
  
  public List<Map<String, Object>> getUnitTreeData(HttpServletRequest request)
  {
    List<Map<String, Object>> dataList = new ArrayList();
    Map<String, Object> paramsMap = new HashMap();
    paramsMap.put("instId", Long.valueOf(getCurrentInstId(request)));
    
    paramsMap.put("parentId", request.getParameter("parentId"));
    dataList = this.sysUnitsDao.getUnitTreeData(paramsMap);
    if ((null == dataList) || (dataList.isEmpty())) {
      dataList = new ArrayList();
    }
    return dataList;
  }
  
  public List<Map<String, Object>> getUnitTreeDataByProvinceId(long unitId)
  {
    List<Map<String, Object>> datalist = new ArrayList();
    Map<String, Object> paramsMap = new HashMap();
    if (1L != unitId)
    {
      paramsMap.put("unitId", Long.valueOf(unitId));
      datalist = this.sysUnitsDao.getUnitTreeData(paramsMap);
      paramsMap.remove("unitId");
      paramsMap.put("partentId", Long.valueOf(unitId));
      List<Map<String, Object>> list = this.sysUnitsDao.getUnitTreeData(paramsMap);
      datalist.addAll(list);
      if ((null != list) && (!list.isEmpty())) {
        for (Map<String, Object> map : list)
        {
          paramsMap.put("partentId", map.get("id"));
          List<Map<String, Object>> shoplist = this.sysUnitsDao.getUnitTreeDataByProvinceId(paramsMap);
          datalist.addAll(shoplist);
        }
      }
    }
    return datalist;
  }
  
  public List<Map<String, Object>> getUnitTreeData(long instId)
  {
    Map<String, Object> paramMap = new HashMap();
    paramMap.put("instId", Long.valueOf(instId));
    
    List<Map<String, Object>> list = this.sysUnitsDao.getUnitTreeData(paramMap);
    if (list == null) {
      list = new ArrayList();
    }
    return list;
  }
  
  public Map<String, Object> save(HttpServletRequest request, SysUnits sysUnits, String op)
  {
    Map<String, Object> resultMap = new HashMap();
    try
    {
      long instId = getCurrentInstId(request);
      if ((op.equals("add")) || (op.equals("modify")))
      {
        sysUnits.setInstId(instId);
        if (((sysUnits.getUnitType() == 2) || (sysUnits.getUnitType() == 3)) && 
          (isHasSameZoneCode(op, sysUnits)))
        {
          resultMap.put("code", "0");
          resultMap.put("msg", "单位区域编码已使用!");
          
          return resultMap;
        }
        Map<String, Object> map = new HashMap();
        
        map.put("unitDomain", sysUnits.getUnitDomain());
        if (isHasSame(op, sysUnits, map))
        {
          resultMap.put("code", "0");
          resultMap.put("msg", "该域名已使用,请重新添加!");
          
          return resultMap;
        }
        map.clear();
        

        map.put("unitName", sysUnits.getUnitName());
        if (isHasSame(op, sysUnits, map))
        {
          resultMap.put("code", Integer.valueOf(0));
          resultMap.put("msg", "该单位名称已使用,请重新添加!");
          
          return resultMap;
        }
        map.clear();
      }
      if (op.equals("add"))
      {
        sysUnits.setInstId(instId);
        sysUnits.setInstCode(getCurrentIndustry(request).getCode());
        sysUnits.setUnitCode(sysUnits.getUnitDomain());
        
        sysUnits.setStatus(1);
        Date date = new Date();
        sysUnits.setRegDate(date);
        sysUnits.setCreateDate(date);
        sysUnits.setUpdateDate(date);
        sysUnits.setUnitStatus("register");
        
        sysUnits.setVersion(1);
        add(sysUnits);
        addFileData(request, sysUnits);
      }
      else if (op.equals("modify"))
      {
        SysUnits sysUnits2 = get(sysUnits.getId());
        sysUnits.setUnitStatus(sysUnits2.getUnitStatus());
        
        sysUnits.setUnitCode(sysUnits.getUnitDomain());
        sysUnits.setRegDate(sysUnits2.getRegDate());
        sysUnits.setCreateDate(sysUnits2.getCreateDate());
        sysUnits.setUpdateDate(new Date());
        sysUnits.setStatus(sysUnits2.getStatus());
        
        sysUnits.setVersion(sysUnits2.getVersion() + 1);
        if ("register".equals(sysUnits2.getUnitStatus())) {
          sysUnits.setUnitStatus("register");
        } else if (sysUnits2.getUnitType() != sysUnits.getUnitType()) {
          sysUnits.setUnitStatus("rechange");
        }
        update(sysUnits);
        
        updateUnitUser(sysUnits);
        
        addFileData(request, sysUnits);
      }
      else if (op.equals("check"))
      {
        checkUnit(request, sysUnits, getCurrentUser(request));
      }
      resultMap.put("code", "1");
      resultMap.put("msg", "保存成功！");
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      resultMap.put("code", "0");
      resultMap.put("msg", "保存失败！");
    }
    return resultMap;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public void checkUnit(HttpServletRequest request, SysUnits sysUnits, SysUser loginSysUser)
  {
    Map<String, Object> sysRoleMap = new HashMap();
    sysRoleMap.put("instId", Long.valueOf(sysUnits.getInstId()));
    sysRoleMap.put("kindType", request.getParameter("kindType"));
    sysRoleMap.put("unitType", Integer.valueOf(sysUnits.getUnitType()));
    String unitRoleId = this.sysRoleService.getAgentOrgRole(sysRoleMap).getId() + "";
    
    String unitStatus = sysUnits.getUnitStatus();
    SysUnits sysUnits2 = this.sysUnitsDao.get(sysUnits.getId());
    
    sysUnits2.setUnitType(sysUnits.getUnitType());
    if (sysUnits2.getUnitStatus().equals("register"))
    {
      sysUnits2.setUnitStatus(unitStatus);
      this.sysUnitsDao.update(sysUnits2);
      if (unitStatus.equals("pass"))
      {
        SysUser sysUser = new SysUser();
        sysUser.setAccount("admin");
        sysUser.setUserName("admin");
        sysUser.setUnitId(sysUnits.getId());
        sysUser.setUnitCode(sysUnits2.getUnitDomain());
        sysUser.setInstId(sysUnits2.getInstId());
        sysUser.setInstCode(sysUnits2.getInstCode());
        sysUser.setInstNid(sysUnits2.getInstNid());
        
        sysUser.setMobile(sysUnits2.getLegalPersonMobile());
        sysUser.setStatus(1);
        sysUser.setIsSys(1L);
        Date date = new Date();
        sysUser.setCreateDate(date);
        sysUser.setUpdateDate(date);
        
        sysUser.setPassword(MD5Util.getMD5("123456"));
        this.sysUserService.add(sysUser);
        























        SysPrivilegeList unitPrivilege = new SysPrivilegeList();
        unitPrivilege.setUnitId(sysUnits.getId());
        unitPrivilege.setPrivilegeMaster("unit");
        unitPrivilege.setPrivilegeMasterValue(sysUnits.getId());
        unitPrivilege.setPrivilegeAccess("role");
        unitPrivilege.setPrivilegeAccessValue(Long.parseLong(unitRoleId));
        this.sysPrivilegeListDao.add(unitPrivilege);
        
        SysPrivilegeList userPrivilege = new SysPrivilegeList();
        userPrivilege.setUnitId(sysUnits.getId());
        userPrivilege.setPrivilegeMaster("user");
        userPrivilege.setPrivilegeMasterValue(sysUser.getId());
        userPrivilege.setPrivilegeAccess("role");
        userPrivilege.setPrivilegeAccessValue(Long.parseLong(unitRoleId));
        this.sysPrivilegeListDao.add(userPrivilege);
      }
    }
    else if ((sysUnits2.getUnitStatus().equals("rechange")) && (unitStatus.equals("pass")))
    {
      sysUnits2.setUnitStatus(unitStatus);
      this.sysUnitsDao.update(sysUnits2);
      
      Map<String, Object> map = new HashMap();
      map.put("unitId", Long.valueOf(sysUnits2.getId()));
      map.put("master", "unit");
      map.put("masterValue", Long.valueOf(sysUnits2.getId()));
      

      SysPrivilegeList userPrivilege = this.sysPrivilegeListDao.getSysPrivilegeListByMaster(map);
      userPrivilege.setPrivilegeAccessValue(Long.parseLong(unitRoleId));
      this.sysPrivilegeListDao.update(userPrivilege);
      

      map.put("master", "user");
      map.put("masterValue", Long.valueOf(this.sysUserService.getAdmin(sysUnits.getId()).getId()));
      userPrivilege = this.sysPrivilegeListDao.getSysPrivilegeListByMaster(map);
      userPrivilege.setPrivilegeAccessValue(Long.parseLong(unitRoleId));
      this.sysPrivilegeListDao.update(userPrivilege);
    }
    else
    {
      sysUnits2.setUnitStatus(unitStatus);
      this.sysUnitsDao.update(sysUnits2);
    }
  }
  
  public List<Map<String, Object>> getChildZoneList(HttpServletRequest request, String code)
  {
    Map<String, Object> map = new HashMap();
    map.put("pcode", code);
    
    List<Map<String, Object>> list = this.sysUnitsDao.getChildZoneListByPcode(map);
    if (list == null) {
      list = new ArrayList();
    }
    return list;
  }
  
  public List<Map<String, Object>> getFirstZoneList()
  {
    Map<String, Object> map = new HashMap();
    return this.sysUnitsDao.getChildZoneListByPcode(map);
  }
  
  public List<Map<String, Object>> getZoneListByPCode(String pcode)
  {
    Map<String, Object> map = new HashMap();
    map.put("pcode", pcode);
    return this.sysUnitsDao.getChildZoneListByPcode(map);
  }
  
  public List<Map<String, Object>> getChildZoneListByPid(long pid)
  {
    return this.sysUnitsDao.getChildZoneListByPid(pid);
  }
  
  public String getZonePrefixByCode(String code)
  {
    return this.sysUnitsDao.getZonePrefixByCode(code);
  }
  
  public boolean isHasSameZoneCode(String op, SysUnits sysUnits)
  {
    Map<String, Object> map = new HashMap();
    map.put("zoneCode", sysUnits.getZoneCode());
    map.put("instId", Long.valueOf(sysUnits.getInstId()));
    if (op.equals("modify")) {
      map.put("id", Long.valueOf(sysUnits.getId()));
    }
    return this.sysUnitsDao.getUnitRowsByZoneCode(map) > 0L;
  }
  
  public boolean isHasSame(String op, SysUnits sysUnits, Map<String, Object> map)
  {
    if (op.equals("modify")) {
      map.put("id", Long.valueOf(sysUnits.getId()));
    }
    List<Map<String, Object>> list = this.sysUnitsDao.getUnitRowsBySame(map);
    if ((null != list) && (list.size() > 0)) {
      return true;
    }
    return false;
  }
  
  public List<Map<String, Object>> getUnitFileTypeList()
  {
    List<Map<String, Object>> list = new ArrayList();
    Map<String, Object> map1 = new HashMap();
    
    map1.put("type", "1");
    map1.put("name", "营业执照");
    map1.put("flag", "image");
    
    Map<String, Object> map2 = new HashMap();
    map2.put("type", "2");
    map2.put("name", "身份证");
    map2.put("flag", "image");
    
    list.add(map1);
    list.add(map2);
    return list;
  }
  
  public SysUnits getUnitByZoneCode(String zoneCode)
  {
    Map<String, Object> paraMap = new HashMap();
    paraMap.put("zoneCode", zoneCode);
    return this.sysUnitsDao.getUnitByZoneCode(paraMap);
  }
  
  public List<Map<String, Object>> getZoneListOnChange(HttpServletRequest request)
  {
    List<Map<String, Object>> zoneList = new ArrayList();
    String zoneLevel = request.getParameter("zoneLevel");
    String zoneCode = request.getParameter("zoneCode");
    
    Map<String, Object> paraMap = new HashMap();
    if ("4".equals(zoneLevel))
    {
      paraMap.put("zoneCode", zoneCode);
      zoneList = this.sysUnitsDao.getMunicipalAgentZoneList(paraMap);
    }
    else if ("5".equals(zoneLevel))
    {
      paraMap.put("zoneCode", zoneCode + "%");
      zoneList = this.sysUnitsDao.getCountyAgentZoneList(paraMap);
    }
    return zoneList;
  }
  
  public List<Map<String, Object>> getAreaList(Map<String, Object> map)
  {
    SysUnits sysUnits = (SysUnits)map.get("sysUnits");
    String queryFlag = (String)map.get("queryFlag");
    

    List<Map<String, Object>> zoneList = new ArrayList();
    
    Map<String, Object> paraMap = new HashMap();
    if ("parent".equals(queryFlag))
    {
      paraMap.put("id", Long.valueOf(sysUnits.getParentId()));
      zoneList = this.sysUnitsDao.getAreaList(paraMap);
    }
    else if ("current".equals(queryFlag))
    {
      paraMap.put("id", Long.valueOf(sysUnits.getId()));
      zoneList = this.sysUnitsDao.getAreaList(paraMap);
    }
    return zoneList;
  }
  
  public long updateUnitUser(SysUnits sysUnits)
  {
    if (sysUnits != null)
    {
      List<SysUser> list = this.sysUserService.getListByUnitId(sysUnits.getId());
      if ((list != null) && (!list.isEmpty())) {
        for (SysUser sysUser : list) {
          if (!sysUnits.getUnitCode().equals(sysUser.getUnitCode()))
          {
            sysUser.setUnitCode(sysUnits.getUnitCode());
            this.sysUserService.update(sysUser);
          }
        }
      }
    }
    return 0L;
  }
  
  private boolean addFileData(HttpServletRequest request, SysUnits sysUnits)
  {
    String[] logoData_array = request.getParameterValues("logoData");
    String[] fileData_array = request.getParameterValues("fileData");
    

    List<FileData> fileDataList = this.fileDataService.saveFiles(request, logoData_array, sysUnits.getId(), ConstantUtil.FileUploadCode.Unit, sysUnits.getVersion());
    if ((fileDataList != null) && (fileDataList.size() > 0))
    {
      FileData fileData = (FileData)fileDataList.get(fileDataList.size() - 1);
      sysUnits.setLogoPath("/" + fileData.getRelativePath() + "/" + fileData.getFileName());
      this.sysUnitsDao.update(sysUnits);
    }
    this.fileDataService.saveFiles(request, fileData_array, sysUnits.getId(), ConstantUtil.FileUploadCode.Unit, sysUnits.getVersion());
    
    return true;
  }
}
