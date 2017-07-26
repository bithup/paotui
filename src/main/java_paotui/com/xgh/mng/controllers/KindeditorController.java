package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.util.ConstantUtil;
import com.xgh.util.DateUtil;
import com.xgh.util.JSONUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@Scope("prototype")
@RequestMapping({"/kindeditor/"})
public class KindeditorController
  extends BaseController
{
  private Logger logger;
  
  public KindeditorController()
  {
    this.logger = Logger.getLogger(KindeditorController.class);
  }
  
  @RequestMapping(value={"uploadJson"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void updateJson(HttpServletRequest request, HttpServletResponse response)
  {
    String savePath = request.getServletContext().getRealPath("/") + "attached/";
    

    String saveUrl = request.getContextPath() + "/attached/";
    

    HashMap<String, String> extMap = new HashMap();
    extMap.put("image", "gif,jpg,jpeg,png,bmp");
    extMap.put("flash", "swf,flv");
    extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
    extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
    

    long maxSize = 1000000L;
    
    response.setContentType("text/html; charset=UTF-8");
    if (!ServletFileUpload.isMultipartContent(request))
    {
      out(getError("请选择文件。"));
      return;
    }
    File uploadDir = new File(savePath);
    if (!uploadDir.isDirectory()) {
      uploadDir.mkdirs();
    }
    if (!uploadDir.canWrite())
    {
      out(getError("上传目录没有写权限。"));
      return;
    }
    String dirName = request.getParameter("dir");
    if (dirName == null) {
      dirName = "image";
    }
    if (!extMap.containsKey(dirName))
    {
      out(getError("目录名不正确。"));
      return;
    }
    savePath = savePath + dirName + "/";
    saveUrl = saveUrl + dirName + "/";
    File saveDirFile = new File(savePath);
    if (!saveDirFile.exists()) {
      saveDirFile.mkdirs();
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String ymd = sdf.format(new Date());
    savePath = savePath + ymd + "/";
    saveUrl = saveUrl + ymd + "/";
    File dirFile = new File(savePath);
    if (!dirFile.exists()) {
      dirFile.mkdirs();
    }
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    Iterator<String> fileNames = multipartRequest.getFileNames();
    
    int i = 1;
    while (fileNames.hasNext())
    {
      String name = (String)fileNames.next();
      MultipartFile myfile = multipartRequest.getFile(name);
      if (myfile.isEmpty())
      {
        this.logger.info("文件未上传");
      }
      else
      {
        if (myfile.getSize() > maxSize)
        {
          out(getError("上传文件大小超过限制。"));
          return;
        }
        String OriginalFileName = myfile.getOriginalFilename();
        String saveName = DateUtil.getSystemTime().getTime() + "" + i + OriginalFileName.substring(OriginalFileName.lastIndexOf("."), OriginalFileName.length());
        

        String fileExt = saveName.substring(saveName.lastIndexOf(".") + 1).toLowerCase();
        if (!Arrays.asList(((String)extMap.get(dirName)).split(",")).contains(fileExt))
        {
          out(getError("上传文件扩展名是不允许的扩展名。\n只允许" + (String)extMap.get(dirName) + "格式。"));
          return;
        }
        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy/MM/dd/");
        String relative_path = "fwb/" + formatdate.format(new Date());
        String serverPath = ConstantUtil.SAVE_PATH;
        String realPath = ConstantUtil.SAVE_PATH + "/" + relative_path;
        
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
          this.logger.error(e.getMessage(), e);
        }
        Map<String, Object> fileData = new HashMap();
        fileData.put("error", Integer.valueOf(0));
        fileData.put("url", serverPath + "/save_path/" + relative_path + "/" + saveName);
        out(JSONUtil.getJson(fileData));
      }
      i++;
    }
  }
  
  private String getError(String message)
  {
    Map<String, Object> obj = new HashMap();
    obj.put("error", Integer.valueOf(1));
    obj.put("message", message);
    return JSONUtil.getJson(obj);
  }
  
  @RequestMapping({"managerJson"})
  public void managerJson()
  {
    String rootPath = this.request.getServletContext().getRealPath("/") + "attached/";
    
    String rootUrl = this.request.getContextPath() + "/attached/";
    
    String[] fileTypes = { "gif", "jpg", "jpeg", "png", "bmp" };
    
    String dirName = this.request.getParameter("dir");
    if (dirName != null)
    {
      if (!Arrays.asList(new String[] { "image", "flash", "media", "file" }).contains(dirName))
      {
        out("Invalid Directory name.");
        return;
      }
      rootPath = rootPath + dirName + "/";
      rootUrl = rootUrl + dirName + "/";
      File saveDirFile = new File(rootPath);
      if (!saveDirFile.exists()) {
        saveDirFile.mkdirs();
      }
    }
    String path = this.request.getParameter("path") != null ? this.request.getParameter("path") : "";
    String currentPath = rootPath + path;
    String currentUrl = rootUrl + path;
    String currentDirPath = path;
    String moveupDirPath = "";
    if (!"".equals(path))
    {
      String str = currentDirPath.substring(0, currentDirPath.length() - 1);
      moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
    }
    String order = this.request.getParameter("order") != null ? this.request.getParameter("order").toLowerCase() : "name";
    if (path.indexOf("..") >= 0)
    {
      out("Access is not allowed.");
      return;
    }
    if ((!"".equals(path)) && (!path.endsWith("/")))
    {
      out("Parameter is not valid.");
      return;
    }
    File currentPathFile = new File(currentPath);
    if (!currentPathFile.isDirectory())
    {
      out("Directory does not exist.");
      return;
    }
    List<Hashtable> fileList = new ArrayList();
    if (currentPathFile.listFiles() != null) {
      for (File file : currentPathFile.listFiles())
      {
        Hashtable<String, Object> hash = new Hashtable();
        String fileName = file.getName();
        if (file.isDirectory())
        {
          hash.put("is_dir", Boolean.valueOf(true));
          hash.put("has_file", Boolean.valueOf(file.listFiles() != null));
          hash.put("filesize", Long.valueOf(0L));
          hash.put("is_photo", Boolean.valueOf(false));
          hash.put("filetype", "");
        }
        else if (file.isFile())
        {
          String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
          hash.put("is_dir", Boolean.valueOf(false));
          hash.put("has_file", Boolean.valueOf(false));
          hash.put("filesize", Long.valueOf(file.length()));
          hash.put("is_photo", Boolean.valueOf(Arrays.asList(fileTypes).contains(fileExt)));
          hash.put("filetype", fileExt);
        }
        hash.put("filename", fileName);
        hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(file.lastModified())));
        fileList.add(hash);
      }
    }
    if ("size".equals(order)) {
      Collections.sort(fileList, new SizeComparator());
    } else if ("type".equals(order)) {
      Collections.sort(fileList, new TypeComparator());
    } else {
      Collections.sort(fileList, new NameComparator());
    }
    Map<String, Object> result = new HashMap();
    result.put("moveup_dir_path", moveupDirPath);
    result.put("current_dir_path", currentDirPath);
    result.put("current_url", currentUrl);
    result.put("total_count", Integer.valueOf(fileList.size()));
    result.put("file_list", fileList);
    
    this.response.setContentType("application/json; charset=UTF-8");
    out(JSONUtil.getJson(result));
  }
  
  public class NameComparator
    implements Comparator
  {
    public NameComparator() {}
    
    public int compare(Object a, Object b)
    {
      Hashtable hashA = (Hashtable)a;
      Hashtable hashB = (Hashtable)b;
      if ((((Boolean)hashA.get("is_dir")).booleanValue()) && (!((Boolean)hashB.get("is_dir")).booleanValue())) {
        return -1;
      }
      if ((!((Boolean)hashA.get("is_dir")).booleanValue()) && (((Boolean)hashB.get("is_dir")).booleanValue())) {
        return 1;
      }
      return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
    }
  }
  
  public class SizeComparator
    implements Comparator
  {
    public SizeComparator() {}
    
    public int compare(Object a, Object b)
    {
      Hashtable hashA = (Hashtable)a;
      Hashtable hashB = (Hashtable)b;
      if ((((Boolean)hashA.get("is_dir")).booleanValue()) && (!((Boolean)hashB.get("is_dir")).booleanValue())) {
        return -1;
      }
      if ((!((Boolean)hashA.get("is_dir")).booleanValue()) && (((Boolean)hashB.get("is_dir")).booleanValue())) {
        return 1;
      }
      if (((Long)hashA.get("filesize")).longValue() > ((Long)hashB.get("filesize")).longValue()) {
        return 1;
      }
      if (((Long)hashA.get("filesize")).longValue() < ((Long)hashB.get("filesize")).longValue()) {
        return -1;
      }
      return 0;
    }
  }
  
  public class TypeComparator
    implements Comparator
  {
    public TypeComparator() {}
    
    public int compare(Object a, Object b)
    {
      Hashtable hashA = (Hashtable)a;
      Hashtable hashB = (Hashtable)b;
      if ((((Boolean)hashA.get("is_dir")).booleanValue()) && (!((Boolean)hashB.get("is_dir")).booleanValue())) {
        return -1;
      }
      if ((!((Boolean)hashA.get("is_dir")).booleanValue()) && (((Boolean)hashB.get("is_dir")).booleanValue())) {
        return 1;
      }
      return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
    }
  }
}
