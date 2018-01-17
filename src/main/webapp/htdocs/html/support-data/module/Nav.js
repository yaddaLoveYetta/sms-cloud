

//面包屑模块
define('Nav', function (require, module, exports) {
    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var KERP = require('KERP');
    var CascadeNavigator = KERP.require('CascadeNavigator');
    var API = KERP.require('API');

    var emitter = MiniQuery.Event.create();

    var tree = {};



    var Navigator;

    function load() {
        var api = new API('/bd/assistitem', {
            action: 'findTypeList'
        });

        api.on({
            success: function (data, json) {
                tree = getTree(data);

                Navigator = new CascadeNavigator({
                    data: tree,
                    containers: {
                        path: '#div-cascade-path',
                        menus: '#div-cascade-menus'
                    },
                    fields: {
                        text: 'name',
                        value: 'typeID',
                        child: 'items'
                    }
                });

                Navigator.on({
                    'change': function (list) {
                        emitter.fire('nav.change', [{
                            'list': list
                        }]);
                    }
                });

                Navigator.render();
            }
        });

        //KERP.API.get('/bd/assistitem', {
        //    'action': 'findTypeList'
        //}).success(function (data, json) {
        //    tree = getTree(data);

        //    Navigator = new CascadeNavigator({
        //        data: tree,
        //        containers: {
        //            path: '#div-cascade-path',
        //            menus: '#div-cascade-menus'
        //        },
        //        fields: {
        //            text: 'name',
        //            value: 'typeID',
        //            child: 'items'
        //        }
        //    });

        //    Navigator.on({
        //        'change': function (list) {
        //            emitter.fire('nav.change', [{
        //                'list': list
        //            }]);
        //        }
        //    });

        //    Navigator.render();
        //});
    }


    function getTree(data) {
        $.Object.extend(data, { name: '全部' });
        return data;
    }

    return {
        load: load,
        on: emitter.on.bind(emitter),
    }
});