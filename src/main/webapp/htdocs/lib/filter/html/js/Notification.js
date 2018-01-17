

var Notification = (function ($, MiniQuery, SMS) {

    var wrapper = document.getElementById('notification-list');

    var list = [];

    var samples = $.String.getTemplates(wrapper.innerHTML, [
        {
            name: "group",
            begin: "<!--",
            end: "-->",
            outer: "{group}"
        }
    ]);



    $(wrapper).delegate('[data-item-index]', 'click', function () {

        var btn = this;

        var index = btn.getAttribute('data-item-index');

        $.each(list, function (i, item) {
            item.select = 0;
        });

        list[index].select = 1;

        render(list);

    });

    function render(data) {

        list = data;

        wrapper.innerHTML = $.map(list, function (item, index) {
            return $.String.format(samples['group'], {
                'index': index,
                'amount': list.length,
                'first-class': index == 0 ? 'first' : '',
                'selected-class': item.select ? 'selected' : '',
                'number': item.defaultValue,
                'name': item.name
            });
        }).join('');

    }

    return {
        render: render
    }

})(jQuery, MiniQuery, SMS);
