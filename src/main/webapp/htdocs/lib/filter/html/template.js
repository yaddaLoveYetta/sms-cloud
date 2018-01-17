define('Filter', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var filterEmitter = MiniQuery.Event.create();

    function render(config) {

        //var html = @@html-area;

        document.getElementById(config.id).innerHTML = html;

        var search = config.search;
        if(search){
            filterEmitter.on('search',search);
        }

        //@@modules-area
    }

    

    return {
        render: render,
        on: function (name, fn) {
            filterEmitter.on(name, fn);
        }
    }

});