/**
 * 时间选择器
 */
define('DatetimePicker', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    // 日期控件集合
    var dateTimePickers = {};

    function render() {

        SMS.use('DateTimePicker', function (DateTimePicker) {

        });

    }

    function set() {

    }

    return {
        render: render,
        set: set
    };

});

