$(document).ready(function () {

    createWebSocket();




    /**
     * 建立webSocket
     */
    function createWebSocket() {
        var webSocket = null;

        //判断当前浏览器是否支持webSocket
        if ('webSocket' in $("window")) {
            webSocket = new webSocket("ws://" + document.location.host + "/hardwareResource");
        } else {
            alert('当前浏览器 Not support webSocket')
        }

        //连接发生错误的回调方法
        webSocket.onerror = function () {
            console.log("webSocket连接发生错误");
        };

        //连接成功建立的回调方法
        webSocket.onopen = function () {
            console.log("webSocket连接成功");
        }

        //接收到消息的回调方法
        webSocket.onmessage = function (event) {
            var result = JSON.parse(event.data);
            createChart();
            if (result.cpu != undefined) {
                $("#cpu").val(result.cpu.totalUsedCpu)
            }
            if (result.memory != undefined) {
                var num = (parseFloat(result.memory.memTotal) - (parseFloat(result.memory.memFree) + parseFloat(result.memory.cached))) / parseFloat(result.memory.memTotal);
                $("#memory").val(num.toFixed(2))
            }

            console.log(JSON.parse(event.data));
        }

        //连接关闭的回调方法
        webSocket.onclose = function () {
            console.log("webSocket连接关闭");
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭webSocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closewebSocket();
        }

        //关闭webSocket连接
        function closewebSocket() {
            webSocket.close();
        }
    }


    /**
     * 建立一个图表
     * @param data
     */
    function createChart(data) {
        new Chartist.Line('#ct-visits', {
            labels: ['2008', '2009', '2010', '2011', '2012', '2013', '2014', '2012', '2013', '2014', '2012', '2013', '2014', '2015'],
            series: [
                [5, 2, 7, 4, 5, 3, 5, 5, 3, 5, 5, 3, 5, 4],
                [2, 5, 2, 6, 2, 5, 2, 2, 5, 2, 2, 5, 2, 4],
                [3, 5, 2, 5, 4, 9, 4, 4, 9, 4, 4, 9, 4, 9]
            ]
        }, {
            top: 0,
            low: 1,
            showPoint: true,
            fullWidth: true,
            plugins: [
                Chartist.plugins.tooltip()
            ],
            axisY: {
                labelInterpolationFnc: function (value) {
                    return (value / 1) + 'k';
                }
            },
            showArea: true
        });
    }
})