



define('ProgressBar', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');


    var config = {};

    function render(_config) {

        config = $.extend(_config);
        var div = config.containerID;

        $(div).progressbar({ value: config.value });  //初始话进度条并设置初始值为30
        alert("当前值是: " + $("#divProgressbar").progressbar("option", "value"));  //读取进度条的当前值

    }
    render(0);//初始化进度条并设置初始值为0

    updateProgressbarValue();   //调用函数


    function updateProgressbarValue() {
        $("#divProgressbar").progressbar({ value: 0 });
        var newValue = $("#divProgressbar").progressbar("option", "value") + 10;
        $("#divProgressbar").progressbar("option", "value", newValue);

        if (newValue <= 100) {
            setTimeout(updateProgressbarValue, 10);
        }
        else {
            alert('100');

        }
    }

    return {
        render: render,
        updateProgressbarValue: updateProgressbarValue,

    }

});