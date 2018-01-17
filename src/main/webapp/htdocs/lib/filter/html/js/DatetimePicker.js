
var DatetimePicker = (function ($, MiniQuery, SMS) {

    function bindEvents() {

        SMS.use('DateTimePicker', function (DateTimePicker) {

            var dtp = new DateTimePicker('#startTime', {
                format: 'yyyy-mm-dd hh:ii',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                startView: 'month',
                minView: 'hour',
            });


        });

        SMS.use('DateTimePicker', function (DateTimePicker) {

            var dtp = new DateTimePicker('#endTime', {
                format: 'yyyy-mm-dd hh:ii',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                startView: 'month',
                minView: 'hour',
            });


        });


        $('#date-range-confirm').on('click', function () {

            $('#startTime').val('');
            $('#endTime').val('');

        });
    }

    function render() {
        bindEvents();
    }

    return {
        render: render
    }

})(jQuery, MiniQuery, SMS);

