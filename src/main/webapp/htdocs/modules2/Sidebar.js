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
    // 缓存菜单结构数据
    var menusTree = {};
    // 缓存菜单数据
    var menus = {};
    // 标识事件绑定
    var hasBind = false;

    var ul = document.getElementById('ul-sidebar');

    var samples = require("Samples")(ul);


    function loadMenuData(parentId, fn) {

        if (menusTree[parentId]) {
            // 缓存中拿
            fn(parentId, menusTree[parentId]);
        }

        var api = new API('menu/getMenu');

        api.get({
            'parentId': parentId,
        });

        api.on({
            'success': function (data, json) {
                //成功
                var list = data;

                // 菜单结构缓存起来
                menusTree[parentId] = list;

                var menuItems = $.Array.toObject(list, function (item, index) {
                    return [item.id, item];
                });
                // 不要length
                delete menuItems.length;
                // 菜单项缓存起来
                $.Object.extend(menus, menuItems);

                fn(parentId, list);

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
            ul.innerHTML = $.String.format(samples["top"], {
                'item': $.Array.keep(menuList, function (menuItem, index) {
                    return $.String.format(samples["top.item"], {
                        index: index || 0,
                        id: menuItem.id,
                        icon: menuItem.icon,
                        name: menuItem.name
                    });
                }).join("")
            });
        } else {

            $("div[data-id=" + parentId + "]")[0].innerHTML = $.String.format(samples["more"], {
                'top': $.Array.keep(menuList, function (menuItem, index) {
                    return $.String.format(samples["top.item"], {
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

        if (hasBind) {
            return;
        }
        /*        if (tabs) {
                    tabs.destroy();
                }*/

        tabs = SMS.Tabs.create({

            container: ul,
            selector: 'li',
            //indexKey: 'data-index',
            current: null,
            event: 'click',  //mouseover
            activedClass: 'hover',
            change: function (index, item) {
                //这里的，如果当前项是高亮，再次进入时不会触发
                //console.log(index);
            }
        });

        var tid = null;

        //每次都会触发，不管当前项是否高亮
        tabs.on('event', function (index, item) {

            // 菜单id
            var id = $(item).data('id');

            var $div = $(item).find('> div');

            if (menus[id] && Boolean(trim(menus[id]['url']))) {
                // 有url的菜单，抛出点击事件
                emitter.fire('menu.click', [menus[id]]);
            }

            if (Boolean(trim($div.html()))) {
                // 已经加载了子菜单--切换显示关闭
                $(item).find("ul:first").slideToggle();
                return;
            }

            render(id);
        });


        $(ul).hover(function () {
            emitter.fire('mouseover');
        }, function () {
            emitter.fire('mouseout');
        });

        /*        $(hiden).on('click', function () {
                    $(div).toggleClass('side-bar-hiden');
                    emitter.fire('hiden');
                })*/
        hasBind = true;
    }

    function trim(s) {
        return s.replace(/\n/g, '').replace(/\s{2,}/g, ' ');
    }

    return {
        render: render,
        on: emitter.on.bind(emitter),
    }

});