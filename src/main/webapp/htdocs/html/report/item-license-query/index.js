;(function () {

    var List = require("List");
    var Pager = require("Pager");

    var DatetimePicker = require("DatetimePicker");

    var SelectorList = require("SelectorList");

    var config = {
        pageNo: 1,
        pageSize: 5,
        conditions: {}
    };

    $("#jsSearch").bind("click", function () {
        search();
    });
    $(document).bind('keypress', function (event) {
        if (event.keyCode == 13) {
            search();
        }
    });

    function search() {

        var itemNumber = $("#itemNumber").val().trim();
        var itemName = $("#itemName").val().trim();
        var itemModel = $("#itemModel").val().trim();
        var manufacturer = $("#manufacturer").val().trim();
        var idNumber = $("#idNumber").val().trim();
        var idName = $("#idName").val().trim();
        var idType = SelectorList["idType"].getData()[0].ID;
        var authOrg = $("#authOrg").val().trim();
        var supplier = SelectorList["supplier"].getData()[0].ID;
        var soonExpired=$('#soonExpired').is(':checked');
        var expired=$('#expired').is(':checked');

        //默认过滤条件
        config.conditions = {};
        if ($.trim(itemNumber) != "") {
            config.conditions['itemNumber'] = itemNumber;
        }
        if ($.trim(itemName) != "") {
            config.conditions['itemName'] = itemName;
        }
        if ($.trim(itemModel) != "") {
            config.conditions['itemModel'] = itemModel;
        }
        if ($.trim(manufacturer) != "") {
            config.conditions['manufacturer'] = manufacturer;
        }
        if ($.trim(idNumber) != "") {
            config.conditions['idNumber'] = idNumber;
        }
        if ($.trim(idName) != "") {
            config.conditions['idName'] = idName;
        }
        if ($.trim(idType) != "") {
            config.conditions['idType'] = idType;
        }
        if ($.trim(authOrg) != "") {
            config.conditions['authOrg'] = authOrg;
        }
        if ($.trim(supplier) != "") {
            config.conditions['supplier'] = supplier;
        }
        if (soonExpired ) {
            config.conditions['soonExpired'] = 1;
        }
        if (expired )    {
            config.conditions['expired'] = 1;
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

    search();
})();