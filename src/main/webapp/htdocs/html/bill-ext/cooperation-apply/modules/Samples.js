/**
 * @Title: Samples 模块
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/23 10:25
 */
define('Samples', function (require, module, exports) {

    var $ = require('$');

    function get(div) {

        return $.String.getTemplates(div.innerHTML, [
            {
                name: 'detail',
                begin: '<!--',
                end: '-->'
            },
            {
                name: 'item',
                begin: '#--item.begin--#',
                end: '#--item.end--#',
                outer: '{item}'
            }
            ,
            {
                name: 'no-item',
                begin: '#--item.none.begin--#',
                end: '#--item.none.end--#',
                outer: '{no-item}'
            }
        ]);
    }

    return get;

});
