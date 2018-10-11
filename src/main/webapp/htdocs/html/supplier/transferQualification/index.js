/**
 * 传送一个证件给合作医院
 * @author : yadda(silenceisok@163.com)
 * @date : 2018/3/11 11:25
 */
;(function ($, MiniQuery, sms) {

    var $ = require('$');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var Selector = require('Selector');

    //检查登录状态
    if (!SMS.Login.check(true)) {
        return;
    }

    Selector.render();

    var dialog = Iframe.getDialog();

    if (dialog) {

        var data = dialog.getData();

        var node = data.node;

        if (node.hospital) {
            // 医院从证件维护携带
            Selector.set('hospital', [
                {
                    id: node.hospital,
                    name: node.text,
                    number: node.text
                }
            ]);
        }
    }

    dialog.on({

        get: function () {

            var selectData = {};

            var selector = Selector.get('type');
            selectData.type = selector && selector[0] && selector[0]['id'];

            var selector = Selector.get('hospital');
            selectData.hospital = selector && selector[0] && selector[0]['id'];

            var selector = Selector.get('qualification');
            selectData.qualification = selector && selector[0] && selector[0]['id'];

            dealMsg('type', selectData.type && selectData.type > 0, '请选择证件类别');
            dealMsg('hospital', selectData.hospital && selectData.hospital > 0, '请选择医院');
            dealMsg('qualification', selectData.qualification && selectData.qualification > 0, '请选择证件');

            dialog.setData(selectData);
        },
        serverBack: function () {
            var data = dialog.getData();

            if (data) {
                if (data.result) {
                    SMS.Tips.success(data.msg, 1000);
                    Selector.clearData('type');
                    Selector.clearData('qualification');
                } else {
                    SMS.Tips.error(data.msg, 1000);
                }
            }
        }
    });

    function dealMsg(key, error, msg) {

        var msgElement = document.getElementById(key + '-msg');

        if (!error) {
            // 校验不通过
            if (!$(msgElement).hasClass('show')) {
                $(msgElement).toggleClass('show');
            }
            $(msgElement).html(msg);
        } else {
            // 校验通过
            if ($(msgElement).hasClass('show')) {
                $(msgElement).toggleClass('show');
            }
            $(msgElement).html('');
        }


    }


})(jQuery, MiniQuery, SMS);