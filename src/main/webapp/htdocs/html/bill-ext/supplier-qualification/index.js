/**
 * 供应商证件详情/新增/编辑页面控制器
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
    var DatetimePicker = require('DatetimePicker');
    var Selector = require('Selector');
    var NumberField = require('NumberField');
    var Edit = require('Edit');


    var user = SMS.Login.get();
    var userRoleType = SMS.Login.getUserRoleType();
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
    // 新增修改时提交接口的证件信息对象
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

                    if (attachment.getFilesCount() === 0) {
                        SMS.Tips.error('请选择证件附件文件', 1000);
                        return;
                    }

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

            if (attachment) {
                attachment.destroy();
            }

            var option = buildFileInputInitOption(operate);

            if (operate === 1) {

                // 新增-初始化附件上传控件

                SMS.use('FileInput', function (FileInput) {

                    attachment = new FileInput('#attachment', option);

                    attachment.on('filebatchpreupload', function (event, data) {

                        if (data.filescount === 0) {
                            return {
                                result: false,
                                message: "请选择:请至少选择一个附件", // 验证错误信息在上传前要显示。如果设置了这个设置，插件会在调用时自动中止上传，并将其显示为错误消息。您可以使用此属性来读取文件并执行自己的自定义验证
                                data: {} // any other data to send that can be referred in `filecustomerror`
                            }
                        }
                        console.log(data);

                    });

                    attachment.on('filebatchuploadsuccess', function (event, data) {

                        var response = data.response;
                        if (response.code !== 200) {
                            // 上传失败
                            SMS.Tips.error(response.msg);

                            return;
                        }
                        // 新增成功返回新增证件id
                        id = response.data.value;
                        // 新增成功变修改模式
                        operate = 2;
                        Edit.render(operate, classId, id);

                        //attachment.reset(); //重置上传控件（清空已上传文件）
                    });

                    // uploadAsync set to false
                    attachment.on('filebatchuploaderror', function (event, data, msg) {

                        /*        var form = data.form;
                                var files = data.files;
                                var extra = data.extra;
                                var response = data.response;
                                var reader = data.reader;*/

                        SMS.Tips.error(msg);
                    });

                    attachment.on('filebatchuploadcomplete', function (event, files, extra) {
                        //layer.msg('上传失败', {icon: 0});//弹框提示
                        //return false;
                    });

                    attachment.on("fileuploaddone", function (e, data) {
                        // 上传完成时调用
                        if (data.result.returnState === "ERROR") {
                            alert("上传失败")
                            return;
                        }
                    });

                });

                return;
            }

            if (operate === 0 || operate === 2) {
                // 查看修改时，初始化附件预览

                var attachments = data.entry && data.entry[1];

                if (attachments) {
                    var initialPreview = [];
                    var initialPreviewConfig = [];
                    $.Array.each(attachments, function (item, index) {

                        if (item.path.indexOf(".txt") > 0) {// 非图片类型的展示
                            initialPreview.push("<div class='file-preview-other-frame'><div class='file-preview-other'></div></div>");
                        } else {// 图片类型
                            initialPreview.push("<img src='" + item.path + "' class='file-preview-image img-responsive'>");
                        }
                        initialPreviewConfig.push({
                            caption: data.qualification_type_DspName, // 展示的文件名
                            url: new API('supplier/delQualificationAttachmentById').getUrl(), // 删除url
                            key: item.attachment_id, // 删除是Ajax向后台传递的参数
                            extra: {
                                qualificationId: item.parent,
                                attachmentId: item.attachment_id
                            }
                        });
                    });

                    option.initialPreview = initialPreview;
                    option.initialPreviewConfig = initialPreviewConfig;
                }

                // 查看时预览附件
                SMS.use('FileInput', function (FileInput) {

                    attachment = new FileInput('#attachment', option);


                    attachment.on('filesuccessremove', function (event, id) {
                        // 图片上传成功后，点击删除按钮的回调函数
                        //return false;

                    });

                    attachment.on('filebeforedelete', function (event, key, data) {
                        // 预览时点击缩略图上的删除按钮才能触发的
                        /*MessageBox.confirm('确定删除该附件?', function (result) {
                            // return true 时不删除
                            return !result;
                        });*/
                        // return true 时不删除
                        //return true;

                    });

                    attachment.on('filepredelete', function (event, key, jqXHR, data) {
                        console.log('Key = ' + key);
                    });

                });


                return;
            }


        },
        'afterFieldLock': function (metaData, itemData) {
            /*            if (operate === 0) {
                            Address.lock();
                        }*/
        },
        'afterInitPage': function (metaData) {

        },
        'beforeSave': function (metaData, headData) {
        }
    });

    function buildFileInputInitOption(operateType) {

        var option = {
            uploadAsync: false,
            allowedFileExtensions: ['jpg', 'jpeg', 'gif', 'png', 'pdf'],//接收的文件后缀
            uploadExtraData: function () {
                return formData;
            }
        };

        if (operateType === 0) {
            // 查看
            option.fileActionSettings = {
                showUpload: false,
                showRemove: false,
                showDrag: false,
                showZoom: true,
                showDownload: true
            };
/*            option.layoutTemplates = {
                actionDelete: '',//显示移除按钮,缩略图中的那个
                actionUpload: '', //是否显示上传按钮,缩略图中的那个
                actionDrag: '', //是否显示移动按钮,缩略图中的那个
                actionDownload: '<button type="button" class="{downloadClass}" title="{downloadTitle}" data-url="{downloadUrl}" data-caption="{caption}">{downloadIcon}</button>\\n'
                //actionZoom: '',//显示预览按钮,缩略图中的那个
            };*/
            option.showUpload = false; //是否显示上传按钮,跟随文本框的那个
            option.showRemove = false; //显示移除按钮,跟随文本框的那个
            option.showCaption = false;//是否显示标题,就是那个文本框
            option.showBrowse = false;//是否显示浏览按钮,跟随那个文本框

        }

        if (operateType === 1) {
            // 新增
            var api = new API("supplier/addQualification");
            // 上传请求路径
            option.uploadUrl = api.getUrl();
            option.layoutTemplates = {
                actionDrag: '',
            };
            option.layoutTemplates = {
                actionUpload: '',
                actionDrag: '',
            };
        }

        if (operateType === 2) {
            // 修改

            api = new API("supplier/editQualification");
            // 上传请求路径
            option.uploadUrl = api.getUrl();
            option.layoutTemplates = {
                actionDrag: ''
            };
            option.overwriteInitial = false; //选择文件后是否自动替换预览列表文件
        }

        return option;
    }

})(jQuery, MiniQuery, SMS);