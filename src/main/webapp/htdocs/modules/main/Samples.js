/**
 * Samples 模块
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
                name: 'item',
                begin: '#--menu.item.begin--#',
                end: '#--menu.item.end--#',
                outer: '{item}',
            },
/*            {
                name: 'top',
                begin: '#--menu.top.begin--#',
                end: '#--menu.top.end--#',
                outer: '{top}',
            },*/
            {
                name: 'sub',
                begin: '#--menu.sub.begin--#',
                end: '#--menu.sub.end--#',
                outer: '{sub}',
            },
            {
                name: 'subItem',
                begin: '#--menu.sub.item.begin--#',
                end: '#--menu.sub.item.end--#',
                outer: '{subItem}',
            },
            {
                name: 'line',
                begin: '#--menu.item.line.begin--#',
                end: '#--menu.item.line.end--#',
                outer: '{line}',
            }
        ]);

        return samples;
    }


    return get;

});






    