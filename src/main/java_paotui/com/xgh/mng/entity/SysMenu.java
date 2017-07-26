package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysMenu
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysMenu";
  private long id;
  private long parentId;
  private long nid;
  private long parentNid;
  private long instId;
  private long instNid;
  private String menuName;
  private String menuUrl;
  private String menuIcon;
  private long isVisible;
  private int isMain;
  private int isGrid;
  private int isQuery;
  private int isValidate;
  private int isSys;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysMenu() {}
  
  public SysMenu(long id)
  {
    this.id = id;
  }
  
  public SysMenu(long id, long parentId, long nid, long parentNid, long instId, long instNid, String menuName, String menuUrl, String menuIcon, long isVisible, int isMain, int isGrid, int isQuery, int isValidate, int isSys, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.parentId = parentId;
    this.nid = nid;
    this.parentNid = parentNid;
    this.instId = instId;
    this.instNid = instNid;
    this.menuName = menuName;
    this.menuUrl = menuUrl;
    this.menuIcon = menuIcon;
    this.isVisible = isVisible;
    this.isMain = isMain;
    this.isGrid = isGrid;
    this.isQuery = isQuery;
    this.isValidate = isValidate;
    this.isSys = isSys;
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
  
  public long getParentId()
  {
    return this.parentId;
  }
  
  public void setParentId(long parentId)
  {
    this.parentId = parentId;
  }
  
  public long getNid()
  {
    return this.nid;
  }
  
  public void setNid(long nid)
  {
    this.nid = nid;
  }
  
  public long getParentNid()
  {
    return this.parentNid;
  }
  
  public void setParentNid(long parentNid)
  {
    this.parentNid = parentNid;
  }
  
  public long getInstId()
  {
    return this.instId;
  }
  
  public void setInstId(long instId)
  {
    this.instId = instId;
  }
  
  public long getInstNid()
  {
    return this.instNid;
  }
  
  public void setInstNid(long instNid)
  {
    this.instNid = instNid;
  }
  
  public String getMenuName()
  {
    return this.menuName;
  }
  
  public void setMenuName(String menuName)
  {
    this.menuName = menuName;
  }
  
  public String getMenuUrl()
  {
    return this.menuUrl;
  }
  
  public void setMenuUrl(String menuUrl)
  {
    this.menuUrl = menuUrl;
  }
  
  public String getMenuIcon()
  {
    return this.menuIcon;
  }
  
  public void setMenuIcon(String menuIcon)
  {
    this.menuIcon = menuIcon;
  }
  
  public long getIsVisible()
  {
    return this.isVisible;
  }
  
  public void setIsVisible(long isVisible)
  {
    this.isVisible = isVisible;
  }
  
  public int getIsMain()
  {
    return this.isMain;
  }
  
  public void setIsMain(int isMain)
  {
    this.isMain = isMain;
  }
  
  public int getIsGrid()
  {
    return this.isGrid;
  }
  
  public void setIsGrid(int isGrid)
  {
    this.isGrid = isGrid;
  }
  
  public int getIsQuery()
  {
    return this.isQuery;
  }
  
  public void setIsQuery(int isQuery)
  {
    this.isQuery = isQuery;
  }
  
  public int getIsValidate()
  {
    return this.isValidate;
  }
  
  public void setIsValidate(int isValidate)
  {
    this.isValidate = isValidate;
  }
  
  public int getIsSys()
  {
    return this.isSys;
  }
  
  public void setIsSys(int isSys)
  {
    this.isSys = isSys;
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
    return "id:" + this.id + "\t" + "parentId:" + this.parentId + "\t" + "nid:" + this.nid + "\t" + "parentNid:" + this.parentNid + "\t" + "instId:" + this.instId + "\t" + "instNid:" + this.instNid + "\t" + "menuName:" + this.menuName + "\t" + "menuUrl:" + this.menuUrl + "\t" + "menuIcon:" + this.menuIcon + "\t" + "isVisible:" + this.isVisible + "\t" + "isMain:" + this.isMain + "\t" + "isGrid:" + this.isGrid + "\t" + "isQuery:" + this.isQuery + "\t" + "isValidate:" + this.isValidate + "\t" + "isSys:" + this.isSys + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
