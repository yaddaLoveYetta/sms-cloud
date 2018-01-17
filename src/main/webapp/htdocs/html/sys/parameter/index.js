; (function () {

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
    $(document).on("blur change click", ":input", function (ev) {
        var value = "";
        var currVal = this.getAttribute("CurrVal");//this.getAttribute("CurrVal");
        var ctlType = this.getAttribute("CtlType").toLocaleLowerCase();
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
        }
        if (currVal == value) { //值未改变则不提交数据到后台
            return;
        }
        var thisDom = this;
        SysParameter.save(data, function () {
            thisDom.setAttribute("CurrVal", value);
            // refresh(false);
        });
        ev.stopPropagation();
    });

    function refresh(isShowLoading) {
        SysParameter.render(function () {
            NumberField.render();
        }, isShowLoading);
    };

    refresh(true);
})();