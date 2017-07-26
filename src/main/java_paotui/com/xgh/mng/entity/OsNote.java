package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class OsNote
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keyOsNote";
  private long id;
  private String code;
  private String name;
  private int noteNo;
  private int noteDbNo;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public OsNote() {}
  
  public OsNote(long id)
  {
    this.id = id;
  }
  
  public OsNote(long id, String code, String name, int noteNo, int noteDbNo, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.code = code;
    this.name = name;
    this.noteNo = noteNo;
    this.noteDbNo = noteDbNo;
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
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int getNoteNo()
  {
    return this.noteNo;
  }
  
  public void setNoteNo(int noteNo)
  {
    this.noteNo = noteNo;
  }
  
  public int getNoteDbNo()
  {
    return this.noteDbNo;
  }
  
  public void setNoteDbNo(int noteDbNo)
  {
    this.noteDbNo = noteDbNo;
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
    return "id:" + this.id + "\t" + "code:" + this.code + "\t" + "name:" + this.name + "\t" + "noteNo:" + this.noteNo + "\t" + "noteDbNo:" + this.noteDbNo + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
