



; define('ProgressBar', function () {


    var config = { value: 100 };

    function render(_config) {

        config = $.extend(_config);
        //var div = $(config.containerID);
        $('#lay-progress').show();
        $('#divProgressBar').progressbar({ value: config.value });  //初始话进度条并设置初始值
        //alert("当前值是: " + $("#divProgressbar").progressbar("option", "value"));  //读取进度条的当前值

    }

    //render(config);
    //updateProgressbarValue();   //调用函数


    //function updateProgressbarValue(value) {
    //    $('#divProgressBar').progressbar({ value: 0 });
    //    $('#divProgressBar').progressbar("option", "value", value);

    //    if (value <= 100) {
    //        setTimeout(updateProgressbarValue, 10);
    //    }
    //    else {
    //        alert('100');

    //    }
    //}

    return {
        render: render,
        //updateProgressbarValue: updateProgressbarValue,

    }

});