/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/20 16:32
 */

;(function () {


    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var MessageBox = SMS.require('MessageBox');
    var Selector = require('Selector');

    var dialog = Iframe.getDialog();

    if (dialog) {

        dialog.on({
            get: function () {

                var data = Selector.get("id").getData();

                if (data && data[0].all) {
                    dialog.setData(data);
                }
            },
            back: function () {
                var data = dialog.getData();
                console.dir(data);
                if (data) {
                    var $label = $('#lbl-msg');
                    $label.toggleClass('error-msg', !data.result);
                    $label[0].innerText = data.msg;
                }
            }
        });
    }

    Selector.render();

    // 同意申请
    $('#cooperation-apply-agree').on('click', function () {
        console.log('cooperation-apply-agree');
    })

})();