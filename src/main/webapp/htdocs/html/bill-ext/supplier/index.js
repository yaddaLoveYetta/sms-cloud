/**
 * 单据详情/新增/编辑页面控制器
 * @author : yadda(silenceisok@163.com)
 * @date : 2018/3/11 11:25
 */
;(function ($, MiniQuery, sms) {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var Iframe = SMS.require('Iframe');
    var MessageBox = SMS.require('MessageBox');
    var BL = SMS.require('ButtonList');

    var FormAction = require('FormAction');
    var FileUpload = require('FileUpload');
    var DatetimePicker = require('DatetimePicker');
    var Selector = require('Selector');
    var NumberField = require('NumberField');
    var Address = require('Address');
    var Edit = require('Edit');


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

    var addressPicker;

    FormAction.create({'classId': classId, 'type': operate}, function (config) {

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
                // 修改时保存
                Edit.save(function (ret) {
                    SMS.Tips.success("保存成功!", 1500);
                });
            },
            'refresh': function (item, index) {
                // 刷新
                Edit.render(operate, classId, id);
            }
        });
    });

    DatetimePicker.render();
    NumberField.render();
    Selector.render();
    Edit.render(operate, classId, id);

    Edit.on({
        'numberFieldSet': function (field, key, value) {
            NumberField.set(field, key, value);
        },
        'numberFieldGet': function () {
            return NumberField.get(field, key);
        },
        'selectorSet': function (field, key, selectorData) {
            Selector.set(field, key, selectorData);
        },
        'afterFill': function (classId, metaData, data) {
            $("#supplier-logo").attr('src', data['logo']);
            // 渲染地址选择器
            Address.create({
                'id': 'address-picker',
                'province': data.province || -1,
                'city': data.city || -1,
                'district': data.district || -1
            });
            Address.showHeadValidInfo(false);

            if (operate === 0) {
                Address.lock();
            }
        },
        'afterInitPage': function (metaData) {

            if (operate === 0) {
                $('#supplier-logo-select').remove();
            } else {

                var api = new API("supplier/changeLogo");

                FileUpload.render('#supplier-logo-select', {
                    uploadUrl: api.getUrl(),// 上传请求路径
                    dropZoneEnabled: false,//是否显示拖拽区域
                    showPreview: false,
                    showCaption: false,
                    uploadExtraData: function (previewId, index) {
                        //额外参数 返回json数组
                        return {
                            classId: classId,
                            id: id
                        };
                    }
                }, function (fileNames, resp) {
                    if (resp.code === 200) {
                        $("#supplier-logo").attr('src', resp.data[fileNames[0]]);
                    } else {
                        SMS.Tips.error(resp.msg, 1000);
                    }
                });
            }

        },
        'beforeSave': function (metaData, headData) {
            var address = Address.getSelectedItems();
            if (address.length !== 3) {
                Address.showHeadValidInfo(true);
                return false;
            } else {
                Address.showHeadValidInfo(false);
                headData.successData['province'] = address[0][0] || -1;
                headData.successData['city'] = address[1][0] || -1;
                headData.successData['district'] = address[2][0] || -1;

                return true;
            }

        }
    });

})(jQuery, MiniQuery, SMS);