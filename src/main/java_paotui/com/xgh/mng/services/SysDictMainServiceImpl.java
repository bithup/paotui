//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xgh.mng.services;

import com.xgh.mng.dao.ISysCommDao;
import com.xgh.mng.dao.ISysDictDetailDao;
import com.xgh.mng.dao.ISysDictMainDao;
import com.xgh.mng.entity.SysDictDetail;
import com.xgh.mng.entity.SysDictMain;
import com.xgh.mng.services.ISysDictMainService;
import com.xgh.util.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDictMainService")
public class SysDictMainServiceImpl implements ISysDictMainService {
  private static final Logger logger = Logger.getLogger(SysDictMainServiceImpl.class);
  @Autowired
  protected ISysDictMainDao sysDictMainDao;
  @Autowired
  protected ISysDictDetailDao sysDictDetailDao;
  @Autowired
  protected ISysCommDao sysCommDao;

  public SysDictMainServiceImpl() {
  }

  public int add(SysDictMain sysDictMain) {
    return this.sysDictMainDao.add(sysDictMain);
  }

  public int delete(long id) {
    return this.sysDictMainDao.deleteById(id);
  }

  public int update(SysDictMain sysDictMain) {
    return this.sysDictMainDao.update(sysDictMain);
  }

  public SysDictMain get(long id) {
    return this.sysDictMainDao.get(id);
  }

  public Map<String, Object> getGridData(HttpServletRequest request) {
    String page = request.getParameter("page");
    String pagesize = request.getParameter("pagesize");
    String sortname = request.getParameter("sortname");
    String sortorder = request.getParameter("sortorder");
    String parentId = request.getParameter("parentId");
    if(sortname != null && !sortname.equals("")) {
      sortname = sortname.toLowerCase();
    }

    HashMap map = new HashMap();
    map.put("sortname", sortname);
    map.put("sortorder", sortorder);
    map.put("page", Integer.valueOf(Integer.parseInt(page)));
    map.put("pagesize", Integer.valueOf(Integer.parseInt(pagesize)));
    map.put("parentId", Integer.valueOf(Integer.parseInt(parentId)));
    HashMap gridData = new HashMap();
    Object dataList = this.sysDictMainDao.getListPage(map);
    if(dataList == null) {
      dataList = new ArrayList();
    }

    gridData.put("Rows", dataList);
    gridData.put("Total", Long.valueOf(this.sysDictMainDao.getRows(map)));
    return gridData;
  }

  public boolean isHasSameCode(SysDictMain sysDictMain, String op) {
    HashMap params = new HashMap();
    params.put("dictCode", sysDictMain.getDictCode());
    if(op.equals("modify")) {
      params.put("id", Long.valueOf(sysDictMain.getId()));
    }

    return this.sysDictMainDao.getRowsByDictCode(params) > 0L;
  }

  public void loadDictDataToCache() {
    Class var1 = SysDictMainServiceImpl.class;
    synchronized(SysDictMainServiceImpl.class) {
      logger.info("使用缓存,加载字典缓存开始");
      List sysDictMainList = this.sysDictMainDao.getAllList();
      logger.debug("sysDictMainList" + sysDictMainList);
      List sysDictDetailList = this.sysDictDetailDao.getAllList();
      logger.debug("sysDictDetailList" + sysDictDetailList);
      if(sysDictMainList != null && !sysDictMainList.isEmpty()) {
        this.reloadMainAndDetail(sysDictMainList, sysDictDetailList);
      }

      logger.info("加载字典缓存结束！");
    }
  }

  public SysDictMain getSysDictMainByCode(String code) {
    return this.sysDictMainDao.getSysDictMainByCode(code);
  }

  public List<SysDictDetail> getDetailListByMainCode(long unitId, String mainCode) {
    Object detailList = new ArrayList();
    SysDictMain sysDictMain = this.sysDictMainDao.getSysDictMainByCode(mainCode);
    if(sysDictMain != null) {
      String dataType = sysDictMain.getDictType();
      if(dataType.equals("json")) {
        List params = JSONUtil.jsonToListMap(sysDictMain.getDictValue());
        this.addDataToDetailList(params, (List)detailList);
      } else {
        HashMap params1;
        if(dataType.equals("sql")) {
          params1 = new HashMap();
          params1.put("commSql", sysDictMain.getDictValue());
          List list = this.sysCommDao.getListMap(params1);
          this.addDataToDetailList(list, (List)detailList);
        } else if(dataType.equals("dict")) {
          params1 = new HashMap();
          params1.put("unitId", Long.valueOf(unitId));
          params1.put("mainCode", mainCode);
          detailList = this.sysDictDetailDao.getDetailListByMainCode(params1);
        }
      }
    }

    return (List)detailList;
  }

  public SysDictDetail getDetailByCode(long unitId, String mainCode, String code) {
    HashMap params = new HashMap();
    params.put("unitId", Long.valueOf(unitId));
    params.put("mainCode", mainCode);
    params.put("code", code);
    return this.sysDictDetailDao.getDetailByMainCode(params);
  }

  public long getDictDetailRowsByMainId(long mainId) {
    return this.sysDictMainDao.getDictDetailRowsByMainId(mainId);
  }

  public long getSysDictMainChildRows(long parentId) {
    return this.sysDictMainDao.getSysDictMainChildRows(parentId);
  }

  public List<Map<String, Object>> getSysDictMainFirstTreeData() {
    return this.sysDictMainDao.getSysDictMainFirstTreeData();
  }

  public List<Map<String, Object>> getDictMainTreeList(Map<String, Object> map) {
    return this.sysDictMainDao.getDictMainTreeList(map);
  }

  private void reloadMainAndDetail(List<SysDictMain> sysDictMainList, List<SysDictDetail> sysDictDetailList) {
    HashMap dictMainMap = new HashMap();
    Iterator i$ = sysDictMainList.iterator();

    while(i$.hasNext()) {
      SysDictMain sysDictMain = (SysDictMain)i$.next();
      dictMainMap.put(sysDictMain.getDictCode(), sysDictMain);
    }

    this.loadSysDictDetails(sysDictMainList, sysDictDetailList);
  }

  private void loadSysDictDetails(List<SysDictMain> sysDictMainList, List<SysDictDetail> sysDictDetailList) {
    new HashMap();
    if(sysDictMainList != null && !sysDictMainList.isEmpty() && sysDictDetailList != null && !sysDictDetailList.isEmpty()) {
      Set unitIdSet = this.getUnitIdSet(sysDictDetailList);
      Iterator i$ = sysDictMainList.iterator();

      while(i$.hasNext()) {
        SysDictMain sysDictMain = (SysDictMain)i$.next();
        Iterator i$1 = unitIdSet.iterator();

        while(i$1.hasNext()) {
          long unitId = ((Long)i$1.next()).longValue();
          this.getSysDictDetails(unitId, sysDictMain.getId(), sysDictDetailList);
        }
      }
    }

  }

  private List<SysDictDetail> getSysDictDetails(long unitId, long mainId, List<SysDictDetail> sysDictDetailList) {
    ArrayList list = new ArrayList();
    Iterator i$ = sysDictDetailList.iterator();

    while(true) {
      SysDictDetail sysDictDetail;
      do {
        do {
          if(!i$.hasNext()) {
            return list;
          }

          sysDictDetail = (SysDictDetail)i$.next();
        } while(sysDictDetail == null);
      } while(sysDictDetail.getUnitId() != 1L && sysDictDetail.getUnitId() != unitId);

      if(sysDictDetail.getDictMainId() == mainId) {
        list.add(sysDictDetail);
      }
    }
  }

  private Set<Long> getUnitIdSet(List<SysDictDetail> sysDictDetailList) {
    HashSet set = new HashSet();
    if(sysDictDetailList != null && !sysDictDetailList.isEmpty()) {
      Iterator i$ = sysDictDetailList.iterator();

      while(i$.hasNext()) {
        SysDictDetail sysDictDetail = (SysDictDetail)i$.next();
        set.add(Long.valueOf(sysDictDetail.getUnitId()));
      }
    }

    return set;
  }

  private int addDataToDetailList(List<Map<String, Object>> list, List<SysDictDetail> detailList) {
    int i = 0;
    if(list != null && !list.isEmpty()) {
      for(Iterator i$ = list.iterator(); i$.hasNext(); ++i) {
        Map map = (Map)i$.next();
        SysDictDetail sysDictDetail = new SysDictDetail();
        sysDictDetail.setUnitId(1L);
        sysDictDetail.setCode(map.get("code") == null?null:map.get("code") + "");
        sysDictDetail.setValue(map.get("value") == null?null:map.get("value") + "");
        sysDictDetail.setOrd((long)i);
        detailList.add(sysDictDetail);
      }
    }

    return i;
  }
}
