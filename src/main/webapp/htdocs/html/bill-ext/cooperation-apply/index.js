/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/20 16:32
 */

;(function () {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    $('#view-hospital').on('click', function () {

        SMS.use('Dialog', function (Dialog) {

            // ./ 表示相对于网站根目录
            var url = $.Url.addQueryString('./html/bill-ext/hospital/index.html', {
                classId: 1012,
                id: '65390654661853184',
                operate: 0
            });

            var dialog = new Dialog({
                id: 'view-hospital',
                title: '医院详细信息',
                url: url,
                width: 1024,
                height: 600,
                button: [{
                    value: '关闭',
                    className: 'sms-cancel-btn'
                }]
            });

            dialog.showModal();
        });

    });
})();