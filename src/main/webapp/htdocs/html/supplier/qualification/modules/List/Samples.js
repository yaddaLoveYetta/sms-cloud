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
                name: 'all',
                begin: '<!--',
                end: '-->',
                fn: trim
            },
            {
                name: 'typeCaption',
                begin: '#--type.caption.begin--#',
                end: '#--type.caption.end--#',
                outer: '{typeCaption}',
                //fn: trim,
            },
            {
                name: 'typeList',
                begin: '#--type.list.begin--#',
                end: '#--type.list.end--#',
                outer: '{typeList}',
                //fn: trim,
            },
            {
                name: 'typeListItem',
                begin: '#--type.list.item.begin--#',
                end: '#--type.list.item.end--#',
                outer: '{typeListItem}',
                //fn: trim,
            },
            {
                name: 'qualificationList',
                begin: '#--qualification.list.begin--#',
                end: '#--qualification.list.end--#',
                outer: '{qualificationList}',
                //fn: trim,
            },
            {
                name: 'qualificationListItem',
                begin: '#--qualification.list.item.begin--#',
                end: '#--qualification.list.item.end--#',
                outer: '{qualificationListItem}',
                //fn: trim,
            }
        ]);

        return samples;
    }


    return get;

});






    