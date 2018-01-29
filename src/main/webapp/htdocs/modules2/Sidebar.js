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

    var tabs = null;
    var menus = {};
    var ul = document.getElementById('ul-sidebar');

    var samples = require("Samples")(ul);


    function loadMenuData(parentId, fn) {

        if (menus[parentId]) {
            // 缓存中拿
            return menus[id];
        }

        var api = new API('menu/getMenu');

        api.get({
            'parentId': parentId,
        });

        api.on({
            'success': function (data, json) {
                //成功
                var list = data;
                // 缓存起来
                menus[parentId] = list;

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
     * @param menuList  菜单列表
     */
    function fill(parentId, menuList) {

        if (menuList && menuList.length === 0) {
            return;
        }
        // 是不是一级菜单
        var isTop = (parentId === 0);

        if (isTop) {
            ul.innerHTML = $.String.format(samples["menus"], {
                'top': $.Array.keep(menuList, function (menuItem, index) {
                    return $.String.format(samples["menu.top"], {
                        index: index || 0,
                        id: menuItem.id,
                        icon: menuItem.icon,
                        name: menuItem.name
                    });
                }).join("")
            });
        } else {
            $("div[data-id=" + parentId + "]")[0].innerHTML = $.String.format(samples["more.begin"], {
                'top': $.Array.keep(menuList, function (menuItem, index) {
                    return $.String.format(samples["menu.top"], {
                        index: index,
                        id: menuItem.id,
                        icon: menuItem.icon,
                        name: menuItem.name
                    });
                }).join("")
            });
        }

        bindEvents();
    }

    function render(parentId) {
        loadMenuData(parentId, fill);
    }

    /**
     * 绑定事件
     */
    function bindEvents() {
        tabs = SMS.Tabs.create({

            container: ul,
            selector: 'li',
            indexKey: 'data-index',
            current: null,
            event: 'mouseover',
            activedClass: 'hover',
            change: function (index, item) {
                //这里的，如果当前项是高亮，再次进入时不会触发
                console.log(index);
            }
        });

        var tid = null;


        //每次都会触发，不管当前项是否高亮
        tabs.on('event', function (index, item) {

            clearTimeout(tid);

            tid = setTimeout(function () {

                var item = list[index];

                emitter.fire('item.mouseover', [{
                    index: index,
                    data: item.items,
                    reversed: index > 0
                }]);

            }, 200);
        });


        $(ul).hover(function () {
            emitter.fire('mouseover');
        }, function () {
            emitter.fire('mouseout');
        });

        $(hiden).on('click', function () {
            $(div).toggleClass('side-bar-hiden');
            emitter.fire('hiden');
        })
    }


    return {
        render: render,
        on: emitter.on.bind(emitter),
    }

});