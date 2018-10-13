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
    // 附件
    var attachment;

    var formData;

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

                Edit.getHeadData(function (headData) {

                    Edit.showHeadValidInfo(headData.successData, headData.errorData);

                    if (headData.errorData) {
                        return;
                    }

                    formData = {

                        id: id, // 新增时id=0
                        number: headData.successData.number,
                        issue: headData.successData.issue,
                        type: headData.successData.qualification_type,
                        validityPeriodBegin: headData.successData.validity_period_begin,
                        validityPeriodEnd: headData.successData.validity_period_end

                    };

                    attachment.upload();

                });

                // 修改时保存

            },
            'refresh': function (item, index) {
                // 刷新
                Edit.render(operate, classId, id);
            }
        });
    });

    DatetimePicker.render();
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
        'selectorGet': function (field, key) {
            return Selector.get(key);
        },
        'selectorLock': function (key) {
            Selector.get(key).lock();
        },
        'selectorUnLock': function (key) {
            Selector.get(key).unlock();
        },
        'afterFill': function (classId, metaData, data) {

        },
        'afterFieldLock': function (metaData, itemData) {
            /*            if (operate === 0) {
                            Address.lock();
                        }*/
        },
        'afterInitPage': function (metaData) {

            if (operate === 1 && user.roles && user.roles[0] && user.roles[0]['type'] === 3) {

                var api = new API("supplier/addQualification");

                // 新增-初始化附件上传控件
                SMS.use('FileInput', function (FileInput) {

                    attachment = new FileInput('#attachment', {
                        uploadUrl: api.getUrl(),// 上传请求路径
                        uploadAsync: false,
                        layoutTemplates: {
                            // actionDelete:'', //去除上传预览的缩略图中的删除图标
                            actionUpload: '',//去除上传预览缩略图中的上传图片；
                            //actionZoom:''   //去除上传预览缩略图中的查看详情预览的缩略图标。
                        },
                        allowedFileExtensions: ['jpg', 'gif', 'png', 'doc', 'docx', 'pdf', 'ppt', 'pptx', 'txt'],//接收的文件后缀
                        uploadExtraData: function () {
                            return formData;
                        }
                    });

                    attachment.on('fileuploaderror', function (event, data, msg) {
                        SMS.Tips.error(msg);
                    });
                });
            }

        },

        'beforeSave': function (metaData, headData) {
        }
    });

})(jQuery, MiniQuery, SMS);