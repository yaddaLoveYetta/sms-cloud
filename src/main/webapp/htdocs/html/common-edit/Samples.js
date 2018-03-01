/**
 * @Title: Samples 模块
 * @author：yadda(silenceisok@163.com)
 * @date： 2018/2/2816:05
 */
define('Samples', function (require, module, exports) {

    var $ = require('$');

    function trim(s) {
        return s.replace(/\n/g, '').replace(/\s{2,}/g, ' ');
    }

    function get(div) {

        var samples = $.String.getTemplates(div.innerHTML, [

            {
                name: "ctrl",
                begin: '#--ctrl.begin--#',
                end: "#--ctrl.end--#",
            },
            {
                name: "items",
                begin: ' #--ctrl.items.begin--#',
                end: "#--ctrl.items.end--#",
                outer: '{items}'
            },
            {
                name: "item",
                begin: ' #--ctrl.items.item.begin--#',
                end: "#--ctrl.items.item.end--#",
                outer: '{item}'
            },
            {
                name: 'checkbox',
                begin: '#--checkbox.begin--#',
                end: '#--checkbox.end--#',
                outer: '{checkbox}'
                //fn: trim,
            },
            {
                name: 'mustInput',
                begin: '#--mustInput.begin--#',
                end: '#--mustInput.end--#',
                outer: '{mustInput}'
                //fn: trim,
            },
            {
                name: 'text',
                begin: '#--text.begin--#',
                end: '#--text.end--#',
                outer: '{text}'
                //fn: trim,
            },
            {
                name: 'textarea',
                begin: '#--textarea.begin--#',
                end: '#--textarea.end--#',
                outer: '{textarea}'
            },
            {
                name: 'datatime',
                begin: '#--datatime.begin--#',
                end: '#--datatime.end--#',
                outer: '{datatime}'
            },
            {
                name: 'password',
                begin: '#--password.begin--#',
                end: '#--password.end--#',
                outer: '{password}'
                //fn: trim,
            },
            {
                name: 'f7',
                begin: '#--f7.begin--#',
                end: '#--f7.end--#',
                outer: '{f7}'
                //fn: trim,
            },
            {
                name:'man_female',
                begin: '#--man_female.begin--#',
                end: '#--man_female.end--#',
                outer: '{man_female}'
            },
            {
                name:'yes_no',
                begin: '#--yes_no.begin--#',
                end: '#--yes_no.end--#',
                outer: '{yes_no}'
            }
        ]);

        return samples;
    }


    return get;

});