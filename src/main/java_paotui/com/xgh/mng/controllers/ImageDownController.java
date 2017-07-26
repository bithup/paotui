package com.xgh.mng.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"common/image"})
@Scope("prototype")
public class ImageDownController
{
  private Logger logger = Logger.getLogger(ImageDownController.class);
  private static final String GIF = "image/gif;charset=GB2312";
  private static final String JPG = "image/jpeg;charset=GB2312";
  private static final String PNG = "image/png;charset=GB2312";
  private static final String BMP = "image/bmp;charset=GB2312";
  
  @RequestMapping({"view"})
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path = request.getParameter("path");
    this.logger.debug("\nurlPath" + path);
    long fileSize = -1L;
    
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    String imageName = "";
    if ((StringUtils.isNotBlank(path)) && (path.startsWith("http:")))
    {
      URL url = new URL(path);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      
      fileSize = connection.getContentLength();
      try
      {
        bis = new BufferedInputStream(connection.getInputStream());
        



        imageName = getUrlName(path);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        return;
      }
    }
    printImage(response, bis, fileSize, imageName);
    if (fis != null) {
      fis.close();
    }
  }
  
  private void printImage(HttpServletResponse response, BufferedInputStream bis, long fileSize, String imageName)
    throws IOException
  {
    if (bis != null)
    {
      this.logger.debug("image print begin");
      

      OutputStream output = response.getOutputStream();
      
      String imp = imageName.toLowerCase();
      if ((imp.endsWith(".jpg")) || (imp.endsWith(".jpeg"))) {
        response.setContentType("image/jpeg;charset=GB2312");
      } else if (imp.endsWith(".gif")) {
        response.setContentType("image/gif;charset=GB2312");
      } else if (imp.endsWith(".png")) {
        response.setContentType("image/png;charset=GB2312");
      } else if (imp.endsWith(".bmp")) {
        response.setContentType("image/bmp;charset=GB2312");
      } else {
        response.setContentType("image/jpeg;charset=GB2312");
      }
      response.setHeader("fileName", imageName);
      response.addHeader("fileLength", fileSize + "");
      

      BufferedOutputStream bos = new BufferedOutputStream(output);
      
      byte[] data = new byte[4096];
      
      int size = 0;
      size = bis.read(data);
      while (size != -1)
      {
        bos.write(data, 0, size);
        size = bis.read(data);
      }
      if (bis != null) {
        bis.close();
      }
      bos.flush();
      if (bos != null) {
        bos.close();
      }
      if (bis != null) {
        bis.close();
      }
      if (output != null) {
        output.close();
      }
      this.logger.debug("print file end");
    }
  }
  
  protected String getUrlName(String url)
  {
    String[] urlname = url.split("/");
    String uname = urlname[(urlname.length - 1)];
    
    return uname;
  }
}
