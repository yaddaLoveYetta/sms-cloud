/**
 * 按钮模块
 *
 */
define('ButtonList', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var ButtonList = SMS.require('ButtonList');

    var emitter = MiniQuery.Event.create();

    var config = {
        container: '#div-button-list',
        fields: {
            text: 'text',
            child: 'items',
            callback: 'click',
            route: 'name',
        },

        autoClose: true,

        items: [{
            text: '保存',
            name: 'optSave',

        }, {
            text: '刷新',
            name: 'optRefresh',

        }]
    };

    function create(c) {

        config = $.Object.extend({}, config, c);

        var bl = new ButtonList(config);

        // 总事件，最后触发
        bl.on('click', function (item, index) {
            console.dir(item);
        });

        return bl;

    }

    return {
        create: create,
        on: emitter.on.bind(emitter)
    };

});
