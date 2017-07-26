package com.xgh.mng.services;

import com.xgh.mng.dao.ISysLogDao;
import com.xgh.mng.entity.SysLog;
import com.xgh.mng.entity.SysUser;
import com.xgh.util.CilentTool;
import com.xgh.util.DateUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysLogService")
public class SysLogServiceImpl
  implements ISysLogService
{
  @Autowired
  protected ISysLogDao sysLogDao;
  
  public int add(SysLog sysLog)
  {
    return this.sysLogDao.add(sysLog);
  }
  
  public int delete(long id)
  {
    return this.sysLogDao.deleteById(id);
  }
  
  public int update(SysLog sysLog)
  {
    return this.sysLogDao.update(sysLog);
  }
  
  public SysLog get(long id)
  {
    return this.sysLogDao.get(id);
  }
  
  public int addLog(HttpServletRequest request, SysUser sysUser, String moduleName, String opType, String opResult, String memo)
  {
    return addLog(request, sysUser, moduleName, opType, opResult, memo, null, null);
  }
  
  public int addLog(HttpServletRequest request, SysUser sysUser, String moduleName, String opType, String opResult, String remark, String businessId, String tableName)
  {
    CilentTool cilentTool = CilentTool.getInstance(request);
    
    SysLog sysLog = new SysLog();
    if (sysUser != null)
    {
      sysLog.setAccount(sysUser.getAccount());
      sysLog.setUnitId(sysUser.getUnitId());
      sysLog.setUserId(sysUser.getId());
      sysLog.setUserName(sysUser.getUserName());
    }
    sysLog.setIp(cilentTool.getIpAddr(request));
    sysLog.setRemark(remark);
    sysLog.setModuleName(moduleName);
    sysLog.setOperateBrowser(cilentTool.getBrowser());
    sysLog.setOperateDate(DateUtil.getSystemTime());
    sysLog.setOperateOs(cilentTool.getOs());
    sysLog.setOperateResult(opResult);
    sysLog.setOperateType(opType);
    sysLog.setBusinessId(businessId);
    sysLog.setTableName(tableName);
    return this.sysLogDao.add(sysLog);
  }
  
  public Map<String, Object> getGridData(HttpServletRequest request, long unitId)
  {
    String page = request.getParameter("page");
    String pagesize = request.getParameter("pagesize");
    String sortname = request.getParameter("sortname");
    String sortorder = request.getParameter("sortorder");
    String configQuery = request.getParameter("configQuery");
    if ((sortname != null) && (!sortname.equals(""))) {
      sortname = sortname.toLowerCase();
    }
    Map<String, Object> map = new HashMap();
    map.put("sortname", sortname);
    map.put("sortorder", sortorder);
    map.put("page", Integer.valueOf(Integer.parseInt(page)));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(pagesize)));
    map.put("configQuery", configQuery);
    map.put("unitId", Long.valueOf(unitId));
    

    Map<String, Object> gridData = new HashMap();
    
    List<Map<String, Object>> dataList = this.sysLogDao.getListMap(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(this.sysLogDao.getListRows(map)));
    return gridData;
  }
}
