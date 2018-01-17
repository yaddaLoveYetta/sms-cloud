define('DatetimePicker', function (require, module, exports) {

    function render() {

        SMS.use('DateTimePicker', function (DateTimePicker) {

            var beginDate = new DateTimePicker('#beginDate', {
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                startView: 'month',
                minView: 2,
            });

            var endDate = new DateTimePicker('#endDate', {
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                startView: 'month',
                minView: 2,
            });

        });

        $('#beginDate').val($.Date.format(new Date(), 'yyyy-MM-dd'));

    }

    return {
        render: render
    };

});

