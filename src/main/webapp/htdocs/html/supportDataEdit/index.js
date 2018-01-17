


; (function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var KERP = require('KERP');
    var Iframe = KERP.require('Iframe');

    var data = require('data');
    var operations = require('operations');

    //获取从dialog传入iframe的数据
    var iframeData = Iframe.getData();
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
        KERP.Tips.error('错误！没有对应的辅助资料！');
    }
})();