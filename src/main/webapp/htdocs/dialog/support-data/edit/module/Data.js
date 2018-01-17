


//详情数据模块
define('data', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var iframe = SMS.require('Iframe');

    var dialog = iframe.getDialog();
    var div = document.getElementById('div_data');

    var samples = $.String.getTemplates(div.innerHTML, [{
        name: 'data',
        begin: '<!--',
        end: '-->'
    }]);

    var list = [];

    //获取数据
    function load(queryStr, fn) {

        SMS.API.get('/bd/assistitem', queryStr, function (data, json) {
            fn && fn(data);
        }, function (code, msg, json) {

        }, function () {

        });
    }

    //上传数据
    function postData(queryStr, fn) {
        SMS.Tips.loading('数据上传中...');

        SMS.API.post('/bd/assistitem', queryStr, function (data, json) {
            fn && fn(data);
            SMS.Tips.success('数据上传成功！', 1000);
            dialog.remove();
        }, function (code, msg, json) {

        }, function () {

        });
    }

    function render(data) {
        list = data;
        div.innerHTML = $.String.format(samples.data, {
            'typeName': data.typeName,
            'number': data.number,
            'name': data.name
        });
    }


    //保存数据
    function saveData() {
        var itemID = list.itemID;

        var name = document.getElementById('name').value;
        var number = document.getElementById('number').value;

        var data = {
            'action': 'update',
            'itemID': itemID,
            'name': name,
            'number': number
        };

        postData(data, render);
    }

    return {
        load: load,
        saveData: saveData,
        render: render
    }
});