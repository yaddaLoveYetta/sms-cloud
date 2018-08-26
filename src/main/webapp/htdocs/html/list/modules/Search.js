/**
 * @Title: 搜索框模块
 * @author: yadda(silenceisok@163.com)
 * @date: 2018/3/22 16:38
 */

define("Search", function (require, module, exports) {


    var $ = require("$");
    var MiniQuery = require("MiniQuery");
    var SMS = require("SMS");
    var emitter = MiniQuery.Event.create();
    var DataSelector = require('DataSelector');

    //检查登录
    if (!SMS.Login.check(true)) {
        return;
    }
    // 事件绑定标识
    var hasBind = false;
    // 过来条件
    var conditions = [];
    // F7选择控件集合
    var selectors = {};
    // 日期控件集合
    var dateTimePickers = {};
    // 数字控件集合
    var numberFields = {};
    // 过滤字段模板
    var filterKeyFormFields = {};

    var div = document.getElementById('filter-template');

    var div_search_box = document.getElementById('search-box');

    var samples = $.String.getTemplates(div.innerHTML, [
            {
                name: 'all',
                begin: '<!--',
                end: '-->'
            },
            {
                name: 'select-option',
                begin: '#--option.begin--#',
                end: '#--option.end--#',
            }, {
                name: 'text-value',
                begin: '#--value.text.begin--#',
                end: '#--value.text.end--#'
            },
            {
                name: 'f7-value',
                begin: '#--value.f7.begin--#',
                end: '#--value.f7.end--#'
            }
        ])
    ;

    /* ctrlType
    1	数字
    2	数字带小数
    3	选择框
    5	下拉列表
    6	F7选择框
    7	级联选择器
    8	手机号码
    9	座机电话
    10	普通文本
    11	多行文本
    12	日期时间
    13	男：女
    14	密码控件
    15	是：否
    16	单价/金额(两位小数)

         EQUAL("ET", "=", "等于"), NOT_EQUAL("NET", "!=", "不等于"), LESS_THAN("LT", "<", "小于"), LESS_OR_EQUAL("LET", "<=", "小于或等于"),
        GREATER("GT", ">", "大于"), GREATER_OR_EQUAL("GET", ">=", "大于或等于"),
        LIKE("BLT", "LIKE", "LIKE模糊匹配"), IN("IN", "IN", "包含于"),
        NOT_SUPPORT("NOT_SUPPORT", "NOT_SUPPORT", "不支持的比较符号");

 */

    var _ctrlTypeOperator = {
        //-1数字-2	数字带小数-16	单价/金额(两位小数)
        1: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "小于", value: "LT"},
            {name: "小于等于", value: "LET"}, {name: "大于", value: "GT"}, {name: "大于等于", value: "GET"}],
        2: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "小于", value: "LT"},
            {name: "小于等于", value: "LET"}, {name: "大于", value: "GT"}, {name: "大于等于", value: "GET"}],
        16: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "小于", value: "LT"},
            {name: "小于等于", value: "LET"}, {name: "大于", value: "GT"}, {name: "大于等于", value: "GET"}],
        //3	选择框
        3: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}],
        //下拉框
        5: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "包含", value: "BLT"}],
        //F7选择框
        6: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "是空", value: "BN"}, {
            name: "不是空",
            value: "NBN"
        }],
        //级联选择器
        7: [{name: "等于", value: "ET"}, {name: "包含", value: "BLT"}],
        //手机号码
        8: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "包含", value: "BLT"},
            {name: "是空", value: "BN"}, {name: "不是空", value: "NBN"}],
        //座机电话
        9: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "包含", value: "BLT"},
            {name: "是空", value: "BN"}, {name: "不是空", value: "NBN"}],
        //普通文本
        10: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "包含", value: "BLT"},
            {name: "是空", value: "BN"}, {name: "不是空", value: "NBN"}],
        //多行文本
        11: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "包含", value: "BLT"},
            {name: "是空", value: "BN"}, {name: "不是空", value: "NBN"}],
        // 日期
        12: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}, {name: "小于", value: "LT"},
            {name: "小于等于", value: "LET"}, {name: "大于", value: "GT"}, {name: "大于等于", value: "GET"},
            {name: "是空", value: "BN"}, {name: "不是空", value: "NBN"}],
        //男：女
        13: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}],
        //密码控件
        14: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}],
        //是：否
        15: [{name: "等于", value: "ET"}, {name: "不等于", value: "NET"}]
    };

    function render(filterItems) {

        if (hasBind) {
            // 保留已经设置好的条件,不在重新渲染
            return;
        }

        if (filterItems.length === 0) {
            // 没有可过滤字段时不出现过滤条件
            $(div_search_box).remove();
            return;
        }

        bindEvents();

        filterKeyFormFields = $.Array.toObject(filterItems, function (item, index) {
            return [item.key, item];
        });

        // 根据模板填充过滤字段
        $('.field-key').each(function (index, selector) {

            selector.innerHTML = $.Array.keep(filterItems, function (item, index) {

                return $.String.format(samples['select-option'], {
                    'index': index,
                    'name': item.name,
                    'value': item.key,
                    'ctrlType': item.ctrlType
                })
            }).join("");

            $(selector).trigger('change');

        });
    }

    function bindEvents() {

        if (hasBind) {
            return;
        }

        $('.search-more').on('click', function (e) {

            $(this).closest('table').find('.search-line:not(.search-line-first)').removeClass('hidden');

            $(this).closest('table').addClass('show-more');
        });

        $('.search-lite').on('click', function (e) {

            $(this).closest('table').find('.search-line:not(.search-line-first)').addClass('hidden');

            $(this).closest('table').removeClass('show-more');

        });
        // 比较字段变化事件-动态填充比较符号及值控件类型
        $('.field-key').on("change", function (ev) {

            var self = this;

            var trIndex = $(this).data('index');

            var $selected = $(this).find("option:selected");

            var fieldKey = $selected.val();
            var ctrlType = $selected.data('ctrltype');


            if (!ctrlType && !fieldKey) {
                return;
            }

            var compareKeys = _ctrlTypeOperator[ctrlType];

            var html = $.Array.keep(compareKeys, function (item, index) {

                return $.String.format(samples['select-option'], {
                    'index': index,
                    'name': item.name,
                    'value': item.value,
                    'ctrlType': ctrlType
                })
            }).join("");

            // 比较操作符呈现
            var $operator = $(this).parent('td').parent("tr").find("select[name='operator']");
            $operator.html(html);
            // 值控件类型呈现-主要针对引用类型及数字，日期等特殊类型
            initValueController(self, trIndex, fieldKey, ctrlType);
            $operator.trigger('change');

            ev.stopPropagation();
        });

        // 比较符号变化后-处理值控件类型
        $('.operator').on("change", function (ev) {

            var self = this;
            var $selected = $(this).find("option:selected");
            var operator = $selected.val();

            if (!operator) {
                return;
            }

            if (operator === 'BN' || operator === 'NBN') {
                $(this).parent('td').parent("tr").find("input[name='value']").attr("disabled", true).val('');
            } else {
                $(this).parent('td').parent("tr").find("input[name='value']").attr("disabled", false);
            }

        });

        // 根据比较字段类型构建比较值呈现形式 eg:date,number,price,f7
        function initValueController(container, trIndex, fieldKey, ctrlType) {

            var key = trIndex + '-' + fieldKey;
            // 获取旧条件值
            var $targetInput = $(container).parent('td').parent("tr").find("input[name='value']");
            var oldValue = $targetInput.val() || '';
            // 重新填充值默认控件
            var $targetTd = $(container).parent('td').parent("tr").find("td[data-name='value']");
            $targetTd.html(samples['text-value']);

            var target = $(container).parent('td').parent("tr").find("input[name='value']")[0];

            // 清空dateTimePickers numberFields 实例
            $.Object.each(dateTimePickers, function (key, value) {
                dateTimePickers[key].remove();
                delete dateTimePickers[key];
            });

            $.Object.each(numberFields, function (key, value) {
                numberFields[key].destroy();
                delete numberFields[key];
            });


            if (ctrlType === 1 || ctrlType === 2 || ctrlType === 16) {

                if (ctrlType === 1) {
                    // 数字-无小数
                    config = {
                        decimalCount: 0,
                        empty: 'zero'
                    };
                } else if (ctrlType === 2) {
                    // 数字-两位小数
                    config = {
                        decimalCount: 2,
                        empty: 'zero'
                    };
                } else if (ctrlType === 16) {
                    // 单价金额-两位小数
                    config = {
                        decimalCount: 2,
                        empty: '',
                        currencySign: '¥'
                    };
                }
                SMS.use('NumberField', function (NumberField) {

                    var numberField = new NumberField(target, config);

                    // 控件对象缓存下来，设置获取值时使用
                    numberFields[key] = numberField;

                });

            }

            else if (ctrlType === 12) {
                // 日期控件
                SMS.use('DateTimePicker', function (DateTimePicker) {

                    var dateTimePicker = new DateTimePicker(target, {
                        format: 'yyyy-mm-dd',
                        autoclose: true,
                        todayBtn: true,
                        todayHighlight: true,
                        timepicker: false,
                        startView: 'month',
                        minView: 2
                    });

                    // 控件对象缓存下来，设置获取值时使用
                    dateTimePickers[key] = dateTimePicker;
                });

            }

            else if (ctrlType === 6) {
                // F7
                $targetTd.html('');
                $targetTd.html(samples['f7-value']);

                var field = filterKeyFormFields[fieldKey];
                var config = {
                    targetType: 1, //跳转方案
                    classId: field.lookUpClassId,
                    destClassId: field.classId,
                    hasBreadcrumbs: true,
                    fieldKey: field.key,
                    container: $targetTd.find("div[data-name='lookUp']")[0],
                    title: field.name,
                    // 控件label有焦点时显示代码，无焦点时显示名称效果
                    dataFieldKey: {
                        // F7控件关联资料的id 值
                        'id': field.srcField,
                        // F7控件无焦点时显示的字段key(引用基础资料的模板key)
                        'name': field.displayField,
                        // F7有焦点时显示的字段key(引用基础资料的模板key)
                        'number': field.displayExt
                    },
                    defaults: {
                        pageSize: 10
                    }
                };

                // 个性化配置
                var pConfig = emitter.fire('initSelector', [field.classId, field.lookUpClassId, field.key, field]);

                pConfig = pConfig && pConfig[pConfig.length - 1];

                config = $.Object.extend({}, config, pConfig);

                selectors[key] = DataSelector.create(config);
            }

            else {
                $(container).parent('td').parent("tr").find("input[name='value']").val(oldValue);
            }

        }

        // 搜索按钮
        $('#search').on('click', function (e) {
            //清空过滤条件
            conditions = [];
            $('.search-line').each(function (index, item) {
                //条件链接符号
                var linkType = $(item).find("select[name='linkType']").val() || 'and';
                // 比较字段key
                var fieldKey = $(item).find("select[name='fieldKey']").val();
                // 比较符号
                var operator = $(item).find("select[name='operator']").val();
                // 比较值
                var value;

                // 根据控件类型获取比较值
                var trIndex = $(item).find("select[name='fieldKey']").data('index');
                var ctrlType = $(item).find("select[name='fieldKey']").find("option:selected").data('ctrltype');

                var key = trIndex + '-' + fieldKey;

                if (ctrlType === 1 || ctrlType === 2 || ctrlType === 16) {

                    value = numberFields[key] && numberFields[key].get();

                } else if (ctrlType === 12) {

                    // 日期控件
                    // 不要从控件取值，控件有默认值当前日期
                    //value = dateTimePickers[key] && dateTimePickers[key].getFormattedDate();
                    value = $(item).find("input[name='value']").val()

                } else if (ctrlType === 6) {
                    // F7
                    value = selectors[key] && selectors[key].getData()[0]['id'];
                } else {
                    value = $(item).find("input[name='value']").val();
                }


                if (!fieldKey) {
                    return true;
                }

                if (operator !== 'BN' && operator !== 'NBN') {
                    // is Null || is not null 条件不需要传比较值-其他比较类型如果没有值则忽略此条件

                    if (typeof value === 'string' && value.trim() === '') {
                        return true;
                    }

                    if (typeof value === 'number' && value === 0) {
                        return true;
                    }

                }

                if (operator === 'BLT') {
                    // 包含（like查询）条件时，前端自主控制%位置，有利于精确匹配，利用索性提高效率，如果没有%符号，则默认全模糊,
                    if (value.indexOf('%') === -1) {
                        value = '%' + value + '%';
                    }
                }

                if (typeof value === 'string') {
                    value = value.trim();
                }

                // 查询条件
                conditions.push({
                    'linkType': linkType,
                    'leftParenTheses': '(',
                    'fieldKey': fieldKey,
                    'logicOperator': operator,
                    'value': value,
                    'rightParenTheses': ')',
                    'needConvert': ctrlType !== 6
                });

            });
            emitter.fire('doSearch', [conditions]);
        });

        $(document).bind('keypress', function (event) {
            if (event.keyCode === 13) {
                $('#search').trigger("click");
            }
        });

        hasBind = true;
    }

    return {
        render: render,
        on: emitter.on.bind(emitter)
    }
});