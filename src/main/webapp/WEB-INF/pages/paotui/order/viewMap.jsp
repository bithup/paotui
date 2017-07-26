<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        body, html {width: 100%;height: 100%;overflow: hidden;margin:0;}
        #locationmap {width: 100%;height:100%;overflow: hidden;margin:0;}
    </style>
    <script src="<%=uiPath%>lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7wfHWsTFhYFhDfatKEj2w5wv"></script>
    <title>查看订单轨迹</title>
</head>
<body>
<div id="locationmap"></div>
</body>
</html>
<script type="text/javascript">
    var orderType = ${orderMap.orderType};
    var city = "${orderCityName}";
    var map = new BMap.Map("locationmap");
    map.centerAndZoom(city, 12);
    map.enableScrollWheelZoom();
    map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.ScaleControl());
    $(function(){
        var result = ${orderPositionPaths};
        if (orderType==1){
            var pointA=new BMap.Point("${orderMap.longitudeA}", "${orderMap.latitudeA}");
            addMarker(pointA, map, "发货地");
            var pointB=new BMap.Point("${orderMap.longitudeB}", "${orderMap.latitudeB}");
            addMarker(pointB, map, "收货地");
        }else if (orderType==2){
            var pointA=new BMap.Point("${orderMap.longitudeA}", "${orderMap.latitudeA}");
            addMarker(pointA, map, "取货地");
            var pointB=new BMap.Point("${orderMap.longitudeB}", "${orderMap.latitudeB}");
            addMarker(pointB, map, "收货地");
        }
        else if (orderType==3){
            var pointA=new BMap.Point("${orderMap.longitudeA}", "${orderMap.latitudeA}");
            addMarker(pointA, map, "购买地");
            var pointB=new BMap.Point("${orderMap.longitudeB}", "${orderMap.latitudeB}");
            addMarker(pointB, map, "收货地");
        }else if (orderType==4){
            var pointA=new BMap.Point("${orderMap.longitudeA}", "${orderMap.latitudeA}");
            addMarker(pointA, map, "排队地");
        }
        var points = [];
        for(i=0;i<result.length;i++)
        {
            var longitude = result[i].longitude; // 经度
            var latitude = result[i].latitude; // 纬度
            var point = new BMap.Point(longitude, latitude);
            if(i==0)
            {
                map.setCenter(point);
            }
            points.push(point);
        }
        var polyline = new BMap.Polyline(points);
        map.addOverlay(polyline);

    })
    function addMarker(point, map, tips)
    {
        var marker = new BMap.Marker(point);
        map.addOverlay(marker);
        var label = new BMap.Label(tips,{offset:new BMap.Size(20,-10)});
        marker.setLabel(label);
    }
</script>
