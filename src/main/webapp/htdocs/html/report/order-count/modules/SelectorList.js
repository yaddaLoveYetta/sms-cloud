/**
 * 填充自定义控件模块
 *
 */
define('SelectorList', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var DataSelector = require('DataSelector');

    var selectors = {};

    var materialContainer = document.getElementById('bd-material');
    var config = {
        targetType: 1, //跳转方案
        classId: 1013,
        hasBreadcrumbs: true,
        container: materialContainer,
        title: '物料',
        defaults: {
            pageSize: 8
        }
    };
    var materialSelector = DataSelector.create(config);
    selectors['material'] = materialSelector;

    var supplierContainer = document.getElementById('bd-supplier');
    var config = {
        targetType: 1, //跳转方案
        classId: 1005,
        hasBreadcrumbs: true,
        container: supplierContainer,
        title: '供应商',
        defaults: {
            pageSize: 8
        }
    };
    var supplierSelector = DataSelector.create(config);
    selectors['supplier'] = supplierSelector;

    //设置 静态变量 用于联动操作
    DataSelector.DataSelectors = selectors;

    return selectors;
});

