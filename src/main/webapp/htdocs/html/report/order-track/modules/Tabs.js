/**
 * Created by yadda on 2017/6/16.
 */

define('Tabs', function (require, module, exports) {

    var MiniQuery = require("MiniQuery");
    var emitter = MiniQuery.Event.create();
    var div = document.getElementById('tab-container');
    var hasbind = false;

    function bindEvents() {
        if (hasbind) {
            return;
        }

        $(div)
            .bind('easytabs:before', function (event, $clicked, $targetPanel, settings) {
                var classId = $clicked.eq(0).parent().attr('id');
                emitter.fire('easytabs:before', [classId]);
            })
            .bind('easytabs:midTransition', function (event, $clicked, $targetPanel, settings) {
                var classId = $clicked.eq(0).parent().attr('id');
                emitter.fire('easytabs:midTransition', [classId]);
            })
            .bind('easytabs:after', function (event, $clicked, $targetPanel, settings) {
                var classId = $clicked.eq(0).parent().attr('id');
                emitter.fire('easytabs:after', [classId]);
            });
    }


    function render() {

        $(div).easytabs({
            defaultTab: 'li:first-child'
        });

        bindEvents();
    }

    module.exports = {
        render: render,
        on: emitter.on.bind(emitter),
    }
});