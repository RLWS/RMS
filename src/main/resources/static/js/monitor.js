var websocket = null;
var username = localStorage.getItem("name");

//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://127.0.0.1:8181/hardwareResource");
} else {
    alert('当前浏览器 Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function() {
    console.log("WebSocket连接发生错误");
};

//连接成功建立的回调方法
websocket.onopen = function() {
    console.log("WebSocket连接成功");
}

//接收到消息的回调方法
websocket.onmessage = function(event) {
    console.log(event.data);
}

//连接关闭的回调方法
websocket.onclose = function() {
    console.log("WebSocket连接关闭");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
    closeWebSocket();
}

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}