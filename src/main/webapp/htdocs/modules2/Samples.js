/**
 * List/Samples 模块
 *
 */
define('Samples', function (require, module, exports) {

    var $ = require('$');

    function trim(s) {
        return s.replace(/\n/g, '').replace(/\s{2,}/g, ' ');
    }

    function get(div) {

        var samples = $.String.getTemplates(div.innerHTML, [

            {
                name: 'menus',
                begin: '<!--',
                end: '-->',
                //fn: trim,
            },
            {
                name: 'top',
                begin: '#--top.begin--#',
                end: '#--top.end--#',
                outer: '{top}',
                //fn: trim,
            }, {
                name: 'top.item',
                begin: '#--top.item.begin--#',
                end: '#--top.item.end--#',
                outer: '{item}'
            },
            {
                name: 'more',
                begin: '#--more.begin--#',
                end: '#--more.end--#',
                outer: '{more}',
                //fn: trim,
            }
        ]);

        return samples;
    }


    return get;

});






    