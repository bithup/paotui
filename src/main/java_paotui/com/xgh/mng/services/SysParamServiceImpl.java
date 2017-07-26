package com.xgh.mng.services;

import com.xgh.mng.dao.ISysParamDao;
import com.xgh.mng.dao.ISysParamTypeDao;
import com.xgh.mng.entity.SysParam;
import com.xgh.mng.entity.SysParamType;
import com.xgh.util.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysParamService")
public class SysParamServiceImpl
  implements ISysParamService
{
  @Autowired
  protected ISysParamDao sysParamDao;
  @Autowired
  protected ISysParamTypeDao sysParamTypeDao;
  
  public int add(SysParam sysParam)
  {
    return this.sysParamDao.add(sysParam);
  }
  
  public int delete(long id)
  {
    return this.sysParamDao.deleteById(id);
  }
  
  public int update(SysParam sysParam)
  {
    return this.sysParamDao.update(sysParam);
  }
  
  public SysParam get(long id)
  {
    return this.sysParamDao.get(id);
  }
  
  public Map<String, Object> getGirdData(HttpServletRequest request, long unitId)
  {
    Map<String, Object> gridData = new HashMap();
    
    String page = request.getParameter("page");
    String pagesize = request.getParameter("pagesize");
    String sortname = request.getParameter("sortname");
    String sortorder = request.getParameter("sortorder");
    String ifQuery = request.getParameter("ifQuery");
    String typeId = request.getParameter("typeId");
    if ((sortname != null) && (!sortname.equals(""))) {
      sortname = "a." + sortname.toLowerCase();
    }
    Map<String, Object> map = new HashMap();
    map.put("page", Integer.valueOf(Integer.parseInt(page)));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(pagesize)));
    map.put("sortname", sortname);
    map.put("sortorder", sortorder);
    map.put("ifQuery", ifQuery);
    map.put("unitId", Long.valueOf(unitId));
    map.put("typeId", Long.valueOf(typeId));
    
    List<Map<String, Object>> dataList = this.sysParamDao.getListMap(map);
    long totalRows = this.sysParamDao.getListRows(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(totalRows));
    return gridData;
  }
  
  public String getParamTypeTreeData()
  {
    List<Map<String, Object>> treeList = this.sysParamTypeDao.getTreeList();
    if (treeList == null) {
      treeList = new ArrayList();
    }
    Map<String, Object> map = new HashMap();
    map.put("id", Integer.valueOf(0));
    map.put("text", "参数类型");
    treeList.add(map);
    return JSONUtil.getJson(treeList);
  }
  
  public boolean isHasSameCode(String op, SysParam sysParam)
  {
    Map<String, Object> map = new HashMap();
    map.put("typeId", Long.valueOf(sysParam.getTypeId()));
    map.put("paramsCode", sysParam.getParamsCode());
    map.put("unitId", Long.valueOf(sysParam.getUnitId()));
    if (op.equals("modify")) {
      map.put("id", Long.valueOf(sysParam.getId()));
    }
    return this.sysParamDao.getRowsByTypeIdAndCode(map) > 0L;
  }
  
  public void save(String op, long unitId, SysParam sysParam)
  {
    if (op.equals("add"))
    {
      this.sysParamDao.add(sysParam);
    }
    else if (unitId == 1L)
    {
      this.sysParamDao.update(sysParam);
    }
    else
    {
      SysParam sysParam2 = this.sysParamDao.get(sysParam.getId());
      if (sysParam2.getUnitId() != unitId)
      {
        sysParam2.setParamsValue(sysParam.getParamsValue());
        sysParam2.setRemark(sysParam.getRemark());
        sysParam2.setOrd(sysParam.getOrd());
        sysParam2.setUnitId(unitId);
        this.sysParamDao.add(sysParam2);
      }
      else
      {
        sysParam2.setParamsValue(sysParam.getParamsValue());
        sysParam2.setRemark(sysParam.getRemark());
        sysParam2.setOrd(sysParam.getOrd());
        this.sysParamDao.update(sysParam2);
      }
    }
  }
  
  public SysParam getSysParamByCode(long unitId, String typeCode, String code)
  {
    SysParamType sysParamType = this.sysParamTypeDao.getParamTypeByCode(typeCode);
    Map<String, Object> map = new HashMap();
    map.put("unitId", Long.valueOf(unitId));
    map.put("typeId", Long.valueOf(sysParamType.getId()));
    map.put("paramsCode", code);
    SysParam sysParam = this.sysParamDao.getByTypeIdAndCode(map);
    if (sysParam == null)
    {
      map.put("unitId", Integer.valueOf(1));
      sysParam = this.sysParamDao.getByTypeIdAndCode(map);
    }
    return sysParam;
  }
  
  public List<SysParam> getSysParamListByTypeCode(long unitId, String typeCode)
  {
    SysParamType sysParamType = this.sysParamTypeDao.getParamTypeByCode(typeCode);
    Map<String, Object> map = new HashMap();
    map.put("unitId", Long.valueOf(unitId));
    map.put("typeId", Long.valueOf(sysParamType.getId()));
    return this.sysParamDao.getListByTypeId(map);
  }
}
