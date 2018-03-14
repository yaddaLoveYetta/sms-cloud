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

    var user = SMS.Login.get();
    //检查登录状态
    if (!SMS.Login.check(true)) {
        return;
    }

    // 业务类别
    var classId = Number(MiniQuery.Url.getQueryString(window.location.href, 'classId'));
    //  单据内码
    var id = Number(MiniQuery.Url.getQueryString(window.location.href, 'id') || 0);
    // 操作类别 0：查看 1：新增 2：修改
    var operate = Number(MiniQuery.Url.getQueryString(window.location.href, 'operate'));
    // 用户角色类别
    var roleType = Number((user.roles && user.roles[0] && user.roles[0]['type']) || -1);
    // 用户id
    var userId = Number(user.id);

    var ButtonList;

    FormAction.create({
        'classId': classId,
        'type': operate,
        'textKey': 'textModify',
        'routeKey': 'nameModify',
        'iconKey': 'iconModify',
    }, function (config) {

        if (operate === 0) {
            // 查看详情时不要工具栏
            config.items = [];
        }

        ButtonList = new BL(config);

        // 总事件，最后触发
        ButtonList.on('click', function (item, index) {
            console.dir(item);
        });

        ButtonList.render();

        // 自定义事件
        ButtonList.on('click', {
            'add': function (item, index) {
                // 新增
                FormEdit.save(function () {
                    SMS.Tips.success("保存成功!", 1500);
                });
                console.log(item);
            },
            'edit': function (item, index) {
                // 编辑
                console.log(index);
                console.log(item);
            },
            'refresh': function (item, index) {
                // 刷新
            }
        });
    });

    FormEdit.render({
        classId: classId,
        id: id,
        operate: operate
    });


})(jQuery, MiniQuery, SMS);