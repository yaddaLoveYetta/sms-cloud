/**
 * 上传图片控件模块
 *
 */
define('List/Upload', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Url = SMS.require('Url');
    var url = Url.uploadFileUrlRoot();

    var API = SMS.require("API");

    // var btn = $('.sms-btn-menu li[index="5"]');
    var btn;

    function refrshUploadify() {
        btn.uploadify('destroy');
        render();
    }

    function render() {
        btn = $('li[data-index="5"]');
        var api = new API("car/importCarGroup");

        btn.uploadify({
            swf: '../../../lib/uploadify/uploadify.swf',
            uploader: api.getUrl(),
            buttonText: '选择附件',
            multi: false,
            uploadLimit: 1,
            auto: false,
            fileSizeLimit: "5MB",
            'fileTypeDesc': 'Excel文件',
            'fileTypeExts': '*.xlsx;*.xlsm;*.xltx;*.xltm;*.xlsa;*.xlsb;*.xlsk;*.xls;*.csv',
            method: 'Post',
            removeTimeout: 1,
            queueID: "file_queue",
            //itemTemplate: '<div id="${fileID}" style="display:inline" ><span class="uploadify-queue-item-progress"></span>正在上传...</div>',
            overrideEvents: ['onSelectError', 'onDialogClose'],

            onSelectError: function (file, errorCode, errorMsg) {
                switch (errorCode) {
                    case -100:
                        SMS.Tips.error('只能上传一个附件！', 2000);
                        break;
                    case -110:
                        SMS.Tips.error("附件 [" + file.name + "] 大小超出系统限制的5M大小！", 2000);
                        break;
                    case -120:
                        SMS.Tips.error("附件 [" + file.name + "] 大小异常！", 2000);
                        break;
                    case -130:
                        SMS.Tips.error("附件 [" + file.name + "] 类型不正确！", 2000);
                        break;
                }
                return false;
            },
            onUploadStart: function (fileObj) {
                //return false;
            },
            onUploadSuccess: function (fileObj, responseData, response) {
                var data = $.Object.parseJson(responseData)

                if (data) {
                    if (data.code == 200) {
                        SMS.Tips.success(data.msg, 2000);
                    } else {
                        SMS.Tips.error(data.msg, 2000);
                    }
                }
                refrshUploadify();
            },
            onUploadError: function () {
                if (arguments[3] != "Cancelled") {
                    SMS.Tips.error("系统繁忙,请稍候再试!", 2000);
                }
                refrshUploadify();
            }
        });

    }

    function upload(classId, list, fn) {

        btn.uploadify("settings", "formData", {
            classId: classId,
            list: list,
        });
        btn.uploadify('upload', '*')
    }

    return {
        render: render,
        upload: upload,
    }
});