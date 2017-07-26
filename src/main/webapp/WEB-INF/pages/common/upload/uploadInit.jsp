<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <link rel="stylesheet" type="text/css" href="<%=uiPath%>lib/uploadify/uploadify.css"/>
    <script src="<%=uiPath%>lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
    <%--<script src="<%=uiPath%>lib/uploadify/jquery.uploadify.js" type="text/javascript"></script>--%>

    <!-- 解决corome崩溃的问题 -->
    <%-- <script src="<%=uiPath%>lib/uploadify/jquery.uploadify.js?f=<%=System.currentTimeMillis()%>" type="text/javascript"></script> --%>
    <title>文件上传</title>
    <script type="text/javascript">
        var i = 0;
        <!-- 解决corome崩溃的问题 -->
        document.write("<script type='text/javascript' "+ "src='<%=uiPath%>lib/uploadify/jquery.uploadify.js?t=" + new Date()+ "'></s" + "cript>");
        $(function () {

            var paraJson = ${paraJson};
            var fileTypeExts = paraJson.fileTypeExts != null ? paraJson.fileTypeExts : "*.*";
            var fileSizeLimit = paraJson.fileSizeLimit != null ? paraJson.fileSizeLimit : 0;
            var multi = paraJson.multi != null ? paraJson.multi : true;
            var uploadLimit = paraJson.uploadLimit != null ? paraJson.uploadLimit : 0;

            $("#file_upload").uploadify({
                'swf': "<%=uiPath%>lib/uploadify/uploadify.swf",
                'uploader': "<%=basePath%>common/upload/doUpload.htm",
                'method': "post",
                'buttonText': "选择文件",
                'fileTypeExts': fileTypeExts,
                'fileSizeLimit': fileSizeLimit,
                'multi': multi,
                'uploadLimit': uploadLimit,
                onUploadComplete: function (file) {
                    //alert(JSON.stringify(file));
                },
                onUploadSuccess: function (file, data, response) {
                    //alert(JSON.stringify(data));
                    var jsonReturn = eval("(" + data + ")");
                    addFileToTr(jsonReturn);
                },
                onSelect: function (file) {
                    //alert(JSON.stringify(file));
                },
                onError: function (event, queueID, fileObj) {
                    alert("文件上传失败");
                }
            });

        });

        function addFileToTr(data) {
            $(data).each(function () {
                var json_value = JSON.stringify(this);
                $("#sucess_queue_table").append("<tr id=\"sucess_queue_tr" + i + "\">" +
                        "<td><input type=\"hidden\" name=\"fileJsonData\" value='" + json_value + "'/>" + this.fileName + "</td>" +
                        "<td width=\"25px\" align=\"center\"><a class=\"del\" href=\"javascript:void(0);\" onclick=\"deleteFile(" + i + ")\"></a></td>" +
                        "</tr>");
                i++;
            });
        }

        function deleteFile(id) {
            $("#sucess_queue_tr" + id).remove();
        }

        function h2y_returnData() {
            var fileJsonList = "";
            $("#sucess_queue_table [name='fileJsonData']").each(function () {
                if (fileJsonList == "") {
                    fileJsonList = this.value;
                } else {
                    fileJsonList += "," + this.value;
                }
            });
            fileJsonList = "[" + fileJsonList + "]";
            return eval("(" + fileJsonList + ")");
        }
    </script>
</head>

<body>

<form>
    <div id="queue"></div>
    <input type="file" name="myfiles" id="file_upload" multiple="true"/>
</form>

<div id="sucess_queue" width="100%">
    <table width="100%" id="sucess_queue_table">
    </table>
</div>
</body>
</html>