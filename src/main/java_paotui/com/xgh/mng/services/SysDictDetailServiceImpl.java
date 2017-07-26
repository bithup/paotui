package com.xgh.mng.services;

import com.xgh.mng.dao.ISysDictDetailDao;
import com.xgh.mng.dao.ISysDictMainDao;
import com.xgh.mng.entity.SysDictDetail;
import com.xgh.mng.entity.SysDictMain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDictDetailService")
public class SysDictDetailServiceImpl
  implements ISysDictDetailService
{
  @Autowired
  protected ISysDictDetailDao sysDictDetailDao;
  @Autowired
  protected ISysDictMainDao sysDictMainDao;
  
  public int add(SysDictDetail sysDictDetail)
  {
    return this.sysDictDetailDao.add(sysDictDetail);
  }
  
  public int delete(long id)
  {
    return this.sysDictDetailDao.deleteById(id);
  }
  
  public int update(SysDictDetail sysDictDetail)
  {
    return this.sysDictDetailDao.update(sysDictDetail);
  }
  
  public SysDictDetail get(long id)
  {
    return this.sysDictDetailDao.get(id);
  }
  
  public Map<String, Object> getGridData(HttpServletRequest request, long unitId)
  {
    String page = request.getParameter("page");
    String pagesize = request.getParameter("pagesize");
    String sortname = request.getParameter("sortname");
    String sortorder = request.getParameter("sortorder");
    long dictMainId = Long.valueOf(request.getParameter("dictMainId")).longValue();
    SysDictMain sysDictMain = this.sysDictMainDao.get(dictMainId);
    if ((sortname != null) && (!sortname.equals(""))) {
      sortname = sortname.toLowerCase();
    }
    Map<String, Object> map = new HashMap();
    map.put("sortname", sortname);
    map.put("sortorder", sortorder);
    map.put("page", Integer.valueOf(Integer.parseInt(page)));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(pagesize)));
    map.put("unitId", Long.valueOf(unitId));
    map.put("dictMainId", Long.valueOf(dictMainId));
    
    Map<String, Object> gridData = new HashMap();
    
    List<SysDictDetail> dataList = new ArrayList();
    long totalRows = 0L;
    if (null != sysDictMain) {
      if (sysDictMain.getIsExtends() == 0)
      {
        dataList = this.sysDictDetailDao.getListPage(map);
        totalRows = this.sysDictDetailDao.getRows(map);
      }
      else
      {
        dataList = this.sysDictDetailDao.getExtendsListMap(map);
        totalRows = this.sysDictDetailDao.getExtendsListRows(map);
      }
    }
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(totalRows));
    return gridData;
  }
  
  public boolean isHasSameCode(SysDictDetail sysDictDetail, String op)
  {
    Map<String, Object> params = new HashMap();
    params.put("op", op);
    params.put("dictMainId", Long.valueOf(sysDictDetail.getDictMainId()));
    params.put("unitId", Long.valueOf(sysDictDetail.getUnitId()));
    params.put("code", sysDictDetail.getCode());
    if (op.equals("modify")) {
      params.put("id", Long.valueOf(sysDictDetail.getId()));
    }
    return this.sysDictDetailDao.getRowsByCode(params) > 0L;
  }
}
