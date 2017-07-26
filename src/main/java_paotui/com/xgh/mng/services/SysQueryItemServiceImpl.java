package com.xgh.mng.services;

import com.xgh.mng.dao.ISysCommDao;
import com.xgh.mng.dao.ISysQueryItemDao;
import com.xgh.mng.dao.ISysTypeDao;
import com.xgh.mng.entity.ListItem;
import com.xgh.mng.entity.SysQueryItem;
import com.xgh.mng.entity.SysType;
import com.xgh.util.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysQueryItemService")
public class SysQueryItemServiceImpl
  implements ISysQueryItemService
{
  @Autowired
  protected ISysQueryItemDao sysQueryItemDao;
  @Autowired
  protected ISysCommDao sysCommDao;
  @Autowired
  protected ISysTypeDao sysTypeDao;
  @Autowired
  protected ISysMenuService sysMenuService;
  
  public int add(SysQueryItem sysQueryItem)
  {
    return this.sysQueryItemDao.add(sysQueryItem);
  }
  
  public int delete(long id)
  {
    return this.sysQueryItemDao.deleteById(id);
  }
  
  public int update(SysQueryItem sysQueryItem)
  {
    return this.sysQueryItemDao.update(sysQueryItem);
  }
  
  public SysQueryItem get(long id)
  {
    return this.sysQueryItemDao.get(id);
  }
  
  public Map<String, Object> getGirdData(Map<String, Object> map)
  {
    Map<String, Object> gridData = new HashMap();
    
    List<SysQueryItem> dataList = this.sysQueryItemDao.getList(map);
    if (dataList == null) {
      dataList = new ArrayList();
    }
    gridData.put("Rows", dataList);
    gridData.put("Total", Integer.valueOf(dataList.size()));
    return gridData;
  }
  
  public void saveQueryItem(long joinId, String joinType, List<Map<String, Object>> dataList)
  {
    Map<String, Object> params = new HashMap();
    params.put("joinId", Long.valueOf(joinId));
    params.put("joinType", joinType);
    

    List<SysQueryItem> sysQueryItemList = new ArrayList();
    if ((dataList != null) && (!dataList.isEmpty()))
    {
      for (Map<String, Object> dataMap : dataList)
      {
        SysQueryItem sysQueryItem = new SysQueryItem();
        
        sysQueryItem.setJoinId(joinId);
        sysQueryItem.setJoinType(joinType);
        sysQueryItem.setDatasourceType(dataMap.get("datasourceType") + "");
        sysQueryItem.setDatasourceValue(dataMap.get("datasourceValue") + "");
        sysQueryItem.setDataType(dataMap.get("dataType") + "");
        sysQueryItem.setFieldName(dataMap.get("fieldName") + "");
        sysQueryItem.setFormName(dataMap.get("formName") + "");
        sysQueryItem.setInputName(dataMap.get("inputName") + "");
        sysQueryItem.setName(dataMap.get("name") + "");
        sysQueryItem.setOperator(dataMap.get("operator") + "");
        if (dataMap.get("ord") != null) {
          sysQueryItem.setOrd(Long.valueOf(dataMap.get("ord") + "").longValue());
        }
        sysQueryItem.setQueryType(dataMap.get("queryType") + "");
        sysQueryItem.setRow(Long.valueOf(dataMap.get("row") + "").longValue());
        sysQueryItem.setRowspan(Long.valueOf(dataMap.get("rowspan") + "").longValue());
        sysQueryItem.setValue(dataMap.get("value") + "");
        if ((dataMap.get("width") != null) && (!dataMap.get("width").equals(""))) {
          sysQueryItem.setWidth(Long.valueOf(dataMap.get("width") + "").longValue());
        }
        if ((dataMap.get("x") != null) && (!dataMap.get("x").equals(""))) {
          sysQueryItem.setX(Long.valueOf(dataMap.get("x") + "").longValue());
        }
        sysQueryItemList.add(sysQueryItem);
      }
      this.sysQueryItemDao.deleteByJoinType(params);
      this.sysQueryItemDao.addBatch(sysQueryItemList);
    }
  }
  
  public List<SysQueryItem> getListByJoinType(long joinId, String joinType)
  {
    Map<String, Object> map = new HashMap();
    map.put("joinId", Long.valueOf(joinId));
    map.put("joinType", joinType);
    return this.sysQueryItemDao.getListByJoinType(map);
  }
  
  public String getConditionHtmlByRequest(HttpServletRequest request, long unitId)
  {
    return "";
  }
  
  public String getConditionHtmlByCode(String typeCode, long unitId)
  {
    String html = "";
    Map<String, Object> params = new HashMap();
    params.put("code", typeCode);
    params.put("type", "query");
    SysType sysType = this.sysTypeDao.getSysTypeByCode(params);
    if (null != sysType)
    {
      List<SysQueryItem> sysQueryItems = getListByJoinType(sysType.getId(), "query");
      html = getConditionHtml(sysQueryItems, unitId);
    }
    return html;
  }
  
  private String getConditionHtml(List<SysQueryItem> sysQueryItems, long unitId)
  {
    int labelWidth = 80;
    StringBuffer sb = new StringBuffer();
    StringBuffer divsb = new StringBuffer();
    
    String formatdiv = "%s,%s,%s,%s,%s,%s";
    
    int beginrow = -1;
    int rowCount = sysQueryItems.size();
    for (int i = 0; i < rowCount; i++)
    {
      SysQueryItem sysQueryItem = (SysQueryItem)sysQueryItems.get(i);
      
      sysQueryItem.setUnitId(unitId);
      
      String name = getStringFromObj(sysQueryItem.getName());
      String field_name = getStringFromObj(sysQueryItem.getFieldName());
      String form_name = getStringFromObj(sysQueryItem.getFormName());
      String input_name = getStringFromObj(sysQueryItem.getInputName());
      String data_type = getStringFromObj(sysQueryItem.getDataType());
      String query_type = getStringFromObj(sysQueryItem.getQueryType());
      String operator = getStringFromObj(sysQueryItem.getOperator());
      
      int row = Integer.parseInt(sysQueryItem.getRow() + "");
      int rowspan = Integer.parseInt(sysQueryItem.getRowspan() + "");
      if (divsb.length() == 0) {
        divsb.append(String.format(formatdiv, new Object[] { form_name, field_name, input_name, data_type, query_type, operator }));
      } else {
        divsb.append(":" + String.format(formatdiv, new Object[] { form_name, field_name, input_name, data_type, query_type, operator }));
      }
      if (beginrow != row)
      {
        beginrow = row;
        if (sb.length() == 0) {
          sb.append("<table id='id-querytable' class='css-querytable' ><tr height='25px'>");
        } else {
          sb.append("</tr><tr  height='25px'>");
        }
        String htmlInput = getHtmlInput(sysQueryItem);
        sb.append(String.format("<td align='right' width='%dpx'  >%s:&nbsp;</td><td colspan='%d'  align='left'   >%s</td>", new Object[] { Integer.valueOf(labelWidth), name, Integer.valueOf(rowspan), htmlInput }));
      }
      else
      {
        String htmlInput = getHtmlInput(sysQueryItem);
        sb.append(String.format("<td align='right'   width='%dpx'  >%s:&nbsp;</td><td colspan='%d'  align='left' >%s</td>", new Object[] { Integer.valueOf(labelWidth), name, Integer.valueOf(rowspan), htmlInput }));
      }
    }
    if (beginrow == -1) {
      return "";
    }
    sb.append("</tr></table>");
    return sb.toString() + String.format("<div style='display:none;' id='fields_queryinfo'>%s</div>", new Object[] { divsb.toString() });
  }
  
  private String getHtmlInput(SysQueryItem sysQueryItem)
  {
    StringBuffer html = new StringBuffer();
    String format = null;
    

    String name = getStringFromObj(sysQueryItem.getName());
    String field_name = getStringFromObj(sysQueryItem.getFieldName());
    String form_name = getStringFromObj(sysQueryItem.getFormName());
    String input_name = getStringFromObj(sysQueryItem.getInputName());
    String data_type = getStringFromObj(sysQueryItem.getDataType());
    String value = getStringFromObj(sysQueryItem.getValue());
    String datasource_type = getStringFromObj(sysQueryItem.getDatasourceType());
    String datasource_value = getStringFromObj(sysQueryItem.getDatasourceValue());
    String query_type = getStringFromObj(sysQueryItem.getQueryType());
    String operator = getStringFromObj(sysQueryItem.getOperator());
    
    int row = Integer.parseInt(sysQueryItem.getRow() + "");
    int rowspan = Integer.parseInt(sysQueryItem.getRowspan() + "");
    long width = sysQueryItem.getWidth();
    long x = sysQueryItem.getX();
    if ("input".equals(query_type))
    {
      format = "<input style='width:%dpx;' id='%s' name='%s' type='text' value='%s'/>";
      html.append(String.format(format, new Object[] { Long.valueOf(width), form_name, form_name, value }));
    }
    else if ("checkbox".equals(query_type))
    {
      html.append(getCheckBox(sysQueryItem));
    }
    else if ("radio".equals(query_type))
    {
      html.append(getRadio(sysQueryItem));
    }
    else if ("select".equals(query_type))
    {
      html.append(getSelect(sysQueryItem));
    }
    return html.length() == 0 ? "" : html.toString();
  }
  
  private String getCheckBox(SysQueryItem sysQueryItem)
  {
    StringBuffer html = new StringBuffer();
    String format = "<input type='checkbox' name='%s' value='%s' />&nbsp;%s &nbsp;&nbsp;";
    
    String name = getStringFromObj(sysQueryItem.getName());
    String field_name = getStringFromObj(sysQueryItem.getFieldName());
    String form_name = getStringFromObj(sysQueryItem.getFormName());
    String input_name = getStringFromObj(sysQueryItem.getInputName());
    String data_type = getStringFromObj(sysQueryItem.getDataType());
    String value = getStringFromObj(sysQueryItem.getValue());
    String datasource_type = getStringFromObj(sysQueryItem.getDatasourceType());
    String datasource_value = getStringFromObj(sysQueryItem.getDatasourceValue());
    String query_type = getStringFromObj(sysQueryItem.getQueryType());
    String operator = getStringFromObj(sysQueryItem.getOperator());
    
    int row = Integer.parseInt(sysQueryItem.getRow() + "");
    int rowspan = Integer.parseInt(sysQueryItem.getRowspan() + "");
    long width = sysQueryItem.getWidth();
    long x = sysQueryItem.getX();
    

    List<ListItem> list = null;
    if ("json".equals(datasource_type)) {
      list = getDataFromJson(datasource_value);
    } else if ("dict".equals(datasource_type)) {
      list = getDataFromDict(datasource_value);
    } else if ("sql".equals(datasource_type)) {
      list = getDataFromSql(datasource_value);
    }
    if (list == null) {
      return "";
    }
    for (int i = 0; i < list.size(); i++)
    {
      ListItem item = (ListItem)list.get(i);
      html.append(String.format(format, new Object[] { form_name, item.getValue(), item.getText() }));
    }
    return html.toString();
  }
  
  private String getRadio(SysQueryItem sysQueryItem)
  {
    StringBuffer html = new StringBuffer();
    String format = "<input type='radio' name='%s' value='%s' />&nbsp;%s &nbsp;&nbsp;";
    
    String name = getStringFromObj(sysQueryItem.getName());
    String field_name = getStringFromObj(sysQueryItem.getFieldName());
    String form_name = getStringFromObj(sysQueryItem.getFormName());
    String input_name = getStringFromObj(sysQueryItem.getInputName());
    String data_type = getStringFromObj(sysQueryItem.getDataType());
    String value = getStringFromObj(sysQueryItem.getValue());
    String datasource_type = getStringFromObj(sysQueryItem.getDatasourceType());
    String datasource_value = getStringFromObj(sysQueryItem.getDatasourceValue());
    String query_type = getStringFromObj(sysQueryItem.getQueryType());
    String operator = getStringFromObj(sysQueryItem.getOperator());
    
    int row = Integer.parseInt(sysQueryItem.getRow() + "");
    int rowspan = Integer.parseInt(sysQueryItem.getRowspan() + "");
    long width = sysQueryItem.getWidth();
    long x = sysQueryItem.getX();
    

    List<ListItem> list = null;
    if ("json".equals(datasource_type)) {
      list = getDataFromJson(datasource_value);
    } else if ("dict".equals(datasource_type)) {
      list = getDataFromDict(datasource_value);
    } else if ("sql".equals(datasource_type)) {
      list = getDataFromSql(datasource_value);
    }
    if (list == null) {
      return "";
    }
    for (int i = 0; i < list.size(); i++)
    {
      ListItem item = (ListItem)list.get(i);
      html.append(String.format(format, new Object[] { form_name, item.getValue(), item.getText() }));
    }
    return html.toString();
  }
  
  private String getSelect(SysQueryItem sysQueryItem)
  {
    StringBuffer html = new StringBuffer();
    

    String format = "<option  value='%s' >%s</option>";
    
    String name = getStringFromObj(sysQueryItem.getName());
    String field_name = getStringFromObj(sysQueryItem.getFieldName());
    String form_name = getStringFromObj(sysQueryItem.getFormName());
    String input_name = getStringFromObj(sysQueryItem.getInputName());
    String data_type = getStringFromObj(sysQueryItem.getDataType());
    String value = getStringFromObj(sysQueryItem.getValue());
    String datasource_type = getStringFromObj(sysQueryItem.getDatasourceType());
    String datasource_value = getStringFromObj(sysQueryItem.getDatasourceValue());
    String query_type = getStringFromObj(sysQueryItem.getQueryType());
    String operator = getStringFromObj(sysQueryItem.getOperator());
    
    int row = Integer.parseInt(sysQueryItem.getRow() + "");
    int rowspan = Integer.parseInt(sysQueryItem.getRowspan() + "");
    long width = sysQueryItem.getWidth();
    long x = sysQueryItem.getX();
    

    List<ListItem> list = null;
    if ("json".equals(datasource_type)) {
      list = getDataFromJson(datasource_value);
    } else if ("dict".equals(datasource_type)) {
      list = getDataFromDict(datasource_value);
    } else if ("sql".equals(datasource_type)) {
      list = getDataFromSql(datasource_value);
    }
    if (list == null) {
      return "";
    }
    for (int i = 0; i < list.size(); i++)
    {
      if (i == 0) {
        html.append(String.format("<select id='%s' name='%s' >", new Object[] { form_name, form_name }));
      }
      ListItem item = (ListItem)list.get(i);
      html.append(String.format(format, new Object[] { item.getValue(), item.getText() }));
    }
    if (html.length() > 0) {
      html.append("</select>");
    }
    return html.toString();
  }
  
  private List<ListItem> getDataFromJson(String datasource_value)
  {
    List<ListItem> list = new ArrayList();
    
    List<Map<String, Object>> jsonList = JSONUtil.jsonToListMap(datasource_value);
    for (Map<String, Object> map : jsonList)
    {
      ListItem item = new ListItem();
      
      item.setValue(map.get("value") + "");
      item.setText(map.get("text") + "");
      list.add(item);
    }
    return list;
  }
  
  private List<ListItem> getDataFromDict(String datasource_value)
  {
    List<ListItem> list = new ArrayList();
    String fsql = "SELECT code as value,value text from  tb_sys_dict_detail where dict_main_id in   (select id from tb_sys_dict_main where dict_code='%s') ORDER BY ord";
    String sql = String.format(fsql, new Object[] { datasource_value });
    Map<String, Object> params = new HashMap();
    params.put("commSql", sql);
    
    List<Map<String, Object>> rows = this.sysCommDao.getListMap(params);
    
    ListItem defaultItem = new ListItem();
    defaultItem.setText("");
    defaultItem.setValue("");
    list.add(defaultItem);
    for (int i = 0; i < rows.size(); i++)
    {
      Map<String, Object> obj = (Map)rows.get(i);
      ListItem item = new ListItem();
      item.setText(obj.get("text").toString());
      item.setValue(obj.get("value").toString());
      list.add(item);
    }
    return list;
  }
  
  private List<ListItem> getDataFromSql(String datasource_value)
  {
    List<ListItem> list = new ArrayList();
    Map<String, Object> params = new HashMap();
    params.put("commSql", datasource_value);
    List<Map<String, Object>> rows = this.sysCommDao.getListMap(params);
    for (int i = 0; i < rows.size(); i++)
    {
      Map<String, Object> obj = (Map)rows.get(i);
      ListItem item = new ListItem();
      item.setText(obj.get("text").toString());
      item.setValue(obj.get("value").toString());
      list.add(item);
    }
    return list;
  }
  
  private String getStringFromObj(Object value)
  {
    if (value == null) {
      return "";
    }
    return (String)value;
  }
}
