
/**
 * 供应商用户公司信息维护控制器
 */
;(function ($, MiniQuery, sms) {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var MessageBox = SMS.require('MessageBox');
    var bl = require('ButtonList');

    var itemId = MiniQuery.Url.getQueryString(window.location.href, 'id');
    var formClassId = MiniQuery.Url.getQueryString(window.location.href, 'classId');

})(jQuery, MiniQuery, SMS);