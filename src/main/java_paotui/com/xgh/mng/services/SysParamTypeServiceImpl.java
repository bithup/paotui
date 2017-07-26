package com.xgh.mng.services;

import com.xgh.mng.dao.ISysParamTypeDao;
import com.xgh.mng.entity.SysParamType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysParamTypeService")
public class SysParamTypeServiceImpl
  implements ISysParamTypeService
{
  @Autowired
  protected ISysParamTypeDao sysParamTypeDao;
  
  public int add(SysParamType sysParamType)
  {
    return this.sysParamTypeDao.add(sysParamType);
  }
  
  public int delete(long id)
  {
    return this.sysParamTypeDao.deleteById(id);
  }
  
  public int update(SysParamType sysParamType)
  {
    return this.sysParamTypeDao.update(sysParamType);
  }
  
  public SysParamType get(long id)
  {
    return this.sysParamTypeDao.get(id);
  }
  
  public Map<String, Object> getGirdData(HttpServletRequest request)
  {
    Map<String, Object> gridData = new HashMap();
    
    String page = request.getParameter("page");
    String pagesize = request.getParameter("pagesize");
    String sortname = request.getParameter("sortname");
    String sortorder = request.getParameter("sortorder");
    String ifQuery = request.getParameter("ifQuery");
    if ((sortname != null) && (!sortname.equals(""))) {
      sortname = sortname.toLowerCase();
    }
    Map<String, Object> map = new HashMap();
    map.put("page", Integer.valueOf(Integer.parseInt(page)));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(pagesize)));
    map.put("sortname", sortname);
    map.put("sortorder", sortorder);
    map.put("ifQuery", ifQuery);
    
    List<Map<String, Object>> dataList = this.sysParamTypeDao.getListMap(map);
    long totalRows = this.sysParamTypeDao.getListRows(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(totalRows));
    return gridData;
  }
  
  public boolean isHasSameCode(String op, SysParamType sysParamType)
  {
    Map<String, Object> map = new HashMap();
    map.put("typeCode", sysParamType.getTypeCode());
    if (op.equals("modify")) {
      map.put("id", Long.valueOf(sysParamType.getId()));
    }
    return this.sysParamTypeDao.getRowsByCode(map) > 0L;
  }
}
