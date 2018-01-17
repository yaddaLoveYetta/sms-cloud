



define('Operation', function (require, module, exports) {
    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var KERP = require('KERP');
    var emitter = MiniQuery.Event.create();

    var hasBind = false;


    function render() {
        if (hasBind) {
            return;
        }
        bindEvents();

    }


    function bindEvents() {

        $('#btn-add').bind('click', function () {
            var btn = this;

            KERP.use('Dialog', function (Dialog) {
                var dialog = new Dialog({
                    id: 'add-dialog',
                    title: '新增',
                    url: './dialog/support-data/add/index.html',
                    width: 300,
                    height: 250,

                    onremove: function () {
                        emitter.fire('dialog.remove');
                    }
                });

                dialog.showModal();
            });
        });

        $('#btn-delete').bind('click', function () {
            emitter.fire('delete.click');
        });

        $('#btn-refresh').bind('click', function () {
            emitter.fire('refresh.click');
        });

    }


    return {
        render: render,
        on: emitter.on.bind(emitter),
    }
});
