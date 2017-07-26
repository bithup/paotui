<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录接口</title>
    <%@ include file="../include/top_list.jsp" %>
    <script type="text/javascript">

        function save() {
            $.post("/paotui/front/weixinFront/v1/customer/login", $("#loginform").serialize(), function (data) {

                if(data.resultFlg==1){
                    alert("登录成功");
                    var url="<%=basePath%>paotui/wechat/toMenuPage.do?openid=${openid}&state=${state}&token=";
                    var token=data.resultData.token;
                    window.location.href=url+token;
                }
                else{
                    alert("登录失败");
                }


            });
        }
    </script>
</head>
<body>
点击菜单，授权后跳转的页面

</br>
openid:${openid}
</br>


access_token:${access_token}
</br>

refresh_token:${refresh_token}

</br>
oauth2：${oauth2}
</br>
<form id="loginform" action="">
    <input type="hidden" id="openid" name="openid" value="${openid}"/>
    <div style="width:500px;height:200px;float:left">
        <table cellpadding="10" cellspacing="0" border="0" width="100%">

            <tr>
                <td>用户：<input id="_userName" name="loginName" type="text" class="login_inpName"  value="17703760272"/></td>
            </tr>
            <tr>
                <td>密码：<input id="_password" type="password"  name='password' value="123456"  class="login_inpLock"/></td>
            </tr>
            <tr>
                <td align="center"><input type="button" onclick="save();" class="login_iptBut" value="登陆"/>
                </td>
            </tr>
        </table>

        <a href="http://jingxin2016.vicp.io/">testtesttest</a>

    </div>
</form>

</body>
</html>
