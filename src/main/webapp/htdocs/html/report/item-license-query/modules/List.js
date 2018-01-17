define("List", function (require, exports, module) {

    var $ = require('$');
    var SMS = require('SMS');
    var API = SMS.require('API');
    var divItemLicense = document.getElementById("div-list");

    var divSupplierLicense = document.getElementById("div-list-supplier-license");

    var getRecordData = function (config, fn) {

        var conditions = {};
        conditions = $.extend({}, config.pageNo, config.pageSize, config.conditions);


        var api = new API("report/getItemLicense");
        SMS.Tips.loading('数据加载中...');

        api.post(conditions);

        api.on({
            'success': function (data, json) {
                SMS.Tips.success('数据加载成功', 1000);
                fn && fn(data.list, data.supplierLicense, data.count);
            },
            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (数据加载异常: {1})', msg, code);
                SMS.Tips.error(s, 1000);
            },
            'error': function () {
                SMS.Tips.error('数据加载异常', 1000);
            }
        });
    };

    var samplesItem = $.String.getTemplates(divItemLicense.innerHTML, [{
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

    var samplesSupplier = $.String.getTemplates(divSupplierLicense.innerHTML, [{
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

        getRecordData(config, function (list, supplierLicense, total) {

            // 物料证件
            divItemLicense.innerHTML = $.String.format(samplesItem.table, {

                'trs': $.Array.keep(list, function (item, no) {

                    return $.String.format(samplesItem.tr, {

                        'index': no,
                        'expired': item.effective < 0 ? 'expired' : item.remainDays < 0 ? 'soon-expired' : '',
                        'tds': $.String.format(samplesItem.td, {
                            'index': no,
                            'itemNumber': item.itemNumber || "",
                            'itemName': item.itemName || "",
                            'itemModel': item.itemModel || "",
                            'factory': item.factory || "",
                            'idNumber': item.idNumber || "",
                            'idName': item.idName || "",
                            'idType': item.idType || "",
                            'authOrg': item.authOrg || "",
                            'agent': item.agent || "",
                            'supplier': item.supplier || 0,
                            'beginDate': item.beginDate || 0,
                            'endDate': item.endDate || 0,
                            'effective': item.effective || 0,
                        })
                    });
                }).join('')
            });

            // 供应商资质
            divSupplierLicense.innerHTML = $.String.format(samplesSupplier.table, {

                'trs': $.Array.keep(supplierLicense, function (item, no) {

                    return $.String.format(samplesSupplier.tr, {

                        'index': no,
                        'expired': item.effective < 0 ? 'expired' : item.remainDays < 0 ? 'soon-expired' : '',
                        'tds': $.String.format(samplesSupplier.td, {
                            'index': no,
                            'idNumber': item.idNumber || "",
                            'idName': item.idName || "",
                            'idType': item.idType || "",
                            'authOrg': item.authOrg || "",
                            'supplier': item.supplier || 0,
                            'beginDate': item.beginDate || 0,
                            'endDate': item.endDate || 0,
                            'effective': item.effective || 0,
                        })
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