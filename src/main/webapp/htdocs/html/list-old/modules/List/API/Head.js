/**
 *
 *
 */
define('List/API/Head', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var user = SMS.Login.get();

    var cache = null;
    var headItems = [];
    var display;
    // 字段显示权限-后端FDisPlay定义 1：平台用户显示，2：供应商用户显示：3：都显示
    if (user.type == 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
        // 平台用户
        display = 1; // 00 -- 01 --10 --11
    } else if (user.type == 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
        // 供应商用户
        display = 2; // 10
    } else {
        display = 0;
    }

    /**
     * 获取表头信息，即列的信息
     */
    function get(config, fn) {

        // 从缓存中读取
        if (cache) {
            console.log('getHead from cache!');
            fn && fn(cache);
            return;
        }

        var api = new API('template/getFormTemplate');

        api.get({
            classId: config.classId,
            // formatted : true,
        });

        api.on('success', function (data, json) {

            console.log('getHead finished');

            cache = data;
            fn && fn(data);

        });

        api.on('fail', function (code, msg, json) {

            var s = $.String.format('{0} (错误码: {1})', msg, code);
            SMS.Tips.error(s);

        });

        api.on('error', function () {
            SMS.Tips.error('网络繁忙，请稍候再试');

        });

    }

    function getItems(fields) {

        if (headItems.length > 0) {
            return headItems;
        }
        //表头信息-Map对象
        $.Object.each(fields, function (key, item) {

            var key = item.key;

            //item = $.Object.extend({}, key$field[key], item);

            var mask = item.display || 0;

            var headItem = {
                'text': item.name,
                'type': item.dataType,
                'key': item.key,
                'width': item.showWidth,
                'visible': !!(mask & display), //转成 boolean--字段按用户类别显示
                'lookupType': item.lookUpType,
                'isCount': item.isCount,
            };

            headItems.push(headItem);

        });

        return headItems;

    }

    function getformFildItems(formFields) {


        if (headItems.length > 0) {
            return headItems;
        }
        // 表头信息-Map对象
        for (var index in formFields) {
            var fields = formFields[index];
            $.Object.each(fields, function (key, item) {

                var key = item.key;

                // item = $.Object.extend({}, key$field[key], item);

                var mask = item.display || 0;

                var headItem = {
                    'text': item.name,
                    'type': item.dataType,
                    'key': item.key,
                    'width': item.showWidth,
                    'visible': !!(mask & display), // 转成 boolean--字段按用户类别显示
                    'lookupType': item.lookUpType,
                    'dataIndex': item.index,
                    'isEntry': index != 0,
                    'isCount': item.isCount,
                    'entryIndex': index
                };

                headItems.push(headItem);

            });
        }
        return headItems.sort(function (a, b) {//数字排序
            return a.dataIndex > b.dataIndex ? 1 : -1;
        });

    }

    // 获取过滤字段信息
    function getFilterItem(formFields) {
        var filterItems = [];
        for (var index in formFields) {
            var fields = formFields[index];
            $.Object.each(fields, function (key, item) {
                //				var mask = item.FDisplay || 0;
                //				if (!!(mask & display)) {
                //					filterItems.push(item)
                //				}
                if (item.isCondition == 1) { //表示过滤字段
                    filterItems.push(item);
                }
            })
        }
        return filterItems;
    }

    return {
        get: get,
        getItems: getItems,
        getformFildItems: getformFildItems,
        getFilterItem: getFilterItem
    };

});