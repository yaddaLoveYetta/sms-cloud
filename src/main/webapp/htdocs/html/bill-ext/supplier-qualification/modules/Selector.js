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

        // 归属医院
        var qualification_typeConfig = {
            targetType: 1,
            classId: 1018,
            destClassId: 1019,
            fieldKey: 'qualification_type',
            hasBreadcrumbs: true,
            container: document.getElementById('qualification_type'),
            title: '证件类别',
            defaults: {
                pageSize: 8
            }
        };

        selectors['qualification_type'] = DataSelector.create(qualification_typeConfig);

        //设置 静态变量 用于联动操作
        DataSelector.DataSelectors = selectors;
        //改变事件捕获
        DataSelector.on({});
    }

    function set(field, key, selectorData) {
        var selector = selectors[key];
        selector.setData(selectorData);
    }

    function get(key) {
        return selectors[key];
    }

    return {
        render: render,
        set: set,
        get: get
    };
});

