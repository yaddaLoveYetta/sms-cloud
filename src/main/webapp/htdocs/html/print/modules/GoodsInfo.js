

define('GoodsInfo', function (require, exports, module) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');

    var wrapper = document.getElementById('order-list');

    var samples = $.String.getTemplates(wrapper.innerHTML, [
        {
            name: 'order-detail',
            begin: '#--order-detail.begin--#',
            end: '#--order-detail.end--#',
            outer: ''
        },
        {
            name: 'order-info',
            begin: '#--order-info.begin--#',
            end: '#--order-info.end--#',
            outer: '{order-info}'
        },
        {
            name: 'order-number',
            begin: '#--order-number.begin--#',
            end: '#--order-number.end--#',
            outer: '{order-number}'
        }

    ]);


    var list = [];
    var tid;

    function fillStatusIcon() {


        return '';
    }

    $(wrapper).delegate('[id^=stock-changed-]', 'click', function () {

        var btn = this;

        var suffix = btn.getAttribute('data-group-index');


        $('#warehouse-area-' + suffix).toggleClass('modifying');

    });


    function render(orderIndex, data) {

        list = data;

        var container = document.getElementById('order-detail-' + orderIndex);

        container.innerHTML =

            $.String.format(samples['order-detail'], {

                'order-info': $.map(list, function (item, groupIndex) {

                    return $.map(item.items, function (good, index) {

                        return $.String.format(samples['order-info'], {
                            'index': index,
                            'group-index': groupIndex,
                            'order-index': orderIndex,
                            'img-path': good.picPath,
                            'title': good.title,
                            'item-number': good.itemNumber,
                            'sku-list': good.skuName,
                            'status-icon-list': fillStatusIcon(),
                            'price': good.price,
                            'amount': good.qty,
                            'stock-name': good.stockName,
                            'stock-position': good.spName,
                            'order-number': index > 0 ? '' :

                                $.String.format(samples['order-number'], {

                                    'number': item.items.length,
                                    'tid': item.tid,
                                    'create-time': item.createTime

                                })

                        })
                    }).join('')

                }).join('')
            });


        clearTimeout(tid);
        tid = setTimeout(function () { //窗口大小变化停止一定时间后才重新启动定时器

            if (+[1, ]) {//如果不是IE
                container.scrollIntoViewIfNeeded();
            }
            else {
                container.scrollIntoView(false);
            }


        }, 700);


    }

    return {
        render: render

    }

});