


/**
* 菜单组模块
* 
*/
define('ButtonList', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var ButtonList = SMS.require('ButtonList');

    var bl = new ButtonList({
        container: '#div-button-list',
        fields: {
            text: 'name',
            child: 'buttons',
            callback: 'click',
        },

        autoClose: true,

        buttons: [
            {
                name: '一键检测',
            },
            {
                name: '打印物流单',
                click: function (item, index) {
                    console.dir(item);
                },
                buttons: [
                    {
                        name: '打印当前页',
                        name: '打印当前条件所有页'
                    },
                ],
            },
            {
                name: '打印配货单',
                click: function (item, index) {
                    console.dir(item);
                },
                buttons: [
                    {
                        name: '打印当前页',
                        name: '打印当前条件所有页'
                    },
                ],
            },
            {
                name: '打印发货单',
                click: function (item, index) {
                    console.dir(item);
                },
                buttons: [
                    {
                        name: '打印当前页',
                        name: '打印当前条件所有页'
                    },
                ],
            },
            {
                name: '填写物流单号',
                click: function () {
                    SMS.use('Dialog', function (Dialog) {

                        var dialog = new Dialog({
                            id: 'test-dialog',
                            title: '填写物流单号',
                            url: './html/print/fill-logistics-number.html',
                            width: 900,
                            height: 550,

                            //需要传递的数据
                            data: {
                                a: 1111,
                                b: 2222,
                                c: {
                                    name: 'micty',
                                    value: 'test',
                                },
                            },
                        });

                        dialog.showModal();
                    });
                }
            },
            {
                name: '批量修改',
                click: function () {
                    SMS.use('Dialog', function (Dialog) {

                        var dialog = new Dialog({
                            id: 'test-dialog',
                            title: '批量修改',
                            url: './html/print/batch-update.html',
                            width: 900,
                            height: 550,

                            //需要传递的数据
                            data: {
                                a: 1111,
                                b: 2222,
                                c: {
                                    name: 'micty',
                                    value: 'test',
                                },
                            },
                        });

                        dialog.showModal();
                    });
                }
            },
            {
                name: '更多',
                cssClass: 'btn-more',
                buttons: [
                    { name: '更多A', },
                    { name: '更多B', },
                    { name: '更多C', },
                ],
            },
        ],
    });


    bl.on('click', function (item, index) {

        console.dir(item);
    });


    return bl;

});