/**
 * 菜单组模块
 *
 */
define('ButtonList', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var ButtonList = SMS.require('ButtonList');
    var Iframe = SMS.require('Iframe');
    var API = SMS.require('API');

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
            text: '新增',
            name: 'add',
            icon: 'file.png'
        }, {
            text: '删除',
            name: 'delete'
        }, {
            text: '刷新',
            name: 'refresh'
        }, {
            text: '禁用',
            name: 'disable',
            items: [{
                text: '反禁用',
                name: 'enable'
            }],
        }, {
            text: '显示禁用项',
            name: 'showDisable'
        }
        ]
    };

    function create(c) {

        config = $.Object.extend({}, config, c);

        var bl = new ButtonList(config);

        //总事件，最后触发
        bl.on('click', function (item, index) {
            console.dir(item);
        });

        return bl;

    }

    return {
        create: create,
    };

});
