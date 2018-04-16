/**
 * @Title: 数字控件
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/16 11:31
 */

define('NumberFieldList', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var numberFields = {};

    function render() {

        /*        SMS.use('NumberField', function (NumberField) {

                    var numberField = new NumberField('#', config);
                    // 控件对象缓存下来，设置获取值时使用
                    numberFields[key] = numberField;

                });*/

    }

    function set(field, key, value) {
        var numberField = numberFields[key];

        if (!numberField) {
            return;
        }

        numberField.set(value || 0);
    }

    return {
        render: render,
        set: set
    }
});

