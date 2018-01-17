define("List", function (require, exports, module) {

    var $ = require('$');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var div = document.getElementById("div-list");

    var getRecordData = function (config, fn) {

        var api = new API("report/orderCount");

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

        getRecordData(config, function (list, count) {

            div.innerHTML = $.String.format(samples.table, {

                'trs': $.Array.keep(list, function (item, no) {

                    return $.String.format(samples.tr, {

                        'index': no,
                        'tds': $.String.format(samples.td, {
                            'index': no + 1,
                            'materialNumber': item.materialNumber || "",
                            'materialName': item.materialName || "",
                            'model': item.model || "",
                            'unit': item.unit || "",
                            'orderQty': item.orderQty || "",
                            'outStockQty': item.outStockQty || "",
                            'returnQty': item.returnQty || "",
                            'stockQty': item.stockQty || "",
                        })
                    });
                }).join('')
            });
            //sumTdTotal();
            fn && fn(count, config.pageSize);
        });
    }

    function sumTdTotal() {
        // 求和运算
        var needtotalTds = $("#div-list table>tfoot td[needtotal='true']");
        var tbodytrLen = $("#div-list table>tbody>tr").length;
        if (needtotalTds.length <= 0 || tbodytrLen === 0) {
            $("#div-list table>tfoot").hide();
        } else {
            needtotalTds.each(function (index, item) {
                var key = $(item).attr("totalkey");
                var totalArray = $("#div-list table>tbody td[key='" + key + "']").map(function () {
                    return $(this).html();
                }).get();
                var totalValue = $.Array.sum(totalArray);
                $(item).html(totalValue.toFixed(2));
            });
        }
    }

    return {
        render: render
    }
});