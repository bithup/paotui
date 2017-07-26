package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/common/file"})
@Scope("prototype")
public class FileDownController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(FileDownController.class);
  
  @RequestMapping({"/down"})
  public void down(HttpServletRequest request, HttpServletResponse response)
  {
    String url = request.getParameter("path");
    
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    ServletOutputStream servletOutputStream = null;
    BufferedOutputStream bos = null;
    try
    {
      response.setContentType("text/html;charset=UTF-8");
      
      File file = new File(url);
      if (!file.exists())
      {
        out("指定文件不存在！");
      }
      else
      {
        fis = new FileInputStream(file);
        bis = new BufferedInputStream(fis);
        long fileSize = file.length();
        
        String saveName = getFileName(url);
        


        String fileName = new String(saveName.getBytes("GBK"), "ISO-8859-1");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.addHeader("Content-Length", fileSize + "");
        response.setContentLength((int)fileSize);
        
        servletOutputStream = response.getOutputStream();
        
        bos = new BufferedOutputStream(servletOutputStream);
        byte[] buff = new byte[8192];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
          bos.write(buff, 0, bytesRead);
        }
      }
      return;
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      out("出现异常！");
    }
    finally
    {
      try
      {
        if (bis != null) {
          bis.close();
        }
        if (fis != null) {
          fis.close();
        }
        if (bos != null) {
          bos.close();
        }
        if (servletOutputStream != null) {
          servletOutputStream.close();
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  private static String getFileName(String url)
  {
    String[] urlname = url.split("/");
    String uname = urlname[(urlname.length - 1)];
    
    return uname;
  }
}
