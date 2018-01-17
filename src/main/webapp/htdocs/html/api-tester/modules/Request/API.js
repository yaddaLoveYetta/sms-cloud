

/**
* 菜单组模块
* 
*/
define('Request/API', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = SMS.require('API');

    var events = [];


    var defaults = {
        'success': function (data, json) {
            SMS.Tips.success('请求成功', 2000);
        },

        'fail': function (code, msg, json) {
            SMS.Tips.error('请求失败', 2000);

        },

        'error': function () {
            SMS.Tips.error('请求发生错误!', 2000);
        },

        'done': function () {

        },
    };


    function parseJSON(data, tryFromQuery) {

        try {
            return window.JSON.parse(data);
        }
        catch (ex) {
            
        }

        try {
            var s = data.replace(/^(\r\n)+/g, '');
            return (new Function('return ' + s))();
        }
        catch (ex) {
            
        }

        try {
            return (new Function('return { ' + data + ' }'))();
        }
        catch (ex2) {

        }

        if (tryFromQuery) {
            var s = data;
            s = s.replace(/\n/g, '').replace(/\s{1,}/g, '');
            return $.Object.parseQueryString(s);
        }

        return null;

    }


    function get(name, data) {
        if (data) {
            data = parseJSON(data, true);
        }

        console.dir(data);

        var api = new API(name);
        api.get(data);

        $.Array.each(events, function (args, index) {
            api.on.apply(api, args);
        });

        api.on(defaults);
    }



    function post(name, data, query) {

        if (data) {
            data = parseJSON(data);
        }

        if (query) {
            query = $.Object.parseQueryString(query);
        }

        var api = new API(name);
        api.post(data, query);

        $.Array.each(events, function (args, index) {
            api.on.apply(api, args);
        });

        api.on(defaults);

    }

    return {
        get: get,
        post: post,

        on: function () {
            var args = [].slice.call(arguments, 0);
            events.push(args);
        },

    };

});






    