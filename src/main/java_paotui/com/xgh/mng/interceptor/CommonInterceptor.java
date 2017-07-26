package com.xgh.mng.interceptor;

import com.xgh.mng.services.ISysCacheService;
import com.xgh.util.MatcherUtil;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonInterceptor
  implements HandlerInterceptor
{
  private static final Logger logger = Logger.getLogger(CommonInterceptor.class);
  private String loginUrl;
  private String errorUrl;
  private String indexUrl;
  private List<String> noFilterUrls;
  @Autowired
  protected ISysCacheService sysCacheService;
  private String mappingURL;
  
  public void setMappingURL(String mappingURL)
  {
    this.mappingURL = mappingURL;
  }
  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    String requestUrl = request.getRequestURI();
    
    String path = request.getContextPath();
    if (!path.endsWith("/")) {
      path = path + "/";
    }
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    


    String contextPath = request.getContextPath();
    if (null != contextPath) {
      requestUrl = requestUrl.substring(contextPath.length() + 1);
    }
    logger.debug("url拦截信息：\n loginUrl:" + this.loginUrl + " \n errorUrl:" + this.errorUrl + " \n noFilterUrls:" + this.noFilterUrls + " \n requestUrl:" + requestUrl);
    
    debugRequestParameters(request);
    if (isHasSqlAttack(request)) {
      return false;
    }
    if (("/".equals(requestUrl)) || ("".equals(requestUrl)))
    {
      if (this.sysCacheService.isUserLogin(request)) {
        response.sendRedirect(basePath + this.indexUrl);
      }
      return true;
    }
    if (requestUrl.equals(this.loginUrl)) {
      return true;
    }
    if (requestUrl.equals(this.errorUrl)) {
      return true;
    }
    if (this.noFilterUrls.contains(requestUrl)) {
      return true;
    }
    if (this.sysCacheService.isUserLogin(request)) {
      return true;
    }
    logger.info("CommonInterceptor###拦截器:" + request.getSession().getId());
    

    response.sendRedirect(basePath + this.loginUrl);
    return false;
  }
  
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception
  {
    logger.info("postHandle");
  }
  
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
    throws Exception
  {
    logger.info("afterCompletion");
  }
  
  private void debugRequestParameters(HttpServletRequest request)
  {
    try
    {
      Enumeration<String> names = request.getParameterNames();
      StringBuffer parameters = new StringBuffer();
      while (names.hasMoreElements())
      {
        String name = (String)names.nextElement();
        String[] values = request.getParameterValues(name);
        String value = "";
        if (values != null) {
          for (String string : values) {
            if (value.equals("")) {
              value = string;
            } else {
              value = value + "," + string;
            }
          }
        }
        parameters.append(name + ":" + value + "\t");
      }
      logger.debug("-method:" + request.getMethod() + ",parameters:{" + parameters + "}");
    }
    catch (Exception e)
    {
      logger.warn(e);
    }
  }
  
  private boolean isHasSqlAttack(HttpServletRequest request)
  {
    String ifQuery = request.getParameter("configQuery");
    if ((null != ifQuery) && (!"".equals(ifQuery))) {
      return MatcherUtil.isSqlAttack(ifQuery);
    }
    return false;
  }
  
  public String getLoginUrl()
  {
    return this.loginUrl;
  }
  
  public void setLoginUrl(String loginUrl)
  {
    this.loginUrl = loginUrl;
  }
  
  public String getErrorUrl()
  {
    return this.errorUrl;
  }
  
  public void setErrorUrl(String errorUrl)
  {
    this.errorUrl = errorUrl;
  }
  
  public List<String> getNoFilterUrls()
  {
    return this.noFilterUrls;
  }
  
  public void setNoFilterUrls(List<String> noFilterUrls)
  {
    this.noFilterUrls = noFilterUrls;
  }
  
  public String getIndexUrl()
  {
    return this.indexUrl;
  }
  
  public void setIndexUrl(String indexUrl)
  {
    this.indexUrl = indexUrl;
  }
}
