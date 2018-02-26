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

    var BL = SMS.require('ButtonList');

    var itemId = MiniQuery.Url.getQueryString(window.location.href, 'id');
    var classId = MiniQuery.Url.getQueryString(window.location.href, 'classId');
    var ButtonList;

    FormAction.create({'classId': classId}, function (config) {

        ButtonList = new BL(config);
        ButtonList.render();

        ButtonList.on('click', {
            'add': function (item, index) {

                console.log(item);
            },
            'edit': function (item, index) {
                console.log(index);
                console.log(item);
            }
        });
    });


})(jQuery, MiniQuery, SMS);