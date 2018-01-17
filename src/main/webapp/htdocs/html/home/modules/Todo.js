﻿
/**
* 待办任务模块。
*/
define('Todo', function (require, module, exports) {


    var $ = require('$');
    var SMS = require('SMS');


    var div = document.getElementById('div-todo');

    var samples = $.String.getTemplates(div.innerHTML, [
        {
            name: 'row',
            begin: '<!--',
            end: '-->'
        },
        {
            name: 'col',
            begin: '#--col.begin--#',
            end: '#--col.end--#',
            outer: '{cols}'
        }
    ]);


    var list = [];
    var hasBind = false;


    var loading = new SMS.Loading({
        selector: div,
        container: '#div-loading-todo',
        text: '数据加载中，请稍候...',
    });


    function load(fn) {

        var api = new SMS.API('home/todo');

        api.get();

        api.success(function (data, json) {
            fn && fn(data);
        });

        api.fail(function (code, msg, json) {


        });

        api.error(function () {


        });

    }

    //把二维的行号和列号转成一维的索引号
    function getIndex(row, col) {

        return row * 2 + col;

    }



    function render() {


   

        loading.show();


        load(function (data) {

            list = data;
            

            div.innerHTML = $.Array.keep(list, function (cols, rowIndex) {

                return $.String.format(samples.row, {
                    'cols': $.Array.keep(cols, function (item, colIndex) {

                        return $.String.format(samples.col, {
                            'count': item.count,
                            'name': item.name,
                            'row-index': rowIndex,
                            'col-index': colIndex,
                        });

                    }).join('')
                });


            }).join('');

            loading.hide();

            bindEvents();
        });
    }


    function bindEvents() {

        if (hasBind) {
            return;
        }

        $(div).delegate('[data-col]', 'click', function (event) {

            var div = this;
            var row = +div.getAttribute('data-row');
            var col = +div.getAttribute('data-col');

            var index = getIndex(row, col);

            var Iframe = SMS.require('Iframe');
            Iframe.open(0, 0, {
                query: {
                    status: index
                }
            });

            //Iframe.open({
            //    name: 'test',
            //    url: './html/warehouse/index.html',
            //});

            //Iframe.open('abc');
        });
        hasBind = true;
    }



    return {
        render: render
    };

});