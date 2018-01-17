/**
 * 单据体模块
 * Created by yadda on 2017/5/13.
 */
define('Entry', function (require, module, exports) {

    var SMS = require('SMS');
    var GridBuilder = require('/GridBuilder');// 真实路径是'Entry/GridBuilder'

    var billGrid;
    var isNeedOpt = true; // 订单详情不可编辑
    var showType = 0; // 0:1:2-查看/新增/编辑

    var billTemplate = {};
    var hasBind = false;

    var cleanGrid = function () {
        billGrid.clear();
    };

    //jqGrid初始化
    function gridRender(template, data) {

        if (!template.formFields["1"]) {
            return;
        }

        billTemplate = template;

        var defaults = {
            gridName: 'bd-grid',
            width: $(window).width() - 5,
            height: 'auto',
            classId: template.formClass.classId,
        };

        if (billGrid) {
/*            billGrid.clear();
            billGrid.render(defaults, data, template, 1);
            return;*/
            billGrid.unload();
        }
        SMS.use('Grid', function (Grid) {

            billGrid = new Grid('bd-grid');

            defaults = GridBuilder.getConfig({
                'fields': template.formFields["1"],
                'defaults': defaults,
                'operator': showType == 1 ? 2 : showType == 2 ? 2 : 0, // 新增时有添加删除按钮，编辑时有删除按钮,查看时无按钮
                'showType': showType,
            });


            billGrid.render(defaults, data, template, 1);

            billGrid.on('f7Selected', function (data) {

            });

            billGrid.on('afterEditCell', function (classId, rowid, cellname, value, iRow, iCol) {

            });

            billGrid.on('afterSaveCell', function (classId, rowid, cellname, value, iRow, iCol) {

                if (classId === 2020) {
                    // 发货单新增编辑时候值更新事件处理
                    // 下一迭代重构(应该由数据库配置字段值更新规则先)
                    switch (cellname) {
                        case 'actualQty':
                            // 实发数量变化后修改金额
                            // 1：获取物料单价
                            var price = billGrid.getCell(rowid, 'price');
                            console.log("price=" + price);
                            var amount = (value * price).toFixed(2);
                            billGrid.setCell(rowid, 'amount', amount)
                            break;
                    }

                }
            });

        });

    }

    function getData() {

        var entry = [];
        /*
         'flag':'1' 0删除, 1新增，2修改
         */
        var gridData = billGrid.getGridDatas(1); // 获取第一个表体数据

        var entryTemplate = billTemplate.formFields["1"]

        var errorData = gridData["error"] || []

        if ((gridData["add"] || []).length == 0 && (gridData["update"] || []).length == 0) {
            errorData['1'] = ['无有效分录，请在列表界面选择单据操作!'];
        }
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

        //修改数据
        $.Array.each(gridData["update"] || [], function (item, index) {

            var addData = {
                data: $.Object.grep(item, function (key, value) {
                    return !(value === null);
                }),
                flag: '2'
            };
            entry.push(addData);
        });

        //删除数据
        $.Array.each(gridData["delete"] || [], function (item, index) {

            var addData = {
                data: $.Object.grep(item, function (key, value) {
                    return !(value === null);
                }),
                flag: '0'
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

    function render(template, data, type) {
        showType = type;
        gridRender(template, data);

        bindEvents();
    }

    function bindEvents() {

        if (hasBind) {
            return;
        }
        var tid = null;

        //浏览器窗口大小变化时，需要重新获取可视区域的大小并重新调整。
        $(window).on('resize', function () {

            clearTimeout(tid);

            tid = setTimeout(function () { //窗口大小变化停止一定时间后才重新启动定时器
                billGrid.setGridWidth($(window).width() - 2);
            }, 500);

        });

        hasBind = true;

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