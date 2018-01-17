/**
 * Created by yadda on 2017/6/15.
 */

;(function () {

    var List = require("List");
    var Pager = require("Pager");
    var Tabs = require("Tabs");

    var DatetimePicker = require("DatetimePicker");

    var SelectorList = require("SelectorList");

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    var config = {
        pageNo: 1,
        pageSize: 10,
    };

    $("#search").bind("click", function () {
        search();
    });
    $(document).bind('keypress', function (event) {
        if (event.keyCode == 13) {
            search();
        }
    });

    function search(defaultCondition) {

        // 订单内码
        var order = SelectorList["order"].getData()[0].ID;
        // 供应商
        var supplier = SelectorList["supplier"].getData()[0].ID;
        // 开始日期
        var beginDate = $("#beginDate").val();
        // 结束日期
        var endDate = $("#endDate").val();

        config.pageNo = 1;

        config.classId = 2020;// 2020：发货单2021；收获信息2022：入库信息2023：退货信息5：付款信息 查询时默认查发货信息

        delete config.supplier;
        delete config.order;
        delete config.beginDate;
        delete config.endDate;

        if ($.trim(supplier) != "") {
            config.supplier = supplier;
        }
        if ($.trim(order) != "") {
            config.order = order;
        }
        if ($.trim(beginDate) != "") {
            config.beginDate = beginDate;
        }
        if ($.trim(endDate) != "") {
            config.endDate = endDate;
        }

        // 点击按钮查询时Tabs回到第一个页签
        $('#tab-container').easytabs('select', 'li#2020');


        List.render(config, function (total, pageSize) {
            Pager.render({
                size: pageSize,
                total: total,
                change: function (no) {
                    config.pageNo = no;
                    List.render(config);
                }
            });
        });
    }

    Tabs.on({
        'easytabs:midTransition': function (targetClassId) {
            //alert(targetClassId);
            config.pageNo = 1;
            config.classId = targetClassId;
            List.render(config, function (total, pageSize) {
                Pager.render({
                    size: pageSize,
                    total: total,
                    change: function (no) {
                        config.pageNo = no;
                        List.render(config);
                    }
                });
            });
        }
    });

    DatetimePicker.render();
    Tabs.render();

    search();

})();