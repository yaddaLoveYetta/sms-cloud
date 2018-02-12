/**
 * 按钮模块
 *
 */
define('FormAction', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');

    var ButtonList = SMS.require('ButtonList');

    var emitter = MiniQuery.Event.create();

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

    function create(config) {
        /*        {
                    classId:1001
                }*/
        loadFormAction(config, function (actions) {

            actions = actions || [];

            __default__.items = actions;

            var bl = new ButtonList(__default__);

            // 总事件，最后触发
            bl.on('click', function (item, index) {
                console.dir(item);
            });

            return bl;

        })


    }

    return {
        create: create,
        on: emitter.on.bind(emitter)
    };

});
