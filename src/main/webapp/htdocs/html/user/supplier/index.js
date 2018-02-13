/**
 * 供应商用户公司信息维护控制器
 */
;(function ($, MiniQuery, sms) {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var MessageBox = SMS.require('MessageBox');
    var FormAction = require('FormAction');
    var bl = SMS.require('ButtonList');
    var ButtonList;

    var itemId = MiniQuery.Url.getQueryString(window.location.href, 'id');
    var classId = MiniQuery.Url.getQueryString(window.location.href, 'classId');

    FormAction.create({'classId': classId}, function (config) {
        ButtonList = new bl(config);
        // 总事件，最后触发
        ButtonList.on('click', function (item, index) {
            console.dir(item);
        });


        ButtonList.on('click', {
            'edit': function (item, index) {
                console.log(item);
            },
        });

        ButtonList.render();
    });

})(jQuery, MiniQuery, SMS);