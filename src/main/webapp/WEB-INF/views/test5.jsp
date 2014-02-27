<!DOCTYPE html>
<html lang="en">
<head>

    <script type="text/javascript" src="http://openlayers.org/api/OpenLayers.js"></script>
    <script type="text/javascript" src="/resources/js/heatmap/heatmap.js"></script>
    <script type="text/javascript" src="/resources/js/heatmap/heatmap-openlayers.js"></script>
    <script type="text/javascript">
       function init2 () {
            var testData = {
                max: 46,
                data: [
                    {lat: 33.5363, lng: -117.044, count: 1},
                    {lat: 33.5608, lng: -117.24, count: 1},
                    {lat: 38, lng: -97, count: 1},
                    {lat: 38.9358, lng: -77.1621, count: 1}
                ]
            };
            var transformedTestData = { max: testData.max, data: [] },
                    data = testData.data,
                    datalen = data.length,
                    nudata = [];

// in order to use the OpenLayers Heatmap Layer we have to transform our data into
// { max: , data: [{lonlat: , count: },...]}
            while (datalen--) {
                nudata.push({
                    lonlat: new OpenLayers.LonLat(data[datalen].lon, data[datalen].lat),
                    count: data[datalen].count
                });
            }
            transformedTestData.data = nudata;
            var map = new OpenLayers.Map('map_canvas',
                    {
                        allOverlays: true,
                        projection: new OpenLayers.Projection("EPSG:900913"),
                        displayProjection: new OpenLayers.Projection("EPSG:900913"),
                        units: "m",
                        maxResolution: 156543.0339,
                        maxExtent: new OpenLayers.Bounds(-20037508, -20037508, 20037508, 20037508),
                        controls: [
                            new OpenLayers.Control.Navigation(),
                            new OpenLayers.Control.PanZoomBar(),
                            new OpenLayers.Control.LayerSwitcher(),
                            new OpenLayers.Control.ScaleLine(),
                            new OpenLayers.Control.MousePosition()
                        ]
                    });
            var layer = new OpenLayers.Layer.OSM();
// create our heatmap layer
            var heatmap = new OpenLayers.Layer.Heatmap("Heatmap Layer", map, testData,
                    {visible: true, radius: 15},
                    {isBaseLayer: false, opacity: 0.3, projection: new OpenLayers.Projection("EPSG:4326")});
//map.addLayer(layer);
            map.addLayers([layer, heatmap]);

            map.zoomToMaxExtent();
            heatmap.setDataSet(transformedTestData);
// Handler for .ready() called.
        };

        function init() {

            // here is our dataset
            // important: a datapoint now contains lat, lng and count property!
            var testData = {
                max: 46,
                data: [
                    {lat: 33.5363, lng: -117.044, count: 1},
                    {lat: 33.5608, lng: -117.24, count: 1},
                    {lat: 38, lng: -97, count: 1},
                    {lat: 38.9358, lng: -77.1621, count: 1}
                ]
            };

            var transformedTestData = { max: testData.max, data: [] },
                    data = testData.data,
                    datalen = data.length,
                    nudata = [];

            // in order to use the OpenLayers Heatmap Layer we have to transform our data into
            // { max: , data: [{lonlat: , count: },...]}
            while (datalen--) {
                nudata.push({
                    lonlat: new OpenLayers.LonLat(data[datalen].lon, data[datalen].lat),
                    count: data[datalen].count
                });
            }
            transformedTestData.data = nudata;
            var map = new OpenLayers.Map('heatmapArea');
            var layer = new OpenLayers.Layer.OSM();
            // create our heatmap layer
            var heatmap = new OpenLayers.Layer.Heatmap("Heatmap Layer", map, layer, {visible: true, radius: 10}, {isBaseLayer: false, opacity: 0.3, projection: new OpenLayers.Projection("EPSG:4326")});
            map.addLayers([layer, heatmap]);

            map.zoomToMaxExtent();
            heatmap.setDataSet(transformedTestData);
        }
        ;
    </script>
</head>
<body onload='init2();'>
<div id="map_canvas"  ></div>
</body>
</html>