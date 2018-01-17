



define('Pager', function (require, module, exports) {
    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var KERP = require('KERP');

    var div = document.getElementById('div-pager-simple');

    function render(config) {

        config = $.Object.extend({}, config, {
            container: '#div-pager-simple',
            current: 1,
            hideIfLessThen: 2
        });

        KERP.SimplePager.create(config);
    }


    return {
        render: render
    };
});