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

    var orderContainer = document.getElementById('bd-order');
    var config = {
        targetType: 1, //跳转方案
        classID: 2019,
        hasBreadcrumbs: true,
        container: orderContainer,
        title: '订单',
        defaults: {
            pageSize: 8
        }
    };
    var orderSelector = DataSelector.create(config);
    selectors['order'] = orderSelector;

    var supplierContainer = document.getElementById('bd-supplier');
    var config = {
        targetType: 1, //跳转方案
        classID: 1005,
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

