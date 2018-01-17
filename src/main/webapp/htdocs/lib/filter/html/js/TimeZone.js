


var TimeZone = (function ($, MiniQuery, SMS) {

    var wrapper = document.getElementById('ul-filter');

    //为设置时间，方案名输入框设id
    var panelId = '';
    var inputId1 = 'input-id-' + $.String.random();
    var inputId2 = 'input-id-' + $.String.random();
    var inputIdName = 'input-id-' + $.String.random();
    var selectId1 = 'select-id-' + $.String.random();
    var selectId2 = 'select-id-' + $.String.random();
    var btnId = 'btn-id-' + $.String.random();



    var samples = $.String.getTemplates(wrapper.innerHTML, [

        {
            name: 'created-area',
            begin: '#--created-area.begin--#',
            end: '#--created-area.end--#'
        },
        {
            name: 'created-zone',
            begin: '#--created-zone.begin--#',
            end: '#--created-zone.end--#',
            outer: "{created-zone}"
        }
    ]);


    $(wrapper).delegate('[data-tag-index]', 'click', function () {

        var tab = this;
        $(tab).hide();

    });

    $(wrapper).delegate('#' + btnId, 'click', function () {
        console.log(btnId + 'click');
        $('#' + panelId).addClass('hidden');

    });

    function createTimePidcker(id) {

        SMS.use('DateTimePicker', function (DateTimePicker) {

            var dtp = new DateTimePicker('#' + id, {
                format: 'hh:ii',
                autoclose: true,
                startView: 'day',
                minView: 'hour'
            });


        });
    }

    function render(config) {

        panelId = config.id;

        $('#' + config.id).toggleClass('hidden');

        if (!$('#' + config.id).hasClass('hidden')) {
            document.getElementById(config.id).innerHTML = $.String.format(samples['created-area'], {

                'created-zone': $.map(config.data, function (item, index) {

                    if (item.value) {
                        return $.String.format(samples['created-zone'], {
                            'index': index,
                            'name': item.name
                        });
                    }
                    else {
                        return null;
                    }

                }).join(''),
                'input-id-1': inputId1,
                'input-id-2': inputId2,
                'input-id-name': inputIdName,
                'select-id-1': selectId1,
                'select-id-2': selectId2,
                'btn-id': btnId

            });

            createTimePidcker(inputId1);
            createTimePidcker(inputId2);
        }


    }

    return {
        render: render
    }

})(jQuery, MiniQuery, SMS);
