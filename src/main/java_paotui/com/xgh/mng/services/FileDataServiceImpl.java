package com.xgh.mng.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.dao.IFileDataDao;
import com.xgh.mng.entity.FileData;
import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysUnits;
import com.xgh.security.Base64Util;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.FileUploadCode;
import com.xgh.util.JSONUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileDataService")
public class FileDataServiceImpl
  extends BaseService
  implements IFileDataService
{
  private Logger logger = Logger.getLogger(FileDataServiceImpl.class);
  @Autowired
  protected IFileDataDao fileDataDao;
  
  public int add(FileData fileData)
  {
    return this.fileDataDao.add(fileData);
  }
  
  public int update(FileData fileData)
  {
    return this.fileDataDao.update(fileData);
  }
  
  public int delete(long id)
  {
    return this.fileDataDao.deleteById(id);
  }
  
  public int updateByDataSource(Map<String, Object> map)
  {
    return this.fileDataDao.updateByDataSource(map);
  }
  
  public FileData get(long id)
  {
    return this.fileDataDao.get(id);
  }
  
  public List<FileData> getList(Map<String, Object> map)
  {
    List<FileData> list = this.fileDataDao.getList(map);
    
    List<FileData> list2 = new ArrayList();
    String server;
    if ((list != null) && (!list.isEmpty()) && (map.containsKey("server")))
    {
      server = map.get("server") + "";
      for (FileData fileData : list)
      {
        fileData.setUrl(server + fileData.getRelativePath() + fileData.getFileName());
        list2.add(fileData);
      }
    }
    return list2;
  }
  
  public List<FileData> getListPage(Map<String, Object> map)
  {
    return this.fileDataDao.getListPage(map);
  }
  
  public long getRows(Map<String, Object> map)
  {
    return this.fileDataDao.getRows(map);
  }
  
  public List<Map<String, Object>> getFileDatas(Map<String, Object> map)
  {
    List<FileData> list = getList(map);
    
    List<Map<String, Object>> list2 = new ArrayList();
    if ((list != null) && (!list.isEmpty())) {
      for (FileData fileData : list)
      {
        Map<String, Object> map2 = new HashMap();
        map2.put("id", Long.valueOf(fileData.getId()));
        map2.put("dataType", Integer.valueOf(fileData.getDataType()));
        map2.put("dataCode", fileData.getDataCode());
        map2.put("dataId", Long.valueOf(fileData.getDataId()));
        map2.put("url", fileData.getUrl());
        map2.put("fileName", fileData.getFileName());
        map2.put("oldName", fileData.getOldName());
        map2.put("ord", Integer.valueOf(fileData.getOrd()));
        list2.add(map2);
      }
    }
    return list2;
  }
  
  public List<FileData> saveFiles(HttpServletRequest request, String[] fileDataArray, long dataId, ConstantUtil.FileUploadCode fileUploadCode, int dataVersion)
  {
    SysIndustry sysIndustry = getCurrentIndustry(request);
    SysUnits sysUnits = getCurrentUnits(request);
    

    String saveToPath = ConstantUtil.SAVE_PATH;
    

    List<FileData> fileList = new ArrayList();
    if ((fileDataArray != null) && (fileDataArray.length > 0))
    {
      List<FileData> oldFileDataList = new ArrayList();
      for (String fd : fileDataArray)
      {
        Map<String, Object> map = JSONUtil.getMap(fd);
        if (map.get("id") != null)
        {
          FileData fileData = get(Long.valueOf(map.get("id") + "").longValue());
          if (fileData != null)
          {
            fileData.setDataVersion(dataVersion);
            









            fileList.add(fileData);
            oldFileDataList.add(fileData);
          }
        }
        else
        {
          try
          {
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
            if ((map.containsKey("ord")) && (map.get("ord") != null)) {
              fileData.setOrd(Integer.parseInt(map.get("ord") + ""));
            } else {
              fileData.setOrd(0);
            }
            FileUtils.copyFile(new File(savePath), new File(saveToPath + "/" + fuCode + "/" + relative_path + fileName));
            fileList.add(fileData);
          }
          catch (Exception e)
          {
            e.printStackTrace();
            this.logger.error(e.getMessage(), e);
          }
        }
      }
      if (!fileList.isEmpty())
      {
        if (dataVersion < 1)
        {
          Map<String, Object> map = new HashMap();
          map.put("unitId", Long.valueOf(getCurrentUnitId(request)));
          map.put("dataId", Long.valueOf(dataId));
          map.put("dataCode", fileUploadCode.value());
          int dataVersion2 = dataVersion < 1 ? 0 : dataVersion - 1;
          map.put("dataVersion", Integer.valueOf(dataVersion2));
          map.put("dataType", ((FileData)fileList.get(0)).getDataType() + "");
          this.fileDataDao.updateByDataSource(map);
        }
        this.fileDataDao.addBatch(fileList);
        
        return fileList;
      }
      return null;
    }
    return null;
  }
}
