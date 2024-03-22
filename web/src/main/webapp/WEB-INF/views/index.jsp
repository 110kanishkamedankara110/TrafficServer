<%--
  Created by IntelliJ IDEA.
  User: BLACKBOX
  Date: 3/16/2024
  Time: 9:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../resources/css/style.css">
</head>
<body>
<nav id="nav-parent" class="navigation">
    <div id="nav-child" class="nav-child">
        <button id="reset" class="resetButton">Reset</button>

        <h1 style="text-align: center;font-size: 40px;">Traffic Data</h1>
        <p class="items">Locations</p>
        <div id="locations" style="font-size: 30px;display:flex;align-items: center;flex-direction: column;gap: 10px">


        </div>
    </div>
    <div id="nav-button" class="cont" onclick="hide()"></div>
</nav>
<div class="main">
    <div class="main-child">
        <div class="column">
            <div style="width:100%;height: 500px;overflow-x: auto;">
                <canvas id="barchart"
                        style="width:fit-content;height: 100%;padding: 20px;box-sizing: border-box;"></canvas>
            </div>
        </div>
        <div class="column">
            <div style="width:100%;height: 500px;overflow-x: auto;">
                <canvas id="myChart"
                        style="width:fit-content;height: 100%;padding: 20px;box-sizing: border-box;"></canvas>

            </div>
            <div style="width: 100%">
                <p id="max" style="text-align: center;font-size: 20px;margin: 0">Max Speed : 230 Kmph</p>
                <p id="min" style="text-align: center;font-size: 20px;margin: 0">Min Speed : 30 Kmph</p>
            </div>

        </div>
    </div>
</div>
<script src="../resources/js/chart.js"></script>
<script src="../resources/js/script.js"></script>
</body>
</html>

