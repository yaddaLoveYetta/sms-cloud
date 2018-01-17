

define('type', function (require, module, exports) {
    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var KERP = require('KERP');
    var API = KERP.require('API');

    var p = document.getElementById('p-types');
    var list = [];

    var samples = $.String.getTemplates(p.innerHTML, [
        {
            name: 'select',
            begin: '<!--',
            end: '-->'
        },
        {
            name: 'option',
            begin: '#--options.begin--#',
            end: '#--options.end--#',
            outer: '{options}'
        }
    ]);

    function load(fn) {
        
        var api = new API('bd/assistitem', {
            action: 'findTypeList'
        });

        api.get();

        api.on({
            success: function (data, json) {
                list = data.items;
                fn() && fn(data.items);
            }
        });

        //KERP.API.get('bd/assistitem', {'action': 'findTypeList'}, function (data, json) {
        //    list = data.items;
        //    fn() && fn(data.items);
        //}, function (code, msg, json) {

        //}, function () {

        //});
    }

    function render() {
        
        load(function () {
            p.innerHTML = $.String.format(samples.select, {
                'options': $.Array.keep(list, function (item, index) {
                    return $.String.format(samples.option, {
                        'typeID': item.typeID,
                        'typeName': item.name
                    });
                }).join('')
            });
        });
    }

    return {
        render: render
    }
});