/**
 *
 * 列表数据
 */
define('List/API/Body', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = SMS.require('API');

    var fields = null;

    /**
     * 获取表体信息，即行的信息
     */
    function get(config, fn) {

        /*        var conditions = new Array();

                for (var item in config.conditions) {

                    if (config.conditions[item] === '') {
                        continue;
                    }
                    conditions.push(config.conditions[item]);
                }*/

        var pageNo = config.pageNo;
        var api = new API('template/getItems');

        var params = {
            'classId': config.classId,
            'pageNo': pageNo,
            'pageSize': config.pageSize,
            'condition': config.conditions.conditionList.length > 0 ? config.conditions : '',
            'sort': config.sort || ''
        };

        api.post(params);

        api.on({
            'success': function (data, json) {

                //console.log('List/API/Body getBody finished');
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

    return {
        get: get,
        getItems: getItems,
    };

});