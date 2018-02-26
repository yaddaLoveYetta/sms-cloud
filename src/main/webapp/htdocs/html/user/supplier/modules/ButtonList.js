/**
 * 菜单组模块
 *
 */
define('ButtonList', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var emitter = MiniQuery.Event.create();

    var ButtonList = SMS.require('ButtonList');
    var FormAction = require('FormAction');

    function render(classId) {

        FormAction.create({
            'classId': classId
        }, function (config) {

           // ButtonList.config(config);
            ButtonList = new ButtonList(__default__);

            // 总事件，最后触发
            ButtonList.on('click', function (item, index) {
                console.dir(item);
            });
        });
    }

    return {
        'render': render,
        on: ButtonList.on
    }
});