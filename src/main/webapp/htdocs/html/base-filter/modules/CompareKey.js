define('CompareKey', function (require, module, exports) {
    //表 t_SubSysEnum FTypeID =1 有说明
    var dataTypeJson = {
        //数字
        1: [{name: "等于", value: "="}, {name: "小于", value: "<"}, {name: "小于等于", value: "<="}, {
            name: "大于",
            value: ">"
        }, {name: "大于等于", value: ">="}],
        //文本值
        2: [{name: "包含", value: "like"}, {name: "等于", value: "="}],
        //日期时间
        3: [{name: "等于", value: "="}, {name: "小于", value: "<"}, {name: "小于等于", value: "<="}, {
            name: "大于",
            value: ">"
        }, {name: "大于等于", value: ">="}],
        //布尔
        4: [{name: "等于", value: "="}]
    };

    //表 t_SubSysEnum FTypeID =20 有说明
    var ctrlTypeJson = {
        //小数
        1: [{name: "等于", value: "="}, {name: "小于", value: "<"}, {name: "小于等于", value: "<="}, {
            name: "大于",
            value: ">"
        }, {name: "大于等于", value: ">="}],
        //checkbox
        3: [{name: "等于", value: "="}],
        //下拉框
        5: [{name: "包含", value: "like"}, {name: "等于", value: "="}],
        //F7选择框
        6: [{name: "包含", value: "like"}, {name: "等于", value: "="}],
        //手机号码
        8: [{name: "包含", value: "like"}, {name: "等于", value: "="}],
        // 日期
        12: [{name: "等于", value: "="}, {name: "小于", value: "<"}, {name: "小于等于", value: "<="}, {
            name: "大于",
            value: ">"
        }, {name: "大于等于", value: ">="}],
        11: [{name: "包含", value: "like"}, {name: "等于", value: "="}],
    };

    function setCompareKey(sourceTagte, dataType, ctrlType) {
        var compareKeyItems = [];
        if (ctrlType && ctrlType !== "null" && ctrlType !== "undefined") {
            compareKeyItems = ctrlTypeJson[ctrlType] || [];
        } else {
            compareKeyItems = dataTypeJson[dataType] || [];
        }

        var keyHtml = "";
        $.Array.keep(compareKeyItems, function (item, index) {
            keyHtml += "<option value='" + item.value + "'>" + item.name + "</option>";
        });
        $(sourceTagte).parents("tr").find("select[name='compareKeys']").html(keyHtml)
    }

    return {
        setCompareKey: setCompareKey
    }
});