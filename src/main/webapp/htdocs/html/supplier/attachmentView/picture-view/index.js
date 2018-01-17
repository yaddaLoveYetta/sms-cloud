/**
 * Created by yadda on 2017/9/18.
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');

    var file = MiniQuery.Url.getQueryString(window.location.href, 'file');

    if (!file) {
        return;
    }

    $('#img').attr('src', file);

})();