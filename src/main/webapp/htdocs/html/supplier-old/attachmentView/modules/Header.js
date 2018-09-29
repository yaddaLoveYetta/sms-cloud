/**
 * 头部简要信息填充
 * Created by yadda on 2017/9/28.
 */

define('Header', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');


    var div = document.getElementById('div-head-content');
    var sample = $.String.between(div.innerHTML, '<!--', '-->');


    function render(item) {

        var html;
        if (item) {
            html = $.String.format(sample, {
                'idNumber': item.idNumber, //
                'name': item.name,
                'type': item.type,
                'beginDate': item.beginDate,
                'endDate': item.endDate,
                'check': !!item.check ? 'correct.png' : item.check == 0 ? 'wrong.png' : 'unknow.png',
            });
        } else {
            html = 'none item';
        }

        $(div).html(html);
    }

    return {
        'render': render
    }

});

