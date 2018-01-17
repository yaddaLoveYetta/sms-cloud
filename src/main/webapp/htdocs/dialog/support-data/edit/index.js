


; (function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');

    var data = require('data');
    var operations = require('operations');

    //获取从dialog传入iframe的数据
    var dialog = Iframe.getDialog();
    var iframeData = dialog.getData();
    var itemID = iframeData.itemID;

    var queryStr = {
        'action': 'findByID',
        'itemID': itemID
    };

    if (itemID != null && itemID != '') {
        data.load(queryStr, data.render);

        operations.render();

        operations.on({
            'save.click': function () {
                data.saveData();
            }
        });
    }
    else {
        SMS.Tips.error('错误！没有对应的辅助资料！');
    }
})();