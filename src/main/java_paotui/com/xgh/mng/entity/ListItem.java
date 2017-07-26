package com.xgh.mng.entity;

import java.io.Serializable;

public class ListItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String value;
  private String text;
  
  public String getValue()
  {
    return this.value;
  }
  
  public ListItem() {}
  
  public ListItem(String value, String text)
  {
    this.value = value;
    this.text = text;
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public void setText(String text)
  {
    this.text = text;
  }
}
