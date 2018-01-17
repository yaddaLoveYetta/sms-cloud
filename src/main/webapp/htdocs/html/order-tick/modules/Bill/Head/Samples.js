/**
 * 单据头页面模板
 * Created by yadda on 2017/5/13.
 */

define('Bill/Head/Samples', function (require, module, exports) {

    var $ = require('$');

    function trim(s) {
        return s.replace(/\n/g, '').replace(/\s{2,}/g, ' ');
    }

    function get(div) {

        var samples = $.String.getTemplates(div.innerHTML, [
            {
                name: 'rows',
                begin: '<!--',
                end: '-->',
                //fn: trim,
            },
            {
                name: 'row',
                begin: '#--row.item.begin--#',
                end: '#--row.item.end--#',
                outer: '{item}',
                //fn: trim,
            },

        ]);

        return samples;
    }


    return get;

});
