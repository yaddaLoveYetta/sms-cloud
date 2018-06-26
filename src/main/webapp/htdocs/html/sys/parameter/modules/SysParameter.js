define("SysParameter", function (require, module, exports) {

    var $ = require('$');
    var MiniQuery = require('MiniQuery');
    var SMS = require('SMS');
    var API = SMS.require("API");

    var div = document.getElementById("div-content");
    var divTemplate = document.getElementById("div-template");

    //文本框渲染类别
    var textCtrl = ['', 'text', 'input', 'number', 'int', 'float'];
    // 下拉选择框渲染类别
    var selectCtrl = ['selector', 'select'];
    // 选择框渲染类别
    var checkBoxCtrl = ['checkbox'];

    var samples = $.String.getTemplates(divTemplate.innerHTML, [
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
        var api = new API("setting/getAll");
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
    };

    var save = function (data, fn) {

        var api = new API("setting/save");
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
    };

    var render = function (fn, isShowDialogding) {

        load(function (sysParmentData, isShowDialogding) {

            div.innerHTML = $.Array.keep(sysParmentData, function (item, index) {

                var explanation = $.Object.parseJson(item["explanation"]);
                //控件类型-默认文本框呈现
                var ctlType = "text";

                if (explanation) {
                    ctlType = explanation.ctlType.toLocaleLowerCase() || "";
                }

                if ($.inArray(ctlType, textCtrl) >= 0) {
                    //Text
                    return $.String.format(samples['t.text'], {
                        'desc': item.desc,
                        'ctlType': ctlType,
                        'key': item.key,
                        'name': item.name,
                        'category': item.category,
                        'value': item.value,
                        'currVal': item.value,
                        'disabled': Boolean(item.readOnly) ? "disabled" : ""
                    });
                }
                if ($.inArray(ctlType, selectCtrl) >= 0) {

                    // 下拉框可选值列表
                    var optionList = explanation.list || [];

                    //select
                    return $.String.format(samples['t.select'], {
                        'desc': item.desc,
                        'ctlType': ctlType,
                        'key': item.key,
                        'name': item.name,
                        'category': item.category,
                        'value': item.value,
                        'currVal': item.value,
                        'disabled': Boolean(item.readOnly) ? "disabled" : "",
                        'options': $.Array.keep(optionList, function (opiton, index) {
                            var key = Object.keys(opiton)[0] || "";
                            var name = opiton[key] || "";
                            return $.String.format(samples['t.option'], {
                                'value': key,
                                'name': name,
                                'selected': (item.value == key) ? "selected" : ""
                            });
                        }).join("")
                    });
                }

                if ($.inArray(ctlType, checkBoxCtrl) >= 0) {
                    //checkbox
                    return $.String.format(samples['t.checkbox'], {
                        'desc': item.desc,
                        'ctlType': ctlType,
                        'key': item.key,
                        'name': item.name,
                        'currVal': item.value,
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
        textArrayCtrl: textCtrl,
        selectArrayCtrl: selectCtrl,
        checkArrayboxCtrl: checkBoxCtrl,
        save: save
    };
});