//控制器。
//注意：所有模块均对控制器可见。
;(function () {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var Iframe = SMS.require('Iframe');

    var MenuData = require('MenuData');
    var Sidebar = require('Sidebar');
    var Menus = require('Menus');
    var PageTabs = require('PageTabs');
    var PageList = require('PageList');
    var Iframes = require('Iframes');
    var Tips = require('Tips');
    var UserInfos = require('UserInfos');

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }
    //重写
    $.Object.overwrite(SMS.require('Tips'), Tips);


    //侧边栏
    Sidebar.on({
        //鼠标移进 Sidebar.item 时
        'item.mouseover': function (config) {
            Menus.render(config);
        },
        //鼠标移进 Sidebar 时
        'mouseover': function () {
            Menus.cancelHide(); //取消隐藏(即保持显示) Menus
        },
        //鼠标移出 Sidebar 时
        'mouseout': function () {
            Menus.hideAfter(200); //延迟隐藏 Menus
            Sidebar.activeAfter(200);
        },
        'hiden':function () {
        	// 简单处理
            $('.content').toggleClass('content-hiden');
            $('.content .top-fixed').toggleClass('toggle-width');
            $('.side-bar .menus-more').toggleClass('toggle-menus-more');
        }
    });


    //弹出层菜单
    Menus.on({
        //鼠标移进 Menus 时
        'mouseover': function () {
            Menus.cancelHide(); //取消隐藏(即保持显示) Menus
            Sidebar.cancelActive();
        },
        //鼠标移出 Menus 时
        'mouseout': function () {
            Menus.hideAfter(200); //隐藏 Menus
            Sidebar.active();

        },
        //单击 Menus.item 时
        'item.click': function (item) {
            PageTabs.add(item); //安静模式，不触发事件
            PageList.add(item); //安静模式，不触发事件
            Iframes.add(item);  //会触发 active 事件
        }
    });


    //页签标签
    PageTabs.on({
        'active': function (item) {
            Iframes.active(item);   //会触发 active 事件
            PageList.active(item);  //安静模式，不触发事件
            Menus.active(item);     //安静模式，不触发事件
        },
        'remove': function (item) {
            Iframes.remove(item);  //会触发 remove 事件
            PageList.remove(item); //安静模式，不触发 remove 事件，但会触发 active 事件
        },
        'dragdrop': function (srcIndex, destIndex) {
            PageList.dragdrop(srcIndex, destIndex);
        },
        'before-close': function (item) {
            return Iframe.fire('before-close', item);
        },
        'cancel-close': function (item) {
            return Iframe.fire('cancel-close', item);
        },
        'close': function (item) {
            return Iframe.fire('close', item);
        },

    });

    //页签列表
    PageList.on({
        'active': function (item) {
            Iframes.active(item);   //会触发 active 事件
            PageTabs.active(item);  //安静模式，不触发事件
            Menus.active(item);     //安静模式，不触发事件

        },
        'remove': function (item) { //如果移除的是当前的激活项，则会触发 active 事件
            Iframes.remove(item);
            PageTabs.remove(item); //安静模式，不触发事件

        },
        'clear': function () {
            Iframes.clear();
            PageTabs.clear();
        },
        'dragdrop': function (srcIndex, destIndex) {
            PageTabs.dragdrop(srcIndex, destIndex);
        },
        'before-close': function (item) {
            return Iframe.fire('before-close', item);
        },
        'cancel-close': function (item) {
            return Iframe.fire('cancel-close', item);
        },
        'close': function (item) {
            return Iframe.fire('close', item);
        },
    });


    //iframe 页面
    Iframes.on({
        'active': function (item) {
            Sidebar.active(item);
            Tips.active(item);
            Iframe.fire(item.id, 'active', [item]);

        },

        'non-active': function (item) {
            Iframe.fire(item.id, 'non-active', [item]);
        },

        'load': function (item) {

        },
    });


    //加载菜单数据
    MenuData.load(function (data) {

        //debugger;

        Sidebar.render(data);

        //要自动打开的页面，请给菜单项设置 autoOpen: true 即可
        var home = MenuData.getHomeItem();
        var items = MenuData.getAutoOpens(data);

        items = [home].concat(items);

        $.Array.each(items, function (item, index) {

            PageTabs.add(item);
            PageList.add(item);
            Iframes.add(item);

        });


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

    Iframe.on({

        'addSuccess': function (sn, text) {
            // 基础资料新增-保存成功后会抛出此事件
            var name = PageTabs.getTabName(sn);
            text = name.replace('新增', '修改');
            PageTabs.changeTitle(sn, text);
            PageList.changeTitle(sn, text);
        },
        'editSuccess': function (sn, data) {

        },
        'addNew':function (sn, data) {
            // 基础资料新增页面点击新增会抛出此事件
            var name = PageTabs.getTabName(sn);
            text = name.replace('修改', '新增');
            PageTabs.changeTitle(sn, text);
            PageList.changeTitle(sn, text);
        }
    });


    PageTabs.render();
    PageList.render();
    Iframes.render();
    UserInfos.render();


})();