/**
 * Created by yadda on 2017/6/15.
 */
;(function () {

    var List = require("List");
    var Pager = require("Pager");

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

        // 物料
        var material = SelectorList["material"].getData()[0].ID;
        // 供应商
        var supplier = SelectorList["supplier"].getData()[0].ID;
        // 开始日期
        var beginDate = $("#beginDate").val();
        // 结束日期
        var endDate = $("#endDate").val();

        config.pageNo = 1;

        delete config.supplier;
        delete config.material;
        delete config.beginDate;
        delete config.endDate;

        if ($.trim(supplier) != "") {
            config.supplier = supplier;
        }
        if ($.trim(material) != "") {
            config.material = material;
        }
        if ($.trim(beginDate) != "") {
            config.beginDate = beginDate;
        }
        if ($.trim(endDate) != "") {
            config.endDate = endDate;
        }

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

    DatetimePicker.render();
    search();

})();