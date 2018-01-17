

define('DatetimePicker', function(require, module, exports) {


    function render() {

        SMS.use('DateTimePicker', function(DateTimePicker) {

            var startTime = new DateTimePicker('#bd-FCreateTime', {
                format: 'yyyy-mm-dd hh:ii:ss',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                startView: 'month',
                minView: 'hour',
            });

            var endTime = new DateTimePicker('#bd-FModifiedTime', {
                format: 'yyyy-mm-dd hh:ii:ss',
                autoclose: true,
                todayBtn: true,
                todayHighlight: true,
                startView: 'month',
                minView: 'hour',
            });
        });

    }


    return {
        render: render
    };

});

