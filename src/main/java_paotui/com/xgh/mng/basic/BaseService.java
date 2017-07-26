package com.xgh.mng.basic;

import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.SysUser;
import com.xgh.mng.services.IOsNoteService;
import com.xgh.mng.services.ISysCacheService;
import javax.servlet.http.HttpServletRequest;

import com.xgh.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BaseService
{
  @Autowired
  protected IOsNoteService osNoteService;
  @Autowired
  protected ISysCacheService sysCacheService;
  
  protected String getOsNO()
  {
    return "101000";
  }
  
  protected SysIndustry getCurrentIndustry(HttpServletRequest request)
  {
    return this.sysCacheService.getCurrentIndustry(request);
  }
  
  protected long getCurrentInstId(HttpServletRequest request)
  {
    return this.sysCacheService.getCurrentInstId(request);
  }
  
  protected SysUnits getCurrentUnits(HttpServletRequest request)
  {
    return this.sysCacheService.getCurrentUnit(request);
  }
  
  protected long getCurrentUnitId(HttpServletRequest request)
  {
    return this.sysCacheService.getCurrentUnitId(request);
  }
  
  protected long getCurrentSysRoleId(HttpServletRequest request)
  {
    return this.sysCacheService.getCurrentSysRoleId(request);
  }
  
  protected long getCurrentUserId(HttpServletRequest request)
  {
    return this.sysCacheService.getCurrentUserId(request);
  }
  
  protected SysUser getCurrentUser(HttpServletRequest request)
  {
    return this.sysCacheService.getCurrentUser(request);
  }

  /**
   * 获取返回的resultMap
   *
   * @param flg
   * @param msg
   * @return
   */
  protected Map<String, Object> getResultMap(Object flg, Object msg) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put(ConstantUtil.ResultKey.resultFlg.value(), flg);
    resultMap.put(ConstantUtil.ResultKey.resultMsg.value(), msg);
    return resultMap;
  }

  protected Map<String, Object> getResultMap(Object flg, Object msg, Object data) {
    Map<String, Object> resultMap = getResultMap(flg, msg);

    resultMap.put(ConstantUtil.ResultKey.resultData.value(), data);

    return resultMap;
  }
}
