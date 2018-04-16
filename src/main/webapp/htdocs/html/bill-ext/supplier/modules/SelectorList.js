/**
 * 填充自定义控件模块
 *
 */
define('SelectorList', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require("API");
    var DataSelector = require('DataSelector');
    var selectors = {};


    function render() {
        //设置 静态变量 用于联动操作
        DataSelector.DataSelectors = selectors;
        //改变事件捕获
        DataSelector.on({});
    }

    function set(field, key, selectorData) {

        var selector = selectors[key];
        selector.setData(selectorData);
    }

    return {
        render: render,
        set: set
    };
});

