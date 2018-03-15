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
    var MessageBox = SMS.require('MessageBox');

    var UserInfos = require('UserInfos');
    var Sidebar = require('Sidebar');
    var NavBar = require('NavBar');

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

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

    //页签标签
    PageTabs.on({
        'active': function (item) {
            Iframes.active(item);   //会触发 active 事件
            PageList.active(item);  //安静模式，不触发事件
            //Sidebar.active(item);     //安静模式，不触发事件
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
        }
    });

    //页签列表
    PageList.on({
        'active': function (item) {
            Iframes.active(item);   //会触发 active 事件
            PageTabs.active(item);  //安静模式，不触发事件
            //Menus.active(item);     //安静模式，不触发事件
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

    UserInfos.on({
        'edit-profile': function () {
            // 修改个人信息
            var user = SMS.Login.get();
            console.log(user);

            var url, classId;
            var roleType = user.roles && user.roles[0] && user.roles[0]['type'];

            if (roleType === 1) {
                // 系统管理员-暂时没有资料维护
                MessageBox.show('系统管理员无可维护信息！');
                return;
            } else if (roleType === 2) {
                //  医院角色类别
                url = './html/bill/index.html?classId=1012';
                classId = 1012;
            } else if (roleType === 3) {
                // 供应商角色类别
                url = './html/bill/index.html?classId=1011'
                classId = 1011;
            }
            Iframe.open({
                id: 'editProfile-' + classId,
                name: '信息维护',
                url: url,
                query: {
                    'roleType': roleType,
                    'user': user.id,
                    'operate': 0   // 0：查看 1：新增 2：修改
                }
            });

        },
        'before-logout': function () {
            // 注销前置事件
            return Iframe.fire('before-logout');
        },
        'cancel-logout': function () {
            // 取消注销事件
            Iframe.fire('cancel-logout', []);
        }
    });

    //iframe 页面
    Iframes.on({
        'active': function (item) {
            Tips.active(item);
            Iframe.fire(item.id, 'active', [item]);
        },
        'non-active': function (item) {
            Iframe.fire(item.id, 'non-active', [item]);
        },
        'load': function (item) {

        }

    });

    Iframe.on('open', function (group, index, data) {

        // 已有菜单打开方式
        /*        MenuData.getItem(group, index, function (item) {

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
                });*/

        //重载以对象传入的方式
        if (typeof group == 'object') {
            //alert('此处需要重置打开方式.');
            open(group);
            return;
        }
        // 内部使用
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
            // 单据新增-保存成功后会抛出此事件
            var name = PageTabs.getTabName(sn);
            text = name.replace('新增', '修改');
            PageTabs.changeTitle(sn, text);
            PageList.changeTitle(sn, text);
        },
        'editSuccess': function (sn, data) {

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