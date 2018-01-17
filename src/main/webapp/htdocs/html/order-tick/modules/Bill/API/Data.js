/**
 * Created by yadda on 2017/5/12.
 * 单据数据取数模块
 */
define('Bill/API/Data', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = SMS.require('API');

    var fields = null;

    /**
     * 获取表体信息，即行的信息
     */
    function get(config, fn) {

        var api = new API('template/getItemById');

        var params = {
            'classId': config.classId,
            'id': config.id,
        };

        api.post(params);

        api.on({
            'success': function (data, json) {
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

    function getItems(list, fields, primaryKey) {

        if (!list) {
            return;
        }
        // 表体信息
        return $.Array.keep(list, function (item, index) { // 行

            return {

                'disabled': item['status'], // 是否禁用
                'data': item,
                'primaryValue': item[primaryKey], // 主键对应的值

                'items': $.Array.keep(fields, function (field, no) { // 列

                    var key = field.key;
                    var value
                    // 后台的一种规定，很蛋疼
                    if (field.lookupType > 0 && field.lookupType < 3) { // lookupType
                        // 不为 0时，说明是引用类型
                        key = key + '_DspName';
                        // 此时要显示的字段为 key + '_DspName'
                    }
                    if (field.isEntry) { // 子表数据
                        var entryIndex = field.entryIndex;
                        var entryValues = item.entry[entryIndex];
                        value = $.Array.keep(entryValues, function (field, no) {
                            return field[key];
                        })
                    } else {
                        value = item[key];
                    }

                    return {
                        'key': key,
                        'value': value,
                    };

                }),
            };

        });

    }

    /**
     * 获取单据数据(一张单据)
     * @param data
     * @param templateData
     * @param primaryKey
     * @returns {*|Array|{parent, leaf}}
     */
    function getValueItems(data, template, templateData) {

        if (!data) {
            return;
        }

        var headTemplate = template[0];
        var billData = data;

        var entryData = {};

        var primaryKey = templateData.formClass.primaryKey;

        if (data.entry) {
            entryData = data.entry;
            delete  billData.entry;
        }

        /**

         // 过滤出表头要显示的字段
         var headData = $.Object.grep(billData, function (key, value) {
            if (key in headTemplate) {
                return headTemplate[key].visible;
            }
            return false;
        });

         // 过滤出子表要显示的字段
         for (var page in entryData) {

            entryData[page] = $.Array.keep(entryData[page], function (rowData, rowIndex) {

                var row = $.Object.grep(rowData, function (key, value) {
                    if (key in template[page]) {
                        return template[page][key].visible;
                    }
                    return false;
                });

                return row;
            });
        }

         */
        return {

            headData: billData,
            entryData: entryData,

        };
    }

    return {
        get: get,
        getItems: getItems,
        getValueItems: getValueItems,
    };

});