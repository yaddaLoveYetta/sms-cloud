/**
 * 分页器模块
 *
 */
define('Pager', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var pagers;

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    function render(config) {

        config = $.Object.extend({
            current: 1, //当前激活的页码，默认为 1
            error: function (msg) {
                SMS.Tips.error(msg, 1500);
                this.focus();
            }
        }, config, {

            container: {
                simple: '#div-pager-simple', //简易分页控件的容器
                normal: '#div-pager-normal' //标准分页控件的容器
            }
        });

        //simplePage=SMS.SimplePager.create(config);
        pagers = SMS.Pagers.create(config);

    }

    function getTotal() {
        return pagers && pagers.pager.getTotal();
    }

    return {
        render: render,
        getTotal: getTotal
    };

});

