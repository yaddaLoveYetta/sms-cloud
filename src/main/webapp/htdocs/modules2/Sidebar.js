/*
* 主菜单模块
* */

define('Sidebar', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = SMS.require("API");
    var Tips = SMS.Tips;
    var user = SMS.Login.get();

    var emitter = MiniQuery.Event.create();

    var menus = {};
    var ul = document.getElementById('ul-sidebar');

    function loadMenuData(id, fn) {

        if (menus[id]) {
            // 缓存中拿
            return menus[id];
        }
        var api = new API('user/getSidebar');

        api.get({
            'id': id,
        });

        api.on({
            'success': function (data, json) {//成功
                var list = data;

                //过滤掉 hidden: true 的分组和项
                list = $.Array.grep(list, function (menuItem, index) {

                    return menuItem.visible;
                });
                // 缓存起来
                menus[id] = list;

                fn(list);

            },
            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (错误码: {1})', msg, code);
                SMS.Tips.error(s);
            },
            'error': function () {
                SMS.Tips.error('网络繁忙，请重试!');
            },
        });

    }

    function fill(menuList) {

        SMS.Template.fill(ul, menuList, function (item, index) {

            return {
                'index': index,
                'name': item.name,
                'icon': item.icon,
                'id': item.id || 0
            };
        });
    }

    function render() {
        loadMenuData(0, fill);
    }

    function bindEvents() {

    }


    return {
        render: render,
        on: emitter.on.bind(emitter),
    }

});