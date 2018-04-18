/**
 * List/Samples 模块
 *
 */
define('List/Samples', function (require, module, exports) {

    var $ = require('$');

    function trim(s) {
        return s.replace(/\n/g, '').replace(/\s{2,}/g, ' ');
    }

    function get(div) {

        var samples = $.String.getTemplates(div.innerHTML, [
            {
                name: 'table',
                begin: '<!--',
                end: '-->'
                //fn: trim,
            },
            {
                name: 'th.checkbox',
                begin: '#--th.checkbox.begin--#',
                end: '#--th.checkbox.end--#',
                outer: '{checkbox}'
                //fn: trim,
            },
            {
                name: 'th',
                begin: '#--th.begin--#',
                end: '#--th.end--#',
                outer: '{ths}'
                //fn: trim,
            },
            {
                name: 'tr',
                begin: '#--tr.begin--#',
                end: '#--tr.end--#',
                outer: '{trs}'
                //fn: trim,
            },
            {
                name: 'td.checkbox',
                begin: '#--td.checkbox.begin--#',
                end: '#--td.checkbox.end--#',
                outer: '{checkbox}'
                //fn: trim,
            },
            {
                name: 'td.image',
                begin: '#--td.image.begin--#',
                end: '#--td.image.end--#',
                outer: '{image}'
            },
            {
                name: 'td',
                begin: '#--td.begin--#',
                end: '#--td.end--#',
                outer: '{tds}'
                //fn: trim,
            },
            {
                name: 'item.a',
                begin: '!--item.a.begin--!',
                end: '!--item.a.end--!',
                outer: ''
                //fn: trim,
            }
        ]);

        return samples;
    }


    return get;

});






    