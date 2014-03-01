<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="/resources/style/app.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="/resources/style/jquery-ui-1.10.4.custom.min.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="/resources/style/jquery.ui.timepicker.css" media="screen"/>
    <%--<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">--%>
    <script type="text/javascript" src="/resources/js/openlayers/OpenLayers.js"></script>
    <script type="text/javascript" src="/resources/js/heatmap/heatmap.js"></script>
    <script type="text/javascript" src="/resources/js/heatmap/heatmap-openlayers.js"></script>
    <script type="text/javascript" src="/resources/js/jquery/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="/resources/js/jquery/jquery.ui.timepicker.js"></script>
    <script type="text/javascript" src="/resources/js/jquery/jquery-ui-1.10.4.custom.min.js"></script>
    <script type="text/javascript">

        var isErrorOccurred = false;

        $(document).ready(function () {
            $('.error').hide();

            var dateRegExp = /^\d\d\d\d\-\d\d\-\d\d$/;
            var timeRegExp = /^\d\d\:\d\d$/;

            $('#findButton').click(function (event) {
                isErrorOccurred = false;

                var startDate = $("#startdate").val();
                var startTime = $("#starttime").val();
                var endDate = $("#enddate").val();
                var endTime = $("#endtime").val();
                var deviceId = $("#deviceId").val();

                validateField(startDate, dateRegExp, '#startDateId');
                validateField(startTime, timeRegExp, '#startTimeId');
                validateField(endDate, dateRegExp, '#endDateId');
                validateField(endTime, timeRegExp, '#endTimeId');
                if (deviceId != "")
                    validateNumber(deviceId, "#deviceIdError");

                if (!isErrorOccurred) {
                    var siteUrl = "/signalsviewer/";
                    var timeEnding = ":00";

                    var resultUrl = "";
                    if (deviceId == "") {
                        resultUrl = siteUrl + startDate + "T" + startTime + timeEnding + "/" +
                                endDate + "T" + endTime + timeEnding;
                    } else {
                        resultUrl = siteUrl + deviceId + "/" + startDate + "T" + startTime + timeEnding + "/" +
                                endDate + "T" + endTime + timeEnding;
                    }

                    $.ajax({
                        type: "GET",
                        url: resultUrl,
                        dataType: "json",
                        success: function (signals) {
                            $("#signalsTotalNumber").text(signals.length);
                            addHeatMapLayer(signals);
                        }
                    });
                }
            });
        });

        function validateNumber(value, fieldId) {
            if ($.isNumeric(value)) {
                $(fieldId).hide();
            } else {
                $(fieldId).show();
                isErrorOccurred = true;
            }
        }

        function validateField(value, regExp, fieldId) {
            if (validateWithReg(value, regExp)) {
                $(fieldId).hide();
            }
            else {
                $(fieldId).show();
                isErrorOccurred = true;
            }
        }

        function validateWithReg(value, regexp) {
            var dtRegex = new RegExp(regexp);
            return dtRegex.test(value);
        }

        $(function () {
            $('.dateChooser').datepicker({
                dateFormat: 'yy-mm-dd',
                changeYear: true
            });

            $('.timeChooser').timepicker({
                showPeriodLabels: false
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
        }
        ;

        function addHeatMapLayer(signals) {
            //remove old heatmap layer
            if (currentHeatmap != null) {
                map.removeLayer(currentHeatmap);
            }

            //create data set
            var heatMapDataSet = { max: 90, data: [] };
            for (var i = 0; i < signals.length; i++) {
                heatMapDataSet.data.push({
                    lonlat: new OpenLayers.LonLat(signals[i].longitude, signals[i].latitude),
                    count: signals[i].strength + 121
                });
            }

            //add new heatmap layer to map
            currentHeatmap = new OpenLayers.Layer.Heatmap("Heatmap Layer", map, baseLayer, {visible: true, radius: 30},
                    {isBaseLayer: false, opacity: 0.3, projection: new OpenLayers.Projection("EPSG:4326")});
            map.addLayer(currentHeatmap);
            currentHeatmap.setDataSet(heatMapDataSet);
        }

    </script>
    <title>Viewer</title>
</head>
<body onload='init();'>
<div class="inputArea">
    <label>Input data: </label>

    <label>Start date*:</label>
    <input type="text" class="dateChooser" id="startdate"/>
    <div class="error" id="startDateId">Invalid date</div>

    <label>Start time*:</label>
    <input type="text" class="timeChooser" id="starttime">
    <div class="error" id="startTimeId">Invalid time</div>

    <label>End date*:</label>
    <input type="text" class="dateChooser" id="enddate"/>
    <div class="error" id="endDateId">Invalid date</div>

    <label>End time*:</label>
    <input type="text" class="timeChooser" id="endtime">
    <div class="error" id="endTimeId">Invalid time</div>

    <label>Device id:</label>
    <input type="text" class="deviceChooser" id="deviceId">
    <div class="error" id="deviceIdError">Invalid number</div>

    <input type="button" id="findButton" value="Find"/>

    <label class="statistic">Total number of received signals: </label>
    <label class="statistic" id="signalsTotalNumber"></label>
</div>

<div id="heatmapArea" class="heatmapArea"></div>
</body>
</html>