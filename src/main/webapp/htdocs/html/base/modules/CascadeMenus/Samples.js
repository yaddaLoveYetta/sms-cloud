

/**
* 模板模块
* 
*/
define('CascadeMenus/Samples', function (require, module, exports) {

    var $ = require('$');


    function get(html) {

        var samples = $.String.getTemplates(html, [
            {
                name: 'group',
                begin: '<!--',
                end: '-->'
            },
            {
                name: 'item',
                begin: '#--item.begin--#',
                end: '#--item.end--#',
                outer: '{items}'
            }
        ]);

        return samples;
    }

    return get;

});






    