


var FilterOptions = (function ($, MiniQuery, SMS) {

    var emitter = MiniQuery.Event.create();

    var filterBox = document.getElementById('filter-box-options');

    var samples = $.String.getTemplates(filterBox.innerHTML, [
        {
            name: "group",
            begin: "<!--",
            end: "-->",
            outer: "{group}"
        },
        {
            name: "button",
            begin: "#--button.begin--#",
            end: "#--button.end--#",
            outer: "{button}"
        },
        {
            name: "item",
            begin: "#--item.begin--#",
            end: "#--item.end--#",
            outer: "{item}"
        }
    ]);

    var list = [];

    var required = false;
    var isFilterOptionsHide = false;

    $('#config-filter').on('click', function () {

        $('#filter-box-options').toggleClass('hidden');

        if (!$('#filter-box-options').hasClass('hidden')) {
            emitter.fire('setting-options');
        }

        var btn = this;
        var index = btn.getAttribute('data-group-index');

        isFilterOptionsHide = false;

        if (!required) {


            $('#filter-box-setting').on('mouseover', function () {
                isFilterOptionsHide = false;
            });

            $('#filter-box-setting').on('mouseout', function () {
                isFilterOptionsHide = true;
            });

            $(document).on('click', function () {

                if (isFilterOptionsHide) {
                    $('#filter-box-options').addClass('hidden');
                }
            });

            required = true;
        }

    });

    $(filterBox).delegate('[data-option-index]', 'click', function () {

        $(this).toggleClass('item-selected');
    });

    function render(data) {

        list = data;

        filterBox.innerHTML = $.map(list, function (item, index) {

            return $.String.format(samples['group'], {
                'title': item.name,
                'item': $.map(item.items || [], function (item, optionIndex) {
                    return $.String.format(samples['item'], {
                        name: item.name,
                        'active-class': item.select ? 'item-selected' : '',
                        'index': index + '_' + optionIndex
                    })
                }).join(''),
                'button': ''
            });

        }).join('') + samples['button'];


    }


    return {
        render: render,
        on: function (name, fn) {
            emitter.on(name, fn);
        }
    }

})(jQuery, MiniQuery, SMS);