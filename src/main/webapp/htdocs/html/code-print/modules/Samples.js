

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
                name: 'codes',
                begin: '<!--',
                end: '-->',
                //fn: trim,
            },
            {
                name: 'code',
                begin: '#--code.begin--#',
                end: '#--code.end--#',
                outer: '{code}',
            }

        ]);

        return samples;
    }



    return get;

});






    