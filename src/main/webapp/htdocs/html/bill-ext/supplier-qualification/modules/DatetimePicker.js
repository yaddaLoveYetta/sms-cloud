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

            //营业期限开始
            var validity_period_begin = new DateTimePicker('#validity_period_begin', {
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                timepicker: false,
                startView: 'month',
                minView: 2
            });

            // 控件对象缓存下来，设置获取值时使用
            dateTimePickers['validity_period_begin'] = validity_period_begin;

            //营业期限结束
            var validity_period_end = new DateTimePicker('#validity_period_end', {
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                startView: 'month',
                minView: 'month'
            });

            dateTimePickers['validity_period_end'] = validity_period_end;

        });

    }

    function set() {

    }

    return {
        render: render,
        set: set
    };

});

