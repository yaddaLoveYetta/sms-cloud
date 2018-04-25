/**
 * @Title:
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/4/25 10:15
 */
;(function () {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var Tree = require('Tree');
    var List = require('List');


    Tree.render();

    Tree.on('messageTypeChange', function (type) {
        List.render(type);
    });

})();