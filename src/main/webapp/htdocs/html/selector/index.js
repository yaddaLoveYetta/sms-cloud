


; (function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var iframe = SMS.require('Iframe');

    var selector = require('selector');

    var DataSelector = require('DataSelector');


    var div = document.getElementById('div-samples');
    var selector2 = document.getElementById('selector2');


    var config = {
        targetType: 2, //跳转方案
        //typeID: 1,   //过滤条件
        hasBreadcrumbs: true,
        container: div,
        title: '辅助资料选取'
    };

    var config2 = {
        targetType: 2, //跳转方案
        typeID: 1,   //过滤条件
        hasBreadcrumbs: false,
        container: selector2
    };

    var data = {
        itemID: 1,
        number: '01',
        name: 'test',
        title: '辅助资料选取'
    };

    var selector = DataSelector.create(config);
    $('[data-role="label"]').blur(function () {
        console.log(selector.getData());
        console.log(selector2.getData());
    });

    var selector2 = DataSelector.create(config2);

    selector2.setData([{
        ID: 1,
        name: 'test',
        number: '01'
    }]);

    //selector.create(div, config);

})();