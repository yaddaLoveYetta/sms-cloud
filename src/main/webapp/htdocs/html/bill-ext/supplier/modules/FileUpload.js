/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/11 11:30
 */

define('FileUpload', function (require, module, exports) {

    var SMS = require('SMS');
    var API = SMS.require('API');

    var api = new API("hospital/changeLogo");

    var defaults = {
        uploadUrl: api.getUrl()// 上传请求路径
    };


    function render(container, config, fn) {

        config = $.Object.extend({}, defaults, config);

        SMS.use('FileInput', function (FileInput) {

            var fileInput = new FileInput(container, config);

            fileInput.on('filebatchselected', function (event, files) {
                fileInput.upload(arguments);
            });

            fileInput.on('fileuploaded', function (event, data) {
                /*   fileInput.destroy();
                   fileInput.render();
                   fileInput.enable();*/
                if (data.response) {
                    fn && fn(data.filenames, data.response);
                }
            });

            fileInput.on('fileuploaderror', function (event, data, msg) {
                var form = data.form, files = data.files, extra = data.extra,
                    response = data.response, reader = data.reader;
                SMS.Tips.error(msg, 1000);
            });
        });

    }

    return {
        render: render
    }
});