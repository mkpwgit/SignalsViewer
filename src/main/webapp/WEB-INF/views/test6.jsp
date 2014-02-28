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
        $(function () {
            $('.dateChooser').datepicker({
                dateFormat: 'yy-mm-dd',
                changeYear: true
            });
        });

        var map;
        var baseLayer;
        var currentHeatmap;


        function init() {
            map = new OpenLayers.Map('heatmapArea');
            baseLayer = new OpenLayers.Layer.OSM();
            map.addLayer(baseLayer);
            map.zoomToMaxExtent();
        };

        function addHeatMapLayer(signals) {
            if (currentHeatmap != null) {
                map.removeLayer(currentHeatmap);
            }

            var transformedTestData = { max: 90, data: [] }, nudata = [];

            for (var i=0; i<signals.length; i++) {
                nudata.push({
                    lonlat: new OpenLayers.LonLat(signals[i].longitude, signals[i].latitude),
                    count: signals[i].strength+120
                });
            }
            transformedTestData.data = nudata;

            currentHeatmap = new OpenLayers.Layer.Heatmap("Heatmap Layer", map, baseLayer, {visible: true, radius: 30}, {isBaseLayer: false, opacity: 0.3, projection: new OpenLayers.Projection("EPSG:4326")});

            map.addLayer(currentHeatmap);

            currentHeatmap.setDataSet(transformedTestData);
        }

        $(function () {
            var siteUrl = "http://localhost:8080/signalsviewer/";

            $('#findButton').click(function () {
                var startDate = $("#startdate").val();
                var endDate = $("#enddate").val();
                var resultUrl = siteUrl + startDate + "/" + endDate;

                $.ajax({
                    type: "GET",
                    url: resultUrl,
                    dataType: "json",
                    success: function (signals) {
                        signals.forEach(print);
                        addHeatMapLayer(signals);
                    }
                });

                function print(signal) {
                    alert("Device id: " + signal.deviceId +
                            " Latitude: " + signal.latitude + " Longitude: " + signal.longitude);
                }

            });


        });

    </script>
</head>
<body onload='init();'>
<div class="inputArea">
    <label>Start date:</label>
    <input type="text" class="dateChooser" id="startdate"/>

    <p/>
    <label>End date:</label>
    <input type="text" class="dateChooser" id="enddate"/>

    <p/>
    <input type="button" id="findButton" value="Find"/>
</div>
<div id="heatmapArea" class="heatmapArea"></div>
</body>
</html>