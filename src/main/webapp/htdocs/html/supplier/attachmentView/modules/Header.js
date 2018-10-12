/**
 * 头部简要信息填充
 * Created by yadda on 2017/9/28.
 */

define('Header', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var emitter = MiniQuery.Event.create();


    var div = document.getElementById('div-head-content');
    var sample = $.String.between(div.innerHTML, '<!--', '-->');


    function render(item) {

        var html;
        if (item) {
            html = $.String.format(sample, {
                'number': item.number,
                'typeName': item.typeName,
                'beginDate': item.beginDate,
                'endDate': item.endDate,
                'check': item.status === 2 ? 'correct.png' : item.check === 3 ? 'wrong.png' : 'unknow.png'
            });
        } else {
            html = 'none item';
        }

        $(div).html(html);

        emitter.fire("done", [item]);
    }

    return {
        'render': render,
        on: emitter.on.bind(emitter)
    }

});

