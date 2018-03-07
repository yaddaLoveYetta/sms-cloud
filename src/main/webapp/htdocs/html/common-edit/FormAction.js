/**
 * 按钮模块
 *
 */
define('FormAction', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');

    var __default__ = {
        container: '#div-button-list',
        fields: {
            text: 'text',
            child: 'items',
            callback: 'click',
            route: 'name',
        },
        autoClose: true,
        items: []
    };

    function loadFormAction(config, fn) {
        var api = new API("template/getFormAction");

        api.get({
            classId: config.classId
        });

        api.on({
            'success': function (data, json) {

                console.log('getFormAction finished');
                fn && fn(data, json);
            },

            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (错误码: {1})', msg, code);
                SMS.Tips.error(s);
            },

            'error': function () {
                SMS.Tips.error('网络繁忙，请稍候再试');
            }
        });
    }

    function create(config, fn) {

        $.Object.extend(__default__, config);

        loadFormAction(config, function (actions) {

            actions = actions || [];

            // 处理菜单分组--actions是Object
            actions = $.Array.aggregate(actions, 'group', function (item, index) {
                return {
                    text: item.name,
                    name: item.text,
                    icon: item.icon
                };
            });
            // 处理菜单分组--转换成Array结构
            actions = $.Object.toArray(actions, function (index, group) {
                if (group.length > 1) {
                    // 多个菜单-分组
                    var item = group[0];
                    item.items = group.slice(1, group.length);

                    return item;
                }

                return group[0];
            })

            __default__.items = actions;

            fn && fn(__default__);

        });

    }

    return {
        create: create
    };

});
