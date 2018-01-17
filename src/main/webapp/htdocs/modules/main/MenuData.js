/**
 * 侧边菜单栏的数据模块
 */
define('MenuData', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API=SMS.require("API");

    var Tips = SMS.Tips;
    var user = SMS.Login.get();


    var list = [];
    var ready = false;

    var homeItem = {
        name: '首页',
        isHome: true,
        id: $.String.random(5),
        url: 'html/home/index.html',
        //url: 'html/home/520/index.html',
    };

    //加载数据。
    //这里采用异步方式，方便以后从服务器端加载。
    function load(fn, fnFail, fnError) {

        if (ready) {
            fn && fn(list);
            return;
        }

        var api = new API('user/getSidebar');

        api.get({
            'type': user.type,
            'userId': user.userId,
        });

        api.on({
            'success': function (data, json) {//成功

                list = data;

                //过滤掉 hidden: true 的分组和项
                list = $.Array.grep(list, function (group, no) {

                    var items = group.items;

                    if (group.hidden || !items || items.length == 0) {//过滤分组
                        return false;
                    }

                    var items = group.items;
                    items = $.Array.grep(items, function (item, index) {
                        return !item.hidden;
                    });

                    if (items.length == 0) {
                        return false;
                    }

                    group.items = items;
                    return true;
                });

                $.Array.each(list, function (group, no) {

                    $.Array.each(group.items, function (item, index) {

                        $.Object.extend(item, {
                            'id': no + '-' + index,
                            'group': no,
                            'index': index
                        });

                        //for test-------------------------
                        if (!item.url) {
                            item.url = 'html/test/test.html?no=' + group.name + '&index=' + item.name;
                        }

                    });
                });

                ready = true;
                fn(list);

            },
            'fail': function (code, msg, json) {
                //fnFail(code, msg);
                //Tips.warn(msg);
                alert(msg);
            },
            'error': function () {
                //fnError('网络繁忙，请重试!');
                //Tips.error('网络繁忙，请重试!');
                alert('网络繁忙，请重试!');
            },
        });

    }

    function getItem(no, index, fn) {

        if (fn) { //异步方式

            load(function (list) {

                var group = list[no];
                var item = group ? group.items[index] : null;
                fn(item);

            });

            return;

        }

        //同步方式
        var group = list[no];
        if (!group) {
            return;
        }

        return group.items[index];
    }


    //找出设置了 autoOpen: true 的项
    function getAutoOpens(data) {

        data = data || list;

        var a = $.Array.map(list, function (group, no) {

            var items = group.items;

            return $.Array.grep(items, function (item, index) {
                return item.autoOpen;
            });
        });

        return $.Array.reduceDimension(a);
    }


    return {

        load: load,

        getHomeItem: function () {
            return homeItem;
        },

        getItem: getItem,
        getAutoOpens: getAutoOpens,
    };
});
