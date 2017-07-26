<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>跑腿放上去页面跳转</title>
</head>

<body>
<%
    String path = request.getContextPath();
    if (!path.endsWith("/")) {
        path = path + "/";
    }
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

    String code=request.getParameter("code");
    String state=request.getParameter( "state");
%>

<script type="text/javascript">

    window.location.href="<%=basePath%>paotui/wechat/toWeixinPage.do?code=<%=code%>&state=<%=state%>";

</script>
</body>
</html>

