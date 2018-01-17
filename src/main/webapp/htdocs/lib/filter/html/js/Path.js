

var Path = (function ($, MiniQuery, SMS) {

    var ul = document.getElementById('ul-path');

    var samples = $.String.getTemplates(ul.innerHTML, [
        {
            name: "group",
            begin: "<!--",
            end: "-->",
            outer: "{group}"
        },
        {
            name: "breadCrumbs",
            begin: "#--breadCrumbs.begin--#",
            end: " #--breadCrumbs.end--#",
            outer: "{breadCrumbs}"
        },
        {
            name: "selectedOption",
            begin: "#--selectedOption.begin--#",
            end: " #--selectedOption.end--#",
            outer: "{selectedOption}"
        },
        {
            name: "selected",
            begin: "#--selected.begin--#",
            end: " #--selected.end--#",
            outer: "{selected}"
        }
    ]);

    var list = [];
    var pathHtml = '';
    var searchObj = null;

    var timeOut = 400;

    var emitter = MiniQuery.Event.create();

    function remove(index) {

        var item = list.splice(index, 1)[0];
        fill();

        emitter.fire('closeTab', [index, item]);

    }

    $(ul).delegate('[name="remove"]', 'click', function () {

        $(this).parent().fadeOut(timeOut, function () {
            var index = this.getAttribute('data-index');
            console.log(index);
            remove(index);
        });


    });

    $(document).delegate('#search-order', 'keyup', function () {
        var input = this;

        if (window.event.keyCode == 13 || input.value !== 0 && !input.value) {

            if (input.value === 0 || input.value) {
                searchObj = {
                    'field': 'tid',
                    'value': input.value,
                    'type': 'like'
                };
            }
            else {
                searchObj = null;
            }

            emitter.fire('search-order');
        }

    });

    function getSearchObj() {
        return searchObj;
    }

    function findItems(type) {

        return $.Array.map(list, function (item, index) {
            if (item.type == type)
                return item;
            else
                return null;
        });
    }

    function fill() {


        ul.innerHTML = $.String.format(samples['group'], {
            breadCrumbs: pathHtml,
            selectedOption: $.Array.map(list, function (item, index) {


                return $.String.format(samples['selectedOption'], {
                    'name': item.name,
                    'index': index,
                    'selected': $.Array.map(item.displayItems, function (option, valueIndex) {

                        return $.String.format(samples['selected'], {
                            'name': option.name
                        })
                    }).join(',')
                })
            }).join('')
        });

    }

    function fillPath(paths) {

        pathHtml = $.Array.keep(paths, function (path, index) {
            return $.String.format(samples['breadCrumbs'], {
                name: path
            });
        }).join('');

        return pathHtml;

    }

    function init(paths) {

        ul.innerHTML = $.String.format(samples['group'], {
            breadCrumbs: fillPath(paths),
            selectedOption: ''
        });

    }

    function render(items) {

        var indexList = [];

        //去掉index重复的对象，时间范围是一种
        list = $.Array.map(items, function (item) {

            var i = $.Array.findIndex(indexList, function (indexItem) {
                return indexItem == item.index;
            });

            if (i == -1) {
                indexList.push(item.index);
                return item;
            }
            else {
                return null;
            }

        });

        fill();

    }

    return {
        init: init,
        render: render,
        getSearchObj: getSearchObj,
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }

})(jQuery, MiniQuery, SMS);

