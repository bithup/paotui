package com.xgh.mng.services;

import com.xgh.mng.dao.ISysDepartmentDao;
import com.xgh.mng.dao.ISysUserDao;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.SelectListKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDialogService")
public class SysDialogServiceImpl
  implements ISysDialogService
{
  @Autowired
  protected ISysUserDao sysUserDao;
  @Autowired
  protected ISysDepartmentDao sysDepartmentDao;
  
  public List<Map<String, Object>> getList(HttpServletRequest request, long unitId, String op)
  {
    List<Map<String, Object>> dataList = new ArrayList();
    
    String deptCode = request.getParameter("deptCode");
    String deptId = request.getParameter("deptId");
    String isCascade = request.getParameter("isCascade");
    String searchName = request.getParameter("searchName");
    if (searchName != null) {
      searchName = "%" + searchName + "%";
    }
    Map<String, Object> map = new HashMap();
    if (deptCode.contains("_")) {
      map.put("deptCode", deptCode + "%");
    } else {
      map.put("deptCode", deptCode + "_%");
    }
    map.put("unitId", Long.valueOf(unitId));
    map.put("isCascade", isCascade);
    map.put("deptId", deptId);
    map.put("searchName", searchName);
    if (op.equals("user")) {
      dataList = this.sysUserDao.getSelectDialogUserList(map);
    } else if (op.equals("dept")) {
      dataList = this.sysDepartmentDao.getSelectDialogDepartmentList(map);
    }
    if (dataList == null) {
      dataList = new ArrayList();
    }
    return dataList;
  }
  
  public Map<String, List<String>> getMixSelectedList(String[] dataArray)
  {
    Map<String, List<String>> map = new HashMap();
    if ((dataArray == null) || (dataArray.length == 0)) {
      return map;
    }
    List<String> pList = new ArrayList();
    List<String> dList = new ArrayList();
    
    splitDeptAndUser(dataArray, pList, dList);
    if (!pList.isEmpty()) {
      map.put(ConstantUtil.SelectListKey.PeopleKey.value(), pList);
    }
    if (!dList.isEmpty()) {
      map.put(ConstantUtil.SelectListKey.DeptKey.value(), dList);
    }
    return map;
  }
  
  public List<Long> getMixDistinctPepleIds(String[] dataArray, long unitId)
  {
    List<Long> pIdList = new ArrayList();
    
    Set<Long> pIdSets = new HashSet();
    if ((dataArray == null) || (dataArray.length == 0)) {
      return pIdList;
    }
    List<String> pList = new ArrayList();
    List<String> dList = new ArrayList();
    
    splitDeptAndUser(dataArray, pList, dList);
    if (!pList.isEmpty()) {
      for (String userIdStr : pList) {
        if (userIdStr != null) {
          pIdSets.add(Long.valueOf(userIdStr));
        }
      }
    }
    Map<String, Object> map = new HashMap();
    map.put("unitId", Long.valueOf(unitId));
    
    List<Long> tempList = new ArrayList();
    if (!dList.isEmpty())
    {
      map.put("list", dList);
      tempList = this.sysDepartmentDao.getUserIdsByDeptCodes(map);
      if (tempList != null) {
        pIdSets.addAll(tempList);
      }
    }
    pIdList.addAll(pIdSets);
    return pIdList;
  }
  
  private void splitDeptAndUser(String[] dataArray, List<String> pList, List<String> dList)
  {
    for (String str : dataArray) {
      if (str.startsWith(ConstantUtil.SelectListKey.PeopleKey.value())) {
        pList.add(str.replaceFirst(ConstantUtil.SelectListKey.PeopleKey.value(), ""));
      } else if (str.startsWith(ConstantUtil.SelectListKey.DeptKey.value())) {
        dList.add(str.replaceFirst(ConstantUtil.SelectListKey.DeptKey.value(), ""));
      }
    }
  }
}
