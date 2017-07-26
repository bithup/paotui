package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysMenu";
  private long id;
  private long parentId;
  private String menuName;
  private String menuUrl;
  private String menuIcon;
  private long ord;
  private List<Menu> childList = new ArrayList();
  
  public Menu() {}
  
  public Menu(long id)
  {
    this.id = id;
  }
  
  public Menu(long id, long parentId, String menuName, String menuUrl, String menuIcon, long ord)
  {
    this.id = id;
    this.parentId = parentId;
    this.menuName = menuName;
    this.menuUrl = menuUrl;
    this.menuIcon = menuIcon;
    this.ord = ord;
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
  
  public long getOrd()
  {
    return this.ord;
  }
  
  public void setOrd(long ord)
  {
    this.ord = ord;
  }
  
  public List<Menu> getChildList()
  {
    return this.childList;
  }
  
  public void setChildList(List<Menu> childList)
  {
    this.childList = childList;
  }
  
  public String toString()
  {
    return "id:" + this.id + "\t" + "parentId:" + this.parentId + "\t" + "menuName:" + this.menuName + "\t" + "menuUrl:" + this.menuUrl + "\t" + "menuIcon:" + this.menuIcon + "\t" + "ord:" + this.ord;
  }
}
