/**
 * 单据详情/新增/编辑页面控制器
 * @author : yadda(silenceisok@163.com)
 * @date : 2018/3/11 11:25
 */
;(function ($, MiniQuery, sms) {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var MessageBox = SMS.require('MessageBox');
    var FormAction = require('FormAction');
    var FormEdit = require('FormEdit');
    var FileUpload = require('FileUpload');

    var BL = SMS.require('ButtonList');

    var user = SMS.Login.get();
    //检查登录状态
    if (!SMS.Login.check(true)) {
        return;
    }

    // 业务类别
    var classId = Number(MiniQuery.Url.getQueryString(window.location.href, 'classId'));
    //  单据内码-后台用18位Long数字存储，js中Number保存不下这个长度，转用String处理
    var id = MiniQuery.Url.getQueryString(window.location.href, 'id') || 0;
    // 操作类别 0：查看 1：新增 2：修改
    var operate = Number(MiniQuery.Url.getQueryString(window.location.href, 'operate'));
    // 用户角色类别
    var roleType = Number((user.roles && user.roles[0] && user.roles[0]['type']) || -1);

    var ButtonList;

    FormAction.create({
        'classId': classId,
        'type': operate,
        'textKey': 'textModify',
        'routeKey': 'nameModify',
        'iconKey': 'iconModify'
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
            'save': function (item, index) {
                // 新增、修改时保存
                FormEdit.save(function (ret) {

                    if (operate === 1) {
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
                            'data': Iframe.getInfos()
                        });
                    }
                    SMS.Tips.success("保存成功!", 1500);
                });
                console.log(item);
            },
            'refresh': function (item, index) {
                // 刷新
                FormEdit.render({
                    classId: classId,
                    id: id,
                    operate: operate
                });
            }
        });
    });

    FormEdit.on({
        'afterFill': function (classId, metaData, data) {
            if (classId === 1012) {
                $("#hospital-logo").attr('src', data['logo']);
            }
        },
        'afterInitPage': function (metaData) {

            if (operate === 0) {
                $('#hospital-logo-select').remove();
                return;
            }
            FileUpload.render('#hospital-logo-select', {

                uploadExtraData: function (previewId, index) {
                    //额外参数 返回json数组
                    return {
                        classId: classId,
                        id: id
                    };
                }
            }, function (fileNames, resp) {
                if (resp.code === 200) {
                    $("#hospital-logo").attr('src', resp.data[fileNames[0]]);
                } else {
                    SMS.Tips.error(resp.msg, 1000);
                }
            });
        }
    });

    FormEdit.render({
        classId: classId,
        id: id,
        operate: operate
    });

})(jQuery, MiniQuery, SMS);