/**
 * List/Samples 模块
 *
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
            },
            {
                name: 'items',
                begin: '#--row.items.begin--#',
                end: '#--row.items.end--#',
                outer: '{items}'
            },
            {
                name: 'row.checkbox',
                begin: '#--row.checkbox.begin--#',
                end: '#--row.checkbox.end--#',
                outer: '{checkbox}',
                //fn: trim,
            },
            {
                name: 'row.mustInput',
                begin: '#--row.mustInput.begin--#',
                end: '#--row.mustInput.end--#',
                outer: '{mustInput}',
                //fn: trim,
            },
            {
                name: 'row.text',
                begin: '#--row.text.begin--#',
                end: '#--row.text.end--#',
                outer: '{text}',
                //fn: trim,
            },
            {
                name: 'row.textarea',
                begin: '#--row.textarea.begin--#',
                end: '#--row.textarea.end--#',
                outer: '{textarea}',
            },
            {
                name: 'row.datatime',
                begin: '#--row.datatime.begin--#',
                end: '#--row.datatime.end--#',
                outer: '{datatime}',
            },
            {
                name: 'row.password',
                begin: '#--row.password.begin--#',
                end: '#--row.password.end--#',
                outer: '{password}',
                //fn: trim,
            },
            {
                name: 'row.f7',
                begin: '#--row.f7.begin--#',
                end: '#--row.f7.end--#',
                outer: '{f7}',
                //fn: trim,
            },

        ]);

        return samples;
    }


    return get;

});






    