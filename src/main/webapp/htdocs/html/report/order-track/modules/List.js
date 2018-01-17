define("List", function (require, exports, module) {

    var $ = require('$');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var div = document.getElementById("div-list");
    var heads = {
        "2020": [
            {key: 'orderNumber', value: '采购订单号', width: 120},
            {key: 'orderSeq', value: '采购订单行号', width: 40},
            {key: 'number', value: '发货单号', width: 150},
            {key: 'sendSeq', value: '发货单行号', width: 40},
            {key: 'date', value: '发货日期', width: 80},
            {key: 'material_NmbName', value: '物料编号', width: 80},
            {key: 'material_DspName', value: '物料名称', width: 120},
            {key: 'unit_DspName', value: '单位', width: 80},
            {key: 'actualQty', value: '发货数量', width: 80},
            {key: 'qty', value: '订单数量', width: 80},
            /* {key: 'noneOutStockQty', value: '未发货数量',width:80},*/
            {key: 'logistics', value: '物流公司', width: 80},
            {key: 'logisticsNo', value: '物流单号', width: 80}],
        "2021": [
            {key: 'orderNumber', value: '采购订单号', width: 120},
            {key: 'orderSeq', value: '订单行号', width: 40},
            {key: 'number', value: '收货单号', width: 80},
            {key: 'seq', value: '收货单行号', width: 40},
            {key: 'bizDate', value: '收货日期', width: 80},
            {key: 'material_NmbName', value: '物料编码', width: 80},
            {key: 'material_DspName', value: '物料名称', width: 80},
            {key: 'unit_DspName', value: '单位', width: 80},
            {key: 'actualQty', value: '收货数量', width: 80},],
        "2022": [
            {key: 'orderNumber', value: '采购订单号', width: 120},
            {key: 'orderSeq', value: '订单行号', width: 40},
            {key: 'number', value: '入库单号', width: 80},
            {key: 'seq', value: '入库单行号', width: 40},
            {key: 'bizDate', value: '收货日期', width: 80},
            {key: 'material_NmbName', value: '物料编码', width: 80},
            {key: 'material_DspName', value: '物料名称', width: 80},
            {key: 'unit_DspName', value: '单位', width: 80},
            {key: 'actualQty', value: '入库数量', width: 80},],
        "2023": [
            {key: 'orderNumber', value: '采购订单号', width: 120},
            {key: 'orderSeq', value: '订单行号', width: 80},
            {key: 'number', value: '退货单号', width: 80},
            {key: 'bizDate', value: '退货日期', width: 80},
            {key: 'material_NmbName', value: '物料编码', width: 80},
            {key: 'material_DspName', value: '物料名称', width: 80},
            {key: 'unit_DspName', value: '单位', width: 80},
            {key: 'returnQty', value: '退货数量', width: 80},
            {key: 'qty', value: '订单数量', width: 80},],
    };

    var getRecordData = function (config, fn) {

        var api = new API("report/traceQuery");

        SMS.Tips.loading('数据加载中...');

        api.post(config);

        api.on({
            'success': function (data, json) {
                SMS.Tips.success('数据加载成功', 1500);
                fn && fn(data.list, data.count);
            },
            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (数据加载异常: {1})', msg, code);
                SMS.Tips.error(s);
            },
            'error': function () {
                SMS.Tips.error('数据加载异常');
            }
        });
    };

    var samples = $.String.getTemplates(div.innerHTML, [{
        name: "table",
        begin: "<!--",
        end: "-->"
    }, {
        name: 'th',
        begin: ' #--th.begin--#',
        end: ' #--th.end--#',
        outer: '{ths}'
    }, {
        name: 'tr',
        begin: '#--tr.begin--#',
        end: '#--tr.end--#',
        outer: '{trs}'
    }, {
        name: 'td',
        begin: '#--td.begin--#',
        end: '#--td.end--#',
        outer: '{tds}'
    }]);

    function render(config, fn) {

        div.innerHTML = '';// 先清空，tabs切换后数据未加载完成，引起歧义
        getRecordData(config, function (list, total) {

            div.innerHTML = $.String.format(samples.table, {

                'ths': $.Array.keep(heads[config.classId], function (item, no) {
                    return $.String.format(samples.th, {
                        th: item.value,
                        width: (item.width || 80) + 'px',
                    });
                }).join(''),

                'trs': $.Array.keep(list, function (rowData, no) {

                    return $.String.format(samples.tr, {

                        'index': no,
                        'tds': $.Array.keep(heads[config.classId], function (item, no) {

                            return $.String.format(samples.td, {
                                'td': rowData[item.key] || '',
                            });

                        }).join(''),
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