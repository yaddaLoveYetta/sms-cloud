(function () {

    var List = require("List");
    var Pager = require("Pager");
    var DatetimePicker = require("DatetimePicker");

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }
    var config = {
        pageNo: 1,
        pageSize: 10,
        classId: 14001,
        conditions: {}
    };

    function refresh() {

        var user = $("#user").val();
        var beginTime = $("#beginTime").val();
        var endTime = $("#endTime").val();
        config.pageNo = 1;
        config.conditions = {};
        if ($.trim(user) != "") {
            config.conditions["userName"] = {
                andOr: 'AND',
                leftParenTheses: '(',
                fieldKey: 'userName',
                logicOperator: 'like',
                value: user,
                rightParenTheses: ')'
            };
        }
        if ($.trim(beginTime) != "") {
            config.conditions["beginTime"] = {
                andOr: 'AND',
                leftParenTheses: '(',
                fieldKey: 'operateTime',
                logicOperator: '>=',
                value: beginTime,
                rightParenTheses: ')'
            };
        }
        if ($.trim(endTime) != "") {
            config.conditions["endTime"] = {
                andOr: 'AND',
                leftParenTheses: '(',
                fieldKey: 'operateTime',
                logicOperator: '<=',
                value: endTime,
                rightParenTheses: ')'
            };
        }

        config.orderBy = [{
            fieldKey: 'id',
            orderDirection: 'DESC',
        }];
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

    $("#jsSearch").bind("click", function () {
        refresh();
    });


    $(document).bind('keypress', function (event) {
        if (event.keyCode == 13) {
            refresh();
        }
    });


    DatetimePicker.render();
    refresh();
})();