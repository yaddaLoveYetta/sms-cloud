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
                end: '-->',
                //fn: trim,
            },
            {
                name: 'th.checkbox',
                begin: '#--th.checkbox.begin--#',
                end: '#--th.checkbox.end--#',
                outer: '{checkbox}',
                //fn: trim,
            },
            {
                name: 'th',
                begin: '#--th.begin--#',
                end: '#--th.end--#',
                outer: '{ths}',
                //fn: trim,
            },
            {
                name: 'tr',
                begin: '#--tr.begin--#',
                end: '#--tr.end--#',
                outer: '{trs}',
                //fn: trim,
            },
            {
                name: 'td.checkbox',
                begin: '#--td.checkbox.begin--#',
                end: '#--td.checkbox.end--#',
                outer: '{checkbox}',
                //fn: trim,
            },
            {
                name: 'td',
                begin: '#--td.begin--#',
                end: '#--td.end--#',
                outer: '{tds}',
                //fn: trim,
            },
            {
                name: 'td.value',
                begin:'#--td.value.begin--#',
                end:' #--td.value.end--#',
                outer:'{td}'
            },
            {
                name: 'item.a',
                begin: '!--item.a.begin--!',
                end: '!--item.a.end--!',
                outer: '',
                //fn: trim,
            },
            {
                name: 'item.table',
                begin: '!--item.table.begin--!',
                end: '!--item.table.end--!',
                outer: '{item_table}',
            },
            {
                name: 'item.table.tr',
                begin: '!--item.table.tr.begin--!',
                end: '!--item.table.tr.end--!',
                outer: '{item_table_tr}',
            }, {
                name: 'item.table.tr.td',
                begin: ' !--item.table.tr.td.begin--!',
                end: '!--item.table.tr.td.end--!',
                outer: '{item_table_tr_td}',
            },
            {
                name: 'trtotal',
                begin: '#--tr.total.begin--#',
                end: '#--tr.total.end--#',
                outer: '{trtotals}',
                //fn: trim,
            },
            {
                name: 'tdtotal',
                begin: '#--td.total.begin--#',
                end: '#--td.total.end--#',
                outer: '{tdtotals}'
                //fn: trim,
            },
            {
                name: 'emptytd',
                begin: '#--td.empty.begin--#',
                end: '#--td.empty.end--#',
                outer: '{emptys}'
                //fn: trim,
            }

        ]);

        return samples;
    }


    return get;

});






    