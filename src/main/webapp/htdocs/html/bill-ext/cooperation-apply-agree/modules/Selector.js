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
        var orgConfig = {
            targetType: 1,
            classId: 1005,
            destClassId: 3001,
            fieldKey:'id',
            hasBreadcrumbs: true,
            container: document.getElementById('supplier'),
            title: 'HRP供应商',
            dataFieldKey: {
                // F7控件关联资料的id 值
                'id': 'id',
                // F7控件无焦点时显示的字段key(引用基础资料的模板key)
                'name': 'name',
                // F7有焦点时显示的字段key(引用基础资料的模板key)
                'number': 'name'
            },
            defaults: {
                pageSize: 8
            }
        };

        selectors['id'] = DataSelector.create(orgConfig);


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

