//主控台控制器。
//注意：所有模块均对控制器可见。

;(function () {

    //alert('hello word!');


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');


    /*    var Iframe = SMS.require('Iframe');
        var Iframes = require('Iframes');
        var PageTabs = require('PageTabs');
        var PageList = require('PageList');
        var Tips = require('Tips');*/
    var UserInfos = require('UserInfos');
    var Sidebar = require('Sidebar');
    var NavBar = require('NavBar');

    Sidebar.on({
        'menu.click': function (item) {
            console.log(item);

            PageTabs.add(item); //安静模式，不触发事件
            PageList.add(item); //安静模式，不触发事件
            Iframes.add(item);  //会触发 active 事件

        }
    });
    // 加载顶级菜单
    Sidebar.render();
    // 导航栏按钮
    NavBar.render();
    // 用户信息
    UserInfos.render();

})();