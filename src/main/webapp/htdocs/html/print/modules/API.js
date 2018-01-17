

//define('API', function (require, exports, module) {

//    var $ = require('$');
//    var MiniQuery = require('MiniQuery');
//    var SMS = require('SMS');

//    function post(action, data, fn, msg) {

//        //延迟显示。 避免数据很快回来造成的只显示瞬间
//        SMS.Tips.loading({
//            text: msg || '数据加载中，请稍后...',
//            delay: 500
//        });


//        var obj = $.extend(action, data);

//        SMS.API.post('eshop/order', obj, function (data, json) { //成功

//            fn && fn(data, json);

//        }, function (code, msg, json) { //失败

//            SMS.Tips.error(msg, 2000);

//        }, function () { //错误

//            SMS.Tips.error('网络错误，请稍候再试', 2000);
//        });

//    }


//    function list(args, fn) {
//        post({ action: 'list' }, args, fn);
//    }

//    function check(args, fn) {
//        post({ action: 'check' }, args, fn);
//    }

//    function merge(args, fn) {
//        post({ action: 'merge' }, args, fn);
//    }

//    function validate(args, fn) {
//        post({ action: 'validate' }, args, fn);
//    }

//    function split(args, fn) {
//        post({ action: 'split' }, args, fn);
//    }

//    function del(args, fn, msg) {
//        post({ action: 'delete' }, args, fn, msg);
//    }

//    function update(args, fn, msg) {
//        post({ action: 'update' }, args, fn, msg);
//    }

//    return {
//        list: list,
//        check: check,
//        merge: merge,
//        split: split,
//        del: del,
//        update: update
//    }

//});


define('API', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');

    //name: eshop/order, action:check, data:args,
    function post(name, query, data, fn, msg) {

        //延迟显示。 避免数据很快回来造成的只显示瞬间
        SMS.Tips.loading({
            text: msg || '数据加载中，请稍后...',
            delay: 500
        });

        var obj = {
            'data': data,
            'query': query

        };

        //var api = new API(name, {

        //    data: obj,

        //    'success': function (data, json) { //success
        //        fn && fn(data, json);

        //    },

        //    'fail': function (code, msg, json) {
        //        SMS.Tips.error(msg, 2000);

        //    },

        //    'error': function () {
        //        SMS.Tips.error('网络错误，请稍候再试', 2000);
        //    },

        //});
        //api.post();

        var api = new SMS.API(name, obj).post().success(function (data, json) { //请求成功时触发

            SMS.Tips.success('数据加载完成', 2000);
            fn && fn(data, json);

        }).fail(function (code, msg, json) { //请求失败时触发

            SMS.Tips.error(msg, 2000);

        }).error(function () { //请求错误时触发

            SMS.Tips.error('网络错误，请稍候再试', 2000);

        })

    }

    //获取筛选列表
    function filterList(args, fn) {
        post('eshop/filter', 'action=query', args, fn);
    }

    //获取订单列表
    function list(args, fn) {
        post('eshop/delivery', 'action=list', args, fn);
    }

    //获取订单明细
    function goodsList(args, fn) {
        post('eshop/delivery', { action: 'getEntry' }, args, fn);
    }

    function check(args, fn) {
        post('eshop/delivery', { action: 'check' }, args, fn);
    }

    function merge(args, fn) {
        post('eshop/delivery', { action: 'merge' }, args, fn);
    }

    function validate(args, fn) {
        post('eshop/delivery', { action: 'check' }, args, fn);
    }

    function split(args, fn) {
        post('eshop/delivery', { action: 'split' }, args, fn);
    }

    function del(args, fn, msg) {
        post('eshop/delivery', { action: 'delete' }, args, fn, msg);
    }

    function update(args, fn, msg) {
        post('eshop/delivery', { action: 'update' }, args, fn, msg);
    }

    return {
        filterList: filterList,
        list: list,
        check: check,
        merge: merge,
        split: split,
        del: del,
        update: update,
        validate: validate
    }

});
