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
        <h1 style="text-align: center;font-size: 40px;">Traffic Data</h1>
        <div id="locations" style="font-size: 30px;display:flex;align-items: center;flex-direction: column;gap: 10px">
            <p class="items">Locations</p>



        </div>
    </div>
    <div id="nav-button" class="cont" onclick="hide()"></div>
</nav>
<div class="main">
    <div class="main-child">
        <div class="column">
            <canvas id="barchart"
                    style="width:1000px;height: 100%;padding: 20px;box-sizing: border-box;position: fixed"></canvas>

        </div>
        <div class="column">
            <div style="width:100%;height: 500px;overflow-x: auto;">
                <canvas id="myChart"
                        style="width:1000px;height: 100%;padding: 20px;box-sizing: border-box;position: fixed"></canvas>
            </div>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
<script src="../resources/js/script.js"></script>
</body>
</html>

