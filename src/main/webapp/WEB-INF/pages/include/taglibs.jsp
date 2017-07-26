<%@ page import="java.util.Map" %>
<%@ page import="com.xgh.mng.entity.SysUnits" %>
<%@ page import="com.xgh.util.ConstantUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/h2y-tags" prefix="h2y" %>
<%
    String path = request.getContextPath();
    if (!path.endsWith("/")) {
        path = path + "/";
    }
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    String uiPath = basePath + "resources/ui/";
    String skinName = "Aqua";
%>
<%
    long unitId = 0L;
    HttpSession session1 = request.getSession();
    String sessionId =  "";
    if(session1 != null){
    sessionId = session1.getId() + "_user";
    }else{
    sessionId = "0_user";
    }
    Object obj = session1.getAttribute(sessionId);
    Map userMap = null;
    if(obj != null && obj instanceof Map) {
    userMap = (Map)obj;
    }
    SysUnits sysUnits = null;
    if(userMap != null && !userMap.isEmpty()) {
        Object obj1 = userMap.get(ConstantUtil.SessionKeys.UnitKey.value());
        if(obj1 != null && obj1 instanceof SysUnits) {
            sysUnits = (SysUnits)obj1;
            unitId = sysUnits.getId();
        }
    }
%>
