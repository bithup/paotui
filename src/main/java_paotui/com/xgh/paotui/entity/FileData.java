package com.xgh.paotui.entity;

import java.io.Serializable;
import java.util.Date;

public class FileData
        implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String key = "keyFileData";
    private long id;
    private long nid;
    private long instId;
    private long instNid;
    private String instCode;
    private long unitId;
    private long unitNid;
    private String unitCode;
    private String dataCode;
    private int dataType;
    private long dataId;
    private int dataVersion;
    private String path;
    private String relativePath;
    private String fileName;
    private String oldName;
    private long fileSize;
    private String fileSuffix;
    private String url;
    private int status;
    private Date createDate;
    private int ord;
    private int type;

    public FileData() {
    }

    public FileData(long id) {
        this.id = id;
    }

    public FileData(long id, long nid, long instId, long instNid, String instCode, long unitId, long unitNid, String unitCode, String dataCode, int dataType, long dataId, int dataVersion, String path, String relativePath, String fileName, String oldName, long fileSize, String fileSuffix, String url, int status, Date createDate, int ord, int type) {
        this.id = id;
        this.nid = nid;
        this.instId = instId;
        this.instNid = instNid;
        this.instCode = instCode;
        this.unitId = unitId;
        this.unitNid = unitNid;
        this.unitCode = unitCode;
        this.dataCode = dataCode;
        this.dataType = dataType;
        this.dataId = dataId;
        this.dataVersion = dataVersion;
        this.path = path;
        this.relativePath = relativePath;
        this.fileName = fileName;
        this.oldName = oldName;
        this.fileSize = fileSize;
        this.fileSuffix = fileSuffix;
        this.url = url;
        this.status = status;
        this.createDate = createDate;
        this.ord = ord;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNid() {
        return this.nid;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public long getInstId() {
        return this.instId;
    }

    public void setInstId(long instId) {
        this.instId = instId;
    }

    public long getInstNid() {
        return this.instNid;
    }

    public void setInstNid(long instNid) {
        this.instNid = instNid;
    }

    public String getInstCode() {
        return this.instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public long getUnitId() {
        return this.unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getUnitNid() {
        return this.unitNid;
    }

    public void setUnitNid(long unitNid) {
        this.unitNid = unitNid;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getDataCode() {
        return this.dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public long getDataId() {
        return this.dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public int getDataVersion() {
        return this.dataVersion;
    }

    public void setDataVersion(int dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRelativePath() {
        return this.relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOldName() {
        return this.oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSuffix() {
        return this.fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getOrd() {
        return this.ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public String toString() {
        return "id:" + this.id + "\t" + "nid:" + this.nid + "\t" + "instId:" + this.instId + "\t" + "instNid:" + this.instNid + "\t" + "instCode:" + this.instCode + "\t" + "unitId:" + this.unitId + "\t" + "unitNid:" + this.unitNid + "\t" + "unitCode:" + this.unitCode + "\t" + "dataCode:" + this.dataCode + "\t" + "dataType:" + this.dataType + "\t" + "dataId:" + this.dataId + "\t" + "dataVersion:" + this.dataVersion + "\t" + "path:" + this.path + "\t" + "relativePath:" + this.relativePath + "\t" + "fileName:" + this.fileName + "\t" + "oldName:" + this.oldName + "\t" + "fileSize:" + this.fileSize + "\t" + "fileSuffix:" + this.fileSuffix + "\t" + "url:" + this.url + "\t" + "status:" + this.status + "\t" + "createDate:" + this.createDate + "\t" + "ord:" + this.ord;
    }
}