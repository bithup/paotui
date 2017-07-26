package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.security.Base64Util;
import com.xgh.util.ConstantUtil;
import com.xgh.util.DateUtil;
import com.xgh.util.FileUtil;
import com.xgh.util.JSONUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.portlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping({"/common/upload"})
public class FileUploadController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(FileUploadController.class);
  
  @RequestMapping({"/{methodName}"})
  public ModelAndView uploadInit(@PathVariable("methodName") String methodName)
  {
    if (("uploadInit".equals(methodName)) || ("imageUploadInit".equals(methodName)))
    {
      ModelAndView view = new ModelAndView();
      
      view.setViewName("common/upload/" + methodName);
      
      Enumeration<String> names = this.request.getParameterNames();
      while (names.hasMoreElements())
      {
        String name = (String)names.nextElement();
        this.request.setAttribute(name, this.request.getParameter(name));
      }
      return view;
    }
    return null;
  }
  
  @RequestMapping(value={"/doUpload"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void upload(HttpServletRequest request, HttpServletResponse response)
  {
    List<Map<String, Object>> fileList = new ArrayList();
    
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    Iterator<String> fileNames = multipartRequest.getFileNames();
    
    int i = 0;
    while (fileNames.hasNext())
    {
      String name = (String)fileNames.next();
      MultipartFile myfile = multipartRequest.getFile(name);
      if (myfile.isEmpty())
      {
        logger.info("文件未上传");
      }
      else
      {
        String OriginalFileName = myfile.getOriginalFilename();
        String saveName = DateUtil.getSystemTime().getTime() + "" + i + OriginalFileName.substring(OriginalFileName.lastIndexOf("."), OriginalFileName.length());
        

        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy/MM/dd/");
        String relative_path = formatdate.format(new Date());
        

        String serverPath = ConstantUtil.SERVER_URL;
        String realPath = ConstantUtil.TEMP_PATH + "/" + relative_path;
        
        File filePath = new File(realPath);
        if (!filePath.exists()) {
          filePath.mkdirs();
        }
        try
        {
          FileUtil.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, saveName));
        }
        catch (IOException e)
        {
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        Map<String, Object> fileData = new HashMap();
        String uuId = UUID.randomUUID().toString();
        
        fileData.put("uuId", uuId);
        fileData.put("fileSize", Long.valueOf(myfile.getSize()));
        fileData.put("fileName", saveName);
        fileData.put("oldName", myfile.getOriginalFilename());
        fileData.put("ord", Integer.valueOf(i));
        
        fileData.put("savePath", Base64Util.encodeToString(realPath + saveName));
        fileData.put("url", serverPath + "/temp_path/" + relative_path + saveName);
        


        fileList.add(fileData);
      }
      i++;
    }
    out(JSONUtil.getJson(fileList));
  }
  
  @RequestMapping(value={"/doUploadImage"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void uploadImage(HttpServletRequest request, HttpServletResponse response)
  {
    List<Map<String, Object>> fileList = new ArrayList();
    
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    Iterator<String> fileNames = multipartRequest.getFileNames();
    
    int i = 1;
    while (fileNames.hasNext())
    {
      String name = (String)fileNames.next();
      MultipartFile myfile = multipartRequest.getFile(name);
      if (myfile.isEmpty())
      {
        logger.info("文件未上传");
      }
      else
      {
        String saveName = DateUtil.getSystemTime().getTime() + "logo_" + i + "_" + ".jpg";
        

        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy/MM/dd/");
        String relative_path = formatdate.format(new Date());
        

        String serverPath = ConstantUtil.SERVER_URL;
        String realPath = ConstantUtil.TEMP_PATH + "/" + relative_path;
        
        File filePath = new File(realPath);
        if (!filePath.exists()) {
          filePath.mkdirs();
        }
        try
        {
          FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, saveName));
        }
        catch (IOException e)
        {
          e.printStackTrace();
          logger.error(e.getMessage(), e);
        }
        Map<String, Object> fileData = new HashMap();
        String uuId = UUID.randomUUID().toString();
        
        fileData.put("uuId", uuId);
        fileData.put("fileSize", Long.valueOf(myfile.getSize()));
        

        fileData.put("fileName", saveName);
        fileData.put("oldName", myfile.getOriginalFilename() + i + ".jpg");
        fileData.put("ord", Integer.valueOf(i));
        
        fileData.put("savePath", Base64Util.encodeToString(realPath + "/" + saveName));
        fileData.put("url", serverPath + "/temp_path/" + relative_path + "/" + saveName);
        


        fileList.add(fileData);
      }
      i++;
    }
    Map<String, Object> dataMap = new HashMap();
    dataMap.put("success", Boolean.valueOf(true));
    dataMap.put("dataList", fileList);
    

    outJson(dataMap);
  }
}
