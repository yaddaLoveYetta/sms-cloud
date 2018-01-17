

(function ($, MiniQuery, SMS, FilterData, Path, Filter, FilterOptions, TimeZone, DatetimePicker, Notification) {



    FilterData.loadPath(function (data) {
        Path.init(data);
    });

    FilterData.load(function (data) {
        Filter.render(data);
    });

    FilterData.loadNotification(function (data) {
        Notification.render(data);
    });

    DatetimePicker.render();


    FilterOptions.on({
        'setting-options': function () {
            FilterData.loadOptions(function (data) {
                FilterOptions.render(data)
            });
        }
    });

    Filter.on({
        'optionChanged': function (selectedItems, type) {

            if (!type) {
                Path.render(selectedItems);

            }

        }
    });

})(jQuery, MiniQuery, SMS, FilterData, Path, Filter, FilterOptions, TimeZone, DatetimePicker, Notification);