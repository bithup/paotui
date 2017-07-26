package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysUnits
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysUnits";
  private long id;
  private long nid;
  private long parentId;
  private long parentNid;
  private long instId;
  private long instNid;
  private String instCode;
  private String unitCode;
  private int unitType;
  private int unitKind;
  private String zoneCode;
  private String unitDomain;
  private String unitName;
  private String shortName;
  private Date regDate;
  private Date stopDate;
  private long userCount;
  private String unitStatus;
  private String adminUrl;
  private String portalUrl;
  private String unitAddress;
  private String telAreaCode;
  private String tel;
  private String telService;
  private String fax;
  private String legalPerson;
  private String legalPersonMobile;
  private String logoPath;
  private String logoUrl;
  private int version;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  
  public SysUnits() {}
  
  public SysUnits(long id)
  {
    this.id = id;
  }
  
  public SysUnits(long id, long nid, long parentId, long parentNid, long instId, long instNid, String instCode, String unitCode, int unitType, int unitKind, String zoneCode, String unitDomain, String unitName, String shortName, Date regDate, Date stopDate, long userCount, String unitStatus, String adminUrl, String portalUrl, String unitAddress, String telAreaCode, String tel, String telService, String fax, String legalPerson, String legalPersonMobile, String logoPath, String logoUrl, int version, long ord, Date createDate, Date updateDate, int status, String remark)
  {
    this.id = id;
    this.nid = nid;
    this.parentId = parentId;
    this.parentNid = parentNid;
    this.instId = instId;
    this.instNid = instNid;
    this.instCode = instCode;
    this.unitCode = unitCode;
    this.unitType = unitType;
    this.unitKind = unitKind;
    this.zoneCode = zoneCode;
    this.unitDomain = unitDomain;
    this.unitName = unitName;
    this.shortName = shortName;
    this.regDate = regDate;
    this.stopDate = stopDate;
    this.userCount = userCount;
    this.unitStatus = unitStatus;
    this.adminUrl = adminUrl;
    this.portalUrl = portalUrl;
    this.unitAddress = unitAddress;
    this.telAreaCode = telAreaCode;
    this.tel = tel;
    this.telService = telService;
    this.fax = fax;
    this.legalPerson = legalPerson;
    this.legalPersonMobile = legalPersonMobile;
    this.logoPath = logoPath;
    this.logoUrl = logoUrl;
    this.version = version;
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
  
  public long getParentId()
  {
    return this.parentId;
  }
  
  public void setParentId(long parentId)
  {
    this.parentId = parentId;
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
  
  public String getInstCode()
  {
    return this.instCode;
  }
  
  public void setInstCode(String instCode)
  {
    this.instCode = instCode;
  }
  
  public String getUnitCode()
  {
    return this.unitCode;
  }
  
  public void setUnitCode(String unitCode)
  {
    this.unitCode = unitCode;
  }
  
  public int getUnitType()
  {
    return this.unitType;
  }
  
  public void setUnitType(int unitType)
  {
    this.unitType = unitType;
  }
  
  public int getUnitKind()
  {
    return this.unitKind;
  }
  
  public void setUnitKind(int unitKind)
  {
    this.unitKind = unitKind;
  }
  
  public String getZoneCode()
  {
    return this.zoneCode;
  }
  
  public void setZoneCode(String zoneCode)
  {
    this.zoneCode = zoneCode;
  }
  
  public String getUnitDomain()
  {
    return this.unitDomain;
  }
  
  public void setUnitDomain(String unitDomain)
  {
    this.unitDomain = unitDomain;
  }
  
  public String getUnitName()
  {
    return this.unitName;
  }
  
  public void setUnitName(String unitName)
  {
    this.unitName = unitName;
  }
  
  public String getShortName()
  {
    return this.shortName;
  }
  
  public void setShortName(String shortName)
  {
    this.shortName = shortName;
  }
  
  public Date getRegDate()
  {
    return this.regDate;
  }
  
  public void setRegDate(Date regDate)
  {
    this.regDate = regDate;
  }
  
  public Date getStopDate()
  {
    return this.stopDate;
  }
  
  public void setStopDate(Date stopDate)
  {
    this.stopDate = stopDate;
  }
  
  public long getUserCount()
  {
    return this.userCount;
  }
  
  public void setUserCount(long userCount)
  {
    this.userCount = userCount;
  }
  
  public String getUnitStatus()
  {
    return this.unitStatus;
  }
  
  public void setUnitStatus(String unitStatus)
  {
    this.unitStatus = unitStatus;
  }
  
  public String getAdminUrl()
  {
    return this.adminUrl;
  }
  
  public void setAdminUrl(String adminUrl)
  {
    this.adminUrl = adminUrl;
  }
  
  public String getPortalUrl()
  {
    return this.portalUrl;
  }
  
  public void setPortalUrl(String portalUrl)
  {
    this.portalUrl = portalUrl;
  }
  
  public String getUnitAddress()
  {
    return this.unitAddress;
  }
  
  public void setUnitAddress(String unitAddress)
  {
    this.unitAddress = unitAddress;
  }
  
  public String getTelAreaCode()
  {
    return this.telAreaCode;
  }
  
  public void setTelAreaCode(String telAreaCode)
  {
    this.telAreaCode = telAreaCode;
  }
  
  public String getTel()
  {
    return this.tel;
  }
  
  public void setTel(String tel)
  {
    this.tel = tel;
  }
  
  public String getTelService()
  {
    return this.telService;
  }
  
  public void setTelService(String telService)
  {
    this.telService = telService;
  }
  
  public String getFax()
  {
    return this.fax;
  }
  
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  
  public String getLegalPerson()
  {
    return this.legalPerson;
  }
  
  public void setLegalPerson(String legalPerson)
  {
    this.legalPerson = legalPerson;
  }
  
  public String getLegalPersonMobile()
  {
    return this.legalPersonMobile;
  }
  
  public void setLegalPersonMobile(String legalPersonMobile)
  {
    this.legalPersonMobile = legalPersonMobile;
  }
  
  public String getLogoPath()
  {
    return this.logoPath;
  }
  
  public void setLogoPath(String logoPath)
  {
    this.logoPath = logoPath;
  }
  
  public String getLogoUrl()
  {
    return this.logoUrl;
  }
  
  public void setLogoUrl(String logoUrl)
  {
    this.logoUrl = logoUrl;
  }
  
  public int getVersion()
  {
    return this.version;
  }
  
  public void setVersion(int version)
  {
    this.version = version;
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
    return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "parentId:" + this.parentId + "\t" + "parentNid:" + this.parentNid + "\t" + "instId:" + this.instId + "\t" + "instNid:" + this.instNid + "\t" + "instCode:" + this.instCode + "\t" + "unitCode:" + this.unitCode + "\t" + "unitType:" + this.unitType + "\t" + "unitKind:" + this.unitKind + "\t" + "zoneCode:" + this.zoneCode + "\t" + "unitDomain:" + this.unitDomain + "\t" + "unitName:" + this.unitName + "\t" + "shortName:" + this.shortName + "\t" + "regDate:" + this.regDate + "\t" + "stopDate:" + this.stopDate + "\t" + "userCount:" + this.userCount + "\t" + "unitStatus:" + this.unitStatus + "\t" + "adminUrl:" + this.adminUrl + "\t" + "portalUrl:" + this.portalUrl + "\t" + "unitAddress:" + this.unitAddress + "\t" + "telAreaCode:" + this.telAreaCode + "\t" + "tel:" + this.tel + "\t" + "telService:" + this.telService + "\t" + "fax:" + this.fax + "\t" + "legalPerson:" + this.legalPerson + "\t" + "legalPersonMobile:" + this.legalPersonMobile + "\t" + "logoPath:" + this.logoPath + "\t" + "logoUrl:" + this.logoUrl + "\t" + "version:" + this.version + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark;
  }
}
