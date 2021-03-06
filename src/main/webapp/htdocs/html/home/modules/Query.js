﻿
/**
* 快捷查询模块。
*/
define('Query', function (require, module, exports) {


    var $ = require('$');
    var SMS = require('SMS');


    var ul = document.getElementById('ul-query');
    var div = ul.parentNode;

    var list = [];
    var hasBind = false; //指示是否已绑定了事件，避免重复绑定。
    var maxCount = 3; //显示的最大记录条数


    var loading = new SMS.Loading({
        selector: div,
        container: '#div-loading-query',
    });

    function load(fn) {

        var api = new SMS.API('home/query');

        api.on({
            'success': function (data, json) {
                fn && fn(data);
            },

            'fail': function (code, msg, json) {
                ul.innerHTML = '<li>加载失败: ' + msg + '</li>';
            },

            'error': function () {
                ul.innerHTML = '<li>加载失败，请稍候再试!</li>';
            },
        });

        api.get();
    }


    function render() {

        loading.show();

        load(function (data) {

            list = data.slice(0, maxCount);

            SMS.Template.fill(ul, list, function (item, index) {

                return {
                    'name': item.name,
                    'index': index
                };
            });


            loading.hide();

            bindEvents();
        });
    }

    function bindEvents() {

        if (hasBind) {
            return;
        }

        $(ul).delegate('[data-index]', 'click', function (event) {

            var li = this;
            var index = +li.getAttribute('data-index');
            var item = list[index];

            console.log(index);
            console.dir(item);

        });

        hasBind = true;
    }



    return {
        render: render
    };

});