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
                FormEdit.save(function (ret) {
                    id = ret.value;
                    operate = 2;
                    FormEdit.render({
                        classId: classId,
                        id: id,
                        operate: operate
                    });
                    // 向主控台跑出一个单据新增成功事件
                    Iframe.raise({
                        'eventName': 'addSuccess',
                        // 修改iframe信息
                        'data': Iframe.setInfos('id', classId + '-edit-' + id)
                    });

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

    FormEdit.on({
        'initDefaultValue': function (meteData) {
            // 新增单据时填充完单据默认值后触发

            if (classId === 1001) {
                // 新增用户时-用户所属组织为当前用户的组织
                var org, selectorData;
                switch (roleType) {

                    case 1:
                        // 系统角色
                        break;
                    case 2:
                        // 医院角色
                        org = FormEdit.getSelectors('org_hospital');
                        selectorData = [{
                            ID: user.org.id,
                            number: user.org_DspNumber || '',
                            name: user.org_DspName || ''
                        }];
                        org.setData(selectorData);
                        break
                    case 3:
                        // 供应商角色
                        org = FormEdit.getSelectors('org_supplier');
                        selectorData = [{
                            ID: user.org.id,
                            number: user.org.number || '',
                            name: user.org.name || ''
                        }];
                        org.setData(selectorData);
                        break;
                }
            }
        }
    });

    FormEdit.render({
        classId: classId,
        id: id,
        operate: operate
    });


})(jQuery, MiniQuery, SMS);