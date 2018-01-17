//标签模块
define('Tabs', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var emitter = MiniQuery.Event.create();

    var tabs = null;

    function init(index) {

        current = index || 0;

        tabs = SMS.Tabs.create({
            container: '#li-tabs',
            selector: '>div',
            current: index,
            event: 'click',
            activedClass: 'hover',
            change: function (index) {
                console.log(index);
            }
        });

        tabs.on('change', function (index) {
            console.log('on: ', index);
        });

    }

    function current() {
        return tabs.getActivedIndex();
    }


    return {
        init: init,
        current: current,
        on: emitter.on.bind(emitter),
    };
});



