


define('OrderList', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var wrapper = document.getElementById('order-list');

    var samples = $.String.getTemplates(wrapper.innerHTML, [
        {
            name: 'group',
            begin: '<!--',
            end: '-->'
        },
        {
            name: 'thead',
            begin: '#--thead.begin--#',
            end: '#--thead.end--#',
            outer: ''
        },
        {
            name: 'gift',
            begin: '#--gift.begin--#',
            end: '#--gift.end--#',
            outer: ''
        },
        {
            name: 'absence',
            begin: '#--absence.begin--#',
            end: '#--absence.end--#',
            outer: ''
        },
        {
            name: 'refund',
            begin: '#--refund.begin--#',
            end: '#--refund.end--#',
            outer: ''
        },
        {
            name: 'not-arrive',
            begin: '#--not-arrive.begin--#',
            end: '#--not-arrive.end--#',
            outer: ''
        },
        {
            name: 'overweight',
            begin: '#--overweight.begin--#',
            end: '#--overweight.end--#',
            outer: ''
        },
        {
            name: 'join',
            begin: '#--join.begin--#',
            end: '#--join.end--#',
            outer: ''
        },
        {
            name: 'order-detail',
            begin: '#--order-detail.begin--#',
            end: '#--order-detail.end--#',
            outer: ''
        }

    ]);
    samples['order-detail'] = '';


    var emitter = MiniQuery.Event.create();

    var list = [];

    var required = false;//判断是否首次调用render()方法，是否绑定时间以及可让事件绑定放到render()方法后执行

    var no = '';

    var tid;

    var memory = {
        no: null,
        index: null,
        type: null
    };

    var addressType = 0;
    var logisticsType = 1;

    function bindEvents() {

        if (!required) {

            $('[id^=address-picker-]').each(function (index, ele) {

                var item = list[ele.getAttribute('data-index')];

                SMS.CascadePicker.create({
                    container: '#' + ele.id,
                    selectedValues: [item.deliveryStateID, item.deliveryCityID, item.deliveryDistrictID],
                    defaultTexts: ['--请选择省份--', '--请选择城市--', '--请选择地区--'],
                    hideEmpty: true,
                    data: 'data/address/array.simple.js',
                    varname: '__AddressData__',
                    fields: {
                        value: 0,
                        text: 1,
                        child: 2,
                    },
                    change: function (level, index) {
                        console.log(level, index);
                        var items = this.getSelectedItems();
                        console.dir(items);
                    }
                });

            });

            function fillEditRegion(index, type) {

                if (!(memory.no == no && memory.index == index && memory.type == type)) {

                    switch (memory.type) {
                        case addressType:
                            $('#address-block-' + memory.index).removeClass('modifying')
                            $('#address-info-' + memory.index).addClass('hidden');
                            break;

                        case logisticsType:
                            $('#logistics-block-' + memory.index).removeClass('modifying')
                            $('#loginstics-info-' + memory.index).addClass('hidden');
                            break;
                    }

                    memory.no = no;
                    memory.index = index;
                    memory.type = type;

                }
            }

            //修改地址
            $('[id^=modify-address-]').on('click', function () {

                var btn = this;
                var index = btn.getAttribute('data-index');

                fillEditRegion(index, addressType);

                $('#address-block-' + index).toggleClass('modifying');

                if ($('#address-block-' + index).hasClass('modifying')) {

                    $('#address-info-' + index).removeClass('hidden');


                    clearTimeout(tid);
                    tid = setTimeout(function () { //窗口大小变化停止一定时间后才重新启动定时器

                        if (+[1, ]) {//如果不是IE
                            document.getElementById('address-info-' + index).scrollIntoViewIfNeeded();
                        }
                        else {
                            document.getElementById('address-info-' + index).scrollIntoView(false);
                        }


                    }, 700);
                }
                else {

                    $('#address-info-' + index).addClass('hidden');
                }

            });


            //修改物理信息
            $('[id^=modify-logistics-info-]').on('click', function () {

                var btn = this;
                var index = btn.getAttribute('data-index');

                fillEditRegion(index, logisticsType);

                $('#logistics-block-' + index).toggleClass('modifying');

                if ($('#logistics-block-' + index).hasClass('modifying')) {

                    $('#loginstics-info-' + index).removeClass('hidden');

                    clearTimeout(tid);
                    tid = setTimeout(function () { //窗口大小变化停止一定时间后才重新启动定时器

                        if (+[1, ]) {//如果不是IE
                            document.getElementById('loginstics-info-' + index).scrollIntoViewIfNeeded();
                        }
                        else {
                            document.getElementById('loginstics-info-' + index).scrollIntoView(false);
                        }


                    }, 700);
                }
                else {

                    $('#loginstics-info-' + index).addClass('hidden');
                }



            });


            $(wrapper).delegate('#check-all', 'click', function () {

                var btn = this;

                $(btn).toggleClass('checked');
            });

            $(wrapper).delegate('[id^=check-one]', 'click', function () {

                var btn = this;

                $(btn).toggleClass('checked');
            });

            $(wrapper).delegate('[id^=spread-btn]', 'click', function () {

                var btn = this;
                var index = btn.getAttribute('data-index');

                $('#order-content-' + index).toggleClass('spread');

                if ($('#order-content-' + index).hasClass('spread')) {

                    emitter.fire('spread-detail', [index]);
                }
                else {
                    document.getElementById('order-detail-' + index).innerHTML = '';
                }


            });

            required = true;
        }
    }


    function fillOrderStatusIcon(item) {

        var icons = '';

        if (item.isMerge) {
            icons += samples['join'];
        }

        return icons;
    }

    function fillLogisticsStatusIcon(item) {

        var icons = '';

        if (item.expressNotArrive) {
            icons += samples['not-arrive'];
        }

        return icons;
    }

    function render(data, _no) {

        list = data;

        no = _no;

        wrapper.innerHTML = samples['thead'] + $.map(list, function (item, index) {

            return $.String.format(
                samples['group'], {
                    'unique-code': item.uniqueCode,
                    'create-date-time': item.createTime,
                    'shop-name': item.shopName,
                    'buyer-nick': item.buyerNick,
                    'print-batch-number': item.lotNumber,
                    'print-serial-number': item.serialNumber,
                    'logistics-nerver-class': item.expressPrint ? 'nerver' : '',
                    'distribution-nerver-class': item.preparePrint ? 'nerver' : '',
                    'deliver-nerver-class': item.consignPrint ? 'nerver' : '',
                    'delivery-address': item.deliveryAddress,
                    'receiver': item.receiver,
                    'receiver-mobile': item.receiverMobile + ',' + item.receiverPhone,
                    'logistics-company-name': item.logisticsCompanyName,
                    'logistics-bill-no': item.logisticsBillNo,
                    'order-status-icon-list': fillOrderStatusIcon(item),
                    'logistics-status-icon-list': fillLogisticsStatusIcon(item),
                    'buyer-message': item.buyerMessage,
                    'vendor-remark': item.outerMemo,
                    'index': index
                }
            );
        }).join('');

        bindEvents();


    }

    return {
        render: render,
        on: function (name, fn) {
            emitter.on(name, fn);
        }

    }

});