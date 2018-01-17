

define('List', function (require, module, exports) {
    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var KERP = require('KERP');
    var API = KERP.require('API');

    var div = document.getElementById('div-list');
    var emitter = MiniQuery.Event.create();


    var samples = $.String.getTemplates(div.innerHTML, [
        {
            name: 'list',
            begin: '<!--',
            end: '-->'
        },
        {
            name: 'tr',
            begin: '#--tr.begin--#',
            end: '#--tr.end--#',
            outer: '{trs}'
        }
    ]);

    var list = [];
    var systemTypes = {};


    function load(fn, config) {

        KERP.Tips.loading('数据加载中...');
        pageNo = config.pageNo || 1;

        //获取当前页数据
        var startIndex = (pageNo - 1) * config.pageSize;
        var endIndex = startIndex + config.pageSize;

        var api = new API('bd/assistitem');

        api.get(config.query);

        api.on({
            'success': function (data, json) {

                getSystemTypes(data.items);

                list = data.items.slice(startIndex, endIndex);

                fn && fn(list, data.items.length);
                KERP.Tips.success('加载成功!', 1000);

            }
        });

        //KERP.API.get('bd/assistitem', config.query).success(function (data, json) {
        
        //    getSystemTypes(data.items);

        //    list = data.items.slice(startIndex, endIndex);

        //    fn && fn(list, data.items.length);
        //    KERP.Tips.success('加载成功!', 1000);

        //});
    }


    function deleteItems() {
        var items = getChecked();
        var api = new API('bd/assistitem');

        var itemsID = '';

        $.Array.each(items, function () {
            var item = this;
            itemsID += item.itemID;
        });

        if (itemsID == '') {
            KERP.Tips.warn('请选择删除项！', 1500);
        }
        else {
            var query = {
                'action': 'delete',
                'itemID': '[' + itemsID + ']'
            };

            api.post(query);

            api.on({
                success: function (data, json) {


                    if (data.items) {
                        KERP.Tips.error(data.items.join(',') + '删除失败！');
                    }
                    else {
                        KERP.Tips.success('删除成功！', 1500);
                    }

                }
            });

            //KERP.API.post('bd/assistitem', query).success(function (data, json) {


            //    if (data.items) {
            //        KERP.Tips.error(data.items.join(',') + '删除失败！');
            //    }
            //    else {
            //        KERP.Tips.success('删除成功！', 1500);
            //    }

            //});
        }

    }


    function render(data, config) {

        list = data;
        div.innerHTML = $.String.format(samples.list, {
            'trs': $.Array.keep(data, function (item, index) {
                return $.String.format(samples.tr, {
                    'index': index,
                    'typeName': item.typeName,
                    'number': item.number,
                    'name': item.name
                });
            }).join('')
        });

        bindEvents(config.multiSelect);
    }


    function getSystemTypes(data) {

        systemTypes = {};

        $.each(data, function () {
            var item = this;
            systemTypes[item.itemID] = item.systemType;
        });

    }

    
    function bindEvents(multiSelect) {

        //是否支持多选
        if (multiSelect) {
            $(div).delegate('[data-check="all"]', 'click', function () {
                var chk = this;
                var checked = chk.checked;


                $('[data-check="item"]').each(function () {
                    var chk = this;
                    chk.checked = checked;
                });
            });

        }
        else {
            $('[data-check="all"]').hide();
            $('[data-check="item"]').each(function () {
                var item = this;
                $(item).bind('click', function () {
                    var item = this;
                    var checked = item.checked;
                    $('[data-check="item"]').each(function () {
                        var item = this;
                        item.checked = false;
                    });
                    item.checked = checked;
                });
            });
        }


        //绑定编辑
        $(div).delegate('[data-role="edit"]', 'click', function () {
            var self = this;
            var index = $(self.parentNode.parentNode).data('index');

            KERP.use('Dialog', function (Dialog) {
                var dialog = new Dialog({
                    id: 'edit_dialog',
                    title: '编辑',
                    url: './dialog/support-data/edit/index.html',
                    width: 300,
                    height: 250,

                    data: {
                        'itemID': list[index].itemID
                    },

                    onremove: function () {
                        emitter.fire('dialog.remove');
                    }
                });

                dialog.showModal();
            });
        })


    }


    //返回所有被选中的ItemID的数组
    function getChecked() {
        var checked = $('input[data-check="item"]:checked');
        var checkedItems = [];
        checked.each(function () {
            var chk = this;
            var item = $(chk.parentNode.parentNode).data('index');
            checkedItems.push(list[item]);
        });

        return checkedItems;
    }


    //检查当前被选中的项中是否有系统内置项
    //data: object
    //items: array
    function checkPre(items) {
        var tip = '请勿修改以下内置资料：';
        var error = false;

        $.each(items, (function () {
            var item = this;
            if (systemTypes[item]) {
                tip += '[' + item + '] ';
                error = true;
            }
        })
        );

        if (error) {
            KERP.Tips.error(tip, 1000);
        }

        return error;
    }

    return {
        load: load,
        render: render,
        checkPre: checkPre,
        on: emitter.on.bind(emitter),
        deleteItems: deleteItems,
        getChecked: getChecked
    }

});