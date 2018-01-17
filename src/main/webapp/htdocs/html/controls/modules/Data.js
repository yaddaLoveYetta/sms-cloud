


var Data = (function ($, MiniQuery, SMS) {


    var baseUrl = SMS.Url.root() + 'data/demo/';

    

    function getUrl(name, url) {

        if (!SMS.Url.check(url)) { //相对 url
            url = baseUrl + name + '/' + url;
        }

        //加上随机查询字符串，以确保拿到最新版本。
        url = $.Url.randomQueryString(url);

        return url;
    }


    function checkReady(json, fn) {

        var demos = json.demos;


        for (var i = 0; i < demos.length; i++) {
            var panels = demos[i].panels;

            for (var index = 0; index < panels.length; index++) {

                var item = panels[index];
                if (!('content' in item)) {
                    return false;
                }
            }
        }

        
        fn && fn(json);
        return true;

    }


    function load(name, fn) {

        var url = baseUrl + name + '/data.json?r=' + Math.random();

        $.getJSON(url, function (json) {

            var demos = json.demos;

            $.Array.each(demos, function (item, index) {

                $.Array.each(item.panels, function (item, index) {

                    if ('content' in item) { //如果显示指定了 content 字段，则不发起直接 ajax 请求
                        item.content = String(item.content); //转成 string

                        if (checkReady(json, fn)) {
                            return;
                        }
                    }

                    var url = getUrl(name, item.url);

                    //这里要作为文本去获取，因为 jQuery 会自动执行 js 代码，这不是我们想要的行为
                    $.get(url, function (content) {
                        item.content = content;
                        checkReady(json, fn)

                    }, 'text'); //强行指定为文本类型

                });
            });

        });

    }
    

    return {

        load: load,
    };



})($, MiniQuery, SMS);