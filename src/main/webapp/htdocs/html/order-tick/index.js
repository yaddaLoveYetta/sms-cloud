/**
 * 接单
 * Created by yadda on 2017/5/15.
 */

;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var MessageBox = SMS.require('MessageBox');
    var Iframe = SMS.require('Iframe');

    var Bill = require('Bill');

    var classId = MiniQuery.Url.getQueryString(window.location.href, 'classId');
    var orderId = MiniQuery.Url.getQueryString(window.location.href, 'orderId');

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var Iframe = SMS.require('Iframe');

    var dialog = Iframe.getDialog();

    var data = {};

    dialog.on({

        get: function () {
            dialog.setData({
                id:orderId,
                entry:Bill.getEntryData(),
            });
        },
        serverBack: function () {
            // 回调
            var data = dialog.getData();
            console.dir(data);
            if (data) {

            }
        }
    });

    Bill.render({
        'classId': classId,
        'id': orderId
    });

})();
