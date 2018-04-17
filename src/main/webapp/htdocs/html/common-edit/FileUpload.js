/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/11 11:30
 */

define('FileUpload', function (require, module, exports) {

    var SMS = require('SMS');

    function render(container, config, fn) {

        SMS.use('FileInput', function (FileInput) {

            var fileInput = new FileInput(container, config);

            fileInput.on('filebatchselected', function (event, files) {
                fileInput.upload(arguments);
            });

            fileInput.on('fileuploaded', function (event, data) {
                if (data.response) {
                    fn && fn(data.filenames, data.response);
                }
            });

            fileInput.on('fileuploaderror', function (event, data, msg) {
                var form = data.form, files = data.files, extra = data.extra,
                    response = data.response, reader = data.reader;
                SMS.Tips.error(msg);
            });
        });

    }

    return {
        render: render
    }
});