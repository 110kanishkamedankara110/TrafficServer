var socket = new WebSocket("ws://localhost:8080/web-traffic/MessageSocket");

socket.onopen = function(event) {
    console.log("WebSocket connection opened");
    socket.send("Hello Server!");
};

socket.onmessage = function(event) {
    console.log("Received message from server: " + event.data);
};

socket.onerror = function(event) {
    console.error("WebSocket error: " + event.data);
};

socket.onclose = function(event) {
    console.log("WebSocket connection closed");
};