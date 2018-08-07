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
    // 字段显示权限-后端disPlay定义:系统用户显示||医院用户显示||供应商用户显示
    var displayMask;
    // 字段过滤性控制-后端is_condition定义:1：系统角色过滤字段||2：医院角色过滤字段|:4：供应商角色过滤字段
    var conditionMask;

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    var roleType = user.roles && user.roles[0] && user.roles[0]['type'];

    if (roleType === 1) {
        // 平台用户
        displayMask = 1;
        conditionMask = 1;
    } else if (roleType === 2) {
        // 医院用户
        displayMask = 64;
        conditionMask = 2;
    } else if (roleType === 3) {
        // 供应商用户
        displayMask = 8;
        conditionMask = 4;
    } else {
        displayMask = 0;
        conditionMask = 0
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
            console.log('getHead from server finished');
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
        /*
         //表头信息-数组
         headItems = headItems || $.Array.keep(fields, function(item, index) {

         var key = item.fieldKey;

         item = $.Object.extend({}, key$field[key], item);

         var mask = item.visible || 0;

         return {
         'text' : item.caption,
         'type' : item.listStyle,
         'key' : key,
         'width' : item.showWidth,
         'visible' : !!(mask & 2), //转成 boolean
         'lookupType' : item.lookupType,
         };
         });
         */

        //fields = $.extend({}, fields["0"], fields["1"]); // 将主表及第一个子表模板取出
        fields = fields["0"];
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
                //'type': item.dataType,
                'type': item.ctrlType,
                'key': item.key,
                'width': item.showWidth,
                'visible': !!(mask & displayMask), //转成 boolean--字段按用户类别显示
                'lookupType': item.lookUpType,
                'isCount': item.isCount,
                'entryIndex': item.page,
                'isEntry': item.page === 1
            };

            headItems.push(headItem);

        });

        return headItems;

    }

    function getFormFieldItems(formFields) {


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
                    'visible': !!(mask & displayMask), // 转成 boolean--字段按用户类别显示
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

                var fieldDisplayMask = item.display || 0; //conditionMask
                var fieldConditionMask = item.isCondition || 0;

                if (!!(fieldConditionMask & conditionMask) && !!(fieldDisplayMask & displayMask)) {
                    //表示过滤字段
                    filterItems.push(item);
                }
            })
        }
        return filterItems;
    }

    return {
        get: get,
        getItems: getItems,
        getFormFieldItems: getFormFieldItems,
        getFilterItem: getFilterItem
    };

});