;(function () {

    var $ = require("$");
    var SysParameter = require("SysParameter");
    var NumberField = require("NumberField");

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }

    /**
     * input标签blur保存
     */

    $(document).on("change", ":input", function (ev) {
        var value = "";
        var currVal = this.getAttribute("currVal");
        var ctlType = this.getAttribute("ctlType").toLocaleLowerCase();
        if ($.inArray(ctlType, SysParameter.selectArrayCtrl) >= 0 || $.inArray(ctlType, SysParameter.textArrayCtrl) >= 0) {
            //select || text
            if (ctlType === "int" || ctlType === "float") {
                value = $(this).autoNumeric('get');
            } else {
                value = this.value;
            }
        }
        if ($.inArray(ctlType, SysParameter.checkArrayboxCtrl) >= 0) { //checkbox
            var isCheck = $(this).is(":checked");
            value = Number(isCheck);
        }

        var data = {
            category: this.getAttribute("category"),
            key: this.getAttribute("name"),
            value: value
        };

        if (currVal === value) { //值未改变则不提交数据到后台
            return;
        }

        var thisDom = this;
        SysParameter.save(data, function () {
            thisDom.setAttribute("currVal", value);
        });
        ev.stopPropagation();
    });

    function refresh(isShowLoading) {
        SysParameter.render(function () {
            NumberField.render();
        }, isShowLoading);
    }

    refresh(true);

})();