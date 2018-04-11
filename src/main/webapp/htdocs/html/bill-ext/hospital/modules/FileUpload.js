/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/11 11:30
 */

define('FileUpload', function (require, module, exports) {

    var SMS = require('SMS');
    var API = SMS.require('API');

    var api = new API("file/uploadPic");

    var defaults = {
        theme: "explorer", //主题
        language: 'zh',
        uploadUrl: api.getUrl(),// 上传请求路径
        allowedFileExtensions: ['jpg', 'gif', 'png', 'jpeg', 'pdf'],//允许上传的文件后缀
        uploadAsync: true, //是否允许异步上传 false时 fileuploaded事件无效
        showUpload: false,//是否显示上传按钮
        showRemove: false,//是否移除按钮
        showCaption: false,//是否显示容器
        dropZoneEnabled: false,//是否显示拖拽区域
        removeFromPreviewOnError: true,//是否移除校验文件失败的文件
        uploadExtraData: function (previewId, index) {   //额外参数 返回json数组
            return {
                //'id': commId  // commId 为全局变量，可以给控件上传额外参数
            };
        },
        layoutTemplates: {    //取消上传按钮
            actionUpload: ''
        },
        showPreview: false,      //显示预览
        minFileCount: 1,   //最低文件数量
        maxFileCount: 3,   //最多文件数量
        maxFileSize: 512,  //允许文件上传大小
        overwriteInitial: true,
        previewFileIcon: '<i class="fa fa-file"></i>',
        initialPreviewAsData: true, // defaults markup
        preferIconicPreview: false, // 是否优先显示图标  false 即优先显示图片
    };


    function render(container, config, fn) {

        /*        config = $.Object.extend({}, defaults, config);

                $(container).fileinput(config).on("filebatchselected", function (event, files) {
                    $(this).fileinput("upload");
                }).on("fileuploaded", function (event, data) {
                    if (data.response) {
                        fn && fn(data.response);
                    }
                });*/

        SMS.use('FileInput', function (FileInput) {

            var fileInput = new FileInput(container, config);

            fileInput.on('filebatchselected', function (event, files) {
                $(this).fileinput("upload");
            });

            fileInput.on('fileuploaded', function (event, data) {
                if (data.response) {
                    fn && fn(data.response);
                }
            });
        });

    }

    return {
        render: render
    }
});