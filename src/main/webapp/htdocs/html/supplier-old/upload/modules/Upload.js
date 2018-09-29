/**
 * 上传图片控件模块
 *
 */
define('Upload', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Url = SMS.require('Url');
    var url = Url.uploadFileUrlRoot();

    var API = SMS.require("API");

    var btn = $('#sel');

    var classId;
    var itemId;

    // 销毁Uploadify实例并将文件上传按钮恢复到原始状态
    function refrshUploadify() {
        btn.uploadify('destroy');
        render();
    }

    function render(opts) {

        if (opts) {
            classId = opts.classId;
            itemId = opts.itemId;
        }
        var api = new API("file/upload");

        btn.uploadify({

            swf: '../../../lib/uploadify/uploadify.swf',
            uploader: api.getUrl(),
            buttonClass: 'sms-btn-item',
            buttonText: '选择附件',
            uploadLimit: 3,
            simUploadLimit: 3,
            auto: false,
            width: 60,
            height: 25,
            fileSizeLimit: "10MB",
            fileTypeDesc: '文件',
            fileTypeExts: '*.pdf;*.jpg;*.jpeg;*.png;*.gif',
            multi: true,
            method: 'post',
            removeCompleted: true,// 上传完成后自动删除队列
            removeTimeout: 1,
            queueID: "fileQueue",
            queueSizeLimit: 3,
            fileObjName: 'upload',
            overrideEvents: ['onSelectError', 'onDialogClose'],
            onUploadStart: function (file) {
                btn.uploadify("settings", "formData", {
                    classId: classId,
                    itemId: itemId,
                });
            },
            onSelectError: function (file, errorCode, errorMsg) {
                switch (errorCode) {
                    case -100:
                        SMS.Tips.error(
                            '该次上传文件数量已超过系统限制的'
                            + btn.uploadify('settings', 'uploadLimit')
                            + '个文件', 1500);
                        break;
                    case -110:
                        SMS.Tips.error("附件 [" + file.name + "] 大小超出系统限制的5M大小！",
                            1500);
                        break;
                    case -120:
                        SMS.Tips.error("附件 [" + file.name + "] 大小异常！", 1500);
                        break;
                    case -130:
                        SMS.Tips.error("附件 [" + file.name + "] 类型不正确！", 1500);
                        break;
                }
                return false;
            },
            onUploadSuccess: function (file, responseData, response) {

                console.log(file.name + " 上传成功！");
                // var data = $.Object.parseJson(responseData)
                //
                // if (data) {
                // if (data.code == 200) {
                // SMS.Tips.success(data.msg, 2000);
                // } else {
                // SMS.Tips.error(data.msg, 2000);
                // }
                // }
                // refrshUploadify();
            },
            onUploadError: function () {

                if (arguments[3] != "Cancelled") {
                    SMS.Tips.error("系统繁忙,请稍候再试!", 2000);
                }
                refrshUploadify();
            },
            onFallback: function () {
                // 检测flash失败
                SMS.Tips.error("您未安装FLASH控件，无法上传，请安装FLASH控件后重试!");
            },
            onQueueComplete: function () {
                // 所有文件上传完成
                SMS.Tips.success('文件上传完成', 2000);
                refrshUploadify();
            }
        });

    }

    $('#upload').on('click', function () {
        btn.uploadify('upload', '*')
    });

    return {
        render: render,
    }
});