<%--
  Created by IntelliJ IDEA.
  User: mikalai
  Date: 2/27/14
  Time: 1:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<spring:url value="/resources/js/OpenLayers.js" var="openLayersJs" />
<html>
<script src="${openLayersJs}"></script>
<head>
    <title>test2</title>
</head>
<body>
<div id="demoMap" style="height:250px"></div>
<script>
    map = new OpenLayers.Map("demoMap");
    map.addLayer(new OpenLayers.Layer.OSM());
    map.zoomToMaxExtent();
</script>
<h1>Hello!</h1>
</body>
</html>
