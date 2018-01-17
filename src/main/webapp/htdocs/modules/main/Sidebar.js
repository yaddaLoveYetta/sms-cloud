


/**
* 侧边菜单栏模块
*/
define('Sidebar', function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    

    var ul = document.getElementById('ul-sidebar');
    var div = ul.parentNode;

    var emitter = MiniQuery.Event.create();
    var tabs = null;
    var list = [];
    var activedItem = null;
    var tid = null;


    //函数 fixed(y)
    var fixed = (function () {

        var hasFixed = false; //指示是否已经 fixed

        var max = 50;   //初始时的 top 值，也是最大的
        var min = -10;  //允许的最小的 top 值，即 fixed 后的 top 值

        //fixed = 
        return function (y) {

            if (typeof y == 'boolean') { // 重载 fixed(true|false)
                $(div).toggleClass('side-bar-fixed', y);
                return;
            }

            //y 是滚动实际数值

            var top = max - y; //要设置的 top 值

            //已经 fixed 了，并且要设置的 top 值比最小的还要小，则忽略。
            //避免重复去设置同一个值。
            if (hasFixed && top <= min) {
                return;
            }

            div.style.top = Math.max(top, min) + 'px';
            hasFixed = top <= min; //要设置的 top 值比最小的还要小，则表示已经 fixed。
        };

    })();




    function fill() {

        SMS.Template.fill(ul, list, function (item, index) {

            return {
                'index': index,
                'name': item.name,
                'icon': item.icon,
            };

        });

    }


    function bindEvents() {

        tabs = SMS.Tabs.create({

            container: ul,
            selector: '>li',
            indexKey: 'data-index',
            current: null,
            event: 'mouseover',
            activedClass: 'hover',
            change: function (index, item) { //这里的，如果当前项是高亮，再次进入时不会触发
                //console.log(index);
            }
        });

        var tid = null;


        //每次都会触发，不管当前项是否高亮
        tabs.on('event', function (index, item) {

            clearTimeout(tid);

            tid = setTimeout(function () {

                var item = list[index];

                emitter.fire('item.mouseover', [{
                    index: index,
                    data: item.items,
                    reversed: index > 0
                }]);

            }, 200);
        });



        $(ul).hover(function () {
            emitter.fire('mouseover');
        }, function () {
            emitter.fire('mouseout');
        });

        $(hiden).on('click' ,function () {
            $(div).toggleClass('side-bar-hiden');
            emitter.fire('hiden');
        })
    }





    function active(item) {

        if (!item) { //active()
            item = activedItem;
        }

        if (!item) {
            return;
        }

        activedItem = item;
        tabs.active(item.group);
    }

    function activeAfter(delay) {

        if (!activedItem) {
            return;
        }

        tid = setTimeout(function () {
            active(activedItem);
        }, delay);
    }


    function cancelActive() {
        clearTimeout(tid);
    }






    function render(data) {

        list = data;

        fill();
        bindEvents();

    }





    return {
        render: render,
        active: active,
        activeAfter: activeAfter,
        cancelActive: cancelActive,
        fixed: fixed,
        on: emitter.on.bind(emitter),
    };

});





    