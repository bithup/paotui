package com.xgh.mng.services;

import com.xgh.mng.dao.ISysTypeDao;
import com.xgh.mng.dao.ISysValidationDao;
import com.xgh.mng.entity.SysMenu;
import com.xgh.mng.entity.SysType;
import com.xgh.mng.entity.SysValidation;
import com.xgh.mng.util.ValidationUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysValidationService")
public class SysValidationServiceImpl
  implements ISysValidationService
{
  @Autowired
  protected ISysValidationDao sysValidationDao;
  @Autowired
  protected ISysTypeDao sysTypeDao;
  @Autowired
  protected ISysMenuService sysMenuService;
  
  public int add(SysValidation sysValidation)
  {
    return this.sysValidationDao.add(sysValidation);
  }
  
  public int delete(long id)
  {
    return this.sysValidationDao.deleteById(id);
  }
  
  public int update(SysValidation sysValidation)
  {
    return this.sysValidationDao.update(sysValidation);
  }
  
  public SysValidation get(long id)
  {
    return this.sysValidationDao.get(id);
  }
  
  public Map<String, Object> getGridData(Map<String, Object> map)
  {
    Map<String, Object> gridData = new HashMap();
    
    List<SysValidation> dataList = this.sysValidationDao.getList(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Integer.valueOf(dataList.size()));
    return gridData;
  }
  
  public void saveValidation(long joinId, String joinType, List<Map<String, Object>> dataList)
  {
    Map<String, Object> params = new HashMap();
    params.put("joinId", Long.valueOf(joinId));
    params.put("joinType", joinType);
    
    List<SysValidation> validationList = new ArrayList();
    if ((dataList != null) && (!dataList.isEmpty()))
    {
      for (Map<String, Object> dataMap : dataList)
      {
        SysValidation sysValidation = new SysValidation();
        sysValidation.setJoinId(joinId);
        sysValidation.setJoinType(joinType);
        sysValidation.setField(dataMap.get("field") + "");
        sysValidation.setMsg(dataMap.get("msg") + "");
        sysValidation.setRuleType(dataMap.get("ruleType") + "");
        sysValidation.setRuleTypeValue(dataMap.get("ruleTypeValue") + "");
        if ((dataMap.get("status") != null) && (!dataMap.get("status").equals(""))) {
          sysValidation.setStatus(Integer.valueOf(dataMap.get("status") + "").intValue());
        }
        Date date = new Date();
        sysValidation.setCreateDate(date);
        sysValidation.setUpdateDate(date);
        sysValidation.setStatus(1);
        validationList.add(sysValidation);
      }
      this.sysValidationDao.deleteByJoinType(params);
      this.sysValidationDao.addBatch(validationList);
    }
  }
  
  public String getValidationByRequest(HttpServletRequest request)
  {
    StringBuffer dataBuffer = new StringBuffer("");
    SysMenu sysMenu = this.sysMenuService.getSysMenuByRequest(request);
    if ((sysMenu != null) && (sysMenu.getIsValidate() == 1))
    {
      List<SysValidation> validations = getListByJoinType(sysMenu.getId(), "menu");
      ValidationUtil.addValidationDataToBuffer(dataBuffer, validations);
    }
    return dataBuffer.toString();
  }
  
  public String getValidationByCode(String code)
  {
    StringBuffer dataBuffer = new StringBuffer("");
    
    Map<String, Object> params = new HashMap();
    params.put("code", code);
    params.put("type", "validate");
    SysType sysType = this.sysTypeDao.getSysTypeByCode(params);
    if (sysType != null)
    {
      List<SysValidation> validations = getListByJoinType(sysType.getId(), "validate");
      ValidationUtil.addValidationDataToBuffer(dataBuffer, validations);
    }
    return dataBuffer.toString();
  }
  
  private List<SysValidation> getListByJoinType(long joinId, String joinType)
  {
    Map<String, Object> params = new HashMap();
    params.put("joinId", Long.valueOf(joinId));
    params.put("joinType", joinType);
    return this.sysValidationDao.getListByJoinType(params);
  }
}
