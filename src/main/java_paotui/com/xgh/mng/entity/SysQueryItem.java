package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysQueryItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysQueryItem";
  private long id;
  private long nid;
  private long unitId;
  private long unitNid;
  private long joinId;
  private long joinNid;
  private String joinType;
  private String name;
  private String fieldName;
  private String formName;
  private String inputName;
  private String dataType;
  private String value;
  private String datasourceType;
  private String datasourceValue;
  private String queryType;
  private String operator;
  private long row;
  private long rowspan;
  private long width;
  private long x;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysQueryItem() {}
  
  public SysQueryItem(long id)
  {
    this.id = id;
  }
  
  public SysQueryItem(long id, long nid, long unitId, long unitNid, long joinId, long joinNid, String joinType, String name, String fieldName, String formName, String inputName, String dataType, String value, String datasourceType, String datasourceValue, String queryType, String operator, long row, long rowspan, long width, long x, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.unitId = unitId;
    this.unitNid = unitNid;
    this.joinId = joinId;
    this.joinNid = joinNid;
    this.joinType = joinType;
    this.name = name;
    this.fieldName = fieldName;
    this.formName = formName;
    this.inputName = inputName;
    this.dataType = dataType;
    this.value = value;
    this.datasourceType = datasourceType;
    this.datasourceValue = datasourceValue;
    this.queryType = queryType;
    this.operator = operator;
    this.row = row;
    this.rowspan = rowspan;
    this.width = width;
    this.x = x;
    this.ord = ord;
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.status = status;
    this.remark = remark;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long id)
  {
    this.id = id;
  }
  
  public long getNid()
  {
    return this.nid;
  }
  
  public void setNid(long nid)
  {
    this.nid = nid;
  }
  
  public long getUnitId()
  {
    return this.unitId;
  }
  
  public void setUnitId(long unitId)
  {
    this.unitId = unitId;
  }
  
  public long getUnitNid()
  {
    return this.unitNid;
  }
  
  public void setUnitNid(long unitNid)
  {
    this.unitNid = unitNid;
  }
  
  public long getJoinId()
  {
    return this.joinId;
  }
  
  public void setJoinId(long joinId)
  {
    this.joinId = joinId;
  }
  
  public long getJoinNid()
  {
    return this.joinNid;
  }
  
  public void setJoinNid(long joinNid)
  {
    this.joinNid = joinNid;
  }
  
  public String getJoinType()
  {
    return this.joinType;
  }
  
  public void setJoinType(String joinType)
  {
    this.joinType = joinType;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getFieldName()
  {
    return this.fieldName;
  }
  
  public void setFieldName(String fieldName)
  {
    this.fieldName = fieldName;
  }
  
  public String getFormName()
  {
    return this.formName;
  }
  
  public void setFormName(String formName)
  {
    this.formName = formName;
  }
  
  public String getInputName()
  {
    return this.inputName;
  }
  
  public void setInputName(String inputName)
  {
    this.inputName = inputName;
  }
  
  public String getDataType()
  {
    return this.dataType;
  }
  
  public void setDataType(String dataType)
  {
    this.dataType = dataType;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public String getDatasourceType()
  {
    return this.datasourceType;
  }
  
  public void setDatasourceType(String datasourceType)
  {
    this.datasourceType = datasourceType;
  }
  
  public String getDatasourceValue()
  {
    return this.datasourceValue;
  }
  
  public void setDatasourceValue(String datasourceValue)
  {
    this.datasourceValue = datasourceValue;
  }
  
  public String getQueryType()
  {
    return this.queryType;
  }
  
  public void setQueryType(String queryType)
  {
    this.queryType = queryType;
  }
  
  public String getOperator()
  {
    return this.operator;
  }
  
  public void setOperator(String operator)
  {
    this.operator = operator;
  }
  
  public long getRow()
  {
    return this.row;
  }
  
  public void setRow(long row)
  {
    this.row = row;
  }
  
  public long getRowspan()
  {
    return this.rowspan;
  }
  
  public void setRowspan(long rowspan)
  {
    this.rowspan = rowspan;
  }
  
  public long getWidth()
  {
    return this.width;
  }
  
  public void setWidth(long width)
  {
    this.width = width;
  }
  
  public long getX()
  {
    return this.x;
  }
  
  public void setX(long x)
  {
    this.x = x;
  }
  
  public long getOrd()
  {
    return this.ord;
  }
  
  public void setOrd(long ord)
  {
    this.ord = ord;
  }
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }
  
  public Date getUpdateDate()
  {
    return this.updateDate;
  }
  
  public void setUpdateDate(Date updateDate)
  {
    this.updateDate = updateDate;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String toString()
  {
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "unitId:" + this.unitId + "\t" + "unitNid:" + this.unitNid + "\t" + "joinId:" + this.joinId + "\t" + "joinNid:" + this.joinNid + "\t" + "joinType:" + this.joinType + "\t" + "name:" + this.name + "\t" + "fieldName:" + this.fieldName + "\t" + "formName:" + this.formName + "\t" + "inputName:" + this.inputName + "\t" + "dataType:" + this.dataType + "\t" + "value:" + this.value + "\t" + "datasourceType:" + this.datasourceType + "\t" + "datasourceValue:" + this.datasourceValue + "\t" + "queryType:" + this.queryType + "\t" + "operator:" + this.operator + "\t" + "row:" + this.row + "\t" + "rowspan:" + this.rowspan + "\t" + "width:" + this.width + "\t" + "x:" + this.x + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
