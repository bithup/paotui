//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xgh.mng.services;

import com.xgh.mng.dao.ISysGridColumnsDao;
import com.xgh.mng.dao.ISysTypeDao;
import com.xgh.mng.entity.SysGridColumns;
import com.xgh.mng.entity.SysMenu;
import com.xgh.mng.entity.SysType;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysMenuService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysGridColumnsService")
public class SysGridColumnsServiceImpl implements ISysGridColumnsService {
  @Autowired
  protected ISysGridColumnsDao sysGridColumnsDao;
  @Autowired
  protected ISysMenuService sysMenuService;
  @Autowired
  protected ISysTypeDao sysTypeDao;

  public SysGridColumnsServiceImpl() {
  }

  public int add(SysGridColumns sysGridColumns) {
    return this.sysGridColumnsDao.add(sysGridColumns);
  }

  public int delete(long id) {
    return this.sysGridColumnsDao.deleteById(id);
  }

  public int deleteByIds(List<Long> ids) {
    return this.sysGridColumnsDao.deleteByIds(ids);
  }

  public int update(SysGridColumns sysGridColumns) {
    return this.sysGridColumnsDao.update(sysGridColumns);
  }

  public SysGridColumns get(long id) {
    return this.sysGridColumnsDao.get(id);
  }

  public List<SysGridColumns> getList(Map<String, Object> map) {
    List list = this.sysGridColumnsDao.getList(map);
    return list;
  }

  public List<SysGridColumns> getListPage(Map<String, Object> map) {
    return this.sysGridColumnsDao.getListPage(map);
  }

  public long getRows(Map<String, Object> map) {
    return this.sysGridColumnsDao.getRows(map);
  }

  public Map<String, Object> getGridData(Map<String, Object> map) {
    HashMap gridData = new HashMap();
    Object dataList = this.sysGridColumnsDao.getList(map);
    if(dataList == null) {
      dataList = new ArrayList();
    }

    gridData.put("Rows", dataList);
    gridData.put("Total", Integer.valueOf(((List)dataList).size()));
    return gridData;
  }

  public void saveGridColumns(long joinId, String joinType, List<Map<String, Object>> dataList) {
    HashMap params = new HashMap();
    params.put("joinId", Long.valueOf(joinId));
    params.put("joinType", joinType);
    ArrayList columnsList = new ArrayList();
    if(dataList != null && !dataList.isEmpty()) {
      Iterator i$ = dataList.iterator();

      while(i$.hasNext()) {
        Map dataMap = (Map)i$.next();
        SysGridColumns sysGridColumns = new SysGridColumns();
        sysGridColumns.setJoinId(joinId);
        sysGridColumns.setJoinType(joinType);
        sysGridColumns.setTitle(dataMap.get("title") + "");
        sysGridColumns.setAlign(dataMap.get("align") + "");
        sysGridColumns.setName(dataMap.get("name") + "");
        sysGridColumns.setDataType(dataMap.get("dataType") + "");
        sysGridColumns.setWidth(dataMap.get("width") + "");
        sysGridColumns.setRender(dataMap.get("render") + "");
        if(dataMap.get("isSort") != null && !dataMap.get("isSort").equals("")) {
          sysGridColumns.setIsSort(Long.valueOf(dataMap.get("isSort") + "").longValue());
        }

        if(dataMap.get("isWidth") != null && !dataMap.get("isWidth").equals("")) {
          sysGridColumns.setIsWidth(Long.valueOf(dataMap.get("isWidth") + "").longValue());
        }

        if(dataMap.get("isVisible") != null && !dataMap.get("isVisible").equals("")) {
          sysGridColumns.setIsVisible(Long.valueOf(dataMap.get("isVisible") + "").longValue());
        }

        if(dataMap.get("ord") != null && !dataMap.get("ord").equals("")) {
          sysGridColumns.setOrd(Long.valueOf(dataMap.get("ord") + "").longValue());
        }

        Date date = new Date();
        sysGridColumns.setCreateDate(date);
        sysGridColumns.setUpdateDate(date);
        sysGridColumns.setStatus(1);
        columnsList.add(sysGridColumns);
      }

      this.sysGridColumnsDao.deleteByJoinType(params);
      this.sysGridColumnsDao.addBatch(columnsList);
    }

  }

  public String getGridColumsByRequest(HttpServletRequest request) {
    StringBuffer dataBuffer = new StringBuffer("");
    SysMenu sysMenu = this.sysMenuService.getSysMenuByRequest(request);
    if(sysMenu != null && sysMenu.getIsGrid() == 1) {
      this.addGridColumsDataToBuffer(sysMenu.getId(), "menu", dataBuffer);
    }

    return dataBuffer.toString();
  }

  public String getGridColumsByCode(String code) {
    StringBuffer dataBuffer = new StringBuffer("");
    HashMap params = new HashMap();
    params.put("code", code);
    params.put("type", "grid");
    SysType sysType = this.sysTypeDao.getSysTypeByCode(params);
    if(sysType != null) {
      this.addGridColumsDataToBuffer(sysType.getId(), "grid", dataBuffer);
    }

    return dataBuffer.toString();
  }

  private void addGridColumsDataToBuffer(long joinId, String joinType, StringBuffer dataBuffer) {
    HashMap map = new HashMap();
    map.put("joinId", Long.valueOf(joinId));
    map.put("joinType", joinType);
    List gridColumns = this.sysGridColumnsDao.getListByJoinType(map);
    if(gridColumns != null) {
      Iterator i$ = gridColumns.iterator();

      while(true) {
        while(i$.hasNext()) {
          SysGridColumns sysGridColumns = (SysGridColumns)i$.next();
          if(!dataBuffer.toString().equals("")) {
            dataBuffer.append(",");
          }

          String width = sysGridColumns.getIsWidth() == 1L?sysGridColumns.getWidth():"\"" + sysGridColumns.getWidth() + "%\"";
          dataBuffer.append(String.format("{display: \"%s\", name: \"%s\", width: %s, align: \"%s\", type: \"%s\", isSort:%b", new Object[]{sysGridColumns.getTitle(), sysGridColumns.getName(), width, sysGridColumns.getAlign(), sysGridColumns.getDataType(), Boolean.valueOf(sysGridColumns.getIsSort() == 1L)}));
          if(sysGridColumns.getRender() != null && !sysGridColumns.getRender().equals("")) {
            dataBuffer.append(",render:function(rowdata, index, value){ " + sysGridColumns.getRender() + "}}");
          } else {
            dataBuffer.append("}");
          }
        }

        return;
      }
    }
  }
}
