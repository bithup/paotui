package com.xgh.mng.util;

import com.xgh.mng.entity.SysUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil
{
  public static boolean isLogin(HttpServletRequest request)
  {
    boolean flg = false;
    if (request != null)
    {
      HttpSession session = getSession(request);
      
      Object obj = session.getAttribute("user_" + session.getId());
      if ((obj != null) && ((obj instanceof SysUser))) {
        flg = true;
      }
    }
    return flg;
  }
  
  private static HttpSession getSession(HttpServletRequest request)
  {
    if (request != null)
    {
      HttpSession session = request.getSession();
      
      return session;
    }
    return null;
  }
}
