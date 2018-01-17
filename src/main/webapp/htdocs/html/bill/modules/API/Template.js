/**
 * Created by yadda on 2017/5/12.
 * 单据模板信息
 */
define('API/Template', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var user = SMS.Login.get();
    var showType = 0; // 0:1:2-查看/新增/编辑

    var cache = null;
    var templateAll = {};
/*    var display;
    // 字段显示权限-后端FDisPlay定义 1：平台用户显示，2：供应商用户显示：3：都显示
    if (user.type == 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
        // 平台用户
        display = 1; // 00 -- 01 --10 --11
    } else if (user.type == 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
        // 供应商用户
        display = 2; // 10
    } else {
        display = 0;
    }*/

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

        showType = config.type || 0;

        var api = new API('template/getFormTemplate');

        api.get({
            classId: config.classId,
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

    function getTemplate(templateDate) {


        if (!$.Object.isEmpty(templateAll)) {
            return templateAll;
        }

        var display = 0;

        if (showType == 2) {
            // 编辑
            //lockMaskDisplay  字段显示权限-后端lock定义 2 编辑时平台用户锁定，8编辑时候供应商用户锁定
            if (user.type === 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                // 平台用户
                display = 4;
            } else if (user.type === 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                //供应商用户
                display = 8;
            }
        } else if (showType == 1) {
            // 新增
            //lockMaskDisplay  字段显示权限-后端lock定义 1 编辑时平台用户锁定，4编辑时候供应商用户锁定
            if (user.type === 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                // 平台用户
                display = 16;
            } else if (user.type === 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                //供应商用户
                display = 32;
            }
        } else if (showType == 0) {
            // 查看
            if (user.type === 'QpXq24FxxE6c3lvHMPyYCxACEAI=') {
                // 平台用户
                display = 1;
            } else if (user.type === 'B3sMo22ZLkWApjO/oEeDOxACEAI=') {
                //供应商用户
                display = 2;
            }
        }

        var templateAll = {};

        $.Object.each(templateDate, function (pageIndex, pageData) {

            var temp = [];

            $.Object.each(pageData, function (key, item) {

                var mask = item.display || 0;

                var t = item;

                t['visible'] = !!(mask & display);// 转成 boolean--字段按用户类别显示

                temp.push(t);

            });

            templateAll[pageIndex] = temp;

        });

        return templateAll;
    }

    function getformFildItems(formFields) {

        if (templateAll.length > 0) {
            return templateAll;
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

                templateAll.push(headItem);

            });
        }
        return templateAll.sort(function (a, b) {//数字排序
            return a.dataIndex > b.dataIndex ? 1 : -1;
        });

    }

    return {
        get: get,
        getTemplate: getTemplate,
    };

});