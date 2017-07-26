    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>${mname}</title>
    <%@ include file="../../include/top_list.jsp" %>
    <%@ include file="../../include/top_Jcrop.jsp" %>
    <%@ include file="../../include/top_lightbox.jsp" %>
    <script src="<%=uiPath%>lib/jquery-ui/jquery-ui.js" type="text/javascript"></script>
    <script src="<%=uiPath%>lib/jquery-ui/jquery-ui-slide.min.js" type="text/javascript"></script>
    <script src="<%=uiPath%>lib/jquery-ui/jquery-ui-timepicker-addon.js" type="text/javascript"></script>

    <style type="text/css">
        .zone_code_select {
            border: 1px solid #aecaf0;
            height: 25px;
            line-height: 25px;
        }
        .advert_img { max-width:400px;}
    </style>


    <script type="text/javascript">
        var Validator = null;
        var isSubmiting = false;
        var op = "${op}";
        var unitType = "${unitType}";
        var kindType = "${kindType}";
        var select_index = 0;
        //文件上传
        var fileType = null;//文件上传的标识
        var fileId = 0;
        var loginUnitId = '${loginUnitId}';
        $(function () {
            $("#toptoolbar").ligerToolBar({
                items: [{line: true}, {
                    text: '保存',
                    click: h2y_save,
                    icon: 'save'
                }, {line: true}, {text: '刷新', click: h2y_refresh, icon: 'refresh'}]
            });
            //平台公司可以选择区域
            if (unitType == 1 || kindType ==2) {

                if (op == "modify") {
                    var prefix = "${zonePrefix}";
                    var prefix_array = prefix.split("_");
                    if (prefix_array.length > 0) {
                        h2y_do_change($("#frist_zone_select"));
                        $("#frist_zone_select").find("option[_zoneid='" + prefix_array[select_index] + "']").attr("selected", true);
                        if (prefix_array.length > 1) {
                            modify_Select(prefix_array);
                        }
                    }
                } else {
                    h2y_do_change($("#frist_zone_select"));
                }
            } else {
                if (op == "modify" && unitType == 1) {
                    var prefix = "${zonePrefix}";
                    var prefix_array = prefix.split("_");
                    if (prefix_array.length > 0) {
                        select_index = 3;
                        h2y_do_change($("#zone_select"));
                        $("#zone_select").find("option[_zoneid='" + prefix_array[select_index] + "']").attr("selected", true);
                        if (prefix_array.length > 1) {
                            modify_Select(prefix_array);
                        }
                    }
                }
            }

            //文件
            if (op == "modify") {
                $(${fileListDataJson}).each(function () {

                    fileId++;
                    //var jsonValue = JSON.stringify(this);
                    var json_value = "{\"fileType\":\"" + this.dataType + "\",\"id\":\"" + this.id + "\"}";
                    if ($("#h2y_file_div_" + this.dataType).attr("_flag") == "image") {
                        $("#h2y_file_div_" + this.dataType).append("<div id=\"h2y_file_" + fileId + "\">" +
                                "<input type=\"hidden\" name=\"fileData\"  value='" + json_value + "'/>" +
                                "<a class=\"light_box_a_" + this.dataType + "\" rel=\"lightbox\" href=\"" + this.url + "\" title=\"" + this.fileName + "\">" +
                                "<img  class=\"advert_img\"  src=\"" + this.url + "\"></a>" +
                                "<a href=\"javascript:deletePic('" + fileId + "');\">删除</a></div>");
                        $(".light_box_a_" + this.dataType).lightBox();
                    } else {
                        $("#h2y_file_div_" + this.dataType).append("<div id=\"h2y_file_" + fileId + "\">" +
                                "<input type=\"hidden\" name=\"fileData\"  value='" + json_value + "'/>" +
                                "<a href=\"javascript:downloadFile('" + this.url + "');\" title=\"" + this.fileName + "\">" + this.fileName + "</a>&nbsp;&nbsp;&nbsp;" +
                                "<a href=\"javascript:deletePic('" + fileId + "');\">删除</a></div>");
                    }

                    if(this.dataType==0){
                        if(this.ord==2){
                            $("#unitLogo_div").append("<input type=\"hidden\" name=\"logoData\"  value='"+json_value+"'/><a class=\"unitLogoImg_lightbox\" rel=\"lightbox\" href=\""+this.url+"\" title=\""+this.fileName+"\"><img class=\"unitLogoImg\" src=\""+this.url+"\"></a>");
                        }else{
                            $("#unitLogo_div").append("<input type=\"hidden\" name=\"logoData\"  value='"+json_value+"'/>");
                        }
                    }
                });
                //处理域名
                if (1 != loginUnitId && '1' != loginUnitId) {
                    var unitDomain = '${sysUnits.unitDomain}';
                    var prefixDomain = '${prefixDomain}';
                    $("#shortDomain").val(unitDomain.substring(prefixDomain.length, unitDomain.length));
                }
            }

            //验证信息
            ${validationRules};
            //验证属性设置
            $.metadata.setType("attr", "validate");
            Validator = deafultValidate($("#editform"));


            $("#userCount").ligerSpinner({type: 'int', height: 25, width: 150, isNegative: false});
            $("#userCount").val(${sysUnits.userCount});
            $("#stopDate").datetimepicker({});

            //Logo上传
            $("#unitLogoUploadBut").click(function(){
                openImageUploadDialog();
            });
        });

        function h2y_do_change(obj) {
            obj.change(function () {
                $(this).nextAll().remove();
                addselect(this.value, $(this));
                $("#_zoneCode").val(this.value);
            });

        }

        //设置区域编码的值
        function h2y_zone_change(obj) {
            $(obj).nextAll().remove();
            addselect(obj.value, $("#zone_select"));
            $("#_zoneCode").val(obj.value);
        }

        function addselect(code, obj) {
            if ("" == code || undefined == code) {
                return;
            }
            $.post("sys/units/getZone.htm", {op: "code", value: code}, function (data) {
                var jsonReturn = eval("(" + data + ")");
                var selectHtml = "";
                $(jsonReturn).each(function () {
                    selectHtml += "<option value=\"" + this.code + "\">" + this.name + "</option>";
                });
                if (selectHtml != "") {
                    selectHtml = "<select class=\"zone_code_select\"><option value=\"\"> </option>" + selectHtml + "</select>";
                    $("#zone_code_td").append(selectHtml);
                    h2y_do_change(obj.next());
                }
            });
        }


        function modify_Select(array) {
            $.post("sys/units/getZone.htm", {op: "id", value: array[select_index]}, function (data) {
                var jsonReturn = eval("(" + data + ")");
                var selectHtml = "";
                $(jsonReturn).each(function () {
                    if (this.id == array[select_index + 1]) {
                        selectHtml += "<option value=\"" + this.code + "\" selected=\"selected\">" + this.name + "</option>";
                    } else {
                        selectHtml += "<option value=\"" + this.code + "\">" + this.name + "</option>";
                    }
                });
                if (selectHtml != "") {
                    selectHtml = "<select class=\"zone_code_select\" id=\"zone_code_select_" + select_index + "\"><option value=\"\"> </option>" + selectHtml + "</select>";
                    $("#zone_code_td").append(selectHtml);
                    h2y_do_change($("#zone_code_select_" + select_index));
                }
                select_index++;
                if (select_index < (array.length - 1)) {
                    modify_Select(array);
                }
            });
        }

        function h2y_save() {
            /**
             * 各个行业下的域名都需要进行对应的组装
             */
            $("#unitDomain").val('${prefixDomain}' + $("#shortDomain").val());

            if (!Validator.form()) return;

            //区域为空使用父级区域  例如济源
            var zoneCode = $("#_zoneCode").val();
            if (undefined == zoneCode || 'undefined' == zoneCode || '' == zoneCode) {
                $("#_zoneCode").val('${parentZoneCode }');
            }

            var queryString = $('#editform').serialize();
            if (isSubmiting) {
                alert("表单正在提交，请稍后操作！");
                return;
            }
            isSubmiting = true;

            $.post("sys/units/save.htm", queryString, function (data) {

                var jsonReturn = eval("(" + data + ")");
                if (op == "modify") {
                    if (jsonReturn.code == "1") {

                        alert(jsonReturn.msg);
                        //管理页面列表刷新
                        if (top.f_getframe("sys_units_init_htm") != null) {
                            top.f_getframe("sys_units_init_htm").f_query();
                        } else if (top.f_getframe("sys_units_initshop_htm") != null) {
                            top.f_getframe("sys_units_initshop_htm").f_query();
                        } else if (top.f_getframe("sys_units_initunit_htm") != null) {
                            top.f_getframe("sys_units_initunit_htm").f_query();
                        }
                        top.f_delTab("sys_units_add_htm_op_modify_id_${sysUnits.id}");
                    } else {
                        alert(jsonReturn.msg);
                    }
                } else {
                    if (jsonReturn.code == "1") {
                        alert(jsonReturn.msg);
                        //管理页面列表刷新
                        if (top.f_getframe("sys_units_init_htm") != null) {
                            top.f_getframe("sys_units_init_htm").f_query();
                        } else if (top.f_getframe("sys_units_initshop_htm") != null) {
                            top.f_getframe("sys_units_initshop_htm").f_query();
                        } else if (top.f_getframe("sys_units_initunit_htm") != null) {
                            top.f_getframe("sys_units_initunit_htm").f_query();
                        }
                        top.f_delTab("sys_units_add_htm_op_add_parentId_${sysUnits.parentId}");
                    } else {
                        alert(jsonReturn.msg);
                    }
                }
                isSubmiting = false;
            });
        }

        function h2y_refresh() {
            document.location.reload();
        }

        /**
         * 文件上传初始化
         */
        function h2y_fileupload(type, flag) {

            var fileTypeExts = "*.jpg;*.png;*.jpeg;*.gif";
            if (flag != "image") {
                fileTypeExts = "*.*";
            }
            openFileUploadDialog({
                fileTypeExts: fileTypeExts,
                multi: true
            });
            fileType = type;
        }

        function h2y_fileUploadCallBack(data) {
            if (data == null) return;
            $(data).each(function () {
                //addToJSON(this,"test","v");
                fileId++;
                //添加属性
                this['fileType'] = fileType;
                var fileData = JSON.stringify(this);
                //获取文件方位url
                var tempurl= this.url;
                var pattern = "/save_path";
                /*把save_path文件置空*/
                tempurl = tempurl.replace(new RegExp(pattern), "");
                //获取文件存储名称
                var fileName = this.fileName;
                if ($("#h2y_file_div_" + fileType).attr("_flag") == "image") {
                    $("#h2y_file_div_" + fileType).append("<div id=\"h2y_file_" + fileId + "\">" +
                            "<input type=\"hidden\" name=\"fileData\"  value='" + fileData + "'/>" +
                            "<a class=\"light_box_a_" + fileType + "\" rel=\"lightbox\" href=\"" + tempurl + "\" title=\"" + fileName + "\">" +
                            "<img  class=\"advert_img\"  src=\"" + tempurl + "\"></a>" +
                            "<a href=\"javascript:deletePic('" + fileId + "'," + fileType + ");\">删除</a></div>");
                    $(".light_box_a_" + fileType).lightBox();
                } else {
                    $("#h2y_file_div_" + fileType).append("<div id=\"h2y_file_" + fileId + "\">" +
                            "<input type=\"hidden\" name=\"fileData\"  value='" + fileData + "'/>" +
                            "<a href=\"" + tempurl + "&saveName=" + fileName + "\" title=\"" + fileName + "\">" + fileName + "</a>&nbsp;&nbsp;&nbsp;" +
                            "<a href=\"javascript:deletePic('" + fileId + "'," + fileType + ");\">删除</a></div>");
                }
            });
        }

        function downloadFile(_url) {
            var url = "<%=basePath%>common/file/down.htm?path" + _url;
            location.href = url;
        }


        function resetPassword(){
            var unitId=$("#unitId").val();
            var unitCode=$("#unitCode").val();
            $.post("mall/memberUser/resetPassword.htm?unitId="+unitId+"&unitCode="+unitCode,  function (data) {
                var jsonReturn = eval("(" + data + ")");
                if (jsonReturn.code == "1") {
                    alert(jsonReturn.msg);
                    h2y_refresh();
                } else {
                    alert(jsonReturn.msg);
                }
            });
        }

        function f_query() {
            var manager = $("#listgrid").ligerGetGridManager();
            manager.loadData(true);
        }

        /**
         * 文件删除
         * @param fileId
         * @param fileType
         * @returns {boolean}
         */
        function deletePic(fileId, fileType) {
            if (!confirm("您确定要删除文件吗?")) {
                return true;
            }
            $("#h2y_file_" + fileId).remove();
            //重新处理预览效果
            $(".light_box_a_" + fileType).lightBox();
        }

        /**
         * logo上传的图片回调
         * @param data
         */
        function h2y_imageUploadCallBack(data){
            if(data==null || data.length==0){
                return;
            }
            var imageHtml = "";
            $(data).each(function(i){
                this['fileType'] = 0;
                var json_value = JSON.stringify(this);
                var tempurl= this.url;
                var pattern = "/save_path";
                /*把save_path文件置空*/
                tempurl = tempurl.replace(new RegExp(pattern), "");
                if(i==1){
                    imageHtml += "<input type=\"hidden\" name=\"logoData\"  value='"+json_value+"'/>" +
                            "<a class=\"unitLogoImg_lightbox\" rel=\"lightbox\" href=\""+this.url+"\" title=\""+this.fileName+"\">" +
                            "<img class=\"unitLogoImg\" src=\""+tempurl+"\"></a>";
                }else{
                    imageHtml += "<input type=\"hidden\" name=\"logoData\"  value='"+json_value+"'/>";
                }
            });
            $("#unitLogo_div").html(imageHtml);
            $(".unitLogoImg_lightbox").lightBox();
        }
    </script>
    <style type="text/css">
        body {
            font-size: 12px;
        }
    </style>

</head>

<body>

<div position="top">
    <table width="100%" class="my_toptoolbar_td">
        <tr>
            <td id="my_toptoolbar_td">
                <div class="l-toolbar">&nbsp;${mname}</div>
            </td>
            <td align="right" width="50%">
                <div id="toptoolbar"></div>
            </td>
        </tr>
    </table>
</div>


<form name="editform" method="post" action="" id="editform">
    <div>
        <input type="hidden" name="op" value="${op}"/>
        <input type="hidden" name="id" id="unitId" value="${sysUnits.id}"/>
        <input type="hidden" name="parentId" value="${sysUnits.parentId}"/>
        <input type="hidden" name="instId" value="${sysUnits.instId}"/>
        <input type="hidden" name="unitCode" id="unitCode" value="${sysUnits.unitCode}"/>
        <input type="hidden" name="instCode" value="${sysUnits.instCode}"/>
        <input type="hidden" name="instNid" value="${sysUnits.instNid}"/>
        <input type="hidden" name="remark" value="${sysUnits.remark}"/>
        <input name="zoneCode" type="hidden" id="_zoneCode" value="${sysUnits.zoneCode}"/>
    </div>
    <table class="h2y_table">
        <tr>
            <td class="h2y_table_label_td">名称:</td>
            <td class="h2y_table_edit_td2">
                <input name="unitName" type="text" id="unitName" class="h2y_input_long"
                       value="${sysUnits.unitName}"/>
                <input type="button" onclick="resetPassword()" id="reset" name="reset" value="重置密码" />
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">简称:</td>
            <td class="h2y_table_edit_td2">
                <input name="shortName" type="text" id="shortName" class="h2y_input_just"
                       value="${sysUnits.shortName}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">域名:</td>
            <td class="h2y_table_edit_td2">
                ${prefixDomain}<input name="shortDomain" type="text" id="shortDomain" class="h2y_input_just" value=""/>
                <input name="unitDomain" type="hidden" id="unitDomain" value="${sysUnits.unitDomain}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">人数:</td>
            <td class="h2y_table_edit_td2">
                <input name="userCount" type="text" id="userCount" value="${sysUnits.userCount}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">地址:</td>
            <td class="h2y_table_edit_td2">
                <input name="unitAddress" type="text" id="unitAddress" class="h2y_input_long"
                       value="${sysUnits.unitAddress}"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">电话区号:</td>
            <td class="h2y_table_edit_td2">
                <input name="telAreaCode" type="text" id="telAreaCode" class="h2y_input_just"
                       value="${sysUnits.telAreaCode}"/>
            </td>
        </tr>
        <tr>
            <td class="h2y_table_label_td">电话:</td>
            <td class="h2y_table_edit_td2">
                <input name="tel" type="text" id="tel" class="h2y_input_just"
                       value="${sysUnits.tel}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">服务电话:</td>
            <td class="h2y_table_edit_td2">
                <input name="telService" type="text" id="telService" class="h2y_input_just"
                       value="${sysUnits.telService}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">传真:</td>
            <td class="h2y_table_edit_td2">
                <input name="fax" type="text" id="fax" class="h2y_input_just"
                       value="${sysUnits.fax}"/></td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">法人:</td>
            <td class="h2y_table_edit_td2">
                <input name="legalPerson" type="text" id="legalPerson" class="h2y_input_just"
                       value="${sysUnits.legalPerson}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">法人手机:</td>
            <td class="h2y_table_edit_td2">
                <input name="legalPersonMobile" type="text" id="legalPersonMobile" class="h2y_input_just"
                       value="${sysUnits.legalPersonMobile}"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">停用时间:</td>
            <td class="h2y_table_edit_td2">
                <input name="stopDate" type="text" id="stopDate" class="h2y_input_just"
                       value="<fmt:formatDate value="${sysUnits.stopDate}"  pattern="yyyy-MM-dd HH:mm"/>"/>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">区域:</td>
            <td id="zone_code_td" class="h2y_table_edit_td2">
                <c:if test="${kindType==1}">
                    <c:if test="${unitType == 1}">
                        <select class="zone_code_select" id="frist_zone_select"
                                validate="{required:true,messages:{required:'请输入内容'}}">
                            <option value=""></option>
                            <c:forEach var="c" items="${zoneList}">
                                <option _zoneid="${c.id}" value="${c.code}">${c.name}</option>
                            </c:forEach>
                        </select>
                    </c:if>
                    <c:if test="${unitType == 2}">
                        ${zoneName }
                        <select class="zone_code_select" id="zone_select"
                                <c:if test="${!empty zoneList}"> validate="{required:true,messages:{required:'请输入内容'}}" </c:if>
                                onchange="h2y_zone_change(this)">
                            <option value=""></option>
                            <c:forEach var="c" items="${zoneList}">
                                <option _zoneid="${c.id}" value="${c.code}"
                                        <c:if test="${op == 'modify' && sysUnits.zoneCode == c.code }">selected="selected"</c:if> >${c.name}</option>
                            </c:forEach>
                        </select>
                    </c:if>
                </c:if>
                <c:if test="${kindType==2}">
                    <select class="zone_code_select" id="frist_zone_select"
                            validate="{required:true,messages:{required:'请输入内容'}}">
                        <option value=""></option>
                        <c:forEach var="c" items="${zoneList}">
                            <option _zoneid="${c.id}" value="${c.code}">${c.name}</option>
                        </c:forEach>
                    </select>
                </c:if>
            </td>
        </tr>


        <tr>
            <td class="h2y_table_label_td">类型:</td>
            <td class="h2y_table_edit_td2">
                <!--regType 1 代理公司注册，2组织机构注册-->
                <c:if test="${kindType==1}">
                    <!--unitType 1 代码行业平台，2行业省级代理-->
                    <c:if test="${unitType == 1}">
                        <h2y:input name="unitType" id="unitType" type="radio" initoption="2,省级代理:3,市级代理" value="${sysUnits.unitType}"/>
                    </c:if>
                    <c:if test="${unitType == 2}">
                        <h2y:input name="unitType" id="unitType" type="radio" initoption="3,市级代理" value="3"/>
                    </c:if>
                </c:if>
                <c:if test="${kindType==2}">
                    <h2y:input name="unitType" id="unitType" type="radio" initoption="10,国家级组织:11,省级组织:12,市级组织" value="${sysUnits.unitType}"/>
                </c:if>
            </td>
        </tr>

        <tr>
            <td class="h2y_table_label_td">
                <input type="button" value="上传Logo11" class="button" id="unitLogoUploadBut"/>:
            </td>
            <td class="h2y_table_edit_td2" colspan="3">
                <div id="unitLogo_div"></div>
            </td>
        </tr>

        <c:forEach var="c" items="${fileTypeList}">
            <tr>
                <td class="h2y_table_label_td">
                    <input type="button" value="上传${c.name}" class="button"
                           onclick="h2y_fileupload('${c.type}','${c.flag}')"/>:
                </td>
                <td class="h2y_table_edit_td2">
                    <div id="h2y_file_div_${c.type}" _flag="${c.flag}"></div>
                </td>
            </tr>
        </c:forEach>

    </table>
</form>
</body>
</html>