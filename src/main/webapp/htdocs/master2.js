//主控台控制器。
//注意：所有模块均对控制器可见。

;(function () {

    //alert('hello word!');


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var Sidebar = require('Sidebar');

    // 加载顶级菜单
    Sidebar.render(0);

})();