/**
 * 单据体模块
 * Created by yadda on 2017/5/13.
 */
define('Bill/Entry', function (require, module, exports) {

    var SMS = require('SMS');
    var GridBuilder = require('/GridBuilder');// 真实路径是'Bill/Entry/GridBuilder'

    var billGrid;
    var isNeedOpt = true; // 订单详情不可编辑

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
            var showKeys = ['entryId', 'parent', 'material', 'specification', 'unit', 'qty',
                'confirmDate', 'deliveryDate', 'confirmQty'];

            //可编辑的列
            var editKeys = ['confirmQty', 'confirmDate'];

            // gridConfig = GridBuilder.getConfig(template.formFields["1"], gridConfig, showKeys, editKeys);
            defaults = GridBuilder.getConfig({
                'fields': template.formFields["1"],
                'defaults': defaults,
                'showKeys': showKeys,
                'editKeys': editKeys,
                'operator': false,
            });


            billGrid.render(defaults, data, template, 1);

            billGrid.editCell(1,'confirmQty',true);

            billGrid.on('f7Selected', function (data) {

            });

        });
    }

    function getData() {

        var entry = [];
        /*
         'data':{
         FEntryID:0, 新增可不传
         FParkID:1,
         FParkName:'ade',
         FParkNumber:'001'
         },
         'flag':'1' 0删除, 1新增，2修改
         */
        var gridData = billGrid.getGridDatas(1); // 获取第一个表体数据

        var entryTemplate = billTemplate.formFields["1"]

        //修改数据
        $.Array.each(gridData["update"], function (item, index) {
            var upData = {
                entryId: item.entryId,
                confirmQty: item.confirmQty,
                confirmDate: item.confirmDate,
            };
            entry.push(upData);
        });

        var entryData = {
            1: entry
        };

        return entryData;
    }

    function render(template, data) {
        gridRender(template, data);
    }

    return {
        render: render,
        getData: getData,
    };
});