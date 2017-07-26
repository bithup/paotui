package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysUnits;
import com.xgh.paotui.dao.IFileDataNewDao;
import com.xgh.paotui.entity.FileData;
import com.xgh.security.Base64Util;
import com.xgh.util.ConstantUtil;
import com.xgh.util.JSONUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("fileDataNewService")
public class FileDataServiceNewImpl extends BaseService
        implements IFileDataServiceNew {
    private Logger logger = Logger.getLogger(FileDataServiceNewImpl.class);

    @Autowired
    protected IFileDataNewDao fileDataDao;

    public int add(FileData fileData) {
        return this.fileDataDao.add(fileData);
    }

    public int update(FileData fileData) {
        return this.fileDataDao.update(fileData);
    }

    public int delete(long id) {
        return this.fileDataDao.deleteById(id);
    }

    public int updateByDataSource(Map<String, Object> map) {
        return this.fileDataDao.updateByDataSource(map);
    }

    public FileData get(long id) {
        return this.fileDataDao.get(id);
    }

    public List<FileData> getList(Map<String, Object> map) {
        List<FileData> list = this.fileDataDao.getList(map);

        List list2 = new ArrayList();
        String server;
        if ((list != null) && (!list.isEmpty()) && (map.containsKey("server"))) {
            server = map.get("server") + "";

/*            for (FileData fileData : list) {
                fileData.setUrl(server + fileData.getRelativePath() + fileData.getFileName());
                list2.add(fileData);
            }*/
            for (FileData fileData : list) {
                fileData.setUrl(server + fileData.getRelativePath() + fileData.getFileName());
                list2.add(fileData);
            }
        }
        return list2;
    }

    public List<FileData> getListPage(Map<String, Object> map) {
        return this.fileDataDao.getListPage(map);
    }

    public long getRows(Map<String, Object> map) {
        return this.fileDataDao.getRows(map);
    }

    public List<Map<String, Object>> getFileDatas(Map<String, Object> map) {
        List<FileData> list = getList(map);
        List list2 = new ArrayList();
        if ((list != null) && (!list.isEmpty())) {
            for (FileData fileData : list) {
                Map map2 = new HashMap();
                map2.put("id", Long.valueOf(fileData.getId()));
                map2.put("dataType", Integer.valueOf(fileData.getDataType()));
                map2.put("dataCode", fileData.getDataCode());
                map2.put("dataId", Long.valueOf(fileData.getDataId()));
                map2.put("url", fileData.getUrl());
                map2.put("fileName", fileData.getFileName());
                map2.put("oldName", fileData.getOldName());
                map2.put("ord", Integer.valueOf(fileData.getOrd()));
                map2.put("type", Integer.valueOf(fileData.getType()));
                map2.put("dataVersion", Integer.valueOf(fileData.getDataVersion()));
                list2.add(map2);
            }
        }
        return list2;
    }

    public List<FileData> saveFiles(HttpServletRequest request, String[] fileDataArray, long dataId, ConstantUtil.FileUploadCode fileUploadCode, int dataVersion, int type) {
        SysIndustry sysIndustry = getCurrentIndustry(request);
        SysUnits sysUnits = getCurrentUnits(request);
        String saveToPath = ConstantUtil.SAVE_PATH;
        List fileList = new ArrayList();
        if ((fileDataArray != null) && (fileDataArray.length > 0)) {
            List oldFileDataList = new ArrayList();
            for (String fd : fileDataArray) {
                Map map = JSONUtil.getMap(fd);
                if (map.get("id") != null) {
                    FileData fileData = get(Long.valueOf(map.get("id") + "").longValue());
                    if (fileData != null) {
                        fileData.setDataVersion(dataVersion);
                        fileList.add(fileData);
                        oldFileDataList.add(fileData);
                    }
                } else {
                    try {
                        String fuCode = fileUploadCode.value();
                        String savePath = Base64Util.decodeToString(map.get("savePath") + "");
                        String fileName = map.get("fileName") + "";
                        String oldName = map.get("oldName") + "";
                        FileData fileData = new FileData();
                        fileData.setInstId(sysIndustry.getId());
                        fileData.setInstNid(sysIndustry.getNid());
                        fileData.setInstCode(sysIndustry.getCode());
                        fileData.setUnitId(sysUnits.getId());
                        fileData.setUnitNid(sysUnits.getNid());
                        fileData.setUnitCode(sysUnits.getUnitCode());
                        fileData.setStatus(1);
                        fileData.setCreateDate(new Date());
                        fileData.setType(type);
                        fileData.setDataCode(fuCode);
                        fileData.setDataId(dataId);
                        fileData.setDataType(Integer.parseInt(map.get("fileType") + ""));
                        fileData.setDataVersion(dataVersion);
                        fileData.setFileName(fileName);
                        fileData.setOldName(oldName);
                        fileData.setFileSuffix(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()));
                        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy/MM/dd/");
                        String relative_path = formatdate.format(new Date());
                        fileData.setRelativePath("/" + fuCode + "/" + relative_path);
                        fileData.setPath(saveToPath);
                        fileData.setFileSize(new File(savePath).length());
                        if ((map.containsKey("ord")) && (map.get("ord") != null))
                            fileData.setOrd(Integer.parseInt(map.get("ord") + ""));
                        else {
                            fileData.setOrd(0);
                        }
                        FileUtils.copyFile(new File(savePath), new File(saveToPath + "/" + fuCode + "/" + relative_path + fileName));
                        fileList.add(fileData);
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.logger.error(e.getMessage(), e);
                    }
                }
            }
            if (!fileList.isEmpty()) {
                if (dataVersion < 1) {//
                    System.out.println("dataVersion..........." + dataVersion);
                    Map map = new HashMap();
                    map.put("unitId", Long.valueOf(getCurrentUnitId(request)));
                    map.put("dataId", Long.valueOf(dataId));
                    map.put("dataCode", fileUploadCode.value());
                    int dataVersion2 = dataVersion < 1 ? 0 : dataVersion - 1;
                    map.put("dataVersion", Integer.valueOf(dataVersion2));
                    map.put("dataType", ((FileData) fileList.get(0)).getDataType() + "");
                    System.out.println("dataType......."+((FileData) fileList.get(0)).getDataType() + "");
                    map.put("type",type);
                    System.out.println("type......."+type);//type=2是单个头像，0为多个头像
                    this.fileDataDao.updateByDataSource(map);
                }
                    this.fileDataDao.addBatch(fileList);//先删除再插入
                return fileList;
            }
            return null;
        }
        return null;
    }
}