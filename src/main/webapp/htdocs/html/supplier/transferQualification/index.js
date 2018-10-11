/**
 * 传送一个证件给合作医院
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

    var Selector = require('Selector');

    var user = SMS.Login.get();
    //检查登录状态
    if (!SMS.Login.check(true)) {
        return;
    }

    var dialog = Iframe.getDialog();

    if (dialog) {

        var data = dialog.getData();

        var node = data.node;

        if (node.hospital) {
            // 医院从证件维护携带
            Selector.set('hospital', {
                id: node.hospital,
                name: node.hospital_DspName,
                number: node.hospital_DspName
            });
        }
    }

    Selector.render();

})(jQuery, MiniQuery, SMS);