<%--
  Created by IntelliJ IDEA.
  User: mikalai
  Date: 2/27/14
  Time: 1:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<spring:url value="/resources/js/OpenLayers.js" var="openLayersJs"/>
<html>
<script src="${openLayersJs}"></script>
<head>
    <title>test2</title>
</head>
<body>
<div id="demoMap" style="height:750px"></div>
<script>
    //        map = new OpenLayers.Map("demoMap");
    //        map.addLayer(new OpenLayers.Layer.OSM());
    //        var position = new OpenLayers.LonLat(27, 53);
    //        var zoom = 15;
    //        map.setCenter(position, zoom);

    var map = new OpenLayers.Map('map', { controls: [] });

    map.addControl(new OpenLayers.Control.PanZoomBar());
    map.addControl(new OpenLayers.Control.LayerSwitcher({'ascending': false}));
    map.addControl(new OpenLayers.Control.Permalink());
    map.addControl(new OpenLayers.Control.Permalink('permalink'));
    map.addControl(new OpenLayers.Control.MousePosition());
    map.addControl(new OpenLayers.Control.OverviewMap());
    map.addControl(new OpenLayers.Control.KeyboardDefaults());

    var control = new OpenLayers.Control();
    OpenLayers.Util.extend(control, {
        draw: function () {
            // this Handler.Box will intercept the shift-mousedown
            // before Control.MouseDefault gets to see it
            this.box = new OpenLayers.Handler.Box( control,
                    {"done": this.notice},
                    {keyMask: OpenLayers.Handler.MOD_SHIFT});
            this.box.activate();
        },

        notice: function (bounds) {
            OpenLayers.Console.userError(bounds);
        }
    });
    map.addControl(control);

    map.addLayer(new OpenLayers.Layer.OSM());

</script>
<h1>Hello!</h1>
</body>
</html>
