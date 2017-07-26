package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class Zone
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keyZone";
  private long id;
  private long nid;
  private String code;
  private String name;
  private String pid;
  private String pcode;
  private String preFix;
  private String level;
  private String isLast;
  private Double longitude;
  private Double latitude;
  private String location;
  private String spellName;
  private String firSpellName;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public Zone() {}
  
  public Zone(long id)
  {
    this.id = id;
  }
  
  public Zone(long id, long nid, String code, String name, String pid, String pcode, String preFix, String level, String isLast, Double longitude, Double latitude, String location, String spellName, String firSpellName, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.code = code;
    this.name = name;
    this.pid = pid;
    this.pcode = pcode;
    this.preFix = preFix;
    this.level = level;
    this.isLast = isLast;
    this.longitude = longitude;
    this.latitude = latitude;
    this.location = location;
    this.spellName = spellName;
    this.firSpellName = firSpellName;
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
  
  public String getPid()
  {
    return this.pid;
  }
  
  public void setPid(String pid)
  {
    this.pid = pid;
  }
  
  public String getPcode()
  {
    return this.pcode;
  }
  
  public void setPcode(String pcode)
  {
    this.pcode = pcode;
  }
  
  public String getPreFix()
  {
    return this.preFix;
  }
  
  public void setPreFix(String preFix)
  {
    this.preFix = preFix;
  }
  
  public String getLevel()
  {
    return this.level;
  }
  
  public void setLevel(String level)
  {
    this.level = level;
  }
  
  public String getIsLast()
  {
    return this.isLast;
  }
  
  public void setIsLast(String isLast)
  {
    this.isLast = isLast;
  }
  
  public Double getLongitude()
  {
    return this.longitude;
  }
  
  public void setLongitude(Double longitude)
  {
    this.longitude = longitude;
  }
  
  public Double getLatitude()
  {
    return this.latitude;
  }
  
  public void setLatitude(Double latitude)
  {
    this.latitude = latitude;
  }
  
  public String getLocation()
  {
    return this.location;
  }
  
  public void setLocation(String location)
  {
    this.location = location;
  }
  
  public String getSpellName()
  {
    return this.spellName;
  }
  
  public void setSpellName(String spellName)
  {
    this.spellName = spellName;
  }
  
  public String getFirSpellName()
  {
    return this.firSpellName;
  }
  
  public void setFirSpellName(String firSpellName)
  {
    this.firSpellName = firSpellName;
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "code:" + this.code + "\t" + "name:" + this.name + "\t" + "pid:" + this.pid + "\t" + "pcode:" + this.pcode + "\t" + "preFix:" + this.preFix + "\t" + "level:" + this.level + "\t" + "isLast:" + this.isLast + "\t" + "longitude:" + this.longitude + "\t" + "latitude:" + this.latitude + "\t" + "location:" + this.location + "\t" + "spellName:" + this.spellName + "\t" + "firSpellName:" + this.firSpellName + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
