;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Iframe = SMS.require('Iframe');
    var Filter = require('Filter');

    // 获取从dialog传入iframe的数据
    var dialog = Iframe.getDialog();
    var iframeData = dialog.getData();
    var conditionExt = dialog.conditionExt;

    if (iframeData) {
        console.log(iframeData)
        Filter.render(iframeData, function () {
            Filter.setDefaultFilter(conditionExt);
        });
    }

    dialog.on({
        get: function () {
            var data = Filter.getFilterObject();
            dialog.setData(data);
        }
    });

    Filter.on({
        'render.done': function () {
        }
    });

})();