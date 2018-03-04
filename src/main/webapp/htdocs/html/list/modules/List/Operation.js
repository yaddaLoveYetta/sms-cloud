/**
 * 列表数据操作 模块
 *
 */
define('List/Operation', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = SMS.require('API');


    function post(name, params, fn, msg) {

        //延迟显示。 避免数据很快回来造成的只显示瞬间
        SMS.Tips.loading({
            text: msg || '数据加载中...',
            delay: 500
        });


        var api = new API(name, {

            data: params,

            'success': function (data, json) { //success
                fn && fn(data, json);

            },

            'fail': function (code, msg, json) {
                SMS.Tips.error(msg, 2000);

            },

            'error': function () {
                SMS.Tips.error('网络错误，请稍候再试', 2000);
            },

        });
        api.post();

    }

    function del(classId, list, fn) {
        var items = '';
        for (var item in list) {
            if (list[item]) {
                items += (',' + list[item].primaryValue);
            }
        }

        items = items.substr(1);
        post('template/delItem', {
            'classId': classId,
            'items': items,
        }, fn);
    }

    function forbid(classId, list, operateType, fn) {
        var items = '';
        for (var item in list) {
            if (list[item]) {
                items += (',' + list[item].primaryValue);
            }
        }
        items = items.substr(1);

        if (items == '') {
            return;
        }

        post('template/forbid', {
            'classId': classId,
            'items': items,
            'operateType': operateType
        }, fn);

    }

    function review(classId, list, fn) {

        var items = '';
        for (var item in list) {
            if (list[item]) {
                items += (',' + list[item].primaryValue);
            }
        }

        items = items.substr(1);

        post('template/checkItem', {
            'classId': classId,
            'items': items
        }, fn);
    }

    function unReview(classId, list, fn) {

        var items = '';
        for (var item in list) {
            if (list[item]) {
                items += (',' + list[item].primaryValue);
            }
        }

        items = items.substr(1);

        post('template/unCheckItem', {
            'classId': classId,
            'items': items
        }, fn);
    }

    function send(classId, list, fn) {

        var items = '';
        for (var item in list) {
            if (list[item]) {
                items += (',' + list[item].primaryValue);
            }
        }

        items = items.substr(1);

        post('sync/hrp/sendItem', {
            'classId': classId,
            'items': items,
        }, fn);
    }

    return {
        del: del,
        forbid: forbid,
        review: review,
        unReview: unReview,
        send: send,
    };

});

