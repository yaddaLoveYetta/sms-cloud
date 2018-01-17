

; (function () {
    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var KERP = require('KERP');
    var Iframe = KERP.require('Iframe');

    var list = require('List');
    var pager = require('Pager');
    var operation = require('Operation');
    var nav = require('Nav');


    //默认配置
    var defaults = {
        pageSize: 30,
        typeID: '',
        pageNo: 1,
        hasBreadcrumbs: true,
        multiSelect: true,
    };




    //处理以弹窗形式传来的数据
    var dialog = Iframe.getDialog();
    if (dialog) {
        var data = dialog.getData();
        defaults = $.Object.extend(defaults, data);

        dialog.on({
            close: function () {
                //dialog.setData({ responseData: list.getChecked() });
                var item = list.getChecked();
                if (item.length > 0) {
                    dialog.setData([{
                        'ID': item[0].itemID,
                        'number': item[0].number,
                        'name': item[0].name
                    }]);
                }
            }
        });
    }


    var query = {
        'action': 'listByType',
        'typeID': defaults.typeID || ''
    };

    //处理面包屑导航的加载
    if (defaults.hasBreadcrumbs) {
        nav.load();

        nav.on({
            'nav.change': function (data) {
                var list = data.list;
                query.typeID = list[list.length - 1].typeID;
                render();
            }
        });
    }

    render();


    operation.on({

        'dialog.remove': function () {
            render();
        },
        'delete.click': function () {
            list.deleteItems();
            render();
        },
        'refresh.click': function () {
            render();
        }

    });

    list.on({
        'dialog.remove': function () {
            render();
        }
    });


    function render() {

        list.load(function (data, total) {

            pager.render({
                size: defaults.pageSize,
                total: total,

                change: function (pageNo) {
                    list.load(function (data) {

                        list.render(data, {
                            'multiSelect': defaults.multiSelect
                        });

                    }, {
                        'pageNo': pageNo,
                        'pageSize': defaults.pageSize,
                        'query': query

                    })
                }
            });

            list.render(data, {
                'multiSelect': defaults.multiSelect
            });

        }, {
            'pageNo': defaults.pageNo,
            'pageSize': defaults.pageSize,
            'query': query
        });


        operation.render();

    }

})();