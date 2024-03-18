let hidden = false

let redData = [];
let greenData = [];
let yellowData = [];
let xValues = [];

var myChart = new Chart("myChart", {
    type: "line",
    data: {
        labels: xValues,
        datasets: [{
            label: "Red",
            data: redData,
            borderColor: "#EE4266",
            fill: false
        }, {
            label: "Green",
            data: greenData,
            borderColor: "#4CCD99",
            fill: false
        }, {
            label: "Yellow",
            data: yellowData,
            borderColor: "#FFC436",
            fill: false
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {display: true},
        scales: {
            xAxes: [{
                suggestedMin: 500,
                suggestedMax: 1000,
                type: 'category',
                ticks: {
                    maxTicksLimit: 5, // Limit number of ticks
                }
            }]

        }

    }
});


var xValues2 = ["Red", "Green", "Yellow"];
var yValues = [];
var barColors = ["#EE4266", "#4CCD99", "#FFC436"];

var barChart = new Chart("barchart", {
    type: "bar",
    data: {
        legend: {display: false},

        labels: xValues2,
        datasets: [{
            backgroundColor: barColors,
            data: yValues
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            xAxes: [{
                suggestedMin: 500,
                suggestedMax: 1000,
                type: 'category',
                ticks: {
                    maxTicksLimit: 5, // Limit number of ticks
                }
            }]

        }

    }
});


function hide() {
    let nav = document.getElementById("nav-parent");
    let button = document.getElementById("nav-button");

    if (!hidden) {
        nav.style.marginLeft = -400 + "px";
        button.style.backgroundImage = "url('../resources/icons/right.svg')";
        hidden = true
    } else {
        nav.style.marginLeft = 0 + "px";
        button.style.backgroundImage = "url('../resources/icons/left.svg')";
        hidden = false;
    }

}

connect();

function connect() {
    var socket = new WebSocket("ws://localhost:8080/web-traffic/MessageSocket");
    socket.onopen = function (event) {
        console.log("WebSocket connection opened");
        socket.send("Hello Server!");
    };

    socket.onmessage = function (event) {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch("http://localhost:8080/web-traffic/api/traffic/locations", requestOptions)
            .then(response => response.json())
            .then(result => {
                let locationData = {};
                result.forEach(loc => {
                    let locdata = {speed: 0, traffic: 0,latitude:loc.latitude,longitude:loc.longitude}
                    locationData[loc.latitude + "" + loc.longitude] = locdata;
                })

                let redData = [];
                let greenData = [];
                let yellowData = [];
                let xValues = [];


                let redAvg = 0;
                let greenAvg = 0;
                let yellowAvg = 0;

                let redCount = 0;
                let greenCount = 0;
                let yellowCount = 0;


                result = JSON.parse(event.data);
                var i = 0;
                result.forEach(element => {

                    locationData[element.gps.latitude + "" + element.gps.longitude].speed = element.speed;
                    locationData[element.gps.latitude + "" + element.gps.longitude].traffic = element.trafficLight;

                    console.log(element);
                    switch (element.trafficLight) {
                        case (1):
                            greenCount++;
                            greenAvg += element.speed;
                            greenData.push(element.speed);
                            break;
                        case (3):
                            redCount++
                            redAvg += element.speed;
                            redData.push(element.speed);
                            break;
                        case (2):
                            yellowCount++;
                            yellowAvg += element.speed
                            yellowData.push(element.speed);
                            break;
                    }
                    xValues.push(i++);
                });

                redAvg = redAvg / redCount;
                greenAvg = greenAvg / greenCount;
                yellowAvg = yellowAvg / yellowCount;


                myChart.data.labels = xValues;
                myChart.data.datasets[0].data = redData;
                myChart.data.datasets[1].data = greenData;
                myChart.data.datasets[2].data = yellowData;


                barChart.data.datasets[0].data[0] = redAvg;
                barChart.data.datasets[0].data[1] = greenAvg;
                barChart.data.datasets[0].data[2] = yellowAvg;

                myChart.update();
                barChart.update();
                loadLocations(locationData);

            })
            .catch(error => console.log('error', error));


    }


    socket.onerror = function (event) {
        console.error("WebSocket error: " + event.data);
    };

    socket.onclose = function (event) {


        console.log("WebSocket connection closed");
        connect();

    };

}


var requestOptions = {
    method: 'GET',
    redirect: 'follow'
};

fetch("http://localhost:8080/web-traffic/api/traffic/locations", requestOptions)
    .then(response => response.json())
    .then(result => {
        let locationData = {};
        result.forEach(loc => {
            let locdata = {speed: 0, traffic: 0,latitude:loc.latitude,longitude:loc.longitude}
            locationData[loc.latitude + "" + loc.longitude] = locdata;
        })

        let xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.addEventListener("readystatechange", function () {
            if (this.readyState === 4) {

                let redAvg = 0;
                let greenAvg = 0;
                let yellowAvg = 0;

                let redCount = 0;
                let greenCount = 0;
                let yellowCount = 0;


                const result = JSON.parse(xhr.responseText);
                let i = 0;
                result.forEach(element => {

                    locationData[element.gps.latitude + "" + element.gps.longitude].speed = element.speed;
                    locationData[element.gps.latitude + "" + element.gps.longitude].traffic = element.trafficLight;


                    switch (element.trafficLight) {
                        case (1):
                            greenData.push(element.speed);
                            greenCount++;
                            greenAvg += element.speed;
                            break;

                        case (3):
                            redData.push(element.speed);
                            redCount++
                            redAvg += element.speed;
                            break;

                        case (2):
                            yellowData.push(element.speed);
                            yellowCount++;
                            yellowAvg += element.speed
                            break;

                    }
                    xValues.push(i++);

                });


                redAvg = redAvg / redCount;
                greenAvg = greenAvg / greenCount;
                yellowAvg = yellowAvg / yellowCount;


                yValues.push(redAvg);
                yValues.push(greenAvg);
                yValues.push(yellowAvg);


                myChart.update();
                barChart.update();

                loadLocations(locationData);
            }
        });

        xhr.open("GET", "http://localhost:8080/web-traffic/api/traffic/getData", true);

        xhr.send();


    })
    .catch(error => console.log('error', error));

function loadLocations(data) {

    let lights = ["#4CCD99", "#FFC436", "#EE4266"]
    document.getElementById("locations").innerHTML="";

    Object.keys(data).reverse().forEach((value) => {


        let mainDiv = document.createElement("div");
        mainDiv.className = "trafficDet";


        let trafficLight = document.createElement("div");
        trafficLight.style = "width: 80px;height: fit-content;border-radius: 50px;background-color:#344955;box-sizing: border-box;padding: 10px;display: flex;flex-direction: column;gap: 5px";

        for (let i = 1; i <= 3; i++) {
            let light = document.createElement("div");
            if (data[value].traffic == i) {
                light.style = "width: 100%;aspect-ratio:1;border-radius: 100%;";
                light.style.backgroundColor=lights[i - 1];
            } else {
                light.style = "width: 100%;aspect-ratio:1;background-color:white;border-radius: 100%;"
            }
            trafficLight.appendChild(light);
        }
        let left = document.createElement("div");
        left.style = "width: 100%;padding: 10px;box-sizing: border-box;display: flex;flex-direction: column;gap: 5px;height: fit-content";

        let leftFont = document.createElement("div");
        leftFont.style="font-size: 15px;box-sizing: border-box;color: #344955";

        let lat=document.createElement("p");
        lat.style="margin: 0";
        lat.innerHTML="Latitude : "+data[value].latitude;

        let lon=document.createElement("p");
        lon.style="margin: 0";
        lon.innerHTML="Longitude : "+data[value].longitude;

        let speed=document.createElement("p");
        speed.style="text-align: end;font-size: 40px;padding: 10px;margin: 0;margin-top: 20px";
        speed.innerHTML=data[value].speed+" Kmph";

        leftFont.appendChild(lat);
        leftFont.appendChild(lon);
        leftFont.appendChild(speed);

        left.appendChild(leftFont);

        mainDiv.appendChild(trafficLight);
        mainDiv.appendChild(left);


        document.getElementById("locations").appendChild(mainDiv);


    });
}