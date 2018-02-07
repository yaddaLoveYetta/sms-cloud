/*
* 主菜单模块
* */

define('Sidebar', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var API = SMS.require("API");
    var Tips = SMS.Tips;
    var user = SMS.Login.get();

    var emitter = MiniQuery.Event.create();

    // 菜单树
    var list;
    // 原始菜单数据Map
    var menus = {};

    var tabs;

    // 标识事件绑定
    var hasBind = false;

    var ul = document.getElementById('ul-sidebar');

    var samples = require("Samples")(ul);

    var homeItem = {
        name: '首页',
        isHome: true,
        id: $.String.random(5),
        //url: 'html/home/index.html',
        //url: 'html/home/index-hrp.html',
        url: 'html/home/520/index.html'
    };

    function loadMenuData(fn) {

        if (list) {
            // 缓存中拿
            fn(list);
        }

        var api = new API('menu/getMenu');

        api.get({
            'parentId': -1,
        });

        api.on({
            'success': function (data, json) {
                // 构造菜单树并缓存起来
                list = toTree(data, 0);
                // 原始菜单数据保存起来
                menus = $.Array.toObject(data, function (item, index) {
                    return [item.id, item];
                });

                fn(list);

            },
            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (错误码: {1})', msg, code);
                SMS.Tips.error(s);
            },
            'error': function () {
                SMS.Tips.error('网络繁忙，请重试!');
            },
        });

    }

    /**
     *
     * @param id 菜单parentId
     */
    function fill() {

        if (list && list.length === 0) {
            return;
        }


        ul.innerHTML = $.String.format(samples["menus"], {
            'item': $.Array.keep(list, function (topItem, topIndex) {
                return $.String.format(samples["item"], {
                    index: topIndex,
                    id: topItem.id,
                    icon: topItem.icon,
                    name: topItem.name,
                    sub: $.String.format(samples["sub"], {
                        subItem: $.Array.keep(topItem.items, function (subItem, subIndex) {
                            return $.String.format(samples["subItem"], {
                                index: topIndex + '-' + subIndex,
                                id: subItem.id,
                                icon: subItem.icon,
                                name: subItem.name,
                            });
                        }).join("")
                    }),
                    line: (topIndex + 1) % 3 === 0 ? $.String.format(samples["line"], {}) : ''
                });
            }).join("")

        });


        bindEvents();

        emitter.fire('renderOver', []);
    }

    function render() {
        loadMenuData(fill);
    }

    /**
     * 绑定事件
     */
    function bindEvents() {

        if (hasBind) {
            return;
        }

        tabs = SMS.Tabs.create({

            container: ul,
            selector: '>li li[data-id]',
            //indexKey: 'data-index',
            current: null,
            event: 'click',  //mouseover
            activedClass: '',//'hover'
            change: function (index, item) {
                //这里的，如果当前项是高亮，再次进入时不会触发
                // console.log(index);
            }
        });

        var tid = null;

        //每次都会触发，不管当前项是否高亮
        tabs.on('event', function (index, item) {

            // 菜单id
            var id = $(item).data('id');

            //console.log(menus[id]);

            if (menus[id] && Boolean(trim(menus[id]['url']))) {
                // 有url的菜单，抛出点击事件
                emitter.fire('menu.click', [menus[id]]);
            }

        });


        $(ul).hover(function () {
            emitter.fire('mouseover');
        }, function () {
            emitter.fire('mouseout');
        });

        hasBind = true;
    }

    /**
     * 线性数据转化为树。
     * @param list
     * @param parentId
     * @returns {Array}
     */
    function toTree(list, parentId) {
        var tree = [];
        var temp;
        for (var i = 0; i < list.length; i++) {
            if (list[i].parentId == parentId) {
                var obj = list[i];
                temp = toTree(list, list[i].id);
                if (temp.length > 0) {
                    obj.items = temp;
                }
                tree.push(obj);
            }
        }
        return tree;
    }

    //找出设置了 autoOpen: true 的项
    function getAutoOpens(data) {

        return [];
        data = data || list;

        var a = $.Array.map(list, function (group, no) {

            var items = group.items;

            return $.Array.grep(items, function (item, index) {
                return item.autoOpen;
            });
        });

        return $.Array.reduceDimension(a);
    }

    function getHomeItem() {
        return homeItem;
    }

    function trim(s) {
        return s.replace(/\n/g, '').replace(/\s{2,}/g, ' ');
    }

    return {
        render: render,
        getHomeItem: getHomeItem,
        getAutoOpens: getAutoOpens,
        on: emitter.on.bind(emitter),
    }

});