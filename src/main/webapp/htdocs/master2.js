//主控台控制器。
//注意：所有模块均对控制器可见。

;(function () {

    //alert('hello word!');


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');


    var Iframe = SMS.require('Iframe');
    var Iframes = require('Iframes');
    var PageTabs = require('PageTabs');
    var PageList = require('PageList');
    var Tips = require('Tips');

    var UserInfos = require('UserInfos');
    var Sidebar = require('Sidebar');
    var NavBar = require('NavBar');


    //重写Tips
    $.Object.overwrite(SMS.require('Tips'), Tips);

    Sidebar.on({
        'menu.click': function (item) {
            console.log(item);

            PageTabs.add(item); //安静模式，不触发事件
            PageList.add(item); //安静模式，不触发事件
            Iframes.add(item);  //会触发 active 事件

        },
        'renderOver': function () {

            //要自动打开的页面，请给菜单项设置 autoOpen: true 即可
            var home = Sidebar.getHomeItem();
            var items = Sidebar.getAutoOpens();

            items = [home].concat(items);

            $.Array.each(items, function (item, index) {
                // 打开需要自动打开的页面
                PageTabs.add(item);
                PageList.add(item);
                Iframes.add(item);

            });
        }
    });

    //iframe 页面
    Iframes.on({
        'active': function (item) {
/*            Sidebar.active(item);
            Tips.active(item);
            Iframe.fire(item.id, 'active', [item]);*/
            Tips.active(item);
            Iframe.fire(item.id, 'active', [item]);
        },

        'non-active': function (item) {
            Iframe.fire(item.id, 'non-active', [item]);
        },

        'load': function (item) {

        },
    });

    Iframe.on('open', function (group, index, data) {

        // 已有菜单打开方式
        MenuData.getItem(group, index, function (item) {

            if (!item) {
                return;
            }

            var query = data.query;
            if (query) {
                //item.url = $.Url.addQueryString(item.url, query);
                //不能直接修改原对象的 url，否则可能会影响到原来的 url
                item = $.Object.extend({}, item, {
                    url: $.Url.addQueryString(item.url, query)
                });
            }

            PageTabs.add(item); //安静模式，不触发事件
            PageList.add(item); //安静模式，不触发事件
            Iframes.add(item, true); //强制刷新
        });

        //重载以对象传入的方式
        if (typeof group == 'object') {
            //alert('此处需要重置打开方式.');
            open(group);
            return;
        }

        function open(item) {
            if (!item.id) {
                item.id = $.String.random();
            }

            var query = item.query;
            if (query) {
                //item.url = $.Url.addQueryString(item.url, query);
                //不能直接修改原对象的 url，否则可能会影响到原来的 url
                item = $.Object.extend({}, item, {
                    url: $.Url.addQueryString(item.url, query)
                });
            }

            //安静模式，不触发事件
            PageTabs.add(item);
            //安静模式，不触发事件
            PageList.add(item);
            //安静模式，不触发事件
            Iframes.add(item);

        }
    });

    // 加载顶级菜单
    Sidebar.render();
    // 导航栏按钮
    NavBar.render();
    // 用户信息
    UserInfos.render();

    PageTabs.render();
    PageList.render();
    Iframes.render();


})();