define("List", function (require, exports, module) {

    var $ = require('$');
    var SMS = require('SMS');
    var div = document.getElementById("div-list");
    var samples = require('/Samples')(div);

    var API = require('/API');

    function load(config, fn) {

        SMS.Tips.loading('数据加载中..');

        API.get({
            classId: config.classId,
            pageNo: config.pageNo,
            pageSize: config.pageSize,
            conditions: config.conditions,
            orderBy:config.orderBy
        }, function (data) {

            SMS.Tips.success('数据加载成功', 1500);

            var total = data.body.total;
            fn && fn(data, total);

        });
    }

    function getHtml(type, data) {

        if (data == null) {
            data = '';
        }

        if (type == 4) {
            // boolean 类型元数据
            data = data ? '是' : '否';
        }
        if (type == 3) {
            // 日期时间类型
            console.log(data instanceof Date);
        }
        if (type == 98) {
            // 处理男/女显示
            data = data ? '男' : '女';
        }
        return data;
    }

    function render(config, fn) {
        load({
            classId: config.classId,
            pageNo: config.pageNo,
            pageSize: config.pageSize,
            conditions: config.conditions,
            orderBy:config.orderBy

        }, function (data, total) {
            var headItems = data.head.items;
            var bodyItems = data.body.items;

            div.innerHTML = $.String.format(samples['table'], {

                'checkbox': data.checkbox ? samples['th.checkbox'] : '',

                'ths': $.Array.keep(headItems, function (field, index) {
                    return $.String.format(samples['th'], {
                        'index': index,
                        'th': field.text,
                        'width': field.width
                    });

                }).join(''),

                'trs': $.Array.keep(bodyItems, function (item, no) {

                    return $.String.format(samples['tr'], {
                        'index': no,
                        'disabled-class': item.disabled ? 'disabled' : '',

                        'checkbox': data.checkbox ? $.String.format(samples['td.checkbox'], {
                            'index': no,

                        }) : '',

                        'tds': $.Array.keep(item.items, function (item, index) {//

                            var field = headItems[index];

                            return $.String.format(samples['td'], {
                                'index': index,
                                'td': getHtml(field.type, item.value),
                            });

                        }).join('')

                    });

                }).join('')
            });
            fn && fn(total, config.pageSize);
        });

    }

    return {
        render: render
    }
});