package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysButton
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysButton";
  private long id;
  private long nid;
  private long menuId;
  private long menuNid;
  private String buttonName;
  private String buttonUrl;
  private String buttonIcon;
  private String buttonClick;
  private long isVisible;
  private long isPublic;
  private long isSys;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysButton() {}
  
  public SysButton(long id)
  {
    this.id = id;
  }
  
  public SysButton(long id, long nid, long menuId, long menuNid, String buttonName, String buttonUrl, String buttonIcon, String buttonClick, long isVisible, long isPublic, long isSys, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.menuId = menuId;
    this.menuNid = menuNid;
    this.buttonName = buttonName;
    this.buttonUrl = buttonUrl;
    this.buttonIcon = buttonIcon;
    this.buttonClick = buttonClick;
    this.isVisible = isVisible;
    this.isPublic = isPublic;
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
  
  public long getNid()
  {
    return this.nid;
  }
  
  public void setNid(long nid)
  {
    this.nid = nid;
  }
  
  public long getMenuId()
  {
    return this.menuId;
  }
  
  public void setMenuId(long menuId)
  {
    this.menuId = menuId;
  }
  
  public long getMenuNid()
  {
    return this.menuNid;
  }
  
  public void setMenuNid(long menuNid)
  {
    this.menuNid = menuNid;
  }
  
  public String getButtonName()
  {
    return this.buttonName;
  }
  
  public void setButtonName(String buttonName)
  {
    this.buttonName = buttonName;
  }
  
  public String getButtonUrl()
  {
    return this.buttonUrl;
  }
  
  public void setButtonUrl(String buttonUrl)
  {
    this.buttonUrl = buttonUrl;
  }
  
  public String getButtonIcon()
  {
    return this.buttonIcon;
  }
  
  public void setButtonIcon(String buttonIcon)
  {
    this.buttonIcon = buttonIcon;
  }
  
  public String getButtonClick()
  {
    return this.buttonClick;
  }
  
  public void setButtonClick(String buttonClick)
  {
    this.buttonClick = buttonClick;
  }
  
  public long getIsVisible()
  {
    return this.isVisible;
  }
  
  public void setIsVisible(long isVisible)
  {
    this.isVisible = isVisible;
  }
  
  public long getIsPublic()
  {
    return this.isPublic;
  }
  
  public void setIsPublic(long isPublic)
  {
    this.isPublic = isPublic;
  }
  
  public long getIsSys()
  {
    return this.isSys;
  }
  
  public void setIsSys(long isSys)
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "menuId:" + this.menuId + "\t" + "menuNid:" + this.menuNid + "\t" + "buttonName:" + this.buttonName + "\t" + "buttonUrl:" + this.buttonUrl + "\t" + "buttonIcon:" + this.buttonIcon + "\t" + "buttonClick:" + this.buttonClick + "\t" + "isVisible:" + this.isVisible + "\t" + "isPublic:" + this.isPublic + "\t" + "isSys:" + this.isSys + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
