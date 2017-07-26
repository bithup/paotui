package com.xgh.mng.entity;

import java.io.Serializable;
import java.util.Date;

public class SysDepartment
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String key = "keySysDepartment";
  private long id;
  private long parentId;
  private long nid;
  private long parentNid;
  private long instId;
  private long instNid;
  private String instCode;
  private long unitId;
  private long unitNid;
  private String unitCode;
  private String deptCode;
  private long deptLeader;
  private String deptName;
  private String deptShortName;
  private String deptDesc;
  private long deptType;
  private String position;
  private Double gpsLongitude;
  private Double gpsLatitude;
  private long isHasShop;
  private long ord;
  private Date createDate;
  private Date updateDate;
  private int status;
  private String remark;
  private String data1;
  private String data2;
  
  public SysDepartment() {}
  
  public SysDepartment(long id)
  {
    this.id = id;
  }
  
  public SysDepartment(long id, long parentId, long nid, long parentNid, long instId, long instNid, String instCode, long unitId, long unitNid, String unitCode, String deptCode, long deptLeader, String deptName, String deptShortName, String deptDesc, long deptType, String position, Double gpsLongitude, Double gpsLatitude, long isHasShop, long ord, Date createDate, Date updateDate, int status, String remark, String data1, String data2)
  {
    this.id = id;
    this.parentId = parentId;
    this.nid = nid;
    this.parentNid = parentNid;
    this.instId = instId;
    this.instNid = instNid;
    this.instCode = instCode;
    this.unitId = unitId;
    this.unitNid = unitNid;
    this.unitCode = unitCode;
    this.deptCode = deptCode;
    this.deptLeader = deptLeader;
    this.deptName = deptName;
    this.deptShortName = deptShortName;
    this.deptDesc = deptDesc;
    this.deptType = deptType;
    this.position = position;
    this.gpsLongitude = gpsLongitude;
    this.gpsLatitude = gpsLatitude;
    this.isHasShop = isHasShop;
    this.ord = ord;
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.status = status;
    this.remark = remark;
    this.data1 = data1;
    this.data2 = data2;
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
  
  public String getInstCode()
  {
    return this.instCode;
  }
  
  public void setInstCode(String instCode)
  {
    this.instCode = instCode;
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
  
  public String getUnitCode()
  {
    return this.unitCode;
  }
  
  public void setUnitCode(String unitCode)
  {
    this.unitCode = unitCode;
  }
  
  public String getDeptCode()
  {
    return this.deptCode;
  }
  
  public void setDeptCode(String deptCode)
  {
    this.deptCode = deptCode;
  }
  
  public long getDeptLeader()
  {
    return this.deptLeader;
  }
  
  public void setDeptLeader(long deptLeader)
  {
    this.deptLeader = deptLeader;
  }
  
  public String getDeptName()
  {
    return this.deptName;
  }
  
  public void setDeptName(String deptName)
  {
    this.deptName = deptName;
  }
  
  public String getDeptShortName()
  {
    return this.deptShortName;
  }
  
  public void setDeptShortName(String deptShortName)
  {
    this.deptShortName = deptShortName;
  }
  
  public String getDeptDesc()
  {
    return this.deptDesc;
  }
  
  public void setDeptDesc(String deptDesc)
  {
    this.deptDesc = deptDesc;
  }
  
  public long getDeptType()
  {
    return this.deptType;
  }
  
  public void setDeptType(long deptType)
  {
    this.deptType = deptType;
  }
  
  public String getPosition()
  {
    return this.position;
  }
  
  public void setPosition(String position)
  {
    this.position = position;
  }
  
  public Double getGpsLongitude()
  {
    return this.gpsLongitude;
  }
  
  public void setGpsLongitude(Double gpsLongitude)
  {
    this.gpsLongitude = gpsLongitude;
  }
  
  public Double getGpsLatitude()
  {
    return this.gpsLatitude;
  }
  
  public void setGpsLatitude(Double gpsLatitude)
  {
    this.gpsLatitude = gpsLatitude;
  }
  
  public long getIsHasShop()
  {
    return this.isHasShop;
  }
  
  public void setIsHasShop(long isHasShop)
  {
    this.isHasShop = isHasShop;
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
  
  public String getData1()
  {
    return this.data1;
  }
  
  public void setData1(String data1)
  {
    this.data1 = data1;
  }
  
  public String getData2()
  {
    return this.data2;
  }
  
  public void setData2(String data2)
  {
    this.data2 = data2;
  }
  
  public String toString()
  {
    return "id:" + this.id + "\t" + "parentId:" + this.parentId + "\t" + "nid:" + this.nid + "\t" + "parentNid:" + this.parentNid + "\t" + "instId:" + this.instId + "\t" + "instNid:" + this.instNid + "\t" + "instCode:" + this.instCode + "\t" + "unitId:" + this.unitId + "\t" + "unitNid:" + this.unitNid + "\t" + "unitCode:" + this.unitCode + "\t" + "deptCode:" + this.deptCode + "\t" + "deptLeader:" + this.deptLeader + "\t" + "deptName:" + this.deptName + "\t" + "deptShortName:" + this.deptShortName + "\t" + "deptDesc:" + this.deptDesc + "\t" + "deptType:" + this.deptType + "\t" + "position:" + this.position + "\t" + "gpsLongitude:" + this.gpsLongitude + "\t" + "gpsLatitude:" + this.gpsLatitude + "\t" + "isHasShop:" + this.isHasShop + "\t" + "ord:" + this.ord + "\t" + "createDate:" + this.createDate + "\t" + "updateDate:" + this.updateDate + "\t" + "status:" + this.status + "\t" + "remark:" + this.remark + "\t" + "data1:" + this.data1 + "\t" + "data2:" + this.data2;
  }
}
