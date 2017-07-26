package com.xgh.mng.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.dao.ISysDeptUserDao;
import com.xgh.mng.dao.ISysUnitsDao;
import com.xgh.mng.dao.ISysUserDao;
import com.xgh.mng.entity.SysDepartment;
import com.xgh.mng.entity.SysDeptUser;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.SysUser;
import com.xgh.security.MD5Util;
import com.xgh.util.DateUtil;
import com.xgh.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserService")
public class SysUserServiceImpl
  extends BaseService
  implements ISysUserService
{
  private static Logger logger = Logger.getLogger(SysUserServiceImpl.class);
  @Autowired
  protected ISysUserDao sysUserDao;
  @Autowired
  protected ISysDeptUserService sysDeptUserService;
  @Autowired
  protected ISysDepartmentService sysDepartmentService;
  @Autowired
  protected ISysDeptUserDao sysDeptUserDao;
  @Autowired
  protected ISysLogService sysLogService;
  @Autowired
  protected ISysUnitsDao sysUnitsDao;
  
  public int add(SysUser sysUser)
  {
    return this.sysUserDao.add(sysUser);
  }
  
  public int delete(long id)
  {
    return this.sysUserDao.deleteById(id);
  }
  
  public int update(SysUser sysUser)
  {
    return this.sysUserDao.update(sysUser);
  }
  
  public SysUser get(long id)
  {
    return this.sysUserDao.get(id);
  }
  
  public Map<String, Object> getGridData(HttpServletRequest request, long deptId)
  {
    String page = request.getParameter("page");
    String pagesize = request.getParameter("pagesize");
    String sortname = request.getParameter("sortname");
    String sortorder = request.getParameter("sortorder");
    

    String isCascade = request.getParameter("isCascade");
    String deptCode = request.getParameter("deptCode");
    String userName = request.getParameter("userName");
    if ((sortname != null) && (!sortname.equals(""))) {
      if (sortname.equals("account")) {
        sortname = "u.account";
      } else if (sortname.equals("ord")) {
        sortname = "du.ord";
      } else {
        sortname = "du.ord";
      }
    }
    Map<String, Object> map = new HashMap();
    map.put("isCascade", isCascade);
    map.put("sortname", sortname);
    map.put("sortorder", sortorder);
    map.put("page", Integer.valueOf(Integer.parseInt(page)));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(pagesize)));
    map.put("deptId", Long.valueOf(deptId));
    if (deptCode.contains("_")) {
      map.put("deptCode", deptCode + "%");
    } else {
      map.put("deptCode", deptCode + "_%");
    }
    if (StringUtils.isNotBlank(userName)) {
      map.put("userName", "%" + userName + "%");
    }
    Map<String, Object> gridData = new HashMap();
    
    List<Map<String, Object>> dataList = this.sysUserDao.getListMap(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(this.sysUserDao.getListRows(map)));
    return gridData;
  }
  
  public void save(SysUser sysUser, SysDeptUser sysDeptUser, String op)
  {
    if (op.equals("add"))
    {
      String pwd = "123456";
      
      sysUser.setPassword(MD5Util.getMD5(pwd));
      Date date = new Date();
      sysUser.setCreateDate(date);
      sysUser.setUpdateDate(date);
      
      this.sysUserDao.add(sysUser);
      
      sysDeptUser.setUserId(sysUser.getId());
      SysDepartment sysDepartment = this.sysDepartmentService.get(sysDeptUser.getDeptId());
      sysDeptUser.setDeptCode(sysDepartment.getDeptCode());
      
      this.sysDeptUserService.add(sysDeptUser);
      if (!StringUtils.isNotBlank(sysUser.getMobile())) {}
    }
    else
    {
      SysUser sysUser2 = this.sysUserDao.get(sysUser.getId());
      sysUser2.setAccount(sysUser.getAccount());
      sysUser2.setEmail(sysUser.getEmail());
      sysUser2.setStatus(sysUser.getStatus());
      sysUser2.setData1(sysUser.getData1());
      sysUser2.setData2(sysUser.getData2());
      sysUser2.setData3(sysUser.getData3());
      sysUser2.setUserName(sysUser.getUserName());
      sysUser2.setUserCord(sysUser.getUserCord());
      sysUser2.setUpdateDate(DateUtil.getSystemTime());
      sysUser2.setMobile(sysUser.getMobile());
      sysUser2.setUpdateDate(new Date());
      sysUser2.setOpenCityId(sysUser.getOpenCityId());
      sysUser2.setOpenCityName(sysUser.getOpenCityName());
      this.sysUserDao.update(sysUser2);
      
      SysDeptUser sysDeptUser2 = this.sysDeptUserService.get(sysDeptUser.getId());
      sysDeptUser2.setOrd(sysDeptUser.getOrd());
      sysDeptUser2.setUserLevel(sysDeptUser.getUserLevel());
      this.sysDeptUserService.update(sysDeptUser2);
    }
  }
  
  public boolean isHasSameAcount(SysUser sysUser, String op)
  {
    Map<String, Object> map = new HashMap();
    map.put("account", sysUser.getAccount());
    map.put("unitId", Long.valueOf(sysUser.getUnitId()));
    if (op.equals("modify")) {
      map.put("id", Long.valueOf(sysUser.getId()));
    }
    return this.sysUserDao.getSameAcountRows(map) > 0L;
  }
  
  public boolean isLastDeptForUser(long userId)
  {
    Map<String, Object> map = new HashMap();
    map.put("userId", Long.valueOf(userId));
    
    return this.sysUserDao.getDeptUserRowsByUserId(map) <= 1L;
  }
  
  public SysUser getLoginCheckUser(String account, String password)
  {
    Map<String, Object> params = new HashMap();
    if ((!StringUtil.isEmpty(account)) && (!StringUtil.isEmpty(password)))
    {
      String[] ss = { account, "0" };
      if (account.contains("@")) {
        ss = account.split("@");
      }
      params.put("unitCode", ss[1]);
      params.put("account", ss[0]);
      
      List<SysUser> sysUserList = this.sysUserDao.getLoginCheckUserList(params);
      if ((sysUserList != null) && (!sysUserList.isEmpty())) {
        return (SysUser)sysUserList.get(0);
      }
      return null;
    }
    return null;
  }
  
  public void move(HttpServletRequest request, String op)
  {
    if (op.equals("in"))
    {
      String deptId = request.getParameter("deptId");
      String deptCode = request.getParameter("deptCode");
      String userIds = request.getParameter("userIds");
      String[] userId_arrray = userIds.split(",");
      if (userId_arrray.length == 0) {
        return;
      }
      Map<String, Object> params = new HashMap();
      params.put("deptId", deptId);
      params.put("userIds", userIds);
      this.sysDeptUserDao.deleteByDeptIdAndUserIds(params);
      

      SysDepartment sysDepartment = this.sysDepartmentService.get(Long.valueOf(deptId).longValue());
      SysUnits sysUnits = this.sysUnitsDao.get(sysDepartment.getUnitId());
      

      List<SysDeptUser> sysDeptUsers = new ArrayList();
      for (String userId : userId_arrray)
      {
        SysDeptUser sysDeptUser = new SysDeptUser();
        sysDeptUser.setDeptCode(deptCode);
        sysDeptUser.setDeptId(Long.valueOf(deptId).longValue());
        sysDeptUser.setOrd(0L);
        sysDeptUser.setUserId(Long.valueOf(userId).longValue());
        sysDeptUser.setUserLevel(0L);
        sysDeptUsers.add(sysDeptUser);
      }
      this.sysDeptUserDao.addBatch(sysDeptUsers);
    }
    else
    {
      String deptUserId = request.getParameter("deptUserId");
      this.sysDeptUserDao.deleteById(Long.valueOf(deptUserId).longValue());
    }
  }
  
  public List<SysUser> getListByUnitId(long unitId)
  {
    SysUser sysUser = new SysUser();
    sysUser.setUnitId(unitId);
    return this.sysUserDao.getList(sysUser);
  }
  
  public SysUser getAdmin(long unitId)
  {
    return this.sysUserDao.getAdmin(unitId);
  }
}
