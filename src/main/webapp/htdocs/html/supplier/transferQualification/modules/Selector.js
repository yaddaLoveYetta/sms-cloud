/**
 * 填充自定义控件模块
 *
 */
define('Selector', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require("API");
    var DataSelector = require('DataSelector');
    var selectors = {};


    function render() {

        // 医院需要的类别
        var typeConfig = {
            targetType: 1,
            classID: 1016,
            destClassId: 1019,
            fieldKey: 'type',
            hasBreadcrumbs: true,
            container: document.getElementById('type'),
            title: '医院需要的类别',
            defaults: {
                pageSize: 8
            }
        };

        selectors['type'] = DataSelector.create(typeConfig);

        // 合作医院
        var hospitalConfig = {
            targetType: 1, //跳转方案
            classID: 1008,
            destClassId: 1019,
            fieldKey: 'hospital',
            hasBreadcrumbs: true,
            container: document.getElementById('hospital'),
            title: '合作医院',
            defaults: {
                pageSize: 8
            }
        };

        selectors['hospital'] = DataSelector.create(hospitalConfig);

        //设置 静态变量 用于联动操作
        DataSelector.DataSelectors = selectors;
        //改变事件捕获
        DataSelector.on({});
    }

    function set(key, selectorData) {

        var selector = selectors[key];
        selector.setData(selectorData);
    }

    return {
        render: render,
        set: set
    };
});

