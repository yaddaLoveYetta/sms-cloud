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
            icon: 'icon'
        },

        autoClose: true,

        items: [{
            text: '保存',
            name: 'optSave',
            icon:'../../css/main/img/save.png',
        }]
    };

    function create() {

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
