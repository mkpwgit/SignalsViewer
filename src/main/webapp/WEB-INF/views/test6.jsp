<!DOCTYPE html>
<html lang="en">
<div id="heatmapArea" style="height: 650px"></div>
<script type="text/javascript" src="http://openlayers.org/api/OpenLayers.js"></script>
<script type="text/javascript" src="/resources/js/heatmap/heatmap.js"></script>
<script type="text/javascript" src="/resources/js/heatmap/heatmap-openlayers.js"></script>
<script type="text/javascript">
    window.onload = function () {

        var testData = {
            max: 46,
            data: [
                {lat: 33.5363, lng: -117.044, count: 50},
                {lat: 33.5608, lng: -117.24, count: 120},
                {lat: 38, lng: -97, count: 11},
                {lat: 38.9358, lng: -77.1621, count: 50}
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
        var heatmap = new OpenLayers.Layer.Heatmap("Heatmap Layer", map, layer, {visible: true, radius: 10}, {isBaseLayer: false, opacity: 0.3, projection: new OpenLayers.Projection("EPSG:4326")});
        map.addLayers([layer, heatmap]);

        map.zoomToMaxExtent();
        heatmap.setDataSet(transformedTestData);
        alert("Ok");
    };
</script>
</html>