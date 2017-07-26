package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysGridColumns
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysGridColumns";
  private long id;
  private long nid;
  private String joinType;
  private long joinId;
  private long joinNid;
  private String title;
  private String name;
  private String width;
  private String align;
  private String dataType;
  private String editorType;
  private String render;
  private long isSort;
  private long isWidth;
  private long isVisible;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysGridColumns() {}
  
  public SysGridColumns(long id)
  {
    this.id = id;
  }
  
  public SysGridColumns(long id, long nid, String joinType, long joinId, long joinNid, String title, String name, String width, String align, String dataType, String editorType, String render, long isSort, long isWidth, long isVisible, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.joinType = joinType;
    this.joinId = joinId;
    this.joinNid = joinNid;
    this.title = title;
    this.name = name;
    this.width = width;
    this.align = align;
    this.dataType = dataType;
    this.editorType = editorType;
    this.render = render;
    this.isSort = isSort;
    this.isWidth = isWidth;
    this.isVisible = isVisible;
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
  
  public String getJoinType()
  {
    return this.joinType;
  }
  
  public void setJoinType(String joinType)
  {
    this.joinType = joinType;
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
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getWidth()
  {
    return this.width;
  }
  
  public void setWidth(String width)
  {
    this.width = width;
  }
  
  public String getAlign()
  {
    return this.align;
  }
  
  public void setAlign(String align)
  {
    this.align = align;
  }
  
  public String getDataType()
  {
    return this.dataType;
  }
  
  public void setDataType(String dataType)
  {
    this.dataType = dataType;
  }
  
  public String getEditorType()
  {
    return this.editorType;
  }
  
  public void setEditorType(String editorType)
  {
    this.editorType = editorType;
  }
  
  public String getRender()
  {
    return this.render;
  }
  
  public void setRender(String render)
  {
    this.render = render;
  }
  
  public long getIsSort()
  {
    return this.isSort;
  }
  
  public void setIsSort(long isSort)
  {
    this.isSort = isSort;
  }
  
  public long getIsWidth()
  {
    return this.isWidth;
  }
  
  public void setIsWidth(long isWidth)
  {
    this.isWidth = isWidth;
  }
  
  public long getIsVisible()
  {
    return this.isVisible;
  }
  
  public void setIsVisible(long isVisible)
  {
    this.isVisible = isVisible;
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "joinType:" + this.joinType + "\t" + "joinId:" + this.joinId + "\t" + "joinNid:" + this.joinNid + "\t" + "title:" + this.title + "\t" + "name:" + this.name + "\t" + "width:" + this.width + "\t" + "align:" + this.align + "\t" + "dataType:" + this.dataType + "\t" + "editorType:" + this.editorType + "\t" + "render:" + this.render + "\t" + "isSort:" + this.isSort + "\t" + "isWidth:" + this.isWidth + "\t" + "isVisible:" + this.isVisible + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
