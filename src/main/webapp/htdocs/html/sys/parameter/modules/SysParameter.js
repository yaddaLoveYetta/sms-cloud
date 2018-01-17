define("SysParameter", function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require("API");

    var div = document.getElementById("div-content");
    var divTemplat = document.getElementById("div-Templat");

    var textArrayCtrl = ['', 'text', 'input', 'number', 'int', 'float'];//文本框 控件集合
    var selectArrayCtrl = ['selector', 'select'];
    var checkArrayboxCtrl = ['checkbox'];

    var samples = $.String.getTemplates(divTemplat.innerHTML, [
        {
            name: 'temp',
            begin: '<!--',
            end: '-->',
        },
        {
            name: 't.text',
            begin: '#--text.begin--#',
            end: '#--text.end--#',
            outer: '{texts}'
        },
        {
            name: 't.select',
            begin: '#--select.begin--#',
            end: '#--select.end--#',
            outer: '{selects}'
        },
        {
            name: 't.option',
            begin: '#--option.begin--#',
            end: '#--option.end--#',
            outer: '{options}'
        },
        {
            name: 't.checkbox',
            begin: '#--checkbox.begin--#',
            end: '#--checkbox.end--#',
            outer: '{checkbox}'
        }
    ]);


    var load = function (fn, isShowLoading) {
        if (isShowLoading) {
            SMS.Tips.loading('数据加载中..');
        }
        var api = new API("sys/getSysParams");
        api.post();

        api.on({
            'success': function (data, json) {
                if (isShowLoading) {
                    SMS.Tips.success('数据加载成功', 1500);
                }
                fn && fn(data, json);
            },

            'fail': function (code, msg, json) {
                if (isShowLoading) {
                    var s = $.String.format('{0} (错误码: {1})', msg, code);
                }
                SMS.Tips.error(s);
            },
            'error': function () {
                SMS.Tips.error('网络繁忙，请稍候再试');
            }
        });
    }

    var save = function (data, fn) {

        var api = new API("sys/setSysParam");
        api.post({
            category: data.category,
            key: data.key,
            value: data.value
        });

        api.on({
            'success': function (data, json) {
                SMS.Tips.success('保存成功', 1500);
                fn && fn(data, json);
            },

            'fail': function (code, msg, json) {
                var s = $.String.format('{0} (错误码: {1})', msg, code);
                SMS.Tips.error(s);
            },

            'error': function () {
                SMS.Tips.error('网络繁忙，请稍候再试');
            }
        });
    }

    var render = function (fn, isShowDialogding) {

        load(function (sysParmentData, isShowDialogding) {

            div.innerHTML = $.Array.keep(sysParmentData, function (item, index) {

                var explanation = $.Object.parseJson(item["explanation"]);
                var ctlType = "";//控件类型
                var optionList = [];//下拉集合列表
                if (explanation) {
                    ctlType = explanation.ctlType.toLocaleLowerCase() || "";
                    optionList = explanation.list || [];
                }
                if ($.inArray(ctlType, textArrayCtrl) >= 0) { //Text
                    return $.String.format(samples['t.text'], {
                        'desc': item.desc,
                        'CtlType': ctlType,
                        'key': item.key,
                        'name': item.name,
                        'category': item.category,
                        'value': item.value,
                        'CurrVal': item.value,
                        'disabled': Boolean(item.readOnly) ? "disabled" : ""
                    });
                }
                if ($.inArray(ctlType, selectArrayCtrl) >= 0) {

                    //select
                    return $.String.format(samples['t.select'], {
                        'desc': item.desc,
                        'CtlType': ctlType,
                        'key': item.key,
                        'name': item.name,
                        'category': item.category,
                        'value': item.value,
                        'CurrVal': item.value,
                        'disabled': Boolean(item.readOnly) ? "disabled" : "",
                        'options': $.Array.keep(optionList, function (opiton, index) {
                            var key = Object.keys(opiton)[0] || "";
                            var name = opiton[key] || "";
                            return $.String.format(samples['t.option'], {
                                'value': key,
                                'name': name,
                                'selected': (item.FValue == key) ? "selected" : ""
                            });
                        }).join("")
                    });
                }

                if ($.inArray(ctlType, checkArrayboxCtrl) >= 0) { //checkbox
                    return $.String.format(samples['t.checkbox'], {
                        'desc': item.desc,
                        'CtlType': ctlType,
                        'key': item.key,
                        'name': item.name,
                        'CurrVal': item.value,
                        'category': item.category,
                        'checked': Boolean(Number(item.value)) === true ? "checked" : "",
                        'disabled': Boolean(item.readOnly) ? "disabled" : ""
                    });
                }
            }).join("");


            fn && fn();

        });
    };
    return {
        render: render,
        textArrayCtrl: textArrayCtrl,
        selectArrayCtrl: selectArrayCtrl,
        checkArrayboxCtrl: checkArrayboxCtrl,
        save: save
    };
});