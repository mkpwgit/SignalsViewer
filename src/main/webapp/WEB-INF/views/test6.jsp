<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/resources/style/app.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="/resources/style/jquery-ui-1.10.4.custom.min.css" media="screen"/>
    <%--<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">--%>
    <script type="text/javascript" src="/resources/js/openlayers/OpenLayers.js"></script>
    <script type="text/javascript" src="/resources/js/heatmap/heatmap.js"></script>
    <script type="text/javascript" src="/resources/js/heatmap/heatmap-openlayers.js"></script>
    <script type="text/javascript" src="/resources/js/jquery/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="/resources/js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#date').datepicker({
                dateFormat : 'yy-mm-dd',
                changeYear : true
            });
        });
    </script>
    <script type="text/javascript">
        function init() {

            var testData = {
                max: 46,
                data: [
                    {lat: 33.5363, lng: -110.044, count: 50},
                    {lat: 33.5608, lng: -117.24, count: 120},
                    {lat: 38, lng: -97, count: 11},
                    {lat: 38.9358, lng: -77.1621, count: 1350}
                ]
            };

            var transformedTestData = { max: testData.max, data: [] },
                    data = testData.data,
                    datalen = data.length,
                    nudata = [];

            while (datalen--) {
                nudata.push({
                    lonlat: new OpenLayers.LonLat(data[datalen].lng, data[datalen].lat),
                    count: data[datalen].count
                });
            }
            transformedTestData.data = nudata;
            var map = new OpenLayers.Map('heatmapArea');
            var layer = new OpenLayers.Layer.OSM();
            // create our heatmap layer
            var heatmap = new OpenLayers.Layer.Heatmap("Heatmap Layer", map, layer, {visible: true, radius: 30}, {isBaseLayer: false, opacity: 0.3, projection: new OpenLayers.Projection("EPSG:4326")});
            map.addLayers([layer, heatmap]);

            map.zoomToMaxExtent();
            heatmap.setDataSet(transformedTestData);
        }
        ;
    </script>
</head>
<body onload='init();'>
<div class="inputArea">
    <input type="text" id="date" name="name"/>
</div>
<div id="heatmapArea" class="heatmapArea"></div>
</body>
</html>