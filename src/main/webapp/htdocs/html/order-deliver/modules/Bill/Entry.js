/**
 * 单据体模块
 * Created by yadda on 2017/5/13.
 */
define('Bill/Entry', function (require, module, exports) {

    var SMS = require('SMS');
    var GridBuilder = require('/GridBuilder');// 真实路径是'Bill/Entry/GridBuilder'

    var billGrid;
    var isNeedOpt = true; // 订单详情不可编辑
    var id; // 单据内码-判断是新增还是修改

    var billTemplate = {};

    var cleanGrid = function () {
        billGrid.clear();
    };

    var defaults = {
        gridName: 'bd-grid',
        width: $(window).width() - 5,
        height: 'auto',
    };

    //jqGrid初始化
    function gridRender(template, data) {

        if (!template.formFields["1"]) {
            return;
        }

        billTemplate = template;

        SMS.use('Grid', function (Grid) {

            billGrid = new Grid('bd-grid');

            //要展示的列
            var showKeys = [];

            defaults = GridBuilder.getConfig({
                'fields': template.formFields["1"],
                'defaults': defaults,
                'operator': 2, // 分录只能删除不能新增
                'showType': 1,
            });


            billGrid.render(defaults, data, template, 1);

            billGrid.on('f7Selected', function (data) {

            });

        });
    }

    function getData() {

        var entry = [];

        var gridData = billGrid.getGridDatas(1); // 获取第一个表体数据

        var entryTemplate = billTemplate.formFields["1"]

        var errorData = gridData["error"] || {};

        //新增数据
        $.Array.each(gridData["add"] || [], function (item, index) {

            var addData = {
                data: $.Object.grep(item, function (key, value) {
                    return !(value === null);
                }),
                flag: '1'
            };
            entry.push(addData);
        });

        var entryData = {
            1: entry
        };

        return {
            errorData: errorData,
            entryData: entryData,
        };
    }

    function render(template, data, itemId) {
        id = itemId;
        gridRender(template, data);
    }


    function showValidInfo(errorData) {

        var msgElement = document.getElementById('bd-grid-msg');
        $(msgElement).html('');

        if (errorData) {

            var errors = '';
            // 显示错误提示
            for (var item in errorData) {
                errors = errors + '<br/>第' + item + '行[' + errorData[item].join('-') + ']是必录项';
            }
            $(msgElement).html(errors);
            if (!$(msgElement).hasClass('show')) {
                $(msgElement).toggleClass('show');
            }

        }
    }

    return {
        render: render,
        getData: getData,
        showValidInfo: showValidInfo,
    };
});