;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var MessageBox = SMS.require('MessageBox');
    var bl = require('ButtonList');
    var Edit = require('Edit');
    var DatetimePicker = require('DatetimePicker');
    var itemId = MiniQuery.Url.getQueryString(window.location.href, 'id');
    var formClassId = MiniQuery.Url.getQueryString(window.location.href, 'classId');
    var defaultValue;

    //检查登录

    var ButtonList = bl.create(itemId);

    if (!SMS.Login.check(true)) {
        return;
    }

    /*    Iframe.on('before-close', function (infos) {

     MessageBox.confirm('正在编辑，确认关闭?', function (result) {
     if (!result) {
     return false;
     }
     });
     });*/

    var dialog = Iframe.getDialog();

    if (dialog) {
        var data = dialog.getData() || {};
        defaultValue = data.defaultValue;
    }

    // 基础资料类别ID
    // 支持二级事件，二级事件对应 item 中的 name
    ButtonList.on('click', {
        'optNew': function () {
            Edit.clear();
        },
        'optSave': function () {
            Edit.save();
        },
        'optRefresh': function () {
            Edit.refresh(formClassId);
        },
        'optDisable': function () {
            Edit.forbid(formClassId, 1);
        },
        'optUndisable': function () {
            Edit.forbid(formClassId, 0);
        }
    });

    Edit.on({
        'addSuccess':function (itemId) {

            Iframe.raise({
                'eventName':'addSuccess',
                'data':Iframe.getInfos(),
            });
            Edit.render(formClassId, itemId);
        },
        'editSuccess':function () {

            Iframe.raise({
                'eventName':'editSuccess',
                'data':Iframe.getInfos(),
            });

        },
        'addNew':function () {

            Iframe.raise({
                'eventName':'addNew',
                'data':Iframe.getInfos(),
            });

        }
    });

    DatetimePicker.render();
    ButtonList.render();
    Edit.render(formClassId, itemId, defaultValue);

})();
