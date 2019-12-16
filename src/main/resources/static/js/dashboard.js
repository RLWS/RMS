$(document).ready(function () {
    $.ajax({
        url: "/getCurveChartNode",
        type: "get",
        data: {},
        success: function (result) {
            console.log(result);
            var htmlNode = "";
            var htmlTitle = "";
            for (var i = 0; i < result.length; i++) {
                htmlTitle += result[i] + "/";
                htmlNode += "<li><h5><i class=\"fa fa-circle m-r-5 text-info\"></i>" + result[i] + "</h5></li>";
            }
            $("#ct-node").html(htmlNode)
            $("#ct-title").html(htmlTitle.substring(0, htmlTitle.length - 1))
        }
    })
});
