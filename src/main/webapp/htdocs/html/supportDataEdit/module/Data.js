


//详情数据模块
define('data', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var KERP = require('KERP');
    var iframe = KERP.require('Iframe');
    var API = KERP.require('API');

    var dialog = iframe.getDialog();
    var div = document.getElementById('div_data');

    var samples = $.String.getTemplates(div.innerHTML, [{
        name: 'data',
        begin: '<!--',
        end: '-->'
    }]);

    var list = [];

    //获取数据
    function load(query, fn) {
        var api = new API('/bd/assistitem');

        api.get(query);

        api.on({
            success: function (data, json) {
                fn && fn(data);
            }
        });

        //KERP.API.get('/bd/assistitem', query, function (data, json) {
        //    fn && fn(data);
        //}, function (code, msg, json) {

        //}, function () {

        //});
    }

    //上传数据
    function postData(query, fn) {
        var api = new API('/bd/assistitem');

        KERP.Tips.loading('数据上传中...');

        api.post(query);

        api.on({
            success: function (data, json) {
                fn && fn(data);
                KERP.Tips.success('数据上传成功！', 1000);
                dialog.remove();
            },
        });

        //KERP.API.post('/bd/assistitem', query, function (data, json) {
        //    fn && fn(data);
        //    KERP.Tips.success('数据上传成功！', 1000);
        //    dialog.remove();
        //}, function (code, msg, json) {

        //}, function () {

        //});
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