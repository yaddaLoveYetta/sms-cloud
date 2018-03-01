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
    var FormEdit = require('FormEdit');

    var BL = SMS.require('ButtonList');

    //检查登录状态
    if (!SMS.Login.check(true)) {
        return;
    }

    // 业务类别
    var classId = MiniQuery.Url.getQueryString(window.location.href, 'classId');
    // 用户角色类别
    var roleType = MiniQuery.Url.getQueryString(window.location.href, 'roleType');
    // 用户id
    var userId = MiniQuery.Url.getQueryString(window.location.href, 'user');
    // 操作类别 0：查看 1：新增 2：修改
    var operate = MiniQuery.Url.getQueryString(window.location.href, 'operate');

    var ButtonList;

    FormAction.create({'classId': classId}, function (config) {

        ButtonList = new BL(config);

        // 总事件，最后触发
        ButtonList.on('click', function (item, index) {
            console.dir(item);
        });

        ButtonList.render();

        // 自定义事件
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

    FormEdit.render(classId, userId, operate);


})(jQuery, MiniQuery, SMS);